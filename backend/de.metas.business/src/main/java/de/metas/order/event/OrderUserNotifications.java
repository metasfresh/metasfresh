package de.metas.order.event;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.ADMessageAndParams;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.order.IOrderBL;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrderUserNotifications
{
	public static OrderUserNotifications newInstance()
	{
		return new OrderUserNotifications();
	}

	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.order.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private static final Logger logger = LogManager.getLogger(OrderUserNotifications.class);

	private static final AdMessageKey MSG_PurchaseOrderCompleted = AdMessageKey.of("Event_PurchaseOrderCreated");
	private static final AdMessageKey MSG_SalesOrderCompleted = AdMessageKey.of("Event_SalesOrderCreated");

	private OrderUserNotifications()
	{
	}

	/**
	 * Convenience method that calls {@link #notifyOrderCompleted(NotificationRequest)} with the order's creator as recipient.
	 */
	public OrderUserNotifications notifyOrderCompleted(@NonNull final I_C_Order order)
	{
		final NotificationRequest request = NotificationRequest.builder()
				.order(order).build();

		return notifyOrderCompleted(request);
	}

	public OrderUserNotifications notifyOrderCompleted(@NonNull final NotificationRequest request)
	{
		final I_C_Order order = request.getOrder();

		final Set<UserId> recipientUserIds = Optional
				.ofNullable(request.getRecipientUserIds())
				.orElse(extractRecipientUserIdsFromOrder(order));

		final ADMessageAndParams adMessageAndParams = Optional
				.ofNullable(request.getAdMessageAndParams())
				.orElse(extractOrderCompletedADMessageAndParams(order));

		try
		{
			postNotifications(createOrderCompletedEvents(
					order,
					recipientUserIds,
					adMessageAndParams));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating event for " + order + ". Ignored.", ex);
		}

		return this;
	}

	private List<UserNotificationRequest> createOrderCompletedEvents(
			@NonNull final I_C_Order order,
			@NonNull final Set<UserId> recipientUserIds,
			@NonNull final ADMessageAndParams adMessageAndParams)
	{
		Check.assumeNotEmpty(recipientUserIds, "recipientUserIds is not empty");

		return recipientUserIds.stream()
				.filter(Objects::nonNull)
				.map(recipientUserId -> newUserNotificationRequest()
						.recipientUserId(recipientUserId)
						.contentADMessage(adMessageAndParams.getAdMessage())
						.contentADMessageParams(adMessageAndParams.getParams())
						.targetAction(TargetRecordAction.of(TableRecordReference.of(order)))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC);
	}

	private static ADMessageAndParams extractOrderCompletedADMessageAndParams(final I_C_Order order)
	{
		final I_C_BPartner bpartner = Services.get(IOrderBL.class).getBPartner(order);
		return ADMessageAndParams.builder()
				.adMessage(order.isSOTrx() ? MSG_SalesOrderCompleted : MSG_PurchaseOrderCompleted)
				.param(TableRecordReference.of(order))
				.param(bpartner.getValue())
				.param(bpartner.getName())
				.build();
	}

	private static Set<UserId> extractRecipientUserIdsFromOrder(final I_C_Order order)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(order, "CreatedBy");
		final UserId recipientUserId = createdBy == null ? null : UserId.ofRepoIdOrNull(createdBy);
		return recipientUserId != null ? ImmutableSet.of(recipientUserId) : ImmutableSet.of();
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}

	@Value
	@Builder
	public static class NotificationRequest
	{
		@NonNull
		I_C_Order order;

		@Nullable
		Set<UserId> recipientUserIds;

		@Nullable
		ADMessageAndParams adMessageAndParams;
	}

}
