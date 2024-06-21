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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
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
import de.metas.edi.process.export.impl.EDI_DESADV_InOut_Export;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_M_InOut_Desadv_V;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.InOutId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceAggregationFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class EDIDocumentBL implements IEDIDocumentBL
{
	private static final String ERR_NotExistsShipmentForOrderError = "NotExistsShipmentForOrderError";

	private static final Logger logger = LogManager.getLogger(EDIDocumentBL.class);
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	@Override
	public boolean updateEdiEnabled(@NonNull final I_EDI_Document_Extension document)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		// EDI applies only for customer invoices and shipments
		if (!document.isSOTrx() && document.isEdiEnabled())
		{
			loggable.addLog("IsSoTrx=false; => update IsEdiEnabled to false");
			document.setIsEdiEnabled(false); // 08619: don't assume that the flag is already false from the beginning, but make sure it is false now
			return document.isEdiEnabled();
		}

		// task 05721: Set isEDIEnabled to false and disable the button for reversals
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(document.getDocStatus());
		if (docStatus.isReversed() && document.isEdiEnabled())
		{
			loggable.addLog("DocStatus={} is reversed; => update IsEdiEnabled to false", docStatus);
			document.setIsEdiEnabled(false);
			return document.isEdiEnabled();
		}

		if (document.getReversal_ID() > 0 && document.isEdiEnabled())
		{
			loggable.addLog("Reversal_ID={} (i.e. >0); => update IsEdiEnabled to false", docStatus);
			document.setIsEdiEnabled(false);
			return document.isEdiEnabled();
		}

		logger.debug("return non-updated isEdiEnabled={}", document.isEdiEnabled());
		return document.isEdiEnabled();
	}

	@Override
	public List<Exception> isValidInvoice(@NonNull final I_C_Invoice invoice)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		final List<Exception> feedback = new ArrayList<>();
		final String EDIStatus = invoice.getEDI_ExportStatus();
		if (!invoice.isEdiEnabled() && !I_EDI_Document.EDI_EXPORTSTATUS_Invalid.equals(EDIStatus))
		{
			loggable.addLog("isValidInvoice - C_Invoice_ID={} has IsEdiEnabled={}, EDI_ExportStatus={}; return empty list", invoice.getC_Invoice_ID(), invoice.isEdiEnabled(), EDIStatus);
			return feedback;
		}

		feedback.addAll(isValidPartner(invoice.getC_BPartner(), true/* isPartOfInvoiceValidation */));

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final org.compiere.model.I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID()));
		feedback.addAll(isValidBPLocation(bPartnerLocationRecord));

		// task 09182: for return material credit memos, we don't have or need an (imported) EDI ORDERS PoReference
		// task 09811: guard against NPE when invoice is not yet completed and therefore doesn'T yet have a docType
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final I_C_DocType docType = invoice.getC_DocType_ID() > 0
				? docTypeDAO.getRecordById(invoice.getC_DocType_ID())
				: docTypeDAO.getRecordById(invoice.getC_DocTypeTarget_ID());

		final boolean invoiceIsRMCreditMemo = docType != null
				&& Services.get(IInvoiceBL.class).isCreditMemo(docType.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_GS_Retoure.equals(docType.getDocSubType());

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

		final Set<String> ilMissingFields = new HashSet<>();
		final List<I_C_InvoiceLine> invoiceLines = Services.get(IInvoiceDAO.class).retrieveLines(invoice);
		for (final I_C_InvoiceLine il : invoiceLines)
		{
			if (il.getLine() <= 0)
			{
				// use invoice here, it's easier to identify as a user
				ilMissingFields.add(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line);
			}

			if (il.getC_OrderLine_ID() <= 0 && !invoiceIsRMCreditMemo)
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

		if (logger.isDebugEnabled() && !feedback.isEmpty())
		{
			logger.debug("Invoice validation problem(s) found; feedback={}", buildFeedback(feedback));
		}
		return feedback;
	}

	@Override
	public List<Exception> isValidDesAdv(@NonNull final I_EDI_Desadv desadvRecord)
	{
		final IDesadvBL desadvBL = Services.get(IDesadvBL.class);
		final List<Exception> feedback = new ArrayList<>();

		final ImmutableList<ITranslatableString> errorMsgs = desadvBL.createMsgsForDesadvsBelowMinimumFulfilment(ImmutableList.of(desadvRecord));
		for (final ITranslatableString msg : errorMsgs)
		{
			feedback.add(new AdempiereException(msg));
		}
		return feedback;
	}

	@Override
	public List<Exception> isValidPartner(@NonNull final org.compiere.model.I_C_BPartner bpartner)
	{
		return isValidPartner(bpartner, false/* isPartOfInvoiceValidation */);
	}

	private List<Exception> isValidPartner(
			@NonNull final org.compiere.model.I_C_BPartner bpartner,
			final boolean isPartOfInvoiceValidation)
	{
		final List<Exception> feedback = new ArrayList<>();
		final List<String> missingFields = new ArrayList<>();

		final I_C_BPartner ediPartner = InterfaceWrapperHelper.create(bpartner, I_C_BPartner.class);
		final boolean isEdiRecipient = ediPartner.isEdiDesadvRecipient() || ediPartner.isEdiInvoicRecipient();
		if (!isEdiRecipient)
		{
			feedback.add(new AdempiereException(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ediPartner), IEDIDocumentBL.MSG_Partner_ValidateIsEDIRecipient_Error)));
		}

		if (Check.isEmpty(ediPartner.getEdiDesadvRecipientGLN(), true))
		{
			missingFields.add(I_C_BPartner.COLUMNNAME_EdiDesadvRecipientGLN);
		}

		final boolean checkForAggregationRule = !isPartOfInvoiceValidation; // if we validate for an already existing invoice we don't need to bother for the partner's aggregation rule
		if (checkForAggregationRule && !hasValidInvoiceAggregation(ediPartner))
		{
			feedback.add(new AdempiereException(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ediPartner), IEDIDocumentBL.MSG_Invalid_Invoice_Aggregation_Error)));
		}

		if (Check.isEmpty(ediPartner.getVATaxID(), true))
		{
			missingFields.add(de.metas.interfaces.I_C_BPartner.COLUMNNAME_VATaxID);
		}

		if (!missingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartner.getValue(), missingFields));
		}

		return feedback;
	}

	private boolean hasValidInvoiceAggregation(final I_C_BPartner ediPartner)
	{
		//
		// Get the BPartner's invoice header aggregation that will be actually used to aggregate sales invoices
		final Properties ctx = InterfaceWrapperHelper.getCtx(ediPartner);
		final boolean isSOTrx = true; // we are checking only Sales side because we don't EDI purchase invoices
		final Aggregation soAggregation = Services.get(IInvoiceAggregationFactory.class).getAggregation(ctx, ediPartner, isSOTrx, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header);

		// Make sure that aggregation includes C_Order_ID or POReference
		if (!soAggregation.hasColumnName(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID)
				&& !soAggregation.hasColumnName(I_C_Invoice_Candidate.COLUMNNAME_POReference))
		{
			return false;
		}

		return true;
	}

	private List<Exception> isValidBPLocation(@NonNull final org.compiere.model.I_C_BPartner_Location bpLocation)
	{
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
	public IExport<? extends I_EDI_Document> createExport(
			final Properties ctx,
			final ClientId clientId,
			final int tableId,
			final int recordId,
			final String trxName)
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
		else if (I_EDI_Desadv.Table_Name.equals(tableName))
		{
			final String tableIdentifier = I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID;
			verifyRecordId(recordId, tableIdentifier);

			final I_EDI_Desadv desadv = InterfaceWrapperHelper.create(ctx, recordId, I_EDI_Desadv.class, trxName);
			export = new EDI_DESADVExport(desadv, tableIdentifier, clientId);
		}
		else if (I_M_InOut.Table_Name.equals(tableName))
		{
			final String tableIdentifier = I_M_InOut.COLUMNNAME_M_InOut_ID;
			verifyRecordId(recordId, tableIdentifier);

			final I_M_InOut_Desadv_V desadvInOut = desadvDAO.getInOutDesadvByInOutId(InOutId.ofRepoId(recordId));
			export = new EDI_DESADV_InOut_Export(desadvInOut, tableIdentifier, clientId);
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
	public String buildFeedback(@NonNull final List<Exception> feedback)
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
