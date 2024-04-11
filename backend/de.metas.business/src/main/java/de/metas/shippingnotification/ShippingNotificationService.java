/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShippingNotificationService
{
	// services
	private final ShippingNotificationRepository shippingNotificationRepository;
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private static final AdMessageKey MSG_M_Shipment_Notification_CompletedNotifications = AdMessageKey.of("de.metas.shippingnotification.CompletedShippingNotifications");

	public ShippingNotificationCollection getByQuery(@NonNull final ShippingNotificationQuery query)
	{
		return shippingNotificationRepository.getByQuery(query);
	}

	public ShippingNotification getByRecord(@NonNull final I_M_Shipping_Notification record)
	{
		return shippingNotificationRepository.getByRecord(record);
	}

	public I_M_Shipping_Notification getRecordById(@NonNull final ShippingNotificationId id)
	{
		return shippingNotificationRepository.getRecordById(id);
	}

	public I_M_Shipping_NotificationLine getLineRecordByLineId(@NonNull final ShippingNotificationLineId id)
	{
		return shippingNotificationRepository.getLineRecordByLineId(id);
	}

	@NonNull
	public ShippingNotification getById(@NonNull final ShippingNotificationId id)
	{
		return shippingNotificationRepository.getById(id);
	}

	public void updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Consumer<ShippingNotification> consumer)
	{
		shippingNotificationRepository.updateWhileSaving(record, shippingNotification -> {
			consumer.accept(shippingNotification);
			return null;
		});
	}

	public <R> R updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Function<ShippingNotification, R> consumer)
	{
		return shippingNotificationRepository.updateWhileSaving(record, consumer);
	}

	public void save(final ShippingNotification shippingNotification)
	{
		shippingNotificationRepository.save(shippingNotification);
	}

	public void completeIt(final ShippingNotification shippingNotification)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = shippingNotificationRepository.saveAndGetRecord(shippingNotification);
		documentBL.processEx(shippingNotificationRecord, IDocument.ACTION_Complete);
	}

	public void reverseBySalesOrderId(@NonNull final OrderId orderId)
	{
		final Set<ShippingNotificationId> shippingNotificationIds = shippingNotificationRepository.listIds(ShippingNotificationQuery.completedOrClosedByOrderId(orderId));
		reverseByIds(shippingNotificationIds);
	}

	public void reverseBySalesOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return;
		}

		final Set<ShippingNotificationId> shippingNotificationIds = shippingNotificationRepository.listIds(ShippingNotificationQuery.completedOrClosedByOrderIds(orderIds));
		reverseByIds(shippingNotificationIds);
	}

	public void reverseByIds(final Set<ShippingNotificationId> shippingNotificationIds)
	{
		shippingNotificationRepository.getRecordsByIds(shippingNotificationIds)
				.forEach(this::reverseByRecord);
	}

	private void reverseByRecord(final I_M_Shipping_Notification record)
	{
		documentBL.processEx(record, IDocument.ACTION_Reverse_Correct);
	}

	public void assertNoCompletedNorClosedShippingNotifications(final OrderId salesOrderId)
	{
		if (shippingNotificationRepository.anyMatch(ShippingNotificationQuery.completedOrClosedByOrderId(salesOrderId)))
		{
			throw new AdempiereException(MSG_M_Shipment_Notification_CompletedNotifications);
		}
	}

	@NonNull
	public ImmutableList<I_M_Shipping_NotificationLine> getLines(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return ImmutableList.copyOf(getLines(ImmutableSet.of(shippingNotificationId))
				.get(shippingNotificationId));
	}

	@NonNull
	public Map<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> getLines(@NonNull final Collection<ShippingNotificationId> shippingNotificationIdCollection)
	{
		return shippingNotificationRepository.getLines(shippingNotificationIdCollection);
	}

	@NonNull
	public Stream<ShippingNotificationId> streamIds(@NonNull final IQueryFilter<I_M_Shipping_Notification> shippingNotificationFilter)
	{
		return shippingNotificationRepository.streamIds(shippingNotificationFilter);
	}
}
