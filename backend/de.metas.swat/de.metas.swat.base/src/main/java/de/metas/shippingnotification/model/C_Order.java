/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.shippingnotification.model;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.shippingnotification.ShippingNotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{

	private static final AdMessageKey MSG_M_Shipment_Notification_CompletedNotifications = AdMessageKey.of("de.metas.shippingnotification.CompletedShippingNotificaitons");

	private final ShippingNotificationService shippingNotificationService;

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void afterVoid(@NonNull final I_C_Order order)
	{
		shippingNotificationService.reverseIfExistsShippingNotifications(OrderId.ofRepoId(order.getC_Order_ID()));
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void beforeReactivate(@NonNull final I_C_Order order)
	{
		final boolean hasCompletedNotifications = shippingNotificationService.hasCompletedOrClosedShippingNotifications(OrderId.ofRepoId(order.getC_Order_ID()));
		if (hasCompletedNotifications)
		{
			throw new AdempiereException(TranslatableStrings.builder()
												 .appendADMessage(MSG_M_Shipment_Notification_CompletedNotifications)
												 .build());
		}
	}
}
