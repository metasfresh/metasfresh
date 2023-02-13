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

package de.metas.cucumber.stepdefs.invoice;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_ElementValue_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Invoice_Acct;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Invoice_Acct_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_AcctSchema_StepDefData acctSchemaTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_InvoiceLine_StepDefData invoiceLineTable;
	private final C_Invoice_Acct_StepDefData invoiceAcctTable;
	private final C_ElementValue_StepDefData elementValueTable;

	public C_Invoice_Acct_StepDef(
			@NonNull final C_AcctSchema_StepDefData acctSchemaTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_InvoiceLine_StepDefData invoiceLineTable,
			@NonNull final C_Invoice_Acct_StepDefData invoiceAcctTable,
			@NonNull final C_ElementValue_StepDefData elementValueTable)
	{
		this.acctSchemaTable = acctSchemaTable;
		this.invoiceTable = invoiceTable;
		this.invoiceLineTable = invoiceLineTable;
		this.invoiceAcctTable = invoiceAcctTable;
		this.elementValueTable = elementValueTable;
	}

	@And("C_Invoice_Acct is found:")
	public void find_C_Invoice_Acct(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			findInvoiceAcct(row);
		}
	}

	@And("metasfresh contains C_Invoice_Acct:")
	public void contains_C_Invoice_Acct(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			createInvoiceAcct(row);
		}
	}

	@And("update C_Invoice_Acct:")
	public void update_C_Invoice_Acct(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			updateInvoiceAcct(row);
		}
	}

	private void findInvoiceAcct(@NonNull final Map<String, String> row)
	{
		final IQueryBuilder<I_C_Invoice_Acct> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter();

		final String acctSchemaIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(acctSchemaIdentifier))
		{
			final I_C_AcctSchema acctSchemaRecord = acctSchemaTable.get(acctSchemaIdentifier);
			assertThat(acctSchemaRecord).isNotNull();

			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaRecord.getC_AcctSchema_ID());
		}

		final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceIdentifier))
		{
			final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);
			assertThat(invoiceRecord).isNotNull();

			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID());
		}

		final String invoiceLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(invoiceLineIdentifier))
		{
			final I_C_InvoiceLine invoiceLineRecord = invoiceLineTable.get(invoiceLineIdentifier);
			assertThat(invoiceLineRecord).isNotNull();

			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID, invoiceLineRecord.getC_InvoiceLine_ID());
		}

		final String accountName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_AccountName);
		if (Check.isNotBlank(accountName))
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_AccountName, accountName);
		}

		final String elementValueIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_C_ElementValue_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(elementValueIdentifier))
		{
			final I_C_ElementValue elementValueRecord = elementValueTable.get(elementValueIdentifier);

			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_ElementValue_ID, elementValueRecord.getC_ElementValue_ID());
		}

		final I_C_Invoice_Acct invoiceAcctRecord = queryBuilder.create()
				.firstOnlyNotNull(I_C_Invoice_Acct.class);

		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_Invoice_Acct_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceAcctTable.put(identifier, invoiceAcctRecord);
	}

	private void createInvoiceAcct(@NonNull final Map<String, String> row)
	{
		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice invoiceRecord = invoiceTable.get(invoiceIdentifier);

		final String acctSchemaIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_AcctSchema acctSchemaRecord = acctSchemaTable.get(acctSchemaIdentifier);

		final String elementValueIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_ElementValue_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_ElementValue elementValueRecord = elementValueTable.get(elementValueIdentifier);

		final IQueryBuilder<I_C_Invoice_Acct> queryBuilder = queryBL.createQueryBuilder(I_C_Invoice_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_Invoice_ID, invoiceRecord.getC_Invoice_ID())
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaRecord.getC_AcctSchema_ID())
				.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_ElementValue_ID, elementValueRecord.getC_ElementValue_ID());

		final String invoiceLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int invoiceLineId;
		if (Check.isNotBlank(invoiceLineIdentifier))
		{
			invoiceLineId = invoiceLineTable.get(invoiceLineIdentifier).getC_InvoiceLine_ID();
			queryBuilder.addEqualsFilter(I_C_Invoice_Acct.COLUMNNAME_C_InvoiceLine_ID, invoiceLineId);
		}
		else
		{
			invoiceLineId = -1;
		}
		
		final I_C_Invoice_Acct invoiceAcctRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBuilder.create().firstOnlyOrNull(I_C_Invoice_Acct.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_Invoice_Acct.class));

		invoiceAcctRecord.setC_Invoice_ID(invoiceRecord.getC_Invoice_ID());
		invoiceAcctRecord.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		invoiceAcctRecord.setC_ElementValue_ID(elementValueRecord.getC_ElementValue_ID());
		invoiceAcctRecord.setC_InvoiceLine_ID(invoiceLineId);

		saveRecord(invoiceAcctRecord);

		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_Invoice_Acct_ID + "." + TABLECOLUMN_IDENTIFIER);
		invoiceAcctTable.putOrReplace(identifier, invoiceAcctRecord);
	}

	private void updateInvoiceAcct(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Acct.COLUMNNAME_C_Invoice_Acct_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Acct invoiceAcctRecord = invoiceAcctTable.get(identifier);

		final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_C_Invoice_Acct.COLUMNNAME_IsActive);
		if (isActive != null)
		{
			invoiceAcctRecord.setIsActive(isActive);
		}

		saveRecord(invoiceAcctRecord);
	}
}
