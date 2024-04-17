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

package de.metas.contracts.modular.computing.tbd.inventory;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryLineId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.INVENTORY_LINE_MODULAR_DEPRECATED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
@RequiredArgsConstructor
public class InventoryLineModularContractHandler implements IComputingMethodHandler
{
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

	@Override
	public boolean applies(@NonNull final TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		if (!logEntryContractType.isModularContractType())
		{
			return false;
		}

		if (recordRef.getTableName().equals(I_M_InventoryLine.Table_Name))
		{
			final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(InventoryLineId.ofRepoId(recordRef.getRecord_ID()));
			return FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()) != null;
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final TableRecordReference recordRef)
	{
		final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(InventoryLineId.ofRepoId(recordRef.getRecord_ID()));
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()));
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return INVENTORY_LINE_MODULAR_DEPRECATED;
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		return null;
	}
}
