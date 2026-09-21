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
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.document.archive.config.DocOutboundConfig;
import de.metas.document.archive.config.DocOutboundConfigService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.IOrderCheckupDAO;
import de.metas.fresh.ordercheckup.OrderCheckupDocumentType;
import de.metas.fresh.ordercheckup.OrderCheckupReportId;
import de.metas.order.OrderLineId;
import de.metas.product.ResourceId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code C_Order_MFGWarehouse_Report} ("Bestellkontrolle") records — locating a report for a
 * sales order, asserting the complete set of reports an order holds, which order lines a report's lines
 * reference, and whether a doc-outbound work package was enqueued for it.
 */
@RequiredArgsConstructor
public class C_Order_MFGWarehouse_Report_StepDef
{
	/** Seeded {@code C_Queue_PackageProcessor.InternalName} for the report-printing enqueue path (see {@code AbstractDocOutboundProducer#createDocOutbound}). */
	private static final String DOC_OUTBOUND_PACKAGE_PROCESSOR_INTERNAL_NAME = "DocOutboundWorkpackageProcessor";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderCheckupDAO orderCheckupDAO = Services.get(IOrderCheckupDAO.class);
	private final DocOutboundConfigService docOutboundConfigService = SpringContextHolder.instance.getBean(DocOutboundConfigService.class);

	@NonNull private final C_Order_MFGWarehouse_Report_StepDefData reportTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final S_Resource_StepDefData plantTable;
	@NonNull private final C_DocType_StepDefData docTypeTable;

	/**
	 * Asserts the COMPLETE set of {@code C_Order_MFGWarehouse_Report} records the given order currently holds:
	 * the given rows and nothing else — a record count, and each row's {@code IsActive}/{@code Processed}.
	 * <p>
	 * Each expected row is paired to the record carrying THAT row's {@code DocumentType}/{@code M_Warehouse_ID}/
	 * {@code PP_Plant_ID}/{@code IsActive}, never to the record at the same position — so the order the rows are
	 * written in carries no meaning. A record already claimed by an earlier row cannot satisfy a second one.
	 * <p>
	 * {@code IsActive} is part of the match (rather than only asserted afterwards) because a rebuild leaves the
	 * deactivated predecessor behind: two records can share document type, warehouse and plant, and only their
	 * active state tells them apart. It is asserted just as strictly this way — a record in the wrong active state
	 * leaves its expected row with no match, which fails the step.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DocumentType</b> — (optional) {@code C_Order_MFGWarehouse_Report.DocumentType} — part of the match<br>
	 *   <b>M_Warehouse_ID</b> — (optional, identifier-ref) warehouse report discriminator — part of the match<br>
	 *   <b>PP_Plant_ID</b> — (optional, identifier-ref) plant report discriminator — part of the match<br>
	 *   <b>IsActive</b> — (optional) expected {@code IsActive} — part of the match<br>
	 *   <b>Processed</b> — (optional) expected {@code Processed}, asserted on the matched record — set once and
	 *       never reset, so it stays true across a reactivate<br>
	 *   <b>C_DocType_ID</b> — (optional, identifier-ref) expected {@code C_DocType_ID}, asserted on the matched
	 *       record like {@code Processed} — the record loaded via "load C_DocType:"<br>
	 * @cucumber.depends StepDefData: C_Order_StepDefData, M_Warehouse_StepDefData, S_Resource_StepDefData,
	 *     C_DocType_StepDefData
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

		final Map<OrderCheckupReportId, OrderCheckupDocumentType> documentTypesByReportId = reports.stream()
				.collect(Collectors.toMap(report -> OrderCheckupReportId.ofRepoId(report.getC_Order_MFGWarehouse_Report_ID()),
						report -> OrderCheckupDocumentType.ofCode(report.getDocumentType())));

		final SoftAssertions softly = new SoftAssertions();
		final Set<OrderCheckupReportId> claimedIds = new HashSet<>();
		rows.forEach(row -> assert_report_of_expected_row(row, reports, documentTypesByReportId, claimedIds, orderIdentifier, softly));
		softly.assertAll();
	}

	private void assert_report_of_expected_row(
			@NonNull final DataTableRow row,
			@NonNull final List<I_C_Order_MFGWarehouse_Report> reports,
			@NonNull final Map<OrderCheckupReportId, OrderCheckupDocumentType> documentTypesByReportId,
			@NonNull final Set<OrderCheckupReportId> claimedIds,
			@NonNull final String orderIdentifier,
			@NonNull final SoftAssertions softly)
	{
		final Optional<OrderCheckupDocumentType> documentType = row.getAsOptionalString(I_C_Order_MFGWarehouse_Report.COLUMNNAME_DocumentType)
				.map(OrderCheckupDocumentType::ofCode);
		final Optional<WarehouseId> warehouseId = row.getAsOptionalIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_M_Warehouse_ID)
				.map(identifier -> identifier.lookupIdIn(warehouseTable));
		final Optional<ResourceId> plantId = row.getAsOptionalIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_PP_Plant_ID)
				.map(identifier -> identifier.lookupIdIn(plantTable));
		final OptionalBoolean isActive = row.getAsOptionalBoolean(I_C_Order_MFGWarehouse_Report.COLUMNNAME_IsActive);

		final List<I_C_Order_MFGWarehouse_Report> matching = reports.stream()
				.filter(report -> !claimedIds.contains(OrderCheckupReportId.ofRepoId(report.getC_Order_MFGWarehouse_Report_ID())))
				.filter(report -> documentType.map(expected -> expected == documentTypesByReportId.get(OrderCheckupReportId.ofRepoId(report.getC_Order_MFGWarehouse_Report_ID()))).orElse(true))
				.filter(report -> warehouseId.map(expected -> WarehouseId.equals(expected, WarehouseId.ofRepoIdOrNull(report.getM_Warehouse_ID()))).orElse(true))
				.filter(report -> plantId.map(expected -> ResourceId.equals(expected, ResourceId.ofRepoIdOrNull(report.getPP_Plant_ID()))).orElse(true))
				.filter(report -> isActive.map(expected -> expected == report.isActive()).orElse(true))
				.collect(Collectors.toList());

		final List<String> unclaimedDescriptions = reports.stream()
				.filter(report -> !claimedIds.contains(OrderCheckupReportId.ofRepoId(report.getC_Order_MFGWarehouse_Report_ID())))
				.map(report -> String.format(
						"C_Order_MFGWarehouse_Report_ID=%s/DocumentType=%s/M_Warehouse_ID=%s/PP_Plant_ID=%s/IsActive=%s",
						report.getC_Order_MFGWarehouse_Report_ID(), report.getDocumentType(), report.getM_Warehouse_ID(), report.getPP_Plant_ID(), report.isActive()))
				.collect(Collectors.toList());

		softly.assertThat(matching)
				.as("unclaimed C_Order_MFGWarehouse_Report matching DocumentType=%s/M_Warehouse_ID=%s/PP_Plant_ID=%s/IsActive=%s of order %s; unclaimed candidates: %s",
						documentType.orElse(null), warehouseId.orElse(null), plantId.orElse(null), isActive.toBooleanOrNull(), orderIdentifier, unclaimedDescriptions)
				.hasSize(1);
		if (matching.size() != 1)
		{
			return;
		}

		final I_C_Order_MFGWarehouse_Report report = matching.get(0);
		claimedIds.add(OrderCheckupReportId.ofRepoId(report.getC_Order_MFGWarehouse_Report_ID()));

		row.getAsOptionalBoolean(I_C_Order_MFGWarehouse_Report.COLUMNNAME_Processed)
				.ifPresent(expected -> softly.assertThat(report.isProcessed()).as("%s of %s", I_C_Order_MFGWarehouse_Report.COLUMNNAME_Processed, report).isEqualTo(expected));

		row.getAsOptionalIdentifier(I_C_Order_MFGWarehouse_Report.COLUMNNAME_C_DocType_ID)
				.map(identifier -> identifier.lookupIdIn(docTypeTable))
				.ifPresent(expected -> softly.assertThat(DocTypeId.ofRepoIdOrNull(report.getC_DocType_ID()))
						.as("%s of %s", I_C_Order_MFGWarehouse_Report.COLUMNNAME_C_DocType_ID, report)
						.isEqualTo(expected));
	}

	/**
	 * Locates one {@code C_Order_MFGWarehouse_Report} for the given order, discriminated by document type,
	 * warehouse and/or plant (the same discriminators {@code OrderCheckupBL} uses to build the report set) plus its
	 * active state — which is what separates a freshly rebuilt report from the deactivated predecessor it replaced.
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
	 * @cucumber.depends StepDefData: C_Order_StepDefData, M_Warehouse_StepDefData, S_Resource_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And C_Order_MFGWarehouse_Report is located:
	 *   | Identifier   | C_Order_ID | DocumentType | M_Warehouse_ID | IsActive |
	 *   | warehouseRpt | order      | WH           | warehouse      | true     |
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
		final OrderCheckupDocumentType documentType = OrderCheckupDocumentType.ofCode(row.getAsString("DocumentType"));
		final WarehouseId warehouseId = row.getAsOptionalIdentifier("M_Warehouse_ID").map(id -> id.lookupIdIn(warehouseTable)).orElse(null);
		final ResourceId plantId = row.getAsOptionalIdentifier("PP_Plant_ID").map(id -> id.lookupIdIn(plantTable)).orElse(null);
		final boolean expectedIsActive = row.getAsOptionalBoolean("IsActive").orElse(true);

		final List<I_C_Order_MFGWarehouse_Report> matches = orderCheckupDAO.retrieveAllReports(order).stream()
				.filter(report -> documentType == OrderCheckupDocumentType.ofCode(report.getDocumentType()))
				.filter(report -> WarehouseId.equals(WarehouseId.ofRepoIdOrNull(report.getM_Warehouse_ID()), warehouseId))
				.filter(report -> ResourceId.equals(ResourceId.ofRepoIdOrNull(report.getPP_Plant_ID()), plantId))
				.filter(report -> report.isActive() == expectedIsActive)
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
					.setParameter("MatchCount", matches.size());
		}

		row.getAsIdentifier().putOrReplace(reportTable, matches.get(0));
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

			final Set<OrderLineId> actualOrderLineIds = orderCheckupDAO.retrieveAllReportLines(report).stream()
					.map(reportLine -> OrderLineId.ofRepoId(reportLine.getC_OrderLine_ID()))
					.collect(Collectors.toSet());

			final Set<OrderLineId> expectedOrderLineIds = rows.stream()
					.map(row -> OrderLineId.ofRepoId(row.getAsIdentifier("C_OrderLine_ID").lookupNotNullIn(orderLineTable).getC_OrderLine_ID()))
					.collect(Collectors.toSet());

			assertThat(actualOrderLineIds).as("Order lines referenced by %s", report).isEqualTo(expectedOrderLineIds);
		});
	}

	/**
	 * Asserts the total number of doc-outbound work packages ever enqueued across ALL {@code
	 * C_Order_MFGWarehouse_Report} records (active or not) of the given order. Because the underlying {@code
	 * C_Queue_Element} is never deleted once created (see {@link #countDocOutboundWorkPackagesFor}), this count can
	 * only grow when a *new* report is built (its {@code Processed} flag flipping false-&gt;true for the first
	 * time) -- deactivating a report and activating it again never touches {@code Processed} and so never changes
	 * it. Asserting the same count before and after such an action is how a scenario proves no work package was
	 * (re-)enqueued by it.
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
	 *   | order      | 4                |
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
	 * Asserts which {@code C_Doc_Outbound_Config} a previously-located {@code C_Order_MFGWarehouse_Report} resolves
	 * to via {@link DocOutboundConfigService#retrieveConfigForModel}, the same call the real outbound pipeline
	 * makes to decide which report/printer configuration applies to this record. Proves the resolution is
	 * per-kind: a Warehouse-kind record must resolve a different configuration than a Plant-kind record, and
	 * neither may resolve the generic table-wide fallback -- that silent fallback is the exact failure this
	 * feature exists to prevent.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Order_MFGWarehouse_Report_ID</b> — (required, identifier-ref) a report located via
	 *       "C_Order_MFGWarehouse_Report is located:"<br>
	 *   <b>C_Doc_Outbound_Config_ID</b> — (required) expected resolved {@code C_Doc_Outbound_Config_ID}<br>
	 *   <b>AD_PrintFormat_ID</b> — (optional) expected resolved {@code AD_PrintFormat_ID} of that configuration<br>
	 * @cucumber.depends StepDefData: C_Order_MFGWarehouse_Report_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then C_Order_MFGWarehouse_Report resolves C_Doc_Outbound_Config:
	 *   | C_Order_MFGWarehouse_Report_ID | C_Doc_Outbound_Config_ID |
	 *   | warehouseRpt                   | 540022                   |
	 * </pre>
	 */
	@Then("C_Order_MFGWarehouse_Report resolves C_Doc_Outbound_Config:")
	public void assert_resolves_doc_outbound_config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order_MFGWarehouse_Report report = row.getAsIdentifier("C_Order_MFGWarehouse_Report_ID").lookupNotNullIn(reportTable);
			final int expectedConfigId = row.getAsInt(I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID);

			final DocOutboundConfig config = docOutboundConfigService.retrieveConfigForModel(report);

			assertThat(config).as("Resolved C_Doc_Outbound_Config for %s", report).isNotNull();
			assertThat(config.getId().getRepoId())
					.as("Resolved %s for %s", I_C_Doc_Outbound_Config.COLUMNNAME_C_Doc_Outbound_Config_ID, report)
					.isEqualTo(expectedConfigId);

			row.getAsOptionalInt(I_C_Doc_Outbound_Config.COLUMNNAME_AD_PrintFormat_ID)
					.ifPresent(expectedPrintFormatId -> assertThat(config.getPrintFormatId().getRepoId())
							.as("Resolved %s for %s", I_C_Doc_Outbound_Config.COLUMNNAME_AD_PrintFormat_ID, report)
							.isEqualTo(expectedPrintFormatId));
		});
	}
}
