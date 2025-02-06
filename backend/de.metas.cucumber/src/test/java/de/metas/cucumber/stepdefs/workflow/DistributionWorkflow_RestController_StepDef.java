package de.metas.cucumber.stepdefs.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.cucumber.stepdefs.APIResponse;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.distribution.workflows_api.DistributionJobLineId;
import de.metas.distribution.workflows_api.DistributionJobStepId;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.distribution.workflows_api.activity_handlers.MoveWFActivityHandler;
import de.metas.distribution.workflows_api.json.JsonDistributionJobLine;
import de.metas.distribution.workflows_api.json.JsonDistributionJobStep;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcessId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class DistributionWorkflow_RestController_StepDef
{
	private final com.fasterxml.jackson.databind.ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final DD_Order_StepDefData ddOrderTable;
	private final JsonWFProcess_StepDefData workflowProcessTable;
	private final JsonWFActivity_StepDefData workflowActivityTable;
	private final JsonWFDistributionStep_StepDefData workflowDistributionStepTable;
	private final JsonWFHQRCode_StepDefData qrCodeTable;
	private final TestContext testContext;

	@And("create JsonWFProcessStartRequest for distribution and store it in context as request payload:")
	public void wf_distribution_process_start_set_request_payload_in_context(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final StepDefDataIdentifier ddOrderIdentifier = row.getAsIdentifier(I_DD_Order.COLUMNNAME_DD_Order_ID);
		final JsonWFProcessStartRequest request = createJsonWFProcessStartRequest(ddOrderIdentifier);
		testContext.setRequestPayload(request);
	}

	public JsonWFProcessStartRequest createJsonWFProcessStartRequest(@NonNull final StepDefDataIdentifier ddOrderIdentifier)
	{
		final DDOrderId ddOrderId = ddOrderIdentifier.lookupIdIn(ddOrderTable);

		final Map<String, Object> wfParams = new HashMap<>();
		wfParams.put("applicationId", DistributionMobileApplication.APPLICATION_ID.getAsString());
		wfParams.put("ddOrderId", ddOrderId.getRepoId());
		return JsonWFProcessStartRequest.builder().wfParameters(wfParams).build();

	}

	@Value
	@Builder
	public static class WFProcessResponse
	{
		@NonNull WFProcessId wfProcessId;
		@NonNull WFActivityId wfActivityId;
		@Nullable JsonDistributionJobLine line;
		@Nullable WFActivityId lastConfirmActivityId;

		public DistributionJobLineId getLineId() {return line != null ? line.getLineId() : null;}

		public DistributionJobStepId getSingleStepIdOrNull()
		{
			final JsonDistributionJobStep step = getSingleStepOrNull();
			return step != null ? step.getId() : null;
		}

		@Nullable
		public JsonDistributionJobStep getSingleStepOrNull()
		{
			if (line == null)
			{
				return null;
			}

			final List<JsonDistributionJobStep> steps = line.getSteps();
			return steps.isEmpty() ? null : CollectionUtils.singleElement(steps);
		}

		public JsonDisplayableQRCode getStepPickFromHUQRCode()
		{
			final JsonDistributionJobStep step = getSingleStepOrNull();
			return step != null ? step.getPickFromHU().getQrCode() : null;
		}
	}

	public static WFProcessResponse extractWFProcessResponse(APIResponse apiResponse)
	{
		try
		{
			final JsonNode json = apiResponse.getContentAs(JsonNode.class);
			final WFProcessResponse.WFProcessResponseBuilder result = WFProcessResponse.builder();
			final WFProcessId wfProcessId = WFProcessId.ofString(json.at("/id").asText());
			result.wfProcessId(wfProcessId);

			for (final JsonNode activityNode : json.at("/activities"))
			{
				final UIComponentType componentType = UIComponentType.ofString(activityNode.at("/componentType").asText());
				if (MoveWFActivityHandler.COMPONENT_TYPE.equals(componentType))
				{
					final WFActivityId wfActivityId = WFActivityId.ofString(activityNode.at("/activityId").asText());
					result.wfActivityId(wfActivityId);

					final JsonNode linesArrayNode = activityNode.at("/componentProps/lines");
					assertThat(linesArrayNode.size()).isOne();
					final JsonNode lineNode = linesArrayNode.get(0);

					final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
					final JsonDistributionJobLine line = jsonObjectMapper.treeToValue(lineNode, JsonDistributionJobLine.class);
					result.line(line);
				}
				else if (UIComponentType.CONFIRM_BUTTON.equals(componentType))
				{
					final WFActivityId wfActivityId = WFActivityId.ofString(activityNode.at("/activityId").asText());
					result.lastConfirmActivityId(wfActivityId);
				}
				else
				{
					continue;
				}
			}

			return result.build();
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed parsing json: " + apiResponse.getContent(), e);
		}
	}

	@And("process response and extract distribution step and pickFrom HU distribution candidate:")
	public void extract_distribution_information(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final WFProcessResponse wfProcess = extractWFProcessResponse(testContext.getApiResponse());
		row.getAsIdentifier("WorkflowProcess").putOrReplace(workflowProcessTable, wfProcess.getWfProcessId());
		row.getAsIdentifier("WorkflowActivity").put(workflowActivityTable, wfProcess.getWfActivityId());
		row.getAsIdentifier("WorkflowStep").put(workflowDistributionStepTable, wfProcess.getSingleStepOrNull());
		qrCodeTable.put(row.getAsIdentifier("WorkflowStepQRCode"), wfProcess.getStepPickFromHUQRCode());
	}

	@And("create JsonDistributionEvent and store it in context as request payload:")
	public void distribution_event_request_payload(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final JsonDistributionEvent.JsonDistributionEventBuilder distributionEventBuilder = JsonDistributionEvent.builder()
				.wfProcessId(row.getAsIdentifier("WorkflowProcess").lookupIn(workflowProcessTable).getAsString())
				.wfActivityId(row.getAsIdentifier("WorkflowActivity").lookupIn(workflowActivityTable).getAsString())
				.distributionStepId(row.getAsIdentifier("WorkflowStep").lookupIn(workflowDistributionStepTable).getId());

		final String event = row.getAsString("Event");
		if (event.equals("PickFrom"))
		{
			final JsonHUQRCode stepQRCode = row.getAsIdentifier("WorkflowStepQRCode").lookupIn(qrCodeTable);
			distributionEventBuilder.pickFrom(JsonDistributionEvent.PickFrom.builder().qrCode(stepQRCode.getCode()).build());
		}
		else if (event.equals("DropTo"))
		{
			distributionEventBuilder.dropTo(JsonDistributionEvent.DropTo.builder().build());
		}

		testContext.setRequestPayload(jsonObjectMapper.writeValueAsString(distributionEventBuilder.build()));
	}
}
