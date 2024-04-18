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

import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

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
	public ShippingNotification getByRecord(@NonNull final I_M_Shipping_Notification record)
	{
		final ShippingNotificationLoaderAndSaver loader = newLoaderAndSaver();
		loader.addToCacheAndAvoidSaving(record);
		return loader.getById(ShippingNotificationId.ofRepoId(record.getM_Shipping_Notification_ID()));
	}

	public I_M_Shipping_Notification getRecordById(@NonNull final ShippingNotificationId id)
	{
		return newLoaderAndSaver().getHeaderRecordById(id);
	}

	public I_M_Shipping_NotificationLine getLineRecordByLineId(@NonNull final ShippingNotificationLineId id)
	{
		return newLoaderAndSaver().getLineRecordByLineId(id);
	}

	public Collection<I_M_Shipping_Notification> getRecordsByIds(final Set<ShippingNotificationId> shippingNotificationIds)
	{
		return newLoaderAndSaver().getHeaderRecordsByIds(shippingNotificationIds).values();
	}

	public ShippingNotificationCollection getByQuery(@NonNull final ShippingNotificationQuery query)
	{
		return newLoaderAndSaver().getByQuery(query);
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

	public <R> R updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Function<ShippingNotification, R> consumer)
	{
		return newLoaderAndSaver().updateWhileSaving(record, consumer);
	}

	public boolean anyMatch(final ShippingNotificationQuery query)
	{
		return newLoaderAndSaver().anyMatch(query);
	}

	public Set<ShippingNotificationId> listIds(final ShippingNotificationQuery query) {return newLoaderAndSaver().listIds(query);}

	@NonNull
	public Map<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> getLines(@NonNull final Collection<ShippingNotificationId> notificationIds)
	{
		return newLoaderAndSaver().getLineRecords(notificationIds);
	}

	@NonNull
	public Stream<ShippingNotificationId> streamIds(@NonNull final IQueryFilter<I_M_Shipping_Notification> shippingNotificationFilter)
	{
		return newLoaderAndSaver().streamIds(shippingNotificationFilter);
	}

}
