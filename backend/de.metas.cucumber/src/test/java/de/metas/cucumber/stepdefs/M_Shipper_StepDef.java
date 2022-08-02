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

import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Shipper.COLUMNNAME_M_Shipper_ID;

public class M_Shipper_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Shipper_StepDefData shipperTable;

	public M_Shipper_StepDef(@NonNull final M_Shipper_StepDefData shipperTable)
	{
		this.shipperTable = shipperTable;
	}

	@And("load M_Shipper:")
	public void load_M_Shipper(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_M_Shipper.COLUMNNAME_Name);

			final I_M_Shipper shipperRecord = queryBL.createQueryBuilder(I_M_Shipper.class)
					.addEqualsFilter(I_M_Shipper.COLUMNNAME_Name, name)
					.create()
					.firstOnlyNotNull(I_M_Shipper.class);

			assertThat(shipperRecord).isNotNull();

			final String internalName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Shipper.COLUMNNAME_InternalName);
			if (Check.isNotBlank(internalName))
			{
				shipperRecord.setInternalName(internalName);
			}

			InterfaceWrapperHelper.saveRecord(shipperRecord);

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipperTable.put(shipperIdentifier, shipperRecord);
		}
	}
}