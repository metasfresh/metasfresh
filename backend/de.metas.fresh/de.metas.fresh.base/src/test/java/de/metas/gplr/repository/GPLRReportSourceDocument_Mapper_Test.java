package de.metas.gplr.repository;

import de.metas.currency.CurrencyCode;
import de.metas.gplr.model.GPLRCurrencyInfo;
import de.metas.gplr.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.model.GPLRReportSourceDocument;
import de.metas.gplr.model.GPLRSectionCodeRenderedString;
import de.metas.invoice.InvoiceId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
public class GPLRReportSourceDocument_Mapper_Test
{
	private static final Function<OrgId, ZoneId> orgId2timeZoneMapper = orgId -> ZoneId.of("Europe/Madrid");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private LocalDateAndOrgId localDateAndOrgId(String localDate, OrgId orgId) {return LocalDateAndOrgId.ofLocalDate(LocalDate.parse(localDate), orgId);}

	@Test
	void save_load()
	{
		final OrgId orgId = OrgId.ofRepoId(111);
		final GPLRReportSourceDocument reportPart = GPLRReportSourceDocument.builder()
				.invoiceId(InvoiceId.ofRepoId(123))
				.orgId(orgId)
				.departmentName("departmentName")
				.sectionCode(GPLRSectionCodeRenderedString.ofNullableRenderedString("sectionCode"))
				.documentNo("InvoiceDocNo")
				.invoiceDocTypeName("invoiceDocTypeName")
				.createdByName("createdByName")
				.documentDate(localDateAndOrgId("2023-05-10", orgId))
				.created(localDateAndOrgId("2023-05-11", orgId))
				//.product() // TODO
				.paymentTerm(GPLRPaymentTermRenderedString.ofRenderedString("paymentTerm"))
				.dueDate(localDateAndOrgId("2023-05-21", orgId))
				.currencyInfo(GPLRCurrencyInfo.builder()
						.currencyRate(new BigDecimal("12.3456"))
						.foreignCurrency(CurrencyCode.ofThreeLetterCode("RON"))
						.fecDocumentNo("fecDocumentNo")
						.build())
				.build();

		final I_GPLR_Report record = InterfaceWrapperHelper.newInstance(I_GPLR_Report.class);
		GPLRReportSourceDocument_Mapper.updateRecord(record, reportPart, orgId2timeZoneMapper);
		final GPLRReportSourceDocument reportPart2 = GPLRReportSourceDocument_Mapper.fromRecord(record, orgId2timeZoneMapper);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}
}
