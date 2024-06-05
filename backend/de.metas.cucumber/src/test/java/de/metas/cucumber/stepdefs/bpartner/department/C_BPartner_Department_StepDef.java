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

package de.metas.cucumber.stepdefs.bpartner.department;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Department;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;

public class C_BPartner_Department_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_BPartner_Department_StepDefData departmentTable;
	private final C_BPartner_StepDefData bpartnerTable;

	public C_BPartner_Department_StepDef(
			@NonNull final C_BPartner_Department_StepDefData departmentStepDefData,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.departmentTable = departmentStepDefData;
		this.bpartnerTable = bpartnerTable;
	}

	@And("metasfresh contains C_BPartner_Departments:")
	public void metasfresh_contains_department(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Department.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Department.COLUMNNAME_Value);
			final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BPartner_Department.COLUMNNAME_Description);
			final boolean active = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BPartner_Department.COLUMNNAME_IsActive, true);

			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Department.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartnerRecord = bpartnerTable.get(bPartnerIdentifier);
			assertThat(bPartnerRecord).as("Missing C_BPartner fir Identifier=%s", bPartnerIdentifier).isNotNull();

			final I_C_BPartner_Department department = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_C_BPartner_Department.class)
							.addEqualsFilter(I_C_BPartner_Department.COLUMNNAME_Value, value)
							.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, bPartnerRecord.getC_BPartner_ID())
							.create()
							.firstOnlyOrNull(I_C_BPartner_Department.class),
					() -> newInstanceOutOfTrx(I_C_BPartner_Department.class));
			assertThat(department).isNotNull();
			
			department.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
			department.setName(name);
			department.setValue(value);
			department.setIsActive(active);

			if (Check.isNotBlank(description))
			{
				department.setDescription(description);
			}

			InterfaceWrapperHelper.saveRecord(department);

			final String identifier = DataTableUtil.extractStringForColumnName(row,  TABLECOLUMN_IDENTIFIER);
			departmentTable.put(identifier, department);
		}
	}
	
}
