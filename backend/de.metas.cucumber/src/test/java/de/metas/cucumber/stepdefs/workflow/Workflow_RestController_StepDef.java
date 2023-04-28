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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFDistributionStep;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFLineManufacturingMaterialReceipt;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingReceivingTargetValues;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingStep;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFPickingStep;
import de.metas.cucumber.stepdefs.workflow.dto.WFActivityId;
import de.metas.cucumber.stepdefs.workflow.dto.WFProcessId;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonReceivingTarget;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
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
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_QtyPicked;
import static org.assertj.core.api.Assertions.*;

public class Workflow_RestController_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper()
			.registerModule(new JavaTimeModule());

	private final static String PICKING_ACTIVITY_COMPONENT_TYPE = "picking/pickProducts";
	private final static String DISTRIBUTION_ACTIVITY_COMPONENT_TYPE = "distribution/move";
	private final static String MANUFACTURING_ISSUE_TO_ACTIVITY_COMPONENT_TYPE = "manufacturing/rawMaterialsIssue";
	private final static String MANUFACTURING_RECEIVE_FROM_ACTIVITY_COMPONENT_TYPE = "manufacturing/materialReceipt";

	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final DD_Order_StepDefData ddOrderTable;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFPickingStep_StepDefData workflowPickingStepTable;
	private final JsonWFDistributionStep_StepDefData workflowDistributionStepTable;
	private final JsonWFManufacturingStep_StepDefData workflowManufacturingStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;
	private final JsonWFLineManufacturingMaterialReceipt_StepDefData materialReceiptLineTable;
	private final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable;
	private final TestContext testContext;

	public Workflow_RestController_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final DD_Order_StepDefData ddOrderTable,
			@NonNull final JsonWFProcess_StepDefData workflowProcessTable,
			@NonNull final JsonWFActivity_StepDefData workflowActivityTable,
			@NonNull final JsonWFPickingStep_StepDefData workflowPickingStepTable,
			@NonNull final JsonWFDistributionStep_StepDefData workflowDistributionStepTable,
			@NonNull final JsonWFManufacturingStep_StepDefData workflowManufacturingStepTable,
			@NonNull final JsonWFHQRCode_StepDefData qrCodeTable,
			@NonNull final JsonWFLineManufacturingMaterialReceipt_StepDefData materialReceiptLineTable,
			@NonNull final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable,
			@NonNull final TestContext testContext
	)
	{
		this.orderTable = orderTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.bPartnerTable = bPartnerTable;
		this.testContext = testContext;
		this.ppOrderTable = ppOrderTable;
		this.ddOrderTable = ddOrderTable;
		this.workflowProcessTable = workflowProcessTable;
		this.workflowActivityTable = workflowActivityTable;
		this.workflowPickingStepTable = workflowPickingStepTable;
		this.workflowDistributionStepTable = workflowDistributionStepTable;
		this.workflowManufacturingStepTable = workflowManufacturingStepTable;
		this.qrCodeTable = qrCodeTable;
		this.receivingTargetValuesTable = receivingTargetValuesTable;
		this.materialReceiptLineTable = materialReceiptLineTable;
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

	@And("create JsonWFProcessStartRequest for picking and store it in context as request payload:")
	public void wf_picking_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);

		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);

		final String salesOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Order.COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Order salesOrder = orderTable.get(salesOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", PickingMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("salesOrderId", salesOrder.getC_Order_ID());
		wfParams.put("customerId", bPartner.getC_BPartner_ID());
		wfParams.put("customerLocationId", bPartnerLocation.getC_BPartner_Location_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:")
	public void wf_manufacturing_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", ManufacturingMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("ppOrderId", ppOrder.getPP_Order_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("create JsonWFProcessStartRequest for distribution and store it in context as request payload:")
	public void wf_distribution_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_DD_Order ddOrder = ddOrderTable.get(ddOrderIdentifier);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", DistributionMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("ddOrderId", ddOrder.getDD_Order_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(request));
	}

	@And("process response and extract picking step and main HU picking candidate:")
	public void extract_picking_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = testContext.getApiResponse().getContent();

		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessId);

		final JsonNode activities = response.at("/activities");

		for (final JsonNode activityNode : activities)
		{
			if (!PICKING_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.of(activityNode.at("/activityId").asText());

			final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
			workflowActivityTable.put(wfActivityIdentifier, workflowActivity);

			final JsonNode pickingActivityLines = activityNode.at("/componentProps/lines");

			assertThat(pickingActivityLines.size()).isEqualTo(1);

			final JsonNode workflowSteps = pickingActivityLines.get(0).at("/steps");

			assertThat(workflowSteps.size()).isEqualTo(1);

			final JsonWFPickingStep workflowPickingStep = objectMapper.treeToValue(workflowSteps.get(0), JsonWFPickingStep.class);

			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
			workflowPickingStepTable.put(wfStepIdentifier, workflowPickingStep);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			qrCodeTable.put(qrCodeIdentifier, workflowPickingStep.getMainPickFrom().getHuQRCode());
		}
	}

	@And("process response and extract distribution step and pickFrom HU distribution candidate:")
	public void extract_distribution_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = testContext.getApiResponse().getContent();

		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessId);

		final JsonNode activities = response.at("/activities");

		for (final JsonNode activityNode : activities)
		{
			if (!DISTRIBUTION_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.of(activityNode.at("/activityId").asText());

			final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
			workflowActivityTable.put(wfActivityIdentifier, workflowActivity);

			final JsonNode distributionActivityLines = activityNode.at("/componentProps/lines");

			assertThat(distributionActivityLines.size()).isEqualTo(1);

			final JsonNode workflowSteps = distributionActivityLines.get(0).at("/steps");

			assertThat(workflowSteps.size()).isEqualTo(1);

			final JsonWFDistributionStep workflowDistributionStep = objectMapper.treeToValue(workflowSteps.get(0), JsonWFDistributionStep.class);

			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
			workflowDistributionStepTable.put(wfStepIdentifier, workflowDistributionStep);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			qrCodeTable.put(qrCodeIdentifier, workflowDistributionStep.getPickFromHU().getQrCode());
		}
	}

	@And("process response and extract manufacturing step and issueTo HU manufacturing candidate:")
	public void extract_manufacturing_issue_to_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = testContext.getApiResponse().getContent();

		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessId);

		final JsonNode activities = response.at("/activities");

		for (final JsonNode activityNode : activities)
		{
			if (!MANUFACTURING_ISSUE_TO_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.of(activityNode.at("/activityId").asText());

			final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
			workflowActivityTable.put(wfActivityIdentifier, workflowActivity);

			final JsonNode manufacturingActivityLines = activityNode.at("/componentProps/lines");

			assertThat(manufacturingActivityLines.size()).isEqualTo(1);

			final JsonNode workflowSteps = manufacturingActivityLines.get(0).at("/steps");

			assertThat(workflowSteps.size()).isEqualTo(1);

			final JsonWFManufacturingStep workflowManufacturingStep = objectMapper.treeToValue(workflowSteps.get(0), JsonWFManufacturingStep.class);

			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
			workflowManufacturingStepTable.put(wfStepIdentifier, workflowManufacturingStep);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			qrCodeTable.put(qrCodeIdentifier, workflowManufacturingStep.getHuQRCode());
		}
	}

	@And("process response and extract manufacturing line and receiving target values:")
	public void extract_manufacturing_receive_from_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = testContext.getApiResponse().getContent();

		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessId);

		final JsonNode activities = response.at("/activities");

		for (final JsonNode activityNode : activities)
		{
			if (!MANUFACTURING_RECEIVE_FROM_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.of(activityNode.at("/activityId").asText());

			final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
			workflowActivityTable.put(wfActivityIdentifier, workflowActivity);

			final JsonNode manufacturingActivityLines = activityNode.at("/componentProps/lines");

			assertThat(manufacturingActivityLines.size()).isEqualTo(1);

			final JsonNode workflowLine = manufacturingActivityLines.get(0);

			final JsonWFLineManufacturingMaterialReceipt materialReceiptLine = objectMapper.treeToValue(workflowLine, JsonWFLineManufacturingMaterialReceipt.class);

			final String workflowLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowLine.Identifier");
			materialReceiptLineTable.put(workflowLineIdentifier, materialReceiptLine);

			final JsonNode availableReceivingTargetValues = workflowLine.at("/availableReceivingTargets/values");

			assertThat(availableReceivingTargetValues.size()).isEqualTo(1);

			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = objectMapper.treeToValue(availableReceivingTargetValues.get(0), JsonWFManufacturingReceivingTargetValues.class);

			final String wfReceivingTargetValuesIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowReceivingTargetValues.Identifier");
			receivingTargetValuesTable.put(wfReceivingTargetValuesIdentifier, receivingTargetValues);
		}
	}

	@And("create JsonPickingEventsList and store it in context as request payload:")
	public void picking_events_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = CollectionUtils.singleElement(dataTable.asMaps());

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final WFProcessId workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final WFActivityId workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final String wfPickingStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
		final JsonWFPickingStep workflowPickingStep = workflowPickingStepTable.get(wfPickingStepIdentifier);

		final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
		final JsonHUQRCode stepQRCode = qrCodeTable.get(qrCodeIdentifier);

		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_QtyPicked);

		final JsonPickingStepEvent pickingStepEvent = JsonPickingStepEvent.builder()
				.wfProcessId(workflowProcess.getId())
				.wfActivityId(workflowActivity.getActivityId())
				.type(JsonPickingStepEvent.EventType.PICK)
				.pickingStepId(workflowPickingStep.getPickingStepId())
				.huQRCode(stepQRCode.getCode())
				.qtyPicked(qtyPicked)
				.build();

		final JsonPickingEventsList pickingEventsList = JsonPickingEventsList.builder()
				.events(ImmutableList.of(pickingStepEvent))
				.build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(pickingEventsList));
	}

	@And("create JsonDistributionEvent and store it in context as request payload:")
	public void distribution_event_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final WFProcessId workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final WFActivityId workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final String wfDistributionStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
		final JsonWFDistributionStep workflowDistributionStep = workflowDistributionStepTable.get(wfDistributionStepIdentifier);

		final String event = DataTableUtil.extractStringForColumnName(tableRow, "Event");

		final JsonDistributionEvent.JsonDistributionEventBuilder distributionEventBuilder = JsonDistributionEvent.builder()
				.wfProcessId(workflowProcess.getId())
				.wfActivityId(workflowActivity.getActivityId())
				.distributionStepId(workflowDistributionStep.getId());

		if (event.equals("PickFrom"))
		{
			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			final JsonHUQRCode stepQRCode = qrCodeTable.get(qrCodeIdentifier);

			distributionEventBuilder.pickFrom(JsonDistributionEvent.PickFrom.builder().qrCode(stepQRCode.getCode()).build());
		}
		else if (event.equals("DropTo"))
		{
			distributionEventBuilder.dropTo(JsonDistributionEvent.DropTo.builder().build());
		}

		testContext.setRequestPayload(objectMapper.writeValueAsString(distributionEventBuilder.build()));
	}

	@And("create JsonManufacturingOrderEvent and store it in context as request payload:")
	public void manufacturing_event_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String event = DataTableUtil.extractStringForColumnName(tableRow, "Event");

		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowProcess.Identifier");
		final WFProcessId workflowProcess = workflowProcessTable.get(wfProcessIdentifier);

		final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowActivity.Identifier");
		final WFActivityId workflowActivity = workflowActivityTable.get(wfActivityIdentifier);

		final JsonManufacturingOrderEvent.JsonManufacturingOrderEventBuilder manufacturingOrderEventBuilder = JsonManufacturingOrderEvent.builder();

		if (event.equals("IssueTo"))
		{
			final String wfStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStep.Identifier");
			final JsonWFManufacturingStep workflowStep = workflowManufacturingStepTable.get(wfStepIdentifier);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowStepQRCode.Identifier");
			final JsonHUQRCode qrCode = qrCodeTable.get(qrCodeIdentifier);

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
			final JsonWFLineManufacturingMaterialReceipt workflowLine = materialReceiptLineTable.get(wfLineIdentifier);

			final String wfReceivingTargetValuesIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "WorkflowReceivingTargetValues.Identifier");
			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = this.receivingTargetValuesTable.get(wfReceivingTargetValuesIdentifier);

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getId())
					.wfActivityId(workflowActivity.getActivityId())
					.receiveFrom(JsonManufacturingOrderEvent.ReceiveFrom.builder()
										 .lineId(workflowLine.getId())
										 .qtyReceived(workflowLine.getQtyToReceive())
										 .aggregateToLU(JsonReceivingTarget.builder()
																.newLU(JsonNewLUTarget.builder()
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