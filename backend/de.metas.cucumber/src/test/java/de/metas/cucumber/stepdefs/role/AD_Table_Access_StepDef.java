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
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Table_Access;

/**
 * Responsible for {@code AD_Table_Access} rows — the per-(role, table) access grants whose four flags subtract
 * capabilities from the role's default (IsReadOnly removes WRITE; IsCanReport / IsCanExport / IsCanCreateNewRecords
 * remove REPORT / EXPORT / CREATE). A row left at its defaults is equivalent to no row.
 */
public class AD_Table_Access_StepDef
{
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final AD_Role_StepDefData roleTable;

	public AD_Table_Access_StepDef(@NonNull final AD_Role_StepDefData roleTable)
	{
		this.roleTable = roleTable;
	}

	/**
	 * Creates an {@code AD_Table_Access} row granting/restricting a role on one table. Each flag defaults to its
	 * column's non-restricting value, so a row that sets only {@code IsCanCreateNewRecords=false} subtracts just the
	 * create permission and leaves read, edit, report and export intact.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Role_ID</b> — (required, identifier-ref) the role the access row belongs to<br>
	 *   <b>TableName</b> — (required) the table the access applies to, e.g. {@code C_BPartner}<br>
	 *   <b>IsReadOnly</b> — (optional, default false) removes WRITE when true<br>
	 *   <b>IsCanReport</b> — (optional, default true) removes REPORT when false<br>
	 *   <b>IsCanExport</b> — (optional, default true) removes EXPORT when false<br>
	 *   <b>IsCanCreateNewRecords</b> — (optional, default true) removes CREATE when false<br>
	 * @cucumber.depends StepDefData: AD_Role_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains AD_Table_Access:
	 *   | AD_Role_ID       | TableName | IsCanCreateNewRecords |
	 *   | restrictedRole   | C_BPartner | false                |
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
		final String tableName = row.getAsString("TableName");
		final int adTableId = adTableDAO.retrieveTableId(tableName);

		final I_AD_Table_Access record = InterfaceWrapperHelper.newInstance(I_AD_Table_Access.class);
		record.setAD_Role_ID(roleRecord.getAD_Role_ID());
		record.setAD_Table_ID(adTableId);
		record.setIsReadOnly(row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsReadOnly).orElseFalse());
		record.setIsCanReport(row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanReport).orElse(true));
		record.setIsCanExport(row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanExport).orElse(true));
		record.setIsCanCreateNewRecords(row.getAsOptionalBoolean(I_AD_Table_Access.COLUMNNAME_IsCanCreateNewRecords).orElse(true));
		record.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(record);
	}
}
