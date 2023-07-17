package de.metas.gplr.report.repository;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRReportCharge;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_Charge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportCharge_Mapper_Test
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
		final GPLRReportCharge reportPart = GPLRReportCharge.builder()
				.purchaseOrderDocumentNo("purchaeOrderDocumentNo")
				.orderLineNo("orderLineNo")
				.costTypeName("costTypeName")
				.vendor(GPLRBPartnerName.builder()
						.code("payee_code")
						.name("payee_name")
						.build())
				.amountFC(Amount.of("1", CurrencyCode.USD))
				.amountLC(Amount.of("2", CurrencyCode.EUR))
				.build();

		final I_GPLR_Report_Charge record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Charge.class);
		GPLRReportCharge_Mapper.updateRecord(record, reportPart, orgId);
		final GPLRReportCharge reportPart2 = GPLRReportCharge_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}

}