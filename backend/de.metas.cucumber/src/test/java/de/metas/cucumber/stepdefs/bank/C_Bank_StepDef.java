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

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.dataImport.C_DataImport_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_DataImport;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class C_Bank_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_DataImport_StepDefData dataImportTable;
	private final C_Bank_StepDefData bankTable;

	public C_Bank_StepDef(
			@NonNull final C_DataImport_StepDefData dataImportTable,
			@NonNull final C_Bank_StepDefData bankTable)
	{
		this.dataImportTable = dataImportTable;
		this.bankTable = bankTable;
	}

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
		final String name = DataTableUtil.extractStringForColumnName(row, I_C_Bank.COLUMNNAME_Name);
		final String routingNo = DataTableUtil.extractStringForColumnName(row, I_C_Bank.COLUMNNAME_RoutingNo);
		final String swiftCode = DataTableUtil.extractStringForColumnName(row, I_C_Bank.COLUMNNAME_SwiftCode);
		final String dataImportIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Bank.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
		final boolean isImportAsSingleSummaryLine  = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Bank.COLUMNNAME_IsImportAsSingleSummaryLine, false);

		final I_C_DataImport dataImportRecord = dataImportTable.get(dataImportIdentifier);
		assertThat(dataImportRecord).isNotNull();

		final I_C_Bank bankRecord = CoalesceUtil
				.coalesceSuppliersNotNull(() -> queryBL.createQueryBuilder(I_C_Bank.class)
												  .addOnlyActiveRecordsFilter()
												  .addEqualsFilter(I_C_Bank.COLUMNNAME_Name, name)
												  .addEqualsFilter(I_C_Bank.COLUMNNAME_RoutingNo, routingNo)
												  .create()
												  .firstOnly(I_C_Bank.class),
										  () -> InterfaceWrapperHelper.newInstance(I_C_Bank.class));

		bankRecord.setC_DataImport_ID(dataImportRecord.getC_DataImport_ID());
		bankRecord.setName(name);
		bankRecord.setRoutingNo(routingNo);
		bankRecord.setSwiftCode(swiftCode);
		bankRecord.setIsImportAsSingleSummaryLine(isImportAsSingleSummaryLine);

		saveRecord(bankRecord);

		final String bankIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Bank.COLUMNNAME_C_Bank_ID + "." + TABLECOLUMN_IDENTIFIER);
		bankTable.put(bankIdentifier, bankRecord);
	}
}
