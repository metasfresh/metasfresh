package de.metas.order.event;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
	public static final OrderUserNotifications newInstance()
	{
		return new OrderUserNotifications();
	}

	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.builder()
			.name("de.metas.order.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private static final Logger logger = LogManager.getLogger(OrderUserNotifications.class);

	private static final String MSG_PurchaseOrderCompleted = "Event_PurchaseOrderCreated";
	private static final String MSG_SalesOrderCompleted = "Event_SalesOrderCreated";

	private OrderUserNotifications()
	{
	}

	/**
	 * Convenience method that calls {@link #notifyOrderCompleted(NotificationRequest)} with the order's creator as recipient.
	 *
	 * @param order
	 * @return
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

		final Set<Integer> recipientUserIds = Optional
				.ofNullable(request.getRecipientUserIds())
				.orElse(ImmutableSet.of(extractRecipientUserIdFromOrder(order)));

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

	private final List<UserNotificationRequest> createOrderCompletedEvents(
			@NonNull final I_C_Order order,
			@NonNull final Set<Integer> recipientUserIds,
			@NonNull final ADMessageAndParams adMessageAndParams)
	{
		Check.assumeNotEmpty(recipientUserIds, "recipientUserIds is not empty");

		return recipientUserIds.stream()
				.filter(recipientUserId -> recipientUserId > 0)
				.map(recipientUserId -> newUserNotificationRequest()
						.recipientUserId(recipientUserId)
						.contentADMessage(adMessageAndParams.getAdMessage())
						.contentADMessageParams(adMessageAndParams.getParams())
						.targetRecord(TableRecordReference.of(order))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(USER_NOTIFICATIONS_TOPIC);
	}

	private static ADMessageAndParams extractOrderCompletedADMessageAndParams(final I_C_Order order)
	{
		final I_C_BPartner bpartner = order.getC_BPartner();
		return ADMessageAndParams.builder()
				.adMessage(order.isSOTrx() ? MSG_SalesOrderCompleted : MSG_PurchaseOrderCompleted)
				.param(TableRecordReference.of(order))
				.param(bpartner.getValue())
				.param(bpartner.getName())
				.build();
	}

	private static int extractRecipientUserIdFromOrder(final I_C_Order order)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(order, "CreatedBy");
		return createdBy == null ? -1 : createdBy;
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).notifyRecipientAfterCommit(notifications);
	}

	@Value
	@Builder
	public static class ADMessageAndParams
	{
		@NonNull
		String adMessage;
		@Singular
		List<Object> params;
	}

	@Value
	@Builder
	public static class NotificationRequest
	{
		@NonNull
		I_C_Order order;

		@Nullable
		Set<Integer> recipientUserIds;

		@Nullable
		ADMessageAndParams adMessageAndParams;
	}

}
