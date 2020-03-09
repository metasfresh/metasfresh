package de.metas.rest_api.ordercandidates.impl;

import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_Bill_Location_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_OLCand_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_Location_ID;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.MimeType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocBaseAndSubType;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandWithUOMForTUsCapacityProvider;
import de.metas.ordercandidate.spi.impl.DefaultOLCandValidator;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.attachment.JsonAttachmentType;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.rest_api.common.JsonErrorItem;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.common.SyncAdvise.IfNotExists;
import de.metas.rest_api.ordercandidates.request.JSONPaymentRule;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest.OrderDocType;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo.Type;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.ordercandidates.response.JsonAttachment;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.rest_api.ordercandidates.response.JsonOLCandCreateBulkResponse;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.utils.PermissionService;
import de.metas.rest_api.utils.PermissionServiceFactories;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class OrderCandidatesRestControllerImplTest
{
	private static final String DATA_SOURCE_INTERNALNAME = "SOURCE.de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.ImportInvoice440RestController";
	private static final String DATA_DEST_INVOICECANDIDATE = "DEST.de.metas.invoicecandidate";

	private TestMasterdata testMasterdata;

	private static final String UOM_CODE = "MJ";
	private UomId uomId;

	private CurrencyId currencyId_EUR = CurrencyId.ofRepoId(12345);

	private static final String COUNTRY_CODE_DE = "DE";
	private CountryId countryId_DE;

	private static final DocBaseAndSubType DOCTYPE_SALES_INVOICE = DocBaseAndSubType.of("ARI", "KV");

	private OrderCandidatesRestControllerImpl orderCandidatesRestControllerImpl;

	private OLCandBL olCandBL;

	@BeforeAll
	public static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	public static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		olCandBL = new OLCandBL(new BPartnerOrderParamsRepository());
		Services.registerService(IOLCandBL.class, olCandBL);

		{ // create the master data requested to process the data from our json file
			testMasterdata = new TestMasterdata();

			final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
			orgInfo.setAD_Org_ID(OrgId.ANY.getRepoId());
			orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
			orgInfo.setTimeZone(ZoneId.of("Europe/Berlin").getId());
			saveRecord(orgInfo);

			countryId_DE = testMasterdata.createCountry(COUNTRY_CODE_DE);

			uomId = testMasterdata.createUOM(UOM_CODE);

			testMasterdata.createDocType(DOCTYPE_SALES_INVOICE);

			testMasterdata.createDataSource(DATA_SOURCE_INTERNALNAME);
			testMasterdata.createDataSource(DATA_DEST_INVOICECANDIDATE);

			testMasterdata.createShipper("DPD");

			testMasterdata.createSalesRep("SalesRep");

			testMasterdata.createDocType(DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_SalesOrder,
					X_C_DocType.DOCSUBTYPE_StandardOrder));

			testMasterdata.createDocType(DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_SalesOrder,
					X_C_DocType.DOCSUBTYPE_PrepayOrder));
		}

		final CurrencyService currencyService = new CurrencyService();
		final DocTypeService docTypeService = new DocTypeService();
		final JsonConverters jsonConverters = new JsonConverters(currencyService, docTypeService);

		final PermissionService permissionService = Mockito.mock(PermissionService.class);
		Mockito.doReturn(OrgId.ANY).when(permissionService).getDefaultOrgId();

		orderCandidatesRestControllerImpl = new OrderCandidatesRestControllerImpl(
				jsonConverters,
				new OLCandRepository());
		orderCandidatesRestControllerImpl.setPermissionServiceFactory(PermissionServiceFactories.singleton(permissionService));

		LogManager.setLoggerLevel(orderCandidatesRestControllerImpl.getClass(), Level.ALL);
	}

	// NOTE: Shall be called programmatically by each test
	private void startInterceptors()
	{
		final DefaultOLCandValidator defaultOLCandValidator = new DefaultOLCandValidator(
				olCandBL,
				new DummyOLCandWithUOMForTUsCapacityProvider());

		final OLCandRegistry olCandRegistry = new OLCandRegistry(
				Optional.empty(),
				Optional.empty(),
				Optional.of(ImmutableList.of(defaultOLCandValidator)));
		final OLCandValidatorService olCandValidatorService = new OLCandValidatorService(olCandRegistry);

		final IModelInterceptorRegistry registry = Services.get(IModelInterceptorRegistry.class);
		registry.addModelInterceptor(new de.metas.ordercandidate.modelvalidator.C_OLCand(olCandValidatorService));
	}

	@Test
	public void extractTags()
	{
		assertThat(invokeWith(ImmutableList.of())).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1"))).isEmpty();

		assertThat(invokeWith(ImmutableList.of("n1", "v1"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2"))).containsEntry("n1", "v1");

		assertThat(invokeWith(ImmutableList.of("n1", "v1", "n2", "v2"))).containsEntry("n1", "v1").containsEntry("n2", "v2");
	}

	private ImmutableMap<String, String> invokeWith(final ImmutableList<String> of)
	{
		return orderCandidatesRestControllerImpl.extractTags(of);
	}

	@Test
	public void createOrderLineCandidates()
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		testMasterdata.createCountry("CH");

		final JsonOLCandCreateBulkRequest bulkRequestFromFile = JsonOLCandUtil.fromResource("/JsonOLCandCreateBulkRequest.json");
		assertThat(bulkRequestFromFile.getRequests()).hasSize(21); // guards
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getBpartner().getName()).isEqualTo("Krankenkasse AG"));
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getBpartner().getExternalId().getValue()).isEqualTo("EAN-7634567890000"));
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getLocation().getGln()).isEqualTo("7634567890000"));

		final SyncAdvise ifNotExistsCreateAdvise = SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build();

		final JsonOLCandCreateBulkRequest bulkRequest = bulkRequestFromFile
				.withOrgSyncAdvise(ifNotExistsCreateAdvise)
				.withBPartnersSyncAdvise(ifNotExistsCreateAdvise)
				.withProductsSyncAdvise(ifNotExistsCreateAdvise);

		// invoke the method under test
		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(bulkRequest)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(21);
		assertThat(olCands).allSatisfy(c -> assertThat(c.getPoReference()).isEqualTo("2009_01:001")); // this is the "invoice-ID as given by the examples file
		assertThat(olCands).allSatisfy(c -> assertThat(c.getExternalHeaderId()).isEqualTo("2011234567890_2009_01:001"));

		for (int i = 1; i <= olCands.size(); i++)
		{
			// the externalLineId is made up of the invoice reference_id, the biller's EAN, the recipient's EAN and the service's (line-)id
			final JsonOLCand olCand = olCands.get(i - 1);
			final JsonOLCandCreateRequest request = bulkRequest.getRequests().get(i - 1);

			assertThat(olCand.getExternalLineId()).isEqualTo("2009_01:001_EAN-2011234567890_EAN-7634567890000_" + i);

			final I_M_Product productRecord = load(olCand.getProductId(), I_M_Product.class);
			assertThat(productRecord.getValue()).isEqualTo(request.getProduct().getCode());

			final I_C_UOM uom = uomDAO.getById(productRecord.getC_UOM_ID());

			assertThat(uom.getX12DE355()).isEqualTo(request.getProduct().getUomCode());
		}
		expect(olCands).toMatchSnapshot();
	}

	/**
	 * Asserts that every {@link AttachmentEntry.Type} has a matching {@link JsonAttachment.Type} and vice versa
	 */
	@Test
	public void jsonAttachmentTypes()
	{
		for (final JsonAttachmentType jsonAttachmentEntryType : JsonAttachmentType.values())
		{
			final AttachmentEntry.Type attachmentEntryType = AttachmentEntry.Type.valueOf(jsonAttachmentEntryType.toString());
			assertThat(attachmentEntryType.toString()).isEqualTo(jsonAttachmentEntryType.toString());
		}

		for (final AttachmentEntry.Type attachmentEntryType : AttachmentEntry.Type.values())
		{
			final JsonAttachmentType jsonAttachmentType = JsonAttachmentType.valueOf(attachmentEntryType.toString());
			assertThat(jsonAttachmentType.toString()).isEqualTo(attachmentEntryType.toString());
		}
	}

	@Test
	public void toJsonAttachment_Type_URL() throws Exception
	{
		final AttachmentEntry attachmentEntry = AttachmentEntry
				.builder()
				.id(AttachmentEntryId.ofRepoId(10))
				.type(AttachmentEntry.Type.URL)
				.url(new URI("https://metasfresh.com"))
				.mimeType(MimeType.TYPE_TextPlain)
				.build();

		final JsonAttachment jsonAttachment = OrderCandidatesRestControllerImpl.toJsonAttachment(
				"externalReference",
				"dataSourceName",
				attachmentEntry);

		assertThat(jsonAttachment.getType().toString()).isEqualTo(AttachmentEntry.Type.URL.toString());

		expect(jsonAttachment).toMatchSnapshot();
	}

	@Test
	public void testDateOrdered()
	{
		testMasterdata.prepareBPartnerAndLocation()
				.bpValue("bpCode")
				.countryId(countryId_DE)
				.build();

		testMasterdata.createProduct("productCode", uomId);

		testDateOrdered(null);
		testDateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 1));
		testDateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 2));
		testDateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 30));
	}

	public void testDateOrdered(@Nullable final LocalDate dateOrdered)
	{
		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(dateOrdered)
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 5))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.build())
				.invoiceDocType(JsonDocTypeInfo.builder()
						.docBaseType("ARI")
						.docSubType("KV")
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")
				.build());

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);

		final JsonOLCand olCand = olCands.get(0);
		assertThat(olCand.getDateOrdered()).isEqualTo(dateOrdered);
	}

	@Test
	public void error_NoBPartnerFound()
	{
		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 5))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.build())
				.invoiceDocType(JsonDocTypeInfo.builder()
						.docBaseType("ARI")
						.docSubType("KV")
						.build())
				.build());

		final ResponseEntity<JsonOLCandCreateBulkResponse> response = orderCandidatesRestControllerImpl.createOrderLineCandidates(request);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		final JsonOLCandCreateBulkResponse responseBody = response.getBody();
		assertThat(responseBody.isError()).isTrue();

		final JsonErrorItem error = responseBody.getError();
		assertThat(error.getMessage()).contains("Found no existing BPartner");
	}

	@Test
	public void test_CreateProductPrice_WarehouseDestId()
	{
		//
		// Masterdata: pricing
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem("pricingSystemCode");
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.createPricingRules();

		//
		// Masterdata: BPartner & Location
		testMasterdata.prepareBPartnerAndLocation()
				.bpValue("bpCode")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("gln1"))
				.build();

		//
		// Masterdata: Warehouse
		final WarehouseId testWarehouseDestId = testMasterdata.createWarehouse("testWarehouseDest");

		startInterceptors();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 10))
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 15))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE)
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln1")
								.build())
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")
				.warehouseDestCode("testWarehouseDest")
				.pricingSystemCode("pricingSystemCode")
				.invoiceDocType(JsonDocTypeInfo.builder()
						.docBaseType("ARI")
						.docSubType("KV")
						.build())
				.build());

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);

		final JsonOLCand olCand = olCands.get(0);
		assertThat(olCand.getPrice()).isEqualByComparingTo(new BigDecimal("13.24"));
		assertThat(olCand.getWarehouseDestId()).isEqualTo(testWarehouseDestId.getRepoId());
		assertThat(olCand.getPricingSystemId()).isEqualTo(pricingSystemId.getRepoId());

		expect(olCand).toMatchSnapshot();

		final I_C_OLCand olCandRecord = load(olCand.getId(), I_C_OLCand.class);
		assertThat(olCandRecord.getM_PricingSystem_ID()).isEqualByComparingTo(pricingSystemId.getRepoId());
	}

	@Test
	public void test_CreateProductPrice_WarehouseDestId_DirectDebit()
	{
		//
		// Masterdata: pricing
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem("pricingSystemCode");
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.createPricingRules();

		//
		// Masterdata: BPartner & Location
		testMasterdata.prepareBPartnerAndLocation()
				.bpValue("bpCode")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("gln1"))
				.build();

		//
		// Masterdata: Warehouse
		final WarehouseId testWarehouseDestId = testMasterdata.createWarehouse("testWarehouseDest");

		startInterceptors();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 10))
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 15))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE)
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln1")
								.build())
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.DirectDebit)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")
				.warehouseDestCode("testWarehouseDest")
				.invoiceDocType(JsonDocTypeInfo.builder()
						.docBaseType("ARI")
						.docSubType("KV")
						.build())
				.build());

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);

		final JsonOLCand olCand = olCands.get(0);

		assertThat(olCand.getPrice()).isEqualByComparingTo(new BigDecimal("13.24"));
		assertThat(olCand.getWarehouseDestId()).isEqualTo(testWarehouseDestId.getRepoId());

		expect(olCand).toMatchSnapshot();
	}

	@Test
	public void test_sameBPartner_DifferentLocations()
	{
		// Masterdata
		testMasterdata.createProduct("productCode", uomId);

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 10))
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 15))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.syncAdvise(SyncAdvise.READ_ONLY)
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.name("bpName")
								.companyName("bpCompanyName")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln-ship")
								.countryCode("DE")
								.build())
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln-bill")
								.countryCode("DE")
								.build())
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln-dropShip")
								.countryCode("DE")
								.build())
						.build())
				.handOverBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln-handOver")
								.countryCode("DE")
								.build())
						.build())
				.build());

		// invoke the mthod under test
		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);

		final JsonOLCand olCand = olCands.get(0);

		final MetasfreshId bpartnerMetasfreshId = olCand.getBpartner().getBpartner().getMetasfreshId();
		assertThat(olCand.getBillBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId);
		assertThat(olCand.getDropShipBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId); // same bpartner, but different location
		assertThat(olCand.getHandOverBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId);

		expect(olCand).toMatchSnapshot();
	}

	@Test
	public void test_no_location_specified()
	{
		//
		// Masterdata: pricing
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem("pricingSystemCode");
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.createPricingRules();

		//
		// Masterdata: BPartner & Location
		final BPartnerLocationId bpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("mainPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId billBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("billPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId dropShipBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("droptShipPartner")
				.salesPricingSystemId(pricingSystemId)
				.gln(GLN.ofString("redHerring!"))
				.countryId(countryId_DE)
				.build();
		final BPartnerLocationId expectedDropShipLocation = testMasterdata.prepareBPartnerLocation().bpartnerId(dropShipBpartnerAndLocation.getBpartnerId())
				.countryId(countryId_DE)
				.gln(GLN.ofString("expectedDropShipLocation"))
				.build();

		startInterceptors();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 10))
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 15))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")

				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE)
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("mainPartner")
								.build()) // no location specified!
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("billPartner")
								.build()) // again, no location specified!
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("droptShipPartner")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("expectedDropShipLocation")
								.build())
						.build())

				.build());

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);
		final JsonOLCand olCand = olCands.get(0);

		// assert That the OLCand record has the C_BPartner_Location_ID that was not specified in JSON, but looked up
		final List<I_C_OLCand> olCandRecords = POJOLookupMap.get().getRecords(I_C_OLCand.class);
		assertThat(olCandRecords).hasSize(1)
				.extracting(COLUMNNAME_C_OLCand_ID)
				.contains(olCand.getId());

		assertThat(olCandRecords).extracting(COLUMNNAME_C_BPartner_ID, COLUMNNAME_C_BPartner_Location_ID)
				.contains(tuple(bpartnerAndLocation.getBpartnerId().getRepoId(), bpartnerAndLocation.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_Bill_BPartner_ID, COLUMNNAME_Bill_Location_ID)
				.contains(tuple(billBpartnerAndLocation.getBpartnerId().getRepoId(), billBpartnerAndLocation.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_DropShip_BPartner_ID, COLUMNNAME_DropShip_Location_ID)
				.contains(tuple(dropShipBpartnerAndLocation.getBpartnerId().getRepoId(), expectedDropShipLocation.getRepoId()));

		expect(olCand).toMatchSnapshot();
	}

	@Test
	public void test_no_location_specified_DirectDebit()
	{
		//
		// Masterdata: pricing
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem("pricingSystemCode");
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.createPricingRules();

		//
		// Masterdata: BPartner & Location
		final BPartnerLocationId bpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("mainPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId billBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("billPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId dropShipBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.bpValue("droptShipPartner")
				.salesPricingSystemId(pricingSystemId)
				.gln(GLN.ofString("redHerring!"))
				.countryId(countryId_DE)
				.build();
		final BPartnerLocationId expectedDropShipLocation = testMasterdata.prepareBPartnerLocation().bpartnerId(dropShipBpartnerAndLocation.getBpartnerId())
				.countryId(countryId_DE)
				.gln(GLN.ofString("expectedDropShipLocation"))
				.build();

		startInterceptors();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
				.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 10))
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 15))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.DirectDebit)
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")

				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE)
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("mainPartner")
								.build()) // no location specified!
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("billPartner")
								.build()) // again, no location specified!
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("droptShipPartner")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("expectedDropShipLocation")
								.build())
						.build())

				.build());

		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);
		final JsonOLCand olCand = olCands.get(0);

		// assert That the OLCand record has the C_BPartner_Location_ID that was not specified in JSON, but looked up
		final List<I_C_OLCand> olCandRecords = POJOLookupMap.get().getRecords(I_C_OLCand.class);
		assertThat(olCandRecords).hasSize(1)
				.extracting(COLUMNNAME_C_OLCand_ID)
				.contains(olCand.getId());

		assertThat(olCandRecords).extracting(COLUMNNAME_C_BPartner_ID, COLUMNNAME_C_BPartner_Location_ID)
				.contains(tuple(bpartnerAndLocation.getBpartnerId().getRepoId(), bpartnerAndLocation.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_Bill_BPartner_ID, COLUMNNAME_Bill_Location_ID)
				.contains(tuple(billBpartnerAndLocation.getBpartnerId().getRepoId(), billBpartnerAndLocation.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_DropShip_BPartner_ID, COLUMNNAME_DropShip_Location_ID)
				.contains(tuple(dropShipBpartnerAndLocation.getBpartnerId().getRepoId(), expectedDropShipLocation.getRepoId()));

		expect(olCand).toMatchSnapshot();
	}

	private static class DummyOLCandWithUOMForTUsCapacityProvider implements IOLCandWithUOMForTUsCapacityProvider
	{
		@Override
		public boolean isProviderNeededForOLCand(I_C_OLCand olCand)
		{
			return false;
		}

		@Override
		public Quantity computeQtyItemCapacity(I_C_OLCand olCand)
		{
			return null;
		}
	}
}
