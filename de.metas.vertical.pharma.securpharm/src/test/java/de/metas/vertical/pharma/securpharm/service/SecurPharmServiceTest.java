/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;

import de.metas.event.impl.PlainEventBusFactory;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.inventory.InventoryId;
import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.client.DecodeDataMatrixClientResponse;
import de.metas.vertical.pharma.securpharm.client.DecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.client.UndoDecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.VerifyProductClientResponse;
import de.metas.vertical.pharma.securpharm.client.schema.JsonExpirationDate;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog.SecurPharmLogBuilder;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductCodeType;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import de.metas.vertical.pharma.securpharm.product.ProductDetails.ProductDetailsBuilder;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;
import lombok.NonNull;

public class SecurPharmServiceTest
{
	/** The service under test */
	private SecurPharmService securPharmService;

	//
	// Other services
	private SecurPharmClient client;
	private SecurPharmProductRepository productsRepo;

	@Before
	public void beforeEachTest()
	{
		AdempiereTestHelper.get().init();

		//
		// Setup config & client
		final SecurPharmConfigRespository configRespository = Mockito.mock(SecurPharmConfigRespository.class);
		final SecurPharmClientFactory clientFactory = Mockito.mock(SecurPharmClientFactory.class);
		client = Mockito.mock(SecurPharmClient.class);
		//
		final SecurPharmConfig config = createDummyConfig();
		Mockito.when(configRespository.getDefaultConfig()).thenReturn(Optional.of(config));
		Mockito.when(clientFactory.createClient(config)).thenReturn(client);
		Mockito.when(client.getConfig()).thenReturn(config);

		//
		// Other services
		productsRepo = new SecurPharmProductRepository();
		// final SecurPharmaActionRepository actionsRepo = Mockito.mock(SecurPharmaActionRepository.class);
		final SecurPharmaActionRepository actionsRepo = new SecurPharmaActionRepository();
		final SecurPharmLogRepository logsRepo = Mockito.mock(SecurPharmLogRepository.class);
		final InventoryRepository inventoryRepo = Mockito.mock(InventoryRepository.class);

		//
		// The service we are testing
		securPharmService = new SecurPharmService(
				PlainEventBusFactory.newInstance(),
				clientFactory,
				configRespository,
				productsRepo,
				actionsRepo,
				logsRepo,
				inventoryRepo);

	}

	private static SecurPharmConfig createDummyConfig()
	{
		return SecurPharmConfig.builder()
				.applicationUUID("uuid")
				.authBaseUrl("url")
				.pharmaAPIBaseUrl("url")
				.certificatePath("path")
				.supportUserId(UserId.METASFRESH)
				.keystorePassword("passw")
				.build();
	}

	private ProductDetailsBuilder newDummyProductDetails()
	{
		return ProductDetails.builder()
				.activeStatus(JsonProductPackageState.ACTIVE)
				.expirationDate(JsonExpirationDate.ofLocalDate(LocalDate.now()))
				.lot("lot")
				.productCode("product code")
				.productCodeType(ProductCodeType.GTIN)
				.serialNumber("serial nr");
	}

	private void mockClientDecodeAndVerifyProductDataOK(
			final DataMatrixCode dataMatrix,
			final ProductDetails productDetails,
			final HuId huId)
	{
		Mockito.when(client.decodeDataMatrix(dataMatrix))
				.thenReturn(DecodeDataMatrixClientResponse.builder()
						.productDetails(productDetails)
						.log(generateDummyLog())
						.build());

		Mockito.when(client.verifyProduct(productDetails))
				.thenReturn(VerifyProductClientResponse.builder()
						.resultCode("200")
						.productDetails(productDetails)
						.log(generateDummyLog())
						.build());
	}

	private void mockClientDecommissionOK(
			@NonNull final ProductDetails productDetails,
			@NonNull final String serverTransactionId)
	{
		Mockito.when(client.decommission(productDetails))
				.thenReturn(DecommissionClientResponse.builder()
						.productDetails(productDetails)
						.log(prepareDummyLog()
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

	private void mockClientUndoDecommissionOK(@NonNull final ProductDetails productDetails)
	{
		final String serverTransactionId = productDetails.getDecommissionedServerTransactionId();

		Mockito.when(client.undoDecommission(productDetails, serverTransactionId))
				.thenReturn(UndoDecommissionClientResponse.builder()
						.productDetails(productDetails)
						.log(prepareDummyLog()
								.serverTransactionId(serverTransactionId)
								.build())
						.build());
	}

	private SecurPharmProduct prepareProductForDecommissioning()
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		final ProductDetails productDetails = newDummyProductDetails()
				.activeStatus(JsonProductPackageState.FRAUD)
				.build();
		final HuId huId = HuId.ofRepoId(1);
		mockClientDecodeAndVerifyProductDataOK(dataMatrix, productDetails, huId);

		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);

		//
		// Make sure the product is eligible for decommissioning
		assertThat(securPharmService.isEligibleForDecommission(product))
				.as("" + product + " shall be eligible for decommissioning")
				.isTrue();

		return product;
	}

	private SecurPharmLog generateDummyLog()
	{
		return prepareDummyLog().build();
	}

	private SecurPharmLogBuilder prepareDummyLog()
	{
		return SecurPharmLog.builder()
				.responseTime(Instant.now())
				.requestTime(Instant.now())
				.requestMethod(HttpMethod.GET)
				.requestUrl("requestUrl")
				.responseData("responseData")
				.clientTransactionId("dummyClientTransactionId")
				.serverTransactionId("dummyServerTransactionId")
				.error(false);
	}

	private void assertProductSaved(final SecurPharmProduct product)
	{
		assertThat(product.getId()).as("product saved: " + product).isNotNull();

		final SecurPharmProduct productReloaded = productsRepo.getProductById(product.getId());
		assertThat(product).as("product shall match the one loaded from database").isEqualTo(productReloaded);
	}

	@Test
	public void getAndSaveProductData_OK_Case()
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		final ProductDetails productDetails = newDummyProductDetails().build();
		final HuId huId = HuId.ofRepoId(1);
		mockClientDecodeAndVerifyProductDataOK(dataMatrix, productDetails, huId);

		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);

		assertThat(product)
				.isEqualTo(SecurPharmProduct.builder()
						.id(product.getId())
						.productDetails(productDetails)
						.huId(huId)
						.build());
	}

	@Test
	public void decommission_OK_Case()
	{
		final SecurPharmProduct product = prepareProductForDecommissioning();
		assertThat(product.isDecommissioned()).isFalse();

		//
		// Decommission the product
		final InventoryId inventoryId = InventoryId.ofRepoId(1);
		final String serverTransactionId = "decomission-ServerTransactionId";
		mockClientDecommissionOK(product.getProductDetails(), serverTransactionId);
		final DecommissionResponse decommissionResponse = securPharmService.decommissionProductIfEligible(product, inventoryId).get();

		//
		// Check Decommission result
		assertThat(decommissionResponse.getId()).isNotNull();
		assertThat(decommissionResponse)
				.isEqualTo(DecommissionResponse.builder()
						.error(false)
						.productId(product.getId())
						.serverTransactionId(serverTransactionId)
						.inventoryId(inventoryId)
						.id(decommissionResponse.getId())
						.build());

		//
		// Check the product
		assertThat(product.isDecommissioned()).isTrue();
		assertThat(product.getDecommissionServerTransactionId()).isEqualTo(serverTransactionId);
		assertProductSaved(product);
	}

	@Test
	public void undoDecommission_OK_Case()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(1);

		//
		// Get, Verify & Decommission a product
		final SecurPharmProductId productId;
		{
			final SecurPharmProduct product = prepareProductForDecommissioning();
			productId = product.getId();

			final String decommissionServerTransactionId = "decomission-ServerTransactionId";
			mockClientDecommissionOK(product.getProductDetails(), decommissionServerTransactionId);
			securPharmService.decommissionProductIfEligible(product, inventoryId).get();
			assertProductSaved(product);
		}

		//
		// Undo-decommission
		final SecurPharmProduct product = productsRepo.getProductById(productId);

		mockClientUndoDecommissionOK(product.getProductDetails());
		final UndoDecommissionResponse undoDecommissionResponse = securPharmService.undoDecommissionProductIfEligible(product, inventoryId).get();

		//
		// Check UndoDecommission result
		assertThat(undoDecommissionResponse.getId()).isNotNull();
		assertThat(undoDecommissionResponse)
				.isEqualTo(UndoDecommissionResponse.builder()
						.error(false)
						.productId(productId)
						.serverTransactionId(product.getDecommissionServerTransactionId())
						.inventoryId(inventoryId)
						.id(undoDecommissionResponse.getId())
						.build());

		//
		// Check the product
		assertThat(product.isDecommissioned()).isFalse();
		assertProductSaved(product);
	}
}
