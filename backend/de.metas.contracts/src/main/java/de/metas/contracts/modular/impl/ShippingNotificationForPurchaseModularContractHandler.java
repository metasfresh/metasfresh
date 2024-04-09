/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.impl;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.computing.IModularContractComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR;
import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED;

@Component
@RequiredArgsConstructor
public class ShippingNotificationForPurchaseModularContractHandler implements IModularContractComputingMethodHandler<I_M_Shipping_NotificationLine>
{
	@NonNull
	private final ModularContractProvider contractProvider;
	@NonNull
	private final ModularContractLogService contractLogService;

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public @NonNull Class<I_M_Shipping_NotificationLine> getType()
	{
		return I_M_Shipping_NotificationLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_M_Shipping_NotificationLine model)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(model.getC_Order_ID()));

		final YearAndCalendarId yearAndCalendarId = YearAndCalendarId.ofRepoIdOrNull(order.getHarvesting_Year_ID(), order.getC_Harvesting_Calendar_ID());

		return yearAndCalendarId != null;
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_M_Shipping_NotificationLine model)
	{
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(model.getC_Order_ID(), model.getC_OrderLine_ID());

		return contractProvider.streamPurchaseContractsForSalesOrderLine(orderAndLineId);
	}

	@Override
	public void validateAction(final @NonNull I_M_Shipping_NotificationLine model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED -> {}
			case RECREATE_LOGS -> contractLogService.throwErrorIfProcessedLogsExistForRecord(TableRecordReference.of(model),
																							 MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR;
	}
}
