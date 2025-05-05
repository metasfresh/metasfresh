package de.metas.product.event;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ProductWithNoCustomsTariffUserNotificationsProducer
{
	private static final AdMessageKey MSG_ProductWithNoCustomsTariff = AdMessageKey.of("M_Product_No_CustomsTariff");

	public static ProductWithNoCustomsTariffUserNotificationsProducer newInstance()
	{
		return new ProductWithNoCustomsTariffUserNotificationsProducer();
	}

	/**
	 * Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously
	 */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.product.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private static final AdWindowId DEFAULT_WINDOW_Product = AdWindowId.ofRepoId(140); // FIXME: HARDCODED

	private ProductWithNoCustomsTariffUserNotificationsProducer()
	{
	}

	public ProductWithNoCustomsTariffUserNotificationsProducer notify(final Collection<ProductId> productIds)
	{
		if (productIds == null || productIds.isEmpty())
		{
			return this;
		}

		postNotifications(productIds.stream()
								  .map(this::createUserNotification)
								  .collect(ImmutableList.toImmutableList()));

		return this;
	}

	private UserNotificationRequest createUserNotification(@NonNull final ProductId productId)
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

		final AdWindowId productWindowId = adWindowDAO.getAdWindowId(I_M_Product.Table_Name, SOTrx.SALES, DEFAULT_WINDOW_Product);

		final TableRecordReference productRef = toTableRecordRef(productId);

		return newUserNotificationRequest()
				.recipientUserId(Env.getLoggedUserId())
				.contentADMessage(MSG_ProductWithNoCustomsTariff)
				.contentADMessageParam(productRef)
				.targetAction(TargetRecordAction.ofRecordAndWindow(productRef, productWindowId))
				.build();

	}

	private static TableRecordReference toTableRecordRef(final ProductId productId)
	{
		return TableRecordReference.of(I_M_Product.Table_Name, productId);
	}

	private UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}

}
