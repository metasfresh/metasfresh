package de.metas.inventory.event;

import java.util.Collection;

import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.event.Topic;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class InventoryUserNotificationsProducer
{
	public static InventoryUserNotificationsProducer newInstance()
	{
		return new InventoryUserNotificationsProducer();
	}

	// services
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.inventory.UserNotifications");

	/** M_Inventory internal use */
	private static final AdWindowId WINDOW_INTERNAL_INVENTORY = AdWindowId.ofRepoId(341); // FIXME: HARDCODED
	private static final AdMessageKey MSG_Event_InventoryGenerated = AdMessageKey.of("Event_InventoryGenerated");

	private InventoryUserNotificationsProducer()
	{
	}

	public void notifyGenerated(@Nullable final Collection<? extends I_M_Inventory> inventories)
	{
		if (inventories == null || inventories.isEmpty())
		{
			return;
		}

		notificationBL.sendAfterCommit(inventories.stream()
				.map(InventoryUserNotificationsProducer::toUserNotification)
				.collect(ImmutableList.toImmutableList()));
	}

	private static UserNotificationRequest toUserNotification(@NonNull final I_M_Inventory inventory)
	{
		final TableRecordReference inventoryRef = TableRecordReference.of(inventory);

		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC)
				.recipientUserId(getNotificationRecipientUserId(inventory))
				.contentADMessage(MSG_Event_InventoryGenerated)
				.contentADMessageParam(inventoryRef)
				.targetAction(TargetRecordAction.ofRecordAndWindow(inventoryRef, WINDOW_INTERNAL_INVENTORY))
				.build();

	}

	private static UserId getNotificationRecipientUserId(final I_M_Inventory inventory)
	{
		//
		// In case of reversal i think we shall notify the current user too
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(inventory.getDocStatus());
		if (docStatus.isReversedOrVoided())
		{
			final UserId loggedUserId = Env.getLoggedUserIdIfExists().orElse(null);
			if (loggedUserId != null)
			{
				return loggedUserId;
			}

			return UserId.ofRepoId(inventory.getUpdatedBy()); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return UserId.ofRepoId(inventory.getCreatedBy());
		}
	}
}
