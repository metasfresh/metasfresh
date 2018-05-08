package de.metas.inventory.event;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import lombok.NonNull;

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
	public static final InventoryUserNotificationsProducer newInstance()
	{
		return new InventoryUserNotificationsProducer();
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.inventory.UserNotifications")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	/** M_Inventory internal use */
	private static final int WINDOW_INTERNAL_INVENTORY = 341; // FIXME: HARDCODED
	private static final String MSG_Event_InventoryGenerated = "Event_InventoryGenerated";

	private InventoryUserNotificationsProducer()
	{
	}

	public InventoryUserNotificationsProducer notifyGenerated(final Collection<? extends I_M_Inventory> inventories)
	{
		if (inventories == null || inventories.isEmpty())
		{
			return this;
		}

		postNotifications(inventories.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

		return this;
	}

	public final InventoryUserNotificationsProducer notifyGenerated(@NonNull final I_M_Inventory inventory)
	{
		notifyGenerated(ImmutableList.of(inventory));
		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final I_M_Inventory inventory)
	{
		final TableRecordReference inventoryRef = TableRecordReference.of(inventory);

		return newUserNotificationRequest()
				.recipientUserId(getNotificationRecipientUserId(inventory))
				.contentADMessage(MSG_Event_InventoryGenerated)
				.contentADMessageParam(inventoryRef)
				.targetRecord(inventoryRef)
				.targetADWindowId(WINDOW_INTERNAL_INVENTORY)
				.build();

	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private final int getNotificationRecipientUserId(final I_M_Inventory inventory)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (docActionBL.isDocumentReversedOrVoided(inventory))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return currentUserId;
			}

			return inventory.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return inventory.getCreatedBy();
		}
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}
}
