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

import de.metas.javaclasses.model.I_AD_JavaClass;
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

public class AD_JavaClass_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_JavaClass_Type_StepDefData javaClassTypeStepTable;
	private final AD_JavaClass_StepDefData javaClassTable;

	public AD_JavaClass_StepDef(
			@NonNull final AD_JavaClass_Type_StepDefData javaClassTypeStepTable,
			@NonNull final AD_JavaClass_StepDefData javaClassTable)
	{
		this.javaClassTypeStepTable = javaClassTypeStepTable;
		this.javaClassTable = javaClassTable;
	}

	@Given("load AD_JavaClass:")
	public void load_ad_javaclass(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadAdJavaClass(tableRow);
		}
	}

	@Given("metasfresh contains AD_JavaClass:")
	public void metasfresh_contains_ad_javaclass(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createAdJavaClass(tableRow);
		}
	}

	private void loadAdJavaClass(@NonNull final Map<String, String> tableRow)
	{
		final String className = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_Classname);
		final String javaClassTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_JavaClass_Type javaClassTypeRecord = javaClassTypeStepTable.get(javaClassTypeIdentifier);

		final I_AD_JavaClass javaClassRecord = queryBL.createQueryBuilder(I_AD_JavaClass.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_Classname, className)
				.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID, javaClassTypeRecord.getAD_JavaClass_Type_ID())
				.create()
				.firstOnlyNotNull(I_AD_JavaClass.class);

		final String javaClassIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_AD_JavaClass_ID + "." + TABLECOLUMN_IDENTIFIER);
		javaClassTable.putOrReplace(javaClassIdentifier, javaClassRecord);
	}

	private void createAdJavaClass(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_Name);
		final String className = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_Classname);
		final String javaClassTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_JavaClass_Type javaClassTypeRecord = javaClassTypeStepTable.get(javaClassTypeIdentifier);

		final String entityType = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass_Type.COLUMNNAME_EntityType);

		final I_AD_JavaClass javaClassRecord = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class);

		javaClassRecord.setName(name);
		javaClassRecord.setClassname(className);
		javaClassRecord.setAD_JavaClass_Type_ID(javaClassTypeRecord.getAD_JavaClass_Type_ID());
		javaClassRecord.setEntityType(entityType);

		InterfaceWrapperHelper.saveRecord(javaClassRecord);

		final String javaClassIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_JavaClass.COLUMNNAME_AD_JavaClass_ID + "." + TABLECOLUMN_IDENTIFIER);
		javaClassTable.putOrReplace(javaClassIdentifier, javaClassRecord);
	}
}
