package de.metas.gplr.report.repository;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRReportSummary;
import de.metas.gplr.report.repository.GPLRReportSummary_Mapper;
import de.metas.organization.OrgId;
import de.metas.util.lang.Percent;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportSummary_Mapper_Test
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
		final GPLRReportSummary reportPart = GPLRReportSummary.builder()
				.localCurrency(CurrencyCode.EUR)
				.foreignCurrency(CurrencyCode.USD)
				.salesLC(Amount.of(1, CurrencyCode.EUR))
				.salesFC(Amount.of(2, CurrencyCode.USD))
				.taxesLC(Amount.of(3, CurrencyCode.EUR))
				.estimatedLC(Amount.of(4, CurrencyCode.EUR))
				.estimatedFC(Amount.of(5, CurrencyCode.USD))
				.cogsLC(Amount.of(6, CurrencyCode.EUR))
				.chargesLC(Amount.of(7, CurrencyCode.EUR))
				.chargesFC(Amount.of(8, CurrencyCode.USD))
				.profitOrLossLC(Amount.of(9, CurrencyCode.EUR))
				.profitRate(Percent.of("33.34"))
				.build();

		final I_GPLR_Report_Summary record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Summary.class);
		GPLRReportSummary_Mapper.updateRecord(record, reportPart, orgId);
		final GPLRReportSummary reportPart2 = GPLRReportSummary_Mapper.fromRecord(record);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}
}