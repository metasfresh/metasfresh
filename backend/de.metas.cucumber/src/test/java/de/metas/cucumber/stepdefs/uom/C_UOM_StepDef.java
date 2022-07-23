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

package de.metas.cucumber.stepdefs.uom;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_UOM_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_UOM_StepDefData uomTable;

	public C_UOM_StepDef(@NonNull final C_UOM_StepDefData uomTable)
	{
		this.uomTable = uomTable;
	}

	@And("metasfresh contains C_UOM:")
	public void create_C_Uom(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String x12de355 = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_X12DE355);

			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_Name);

			final String uomType = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_UOMType);

			final I_C_UOM uomRecord = CoalesceUtil.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_C_UOM.class)
																			 .addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, x12de355)
																			 .create()
																			 .firstOnlyOrNull(I_C_UOM.class),
																	 () -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_UOM.class));

			assertThat(uomRecord).isNotNull();

			uomRecord.setX12DE355(x12de355);
			uomRecord.setUOMType(uomType);
			uomRecord.setName(name);
			uomRecord.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(uomRecord);

			final String uomIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);

			uomTable.put(uomIdentifier, uomRecord);
		}
	}

}
