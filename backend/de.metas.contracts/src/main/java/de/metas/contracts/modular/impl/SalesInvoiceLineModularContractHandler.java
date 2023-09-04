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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class SalesInvoiceLineModularContractHandler implements IModularContractTypeHandler<I_C_InvoiceLine>
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@Override
	public @NonNull Class<I_C_InvoiceLine> getType()
	{
		return I_C_InvoiceLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		if (soTrx.isPurchase())
		{
			return false;
		}

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(invoice.getC_Harvesting_Calendar_ID());
		if (harvestingCalendarId == null)
		{
			return false;
		}

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(invoice.getHarvesting_Year_ID());
		if (harvestingYearId == null)
		{
			return false;
		}

		final BPartnerId warehousePartnerId = Optional.ofNullable(WarehouseId.ofRepoIdOrNull(invoice.getM_Warehouse_ID()))
				.map(warehouseBL::getBPartnerId)
				.orElse(null);

		if (warehousePartnerId == null)
		{
			return false;
		}

		final ModularFlatrateTermQuery modularFlatrateTermQuery = ModularFlatrateTermQuery.builder()
				.calendarId(harvestingCalendarId)
				.yearId(harvestingYearId)
				.bPartnerId(warehousePartnerId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.productId(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.build();

		return flatrateBL.isModularContractInProgress(modularFlatrateTermQuery);
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

		return flatrateBL.streamModularFlatrateTermIdsByQuery(
				ModularFlatrateTermQuery.builder()
						.calendarId(CalendarId.ofRepoIdOrNull(invoice.getC_Harvesting_Calendar_ID()))
						.yearId(YearId.ofRepoIdOrNull(invoice.getHarvesting_Year_ID()))
						.bPartnerId(extractWarehousePartnerId(invoice))
						.productId(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
						.typeConditions(TypeConditions.MODULAR_CONTRACT)
						.soTrx(SOTrx.PURCHASE)
						.build());
	}

	private BPartnerId extractWarehousePartnerId(final I_C_Invoice invoice)
	{
		final WarehouseId warehouseId = WarehouseId.optionalOfRepoId(invoice.getM_Warehouse_ID())
				.orElseThrow(() -> new AdempiereException("WarehouseId should not be null at this stage!"));

		return warehouseBL.getBPartnerId(warehouseId);
	}

	@Override
	public void validateDocAction(final @NonNull I_C_InvoiceLine model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED ->
			{
			}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
