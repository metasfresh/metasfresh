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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.processing.exception.ProcessingException;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.exception.CommissionException;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_IncidentLine;
import de.metas.commission.model.I_C_IncidentLineFact;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.model.MCIncidentLine;
import de.metas.commission.model.MCIncidentLineFact;
import de.metas.commission.model.MCSponsorCond;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.IComRelevantPoBL;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionTypeBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.Messages;
import de.metas.document.engine.IDocument;
import de.metas.i18n.Msg;
import de.metas.logging.MetasfreshLastError;
import de.metas.prepayorder.service.IPrepayOrderBL;

public class ComRelevantPoBL implements IComRelevantPoBL
{
	private static final Logger logger = LogManager.getLogger(ComRelevantPoBL.class);

	public static final String MSG_INACTIVE_COMMISSION_TYPE_2P = "RelevantPODefRefsInactiveComType_2P";

	/**
	 * Message indicates that a PO may not be deleted, because it is referenced from the commission facts.
	 */
	public static final String MSG_PO_REFERENCED_1P = "PoStillReferenced_1P";

	@Override
	public void recordIncident(final MCAdvCommissionFactCand candidate)
	{
		final MBPartner bPartner = candidate.retrieveBPartner();

		//
		// record the po in the commission incident tables
		// also, if they are commission-triggers, add them
		final IFieldAccessBL faBL = Services.get(IFieldAccessBL.class);
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(candidate);

		if (I_C_Sponsor_SalesRep.Table_Name.equals(po.get_TableName()))
		{
			return;
		}

		for (final Object poLine : faBL.retrieveLines(po, false))
		{
			retrieveOrCreateRelevant(bPartner, candidate, poLine);
		}
	}

	@Override
	public void invokeCommissionType(
			final MCAdvCommissionFactCand candidate,
			final boolean documentError,
			final int adPInstanceID)
	{
		Check.assume(!candidate.isInfo(), candidate + " has isInfo='N'");

		final MCAdvComRelevantPOType relPOType = candidate.getCurrentRelPOType();
		Check.assume(relPOType.isProcessImmediately() || adPInstanceID > 0,
				"If relPOType.isProcessImmediately()=false, then adPInstanceID is > 0");

		try
		{
			final MCAdvComSystemType comSystemType = (MCAdvComSystemType)relPOType.getC_AdvComSystem_Type();

			if (!isSameComSystem(comSystemType, candidate, false))
			{
				// nothing to do
				return;
			}

			if (!comSystemType.isActive())
			{
				final String msg = Msg.getMsg(
						relPOType.getCtx(),
						ComRelevantPoBL.MSG_INACTIVE_COMMISSION_TYPE_2P,
						new Object[] { relPOType.getC_AdvCommissionRelevantPO().getName(), comSystemType.getName() });
				throw CommissionException.inconsistentConfig(msg, relPOType);
			}

			checkIfPrecedingPOIsProcessed(candidate, documentError, adPInstanceID, relPOType.isProcessImmediately());

			final ICommissionType businessLogic = Services.get(ICommissionTypeBL.class).getBusinessLogic(comSystemType);
			businessLogic.evaluateCandidate(candidate, X_C_AdvComSalesRepFact.STATUS_Prognose, adPInstanceID);
		}
		catch (final Throwable t)
		{
			final String summary = "Error processing '" + candidate + "' and '" + relPOType + "':" + t.getClass().getName() + " with Msg '" + t.getMessage() + "'";
			handleError(candidate, documentError, adPInstanceID, t, summary);
		}
	}

	private void checkIfPrecedingPOIsProcessed(
			final MCAdvCommissionFactCand candidate,
			final boolean documentError,
			final int adPInstanceID,
			final boolean immediate)
	{
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(candidate);

		final Map<Integer, MOrder> precedingOrders = new HashMap<Integer, MOrder>();
		final Map<Integer, MInvoice> precedingInvoices = new HashMap<Integer, MInvoice>();

		// 1.
		// get our preceeding pos
		if (po instanceof MOrder)
		{
			// nothing to do, there are no preceeding pos
		}
		else if (po instanceof MInvoice)
		{
			for (final MInvoiceLine il : ((MInvoice)po).getLines())
			{
				final MOrderLine ol = (MOrderLine)il.getC_OrderLine();

				if (ol != null && !precedingOrders.containsKey(ol.getC_Order_ID()))
				{
					precedingOrders.put(ol.getC_Order_ID(), ol.getParent());
				}
			}
		}
		else if (po instanceof MAllocationHdr)
		{
			for (final MAllocationLine al : ((MAllocationHdr)po).getLines(false))
			{
				if (al.getC_Order_ID() > 0 && !precedingOrders.containsKey(al.getC_Order_ID()))
				{
					if (Services.get(IPrepayOrderBL.class).isPrepayOrder(candidate.getCtx(), al.getC_Order_ID(), candidate.get_TrxName()))
					{
						precedingOrders.put(al.getC_Order_ID(), (MOrder)al.getC_Order());
					}
				}

				if (!precedingInvoices.containsKey(al.getC_Invoice_ID()))
				{
					precedingInvoices.put(al.getC_Invoice_ID(), (MInvoice)al.getC_Invoice());
				}
			}
		}

		// 2.
		// check if there were problems processing them
		iteratePrecedingPOsCheckForError(candidate, documentError, adPInstanceID, immediate, precedingOrders);
		iteratePrecedingPOsCheckForError(candidate, documentError, adPInstanceID, immediate, precedingInvoices);
	}

	private void iteratePrecedingPOsCheckForError(
			final MCAdvCommissionFactCand candidate,
			final boolean documentError,
			final int adPInstanceID,
			final boolean immediate,
			final Map<Integer, ? extends PO> preceedingOrders)
	{
		for (final PO preceedingOrder : preceedingOrders.values())
		{
			final List<MCAdvCommissionFactCand> preceedingCands = MCAdvCommissionFactCand.retrieveForPO(preceedingOrder, candidate);
			if (preceedingCands.isEmpty())
			{
				continue;
			}
			final MCAdvCommissionFactCand lastPrecedingCand = preceedingCands.get(preceedingCands.size() - 1);

			final boolean processed = immediate && lastPrecedingCand.isImmediateProcessingDone()
					|| !immediate && lastPrecedingCand.isSubsequentProcessingDone();

			if (!processed)
			{
				// Preceding candidate has not been processed. Usually this means that an error has occurred
				final String summary = "Can't process '" + candidate + "', because preceding '" + lastPrecedingCand + "' has not been processed yet";
				handleError(candidate, documentError, adPInstanceID, null, summary);
			}
		}
	}

	private void handleError(
			final MCAdvCommissionFactCand candidate,
			final boolean documentError,
			final int adPInstanceID,
			final Throwable t,
			final String summary)
	{
		final ProcessingException ape = new ProcessingException(summary, t, adPInstanceID);
		if (documentError)
		{
			Services.get(IProcessingService.class).handleProcessingException(candidate.getCtx(), candidate, ape, candidate.getTrxName());
		}
		else
		{
			throw ape;
		}
	}

	@Override
	public boolean isSameComSystem(
			final I_C_AdvComSystem_Type comSystemType,
			final MCAdvCommissionFactCand candidate,
			final boolean throwEx)
	{
		final MBPartner customer = candidate.retrieveBPartner();
		if (customer == null)
		{
			if (throwEx)
			{
				throw CommissionException.inconsistentConfig(candidate + " has no BPartner", candidate);
			}
			else
			{
				return true;
			}
		}

		final I_C_Sponsor customerSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(customer, false);
		if (customerSponsor == null)
		{
			// Our customer is not part of the hierarchy (at least not now ;-) )!
			// Therefore the commission system won't do anything with the candidate
			return false;
		}

		// Using the candidate's DateAcct to retrieve the contract (not the candidate's cause's DateAcct),
		// because the causes's date might be in the past, when the customerSponsor didn't have a contract.
		// Example: 'cause' is a C_Sponsor_SalesRepFact, and required all later commission triggers to be reevaluated;
		// in that case, the triggers have a later date and there might be younger child sponsors related to these triggers.
		final Timestamp date = candidate.getDateAcct();

		Check.assume(date != null, "DateAcct of " + candidate + " is not null");

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		final ICommissionContext comCtx = Services.get(ICommissionContextFactory.class).create(customerSponsor, date, null, null);

		final I_C_AdvComSystem comSystem = sponsorBL.retrieveComSystem(comCtx);
		if (comSystem == null)
		{
			// the sponsor has no commission system
			return false;
		}

		if (comSystem.getC_AdvComSystem_ID() != comSystemType.getC_AdvComSystem_ID())
		{
			logger.debug("Customer sponsor  " + customerSponsor.getSponsorNo() + " belongs to " + comSystem + "; Nothing to do for " + comSystemType);
			return false;
		}
		return true;
	}

	@Override
	public boolean isRelevantInvoiceLine(final I_C_Invoice invoice, final MInvoiceLine il, final ICommissionType type)
	{
		if (il.isDescription())
		{
			return false;
		}

		final String docStatus = invoice.getDocStatus();

		final boolean completeOrReversed = IDocument.STATUS_Completed.equals(docStatus) || IDocument.STATUS_Reversed.equals(docStatus);
		return completeOrReversed;
	}

	@Override
	public boolean hasCommissionPoints(final PO ilOrOl)
	{
		final IFieldAccessBL faService = Services.get(IFieldAccessBL.class);

		final BigDecimal commissionPoints = faService.getCommissionPoints(ilOrOl, false);

		return commissionPoints != null && commissionPoints.signum() != 0;
	}

	private List<I_C_IncidentLineFact> retrieveOrCreateRelevant(
			final MBPartner bPartner,
			final MCAdvCommissionFactCand candidate,
			final Object poLine)
	{
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(candidate);
		final Timestamp dateDoc = candidate.getDateAcct();

		final List<I_C_IncidentLineFact> facts = new ArrayList<I_C_IncidentLineFact>();

		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_OrderLine.class))
		{
			final I_C_IncidentLine incident = MCIncidentLine.retrieveOrCreate(poLine);

			facts.add(MCIncidentLineFact.retrieveOrCreate(poLine, bPartner, candidate, dateDoc, incident));
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_InvoiceLine.class))
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(poLine, I_C_InvoiceLine.class);
			final I_C_Invoice invoice = il.getC_Invoice();

			if (Constants.DOCBASETYPE_AEInvoice.equals(invoice.getC_DocType().getDocBaseType()))
			{
				// wrong configuration of relevant POs
				throw new IllegalArgumentException("Can't handle employee invoice line " + poLine);
			}
			else
			{
				final I_C_IncidentLine incidentLine;

				final MOrderLine ol = (MOrderLine)il.getC_OrderLine();
				if (ol == null || ol.getC_OrderLine_ID() == 0)
				{
					incidentLine = MCIncidentLine.retrieveOrCreate(il);
				}
				else
				{
					// MCIncidentLine.retrieve should be sufficient, but it is
					// possible to arrive at an order with DateOrdered after the
					// invoice date in this case, there might be no incident for
					// the order
					incidentLine = MCIncidentLine.retrieveOrCreate(ol);
				}
				facts.add(MCIncidentLineFact.retrieveOrCreate(il, bPartner, candidate, dateDoc, incidentLine));
			}
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_M_InOutLine.class))
		{
			final MInOut io = (MInOut)po;

			final String docStatus = io.getDocStatus();

			final boolean completeOrReversed = IDocument.STATUS_Completed.equals(docStatus)
					|| IDocument.STATUS_Reversed.equals(docStatus);

			if (completeOrReversed)
			{

				final MInOutLine iol = (MInOutLine)poLine;
				final MOrderLine ol = (MOrderLine)iol.getC_OrderLine();

				if (ol == null || ol.getC_OrderLine_ID() == 0)
				{
					final I_C_IncidentLine progress = MCIncidentLine.retrieveOrCreate(ol);
					facts.add(MCIncidentLineFact.retrieveOrCreate(iol, bPartner, candidate, dateDoc, progress));
				}
			}
		}
		else if (poLine instanceof MAllocationLine)
		{

		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AllocationHdr.class))
		{
			Check.assumeNull(bPartner, "Param 'bPartner' is null");

			for (final I_C_AllocationLine allocLine : Services.get(IAllocationDAO.class).retrieveLines(InterfaceWrapperHelper.create(poLine, I_C_AllocationHdr.class)))
			{
				if (allocLine.getC_Order_ID() > 0)
				{
					final MOrder order = (MOrder)allocLine.getC_Order();
					for (final MOrderLine ol : order.getLines())
					{
						final I_C_IncidentLine progress = MCIncidentLine.retrieveOrCreate(ol);
						facts.add(MCIncidentLineFact.retrieveOrCreate(allocLine, (MBPartner)order.getC_BPartner(), candidate, dateDoc, progress));
					}
				}
				if (allocLine.getC_Invoice_ID() > 0)
				{
					final MInvoice invoice = (MInvoice)allocLine.getC_Invoice();

					for (final MInvoiceLine il : invoice.getLines())
					{
						final I_C_IncidentLine progress = MCIncidentLine.retrieveOrCreate(il);
						facts.add(MCIncidentLineFact.retrieveOrCreate(allocLine, (MBPartner)invoice.getC_BPartner(), candidate, dateDoc, progress));
					}
				}
			}
		}
		else if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_AdvComSalesRepFact.class))
		{

		}
		else if (poLine instanceof MCAdvCommissionFact)
		{

		}
		else if (poLine instanceof MCAdvComDoc)
		{

		}
		else if (poLine instanceof MCSponsorCond)
		{

		}
		else
		{
			throw new IllegalArgumentException("Can't handle " + poLine);
		}
		return facts;
	}

	// private boolean isRelevantReceipt(final I_C_Payment payment)
	// {
	//
	// if (!DocAction.STATUS_Completed.equals(payment.getDocStatus())
	// && !DocAction.STATUS_Reversed.equals(payment.getDocStatus()))
	// {
	// return false;
	// }
	// return X_C_DocType.DOCBASETYPE_ARReceipt.equals(payment.getC_DocType().getDocBaseType());
	// }

	@Override
	public boolean validatePOAfterNewOrChange(final PO po)
	{
		final MCAdvCommissionRelevantPO relevantPODef = MCAdvCommissionRelevantPO.retrieveIfRelevant(po);

		if (relevantPODef == null)
		{
			// nothing else to do, just return the positive invocation result
			return true;
		}

		final List<MCAdvComRelevantPOType> relPOTypes = MCAdvComRelevantPOType.retrieveFor(relevantPODef);
		boolean allProcessedImmediately = true;

		final MCAdvCommissionFactCand candidate = MCAdvCommissionFactCand.createOrUpdate(po, relevantPODef);

		for (final MCAdvComRelevantPOType relPOType : relPOTypes)
		{
			final boolean processNow = relPOType.isProcessImmediately();

			if (processNow)
			{
				try
				{
					processNow(po, candidate, relPOType);
				}
				catch (final RuntimeException e)
				{
					MetasfreshLastError.saveError(logger, "Error processing " + po, e);
					return false;
				}
			}
			else
			{
				// a least one MCAdvComRelevantPOType is to be evaluated later
				allProcessedImmediately = false;
			}
		}
		if (allProcessedImmediately)
		{
			candidate.setIsSubsequentProcessingDone(true);
		}
		candidate.setIsImmediateProcessingDone(true);
		candidate.saveEx();
		return true;
	}

	private void processNow(final PO po, final MCAdvCommissionFactCand candidate, final MCAdvComRelevantPOType relPOType)
	{
		logger.info("Immediately processing " + po + " (due to " + relPOType + ")");

		final IComRelevantPoBL comRelevantPoBL = Services.get(IComRelevantPoBL.class);

		comRelevantPoBL.recordIncident(candidate);

		if (!candidate.isInfo())
		{
			candidate.setCurrentRelPOType(relPOType);
			comRelevantPoBL.invokeCommissionType(candidate, true, IProcessingService.NO_AD_PINSTANCE_ID);
			candidate.setCurrentRelPOType(null);
		}
	}

	@Override
	public void validatePOBeforeDelete(final PO po)
	{
		if (po == null)
		{
			return; // nothing to do
		}
		
		final MCAdvCommissionRelevantPO relevantPODef = MCAdvCommissionRelevantPO.retrieveIfRelevant(po);
		if (relevantPODef != null)
		{
			throw new CommissionException(Messages.RELEVANT_PO_DELETION_FORBIDDEN_1P, po.toString());
		}

		if (MCAdvCommissionFact.isFactExists(po))
		{
			throw new CommissionException(MSG_PO_REFERENCED_1P, po.toString());
		}
	}
}
