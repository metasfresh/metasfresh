/*
 * #%L
 * de.metas.business
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
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ShippingNotificationDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ImmutableList<I_M_Shipping_NotificationLine> getLines(@NonNull final ShippingNotificationId notificationId)
	{
		return ImmutableList.copyOf(loaderAndSaver().getLineRecords(notificationId));
	}

	@NonNull
	public Stream<I_M_Shipping_Notification> stream(@NonNull final IQueryFilter<I_M_Shipping_Notification> shippingNotificationFilter)
	{
		return loaderAndSaver().stream(shippingNotificationFilter);
	}

	@NonNull
	private ShippingNotificationLoaderAndSaver loaderAndSaver()
	{
		return new ShippingNotificationLoaderAndSaver(queryBL);
	}
}
