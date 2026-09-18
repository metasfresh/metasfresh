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

package de.metas.cucumber.stepdefs.ordercheckup;

import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.fresh.ordercheckup.IOrderCheckupDAO;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import org.assertj.core.api.SoftAssertions;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code C_Order_MFGWarehouse_Report} ("Bestellkontrolle") records — locating a report for a
 * sales order, asserting how many are currently active and inactive, asserting a report's
 * {@code OrderCheckupGeneration}, which order lines its report lines reference, and whether a doc-outbound work
 * package was enqueued for it.
 */
public class C_Order_MFGWarehouse_Report_StepDef
{
	/** Seeded {@code C_Queue_PackageProcessor.InternalName} for the report-printing enqueue path (see {@code AbstractDocOutboundProducer#createDocOutbound}). */
	private static final String DOC_OUTBOUND_PACKAGE_PROCESSOR_INTERNAL_NAME = "DocOutboundWorkpackageProcessor";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderCheckupDAO orderCheckupDAO = Services.get(IOrderCheckupDAO.class);
	private final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);

	@NonNull private final C_Order_MFGWarehouse_Report_StepDefData reportTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final S_Resource_StepDefData plantTable;

	public C_Order_MFGWarehouse_Report_StepDef(
			@NonNull final C_Order_MFGWarehouse_Report_StepDefData reportTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final S_Resource_StepDefData plantTable)
	{
		this.reportTable = reportTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.warehouseTable = warehouseTable;
		this.plantTable = plantTable;
	}

	/**
	 * Asserts the COMPLETE set of {@code C_Order_MFGWarehouse_Report} records the given order currently holds:
	 * the given rows and nothing else — a record count, and each row's {@code IsActive}/{@code Processed}.
	 * <p>
	 * Each expected row is paired to the record carrying THAT row's {@code DocumentType}/{@code M_Warehouse_ID}/
	 * {@code PP_Plant_ID} (and {@code OrderCheckupGeneration}, when given, to disambiguate several generations
	 * sharing the same document type/warehouse/plant), never to the record at the same position — so the order
	 * the rows are written in carries no meaning. A record already claimed by an earlier row cannot satisfy a
	 * second one.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DocumentType</b> — (optional) {@code C_Order_MFGWarehouse_Report.DocumentType} — part of the match<br>
	 *   <b>M_Warehouse_ID</b> — (optional, identifier-ref) warehouse report discriminator — part of the match<br>
	 *   <b>PP_Plant_ID</b> — (optional, identifier-ref) plant report discriminator — part of the match<br>
	 *   <b>OrderCheckupGeneration</b> — (optional) generation number — part of the match when given<br>
	 *   <b>IsActive</b> — (optional) expected {@code IsActive}, asserted on the matched record<br>
	 *   <b>Processed</b> — (optional) expected {@code Processed}, asserted on the matched record — {@code true}
	 *       once the record's own doc-outbound enqueue has fired for it (once ever, never reset by a later
	 *       restore); this is the field the print trigger ({@code DocOutboundProducerValidator#isJustProcessed})
	 *       actually keys on, so asserting it directly is stronger evidence of "restored, not rebuilt" than any
	 *       enqueue count<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, M_Warehouse_StepDefData, S_Resource_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the order identified by order has exactly the following C_Order_MFGWarehouse_Reports
	 *   | DocumentType | M_Warehouse_ID | PP_Plant_ID | IsActive | Processed |
	 *   | WH           | warehouse      | plant       | false    | true      |
	 *   | PL           |                | plant       | false    | true      |
	 * </pre>
	 */
	@Then("^the order identified by (.*) has exactly the following C_Order_MFGWarehouse_Reports$")
	public void assert_holds_exactly(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final List<I_C_Order_MFGWarehouse_Report> reports = orderCheckupDAO.retrieveAllReports(order);
		final DataTableRows rows = DataTableRows.of(dataTable);

		assertThat(reports).as("C_Order_MFGWarehouse_Report records of order %s", orderIdentifier).hasSize(rows.size());

		final Set<Integer> claimedIds = new HashSet<>();
		rows.forEach(row -> assert_report_of_expected_row(row, reports, claimedIds, orderIdentifier));
	}

	private void assert_report_of_expected_row(
			@NonNull final DataTableRow row,
			@NonNull final List<I_C_Order_MFGWarehouse_Report> reports,
			@NonNull final Set<Integer> claimedIds,
			@NonNull final String orderIdentifier)
	{
		final Optional<String> documentType = row.getAsOptionalString(I_C_Order_MFGWarehouse_Report.COLUMNNAME_DocumentType);
		final Optional<WarehouseId> warehouseId = row.getAsOptionalIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_M_Warehouse_ID)
				.map(identifier -> identifier.lookupIdIn(warehouseTable));
		final Optional<ResourceId> plantId = row.getAsOptionalIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_PP_Plant_ID)
				.map(identifier -> identifier.lookupIdIn(plantTable));
		final Optional<Integer> generation = row.getAsOptionalInt(I_C_Order_MFGWarehouse_Report.COLUMNNAME_OrderCheckupGeneration);

		final List<I_C_Order_MFGWarehouse_Report> matching = reports.stream()
				.filter(report -> !claimedIds.contains(report.getC_Order_MFGWarehouse_Report_ID()))
				.filter(report -> documentType.map(expected -> expected.equals(report.getDocumentType())).orElse(true))
				.filter(report -> warehouseId.map(expected -> Objects.equals(expected, WarehouseId.ofRepoIdOrNull(report.getM_Warehouse_ID()))).orElse(true))
				.filter(report -> plantId.map(expected -> Objects.equals(expected, ResourceId.ofRepoIdOrNull(report.getPP_Plant_ID()))).orElse(true))
				.filter(report -> generation.map(expected -> expected == report.getOrderCheckupGeneration()).orElse(true))
				.collect(Collectors.toList());

		if (matching.size() != 1)
		{
			final List<Integer> unclaimedIds = reports.stream()
					.map(I_C_Order_MFGWarehouse_Report::getC_Order_MFGWarehouse_Report_ID)
					.filter(id -> !claimedIds.contains(id))
					.collect(Collectors.toList());

			throw new AdempiereException("Expected exactly one unclaimed C_Order_MFGWarehouse_Report matching this row")
					.appendParametersToMessage()
					.setParameter("C_Order_ID.Identifier", orderIdentifier)
					.setParameter("DocumentType", documentType.orElse(null))
					.setParameter("M_Warehouse_ID", warehouseId.orElse(null))
					.setParameter("PP_Plant_ID", plantId.orElse(null))
					.setParameter("OrderCheckupGeneration", generation.orElse(null))
					.setParameter("MatchCount", matching.size())
					.setParameter("UnclaimedC_Order_MFGWarehouse_Report_IDs", unclaimedIds);
		}

		final I_C_Order_MFGWarehouse_Report report = matching.get(0);
		claimedIds.add(report.getC_Order_MFGWarehouse_Report_ID());

		final SoftAssertions softly = new SoftAssertions();
		row.getAsOptionalBoolean(I_C_Order_MFGWarehouse_Report.COLUMNNAME_IsActive)
				.ifPresent(expected -> softly.assertThat(report.isActive()).as("%s of %s", I_C_Order_MFGWarehouse_Report.COLUMNNAME_IsActive, report).isEqualTo(expected));
		row.getAsOptionalBoolean(I_C_Order_MFGWarehouse_Report.COLUMNNAME_Processed)
				.ifPresent(expected -> softly.assertThat(report.isProcessed()).as("%s of %s", I_C_Order_MFGWarehouse_Report.COLUMNNAME_Processed, report).isEqualTo(expected));
		softly.assertAll();
	}

	/**
	 * Locates one {@code C_Order_MFGWarehouse_Report} for the given order, discriminated by document type,
	 * warehouse and/or plant (the same discriminators {@code OrderCheckupBL} uses to build the report set), active
	 * state, and — optionally — its {@code OrderCheckupGeneration}. The generation is needed as a filter, not only
	 * a post-hoc assertion: several *inactive* generations can share the same document type/warehouse/plant (e.g.
	 * after several reactivate-and-complete cycles), so only the generation number can pick a specific one of them.
	 * The located record is stored under {@code Identifier} for later reference (e.g. asserting its doc-outbound
	 * enqueue status).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>C_Order_ID</b> — (required, identifier-ref) the sales order the report belongs to<br>
	 *   <b>DocumentType</b> — (required) {@code C_Order_MFGWarehouse_Report.DocumentType}<br>
	 *   <b>M_Warehouse_ID</b> — (optional, identifier-ref) warehouse report discriminator<br>
	 *   <b>PP_Plant_ID</b> — (optional, identifier-ref) plant report discriminator<br>
	 *   <b>IsActive</b> — (optional, default {@code true}) whether the located record must be active<br>
	 *   <b>OrderCheckupGeneration</b> — (optional) when given, filters on this exact generation number
	 *       (disambiguates among several inactive generations); the located record's generation is re-confirmed
	 *       against it regardless<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, M_Warehouse_StepDefData, S_Resource_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And C_Order_MFGWarehouse_Report is located:
	 *   | Identifier   | C_Order_ID | DocumentType | M_Warehouse_ID | OrderCheckupGeneration |
	 *   | warehouseRpt | order      | S            | warehouse      | 2                      |
	 * </pre>
	 */
	@And("C_Order_MFGWarehouse_Report is located:")
	public void locate_report(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::locate_report);
	}

	private void locate_report(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier("C_Order_ID").lookupNotNullIn(orderTable);
		final String documentType = row.getAsString("DocumentType");
		final WarehouseId warehouseId = row.getAsOptionalIdentifier("M_Warehouse_ID").map(id -> id.lookupIdIn(warehouseTable)).orElse(null);
		final ResourceId plantId = row.getAsOptionalIdentifier("PP_Plant_ID").map(id -> id.lookupIdIn(plantTable)).orElse(null);
		final boolean expectedIsActive = row.getAsOptionalBoolean("IsActive").orElse(true);
		final Optional<Integer> expectedGeneration = row.getAsOptionalInt("OrderCheckupGeneration");

		final List<I_C_Order_MFGWarehouse_Report> matches = orderCheckupDAO.retrieveAllReports(order).stream()
				.filter(report -> documentType.equals(report.getDocumentType()))
				.filter(report -> Objects.equals(WarehouseId.ofRepoIdOrNull(report.getM_Warehouse_ID()), warehouseId))
				.filter(report -> Objects.equals(ResourceId.ofRepoIdOrNull(report.getPP_Plant_ID()), plantId))
				.filter(report -> report.isActive() == expectedIsActive)
				.filter(report -> expectedGeneration.map(generation -> report.getOrderCheckupGeneration() == generation).orElse(true))
				.collect(Collectors.toList());

		if (matches.size() != 1)
		{
			throw new AdempiereException("Expected exactly one matching C_Order_MFGWarehouse_Report")
					.appendParametersToMessage()
					.setParameter("C_Order_ID", order.getC_Order_ID())
					.setParameter("DocumentType", documentType)
					.setParameter("M_Warehouse_ID", warehouseId)
					.setParameter("PP_Plant_ID", plantId)
					.setParameter("IsActive", expectedIsActive)
					.setParameter("OrderCheckupGeneration", expectedGeneration.orElse(null))
					.setParameter("MatchCount", matches.size());
		}

		final I_C_Order_MFGWarehouse_Report report = matches.get(0);

		// re-confirm the generation even when it was already used as a filter — a no-op in that case, but a real
		// assertion whenever the generation was not part of the filter criteria above.
		expectedGeneration.ifPresent(generation -> assertThat(report.getOrderCheckupGeneration())
				.as("OrderCheckupGeneration of %s", report)
				.isEqualTo(generation));

		row.getAsIdentifier().putOrReplace(reportTable, report);
	}

	/**
	 * Asserts whether a doc-outbound work package (the async enqueue that eventually renders and prints the
	 * report — see {@code AbstractDocOutboundProducer#createDocOutbound}) was enqueued for a previously-located
	 * {@code C_Order_MFGWarehouse_Report}. Looked up directly on {@code C_Queue_Element}/{@code C_Queue_WorkPackage}
	 * by the report's own table+record — the enqueue is synchronous even though the processing itself is async
	 * (no wait needed to observe whether the work package was created).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_MFGWarehouse_Report_ID</b> — (required, identifier-ref) a report located via
	 *       "C_Order_MFGWarehouse_Report is located:"<br>
	 *   <b>IsEnqueued</b> — (required) whether a doc-outbound work package was enqueued for that report<br>
	 * @cucumber.depends StepDefData: C_Order_MFGWarehouse_Report_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Order_MFGWarehouse_Report doc-outbound enqueue status is:
	 *   | C_Order_MFGWarehouse_Report_ID | IsEnqueued |
	 *   | warehouseRpt                   | false      |
	 * </pre>
	 */
	@Then("C_Order_MFGWarehouse_Report doc-outbound enqueue status is:")
	public void assert_doc_outbound_enqueue_status(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order_MFGWarehouse_Report report = row.getAsIdentifier("C_Order_MFGWarehouse_Report_ID").lookupNotNullIn(reportTable);
			final boolean expectedIsEnqueued = row.getAsBoolean("IsEnqueued");

			final boolean actualIsEnqueued = isDocOutboundWorkPackageEnqueuedFor(report);

			assertThat(actualIsEnqueued).as("Doc-outbound work package enqueued for %s", report).isEqualTo(expectedIsEnqueued);
		});
	}

	private boolean isDocOutboundWorkPackageEnqueuedFor(@NonNull final I_C_Order_MFGWarehouse_Report report)
	{
		return countDocOutboundWorkPackagesFor(report) > 0;
	}

	/**
	 * Counts the doc-outbound work packages ever enqueued for one {@code C_Order_MFGWarehouse_Report}. One such
	 * work package is created the moment the report's {@code Processed} flag first flips to {@code true} (see
	 * {@code AbstractDocOutboundProducer#createDocOutbound}); the underlying {@code C_Queue_Element} is never
	 * deleted afterward, so this count only ever grows -- it is the way to prove that a later action (e.g.
	 * reactivating the report) did NOT enqueue an additional one.
	 */
	private long countDocOutboundWorkPackagesFor(@NonNull final I_C_Order_MFGWarehouse_Report report)
	{
		final TableRecordReference reportReference = TableRecordReference.of(report);

		return queryBL.createQueryBuilder(I_C_Queue_Element.class)
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_AD_Table_ID, reportReference.getAD_Table_ID())
				.addEqualsFilter(I_C_Queue_Element.COLUMNNAME_Record_ID, reportReference.getRecord_ID())
				.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID, I_C_Queue_WorkPackage.class)
				.andCollect(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, I_C_Queue_PackageProcessor.class)
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_InternalName, DOC_OUTBOUND_PACKAGE_PROCESSOR_INTERNAL_NAME)
				.create()
				.count();
	}

	/**
	 * Asserts exactly which {@code C_OrderLine}s a previously-located {@code C_Order_MFGWarehouse_Report}'s report
	 * lines reference — one row per (report, order line) pair; several rows may share the same {@code Identifier}
	 * when a report has several lines. Used to pin that a restored report still references the order line it was
	 * originally built from, even after that order line's quantity was changed by a later correction.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required, identifier-ref) a report located via
	 *       "C_Order_MFGWarehouse_Report is located:"<br>
	 *   <b>C_OrderLine_ID</b> — (required, identifier-ref) an order line the report's lines must reference<br>
	 * @cucumber.depends StepDefData: C_Order_MFGWarehouse_Report_StepDefData, C_OrderLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Order_MFGWarehouse_Report references order lines:
	 *   | Identifier   | C_OrderLine_ID |
	 *   | warehouseRpt | orderLine      |
	 * </pre>
	 */
	@Then("C_Order_MFGWarehouse_Report references order lines:")
	public void assert_report_references_order_lines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).groupBy("Identifier").forEach((identifier, rows) -> {
			final I_C_Order_MFGWarehouse_Report report = reportTable.get(identifier);
			assertThat(report).as("C_Order_MFGWarehouse_Report for Identifier=%s", identifier).isNotNull();

			final Set<Integer> actualOrderLineIds = orderCheckupDAO.retrieveAllReportLines(report).stream()
					.map(I_C_Order_MFGWarehouse_ReportLine::getC_OrderLine_ID)
					.collect(Collectors.toSet());

			final Set<Integer> expectedOrderLineIds = rows.stream()
					.map(row -> row.getAsIdentifier("C_OrderLine_ID").lookupNotNullIn(orderLineTable).getC_OrderLine_ID())
					.collect(Collectors.toSet());

			assertThat(actualOrderLineIds).as("Order lines referenced by %s", report).isEqualTo(expectedOrderLineIds);
		});
	}

	/**
	 * Asserts the total number of doc-outbound work packages ever enqueued across ALL {@code
	 * C_Order_MFGWarehouse_Report} records (active or not) of the given order. Because the underlying {@code
	 * C_Queue_Element} is never deleted once created (see {@link #countDocOutboundWorkPackagesFor}), this count can
	 * only grow when a *new* report is built (its {@code Processed} flag flipping false-&gt;true for the first
	 * time) -- reactivating an existing report's header (as {@code restoreMostRecentGeneration} does) never
	 * touches {@code Processed} and so never changes it. Asserting the same count before and after such an action
	 * is how a scenario proves no work package was (re-)enqueued by it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_ID</b> — (required, identifier-ref) the sales order whose reports' work packages are counted<br>
	 *   <b>WorkPackageCount</b> — (required) expected total count<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Order_MFGWarehouse_Report doc-outbound work package count is:
	 *   | C_Order_ID | WorkPackageCount |
	 *   | order      | 4                 |
	 * </pre>
	 */
	@Then("C_Order_MFGWarehouse_Report doc-outbound work package count is:")
	public void assert_doc_outbound_work_package_count(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order order = row.getAsIdentifier("C_Order_ID").lookupNotNullIn(orderTable);
			final int expectedCount = row.getAsInt("WorkPackageCount");

			final long actualCount = orderCheckupDAO.retrieveAllReports(order).stream()
					.mapToLong(this::countDocOutboundWorkPackagesFor)
					.sum();

			assertThat(actualCount).as("Doc-outbound work package count for order %s", order).isEqualTo(expectedCount);
		});
	}

	/**
	 * Voids (deactivates) every {@code C_Order_MFGWarehouse_Report} header currently held for the given order --
	 * i.e. calls {@code IOrderCheckupBL#voidReports}, the same production method
	 * {@code generateReportsIfEligible} runs before rebuilding a fresh set of reports. Used to bring an order into
	 * the "several deactivated generations" starting state a restore scenario needs, without going through a full
	 * completion cycle.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the Bestellkontrolle reports for the order identified by order are voided
	 * </pre>
	 */
	@And("^the Bestellkontrolle reports for the order identified by (.*) are voided$")
	public void void_reports(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		orderCheckupBL.voidReports(order);
	}

	/**
	 * Calls {@code IOrderCheckupBL#restoreMostRecentGeneration} for the given order -- reactivates the
	 * {@code C_Order_MFGWarehouse_Report} headers of its highest {@code OrderCheckupGeneration}, leaving any older
	 * generation as-is.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the most recent Bestellkontrolle generation for the order identified by order is restored
	 * </pre>
	 */
	@And("^the most recent Bestellkontrolle generation for the order identified by (.*) is restored$")
	public void restore_most_recent_generation(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		orderCheckupBL.restoreMostRecentGeneration(order);
	}
}
