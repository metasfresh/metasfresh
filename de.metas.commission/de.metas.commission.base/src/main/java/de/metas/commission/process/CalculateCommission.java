package de.metas.commission.process;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MPeriod;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionPayroll;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionPayroll;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionPayrollBL;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class CalculateCommission extends JavaProcess
{
	public static final String MSG_CALC_ERROR_UNPROCESSED_CANDIDATES_1P = "CalcErrorUnprocessedCandidates_1P";

	private I_C_Period period;

	@Override
	protected String doIt() throws Exception
	{
		int documentCount = 0;

		final Timestamp date = Env.getContextAsDate(getCtx(), "#Date");

		final Map<Integer, MCAdvCommissionPayroll> salesRepId2PayRoll = new HashMap<Integer, MCAdvCommissionPayroll>();

		final List<MCAdvComSystemType> comSystemTypes = MCAdvComSystemType.retrieveAll(getCtx(), Env.getAD_Org_ID(getCtx()), get_TrxName());

		checkCandsInPeriod();

		final int adPInstanceID = getProcessInfo().getAD_PInstance_ID();

		// 1.
		// make sure that there are commission instances, for the case that there is no forecast, but still there
		// will be a commission (due to compression of other sales reps, etc.)
		for (final MCAdvComSystemType comSystemType : comSystemTypes)
		{
			final I_C_AdvCommissionType commissionType = comSystemType.getC_AdvCommissionType();
			final ICommissionType businessLogic = Services.get(ICommissionTypeBL.class).getBusinessLogic(commissionType, comSystemType);

			if (!businessLogic.isCommissionCalculated())
			{
				// nothing to do
				continue;
			}

			final Collection<MCAdvCommissionFactCand> candsToReview = retrieveCandsToReview(comSystemType);
			for (final MCAdvCommissionFactCand candToReview : candsToReview)
			{
				businessLogic.evaluateCandidate(candToReview, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, adPInstanceID);
			}
		}

		// 2.
		// Do the real calculation
		final ICommissionPayrollBL cPayrollBL = Services.get(ICommissionPayrollBL.class);

		for (final I_C_AdvComSystem_Type comSystemType : comSystemTypes)
		{
			final I_C_AdvCommissionType commissionType = comSystemType.getC_AdvCommissionType();

			final ICommissionType businessLogic = Services.get(ICommissionTypeBL.class).getBusinessLogic(commissionType, comSystemType);

			if (!businessLogic.isCommissionCalculated())
			{
				continue;
			}

			final Map<Integer, List<IAdvComInstance>> salesRepId2Instances = Services.get(ICommissionInstanceDAO.class)
					.retrieveForCalculation(getCtx(), period, comSystemType, get_TrxName());

			final Map<Integer, Object> commissionTriggers = new HashMap<Integer, Object>();

			for (final int salesRepId : salesRepId2Instances.keySet())
			{
				final List<MCAdvCommissionPayrollLine> newLines = new ArrayList<MCAdvCommissionPayrollLine>();

				for (final IAdvComInstance instance : salesRepId2Instances.get(salesRepId))
				{
					// get the commission trigger documents for 'inst' and store them
					for (final MCAdvCommissionFact trigger : Services.get(ICommissionFactBL.class).retrieveTriggers(instance, period))
					{
						final Object header = Services.get(IFieldAccessBL.class).retrieveHeader(trigger.retrievePO());
						final int headerId = InterfaceWrapperHelper.getId(header);
						if (!commissionTriggers.containsKey(headerId))
						{
							commissionTriggers.put(headerId, header);
						}
					}

					try
					{
						final List<MCAdvCommissionPayrollLine> payrollLine = cPayrollBL.createPayrollLine(instance, period, date, adPInstanceID);
						newLines.addAll(payrollLine);
					}
					catch (final RuntimeException e)
					{
						MetasfreshLastError.saveError(log, e.getMessage(), e);
						addLog(e.getMessage());
					}
				}

				if (!newLines.isEmpty())
				{
					// at least one line was created. make sure that there is
					// also a commission payroll header
					final boolean payrollExists = salesRepId2PayRoll.containsKey(salesRepId);

					final MCAdvCommissionPayroll commissionPayroll;

					if (payrollExists)
					{
						commissionPayroll = salesRepId2PayRoll.get(salesRepId);
					}
					else
					{
						if (salesRepId > 0)
						{
							commissionPayroll = createPayroll(salesRepId, date);
						}
						else
						{
							// There is no sales rep BPartner to receive this commission.
							// To document this, we create a commission payroll doc for the BPartner assigned to the root sponsor
							final I_C_Sponsor rootSponsor = comSystemType.getC_AdvComSystem().getC_Sponsor_Root();
							commissionPayroll = createPayroll(rootSponsor.getC_BPartner_ID(), date);
						}
						salesRepId2PayRoll.put(salesRepId, commissionPayroll);

						documentCount++;
						addLog(0, SystemTime.asTimestamp(), new BigDecimal(
								documentCount), "@Created@: @"
								+ I_C_AdvCommissionPayroll.COLUMNNAME_C_AdvCommissionPayroll_ID + "@ "
								+ commissionPayroll.getDocumentNo());
					}

					for (final MCAdvCommissionPayrollLine newLine : newLines)
					{
						newLine.setC_AdvCommissionPayroll_ID(commissionPayroll.get_ID());
						newLine.setLine(commissionPayroll.nextLine());
						newLine.saveEx();
					}
				}
			}

			for (final Object header : commissionTriggers.values())
			{
				final MCAdvComDoc comDoc = MCAdvComDoc.retrieveForTrigger(header);
				if (comDoc != null)
				{
					comDoc.setIsDateFactOverridable(false);
					comDoc.saveEx();
				}
			}
		}
		return "@Created@ " + documentCount;
	}

	private MCAdvCommissionPayroll createPayroll(final int bPartnerId, final Timestamp date)
	{
		Check.assume(bPartnerId>0, "Param 'bPartnerId' > 0");
		final MCAdvCommissionPayroll commissionPayroll = new MCAdvCommissionPayroll(getCtx(), 0, get_TrxName());

		commissionPayroll.setC_Period_ID(period.getC_Period_ID());
		commissionPayroll.setDateCalculated(date);
		commissionPayroll.setC_BPartner_ID(bPartnerId);
		commissionPayroll.setProcessed(true);

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(commissionPayroll.getC_BPartner(), I_C_BPartner.class);
		final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(getCtx(), bPartner, false, get_TrxName());
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final I_M_AttributeSetInstance setInstance = sponsorBL.createSalaryGroupAttributeSet(sponsor, commissionPayroll.getC_Period(), date);

		commissionPayroll.setM_AttributeSetInstance_ID(setInstance.getM_AttributeSetInstance_ID());

		commissionPayroll.saveEx();

		return commissionPayroll;
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
			}
			else if (name.equals(I_C_AdvCommissionPayroll.COLUMNNAME_C_Period_ID))
			{
				final int periodId = ((BigDecimal)para[i].getParameter()).intValue();
				period = MPeriod.get(getCtx(), periodId);
			}
			else if (name.equals(I_C_Period.COLUMNNAME_C_Year_ID))
			{
				// nothing to do
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	private void checkCandsInPeriod()
	{
		final String whereClause =
				I_C_AdvCommissionFactCand.COLUMNNAME_DateAcct + "<=? AND "
						+ I_C_AdvCommissionFactCand.COLUMNNAME_IsSubsequentProcessingDone + "='N'";

		final List<MCAdvCommissionFactCand> list =
				new Query(getCtx(), I_C_AdvCommissionFactCand.Table_Name, whereClause, get_TrxName())
						.setParameters(period.getEndDate())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.list();

		if (list.isEmpty())
		{
			return;
		}

		throw new AdempiereException(Msg.getMsg(getCtx(), CalculateCommission.MSG_CALC_ERROR_UNPROCESSED_CANDIDATES_1P, new Object[] { list.size() }));
	}

	/**
	 * Select statement for the C_AdvCommissionInstance records that need a review prior to the actual calculation.
	 * 
	 * @param termId
	 * @return
	 */
	private Collection<MCAdvCommissionFactCand> retrieveCandsToReview(final I_C_AdvComSystem_Type comSystemType)
	{
		// 1.
		// Retrieving poLines whose commission instances need calculation
		final List<GenericPO> poLines = new ArrayList<GenericPO>();

		final List<IAdvComInstance> unprocessedInstances =
				Services.get(ICommissionInstanceDAO.class).retrieveToCalcUnprocessed(getCtx(), period, comSystemType, true, get_TrxName());

		for (final IAdvComInstance inst : unprocessedInstances)
		{
			final String tableName = MTable.getTableName(getCtx(), inst.getAD_Table_ID());
			final GenericPO poLine = new GenericPO(tableName, getCtx(), inst.getRecord_ID(), get_TrxName());

			poLines.add(poLine);
		}

		// 2.
		// retrieving PO headers
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

		final Map<Integer, Object> pos = new HashMap<Integer, Object>();
		for (final GenericPO poLine : poLines)
		{
			final Object poHeader = faBL.retrieveHeader(poLine);
			pos.put(InterfaceWrapperHelper.getId(poHeader), poHeader);
		}

		final Collection<MCAdvCommissionFactCand> candidates = new ArrayList<MCAdvCommissionFactCand>(pos.size());

		for (final Object po : pos.values())
		{
			final MCAdvCommissionRelevantPO relevantPODef = MCAdvCommissionRelevantPO.retrieveIfRelevant(po);
			Check.assume(relevantPODef != null, "PO=" + po + "; comSystemType=" + comSystemType + " has a C_AdvCommissionRelevantPO");

			final MCAdvCommissionFactCand cand = MCAdvCommissionFactCand.createNoSave(po, relevantPODef);
			// cand.setDateAcct(period.getEndDate());

			candidates.add(cand);
		}

		return candidates;
	}
}
