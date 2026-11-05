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

package de.metas.cucumber.stepdefs.bank;

import de.metas.cucumber.stepdefs.C_DataImport_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Bank;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_Bank_StepDef
{
	private final C_DataImport_StepDefData dataImportTable;
	private final C_Bank_StepDefData bankTable;

	public C_Bank_StepDef(
			@NonNull final C_DataImport_StepDefData dataImportTable,
			@NonNull final C_Bank_StepDefData bankTable)
	{
		this.dataImportTable = dataImportTable;
		this.bankTable = bankTable;
	}

	/**
	 * Creates a new C_Bank record per row (no upsert).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Bank_ID</b> (or <b>C_Bank_ID.Identifier</b>) - (required) identifier under which the bank is registered<br>
	 *   <b>Name</b>, <b>RoutingNo</b> - (required)<br>
	 *   <b>SwiftCode</b> - (optional) the BIC; absent or blank = the bank has no BIC<br>
	 *   <b>C_DataImport_ID.Identifier</b> - (optional, identifier-ref) data import of the bank; absent = the AD_Column default (the bank statement data import)<br>
	 *   <b>OPT.IsImportAsSingleSummaryLine</b> - (optional) Y/N, default N
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Bank:
	 *   | C_Bank_ID  | Name        | RoutingNo | SwiftCode   |
	 *   | debtorBank | Debtor Bank | 20000000  | TESTDEB2XXX |
	 * </pre>
	 */
	@And("metasfresh contains C_Bank:")
	public void add_C_BanK(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			addBank(row);
		}
	}

	private void addBank(@NonNull final Map<String, String> row)
	{
		final DataTableRow tableRow = DataTableRow.singleRow(row);

		final I_C_Bank bankRecord = InterfaceWrapperHelper.newInstance(I_C_Bank.class);
		tableRow.getAsOptionalIdentifier(I_C_Bank.COLUMNNAME_C_DataImport_ID)
				.map(dataImportTable::get)
				.ifPresent(dataImportRecord -> bankRecord.setC_DataImport_ID(dataImportRecord.getC_DataImport_ID()));
		bankRecord.setName(tableRow.getAsString(I_C_Bank.COLUMNNAME_Name));
		bankRecord.setRoutingNo(tableRow.getAsString(I_C_Bank.COLUMNNAME_RoutingNo));
		tableRow.getAsOptionalString(I_C_Bank.COLUMNNAME_SwiftCode).ifPresent(bankRecord::setSwiftCode);
		tableRow.getAsOptionalBoolean(I_C_Bank.COLUMNNAME_IsImportAsSingleSummaryLine).ifPresent(bankRecord::setIsImportAsSingleSummaryLine);
		saveRecord(bankRecord);

		bankTable.put(tableRow.getAsIdentifier(I_C_Bank.COLUMNNAME_C_Bank_ID), bankRecord);
	}
}
