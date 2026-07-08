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
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;

/**
 * Step definitions for C_OLCandProcessor records.
 * Covers updating processor-level defaults (e.g. the default warehouse) used by the OLCand-to-Order pipeline.
 */
@RequiredArgsConstructor
public class C_OLCandProcessor_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	/**
	 * Updates an existing C_OLCandProcessor record.
	 * The record is located by {@code C_OLCandProcessor_ID} when provided, otherwise by {@code Name}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Name</b> — (required when C_OLCandProcessor_ID absent) name of the C_OLCandProcessor to update<br>
	 *   <b>OPT.C_OLCandProcessor_ID</b> — (optional) raw repo-ID of the processor (preferred when Name is uncertain)<br>
	 *   <b>OPT.M_Warehouse_ID.Identifier</b> — (optional, identifier-ref) new default warehouse; resolved via M_Warehouse_StepDefData<br>
	 * @cucumber.depends StepDefData: M_Warehouse_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update C_OLCandProcessor:
	 *   | OPT.C_OLCandProcessor_ID | OPT.M_Warehouse_ID.Identifier |
	 *   | 1000003                  | defaultWH                     |
	 * </pre>
	 */
	@And("update C_OLCandProcessor:")
	public void update_C_OLCandProcessor(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateProcessor);
	}

	private void updateProcessor(@NonNull final DataTableRow row)
	{
		final I_C_OLCandProcessor processor = loadProcessor(row);

		row.getAsOptionalIdentifier(I_C_OLCandProcessor.COLUMNNAME_M_Warehouse_ID)
				.ifPresent(warehouseIdentifier -> {
					final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
					processor.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
				});

		InterfaceWrapperHelper.saveRecord(processor);
	}

	@NonNull
	private I_C_OLCandProcessor loadProcessor(@NonNull final DataTableRow row)
	{
		final int processorId = row.getAsOptionalInt(I_C_OLCandProcessor.COLUMNNAME_C_OLCandProcessor_ID).orElse(0);
		if (processorId > 0)
		{
			final I_C_OLCandProcessor processor = InterfaceWrapperHelper.load(processorId, I_C_OLCandProcessor.class);
			if (processor == null)
			{
				throw new AdempiereException("No C_OLCandProcessor found with C_OLCandProcessor_ID=" + processorId);
			}
			return processor;
		}

		final String name = row.getAsString(I_C_OLCandProcessor.COLUMNNAME_Name);
		final I_C_OLCandProcessor processor = queryBL.createQueryBuilder(I_C_OLCandProcessor.class)
				.addEqualsFilter(I_C_OLCandProcessor.COLUMNNAME_Name, name)
				.create()
				.firstOnly(I_C_OLCandProcessor.class);

		if (processor == null)
		{
			throw new AdempiereException("No C_OLCandProcessor found with Name=" + name);
		}
		return processor;
	}
}
