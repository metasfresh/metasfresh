package de.metas.rest_api.ordercandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.MimeType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocBaseAndSubType;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.spi.impl.DefaultOLCandValidator;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfNotExists;
import de.metas.rest_api.attachment.JsonAttachmentType;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.ordercandidates.JsonAttachment;
import de.metas.rest_api.ordercandidates.JsonBPartnerInfo;
import de.metas.rest_api.ordercandidates.JsonDocTypeInfo;
import de.metas.rest_api.ordercandidates.JsonOLCand;
import de.metas.rest_api.ordercandidates.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.JsonOLCandCreateBulkResponse;
import de.metas.rest_api.ordercandidates.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.JsonProductInfo;
import de.metas.rest_api.ordercandidates.JsonProductInfo.Type;
import de.metas.rest_api.utils.JsonError;
import de.metas.rest_api.utils.PermissionService;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import mockit.Mocked;

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

public class OrderCandidatesRestControllerImplTest
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

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

	@Mocked
	private PermissionService permissionService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		{ // create the master data requested to process the data from our json file
			testMasterdata = new TestMasterdata();

			countryId_DE = testMasterdata.createCountry(COUNTRY_CODE_DE);

			uomId = testMasterdata.createUOM(UOM_CODE);

			testMasterdata.createDocType(DOCTYPE_SALES_INVOICE);

			testMasterdata.createDataSource(DATA_SOURCE_INTERNALNAME);
			testMasterdata.createDataSource(DATA_DEST_INVOICECANDIDATE);
		}

		final MasterdataProviderFactory masterdataProviderFactory = MasterdataProviderFactory
				.builder()
				.permissionService(permissionService).build();

		orderCandidatesRestControllerImpl = new OrderCandidatesRestControllerImpl(
				masterdataProviderFactory,
				new JsonConverters(),
				new OLCandRepository());
		LogManager.setLoggerLevel(orderCandidatesRestControllerImpl.getClass(), Level.ALL);
	}

	// NOTE: Shall be called programatically by each test
	private void startInterceptors()
	{
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		final OLCandRegistry olCandRegistry = new OLCandRegistry(
				Optional.empty(),
				Optional.empty(),
				Optional.of(ImmutableList.of(new DefaultOLCandValidator())));
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
				.dataSourceInternalName(DATA_SOURCE_INTERNALNAME)
				.dataDestInternalName(DATA_DEST_INVOICECANDIDATE)
				.dateOrdered(dateOrdered)
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 5))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.build())
				.bpartner(JsonBPartnerInfo.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.build())
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
		assertThat(olCand.getDateOrdered()).isEqualTo(dateOrdered);
	}

	@Test
	public void error_NoBPartnerFound()
	{
		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSourceInternalName(DATA_SOURCE_INTERNALNAME)
				.dataDestInternalName(DATA_DEST_INVOICECANDIDATE)
				.dateRequired(LocalDate.of(2019, Month.SEPTEMBER, 5))
				.qty(new BigDecimal("66"))
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poRef")
				.product(JsonProductInfo.builder()
						.code("productCode")
						.build())
				.bpartner(JsonBPartnerInfo.builder()
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

		final JsonError error = responseBody.getError();
		assertThat(error.getMessage()).contains("Found no existing BPartner");
	}

	@Test
	public void testCreatProductPrice()
	{
		final TaxCategoryId taxCategoryId = testMasterdata.createTaxCategory();
		final PricingSystemId pricingSystemId = testMasterdata.createPricingSystem();
		final PriceListId priceListId = testMasterdata.createSalesPriceList(pricingSystemId, countryId_DE, currencyId_EUR, taxCategoryId);
		testMasterdata.createPriceListVersion(priceListId, LocalDate.of(2019, Month.SEPTEMBER, 1));
		testMasterdata.prepareBPartnerAndLocation()
				.bpValue("bpCode")
				.salesPricingSystemId(pricingSystemId)
				.countryId(countryId_DE)
				.gln(GLN.ofString("gln1"))
				.build();

		testMasterdata.createPricingRules();

		startInterceptors();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.of(JsonOLCandCreateRequest.builder()
				.dataSourceInternalName(DATA_SOURCE_INTERNALNAME)
				.dataDestInternalName(DATA_DEST_INVOICECANDIDATE)
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
						.price(new BigDecimal("13.24"))
						.syncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS)
						.build())
				.bpartner(JsonBPartnerInfo.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("bpCode")
								.build())
						.location(JsonRequestLocation.builder()
								.gln("gln1")
								.build())
						.build())
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
		System.out.println(olCand);

		assertThat(olCand.getPrice()).isEqualByComparingTo(new BigDecimal("13.24"));
	}

}
