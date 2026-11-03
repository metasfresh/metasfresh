package de.metas.edi.api.impl;

import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.edi.model.I_C_Invoice;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import de.metas.edi.api.EDIExportStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class EDIDocumentBL_updateEdiExportStatus_Invoice_Test
{
	private EDIDocumentBL ediDocumentBL;
	private I_C_BPartner_Location ediInvoicRecipientLocation;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ediDocumentBL = EDIDocumentBL.newInstanceForUnitTesting();

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue("EDICustomer");
		bpartner.setName("EDICustomer");
		saveRecord(bpartner);

		ediInvoicRecipientLocation = newInstance(I_C_BPartner_Location.class);
		ediInvoicRecipientLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(ediInvoicRecipientLocation);

		final I_C_BPartner_EDI_Setting setting = newInstance(I_C_BPartner_EDI_Setting.class);
		setting.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		setting.setIsEdiDesadvRecipient(false);
		setting.setEdiDESADVSendingMode("R");
		setting.setIsEdiInvoicRecipient(true);
		setting.setEdiINVOICSendingMode("R");
		saveRecord(setting);
	}

	@Test
	void salesInvoice_ofEdiInvoicRecipient_isNotDontSend()
	{
		final I_C_Invoice invoice = completedSalesInvoice(docType(DocBaseType.SalesInvoice, null));

		ediDocumentBL.updateEdiExportStatus(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isNotEqualTo(EDIExportStatus.DontSend.getCode());
	}

	@Test
	void paymentBonusCreditMemo_ofEdiInvoicRecipient_isDontSend()
	{
		final I_C_Invoice invoice = completedSalesInvoice(docType(DocBaseType.SalesCreditMemo, X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo));

		ediDocumentBL.updateEdiExportStatus(invoice);

		assertThat(invoice.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.DontSend.getCode());
	}

	private I_C_DocType docType(@SuppressWarnings("SameParameterValue") final DocBaseType docBaseType, @Nullable final String docSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType.getCode());
		docType.setDocSubType(docSubType);
		docType.setIsSOTrx(true);
		saveRecord(docType);
		return docType;
	}

	private I_C_Invoice completedSalesInvoice(final I_C_DocType docType)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(ediInvoicRecipientLocation.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(ediInvoicRecipientLocation.getC_BPartner_Location_ID());
		invoice.setIsSOTrx(true);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		invoice.setDocStatus(DocStatus.Completed.getCode());
		return invoice;
	}
}
