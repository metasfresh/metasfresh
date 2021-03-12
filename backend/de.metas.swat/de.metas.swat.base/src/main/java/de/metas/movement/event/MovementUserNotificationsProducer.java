package de.metas.movement.event;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Movement;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.List;

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
	public static MovementUserNotificationsProducer newInstance()
	{
		return new MovementUserNotificationsProducer();
	}

	/** Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously */
	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.movement.UserNotifications")
			.type(Type.REMOTE)
			.build();

	// services
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);

	private static final AdMessageKey MSG_Event_MovementGenerated = AdMessageKey.of("Event_MovementGenerated");

	private MovementUserNotificationsProducer()
	{
	}

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

	public final MovementUserNotificationsProducer notifyProcessed(@NonNull final I_M_Movement movement)
	{
		notifyProcessed(ImmutableList.of(movement));
		return this;
	}

	private UserNotificationRequest createUserNotification(@NonNull final I_M_Movement movement)
	{
		final UserId recipientUserId = getNotificationRecipientUserId(movement);
		final TableRecordReference movementRef = TableRecordReference.of(movement);
		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(MSG_Event_MovementGenerated)
				.contentADMessageParam(movementRef)
				.targetAction(TargetRecordAction.of(movementRef))
				.build();
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC);
	}

	private UserId getNotificationRecipientUserId(final I_M_Movement movement)
	{
		//
		// In case of reversal i think we shall notify the current user too
		final DocStatus docStatus = DocStatus.ofCode(movement.getDocStatus());
		if (docStatus.isReversedOrVoided())
		{
			final int currentUserId = Env.getAD_User_ID(Env.getCtx()); // current/triggering user
			if (currentUserId > 0)
			{
				return UserId.ofRepoId(currentUserId);
			}

			return UserId.ofRepoId(movement.getUpdatedBy()); // last updated
		}
		//
		// Fallback: notify only the creator
		else
		{
			return UserId.ofRepoId(movement.getCreatedBy());
		}
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		notificationBL.sendAfterCommit(notifications);
	}
}
