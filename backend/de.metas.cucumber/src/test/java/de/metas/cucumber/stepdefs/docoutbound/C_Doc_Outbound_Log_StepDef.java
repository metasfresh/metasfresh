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

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_C_BPartner_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID;
import static de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_Line_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.*;

public class C_Doc_Outbound_Log_StepDef
{
	private static final Logger logger = LogManager.getLogger(C_Doc_Outbound_Log_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Doc_Outbound_Log_StepDefData docOutboundLogTable;
	private final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Order_StepDefData orderTable;
	private final C_Invoice_StepDefData invoiceTable;

	public C_Doc_Outbound_Log_StepDef(
			@NonNull final C_Doc_Outbound_Log_StepDefData docOutboundLogTable,
			@NonNull final C_Doc_Outbound_Log_Line_StepDefData docOutboundLogLineTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.docOutboundLogTable = docOutboundLogTable;
		this.docOutboundLogLineTable = docOutboundLogLineTable;
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
		this.invoiceTable = invoiceTable;
	}

	/**
	 * Polls until the outbound log of the given record exists AND all given columns match,
	 * then stores it under the given identifier. Fails with the last mismatch on timeout.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Doc_Outbound_Log_ID</b> — (required, identifier-ref) identifier under which the log is stored<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the order or invoice the log belongs to<br>
	 *   <b>AD_Table.Name</b> — (required) C_Order or C_Invoice<br>
	 *   <b>CurrentEMailAddress</b>, <b>CurrentEMailAddressCC</b> — (optional) expected address; the literal <code>null</code> expects none<br>
	 *   <b>C_BPartner_ID</b> — (optional, identifier-ref) expected business partner; the literal <code>null</code> expects none<br>
	 *   <b>DocBaseType</b> — (optional) expected base type of the log's document type<br>
	 *   <b>DocStatus</b>, <b>POReference</b>, <b>DocumentNo</b> — (optional) expected values<br>
	 *   <b>DateDoc</b> — (optional) expected document date, e.g. 2022-05-17<br>
	 *   <b>IsInvoiceEmailEnabled</b> — (optional) expected flag<br>
	 * @cucumber.depends StepDefData: C_Doc_Outbound_Log_StepDefData, C_BPartner_StepDefData, C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s validate C_Doc_Outbound_Log:
	 *   | C_Doc_Outbound_Log_ID | Record_ID | AD_Table.Name | C_BPartner_ID | DocStatus |
	 *   | orderOutboundLog      | order_1   | C_Order       | bpartner_1    | CO        |
	 * </pre>
	 */
	@And("^after not more than (.*)s validate C_Doc_Outbound_Log:$")
	public void validate_C_Doc_Outbound_Log(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final AtomicReference<String> lastMismatch = new AtomicReference<>("no C_Doc_Outbound_Log found");

			StepDefUtil.tryAndWait(
					timeoutSec,
					500,
					() -> {
						final I_C_Doc_Outbound_Log docOutboundLog = retrieveDocOutboundLog(row);
						if (docOutboundLog == null)
						{
							lastMismatch.set("no C_Doc_Outbound_Log found for the record");
							return false;
						}

						final Optional<String> mismatch = findMismatch(row, docOutboundLog);
						if (mismatch.isPresent())
						{
							lastMismatch.set(mismatch.get());
							return false;
						}

						docOutboundLogTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_Doc_Outbound_Log_ID), docOutboundLog);
						return true;
					},
					() -> logger.error("C_Doc_Outbound_Log did not reach the expected state: {}", lastMismatch.get()));
		});
	}

	/**
	 * Validates the history lines of an outbound log, in creation order
	 * (the n-th table row of a log is checked against its n-th line) and stores each line under its identifier.
	 * The log must have exactly as many lines as the table has rows for it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Doc_Outbound_Log_Line_ID</b> — (required, identifier-ref) identifier under which the line is stored<br>
	 *   <b>C_Doc_Outbound_Log_ID</b> — (required, identifier-ref) the log whose lines are validated<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the order or invoice the line belongs to<br>
	 *   <b>AD_Table.Name</b> — (required) C_Order or C_Invoice<br>
	 *   <b>DocBaseType</b>, <b>DocStatus</b> — (optional) expected values<br>
	 * @cucumber.depends StepDefData: C_Doc_Outbound_Log_StepDefData, C_Doc_Outbound_Log_Line_StepDefData, C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And validate C_Doc_Outbound_Log_Line:
	 *   | C_Doc_Outbound_Log_Line_ID | C_Doc_Outbound_Log_ID | Record_ID | AD_Table.Name | DocStatus |
	 *   | line_1                     | orderOutboundLog      | order_1   | C_Order       | CO        |
	 *   | line_2                     | orderOutboundLog      | order_1   | C_Order       | IP        |
	 * </pre>
	 */
	@And("validate C_Doc_Outbound_Log_Line:")
	public void validate_C_Doc_Outbound_Log_Line(@NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable);
		final Map<Integer, Integer> expectedLineCountByLogId = new HashMap<>();
		rows.stream().forEach(row -> expectedLineCountByLogId.merge(
				row.getAsIdentifier(I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID).lookupNotNullIn(docOutboundLogTable).getC_Doc_Outbound_Log_ID(),
				1,
				Integer::sum));
		final Map<Integer, Integer> rowCountByLogId = new HashMap<>();

		rows.forEach(row -> {
			final I_C_Doc_Outbound_Log docOutboundLog = row.getAsIdentifier(I_C_Doc_Outbound_Log_Line.COLUMNNAME_C_Doc_Outbound_Log_ID).lookupNotNullIn(docOutboundLogTable);

			final List<I_C_Doc_Outbound_Log_Line> docOutboundLogLines = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
					.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, docOutboundLog.getC_Doc_Outbound_Log_ID())
					.orderBy(I_C_Doc_Outbound_Log_Line.COLUMNNAME_Created)
					.orderBy(COLUMNNAME_C_Doc_Outbound_Log_Line_ID)
					.create()
					.list(I_C_Doc_Outbound_Log_Line.class);

			assertThat(docOutboundLogLines)
					.as("C_Doc_Outbound_Log_Line count of C_Doc_Outbound_Log_ID=%s", docOutboundLog.getC_Doc_Outbound_Log_ID())
					.hasSize(expectedLineCountByLogId.get(docOutboundLog.getC_Doc_Outbound_Log_ID()));

			final int lineIndex = rowCountByLogId.merge(docOutboundLog.getC_Doc_Outbound_Log_ID(), 1, Integer::sum) - 1;
			final I_C_Doc_Outbound_Log_Line docOutboundLogLine = docOutboundLogLines.get(lineIndex);

			final TableRecordReference recordReference = getTableRecordReference(row);

			assertThat(docOutboundLogLine.getRecord_ID()).isEqualTo(recordReference.getRecord_ID());
			assertThat(docOutboundLogLine.getAD_Table_ID()).isEqualTo(recordReference.getAD_Table_ID());

			row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocBaseType)
					.filter(Check::isNotBlank)
					.ifPresent(docBaseType -> {
						final I_C_DocType docType = load(docOutboundLogLine.getC_DocType_ID(), I_C_DocType.class);
						assertThat(docType.getDocBaseType()).isEqualTo(docBaseType);
					});

			row.getAsOptionalString(I_C_Doc_Outbound_Log_Line.COLUMNNAME_DocStatus)
					.filter(Check::isNotBlank)
					.ifPresent(docStatus -> assertThat(docOutboundLogLine.getDocStatus()).isEqualTo(docStatus));

			docOutboundLogLineTable.putOrReplace(row.getAsIdentifier(COLUMNNAME_C_Doc_Outbound_Log_Line_ID), docOutboundLogLine);
		});
	}

	/**
	 * Verifies that the given order or invoice has no outbound log with the given status.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData, C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And no C_Doc_Outbound_Log with DocStatus CO exists for record "order_1"
	 * </pre>
	 */
	@And("no C_Doc_Outbound_Log with DocStatus {word} exists for record {string}")
	public void no_C_Doc_Outbound_Log_with_DocStatus_exists_for_record(@NonNull final String docStatus, @NonNull final String recordIdentifier)
	{
		final TableRecordReference recordReference = getTableRecordReference(recordIdentifier);

		final List<Integer> matchingLogIds = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addEqualsFilter(COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus, docStatus)
				.create()
				.listIds();

		assertThat(matchingLogIds)
				.as("C_Doc_Outbound_Log_IDs with DocStatus=%s for record %s", docStatus, recordReference)
				.isEmpty();
	}

	@NonNull
	private Optional<String> findMismatch(@NonNull final DataTableRow row, @NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		// blank cell = not checked; literal "null" = expect no address (legacy: blank was never checked)
		final Optional<String> currentEmailAddress = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress).filter(Check::isNotBlank);
		if (currentEmailAddress.isPresent() && !matchesString(currentEmailAddress.get(), docOutboundLog.getCurrentEMailAddress()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress, currentEmailAddress.get(), docOutboundLog.getCurrentEMailAddress());
		}

		final Optional<String> currentEmailAddressCC = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddressCC).filter(Check::isNotBlank);
		if (currentEmailAddressCC.isPresent() && !matchesString(currentEmailAddressCC.get(), docOutboundLog.getCurrentEMailAddressCC()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddressCC, currentEmailAddressCC.get(), docOutboundLog.getCurrentEMailAddressCC());
		}

		// literal "null" = the partner must not be set
		final Optional<StepDefDataIdentifier> bpIdentifier = row.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_ID);
		if (bpIdentifier.isPresent())
		{
			final int expectedBPartnerId = bpIdentifier.get().isNullPlaceholder() ? -1 : bpIdentifier.get().lookupNotNullIn(bpartnerTable).getC_BPartner_ID();
			final boolean matches = expectedBPartnerId <= 0 ? docOutboundLog.getC_BPartner_ID() <= 0 : docOutboundLog.getC_BPartner_ID() == expectedBPartnerId;
			if (!matches)
			{
				return mismatch(COLUMNNAME_C_BPartner_ID, expectedBPartnerId <= 0 ? "not set" : expectedBPartnerId, docOutboundLog.getC_BPartner_ID());
			}
		}

		final Optional<String> docBaseType = row.getAsOptionalString(I_C_DocType.COLUMNNAME_DocBaseType).filter(Check::isNotBlank);
		if (docBaseType.isPresent())
		{
			final I_C_DocType docType = load(docOutboundLog.getC_DocType_ID(), I_C_DocType.class);
			if (!docBaseType.get().equals(docType.getDocBaseType()))
			{
				return mismatch(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType.get(), docType.getDocBaseType());
			}
		}

		final Optional<String> docStatus = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus).filter(Check::isNotBlank);
		if (docStatus.isPresent() && !docStatus.get().equals(docOutboundLog.getDocStatus()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DocStatus, docStatus.get(), docOutboundLog.getDocStatus());
		}

		final Optional<String> poReference = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_POReference).filter(Check::isNotBlank);
		if (poReference.isPresent() && !matchesString(poReference.get(), docOutboundLog.getPOReference()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_POReference, poReference.get(), docOutboundLog.getPOReference());
		}

		final Optional<String> documentNo = row.getAsOptionalString(I_C_Doc_Outbound_Log.COLUMNNAME_DocumentNo).filter(Check::isNotBlank);
		if (documentNo.isPresent() && !documentNo.get().equals(docOutboundLog.getDocumentNo()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DocumentNo, documentNo.get(), docOutboundLog.getDocumentNo());
		}

		final Optional<Timestamp> dateDoc = row.getAsOptionalLocalDateTimestamp(I_C_Doc_Outbound_Log.COLUMNNAME_DateDoc);
		if (dateDoc.isPresent() && !dateDoc.get().equals(docOutboundLog.getDateDoc()))
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_DateDoc, dateDoc.get(), docOutboundLog.getDateDoc());
		}

		final Boolean isInvoiceEmailEnabled = row.getAsOptionalBoolean(I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled).toBooleanOrNull();
		if (isInvoiceEmailEnabled != null && isInvoiceEmailEnabled != docOutboundLog.isInvoiceEmailEnabled())
		{
			return mismatch(I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled, isInvoiceEmailEnabled, docOutboundLog.isInvoiceEmailEnabled());
		}

		return Optional.empty();
	}

	private static boolean matchesString(@NonNull final String expected, @Nullable final String actual)
	{
		return "null".equals(expected) ? Check.isBlank(actual) : expected.equals(actual);
	}

	@NonNull
	private static Optional<String> mismatch(final String columnName, final Object expected, @Nullable final Object actual)
	{
		return Optional.of(columnName + ": expected <" + expected + "> but was <" + actual + ">");
	}

	@NonNull
	private TableRecordReference getTableRecordReference(@NonNull final DataTableRow row)
	{
		final String recordIdentifier = row.getAsIdentifier(COLUMNNAME_Record_ID).getAsString();
		final String tableName = row.getAsString(I_AD_Table.Table_Name + "." + I_AD_Table.COLUMNNAME_Name);

		switch (tableName)
		{
			case I_C_Order.Table_Name:
				return TableRecordReference.of(orderTable.get(recordIdentifier));

			case I_C_Invoice.Table_Name:
				return TableRecordReference.of(invoiceTable.get(recordIdentifier));

			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("TableName", tableName);
		}
	}

	/** Resolves an order or invoice identifier (orders first). */
	@NonNull
	private TableRecordReference getTableRecordReference(@NonNull final String recordIdentifier)
	{
		final Optional<I_C_Order> order = orderTable.getOptional(recordIdentifier);
		if (order.isPresent())
		{
			return TableRecordReference.of(order.get());
		}

		final Optional<I_C_Invoice> invoice = invoiceTable.getOptional(recordIdentifier);
		if (invoice.isPresent())
		{
			return TableRecordReference.of(invoice.get());
		}

		throw new AdempiereException("No order or invoice registered for identifier").setParameter("Identifier", recordIdentifier);
	}

	@Nullable
	private I_C_Doc_Outbound_Log retrieveDocOutboundLog(@NonNull final DataTableRow row)
	{
		final TableRecordReference recordReference = getTableRecordReference(row);

		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addEqualsFilter(COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.addEqualsFilter(COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.create()
				.firstOnlyOrNull(I_C_Doc_Outbound_Log.class);
	}
}
