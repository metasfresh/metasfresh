/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.olcand;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.ordercandidate.api.OLCandProcessorId;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Step definitions for C_OLCandProcessor records.
 * Updates processor-level defaults (e.g. the default warehouse) used by the OLCand-to-Order pipeline.
 * The processor and its warehouse are addressed by their repo-IDs, since the processor is a standard seed record.
 * <p>
 * Because a C_OLCandProcessor is a shared seed record, any warehouse mutated here is restored to its original
 * value in an {@link After} hook, so sibling scenarios on the same executor are not affected.
 */
public class C_OLCandProcessor_StepDef
{
	/** Original M_Warehouse_ID per processor touched in the current scenario, for restoration in {@link #restoreProcessors()}. */
	private final Map<OLCandProcessorId, Integer> originalWarehouseIdByProcessor = new LinkedHashMap<>();

	/**
	 * Updates an existing C_OLCandProcessor record, addressed by its repo-ID.
	 *
	 * @cucumber.columns
	 *   <b>C_OLCandProcessor_ID</b> — repo-ID of the processor to update<br>
	 *   <b>OPT.M_Warehouse_ID</b> — (optional) repo-ID of the new default warehouse<br>
	 * @cucumber.example
	 * <pre>
	 * And update C_OLCandProcessor:
	 *   | C_OLCandProcessor_ID | M_Warehouse_ID |
	 *   | 1000003              | 540008         |
	 * </pre>
	 */
	@And("update C_OLCandProcessor:")
	public void update_C_OLCandProcessor(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateProcessor);
	}

	private void updateProcessor(@NonNull final DataTableRow row)
	{
		final OLCandProcessorId processorId = row.getAsIdentifier(I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID)
				.getAsId(OLCandProcessorId.class);

		final I_C_OLCandProcessor processor = InterfaceWrapperHelper.load(processorId.getRepoId(), I_C_OLCandProcessor.class);
		if (processor == null)
		{
			throw new AdempiereException("No C_OLCandProcessor found with C_OLCandProcessor_ID=" + processorId.getRepoId());
		}

		row.getAsOptionalIdentifier(I_C_OLCandProcessor.COLUMNNAME_M_Warehouse_ID)
				.map(identifier -> identifier.getAsId(WarehouseId.class))
				.ifPresent(warehouseId -> {
					originalWarehouseIdByProcessor.putIfAbsent(processorId, processor.getM_Warehouse_ID());
					processor.setM_Warehouse_ID(warehouseId.getRepoId());
				});

		InterfaceWrapperHelper.saveRecord(processor);
	}

	/**
	 * Restores every processor whose warehouse this scenario changed back to its original value,
	 * so a shared seed processor does not leak state into sibling scenarios on the same executor.
	 */
	@After
	public void restoreProcessors()
	{
		originalWarehouseIdByProcessor.forEach((processorId, originalWarehouseId) -> {
			final I_C_OLCandProcessor processor = InterfaceWrapperHelper.load(processorId.getRepoId(), I_C_OLCandProcessor.class);
			if (processor != null)
			{
				processor.setM_Warehouse_ID(originalWarehouseId);
				InterfaceWrapperHelper.saveRecord(processor);
			}
		});
		originalWarehouseIdByProcessor.clear();
	}
}
