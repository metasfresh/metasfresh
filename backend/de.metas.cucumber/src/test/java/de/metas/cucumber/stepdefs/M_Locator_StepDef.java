/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;

public class M_Locator_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Warehouse_StepDefData warehouseTable;
	private final M_Locator_StepDefData locatorTable;

	public M_Locator_StepDef(
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final M_Locator_StepDefData locatorTable)
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

	@And("metasfresh contains M_Locator:")
	public void create_M_Locator_record(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			createLocator(row);
		});
	}

	private void createLocator(@NonNull final DataTableRow row)
	{
		final String value = row.getAsString(I_M_Locator.COLUMNNAME_Value);

		final StepDefDataIdentifier warehouseIdentifier = row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID);
			final WarehouseId warehouseId = warehouseTable.getIdOptional(warehouseIdentifier)
				.orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class));

		final I_M_Locator locatorRecord = CoalesceUtil.coalesceSuppliers(
				() -> getExistingLocator(warehouseId, value),
				() -> InterfaceWrapperHelper.newInstance(I_M_Locator.class));
		assertThat(locatorRecord).isNotNull();
		final boolean isNew = InterfaceWrapperHelper.isNew(locatorRecord);

		locatorRecord.setValue(value);
		locatorRecord.setM_Warehouse_ID(warehouseId.getRepoId());

		final OptionalBoolean isDefault = row.getAsOptionalBoolean(I_M_Locator.COLUMNNAME_IsDefault);
		if (isNew || isDefault.isPresent())
		{
			locatorRecord.setIsDefault(isDefault.orElse(true));
		}

		final OptionalInt priorityNo = row.getAsOptionalInt(I_M_Locator.COLUMNNAME_PriorityNo);
		if (isNew || priorityNo.isPresent())
		{
			locatorRecord.setPriorityNo(priorityNo.orElse(50));
		}

		final Optional<String> x = row.getAsOptionalString(I_M_Locator.COLUMNNAME_X);
		if (isNew || x.isPresent())
		{
			locatorRecord.setX(x.orElse("0"));
		}
		final Optional<String> y = row.getAsOptionalString(I_M_Locator.COLUMNNAME_X);
		if (isNew || y.isPresent())
		{
			locatorRecord.setY(y.orElse("0"));
		}
		final Optional<String> z = row.getAsOptionalString(I_M_Locator.COLUMNNAME_Z);
		if (isNew || z.isPresent())
		{
			locatorRecord.setZ(z.orElse("0"));
		}

		InterfaceWrapperHelper.saveRecord(locatorRecord);

		row.getAsIdentifier(COLUMNNAME_M_Locator_ID).put(locatorTable, locatorRecord);
		}
	}

	@Nullable
	private I_M_Locator getExistingLocator(final WarehouseId warehouseId, final String value)
	{
		return queryBL.createQueryBuilder(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOrNull(I_M_Locator.class);
	}
}
