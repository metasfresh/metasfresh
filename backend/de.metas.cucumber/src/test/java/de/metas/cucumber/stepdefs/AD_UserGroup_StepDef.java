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
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_UserGroup;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;

public class AD_UserGroup_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final AD_UserGroup_StepDefData userGroupTable;

	public AD_UserGroup_StepDef(@NonNull final AD_UserGroup_StepDefData userGroupTable)
	{
		this.userGroupTable = userGroupTable;
	}

	@And("metasfresh contains AD_UserGroup:")
	public void metasfresh_contains_ad_userGroup(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_AD_UserGroup.COLUMNNAME_Name);
			final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_UserGroup.COLUMNNAME_Description);
			final boolean active = DataTableUtil.extractBooleanForColumnName(row, I_AD_UserGroup.COLUMNNAME_IsActive);

			final I_AD_UserGroup userGroup = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_AD_UserGroup.class)
							.addEqualsFilter(I_AD_UserGroup.COLUMNNAME_Name, name)
							.create()
							.firstOnlyOrNull(I_AD_UserGroup.class),
					() -> newInstanceOutOfTrx(I_AD_UserGroup.class));

			assertThat(userGroup).isNotNull();

			userGroup.setName(name);
			userGroup.setIsActive(active);

			if (Check.isNotBlank(description))
			{
				userGroup.setDescription(description);
			}

			InterfaceWrapperHelper.saveRecord(userGroup);

			final String userGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_UserGroup.COLUMNNAME_AD_UserGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
			userGroupTable.put(userGroupIdentifier, userGroup);
		}
	}

	@And("load AD_UserGroup from AD_SysConfig:")
	public void load_userGroup_from_sysConfig(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String sysConfigName = DataTableUtil.extractStringForColumnName(row, I_AD_SysConfig.Table_Name + "." + I_AD_SysConfig.COLUMNNAME_Name);

			final int userGroupSysConfigValue = sysConfigBL.getIntValue(sysConfigName, -1);
			assertThat(userGroupSysConfigValue).isGreaterThan(0);

			final I_AD_UserGroup userGroup = InterfaceWrapperHelper.load(userGroupSysConfigValue, I_AD_UserGroup.class);
			assertThat(userGroup).isNotNull();

			final String userGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_UserGroup.COLUMNNAME_AD_UserGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
			userGroupTable.putOrReplace(userGroupIdentifier, userGroup);
		}
	}
}
