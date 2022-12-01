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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_Shipper_StepDef
{
	private final M_Shipper_StepDefData shipperTable;

	public M_Shipper_StepDef(@NonNull final M_Shipper_StepDefData shipperTable)
	{
		this.shipperTable = shipperTable;
	}

	@And("load M_Shipper")
	public void load_M_Shipper(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final int id = DataTableUtil.extractIntForColumnName(row, I_M_Shipper.COLUMNNAME_M_Shipper_ID);
			final I_M_Shipper shipper = InterfaceWrapperHelper.load(id, I_M_Shipper.class);
			assertThat(shipper).isNotNull();

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Shipper.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipperTable.put(shipperIdentifier, shipper);
		}
	}
}
