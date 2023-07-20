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

package de.metas.cucumber.stepdefs.conversion;

import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_C_ConversionType;

import java.util.Map;

import static de.metas.bpartner.api.impl.BPRelationDAO.queryBL;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_ConversionType_StepDef
{
	private final C_ConversionType_StepDefData conversionTypeTable;

	public C_ConversionType_StepDef(@NonNull final C_ConversionType_StepDefData conversionTypeTable)
	{
		this.conversionTypeTable = conversionTypeTable;
	}

	@And("load C_ConversionType:")
	public void load_C_ConversionType(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String value = DataTableUtil.extractStringForColumnName(row, I_C_ConversionType.COLUMNNAME_Value);

			final I_C_ConversionType conversionType = queryBL.createQueryBuilder(I_C_ConversionType.class)
					.addEqualsFilter(I_C_ConversionType.COLUMNNAME_Value, value)
					.create()
					.firstOnlyNotNull(I_C_ConversionType.class);

			final String conversionTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_ConversionType.COLUMNNAME_C_ConversionType_ID + "." + TABLECOLUMN_IDENTIFIER);
			conversionTypeTable.putOrReplace(conversionTypeIdentifier, conversionType);
		}
	}
}
