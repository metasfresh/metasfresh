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

import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class AD_JavaClass_Type_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_JavaClass_Type_StepDefData javaClassTypeStepTable;

	public AD_JavaClass_Type_StepDef(@NonNull final AD_JavaClass_Type_StepDefData javaClassTypeStepTable)
	{
		this.javaClassTypeStepTable = javaClassTypeStepTable;
	}

	@Given("load AD_JavaClass_Type:")
	public void load_ad_javaclass_type(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadAdJavaClassType(tableRow);
		}
	}

	@Given("metasfresh contains AD_JavaClass_Type:")
	public void metasfresh_contains_ad_javaclass_type(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createAdJavaClassType(tableRow);
		}
	}

	private void loadAdJavaClassType(@NonNull final Map<String, String> tableRow)
	{
		final String className = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_Classname);
		final I_AD_JavaClass_Type javaClassTypeRecord = queryBL.createQueryBuilder(I_AD_JavaClass_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_JavaClass_Type.COLUMNNAME_Classname, className)
				.create()
				.firstOnlyNotNull(I_AD_JavaClass_Type.class);

		final String javaClassTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_AD_JavaClass_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		javaClassTypeStepTable.putOrReplace(javaClassTypeIdentifier, javaClassTypeRecord);
	}

	private void createAdJavaClassType(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_Name);
		final String className = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_Classname);
		final String entityType = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_EntityType);

		final I_AD_JavaClass_Type javaClassType = InterfaceWrapperHelper.newInstance(I_AD_JavaClass_Type.class);

		javaClassType.setName(name);
		javaClassType.setClassname(className);
		javaClassType.setEntityType(entityType);

		InterfaceWrapperHelper.saveRecord(javaClassType);

		final String javaClassTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_AD_JavaClass_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		javaClassTypeStepTable.putOrReplace(javaClassTypeIdentifier, javaClassType);
	}
}
