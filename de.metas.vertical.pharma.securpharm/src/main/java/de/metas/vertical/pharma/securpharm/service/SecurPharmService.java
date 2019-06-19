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
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inventory.InventoryLineRepository;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.InventoryId;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionResultId;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.client.DecodeDataMatrixClientResponse;
import de.metas.vertical.pharma.securpharm.client.DecommisionClientResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.client.UndoDecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.client.VerifyProductClientResponse;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.ProductDetails;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;
import lombok.NonNull;

@Service
public class SecurPharmService
{
	private static final String MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE = "SecurpharmActionResultErrorNotificationMessage";

	private final SecurPharmClientFactory clientFactory;
	private final SecurPharmConfigRespository configRespository;
	private final SecurPharmProductRepository productsRepo;
	private final SecurPharmaActionRepository actionsRepo;
	private final SecurPharmLogRepository logsRepo;
	private final InventoryLineRepository inventoryRepo;

	public SecurPharmService(
			@NonNull final SecurPharmClientFactory clientFactory,
			@NonNull final SecurPharmConfigRespository configRespository,
			@NonNull final SecurPharmProductRepository productsRepo,
			@NonNull final SecurPharmaActionRepository actionsRepo,
			@NonNull final SecurPharmLogRepository logsRepo,
			@NonNull final InventoryLineRepository inventoryRepo)
	{
		this.clientFactory = clientFactory;
		this.configRespository = configRespository;

		this.productsRepo = productsRepo;
		this.actionsRepo = actionsRepo;
		this.logsRepo = logsRepo;
		this.inventoryRepo = inventoryRepo;
	}

	public boolean hasConfig()
	{
		return configRespository.getDefaultConfig().isPresent();
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		return productsRepo.getProductById(id);
	}

	public Collection<SecurPharmProduct> getProductsByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Set<HuId> huIds = getHUIdsByInventoryId(inventoryId);
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return productsRepo.getProductsByHuIds(huIds);
	}

	private Set<HuId> getHUIdsByInventoryId(final InventoryId inventoryId)
	{
		return inventoryRepo
				.getByInventoryId(inventoryId)
				.getHuIds();
	}

	public SecurPharmProduct getAndSaveProduct(
			@NonNull final DataMatrixCode datamatrix,
			@NonNull final HuId huId)
	{
		final SecurPharmClient client = clientFactory.createClient();

		final List<SecurPharmLog> logs = new ArrayList<>();

		//
		// Decode datamatrix
		final DecodeDataMatrixClientResponse decodeResult = client.decodeDataMatrix(datamatrix);
		logs.add(decodeResult.getLog());
		ProductDetails productDetails = decodeResult.getProductDetails();
		boolean error = decodeResult.isError();

		//
		// Verify product
		if (!error)
		{
			final VerifyProductClientResponse verifyResult = client.verifyProduct(productDetails);
			logs.add(verifyResult.getLog());
			productDetails = verifyResult.getProductDetails();
			error = verifyResult.isError();
		}

		final SecurPharmProduct product = SecurPharmProduct.builder()
				.error(error)
				.productDetails(productDetails)
				.huId(huId)
				.build();
		productsRepo.save(product);
		logsRepo.saveProductLogs(logs, product.getId());

		if (product.isError())
		{
			sendNotification(
					client.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					product.getRecordRef());
		}

		return product;
	}

	private void sendNotification(
			@NonNull final UserId recipientUserId,
			@NonNull final String notificationADMessage,
			@NonNull final TableRecordReference recordRef)
	{
		final String message = Services.get(IMsgBL.class).getMsg(Env.getCtx(), notificationADMessage);

		final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
				.recipientUserId(recipientUserId)
				.contentPlain(message)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(recordRef))
				.build();

		Services.get(INotificationBL.class).sendAfterCommit(userNotificationRequest);
	}

	public void decommissionProductsByInventoryId(final InventoryId inventoryId)
	{
		final Collection<SecurPharmProduct> products = getProductsByInventoryId(inventoryId);
		if (products.isEmpty())
		{
			return;
		}

		for (final SecurPharmProduct product : products)
		{
			decommissionProductIfEligible(product, inventoryId);
		}
	}

	public void decommissionProductIfEligible(final SecurPharmProduct product, final InventoryId inventoryId)
	{
		if (!isEligibleForDecommission(product))
		{
			return;
		}

		final SecurPharmClient client = clientFactory.createClient();
		final DecommisionClientResponse clientResponse = client.decommission(product.getProductDetails());

		final DecommissionResponse response = DecommissionResponse.builder()
				.error(clientResponse.isError())
				.inventoryId(inventoryId)
				.productDataResultId(product.getId())
				.serverTransactionId(clientResponse.getServerTransactionId())
				.build();
		final SecurPharmActionResultId actionId = actionsRepo.save(response);
		logsRepo.saveActionLog(
				clientResponse.getLog(),
				response.getProductDataResultId(),
				actionId);

		if (!response.isError())
		{
			product.productDecommissioned(response.getServerTransactionId());
			productsRepo.save(product);
		}
		else
		{
			sendNotification(
					client.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					TableRecordReference.of(org.compiere.model.I_M_Inventory.Table_Name, response.getInventoryId()));
		}
	}

	public boolean isEligibleForDecommission(@NonNull final SecurPharmProduct product)
	{
		if (product.isError())
		{
			return false;
		}

		if (!product.isActive())
		{
			return false;
		}

		return !product.isDecommissioned();
	}

	public void undoDecommissionProductsByInventoryId(final InventoryId inventoryId)
	{
		final Collection<SecurPharmProduct> products = getProductsByInventoryId(inventoryId);
		if (products.isEmpty())
		{
			return;
		}

		for (final SecurPharmProduct product : products)
		{
			undoDecommissionProductIfEligible(product, inventoryId);
		}
	}

	public void undoDecommissionProductIfEligible(final SecurPharmProduct product, final InventoryId inventoryId)
	{
		if (!isEligibleForUndoDecommission(product))
		{
			return;
		}

		final SecurPharmClient client = clientFactory.createClient();
		final UndoDecommissionClientResponse clientResponse = client.undoDecommission(
				product.getProductDetails(),
				product.getDecommissionServerTransactionId());

		final UndoDecommissionResponse response = UndoDecommissionResponse.builder()
				.error(clientResponse.isError())
				.inventoryId(inventoryId)
				.productDataResultId(product.getId())
				.serverTransactionId(clientResponse.getServerTransactionId())
				.build();
		final SecurPharmActionResultId actionId = actionsRepo.save(response);
		logsRepo.saveActionLog(
				clientResponse.getLog(),
				response.getProductDataResultId(),
				actionId);

		if (!response.isError())
		{
			product.productDecommissionUndo(clientResponse.getServerTransactionId());
			productsRepo.save(product);
		}
		else
		{
			sendNotification(
					client.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					TableRecordReference.of(org.compiere.model.I_M_Inventory.Table_Name, response.getInventoryId()));
		}
	}

	public boolean isEligibleForUndoDecommission(@NonNull final SecurPharmProduct product)
	{
		if (product.isError())
		{
			return false;
		}

		if (!product.isActive())
		{
			return false;
		}

		return product.isDecommissioned();
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
