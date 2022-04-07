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

package de.metas.cucumber.stepdefs.role;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_Roles;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID;

public class AD_Role_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Role_StepDefData roleTable;
	private final AD_User_StepDefData userTable;

	public AD_Role_StepDef(
			@NonNull final AD_Role_StepDefData roleTable,
			@NonNull final AD_User_StepDefData userTable)
	{
		this.roleTable = roleTable;
		this.userTable = userTable;
	}

	@Given("load AD_Roles")
	public void load_AD_Role(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			loadADRole(row);
		}
	}

	@Given("user has role")
	public void add_Role(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			addRole(row);
		}
	}

	private void addRole(@NonNull final Map<String, String> tableRow)
	{
		final String userIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String roleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Role.COLUMNNAME_AD_Role_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_AD_User userRecord = userTable.get(userIdentifier);
		final I_AD_Role roleRecord = roleTable.get(roleIdentifier);

		final I_AD_User_Roles adUserRoles = InterfaceWrapperHelper.newInstance(I_AD_User_Roles.class);

		adUserRoles.setAD_User_ID(userRecord.getAD_User_ID());
		adUserRoles.setAD_Role_ID(roleRecord.getAD_Role_ID());
		adUserRoles.setIsActive(true);

		InterfaceWrapperHelper.saveRecord(adUserRoles);
	}

	private void loadADRole(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Role.COLUMNNAME_AD_Role_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Role.COLUMNNAME_Name);

		final I_AD_Role roleRecord = queryBL.createQueryBuilder(I_AD_Role.class)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_Name, name)
				.create()
				.firstOnlyNotNull(I_AD_Role.class);

		roleTable.put(identifier, roleRecord);
	}
}