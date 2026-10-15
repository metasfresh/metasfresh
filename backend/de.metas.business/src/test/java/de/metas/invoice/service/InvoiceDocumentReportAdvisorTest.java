package de.metas.invoice.service;

import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceDocumentReportAdvisorTest
{
	private InvoiceDocumentReportAdvisor advisor;
	private PrintFormatId printFormatId;
	private I_C_BPartner_Location bpartnerLocation;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		advisor = new InvoiceDocumentReportAdvisor(new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(),
				new BPartnerPrintFormatRepository()));

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);
		bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(bpartnerLocation);

		final I_AD_PrintFormat printFormat = newInstance(I_AD_PrintFormat.class);
		printFormat.setJasperProcess_ID(540001);
		save(printFormat);
		printFormatId = PrintFormatId.ofRepoId(printFormat.getAD_PrintFormat_ID());
	}

	@Test
	void salesInvoice_isAutoPrinted()
	{
		final DocumentReportInfo reportInfo = getReportInfo(X_C_DocType.DOCBASETYPE_ARInvoice, null);

		assertThat(reportInfo.isSuppressAutoPrint()).isFalse();
	}

	@Test
	void paymentBonusCreditMemo_isNotAutoPrinted()
	{
		final DocumentReportInfo reportInfo = getReportInfo(X_C_DocType.DOCBASETYPE_ARCreditMemo, X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo);

		assertThat(reportInfo.isSuppressAutoPrint()).isTrue();
	}

	private DocumentReportInfo getReportInfo(final String docBaseType, @Nullable final String docSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		docType.setDocSubType(docSubType);
		docType.setDocumentCopies(1);
		save(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bpartnerLocation.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setDocumentNo("INV-1");
		save(invoice);

		return advisor.getDocumentReportInfo(TableRecordReference.of(I_C_Invoice.Table_Name, invoice.getC_Invoice_ID()), printFormatId, null);
	}
}
