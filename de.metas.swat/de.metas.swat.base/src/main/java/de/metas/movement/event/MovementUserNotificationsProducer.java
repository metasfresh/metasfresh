package de.metas.movement.event;

import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Movement;
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

public class MovementUserNotificationsProducer
{
	public static final MovementUserNotificationsProducer newInstance()
	{
		return new MovementUserNotificationsProducer();
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.movement.UserNotifications")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private static final String MSG_Event_MovementGenerated = "Event_MovementGenerated";

	private MovementUserNotificationsProducer()
	{
	}

	/**
	 * Post events about given shipment/receipts that were processed.
	 *
	 * @param inouts
	 * @see #notifyProcessed(I_M_InOut)
	 */
	public MovementUserNotificationsProducer notifyProcessed(final Collection<? extends I_M_Movement> movements)
	{
		if (movements == null || movements.isEmpty())
		{
			return this;
		}

		postNotifications(movements.stream()
				.map(this::createUserNotification)
				.collect(ImmutableList.toImmutableList()));

		return this;
	}

	/**
	 * Post events about given shipment/receipts that were processed, i.e.
	 * <ul>
	 * <li>if inout's DocStatus is Completed, a "generated" notification will be sent
	 * <li>if inout's DocStatus is Voided or Reversed, a "reversed" notification will be sent
	 * </ul>
	 *
	 * @param inout
	 */
	public final MovementUserNotificationsProducer notifyProcessed(final I_M_Movement movement)
	{
		Check.assumeNotNull(movement, "inout not null");
		notifyProcessed(ImmutableList.of(movement));
		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final I_M_Movement movement)
	{
		final String adMessage = getNotificationAD_Message(movement);
		final int recipientUserId = getNotificationRecipientUserId(movement);

		final TableRecordReference movementRef = TableRecordReference.of(movement);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(adMessage)
				.contentADMessageParam(movementRef)
				.targetRecord(movementRef)
				.build();
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private final String getNotificationAD_Message(final I_M_Movement movement)
	{

		return MSG_Event_MovementGenerated;
	}

	private final int getNotificationRecipientUserId(final I_M_Movement movement)
	{
		//
		// In case of reversal i think we shall notify the current user too
		if (docActionBL.isDocumentReversedOrVoided(movement))
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return currentUserId;
			}

			return movement.getUpdatedBy(); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return movement.getCreatedBy();
		}
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).notifyUserAfterCommit(notifications);
	}
}
