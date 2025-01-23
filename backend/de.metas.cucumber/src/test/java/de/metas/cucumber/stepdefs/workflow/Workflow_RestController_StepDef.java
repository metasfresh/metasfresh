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
import de.metas.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFLineManufacturingMaterialReceipt;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingReceivingTargetValues;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingStep;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonReceivingTarget;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcessId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.eevolution.model.I_PP_Order;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Workflow_RestController_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final static String MANUFACTURING_ISSUE_TO_ACTIVITY_COMPONENT_TYPE = "manufacturing/rawMaterialsIssue";
	private final static String MANUFACTURING_RECEIVE_FROM_ACTIVITY_COMPONENT_TYPE = "manufacturing/materialReceipt";

	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFManufacturingStep_StepDefData workflowManufacturingStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;
	private final JsonWFLineManufacturingMaterialReceipt_StepDefData materialReceiptLineTable;
	private final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable;
	private final TestContext testContext;

	@And("update duration for AD_Workflow nodes")
	public void update_AD_Workflow_nodes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateADWorkflowNodes);
	}

	@And("create JsonWFProcessStartRequest for picking and store it in context as request payload:")
	public void wf_picking_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_C_BPartner bPartner = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupIn(bPartnerTable);
		final I_C_BPartner_Location bPartnerLocation = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID).lookupIn(bPartnerLocationTable);
		final I_C_Order salesOrder = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupIn(orderTable);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", PickingMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("salesOrderId", salesOrder.getC_Order_ID());
		wfParams.put("customerId", bPartner.getC_BPartner_ID());
		wfParams.put("customerLocationId", bPartnerLocation.getC_BPartner_Location_ID());
		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(request);
	}

	@And("create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:")
	public void wf_manufacturing_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_PP_Order ppOrder = row.getAsIdentifier(I_PP_Order.COLUMNNAME_PP_Order_ID).lookupIn(ppOrderTable);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", ManufacturingMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("ppOrderId", ppOrder.getPP_Order_ID());

		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(request);
	}

	@And("process response and extract manufacturing step and issueTo HU manufacturing candidate:")
	public void extract_manufacturing_issue_to_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final String content = testContext.getApiResponse().getContent();
		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.ofString(response.at("/id").asText());
		row.getAsIdentifier("WorkflowProcess").putOrReplace(workflowProcessTable, wfProcessId);

		for (final JsonNode activityNode : response.at("/activities"))
		{
			if (!MANUFACTURING_ISSUE_TO_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.ofString(activityNode.at("/activityId").asText());
			row.getAsIdentifier("WorkflowActivity").put(workflowActivityTable, workflowActivity);

			final JsonNode manufacturingActivityLines = activityNode.at("/componentProps/lines");
			assertThat(manufacturingActivityLines.size()).isOne();
			final JsonNode workflowSteps = manufacturingActivityLines.get(0).at("/steps");
			assertThat(workflowSteps.size()).isOne();
			final JsonWFManufacturingStep workflowManufacturingStep = objectMapper.treeToValue(workflowSteps.get(0), JsonWFManufacturingStep.class);
			row.getAsIdentifier("WorkflowStep").put(workflowManufacturingStepTable, workflowManufacturingStep);
			row.getAsIdentifier("WorkflowStepQRCode").put(qrCodeTable, workflowManufacturingStep.getHuQRCode());
		}
	}

	@And("process response and extract manufacturing line and receiving target values:")
	public void extract_manufacturing_receive_from_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final String content = testContext.getApiResponse().getContent();
		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.ofString(response.at("/id").asText());
		row.getAsIdentifier("WorkflowProcess").putOrReplace(workflowProcessTable, wfProcessId);

		for (final JsonNode activityNode : response.at("/activities"))
		{
			if (!MANUFACTURING_RECEIVE_FROM_ACTIVITY_COMPONENT_TYPE.equals(activityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId workflowActivity = WFActivityId.ofString(activityNode.at("/activityId").asText());
			row.getAsIdentifier("WorkflowActivity").put(workflowActivityTable, workflowActivity);

			final JsonNode manufacturingActivityLines = activityNode.at("/componentProps/lines");
			assertThat(manufacturingActivityLines.size()).isOne();
			final JsonNode workflowLine = manufacturingActivityLines.get(0);
			final JsonWFLineManufacturingMaterialReceipt materialReceiptLine = objectMapper.treeToValue(workflowLine, JsonWFLineManufacturingMaterialReceipt.class);
			row.getAsIdentifier("WorkflowLine").put(materialReceiptLineTable, materialReceiptLine);

			final JsonNode availableReceivingTargetValues = workflowLine.at("/availableReceivingTargets/values");
			assertThat(availableReceivingTargetValues.size()).isOne();
			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = objectMapper.treeToValue(availableReceivingTargetValues.get(0), JsonWFManufacturingReceivingTargetValues.class);
			row.getAsIdentifier("WorkflowReceivingTargetValues").put(receivingTargetValuesTable, receivingTargetValues);
		}
	}

	@And("create JsonManufacturingOrderEvent and store it in context as request payload:")
	public void manufacturing_event_request_payload(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final String event = row.getAsString("Event");
		final WFProcessId workflowProcess = row.getAsIdentifier("WorkflowProcess").lookupIn(workflowProcessTable);
		final WFActivityId workflowActivity = row.getAsIdentifier("WorkflowActivity").lookupIn(workflowActivityTable);

		final JsonManufacturingOrderEvent.JsonManufacturingOrderEventBuilder manufacturingOrderEventBuilder = JsonManufacturingOrderEvent.builder();

		if (event.equals("IssueTo"))
		{
			final JsonWFManufacturingStep workflowStep = row.getAsIdentifier("WorkflowStep").lookupIn(workflowManufacturingStepTable);
			final JsonHUQRCode qrCode = row.getAsIdentifier("WorkflowStepQRCode").lookupIn(qrCodeTable);

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getAsString())
					.wfActivityId(workflowActivity.getAsString())
					.issueTo(JsonManufacturingOrderEvent.IssueTo.builder()
							.issueStepId(workflowStep.getId())
							.qtyIssued(workflowStep.getQtyToIssue())
							.huQRCode(qrCode.getCode())
							.build());
		}
		else if (event.equals("ReceiveFrom"))
		{
			final JsonWFLineManufacturingMaterialReceipt workflowLine = row.getAsIdentifier("WorkflowLine").lookupIn(materialReceiptLineTable);
			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = row.getAsIdentifier("WorkflowReceivingTargetValues").lookupIn(receivingTargetValuesTable);

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getAsString())
					.wfActivityId(workflowActivity.getAsString())
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

		testContext.setRequestPayload(manufacturingOrderEventBuilder.build());
	}

	private void updateADWorkflowNodes(@NonNull final DataTableRow row)
	{
		final int id = row.getAsInt(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID);
		final int duration = row.getAsInt(I_AD_WF_Node.COLUMNNAME_Duration);

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