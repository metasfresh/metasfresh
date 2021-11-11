package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.CompletePickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.product.ProductId;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.service.WFActivityHandlersRegistry;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class PickingMobileApplicationTest
{
	private PickingJobTestHelper helper;
	private PickingMobileApplication pickingMobileApplication;
	private WorkflowRestController workflowRestController;
	private PickingRestController pickingRestController;

	// Masterdata
	final UserId loggedUserId = UserId.ofRepoId(1234);
	final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(1, 2);

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());
	}

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();

		Env.setLoggedUserId(Env.getCtx(), loggedUserId);

		final PickingJobRestService pickingJobRestService = new PickingJobRestService(helper.pickingJobService);
		pickingMobileApplication = new PickingMobileApplication(pickingJobRestService);

		final WorkflowRestAPIService workflowRestAPIService = new WorkflowRestAPIService(
				Optional.of(ImmutableList.of(pickingMobileApplication)),
				new WFActivityHandlersRegistry(Optional.of(ImmutableList.of(
						new SetPickingSlotWFActivityHandler(pickingJobRestService),
						new ActualPickingWFActivityHandler(pickingJobRestService),
						new CompletePickingWFActivityHandler(pickingJobRestService)
				)))
		);
		workflowRestController = new WorkflowRestController(workflowRestAPIService);
		pickingRestController = new PickingRestController(pickingMobileApplication);

		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "130");
		System.out.println("VHU1: " + vhu1);
		final HuId vhu2 = helper.createVHU(productId, "70");
		System.out.println("VHU2: " + vhu2);

		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.date(Instant.parse("2021-11-09T08:58:52Z"))
				.build();
	}

	private JsonWFProcess startWFProcess()
	{
		final HashMap<String, Object> wfParameters = new HashMap<>(PickingWFProcessStartParams.builder()
				.salesOrderId(orderAndLineId.getOrderId())
				.deliveryBPLocationId(helper.shipToBPLocationId)
				.build()
				.toParams()
				.toJson());

		wfParameters.put(JsonWFProcessStartRequest.PARAM_ApplicationId, PickingMobileApplication.HANDLER_ID.getAsString());

		final JsonWFProcess wfProcess = workflowRestController.start(JsonWFProcessStartRequest.builder()
				.wfParameters(wfParameters)
				.build());
		System.out.println("Created " + wfProcess);
		return wfProcess;
	}

	@Test
	void createJobAndGet()
	{
		final JsonWFProcess wfProcess = startWFProcess();
		expect(wfProcess).toMatchSnapshot();

		final JsonWFProcess wfProcessLoaded = workflowRestController.getWFProcessById(wfProcess.getId());
		Assertions.assertThat(wfProcessLoaded)
				.usingRecursiveComparison()
				.isEqualTo(wfProcess);
	}

	@Test
	void abortJob()
	{
		JsonWFProcess wfProcess = startWFProcess();
		workflowRestController.abort(wfProcess.getId());

		wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
		expect(wfProcess).toMatchSnapshot();
	}

	@Test
	void completeJob()
	{
		JsonWFProcess wfProcess = startWFProcess();
		final PickingJob pickingJob = pickingMobileApplication.getWFProcessById(WFProcessId.ofString(wfProcess.getId())).getDocumentAs(PickingJob.class);
		final PickingJobStep step = helper.extractSingleStep(pickingJob);
		final JsonWFActivity pickingActivity = wfProcess.getActivities()
				.stream()
				.filter(activity -> activity.getComponentType().equals(ActualPickingWFActivityHandler.COMPONENTTYPE_PICK_PRODUCTS.getAsString()))
				.findFirst()
				.get();
		final JsonWFActivity completeActivity = wfProcess.getActivities().get(wfProcess.getActivities().size() - 1);

		pickingRestController.postEvents(
				JsonPickingEventsList.builder()
						.events(ImmutableList.of(
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingStepId(step.getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huBarcode(step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getBarcode().getAsString())
										.qtyPicked(new BigDecimal("100"))
										.build()
						))
						.build());

		workflowRestController.setUserConfirmation(wfProcess.getId(), completeActivity.getActivityId());

		wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
		expect(wfProcess).toMatchSnapshot();
	}

	@Test
	void pickUsingAlternatives()
	{
		JsonWFProcess wfProcess = startWFProcess();
		final PickingJob pickingJob = pickingMobileApplication.getWFProcessById(WFProcessId.ofString(wfProcess.getId())).getDocumentAs(PickingJob.class);
		final PickingJobStep step = helper.extractSingleStep(pickingJob);
		final PickingJobStepPickFrom pickFromMain = step.getPickFrom(PickingJobStepPickFromKey.MAIN);
		final PickingJobStepPickFromKey pickFromAlternativeKey = step.getPickFromKeys().stream().filter(PickingJobStepPickFromKey::isAlternative).findFirst().get();
		final PickingJobStepPickFrom pickFromAlternative = step.getPickFrom(pickFromAlternativeKey);

		final JsonWFActivity pickingActivity = wfProcess.getActivities()
				.stream()
				.filter(activity -> activity.getComponentType().equals(ActualPickingWFActivityHandler.COMPONENTTYPE_PICK_PRODUCTS.getAsString()))
				.findFirst()
				.get();
		final JsonWFActivity completeActivity = wfProcess.getActivities().get(wfProcess.getActivities().size() - 1);

		pickingRestController.postEvents(
				JsonPickingEventsList.builder()
						.events(ImmutableList.of(
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingStepId(step.getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huBarcode(pickFromMain.getPickFromHU().getBarcode().getAsString())
										.qtyPicked(new BigDecimal("70"))
										.qtyRejectedReasonCode("damaged")
										.build(),
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingStepId(step.getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huBarcode(pickFromAlternative.getPickFromHU().getBarcode().getAsString())
										.qtyPicked(new BigDecimal("30"))
										.qtyRejected(new BigDecimal("1"))
										.qtyRejectedReasonCode("damaged")
										.build()
						))
						.build());

		workflowRestController.setUserConfirmation(wfProcess.getId(), completeActivity.getActivityId());

		wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
		expect(wfProcess).toMatchSnapshot();
	}

}