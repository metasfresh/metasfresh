package de.metas.edi.api.impl;

/*
 * #%L
 * de.metas.edi
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Invoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.aggregation.api.IAggregation;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.api.ValidationState;
import de.metas.edi.exception.EDIFillMandatoryException;
import de.metas.edi.exception.EDIMissingDependencyException;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_BPartner_Location;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.process.export.IExport;
import de.metas.edi.process.export.impl.C_InvoiceExport;
import de.metas.edi.process.export.impl.EDI_DESADVExport;
import de.metas.edi.process.export.impl.M_InOutExport;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutDAO;
import de.metas.invoicecandidate.api.IInvoiceAggregationFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderDAO;
import de.metas.purchasing.api.IBPartnerProductDAO;

public class EDIDocumentBL implements IEDIDocumentBL
{
	private static final String ERR_NotExistsShipmentForOrderError = "NotExistsShipmentForOrderError";

	@Override
	public boolean updateEdiEnabled(final I_EDI_Document_Extension document)
	{
		// EDI applies only for customer invoices and shipments
		if (!document.isSOTrx())
		{
			document.setIsEdiEnabled(false); // 08619: don't assume that the flag is already false from the beginning, but make sure it is false now
			return document.isEdiEnabled();
		}

		// task 05721: Set isEDIEnabled to false and disable the button for reversals
		if (X_C_Invoice.DOCSTATUS_Reversed.equals(document.getDocStatus()) || document.getReversal_ID() > 0)
		{
			document.setIsEdiEnabled(false);
			return document.isEdiEnabled();
		}

		return document.isEdiEnabled();
		// final I_C_BPartner bpartner = InterfaceWrapperHelper.create(document.getC_BPartner(), I_C_BPartner.class);
		// if (bpartner == null || bpartner.getC_BPartner_ID() <= 0)
		// {
		// // BPartner was not set yet, nothing to do
		// return document.isEdiEnabled();
		// }
		//
		// document.setIsEdiEnabled(bpartner.isEdiRecipient());
		// return bpartner.isEdiRecipient();
	}

	@Override
	public List<Exception> isValidInvoice(final I_C_Invoice invoice)
	{
		final List<Exception> feedback = new ArrayList<>();
		final String EDIStatus = invoice.getEDI_ExportStatus();
		if (!invoice.isEdiEnabled() && !I_EDI_Document.EDI_EXPORTSTATUS_Invalid.equals(EDIStatus))
		{
			return feedback;
		}

		feedback.addAll(isValidPartner(invoice.getC_BPartner()));
		feedback.addAll(isValidBPLocation(invoice.getC_BPartner_Location()));

		// TODO not used right now
		// final IBPartnerOrgBL bpOrgBL = Services.get(IBPartnerOrgBL.class);
		// final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		// final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		//
		// final I_AD_Org org = InterfaceWrapperHelper.create(ctx, invoice.getAD_Org_ID(), I_AD_Org.class, trxName);
		//
		// final org.compiere.model.I_C_BPartner orgBP = bpOrgBL.retrieveLinkedBPartner(org);
		// feedback.addAll(isValidPartner(orgBP));
		//
		// final org.compiere.model.I_C_BPartner_Location orgBPLocation = bpOrgBL.retrieveOrgBPLocation(ctx, org.getAD_Org_ID(), trxName);
		// if (orgBPLocation.isRemitTo() && orgBPLocation.isActive())
		// {
		// feedback.addAll(isValidBPLocation(orgBPLocation));
		// }

		// task 09182: for return material credit memos, we don't have or need an (imported) EDI ORDERS PoReference
		// task 09811: guard against NPE when invoice is not yet completed and therefore doesn'T yet have a docType
		final I_C_DocType docType = invoice.getC_DocType_ID() > 0
				? invoice.getC_DocType()
				: invoice.getC_DocTypeTarget();

		final boolean invoiceIsRMCreditMemo = docType != null
				&& Services.get(IInvoiceBL.class).isCreditMemo(docType.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_GS_Retoure.equals(docType.getDocSubType());

		if (invoice.getC_Order_ID() <= 0 && !invoiceIsRMCreditMemo)
		{
			// an order must be linked to an invoice for successful EDI export
			feedback.add(new EDIFillMandatoryException(null, null, org.compiere.model.I_C_Invoice.COLUMNNAME_C_Order_ID));
		}

		// an invoice order must have AT LEAST one M_InOut for successful EDI export
		if (invoice.getC_Order_ID() > 0 // to avoid NPE in OrderDAO impl
				&& !invoiceIsRMCreditMemo)
		{
			final org.compiere.model.I_C_Order order = invoice.getC_Order();
			final boolean hasInOuts = Services.get(IOrderDAO.class).hasInOuts(order);
			if (!hasInOuts)
			{
				feedback.add(new EDIMissingDependencyException(EDIDocumentBL.ERR_NotExistsShipmentForOrderError,
						org.compiere.model.I_C_Invoice.COLUMNNAME_C_Order_ID, order.getDocumentNo()));
			}
		}
		// 05768 took out poreference mandatory
		// if (Check.isEmpty(invoice.getPOReference()))
		// {
		// feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_Invoice.COLUMNNAME_POReference));
		// }

		final Set<String> ilMissingFields = new HashSet<>();
		final List<I_C_InvoiceLine> invoiceLines = Services.get(IInvoiceDAO.class).retrieveLines(invoice);
		for (final I_C_InvoiceLine il : invoiceLines)
		{
			if (il.getLine() <= 0)
			{
				// use invoice here, it's easier to identify as a user
				ilMissingFields.add(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line);
			}

			if (il.getC_OrderLine_ID() <= 0
					&& !invoiceIsRMCreditMemo)
			{
				// task 09182: on line level, we need an order line reference,
				// only for docSubType='CS' an orderLine does not have to be linked to an invoiceLine for successful EDI export.
				// note: if this changes in a new project, use AD_SysConfig
				ilMissingFields.add(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID);
			}
		}

		if (!ilMissingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(ilMissingFields));
		}

		return feedback;
	}

	@Override
	public List<Exception> isValidDesAdv(final I_EDI_Desadv desadv)
	{
		// TODO implement, use a lot of code from isValidInOut
		return Collections.emptyList();
	}

	@Override
	public List<Exception> isValidInOut(final I_M_InOut inOut)
	{
		final List<Exception> feedback = new ArrayList<>();

		if (!inOut.isEdiEnabled())
		{
			return feedback;
		}

		final org.compiere.model.I_C_BPartner bPartner = inOut.getC_BPartner();

		feedback.addAll(isValidPartner(bPartner));
		feedback.addAll(isValidBPLocation(inOut.getC_BPartner_Location()));

		final org.compiere.model.I_C_BPartner dropShipPartner = inOut.getDropShip_BPartner();
		if (dropShipPartner != null && dropShipPartner.getC_BPartner_ID() > 0)
		{
			feedback.addAll(isValidPartner(dropShipPartner));
		}

		final org.compiere.model.I_C_BPartner_Location dropShipLocation = inOut.getDropShip_Location();
		if (dropShipLocation != null && dropShipLocation.getC_BPartner_Location_ID() > 0)
		{
			feedback.addAll(isValidBPLocation(dropShipLocation));
		}

		// 05768 took out poreference mandatory
		// if (Check.isEmpty(inOut.getPOReference()))
		// {
		// feedback.add(new EDIFillMandatoryException(org.compiere.model.I_M_InOut.COLUMNNAME_POReference));
		// }

		Check.assumeNotNull(inOut.getC_Order(), "C_Order not null");
		final I_C_Order order = InterfaceWrapperHelper.create(inOut.getC_Order(), I_C_Order.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(inOut);
		final String trxName = InterfaceWrapperHelper.getTrxName(inOut);

		final int handOverPartnerId = order.getHandOver_Partner_ID();
		if (handOverPartnerId > 0)
		{
			final org.compiere.model.I_C_BPartner handOverPartner = InterfaceWrapperHelper.create(
					ctx, handOverPartnerId, org.compiere.model.I_C_BPartner.class, trxName);
			feedback.addAll(isValidPartner(handOverPartner));
		}

		final int handOverLocationId = order.getHandOver_Location_ID();
		if (handOverLocationId > 0)
		{
			final org.compiere.model.I_C_BPartner_Location handOverLocation = InterfaceWrapperHelper.create(
					ctx, handOverLocationId, org.compiere.model.I_C_BPartner_Location.class, trxName);
			feedback.addAll(isValidBPLocation(handOverLocation));
		}

		if (order.getC_Currency_ID() <= 0)
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID, order.getDocumentNo(), org.compiere.model.I_C_Order.COLUMNNAME_C_Currency_ID));
		}

		final org.compiere.model.I_C_BPartner billBPartner = order.getBill_BPartner();
		if (billBPartner != null && billBPartner.getC_BPartner_ID() > 0)
		{
			feedback.addAll(isValidPartner(billBPartner));
		}

		final org.compiere.model.I_C_BPartner_Location billLocation = order.getBill_Location();
		if (billLocation != null && billLocation.getC_BPartner_Location_ID() > 0)
		{
			feedback.addAll(isValidBPLocation(billLocation));
		}

		final Set<String> iolMissingFields = new HashSet<>();
		final Set<String> olMissingFields = new HashSet<>();
		final List<I_M_InOutLine> inOutLines = Services.get(IInOutDAO.class).retrieveLines(inOut, I_M_InOutLine.class);
		final List<de.metas.interfaces.I_C_OrderLine> inOutOrderLines = new ArrayList<>(); // orderLines for inOutLines
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			if (inOutLine.isPackagingMaterial())
			{
				continue; // nothing to check, because PackagingMaterial lines won't be exported anyways
			}

			if (inOutLine.getLine() <= 0)
			{
				iolMissingFields.add(org.compiere.model.I_M_InOutLine.COLUMNNAME_Line);
			}

			if (inOutLine.getC_UOM_ID() <= 0)
			{
				iolMissingFields.add(org.compiere.model.I_M_InOutLine.COLUMNNAME_C_UOM_ID);
			}

			if (inOutLine.getM_Product_ID() <= 0)
			{
				iolMissingFields.add(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID);
			}

			if (Check.isEmpty(inOutLine.getMovementQty()))
			{
				iolMissingFields.add(org.compiere.model.I_M_InOutLine.COLUMNNAME_MovementQty);
			}

			Check.assumeNotNull(inOutLine.getC_OrderLine_ID() > 0, "C_OrderLine_ID of {} should not be null", inOutLine);
			final de.metas.handlingunits.model.I_C_OrderLine orderLine = InterfaceWrapperHelper.create(inOutLine.getC_OrderLine(),
					de.metas.handlingunits.model.I_C_OrderLine.class);

			if (orderLine.getQtyItemCapacity() == null) // may be 0
			{
				olMissingFields.add(de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_QtyItemCapacity);
			}

			if (orderLine.getLine() <= 0)
			{
				olMissingFields.add(I_C_OrderLine.COLUMNNAME_Line);
			}

			final I_M_Product product = inOutLine.getM_Product();
			
			final int orgId = product.getAD_Org_ID();

			final I_C_BPartner_Product bPartnerProduct = Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(bPartner, product, orgId);
			if (bPartnerProduct == null)
			{
				feedback.add(new EDIMissingDependencyException("Missing C_BPartner_Product for partner " + bPartner.getValue() + " and product " + product.getValue()));
			}

			inOutOrderLines.add(orderLine);
		}

		final List<de.metas.interfaces.I_C_OrderLine> orderOverdeliveryLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		orderOverdeliveryLines.removeAll(inOutOrderLines); // remove already checked inOut-orderLines of this order
		for (final de.metas.interfaces.I_C_OrderLine orderLine : inOutOrderLines)
		{
			if (orderLine.getLine() <= 0)
			{
				olMissingFields.add(I_C_OrderLine.COLUMNNAME_Line);
			}
		}

		if (!iolMissingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(iolMissingFields));
		}

		if (!olMissingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID, order.getDocumentNo(), olMissingFields));
		}

		return feedback;
	}

	@Override
	public List<Exception> isValidPartner(final org.compiere.model.I_C_BPartner partner)
	{
		Check.assumeNotNull(partner, "C_BPartner not null when validating it");

		final List<Exception> feedback = new ArrayList<>();
		final List<String> missingFields = new ArrayList<>();

		final I_C_BPartner ediPartner = InterfaceWrapperHelper.create(partner, I_C_BPartner.class);
		if (!ediPartner.isEdiRecipient())
		{
			feedback.add(new AdempiereException(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ediPartner), IEDIDocumentBL.MSG_Partner_ValidateIsEDIRecipient_Error)));
		}

		if (Check.isEmpty(ediPartner.getEdiRecipientGLN(), true))
		{
			missingFields.add(I_C_BPartner.COLUMNNAME_EdiRecipientGLN);
		}

		if (!hasValidInvoiceAggregation(ediPartner))
		{
			feedback.add(new AdempiereException(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ediPartner), IEDIDocumentBL.MSG_Invalid_Invoice_Aggregation_Error)));
		}

		if (Check.isEmpty(ediPartner.getVATaxID(), true))
		{
			missingFields.add(de.metas.interfaces.I_C_BPartner.COLUMNNAME_VATaxID);
		}

		if (!missingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID, partner.getValue(), missingFields));
		}

		return feedback;
	}

	private boolean hasValidInvoiceAggregation(final I_C_BPartner ediPartner)
	{
		//
		// Get the BPartner's invoice header aggregation that will be actually used to aggregate sales invoices
		final Properties ctx = InterfaceWrapperHelper.getCtx(ediPartner);
		final boolean isSOTrx = true; // we are checking only Sales side (per Tobias advice)
		final IAggregation soAggregation = Services.get(IInvoiceAggregationFactory.class).getAggregation(ctx, ediPartner, isSOTrx, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header);

		// Make sure that aggregation includes C_Order_ID or POReference
		if (!soAggregation.hasColumnName(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID)
				&& !soAggregation.hasColumnName(I_C_Invoice_Candidate.COLUMNNAME_POReference))
		{
			return false;
		}

		return true;
	}

	private List<Exception> isValidBPLocation(final org.compiere.model.I_C_BPartner_Location bpLocation)
	{
		Check.assumeNotNull(bpLocation, "C_BPartner_Location not null when validating it");

		final List<Exception> feedback = new ArrayList<>();

		final I_C_BPartner_Location ediLocation = InterfaceWrapperHelper.create(bpLocation, I_C_BPartner_Location.class);
		if (Check.isEmpty(ediLocation.getGLN(), true))
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpLocation.getName(),
					I_C_BPartner_Location.COLUMNNAME_GLN));
		}

		return feedback;
	}

	@Override
	public ValidationState updateInvalid(final I_EDI_Document document, final String EDI_ExportStatus, final List<Exception> feedback, final boolean saveLocally)
	{
		if (feedback != null && !feedback.isEmpty())
		{
			return ValidationState.INVALID;
		}

		if (!I_EDI_Document.EDI_EXPORTSTATUS_Invalid.equals(EDI_ExportStatus))
		{
			return ValidationState.ALREADY_VALID;
		}

		// If EDI status is invalid, forcefully validate
		document.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Pending);
		if (saveLocally)
		{
			InterfaceWrapperHelper.save(document);
		}
		return ValidationState.UPDATED_VALID;
	}

	@Override
	public IExport<? extends I_EDI_Document> createExport(final Properties ctx, final int clientId, final int tableId, final int recordId, final String trxName)
	{
		//
		// Services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final String tableName = adTableDAO.retrieveTableName(tableId);

		final IExport<? extends I_EDI_Document> export;
		if (org.compiere.model.I_C_Invoice.Table_Name.equals(tableName))
		{
			final String tableIdentifier = org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID;
			verifyRecordId(recordId, tableIdentifier);

			final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, recordId, I_C_Invoice.class, trxName);
			export = new C_InvoiceExport(invoice, tableIdentifier, clientId);
		}
		else if (org.compiere.model.I_M_InOut.Table_Name.equals(tableName))
		{
			final String tableIdentifier = org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID;
			verifyRecordId(recordId, tableIdentifier);

			final I_M_InOut inOut = InterfaceWrapperHelper.create(ctx, recordId, I_M_InOut.class, trxName);
			export = new M_InOutExport(inOut, tableIdentifier, clientId);
		}
		else if (I_EDI_Desadv.Table_Name.equals(tableName))
		{
			final String tableIdentifier = I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID;
			verifyRecordId(recordId, tableIdentifier);

			final I_EDI_Desadv desadv = InterfaceWrapperHelper.create(ctx, recordId, I_EDI_Desadv.class, trxName);
			export = new EDI_DESADVExport(desadv, tableIdentifier, clientId);
		}
		else
		{
			throw new AdempiereException("Export EDI operation not supported for table " + tableName);
		}

		return export;
	}

	private void verifyRecordId(final int recordId, final String tableIdentifier)
	{
		if (recordId <= 0)
		{
			throw new FillMandatoryException(tableIdentifier);
		}
	}

	@Override
	public String buildFeedback(final List<Exception> feedback)
	{
		final StringBuilder feedbackBuilder = new StringBuilder();
		for (final Exception feedbackElement : feedback)
		{
			// addLog(feedbackElement.getLocalizedMessage());
			feedbackBuilder.append(feedbackElement.getLocalizedMessage()).append("\n");
		}
		return feedbackBuilder.toString();
	}
}
