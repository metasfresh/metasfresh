/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.workflow.dto.JsonWFPickingStep;
import de.metas.cucumber.stepdefs.workflow.dto.WFActivityId;
import de.metas.cucumber.stepdefs.workflow.dto.WFProcessId;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final String content = testContext.getApiResponse().getContent();
		final JsonNode response = objectMapper.readValue(content, JsonNode.class);

		final WFProcessId wfProcessId = WFProcessId.of(response.at("/id").asText());
		row.getAsIdentifier("WorkflowProcess").putOrReplace(workflowProcessTable, wfProcessId);

		final JsonNode wfActivities = response.at("/activities");
		for (final JsonNode wfActivityNode : wfActivities)
		{
			if (!PICKING_ACTIVITY_COMPONENT_TYPE.equals(wfActivityNode.at("/componentType").asText()))
			{
				continue;
			}

			final WFActivityId wfActivityId = WFActivityId.of(wfActivityNode.at("/activityId").asText());
			row.getAsIdentifier("WorkflowActivity").put(workflowActivityTable, wfActivityId);

			final JsonNode pickingLinesNodes = wfActivityNode.at("/componentProps/lines");
			assertThat(pickingLinesNodes.size()).isOne();
			final JsonPickingJobLine pickingLine = objectMapper.treeToValue(pickingLinesNodes.get(0), JsonPickingJobLine.class);
			row.getAsIdentifier("PickingLine").put(workflowPickingLineTable, pickingLine);

			final JsonNode pickingStepsNodes = pickingLinesNodes.get(0).at("/steps");
			assertThat(pickingStepsNodes.size()).isOne();
			final JsonWFPickingStep pickingStep = objectMapper.treeToValue(pickingStepsNodes.get(0), JsonWFPickingStep.class);

			row.getAsIdentifier("PickingStep").put(workflowPickingStepTable, pickingStep);
			row.getAsIdentifier("PickingStepQRCode").put(qrCodeTable, pickingStep.getMainPickFrom().getHuQRCode());
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
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		return JsonPickingStepEvent.builder()
				.wfProcessId(row.getAsIdentifier("WorkflowProcess").lookupIn(workflowProcessTable).getId())
				.wfActivityId(row.getAsIdentifier("WorkflowActivity").lookupIn(workflowActivityTable).getActivityId())
				.type(JsonPickingStepEvent.EventType.PICK)
				// this field is and shall be present in master
				// .pickingLineId(row.getAsIdentifier("PickingLine").lookupIn(workflowPickingLineTable).getPickingLineId())
				.pickingStepId(row.getAsIdentifier("PickingStep").lookupIn(workflowPickingStepTable).getPickingStepId())
				.huQRCode(row.getAsIdentifier("PickingStepQRCode").lookupIn(qrCodeTable).getCode())
				.qtyPicked(row.getAsBigDecimal(COLUMNNAME_QtyPicked))
				.build();
	}
}
