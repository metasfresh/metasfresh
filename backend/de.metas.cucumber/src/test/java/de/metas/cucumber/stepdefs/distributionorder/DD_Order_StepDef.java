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

import com.google.common.collect.ImmutableSet;
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
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.picking.M_Picking_Job_Schedule_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.commands.pick_from.DDOrderPickFromRequest;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.util.StringUtils;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingJobScheduleId;
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
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code DD_Order} (distribution order) lifecycle — creation, completion,
 * voiding, and picking-replenishment reconcile assertions.
 *
 * <p>Covers:
 * <ul>
 *   <li>Creating DD_Orders via {@code metasfresh contains DD_Orders}</li>
 *   <li>Completing, voiding, and asserting DocStatus</li>
 *   <li>Waiting for the DD_Order serving a {@code M_Picking_Job_Schedule} to appear (reconcile)</li>
 *   <li>Asserting that no live DD_Order serves a {@code M_ShipmentSchedule}</li>
 *   <li>Driving the reconcile event handler directly (bypass async) for unit-like Cucumber assertions</li>
 * </ul>
 */
@RequiredArgsConstructor
public class DD_Order_StepDef
{
	private static final Logger logger = LogManager.getLogger(DD_Order_StepDef.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO = SpringContextHolder.instance.getBean(DDOrderLowLevelDAO.class);
	@NonNull private final DDOrderLineContributorRepository contributorRepository = SpringContextHolder.instance.getBean(DDOrderLineContributorRepository.class);
	@NonNull private final DDOrderMoveScheduleService moveScheduleService = SpringContextHolder.instance.getBean(DDOrderMoveScheduleService.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
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
	@NonNull private final M_Picking_Job_Schedule_StepDefData pickingJobScheduleTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final M_HU_StepDefData huTable;

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

		// Close-out disposition assertions: the in-progress disconnect marker, and the close-out
		// picker release (AD_User_Responsible_ID cleared). Use `-` in the feature to assert the responsible is unset.
		expected.getAsOptionalBoolean(I_DD_Order.COLUMNNAME_IsPickingDisconnected)
				.ifPresent(expectedDisconnected -> softly.assertThat(actual.isPickingDisconnected())
						.as("IsPickingDisconnected")
						.isEqualTo(expectedDisconnected));

		// AD_User_Responsible_ID: a `-` cell asserts the responsible is unset (the CLOSE path releases the picker).
		// Any other (numeric) value asserts that exact AD_User_ID.
		expected.getAsOptionalString(I_DD_Order.COLUMNNAME_AD_User_Responsible_ID)
				.map(StringUtils::trimBlankToNull)
				.ifPresent(responsibleStr -> {
					final int expectedResponsibleId = "-".equals(responsibleStr) ? -1 : Integer.parseInt(responsibleStr);
					final int actualResponsibleId = actual.getAD_User_Responsible_ID() > 0 ? actual.getAD_User_Responsible_ID() : -1;
					softly.assertThat(actualResponsibleId).as("AD_User_Responsible_ID").isEqualTo(expectedResponsibleId);
				});

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
	 * @cucumber.stepdef Asserts the DD_Order's {@code C_BPartner_ID} is none of the given business partners.
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) a business partner the DD_Order must NOT name<br>
	 * @cucumber.depends StepDefData: DD_Order_StepDefData, C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the DD_Order identified by groupDDOrder names none of these business partners:
	 *   | C_BPartner_ID |
	 *   | customerA     |
	 *   | customerB     |
	 * </pre>
	 */
	@Then("^the DD_Order identified by (.*) names none of these business partners:$")
	public void assertDDOrderNamesNoneOfTheseBPartners(@NonNull final String ddOrderIdentifier, @NonNull final DataTable dataTable)
	{
		final I_DD_Order ddOrder = ddOrderTable.get(ddOrderIdentifier);
		final BPartnerId actualBPartnerId = BPartnerId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID());

		final SoftAssertions softly = new SoftAssertions();
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier(I_DD_Order.COLUMNNAME_C_BPartner_ID);
			softly.assertThat(actualBPartnerId)
					.as("DD_Order %s (DD_Order_ID=%s) C_BPartner_ID must not be C_BPartner %s",
							ddOrderIdentifier, ddOrder.getDD_Order_ID(), identifier)
					.isNotEqualTo(identifier.lookupNotNullIdIn(bPartnerTable));
		});
		softly.assertAll();
	}

	/** The live (DocStatus != Voided) DD_Orders serving the given assignment, resolved through its {@code DD_OrderLine_PickingJobSchedule} rows. */
	private IQuery<I_DD_Order> liveDDOrderForPickingJobScheduleQuery(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		return liveDDOrdersServingQuery(contributorRowsOfPickingJobScheduleQuery(jobScheduleId));
	}

	/** The live (DocStatus != Voided) DD_Orders serving any assignment of the given delivery, resolved through the alloc table. */
	private IQuery<I_DD_Order> liveDDOrderForScheduleQuery(@NonNull final ShipmentScheduleId scheduleId)
	{
		return liveDDOrdersServingQuery(contributorRowsOfShipmentScheduleQuery(scheduleId));
	}

	private IQuery<I_DD_Order> liveDDOrdersServingQuery(@NonNull final IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsQuery)
	{
		return liveDDOrdersServingBuilder(contributorRowsQuery).create();
	}

	private IQueryBuilder<I_DD_Order> liveDDOrdersServingBuilder(@NonNull final IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsQuery)
	{
		return queryBL.createQueryBuilder(I_DD_Order.class)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.addInSubQueryFilter(
						I_DD_Order.COLUMNNAME_DD_Order_ID,
						I_DD_OrderLine.COLUMNNAME_DD_Order_ID,
						linesOf(contributorRowsQuery));
	}

	private IQuery<I_DD_OrderLine> linesOf(@NonNull final IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsQuery)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID,
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID,
						contributorRowsQuery)
				.create();
	}

	private IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsOfPickingJobScheduleQuery(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleId)
				.create();
	}

	private IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsOfShipmentScheduleQuery(@NonNull final ShipmentScheduleId scheduleId)
	{
		final IQuery<I_M_Picking_Job_Schedule> assignmentsQuery = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.create();

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						assignmentsQuery)
				.create();
	}

	private void validateDDOrderHeader(@NonNull final I_DD_Order actual, @NonNull final DataTableRow expected)
	{
		final SoftAssertions softly = new SoftAssertions();

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

	/**
	 * @cucumber.stepdef Voids the live DD_Order serving the given shipment schedule by applying the Void action directly on the document.
	 */
	@When("^the DD_Order linked to M_ShipmentSchedule (.*) is voided directly$")
	public void void_DD_Order_directly(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		final I_DD_Order liveDDOrder = liveDDOrderForScheduleQuery(scheduleId).firstOnlyNotNull(I_DD_Order.class);

		documentBL.processEx(liveDDOrder, IDocument.ACTION_Void, IDocument.STATUS_Voided);
	}

	/**
	 * @cucumber.stepdef Asserts immediately that no live (non-voided) DD_Order serves the given shipment schedule.
	 */
	@Then("^there is no live DD_Order for M_ShipmentSchedule (.*)$")
	public void assert_no_live_DD_Order(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		assertThat(liveDDOrderForScheduleQuery(scheduleId).listIds())
				.as("live DD_Orders serving M_ShipmentSchedule %s", shipmentScheduleIdentifier)
				.isEmpty();
	}

	/**
	 * @cucumber.stepdef Asserts that none of the shipment schedules belonging to the given sales order has a reconcile DD_Order (i.e., the order's schedules are for non-packing warehouses).
	 */
	@Then("^there is no reconcile DD_Order for the C_Order (.*)$")
	public void assert_no_reconcile_DD_Order_for_order(@NonNull final String orderIdentifier)
	{
		final org.compiere.model.I_C_Order order = orderTable.get(orderIdentifier);

		final IQuery<I_M_ShipmentSchedule> schedulesOfOrder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create();

		final IQuery<I_M_Picking_Job_Schedule> assignmentsQuery = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID,
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
						schedulesOfOrder)
				.create();

		final IQuery<I_DD_OrderLine_PickingJobSchedule> contributorRowsQuery = queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addInSubQueryFilter(
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						assignmentsQuery)
				.create();

		// Any DocStatus, including Voided: a reconcile DD_Order must never have been produced at all.
		assertThat(queryBL.createQueryBuilder(I_DD_Order.class)
				.addInSubQueryFilter(
						I_DD_Order.COLUMNNAME_DD_Order_ID,
						I_DD_OrderLine.COLUMNNAME_DD_Order_ID,
						linesOf(contributorRowsQuery))
				.create()
				.listIds())
				.as("reconcile DD_Orders for any shipment schedule of C_Order %s (non-packing warehouse)", orderIdentifier)
				.isEmpty();
	}

	/**
	 * @cucumber.stepdef Polls for the single live (DocStatus != Voided) DD_Order serving a workstation assignment
	 * through its {@code DD_OrderLine_PickingJobSchedule} rows, asserts exactly one is found, and validates
	 * header + line.
	 * <p>
	 * This is the assertion used by the workstation-assignment-driven DD_Order picking-reconcile flow, where
	 * exactly one Completed DD_Order is created per {@code M_Picking_Job_Schedule} assignment. The found line's
	 * contributor set is asserted to be exactly that one assignment.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the assignment the DD_Order must be linked to</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code Identifier} — stores the found DD_Order for later reference</li>
	 *   <li>{@code DD_OrderLine_ID} — stores the found DD_Order's single line for later reference</li>
	 *   <li>{@code DocStatus} — expected header doc status (e.g. {@code CO})</li>
	 *   <li>{@code M_Warehouse_From_ID} — expected source warehouse identifier (header + line)</li>
	 *   <li>{@code M_Warehouse_To_ID} — expected target warehouse identifier (header)</li>
	 *   <li>{@code M_LocatorTo_ID} — expected line target locator identifier (the workstation's pick-from locator)</li>
	 *   <li>{@code QtyEntered} — expected line quantity</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 120s, the DD_Order linked to picking job schedule is found:
	 *   | M_Picking_Job_Schedule_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
	 *   | jobSchedule               | CO        | stockWH             | packingWH         | packingLocator | 5          |
	 * </pre>
	 */
	@And("^after not more than (.*)s, the DD_Order linked to picking job schedule is found:$")
	public void validateDDOrderLinkedToPickingJobSchedule(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> validateDDOrderLinkedToPickingJobSchedule(timeoutSec, row));
	}

	private void validateDDOrderLinkedToPickingJobSchedule(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final PickingJobScheduleId jobScheduleId = row.getAsIdentifier(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIdIn(pickingJobScheduleTable);

		// Validate header AND line inside the retry so an in-flight RECREATE (transient stale header) cannot be
		// grabbed before the fully-matching DD_Order exists.
		final I_DD_Order ddOrder = StepDefUtil.tryAndWaitForItem(liveDDOrderForPickingJobScheduleQuery(jobScheduleId))
				.validateUsingConsumer(record -> {
					validateDDOrderHeader(record, row);
					validatePickingJobScheduleLine(record, jobScheduleId, row);
				})
				.maxWaitSeconds(timeoutSec)
				.execute();

		row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
		row.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.ifPresent(identifier -> ddOrderLineTable.putOrReplace(identifier, singleLineOf(ddOrder)));
	}

	private I_DD_OrderLine singleLineOf(@NonNull final I_DD_Order ddOrder)
	{
		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
				.create()
				.list(I_DD_OrderLine.class);
		assertThat(lines).as("DD_Order %s has exactly one line", ddOrder.getDD_Order_ID()).hasSize(1);
		return lines.get(0);
	}

	/**
	 * @cucumber.stepdef Test seam: assigns a responsible user ({@code AD_User_Responsible_ID}) to the live DD_Order
	 * linked to the given workstation assignment, simulating a worker who has picked up the DD_Order-backed mobile
	 * DistributionJob (the launcher keys on {@code AD_User_Responsible_ID}). Used so the close-out CLOSE path's picker
	 * release ({@code AD_User_Responsible_ID} cleared) can be asserted as a state transition.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the assignment whose DD_Order gets a responsible user</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * When a worker takes the DD_Order linked to picking job schedule:
	 *   | M_Picking_Job_Schedule_ID |
	 *   | jobSchedule               |
	 * </pre>
	 */
	@When("^a worker takes the DD_Order linked to picking job schedule:$")
	public void assignResponsibleToDDOrder(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final PickingJobScheduleId jobScheduleId = row.getAsIdentifier(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIdIn(pickingJobScheduleTable);
			final I_DD_Order ddOrder = liveDDOrderForPickingJobScheduleQuery(jobScheduleId).firstOnlyNotNull(I_DD_Order.class);
			// UpdatedBy is always a valid AD_User_ID (> 0) — use it as the "worker who picked up the job".
			ddOrderService.assignToResponsible(ddOrder, UserId.ofRepoId(ddOrder.getUpdatedBy()));
		});
	}

	/**
	 * @cucumber.stepdef Picks the source HU from the DD_Order linked to the given picking job schedule, leaving the
	 * move IN_PROGRESS (goods moved to in-transit, not yet dropped). For the DD_Order's single line it creates a
	 * move-schedule via {@link DDOrderMoveScheduleService#createScheduleToMove} and then picks the HU via
	 * {@link DDOrderMoveScheduleService#pickFromHU}.
	 * <p>
	 * Real-world trigger: a worker opens the DD_Order-backed mobile DistributionJob and picks the source HU, the first
	 * leg of a warehouse move. The IN_PROGRESS state is what {@link DDOrderMoveScheduleService#hasInProgressSchedules}
	 * checks, which drives the shipment close-out disposition down the DISCONNECT branch.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the assignment whose DD_Order is picked from</li>
	 *   <li>{@code PickFrom_HU_ID} — identifier of the source HU being picked</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * When pick from the DD_Order linked to picking job schedule:
	 *   | M_Picking_Job_Schedule_ID | PickFrom_HU_ID |
	 *   | jobSchedule               | stockSourceHU  |
	 * </pre>
	 */
	@When("^pick from the DD_Order linked to picking job schedule:$")
	public void pickFromDDOrderLinkedToPickingJobSchedule(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::pickFromDDOrderLinkedToPickingJobSchedule);
	}

	private void pickFromDDOrderLinkedToPickingJobSchedule(@NonNull final DataTableRow row)
	{
		final PickingJobScheduleId jobScheduleId = row.getAsIdentifier(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIdIn(pickingJobScheduleTable);
		final HuId pickFromHuId = row.getAsIdentifier("PickFrom_HU_ID").lookupNotNullIdIn(huTable);

		final List<I_DD_OrderLine> lines = linesOf(contributorRowsOfPickingJobScheduleQuery(jobScheduleId)).list(I_DD_OrderLine.class);

		assertThat(lines)
				.as("DD_OrderLines for picking job schedule %s (must exist before picking from the DD_Order)", jobScheduleId.getRepoId())
				.isNotEmpty();

		trxManager.runInThreadInheritedTrx(() -> {
			for (final I_DD_OrderLine line : lines)
			{
				final DDOrderMoveSchedule schedule = moveScheduleService.createScheduleToMove(
						DDOrderMoveScheduleCreateRequest.builder()
								.ddOrderId(DDOrderId.ofRepoId(line.getDD_Order_ID()))
								.ddOrderLineId(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))
								.productId(ProductId.ofRepoId(line.getM_Product_ID()))
								.pickFromLocatorId(LocatorId.ofRecord(warehouseDAO.getLocatorByRepoId(line.getM_Locator_ID())))
								.pickFromHUId(pickFromHuId)
								.qtyToPick(Quantitys.of(line.getQtyOrdered(), UomId.ofRepoId(line.getC_UOM_ID())))
								.isPickWholeHU(true)
								.dropToLocatorId(LocatorId.ofRecord(warehouseDAO.getLocatorByRepoId(line.getM_LocatorTo_ID())))
								.build());

				moveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
						.scheduleId(schedule.getId())
						.huId(pickFromHuId)
						.build());
			}
		});
	}

	private void validatePickingJobScheduleLine(
			@NonNull final I_DD_Order ddOrder,
			@NonNull final PickingJobScheduleId jobScheduleId,
			@NonNull final DataTableRow expected)
	{
		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
				.create()
				.list(I_DD_OrderLine.class);

		assertThat(lines).as("DD_Order %s has exactly one line", ddOrder.getDD_Order_ID()).hasSize(1);

		final I_DD_OrderLine line = lines.get(0);
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(contributorRepository.getPickingJobScheduleIds(ImmutableSet.of(DDOrderLineId.ofRepoId(line.getDD_OrderLine_ID()))))
				.as("DD_OrderLine_PickingJobSchedule contributor set of DD_OrderLine_ID=%s", line.getDD_OrderLine_ID())
				.containsExactly(jobScheduleId);

		expected.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID)
				.ifPresent(identifier -> softly.assertThat(LocatorId.ofRepoIdOrNull(WarehouseId.ofRepoIdOrNull(line.getM_WarehouseTo_ID()), line.getM_LocatorTo_ID()))
						.as("DD_OrderLine.M_LocatorTo_ID")
						.isEqualTo(identifier.lookupNotNullIdIn(locatorTable)));

		expected.getAsOptionalBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered)
				.ifPresent(qtyEntered -> softly.assertThat(line.getQtyEntered().stripTrailingZeros())
						.as("DD_OrderLine.QtyEntered")
						.isEqualByComparingTo(qtyEntered.stripTrailingZeros()));

		softly.assertAll();
	}

	/**
	 * @cucumber.stepdef Polls for the live (DocStatus != Voided) per-locator DD_Orders serving a workstation
	 * assignment through its {@code DD_OrderLine_PickingJobSchedule} rows, matching each expected row to the DD_Order
	 * whose single line's source locator ({@code DD_OrderLine.M_Locator_ID}) equals the row's {@code M_Locator_ID}.
	 * <p>
	 * This is the assertion for the stock-aware multi-locator split flow: the demand is split greedily across the
	 * contributing source locators, one Completed DD_Order (one line) per locator. The step asserts, per expected
	 * row, that exactly one live DD_Order exists sourcing from that locator (with the expected qty / target locator /
	 * doc status), AND that the set of contributing source locators is EXACTLY the expected set (no extra live
	 * DD_Orders for other locators).
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the assignment the DD_Orders must be linked to</li>
	 *   <li>{@code M_Locator_ID} — identifier of the source locator the matching DD_Order line sources from</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code Identifier} — stores the matched DD_Order for later reference (e.g. to assert it was later voided)</li>
	 *   <li>{@code DocStatus} — expected header doc status (e.g. {@code CO})</li>
	 *   <li>{@code M_Warehouse_From_ID} — expected source warehouse identifier (header)</li>
	 *   <li>{@code M_Warehouse_To_ID} — expected target warehouse identifier (header)</li>
	 *   <li>{@code M_LocatorTo_ID} — expected line target locator identifier (the workstation's pick-from locator)</li>
	 *   <li>{@code QtyEntered} — expected line quantity (the portion allocated to this source locator)</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 120s, the per-locator DD_Orders linked to picking job schedule are found:
	 *   | M_Picking_Job_Schedule_ID | M_Locator_ID | DocStatus | M_Warehouse_From_ID | M_Warehouse_To_ID | M_LocatorTo_ID | QtyEntered |
	 *   | jobSchedule               | locatorA     | CO        | stockWH             | packingWH         | packingLocator | 10         |
	 *   | jobSchedule               | locatorB     | CO        | stockWH             | packingWH         | packingLocator | 5          |
	 * </pre>
	 */
	@And("^after not more than (.*)s, the per-locator DD_Orders linked to picking job schedule are found:$")
	public void validatePerLocatorDDOrdersLinkedToPickingJobSchedule(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.toList();

		// All rows of a single invocation describe the SAME assignment's complete set of per-locator DD_Orders.
		final PickingJobScheduleId jobScheduleId = rows.get(0)
				.getAsIdentifier(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIdIn(pickingJobScheduleTable);

		// Expected per-locator picture: the full source-locator SET, plus the expected QtyEntered for each row that
		// specifies one (QtyEntered is an optional column).
		final Map<LocatorId, DataTableRow> expectedBySourceLocator = new LinkedHashMap<>();
		final Map<LocatorId, BigDecimal> expectedQtyBySourceLocator = new LinkedHashMap<>();
		for (final DataTableRow row : rows)
		{
			final LocatorId sourceLocatorId = row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_Locator_ID).lookupNotNullIdIn(locatorTable);
			expectedBySourceLocator.put(sourceLocatorId, row);
			row.getAsOptionalBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered)
					.ifPresent(qtyEntered -> expectedQtyBySourceLocator.put(sourceLocatorId, qtyEntered));
		}

		// Poll until the async reconcile has SETTLED, then validate each matched DD_Order's header + single line.
		// "Settled" = the live source-locator set EXACTLY matches the expected set AND every expected per-locator
		// QtyEntered matches the live line quantity. Polling on the whole picture (not row-by-row) avoids binding to
		// a transient in-flight state where a void/create/update has only partially landed. QtyEntered is part of
		// the gate because a demand change that keeps the SAME contributing locators (only the quantity changes)
		// leaves the source-locator set unchanged: a set-only check is satisfied immediately by the stale pre-change
		// DD_Orders and would race the async (event-bus) reconcile that updates the line quantity.
		final Supplier<Boolean> reconcileSettled = () -> {
			final Map<LocatorId, BigDecimal> liveQtyBySourceLocator = liveLineQtyBySourceLocatorForPickingJobSchedule(jobScheduleId);
			return isPerLocatorReconcileSettled(expectedBySourceLocator.keySet(), expectedQtyBySourceLocator,
					liveQtyBySourceLocator.keySet(), liveQtyBySourceLocator);
		};
		StepDefUtil.tryAndWait(timeoutSec, 1000, reconcileSettled, () -> logCurrentDDOrdersForPickingJobSchedule(jobScheduleId));

		for (final Map.Entry<LocatorId, DataTableRow> entry : expectedBySourceLocator.entrySet())
		{
			final I_DD_Order ddOrder = liveDDOrderForPickingJobScheduleAndSourceLocator(jobScheduleId, entry.getKey());
			assertThat(ddOrder).as("live DD_Order for assignment %s sourcing from locator %s", jobScheduleId, entry.getKey()).isNotNull();

			final DataTableRow row = entry.getValue();
			validateDDOrderHeader(ddOrder, row);
			validatePickingJobScheduleLine(ddOrder, jobScheduleId, row);

			row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
		}
	}

	/**
	 * Live per-locator picture: source {@code LocatorId} → the line's {@code QtyEntered}, for every live (non-voided)
	 * DD_Order serving the assignment. Each reconcile DD_Order has exactly one line, so the key set is the set of
	 * contributing source locators and each value is that locator's current planned quantity.
	 */
	private Map<LocatorId, BigDecimal> liveLineQtyBySourceLocatorForPickingJobSchedule(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		// Collect the live DD_Order ids, then fetch all their lines in a single batched query (no per-DD_Order query).
		final List<DDOrderId> liveDDOrderIds = liveDDOrderForPickingJobScheduleQuery(jobScheduleId)
				.stream(I_DD_Order.class)
				.map(ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.collect(java.util.stream.Collectors.toList());

		final Map<LocatorId, BigDecimal> qtyBySourceLocator = new LinkedHashMap<>();
		ddOrderLowLevelDAO.streamLinesByDDOrderIds(liveDDOrderIds)
				.forEach(line -> {
					// Resolve the source LocatorId from the locator record (authoritative warehouse), not the
					// line's M_Warehouse_ID (not reliably set on a programmatically-built DD_OrderLine).
					final LocatorId sourceLocatorId = LocatorId.ofRecordOrNull(warehouseDAO.getLocatorByRepoId(line.getM_Locator_ID()));
					if (sourceLocatorId != null)
					{
						qtyBySourceLocator.put(sourceLocatorId, line.getQtyEntered());
					}
				});
		return qtyBySourceLocator;
	}

	/**
	 * Readiness predicate for {@link #validatePerLocatorDDOrdersLinkedToPickingJobSchedule}: the async reconcile has
	 * settled iff the live source-locator set exactly matches the expected set AND every expected per-locator
	 * {@code QtyEntered} equals the live line quantity. Including the quantity is essential — a demand change that
	 * keeps the same contributing locators (only the quantity changes) leaves the source-locator set unchanged, so a
	 * set-only check would report "ready" immediately against the stale pre-change DD_Orders and race the async
	 * reconcile that updates the line quantity. Pure/stateless so it can be unit-tested without a DB.
	 */
	static boolean isPerLocatorReconcileSettled(
			@NonNull final Set<LocatorId> expectedLocators,
			@NonNull final Map<LocatorId, BigDecimal> expectedQtyByLocator,
			@NonNull final Set<LocatorId> liveLocators,
			@NonNull final Map<LocatorId, BigDecimal> liveQtyByLocator)
	{
		if (!liveLocators.equals(expectedLocators))
		{
			return false;
		}
		for (final Map.Entry<LocatorId, BigDecimal> expected : expectedQtyByLocator.entrySet())
		{
			final BigDecimal liveQty = liveQtyByLocator.get(expected.getKey());
			if (liveQty == null || liveQty.compareTo(expected.getValue()) != 0)
			{
				return false;
			}
		}
		return true;
	}

	@Nullable
	private I_DD_Order liveDDOrderForPickingJobScheduleAndSourceLocator(
			@NonNull final PickingJobScheduleId jobScheduleId,
			@NonNull final LocatorId sourceLocatorId)
	{
		return liveDDOrdersServingBuilder(contributorRowsOfPickingJobScheduleQuery(jobScheduleId))
				.andCollectChildren(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_Locator_ID, sourceLocatorId.getRepoId())
				.create()
				.firstOptional(I_DD_OrderLine.class)
				.map(line -> InterfaceWrapperHelper.load(line.getDD_Order_ID(), I_DD_Order.class))
				.orElse(null);
	}

	private void logCurrentDDOrdersForPickingJobSchedule(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		final StringBuilder sb = new StringBuilder("DD_Orders serving M_Picking_Job_Schedule_ID=").append(jobScheduleId).append(":\n");
		queryBL.createQueryBuilder(I_DD_Order.class)
				.addInSubQueryFilter(
						I_DD_Order.COLUMNNAME_DD_Order_ID,
						I_DD_OrderLine.COLUMNNAME_DD_Order_ID,
						linesOf(contributorRowsOfPickingJobScheduleQuery(jobScheduleId)))
				.create()
				.stream(I_DD_Order.class)
				.forEach(ddOrder -> {
					sb.append(" DD_Order_ID=").append(ddOrder.getDD_Order_ID())
							.append(" DocStatus=").append(ddOrder.getDocStatus());
					queryBL.createQueryBuilder(I_DD_OrderLine.class)
							.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
							.create()
							.stream(I_DD_OrderLine.class)
							.forEach(line -> sb.append(" [line M_Locator_ID=").append(line.getM_Locator_ID())
									.append(" QtyEntered=").append(line.getQtyEntered()).append("]"));
					sb.append("\n");
				});
		logger.error("*** Waiting for per-locator DD_Orders, current context:\n{}", sb);
	}

	/**
	 * @cucumber.stepdef Asserts the DD_Order's header business partner and location ARE the ones of the DD_Order's own
	 * organization — i.e. exactly what {@code IBPartnerOrgBL#retrieveOrgBPLocationId} resolves from
	 * {@code AD_OrgInfo.OrgBP_Location_ID} for the order's {@code AD_Org_ID}.
	 * <p>
	 * A replenishment order moves goods between the organization's own warehouses, so the organization owns it: never
	 * one of the customers whose deliveries it serves, and never nobody. The header partner and location drive the
	 * order's {@code DeliveryRule}, print format and print language, so both are pinned.
	 * <p>
	 * The step first asserts the organization HAS a business-partner link, so it can never pass by comparing "unset"
	 * against "unset".
	 * @cucumber.depends StepDefData: DD_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the DD_Order identified by groupDDOrder names the business partner of its own organization
	 * </pre>
	 */
	@Then("^the DD_Order identified by (.*) names the business partner of its own organization$")
	public void assertDDOrderNamesOwnOrgBPartner(@NonNull final String ddOrderIdentifier)
	{
		final I_DD_Order ddOrder = ddOrderTable.get(ddOrderIdentifier);
		final OrgId orgId = OrgId.ofRepoId(ddOrder.getAD_Org_ID());

		final BPartnerLocationId orgBPLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(orgId);
		assertThat(orgBPLocationId)
				.as("AD_OrgInfo.OrgBP_Location_ID of AD_Org_ID=%s — the organization's own business partner must be"
						+ " linked, otherwise the assertions below would compare unset against unset", orgId.getRepoId())
				.isNotNull();

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(BPartnerId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID()))
				.as("DD_Order %s (DD_Order_ID=%s) C_BPartner_ID must be the organization's own business partner",
						ddOrderIdentifier, ddOrder.getDD_Order_ID())
				.isEqualTo(orgBPLocationId.getBpartnerId());

		softly.assertThat(BPartnerLocationId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID(), ddOrder.getC_BPartner_Location_ID()))
				.as("DD_Order %s (DD_Order_ID=%s) C_BPartner_Location_ID must be the organization's own business partner location",
						ddOrderIdentifier, ddOrder.getDD_Order_ID())
				.isEqualTo(orgBPLocationId);

		softly.assertAll();
	}
}
