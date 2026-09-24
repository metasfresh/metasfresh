/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateTableAccessRequest;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Table_Access;

/**
 * Responsible for {@code AD_Table_Access} rows — the per-(role, table) access grants whose four flags subtract
 * capabilities from the role's default (IsReadOnly removes WRITE; IsCanReport / IsCanExport / IsCanCreateNewRecords
 * remove REPORT / EXPORT / CREATE). A row left at its defaults is equivalent to no row. Delegates to the production
 * {@link IUserRolePermissionsDAO#createTableAccess} so the column defaults and org handling stay in one place.
 */
@RequiredArgsConstructor
public class AD_Table_Access_StepDef
{
	@NonNull private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	@NonNull private final AD_Role_StepDefData roleTable;

	/**
	 * Creates an {@code AD_Table_Access} row granting/restricting a role on one table. Each flag is optional; an
	 * omitted flag is left off the request, so its builder default (which mirrors the column's non-restricting
	 * default) applies — a row that sets only {@code IsCanCreateNewRecords=false} subtracts just the create
	 * permission and leaves read, edit, report and export intact.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Role_ID</b> — (required, identifier-ref) the role the access row belongs to<br>
	 *   <b>TableName</b> — (required) the table the access applies to, e.g. {@code C_BPartner}<br>
	 *   <b>IsReadOnly</b> — (optional) removes WRITE when true; omit to keep the column default<br>
	 *   <b>IsCanReport</b> — (optional) removes REPORT when false; omit to keep the column default<br>
	 *   <b>IsCanExport</b> — (optional) removes EXPORT when false; omit to keep the column default<br>
	 *   <b>IsCanCreateNewRecords</b> — (optional) removes CREATE when false; omit to keep the column default<br>
	 * @cucumber.depends StepDefData: AD_Role_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains AD_Table_Access:
	 *   | AD_Role_ID     | TableName  | IsCanCreateNewRecords |
	 *   | restrictedRole | C_BPartner | false                 |
	 * </pre>
	 */
	@Given("metasfresh contains AD_Table_Access:")
	public void metasfresh_contains_AD_Table_Access(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createTableAccess);
	}

	private void createTableAccess(@NonNull final DataTableRow row)
	{
		final I_AD_Role roleRecord = row.getAsIdentifier(I_AD_Table_Access.COLUMNNAME_AD_Role_ID).lookupNotNullIn(roleTable);
		final AdTableId adTableId = adTableDAO.retrieveAdTableId(row.getAsString("TableName"));

		// orgId and every unspecified flag fall through to the request's builder defaults (which mirror the
		// AD_Table_Access column defaults); only a flag the DataTable actually sets is passed, so an omitted
		// column stays at its non-restricting default.
		final CreateTableAccessRequest.CreateTableAccessRequestBuilder builder = CreateTableAccessRequest.builder()
				.roleId(RoleId.ofRepoId(roleRecord.getAD_Role_ID()))
				.adTableId(adTableId);
		row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsReadOnly).ifPresent(builder::readOnly);
		row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanReport).ifPresent(builder::canReport);
		row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanExport).ifPresent(builder::canExport);
		row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanCreateNewRecords).ifPresent(builder::canCreateNewRecords);

		userRolePermissionsDAO.createTableAccess(builder.build());
	}
}
