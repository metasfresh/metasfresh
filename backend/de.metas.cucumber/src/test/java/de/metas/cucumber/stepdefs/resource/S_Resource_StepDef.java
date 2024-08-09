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

package de.metas.cucumber.stepdefs.resource;

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.material.planning.ManufacturingResourceType;
import de.metas.material.planning.ResourceTypeId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_S_Resource.COLUMNNAME_S_Resource_ID;

public class S_Resource_StepDef
{
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	private final S_Resource_StepDefData resourceTable;

	public S_Resource_StepDef(@NonNull final S_Resource_StepDefData resourceTable)
	{
		this.resourceTable = resourceTable;
	}

	@And("load S_Resource:")
	public void load_S_Resource(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_S_Resource_ID);

			final I_S_Resource testResource = InterfaceWrapperHelper.load(resourceId, I_S_Resource.class);
			assertThat(testResource).isNotNull();

			resourceTable.put(resourceIdentifier, testResource);
		}
	}

	@And("update S_Resource:")
	public void update_S_Resource(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final BigDecimal capacityPerProductionCycle = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_Resource.COLUMNNAME_CapacityPerProductionCycle);
			final String capacityPerProductionCycleUOMCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_Resource.COLUMNNAME_CapacityPerProductionCycle + "UOMCode");

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Resource resource = resourceTable.get(resourceIdentifier);
			assertThat(resource).isNotNull();

			if (capacityPerProductionCycle != null)
			{
				resource.setCapacityPerProductionCycle(capacityPerProductionCycle);
			}

			if (Check.isNotBlank(capacityPerProductionCycleUOMCode))
			{
				final UomId capacityPerProductionCycleUOMId = uomDao.getUomIdByX12DE355(X12DE355.ofCode(capacityPerProductionCycleUOMCode));

				resource.setCapacityPerProductionCycle_UOM_ID(capacityPerProductionCycleUOMId.getRepoId());
			}

			saveRecord(resource);

			resourceTable.putOrReplace(resourceIdentifier, resource);
		}
	}

	@And("create S_Resource:")
	public void createResources(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createResource);
	}

	private void createResource(@NonNull final DataTableRow row)
	{
		final ResourceTypeId resourceTypeId = row.getAsIdentifier(I_S_Resource.COLUMNNAME_S_ResourceType_ID).getAsId(ResourceTypeId.class);
		final ValueAndName valueAndName = row.suggestValueAndName();

		final I_S_Resource record = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		record.setValue(valueAndName.getValue());
		record.setName(valueAndName.getName());
		record.setS_ResourceType_ID(resourceTypeId.getRepoId());
		record.setIsAvailable(true);
		record.setPercentUtilization(BigDecimal.valueOf(100));

		row.getAsOptionalBoolean(I_S_Resource.COLUMNNAME_IsManufacturingResource).ifPresent(record::setIsManufacturingResource);
		row.getAsOptionalEnum(I_S_Resource.COLUMNNAME_ManufacturingResourceType, ManufacturingResourceType.class)
				.map(ManufacturingResourceType::getCode)
				.ifPresent(record::setManufacturingResourceType);
		row.getAsOptionalInt(I_S_Resource.COLUMNNAME_PlanningHorizon).ifPresent(record::setPlanningHorizon);

		row.getAsOptionalQuantity(
						I_S_Resource.COLUMNNAME_CapacityPerProductionCycle,
						I_S_Resource.COLUMNNAME_CapacityPerProductionCycle_UOM_ID,
						uomDao::getByX12DE355
				)
				.ifPresent(capacityPerProductionCycle -> {
					record.setCapacityPerProductionCycle(capacityPerProductionCycle.toBigDecimal());
					record.setCapacityPerProductionCycle_UOM_ID(capacityPerProductionCycle.getUomId().getRepoId());
				});

		InterfaceWrapperHelper.save(record);

		resourceTable.put(row.getAsIdentifier(), record);
	}

}
