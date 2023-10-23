/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFPickingStep;
import de.metas.cucumber.stepdefs.workflow.dto.WFActivityId;
import de.metas.cucumber.stepdefs.workflow.dto.WFProcessId;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.handlingunits.model.I_M_Picking_Candidate.COLUMNNAME_QtyPicked;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class PickingRestController_StepDef
{
	private static final String PICKING_ACTIVITY_COMPONENT_TYPE = "picking/pickProducts";

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private final TestContext testContext;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFPickingLine_StepDefData workflowPickingLineTable;
	private final JsonWFPickingStep_StepDefData workflowPickingStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;

	@And("process response and extract picking step and main HU picking candidate:")
	public void extract_picking_information(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> row = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = testContext.getApiResponse().getContent();
		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());
		final String wfProcessIdentifier = DataTableUtil.extractStringForColumnName(row, "WorkflowProcess.Identifier");
		workflowProcessTable.putOrReplace(wfProcessIdentifier, wfProcessId);

		final JsonNode wfActivities = response.at("/activities");
		for (final JsonNode wfActivityNode : wfActivities)
		{
			if (!PICKING_ACTIVITY_COMPONENT_TYPE.equals(wfActivityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId wfActivityId = WFActivityId.of(wfActivityNode.at("/activityId").asText());
			final String wfActivityIdentifier = DataTableUtil.extractStringForColumnName(row, "WorkflowActivity.Identifier");
			workflowActivityTable.put(wfActivityIdentifier, wfActivityId);

			final JsonNode pickingLinesNodes = wfActivityNode.at("/componentProps/lines");
			assertThat(pickingLinesNodes.size()).isOne();
			final JsonPickingJobLine pickingLine = objectMapper.treeToValue(pickingLinesNodes.get(0), JsonPickingJobLine.class);
			final String pickingLineIdentifier = DataTableUtil.extractStringForColumnName(row, "PickingLine.Identifier");
			workflowPickingLineTable.put(pickingLineIdentifier, pickingLine);

			final JsonNode pickingStepsNodes = pickingLinesNodes.get(0).at("/steps");
			assertThat(pickingStepsNodes.size()).isOne();
			final JsonWFPickingStep pickingStep = objectMapper.treeToValue(pickingStepsNodes.get(0), JsonWFPickingStep.class);

			final String pickingStepIdentifier = DataTableUtil.extractStringForColumnName(row, "PickingStep.Identifier");
			workflowPickingStepTable.put(pickingStepIdentifier, pickingStep);

			final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(row, "PickingStepQRCode.Identifier");
			qrCodeTable.put(qrCodeIdentifier, pickingStep.getMainPickFrom().getHuQRCode());
		}
	}

	@And("create JsonPickingEventsList and store it in context as request payload:")
	public void picking_events_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonPickingStepEvent pickingEvent = extractJsonPickingStepEvent(dataTable);
		testContext.setRequestPayload(objectMapper.writeValueAsString(pickingEvent));
	}

	private JsonPickingStepEvent extractJsonPickingStepEvent(final @NonNull DataTable dataTable)
	{
		final Map<String, String> row = CollectionUtils.singleElement(dataTable.asMaps());
		final WFProcessId workflowProcess = workflowProcessTable.get(DataTableUtil.extractStringForColumnName(row, "WorkflowProcess.Identifier"));
		final WFActivityId workflowActivity = workflowActivityTable.get(DataTableUtil.extractStringForColumnName(row, "WorkflowActivity.Identifier"));
		final JsonPickingJobLine pickingLine = workflowPickingLineTable.get(DataTableUtil.extractStringForColumnName(row, "PickingLine.Identifier"));
		final JsonWFPickingStep pickingStep = workflowPickingStepTable.get(DataTableUtil.extractStringForColumnName(row, "PickingStep.Identifier"));
		final JsonHUQRCode stepQRCode = qrCodeTable.get(DataTableUtil.extractStringForColumnName(row, "PickingStepQRCode.Identifier"));
		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyPicked);

		return JsonPickingStepEvent.builder()
				.wfProcessId(workflowProcess.getId())
				.wfActivityId(workflowActivity.getActivityId())
				.type(JsonPickingStepEvent.EventType.PICK)
				.pickingLineId(pickingLine.getPickingLineId())
				.pickingStepId(pickingStep.getPickingStepId())
				.huQRCode(stepQRCode.getCode())
				.qtyPicked(qtyPicked)
				.build();
	}
}
