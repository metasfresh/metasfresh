package de.metas.gplr.report.repository;

import de.metas.business.BusinessTestHelper;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRReportLineItem;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_GPLR_Report_Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportLineItem_Mapper_Test
{

	private I_C_UOM uomEach;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
	}

	@Test
	void save_load()
	{
		final OrgId orgId = OrgId.ofRepoId(111);
		final GPLRReportLineItem reportPart = GPLRReportLineItem.builder()
				.documentNo("documentNo")
				.lineCode("lineCode")
				.description("description")
				.qty(Quantity.of("123.456", uomEach))
				.priceFC(Amount.of("1", CurrencyCode.USD))
				.amountFC(Amount.of("2", CurrencyCode.USD))
				.amountLC(Amount.of("3", CurrencyCode.EUR))
				.batchNo("batchNo")
				.build();

		final I_GPLR_Report_Line record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Line.class);
		GPLRReportLineItem_Mapper.updateRecord(record, reportPart, orgId, 10);
		final GPLRReportLineItem reportPart2 = GPLRReportLineItem_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}

}