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
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MBPartner;

import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.ICommissionRankDAO;
import de.metas.commission.service.IHierarchyBL;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.HierarchyAscender;
import de.metas.commission.util.HierarchyClimber.Result;
import de.metas.commission.util.HierarchyDescender;
import de.metas.commission.util.LevelAndSponsor;

public class HierarchyBL implements IHierarchyBL
{

	@Override
	public List<I_C_Sponsor> findSponsorsInUpline(
			final Timestamp date,
			final I_C_Sponsor sponsor,
			final int maxLevel,
			final int maxSalesReps,
			final I_C_AdvComSystem comSystem,
			final String lowestSalaryGroupValue,
			final String srfStatus)
	{
		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor;
		if (comSystem != null)
		{
			sgsToLookfor = retrieveRanksToLookFor(sponsor, comSystem.getC_AdvComRankCollection(), lowestSalaryGroupValue);
		}
		else
		{
			sgsToLookfor = null;
		}
		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();

		// ascend the hierarchy to find the sales rep that will receive the
		// bonus
		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{

				return evalSalesRep(sponsorCurrentLevel, comSystem, date, sgsToLookfor, maxSalesReps, result, srfStatus);
			}
		}
				.setDate(date).climb(sponsor, maxLevel);

		return result;
	}

	@Override
	public List<I_C_Sponsor> findSponsorsInDownline(
			final Timestamp date,
			final I_C_Sponsor sponsor,
			final int maxLevel,
			final int maxSalesReps,
			final I_C_AdvComSystem comSystem,
			final String lowestSalaryGroupValue,
			final String srfStatus)
	{

		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
				InterfaceWrapperHelper.getCtx(sponsor),
				comSystem.getC_AdvComRankCollection(),
				lowestSalaryGroupValue,
				InterfaceWrapperHelper.getTrxName(sponsor)
				);

		final List<I_C_Sponsor> salesReps = new ArrayList<I_C_Sponsor>(maxSalesReps);

		new HierarchyDescender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				return evalSalesRep(sponsorCurrentLevel, comSystem, date, sgsToLookfor, maxSalesReps, salesReps, srfStatus);
			}
		}.setDate(date).climb(sponsor, Integer.MAX_VALUE);

		return salesReps;
	}

	@Override
	public List<I_C_Sponsor> findSponsorsInUpline(final Timestamp date,
			final MBPartner customer, final int maxLevel,
			final int maxSalesReps, final boolean skipNonSalesReps)
	{
		final I_C_Sponsor customerSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(customer, true);

		final List<I_C_Sponsor> result = new ArrayList<I_C_Sponsor>();
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		// ascend the hierarchy to find the sales rep that will receive the
		// bonus
		new HierarchyAscender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorCurrentLevel);
				final String trxName = InterfaceWrapperHelper.getTrxName(sponsorCurrentLevel);

				final I_C_BPartner salesRep = sponsorBL.retrieveSalesRepAt(ctx, date, sponsorCurrentLevel, false, trxName); // throwEx=false
				final Result invResult;

				if (salesRep == null)
				{
					if (skipNonSalesReps)
					{
						invResult = Result.SKIP_IGNORE;
					}
					else
					{
						invResult = Result.GO_ON;
					}
				}
				else
				{
					result.add(sponsorCurrentLevel);

					if (result.size() >= maxSalesReps)
					{
						invResult = Result.FINISHED;
					}
					else
					{
						invResult = Result.GO_ON;
					}
				}
				return invResult;
			}
		}.setDate(date).climb(customerSponsor, maxLevel);

		return result;
	}

	/**
	 * 
	 * @param sponsor
	 * @param date
	 * @param sgsToLookfor may be <code>null</code>.
	 * @param maxSalesReps
	 * @param salesReps
	 * @return
	 */
	private Result evalSalesRep(final I_C_Sponsor sponsor, final I_C_AdvComSystem comSystem,
			final Timestamp date,
			final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor,
			final int maxSalesReps, final List<I_C_Sponsor> foundSalesReps,
			final String srfStatus)
	{
		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final int salaryGroupId;

		if (sgsToLookfor == null)
		{
			salaryGroupId = 0;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
			final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
			final I_C_AdvCommissionSalaryGroup sg = sponsorBL.retrieveRank(ctx, sponsor, comSystem, date, srfStatus, trxName);
			salaryGroupId = sg == null ? 0 : sg.getC_AdvCommissionSalaryGroup_ID();
		}
		if (srfBL.isInSGToLookFor(sgsToLookfor, salaryGroupId))
		{
			foundSalesReps.add(sponsor);
		}

		if (foundSalesReps.size() >= maxSalesReps)
		{
			return Result.FINISHED;
		}
		return Result.GO_ON;
	}

	private List<I_C_AdvCommissionSalaryGroup> retrieveRanksToLookFor(
			final I_C_Sponsor sponsor,
			final I_C_AdvComRankCollection rankCollection,
			final String lowestSalaryGroupValue)
	{

		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor;

		if (lowestSalaryGroupValue == null)
		{
			sgsToLookfor = null;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
			final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);
			sgsToLookfor = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
					ctx,
					rankCollection,
					lowestSalaryGroupValue,
					trxName);
		}
		return sgsToLookfor;
	}

	@Override
	public List<LevelAndSponsor> retrieveDownLine(
			final Timestamp date,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String lowestSalaryGroupValue)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final List<I_C_AdvCommissionSalaryGroup> sgsToLookfor = Services.get(ICommissionRankDAO.class).retrieveGroupAndBetter(
				ctx,
				comSystem.getC_AdvComRankCollection(),
				lowestSalaryGroupValue,
				trxName);

		final List<LevelAndSponsor> listResult = new ArrayList<LevelAndSponsor>();

		final int[] level = { 0 };

		new HierarchyDescender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel,
					final int logicalLevel, final int hierarchyLevel,
					final Map<String, Object> contextInfo)
			{

				final List<I_C_Sponsor> singleSponsor = new ArrayList<I_C_Sponsor>();

				final Result result =
						evalSalesRep(sponsorCurrentLevel, comSystem, date, sgsToLookfor, Integer.MAX_VALUE, singleSponsor,
								X_C_AdvComSalesRepFact.STATUS_Prov_Relevant);

				if (!singleSponsor.isEmpty())
				{
					final LevelAndSponsor levelAndSponsor =
							new LevelAndSponsor(level[0], sponsorCurrentLevel);
					listResult.add(levelAndSponsor);
					level[0]++;
				}
				return result;
			}
		}.setDate(date).climb(sponsor, Integer.MAX_VALUE);

		return listResult;
	}
}
