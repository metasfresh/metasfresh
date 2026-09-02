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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFLineManufacturingMaterialReceipt;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingReceivingTargetValues;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFManufacturingStep;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonLUReceivingTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonTUReceivingTarget;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.order.OrderId;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
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
import org.compiere.model.I_C_Order;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class Workflow_RestController_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final static String MANUFACTURING_ISSUE_TO_ACTIVITY_COMPONENT_TYPE = "manufacturing/rawMaterialsIssue";
	private final static String MANUFACTURING_RECEIVE_FROM_ACTIVITY_COMPONENT_TYPE = "manufacturing/materialReceipt";

	private final C_Order_StepDefData orderTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFManufacturingStep_StepDefData workflowManufacturingStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;
	private final JsonWFLineManufacturingMaterialReceipt_StepDefData materialReceiptLineTable;
	private final JsonWFManufacturingReceivingTargetValues_StepDefData receivingTargetValuesTable;
	private final M_Attribute_StepDefData attributeTable;
	private final TestContext testContext;

	@And("update duration for AD_Workflow nodes")
	public void update_AD_Workflow_nodes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateADWorkflowNodes);
	}

	@And("create JsonWFProcessStartRequest for picking and store it in context as request payload:")
	public void wf_picking_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_C_Order salesOrder = row.getAsIdentifier(I_C_Order.COLUMNNAME_C_Order_ID).lookupIn(orderTable);
		assertThat(salesOrder).isNotNull();

		final LinkedHashMap<String, Object> wfParams = new LinkedHashMap<>(PickingWFProcessStartParams.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderId(OrderId.ofRepoId(salesOrder.getC_Order_ID()))
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(salesOrder.getC_BPartner_ID(), salesOrder.getC_BPartner_Location_ID()))
				.build()
				.toParams()
				.toJson());
		wfParams.put("applicationId", PickingMobileApplication.APPLICATION_ID.getAsString());
		final JsonWFProcessStartRequest request = JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

		testContext.setRequestPayload(request);
	}

	@And("create JsonWFProcessStartRequest for manufacturing and store it in context as request payload:")
	public void wf_manufacturing_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_PP_Order ppOrder = row.getAsIdentifier(I_PP_Order.COLUMNNAME_PP_Order_ID).lookupNotNullIn(ppOrderTable);

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

	/**
	 * Builds a {@link JsonManufacturingOrderEvent} from a single-row DataTable and stores it as the context
	 * request payload for a subsequent POST to {@code api/v2/manufacturing/event}.
	 * <p>
	 * Required columns (all events): {@code Event} ({@code IssueTo} or {@code ReceiveFrom}),
	 * {@code WorkflowProcess} / {@code WorkflowActivity} (identifiers resolved via the workflow-process /
	 * -activity tables).
	 * <p>
	 * {@code Event=IssueTo} — required: {@code WorkflowStep}, {@code WorkflowStepQRCode} (identifiers).
	 * <p>
	 * {@code Event=ReceiveFrom} — required: {@code WorkflowLine}, {@code WorkflowReceivingTargetValues}
	 * (identifiers). Optional: {@code ReceiveTo} ({@code TU} receives straight into top-level TUs; default
	 * aggregates to an LU), {@code CatchWeight} (e.g. {@code 0.5 KGM}), {@code BestBeforeDate},
	 * {@code ProductionDate}, {@code LotNo} (the deprecated dedicated fields — a mobile caller instead submits
	 * these through the generic map below), and one generic editable-attribute entry via {@code Attribute}
	 * (identifier resolved via {@link de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData}) +
	 * {@code AttributeValue}. A present {@code Attribute} with an absent/blank {@code AttributeValue} submits a
	 * null map value (proving an empty submission is not stamped, vs. no attribute submitted at all).
	 * <p>
	 * Example:
	 * <pre>
	 * And create JsonManufacturingOrderEvent and store it in context as request payload:
	 *   | Event       | ReceiveTo | Attribute       | AttributeValue | WorkflowProcess.Identifier | WorkflowActivity.Identifier  | WorkflowLine.Identifier          | WorkflowReceivingTargetValues.Identifier |
	 *   | ReceiveFrom | TU        | genericDateAttr | 2025-04-15     | manufacturingWorkflow      | workflowManufacturingReceipt | workflowManufacturingReceiptLine | workflowReceivingTargetValues            |
	 * </pre>
	 */
	@And("create JsonManufacturingOrderEvent and store it in context as request payload:")
	public void manufacturing_event_request_payload(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final String event = row.getAsString("Event");
		final WFProcessId workflowProcess = row.getAsIdentifier("WorkflowProcess").lookupNotNullIn(workflowProcessTable);
		final WFActivityId workflowActivity = row.getAsIdentifier("WorkflowActivity").lookupNotNullIn(workflowActivityTable);

		final JsonManufacturingOrderEvent.JsonManufacturingOrderEventBuilder manufacturingOrderEventBuilder = JsonManufacturingOrderEvent.builder();

		if (event.equals("IssueTo"))
		{
			final JsonWFManufacturingStep workflowStep = row.getAsIdentifier("WorkflowStep").lookupNotNullIn(workflowManufacturingStepTable);
			final JsonHUQRCode qrCode = row.getAsIdentifier("WorkflowStepQRCode").lookupNotNullIn(qrCodeTable);

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
			final JsonWFLineManufacturingMaterialReceipt workflowLine = row.getAsIdentifier("WorkflowLine").lookupNotNullIn(materialReceiptLineTable);
			final JsonWFManufacturingReceivingTargetValues receivingTargetValues = row.getAsIdentifier("WorkflowReceivingTargetValues").lookupNotNullIn(receivingTargetValuesTable);

			final Quantity catchWeight = row.getAsOptionalQuantity("CatchWeight", uomDAO::getByX12DE355)
					.orElse(null);

			// ReceiveTo=TU receives straight into (possibly several) top-level TUs, without an LU wrapper -
			// used to prove a generic attribute value lands on EVERY produced HU of the line.
			final boolean receiveToTUOnly = row.getAsOptionalString("ReceiveTo").map("TU"::equalsIgnoreCase).orElse(false);

			final JsonManufacturingOrderEvent.ReceiveFrom.ReceiveFromBuilder receiveFromBuilder = JsonManufacturingOrderEvent.ReceiveFrom.builder()
					.lineId(workflowLine.getId())
					.qtyReceived(workflowLine.getQtyToReceive())
					.bestBeforeDate(row.getAsOptionalString("BestBeforeDate").orElse(null))
					.productionDate(row.getAsOptionalString("ProductionDate").orElse(null))
					.catchWeight(catchWeight != null ? catchWeight.toBigDecimal() : null)
					.catchWeightUomSymbol(catchWeight != null ? catchWeight.getUOMSymbol() : null)
					.lotNo(row.getAsOptionalString("LotNo").orElse(null))
					.attributes(extractGenericAttributes(row));

			if (receiveToTUOnly)
			{
				receiveFromBuilder.aggregateToTU(JsonTUReceivingTarget.builder()
						.newTU(JsonNewTUTarget.builder()
									   .caption(receivingTargetValues.getTuCaption())
									   .tuPIItemProductId(HUPIItemProductId.ofRepoId(receivingTargetValues.getTuPIItemProductId()))
									   .build())
						.build());
			}
			else
			{
				receiveFromBuilder.aggregateToLU(JsonLUReceivingTarget.builder()
						.newLU(JsonNewLUTarget.builder()
									   .luCaption(receivingTargetValues.getLuCaption())
									   .tuCaption(receivingTargetValues.getTuCaption())
									   .luPIItemId(HuPackingInstructionsItemId.ofRepoId(receivingTargetValues.getLuPIItemId()))
									   .tuPIItemProductId(HUPIItemProductId.ofRepoId(receivingTargetValues.getTuPIItemProductId()))
									   .build())
						.build());
			}

			manufacturingOrderEventBuilder
					.wfProcessId(workflowProcess.getAsString())
					.wfActivityId(workflowActivity.getAsString())
					.receiveFrom(receiveFromBuilder.build());
		}

		testContext.setRequestPayload(manufacturingOrderEventBuilder.build());
	}

	/**
	 * Builds the generic editable-attribute value list from the optional {@code Attribute} (identifier, resolved
	 * via {@link M_Attribute_StepDefData}) + {@code AttributeValue} columns. A blank/absent {@code AttributeValue}
	 * still produces a list entry with a {@code null} value - this is how scenarios prove that an empty submitted
	 * value is not stamped, as opposed to no attribute being submitted at all.
	 */
	@Nullable
	private List<JsonManufacturingOrderEvent.Attribute> extractGenericAttributes(@NonNull final DataTableRow row)
	{
		return row.getAsOptionalIdentifier("Attribute")
				.map(identifier -> identifier.lookupNotNullIn(attributeTable))
				.map(attribute -> Collections.singletonList(
						JsonManufacturingOrderEvent.Attribute.builder()
								.code(attribute.getAttributeCode())
								.value(row.getAsOptionalString("AttributeValue").orElse(null))
								.build()))
				.orElse(null);
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