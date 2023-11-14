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

import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_ReferenceNo_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_ReferenceNo_StepDefData referenceNoTable;
	private final C_Invoice_StepDefData invoiceTable;

	public C_ReferenceNo_StepDef(@NonNull final C_ReferenceNo_StepDefData referenceNoTable,
			@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.referenceNoTable = referenceNoTable;
		this.invoiceTable = invoiceTable;
	}

	@And("update C_ReferenceNo:")
	public void update_C_ReferenceNo(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			updateReferenceNo(dataTableRow);
		}
	}

	private void updateReferenceNo(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_ReferenceNo referenceNoRecord = referenceNoTable.get(identifier);

		final String referenceNo = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_ReferenceNo.COLUMNNAME_ReferenceNo);
		if (Check.isNotBlank(referenceNo))
		{
			referenceNoRecord.setReferenceNo(referenceNo);
		}

		saveRecord(referenceNoRecord);
	}

	@And("load C_ReferenceNo:")
	public void load_C_ReferenceNo(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			loadReferenceNo(dataTableRow);
		}
	}

	private void loadReferenceNo(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer recordId = invoiceTable.getOptional(recordIdentifier)
				.map(I_C_Invoice::getC_Invoice_ID)
				.orElseGet(() -> Integer.parseInt(recordIdentifier));

		final Integer typeId = DataTableUtil.extractIntegerOrNullForColumnName(row, I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (recordId != null && typeId!=null)
		{

			final IQuery<I_C_ReferenceNo_Doc> referenceNo_docQueryBuilderQuery = queryBL
					.createQueryBuilder(I_C_ReferenceNo_Doc.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
					.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID, recordId)
					.create();

			final I_C_ReferenceNo referenceNoRecord =  queryBL
					.createQueryBuilder(I_C_ReferenceNo.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID, typeId)
					.addInArrayFilter(I_C_ReferenceNo_Type.COLUMNNAME_AD_Org_ID, OrgId.MAIN, OrgId.ANY)
					.addInSubQueryFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID, I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID, referenceNo_docQueryBuilderQuery)
					.create()
					.firstOnly(I_C_ReferenceNo.class);
					;
			referenceNoTable.putOrReplace(identifier, referenceNoRecord);
		}
	}
}
