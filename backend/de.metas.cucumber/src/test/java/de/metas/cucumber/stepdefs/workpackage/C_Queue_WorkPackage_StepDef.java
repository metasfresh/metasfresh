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

package de.metas.cucumber.stepdefs.workpackage;

import com.google.common.collect.ImmutableSet;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.*;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.olcand.C_OLCand_StepDefData;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Invoice;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/** Step definitions for {@code C_Queue_WorkPackage} — locating, validating, and asserting the state of async workpackages. */
public class C_Queue_WorkPackage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private static final String MAIL_WP_PROCESSOR_INTERNAL_NAME = "MailWorkpackageProcessor";

	@NonNull private final C_Queue_Processor_StepDefData processorTable;
	@NonNull private final C_Queue_WorkPackage_StepDefData workPackageTable;
	@NonNull private final C_Queue_Element_StepDefData queueElementTable;
	@NonNull private final C_OLCand_StepDefData candidateTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	public C_Queue_WorkPackage_StepDef(
			@NonNull final C_Queue_Processor_StepDefData processorTable,
			@NonNull final C_Queue_WorkPackage_StepDefData workPackageTable,
			@NonNull final C_Queue_Element_StepDefData queueElementTable,
			@NonNull final C_OLCand_StepDefData candidateTable,
			@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.processorTable = processorTable;
		this.workPackageTable = workPackageTable;
		this.queueElementTable = queueElementTable;
		this.candidateTable = candidateTable;
		this.invoiceTable = invoiceTable;
	}

	@And("locate last C_Queue_WorkPackage by enqueued element")
	public void locate_C_Queue_WorkPackage_by_enqueued_element(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String workPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String packageProcessorInternalName = DataTableUtil.extractStringForColumnName(row, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID + "." + I_C_Queue_PackageProcessor.COLUMNNAME_InternalName);
			final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
			final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_Record_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_AD_Table adTable = tableDAO.retrieveTable(tableName);

			switch (adTable.getTableName())
			{
				case I_C_OLCand.Table_Name:
					final I_C_OLCand candidate = candidateTable.get(recordIdentifier);
					final TableRecordReference candidateReference = TableRecordReference.of(candidate);

					resolveWorkPackageByQueueElementAndPackageProcessor(workPackageIdentifier, candidateReference, packageProcessorInternalName);
					break;

				default:
					throw new AdempiereException("Table not supported! TableName:" + tableName);
			}
		}
	}

	@And("validate enqueued elements for C_Queue_WorkPackage")
	public void validate_enqueued_elements_for_C_Queue_WorkPackage(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String queueElementIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_C_Queue_Element_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String workPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Queue_WorkPackage workPackage = workPackageTable.get(workPackageIdentifier);
			final QueueWorkPackageId workPackageId = QueueWorkPackageId.ofRepoId(workPackage.getC_Queue_WorkPackage_ID());

			final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_AD_Table_ID + "." + I_AD_Table.COLUMNNAME_TableName);
			final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Element.COLUMNNAME_Record_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final I_AD_Table adTable = tableDAO.retrieveTable(tableName);

			switch (adTable.getTableName())
			{
				case I_C_OLCand.Table_Name:
					final I_C_OLCand candidate = candidateTable.get(recordIdentifier);
					final TableRecordReference candidateReference = TableRecordReference.of(candidate);

					validateC_Queue_Element(workPackageId, candidateReference, queueElementIdentifier);
					break;

				default:
					throw new AdempiereException("Table not supported! TableName:" + tableName);
			}

		}
	}

	private void resolveWorkPackageByQueueElementAndPackageProcessor(
			@NonNull final String workPackageIdentifier,
			@NonNull final TableRecordReference reference,
			@NonNull final String packageProcessorName)
	{
		final I_C_Queue_PackageProcessor packageProcessor = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_InternalName, packageProcessorName)
				.create()
				.firstOnlyNotNull(I_C_Queue_PackageProcessor.class);

		final IQuery<I_C_Queue_PackageProcessor> queueryWithGivenPackageProcessorQuery = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessor.getC_Queue_PackageProcessor_ID())
				.create();

		final I_C_Queue_WorkPackage workPackage = queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, reference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, reference.getRecord_ID())
				.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class)
				.addInSubQueryFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID, queueryWithGivenPackageProcessorQuery)
				.orderByDescending(I_C_Queue_WorkPackage.COLUMNNAME_Created)
				.create()
				.firstOptional(I_C_Queue_WorkPackage.class)
				.orElseThrow(() -> new AdempiereException("No C_Queue_WorkPackage found for TableRecordReference and PackageProcessorName")
						.appendParametersToMessage()
						.setParameter("TableRecordReference", reference)
						.setParameter("PackageProcessorName", packageProcessorName));

		workPackageTable.putOrReplace(workPackageIdentifier, workPackage);
	}

	private void validateC_Queue_Element(
			@NonNull final QueueWorkPackageId workPackageId,
			@NonNull final TableRecordReference recordReference,
			@NonNull final String queueElementIdentifier)
	{
		final I_C_Queue_Element queueElement = queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_C_Queue_WorkPackage_ID, workPackageId)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, recordReference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, recordReference.getRecord_ID())
				.create()
				.firstOptional(I_C_Queue_Element.class)
				.orElseThrow(() -> new AdempiereException("No C_Queue_Element found for QueueWorkPackageId and TableRecordReference")
						.appendParametersToMessage()
						.setParameter("QueueWorkPackageId", workPackageId)
						.setParameter("TableRecordReference", recordReference));

		assertThat(queueElement).isNotNull();
		queueElementTable.putOrReplace(queueElementIdentifier, queueElement);
	}

	@And("^after not more than (.*)s, there are no C_Queue_WorkPackage pending or running in queue (.*)$")
	public void there_are_no_C_Queue_WorkPackage_Pending_Running(final int nrOfSeconds, @NonNull final String queueProcessorIdentifier) throws InterruptedException
	{
		final I_C_Queue_Processor processor = processorTable.get(queueProcessorIdentifier);

		final Set<Integer> assignedPackageProcessorsIds = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_Processor_ID, processor.getC_Queue_Processor_ID())
				.create()
				.stream()
				.map(I_C_Queue_Processor_Assign::getC_Queue_PackageProcessor_ID)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(assignedPackageProcessorsIds.size()).isGreaterThan(0);

		final Supplier<Boolean> noPendingOrRunningPackage = () -> {

			final IQueryFilter<I_C_Queue_WorkPackage> isNotDoneYet = queryBL.createCompositeQueryFilter(I_C_Queue_WorkPackage.class)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_Processed, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, false)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsReadyForProcessing, true);

			return queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
					.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, assignedPackageProcessorsIds)
					.filter(isNotDoneYet)
					.create()
					.count() == 0;
		};

		StepDefUtil.tryAndWait(nrOfSeconds, 1000, noPendingOrRunningPackage);
	}

	/**
	 * Polls until the {@code MailWorkpackageProcessor} workpackage for the given invoice's
	 * {@link I_C_Doc_Outbound_Log_Line} reaches the expected state:
	 * <ul>
	 *   <li>{@code skipped} — held back by the notification-delay gate:
	 *       {@code SkippedAt IS NOT NULL AND Processed = false AND IsError = false}</li>
	 *   <li>{@code processed} — successfully sent: {@code Processed = true}</li>
	 *   <li>{@code released} — passed the gate (either sent or attempted to send, possibly with an error):
	 *       {@code Processed = true OR IsError = true}</li>
	 * </ul>
	 *
	 * <p>The step navigates the chain:
	 * {@code C_Invoice} → {@code C_Doc_Outbound_Log} (by table+record) →
	 * {@code C_Doc_Outbound_Log_Line} → {@code C_Queue_Element} →
	 * {@code C_Queue_WorkPackage} (filtered by {@code MailWorkpackageProcessor}).</p>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) invoice whose mail workpackage is checked<br>
	 *   <b>ExpectedState</b> — (required) one of {@code skipped}, {@code processed}, or {@code released}<br>
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, MailWorkpackageProcessor workpackage for invoice is in state:
	 *   | C_Invoice_ID | ExpectedState |
	 *   | invoice_1    | skipped       |
	 * </pre>
	 */
	@And("^after not more than (.*)s, MailWorkpackageProcessor workpackage for invoice is in state:$")
	public void assertMailWorkpackageState(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach(row -> {
			try
			{
				assertMailWorkpackageStateForRow(timeoutSec, row);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
	}

	private void assertMailWorkpackageStateForRow(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Invoice invoice = row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable);
		final int invoiceTableId = tableDAO.retrieveTableId(I_C_Invoice.Table_Name);
		final String expectedState = row.getAsString("ExpectedState");

		final I_C_Queue_PackageProcessor mailProcessor = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_InternalName, MAIL_WP_PROCESSOR_INTERNAL_NAME)
				.create()
				.firstOnlyNotNull(I_C_Queue_PackageProcessor.class);

		final Supplier<Boolean> condition = () -> {
			final I_C_Doc_Outbound_Log docLog = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
					.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, invoiceTableId)
					.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID())
					.orderByDescending(I_C_Doc_Outbound_Log.COLUMNNAME_Created)
					.create()
					.first(I_C_Doc_Outbound_Log.class);
			if (docLog == null)
			{
				return false;
			}

			final I_C_Doc_Outbound_Log_Line docLogLine = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log_Line.class)
					.addEqualsFilter(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, docLog.getC_Doc_Outbound_Log_ID())
					.orderByDescending(I_C_Doc_Outbound_Log_Line.COLUMNNAME_Created)
					.create()
					.first(I_C_Doc_Outbound_Log_Line.class);
			if (docLogLine == null)
			{
				return false;
			}

			final I_C_Queue_WorkPackage workPackage = queryBL.createQueryBuilder(I_C_Queue_Element.class)
					.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID,
							tableDAO.retrieveTableId(I_C_Doc_Outbound_Log_Line.Table_Name))
					.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, docLogLine.getC_Doc_Outbound_Log_Line_ID())
					.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class)
					.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID,
							mailProcessor.getC_Queue_PackageProcessor_ID())
					.orderByDescending(I_C_Queue_WorkPackage.COLUMNNAME_Created)
					.create()
					.first(I_C_Queue_WorkPackage.class);
			if (workPackage == null)
			{
				return false;
			}

			switch (expectedState)
			{
				case "skipped":
					// WP was held back by the delay gate: SkippedAt is set, not yet processed or errored
					return workPackage.getSkippedAt() != null && !workPackage.isProcessed() && !workPackage.isError();
				case "processed":
					return workPackage.isProcessed();
				case "released":
					// WP was NOT held back (or was released after skip) — it ran (successfully or errored for non-delay reasons)
					// IsError=Y (e.g. Azure SDK missing) OR Processed=Y, but SkippedAt must NOT be the sole final state
					return workPackage.isProcessed() || workPackage.isError();
				default:
					throw new AdempiereException("Unknown ExpectedState: " + expectedState
							+ " — use 'skipped', 'processed', or 'released'");
			}
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, condition);

		// Re-read and assert clearly so the failure message is informative
		final boolean satisfied = condition.get();
		assertThat(satisfied)
				.as("MailWorkpackageProcessor workpackage for C_Invoice_ID=%s did not reach state '%s' within %ss",
						invoice.getC_Invoice_ID(), expectedState, timeoutSec)
				.isTrue();
	}
}
