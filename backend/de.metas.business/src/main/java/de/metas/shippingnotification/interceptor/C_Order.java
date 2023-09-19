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

package de.metas.shippingnotification.interceptor;

import de.metas.i18n.AdMessageKey;
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

import java.util.Optional;

@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	private static final AdMessageKey MSG_M_Shipment_Notification_CompletedNotifications = AdMessageKey.of("de.metas.shippingnotification.CompletedShippingNotifications");

	private final ShippingNotificationService shippingNotificationService;

	private static Optional<OrderId> getSalesOrderId(@NonNull final I_C_Order order)
	{
		return order.isSOTrx() ? OrderId.optionalOfRepoId(order.getC_Order_ID()) : Optional.empty();
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void afterVoid(@NonNull final I_C_Order order)
	{
		getSalesOrderId(order).ifPresent(shippingNotificationService::reverseBySalesOrderId);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void beforeReactivate(@NonNull final I_C_Order order)
	{
		getSalesOrderId(order).ifPresent(this::assertNoCompletedNorClosedShippingNotifications);
	}

	private void assertNoCompletedNorClosedShippingNotifications(final OrderId salesOrderId)
	{
		if (shippingNotificationService.hasCompletedOrClosedShippingNotifications(salesOrderId))
		{
			throw new AdempiereException(MSG_M_Shipment_Notification_CompletedNotifications);
		}
	}
}
