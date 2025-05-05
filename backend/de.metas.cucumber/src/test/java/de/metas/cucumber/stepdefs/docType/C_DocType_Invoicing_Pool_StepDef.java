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

package de.metas.cucumber.stepdefs.docType;

import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Invoicing_Pool;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_C_DocType_Invoicing_Pool_ID;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_IsOnDistinctICTypes;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_IsSOTrx;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_Name;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_Negative_Amt_C_DocType_ID;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_Positive_Amt_C_DocType_ID;

public class C_DocType_Invoicing_Pool_StepDef
{
	private final C_DocType_Invoicing_Pool_StepDefData invoicingPoolTable;
	private final C_DocType_StepDefData docTypeTable;

	public C_DocType_Invoicing_Pool_StepDef(
			@NonNull final C_DocType_Invoicing_Pool_StepDefData invoicingPoolTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.invoicingPoolTable = invoicingPoolTable;
		this.docTypeTable = docTypeTable;
	}

	@And("metasfresh contains C_DocType_Invoicing_Pool:")
	public void metasfresh_contains_C_DocType_Invoicing_Pool(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createDocTypeInvoicingPool(tableRow);
		}
	}

	private void createDocTypeInvoicingPool(@NonNull final Map<String, String> tableRow)
	{
		final String name = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
		final String positiveAmtDocTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Positive_Amt_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String negativeAmtDocTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Negative_Amt_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		final boolean soTrx = DataTableUtil.extractBooleanForColumnName(tableRow, COLUMNNAME_IsSOTrx);
		final boolean isOnDistinctICTypes = DataTableUtil.extractBooleanForColumnName(tableRow, COLUMNNAME_IsOnDistinctICTypes);

		final I_C_DocType positiveAmtDocTypeRecord = docTypeTable.get(positiveAmtDocTypeIdentifier);
		assertThat(positiveAmtDocTypeRecord).isNotNull();

		final I_C_DocType negativeAmtDocTypeRecord = docTypeTable.get(negativeAmtDocTypeIdentifier);
		assertThat(negativeAmtDocTypeRecord).isNotNull();

		final I_C_DocType_Invoicing_Pool docTypeInvoicingPoolRecord = InterfaceWrapperHelper.newInstance(I_C_DocType_Invoicing_Pool.class);
		docTypeInvoicingPoolRecord.setName(name);
		docTypeInvoicingPoolRecord.setIsSOTrx(soTrx);
		docTypeInvoicingPoolRecord.setIsOnDistinctICTypes(isOnDistinctICTypes);
		docTypeInvoicingPoolRecord.setPositive_Amt_C_DocType_ID(positiveAmtDocTypeRecord.getC_DocType_ID());
		docTypeInvoicingPoolRecord.setNegative_Amt_C_DocType_ID(negativeAmtDocTypeRecord.getC_DocType_ID());

		saveRecord(docTypeInvoicingPoolRecord);

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_DocType_Invoicing_Pool_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoicingPoolTable.put(identifier, docTypeInvoicingPoolRecord);
	}
}
