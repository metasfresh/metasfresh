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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ShippingNotificationService
{
	private final ShippingNotificationRepository shippingNotificationRepository;
	private final DocTypeService docTypeService;
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public ShippingNotification getByRecord(@NonNull final I_M_Shipping_Notification record) {return shippingNotificationRepository.getByRecord(record);}

	public void generateShippingNotificationAndPropagatePhysicalClearanceDate(
			@NonNull final OrderId salesOrderId,
			@NonNull final Instant physicalClearanceDate)
	{
		ShippingNotificationFromShipmentScheduleProducer.builder()
				.shippingNotificationService(this)
				.shipmentScheduleBL(shipmentScheduleBL)
				.orderBL(orderBL)
				.docTypeService(docTypeService)
				//
				.salesOrderId(salesOrderId)
				.physicalClearanceDate(physicalClearanceDate)
				//
				.build()
				.execute();
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

	public void reverseByIds(final Set<ShippingNotificationId> shippingNotificationIds)
	{
		shippingNotificationRepository.getRecordsByIds(shippingNotificationIds)
				.forEach(this::reverseByRecord);
	}

	private void reverseByRecord(final I_M_Shipping_Notification record)
	{
		documentBL.processEx(record, IDocument.ACTION_Reverse_Correct);
	}

	public boolean hasCompletedOrClosedShippingNotifications(@NonNull final OrderId orderId)
	{
		return shippingNotificationRepository.anyMatch(ShippingNotificationQuery.completedOrClosedByOrderId(orderId));
	}
}
