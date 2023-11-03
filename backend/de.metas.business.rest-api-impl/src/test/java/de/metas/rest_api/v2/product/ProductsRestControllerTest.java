/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.product;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.externalsystem.externalservice.ExternalServices;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.process.runtimeparameters.RuntimeParametersRepository;
import de.metas.greeting.GreetingRepository;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.job.JobService;
import de.metas.logging.LogManager;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.quality.attribute.ProductQualityAttributeRepository;
import de.metas.product.quality.attribute.QualityAttributeService;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.externlasystem.ExternalSystemService;
import de.metas.rest_api.v2.externlasystem.JsonExternalSystemRetriever;
import de.metas.rest_api.v2.warehouseassignment.ProductWarehouseAssignmentRestService;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeRepository;
import de.metas.sectionCode.SectionCodeService;
import de.metas.title.TitleRepository;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import de.metas.vertical.healthcare.alberta.dao.AlbertaProductDAO;
import de.metas.vertical.healthcare.alberta.service.AlbertaProductService;
import de.metas.warehouseassignment.ProductWarehouseAssignmentRepository;
import de.metas.warehouseassignment.ProductWarehouseAssignmentService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class ProductsRestControllerTest
{
	private ProductsRestController restController;

	private UomId eachUomId;
	private UomId kgUomId;
	private SectionCodeId sectionCodeId;

	private Expect expect;

	@BeforeAll
	static void initStatic()
	{
		LogManager.setLoggerLevel(de.metas.rest_api.v2.product.ProductsRestController.class, Level.ALL);
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		createMasterData();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		final SectionCodeRepository sectionCodeRepository = new SectionCodeRepository();

		final ExternalServices externalServices = Mockito.mock(ExternalServices.class);

		final ExternalSystemService externalSystemService = new ExternalSystemService(new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository()),
																					  new ExternalSystemExportAuditRepo(),
																					  new RuntimeParametersRepository(),
																					  externalServices,
																					  new JsonExternalSystemRetriever());
		final ProductRepository productRepository = new ProductRepository();
		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();

		final ExternalReferenceRepository externalReferenceRepository =
				new ExternalReferenceRepository(Services.get(IQueryBL.class), new ExternalSystems(), externalReferenceTypes);

		final ExternalReferenceRestControllerService externalReferenceRestControllerService =
				new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystems(), new ExternalReferenceTypes());
		final AlbertaProductService albertaProductService = new AlbertaProductService(new AlbertaProductDAO(), externalReferenceRepository);

		final ProductsServicesFacade productsServicesFacade = new ProductsServicesFacade(sectionCodeRepository);

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), new BPartnerCreditLimitRepository());
		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				new TitleRepository(),
				currencyRepository,
				JobService.newInstanceForUnitTesting(),
				Mockito.mock(de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService.class),
				new SectionCodeService(sectionCodeRepository),
				new IncotermsRepository(),
				Mockito.mock(AlbertaBPartnerCompositeService.class),
				new BPartnerCreditLimitRepository());

		final ExternalIdentifierResolver externalIdentifierResolver = new ExternalIdentifierResolver(externalReferenceRestControllerService);

		final ProductWarehouseAssignmentService productWarehouseAssignmentService = new ProductWarehouseAssignmentService(new ProductWarehouseAssignmentRepository());
		final ProductWarehouseAssignmentRestService productWarehouseAssignmentRestService = new ProductWarehouseAssignmentRestService(productWarehouseAssignmentService, externalIdentifierResolver);
		//
		final ProductRestService productRestService = new ProductRestService(productRepository,
																			 productWarehouseAssignmentRestService,
																			 externalReferenceRestControllerService,
																			 new SectionCodeService(sectionCodeRepository),
																			 Mockito.mock(ProductAllergenRestService.class),
																			 new QualityAttributeService(new ProductQualityAttributeRepository()),
																			 jsonServiceFactory,
																			 externalIdentifierResolver);
		//
		restController = new ProductsRestController(productsServicesFacade, albertaProductService, externalSystemService, productRestService, externalIdentifierResolver);
	}

	private void createMasterData()
	{
		eachUomId = createUOM("Ea");
		kgUomId = createUOM("Kg");
		sectionCodeId = createSectionCode("SectionCode");
	}

	@Test
	public void getProducts_standardCase()
	{
		//
		// Masterdata
		final I_M_Product product1 = prepareProduct()
				.value("value1")
				.categoryId(ProductCategoryId.ofRepoId(3))
				.name("name1")
				.description("description1")
				.ean("ean1")
				.uomId(eachUomId)
				.sectionCodeId(sectionCodeId)
				.build();
		final ProductId productId1 = ProductId.ofRepoId(product1.getM_Product_ID());
		prepareBPartnerProduct()
				.productId(productId1)
				.bpartnerId(BPartnerId.ofRepoId(1))
				.productNo("productNo1-vendor1")
				.productName("productName1-vendor1")
				.productDescription("productDescription1-vendor1")
				.productCategory("productCategory1-vendor1")
				.ean("ean1-vendor1")
				.vendor(true)
				.currentVendor(true)
				.customer(false)
				.leadTimeInDays(13)
				.build();
		prepareBPartnerProduct()
				.productId(productId1)
				.bpartnerId(BPartnerId.ofRepoId(2))
				.productNo("productNo1-customer2")
				.productName("productName1-customer2")
				.productDescription("productDescription1-customer2")
				.productCategory("productCategory1-customer2")
				.ean("ean1-customer2")
				.vendor(false)
				.currentVendor(false)
				.customer(true)
				.leadTimeInDays(32)
				.build();

		final I_M_Product product2 = prepareProduct()
				.value("value2")
				.categoryId(ProductCategoryId.ofRepoId(4))
				.name("name2")
				.description("description2")
				.ean("ean2")
				.uomId(kgUomId)
				.build();
		//
		// Call endpoint
		final ResponseEntity<JsonGetProductsResponse> response = (ResponseEntity<JsonGetProductsResponse>)restController.getProducts(null, null, null, null);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		//
		// Expectations
		final JsonGetProductsResponse responseBody = response.getBody();
		assertThat(responseBody)
				.isEqualTo(JsonGetProductsResponse.builder()
								   .product(JsonProduct.builder()
													.id(JsonMetasfreshId.of(productId1.getRepoId()))
													.productNo("value1")
													.name("name1")
													.description("description1")
													.ean("ean1")
													.uom("Ea")
													.sectionCode("SectionCode")
													.productCategoryId(JsonMetasfreshId.of(3))
													.bpartner(JsonProductBPartner.builder()
																	  .bpartnerId(JsonMetasfreshId.of(1))
																	  .productNo("productNo1-vendor1")
																	  .productName("productName1-vendor1")
																	  .productDescription("productDescription1-vendor1")
																	  .productCategory("productCategory1-vendor1")
																	  .ean("ean1-vendor1")
																	  .vendor(true)
																	  .currentVendor(true)
																	  .customer(false)
																	  .leadTimeInDays(13)
																	  .build())
													.bpartner(JsonProductBPartner.builder()
																	  .bpartnerId(JsonMetasfreshId.of(2))
																	  .productNo("productNo1-customer2")
																	  .productName("productName1-customer2")
																	  .productDescription("productDescription1-customer2")
																	  .productCategory("productCategory1-customer2")
																	  .ean("ean1-customer2")
																	  .vendor(false)
																	  .currentVendor(false)
																	  .customer(true)
																	  .leadTimeInDays(32)
																	  .build())
													.build())
								   .product(JsonProduct.builder()
													.id(JsonMetasfreshId.of(product2.getM_Product_ID()))
													.productNo("value2")
													.name("name2")
													.description("description2")
													.ean("ean2")
													.uom("Kg")
													.productCategoryId(JsonMetasfreshId.of(4))
													.build())
								   .build());

		//
		expect.serializer("orderedJson").toMatchSnapshot(responseBody);
	}

	private UomId createUOM(@NonNull final String uomSymbol)
	{
		final I_C_UOM record = newInstance(I_C_UOM.class);
		record.setUOMSymbol(uomSymbol);
		saveRecord(record);
		return UomId.ofRepoId(record.getC_UOM_ID());
	}

	private SectionCodeId createSectionCode(@NonNull final String sectionCode)
	{
		final I_M_SectionCode record = newInstance(I_M_SectionCode.class);
		record.setName(sectionCode);
		record.setValue(sectionCode);
		saveRecord(record);
		return SectionCodeId.ofRepoId(record.getM_SectionCode_ID());
	}

	@Builder(builderMethodName = "prepareProduct", builderClassName = "prepareProductBuilder")
	private I_M_Product createProduct(
			@NonNull final String value,
			@NonNull final String name,
			@NonNull final ProductCategoryId categoryId,
			final String description,
			final String ean,
			@NonNull final UomId uomId,
			@Nullable final SectionCodeId sectionCodeId)
	{
		final I_M_Product record = newInstance(I_M_Product.class);
		record.setValue(value);
		record.setM_Product_Category_ID(categoryId.getRepoId());
		record.setName(name);
		record.setDescription(description);
		record.setUPC(ean);
		record.setC_UOM_ID(uomId.getRepoId());
		record.setM_SectionCode_ID(SectionCodeId.toRepoId(sectionCodeId));

		saveRecord(record);
		return record;
	}

	@Builder(builderMethodName = "prepareBPartnerProduct", builderClassName = "prepareBPartnerProductBuilder")
	private I_C_BPartner_Product createBPartnerProduct(
			final ProductId productId,
			final BPartnerId bpartnerId,
			//
			final String productNo,
			final String productName,
			final String productDescription,
			final String productCategory,
			//
			final String ean,
			//
			final boolean vendor,
			final boolean currentVendor,
			final boolean customer,
			//
			final int leadTimeInDays)
	{
		final I_C_BPartner_Product record = newInstance(I_C_BPartner_Product.class);
		record.setM_Product_ID(productId.getRepoId());
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		//
		record.setProductNo(productNo);
		record.setProductName(productName);
		record.setProductDescription(productDescription);
		record.setProductCategory(productCategory);
		//
		record.setUPC(ean);
		//
		record.setUsedForVendor(vendor);
		record.setIsCurrentVendor(currentVendor);
		record.setUsedForCustomer(customer);
		//
		record.setDeliveryTime_Promised(leadTimeInDays);

		saveRecord(record);
		return record;
	}
}
