/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;

public class M_Locator_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_Warehouse> warehouseTable;
	private final StepDefData<I_M_Locator> locatorTable;

	public M_Locator_StepDef(
			@NonNull final StepDefData<I_M_Warehouse> warehouseTable,
			@NonNull final StepDefData<I_M_Locator> locatorTable)
	{
		this.warehouseTable = warehouseTable;
		this.locatorTable = locatorTable;
	}

	@And("load M_Locator:")
	public void load_M_Locator(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String value = DataTableUtil.extractStringForColumnName(row, I_M_Locator.COLUMNNAME_Value);

			final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

			final I_M_Locator locatorRecord = queryBL.createQueryBuilder(I_M_Locator.class)
					.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouse.getM_Warehouse_ID())
					.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, value)
					.orderByDescending(COLUMNNAME_M_Locator_ID)
					.create()
					.firstNotNull(I_M_Locator.class);

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			locatorTable.put(locatorIdentifier, locatorRecord);
		}
	}
}
