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

package de.metas.contracts.modular.computing.tbd.purchasecontract.salesorderline;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
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

import static de.metas.contracts.modular.ComputingMethodType.SO_LINE_FOR_PO_MODULAR_DEPRECATED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class SOLineForPOModularContractHandler implements IComputingMethodHandler
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@NonNull private final ModularContractProvider contractProvider;

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return SO_LINE_FOR_PO_MODULAR_DEPRECATED;
	}

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, final @NonNull LogEntryContractType logEntryContractType)
	{
		if (recordRef.getTableName().equals(I_C_OrderLine.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecord_ID()));
			final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
			if (SOTrx.ofBoolean(order.isSOTrx()).isPurchase() || orderBL.isProFormaSO(order))
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

			final BPartnerId warehousePartnerId = warehouseBL.getBPartnerId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()));

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
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_C_OrderLine.Table_Name))
		{
			final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(recordRef.getRecord_ID()));
			return contractProvider.streamPurchaseContractsForSalesOrderLine(OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(),
																									  orderLine.getC_OrderLine_ID()));
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		return null;
	}
}
