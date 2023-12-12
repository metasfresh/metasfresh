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

import de.metas.common.util.CoalesceUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_UserGroup;
import org.compiere.model.I_AD_UserGroup_User_Assign;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_UserGroup_User_Assign.COLUMNNAME_AD_UserGroup_User_Assign_ID;

public class AD_UserGroup_User_Assign_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_User_StepDefData userTable;
	private final AD_UserGroup_StepDefData userGroupTable;
	private final AD_UserGroup_User_Assign_StepDefData userGroupUserAssignTable;

	public AD_UserGroup_User_Assign_StepDef(
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final AD_UserGroup_StepDefData userGroupTable,
			@NonNull final AD_UserGroup_User_Assign_StepDefData userGroupUserAssignTable)
	{
		this.userTable = userTable;
		this.userGroupTable = userGroupTable;
		this.userGroupUserAssignTable = userGroupUserAssignTable;
	}

	@And("metasfresh contains AD_UserGroup_User_Assign:")
	public void metasfresh_contains_AD_UserGroup_User_Assign(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String userIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_UserGroup_User_Assign.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int userId = userTable.get(userIdentifier).getAD_User_ID();

			final String userGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_UserGroup_User_Assign.COLUMNNAME_AD_UserGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_UserGroup userGroup = userGroupTable.get(userGroupIdentifier);
			assertThat(userGroup).isNotNull();

			final boolean active = DataTableUtil.extractBooleanForColumnName(row, I_AD_UserGroup_User_Assign.COLUMNNAME_IsActive);

			final I_AD_UserGroup_User_Assign userGroupAssign = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_AD_UserGroup_User_Assign.class)
							.addEqualsFilter(I_AD_UserGroup_User_Assign.COLUMNNAME_AD_User_ID, userId)
							.addEqualsFilter(I_AD_UserGroup_User_Assign.COLUMN_AD_UserGroup_ID, userGroup.getAD_UserGroup_ID())
							.create()
							.firstOnlyOrNull(I_AD_UserGroup_User_Assign.class),
					() -> newInstanceOutOfTrx(I_AD_UserGroup_User_Assign.class));

			assertThat(userGroupAssign).isNotNull();

			userGroupAssign.setAD_User_ID(userId);
			userGroupAssign.setAD_UserGroup_ID(userGroup.getAD_UserGroup_ID());
			userGroupAssign.setIsActive(active);

			InterfaceWrapperHelper.saveRecord(userGroupAssign);

			final String userGroupAssignIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_UserGroup_User_Assign_ID + "." + TABLECOLUMN_IDENTIFIER);
			userGroupUserAssignTable.put(userGroupAssignIdentifier, userGroupAssign);
		}
	}
}
