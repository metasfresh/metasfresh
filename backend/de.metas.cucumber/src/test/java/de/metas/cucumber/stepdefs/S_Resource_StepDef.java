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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
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
import static org.assertj.core.api.Assertions.*;
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
}
