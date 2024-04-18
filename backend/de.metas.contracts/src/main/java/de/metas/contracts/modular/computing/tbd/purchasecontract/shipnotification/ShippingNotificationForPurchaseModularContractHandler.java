/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.computing.tbd.purchasecontract.shipnotification;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR_DEPRECATED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class ShippingNotificationForPurchaseModularContractHandler implements IComputingMethodHandler
{
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final ShippingNotificationService notificationService;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, final @NonNull LogEntryContractType contractType)
	{
		if (recordRef.getTableName().equals(I_M_Shipping_NotificationLine.Table_Name) && contractType.isModularContractType())
		{
			final I_M_Shipping_NotificationLine notificationLine = notificationService.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_C_Order order = orderBL.getById(OrderId.ofRepoId(notificationLine.getC_Order_ID()));

			final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(order.getHarvesting_Year_ID(), order.getC_Harvesting_Calendar_ID());

			return yearAndCalendarId != null;
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_M_Shipping_NotificationLine.Table_Name))
		{
			final I_M_Shipping_NotificationLine notificationLine = notificationService.getLineRecordByLineId(ShippingNotificationLineId.ofRepoId(recordRef.getRecord_ID()));
			final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(notificationLine.getC_Order_ID(), notificationLine.getC_OrderLine_ID());

			return contractProvider.streamPurchaseContractsForSalesOrderLine(orderAndLineId);
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		return null;
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR_DEPRECATED;
	}
}
