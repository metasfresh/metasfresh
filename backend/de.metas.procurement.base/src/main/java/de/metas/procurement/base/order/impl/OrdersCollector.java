package de.metas.procurement.base.order.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.metas.i18n.AdMessageKey;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import com.google.common.collect.ImmutableList;

import de.metas.event.DocumentUserNotificationsProducer;
import de.metas.order.IOrderBL;
import de.metas.procurement.base.ProcurementConstants;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;

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

/**
 * Default {@link IOrdersCollector} implementation which is:
 * <ul>
 * <li>counts generated orders: {@link #getCountOrders()}
 * <li>notifies on event bus that an order was generated
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrdersCollector implements IOrdersCollector
{
	public static OrdersCollector newInstance()
	{
		return new OrdersCollector();
	}

	private final AtomicInteger countOrders = new AtomicInteger(0);

	private static final AdMessageKey MSG_Event_Generated = AdMessageKey.of("Event_ProcurementPurchaseOrderGenerated");
	private final DocumentUserNotificationsProducer<I_C_Order> orderGeneratedNotifier = DocumentUserNotificationsProducer.<I_C_Order> builder()
			.logger(ProcurementConstants.getLogger(OrdersCollector.class))
			.topic(ProcurementConstants.USER_NOTIFICATIONS_TOPIC)
			.eventAD_Message(MSG_Event_Generated)
			.eventAD_MessageParamsExtractor(OrdersCollector::extractUserNotificationADMessageParams)
			.build();

	private UserId defaultNotificationRecipientId;

	private OrdersCollector()
	{
	}

	private static List<Object> extractUserNotificationADMessageParams(final I_C_Order order)
	{
		final I_C_BPartner bpartner = Services.get(IOrderBL.class).getBPartner(order);
		return ImmutableList.builder()
				.add(TableRecordReference.of(order))
				.add(bpartner.getValue())
				.add(bpartner.getName())
				.build();
	}

	public void setDefaultNotificationRecipientId(final UserId defaultNotificationRecipientId)
	{
		this.defaultNotificationRecipientId = defaultNotificationRecipientId;
	}

	@Override
	public void add(final I_C_Order order)
	{
		Loggables.addLog("@Created@ " + order.getDocumentNo());

		orderGeneratedNotifier.notify(order, defaultNotificationRecipientId);

		countOrders.incrementAndGet();
	}

	public int getCountOrders()
	{
		return countOrders.get();
	}
}
