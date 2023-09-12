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

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShippingNotificationDocumentHandlerProvider implements DocumentHandlerProvider
{

	private final ShipperNotificationRepository shipperNotificationRepository;

	public ShippingNotificationDocumentHandlerProvider(
			@NonNull final ShipperNotificationRepository shipperNotificationRepository)
	{
		this.shipperNotificationRepository = shipperNotificationRepository;
	}

	@Override
	public String getHandledTableName()
	{
		return I_M_Shipping_Notification.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model)
	{
		return new ShippingNotificationDocumentHandler(shipperNotificationRepository);
	}
}
