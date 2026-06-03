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

package de.metas.cucumber.stepdefs.distributionorder;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.inout.ShipmentScheduleId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import javax.annotation.Nullable;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DD_Order_StepDef
{
	private static final Logger logger = LogManager.getLogger(DD_Order_StepDef.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final S_Resource_StepDefData resourceTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;

	/**
	 * @cucumber.stepdef Creates DD_Order header records.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code Identifier} — step-internal identifier for cross-step reference</li>
	 *   <li>{@code M_Warehouse_ID.From} — identifier of the source warehouse</li>
	 *   <li>{@code M_Warehouse_ID.To} — identifier of the target warehouse</li>
	 *   <li>{@code M_Warehouse_ID.Transit} — identifier of the transit (in-transit) warehouse</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code C_BPartner_ID} — identifier of the business partner (defaults to org-linked BPartner)</li>
	 *   <li>{@code S_Resource_ID} — identifier of the plant (PP_Plant)</li>
	 *   <li>{@code C_DocType_ID.Name} — name of the doc type (defaults to first matching Distribution Order doc type)</li>
	 *   <li>{@code DatePromised} — promised date (defaults to system time); used as supply date by material dispo</li>
	 *   <li>{@code DateOrdered} — order date (defaults to system time)</li>
	 * </ul>
	 */
	@And("metasfresh contains DD_Orders:")
	public void metasfresh_contains_dd_orders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> {
					final OrgId orgId = Env.getOrgId();
					final BPartnerId bpartnerId = Optionals.firstPresentOfSuppliers(
									() -> row.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_C_BPartner_ID).map(bPartnerTable::getId),
									() -> bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
							)
							.orElse(null);

					final BPartnerLocationId bpartnerLocationId = bpartnerId != null
							? bpartnerDAO.retrieveBPartnerLocationId(BPartnerLocationQuery.builder().bpartnerId(bpartnerId).type(BPartnerLocationQuery.Type.SHIP_TO).build())
							: null;

					final WarehouseId fromWarehouseId = row.getAsIdentifier("M_Warehouse_ID.From").lookupNotNullIdIn(warehouseTable);
					final WarehouseId toWarehouseId = row.getAsIdentifier("M_Warehouse_ID.To").lookupNotNullIdIn(warehouseTable);
					final WarehouseId transitWarehouseId = row.getAsIdentifier("M_Warehouse_ID.Transit").lookupNotNullIdIn(warehouseTable);

					final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstanceOutOfTrx(I_DD_Order.class);
					ddOrder.setAD_Org_ID(orgId.getRepoId());
					ddOrder.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
					ddOrder.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
					ddOrder.setM_Warehouse_From_ID(fromWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_To_ID(toWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_ID(transitWarehouseId.getRepoId());
					ddOrder.setIsInDispute(false);
					ddOrder.setIsSOTrx(false);
					ddOrder.setIsInTransit(false);
					ddOrder.setDeliveryRule(X_DD_Order.DELIVERYRULE_Availability);

					final Timestamp defaultTimestamp = Timestamp.from(SystemTime.asInstant());
					ddOrder.setDatePromised(row.getAsOptionalString(I_DD_Order.COLUMNNAME_DatePromised)
							.map(Instant::parse)
							.map(Timestamp::from)
							.orElse(defaultTimestamp));
					ddOrder.setDateOrdered(row.getAsOptionalString(I_DD_Order.COLUMNNAME_DateOrdered)
							.map(Instant::parse)
							.map(Timestamp::from)
							.orElse(defaultTimestamp));

					row.getAsOptionalIdentifier("S_Resource_ID")
							.map(plantIdentifier -> resourceTable.getIdOptional(plantIdentifier).orElseGet(() -> plantIdentifier.getAsId(ResourceId.class)))
							.ifPresent(plantId -> ddOrder.setPP_Plant_ID(plantId.getRepoId()));

					final String docTypeName = row.getAsOptionalString(I_DD_Order.COLUMNNAME_C_DocType_ID + "." + I_C_DocType.COLUMNNAME_Name).orElse(null);
					final DocTypeId docTypeId = findDocTypeId(orgId, docTypeName);
					ddOrder.setC_DocType_ID(docTypeId.getRepoId());

					ddOrderService.save(ddOrder);

					row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
				});
	}

	private DocTypeId findDocTypeId(@NonNull final OrgId orgId, @Nullable final String docTypeName)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.DistributionOrder)
				.name(docTypeName)
				.clientAndOrgId(Env.getClientId(), orgId)
				.build());
	}

	/**
	 * @cucumber.stepdef Performs a document action on a DD_Order identified by its step-internal identifier.
	 * <p>
	 * Currently supported actions: {@code completed}.
	 * The DD_Order must have been previously created and registered via {@code metasfresh contains DD_Orders:}.
	 */
	@And("^the dd_order identified by (.*) is (completed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String actionStr)
	{
		final I_DD_Order order = ddOrderTable.get(orderIdentifier);

		final StepDefDocAction action = StepDefDocAction.valueOf(actionStr);
		if (action == StepDefDocAction.completed)
		{
			order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
			documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			throw new AdempiereException("Unhandled DD_Order action")
					.appendParametersToMessage()
					.setParameter("action", actionStr);
		}
	}

	/**
	 * @cucumber.stepdef Polls for DD_Orders until they match the expected values or the timeout is reached.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code Identifier} — step-internal identifier (must reference a previously created DD_Order)</li>
	 * </ul>
	 * Optional validation columns:
	 * <ul>
	 *   <li>{@code DocStatus} — expected document status (e.g. {@code Completed}, {@code Closed})</li>
	 *   <li>{@code Forward_PP_Order_ID} — expected forward PP_Order identifier</li>
	 *   <li>{@code Forward_PP_Order_BOMLine_ID} — expected forward PP_Order BOM line identifier</li>
	 *   <li>{@code C_Order_ID} — expected sales order identifier</li>
	 * </ul>
	 */
	@And("^after not more than (.*)s, following DD_Orders are found$")
	public void validateDDOrders(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> validateDDOrder(timeoutSec, row));
	}

	private void validateDDOrder(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_DD_Order ddOrder = StepDefUtil.tryAndWaitForItem(toSqlQuery(row))
				.validateUsingConsumer(record -> validateDDOrder(record, row))
				.maxWaitSeconds(timeoutSec)
				.execute();

		row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
	}

	private void validateDDOrder(final I_DD_Order actual, @NonNull final DataTableRow expected)
	{
		final SoftAssertions softly = new SoftAssertions();

		expected.getAsOptionalEnum("DocStatus", DocStatus.class)
				.ifPresent(expectedDocStatus -> {
					final DocStatus actualDocStatus = DocStatus.ofNullableCodeOrUnknown(actual.getDocStatus());
					softly.assertThat(actualDocStatus).as("DocStatus").isEqualTo(expectedDocStatus);
				});

		final StepDefDataIdentifier ppOrderIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_Forward_PP_Order_ID).orElse(null);
		if (ppOrderIdentifier != null)
		{
			final PPOrderId expectedPPOrderId = ppOrderIdentifier.lookupIdIn(ppOrderTable);
			final PPOrderId actualPPOrderId = PPOrderId.ofRepoIdOrNull(actual.getForward_PP_Order_ID());
			softly.assertThat(actualPPOrderId).as("Forward_PP_Order_ID").isEqualTo(expectedPPOrderId);
		}

		final StepDefDataIdentifier ppOrderBOMLineIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_Forward_PP_Order_BOMLine_ID).orElse(null);
		if (ppOrderBOMLineIdentifier != null)
		{
			final PPOrderBOMLineId expectedPPOrderBOMLineId = ppOrderBOMLineIdentifier.lookupIdIn(ppOrderBOMLineTable);
			final PPOrderBOMLineId actualPPOrderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(actual.getForward_PP_Order_BOMLine_ID());
			softly.assertThat(actualPPOrderBOMLineId).as("Forward_PP_Order_BOMLine_ID").isEqualTo(expectedPPOrderBOMLineId);
		}

		final StepDefDataIdentifier salesOrderIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_C_Order_ID).orElse(null);
		if (salesOrderIdentifier != null)
		{
			final OrderId expectedOrderId = salesOrderIdentifier.lookupIdIn(orderTable);
			final OrderId actualOrderId = OrderId.ofRepoIdOrNull(actual.getC_Order_ID());
			softly.assertThat(actualOrderId).as("C_Order_ID").isEqualTo(expectedOrderId);
		}

		softly.assertAll();
	}

	private IQuery<I_DD_Order> toSqlQuery(final DataTableRow row)
	{
		final DDOrderId ddOrderId = row.getAsIdentifier().lookupIdIn(ddOrderTable);
		return queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderId)
				.create();
	}

	/**
	 * @cucumber.stepdef Polls for the single live (DocStatus != Voided) DD_Order linked to a shipment schedule via
	 * {@code DD_Order.M_ShipmentSchedule_ID}, asserts exactly one is found, and validates header + line.
	 * <p>
	 * This is the assertion used by the DD_Order picking-reconcile flow, where exactly one Completed DD_Order
	 * is created per packing-warehouse shipment-schedule line. Both {@code DD_Order.M_ShipmentSchedule_ID} and
	 * {@code DD_OrderLine.M_ShipmentSchedule_ID} are asserted to reference the given schedule.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule the DD_Order must be linked to</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code Identifier} — stores the found DD_Order for later reference</li>
	 *   <li>{@code DocStatus} — expected header doc status (e.g. {@code Completed})</li>
	 *   <li>{@code M_Warehouse_From_ID} — expected source warehouse identifier (header + line)</li>
	 *   <li>{@code M_Warehouse_To_ID} — expected target warehouse identifier (header + line)</li>
	 *   <li>{@code QtyEntered} — expected line quantity</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 120s, the DD_Order linked to shipment schedule is found:
	 *   | M_ShipmentSchedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | QtyEntered |
	 *   | shipmentSchedule       | Completed | sourceWH            | packingWH         | 5          |
	 * </pre>
	 */
	@And("^after not more than (.*)s, the DD_Order linked to shipment schedule is found:$")
	public void validateDDOrderLinkedToSchedule(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> validateDDOrderLinkedToSchedule(timeoutSec, row));
	}

	private void validateDDOrderLinkedToSchedule(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final ShipmentScheduleId scheduleId = row.getAsIdentifier(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);

		// Validate header AND line inside the retry: during an async RECREATE (qty change) the old DD_Order is
		// transiently still live with the same header (schedule/warehouses/DocStatus) but the old qty. Binding on
		// header-only would grab that stale record (and collide with its already-assigned identifier). Folding the
		// single-line (qty) check into the supplier makes the poll wait for the fully-matching DD_Order before binding.
		final I_DD_Order ddOrder = StepDefUtil.tryAndWaitForItem(liveDDOrderForScheduleQuery(scheduleId))
				.validateUsingConsumer(record -> {
					validateDDOrderHeader(record, row);
					validateSingleLine(record, scheduleId, row);
				})
				.maxWaitSeconds(timeoutSec)
				.execute();

		row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
	}

	private IQuery<I_DD_Order> liveDDOrderForScheduleQuery(@NonNull final ShipmentScheduleId scheduleId)
	{
		return queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.create();
	}

	private void validateDDOrderHeader(@NonNull final I_DD_Order actual, @NonNull final DataTableRow expected)
	{
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(actual.getM_ShipmentSchedule_ID()).as("DD_Order.M_ShipmentSchedule_ID is set").isGreaterThan(0);

		expected.getAsOptionalEnum("DocStatus", DocStatus.class)
				.ifPresent(expectedDocStatus -> {
					final DocStatus actualDocStatus = DocStatus.ofNullableCodeOrUnknown(actual.getDocStatus());
					softly.assertThat(actualDocStatus).as("DocStatus").isEqualTo(expectedDocStatus);
				});

		expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_From_ID)
				.ifPresent(identifier -> softly.assertThat(WarehouseId.ofRepoIdOrNull(actual.getM_Warehouse_From_ID()))
						.as("DD_Order.M_Warehouse_From_ID")
						.isEqualTo(identifier.lookupNotNullIdIn(warehouseTable)));

		expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_To_ID)
				.ifPresent(identifier -> softly.assertThat(WarehouseId.ofRepoIdOrNull(actual.getM_Warehouse_To_ID()))
						.as("DD_Order.M_Warehouse_To_ID")
						.isEqualTo(identifier.lookupNotNullIdIn(warehouseTable)));

		softly.assertAll();
	}

	private void validateSingleLine(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final ShipmentScheduleId scheduleId,
			@NonNull final DataTableRow expected)
	{
		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
				.create()
				.list(I_DD_OrderLine.class);

		assertThat(lines).as("DD_Order %s has exactly one line", ddOrder.getDD_Order_ID()).hasSize(1);

		final I_DD_OrderLine line = lines.get(0);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(line.getM_ShipmentSchedule_ID())
				.as("DD_OrderLine.M_ShipmentSchedule_ID")
				.isEqualTo(scheduleId.getRepoId());

		expected.getAsOptionalBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered)
				.ifPresent(qtyEntered -> softly.assertThat(line.getQtyEntered().stripTrailingZeros())
						.as("DD_OrderLine.QtyEntered")
						.isEqualByComparingTo(qtyEntered.stripTrailingZeros()));

		expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_From_ID)
				.ifPresent(identifier -> softly.assertThat(WarehouseId.ofRepoIdOrNull(line.getM_Warehouse_ID()))
						.as("DD_OrderLine.M_Warehouse_ID (from)")
						.isEqualTo(identifier.lookupNotNullIdIn(warehouseTable)));

		expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_M_Warehouse_To_ID)
				.ifPresent(identifier -> softly.assertThat(WarehouseId.ofRepoIdOrNull(line.getM_WarehouseTo_ID()))
						.as("DD_OrderLine.M_WarehouseTo_ID")
						.isEqualTo(identifier.lookupNotNullIdIn(warehouseTable)));

		softly.assertAll();
	}

	/**
	 * @cucumber.stepdef Voids the live DD_Order linked to the given shipment schedule by applying the Void action directly on the document.
	 */
	@When("^the DD_Order linked to M_ShipmentSchedule (.*) is voided directly$")
	public void void_DD_Order_directly(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final I_DD_Order liveDDOrder = queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.create()
				.firstOnlyNotNull(I_DD_Order.class);

		documentBL.processEx(liveDDOrder, IDocument.ACTION_Void, IDocument.STATUS_Voided);
	}

	/**
	 * @cucumber.stepdef Polls until exactly one Voided DD_Order exists for the given shipment schedule and no live (non-voided) one remains.
	 */
	@Then("^after not more than (.*)s, the DD_Order linked to M_ShipmentSchedule (.*) is Voided$")
	public void assert_DD_Order_voided(final int timeoutSec, @NonNull final String shipmentScheduleIdentifier) throws InterruptedException
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		final Supplier<Boolean> isVoided = () -> {
			final boolean liveExists = queryBL.createQueryBuilder(I_DD_Order.class)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
					.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
					.create()
					.anyMatch();

			final boolean voidedExists = queryBL.createQueryBuilder(I_DD_Order.class)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
					.create()
					.anyMatch();

			// A voided DD_Order exists and there is no live one remaining for the schedule.
			return voidedExists && !liveExists;
		};

		StepDefUtil.tryAndWait(timeoutSec, 1000, isVoided, () -> logCurrentDDOrders(scheduleId));
	}

	/**
	 * @cucumber.stepdef Asserts immediately that no live (non-voided) DD_Order exists for the given shipment schedule.
	 */
	@Then("^there is no live DD_Order for M_ShipmentSchedule (.*)$")
	public void assert_no_live_DD_Order(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		final boolean liveExists = queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.create()
				.anyMatch();

		assertThat(liveExists)
				.as("No live DD_Order must exist for M_ShipmentSchedule %s", shipmentScheduleIdentifier)
				.isFalse();
	}

	/**
	 * @cucumber.stepdef Asserts that none of the shipment schedules belonging to the given sales order has a reconcile DD_Order (i.e., the order's schedules are for non-packing warehouses).
	 */
	@Then("^there is no reconcile DD_Order for the C_Order (.*)$")
	public void assert_no_reconcile_DD_Order_for_order(@NonNull final String orderIdentifier)
	{
		final org.compiere.model.I_C_Order order = orderTable.get(orderIdentifier);

		// All schedules of the order; assert none has a reconcile DD_Order (M_ShipmentSchedule_ID linkage).
		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.listIds()
				.forEach(scheduleId -> {
					final boolean ddOrderExists = queryBL.createQueryBuilder(I_DD_Order.class)
							.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
							.create()
							.anyMatch();
					assertThat(ddOrderExists)
							.as("No reconcile DD_Order must exist for schedule %s of order %s (non-packing warehouse)", scheduleId, orderIdentifier)
							.isFalse();
				});
	}

	private void logCurrentDDOrders(@NonNull final ShipmentScheduleId scheduleId)
	{
		final StringBuilder sb = new StringBuilder("DD_Orders linked to M_ShipmentSchedule_ID=").append(scheduleId).append(":\n");
		queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.create()
				.stream(I_DD_Order.class)
				.forEach(ddOrder -> sb.append(" DD_Order_ID=").append(ddOrder.getDD_Order_ID())
						.append(" DocStatus=").append(ddOrder.getDocStatus()).append("\n"));
		logger.error("*** Waiting for DD_Order to be Voided, current context:\n{}", sb);
	}
}
