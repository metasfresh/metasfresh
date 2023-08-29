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

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
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
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ShipmentLineModularContractHandler implements IModularContractTypeHandler<I_M_InOutLine>
{
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	@NonNull
	public Class<I_M_InOutLine> getType()
	{
		return I_M_InOutLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_M_InOutLine inOutLineRecord)
	{
		final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		return (SOTrx.ofBoolean(inOutRecord.isSOTrx()).isSales() && inOutLineRecord.getC_Order_ID() > 0);
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(inOutLineRecord.getC_Order_ID()));

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID()); // C_Order.M_Warehouse_ID is mandatory and (warehouseBL.getBPartnerId demands NonNull

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(order.getC_Harvesting_Calendar_ID());

		final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE) // in this handler we want the *purchase* flatrate-terms that led to this (sales-)shipment
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.calendarId(harvestingCalendarId)
				.build();

		return flatrateBL.streamModularFlatrateTermsByQuery(query)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@Override
	public void validateDocAction(final @NonNull I_M_InOutLine model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case VOIDED, REACTIVATED -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
			case COMPLETED, REVERSED ->
			{
				// allow the docAction
			}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
