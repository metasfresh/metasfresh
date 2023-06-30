package de.metas.gplr.report.repository;

import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRReportShipment;
import de.metas.gplr.report.model.GPLRShipperRenderedString;
import de.metas.gplr.report.model.GPLRWarehouseName;
import de.metas.location.ICountryCodeFactory;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_GPLR_Report_Shipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
class GPLRReportShipment_Mapper_Test
{
	private static final Function<OrgId, ZoneId> orgId2timeZoneMapper = orgId -> ZoneId.of("Europe/Madrid");
	private ICountryCodeFactory countryCodeFactory;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		countryCodeFactory = Services.get(ICountryCodeFactory.class);
	}

	@SuppressWarnings("SameParameterValue")
	private LocalDateAndOrgId localDateAndOrgId(String localDate, OrgId orgId) {return LocalDateAndOrgId.ofLocalDate(LocalDate.parse(localDate), orgId);}

	@Test
	void save_load()
	{
		final OrgId orgId = OrgId.ofRepoId(111);
		final GPLRReportShipment reportPart = GPLRReportShipment.builder()
				.documentNo("documentNo")
				.shipTo(GPLRBPartnerName.builder()
						.code("bp_code")
						.name("bp_name")
						.build())
				.shipToCountry(countryCodeFactory.getCountryCodeByAlpha2("RO"))
				.warehouse(GPLRWarehouseName.builder()
						.code("wh_code")
						.name("wh_name")
						.externalId("wh_externalId")
						.build())
				.movementDate(localDateAndOrgId("2022-06-11", orgId))
				.incoterms(GPLRIncotermsInfo.builder()
						.code("incoterms_code")
						.location("incoterms_location")
						.build())
				.shipper(GPLRShipperRenderedString.ofShipperName("shipper_name"))
				.build();

		final I_GPLR_Report_Shipment record = InterfaceWrapperHelper.newInstance(I_GPLR_Report_Shipment.class);
		GPLRReportShipment_Mapper.updateRecord(record, reportPart, orgId, orgId2timeZoneMapper);
		final GPLRReportShipment reportPart2 = GPLRReportShipment_Mapper.fromRecord(record, orgId2timeZoneMapper, countryCodeFactory);
		assertThat(reportPart2).usingRecursiveComparison().isEqualTo(reportPart);
	}
}