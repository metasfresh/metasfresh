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

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.common.util.Check;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class SalesOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Override
	public @NonNull Class<I_C_OrderLine> getType()
	{
		return I_C_OrderLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (SOTrx.ofBoolean(order.isSOTrx()).isPurchase())
		{
			return false;
		}

		final BPartnerId warehousePartnerId = Optional.ofNullable(WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID()))
				.map(warehouseBL::getBPartnerId)
				.orElse(null);

		if (warehousePartnerId == null)
		{
			return false;
		}

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(order.getC_Harvesting_Calendar_ID());
		if (harvestingCalendarId == null)
		{
			return false;
		}

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());
		if (harvestingYearId == null)
		{
			return false;
		}

		final ModularFlatrateTermQuery modularFlatrateTermQuery = ModularFlatrateTermQuery.builder()
				.bPartnerId(warehousePartnerId)
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.yearId(harvestingYearId)
				.calendarId(harvestingCalendarId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.build();

		return flatrateBL.isModularContractInProgress(modularFlatrateTermQuery);
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID());
		Check.assume(warehouseId != null, "WarehouseId should not be null at this stage!");

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());
		Check.assume(harvestingYearId != null, "Harvesting year ID should not be null at this stage!");

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(order.getC_Harvesting_Calendar_ID());
		Check.assume(harvestingCalendarId != null, "Harvesting calendar ID should not be null at this stage!");

		final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.calendarId(harvestingCalendarId)
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.build();

		return flatrateBL.streamModularFlatrateTermsByQuery(query)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@Override
	public void validateDocAction(final @NonNull I_C_OrderLine model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, VOIDED, REACTIVATED ->
			{
			}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
