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

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for creating {@link I_C_CompensationGroup_Schema} records.
 * <p>
 * These schemas define compensation group templates that are used when creating
 * order lines via Quick Input for "Mischkarton" (mixed carton) products.
 * <p>
 * DataTable columns:
 * <ul>
 *     <li>{@code Identifier} (required) — identifier for later reference</li>
 *     <li>{@code Name} (required) — name of the schema</li>
 *     <li>{@code IsInheritPackingInstruction} (optional, default N) — if Y, sub-article order lines
 *         inherit the packing instruction from the main article. Each sub-article must have a compatible
 *         M_HU_PI_Item_Product for the same TU type, otherwise inheritance is skipped for that article.</li>
 * </ul>
 */
@RequiredArgsConstructor
public class C_CompensationGroup_Schema_StepDef
{
	private final @NonNull C_CompensationGroup_Schema_StepDefData schemaTable;

	/** Creates compensation group schema records. Add template lines via the separate TemplateLine step. */
	@Given("metasfresh contains C_CompensationGroup_Schema:")
	public void createSchemas(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_Schema record = newInstance(I_C_CompensationGroup_Schema.class);
			record.setName(row.getAsString(I_C_CompensationGroup_Schema.COLUMNNAME_Name));

			row.getAsOptionalBoolean(I_C_CompensationGroup_Schema.COLUMNNAME_IsInheritPackingInstruction)
					.ifPresent(record::setIsInheritPackingInstruction);

			saveRecord(record);

			schemaTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}
}
