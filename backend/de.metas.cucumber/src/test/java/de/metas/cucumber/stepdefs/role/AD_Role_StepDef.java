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
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.organization.OrgId;
import de.metas.security.TableAccessLevel;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_Roles;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

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

	/**
	 * Creates a purpose-built client+org-level role that includes the standard {@code WebUI} role, so it inherits
	 * the WebUI role's window/table access as a baseline and adds only what the scenario configures on top (e.g. an
	 * {@code AD_Table_Access} restriction). Mirrors how an administrator builds a restricted role by including a
	 * broad base role rather than granting every window from scratch.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Role_ID</b> — (required, identifier-ref) alias for cross-step reference<br>
	 *   <b>Name</b> — (optional) the {@code AD_Role.Name}; when omitted a unique name is generated from the
	 *     identifier, so the scenario can be replayed against the same DB without hitting the AD_Role name
	 *     unique index. Address the role downstream by its identifier (e.g. the identifier-based API-token step).<br>
	 * @cucumber.depends StepDefData: AD_Role_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains AD_Roles including the WebUI role:
	 *   | AD_Role_ID     |
	 *   | restrictedRole |
	 * </pre>
	 */
	@Given("metasfresh contains AD_Roles including the WebUI role:")
	public void create_AD_Roles_including_WebUI(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_AD_Role.COLUMNNAME_AD_Role_ID)
				.forEach(this::createADRoleIncludingWebUI);
	}

	private void createADRoleIncludingWebUI(@NonNull final DataTableRow row)
	{
		// Name is optional: suggestValueAndName reuses a supplied Name or derives a unique one from the
		// identifier. Together with the upsert-by-name below the step is idempotent and replayable against
		// the same DB — a supplied name reuses the role, a generated one always yields a fresh role, so the
		// AD_Role.Name unique index is never violated on a re-run.
		final ValueAndName valueAndName = row.suggestValueAndName();

		final I_AD_Role roleRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_AD_Role.class)
						.addEqualsFilter(I_AD_Role.COLUMNNAME_Name, valueAndName.getName())
						.create()
						.firstOnlyOrNull(I_AD_Role.class),
				() -> InterfaceWrapperHelper.newInstance(I_AD_Role.class));
		roleRecord.setAD_Org_ID(OrgId.ANY.getRepoId());
		roleRecord.setName(valueAndName.getName());
		roleRecord.setUserLevel(TableAccessLevel.ClientPlusOrganization.getUserLevelString());
		roleRecord.setIsAccessAllOrgs(false);
		InterfaceWrapperHelper.saveRecord(roleRecord);

		final I_AD_Role webUiRole = queryBL.createQueryBuilder(I_AD_Role.class)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_Name, "WebUI")
				.create()
				.firstOnlyNotNull(I_AD_Role.class);

		final I_AD_Role_Included inclusion = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_AD_Role_Included.class)
						.addEqualsFilter(I_AD_Role_Included.COLUMNNAME_AD_Role_ID, roleRecord.getAD_Role_ID())
						.addEqualsFilter(I_AD_Role_Included.COLUMNNAME_Included_Role_ID, webUiRole.getAD_Role_ID())
						.create()
						.firstOnlyOrNull(I_AD_Role_Included.class),
				() -> InterfaceWrapperHelper.newInstance(I_AD_Role_Included.class));
		inclusion.setAD_Org_ID(OrgId.ANY.getRepoId());
		inclusion.setAD_Role_ID(roleRecord.getAD_Role_ID());
		inclusion.setIncluded_Role_ID(webUiRole.getAD_Role_ID());
		inclusion.setSeqNo(10);
		InterfaceWrapperHelper.saveRecord(inclusion);

		roleTable.putOrReplace(row.getAsIdentifier(I_AD_Role.COLUMNNAME_AD_Role_ID), roleRecord);
	}

	/**
	 * Assigns an existing role to an existing user (creates the {@code AD_User_Roles} link). Idempotent:
	 * re-running with the same (user, role) pair upserts the existing link rather than adding a duplicate,
	 * so the step is replay-safe against the same DB.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_User_ID</b> — (required, identifier-ref) the user to assign, resolved in {@code AD_User_StepDefData}<br>
	 *   <b>AD_Role_ID</b> — (required, identifier-ref) the role to assign, resolved in {@code AD_Role_StepDefData}<br>
	 * @cucumber.depends StepDefData: AD_User_StepDefData, AD_Role_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And user has role
	 *   | AD_User_ID     | AD_Role_ID     |
	 *   | metasfreshUser | restrictedRole |
	 * </pre>
	 */
	@Given("user has role")
	public void add_Role(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::addRole);
	}

	private void addRole(@NonNull final DataTableRow row)
	{
		final I_AD_User userRecord = row.getAsIdentifier(I_AD_User_Roles.COLUMNNAME_AD_User_ID).lookupNotNullIn(userTable);
		final I_AD_Role roleRecord = row.getAsIdentifier(I_AD_Role.COLUMNNAME_AD_Role_ID).lookupNotNullIn(roleTable);

		// upsert the assignment so the step is idempotent when a scenario is replayed against the same DB
		final I_AD_User_Roles adUserRoles = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_AD_User_Roles.class)
						.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_User_ID, userRecord.getAD_User_ID())
						.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_Role_ID, roleRecord.getAD_Role_ID())
						.create()
						.firstOnlyOrNull(I_AD_User_Roles.class),
				() -> InterfaceWrapperHelper.newInstance(I_AD_User_Roles.class));
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