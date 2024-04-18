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

package de.metas.contracts.modular.computing.tbd.purchasecontract.pforderline;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.SALES_ORDER_LINE_PRO_FORMA_PO_MODULAR_DEPRECATED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class SalesOrderLineProFormaPOModularContractHandler implements IComputingMethodHandler
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Override
	public boolean applies(@NonNull final TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (recordRef.getTableName().equals(I_C_OrderLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_C_OrderLine orderLineRecord = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_C_Order orderRecord = orderBL.getById(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()));
			if (SOTrx.ofBoolean(orderRecord.isSOTrx()).isPurchase() || !orderBL.isProFormaSO(orderRecord))
			{
				return false;
			}

			final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(orderRecord.getC_Harvesting_Calendar_ID());
			if (harvestingCalendarId == null)
			{
				return false;
			}

			final YearId harvestingYearId = YearId.ofRepoIdOrNull(orderRecord.getHarvesting_Year_ID());
			if (harvestingYearId == null)
			{
				return false;
			}

			final BPartnerId warehousePartnerId = warehouseBL.getBPartnerId(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID()));

			final ModularFlatrateTermQuery modularFlatrateTermQuery = ModularFlatrateTermQuery.builder()
					.bPartnerId(warehousePartnerId)
					.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
					.yearId(harvestingYearId)
					.calendarId(harvestingCalendarId)
					.soTrx(SOTrx.PURCHASE)
					.typeConditions(TypeConditions.MODULAR_CONTRACT)
					.build();

			return flatrateBL.isModularContractInProgress(modularFlatrateTermQuery);
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_C_OrderLine.Table_Name))
		{
			final I_C_OrderLine orderLineRecord = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()));
			final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
			final YearId harvestingYearId = YearId.ofRepoId(order.getHarvesting_Year_ID());
			final CalendarId harvestingCalendarId = CalendarId.ofRepoId(order.getC_Harvesting_Calendar_ID());
			final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
					.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
					.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
					.calendarId(harvestingCalendarId)
					.yearId(harvestingYearId)
					.soTrx(SOTrx.PURCHASE)
					.typeConditions(TypeConditions.MODULAR_CONTRACT)
					.build();

			return flatrateBL.streamModularFlatrateTermsByQuery(query)
					.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
					.map(FlatrateTermId::ofRepoId);
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
		return SALES_ORDER_LINE_PRO_FORMA_PO_MODULAR_DEPRECATED;
	}
}
