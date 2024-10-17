package de.metas.contracts.event;

import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class FlatrateUserNotificationsProducer
{
	public static final FlatrateUserNotificationsProducer newInstance()
	{
		return new FlatrateUserNotificationsProducer();
	}

	private static final transient Logger logger = LogManager.getLogger(FlatrateUserNotificationsProducer.class);

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.contracts.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	public FlatrateUserNotificationsProducer notifyUser(
			final I_C_Flatrate_Term contract,
			final UserId recipientUserId,
			@NonNull final AdMessageKey message)
	{
		if (contract == null)
		{
			return this;
		}

		try
		{
			postNotification(createFlatrateTermGeneratedEvent(contract, recipientUserId, message));
		}
		catch (final Exception ex)
		{

			logger.warn("Failed creating event for contract {}. Ignored.", contract, ex);
		}

		return this;
	}

	private final UserNotificationRequest createFlatrateTermGeneratedEvent(
			@NonNull final I_C_Flatrate_Term contract,
			final UserId recipientUserId,
			@NonNull final AdMessageKey message)
	{
		if (recipientUserId == null)
		{
			// nothing to do
			return null;
		}

		final TableRecordReference flatrateTermRef = TableRecordReference.of(contract);

		return newUserNotificationRequest()
				.recipientUserId(recipientUserId)
				.contentADMessage(message)
				.targetAction(TargetRecordAction.ofRecordAndWindow(flatrateTermRef, Contracts_Constants.CONTRACTS_WINDOW_ID))
				.build();
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()

				.topic(EVENTBUS_TOPIC);
	}

	private void postNotification(final UserNotificationRequest notification)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notification);
	}
}
