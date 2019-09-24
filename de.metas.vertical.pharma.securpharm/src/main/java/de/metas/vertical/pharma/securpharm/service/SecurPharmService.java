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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionsDispatcher;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionsHandler;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRequest;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.client.DecodeDataMatrixClientResponse;
import de.metas.vertical.pharma.securpharm.client.DecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.client.UndoDecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.VerifyProductClientResponse;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.notifications.SecurPharmUserNotifications;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;
import lombok.Builder;
import lombok.NonNull;

@Service
public class SecurPharmService
{
	private final SecurPharmClientFactory clientFactory;
	private final SecurPharmConfigRespository configRespository;
	private final SecurPharmProductRepository productsRepo;
	private final SecurPharmaActionRepository actionsRepo;
	private final SecurPharmLogRepository logsRepo;
	private final SecurPharmUserNotifications userNotifications;

	private final InventoryRepository inventoryRepo;

	private final SecurPharmActionsDispatcher actionRequestDispatcher;

	@Builder
	public SecurPharmService(
			@NonNull final SecurPharmClientFactory clientFactory,
			@NonNull final SecurPharmConfigRespository configRespository,
			@NonNull final SecurPharmProductRepository productsRepo,
			@NonNull final SecurPharmaActionRepository actionsRepo,
			@NonNull final SecurPharmLogRepository logsRepo,
			@NonNull final SecurPharmActionsDispatcher actionRequestDispatcher,
			@NonNull final SecurPharmUserNotifications userNotifications,
			@NonNull final InventoryRepository inventoryRepo)
	{
		this.clientFactory = clientFactory;
		this.configRespository = configRespository;

		this.productsRepo = productsRepo;
		this.actionsRepo = actionsRepo;
		this.logsRepo = logsRepo;
		this.userNotifications = userNotifications;
		this.inventoryRepo = inventoryRepo;

		this.actionRequestDispatcher = actionRequestDispatcher;
	}

	public boolean hasConfig()
	{
		return configRespository.getDefaultConfig().isPresent();
	}

	private SecurPharmClient createClient()
	{
		final SecurPharmConfig config = configRespository
				.getDefaultConfig()
				.orElseThrow(() -> new AdempiereException("No default SecurPharm config found"));

		return clientFactory.createClient(config);
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		return productsRepo.getProductById(id);
	}

	public Collection<SecurPharmProduct> findProductsToDecommissionForInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Inventory inventory = inventoryRepo.getById(inventoryId);

		//
		// Applies only to internal use inventory
		if (!inventory.isInternalUseInventory())
		{
			return ImmutableList.of();
		}

		final List<InventoryLineHU> lineHUs = inventory.getLineHUs()
				.stream()
				.filter(lineHU -> isEligibleForDecommission(lineHU))
				.collect(ImmutableList.toImmutableList());
		final Set<HuId> vhuIds = getVHUIds(lineHUs);
		if (vhuIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return productsRepo.getProductsByHuIds(vhuIds);
	}

	public SecurPharmProduct getAndSaveProduct(
			@NonNull final DataMatrixCode datamatrix,
			@NonNull final HuId huId)
	{
		final SecurPharmClient client = createClient();

		final List<SecurPharmLog> logs = new ArrayList<>();

		//
		// Decode datamatrix
		final DecodeDataMatrixClientResponse decodeResult = client.decodeDataMatrix(datamatrix);
		logs.add(decodeResult.getLog());
		ProductDetails productDetails = decodeResult.getProductDetails();
		boolean error = decodeResult.isError();
		String resultCode = decodeResult.getResultCode();
		String resultMessage = decodeResult.getResultMessage();

		//
		// Verify product
		if (!error)
		{
			final VerifyProductClientResponse verifyResult = client.verifyProduct(productDetails);
			logs.add(verifyResult.getLog());
			productDetails = verifyResult.getProductDetails();
			error = verifyResult.isError();
			resultCode = verifyResult.getResultCode();
			resultMessage = verifyResult.getResultMessage();
		}

		final SecurPharmProduct product = SecurPharmProduct.builder()
				.error(error)
				.resultCode(resultCode)
				.resultMessage(resultMessage)
				.productDetails(productDetails)
				.huId(huId)
				.build();
		productsRepo.save(product);
		logsRepo.saveProductLogs(logs, product.getId());

		if (product.isError())
		{
			userNotifications.notifyProductDecodeAndVerifyError(client.getSupportUserId(), product);
		}

		return product;
	}

	public void scheduleAction(@NonNull final SecurPharmaActionRequest request)
	{
		actionRequestDispatcher.post(request);
	}

	public void subscribeOnActions(@NonNull final SecurPharmActionsHandler handler)
	{
		actionRequestDispatcher.subscribe(handler);
	}

	public void decommissionProductsByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Collection<SecurPharmProduct> products = findProductsToDecommissionForInventoryId(inventoryId);
		if (products.isEmpty())
		{
			return;
		}

		for (final SecurPharmProduct product : products)
		{
			decommissionProductIfEligible(product, inventoryId);
		}
	}

	private boolean isEligibleForDecommission(final InventoryLineHU lineHU)
	{
		// TODO: shall we check if the MovementQty is "-1";
		return true;
	}

	private Set<HuId> getVHUIds(final List<InventoryLineHU> lineHUs)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Set<HuId> huIds = InventoryLineHU.extractHuIds(lineHUs);
		return handlingUnitsBL.getVHUIds(huIds);
	}

	public Optional<DecommissionResponse> decommissionProductIfEligible(
			@NonNull final SecurPharmProductId productId,
			@Nullable final InventoryId inventoryId)
	{
		final SecurPharmProduct product = productsRepo.getProductById(productId);
		return decommissionProductIfEligible(product, inventoryId);
	}

	public Optional<DecommissionResponse> decommissionProductIfEligible(
			@NonNull final SecurPharmProduct product,
			@Nullable final InventoryId inventoryId)
	{
		if (!isEligibleForDecommission(product))
		{
			return Optional.empty();
		}

		final SecurPharmClient client = createClient();
		final DecommissionClientResponse clientResponse = client.decommission(product.getProductDetails());

		final DecommissionResponse response = DecommissionResponse.builder()
				.error(clientResponse.isError())
				.inventoryId(inventoryId)
				.productId(product.getId())
				.serverTransactionId(clientResponse.getServerTransactionId())
				.build();
		actionsRepo.save(response);

		logsRepo.saveActionLog(
				clientResponse.getLog(),
				response.getProductId(),
				response.getId());

		if (!response.isError())
		{
			product.productDecommissioned(response.getServerTransactionId());
			productsRepo.save(product);
		}
		else
		{
			userNotifications.notifyDecommissionFailed(client.getSupportUserId(), response);
		}

		return Optional.of(response);
	}

	public boolean isEligibleForDecommission(@NonNull final SecurPharmProduct product)
	{
		return product.isFraud() // fraud product
				&& !product.isDecommissioned() // was not already decommissioned
				&& !product.isError() // no errors
		;
	}

	public void undoDecommissionProductsByInventoryId(final InventoryId inventoryId)
	{
		final Set<SecurPharmProductId> productIds = actionsRepo.getProductIdsByInventoryId(inventoryId);
		final Collection<SecurPharmProduct> products = productsRepo.getProductsByIds(productIds);
		if (products.isEmpty())
		{
			return;
		}

		for (final SecurPharmProduct product : products)
		{
			undoDecommissionProductIfEligible(product, inventoryId);
		}
	}

	public Optional<UndoDecommissionResponse> undoDecommissionProductIfEligible(
			@NonNull final SecurPharmProductId productId,
			@Nullable final InventoryId inventoryId)
	{
		final SecurPharmProduct product = productsRepo.getProductById(productId);
		return undoDecommissionProductIfEligible(product, inventoryId);
	}

	public Optional<UndoDecommissionResponse> undoDecommissionProductIfEligible(
			@NonNull final SecurPharmProduct product,
			@Nullable final InventoryId inventoryId)
	{
		if (!isEligibleForUndoDecommission(product))
		{
			return Optional.empty();
		}

		final SecurPharmClient client = createClient();
		final UndoDecommissionClientResponse clientResponse = client.undoDecommission(
				product.getProductDetails(),
				product.getDecommissionServerTransactionId());

		final UndoDecommissionResponse response = UndoDecommissionResponse.builder()
				.error(clientResponse.isError())
				.inventoryId(inventoryId)
				.productId(product.getId())
				.serverTransactionId(clientResponse.getServerTransactionId())
				.build();
		actionsRepo.save(response);

		logsRepo.saveActionLog(
				clientResponse.getLog(),
				response.getProductId(),
				response.getId());

		if (!response.isError())
		{
			product.productDecommissionUndo(clientResponse.getServerTransactionId());
			productsRepo.save(product);
		}
		else
		{
			userNotifications.notifyUndoDecommissionFailed(client.getSupportUserId(), response);
		}

		return Optional.of(response);
	}

	public boolean isEligibleForUndoDecommission(@NonNull final SecurPharmProduct product)
	{
		return product.isDecommissioned() // was already decommissioned
				&& !product.isError() // no errors
		;
	}

	public SecurPharmHUAttributesScanner newHUScanner()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);

		return SecurPharmHUAttributesScanner.builder()
				.securPharmService(this)
				.handlingUnitsBL(handlingUnitsBL)
				.handlingUnitsRepo(handlingUnitsRepo)
				.build();
	}
}
