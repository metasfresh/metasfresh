package de.metas.commission.custom.type;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_Cond;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.model.MCIncidentLine;
import de.metas.commission.model.MCIncidentLineFact;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.model.X_C_Sponsor_Cond;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ICommissionFactBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorCondition;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.HierarchyAscender;
import de.metas.commission.util.HierarchyDescender;
import de.metas.logging.LogManager;

/**
 * Type handles {@link I_C_Sponsor_SalesRep} changes, i.e. changes made to the sponsor hierarchy
 * 
 * @author ts
 * 
 */
public class SponsorCond implements ICommissionType
{

	private static final Logger logger = LogManager.getLogger(SponsorCond.class);

	private I_C_AdvComSystem_Type comSystemType;

	@Override
	public void evaluateCandidate(
			final MCAdvCommissionFactCand cand,
			final String status,
			final int adPinstanceId)
	{
		Check.assume(X_C_AdvComSalesRepFact.STATUS_Prognose.equals(status), "Wrong status " + status);

		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(cand);

		if (!(po instanceof X_C_Sponsor_Cond))
		{
			return;
		}
		if (!X_C_Sponsor_Cond.DOCSTATUS_Fertiggestellt.equals(InterfaceWrapperHelper.create(po, I_C_Sponsor_Cond.class).getDocStatus()))
		{
			return;
		}
		handleSSRChange(cand, adPinstanceId);
	}

	@Override
	public I_C_AdvComSystem_Type getComSystemType()
	{
		return comSystemType;
	}

	@Override
	public BigDecimal getCommissionPointsSum(final IAdvComInstance inst, final String status, final Timestamp date, final Object po)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getFactor()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public IParameterizable getInstanceParams(
			final Properties ctx,
			final I_C_AdvComSystem system,
			final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return false;
	}

	@Override
	public void setComSystemType(final I_C_AdvComSystem_Type comSystemType)
	{
		this.comSystemType = comSystemType;
	}

	private void handleSSRChange(
			final MCAdvCommissionFactCand cand,
			final int adPInstanceId)
	{
		final I_C_Sponsor_Cond condChange = InterfaceWrapperHelper.create(Services.get(ICommissionFactCandBL.class).retrievePO(cand), I_C_Sponsor_Cond.class);

		final ISponsorCondition condChangeBL = Services.get(ISponsorCondition.class);

		final Set<Object> allDocs = new HashSet<Object>();

		for (final I_C_Sponsor_CondLine condChangeLine : condChangeBL.retrieveLines(condChange))
		{
			if (condChangeLine.getC_AdvComSystem_ID() != getComSystemType().getC_AdvComSystem_ID())
			{
				continue;
			}

			final Set<Object> ordersAndInvoices = new HashSet<Object>();
			final Set<Object> otherDocs = new HashSet<Object>();

			retrieveAllDocs(condChangeLine, ordersAndInvoices, otherDocs);

			closeObsoleteInstances(ordersAndInvoices, adPInstanceId, cand, condChangeLine);

			otherDocs.addAll(ordersAndInvoices);

			allDocs.addAll(ordersAndInvoices);
			allDocs.addAll(otherDocs);
		}

		reschedulePOs(cand, allDocs);
	}

	private void retrieveAllDocs(
			final I_C_Sponsor_CondLine condLine,
			final Set<Object> ordersAndInvoices,
			final Set<Object> theRest)
	{
		final Map<ArrayKey, Object> newOrdersAndInvoices = new HashMap<ArrayKey, Object>();

		final Map<ArrayKey, Object> newTheRest = new HashMap<ArrayKey, Object>();

		final I_C_Sponsor sponsor = condLine.getC_Sponsor_Cond().getC_Sponsor();

		new HierarchyDescender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel, final int logicalLevel, final int hierarchyLevel, final Map<String, Object> contextInfo)
			{
				//
				// collect all invoices and orders for all bPartner's that are connected to 'sponsorCurrentLevel'
				final Set<Integer> bPartnersSeen = new HashSet<Integer>();

				final I_C_BPartner bPartnerCust = InterfaceWrapperHelper.create(sponsorCurrentLevel.getC_BPartner(), I_C_BPartner.class);
				bPartnersSeen.add(bPartnerCust.getC_BPartner_ID());
				retrieveDocsForBP(bPartnerCust, newOrdersAndInvoices, newTheRest);

				for (final I_C_Sponsor_SalesRep ssr : Services.get(ISponsorDAO.class).retrieveSalesRepSSRs(sponsorCurrentLevel))
				{
					if (bPartnersSeen.add(ssr.getC_BPartner_ID()))
					{
						retrieveDocsForBP(InterfaceWrapperHelper.create(ssr.getC_BPartner(), I_C_BPartner.class), newOrdersAndInvoices, newTheRest);
					}
				}
				return Result.GO_ON;
			}

			private void retrieveDocsForBP(
					final I_C_BPartner bPartnerCust,
					final Map<ArrayKey, Object> instTriggers,
					final Map<ArrayKey, Object> nonInstTriggers)
			{
				final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

				final List<MCIncidentLine> incidentLines =
						MCIncidentLine.retrieveForBPartner(bPartnerCust, condLine.getDateFrom());

				for (final MCIncidentLine line : incidentLines)
				{
					for (final MCIncidentLineFact lineFact : MCIncidentLineFact.retrieveForLine(line))
					{
						if (lineFact.getAD_Table_ID() == MTable.getTable_ID(I_C_OrderLine.Table_Name))
						{
							final MOrderLine ol = (MOrderLine)lineFact.retrievePO();
							if (ol == null)
							{
								continue;
							}
							final ArrayKey key = Util.mkKey(org.compiere.model.I_C_Order.Table_ID, ol.getC_Order_ID());

							if (!instTriggers.containsKey(key))
							{
								instTriggers.put(key, ol.getParent());
							}
						}
						else if (lineFact.getAD_Table_ID() == MTable.getTable_ID(org.compiere.model.I_C_InvoiceLine.Table_Name))
						{
							final MInvoiceLine il = (MInvoiceLine)lineFact.retrievePO();
							if (il == null)
							{
								continue;
							}
							final ArrayKey key = Util.mkKey(I_C_Invoice.Table_ID, il.getC_Invoice_ID());

							if (!instTriggers.containsKey(key))
							{
								instTriggers.put(key, il.getParent());
							}
						}
						else
						{
							final PO poLine = lineFact.retrievePO();
							if (poLine == null)
							{
								continue;
							}
							final Object po = faBL.retrieveHeader(poLine);
							final ArrayKey key = Util.mkKey(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(po)), InterfaceWrapperHelper.getId(po));

							if (!nonInstTriggers.containsKey(key))
							{
								nonInstTriggers.put(key, po);
							}
						}
					}
				}
			}
		}
				.setCommissionSystem(getComSystemType().getC_AdvComSystem_ID())
				.setDateFrom(condLine.getDateFrom())
				.setDateTo(condLine.getDateTo())
				// Note: we need to use 'VALID_RANGE_MIN', because the ssr change might be a contract change where
				// the contract's valid from date was moved to a later date. In that case, the descender still needs to
				// find those instances that are now outside of the contract
				.climb(sponsor, Integer.MAX_VALUE);

		ordersAndInvoices.addAll(newOrdersAndInvoices.values());
		theRest.addAll(newTheRest.values());
	}

	private void closeObsoleteInstances(
			final Collection<? extends Object> ordersAndInvoices,
			final int adPInstanceId,
			final MCAdvCommissionFactCand cand,
			final I_C_Sponsor_CondLine condChangeLine)
	{
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(condChangeLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(condChangeLine);

		for (final Object po : ordersAndInvoices)
		{
			SponsorCond.logger.debug("retrieving all instances for poHeader " + po);

			for (final Object poLine : faBL.retrieveLines(po, true))
			{
				SponsorCond.logger.debug("retrieving all instances for poLine " + poLine);

				// Step 1
				// Get all commission instances that were triggered by 'poLine'.
				// Those instances which are *not* removed in step 2 will be closed in step 3.
				final List<IAdvComInstance> instances = Services.get(ICommissionInstanceDAO.class).retrieveNonClosedForInstanceTrigger(poLine, getComSystemType().getC_AdvComSystem_ID());
				if (instances.isEmpty())
				{
					continue; // 'poLine' didn't trigger a commission instance
				}
				final Map<Integer, IAdvComInstance> id2instance = new HashMap<Integer, IAdvComInstance>(instances.size());
				for (final IAdvComInstance inst : instances)
				{
					id2instance.put(inst.getC_AdvCommissionInstance_ID(), inst);
				}

				final Timestamp dateDoc = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfPO(po);
				final MBPartner bPartnerDoc = MCAdvCommissionFactCand.retrieveBPartnerOfPO(po);
				Check.assume(bPartnerDoc != null, "bPartnerDoc of po='" + po + "' is not null");

				final I_C_Sponsor customerSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(bPartnerDoc, true);

				// Step 2
				// Climb the upline from poLine's customer sponsor, using po's dateDoc and the current sponsor
				// hierarchy.
				// Find out which instances are still valid, given the current sponsor hierarchy and current commission
				// terms.
				// Valid instances are removed from 'id2instance'. The rest will be closed in step 3.
				new HierarchyAscender()
				{
					@Override
					public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel, final int logicalLevel, final int hierarchyLevel, final Map<String, Object> contextInfo)
					{
						final Properties ctx = InterfaceWrapperHelper.getCtx(sponsorCurrentLevel);
						final String trxName = InterfaceWrapperHelper.getTrxName(sponsorCurrentLevel);

						final boolean sponsorCurrentLevelWasSalesRepAtDate =
								!Services.get(ISponsorDAO.class).retrieveSSRsAtDate(ctx, sponsorCurrentLevel, dateDoc, X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP, trxName).isEmpty();

						final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

						final I_C_AdvCommissionCondition currentSponsorContract = sponsorBL.retrieveContract(ctx, sponsorCurrentLevel, dateDoc, trxName);
						if (sponsorCurrentLevelWasSalesRepAtDate)
						{
							for (final IAdvComInstance inst : Services.get(ICommissionInstanceDAO.class).retrieveFor(poLine, customerSponsor, sponsorCurrentLevel))
							{
								if (inst.getC_AdvCommissionTerm().getC_AdvCommissionCondition_ID() == currentSponsorContract.getC_AdvCommissionCondition_ID())
								{
									// according to the current sponsor hierarchy,
									// *and* to the sponsor's current contract,
									// 'inst' is still valid.
									id2instance.remove(inst.getC_AdvCommissionInstance_ID());
								}
							}
						}
						return Result.GO_ON;
					}
				}
						.setDate(dateDoc)
						.climb(customerSponsor, Integer.MAX_VALUE);

				// Step 3
				// Those instances that are still included in 'id2instance' are not valid, according to the current
				// sponsor hierarchy. Close them
				for (final IAdvComInstance instanceToClose : id2instance.values())
				{
					final ICommissionFactBL commissionFactBL = Services.get(ICommissionFactBL.class);

					// 3.a
					// unwind the instance's commissionInvoiceFacts
					final List<MCAdvCommissionFact> commissionInvoiceFacts =
							MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instanceToClose.getC_AdvCommissionInstance_ID())
									.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, false)
									.setCounterRecord(false)
									.setTableId(org.compiere.model.I_C_InvoiceLine.Table_ID)
									.list();
					for (final MCAdvCommissionFact commissionInvoiceFact : commissionInvoiceFacts)
					{
						if (commissionFactBL.isCommissionInvoiceFactUnwound(commissionInvoiceFact))
						{
							continue;
						}

						// Get the calculation (aka commission payroll line) minus facts that precede
						// 'commissionInvoiceFact'.
						// Note: there might be more that one calculation fact for one invoice fact
						final List<MCAdvCommissionFact> commissionCalculationMinusFacts =
								MCAdvCommissionFact.mkQuery(ctx, trxName)
										.setFollowUpSubstring(Integer.toString(commissionInvoiceFact.get_ID()))
										.setAdvCommissionInstanceId(instanceToClose.getC_AdvCommissionInstance_ID())
										.list();
						for (final MCAdvCommissionFact commissionCalculationMinusFact : commissionCalculationMinusFacts)
						{
							Check.assume(commissionCalculationMinusFact.isCounterEntry(), "commissionCalculationMinusFact has CounterEntry='Y'");

							// Get the plus fact that precedes 'commissionCalculationMinusFact'
							final List<MCAdvCommissionFact> commissionCalculationPlusFacts =
									MCAdvCommissionFact.mkQuery(ctx, trxName)
											.setFollowUpSubstring(Integer.toString(commissionCalculationMinusFact.get_ID()))
											.setAdvCommissionInstanceId(instanceToClose.getC_AdvCommissionInstance_ID())
											.setIncludeInactive(true) // There are facts that we disabled for some
																		// reasons. But this should not hinder the
																		// unwinding
											.list();
							Check.assume(commissionCalculationPlusFacts.size() == 1,
									"There is one commissionCalculationPlusFact for " + commissionCalculationMinusFact);

							// Unwind 'commissionInvoiceFact' back to 'commissionCalculationPlusFact'
							commissionFactBL.unwindCommissionInvoice(commissionInvoiceFact, commissionCalculationPlusFacts.get(0), cand, adPInstanceId);
						}
					}

					// 3.b
					// Unwind the instance's commissionCalculationFacts
					// Note: 'commissionCalculationFacts' should include the new 'commissionCalculationPlusFacts' that were created
					// by 'unwindCommissionInvoice' above
					final List<MCAdvCommissionFact> commissionCalculationFacts =
							MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instanceToClose.getC_AdvCommissionInstance_ID())
									.setStatus(X_C_AdvCommissionFact.STATUS_Berechnet, false)
									.setCounterRecord(false)
									.setTableId(I_C_AdvCommissionPayrollLine.Table_ID)
									.setIncludeInactive(true) // There are facts that we disabled for some reasons. But
																// this should not hinder the unwinding
									.list();
					for (final MCAdvCommissionFact commissionCalculationFact : commissionCalculationFacts)
					{
						if (commissionFactBL.isCommissionCalculationFactUnwound(commissionCalculationFact))
						{
							// commissionCalculationFact has already been unwound; nothing to do
							continue;
						}
						if (commissionFactBL.isCommissionCalculationFactInvoiced(commissionCalculationFact))
						{
							// we won't unwind facts that have been invoiced
							continue;
						}

						// Get the commission trigger plus facts that precede 'commissionCalculationFact'.
						// Note: there might be more than one instance trigger fact for one calculation fact
						final List<MCAdvCommissionFact> commissionTriggerPlusFacts =
								MCAdvCommissionFact.mkQuery(ctx, trxName)
										.setAdvCommissionPayrollLineId(commissionCalculationFact.getRecord_ID())
										.setCounterRecord(false)
										.setAdvCommissionInstanceId(instanceToClose.getC_AdvCommissionInstance_ID())
										.setStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen, false)
										.setIncludeInactive(true) // There are facts that we disabled for some reasons.
																	// But this should not hinder the unwinding
										.list();

						for (final MCAdvCommissionFact commissionTriggerPlusFact : commissionTriggerPlusFacts)
						{
							Check.assume(!commissionTriggerPlusFact.isCounterEntry(), commissionTriggerPlusFact + " has CounterEntry='N'");

							// Unwind 'commissionCalculationFact' back to 'commissionTriggerPlusFact'
							commissionFactBL.unwindCommissionCalculation(commissionCalculationFact, commissionTriggerPlusFact, cand, adPInstanceId);
						}
					}

					// 3.c and 3.d
					closeForStatus(adPInstanceId, cand, condChangeLine, dateDoc, instanceToClose, X_C_AdvCommissionFact.STATUS_ZuBerechnen);
					closeForStatus(adPInstanceId, cand, condChangeLine, dateDoc, instanceToClose, X_C_AdvCommissionFact.STATUS_Prognostiziert);

					instanceToClose.setIsClosed(true);
					InterfaceWrapperHelper.save(instanceToClose);
				}
			}
		}
	}

	private void closeForStatus(
			final int adPinstanceId,
			final MCAdvCommissionFactCand cand,
			final I_C_Sponsor_CondLine condChangeLine,
			final Timestamp dateDoc,
			final IAdvComInstance instanceToClose,
			final String status)
	{
		final BigDecimal points = Services.get(ICommissionInstanceDAO.class).retrieveBalancePoints(instanceToClose, status);
		final BigDecimal pointsSum = Services.get(ICommissionInstanceDAO.class).retrieveBalancePointsSum(instanceToClose, status);
		final BigDecimal amt = Services.get(ICommissionInstanceDAO.class).retrieveBalanceAmt(instanceToClose, status);
		final BigDecimal qty = Services.get(ICommissionInstanceDAO.class).retrieveBalanceQty(instanceToClose, status);

		if (points.signum() != 0 || pointsSum.signum() != 0 || amt.signum() != 0 || qty.signum() != 0)
		{
			final MCAdvCommissionFact minusFact = MCAdvCommissionFact.createNew(InterfaceWrapperHelper.getPO(condChangeLine), cand);
			minusFact.setStatus(status);
			minusFact.setAD_PInstance_ID(adPinstanceId);
			minusFact.setIsCounterEntry(true);
			minusFact.setC_AdvCommissionInstance_ID(instanceToClose.getC_AdvCommissionInstance_ID());

			minusFact.setCommissionPointsBase(null);
			minusFact.setQty(qty.negate());
			minusFact.setCommissionPointsSum(pointsSum.negate());
			minusFact.setCommission(null);
			minusFact.setCommissionPoints(points.negate());
			minusFact.setAmtMultiplier(null);
			minusFact.setCommissionAmt(null);
			minusFact.setDateDoc(dateDoc);
			minusFact.setDateFact(cand.getDateAcct());

			minusFact.setFactType(X_C_AdvCommissionFact.FACTTYPE_VorgangSchliessen);
			minusFact.saveEx();

			SponsorCond.logger.debug("Created minus fact " + minusFact);
		}
		else
		{
			SponsorCond.logger.debug("No need to create minus fact for status='" + status + "' and " + instanceToClose);
		}
	}

	/**
	 * Creates new MCAdvCommissionFactCand records for the given set of POs. Note:
	 * <ul>
	 * <li>For each PO, the existing (and processed) candidates are loaded. For each such candidate, a new candidate is created.</li>
	 * <li>The new candidates are created using the same order that their respective old counter-parts have</li>
	 * </ul>
	 * 
	 * @param ssrCand
	 * @param posToReevaluate
	 */
	private void reschedulePOs(final MCAdvCommissionFactCand ssrCand, final Set<Object> posToReevaluate)
	{
		// using a sorted map to make sure that the new candidates will be created in the same order that the existing
		// candidates have.
		final SortedMap<Integer, Object> candId2PO = new TreeMap<Integer, Object>();

		final Map<Integer, MCAdvCommissionFactCand> candId2Cand = new HashMap<Integer, MCAdvCommissionFactCand>();

		for (final Object po : posToReevaluate)
		{
			final List<MCAdvCommissionFactCand> candsForPO = MCAdvCommissionFactCand.retrieveProcessedForPO(po, false);
			for (final MCAdvCommissionFactCand existingCand : candsForPO)
			{
				candId2PO.put(existingCand.get_ID(), po);
				candId2Cand.put(existingCand.get_ID(), existingCand);
			}
			SponsorCond.logger.debug("Retrieved " + candsForPO.size() + " existing candidates for " + po);
		}

		SponsorCond.logger.info("Retrieved " + candId2PO.size() + " existing candidates for " + posToReevaluate.size() + " POs");

		for (final Entry<Integer, Object> entry : candId2PO.entrySet())
		{
			final MCAdvCommissionRelevantPO relevantPO = (MCAdvCommissionRelevantPO)candId2Cand.get(entry.getKey()).getC_AdvCommissionRelevantPO();

			final MCAdvCommissionFactCand newCand = MCAdvCommissionFactCand.createNoSave(entry.getValue(), relevantPO);
			Services.get(ICommissionFactCandBL.class).setCause(newCand, ssrCand);
			newCand.setAlsoHandleTypesWithProcessNow(true);
			newCand.saveEx();

			SponsorCond.logger.debug("Created new candidate " + newCand);
		}
	}

	/**
	 * Important: Returned string includes the name of this type's commission system. That means that toString() will make a DB-request!
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() + "[");
		sb.append("Name=" + getComSystemType().getName());
		sb.append("; ComSystem=" + getComSystemType().getC_AdvComSystem().getName());
		sb.append("]");

		return sb.toString();
	}

}
