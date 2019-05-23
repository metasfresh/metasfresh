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

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.SecurPharmClient;
import de.metas.vertical.pharma.securpharm.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.model.DecommisionRequest;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResultId;
import de.metas.vertical.pharma.securpharm.model.UndoDecommisionRequest;
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
		return configRespository.isConfigured();
	}

	public SecurPharmProductDataResult getProductDataResultById(@NonNull final SecurPharmProductDataResultId productDataResultId)
	{
		return resultService.getProductDataResultById(productDataResultId);
	}

	public SecurPharmProductDataResult getAndSaveProductData(
			@NonNull final String datamatrix,
			@NonNull final HuId huId)
	{
		final SecurPharmClient client = clientFactory.createClient();

		final SecurPharmProductDataResult productDataResult = client.decodeDataMatrix(datamatrix);
		productDataResult.setHuId(huId);

		resultService.saveNew(productDataResult);

		if (productDataResult.isError())
		{
			sendNotification(
					client.getConfig().getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					productDataResult.getRecordRef());
		}

		return productDataResult;
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

	@Async
	public void decommision(@NonNull final DecommisionRequest request)
	{
		final SecurPharmClient client = clientFactory.createClient();
		final SecurPharmActionResult actionResult = client.decommission(request.getProductData());

		actionResult.setInventoryId(request.getInventoryId());
		actionResult.setProductDataResultId(request.getProductDataResultId());

		handleActionResult(actionResult, client.getConfig());
	}

	@Async
	public void undoDecommision(@NonNull final UndoDecommisionRequest request)
	{
		final SecurPharmClient client = clientFactory.createClient();
		final SecurPharmActionResult actionResult = client.undoDecommission(
				request.getProductData(),
				request.getServerTransactionId());

		actionResult.setInventoryId(request.getInventoryId());
		actionResult.setProductDataResultId(request.getProductDataResultId());

		handleActionResult(actionResult, client.getConfig());
	}

	private void handleActionResult(
			@NonNull final SecurPharmActionResult result,
			@NonNull final SecurPharmConfig config)
	{
		resultService.saveNew(result);
		if (result.isError())
		{
			sendNotification(
					config.getSupportUserId(),
					MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
					TableRecordReference.of(I_M_Inventory.Table_Name, result.getInventoryId()));
		}
	}
}
