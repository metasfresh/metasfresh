package de.metas.order.event;

import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.QueueableForwardingEventBus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

public class OrderUserNotifications extends QueueableForwardingEventBus
{
	public static final OrderUserNotifications newInstance()
	{
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC);
		final OrderUserNotifications orderUserNotifications = new OrderUserNotifications(eventBus);
		orderUserNotifications.queueEventsUntilCurrentTrxCommit();
		return orderUserNotifications;
	}

	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.order.OrderUserNotifications")
			.type(Type.REMOTE)
			.build();

	private static final Logger logger = LogManager.getLogger(OrderUserNotifications.class);

	private static final String MSG_PurchaseOrderCompleted = "Event_PurchaseOrderCreated";
	private static final String MSG_SalesOrderCompleted = "Event_SalesOrderCreated";

	private OrderUserNotifications(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public OrderUserNotifications queueEvents()
	{
		super.queueEvents();
		return this;
	}

	@Override
	public OrderUserNotifications queueEventsUntilCurrentTrxCommit()
	{
		super.queueEventsUntilCurrentTrxCommit();
		return this;
	}

	public OrderUserNotifications notifyOrderCompleted(@NonNull final I_C_Order order)
	{
		final int recipientUserId = extractRecipientUserIdFromOrder(order);
		return notifyOrderCompleted(order, ImmutableSet.of(recipientUserId));
	}

	/**
	 * Post events about given document.
	 *
	 * @param order
	 * @param recipientUserId
	 */
	public OrderUserNotifications notifyOrderCompleted(@NonNull final I_C_Order order, final Set<Integer> recipientUserIds)
	{
		try
		{
			final List<Event> events = createOrderCompletedEvents(order, recipientUserIds);
			events.forEach(this::postEvent);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating event for " + order + ". Ignored.", ex);
		}

		return this;
	}

	private final List<Event> createOrderCompletedEvents(final I_C_Order order, final Set<Integer> recipientUserIds)
	{
		Check.assumeNotEmpty(recipientUserIds, "recipientUserIds is not empty");

		//
		// Extract event message parameters
		final IPair<String, Object[]> adMessageAndParams = extractOrderCompletedADMessageAndParams(order);
		final String adMessage = adMessageAndParams.getLeft();
		final Object[] adMessageParams = adMessageAndParams.getRight();

		//
		// Create an return the event
		return recipientUserIds.stream()
				.map(recipientUserId -> Event.builder()
						.setDetailADMessage(adMessage, adMessageParams)
						.addRecipient_User_ID(recipientUserId)
						.setRecord(TableRecordReference.of(order))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private static IPair<String, Object[]> extractOrderCompletedADMessageAndParams(final I_C_Order order)
	{
		final String adMessage = order.isSOTrx() ? MSG_SalesOrderCompleted : MSG_PurchaseOrderCompleted;

		final I_C_BPartner bpartner = order.getC_BPartner();
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();
		final Object[] params = new Object[] { TableRecordReference.of(order), bpValue, bpName };

		return ImmutablePair.of(adMessage, params);
	}

	private static int extractRecipientUserIdFromOrder(final I_C_Order order)
	{
		final Integer createdBy = InterfaceWrapperHelper.getValueOrNull(order, "CreatedBy");
		return createdBy == null ? -1 : createdBy;
	}

}
