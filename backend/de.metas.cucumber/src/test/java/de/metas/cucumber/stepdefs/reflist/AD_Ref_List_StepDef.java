/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.reflist;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Ref_List;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AD_Ref_List_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Ref_List_StepDefData refListTable;

	public AD_Ref_List_StepDef(@NonNull final AD_Ref_List_StepDefData refListTable)
	{
		this.refListTable = refListTable;
	}

	@Given("update AD_Ref_Lists:")
	public void metasfresh_contains_modcntr_types(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateRefLists(tableRow);
		}
	}

	@Given("load AD_Ref_Lists:")
	public void load_ad_ref_lists(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadRefLists(tableRow);
		}
	}

	private void loadRefLists(@NonNull final Map<String, String> tableRow)
	{
		final String value = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Ref_List.COLUMNNAME_Value);

		final I_AD_Ref_List refListRecord = queryBL.createQueryBuilder(I_AD_Ref_List.class)
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_Value, value)
				.create()
				.firstOnlyNotNull(I_AD_Ref_List.class);

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID + "." + TABLECOLUMN_IDENTIFIER);
		refListTable.putOrReplace(identifier, refListRecord);
	}

	private void updateRefLists(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Ref_List refListRecord = refListTable.get(identifier);

		final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, I_AD_Ref_List.COLUMNNAME_IsActive);
		if (isActive != null)
		{
			refListRecord.setIsActive(isActive);
		}

		saveRecord(refListRecord);
	}
}
