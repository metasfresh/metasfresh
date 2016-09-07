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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MChat;
import org.compiere.model.MChatEntry;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_AllocationHdr;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFact.FactQuery;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.util.CommissionTools;
import de.metas.commission.util.PrecedingFactFinder;
import de.metas.document.IDocumentPA;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * Helper class that records an allocation line. It's supposed to be called by {@link CommissionFactBL#recordAllocationLine(ICommissionType, MCAdvCommissionFactCand, MAllocationLine, int)}
 * 
 * 
 * @author ts
 * 
 */
class CommissionFactRecordAllocationLine
{
	private static final Logger logger = LogManager.getLogger(CommissionFactBL.class);

	private final CommissionFactBL factBL;

	CommissionFactRecordAllocationLine(final CommissionFactBL factBL)
	{
		this.factBL = factBL;
	}

	void recordAllocationLine(
			final ICommissionType type,
			final MCAdvCommissionFactCand cand,
			final MAllocationLine allocLinePO,
			final int adPInstanceId)
	{
		final MInvoice invoice = allocLinePO.getInvoice();

		final I_C_AllocationLine allocLine = InterfaceWrapperHelper.create(allocLinePO, I_C_AllocationLine.class);
		if (allocLine.getReversalLine_ID() > 0
				&& allocLine.getC_AllocationLine_ID() > allocLine.getReversalLine_ID())
		{
			// 'allocLine' has a reversal line which is older that 'allocLine' itself.
			// This means that the allocation is the reversal of an older allocation. (introduced by 02181).
			// The commission business logic doesn't need to handle this reversal,
			// as the original allocation with its status change to "RE" is sufficient.

			// Note: we can't assume this, because the reversal is first completed and only shortly after that it's
			// status is reset to 'RE'
			// Check.assume(X_C_AllocationHdr.DOCSTATUS_Reversed.equals(allocLine.getC_AllocationHdr().getDocStatus()),
			// "C_AllocationHdr of " + allocLinePO + " is reversed");
			return;
		}
		if (invoice != null && CommissionTools.isEmployeeInvoice(invoice))
		{
			for (final MInvoiceLine il : invoice.getLines())
			{
				for (final IAdvComInstance instanceForPOLine : Services.get(ICommissionInstanceDAO.class).retrieveAllFor(il, type.getComSystemType()))
				{
					if (instanceForPOLine.isClosed())
					{
						CommissionFactRecordAllocationLine.logger.debug("Also processing closed instance " + instanceForPOLine);
					}
					CommissionFactRecordAllocationLine.logger.debug("Recording " + allocLinePO + " within " + instanceForPOLine);
					recordAllocationLine(instanceForPOLine, cand, allocLinePO, il, RecordAllocLineMode.COMMISSIOM_PAYMENT, adPInstanceId);
				}
			}
		}
		else
		{
			// We have a "common" allocation
			// Find out if it belongs to an order or invoice and get the lines
			final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
			final int orderID = allocLinePO.getC_Order_ID();

			final List<PO> poLines = new ArrayList<PO>();

			final boolean allocLineHasPrepayOrder = orderID > 0 && prepayOrderBL.isPrepayOrder(cand.getCtx(), orderID, cand.get_TrxName());

			if (invoice != null && !MCAdvCommissionFactCand.retrieveProcessedForPO(invoice, cand.getCurrentRelPOType().isProcessImmediately()).isEmpty())
			{
				// there is an invoice associated with the given 'allocLine' and that invoice has already been processed
				// (at least once). That means, that the allocLine should be recorded "against" the invoice line.

				final MInvoiceLine[] invoiceLines = invoice.getLines();

				final String docBaseType = Services.get(IDocumentPA.class).retrieveDocBaseType(invoice.getC_DocTypeTarget_ID(), Trx.TRXNAME_None);
				final boolean invoiceIsCreditMemo = X_C_DocType.DOCBASETYPE_ARCreditMemo.equals(docBaseType);

				// Make sure that the invoice line is *really* recorded.
				// For unknown reasons, ils were not recorded, despite their candidates being marked as processed.
				for (final MInvoiceLine il : invoiceLines)
				{
					if (il.getC_OrderLine_ID() <= 0 || invoiceIsCreditMemo)
					{
						poLines.add(il);
					}
					else
					{
						final MOrderLine ol = (MOrderLine)il.getC_OrderLine();

						final List<IAdvComInstance> instancesForOl = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(ol, type.getComSystemType());
						for (final IAdvComInstance instanceForOl : instancesForOl)
						{
							// see if there is at least one fact for 'il'
							final List<MCAdvCommissionFact> ilFactsForInst = MCAdvCommissionFact.mkQuery(allocLinePO.getCtx(), allocLinePO.get_TrxName())
									.setAdvCommissionInstanceId(instanceForOl.getC_AdvCommissionInstance_ID())
									.setTableId(org.compiere.model.I_C_InvoiceLine.Table_ID)
									.setRecordId(il.get_ID())
									.list();
							if (ilFactsForInst.isEmpty())
							{
								// There are commission instances for ol, but no facts for il (despite the fact that
								// il's candidate has been processed!).
								// Note: we have a bug, that for unknown reasons ils don't get always recorded
								if (allocLineHasPrepayOrder)
								{
									// Don't use il, because they haven't been recorded and aren't really
									// that important, as we have a prepay order
									poLines.add(ol);
								}
								else
								{
									final Timestamp dateToUse =
											Services.get(ISponsorBL.class).retrieveDateTo(instanceForOl.getC_Sponsor_SalesRep(), instanceForOl.getC_AdvComSystem_Type(),
													instanceForOl.getDateDoc());

									// Use il.
									// And for good measure, make sure that il is now recorded.
									factBL.recordInstanceTrigger(type, cand, dateToUse, il, instanceForOl, adPInstanceId);
									poLines.add(il);
								}
							}
							else
							{
								// this is the default (without any bug workaround)
								poLines.add(il);
							}
						}
					}
				}
			}

			else if (allocLineHasPrepayOrder)
			{
				final MOrder order = (MOrder)allocLinePO.getC_Order();
				for (final MOrderLine ol : order.getLines())
				{
					poLines.add(ol);
				}
			}
			else
			{
				CommissionFactRecordAllocationLine.logger.debug(allocLinePO + " does not refer to a prepay order or invoice. Nothing to do.");
				return;
			}

			// Get the instances for each il or ol and record the allocationLine.
			for (final PO poLine : poLines)
			{
				for (final IAdvComInstance instanceForPOLine : Services.get(ICommissionInstanceDAO.class).retrieveAllFor(poLine, type.getComSystemType()))
				{
					if (instanceForPOLine.isClosed())
					{
						CommissionFactRecordAllocationLine.logger.info("Not processing closed instance " + instanceForPOLine);
						continue;
					}
					CommissionFactRecordAllocationLine.logger.debug("Recording " + allocLinePO + " within " + instanceForPOLine);
					recordAllocationLine(instanceForPOLine, cand, allocLinePO, poLine, RecordAllocLineMode.INSTANCE_TRIGGER, adPInstanceId);
				}
			}
		}
	}

	/**
	 * For a given MAllocationLine (<code>allocLine</code>) and a MOrderLine or MInvoiceLine (<code>poLine</code>) whose parent the allocLine refers to, this method updates the given
	 * MCAdvCommissionInstance. If allocLine's parent ( {@link MAllocationHdr}) is neither complete nor reversed, there is nothing to do. Otherwise, there are different cases to distinguish:
	 * http://dewiki908/mediawiki/index.php/US342:_Definition_Provisionsausl%C3%B6ser_%282010070510001145 %29#Table_of_different_cases
	 * 
	 * @param instance the instance that is updated with the given allocLine
	 * @param cand contains further informations about allocLine
	 * @param allocLine the allocation line to record
	 * @param poLine the MOrderLine or MInvoiceLine whose parent is referred to by the allocation line
	 * @param adPInstanceId
	 */
	private void recordAllocationLine(
			final IAdvComInstance instance,
			final MCAdvCommissionFactCand cand,
			final MAllocationLine allocLine,
			final PO poLine,
			final RecordAllocLineMode mode,
			final int adPInstanceId)
	{
		Check.assume(poLine instanceof MOrderLine || poLine instanceof MInvoiceLine,
				poLine + " is a MOrderLine or MInvoiceLine");

		final String allocParentStatus = allocLine.getParent().getDocStatus();
		if (!X_C_AllocationHdr.DOCSTATUS_Completed.equals(allocParentStatus) && !X_C_AllocationHdr.DOCSTATUS_Reversed.equals(allocParentStatus))
		{
			CommissionFactRecordAllocationLine.logger.debug("Parent of " + allocLine + " has DocStatus=" + allocParentStatus + ". Nothing to do.");
			return;
		}

		//
		// 1. Get all the data that we need in order to decide what to do
		//

		//
		// get the status of the most recent 'poLine' commission fact
		final Properties ctx = poLine.getCtx();
		final String trxName = poLine.get_TrxName();
		final List<MCAdvCommissionFact> poLineFacts =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setTableId(poLine.get_Table_ID())
						.setRecordId(poLine.get_ID())
						.list();
		Check.assume(!poLineFacts.isEmpty(), "instance = " + instance + "; poLine=" + poLine);
		final MCAdvCommissionFact lastPoLineFact = poLineFacts.get(poLineFacts.size() - 1);

		final PO lastPOLine = lastPoLineFact.retrievePO();

		if (X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig.equals(lastPoLineFact.getFactType()))
		{
			// this allocation line's C_AllocationHdr allocates two reversed invoices against each other
			// no need to record this
			Check.assume(lastPOLine instanceof MInvoiceLine, lastPoLineFact + " is an invoice line");

			// Note: We can't make assumptions on the invoice of 'lastPOLine', unless ProcessImmediately is false
			// If ProcessImmediately is true, then the instance that we can get from the invoice line doen't yet have
			// Status='RE'.
			if (!cand.getCurrentRelPOType().isProcessImmediately())
			{
				Check.assume(
						X_C_Invoice.DOCSTATUS_Reversed.equals(((MInvoiceLine)lastPOLine).getC_Invoice().getDocStatus()),
						"invoice of " + lastPOLine + " is reversed");
			}
			// nothing to do
			return;
		}

		final boolean isAllocReversed = X_C_AllocationHdr.DOCSTATUS_Reversed.equals(allocParentStatus);

		if (mode == RecordAllocLineMode.COMMISSIOM_PAYMENT)
		{
			// Note: for mode == INSTANCE_TRIGGER, we can't make this check because, the same allocationLine can be
			// reevaluated again after a retroactive hierarchy change
			final String typeToLookFor = isAllocReversed ?
					X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg : X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung;

			if (!MCAdvCommissionFact.mkQuery(ctx, trxName)
					.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
					.setTableId(Services.get(IADTableDAO.class).retrieveTableId(I_C_AllocationLine.Table_Name))
					.setRecordId(allocLine.getC_AllocationLine_ID())
					.setFactType(typeToLookFor, false)
					.list()
					.isEmpty())
			{
				CommissionFactRecordAllocationLine.logger.debug("Commission payment allocLine" + allocLine + " has already been recorded");
				return;
			}
		}

		//
		// get the existing C_AllocationLine commission facts

		final List<I_C_AdvCommissionFact> cAllocationLineFacts = retrieveAllocationFacts(ctx, allocLine, instance, mode, trxName);
		final I_C_AdvCommissionFact lastCAllocationLineFact;

		if (cAllocationLineFacts.isEmpty())
		{
			lastCAllocationLineFact = null;
		}
		else
		{
			lastCAllocationLineFact = cAllocationLineFacts.get(cAllocationLineFacts.size() - 1);
		}

		final boolean negateAllocLineAmounts =
				mode == RecordAllocLineMode.COMMISSIOM_PAYMENT
						|| Services.get(IFieldAccessBL.class).isCreditMemo(poLine);

		//
		// find out if 'allocLine' plus the recorded lines have a sufficient allocation for 'poLine's parent.
		BigDecimal allocLinesSum = BigDecimal.ZERO;
		if (!isAllocReversed)
		{
			BigDecimal allocationLineAmt = allocLine.getAmount().add(allocLine.getDiscountAmt().add(allocLine.getWriteOffAmt()));
			if (negateAllocLineAmounts)
			{
				allocationLineAmt = allocationLineAmt.negate();
			}
			allocLinesSum = allocLinesSum.add(allocationLineAmt);
		}
		for (final I_C_AdvCommissionFact cAllocationLineFact : cAllocationLineFacts)
		{
			final MAllocationLine currentAllocationLine = (MAllocationLine)MCAdvCommissionFact.retrievePO(cAllocationLineFact);
			if (currentAllocationLine.get_ID() == allocLine.get_ID())
			{
				continue;
			}
			final boolean currentAllocHdrReversed = currentAllocationLine.getParent().getDocStatus().equals(X_C_AllocationHdr.DOCSTATUS_Reversed);
			if (!currentAllocHdrReversed)
			{
				BigDecimal allocationLineAmt = currentAllocationLine.getAmount().add(currentAllocationLine.getDiscountAmt().add(currentAllocationLine.getWriteOffAmt()));
				if (negateAllocLineAmounts)
				{
					allocationLineAmt = allocationLineAmt.negate();
				}
				allocLinesSum = allocLinesSum.add(allocationLineAmt);
			}
		}

		final BigDecimal grandTotal;
		if (poLine instanceof MOrderLine)
		{
			grandTotal = ((MOrderLine)poLine).getParent().getGrandTotal();
		}
		else
		{
			// Note: we already asserted that poLine is either MOrderLine or MInvoiceLine
			grandTotal = ((MInvoiceLine)poLine).getParent().getGrandTotal();
		}

		final boolean isTrigger =
				grandTotal.compareTo(allocLinesSum) <= 0 // allocation lines must completely cover the 'grandTotal'
						&& allocLinesSum.signum() != 0; // closed and voided orders could have 'grandTotal'=0 and could
														// otherwise be a commission trigger, even if there is no
														// payment (01600)

		//
		// get the status of the most recent "C_AllocationLine" commission fact
		final String lastCAllocationLineFactStatus;
		if (cAllocationLineFacts.isEmpty())
		{
			lastCAllocationLineFactStatus = null;
		}
		else
		{
			lastCAllocationLineFactStatus = lastCAllocationLineFact.getStatus();
		}

		if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
		{
			// lastPoLineFact can have status=STATUS_ZuBerechnen (CP) only if it is an invoice line that came after the
			// allocLine, because in that case we are dealing with prepay order -> allocation -> invoice
			final boolean canRecordAllocLine =
					!X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(lastPoLineFact.getStatus())
							|| poLine instanceof MInvoiceLine && (
							X_C_AdvCommissionFact.FACTTYPE_Aenderung.equals(lastPoLineFact.getFactType())
									|| X_C_AdvCommissionFact.FACTTYPE_Umbuchung.equals(lastPoLineFact.getFactType())
									|| X_C_AdvCommissionFact.FACTTYPE_Rueckgaengig.equals(lastPoLineFact.getFactType()));
			if (!canRecordAllocLine)
			{
				return;
			}
		}
		//
		// is the latest 'poLine' commission fact more recent than the latest 'C_AllocationLine' commission fact?

		final boolean isCAllocationLineMoreRecent;
		if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
		{
			// Note: if 'lastPoLineFact' is merely an 'FACTTYPE_Umbuchung' with 'STATUS_ZuBerechnen', it can be ignored
			isCAllocationLineMoreRecent = lastCAllocationLineFact != null
					&& (lastPoLineFact.get_ID() < lastCAllocationLineFact.getC_AdvCommissionFact_ID() || X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(lastPoLineFact.getStatus()));
		}
		else
		{
			isCAllocationLineMoreRecent = lastCAllocationLineFact != null
					&& lastPoLineFact.getC_AdvCommissionFact_ID() < lastCAllocationLineFact.getC_AdvCommissionFact_ID();
		}

		final RecordAllocLineAction action;

		//
		// 2. decide on the action to take.
		//
		// See
		// http://dewiki908/mediawiki/index.php/US342:_Definition_Provisionsausl%C3%B6ser_%282010070510001145%29#Table_of_different_cases
		// and
		// http://dewiki908/mediawiki/index.php/US030:_QS:_Provisionszahlungen_werden_im_Buchauszug_dokumentiert_%282010101810000053%29#Detailbeschreibung_fachlich
		if (lastCAllocationLineFactStatus == null)
		{
			// cases 9, 10, 11, 12
			if (isTrigger)
			{
				// cases 9, 10
				if (isAllocReversed)
				{
					throw new IllegalAllocLineUpdate("10"); // case 10
				}
				else
				{
					action = RecordAllocLineAction.B; // case 9
				}
			}
			else
			{
				// cases 11, 12
				if (isAllocReversed)
				{
					action = RecordAllocLineAction.G; // case 12
				}
				else
				{
					action = RecordAllocLineAction.D; // case 11
				}
			}
		}
		else if (mode == RecordAllocLineMode.INSTANCE_TRIGGER && lastCAllocationLineFactStatus.equals(X_C_AdvCommissionFact.STATUS_Prognostiziert)
				|| mode == RecordAllocLineMode.COMMISSIOM_PAYMENT && lastCAllocationLineFact.getCommissionAmt().signum() != 0)
		{
			// cases 1, 2, 3, 4
			if (isTrigger)
			{
				// cases 1, 2
				if (isAllocReversed)
				{
					throw new IllegalAllocLineUpdate("1");// case 1
				}
				else
				{
					action = RecordAllocLineAction.B; // case 2
				}
			}
			else
			{
				// cases 3, 4
				if (isAllocReversed)
				{
					action = RecordAllocLineAction.C; // case 3
				}
				else
				{
					action = RecordAllocLineAction.D; // case 4
				}
			}
		}
		else
		{
			Check.assume(
					mode == RecordAllocLineMode.INSTANCE_TRIGGER && lastCAllocationLineFactStatus.equals(X_C_AdvCommissionFact.STATUS_ZuBerechnen)
							|| mode == RecordAllocLineMode.COMMISSIOM_PAYMENT && lastCAllocationLineFact.getCommissionAmt().signum() == 0, "");
			// cases 5, 5a, 6, 6a, 7, 7a, 8, 8a
			if (isTrigger)
			{ // cases 5, 5a, 6, 6a
				if (isAllocReversed)
				{ // cases 5, 5a
					if (isCAllocationLineMoreRecent)
					{
						action = RecordAllocLineAction.C; // case 5
					}
					else
					{
						throw new IllegalAllocLineUpdate("5a"); // case 5a
					}
				}
				else
				{ // cases 6, 6a
					if (isCAllocationLineMoreRecent)
					{ // case 6
						if (lastCAllocationLineFact.getQty().signum() != 0)
						{ // last allocation line did already trigger the commission
							action = RecordAllocLineAction.F;
						}
						else
						{
							action = RecordAllocLineAction.B;
						}
					}
					else
					{
						action = RecordAllocLineAction.B; // case 6a
					}
				}
			}
			else
			{ // cases 7, 7a, 8, 8a
				if (isAllocReversed)
				{ // cases 7, 8
					if (isCAllocationLineMoreRecent)
					{
						action = RecordAllocLineAction.E; // case 7
					}
					else
					{
						action = RecordAllocLineAction.C; // case 8
					}
				}
				else
				{ // cases 7a, 8a
					if (isCAllocationLineMoreRecent)
					{
						throw new IllegalAllocLineUpdate("7a"); // case 7a
					}
					else
					{
						action = RecordAllocLineAction.F; // case 8a
					}
				}
			}
		}
		//
		// 3. Set output values, based on the action that was decided on in part 2
		//

		final I_C_AdvComSystem_Type comSystemType = instance.getC_AdvCommissionTerm().getC_AdvComSystem_Type();
		final ICommissionType type = Services.get(ICommissionTypeBL.class).getBusinessLogic(comSystemType);

		// the commission facts we will add the follow-up info to
		final List<I_C_AdvCommissionFact> factsToFollowUp = new ArrayList<I_C_AdvCommissionFact>();

		// qty will be used with the plusFact and qty.negate will be used with the minusFact
		final BigDecimal qty;
		final BigDecimal commissionPointsSum;
		final BigDecimal commissionPoints;
		final BigDecimal minusCommissionAmt;
		final BigDecimal plusCommissionAmt;
		final PO minusPO;
		final PO plusPO;
		final String plusStatus;
		final String minusStatus;
		final String factType;

		switch (action)
		{
			case B:

				factType = mode == RecordAllocLineMode.INSTANCE_TRIGGER ?
						X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung : X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung;

				if (lastPoLineFact.isCounterEntry())
				{
					if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
					{
						qty = lastPoLineFact.getQty().negate();
						commissionPointsSum = lastPoLineFact.getCommissionPointsSum().negate();
						commissionPoints = lastPoLineFact.getCommissionPoints().negate();
						minusCommissionAmt = null;
						plusCommissionAmt = null;

						factsToFollowUp.add(lastPoLineFact);
					}
					else
					{
						Check.assume(!X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur.equals(lastPoLineFact.getFactType()), lastPoLineFact + " has the correct type");

						qty = null;
						commissionPointsSum = null;
						commissionPoints = null;

						BigDecimal unPaidSum = BigDecimal.ZERO;
						for (final I_C_AdvCommissionFact currentFact : MCAdvCommissionFact.mkQuery(ctx, trxName)
								.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
								.setTableId(poLine.get_Table_ID())
								.setRecordId(poLine.get_ID())
								.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, false)
								.list())
						{
							if (X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung.equals(currentFact.getFactType())
									|| X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg.equals(currentFact.getFactType()))
							{
								continue;
							}

							unPaidSum = unPaidSum.add(currentFact.getCommissionAmt());

							factsToFollowUp.add(currentFact);
						}
						minusCommissionAmt = unPaidSum.negate();
						plusCommissionAmt = BigDecimal.ZERO;
					}
				}
				else
				{
					if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
					{
						qty = lastPoLineFact.getQty();
						commissionPointsSum = lastPoLineFact.getCommissionPointsSum();
						commissionPoints = lastPoLineFact.getCommissionPoints();
						minusCommissionAmt = null;
						plusCommissionAmt = null;

						factsToFollowUp.add(lastPoLineFact);
					}
					else
					{
						qty = null;
						commissionPointsSum = null;
						commissionPoints = null;
						if (X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur.equals(lastPoLineFact.getFactType()))
						{
							final List<MCAdvCommissionFact> secondLastPoLineFacts =
									MCAdvCommissionFact.mkQuery(ctx, trxName)
											.setFollowUpSubstring(Integer.toString(lastPoLineFact.getC_AdvCommissionFact_ID()))
											.setFactType(X_C_AdvCommissionFact.FACTTYPE_ProvisionsabrKorrektur, false)
											.list();
							Check.assume(secondLastPoLineFacts.size() == 1, lastPoLineFact + " has exactly one predecessor with same fact type");

							BigDecimal sumToPay = BigDecimal.ZERO;

							for (final I_C_AdvCommissionFact currentFact : MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
									.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, false)
									.list())
							{
								sumToPay = sumToPay.add(currentFact.getCommissionAmt());
								factsToFollowUp.add(currentFact);
							}

							minusCommissionAmt = sumToPay;
						}
						else
						{
							Check.assume(
									X_C_AdvCommissionFact.FACTTYPE_Provisionsabrechnung.equals(lastPoLineFact.getFactType())
											|| X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg.equals(lastPoLineFact.getFactType()),
									"lastPoLineFact=" + lastPoLineFact + " has the correct factType");

							FactQuery factQuery = MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
									.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, false);

							if (X_C_AdvCommissionFact.FACTTYPE_Provisionsabrechnung.equals(lastPoLineFact.getFactType()))
							{
								factQuery = factQuery
										.setTableId(poLine.get_Table_ID())
										.setRecordId(poLine.get_ID());
							}

							BigDecimal unPaidSum = BigDecimal.ZERO;
							for (final I_C_AdvCommissionFact currentFact : factQuery.list())
							{
								unPaidSum = unPaidSum.add(currentFact.getCommissionAmt());
								factsToFollowUp.add(currentFact);
							}
							minusCommissionAmt = unPaidSum;
						}
						plusCommissionAmt = BigDecimal.ZERO;
					}
				}

				minusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_Prognostiziert : X_C_AdvCommissionFact.STATUS_Auszuzahlen;
				minusPO = lastPOLine;

				plusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_ZuBerechnen : X_C_AdvCommissionFact.STATUS_Auszuzahlen;
				plusPO = allocLine;

				break;
			case C:
				// does a record for allocLine exist?
				final List<MCAdvCommissionFact> existingAllocLineMinusFacts =
						MCAdvCommissionFact.mkQuery(ctx, trxName)
								.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
								.setTableId(allocLine.get_Table_ID())
								.setRecordId(allocLine.get_ID())
								.setCounterRecord(true)
								.list();
				if (!existingAllocLineMinusFacts.isEmpty())
				{
					CommissionFactRecordAllocationLine.logger.debug("Nothing to do");
					return;
				}
				else
				{
					// does any fact for allocLine exist?
					final List<MCAdvCommissionFact> existingAllocLineFacts =
							MCAdvCommissionFact.mkQuery(ctx, trxName)
									.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
									.setTableId(allocLine.get_Table_ID())
									.setRecordId(allocLine.get_ID())
									.list();
					if (existingAllocLineFacts.isEmpty())
					{
						CommissionFactRecordAllocationLine.logger.debug("Nothing to do");
						return;
					}
					else
					{
						factType = mode == RecordAllocLineMode.INSTANCE_TRIGGER ?
								X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg : X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg;

						factsToFollowUp.add(lastPoLineFact);

						qty = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
						commissionPointsSum = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
						commissionPoints = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
						minusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;
						plusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;

						minusPO = allocLine;
						minusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_Prognostiziert : X_C_AdvCommissionFact.STATUS_Auszuzahlen;

						plusPO = null;
						plusStatus = null;
					}
				}
				break;
			case D:

				factType = mode == RecordAllocLineMode.INSTANCE_TRIGGER ?
						X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung : X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung;

				factsToFollowUp.add(lastPoLineFact);

				qty = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;

				commissionPointsSum = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
				commissionPoints = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
				minusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;
				plusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;

				minusPO = null;
				minusStatus = null;

				plusPO = allocLine;
				plusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_Prognostiziert : X_C_AdvCommissionFact.STATUS_Auszuzahlen;

				break;
			case E:

				Check.assume(allocLine != null, "allocLine != null");

				factType = mode == RecordAllocLineMode.INSTANCE_TRIGGER ?
						X_C_AdvCommissionFact.FACTTYPE_ZahlungszuordnRueckg : X_C_AdvCommissionFact.FACTTYPE_Prov_ZahlungszuordnRueckg;

				factsToFollowUp.add(lastCAllocationLineFact);
				if (lastCAllocationLineFact.isCounterEntry())
				{
					if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
					{
						qty = lastCAllocationLineFact.getQty().negate();
						commissionPointsSum = lastCAllocationLineFact.getCommissionPointsSum().negate();
						commissionPoints = lastCAllocationLineFact.getCommissionPoints().negate();
						minusCommissionAmt = null;
						plusCommissionAmt = null;
					}
					else
					{
						qty = null;
						commissionPointsSum = null;
						commissionPoints = null;
						minusCommissionAmt = BigDecimal.ZERO;

						// lastCAllocationLineFact has 'commissionAmt'==0. Therefore we use lastPOLineFact
						Check.assume(!lastPoLineFact.isCounterEntry(), lastPoLineFact + " has IsCounterEntry='N'");
						plusCommissionAmt = lastPoLineFact.getCommissionAmt();

					}
				}
				else
				{
					if (mode == RecordAllocLineMode.INSTANCE_TRIGGER)
					{
						qty = lastCAllocationLineFact.getQty();
						commissionPointsSum = lastCAllocationLineFact.getCommissionPointsSum();
						commissionPoints = lastCAllocationLineFact.getCommissionPoints();
						minusCommissionAmt = null;
						plusCommissionAmt = null;
					}
					else
					{
						qty = null;
						commissionPointsSum = null;
						commissionPoints = null;
						minusCommissionAmt = BigDecimal.ZERO;

						// lastCAllocationLineFact has 'commissionAmt'==0.We use lastPOLineFact
						Check.assume(lastPoLineFact.isCounterEntry(), lastPoLineFact + " has IsCounterEntry='Y'");
						plusCommissionAmt = lastPoLineFact.getCommissionAmt().negate();
					}
				}

				minusPO = allocLine;
				minusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_ZuBerechnen : X_C_AdvCommissionFact.STATUS_Auszuzahlen;

				plusPO = lastPOLine;
				plusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_Prognostiziert : X_C_AdvCommissionFact.STATUS_Auszuzahlen;

				break;
			case F:
				final List<MCAdvCommissionFact> existingAllocLinePlusFacts =
						MCAdvCommissionFact.mkQuery(ctx, trxName)
								.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
								.setTableId(allocLine.get_Table_ID())
								.setRecordId(allocLine.get_ID())
								.setCounterRecord(false)
								.list();
				if (!existingAllocLinePlusFacts.isEmpty())
				{
					CommissionFactRecordAllocationLine.logger.debug("Nothing to do");
					return;
				}
				else
				{
					factType = mode == RecordAllocLineMode.INSTANCE_TRIGGER ?
							X_C_AdvCommissionFact.FACTTYPE_Zahlungszuordnung : X_C_AdvCommissionFact.FACTTYPE_Prov_Zahlungszuordnung;

					factsToFollowUp.add(lastPoLineFact);

					qty = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;

					commissionPointsSum = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
					commissionPoints = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? BigDecimal.ZERO : null;
					minusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;
					plusCommissionAmt = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? BigDecimal.ZERO : null;

					minusPO = null;
					minusStatus = null;

					plusPO = allocLine;
					plusStatus = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? X_C_AdvCommissionFact.STATUS_ZuBerechnen : X_C_AdvCommissionFact.STATUS_Auszuzahlen;
				}
				break;
			case G:
				CommissionFactRecordAllocationLine.logger.debug("Nothing to do");
				return;
			default:
				throw new AssertionError();
		}

		//
		// 4. Create commission facts, using the values set in part 3
		//

		Check.assume(minusPO != null || plusPO != null, "At least one of minusPO and plusPO is != null");
		CommissionFactRecordAllocationLine.logger.debug("Recording " + allocLine + " within " + instance);

		final MCAdvCommissionFact newMinusFact;

		final BigDecimal commissionPercent = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? lastPoLineFact.getCommission() : null;
		final BigDecimal amtMultiplier = mode == RecordAllocLineMode.COMMISSIOM_PAYMENT ? lastPoLineFact.getAmtMultiplier() : null;
		final BigDecimal commissionPointsBase = mode == RecordAllocLineMode.INSTANCE_TRIGGER ? lastPoLineFact.getCommissionPointsBase() : null;

		final String changeReason = "Action=" + action;

		if (minusPO != null)
		{
			final BigDecimal minusCommissionPointsSum = commissionPointsSum == null ? null : commissionPointsSum.negate();
			final BigDecimal minusCommissionPoints = commissionPoints == null ? null : commissionPoints.negate();
			final BigDecimal minusQty = qty == null ? null : qty.negate();
			newMinusFact =
					factBL.createFact(type, cand, minusPO, instance,
							commissionPointsBase,
							minusQty,
							minusCommissionPointsSum,
							commissionPercent,
							minusCommissionPoints,
							factType, changeReason, adPInstanceId);

			// 01513: if 'newMinusFact' refers to the allocation line, it has no CommissionAmtBase value.
			// In this case, we use the plusFacts's CommissionAmtBase.
			if (minusPO == allocLine)
			{
				setFactCommissionAmtBase(newMinusFact, poLine);
			}

			newMinusFact.setAmtMultiplier(amtMultiplier);
			newMinusFact.setC_Currency_ID(lastPoLineFact.getC_Currency_ID());
			newMinusFact.setCommissionAmt(minusCommissionAmt == null ? null : minusCommissionAmt.negate());
			newMinusFact.setStatus(minusStatus);
			newMinusFact.setDateFact(factBL.retrieveDateFact(allocLine));
			newMinusFact.setIsCounterEntry(true);

			newMinusFact.saveEx();

			if (Services.get(ISysConfigBL.class).getBooleanValue("de.metas.adempiere.debug", false))
			{
				final String debugMsg =
						mkDebugMsgForMinusFact(
								mode, poLineFacts, lastPoLineFact, isAllocReversed, cAllocationLineFacts, lastCAllocationLineFact, isTrigger, isCAllocationLineMoreRecent, factsToFollowUp);
				chatInfoForPO(newMinusFact, debugMsg);
			}
		}
		else
		{
			newMinusFact = null;
		}

		final MCAdvCommissionFact newPlusFact;
		if (plusPO != null)
		{
			newPlusFact =
					factBL.createFact(type, cand, plusPO, instance,
							commissionPointsBase,
							qty,
							commissionPointsSum,
							commissionPercent,
							commissionPoints,
							factType, changeReason, adPInstanceId);

			// 01513: if 'newPlusFact' refers to the allocation line, it has no CommissionAmtBase value.
			// In this case, we use the minusFact's CommissionAmtBase.
			if (plusPO == allocLine)
			{
				setFactCommissionAmtBase(newPlusFact, poLine);
			}

			newPlusFact.setAmtMultiplier(amtMultiplier);
			newPlusFact.setC_Currency_ID(lastPoLineFact.getC_Currency_ID());
			newPlusFact.setCommissionAmt(plusCommissionAmt);
			newPlusFact.setStatus(plusStatus);
			newPlusFact.setDateFact(factBL.retrieveDateFact(allocLine));
			newPlusFact.setIsCounterEntry(false);
			newPlusFact.saveEx();
		}
		else
		{
			newPlusFact = null;
		}

		//
		// update the followUpInfo between lastPoLineFact, newMinusFact and newPlusFact.
		if (minusPO != null)
		{
			// minus !=null, plus != null
			// minus !=null, plus == null
			for (final I_C_AdvCommissionFact factToFollowUp : factsToFollowUp)
			{
				MCAdvCommissionFact.addFollowUpInfo(factToFollowUp, newMinusFact);
				InterfaceWrapperHelper.save(factToFollowUp);
			}
			if (plusPO != null)
			{
				newMinusFact.addFollowUpInfo(newPlusFact);
				newMinusFact.saveEx();
			}
		}
		else if (newPlusFact != null)
		{
			// minus == null, plus != null
			for (final I_C_AdvCommissionFact factToFollowUp : factsToFollowUp)
			{
				MCAdvCommissionFact.addFollowUpInfo(factToFollowUp, newPlusFact);
				InterfaceWrapperHelper.save(factToFollowUp);
			}
		}
		else
		{
			// minus ==null, plus!=null
			// no followup-info to add
		}
	}

	private String mkDebugMsgForMinusFact(
			final RecordAllocLineMode mode,
			final List<MCAdvCommissionFact> poLineFacts,
			final MCAdvCommissionFact lastPoLineFact,
			final boolean isAllocReversed,
			final List<I_C_AdvCommissionFact> cAllocationLineFacts,
			final I_C_AdvCommissionFact lastCAllocationLineFact,
			final boolean isTrigger,
			final boolean isCAllocationLineMoreRecent,
			final List<I_C_AdvCommissionFact> factsToFollowUp)
	{
		final StringBuilder debugMsg = new StringBuilder();

		debugMsg.append("mode=" + mode);
		debugMsg.append(";\nIsAllocreversed=" + isAllocReversed);
		debugMsg.append(";\nIsTrigger=" + isTrigger);
		debugMsg.append(";\nlastPoLineFact-ID=" + lastPoLineFact.get_ID());

		debugMsg.append(";\npoLineFacts=");
		for (final I_C_AdvCommissionFact fact : poLineFacts)
		{
			debugMsg.append("\n\t" + fact);
		}

		debugMsg.append("\nlastCAllocationLineFact-ID=" + (lastCAllocationLineFact == null ? 0 : lastCAllocationLineFact.getC_AdvCommissionFact_ID()));
		debugMsg.append(";\nIsCAllocationLineMoreRecent=" + isCAllocationLineMoreRecent);

		debugMsg.append(";\ncAllocationLineFacts=");
		for (final I_C_AdvCommissionFact fact : cAllocationLineFacts)
		{
			debugMsg.append("\n\t" + fact);
		}
		debugMsg.append("\nfactsToFollowUp=" + factsToFollowUp);

		return debugMsg.toString();
	}

	/**
	 * For the given allocLine and instance this method returns a list of facts which refer to an allocation line that belongs to the same C_Invoice or C_Order as the given allocLine.
	 * 
	 * @param ctx
	 * @param allocLine
	 * @param instance
	 * @param mode
	 * @param trxName
	 * @return
	 */
	private List<I_C_AdvCommissionFact> retrieveAllocationFacts(
			final Properties ctx,
			final MAllocationLine allocLine,
			final IAdvComInstance instance,
			final RecordAllocLineMode mode,
			final String trxName)
	{
		// If we are recording an allocation line for an instance trigger, we are interested in facts with
		// status!=ToPay.
		// If the allocation line belongs to a commission invoice, we are interested only in fact with status=ToPay.
		final boolean negateStatusToPay = mode == RecordAllocLineMode.INSTANCE_TRIGGER;

		//
		// First we search for any fact referencing a C_AllocationLine. This also includes facts referencing different
		// allocation lines and those lines might in turn reference different documents.
		final List<MCAdvCommissionFact> allAllocationLineFacts =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setTableId(allocLine.get_Table_ID())
						.setStatus(X_C_AdvCommissionFact.STATUS_Auszuzahlen, negateStatusToPay)
						.list();

		if (allAllocationLineFacts.isEmpty())
		{
			return Collections.emptyList(); // search is over
		}

		//
		// no we define a fact finder to examine 'instance' and search for existing facts that reference an allocation
		// line belonging to the order or invoice as 'allocLine'.
		final PrecedingFactFinder factFinder = new PrecedingFactFinder()
		{
			@Override
			public boolean isOK(final I_C_AdvCommissionFact fact)
			{
				Check.assume(fact.getC_AdvCommissionInstance_ID() == instance.getC_AdvCommissionInstance_ID(), "");
				if (fact.getAD_Table_ID() != allocLine.get_Table_ID())
				{
					return false;
				}

				if (mode == RecordAllocLineMode.INSTANCE_TRIGGER && X_C_AdvCommissionFact.STATUS_Auszuzahlen.equals(fact.getStatus()))
				{
					return false;
				}
				if (mode == RecordAllocLineMode.COMMISSIOM_PAYMENT && !X_C_AdvCommissionFact.STATUS_Auszuzahlen.equals(fact.getStatus()))
				{
					return false;
				}

				if (fact.getRecord_ID() == allocLine.getC_AllocationLine_ID())
				{
					return true;
				}

				final MAllocationLine factAllocLine = (MAllocationLine)MCAdvCommissionFact.retrievePO(fact);
				if (factAllocLine.getC_Invoice_ID() != allocLine.getC_Invoice_ID())
				{
					return false;
				}
				if (factAllocLine.getC_Order_ID() != allocLine.getC_Order_ID())
				{
					return false;
				}

				return true;
			}
		};

		final List<I_C_AdvCommissionFact> result = new ArrayList<I_C_AdvCommissionFact>();

		// Note: we don't use PrecedingFactFinder.retrievePreceeding, because the fact we are looking for might not be
		// reachable using preceding facts from the most recent element of 'allAllocationLineFacts'
		for (final MCAdvCommissionFact fact : allAllocationLineFacts)
		{
			if (factFinder.isOK(fact))
			{
				result.add(fact);
			}
		}

		return result;
	}

	void setPlusFactCommissionAmtBase(
			final MAllocationLine allocLine,
			final PO plusPO,
			final MCAdvCommissionFact newMinusFact,
			final MCAdvCommissionFact newPlusFact)
	{
		if (plusPO == allocLine && newMinusFact != null)
		{
			Check.assume(newPlusFact.getCommissionAmtBase().signum() == 0, "newPlusFact has CommissionAmtBase=0");
			newPlusFact.setCommissionAmtBase(newMinusFact.getCommissionAmtBase());
		}
	}

	void setFactCommissionAmtBase(
			final MCAdvCommissionFact newFact,
			final PO poLine)
	{
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);
		final BigDecimal lineNetAmt = faBL.getLineNetAmtOrNull(poLine);
		Check.assume(lineNetAmt != null, poLine + " has a LineNetAmt != null");
		newFact.setCommissionAmtBase(lineNetAmt);
	}

	private void chatInfoForPO(final Object model, final String info)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);

		final MChat chat = new MChat(po.getCtx(), po.get_Table_ID(), po.get_ID(), null, po.get_TrxName());
		chat.saveEx();
		final MChatEntry entry = new MChatEntry(chat, getClass().getSimpleName() + " - " + info);
		entry.saveEx();
	}

	private class IllegalAllocLineUpdate extends CommissionException
	{
		private static final long serialVersionUID = 5811189860027155014L;

		private IllegalAllocLineUpdate(final String caseNo)
		{
			super("Unexpected " + caseNo + ". See http://dewiki908/mediawiki/index.php/US342:_Definition_Provisionsausl%C3%B6ser_%282010070510001145%29#Table_of_different_cases");
		}
	}

	private static enum RecordAllocLineMode
	{
		/**
		 * The allocation line belongs to a commission trigger (i.e. sales order or sales invoice).
		 */
		INSTANCE_TRIGGER,

		/**
		 * The allocation line belongs to a commission/salary invoice
		 */
		COMMISSIOM_PAYMENT
	}

	private static enum RecordAllocLineAction
	{
		/**
		 * <ul>
		 * <li>poLine' forecast/minus with points
		 * <li>'allocLine' toCalculate/plus with points.
		 */
		B,

		/**
		 * <ul>
		 * <li>If 'allocLine' minus record already exists: nothing to do.
		 * <li>If no 'allocLine' record exists: nothing to do.
		 * <li>Else: 'allocLine' forecast/minus without points.
		 */
		C,

		/**
		 * <li>'allocLine' forecast/plus without points.
		 */
		D,

		/**
		 * <ul>
		 * <li>'allocLine' toCalculate/minus with points.
		 * <li>'poLine' forecast/plus with points.
		 */
		E,

		/**
		 * <ul>
		 * <li>If 'allocLine' plus record exists: nothing to do
		 * <li>Else: 'allocLine' toCalculate/plus without points
		 */
		F,

		/**
		 * <li>Nothing to do
		 */
		G
	}

}
