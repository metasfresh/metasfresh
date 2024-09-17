/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.shippingnotification.location.adapter;

import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.document.location.adapter.IDocumentShipFromLocationAdapter;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShippingNotificationDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentShipFromLocationAdapter> getDocumentShipFromLocationAdapter(Object record)
	{
		return toShippingNotification(record).map(ShippingNotificationDocumentLocationAdapterFactory::shipFromLocationAdapter);
	}

	@Override
	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		return Optional.empty();
	}

	private static Optional<I_M_Shipping_Notification> toShippingNotification(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_M_Shipping_Notification.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_M_Shipping_Notification.class))
				: Optional.empty();
	}

	public static ShippingNotificationMainLocationAdapter locationAdapter(@NonNull final I_M_Shipping_Notification delegate)
	{
		return new ShippingNotificationMainLocationAdapter(delegate);
	}

	public static ShippingNotificationShipFromLocationAdapter shipFromLocationAdapter(@NonNull final I_M_Shipping_Notification delegate)
	{
		return new ShippingNotificationShipFromLocationAdapter(delegate);
	}
}
