package de.metas.gplr.report.repository;

import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRCurrencyInfo;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.report.model.GPLRReportPurchaseOrder;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_PurchaseOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportPurchaseOrder_Mapper_Test
{

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void save_load()
	{
		final OrgId orgId = OrgId.ofRepoId(111);
		final GPLRReportPurchaseOrder reportPart = GPLRReportPurchaseOrder.builder()
				.documentNo("documentNo")
				.purchasedFrom(GPLRBPartnerName.builder()
						.code("bp_code")
						.name("bp_name")
						.vatId("bp_vat")
						.build())
				.vendorReference("vendorReference")
				.paymentTerm(GPLRPaymentTermRenderedString.ofRenderedString("paymentTerm"))
				.incoterms(GPLRIncotermsInfo.builder()
						.code("incoterms_code")
						.location("incoterms_location")
						.build())
				.currencyInfo(GPLRCurrencyInfo.builder()
						.currencyRate(new BigDecimal("12.3456"))
						.foreignCurrency(CurrencyCode.ofThreeLetterCode("RON"))
						.fecDocumentNo("fecDocumentNo")
						.build())
				.build();

		final I_GPLR_Report_PurchaseOrder record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_PurchaseOrder.class);
		GPLRReportPurchaseOrder_Mapper.updateRecord(record, reportPart, orgId);
		final GPLRReportPurchaseOrder reportPart2 = GPLRReportPurchaseOrder_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}

}