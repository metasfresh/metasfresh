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

package de.metas.cucumber.stepdefs.tourplanning;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.tourplanning.model.I_M_Tour;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
public class M_Tour_StepDef
{
	private final M_Tour_StepDefData tourTable;

	/**
	 * Creates {@link I_M_Tour} records.
	 * <p>
	 * Columns:
	 * <ul>
	 *     <li>Identifier (required): identifier under which the created M_Tour is stored for later reference.</li>
	 *     <li>OPT.Name (optional): the tour's name; defaults to the identifier.</li>
	 * </ul>
	 * Example:
	 * <pre>
	 * Given metasfresh contains M_Tour:
	 *   | Identifier | OPT.Name  |
	 *   | tour       | Tour Mon  |
	 * </pre>
	 */
	@Given("metasfresh contains M_Tour:")
	public void metasfresh_contains_M_Tour(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Tour tour = newInstance(I_M_Tour.class);
			tour.setName(row.getAsOptionalString("Name").orElseGet(() -> row.getAsIdentifier().getAsString()));
			saveRecord(tour);

			row.getAsOptionalIdentifier().ifPresent(identifier -> tourTable.putOrReplace(identifier, tour));
		});
	}
}
