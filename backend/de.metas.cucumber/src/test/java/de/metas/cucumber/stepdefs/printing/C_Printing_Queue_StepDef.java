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

package de.metas.cucumber.stepdefs.printing;

import de.metas.adempiere.model.I_AD_PrinterRouting;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.ordercheckup.C_Order_MFGWarehouse_Report_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.document.DocTypeId;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.IQuery;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies how many {@code C_Printing_Queue} items exist for the archive(s) of a given document -- i.e.
 * whether that document's printout was (auto-)enqueued for printing -- and, for a located item, that it
 * carries its record's own document type and resolves its own {@code AD_PrinterRouting}.
 */
@RequiredArgsConstructor
public class C_Printing_Queue_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final C_Printing_Queue_StepDefData queueItemTable;
	@NonNull private final C_Order_MFGWarehouse_Report_StepDefData reportTable;
	@NonNull private final C_DocType_StepDefData docTypeTable;
	@NonNull private final AD_PrinterRouting_StepDefData printerRoutingTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrinterRoutingDAO printerRoutingDAO = Services.get(IPrinterRoutingDAO.class);
	private final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue contains 0 items for the record identified by shipment
	 * </pre>
	 */
	@Then("C_Printing_Queue contains {int} items for the record identified by {string}")
	public void assert_printing_queue_item_count(final int expectedCount, @NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final IQuery<I_AD_Archive> archivesOfRecord = queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create();

		final int count = queryBL.createQueryBuilder(I_C_Printing_Queue.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_Printing_Queue.COLUMNNAME_AD_Archive_ID, I_AD_Archive.COLUMNNAME_AD_Archive_ID, archivesOfRecord)
				.create()
				.count();

		assertThat(count)
				.as("C_Printing_Queue item count for record %s", recordRef)
				.isEqualTo(expectedCount);
	}

	/**
	 * Locates the {@code C_Printing_Queue} item enqueued for a previously-located {@code C_Order_MFGWarehouse_Report}
	 * -- i.e. the real record -&gt; archive -&gt; printing-queue-item path, never a fabricated queue item. Stored under
	 * {@code Identifier} for the following {@code resolves} assertions.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>C_Order_MFGWarehouse_Report_ID</b> — (required, identifier-ref) a report located via
	 *       "C_Order_MFGWarehouse_Report is located:"<br>
	 * @cucumber.depends StepDefData: C_Order_MFGWarehouse_Report_StepDefData, C_Printing_Queue_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And C_Printing_Queue item is located:
	 *   | Identifier      | C_Order_MFGWarehouse_Report_ID |
	 *   | warehouseQueue3 | warehouseRpt3                  |
	 * </pre>
	 */
	@And("C_Printing_Queue item is located:")
	public void locate_printing_queue_item(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			try
			{
				locatePrintingQueueItemForRow(row);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
	}

	private void locatePrintingQueueItemForRow(@NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Order_MFGWarehouse_Report report = row.getAsIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_C_Order_MFGWarehouse_Report_ID)
				.lookupNotNullIn(reportTable);
		final TableRecordReference reportRef = TableRecordReference.of(report);

		// The doc-outbound work package that turns a Processed report into an AD_Archive + C_Printing_Queue
		// item runs on a background thread pool (DocOutboundWorkpackageProcessor) even with
		// SKIP_WP_PROCESSOR_FOR_AUTOMATION=false -- that flag only decides whether the processor, once
		// invoked, does the archiving at all or short-circuits; it never makes it run on the calling
		// thread. Wait for it to land before reading its result (module rule: never read/assert an async
		// result before it lands).
		StepDefUtil.tryAndWait(30, 500, () -> queryPrintingQueueItem(reportRef).isPresent());

		final I_C_Printing_Queue queueItem = queryPrintingQueueItem(reportRef)
				.orElseThrow(() -> new AdempiereException("No C_Printing_Queue item found for report")
						.appendParametersToMessage()
						.setParameter("report", report));

		row.getAsIdentifier().putOrReplace(queueItemTable, queueItem);
	}

	private Optional<I_C_Printing_Queue> queryPrintingQueueItem(@NonNull final TableRecordReference reportRef)
	{
		final IQuery<I_AD_Archive> archivesOfReport = queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, reportRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, reportRef.getRecord_ID())
				.create();

		return queryBL.createQueryBuilder(I_C_Printing_Queue.class)
				.addInSubQueryFilter(I_C_Printing_Queue.COLUMNNAME_AD_Archive_ID, I_AD_Archive.COLUMNNAME_AD_Archive_ID, archivesOfReport)
				.create()
				.firstOptional(I_C_Printing_Queue.class);
	}

	/**
	 * Asserts that a previously-located {@code C_Printing_Queue} item carries the given {@code C_DocType_ID} --
	 * the routing key {@link IPrintingQueueBL#createPrinterRoutingsQueryForItem} reads to build the
	 * {@link PrinterRoutingsQuery}. Set generically by {@code DocumentPrintingQueueHandler} straight off the
	 * archived record's own {@code C_DocType_ID} column, the same mechanism proven for the report itself in
	 * "the order identified by ... has exactly the following C_Order_MFGWarehouse_Reports".
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Printing_Queue_ID</b> — (required, identifier-ref) a queue item located via
	 *       "C_Printing_Queue item is located:"<br>
	 *   <b>C_DocType_ID</b> — (required, identifier-ref) expected {@code C_DocType_ID} of that queue item<br>
	 * @cucumber.depends StepDefData: C_Printing_Queue_StepDefData, C_DocType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue resolves C_DocType:
	 *   | C_Printing_Queue_ID | C_DocType_ID      |
	 *   | warehouseQueue3     | docTypeProduktion |
	 * </pre>
	 */
	@Then("C_Printing_Queue resolves C_DocType:")
	public void assert_resolves_doc_type(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Printing_Queue queueItem = row.getAsIdentifier(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID).lookupNotNullIn(queueItemTable);
			final DocTypeId expectedDocTypeId = row.getAsIdentifier(I_C_Printing_Queue.COLUMNNAME_C_DocType_ID).lookupIdIn(docTypeTable);

			assertThat(DocTypeId.ofRepoIdOrNull(queueItem.getC_DocType_ID()))
					.as("%s of %s", I_C_Printing_Queue.COLUMNNAME_C_DocType_ID, queueItem)
					.isEqualTo(expectedDocTypeId);
		});
	}

	/**
	 * Asserts which {@code AD_PrinterRouting} a previously-located {@code C_Printing_Queue} item resolves to, via
	 * the exact production call {@link de.metas.printing.printingdata.PrintingDataFactory} makes:
	 * {@link IPrintingQueueBL#createPrinterRoutingsQueryForItem} then {@link IPrinterRoutingDAO#fetchPrinterRoutings}.
	 * The first entry of the returned (already-ordered) list is the routing production would use -- proves the
	 * doctype-specific routing wins over a catch-all routing (every dimension null), which the DAO also matches.
	 * <p>
	 * Stops at routing resolution, deliberately: the final hop to a physical printer via {@code AD_Printer_Matching}
	 * needs a real printer and is out of scope here.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Printing_Queue_ID</b> — (required, identifier-ref) a queue item located via
	 *       "C_Printing_Queue item is located:"<br>
	 *   <b>AD_PrinterRouting_ID</b> — (required, identifier-ref) the routing fixture created via
	 *       "metasfresh contains AD_PrinterRouting:" expected to win the resolution<br>
	 * @cucumber.depends StepDefData: C_Printing_Queue_StepDefData, AD_PrinterRouting_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue resolves AD_PrinterRouting:
	 *   | C_Printing_Queue_ID | AD_PrinterRouting_ID |
	 *   | warehouseQueue3     | routingProduktion     |
	 * </pre>
	 */
	@Then("C_Printing_Queue resolves AD_PrinterRouting:")
	public void assert_resolves_printer_routing(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Printing_Queue queueItem = row.getAsIdentifier(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID).lookupNotNullIn(queueItemTable);
			final I_AD_PrinterRouting expectedRouting = row.getAsIdentifier(I_AD_PrinterRouting.COLUMNNAME_AD_PrinterRouting_ID).lookupNotNullIn(printerRoutingTable);

			final PrinterRoutingsQuery query = printingQueueBL.createPrinterRoutingsQueryForItem(queueItem);
			final List<I_AD_PrinterRouting> matchingRoutings = printerRoutingDAO.fetchPrinterRoutings(query);

			assertThat(matchingRoutings)
					.as("AD_PrinterRouting candidates for %s (query=%s)", queueItem, query)
					.isNotEmpty();

			final I_AD_PrinterRouting winningRouting = matchingRoutings.get(0);
			assertThat(winningRouting.getAD_PrinterRouting_ID())
					.as("Winning %s for %s must be the doctype-specific routing, not the catch-all", I_AD_PrinterRouting.COLUMNNAME_AD_PrinterRouting_ID, queueItem)
					.isEqualTo(expectedRouting.getAD_PrinterRouting_ID());
		});
	}

	/**
	 * Asserts the {@code IsActive} flag of a previously-located {@code C_Printing_Queue} item -- {@code false}
	 * is how {@code OrderCheckupPrintingQueueHandler} cancels a print job when the underlying report has no
	 * responsible user, with no error anywhere else. The regression guard for the accepted risk: an
	 * unconfigured manufacturing routing silently loses the Packzettel print job.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Printing_Queue_ID</b> — (required, identifier-ref) a queue item located via
	 *       "C_Printing_Queue item is located:"<br>
	 *   <b>IsActive</b> — (required) expected {@code IsActive}<br>
	 * @cucumber.depends StepDefData: C_Printing_Queue_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue has IsActive:
	 *   | C_Printing_Queue_ID | IsActive |
	 *   | warehouseQueue4     | false    |
	 * </pre>
	 */
	@Then("C_Printing_Queue has IsActive:")
	public void assert_is_active(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Printing_Queue queueItem = row.getAsIdentifier(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID).lookupNotNullIn(queueItemTable);
			final boolean expectedIsActive = row.getAsBoolean(I_C_Printing_Queue.COLUMNNAME_IsActive);

			assertThat(queueItem.isActive())
					.as("%s of %s", I_C_Printing_Queue.COLUMNNAME_IsActive, queueItem)
					.isEqualTo(expectedIsActive);
		});
	}

	/**
	 * Asserts that {@link IOrderCheckupBL#getNumberOfCopies} for a previously-located {@code C_Printing_Queue}
	 * item equals the LIVE value of the given sys config -- read independently via {@link ISysConfigBL} at
	 * assertion time, never a hardcoded literal. Each row names the sys config key its OWN report's kind must
	 * route to (Warehouse-kind reports to the barcode-sheet copies count, Plant-kind reports to the plain
	 * one -- {@code OrderCheckupBL#getNumberOfCopies}), so a swapped or ignored per-kind branch shows up as a
	 * mismatch against distinct, non-default sys config values set earlier in the scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Printing_Queue_ID</b> — (required, identifier-ref) a queue item located via
	 *       "C_Printing_Queue item is located:"<br>
	 *   <b>SysConfigName</b> — (required) the {@code AD_SysConfig.Name} this queue item's report kind must
	 *       resolve its copies count from<br>
	 * @cucumber.depends StepDefData: C_Printing_Queue_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Printing_Queue resolves number of copies from sys config:
	 *   | C_Printing_Queue_ID | SysConfigName                              |
	 *   | warehouseQueue6     | de.metas.fresh.ordercheckup_barcode.Copies |
	 * </pre>
	 */
	@Then("C_Printing_Queue resolves number of copies from sys config:")
	public void assert_resolves_number_of_copies(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Printing_Queue queueItem = row.getAsIdentifier(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID).lookupNotNullIn(queueItemTable);
			final String sysConfigName = row.getAsString("SysConfigName");

			final de.metas.document.archive.model.I_AD_Archive printOut = InterfaceWrapperHelper.load(
					queueItem.getAD_Archive_ID(), de.metas.document.archive.model.I_AD_Archive.class);

			final int actualCopies = orderCheckupBL.getNumberOfCopies(queueItem, printOut);
			final int expectedCopies = sysConfigBL.getIntValue(sysConfigName, 1, queueItem.getAD_Client_ID(), queueItem.getAD_Org_ID());

			assertThat(actualCopies)
					.as("Number of copies for %s must equal the live sys config %s", queueItem, sysConfigName)
					.isEqualTo(expectedCopies);
		});
	}
}
