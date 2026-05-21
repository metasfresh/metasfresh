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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.EDIType;
import de.metas.edi.api.ValidationState;
import de.metas.edi.exception.EDIFillMandatoryException;
import de.metas.edi.exception.EDIMissingDependencyException;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.edi.process.export.IExport;
import de.metas.edi.process.export.impl.C_InvoiceExport;
import de.metas.edi.process.export.impl.EDI_DESADVExport;
import de.metas.edi.process.export.impl.EDI_DESADV_InOut_Export;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_M_InOut_Desadv_V;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EDIDocumentBL
{
	private static final String ERR_NotExistsShipmentForOrderError = "NotExistsShipmentForOrderError";
	private static final String MSG_Partner_ValidateIsEDIRecipient_Error = "de.metas.edi.ValidateIsEDIRecipientError";
	private static final String MSG_Invalid_Invoice_Aggregation_Error = "de.metas.edi.InvalidInvoiceAggregationError";

	@NonNull private static final Logger logger = LogManager.getLogger(EDIDocumentBL.class);
	@NonNull private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@NonNull private final EDIBPartnerConfigService ediBpartnerConfigService;
    @NonNull private final DesadvBL desadvBL;

	@VisibleForTesting
	public static EDIDocumentBL newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(EDIDocumentBL.class,
				() -> new EDIDocumentBL(EDIBPartnerConfigService.newInstanceForUnitTesting(),
						DesadvBL.newInstanceForUnitTesting())
				);
	}

	public boolean updateEdiExportStatus(@NonNull final I_M_InOut inOut)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inOut))
		{
			if (huInOutBL.isCustomerReturn(inOut))
			{
				// no EDI for customer return (for the time being)
				return updateEdiExportStatus(inOut, EDIType.DESADV, false);
			}

			if (!updateEdiExportStatus(inOut, EDIType.DESADV, true))
			{
				return false;
			}

			final List<Exception> feedback = isValidShipment(inOut);

			final ValidationState validationState = updateInvalid(inOut,
					EDIExportStatus.ofCode(inOut.getEDI_ExportStatus()),
					feedback,
					false);

			if (validationState.isInvalid())
			{
				logger.debug("validationState={}; persisting error-message in M_InOut", validationState);
				final String errorMessage = buildFeedback(feedback);
				inOut.setEDIErrorMsg(errorMessage);
				inOut.setEDI_ExportStatus(EDIExportStatus.Invalid.getCode());
				return false;
			}
			return true;
		}
	}

	public void updateEdiExportStatus(@NonNull final I_C_Invoice invoice)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(invoice))
		{
			if (!updateEdiExportStatus(invoice, EDIType.INVOIC, true))
			{
				return;
			}

			final List<Exception> feedback = isValidInvoice(invoice);

			final ValidationState validationState = updateInvalid(invoice,
					EDIExportStatus.ofCode(invoice.getEDI_ExportStatus()),
					feedback,
					false);

			if (ValidationState.INVALID == validationState)
			{
				logger.debug("validationState={}; persisting error-message in C_Invoice", validationState);
				final String errorMessage = buildFeedback(feedback);
				invoice.setEDIErrorMsg(errorMessage);
				invoice.setEDI_ExportStatus(EDIExportStatus.Invalid.getCode());
			}
		}
	}

	private boolean updateEdiExportStatus(@NonNull final I_EDI_Document_Extension document,
										 @NonNull final EDIType ediType,
										 final boolean isDocumentEligibleForEDI)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		// only use if not important info for the user, otherwise invalid status should be set via models validation function
		if(!isDocumentEligibleForEDI)
		{
			logger.debug("isDocumentEligibleForEDI=false; => update EdiExportStatus to {}", EDIExportStatus.DontSend.name());
			setEdiExportStatus(document, false);
			return false;
		}

		// EDI applies only for customer invoices and shipments
		if (!document.isSOTrx())
		{
			loggable.addLog("IsSoTrx=false; => update EdiExportStatus to {}", EDIExportStatus.DontSend.name());
			setEdiExportStatus(document, false);
			return false;
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(document.getDocStatus());
		if (docStatus.isReversed())
		{
			loggable.addLog("DocStatus={} is reversed; => update EdiExportStatus to {}", docStatus, EDIExportStatus.DontSend.name());
			setEdiExportStatus(document, false);
			return false;
		}

		if (document.getReversal_ID() > 0)
		{
			loggable.addLog("Reversal_ID={} (i.e. >0); => update EdiExportStatus to {}", docStatus, EDIExportStatus.DontSend.name());
			setEdiExportStatus(document, false);
			return false;
		}

		final boolean isBPartnerEDIConfigEnabled;
		if (ediType.isDesadv())
		{
			isBPartnerEDIConfigEnabled = ediBpartnerConfigService.isEdiDesadvRecipient(BPartnerId.ofRepoId(document.getC_BPartner_ID()));
		}
		else if (ediType.isInvoic())
		{
			isBPartnerEDIConfigEnabled = ediBpartnerConfigService.isEdiInvoicRecipient(BPartnerId.ofRepoId(document.getC_BPartner_ID()));
		}
		else
		{
			throw new AdempiereException("Unsupported EDIType: " + ediType);
		}

		final EDIExportStatus ediExportStatus = isBPartnerEDIConfigEnabled ? EDIExportStatus.Pending : EDIExportStatus.DontSend;
		logger.debug("BPartnerEDIConfigEnabled={}; => update EdiExportStatus to {}", isBPartnerEDIConfigEnabled, ediExportStatus.name());
		return setEdiExportStatus(document, isBPartnerEDIConfigEnabled);
	}

	private boolean setEdiExportStatus(@NonNull final I_EDI_Document_Extension document, final boolean isBPartnerEDIConfigEnabled)
	{
		if(!isBPartnerEDIConfigEnabled && EDIExportStatus.isInProgressOrSend(EDIExportStatus.ofNullableCode(document.getEDI_ExportStatus())))
		{
			throw new AdempiereException("EdiExportStatus can't be changed, while export is in progress or already sent ");
		}

		document.setEDI_ExportStatus(isBPartnerEDIConfigEnabled ? EDIExportStatus.Pending.getCode() : EDIExportStatus.DontSend.getCode());
		return isBPartnerEDIConfigEnabled;
	}

	@NonNull
	public List<Exception> isValidInvoice(@NonNull final I_C_Invoice invoice)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		final List<Exception> feedback = new ArrayList<>();
		final EDIExportStatus ediExportStatus = EDIExportStatus.ofCode(invoice.getEDI_ExportStatus());
		final boolean isEdiInvoicRecipient = ediBpartnerConfigService.isEdiInvoicRecipient(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()));
		if (!isEdiInvoicRecipient && !ediExportStatus.isInvalid())
		{
			loggable.addLog("isValidInvoice - C_Invoice_ID={} has isBPartnerEDIConfigEnabled=false, EDI_ExportStatus={}; return empty list", invoice.getC_Invoice_ID(), ediExportStatus.name());
			return feedback;
		}

		feedback.addAll(isValidPartner(invoice.getC_BPartner(), EDIType.INVOIC));

		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID()));
		feedback.addAll(isValidBPLocation(bPartnerLocationRecord));

		// task 09182: for return material credit memos, we don't have or need an (imported) EDI ORDERS PoReference
		// task 09811: guard against NPE when invoice is not yet completed and therefore doesn'T yet have a docType
		final I_C_DocType docType = invoice.getC_DocType_ID() > 0
				? docTypeDAO.getById(invoice.getC_DocType_ID())
				: docTypeDAO.getById(invoice.getC_DocTypeTarget_ID());

		final boolean invoiceIsRMCreditMemo = docType != null
				&& Services.get(IInvoiceBL.class).isCreditMemo(docType.getDocBaseType())
				&& X_C_DocType.DOCSUBTYPE_GS_Retoure.equals(docType.getDocSubType());

		// an invoice order must have AT LEAST one M_InOut for successful EDI export
		final OrderId orderId = OrderId.ofRepoIdOrNull(invoice.getC_Order_ID());
		if (orderId != null && !invoiceIsRMCreditMemo)
		{
			final I_C_Order order = orderDAO.getById(orderId);
			final boolean hasInOuts = orderDAO.hasInOuts(order);
			if (!hasInOuts)
			{
				feedback.add(new EDIMissingDependencyException(EDIDocumentBL.ERR_NotExistsShipmentForOrderError,
															   org.compiere.model.I_C_Invoice.Table_Name, order.getDocumentNo()));
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

	@NonNull
	private List<Exception> isValidShipment(@NonNull final I_M_InOut shipment)
	{
		final List<Exception> feedback = new ArrayList<>();

		if (Check.isBlank(shipment.getPOReference()))
		{
			feedback.add(new EDIMissingDependencyException("NotExistsShipmentPOReference", I_M_InOut.Table_Name, shipment.getDocumentNo()));
		}

		// me03#29231 (consolidated multi-source-order shipments): when M_InOutLines come
		// from multiple source orders, the legacy interceptor M_InOutLine.unsetM_InOut_C_Order_ID
		// nulls out M_InOut.C_Order_ID. The shipment is still EDI-valid as long as the
		// M_InOutLines reference C_OrderLines (the per-line orderlines carry the source
		// orders; DesadvBL.addToDesadvCreateForInOutIfNotExist resolves DESADVs via
		// orderLine.EDI_DesadvLine_ID).
		if (OrderId.ofRepoIdOrNull(shipment.getC_Order_ID()) == null
				&& !hasInOutLineWithOrderLine(shipment))
		{
			feedback.add(new EDIMissingDependencyException("NotExistsShipmentOrder", I_M_InOut.Table_Name, shipment.getDocumentNo()));
		}

		feedback.addAll(isValidPartner(shipment.getC_BPartner(), EDIType.DESADV));

		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(shipment.getC_BPartner_ID(), shipment.getC_BPartner_Location_ID()));
		feedback.addAll(isValidBPLocation(bPartnerLocationRecord));

		return feedback;
	}

	private boolean hasInOutLineWithOrderLine(@NonNull final I_M_InOut shipment)
	{
		return inOutDAO.retrieveLines(shipment, I_M_InOutLine.class)
				.stream()
				.anyMatch(line -> line.getC_OrderLine_ID() > 0);
	}

	public List<Exception> isValidDesAdv(@NonNull final I_EDI_Desadv desadvRecord)
	{
		final List<Exception> feedback = new ArrayList<>();

		final ImmutableList<ITranslatableString> errorMsgs = desadvBL.createMsgsForDesadvsBelowMinimumFulfilment(ImmutableList.of(desadvRecord));
		for (final ITranslatableString msg : errorMsgs)
		{
			feedback.add(new AdempiereException(msg));
		}
		return feedback;
	}

	public List<Exception> isValidPartner(@NonNull final org.compiere.model.I_C_BPartner bpartner)
	{
		return isValidPartner(bpartner, null);
	}

	private List<Exception> isValidPartner(@NonNull final org.compiere.model.I_C_BPartner bpartner,
										  @Nullable final EDIType ediType)
	{
		final List<Exception> feedback = new ArrayList<>();
		final List<String> missingFields = new ArrayList<>();

		final I_C_BPartner ediPartner = InterfaceWrapperHelper.create(bpartner, I_C_BPartner.class);
		final boolean isBPartnerEDIConfigEnabled;
		if (ediType == null)
		{
			isBPartnerEDIConfigEnabled = ediPartner.isEdiDesadvRecipient() || ediPartner.isEdiInvoicRecipient();
		}
		else if (ediType.isDesadv())
		{
			isBPartnerEDIConfigEnabled = ediPartner.isEdiDesadvRecipient();
		}
		else if (ediType.isInvoic())
		{
			isBPartnerEDIConfigEnabled = ediPartner.isEdiInvoicRecipient();
		}
		else
		{
			throw new AdempiereException("Unsupported EDIType: " + ediType);
		}

		if (!isBPartnerEDIConfigEnabled)
		{
			feedback.add(new AdempiereException(Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(ediPartner), MSG_Partner_ValidateIsEDIRecipient_Error)));
		}

		if (ediPartner.isEdiDesadvRecipient() && Check.isBlank(ediPartner.getEdiDesadvRecipientGLN()))
		{
			missingFields.add(I_C_BPartner.COLUMNNAME_EdiDesadvRecipientGLN);
		}
		if (ediPartner.isEdiInvoicRecipient() && Check.isBlank(ediPartner.getEdiInvoicRecipientGLN()))
		{
			missingFields.add(I_C_BPartner.COLUMNNAME_EdiInvoicRecipientGLN);
		}

		// VATaxIDs are not needed in general, but only if the customer is in a different country or if the customer explicitly requests them to be in their INVOICs

		if (!missingFields.isEmpty())
		{
			feedback.add(new EDIFillMandatoryException(org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartner.getValue(), missingFields));
		}

		return feedback;
	}

	private List<Exception> isValidBPLocation(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final List<Exception> feedback = new ArrayList<>();
		if (Check.isEmpty(bpLocation.getGLN(), true))
		{
			feedback.add(new EDIFillMandatoryException(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpLocation.getName(),
													   I_C_BPartner_Location.COLUMNNAME_GLN));
		}

		return feedback;
	}

	public ValidationState updateInvalid(@NonNull final I_EDI_Document document,
										 @NonNull final EDIExportStatus EDI_ExportStatus,
										 @NonNull final List<Exception> feedback,
										 final boolean saveLocally)
	{
		if (!feedback.isEmpty())
		{
			return ValidationState.INVALID;
		}

		if (!EDI_ExportStatus.isInvalid())
		{
			return ValidationState.ALREADY_VALID;
		}

		if (!EDIExportStatus.ofCode(document.getEDI_ExportStatus()).isProcessed())
		{
			// If EDI status is valid, forcefully validate
			document.setEDI_ExportStatus(EDIExportStatus.Pending.getCode());
		}

		if (saveLocally)
		{
			InterfaceWrapperHelper.save(document);
		}
		return ValidationState.UPDATED_VALID;
	}

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

			final I_M_InOut_Desadv_V desadvInOut = desadvBL.getInOutDesadvByInOutId(InOutId.ofRepoId(recordId));
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
