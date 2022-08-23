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

package de.metas.cucumber.stepdefs.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.distributionorder.DD_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.productionorder.PP_Order_BOMLine_StepDefData;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonAggregateToLU;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonAggregateToNewLU;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFHQRCode;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFLine;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFManufacturingReceivingTargetValues;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFStep;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_QtyPicked;
import static org.assertj.core.api.Assertions.*;

public class AD_Workflow_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
			.registerModule(new JavaTimeModule());

	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final DD_Order_StepDefData ddOrderTable;
	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final M_Product_StepDefData productTable;
	private final M_Locator_StepDefData locatorTable;
	private final PP_Order_BOMLine_StepDefData bomLineTable;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFStep_StepDefData workflowStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;
	private final JsonWFLine_StepDefData workflowLineTable;
	private final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable;
	private final TestContext testContext;

	public AD_Workflow_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final DD_Order_StepDefData ddOrderTable,
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final PP_Order_BOMLine_StepDefData bomLineTable,
			@NonNull final JsonWFProcess_StepDefData workflowProcessTable,
			@NonNull final JsonWFActivity_StepDefData workflowActivityTable,
			@NonNull final JsonWFStep_StepDefData workflowStepTable,
			@NonNull final JsonWFHQRCode_StepDefData qrCodeTable,
			@NonNull final JsonWFLine_StepDefData workflowLineTable,
			@NonNull final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable,
			@NonNull final TestContext testContext
	)
	{
		this.orderTable = orderTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.bPartnerTable = bPartnerTable;
		this.testContext = testContext;
		this.orderLineTable = orderLineTable;
		this.ppOrderTable = ppOrderTable;
		this.ddOrderTable = ddOrderTable;
		this.ddOrderLineTable = ddOrderLineTable;
		this.productTable = productTable;
		this.locatorTable = locatorTable;
		this.bomLineTable = bomLineTable;
		this.workflowProcessTable = workflowProcessTable;
		this.workflowActivityTable = workflowActivityTable;
		this.workflowStepTable = workflowStepTable;
		this.qrCodeTable = qrCodeTable;
		this.receivingTargetValuesTable = receivingTargetValuesTable;
		this.workflowLineTable = workflowLineTable;
	}

	@And("update duration for AD_Workflow nodes")
	public void update_AD_Workflow_nodes(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			updateADWorkflowNodes(tableRow);
		}
	}

	@And("create JsonWFProcessStartRequest for picking and store it as the request payload in the test context")
	public void wf_picking_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);

		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);

		final String salesOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Order.COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Order salesOrder = orderTable.get(salesOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", PickingMobileApplication.HANDLER_ID.getAsString());
		wfParams.put("salesOrderId", salesOrder.getC_Order_ID());
		wfParams.put("customerId", bPartner.getC_BPartner_ID());
		wfParams.put("customerLocationId", bPartnerLocation.getC_BPartner_Location_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("create JsonWFProcessStartRequest for manufacturing and store it as the request payload in the test context")
	public void wf_manufacturing_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", ManufacturingMobileApplication.HANDLER_ID.getAsString());
		wfParams.put("ppOrderId", ppOrder.getPP_Order_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("create JsonWFProcessStartRequest for distribution and store it as the request payload in the test context")
	public void wf_distribution_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_DD_Order ddOrder = ddOrderTable.get(ddOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", DistributionMobileApplication.HANDLER_ID.getAsString());
		wfParams.put("ddOrderId", ddOrder.getDD_Order_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("^process response and extract (picking|distribution|manufacturing/materialissue|manufacturing/materialreceipt) information:$")
	public void extract_workflow_information_from_response(@NonNull final String workflowActivity, @NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final IllegalArgumentException noSuchWorkFlowException = new IllegalArgumentException("There is no workflow called " + workflowActivity + " that you cand extract information for!");

		final String content = testContext.getApiResponse().getContent();

		final JsonWFProcess wfProcessResponse = objectMapper.readValue(content, JsonWFProcess.class);

		final Predicate<JsonWFActivity> jsonActivityComponentTypePredicate = jsonWFActivity -> {
			if (workflowActivity.equals("picking"))
			{
				return jsonWFActivity.getComponentType().equals("picking/pickProducts");
			}
			else if (workflowActivity.equals("distribution"))
			{
				return jsonWFActivity.getComponentType().equals("distribution/move");
			}
			else if (workflowActivity.equals("manufacturing/materialissue"))
			{
				return jsonWFActivity.getComponentType().equals("manufacturing/rawMaterialsIssue");
			}
			else if(workflowActivity.equals("manufacturing/materialreceipt"))
			{
				return jsonWFActivity.getComponentType().equals("manufacturing/materialReceipt");
			}
			else
			{
				throw noSuchWorkFlowException;
			}
		};

		final JsonWFActivity wfPickActivity = wfProcessResponse.getActivities().stream()
				.filter(jsonActivityComponentTypePredicate)
				.collect(Collectors.toList()).get(0);

		final ArrayList activityLines = (ArrayList)wfPickActivity.getComponentProps().get("lines");

		final JsonWFLine wfLine = objectMapper.readValue(objectMapper.writeValueAsString(activityLines.get(0)), JsonWFLine.class);

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessResponse);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		workflowActivityTable.put(wfActivityIdentifier, wfPickActivity);

		final String wfLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.WorkflowLine.Identifier");
		if (Check.isNotBlank(wfLineIdentifier))
		{
			workflowLineTable.put(wfLineIdentifier, wfLine);
		}

		final String wfStepIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.WorkflowStep.Identifier");
		if (Check.isNotBlank(wfStepIdentifier))
		{
			workflowStepTable.put(wfStepIdentifier, wfLine.getSteps().get(0));
		}

		final String wfReceivingTargetValuesIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.WorkflowReceivingTargetValues.Identifier");
		if (Check.isNotBlank(wfReceivingTargetValuesIdentifier))
		{
			this.receivingTargetValuesTable.put(wfReceivingTargetValuesIdentifier, wfLine.getAvailableReceivingTargets().getValues().get(0));
		}

		final String qrCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.WorkflowStepQRCode.Identifier");
		if (Check.isNotBlank(qrCodeIdentifier))
		{
			if (workflowActivity.equals("picking"))
			{
				qrCodeTable.put(qrCodeIdentifier, wfLine.getSteps().get(0).getMainPickFrom().getHuQRCode());
			}
			else if (workflowActivity.equals("distribution"))
			{
				qrCodeTable.put(qrCodeIdentifier, wfLine.getSteps().get(0).getPickFromHU().getQrCode());
			}
			else if (workflowActivity.equals("manufacturing/materialissue"))
			{
				qrCodeTable.put(qrCodeIdentifier, wfLine.getSteps().get(0).getHuQRCode());
			}
			else
			{
				throw noSuchWorkFlowException;
			}
		}
	}

	@And("create JsonPickingEventsList and store it in context:")
	public void picking_events_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final JsonWFProcess workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final JsonWFActivity workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final String wfPickingStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
		final JsonWFStep workflowPickingStep = workflowStepTable.get(wfPickingStepIdentifier);

		final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
		final JsonWFHQRCode stepQRCode = qrCodeTable.get(qrCodeIdentifier);

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyPicked);

		final JsonPickingStepEvent pickingStepEvent = JsonPickingStepEvent.builder()
				.wfProcessId(workflowProcess.getId())
				.wfActivityId(workflowActivity.getActivityId())
				.type(JsonPickingStepEvent.EventType.PICK)
				.pickingStepId(workflowPickingStep.getPickingStepId())
				.huQRCode(stepQRCode.getCode())
				.qtyPicked(qtyPicked)
				.build();

		final List<JsonPickingStepEvent> pickingStepEvents = new ArrayList<>();
		pickingStepEvents.add(pickingStepEvent);

		final JsonPickingEventsList pickingEventsList = JsonPickingEventsList.builder().events(pickingStepEvents).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(pickingEventsList));
	}

	@And("create JsonDistributionEvent and store it in context:")
	public void distribution_event_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final JsonWFProcess workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final JsonWFActivity workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final String wfDistributionStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
		final JsonWFStep workflowDistributionStep = workflowStepTable.get(wfDistributionStepIdentifier);

		final String event = DataTableUtil.extractStringForColumnName(tableRow, "Event");

		final JsonDistributionEvent.JsonDistributionEventBuilder distributionEventBuilder = JsonDistributionEvent.builder()
				.wfProcessId(workflowProcess.getId())
				.wfActivityId(workflowActivity.getActivityId())
				.distributionStepId(workflowDistributionStep.getId());

		if (event.equals("PickFrom"))
		{
			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			final JsonWFHQRCode stepQRCode = qrCodeTable.get(qrCodeIdentifier);

			distributionEventBuilder.pickFrom(JsonDistributionEvent.PickFrom.builder().qrCode(stepQRCode.getCode()).build());
		}
		else if (event.equals("DropTo"))
		{
			distributionEventBuilder.dropTo(JsonDistributionEvent.DropTo.builder().build());
		}

		testContext.setRequestPayload(objectMapper.writeValueAsString(distributionEventBuilder.build()));
	}

	@And("create JsonManufacturingOrderEvent from JsonWFProcess that comes back from previous request and store it as the request payload in the test context")
	public void manufacturing_event_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String event = DataTableUtil.extractStringForColumnName(tableRow, "Event");

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final JsonWFProcess workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final JsonWFActivity workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final JsonManufacturingOrderEvent.JsonManufacturingOrderEventBuilder manufacturingOrderEventBuilder = JsonManufacturingOrderEvent.builder();

		if (event.equals("IssueTo"))
		{
			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
			final JsonWFStep workflowStep = workflowStepTable.get(wfStepIdentifier);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			final JsonWFHQRCode qrCode = qrCodeTable.get(qrCodeIdentifier);

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getId())
					.wfActivityId(workflowActivity.getActivityId())
					.issueTo(JsonManufacturingOrderEvent.IssueTo.builder()
									 .issueStepId(workflowStep.getId())
									 .qtyIssued(workflowStep.getQtyToIssue())
									 .huQRCode(qrCode.getCode())
									 .build());
		}
		else if (event.equals("ReceiveFrom"))
		{
			final String wfLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowLine.Identifier");
			final JsonWFLine workflowLine = workflowLineTable.get(wfLineIdentifier);

			final String wfReceivingTargetValuesIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowReceivingTargetValues.Identifier");
			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = this.receivingTargetValuesTable.get(wfReceivingTargetValuesIdentifier);

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getId())
					.wfActivityId(workflowActivity.getActivityId())
					.receiveFrom(JsonManufacturingOrderEvent.ReceiveFrom.builder()
										 .lineId(workflowLine.getId())
										 .qtyReceived(workflowLine.getQtyToReceive())
										 .aggregateToLU(JsonAggregateToLU.builder()
																.newLU(JsonAggregateToNewLU.builder()
																			   .luCaption(receivingTargetValues.getLuCaption())
																			   .tuCaption(receivingTargetValues.getTuCaption())
																			   .luPIItemId(HuPackingInstructionsItemId.ofRepoId(receivingTargetValues.getLuPIItemId()))
																			   .tuPIItemProductId(HUPIItemProductId.ofRepoId(receivingTargetValues.getTuPIItemProductId()))
																			   .build())
																.build())
										 .build());
		}

		testContext.setRequestPayload(objectMapper.writeValueAsString(manufacturingOrderEventBuilder.build()));
	}

	@And("validate I_M_ShipmentSchedule, I_M_Picking_Candidate and I_M_HU after picking order workflow")
	public void validate_after_picking_workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

			final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyPicked);

			final I_M_ShipmentSchedule shipmentSchedule = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
					.orderBy(I_M_ShipmentSchedule.COLUMNNAME_Created)
					.create()
					.first(I_M_ShipmentSchedule.class);

			assertThat(shipmentSchedule).isNotNull();
			assertThat(shipmentSchedule.getQtyOrdered_Calculated()).isEqualTo(qtyPicked);

			final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
					.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.orderBy(I_M_Picking_Candidate.COLUMNNAME_Created)
					.create()
					.first(I_M_ShipmentSchedule_QtyPicked.class);

			assertThat(shipmentScheduleQtyPicked).isNotNull();
			assertThat(shipmentScheduleQtyPicked.getQtyPicked()).isEqualTo(qtyPicked);

			final I_M_Picking_Candidate pickingCandidate = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
					.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.orderBy(I_M_Picking_Candidate.COLUMNNAME_Created)
					.create()
					.first(I_M_Picking_Candidate.class);

			assertThat(pickingCandidate).isNotNull();
			assertThat(pickingCandidate.getQtyPicked()).isEqualTo(qtyPicked);

			final I_M_HU hu = queryBL.createQueryBuilder(I_M_HU.class)
					.addEqualsFilter(I_M_HU.COLUMN_M_HU_ID, pickingCandidate.getM_HU_ID())
					.orderBy(I_M_Picking_Candidate.COLUMNNAME_Created)
					.create()
					.firstOnly(I_M_HU.class);

			assertThat(hu).isNotNull();
			assertThat(hu.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Picked);
		}
	}

	@And("validate I_DD_Order_MoveSchedule after distribution order workflow")
	public void validate_move_schedule_after_distribution_workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_DD_OrderLine orderLine = ddOrderLineTable.get(orderLineIdentifier);

			final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_DD_Order_MoveSchedule.COLUMNNAME_QtyPicked);

			final I_DD_Order_MoveSchedule moveSchedule = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
					.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, orderLine.getDD_OrderLine_ID())
					.orderBy(I_DD_Order_MoveSchedule.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(moveSchedule).isNotNull();
			assertThat(moveSchedule.getQtyToPick()).isEqualTo(qtyPicked);

		}
	}

	@And("validate I_M_Movement and I_M_HU after distribution order workflow")
	public void validate_movement_and_HU_after_distribution_workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_DD_OrderLine orderLine = ddOrderLineTable.get(orderLineIdentifier);

			final I_M_Movement movement = queryBL.createQueryBuilder(I_M_Movement.class)
					.addEqualsFilter(I_M_Movement.COLUMNNAME_DD_Order_ID, orderLine.getDD_Order_ID())
					.orderBy(I_M_Movement.COLUMN_Created)
					.create()
					.first();

			assertThat(movement).isNotNull();

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU.COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Locator locator = locatorTable.get(locatorIdentifier);

			final I_M_HU_Assignment huAssignment = queryBL.createQueryBuilder(I_M_HU_Assignment.class)
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, movement.getM_Movement_ID())
					.orderBy(I_M_HU_Assignment.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(huAssignment).isNotNull();

			final I_M_HU hu = queryBL.createQueryBuilder(I_M_HU.class)
					.addEqualsFilter(COLUMNNAME_M_HU_ID, huAssignment.getM_HU_ID())
					.orderBy(I_M_HU.COLUMNNAME_Updated)
					.create()
					.first();

			assertThat(hu).isNotNull();
			assertThat(hu.getM_Locator_ID()).isEqualTo(locator.getM_Locator_ID());
		}
	}

	@And("validate I_PP_Cost_Collector after manufacturing order workflow")
	public void validate_cost_collector_after_manufacturing_workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order order = ppOrderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = bomLineIdentifier != null ? bomLineTable.get(bomLineIdentifier) : null;

			final I_PP_Cost_Collector costCollector = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
					.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.orderBy(I_PP_Cost_Collector.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(costCollector).isNotNull();
			assertThat(costCollector.isProcessed()).isEqualTo(true);
			assertThat(costCollector.getMovementQty()).isEqualTo(new BigDecimal(1));
			if (bomLine != null)
			{
				assertThat(costCollector.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			}
		}
	}

	@And("validate I_PP_Order_Qty after manufacturing order workflow")
	public void validate_order_qty_after_manufacturing_workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order order = ppOrderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = bomLineIdentifier != null ? bomLineTable.get(bomLineIdentifier) : null;

			final I_PP_Order_Qty orderQty = queryBL.createQueryBuilder(I_PP_Order_Qty.class)
					.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.orderBy(I_PP_Order_Qty.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(orderQty).isNotNull();
			assertThat(orderQty.getQty()).isEqualTo(new BigDecimal(1));
			if (bomLine != null)
			{
				assertThat(orderQty.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			}
		}
	}

	private void updateADWorkflowNodes(@NonNull final Map<String, String> tableRow)
	{
		final int id = DataTableUtil.extractIntForColumnName(tableRow, I_AD_Workflow.COLUMNNAME_AD_Workflow_ID);

		final int duration = DataTableUtil.extractIntForColumnName(tableRow, I_AD_WF_Node.COLUMNNAME_Duration);

		final I_AD_Workflow workflow = InterfaceWrapperHelper.load(id, I_AD_Workflow.class);

		final IQueryUpdater<I_AD_WF_Node> updater = queryBL.createCompositeQueryUpdater(I_AD_WF_Node.class)
				.addSetColumnValue(I_AD_WF_Node.COLUMNNAME_Duration, duration);

		queryBL.createQueryBuilder(I_AD_WF_Node.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, workflow.getAD_Workflow_ID())
				.create()
				.update(updater);
	}
}