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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Period;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComCorrection;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionPayrollBL;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.impl.CPLData.AggregatedCPLDataItem;

public class CommissionPayrollBL implements ICommissionPayrollBL
{
	final Logger logger = LogManager.getLogger(CommissionPayrollBL.class);

	@Override
	public List<MCAdvCommissionPayrollLine> createPayrollLine(final IAdvComInstance instance, final I_C_Period period, final Timestamp date, final int adPInstanceId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		final I_C_AdvCommissionTerm term = instance.getC_AdvCommissionTerm();

		final List<MCAdvCommissionPayrollLine> newLines = new ArrayList<MCAdvCommissionPayrollLine>();

		if (!instance.isClosed())
		{
			final I_C_AdvComSystem_Type comSystemType = term.getC_AdvComSystem_Type();

			final ICommissionType type = Services.get(ICommissionTypeBL.class).getBusinessLogic(comSystemType);

			//
			// sum up the commission points grouped by taxCategory
			// taxCategoryId2ComPoints maps
			// ( TaxCategoryId ->
			// _____( CommissionPointsBase ->
			// __________( CommissionPercent -> CPLData ) ) )
			// "Points" are represented by CPLData.
			final Map<Integer, Map<BigDecimal, Map<BigDecimal, CPLData>>> taxCategoryId2ComPoints = new HashMap<Integer, Map<BigDecimal, Map<BigDecimal, CPLData>>>();

			final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

			for (final MCAdvCommissionFact trigger : commissionFactBL.retrieveTriggers(instance, period))
			{
				final PO factPO = trigger.retrievePO();

				final BigDecimal percent;

				if (factPO instanceof MCAdvComCorrection)
				{
					// this is a generic correction record, so don't ask 'term' for
					// its opinion, but use the fact's commission
					percent = trigger.getCommission();
				}
				else
				{
					Check.assume(!(factPO instanceof MCAdvCommissionPayrollLine), "factPO " + factPO + " of trigger " + trigger + " is NOT instanceof MCAdvCommissionPayrollLine");

					//
					// Get the date to use when evaluating the candidate with the current sponsor
					// Note: if the commission type's retroactive-type is "period", then this date depends on the
					// sponsor's
					// contract.
					final I_C_Sponsor sponsorSalesRep = instance.getC_Sponsor_SalesRep();

					final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

					final Timestamp dateToUse = sponsorBL.retrieveDateTo(sponsorSalesRep, type.getComSystemType(), instance.getDateDoc());

					percent = type.getPercent(instance, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, dateToUse).multiply(Env.ONEHUNDRED);
				}

				sumUpCommission(instance, trigger, trigger.getCommissionPointsBase(), percent, taxCategoryId2ComPoints);
			}

			for (final int taxCategoryId : taxCategoryId2ComPoints.keySet())
			{
				for (final BigDecimal comissionPointsBase : taxCategoryId2ComPoints
						.get(taxCategoryId).keySet())
				{
					for (final BigDecimal percent : taxCategoryId2ComPoints
							.get(taxCategoryId)
							.get(comissionPointsBase).keySet())
					{
						final CPLData cplData = taxCategoryId2ComPoints
								.get(taxCategoryId)
								.get(comissionPointsBase)
								.get(percent);

						for (final AggregatedCPLDataItem aggregate : cplData.aggregateItems())
						{
							final MCAdvCommissionPayrollLine newLine = createNewCPL(term, type, instance, taxCategoryId, comissionPointsBase, percent, aggregate);

							// record that trigger's facts have been "used up" with this payroll line (even if it has 0
							// points)
							for (final MCAdvCommissionFact fact : aggregate.getFacts())
							{
								Check.assume(fact.getC_AdvCommissionPayrollLine_ID() <= 0, fact + " has no CommissionPayrollLine_ID yet");
								Check.assume(fact.isProcessed() == false, fact + " has processed='N'");

								if (newLine != null)
								{
									fact.setC_AdvCommissionPayrollLine_ID(newLine.get_ID());
								}
								fact.setProcessed(true);
								fact.saveEx();
							}

							if (newLine == null)
							{
								// no commission payroll line was created. Still, we need to close the commission instance
								commissionFactBL.makeCalculationMinusFact(aggregate.getFacts(), date, adPInstanceId, trxName);
							}
							else
							{
								newLines.add(newLine);

								final List<MCAdvCommissionFact> result = commissionFactBL.recordCommissionPayrollLine(newLine, date, adPInstanceId, trxName);

								Check.assume(result.size() == 2, "expecting result to contain one minus fact and one plus fact");
								logger.info("Created commission facts for new " + newLine);
							}
						}
					}
				}
			}
		}

		//
		// add lines for our commission unwinding facts
		final List<MCAdvCommissionFact> unwindFacts =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setCounterRecord(true)
						.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet, false)
						.setProcessed(false)
						.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung, false)
						.setDateFactBefore(period.getEndDate())
						.list();

		for (final MCAdvCommissionFact unwindFact : unwindFacts)
		{
			final MCAdvCommissionPayrollLine oldPayrollLine = (MCAdvCommissionPayrollLine)unwindFact.retrievePO();
			final MCAdvCommissionPayrollLine newCorrectionPayrollLine = new MCAdvCommissionPayrollLine(ctx, 0, trxName);

			newCorrectionPayrollLine.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());
			newCorrectionPayrollLine.setC_AdvCommissionTerm_ID(term.getC_AdvCommissionTerm_ID());

			newCorrectionPayrollLine.setCommissionPointsBase(oldPayrollLine.getCommissionPointsBase());
			newCorrectionPayrollLine.setQty(oldPayrollLine.getQty().negate());
			newCorrectionPayrollLine.setCommissionPointsSum(oldPayrollLine.getCommissionPointsSum().negate());
			newCorrectionPayrollLine.setCommission(oldPayrollLine.getCommission());
			newCorrectionPayrollLine.setCommissionPoints(oldPayrollLine.getCommissionPoints().negate());

			newCorrectionPayrollLine.setAmtMultiplier(oldPayrollLine.getAmtMultiplier());
			newCorrectionPayrollLine.setCommissionAmt(oldPayrollLine.getCommissionAmt().negate());
			newCorrectionPayrollLine.setC_Currency_ID(oldPayrollLine.getC_Currency_ID());
			newCorrectionPayrollLine.setC_TaxCategory_ID(oldPayrollLine.getC_TaxCategory_ID());

			newCorrectionPayrollLine.setIsCommissionLock(oldPayrollLine.isCommissionLock());
			newCorrectionPayrollLine.setCommissionAmtBase(oldPayrollLine.getCommissionAmtBase());

			newCorrectionPayrollLine.saveEx();

			final MCAdvCommissionFact correctionMinusFact = MCAdvCommissionFact.createNew(oldPayrollLine, null);
			correctionMinusFact.setIsCounterEntry(true);
			correctionMinusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
			correctionMinusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberKorrektur);

			correctionMinusFact.setAD_PInstance_ID(adPInstanceId);
			correctionMinusFact.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());

			correctionMinusFact.setCommissionAmtBase(unwindFact.getCommissionAmtBase());
			correctionMinusFact.setCommissionPointsBase(unwindFact.getCommissionPointsBase());
			correctionMinusFact.setQty(unwindFact.getQty().negate());
			correctionMinusFact.setCommissionPointsSum(unwindFact.getCommissionPointsSum().negate());
			correctionMinusFact.setCommission(unwindFact.getCommission());
			correctionMinusFact.setCommissionPoints(unwindFact.getCommissionPoints().negate());

			correctionMinusFact.setAmtMultiplier(unwindFact.getAmtMultiplier());
			correctionMinusFact.setCommissionAmt(unwindFact.getCommissionAmt().negate());
			correctionMinusFact.setC_Currency_ID(unwindFact.getC_Currency_ID());

			correctionMinusFact.setDateFact(date);
			correctionMinusFact.setDateDoc(oldPayrollLine.getC_AdvCommissionPayroll().getDateCalculated());

			correctionMinusFact.saveEx();

			unwindFact.setC_AdvCommissionPayrollLine_ID(newCorrectionPayrollLine.get_ID());
			unwindFact.setProcessed(true);
			unwindFact.addFollowUpInfo(correctionMinusFact);
			unwindFact.saveEx();

			final MCAdvCommissionFact correctionPlusFact = MCAdvCommissionFact.createNew(newCorrectionPayrollLine, null);

			correctionPlusFact.setIsCounterEntry(false);
			correctionPlusFact.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet);
			correctionPlusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsberKorrektur);

			correctionPlusFact.setAD_PInstance_ID(adPInstanceId);
			correctionPlusFact.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());

			correctionPlusFact.setCommissionAmtBase(unwindFact.getCommissionAmtBase());
			correctionPlusFact.setCommissionPointsBase(unwindFact.getCommissionPointsBase());
			correctionPlusFact.setQty(unwindFact.getQty());
			correctionPlusFact.setCommissionPointsSum(unwindFact.getCommissionPointsSum());
			correctionPlusFact.setCommission(unwindFact.getCommission());
			correctionPlusFact.setCommissionPoints(unwindFact.getCommissionPoints());

			correctionPlusFact.setAmtMultiplier(unwindFact.getAmtMultiplier());
			correctionPlusFact.setCommissionAmt(unwindFact.getCommissionAmt());
			correctionPlusFact.setC_Currency_ID(unwindFact.getC_Currency_ID());

			correctionPlusFact.setDateFact(date);
			correctionPlusFact.setDateDoc(date);

			correctionPlusFact.saveEx();

			correctionMinusFact.addFollowUpInfo(correctionPlusFact);
			correctionMinusFact.saveEx();

			newLines.add(newCorrectionPayrollLine);
		}

		return newLines;
	}

	/**
	 * 
	 * @param term
	 * @param it the instance trigger (po)
	 * @param fact the commission trigger
	 * 
	 * @param taxCategoryId2PointsData
	 * @param taxCategoryId2CommissionAmtBase maps
	 * 
	 *            <pre>
	 * (TaxCategoryId -> ( CommissionPointsBase -> ( CommissionPercent -> Points) ) )
	 * </pre>
	 */
	private void sumUpCommission(
			final IAdvComInstance instance,
			final MCAdvCommissionFact fact,
			final BigDecimal pointsBase,
			final BigDecimal percentActual,
			final Map<Integer, Map<BigDecimal, Map<BigDecimal, CPLData>>> taxCategoryId2PointsData)
	{
		// each fact of the trigger has the same po

		final BigDecimal qty = fact.getQty();

		final BigDecimal pointsSum;

		final BigDecimal points;

		pointsSum = pointsBase.multiply(qty);
		points = pointsSum.multiply(percentActual).divide(Env.ONEHUNDRED);

		final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);
		final PO instPO = InterfaceWrapperHelper.getPO(Services.get(ICommissionInstanceDAO.class).retrievePO(instance, Object.class));
		final int taxCategoryId = faService.getTaxCategoryId(instPO);

		Map<BigDecimal, Map<BigDecimal, CPLData>> pointsBase2pointsData = taxCategoryId2PointsData.get(taxCategoryId);
		if (pointsBase2pointsData == null)
		{
			pointsBase2pointsData = new HashMap<BigDecimal, Map<BigDecimal, CPLData>>();
			taxCategoryId2PointsData.put(taxCategoryId, pointsBase2pointsData);
		}

		Map<BigDecimal, CPLData> percent2pointsData = pointsBase2pointsData.get(pointsBase);
		if (percent2pointsData == null)
		{
			percent2pointsData = new HashMap<BigDecimal, CPLData>();
			pointsBase2pointsData.put(pointsBase, percent2pointsData);
		}

		CPLData pointsData = percent2pointsData.get(percentActual);
		if (pointsData == null)
		{
			pointsData = new CPLData();
			percent2pointsData.put(percentActual, pointsData);
		}

		pointsData.addItem(fact, points);
	}

	/**
	 * Creates a payroll line, if there are any commission points according to {@link CPLData#commissionPoints}. Otherwise, the method returns <code>null</code>. Note: the new line is saved.
	 * 
	 * @param term
	 * @param type
	 * @param instance
	 * @param taxCategoryId
	 * @param commissionPointsBase
	 * @param commissionPercent
	 * @param cplData
	 * @return
	 */
	private MCAdvCommissionPayrollLine createNewCPL(
			final I_C_AdvCommissionTerm term, final ICommissionType type,
			final IAdvComInstance instance, final int taxCategoryId,
			final BigDecimal commissionPointsBase,
			final BigDecimal commissionPercent, final AggregatedCPLDataItem cplData)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		if (cplData.getCommissionPoints() == null || cplData.getCommissionPoints().signum() == 0)
		{
			return null;
		}

		final I_C_Currency currency = term.getC_AdvCommissionCondition().getC_Currency();

		final MCAdvCommissionPayrollLine newLine = new MCAdvCommissionPayrollLine(ctx, 0, trxName);

		newLine.setC_AdvCommissionInstance_ID(instance.getC_AdvCommissionInstance_ID());
		newLine.setC_AdvCommissionTerm_ID(term.getC_AdvCommissionTerm_ID());

		newLine.setCommissionPointsBase(commissionPointsBase);
		newLine.setQty(cplData.getQtySum());
		newLine.setCommissionPointsSum(cplData.getCommissionPointsSum());
		newLine.setCommission(commissionPercent);
		newLine.setCommissionPoints(cplData.getCommissionPoints());

		newLine.setAmtMultiplier(type.getFactor());
		newLine.setCommissionAmt(computeAmt(cplData.getCommissionPoints(), type.getFactor(), currency));
		newLine.setC_Currency_ID(currency.getC_Currency_ID());
		newLine.setC_TaxCategory_ID(taxCategoryId);

		newLine.setIsCommissionLock(instance.isCommissionLock());
		newLine.setCommissionAmtBase(cplData.getCommissionAmtBase());

		newLine.saveEx();

		return newLine;
	}

	/**
	 * Computes the payroll amount using currency (precision), points and factor
	 * 
	 * @param commissionPoints
	 * @param factor
	 * @param currency
	 * @return
	 */
	BigDecimal computeAmt(
			final BigDecimal commissionPoints, final BigDecimal factor, final I_C_Currency currency)
	{
		final BigDecimal commissionAmt = commissionPoints.multiply(factor);

		return commissionAmt.setScale(currency.getStdPrecision(), RoundingMode.HALF_UP);
	}

}
