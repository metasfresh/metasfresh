package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_Cond;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.modelvalidator.SponsorCondValidator;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ISponsorCondition;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.HierarchyDescender;
import de.metas.logging.LogManager;

public class SponsorCondition implements ISponsorCondition
{

	private final Logger logger = LogManager.getLogger(SponsorCondition.class);

	@Override
	public List<I_C_Sponsor_SalesRep> updateSSRs(final I_C_Sponsor_Cond condChange)
	{
		Check.assume(condChange.isProcessed(), condChange + " has Processed='Y'");

		final Properties ctx = InterfaceWrapperHelper.getCtx(condChange);
		final String trxName = InterfaceWrapperHelper.getTrxName(condChange);

		// Check if there are existing commission instances that might be affected by the change
		// If there are existing instances, check if the current user is allowed to do retroactive changes.
		final Map<Integer, List<IAdvComInstance>> comSystemId2AffectedInstances = checkIfRetroactiveChange(condChange);

		for (final int comSystemId : comSystemId2AffectedInstances.keySet())
		{
			final I_C_AdvComSystem comSystem = InterfaceWrapperHelper.create(ctx, comSystemId, I_C_AdvComSystem.class, trxName);

			if (!isLoginUserAdminForComSystem(ctx, comSystem, trxName))
			{
				logger.info("Current user is not allowed to do retroactive hierarchy changes, as far as " + comSystem + " is involved");
				throw new AdempiereException(Env.getAD_Language(ctx), SponsorCondValidator.MSG_COMMISSION_RETROACTIVE_HIERARCHY_CHANGE_1P, new Object[] { comSystem.getName() });
			}
			else
			{
				if (!Services.get(IClientUI.class).ask(0, "CommissionRetroactiveHierarchyChange_Confirm"))
				{
					throw new AdempiereException(Env.getAD_Language(ctx), SponsorCondValidator.MSG_COMMISSION_RETROACTIVE_HIERARCHY_CHANGE_CANCELED, new Object[0]);
				}
			}
		}

		final List<I_C_Sponsor_SalesRep> newSSRs = new ArrayList<I_C_Sponsor_SalesRep>();

		for (final I_C_Sponsor_CondLine line : retrieveLines(condChange))
		{
			final List<I_C_Sponsor_SalesRep> oldSSRs = Services.get(ISponsorDAO.class)
					.retrieveSalesRepLinksForSponsor(ctx, condChange.getC_Sponsor_ID(), line.getDateFrom(), line.getDateTo(), trxName);

			for (final I_C_Sponsor_SalesRep oldSSR : oldSSRs)
			{
				if (oldSSR.getSponsorSalesRepType().equals(line.getSponsorSalesRepType()))
				{
					final boolean ssrStartsBeforeLine = oldSSR.getValidFrom().before(line.getDateFrom());
					final boolean ssrEndsAfterLine = oldSSR.getValidTo().after(line.getDateTo());

					final Timestamp oldSSRValidFrom = oldSSR.getValidFrom();
					final Timestamp oldSSRValidTo = oldSSR.getValidTo();

					if (ssrStartsBeforeLine)
					{
						final I_C_Sponsor_SalesRep newSsrBeforeLine = copySSR(ctx, oldSSR, trxName);
						newSsrBeforeLine.setValidFrom(oldSSRValidFrom);
						newSsrBeforeLine.setValidTo(TimeUtil.addDays(line.getDateFrom(), -1));
						InterfaceWrapperHelper.save(newSsrBeforeLine);

						newSSRs.add(newSsrBeforeLine);
					}

					if (ssrEndsAfterLine)
					{
						final I_C_Sponsor_SalesRep newSsrAfterLine = copySSR(ctx, oldSSR, trxName);
						newSsrAfterLine.setValidFrom(TimeUtil.addDays(line.getDateTo(), 1));
						newSsrAfterLine.setValidTo(oldSSRValidTo);
						InterfaceWrapperHelper.save(newSsrAfterLine);

						newSSRs.add(newSsrAfterLine);
					}

					InterfaceWrapperHelper.delete(oldSSR);
				}
			}
			newSSRs.addAll(createSSRForLine(ctx, condChange, line, trxName));
		}

		return newSSRs;
	}

	private I_C_Sponsor_SalesRep copySSR(
			final Properties ctx,
			final I_C_Sponsor_SalesRep ssr,
			final String trxName)
	{
		final I_C_Sponsor_SalesRep newSSR = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, trxName);
		newSSR.setSponsorSalesRepType(ssr.getSponsorSalesRepType());
		newSSR.setAD_Org_ID(ssr.getAD_Org_ID());
		newSSR.setC_AdvComSystem_ID(ssr.getC_AdvComSystem_ID());
		newSSR.setC_Sponsor_Parent_ID(ssr.getC_Sponsor_Parent_ID());
		newSSR.setC_AdvCommissionCondition_ID(ssr.getC_AdvCommissionCondition_ID());
		newSSR.setC_BPartner_ID(ssr.getC_BPartner_ID());
		newSSR.setValidTo(ssr.getValidTo());
		newSSR.setC_Sponsor_ID(ssr.getC_Sponsor_ID());

		return newSSR;
	}

	private List<I_C_Sponsor_SalesRep> createSSRForLine(
			final Properties ctx,
			final I_C_Sponsor_Cond condChange,
			final I_C_Sponsor_CondLine line,
			final String trxName)
	{
		if (line.getC_BPartner_ID() <= 0 && line.getC_Sponsor_Parent_ID() <= 0)
		{
			// nothing to do
			return Collections.emptyList();
		}

		final I_C_Sponsor_SalesRep newSsrForLine = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, trxName);

		newSsrForLine.setSponsorSalesRepType(line.getSponsorSalesRepType());
		newSsrForLine.setAD_Org_ID(line.getAD_Org_ID());
		newSsrForLine.setC_AdvComSystem_ID(line.getC_AdvComSystem_ID());
		newSsrForLine.setC_Sponsor_Parent_ID(line.getC_Sponsor_Parent_ID());
		newSsrForLine.setC_AdvCommissionCondition_ID(line.getC_AdvCommissionCondition_ID());
		newSsrForLine.setC_BPartner_ID(line.getC_BPartner_ID());
		newSsrForLine.setValidFrom(line.getDateFrom());
		newSsrForLine.setValidTo(line.getDateTo());
		newSsrForLine.setC_Sponsor_ID(condChange.getC_Sponsor_ID());

		InterfaceWrapperHelper.save(newSsrForLine);

		return Collections.singletonList(newSsrForLine);
	}

	/**
	 * Iterates the downline and checks if there are already commission instances for any downline node.
	 * 
	 * @param ssr
	 * @return
	 */
	private Map<Integer, List<IAdvComInstance>> checkIfRetroactiveChange(final I_C_Sponsor_Cond condChange)
	{
		//
		// get a mapping C_AdvComSysem_ID => Time interval covered by the condChange lines
		final Map<Integer, Timestamp[]> comSystemId2Dates = new HashMap<Integer, Timestamp[]>();
		for (final I_C_Sponsor_CondLine line : retrieveLines(condChange))
		{
			Timestamp[] dates = comSystemId2Dates.get(line.getC_AdvComSystem_ID());
			if (dates == null)
			{
				dates = new Timestamp[] { line.getDateFrom(), line.getDateTo() };
				comSystemId2Dates.put(line.getC_AdvComSystem_ID(), dates);
			}
			else
			{
				if (dates[0].after(line.getDateFrom()))
				{
					dates[0] = line.getDateFrom();
				}
				if (dates[1].before(line.getDateTo()))
				{
					dates[1] = line.getDateTo();
				}
			}
		}

		final Map<Integer, List<IAdvComInstance>> result = new HashMap<Integer, List<IAdvComInstance>>();

		for (final int comSystemId : comSystemId2Dates.keySet())
		{
			final ArrayList<IAdvComInstance> currentList = new ArrayList<IAdvComInstance>();

			final Timestamp currentDateFrom = comSystemId2Dates.get(comSystemId)[0];
			final Timestamp currentDateTo = comSystemId2Dates.get(comSystemId)[1];

			final Set<Integer> alreadyAdded = new HashSet<Integer>();

			new HierarchyDescender()
			{
				@Override
				public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
						final int logicalLevel, final int hierarchyLevel,
						final Map<String, Object> contextInfo)
				{
					final List<IAdvComInstance> existingInstances = Services.get(ICommissionInstanceDAO.class).retrieveForSalesRep(sponsorCurrentLevel, currentDateFrom, currentDateTo, 0);

					existingInstances.addAll(Services.get(ICommissionInstanceDAO.class).retrieveForCustomer(sponsorCurrentLevel, currentDateFrom, currentDateTo, 0));

					for (final IAdvComInstance inst : existingInstances)
					{
						if (alreadyAdded.add(inst.getC_AdvCommissionInstance_ID()))
						{
							logger.debug("Adding instance " + inst + " that might be affected by ssr change");
							currentList.add(inst);
						}
					}

					return Result.GO_ON;
				}
			}
					.setCommissionSystem(comSystemId)
					.setDateFrom(currentDateFrom)
					.setDateTo(currentDateTo)
					.climb(condChange.getC_Sponsor(), Integer.MAX_VALUE);

			if (!currentList.isEmpty())
			{
				result.put(comSystemId, currentList);
			}
		}
		return result;
	}

	private boolean isLoginUserAdminForComSystem(final Properties ctx, final I_C_AdvComSystem comSystem, final String trxName)
	{

		boolean userIsAdmin = false;

		if (comSystem.getAD_User_Admin_ID() == Env.getAD_User_ID(ctx))
		{
			logger.debug("Login user " + Env.getAD_User_ID(ctx) + " is an admin for " + comSystem);
			userIsAdmin = true;
		}
		if (comSystem.getAD_Role_Admin_ID() == Env.getAD_Role_ID(ctx))
		{
			logger.debug("Login role " + Env.getAD_Role_ID(ctx) + " is an admin for " + comSystem);
			userIsAdmin = true;
		}

		return userIsAdmin;
	}

	@Override
	public List<I_C_Sponsor_CondLine> retrieveLines(
			final I_C_Sponsor_Cond condChange)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(condChange);
		final String trxName = InterfaceWrapperHelper.getTrxName(condChange);

		final String wc = I_C_Sponsor_Cond.COLUMNNAME_C_Sponsor_Cond_ID + "=?";

		return new Query(ctx, I_C_Sponsor_CondLine.Table_Name, wc, trxName)
				.setParameters(condChange.getC_Sponsor_Cond_ID())
				.setApplyAccessFilter(true)
				.setOrderBy(I_C_Sponsor_CondLine.COLUMNNAME_C_Sponsor_CondLine_ID)
				.list(I_C_Sponsor_CondLine.class);
	}
}
