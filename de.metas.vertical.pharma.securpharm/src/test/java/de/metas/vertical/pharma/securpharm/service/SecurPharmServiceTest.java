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

import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.inventory.InventoryId;
import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.client.schema.JsonExpirationDate;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductCodeType;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import de.metas.vertical.pharma.securpharm.product.ProductDetails.ProductDetailsBuilder;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;

public class SecurPharmServiceTest
{
	/** The service under test */
	private SecurPharmService securPharmService;

	//
	// Other services
	private MockedSecurPharmClientHelper clientHelper;
	private MockedSecurPharmUserNotifications userNotifications;
	private SecurPharmProductRepository productsRepo;

	private final UserId supportUserId = UserId.ofRepoId(12345);

	@Before
	public void beforeEachTest()
	{
		AdempiereTestHelper.get().init();

		clientHelper = MockedSecurPharmClientHelper.builder()
				.supportUserId(supportUserId)
				.build();

		//
		// Other services
		userNotifications = new MockedSecurPharmUserNotifications();
		productsRepo = new SecurPharmProductRepository();
		// final SecurPharmaActionRepository actionsRepo = Mockito.mock(SecurPharmaActionRepository.class);
		final SecurPharmaActionRepository actionsRepo = new SecurPharmaActionRepository();
		final SecurPharmLogRepository logsRepo = Mockito.mock(SecurPharmLogRepository.class);
		final InventoryRepository inventoryRepo = Mockito.mock(InventoryRepository.class);

		//
		// The service we are testing
		securPharmService = SecurPharmService.builder()
				.clientFactory(clientHelper.getClientFactory())
				.configRespository(clientHelper.getConfigRespository())
				.productsRepo(productsRepo)
				.actionsRepo(actionsRepo)
				.logsRepo(logsRepo)
				.actionRequestDispatcher(new DirectSecurPharmActionsDispatcher())
				.userNotifications(userNotifications)
				.inventoryRepo(inventoryRepo)
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

	private SecurPharmProduct prepareProductForDecommissioning()
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		final ProductDetails productDetails = newDummyProductDetails()
				.activeStatus(JsonProductPackageState.FRAUD)
				.build();
		clientHelper.mockDecodeAndVerifyOK(dataMatrix, productDetails);

		final HuId huId = HuId.ofRepoId(1);
		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);
		assertThat(product.isDecommissioned()).isFalse();

		//
		// Make sure the product is eligible for decommissioning
		assertThat(securPharmService.isEligibleForDecommission(product))
				.as("" + product + " shall be eligible for decommissioning")
				.isTrue();

		return product;
	}

	private DecommissionResponse prepareProductAndDecommissionIt()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(1);

		//
		// Get, Verify & Decommission a product
		final SecurPharmProduct product = prepareProductForDecommissioning();

		//
		// Decommission it
		final String decommissionServerTransactionId = "decomission-ServerTransactionId";
		clientHelper.mockDecommissionOK(product.getProductDetails(), decommissionServerTransactionId);
		final DecommissionResponse decommissionResult = securPharmService.decommissionProductIfEligible(product, inventoryId).get();
		assertProductSaved(product);

		//
		// Make sure the product is eligible for decommissioning
		assertThat(securPharmService.isEligibleForUndoDecommission(product))
				.as("" + product + " shall be eligible for undo-decommissioning")
				.isTrue();

		return decommissionResult;
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
		clientHelper.mockDecodeAndVerifyOK(dataMatrix, productDetails);

		final HuId huId = HuId.ofRepoId(1);
		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);

		assertThat(product)
				.isEqualTo(SecurPharmProduct.builder()
						.error(false)
						.resultCode(MockedSecurPharmClientHelper.RESULT_CODE_OK)
						.resultMessage(MockedSecurPharmClientHelper.RESULT_MESSAGE_OK)
						.id(product.getId())
						.productDetails(productDetails)
						.huId(huId)
						.build());

		userNotifications.assertNoErrors();
	}

	@Test
	public void getAndSaveProductData_FAIL_on_Decode()
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		// final ProductDetails productDetails = newDummyProductDetails().build();
		final HuId huId = HuId.ofRepoId(1);
		clientHelper.mockDecodeERROR(dataMatrix);
		// clientHelper.mockVerifyOK(productDetails);

		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);

		assertThat(product)
				.isEqualTo(SecurPharmProduct.builder()
						.error(true)
						.resultCode(MockedSecurPharmClientHelper.RESULT_CODE_DECODE_ERROR)
						.resultMessage(MockedSecurPharmClientHelper.RESULT_MESSAGE_DECODE_ERROR)
						.id(product.getId())
						.productDetails(null)
						.huId(huId)
						.build());

		userNotifications.assertProductDecodeAndVerifyError(product.getId(), supportUserId);
	}

	@Test
	public void getAndSaveProductData_FAIL_on_Verify()
	{
		final DataMatrixCode dataMatrix = DataMatrixCode.ofString("dummy datamatrix");
		final ProductDetails productDetails = newDummyProductDetails().build();
		final HuId huId = HuId.ofRepoId(1);
		clientHelper.mockDecodeOK(dataMatrix, productDetails);
		clientHelper.mockVerifyERROR(productDetails);

		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		assertProductSaved(product);

		assertThat(product)
				.isEqualTo(SecurPharmProduct.builder()
						.error(true)
						.resultCode(MockedSecurPharmClientHelper.RESULT_CODE_VERIFY_ERROR)
						.resultMessage(MockedSecurPharmClientHelper.RESULT_MESSAGE_VERIFY_ERROR)
						.id(product.getId())
						.productDetails(null)
						.huId(huId)
						.build());

		userNotifications.assertProductDecodeAndVerifyError(product.getId(), supportUserId);
	}

	@Test
	public void decommission_OK_Case()
	{
		final SecurPharmProduct product = prepareProductForDecommissioning();

		//
		// Decommission the product
		final InventoryId inventoryId = InventoryId.ofRepoId(1);
		final String serverTransactionId = "decomission-ServerTransactionId";
		clientHelper.mockDecommissionOK(product.getProductDetails(), serverTransactionId);
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

		userNotifications.assertNoErrors();
	}

	@Test
	public void decommission_Error_Case()
	{
		final SecurPharmProduct product = prepareProductForDecommissioning();

		//
		// Decommission the product
		final InventoryId inventoryId = InventoryId.ofRepoId(1);
		final String serverTransactionId = "decomission-ServerTransactionId";
		clientHelper.mockDecommissionERROR(serverTransactionId);
		final DecommissionResponse decommissionResponse = securPharmService.decommissionProductIfEligible(product, inventoryId).get();

		//
		// Check Decommission result
		assertThat(decommissionResponse.getId()).isNotNull();
		assertThat(decommissionResponse)
				.isEqualTo(DecommissionResponse.builder()
						.error(true)
						.productId(product.getId())
						.serverTransactionId(serverTransactionId)
						.inventoryId(inventoryId)
						.id(decommissionResponse.getId())
						.build());

		//
		// Check the product
		assertThat(product.isDecommissioned()).isFalse();
		assertThat(product.getDecommissionServerTransactionId()).isNull();
		assertProductSaved(product);

		userNotifications.assertDecommissionError(product.getId(), supportUserId);
	}

	@Test
	public void undoDecommission_OK_Case()
	{
		final DecommissionResponse decommissionResult = prepareProductAndDecommissionIt();
		final SecurPharmProductId productId = decommissionResult.getProductId();
		final InventoryId inventoryId = decommissionResult.getInventoryId();

		//
		// Undo-decommission
		final SecurPharmProduct product = productsRepo.getProductById(productId);
		clientHelper.mockUndoDecommissionOK(product.getProductDetails());
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

		userNotifications.assertNoErrors();
	}

	@Test
	public void undoDecommission_Error_Case()
	{
		final DecommissionResponse decommissionResult = prepareProductAndDecommissionIt();
		final SecurPharmProductId productId = decommissionResult.getProductId();
		final InventoryId inventoryId = decommissionResult.getInventoryId();

		//
		// Undo-decommission
		final SecurPharmProduct product = productsRepo.getProductById(productId);
		clientHelper.mockUndoDecommissionError(product.getProductDetails());
		final UndoDecommissionResponse undoDecommissionResponse = securPharmService.undoDecommissionProductIfEligible(product, inventoryId).get();

		//
		// Check UndoDecommission result
		assertThat(undoDecommissionResponse.getId()).isNotNull();
		assertThat(undoDecommissionResponse)
				.isEqualTo(UndoDecommissionResponse.builder()
						.error(true)
						.productId(productId)
						.serverTransactionId(product.getDecommissionServerTransactionId())
						.inventoryId(inventoryId)
						.id(undoDecommissionResponse.getId())
						.build());

		//
		// Check the product
		assertThat(product.isDecommissioned()).isTrue();
		assertProductSaved(product);

		userNotifications.assertUndoDecommissionError(productId, supportUserId);
	}

}
