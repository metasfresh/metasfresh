package de.metas.cucumber.stepdefs.distributionorder;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.api.APIRequest;
import de.metas.cucumber.stepdefs.api.APIResponse;
import de.metas.cucumber.stepdefs.api.REST_API_StepDef;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.workflow.DistributionWorkflow_RestController_StepDef;
import de.metas.cucumber.stepdefs.workflow.DistributionWorkflow_RestController_StepDef.WFProcessResponse;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig.MobileUIDistributionConfigBuilder;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionEvent;
import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcessId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MobileDistribution_StepDef
{
	private static final String ENDPOINT_BASE_UserWorkflows = "api/v2/userWorkflows";
	private static final String ENDPOINT_StartWorkflow = ENDPOINT_BASE_UserWorkflows + "/wfProcess/start";
	private static final String ENDPOINT_Trolley = ENDPOINT_BASE_UserWorkflows + "/trolley";
	private static final String ENDPOINT_Event = "api/v2/distribution/event";

	// services
	private static final Logger logger = LogManager.getLogger(MobileDistribution_StepDef.class);
	private final MobileUIDistributionConfigRepository configRepository = SpringContextHolder.instance.getBean(MobileUIDistributionConfigRepository.class);
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	//
	private final DistributionWorkflow_RestController_StepDef distributionWorkflowRestControllerStepDef;
	private final M_HU_StepDefData huTable;
	private final M_Locator_StepDefData locatorTable;
	private final REST_API_StepDef restAPIStepDef;
	private final TestContext testContext;

	// state
	private WFProcessId wfProcessId;
	private WFActivityId wfActivityId;
	private DistributionJobLineId lineId;
	private DistributionJobStepId stepId;
	private WFActivityId lastConfirmActivityId;
	private boolean trolleySetInScenario;

	private APIResponse httpPOST(@NonNull final String path)
	{
		return httpPOST(requestBuilder -> requestBuilder.endpointPath(path)
				.payload(null)
				.expectedStatusCode(200)
				.build()
		);
	}

	private APIResponse httpPOST(@NonNull final String path, @Nullable final Object requestBody)
	{
		return httpPOST(requestBuilder -> requestBuilder.endpointPath(path)
				.payload(requestBody)
				.expectedStatusCode(200)
				.build()
		);
	}

	private APIResponse httpPOST(@NonNull final String path, @Nullable final Object requestBody, final int expectedStatusCode)
	{
		return httpPOST(requestBuilder -> requestBuilder.endpointPath(path)
				.payload(requestBody)
				.expectedStatusCode(expectedStatusCode)
				.build()
		);
	}

	private APIResponse httpPOST(@NonNull final Consumer<APIRequest.APIRequestBuilder> requestCustomizer)
	{
		APIRequest request = null;
		try
		{
			final APIRequest.APIRequestBuilder requestBuilder = restAPIStepDef.newAPIRequest().method("POST");
			requestCustomizer.accept(requestBuilder);
			request = requestBuilder.build();
			testContext.setRequestPayload(request.getPayload());

			restAPIStepDef.performHTTPRequest(request);
		}
		catch (IOException e)
		{
			throw new AdempiereException("Failed executing request: " + request, e);
		}

		return testContext.getApiResponse();
	}

	private void updateContextFromWFProcessResponse(final APIResponse response)
	{
		final WFProcessResponse wfProcess = DistributionWorkflow_RestController_StepDef.extractWFProcessResponse(response);
		this.wfProcessId = wfProcess.getWfProcessId();
		this.wfActivityId = wfProcess.getWfActivityId();
		this.lineId = wfProcess.getLineId();
		this.stepId = wfProcess.getSingleStepIdOrNull();
		this.lastConfirmActivityId = wfProcess.getLastConfirmActivityId();
	}

	@And("set mobile UI distribution profile")
	public void updateProfile(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		final MobileUIDistributionConfigBuilder newConfigBuilder = configRepository.getConfig().toBuilder();
		row.getAsOptionalBoolean("IsAllowPickingAnyHU").ifPresent(newConfigBuilder::allowPickingAnyHU);
		final MobileUIDistributionConfig newConfig = newConfigBuilder.build();
		configRepository.save(newConfig);
		logger.info("Config updated: {}", newConfig);
	}

	@And("^Start distribution job for dd_order identified by (.*)$")
	public void startDistributionJob(
			@NonNull final String ddOrderIdentifierStr,
			@SuppressWarnings("unused") @NonNull DataTable dataTable // required by cucumber :(
	)
	{
		final JsonWFProcessStartRequest request = distributionWorkflowRestControllerStepDef.createJsonWFProcessStartRequest(StepDefDataIdentifier.ofString(ddOrderIdentifierStr));
		final APIResponse response = httpPOST(ENDPOINT_StartWorkflow, request);
		updateContextFromWFProcessResponse(response);
	}

	@And("Pick HU for distribution job line")
	public void linePickFrom(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HuId huId = row.getAsIdentifier("M_HU_ID").lookupIdIn(huTable);
		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(huId);

		final JsonDistributionEvent request = JsonDistributionEvent.builder()
				.wfProcessId(wfProcessId.getAsString())
				.wfActivityId(wfActivityId.getAsString())
				.lineId(lineId)
				.pickFrom(JsonDistributionEvent.PickFrom.builder().qrCode(huQRCode.toGlobalQRCodeString()).build())
				.build();

		final String expectErrorContaining = row.getAsOptionalString("ExpectErrorContaining").map(StringUtils::trimBlankToNull).orElse(null);
		final Boolean expectErrorUserFriendly = row.getAsOptionalBoolean("ExpectErrorUserFriendly").toBooleanOrNull();
		final boolean isExpectError = expectErrorContaining != null || expectErrorUserFriendly != null;
		final int expectedStatusCode = isExpectError ? 422 : 200;
		final APIResponse response = httpPOST(requestBuilder -> requestBuilder.endpointPath(ENDPOINT_Event)
				.payload(request)
				.expectedStatusCode(expectedStatusCode)
				.expectedErrorMessageContaining(expectErrorContaining)
				.expectErrorUserFriendly(expectErrorUserFriendly)
				.build()
		);

		if (expectedStatusCode == 200)
		{
			updateContextFromWFProcessResponse(response);
		}
	}

	@And("Drop HU for distribution job line")
	public void lineDropTo()
	{
		final JsonDistributionEvent request = JsonDistributionEvent.builder()
				.wfProcessId(wfProcessId.getAsString())
				.wfActivityId(wfActivityId.getAsString())
				.lineId(lineId)
				.distributionStepId(stepId)
				.dropTo(JsonDistributionEvent.DropTo.builder().build())
				.build();

		final APIResponse response = httpPOST(ENDPOINT_Event, request);
		updateContextFromWFProcessResponse(response);
	}

	@And("Complete current distribution job")
	public void complete()
	{
		// private static final String ENDPOINT_StartWorkflow = "api/v2/userWorkflows/wfProcess/start";
		///wfProcess/{wfProcessId}/{wfActivityId}/userConfirmation

		final APIResponse response = httpPOST(ENDPOINT_BASE_UserWorkflows + "/wfProcess/" + wfProcessId.getAsString() + "/" + lastConfirmActivityId.getAsString() + "/userConfirmation");
		updateContextFromWFProcessResponse(response);
	}

	@And("^the mobile user's trolley is set to locator (.*)$")
	public void setTrolley(@NonNull final String locatorIdentifierStr)
	{
		final I_M_Locator locator = locatorTable.get(StepDefDataIdentifier.ofString(locatorIdentifierStr));
		final String scannedCode = LocatorQRCode.ofLocator(locator).toGlobalQRCodeJsonString();

		trolleySetInScenario = true; // set before the call, so that @After clears even if the call fails half-way
		httpPOST(ENDPOINT_Trolley, ImmutableMap.of("scannedCode", scannedCode));
	}

	@And("the mobile user's trolley is cleared")
	public void clearTrolley()
	{
		httpDELETE(ENDPOINT_Trolley);
	}

	@Then("the mobile user's trolley is empty")
	public void assertTrolleyEmpty()
	{
		final APIResponse response = httpGET(ENDPOINT_Trolley);
		assertThat(getTrolleyOrNull(response)).as("current trolley").isNull();
	}

	/**
	 * The trolley is in-memory state of {@code TrolleyService}, keyed by the API user; it would leak into the next scenario.
	 * Fires for every scenario, but only acts if that scenario set a trolley. A cleanup error is logged, not thrown,
	 * so it cannot mask the scenario's own failure.
	 */
	@After
	public void clearTrolleyAfterScenario()
	{
		if (!trolleySetInScenario)
		{
			return;
		}
		trolleySetInScenario = false;

		try
		{
			clearTrolley();
			assertTrolleyEmpty();
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed clearing the mobile user's trolley after scenario", ex);
		}
	}

	@Nullable
	private static JsonNode getTrolleyOrNull(@NonNull final APIResponse response)
	{
		try
		{
			final JsonNode trolley = response.getContentAs(JsonNode.class).get("trolley");
			return trolley == null || trolley.isNull() ? null : trolley;
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed parsing trolley response: " + response, e);
		}
	}

	private APIResponse httpGET(@NonNull final String path)
	{
		return httpNoPayload("GET", path);
	}

	private APIResponse httpDELETE(@NonNull final String path)
	{
		return httpNoPayload("DELETE", path);
	}

	private APIResponse httpNoPayload(@NonNull final String method, @NonNull final String path)
	{
		APIRequest request = null;
		try
		{
			request = restAPIStepDef.newAPIRequest().method(method).endpointPath(path).payload(null).expectedStatusCode(200).build();
			testContext.setRequestPayload(null);
			restAPIStepDef.performHTTPRequest(request);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed executing request: " + request, e);
		}
		return testContext.getApiResponse();
	}
}
