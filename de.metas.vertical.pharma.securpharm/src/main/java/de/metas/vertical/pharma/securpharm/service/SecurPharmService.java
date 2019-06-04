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

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.InventoryId;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.DecodeDataMatrixResponse;
import de.metas.vertical.pharma.securpharm.model.DecommisionClientResponse;
import de.metas.vertical.pharma.securpharm.model.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.model.UndoDecommissionClientResponse;
import de.metas.vertical.pharma.securpharm.model.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.VerifyProductResponse;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmConfigRespository;
import lombok.NonNull;

@Service
public class SecurPharmService
{
	private static final String MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE = "SecurpharmActionResultErrorNotificationMessage";

	private final SecurPharmClientFactory clientFactory;
	private final SecurPharmResultService resultService;
	private final SecurPharmConfigRespository configRespository;

	public SecurPharmService(
			@NonNull final SecurPharmClientFactory clientFactory,
			@NonNull final SecurPharmResultService resultService,
			@NonNull final SecurPharmConfigRespository configRespository)
	{
		this.clientFactory = clientFactory;
		this.resultService = resultService;
		this.configRespository = configRespository;
	}

	public boolean hasConfig()
	{
		return configRespository.getDefaultConfig().isPresent();
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		return resultService.getProductById(id);
	}

	public Collection<SecurPharmProduct> getProductsByInventoryId(@NonNull final InventoryId inventoryId)
	{
		return resultService.getProductsByInventoryId(inventoryId);
	}

	public SecurPharmProduct getAndSaveProduct(
			@NonNull final DataMatrixCode datamatrix,
			@NonNull final HuId huId)
	{
		final SecurPharmClient client = clientFactory.createClient();

		final List<SecurPharmLog> logs = new ArrayList<>();

		//
		// Decode datamatrix
		final DecodeDataMatrixResponse decodeResult = client.decodeDataMatrix(datamatrix);
		logs.add(decodeResult.getLog());
		ProductDetails productDetails = decodeResult.getProductDetails();
		boolean error = decodeResult.isError();

		//
		// Verify product
		if (!error)
		{
			final VerifyProductResponse verifyResult = client.verifyProduct(productDetails);
			logs.add(verifyResult.getLog());
			productDetails = verifyResult.getProductDetails();
			error = verifyResult.isError();
		}

		final SecurPharmProduct product = SecurPharmProduct.builder()
				.error(error)
				.productDetails(productDetails)
				.huId(huId)
				.build();
		resultService.save(product, logs);

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
		final Collection<SecurPharmProduct> products = resultService.getProductsByInventoryId(inventoryId);
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

		resultService.save(response, ImmutableList.of(clientResponse.getLog()));

		if (!response.isError())
		{
			product.productDecommissioned(response.getServerTransactionId());
			resultService.save(product);
		}
		else
		{
			sendNotification(
					client.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					TableRecordReference.of(I_M_Inventory.Table_Name, response.getInventoryId()));
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
		final Collection<SecurPharmProduct> products = resultService.getProductsByInventoryId(inventoryId);
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

		resultService.save(response, ImmutableList.of(clientResponse.getLog()));

		if (!response.isError())
		{
			product.productDecommissionUndo(clientResponse.getServerTransactionId());
			resultService.save(product);
		}
		else
		{
			sendNotification(
					client.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					TableRecordReference.of(I_M_Inventory.Table_Name, response.getInventoryId()));
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
}
