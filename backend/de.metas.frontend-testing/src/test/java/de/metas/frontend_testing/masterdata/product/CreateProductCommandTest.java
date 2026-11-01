package de.metas.frontend_testing.masterdata.product;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.costing.CostElement;
import de.metas.costing.CostingLevel;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2025 metas GmbH
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

public class CreateProductCommandTest
{
	private ProductRepository productRepository;
	private ICurrentCostsRepository currentCostsRepository;
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		productRepository = new ProductRepository();
		currentCostsRepository = new CurrentCostsRepository(new CostElementRepository(ADReferenceService.newMocked()));
		context = new MasterdataContext();
	}

	@Test
	public void execute_withProductValue_shouldCreateProductRecord()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_001")
				.name("Test Product One")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("product1"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getProductCode()).isEqualTo("PROD_001");
		assertThat(response.getProductName()).isEqualTo("Test Product One");

		// Verify database record
		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.getValue()).isEqualTo("PROD_001");
		assertThat(product.getName()).isEqualTo("Test Product One");
		assertThat(product.isStocked()).isTrue();
		assertThat(product.isSold()).isTrue();
		assertThat(product.isPurchased()).isTrue();
	}

	@Test
	public void execute_withCustomName_shouldUseCustomName()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_002")
				.name("Custom Product Name")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("product2"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getValue()).isEqualTo("PROD_002");
		assertThat(product.getName()).isEqualTo("Custom Product Name");
	}

	@Test
	public void execute_withoutCustomName_shouldUseValueAsName()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_003")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("product3"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getValue()).isEqualTo("PROD_003");
		assertThat(product.getName()).isEqualTo("PROD_003");
	}

	@Test
	public void execute_shouldSetDefaultProductProperties()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_DEFAULT")
				.name("Product with Defaults")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("productDefault"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getProductType()).isEqualTo("I"); // Item
		assertThat(product.isStocked()).isTrue();
		assertThat(product.isSold()).isTrue();
		assertThat(product.isPurchased()).isTrue();
		assertThat(product.getC_UOM_ID()).isGreaterThan(0); // UOM should be set
		assertThat(product.getM_Product_Category_ID()).isGreaterThan(0); // Category should be set
	}

	@Test
	public void execute_shouldStoreIdentifierInContext()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_CTX")
				.name("Context Test Product")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("contextProduct"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		final ProductId productId = context.getId(Identifier.ofString("contextProduct"), ProductId.class);
		assertThat(productId).isNotNull();
		assertThat(productId).isEqualTo(response.getId());
	}

	@Test
	public void execute_multipleProducts_shouldCreateUniqueRecords()
	{
		// given & when
		final JsonCreateProductResponse response1 = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("UNIQUE_PROD_001")
						.name("Unique Product 1")
						.build())
				.identifier(Identifier.ofString("uniqueProd1"))
				.build()
				.execute();

		final JsonCreateProductResponse response2 = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("UNIQUE_PROD_002")
						.name("Unique Product 2")
						.build())
				.identifier(Identifier.ofString("uniqueProd2"))
				.build()
				.execute();

		// then
		assertThat(response1.getId()).isNotEqualTo(response2.getId());
		assertThat(response1.getProductCode()).isNotEqualTo(response2.getProductCode());

		final I_M_Product product1 = InterfaceWrapperHelper.load(response1.getId(), I_M_Product.class);
		final I_M_Product product2 = InterfaceWrapperHelper.load(response2.getId(), I_M_Product.class);
		assertThat(product1.getValue()).isEqualTo("UNIQUE_PROD_001");
		assertThat(product2.getValue()).isEqualTo("UNIQUE_PROD_002");
	}

	@Test
	public void execute_withNullValue_shouldGenerateUniqueValue()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.name("Product with Auto Value")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("autoValueProduct"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getProductCode()).isNotNull();
		assertThat(response.getProductCode()).isNotEmpty();

		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getValue()).isNotNull();
		assertThat(product.getValue()).isNotEmpty();
	}

	@Test
	public void execute_withGTIN_shouldSetGTIN()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_GTIN")
				.name("Product with GTIN")
				.gtin(GTIN.ofString("1234567890123"))
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("gtinProduct"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response.getGtin()).isNotNull();
		assertThat(response.getGtin().getAsString()).isEqualTo("1234567890123");

		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getGTIN()).isEqualTo("1234567890123");
	}

	@Test
	public void execute_withEAN13ProductCode_shouldSetEAN13()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_EAN13")
				.name("Product with EAN13")
				.ean13ProductCode(EAN13ProductCode.ofString("1234567890123"))
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("ean13Product"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response.getEan13ProductCode()).isNotNull();
		assertThat(response.getEan13ProductCode().getAsString()).isEqualTo("1234567890123");

		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getEAN13_ProductCode()).isEqualTo("1234567890123");
	}

	@Test
	public void execute_shouldCreateProductPrices()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder()
				.value("PROD_WITH_PRICES")
				.name("Product with Prices")
				.build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("pricesProduct"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response.getId()).isNotNull();

		// Verify product exists (price creation is part of the command execution)
		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.getValue()).isEqualTo("PROD_WITH_PRICES");
	}

	@Test
	public void execute_withMinimalRequest_shouldSucceed()
	{
		// given
		final JsonCreateProductRequest request = JsonCreateProductRequest.builder().build();

		final CreateProductCommand command = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString("minimalProduct"))
				.build();

		// when
		final JsonCreateProductResponse response = command.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getProductCode()).isNotNull();

		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product).isNotNull();
	}

	@Test
	public void execute_withSkipDefaultCosts_shouldDeleteTheProductCosts()
	{
		final RecordingCurrentCostsRepository costsRepository = new RecordingCurrentCostsRepository();

		final JsonCreateProductResponse response = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(costsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("PROD_NO_COSTS")
						.isSkipDefaultCosts(true)
						.build())
				.identifier(Identifier.ofString("noCostsProduct"))
				.build()
				.execute();

		assertThat(costsRepository.deletedForProductIds).containsExactly(response.getId().getRepoId());
	}

	@Test
	public void execute_withoutSkipDefaultCosts_shouldKeepTheProductCosts()
	{
		final RecordingCurrentCostsRepository costsRepository = new RecordingCurrentCostsRepository();

		CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(costsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("PROD_WITH_COSTS")
						.build())
				.identifier(Identifier.ofString("withCostsProduct"))
				.build()
				.execute();

		assertThat(costsRepository.deletedForProductIds).isEmpty();
	}

	@Test
	public void currentCostPrice_isSet()
	{
		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(CostingMethod.StandardCosting)
				.build();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		final I_AD_ClientInfo clientInfo = InterfaceWrapperHelper.newInstance(I_AD_ClientInfo.class);
		InterfaceWrapperHelper.setValue(clientInfo, I_AD_ClientInfo.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		clientInfo.setC_AcctSchema1_ID(acctSchemaId.getRepoId());
		InterfaceWrapperHelper.saveRecord(clientInfo);
		final I_C_UOM each = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_UOM.class);
		each.setC_UOM_ID(UomId.EACH.getRepoId()); // UomId.EACH is a fixed constant that the costing code loads by id
		each.setName("Each");
		each.setX12DE355("EA");
		each.setStdPrecision(0);
		InterfaceWrapperHelper.saveRecord(each);
		final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		category.setName("cat");
		InterfaceWrapperHelper.saveRecord(category);
		final I_M_Product_Category_Acct categoryAcct = InterfaceWrapperHelper.newInstance(I_M_Product_Category_Acct.class);
		categoryAcct.setM_Product_Category_ID(category.getM_Product_Category_ID());
		categoryAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		InterfaceWrapperHelper.saveRecord(categoryAcct);
		context.putIdentifier(Identifier.ofString("cat"), ProductCategoryId.ofRepoId(category.getM_Product_Category_ID()));
		final CostElementRepository costElementRepository = new CostElementRepository(ADReferenceService.newMocked());
		final CostElement costElement = costElementRepository.getOrCreateMaterialCostElement(ClientId.METASFRESH, CostingMethod.StandardCosting);

		final JsonCreateProductResponse response = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("PROD_COST")
						.productCategory(Identifier.ofString("cat"))
						.currentCostPrice(new BigDecimal("16.93"))
						.build())
				.identifier(Identifier.ofString("costProduct"))
				.build()
				.execute();

		final I_M_Cost costRecord = POJOLookupMap.get().getRecords(I_M_Cost.class, record -> record.getM_Product_ID() == response.getId().getRepoId()
				&& record.getC_AcctSchema_ID() == acctSchemaId.getRepoId()
				&& record.getM_CostElement_ID() == costElement.getId().getRepoId())
				.stream().findFirst().orElse(null);
		assertThat(costRecord).as("M_Cost of the schema's costing-method element").isNotNull();
		assertThat(costRecord.getCurrentCostPrice()).isEqualByComparingTo("16.93");
	}

	@Test
	public void coProductCostDistributionPercent_isSet()
	{
		final JsonCreateProductResponse response = CreateProductCommand.builder()
				.productRepository(productRepository)
				.currentCostsRepository(currentCostsRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder()
						.value("PROD_COPRODUCT")
						.coProductCostDistributionPercent(new BigDecimal("20"))
						.build())
				.identifier(Identifier.ofString("coProduct"))
				.build()
				.execute();

		final I_M_Product product = InterfaceWrapperHelper.load(response.getId(), I_M_Product.class);
		assertThat(product.getCoProductCostDistributionPercent()).isEqualByComparingTo("20");
	}

	/**
	 * Records for which products the command asked to delete the cost rows.
	 */
	private static class RecordingCurrentCostsRepository extends CurrentCostsRepository
	{
		private final List<Integer> deletedForProductIds = new ArrayList<>();

		RecordingCurrentCostsRepository()
		{
			super(new CostElementRepository(ADReferenceService.newMocked()));
		}

		@Override
		public void deleteForProduct(final I_M_Product product)
		{
			deletedForProductIds.add(product.getM_Product_ID());
		}
	}
}
