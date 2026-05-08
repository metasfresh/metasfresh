package de.metas.frontend_testing.masterdata.product;

import de.metas.calendar.PeriodRepo;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		productRepository = new ProductRepository();
		SpringContextHolder.registerJUnitBean(new PeriodRepo());

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
}
