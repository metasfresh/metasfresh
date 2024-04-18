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

package de.metas.contracts.modular.computing.tbd.logimport;

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.X_I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.IMPORT_LOG;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class ImportLogModularContractHandler implements IComputingMethodHandler
{
	@NonNull final ModularContractLogService modularContractLogService;
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (recordRef.getTableName().equals(I_I_ModCntr_Log.Table_Name) && logEntryContractType.isModularContractType())
		{
			final I_I_ModCntr_Log importLogRecord = InterfaceWrapperHelper.load(recordRef.getRecord_ID(), I_I_ModCntr_Log.class);
			return Check.isBlank(importLogRecord.getI_ErrorMsg()) &&
					!Objects.equals(X_I_ModCntr_Log.I_ISIMPORTED_ImportFailed, importLogRecord.getI_IsImported()) ||
					CalendarId.ofRepoIdOrNull(importLogRecord.getC_Calendar_ID()) != null &&
							YearId.ofRepoIdOrNull(importLogRecord.getHarvesting_Year_ID()) != null &&
							FlatrateTermId.ofRepoIdOrNull(importLogRecord.getC_Flatrate_Term_ID()) != null;
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		if (recordRef.getTableName().equals(I_I_ModCntr_Log.Table_Name))
		{
			final I_I_ModCntr_Log importLogRecord = InterfaceWrapperHelper.load(recordRef.getRecord_ID(), I_I_ModCntr_Log.class);
			return Stream.of(FlatrateTermId.ofRepoId(importLogRecord.getC_Flatrate_Term_ID()));
		}
		return Stream.empty();
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return IMPORT_LOG;
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final I_C_UOM stockUOM = productBL.getStockUOM(request.getProductId());
		final Quantity qty = Quantity.of(BigDecimal.ONE, stockUOM);
		final List<ModularContractLogEntry> logs = new ArrayList<>();

		return ComputingResponse.builder()
				.ids(logs.stream().map(ModularContractLogEntry::getId).collect(Collectors.toSet()))
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.of(BigDecimal.ONE, request.getCurrencyId()))
							   .uomId(UomId.ofRepoId(stockUOM.getC_UOM_ID()))
							   .build())
				.qty(qty)
				.build();
	}
}
