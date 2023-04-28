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

package de.metas.cucumber.stepdefs.docoutbound;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_Line_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;

public class C_Doc_Outbound_Log_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Doc_Outbound_Log_StepDefData docOutboundLogTable;
	private final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Order_StepDefData orderTable;

	public C_Doc_Outbound_Log_StepDef(
			@NonNull final C_Doc_Outbound_Log_StepDefData docOutboundLogTable,
			@NonNull final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Order_StepDefData orderTable)
	{
		this.docOutboundLogTable = docOutboundLogTable;
		this.docOutboundLogLineTable = docOutboundLogLineTable;
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
	}

	@And("^after not more than (.*)s validate C_Doc_Outbound_Log:$")
	public void validate_C_Doc_Outbound_Log(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> retrieveDocOutboundLog(row));

			validateDocOutboundLog(row);
		}
	}

	@And("validate C_Doc_Outbound_Log_Line:")
	public void validate_C_Doc_Outbound_Log_Line(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String docOutboundIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Doc_Outbound_Log docOutboundLog = docOutboundLogTable.get(docOutboundIdentifier);
			assertThat(docOutboundLog).isNotNull();

			final I_C_Doc_Outbound_Log_Line docOutboundLogLine = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
					.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, docOutboundLog.getC_Doc_Outbound_Log_ID())
					.create()
					.firstOnlyNotNull(I_C_Doc_Outbound_Log_Line.class);
			assertThat(docOutboundLogLine).isNotNull();

			final TableRecordReference recordReference = getTableRecordReference(row);

			assertThat(docOutboundLogLine.getRecord_ID()).isEqualTo(recordReference.getRecord_ID());
			assertThat(docOutboundLogLine.getAD_Table_ID()).isEqualTo(recordReference.getAD_Table_ID());

			final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_DocType.COLUMNNAME_DocBaseType);
			if (Check.isNotBlank(docBaseType))
			{
				final I_C_DocType docType = load(docOutboundLogLine.getC_DocType_ID(), I_C_DocType.class);
				assertThat(docType.getDocBaseType()).isEqualTo(docBaseType);
			}

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Doc_Outbound_Log_Line.COLUMNNAME_DocStatus);
			if (Check.isNotBlank(docStatus))
			{
				assertThat(docOutboundLogLine.getDocStatus()).isEqualTo(docStatus);
			}

			final String docOutboundLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Doc_Outbound_Log_Line_ID + "." + TABLECOLUMN_IDENTIFIER);
			docOutboundLogLineTable.put(docOutboundLineIdentifier, docOutboundLogLine);
		}
	}

	private void validateDocOutboundLog(@NonNull final Map<String, String> row)
	{
		final String docOutBoundIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Doc_Outbound_Log docOutboundLog = docOutboundLogTable.get(docOutBoundIdentifier);

		final String currentEmailAddress = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress);
		if (Check.isNotBlank(currentEmailAddress))
		{
			assertThat(docOutboundLog.getCurrentEMailAddress()).isEqualTo(currentEmailAddress);
		}

		final String bpIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bpIdentifier);
			assertThat(bPartner).isNotNull();

			assertThat(docOutboundLog.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());
		}

		final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_DocType.COLUMNNAME_DocBaseType);
		if (Check.isNotBlank(docBaseType))
		{
			final I_C_DocType docType = load(docOutboundLog.getC_DocType_ID(), I_C_DocType.class);
			assertThat(docType.getDocBaseType()).isEqualTo(docBaseType);
		}

		final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus);
		if (Check.isNotBlank(docStatus))
		{
			assertThat(docOutboundLog.getDocStatus()).isEqualTo(docStatus);
		}
	}

	@NonNull
	private TableRecordReference getTableRecordReference(@NonNull final Map<String, String> row)
	{
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.Table_Name + "." + I_AD_Table.COLUMNNAME_Name);

		switch (tableName)
		{
			case I_C_Order.Table_Name:
				final I_C_Order order = orderTable.get(recordIdentifier);
				assertThat(order).isNotNull();

				return TableRecordReference.of(order);

			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("TableName", tableName);
		}
	}

	private boolean retrieveDocOutboundLog(@NonNull final Map<String, String> row)
	{
		final TableRecordReference recordReference = getTableRecordReference(row);

		final ImmutableList<I_C_Doc_Outbound_Log> docOutboundLogs = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addEqualsFilter(COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.create()
				.listImmutable(I_C_Doc_Outbound_Log.class);

		if (docOutboundLogs.isEmpty())
		{
			return false;
		}

		if (docOutboundLogs.size() > 1)
		{
			final StringBuilder stringBuilder = new StringBuilder("Multiple C_Doc_Outbound_Log records found: \n");

			docOutboundLogs.forEach(docOutboundLogRecord -> stringBuilder
					.append("Filename: ").append(docOutboundLogRecord.getFileName())
					.append("Created: ").append(docOutboundLogRecord.getCreated())
					.append("\n"));

			throw new AdempiereException(stringBuilder.toString());
		}

		final String docOutBoundIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID + "." + TABLECOLUMN_IDENTIFIER);
		docOutboundLogTable.putOrReplace(docOutBoundIdentifier, docOutboundLogs.get(0));
		return true;
	}
}
