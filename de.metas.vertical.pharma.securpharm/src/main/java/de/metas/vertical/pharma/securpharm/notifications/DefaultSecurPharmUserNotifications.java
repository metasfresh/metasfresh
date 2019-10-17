package de.metas.vertical.pharma.securpharm.notifications;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class DefaultSecurPharmUserNotifications implements SecurPharmUserNotifications
{
	private static final String MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE = "SecurpharmActionResultErrorNotificationMessage";

	@Override
	public void notifyProductDecodeAndVerifyError(
			@NonNull final UserId responsibleId,
			@NonNull final SecurPharmProduct product)
	{
		sendNotification(
				responsibleId,
				MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
				TableRecordReference.of(I_M_Securpharm_Productdata_Result.Table_Name, product.getId()));
	}

	@Override
	public void notifyDecommissionFailed(
			@NonNull final UserId responsibleId,
			@NonNull final DecommissionResponse response)
	{
		sendNotification(
				responsibleId,
				MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
				TableRecordReference.of(org.compiere.model.I_M_Inventory.Table_Name, response.getInventoryId()));
	}

	@Override
	public void notifyUndoDecommissionFailed(
			@NonNull final UserId responsibleId,
			@NonNull final UndoDecommissionResponse response)
	{
		sendNotification(
				responsibleId,
				MSG_SECURPHARM_ACTION_RESULT_ERROR_NOTIFICATION_MESSAGE,
				TableRecordReference.of(org.compiere.model.I_M_Inventory.Table_Name, response.getInventoryId()));
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

}
