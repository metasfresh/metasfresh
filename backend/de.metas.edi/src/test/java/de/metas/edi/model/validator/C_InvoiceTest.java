package de.metas.edi.model.validator;

import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.model.I_C_Invoice;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class C_InvoiceTest
{
	private static final String STALE_ERROR_MSG = "UNB sender GLN 4000000000001 equals recipient GLN 4000000000001";

	private C_Invoice interceptor;
	private I_C_BPartner_Location validLocation;
	private I_C_BPartner_Location locationWithoutGLN;
	private I_C_DocType salesInvoiceDocType;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		interceptor = new C_Invoice(EDIDocumentBL.newInstanceForUnitTesting(), new EDIDocOutBoundLogService());
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(interceptor);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue("EDICustomer");
		bpartner.setName("EDICustomer");
		saveRecord(bpartner);

		validLocation = newInstance(I_C_BPartner_Location.class);
		validLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		validLocation.setGLN("4000000000002");
		saveRecord(validLocation);

		locationWithoutGLN = newInstance(I_C_BPartner_Location.class);
		locationWithoutGLN.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		locationWithoutGLN.setName("LocationWithoutGLN");
		saveRecord(locationWithoutGLN);

		final I_C_BPartner_EDI_Setting setting = newInstance(I_C_BPartner_EDI_Setting.class);
		setting.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		setting.setIsEdiDesadvRecipient(false);
		setting.setEdiDESADVSendingMode("R");
		setting.setIsEdiInvoicRecipient(true);
		setting.setEdiInvoicRecipientGLN("4000000000003");
		setting.setEdiINVOICSendingMode("R");
		saveRecord(setting);

		salesInvoiceDocType = newInstance(I_C_DocType.class);
		salesInvoiceDocType.setDocBaseType(DocBaseType.SalesInvoice.getCode());
		salesInvoiceDocType.setIsSOTrx(true);
		saveRecord(salesInvoiceDocType);
	}

	@Test
	void statusChangedFromErrorToSent_clearsErrorMsg()
	{
		final I_C_Invoice invoice = invoiceWithFailedEDISend(validLocation);

		invoice.setEDI_ExportStatus(EDIExportStatus.Sent.getCode());
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Sent.getCode());
		assertThat(invoice.getEDIErrorMsg()).isNull();
	}

	@Test
	void statusChangedFromErrorToPending_validInvoice_clearsErrorMsg()
	{
		final I_C_Invoice invoice = invoiceWithFailedEDISend(validLocation);

		invoice.setEDI_ExportStatus(EDIExportStatus.Pending.getCode());
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Pending.getCode());
		assertThat(invoice.getEDIErrorMsg()).isNull();
	}

	@Test
	void statusChangedFromErrorToPending_invalidInvoice_setsFreshValidationMsg()
	{
		final I_C_Invoice invoice = invoiceWithFailedEDISend(locationWithoutGLN);

		invoice.setEDI_ExportStatus(EDIExportStatus.Pending.getCode());
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Invalid.getCode());
		assertThat(invoice.getEDIErrorMsg())
				.isNotBlank()
				.doesNotContain(STALE_ERROR_MSG)
				.contains(I_C_BPartner_Location.COLUMNNAME_GLN);
	}

	@Test
	void invalidInvoiceBecomesValid_clearsValidationMsg()
	{
		final I_C_Invoice invoice = newSalesInvoice(locationWithoutGLN, EDIExportStatus.Pending, null, EDIExportStatus.Invalid);
		assertThat(invoice.getEDIErrorMsg()).contains(I_C_BPartner_Location.COLUMNNAME_GLN);

		locationWithoutGLN.setGLN("4000000000004");
		saveRecord(locationWithoutGLN);
		invoice.setDescription("re-validate after the GLN was added");
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Pending.getCode());
		assertThat(invoice.getEDIErrorMsg()).isNull();
	}

	@Test
	void statusChangedToError_keepsNewErrorMsg()
	{
		final I_C_Invoice invoice = newSalesInvoice(validLocation, EDIExportStatus.SendingStarted, null);

		invoice.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
		invoice.setEDIErrorMsg(STALE_ERROR_MSG);
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Error.getCode());
		assertThat(invoice.getEDIErrorMsg()).isEqualTo(STALE_ERROR_MSG);
	}

	@Test
	void statusUnchanged_keepsErrorMsg()
	{
		final I_C_Invoice invoice = invoiceWithFailedEDISend(validLocation);

		invoice.setDescription("some unrelated change");
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Error.getCode());
		assertThat(invoice.getEDIErrorMsg()).isEqualTo(STALE_ERROR_MSG);
	}

	@Test
	void translateEDIErrorMessage_nullMsg_staysNull()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setEDIErrorMsg(null);

		interceptor.translateEDIErrorMessage(invoice);

		assertThat(invoice.getEDIErrorMsg()).isNull();
	}

	/**
	 * Mirrors a failed EDI send: the export sets status {@code E} together with the error message.
	 */
	private I_C_Invoice invoiceWithFailedEDISend(final I_C_BPartner_Location location)
	{
		return newSalesInvoice(location, EDIExportStatus.Error, STALE_ERROR_MSG);
	}

	private I_C_Invoice newSalesInvoice(
			final I_C_BPartner_Location location,
			final EDIExportStatus ediExportStatus,
			@Nullable final String ediErrorMsg)
	{
		final I_C_Invoice invoice = newSalesInvoice(location, ediExportStatus, ediErrorMsg, ediExportStatus);
		assertThat(invoice.getEDIErrorMsg()).isEqualTo(ediErrorMsg);
		return invoice;
	}

	private I_C_Invoice newSalesInvoice(
			final I_C_BPartner_Location location,
			final EDIExportStatus ediExportStatus,
			@Nullable final String ediErrorMsg,
			final EDIExportStatus expectedEdiExportStatusAfterSave)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(location.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_DocType_ID(salesInvoiceDocType.getC_DocType_ID());
		invoice.setC_DocTypeTarget_ID(salesInvoiceDocType.getC_DocType_ID());
		invoice.setDocStatus(DocStatus.Completed.getCode());
		invoice.setEDI_ExportStatus(ediExportStatus.getCode());
		invoice.setEDIErrorMsg(ediErrorMsg);
		saveRecord(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(expectedEdiExportStatusAfterSave.getCode());
		return invoice;
	}
}
