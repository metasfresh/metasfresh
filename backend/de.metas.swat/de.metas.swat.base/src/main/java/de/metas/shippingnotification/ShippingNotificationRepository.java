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

import de.metas.order.OrderId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.X_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository
public class ShippingNotificationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ShippingNotification getById(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return newLoaderAndSaver().getById(shippingNotificationId);
	}

	@NonNull
	private ShippingNotificationLoaderAndSaver newLoaderAndSaver()
	{
		return new ShippingNotificationLoaderAndSaver(queryBL);
	}

	public void save(final ShippingNotification shippingNotification)
	{
		newLoaderAndSaver().save(shippingNotification);
	}

	I_M_Shipping_Notification saveAndGetRecord(final ShippingNotification shippingNotification)
	{
		return newLoaderAndSaver().save(shippingNotification);
	}

	void saveRecord(final I_M_Shipping_Notification record)
	{
		InterfaceWrapperHelper.saveRecord(record);
	}

	public OrderId getOrderId(@NonNull final ShippingNotificationId shippingNotificationId)
	{
		return getById(shippingNotificationId).getOrderId();
	}

	public void updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Consumer<ShippingNotification> consumer)
	{
		newLoaderAndSaver().updateWhileSaving(record, consumer);
	}

	public List<ShippingNotification> getByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_DocStatus, X_M_Shipping_Notification.DOCSTATUS_Completed, X_M_Shipping_Notification.DOCSTATUS_Closed)
				.addEqualsFilter(I_M_Shipping_Notification.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.stream(I_M_Shipping_Notification.class)
				.map(shippingNotification -> getById(ShippingNotificationId.ofRepoId(shippingNotification.getM_Shipping_Notification_ID())))
				.collect(Collectors.toList())
				;
	}

	public boolean hasCompletedOrClosedShippingNotifications(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_DocStatus, X_M_Shipping_Notification.DOCSTATUS_Completed, X_M_Shipping_Notification.DOCSTATUS_Closed)
				.addEqualsFilter(I_M_Shipping_Notification.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.anyMatch()
				;
	}
}
