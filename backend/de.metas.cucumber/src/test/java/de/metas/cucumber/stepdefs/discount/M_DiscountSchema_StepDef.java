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

package de.metas.cucumber.stepdefs.discount;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.discountschema.M_DiscountSchema_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_DiscountSchema;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class M_DiscountSchema_StepDef
{
	private final M_DiscountSchema_StepDefData discountSchemaTable;

	public M_DiscountSchema_StepDef(@NonNull final M_DiscountSchema_StepDefData discountSchemaTable)
	{
		this.discountSchemaTable = discountSchemaTable;
	}

	@And("metasfresh contains M_DiscountSchema")
	public void create_M_DiscountSchema(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String breakupValType = DataTableUtil.extractStringForColumnName(row, I_M_DiscountSchema.COLUMNNAME_BreakValueType);
			final String discountType = DataTableUtil.extractStringForColumnName(row, I_M_DiscountSchema.COLUMNNAME_DiscountType);
			final String name = DataTableUtil.extractStringForColumnName(row, I_M_DiscountSchema.COLUMNNAME_Name);
			final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, I_M_DiscountSchema.COLUMNNAME_ValidFrom);

			final I_M_DiscountSchema discountSchema = InterfaceWrapperHelper.newInstance(I_M_DiscountSchema.class);
			discountSchema.setName(name);
			discountSchema.setDiscountType(discountType);
			discountSchema.setBreakValueType(breakupValType);
			discountSchema.setValidFrom(validFrom);

			InterfaceWrapperHelper.saveRecord(discountSchema);
			final String discountSchemaIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
			discountSchemaTable.put(discountSchemaIdentifier, discountSchema);
		}
	}
}