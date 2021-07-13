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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.audit.ExternalSystemExportAuditRepo;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.process.runtimeparameters.RuntimeParametersRepository;
import de.metas.logging.LogManager;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.rest_api.v2.externlasystem.dto.ExternalSystemService;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.dao.AlbertaProductDAO;
import de.metas.vertical.healthcare.alberta.service.AlbertaProductService;
import io.github.jsonSnapshot.SnapshotMatcher;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class ProductsRestControllerTest
{
	private ProductsRestController restController;

	private UomId eachUomId;
	private UomId kgUomId;

	@BeforeAll
	static void initStatic()
	{
		SnapshotMatcher.start(
				AdempiereTestHelper.SNAPSHOT_CONFIG,
				AdempiereTestHelper.createSnapshotJsonFunction());

		LogManager.setLoggerLevel(de.metas.rest_api.v2.product.ProductsRestController.class, Level.ALL);
	}

	@AfterAll
	static void afterAll()
	{
		SnapshotMatcher.validateSnapshots();
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		createMasterData();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		final ProductsServicesFacade productsServicesFacade = new ProductsServicesFacade();

		final ExternalSystemService externalSystemService = new ExternalSystemService(new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository()), new ExternalSystemExportAuditRepo(), new RuntimeParametersRepository());
		final ProductRepository productRepository = new ProductRepository();
		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();

		final ExternalReferenceRepository externalReferenceRepository =
				new ExternalReferenceRepository(Services.get(IQueryBL.class), new ExternalSystems(), externalReferenceTypes);

		final ExternalReferenceRestControllerService externalReferenceRestControllerService =
				new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystems(), new ExternalReferenceTypes());
		final AlbertaProductService albertaProductService = new AlbertaProductService(new AlbertaProductDAO(), externalReferenceRepository);

		final ProductRestService productRestService = new ProductRestService(productRepository, externalReferenceRestControllerService);

		restController = new ProductsRestController(productsServicesFacade, albertaProductService, externalSystemService, productRestService);
	}

	private void createMasterData()
	{
		eachUomId = createUOM("Ea");
		kgUomId = createUOM("Kg");
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
		SnapshotMatcher.expect(responseBody).toMatchSnapshot();
	}

	private UomId createUOM(@NonNull final String uomSymbol)
	{
		final I_C_UOM record = newInstance(I_C_UOM.class);
		record.setUOMSymbol(uomSymbol);
		saveRecord(record);
		return UomId.ofRepoId(record.getC_UOM_ID());
	}

	@Builder(builderMethodName = "prepareProduct", builderClassName = "prepareProductBuilder")
	private I_M_Product createProduct(
			@NonNull final String value,
			@NonNull final String name,
			@NonNull final ProductCategoryId categoryId,
			final String description,
			final String ean,
			@NonNull final UomId uomId)
	{
		final I_M_Product record = newInstance(I_M_Product.class);
		record.setValue(value);
		record.setM_Product_Category_ID(categoryId.getRepoId());
		record.setName(name);
		record.setDescription(description);
		record.setUPC(ean);
		record.setC_UOM_ID(uomId.getRepoId());

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
