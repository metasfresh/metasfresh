package de.metas.gplr.repository;

import de.metas.gplr.model.GPLRBPartnerName;
import de.metas.gplr.model.GPLRIncotermsInfo;
import de.metas.gplr.model.GPLRReportSalesOrder;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_SalesOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportSalesOrder_Mapper_Test
{

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void save_load()
	{
		final GPLRReportSalesOrder reportPart = GPLRReportSalesOrder.builder()
				.documentNo("documentNo")
				.customer(GPLRBPartnerName.builder()
						.code("code")
						.name("name")
						.vatId("vatId")
						.build())
				.frameContractNo("frameContractNo")
				.poReference("poReference")
				.incoterms(GPLRIncotermsInfo.builder()
						.code("code")
						.location("location")
						.build())
				.build();

		final I_GPLR_Report_SalesOrder record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_SalesOrder.class);
		GPLRReportSalesOrder_Mapper.updateRecord(record, reportPart, OrgId.MAIN);
		final GPLRReportSalesOrder reportPart2 = GPLRReportSalesOrder_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}

}