package de.metas.rest_api.v1.ordercandidates.impl;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.business.BusinessTestHelper;
import de.metas.common.bpartner.v1.request.JsonRequestBPartner;
import de.metas.common.bpartner.v1.request.JsonRequestLocation;
import de.metas.common.bpartner.v1.response.JsonResponseBPartner;
import de.metas.common.ordercandidates.v1.request.BPartnerLookupAdvise;
import de.metas.common.ordercandidates.v1.request.JSONPaymentRule;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest.OrderDocType;
import de.metas.common.ordercandidates.v1.request.JsonProductInfo;
import de.metas.common.ordercandidates.v1.request.JsonProductInfo.Type;
import de.metas.common.ordercandidates.v1.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.ordercandidates.v1.response.JsonOLCand;
import de.metas.common.ordercandidates.v1.response.JsonOLCandCreateBulkResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.JsonDocTypeInfo;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.rest_api.v1.SyncAdvise;
import de.metas.common.rest_api.v1.SyncAdvise.IfNotExists;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.externalreference.rest.v1.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandSPIRegistry;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.location.OLCandLocationsUpdaterService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandWithUOMForTUsCapacityProvider;
import de.metas.ordercandidate.spi.impl.DefaultOLCandValidator;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.v1.bpartner.BPartnerEndpointService;
import de.metas.rest_api.v1.bpartner.BpartnerRestController;
import de.metas.rest_api.v1.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v1.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.security.permissions2.PermissionService;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_Bill_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_Bill_Location_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_OLCand_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_Location_ID;
import static de.metas.rest_api.v1.ordercandidates.impl.TestMasterdata.RESOURCE_PATH;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_ExternalId;

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

@ExtendWith({SnapshotExtension.class, AdempiereTestWatcher.class})
public class
OrderCandidatesRestControllerImpl_createOrderLineCandidates_Test
{
	private static final ZonedDateTime FIXED_TIME = LocalDate.parse("2020-03-16")
					.atTime(LocalTime.parse("23:07:16.193"))
					.atZone(ZoneId.of("Europe/Berlin"));

	private static final String DATA_SOURCE_INTERNALNAME = "SOURCE.de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.ImportInvoice440RestController";
	private static final String DATA_DEST_INVOICECANDIDATE = "DEST.de.metas.invoicecandidate";

	private BPartnerBL bpartnerBL;
	private TestMasterdata testMasterdata;

	private static final X12DE355 UOM_CODE = X12DE355.ofCode("MJ");
	private UomId uomId;

	private final CurrencyId currencyId_EUR = CurrencyId.ofRepoId(12345);

	private static final String COUNTRY_CODE_DE = "DE";
	private CountryId countryId_DE;
	private OrgId defaultOrgId;

	private static final DocBaseAndSubType DOCTYPE_SALES_INVOICE = DocBaseAndSubType.of("ARI", "KV");

	private OrderCandidatesRestController orderCandidatesRestControllerImpl;

	private OLCandBL olCandBL;

	private Expect expect;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		SystemTime.setFixedTimeSource(FIXED_TIME);

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);
		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		SpringContextHolder.registerJUnitBean(ProductScalePriceService.newInstanceForUnitTesting());

		olCandBL = new OLCandBL(bpartnerBL, new BPartnerOrderParamsRepository());
		Services.registerService(IOLCandBL.class, olCandBL);

		final I_AD_Org defaultOrgRecord;

		{ // create the master data requested to process the data from our json file
			testMasterdata = new TestMasterdata();

			defaultOrgRecord = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
			defaultOrgRecord.setValue("001");
			saveRecord(defaultOrgRecord);

			final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
			orgInfo.setAD_Org_ID(defaultOrgRecord.getAD_Org_ID());
			orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
			orgInfo.setTimeZone(ZoneId.of("Europe/Berlin").getId());
			saveRecord(orgInfo);

			defaultOrgId = OrgId.ofRepoId(defaultOrgRecord.getAD_Org_ID());

			countryId_DE = BusinessTestHelper.createCountry(COUNTRY_CODE_DE);

			final I_C_UOM uom = BusinessTestHelper.createUOM(UOM_CODE.getCode(), UOM_CODE);
			uomId = UomId.ofRepoId(uom.getC_UOM_ID());
			BusinessTestHelper.createProduct("ProductCode", uomId);

			testMasterdata.createDocType(DOCTYPE_SALES_INVOICE);

			testMasterdata.createDataSource(DATA_SOURCE_INTERNALNAME);
			testMasterdata.createDataSource(DATA_DEST_INVOICECANDIDATE);

			testMasterdata.createShipper("DPD");

			testMasterdata.createSalesRep("SalesRep");

			testMasterdata.createDocType(DocBaseAndSubType.of(DocBaseType.SalesOrder, X_C_DocType.DOCSUBTYPE_StandardOrder));

			testMasterdata.createDocType(DocBaseAndSubType.of(DocBaseType.SalesOrder, X_C_DocType.DOCSUBTYPE_PrepayOrder));

			testMasterdata.createPaymentTerm("paymentTermValue", "paymentTermExternalId");

			SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		}

		final CurrencyService currencyService = new CurrencyService();
		final DocTypeService docTypeService = new DocTypeService();
		final JsonConverters jsonConverters = new JsonConverters(currencyService, docTypeService);

		// bpartnerRestController
		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(bpartnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), new BPartnerCreditLimitRepository());
		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				currencyRepository,
				Mockito.mock(ExternalReferenceRestControllerService.class));
		final BpartnerRestController bpartnerRestController = new BpartnerRestController(
				new BPartnerEndpointService(jsonServiceFactory),
				jsonServiceFactory,
				new JsonRequestConsolidateService());

		orderCandidatesRestControllerImpl = new OrderCandidatesRestController(
				jsonConverters,
				new OLCandRepository(),
				bpartnerRestController,
				NoopPerformanceMonitoringService.INSTANCE);

		final PermissionService permissionService = Mockito.mock(PermissionService.class);
		Mockito.doReturn(OrgId.ofRepoId(defaultOrgRecord.getAD_Org_ID())).when(permissionService).getDefaultOrgId();
		orderCandidatesRestControllerImpl.setPermissionServiceFactory(PermissionServiceFactories.singleton(permissionService));

		LogManager.setLoggerLevel(orderCandidatesRestControllerImpl.getClass(), Level.ALL);
	}

	// NOTE: Shall be called programmatically by each test
	private void startInterceptors()
	{
		final DefaultOLCandValidator defaultOLCandValidator = new DefaultOLCandValidator(
				olCandBL,
				new DummyOLCandWithUOMForTUsCapacityProvider());

		final OLCandSPIRegistry olCandSPIRegistry = new OLCandSPIRegistry(
				Optional.empty(),
				Optional.empty(),
				Optional.of(ImmutableList.of(defaultOLCandValidator)));
		final OLCandValidatorService olCandValidatorService = new OLCandValidatorService(olCandSPIRegistry);
		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		final OLCandLocationsUpdaterService olCandLocationsUpdaterService = new OLCandLocationsUpdaterService(new DocumentLocationBL(bpartnerBL));

		final IModelInterceptorRegistry registry = Services.get(IModelInterceptorRegistry.class);
		registry.addModelInterceptor(new de.metas.ordercandidate.modelvalidator.C_OLCand(bpartnerBL, olCandValidatorService, olCandLocationsUpdaterService));
	}

	private static class DummyOLCandWithUOMForTUsCapacityProvider implements IOLCandWithUOMForTUsCapacityProvider
	{
		@Override
		public boolean isProviderNeededForOLCand(@NonNull final I_C_OLCand olCand)
		{
			return false;
		}

		@NonNull
		@Override
		public Optional<Quantity> computeQtyItemCapacity(@NonNull final I_C_OLCand olCand)
		{
			return Optional.of(Quantitys.zero(ProductId.ofRepoId(olCand.getM_Product_ID())));
		}
	}

	@Test
	void single_customer_address()
	{
		// Given
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		BusinessTestHelper.createBPGroup("DefaultGroup", true);
		BusinessTestHelper.createCountry("CH");

		// guards
		final JsonOLCandCreateBulkRequest bulkRequestFromFile = JsonOLCandUtil.loadJsonOLCandCreateBulkRequest(RESOURCE_PATH + "JsonOLCandCreateBulkRequest.json");
		assertThat(bulkRequestFromFile.getRequests()).hasSize(21); // guards
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getBpartner().getName()).isEqualTo("Krankenkasse AG"));
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getBpartner().getExternalId().getValue()).isEqualTo("EAN-7634567890000"));
		assertThat(bulkRequestFromFile.getRequests()).allSatisfy(r -> assertThat(r.getBpartner().getLocation().getGln()).isEqualTo("7634567890000"));

		final SyncAdvise ifNotExistsCreateAdvise = SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build();

		final JsonOLCandCreateBulkRequest bulkRequest = bulkRequestFromFile
				.withOrgSyncAdvise(ifNotExistsCreateAdvise)
				.withBPartnersSyncAdvise(ifNotExistsCreateAdvise)
				.withProductsSyncAdvise(ifNotExistsCreateAdvise);

		// when
		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(bulkRequest)
				.getBody();

		// then
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

			final X12DE355 x12DE355 = uomDAO.getX12DE355ById(UomId.ofRepoId(productRecord.getC_UOM_ID()));
			assertThat(x12DE355.getCode()).isEqualTo(request.getProduct().getUomCode());
		}
		expect.serializer("orderedJson").toMatchSnapshot(olCands);
	}

	@Test
	void dateOrdered()
	{
		testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("bpCode")
				.countryId(countryId_DE)
				.build();

		testMasterdata.createProduct("productCode", uomId);

		dateOrdered(null);
		dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 1));
		dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 2));
		dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 30));
	}

	private void dateOrdered(@Nullable final LocalDate dateOrdered)
	{
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bpCode");

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
						.bpartner(bpartner)
						.build())
				.invoiceDocType(JsonDocTypeInfo.builder()
						.docBaseType("ARI")
						.docSubType("KV")
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.paymentTerm("val-paymentTermValue")
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

	@Nested
	public class import_VatID
	{
		@Test
		public void notSpecified()
		{
			final JsonOLCand olCand = importOLCandWithVatId("currentVatId", null);
			final JsonResponseBPartner bpartner = olCand.getBpartner().getBpartner();
			assertThat(bpartner.getVatId()).isEqualTo("currentVatId");
		}

		@Test
		public void setToNull()
		{
			final JsonOLCand olCand = importOLCandWithVatId("currentVatId", Optional.empty());
			final JsonResponseBPartner bpartner = olCand.getBpartner().getBpartner();
			assertThat(bpartner.getVatId()).isNull();
		}

		@Test
		public void changeIt()
		{
			final JsonOLCand olCand = importOLCandWithVatId("currentVatId", Optional.of("newVatId"));
			final JsonResponseBPartner bpartner = olCand.getBpartner().getBpartner();
			assertThat(bpartner.getVatId()).isEqualTo("newVatId");
		}

		@SuppressWarnings({ "SameParameterValue", "OptionalUsedAsFieldOrParameterType" })
		private JsonOLCand importOLCandWithVatId(
				final String currentVatId,
				@Nullable final Optional<String> newVatId)
		{
			testMasterdata.prepareBPartnerAndLocation()
					.orgId(defaultOrgId)
					.bpValue("bpCode")
					.countryId(countryId_DE)
					.vatId(currentVatId)
					.build();

			testMasterdata.createProduct("productCode", uomId);

			final JsonRequestBPartner bpartner = new JsonRequestBPartner();
			bpartner.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
			bpartner.setCode("bpCode");
			if (newVatId != null)
			{
				bpartner.setVatId(newVatId.orElse(null));
			}

			final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
					.dataSource("int-" + DATA_SOURCE_INTERNALNAME)
					.dataDest("int-" + DATA_DEST_INVOICECANDIDATE)
					.dateOrdered(LocalDate.of(2019, Month.SEPTEMBER, 1))
					.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 5))
					.qty(new BigDecimal("66"))
					.externalHeaderId("externalHeaderId")
					.externalLineId("externalLineId")
					.poReference("poRef")
					.product(JsonProductInfo.builder()
							.code("productCode")
							.build())
					.bpartner(JsonRequestBPartnerLocationAndContact.builder()
							.bpartner(bpartner)
							.build())
					.invoiceDocType(JsonDocTypeInfo.builder()
							.docBaseType("ARI")
							.docSubType("KV")
							.build())
					.shipper("val-DPD")
					.salesPartnerCode("SalesRep")
					.paymentRule(JSONPaymentRule.Paypal)
					.paymentTerm("val-paymentTermValue")
					.orderDocType(OrderDocType.PrepayOrder)
					.shipper("val-DPD")
					.build());

			final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
					.createOrderLineCandidates(request)
					.getBody();

			final List<JsonOLCand> olCands = response.getResult();
			assertThat(olCands).hasSize(1);

			final JsonOLCand olCand = olCands.get(0);
			return olCand;
		}
	}

	@Test
	void error_NoBPartnerFound()
	{
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bpCode");

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
						.bpartner(bpartner)
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
		assertThat(error.getMessage()).contains("The resource with resourceName=bpartner - which is identified by resourceIdentifier=val-bpCode -  could not be found.");
	}

	@Test
	void CreateProductPrice_WarehouseDestId()
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
				.orgId(defaultOrgId)
				.bpValue("bpCode")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("gln1"))
				.build();

		//
		// Masterdata: Warehouse
		final WarehouseId testWarehouseDestId = testMasterdata.createWarehouse("testWarehouseDest");

		startInterceptors();

		final JsonRequestLocation bpartherLocation = new JsonRequestLocation();
		bpartherLocation.setGln("gln1");

		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bpCode");

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
						.uomCode(UOM_CODE.getCode())
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(bpartner)
						.location(bpartherLocation)
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.Paypal)
				.paymentTerm("ext-paymentTermExternalId")
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

		expect.serializer("orderedJson").toMatchSnapshot(olCand);

		final I_C_OLCand olCandRecord = load(olCand.getId(), I_C_OLCand.class);
		assertThat(olCandRecord.getM_PricingSystem_ID()).isEqualByComparingTo(pricingSystemId.getRepoId());
	}

	@Test
	void CreateProductPrice_WarehouseDestId_DirectDebit()
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
				.orgId(defaultOrgId)
				.bpValue("bpCode")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("gln1"))
				.build();

		//
		// Masterdata: Warehouse
		final WarehouseId testWarehouseDestId = testMasterdata.createWarehouse("testWarehouseDest");

		startInterceptors();

		final JsonRequestLocation bpartherLocation = new JsonRequestLocation();
		bpartherLocation.setGln("gln1");

		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bpCode");

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
						.uomCode(UOM_CODE.getCode())
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(bpartner)
						.location(bpartherLocation)
						.build())
				.shipper("val-DPD")
				.salesPartnerCode("SalesRep")
				.paymentRule(JSONPaymentRule.DirectDebit)
				.paymentTerm("val-paymentTermValue")
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

		expect.serializer("orderedJson").toMatchSnapshot(olCand);
	}

	@Test
	void sameBPartner_DifferentLocations()
	{
		// Masterdata
		testMasterdata.createProduct("productCode", uomId);

		final JsonRequestLocation bpartherLocation = new JsonRequestLocation();
		bpartherLocation.setGln("gln-ship");
		bpartherLocation.setCountryCode("DE");

		final JsonRequestLocation billPartherLocation = new JsonRequestLocation();
		billPartherLocation.setGln("gln-bill");
		billPartherLocation.setCountryCode("DE");

		final JsonRequestLocation dropShipPartherLocation = new JsonRequestLocation();
		dropShipPartherLocation.setGln("gln-dropShip");
		dropShipPartherLocation.setCountryCode("DE");

		final JsonRequestLocation handOverPartherLocation = new JsonRequestLocation();
		handOverPartherLocation.setGln("gln-handOver");
		handOverPartherLocation.setCountryCode("DE");

		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bpCode");
		bpartner.setName("bpName");
		bpartner.setCompanyName("bpCompanyName");
		bpartner.setGroup("bpGroupName");

		final JsonRequestBPartner billPartner = new JsonRequestBPartner();
		billPartner.setCode("bpCode");

		final JsonRequestBPartner dropShipBPartner = new JsonRequestBPartner();
		dropShipBPartner.setCode("bpCode");

		final JsonRequestBPartner handOverBPartner = new JsonRequestBPartner();
		handOverBPartner.setCode("bpCode");

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
				.paymentTerm("val-paymentTermValue")
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(bpartner)
						.location(bpartherLocation)
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(billPartner)
						.location(billPartherLocation)
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(dropShipBPartner)
						.location(dropShipPartherLocation)
						.build())
				.handOverBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
						.bpartner(handOverBPartner)
						.location(handOverPartherLocation)
						.build())
				.build());

		// invoke the method under test
		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);

		final JsonOLCand olCand = olCands.get(0);

		final JsonMetasfreshId bpartnerMetasfreshId = JsonMetasfreshId.of(olCand.getBpartner().getBpartner().getMetasfreshId().getValue());
		assertThat(olCand.getBillBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId);
		assertThat(olCand.getDropShipBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId); // same bpartner, but different location
		assertThat(olCand.getHandOverBPartner().getBpartner().getMetasfreshId()).isEqualTo(bpartnerMetasfreshId);

		expect.serializer("orderedJson").toMatchSnapshot(olCand);
	}

	@Test
	void no_location_specified()
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
				.orgId(defaultOrgId)
				.bpValue("mainPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId billBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("billPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId dropShipBpartnerAndLocation = testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("dropShipPartner")
				.salesPricingSystemId(pricingSystemId)
				.gln(GLN.ofString("redHerring!"))
				.countryId(countryId_DE)
				.build();
		final BPartnerLocationId expectedDropShipLocation = testMasterdata.prepareBPartnerLocation()
				.orgId(defaultOrgId)
				.bpartnerId(dropShipBpartnerAndLocation.getBpartnerId())
				.countryId(countryId_DE)
				.gln(GLN.ofString("expectedDropShipLocation"))
				.build();

		startInterceptors();

		final JsonRequestLocation dropShipLocation = new JsonRequestLocation();
		dropShipLocation.setGln("expectedDropShipLocation");

		final JsonRequestBPartner mainPartner = new JsonRequestBPartner();
		mainPartner.setCode("mainPartner");

		final JsonRequestBPartner billPartner = new JsonRequestBPartner();
		billPartner.setCode("billPartner");

		final JsonRequestBPartner dropShipPartner = new JsonRequestBPartner();
		dropShipPartner.setCode("dropShipPartner");

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
				.paymentTerm("val-paymentTermValue")
				.orderDocType(OrderDocType.PrepayOrder)
				.shipper("val-DPD")

				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE.getCode())
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(mainPartner) // no location specified!
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(billPartner) // again, no location specified!
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(dropShipPartner)
						.location(dropShipLocation)
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

		expect.serializer("orderedJson").toMatchSnapshot(olCand);
	}

	@Test
	void no_location_specified_DirectDebit()
	{
		// given
		//
		// Masterdata: pricing
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem("pricingSystemCode");
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.createPricingRules();

		//
		// Masterdata: BPartner & Location
		final BPartnerLocationId bpartnerLocationId = testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("mainPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId billBpartnerLocationId = testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("billPartner")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.build();

		final BPartnerLocationId dropShipBpartnerLocationId = testMasterdata.prepareBPartnerAndLocation()
				.orgId(defaultOrgId)
				.bpValue("dropShipPartner")
				.salesPricingSystemId(pricingSystemId)
				.gln(GLN.ofString("redHerring!"))
				.countryId(countryId_DE)
				.build();
		final BPartnerLocationId expectedDropShipLocationId = testMasterdata.prepareBPartnerLocation().bpartnerId(dropShipBpartnerLocationId.getBpartnerId())
				.orgId(defaultOrgId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("expectedDropShipLocation"))
				.build();

		startInterceptors();

		final JsonRequestLocation dropShipLocation = new JsonRequestLocation();
		dropShipLocation.setGln("expectedDropShipLocation");

		final JsonRequestBPartner mainPartner = new JsonRequestBPartner();
		mainPartner.setCode("mainPartner");

		final JsonRequestBPartner billPartner = new JsonRequestBPartner();
		billPartner.setCode("billPartner");

		final JsonRequestBPartner dropShipPartner = new JsonRequestBPartner();
		dropShipPartner.setCode("dropShipPartner");

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
				.paymentTerm("val-paymentTermValue")

				.product(JsonProductInfo.builder()
						.code("productCode")
						.name("productName")
						.type(Type.ITEM)
						.uomCode(UOM_CODE.getCode())
						.priceStd(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())

				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.bpartner(mainPartner) // no location specified!
						.build())
				.billBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.bpartner(billPartner) // again, no location specified!
						.build())
				.dropShipBPartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartnerLookupAdvise(BPartnerLookupAdvise.Code)
						.bpartner(dropShipPartner)
						.location(dropShipLocation)
						.build())

				.build());

		// when
		final JsonOLCandCreateBulkResponse response = orderCandidatesRestControllerImpl
				.createOrderLineCandidates(request)
				.getBody();

		// then
		final List<JsonOLCand> olCands = response.getResult();
		assertThat(olCands).hasSize(1);
		final JsonOLCand olCand = olCands.get(0);
		assertThat(olCand.getBillBPartner().getBpartner().getChangeInfo().getCreatedMillis()).isEqualTo(FIXED_TIME.toInstant().toEpochMilli());
		assertThat(olCand.getBillBPartner().getBpartner().getChangeInfo().getLastUpdatedMillis()).isEqualTo(FIXED_TIME.toInstant().toEpochMilli());

		// assert That the OLCand record has the C_BPartner_Location_ID that was not specified in JSON, but looked up
		final List<I_C_OLCand> olCandRecords = POJOLookupMap.get().getRecords(I_C_OLCand.class);
		assertThat(olCandRecords).hasSize(1)
				.extracting(COLUMNNAME_C_OLCand_ID)
				.contains(olCand.getId());

		assertThat(olCandRecords).extracting(COLUMNNAME_C_BPartner_ID, COLUMNNAME_C_BPartner_Location_ID)
				.contains(tuple(bpartnerLocationId.getBpartnerId().getRepoId(), bpartnerLocationId.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_Bill_BPartner_ID, COLUMNNAME_Bill_Location_ID)
				.contains(tuple(billBpartnerLocationId.getBpartnerId().getRepoId(), billBpartnerLocationId.getRepoId()));

		assertThat(olCandRecords).extracting(COLUMNNAME_DropShip_BPartner_ID, COLUMNNAME_DropShip_Location_ID)
				.contains(tuple(dropShipBpartnerLocationId.getBpartnerId().getRepoId(), expectedDropShipLocationId.getRepoId()));

		expect.serializer("orderedJson").toMatchSnapshot(olCand);
	}

	/**
	 * Uses a production-based JSON to make sure that billTo and shipTo flags end up the in C_BPartner_Location the ways they should.
	 */
	@Test
	void billToDefault_newBPartner()
	{
		// given
		BusinessTestHelper.createBPGroup("DefaultGroup", true);
		testMasterdata.createDataSource("SOURCE.de.metas.rest_api.ordercandidates.impl.OrderCandidatesRestControllerImpl_B2C");
		testMasterdata.createDataSource("DEST.de.metas.ordercandidate");
		testMasterdata.createPricingSystem("vk3");
		testMasterdata.createShipper("Standard");
		testMasterdata.createSalesRep("ABC-DEF-12345");
		testMasterdata.createDocType(DocBaseAndSubType.of("ARI"));

		final JsonOLCandCreateRequest request = JsonOLCandUtil.loadJsonOLCandCreateRequest(RESOURCE_PATH + "OrderCandidatesRestControllerImplTest_Create_DontUpdate_1.json");

		// when
		final ResponseEntity<JsonOLCandCreateBulkResponse> result = orderCandidatesRestControllerImpl.createOrderLineCandidate(request);

		// then
		final JsonOLCand jsonOLCand = assertResultOKForTest_1_JSON(result);
		expect.serializer("orderedJson").toMatchSnapshot(jsonOLCand);

		final List<I_C_BPartner_Location> bplRecords = POJOLookupMap.get().getRecords(I_C_BPartner_Location.class);
		assertThat(bplRecords)
				.extracting(COLUMNNAME_ExternalId, "shipTo", "billTo", "billToDefault")
				.containsExactlyInAnyOrder(
						tuple("billToId-1-2", false, true, true),
						tuple("shipToId-1-2", true, false, false));
	}

	/**
	 * existing bpartner with location "billToId-1-2" that is updated
	 */
	@Test
	void billToDefault_exitingBPartner()
	{
		// given
		BusinessTestHelper.createBPGroup("DefaultGroup", true);
		testMasterdata.createDataSource("SOURCE.de.metas.rest_api.ordercandidates.impl.OrderCandidatesRestControllerImpl_B2C");
		testMasterdata.createDataSource("DEST.de.metas.ordercandidate");
		testMasterdata.createPricingSystem("vk3");
		testMasterdata.createShipper("Standard");
		testMasterdata.createSalesRep("ABC-DEF-12345");
		testMasterdata.createDocType(DocBaseAndSubType.of("ARI"));

		final BPartnerId bpartnerId = testMasterdata.prepareBPartner()
				.orgId(defaultOrgId)
				.bpValue("bpValue")
				.bpExternalId("1-2")
				.bpGroupExistingName("DefaultGroup")
				.build();
		testMasterdata.prepareBPartnerLocation().bpartnerId(bpartnerId)
				.externalId("billToId-1-2")
				.countryId(countryId_DE)
				.shipTo(false)
				.billTo(true)
				.billToDefault(false) // we will expect this to be updated to true by the json-request
				.shipToDefault(false)
				.build();
		testMasterdata.prepareBPartnerLocation().bpartnerId(bpartnerId)
				.externalId("shipToId-1-2")
				.countryId(countryId_DE)
				.shipTo(true)
				.billTo(false)
				.billToDefault(false)
				.shipToDefault(false)
				.build();
		testMasterdata.prepareBPartnerLocation().bpartnerId(bpartnerId)
				.externalId("billToId-1-2_alt")
				.countryId(countryId_DE)
				.shipTo(false)
				.billTo(true)
				.billToDefault(true) //  we will expect this to be updated to false when "billToId-1-2" is updated to true
				.shipToDefault(false)
				.build();
		testMasterdata.prepareBPartnerLocation().bpartnerId(bpartnerId)
				.externalId("shipToId-1-2_alt")
				.countryId(countryId_DE)
				.shipTo(true)
				.billTo(false)
				.billToDefault(false)
				.shipToDefault(false)
				.build();

		SystemTime.setTimeSource(() -> 1584400036193L + 10000); // some later time, such that the bpartner's creation was in the past.

		final JsonOLCandCreateRequest request = JsonOLCandUtil.loadJsonOLCandCreateRequest(RESOURCE_PATH + "OrderCandidatesRestControllerImplTest_Create_UpdateMerge_1.json");

		// when
		final ResponseEntity<JsonOLCandCreateBulkResponse> result = orderCandidatesRestControllerImpl.createOrderLineCandidate(request);

		// then
		final JsonOLCand jsonOLCand = assertResultOKForTest_1_JSON(result);
		expect.serializer("orderedJson").toMatchSnapshot(jsonOLCand);

		final List<I_C_BPartner_Location> bplRecords = POJOLookupMap.get().getRecords(I_C_BPartner_Location.class);
		assertThat(bplRecords)
				.extracting(COLUMNNAME_ExternalId, "shipTo", "billTo", "billToDefault")
				.containsExactlyInAnyOrder(
						tuple("billToId-1-2", false, true, true),
						tuple("shipToId-1-2", true, false, false),
						tuple("billToId-1-2_alt", false, true, false),
						tuple("shipToId-1-2_alt", true, false, false));

	}

	private JsonOLCand assertResultOKForTest_1_JSON(@NonNull final ResponseEntity<JsonOLCandCreateBulkResponse> result)
	{
		assertThat(result.getBody().getResult()).hasSize(1);

		final JsonOLCand jsonOLCand = result.getBody().getResult().get(0);

		//bpartner's location
		assertThat(jsonOLCand.getBpartner().getLocation())
				.extracting("externalId.value", "billTo", "billToDefault", "shipTo")
				.containsExactly("billToId-1-2", true, true, false);
		//billBPartner's location
		assertThat(jsonOLCand.getBillBPartner().getLocation())
				.extracting("externalId.value", "billTo", "billToDefault", "shipTo")
				.containsExactly("billToId-1-2", true, true, false);
		//dropShipBPartner's location
		assertThat(jsonOLCand.getDropShipBPartner().getLocation())
				.extracting("externalId.value", "billTo", "billToDefault", "shipTo")
				.containsExactly("shipToId-1-2", false, false, true);

		return jsonOLCand;
	}
}
