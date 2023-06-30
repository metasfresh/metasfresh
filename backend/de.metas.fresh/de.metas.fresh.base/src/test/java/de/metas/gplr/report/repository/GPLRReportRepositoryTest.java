package de.metas.gplr.report.repository;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRBPartnerName;
import de.metas.gplr.report.model.GPLRCurrencyInfo;
import de.metas.gplr.report.model.GPLRIncotermsInfo;
import de.metas.gplr.report.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.report.model.GPLRReport;
import de.metas.gplr.report.model.GPLRReportCharge;
import de.metas.gplr.report.model.GPLRReportLineItem;
import de.metas.gplr.report.model.GPLRReportNote;
import de.metas.gplr.report.model.GPLRReportPurchaseOrder;
import de.metas.gplr.report.model.GPLRReportSalesOrder;
import de.metas.gplr.report.model.GPLRReportShipment;
import de.metas.gplr.report.model.GPLRReportSourceDocument;
import de.metas.gplr.report.model.GPLRReportSummary;
import de.metas.gplr.report.model.GPLRSectionCodeRenderedString;
import de.metas.gplr.report.model.GPLRShipperRenderedString;
import de.metas.gplr.report.model.GPLRWarehouseName;
import de.metas.invoice.InvoiceId;
import de.metas.location.ICountryCodeFactory;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class GPLRReportRepositoryTest
{
	private GPLRReportRepository gplrReportRepository;
	private ICountryCodeFactory countryCodeFactory;

	private I_C_UOM uomEach;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		gplrReportRepository = new GPLRReportRepository();
		countryCodeFactory = Services.get(ICountryCodeFactory.class);
		uomEach = BusinessTestHelper.createUomEach();
		orgId = AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("Europe/Madrid"));
	}

	private LocalDateAndOrgId localDateAndOrgId(String localDate, OrgId orgId) {return LocalDateAndOrgId.ofLocalDate(LocalDate.parse(localDate), orgId);}

	private $DummyReport dummyReport()
	{
		return dummyReport0()
				.salesInvoiceId(InvoiceId.ofRepoId(123))
				.purchaseInvoiceId(InvoiceId.ofRepoId(124));
	}

	@Builder(builderMethodName = "dummyReport0", builderClassName = "$DummyReport")
	private GPLRReport generateDummyReport(
			@NonNull InvoiceId salesInvoiceId,
			@Nullable InvoiceId purchaseInvoiceId)
	{
		return GPLRReport.builder()
				.id(null)
				.created(SystemTime.asInstant())
				.sourceDocument(GPLRReportSourceDocument.builder()
						.salesInvoiceId(salesInvoiceId)
						.purchaseInvoiceId(purchaseInvoiceId)
						.orgId(orgId)
						.departmentName("departmentName")
						.sectionCode(GPLRSectionCodeRenderedString.ofNullableRenderedString("sectionCode"))
						.documentNo("InvoiceDocNo")
						.invoiceDocTypeName("invoiceDocTypeName")
						.createdByName("createdByName")
						.documentDate(localDateAndOrgId("2023-05-10", orgId))
						.created(localDateAndOrgId("2023-05-11", orgId))
						.sapProductHierarchy("sapProductHierarchy")
						.paymentTerm(GPLRPaymentTermRenderedString.ofRenderedString("paymentTerm"))
						.dueDate(localDateAndOrgId("2023-05-21", orgId))
						.currencyInfo(GPLRCurrencyInfo.builder()
								.currencyRate(new BigDecimal("12.3456"))
								.foreignCurrency(CurrencyCode.ofThreeLetterCode("RON"))
								.fecDocumentNo("fecDocumentNo")
								.build())
						.build())
				.salesOrder(GPLRReportSalesOrder.builder()
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
						.build())
				.shipments(ImmutableList.of(
						GPLRReportShipment.builder()
								.documentNo("documentNo")
								.shipTo(GPLRBPartnerName.builder()
										.code("bp_code")
										.name("bp_name")
										.build())
								.shipToCountry(countryCodeFactory.getCountryCodeByAlpha2("RO"))
								.warehouse(GPLRWarehouseName.builder()
										.code("wh_code")
										.name("wh_name")
										.build())
								.movementDate(localDateAndOrgId("2022-06-11", orgId))
								.incoterms(GPLRIncotermsInfo.builder()
										.code("incoterms_code")
										.location("incoterms_location")
										.build())
								.shipper(GPLRShipperRenderedString.ofShipperName("shipper_name"))
								.build()
				))
				.purchaseOrders(ImmutableList.of(
						GPLRReportPurchaseOrder.builder()
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
								.build()
				))
				.summary(GPLRReportSummary.builder()
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
						.build())
				.lineItems(ImmutableList.of(
						GPLRReportLineItem.builder()
								.documentNo("documentNo")
								.lineCode("lineCode")
								.description("description")
								.qty(Quantity.of("123.456", uomEach))
								.priceFC(Amount.of("1", CurrencyCode.USD))
								.amountFC(Amount.of("2", CurrencyCode.USD))
								.amountLC(Amount.of("3", CurrencyCode.EUR))
								.batchNo("batchNo")
								.build()
				))
				.charges(ImmutableList.of(
						GPLRReportCharge.builder()
								.purchaseOrderDocumentNo("purchaeOrderDocumentNo")
								.orderLineNo("orderLineNo")
								.costTypeName("costTypeName")
								.vendor(GPLRBPartnerName.builder()
										.code("payee_code")
										.name("payee_name")
										.build())
								.amountFC(Amount.of("1", CurrencyCode.USD))
								.amountLC(Amount.of("2", CurrencyCode.EUR))
								.build()
				))
				.otherNotes(ImmutableList.of(
						GPLRReportNote.builder()
								.sourceDocument("sourceDocument")
								.text("line1\nline2\nline3")
								.build()
				))
				//
				.build();
	}

	@Test
	void createNew_getById()
	{
		final GPLRReport report = dummyReport().build();

		gplrReportRepository.createNew(report);
		System.out.println("Report: " + report);
		assertThat(report.getId()).isNotNull();

		final GPLRReport report2 = gplrReportRepository.getById(report.getId());
		assertThat(report2).usingRecursiveComparison().isEqualTo(report);
	}

	@Test
	void isReportGeneratedForInvoice()
	{
		final InvoiceId salesInvoiceId = InvoiceId.ofRepoId(1);
		final InvoiceId purchaseInvoiceId = InvoiceId.ofRepoId(2);

		assertThat(gplrReportRepository.isReportGeneratedForInvoice(salesInvoiceId)).isFalse();
		assertThat(gplrReportRepository.isReportGeneratedForInvoice(purchaseInvoiceId)).isFalse();

		gplrReportRepository.createNew(
				dummyReport()
						.salesInvoiceId(salesInvoiceId)
						.purchaseInvoiceId(purchaseInvoiceId)
						.build());

		assertThat(gplrReportRepository.isReportGeneratedForInvoice(salesInvoiceId)).isTrue();
		assertThat(gplrReportRepository.isReportGeneratedForInvoice(purchaseInvoiceId)).isTrue();
	}

}