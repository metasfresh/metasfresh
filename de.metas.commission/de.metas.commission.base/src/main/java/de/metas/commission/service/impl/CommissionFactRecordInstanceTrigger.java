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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Period;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionInstance;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.PrecedingFactFinder;
import de.metas.commission.util.SuccessiveFactsFinder;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * Helper class that records a commission trigger (sales order line or sales invoice line). It's supposed to be called by
 * {@link CommissionFactBL#recordInstanceTrigger(ICommissionType, MCAdvCommissionFactCand, Timestamp, PO, MCAdvCommissionInstance, int)}
 * 
 * 
 * @author ts
 * 
 */
class CommissionFactRecordInstanceTrigger
{
	private static final Logger logger = LogManager.getLogger(CommissionFactBL.class);

	private final CommissionFactBL factBL;

	CommissionFactRecordInstanceTrigger(final CommissionFactBL factBL)
	{
		this.factBL = factBL;
	}

	public void recordInstanceTrigger(
			final ICommissionType type,
			final I_C_AdvCommissionFactCand cand,
			final Timestamp dateToUse,
			final Object plusPoLine,
			final IAdvComInstance instance,
			final int adPInstanceId)
	{
		Check.assume(!cand.isInfo(),
				"cand=" + cand + " plusPoLine=" + plusPoLine + " and instance=" + instance);
		Check.assume(InterfaceWrapperHelper.isInstanceOf(plusPoLine, I_C_OrderLine.class) || InterfaceWrapperHelper.isInstanceOf(plusPoLine, I_C_InvoiceLine.class),
				plusPoLine + " is a I_C_OrderLine or I_C_InvoiceLine");
		Check.assume(instance.getC_AdvComSystem_Type_ID() == type.getComSystemType().getC_AdvComSystem_Type_ID(),
				"instance and type must be consistent; instance=" + instance + "; type=" + type);

		if (instance.isClosed())
		{
			CommissionFactRecordInstanceTrigger.logger.info("Not processing closed instance " + instance);
			return;
		}

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);

		final UpdateType updateType;

		I_C_AdvCommissionFact oldFact = null;
		final BigDecimal oldQty;

		final BigDecimal plusQty;
		String plusStatusTmp = null;

		final Properties ctx = InterfaceWrapperHelper.getCtx(cand, true);
		final String trxName = InterfaceWrapperHelper.getTrxName(cand);

		//
		// find out if there are existing facts for plusPoLine
		final List<MCAdvCommissionFact> factsForPoLine =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setTableId(InterfaceWrapperHelper.getModelTableId(plusPoLine))
						.setRecordId(InterfaceWrapperHelper.getId(plusPoLine))
						.list();

		if (factsForPoLine.isEmpty())
		{
			// 'plusPoLine' is a po that has not yet been recored in this instance

			// plusPoLine must be an invoice line, because there is never more than one ol per instance
			// and if there is such an ol, it is the instance trigger and has therefore been recorded when the instance
			// was created.
			Check.assume(plusPoLine instanceof MInvoiceLine, plusPoLine + " is a MInvoiceLine");

			plusQty = faBL.getQty(plusPoLine);

			//
			// decide which is the 'old' poLine that has already been recorded earlier and which 'plusPoLine' refers to.

			// First, find out if there is a counter (reversal) invoice line
			PO recordedReversalLine = null;
			final MInvoiceLine reverseLine = findReverseLine((MInvoiceLine)plusPoLine);
			if (reverseLine != null)
			{
				// checking for reverse/counter line facts in instance
				final List<MCAdvCommissionFact> revIlFacts =
						MCAdvCommissionFact.mkQuery(ctx, trxName)
								.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
								.setTableId(reverseLine.get_Table_ID())
								.setRecordId(reverseLine.get_ID())
								.setCounterRecord(false)
								.list();

				if (!revIlFacts.isEmpty())
				{
					recordedReversalLine = reverseLine;
				}
			}

			final PO oldPoLine;
			if (recordedReversalLine != null)
			{
				// plusPoLine is an il that reverses another, already recorded il.
				// That means, both il have to neutralize each other in this instance.
				updateType = UpdateType.REVERSE_INVOICE;
				oldPoLine = recordedReversalLine;

				// we don't subtract the old il's qty, because we want the two ils' qtys to
				// neutralize each other.
				oldQty = BigDecimal.ZERO;
			}
			else
			{
				updateType = UpdateType.TRANSFER;

				if (prepayOrderBL.isPrepayOrder(ctx, ((MInvoiceLine)plusPoLine).getC_OrderLine().getC_Order_ID(), trxName))
				{
					// plusPoLine is an il that belongs to a prepay order. That means that the order has already been
					// paid and the C_AllocationLine has already been recorded.
					//
					// We are therefore dealing with a transfer of points from allocLine to il
					final List<MCAdvCommissionFact> allocLineFacts = MCAdvCommissionFact.mkQuery(ctx, trxName)
							.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
							.setTableId(MTable.getTable_ID(I_C_AllocationLine.Table_Name))
							.setCounterRecord(false)
							.setStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen, false)
							.list();
					Collections.reverse(allocLineFacts);

					// look for the latest allocLineFact that has a qty
					MCAdvCommissionFact allocLineFactToUse = null;
					for (final MCAdvCommissionFact allocLineFact : allocLineFacts)
					{
						if (allocLineFact.getQty().signum() > 0)
						{
							allocLineFactToUse = allocLineFact;
							break;
						}
					}
					if (allocLineFactToUse == null)
					{
						// This can happen if the candidate has been created by the BuildRelevantPOQueue process.
						// Note: the il for a prepay order is not really relevant, so we don't absolutely have to record
						// it.
						CommissionFactRecordInstanceTrigger.logger.info("Not recording prepay order related " + plusPoLine + ", because the preceding allocLine has not been recorded either");
						return;
					}

					oldPoLine = allocLineFactToUse.retrievePO();
				}
				else
				{
					// we are dealing with a transfer of points from ol to il
					oldPoLine = InterfaceWrapperHelper.getPO(Services.get(ICommissionInstanceDAO.class).retrievePO(instance, Object.class));
				}

				// 02777: A "transfer" implies that the Qty is not changed between minusFact and plusFact.
				// Therefore we use the plusPoLine's qty as oldQty
				oldQty = faBL.getQty(plusPoLine);

				// Note: a transfer can be partial, so the qty of 'plusPoLine might be smaller
				// task 05176: ts: this assumpty doesn't always hold. e.g. the ordered qty is *usually* >= the invoiced qty, but that's not a law
				// also, if an order is voided, then the QtyOrdered is set to 0, but and existing invoice is reversed (so it keeps it's Qty)
				// Check.assume(faBL.getQty(oldPoLine) == null
				// || faBL.getQty(oldPoLine).compareTo(faBL.getQty(plusPoLine)) >= 0,
				// "Qty of " + oldPoLine + " is greater than or equal qty of" + plusPoLine);

			}

			//
			// retrieve the 'old' fact that belongs to 'oldPoLine' that we need to update here
			final List<MCAdvCommissionFact> factsForTransferSource =
					MCAdvCommissionFact.mkQuery(ctx, trxName)
							.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
							.setTableId(oldPoLine.get_Table_ID())
							.setRecordId(oldPoLine.get_ID())
							.setCounterRecord(false)
							.list();
			Check.assume(!factsForTransferSource.isEmpty(), instance.toString());

			oldFact = factsForTransferSource.get(factsForTransferSource.size() - 1);
		}
		else
		{
			// 'plusPoLine' has already been recorded in this instance, but has been changed since then.
			updateType = UpdateType.UPDATE;

			if (InterfaceWrapperHelper.isInstanceOf(plusPoLine, I_C_OrderLine.class))
			{
				final Object newHeaderPO = faBL.retrieveHeader(plusPoLine);

				if (prepayOrderBL.isPrepayOrder(ctx, InterfaceWrapperHelper.getId(newHeaderPO), trxName))
				{
					// 'plusPoLine' is a prepay order line. find out if there are allocation lines for plusPoLine.
					// If there are any, find out if they make poLine a commission trigger.
					// Also find out which 'oldFact' we can use

					final List<MCAdvCommissionFact> alFacts =
							MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
									.setTableId(MTable.getTable_ID(I_C_AllocationLine.Table_Name))
									.setCounterRecord(false)
									.setStatus(X_C_AdvCommissionFact.STATUS_ZuBerechnen, false)
									.list();
					if (!alFacts.isEmpty())
					{
						final PrecedingFactFinder factFinder = new PrecedingFactFinder()
						{
							@Override
							public boolean isOK(final I_C_AdvCommissionFact fact)
							{
								if (fact.isCounterEntry())
								{
									return false;
								}

								final PO factPO = MCAdvCommissionFact.retrievePO(fact);
								if (factPO instanceof MAllocationLine)
								{
									// There can be recorded alloc lines that belong to a commission invoice
									// or alloc lines without commission points.
									// We are *not* interested in either of those.
									if (X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung.equals(fact.getFactType())
											|| X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg.equals(fact.getFactType())
											|| type.isCommissionCalculated() && fact.getCommissionPoints().signum() == 0
											|| !type.isCommissionCalculated() && fact.getCommissionPointsBase().signum() == 0)
									{
										return false;
									}
									return true;
								}
								if (factPO instanceof MInvoiceLine)
								{
									// For an invoice line, we are *not* interested if the il's header is reversed.
									final boolean isReversalLine = ((MInvoiceLine)factPO).getC_Invoice().getReversal_ID() > 0;
									return !isReversalLine;
								}
								if (factPO instanceof MOrderLine)
								{
									Check.assume(factPO.get_ID() == InterfaceWrapperHelper.getId(plusPoLine), "Can't record plusPoLine=" + plusPoLine + " in instance=" + instance + " with fact="
											+ fact + " having po=" + factPO);
									return !fact.isCounterEntry();
								}
								return false;
							}
						};
						oldFact = factFinder.retrievePrecedingFirst(ctx, instance, alFacts.get(alFacts.size() - 1), null, trxName);
						Check.assume(oldFact != null, "When updating instance=" + instance + " with ol=" + plusPoLine + " we expect at least an older fact with same ol");

						// Now we found our 'oldFactTmp'.
						// If oldFactTmp is an allocation line, also find out if it make plusPoLine a commission trigger
						if (MCAdvCommissionFact.retrievePO(oldFact) instanceof MAllocationLine)
						{
							final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, InterfaceWrapperHelper.getId(newHeaderPO), trxName);
							Check.assume(allocatedAmt.signum() >= 0, "allocation lines for " + newHeaderPO + " don't sum up to " + allocatedAmt);
							final BigDecimal grandTotal = ((MOrder)newHeaderPO).getGrandTotal();
							if (allocatedAmt.compareTo(grandTotal) < 0)
							{
								plusStatusTmp = X_C_AdvCommissionFact.STATUS_Prognostiziert;
							}
						}
					}
				}
				else
				{
					// newPoLine is an orderLine (but no prepay order) and might not be the relevant poLine for this
					// instance (ol when an il is already present)
					final List<MCAdvCommissionFact> ilFacts =
							MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
									.setTableId(MTable.getTable_ID(org.compiere.model.I_C_InvoiceLine.Table_Name))
									.setCounterRecord(false)
									.list();

					if (!ilFacts.isEmpty())
					{
						for (int i = ilFacts.size() - 1; i >= 0; i--)
						{
							// Check if there is a fact with an un-reversed sales invoice line
							final MInvoiceLine il = (MInvoiceLine)ilFacts.get(i).retrievePO();

							final I_C_Invoice invoice = InterfaceWrapperHelper.create(faBL.retrieveHeader(il), I_C_Invoice.class);

							if (invoice.isSOTrx() && invoice.getReversal_ID() <= 0)
							{
								CommissionFactRecordInstanceTrigger.logger.info("Ignoring plusPoLine=" + plusPoLine + " for instance ID="
										+ instance.getC_AdvCommissionInstance_ID() + ", because there is already a fact with ID="
										+ ilFacts.get(i).get_ID() + " for " + il);
								return;
							}
						}
					}
				}
			}

			if (oldFact == null)
			{
				final PrecedingFactFinder factFinder = new PrecedingFactFinder()
				{
					@Override
					public boolean isOK(final I_C_AdvCommissionFact fact)
					{
						if (fact.isCounterEntry())
						{
							return false;
						}
						final PO factPO = MCAdvCommissionFact.retrievePO(fact);
						if (factPO instanceof MAllocationLine)
						{
							// There can be recorded alloc lines that belong to a commission invoice (fact types 'PAL', 'PAR')
							// or alloc lines without commission points ().
							// We are *not* interested in either of those.
							if (X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung.equals(fact.getFactType())
									|| X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg.equals(fact.getFactType())
									|| type.isCommissionCalculated() && fact.getCommissionPoints().signum() == 0
									|| !type.isCommissionCalculated() && fact.getCommissionPointsBase().signum() == 0)
							{
								return false;
							}
							return true;
						}
						if (factPO instanceof MInvoiceLine)
						{
							final org.compiere.model.I_C_Invoice invoice = ((MInvoiceLine)factPO).getC_Invoice();
							// for an invoice line, we are *not* interested if the il's header is reversed or if the invoice
							final boolean isReversalLine = invoice.getReversal_ID() > 0;
							return invoice.isSOTrx() && !isReversalLine;
						}
						if (factPO instanceof MOrderLine)
						{
							if (plusPoLine instanceof MInvoiceLine)
							{
								return false;
							}
							Check.assume(factPO.get_ID() == InterfaceWrapperHelper.getId(plusPoLine), factPO + " is qual to " + plusPoLine + ";  instance=" + instance);
							return true;
						}
						return false;
					}
				};

				// select oldFact
				// retrieve existing facts for plusPoLine
				final List<MCAdvCommissionFact> nonCounterFacts =
						MCAdvCommissionFact.mkQuery(ctx, trxName)
								.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
								.setCounterRecord(false)
								.list();
				final MCAdvCommissionFact lastNoncounterFact = nonCounterFacts.get(nonCounterFacts.size() - 1);
				if (factFinder.isOK(lastNoncounterFact))
				{
					oldFact = lastNoncounterFact;
				}
				else
				{
					oldFact = factFinder.retrievePrecedingFirst(ctx, instance, lastNoncounterFact, null, trxName);
				}
				CommissionFactRecordInstanceTrigger.logger.debug("oldFact=" + oldFact);
				if (oldFact == null)
				{
					// Nothing to do. This can happen due to the fact that we don't record further invoice changes
					// *after* the commission calculation.
					// Known example: Invoice "99997" has been reversed after commission calculation. The reversal has
					// thus not been recorded.
					return;
				}
			}

			Check.assume(oldFact != null, "oldFact is not null");
			final PO oldPoLine = MCAdvCommissionFact.retrievePO(oldFact);

			oldQty = oldFact.getQty();

			if (InterfaceWrapperHelper.isInstanceOf(oldPoLine, I_C_InvoiceLine.class))
			{
				// invoiceLines can't change their qty, they are just
				// invalidated, reversed etc.
				plusQty = oldQty;
			}
			else
			{
				plusQty = faBL.getQty(plusPoLine);
			}
		}

		final String plusStatus;
		if (plusStatusTmp != null)
		{
			plusStatus = plusStatusTmp;
		}
		else
		{
			plusStatus = oldFact.getStatus();
		}

		//
		// get old values and compute the new ones
		final BigDecimal plusCommission = type.getPercent(instance, X_C_AdvComSalesRepFact.STATUS_Prognose, dateToUse).multiply(Env.ONEHUNDRED);
		final BigDecimal plusPointsSum = type.getCommissionPointsSum(instance, X_C_AdvComSalesRepFact.STATUS_Prognose, dateToUse, plusPoLine);

		//
		// find out what has changed
		final boolean commissionPointsSumChanged = plusPointsSum.compareTo(oldFact.getCommissionPointsSum()) != 0;

		final boolean qtyChanged = plusQty.compareTo(oldQty) != 0;

		final boolean commissionChanged = plusCommission.compareTo(oldFact.getCommission()) != 0;

		final Timestamp newDateDoc = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfPO(faBL.retrieveHeader(plusPoLine));

		final PO minusPoLine = MCAdvCommissionFact.retrievePO(oldFact);

		// figure out newPoLine's dateDoc and if it has changed
		final boolean dateDocChanged;
		if (UpdateType.UPDATE.equals(updateType) && minusPoLine.get_ID() == InterfaceWrapperHelper.getId(plusPoLine)
				&& minusPoLine.get_Table_ID() == InterfaceWrapperHelper.getModelTableId(plusPoLine))
		{
			dateDocChanged = !oldFact.getDateDoc().equals(newDateDoc);
		}
		else
		{
			dateDocChanged = false;
		}
		final String changeReason;
		if (UpdateType.TRANSFER.equals(updateType) || UpdateType.REVERSE_INVOICE.equals(updateType))
		{
			changeReason = "";
			CommissionFactRecordInstanceTrigger.logger.info("Recording new po " + plusPoLine + " within " + instance);
		}
		else if (qtyChanged || commissionPointsSumChanged || commissionChanged || dateDocChanged)
		{
			changeReason =
					mkUpdateChangeReason(ctx, qtyChanged, commissionPointsSumChanged, commissionChanged, dateDocChanged);
			CommissionFactRecordInstanceTrigger.logger.info("Updating existing po " + MCAdvCommissionFact.retrievePO(oldFact) + " within " + instance);
		}
		else
		{
			CommissionFactRecordInstanceTrigger.logger.info("There are no data changes for already recorded po " + MCAdvCommissionFact.retrievePO(oldFact) + " within " + instance);
			return;
		}

		//
		// compute the actual values to record
		final BigDecimal minusQty = oldQty.negate();
		final BigDecimal minusPointsBase = oldFact.getCommissionPointsBase();
		final BigDecimal minusCommission = oldFact.getCommission();
		final BigDecimal minusPointsSum = minusQty.multiply(minusPointsBase);
		final BigDecimal minusPoints = minusPointsSum.multiply(minusCommission).divide(Env.ONEHUNDRED, RoundingMode.HALF_UP);

		final BigDecimal plusPointsBase = plusQty.signum() == 0 ? BigDecimal.ZERO : plusPointsSum.divide(plusQty, RoundingMode.HALF_UP);
		final BigDecimal plusPoints = plusPointsSum.multiply(plusCommission).divide(Env.ONEHUNDRED, RoundingMode.HALF_UP);

		// make sure that the change can be recorded, even if there has already been a commission calculation and
		// invoicing

		// retrieve possible commission calculation facts for 'oldFact'
		final I_C_AdvCommissionFact commissionCalculationFact = retrieveCommissionCalculationFact(instance, oldFact);
		if (commissionCalculationFact != null)
		{
			// for our retrieved commission *calculation* fact, retrieve its respective commission *invoice* fact
			final I_C_AdvCommissionFact commissionInvoiceFact = retrieveCommissionInvoiceFact(instance, commissionCalculationFact);
			if (commissionInvoiceFact != null)
			{
				final I_C_AdvCommissionFact newCalculationFact = factBL.unwindCommissionInvoice(commissionInvoiceFact, commissionCalculationFact, cand, adPInstanceId);
				final I_C_AdvCommissionFact newInstanceTriggerFact = factBL.unwindCommissionCalculation(newCalculationFact, oldFact, cand, adPInstanceId);
				oldFact = newInstanceTriggerFact;
			}
			else
			{
				final I_C_AdvCommissionFact newInstanceTriggerFact = factBL.unwindCommissionCalculation(commissionCalculationFact, oldFact, cand, adPInstanceId);
				oldFact = newInstanceTriggerFact;
			}
		}

		final String factType = mkFactType(updateType);

		final MCAdvCommissionFact newMinusFact = factBL.createFact(
				type, cand,
				minusPoLine, instance,
				minusPointsBase, minusQty, minusPointsSum, minusCommission, minusPoints,
				factType, changeReason, adPInstanceId);

		newMinusFact.setStatus(oldFact.getStatus());
		newMinusFact.setIsCounterEntry(true);
		if (InterfaceWrapperHelper.isInstanceOf(minusPoLine, I_C_AllocationLine.class) && InterfaceWrapperHelper.isInstanceOf(plusPoLine, I_C_InvoiceLine.class))
		{
			final Timestamp dateFact = factBL.retrieveDateFact(minusPoLine);
			newMinusFact.setDateFact(dateFact != null ? dateFact : newMinusFact.getDateDoc());

			final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);
			newMinusFact.setCommissionAmtBase(faService.getLineNetAmtOrNull(plusPoLine).negate());
		}
		newMinusFact.saveEx();

		// Note: it is important that the minusFact is saved before the
		// plusFact(s) to make sure that the latest fact has the biggest ID
		MCAdvCommissionFact.addFollowUpInfo(oldFact, newMinusFact);
		InterfaceWrapperHelper.save(oldFact);

		final MCAdvCommissionFact newPlusFact = factBL.createFact(
				type, cand,
				plusPoLine, instance,
				plusPointsBase, plusQty, plusPointsSum, plusCommission, plusPoints,
				factType, changeReason, adPInstanceId);
		newPlusFact.setStatus(plusStatus);
		if (InterfaceWrapperHelper.isInstanceOf(minusPoLine, I_C_AllocationLine.class) && InterfaceWrapperHelper.isInstanceOf(plusPoLine, I_C_InvoiceLine.class))
		{
			final Timestamp dateFact = factBL.retrieveDateFact(plusPoLine);
			newPlusFact.setDateFact(dateFact != null ? dateFact : newMinusFact.getDateDoc());

		}
		newPlusFact.saveEx();

		newMinusFact.addFollowUpInfo(newPlusFact);

		if (UpdateType.REVERSE_INVOICE.equals(updateType))
		{
			// in case of a reversal, we usually create a 3rd fact

			// find the last preceding fact for oldFact, whose PO is not reversed
			final Map<ArrayKey, I_C_AdvCommissionFact> nonUsablePOs = new HashMap<ArrayKey, I_C_AdvCommissionFact>();
			nonUsablePOs.put(Util.mkKey(oldFact.getAD_Table_ID(), oldFact.getRecord_ID()), oldFact);

			final PrecedingFactFinder factFinder = new PrecedingFactFinder()
			{
				@Override
				public boolean isOK(final I_C_AdvCommissionFact fact)
				{
					if (isReverse(fact.getFactType()))
					{
						return false;
					}

					// 02914: we don't want facts that are related to commission calculation, commission payment etc
					if (Check.isEmpty(fact.getFactType())
							|| X_C_AdvCommissionFact.FACTTYPE_Aenderung.equals(fact.getFactType())
							|| X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig.equals(fact.getFactType())
							|| X_C_AdvCommissionFact.FACTTYPE_Umbuchung.equals(fact.getFactType())
							|| X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg.equals(fact.getFactType())
							|| X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung.equals(fact.getFactType()))
					{
						return true;
					}
					return false;
				}
			};
			final I_C_AdvCommissionFact precedingFact = factFinder.retrievePrecedingFirst(ctx, instance, oldFact, nonUsablePOs, trxName);

			if (precedingFact == null)
			{
				// there is no reversePO to which we can go back,
				// because what was just reversed was the instance starter.
				CommissionFactRecordInstanceTrigger.logger.info("Not creating reverseFact");
			}
			else
			{
				final MCAdvCommissionFact newReverseFact = factBL.createFact(
						type, cand,
						MCAdvCommissionFact.retrievePO(precedingFact), instance,
						plusPointsBase, plusQty.negate(), plusPointsSum.negate(), plusCommission, plusPoints.negate(),
						factType, changeReason, adPInstanceId);

				newReverseFact.setStatus(precedingFact.getStatus());
				newReverseFact.saveEx();

				newMinusFact.addFollowUpInfo(newReverseFact);
			}
		}
		newMinusFact.saveEx();

		//
		// check if the instance needs updating.
		if (!instance.getDateDoc().equals(cand.getDateAcct())
				&& InterfaceWrapperHelper.getId(plusPoLine) == InterfaceWrapperHelper.getId(Services.get(ICommissionInstanceDAO.class).retrievePO(instance, Object.class)))
		{
			// newPoLine is the instance trigger

			final Timestamp oldInstanceDate = instance.getDateDoc();
			final Timestamp newInstanceDate = Services.get(ICommissionFactCandBL.class).retrieveDateDocOfReferencedPO(cand);

			instance.setDateDoc(newInstanceDate);
			InterfaceWrapperHelper.save(instance);

			CommissionFactRecordInstanceTrigger.logger.info("Updated DateDoc of " + instance + " to " + cand.getDateAcct());

			//
			// check if the date was changed to another period.
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

			final I_C_Period oldPeriod = sponsorBL.retrieveCommissionPeriod(instance.getC_Sponsor_SalesRep(), oldInstanceDate);
			final I_C_Period newPeriod = sponsorBL.retrieveCommissionPeriod(instance.getC_Sponsor_SalesRep(), newInstanceDate);

			if (oldPeriod.getC_Period_ID() != newPeriod.getC_Period_ID())
			{
				final List<IAdvComInstance> allInstances =
						Services.get(ICommissionInstanceDAO.class).retrieveFor(
								InterfaceWrapperHelper.getPO(Services.get(ICommissionInstanceDAO.class).retrievePO(instance, Object.class)),
								type.getComSystemType());

				CommissionFactRecordInstanceTrigger.logger.info("Period changed from " + oldPeriod.getName() + " to " + newPeriod.getName()
						+ ". Updating LevelCalculation of " + allInstances.size() + " instances.");

				factBL.updateLevelCalculation(type, type.getComSystemType().getDynamicCompression(), newPeriod.getEndDate(), allInstances);
			}
		}
	}

	private I_C_AdvCommissionFact retrieveCommissionCalculationFact(
			final IAdvComInstance instance,
			final I_C_AdvCommissionFact oldFact)
	{
		if (oldFact.getC_AdvCommissionPayrollLine_ID() <= 0)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		final List<I_C_AdvCommissionFact> calculationFacts = new SuccessiveFactsFinder()
		{
			@Override
			public boolean isOK(final I_C_AdvCommissionFact fact)
			{
				if (X_C_AdvCommissionFact.FACTTYPE_Provisionsberechnung.equals(fact.getFactType())
						&& X_C_AdvCommissionFact.STATUS_Berechnet.equals(fact.getStatus())
						&& !fact.isCounterEntry()
						&& fact.getRecord_ID() == oldFact.getC_AdvCommissionPayrollLine_ID()
						&& fact.getAD_Table_ID() == I_C_AdvCommissionPayrollLine.Table_ID)
				{
					// 'fact' is a commission invoice fact.
					// But we are only interested in it, if it has *not* already been reverted
					for (final I_C_AdvCommissionFact followUpFact : MCAdvCommissionFact.retrieveFollowUpFacts(fact))
					{
						if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsberRueckabwicklung.equals(followUpFact.getFactType()))
						{
							return false;
						}
					}
					return true;
				}
				return false;
			}
		}.retrieveSuccessive(ctx, oldFact, trxName);

		Check.assume(calculationFacts.size() <= 1, "instance=" + instance + "; oldFact=" + oldFact + "; calculationFacts=" + calculationFacts);

		if (calculationFacts.isEmpty())
		{
			return null;
		}
		return calculationFacts.get(0);
	}

	private I_C_AdvCommissionFact retrieveCommissionInvoiceFact(
			final IAdvComInstance instance,
			final I_C_AdvCommissionFact calculationFact)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(instance);
		final String trxName = InterfaceWrapperHelper.getTrxName(instance);

		final List<I_C_AdvCommissionFact> invoiceFacts = new SuccessiveFactsFinder()
		{
			@Override
			public boolean isOK(final I_C_AdvCommissionFact fact)
			{
				if (X_C_AdvCommissionFact.FACTTYPE_Provisionsabrechnung.equals(fact.getFactType())
						&& X_C_AdvCommissionFact.STATUS_Auszuzahlen.equals(fact.getStatus())
						&& !fact.isCounterEntry())
				{
					// 'fact' is a commission invoice fact.
					// But we are only interested in it, if it has *not* already been reverted
					return !factBL.isCommissionInvoiceFactUnwound(fact);
				}
				return false;
			}
		}.retrieveSuccessive(ctx, calculationFact, trxName);

		assert invoiceFacts.size() <= 1 : "instance=" + instance + "; calculationFact=" + calculationFact + "; invoiceFacts=" + invoiceFacts;

		if (invoiceFacts.isEmpty())
		{
			return null;
		}
		return invoiceFacts.get(0);
	}

	private enum UpdateType
	{
		TRANSFER, UPDATE, REVERSE_INVOICE
	};

	private boolean isReverse(final String factType)
	{
		return X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig.equals(factType)
				|| X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg.equals(factType)
				|| X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg.equals(factType);
	}

	private String mkFactType(final UpdateType updateType)
	{
		final String factType;
		if (UpdateType.TRANSFER.equals(updateType))
		{
			factType = X_C_AdvCommissionFact.FACTTYPE_Umbuchung;
		}
		else if (UpdateType.REVERSE_INVOICE.equals(updateType))
		{
			factType = X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig;
		}
		else
		{
			factType = X_C_AdvCommissionFact.FACTTYPE_Aenderung;

		}
		return factType;
	}

	private MInvoiceLine findReverseLine(final MInvoiceLine il)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(il.getC_Invoice(), I_C_Invoice.class);

		final MInvoice counterDocPO;

		if (invoice.getReversal_ID() > 0)
		{
			counterDocPO = (MInvoice)invoice.getReversal();
		}
		else
		{
			return null;
		}

		// il's invoice references a counter document
		for (final MInvoiceLine counterIl : counterDocPO.getLines())
		{
			if (counterIl.getC_OrderLine_ID() == il.getC_OrderLine_ID())
			{
				return counterIl;
			}
		}
		return null;
	}

	private String mkUpdateChangeReason(final Properties ctx,
			final boolean qtyChanged,
			final boolean commissionPointsBaseChanged,
			final boolean commissionChanged, final boolean dateDocChanged)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(Msg.getMsg(ctx, MCAdvCommissionFact.MSG_COMMISSION_CHANGE));
		sb.append(" ");

		boolean atLeastOne = false;

		if (qtyChanged)
		{
			sb.append(Msg.translate(ctx, I_C_AdvCommissionFact.COLUMNNAME_Qty));
			atLeastOne = true;
		}
		if (commissionPointsBaseChanged)
		{
			if (atLeastOne)
			{
				sb.append(", ");
			}
			sb.append(Msg.translate(ctx, I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsBase));
			atLeastOne = true;
		}
		if (commissionChanged)
		{
			if (atLeastOne)
			{
				sb.append(", ");
			}
			sb.append(Msg.translate(ctx, I_C_AdvCommissionFact.COLUMNNAME_Commission));
			atLeastOne = true;
		}

		if (dateDocChanged)
		{
			if (atLeastOne)
			{
				sb.append(", ");
			}
			sb.append(Msg.translate(ctx, I_C_AdvCommissionFact.COLUMNNAME_DateDoc));
		}

		return sb.toString();
	}
}
