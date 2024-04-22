package de.metas.picking.workflow.handlers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.picking.PickingSlotConnectedComponent;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.picking.job.service.TestRecorder;
import de.metas.handlingunits.picking.job.service.commands.LUPackingInstructions;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.Language;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.config.MobileUIPickingUserProfileRepository;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.JsonPickingEventsList;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.PickingWFProcessStartParams;
import de.metas.picking.workflow.handlers.activity_handlers.ActualPickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.CompletePickingWFActivityHandler;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonSetScannedBarcodeRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.service.WFActivityHandlersRegistry;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(AdempiereTestWatcher.class)
class PickingMobileApplicationTest
{
	private PickingJobTestHelper helper;
	private WorkflowRestController workflowRestController;
	private PickingRestController pickingRestController;

	//
	// Masterdata
	private final UserId loggedUserId = UserId.ofRepoId(1234);
	private OrderAndLineId orderAndLineId;
	private HuId lu1;
	private HuId lu2;

	private TestRecorder recorder;
	private PickingSlotIdAndCaption pickingSlot;

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, PickingJobTestHelper.snapshotSerializeFunction);
	}

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		recorder = helper.newTestRecorder();

		// Needed because we take snapshots of date/time translatable strings,
		// and it seems the date/time formats differs from OS to OS or from JVM impl to JVM impl
		Language.setUseJUnitFixedFormats(true);

		Env.setLoggedUserId(Env.getCtx(), loggedUserId);
		Env.setAD_Language(Env.getCtx(), "de_DE");

		final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class);
		final IDocumentLocationBL documentLocationBL = new DummyDocumentLocationBL(partnerBL);
		final PickingJobRestService pickingJobRestService = new PickingJobRestService(helper.pickingJobService, new MobileUIPickingUserProfileRepository());
		final MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository = new MobileUIPickingUserProfileRepository();
		final WorkplaceService workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		final PickingMobileApplication pickingMobileApplication = new PickingMobileApplication(
				pickingJobRestService,
				mobileUIPickingUserProfileRepository,
				workplaceService,
				new DisplayValueProviderService(documentLocationBL),
				documentLocationBL);

		final WorkflowRestAPIService workflowRestAPIService = new WorkflowRestAPIService(
				Optional.of(ImmutableList.of(pickingMobileApplication)),
				new WFActivityHandlersRegistry(Optional.of(ImmutableList.of(
						new SetPickingSlotWFActivityHandler(pickingJobRestService),
						new ActualPickingWFActivityHandler(pickingJobRestService),
						new CompletePickingWFActivityHandler(pickingJobRestService)
				)))
		);
		workflowRestController = new WorkflowRestController(workflowRestAPIService);
		pickingRestController = new PickingRestController(pickingMobileApplication, workflowRestController);

		createMasterdata();

		SpringContextHolder.registerJUnitBean((PickingSlotConnectedComponent)slotId -> false);
	}

	private void createMasterdata()
	{
		pickingSlot = PickingSlotIdAndCaption.of(helper.createPickingSlot("1"), "1");

		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		final ProductId productId = BusinessTestHelper.createProductId("P1", uomKg);
		final HUPIItemProductId tuPackingInstructionsId = helper.createTUPackingInstructionsId("TU1", productId, Quantity.of("25", uomKg));
		final LUPackingInstructions luPackingInstructions = helper.createLUPackingInstructions("Pallet", tuPackingInstructionsId, QuantityTU.ofInt(10));

		//
		// Masterdata: HUs
		{
			lu1 = helper.createLU(luPackingInstructions, "75");
			helper.createQRCode(lu1, "QR-LU1");
			helper.dumpHU("LU1", lu1);

			lu2 = helper.createLU(luPackingInstructions, "250");
			helper.createQRCode(lu2, "QR-LU2");
			helper.dumpHU("LU2", lu2);
		}

		orderAndLineId = helper.createOrderAndLineId("salesOrder01");

		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.huPIItemProductId(tuPackingInstructionsId)
				.qtyToDeliver("100")
				.date(Instant.parse("2021-11-09T08:58:52Z"))
				.build();

		//
		// Mobile UI Picking profile
		{
			I_MobileUI_UserProfile_Picking record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking.class);
			record.setName("test");
			record.setIsAllowPickingAnyHU(false);
			record.setCreateShipmentPolicy(CreateShipmentPolicy.DO_NOT_CREATE.getCode());
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	private JsonWFProcess startWFProcess()
	{
		final HashMap<String, Object> wfParameters = new HashMap<>(PickingWFProcessStartParams.builder()
				.salesOrderId(orderAndLineId.getOrderId())
				.deliveryBPLocationId(helper.shipToBPLocationId)
				.build()
				.toParams()
				.toJson());

		wfParameters.put(JsonWFProcessStartRequest.PARAM_ApplicationId, PickingMobileApplication.APPLICATION_ID.getAsString());

		final JsonWFProcess wfProcess = workflowRestController.start(JsonWFProcessStartRequest.builder()
				.wfParameters(wfParameters)
				.build());
		record_WFProcess_PickingJob_AllHUs("After WFProcess started", wfProcess);

		return wfProcess;
	}

	private void assertEqualsToDatabaseVersion(final JsonWFProcess wfProcess)
	{
		final JsonWFProcess wfProcessFromDatabase = workflowRestController.getWFProcessById(wfProcess.getId());
		assertThat(wfProcess)
				.usingRecursiveComparison()
				.isEqualTo(wfProcessFromDatabase);
	}

	private PickingJob retrievePickingJobFromDatabase(final JsonWFProcess wfProcess)
	{
		final PickingJobId pickingJobId = WFProcessId.ofString(wfProcess.getId()).getRepoId(PickingJobId::ofRepoId);
		return helper.pickingJobService.getById(pickingJobId);
	}

	@NonNull
	private JsonWFActivity getFirstActivityByComponentType(final JsonWFProcess wfProcess, final UIComponentType uiComponentType)
	{
		return wfProcess.getActivities()
				.stream()
				.filter(activity -> activity.getComponentType().equals(uiComponentType.getAsString()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found for component type: " + uiComponentType + " in " + wfProcess));
	}

	private void record_WFProcess_PickingJob_AllHUs(@NonNull final String when, @NonNull final JsonWFProcess wfProcess)
	{
		recorder.reportStep(when + " - JsonWFProcess", wfProcess);
		recorder.reportStep(when + " - PickingJob", retrievePickingJobFromDatabase(wfProcess));
		recorder.reportStepWithAllHUs(when + " - HUs");
	}

	private String toQRCodeString(@NonNull final HuId huId)
	{
		final HUQRCode qrCode = helper.getQRCode(huId);
		return qrCode.toRenderedJson().getCode();
	}

	@Test
	void createJobAndGet()
	{
		final JsonWFProcess wfProcess = startWFProcess();
		assertEqualsToDatabaseVersion(wfProcess);

		recorder.assertMatchesSnapshot();
	}

	@Test
	void abortJob()
	{
		recorder.reportStep("AD_Language", Env.getAD_Language());

		JsonWFProcess wfProcess = startWFProcess();
		assertThat(wfProcess.isAllowAbort()).isTrue();

		workflowRestController.abort(wfProcess.getId());
		wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
		assertThat(wfProcess.isAllowAbort()).isFalse();
		record_WFProcess_PickingJob_AllHUs("After ABORT", wfProcess);

		recorder.assertMatchesSnapshot();
	}

	@Test
	void scanPickingSlot_pick_complete()
	{
		JsonWFProcess wfProcess = startWFProcess();

		//
		// Scan Barcode
		{
			wfProcess = workflowRestController.setScannedBarcode(
					wfProcess.getId(),
					getFirstActivityByComponentType(wfProcess, UIComponentType.SCAN_BARCODE).getActivityId(),
					JsonSetScannedBarcodeRequest.builder()
							.barcode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot).toGlobalQRCodeJsonString())
							.build()
			);
			assertEqualsToDatabaseVersion(wfProcess);
			record_WFProcess_PickingJob_AllHUs("After Scan PickingSlot", wfProcess);
		}

		//
		// Pick
		{
			final PickingJob pickingJob = retrievePickingJobFromDatabase(wfProcess);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final List<PickingJobStep> steps = line.getSteps();
			final JsonWFActivity pickingActivity = getFirstActivityByComponentType(wfProcess, ActualPickingWFActivityHandler.COMPONENTTYPE_PICK_PRODUCTS);

			try
			{
				pickingRestController.postEvent(
						JsonPickingStepEvent.builder()
								.wfProcessId(wfProcess.getId())
								.wfActivityId(pickingActivity.getActivityId())
								.pickingLineId(line.getId().getAsString())
								.pickingStepId(steps.get(0).getId().getAsString())
								.type(JsonPickingStepEvent.EventType.PICK)
								.huQRCode(toQRCodeString(lu1))
								.qtyPicked(QtyTU.THREE.toBigDecimal()) // 3x25kg
								.build()
				);
				wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
			}
			finally
			{
				record_WFProcess_PickingJob_AllHUs("After pick 1", wfProcess);
			}

			try
			{
				pickingRestController.postEvent(
						JsonPickingStepEvent.builder()
								.wfProcessId(wfProcess.getId())
								.wfActivityId(pickingActivity.getActivityId())
								.pickingLineId(line.getId().getAsString())
								.pickingStepId(steps.get(1).getId().getAsString())
								.type(JsonPickingStepEvent.EventType.PICK)
								.huQRCode(toQRCodeString(lu2))
								.qtyPicked(QtyTU.ONE.toBigDecimal()) // 1x25kg
								.build()
				);
				wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
			}
			finally
			{
				record_WFProcess_PickingJob_AllHUs("After pick 2", wfProcess);
			}
		}

		//
		// Complete
		{
			final JsonWFActivity completeActivity = wfProcess.getActivities().get(wfProcess.getActivities().size() - 1);
			wfProcess = workflowRestController.setUserConfirmation(wfProcess.getId(), completeActivity.getActivityId());
			assertEqualsToDatabaseVersion(wfProcess);
			record_WFProcess_PickingJob_AllHUs("After Complete", wfProcess);
		}

		recorder.assertMatchesSnapshot();
	}

	@Test
	void scanPickingSlot_pickUsingAlternatives_complete()
	{
		JsonWFProcess wfProcess = startWFProcess();
		final PickingJob pickingJob = retrievePickingJobFromDatabase(wfProcess);
		assertThat(pickingJob.isAllowPickingAnyHU()).isFalse();
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		final ImmutableList<PickingJobStep> steps = line.getSteps();

		final JsonWFActivity pickingActivity = getFirstActivityByComponentType(wfProcess, ActualPickingWFActivityHandler.COMPONENTTYPE_PICK_PRODUCTS);

		final JsonWFActivity completeActivity = wfProcess.getActivities().get(wfProcess.getActivities().size() - 1);

		pickingRestController.postEvents(
				JsonPickingEventsList.builder()
						.events(ImmutableList.of(
								//
								// First step:
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingLineId(line.getId().getAsString())
										.pickingStepId(steps.get(0).getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huQRCode(toQRCodeString(lu1))
										.qtyPicked(QtyTU.ONE.toBigDecimal()) // 1x25kg
										.qtyRejected(QtyTU.TWO.toBigDecimal()) // 2x25kg
										.qtyRejectedReasonCode("damaged")
										.build(),
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingLineId(line.getId().getAsString())
										.pickingStepId(steps.get(0).getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huQRCode(toQRCodeString(lu2))
										.qtyPicked(QtyTU.ONE.toBigDecimal()) // 1x25kg
										.qtyRejected(QtyTU.ONE.toBigDecimal()) // 1x25kg
										.qtyRejectedReasonCode("damaged")
										.build(),
								//
								// Second step:
								JsonPickingStepEvent.builder()
										.wfProcessId(wfProcess.getId())
										.wfActivityId(pickingActivity.getActivityId())
										.pickingLineId(line.getId().getAsString())
										.pickingStepId(steps.get(1).getId().getAsString())
										.type(JsonPickingStepEvent.EventType.PICK)
										.huQRCode(toQRCodeString(lu2))
										.qtyPicked(QtyTU.ONE.toBigDecimal()) // 1x25kg
										.build()
						))
						.build());
		wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
		record_WFProcess_PickingJob_AllHUs("After pick", wfProcess);

		wfProcess = workflowRestController.setUserConfirmation(wfProcess.getId(), completeActivity.getActivityId());
		assertEqualsToDatabaseVersion(wfProcess);
		record_WFProcess_PickingJob_AllHUs("After Complete", wfProcess);

		recorder.assertMatchesSnapshot();
	}

	@Test
	public void scanPickingSlot_pick_unpick_abort()
	{
		JsonWFProcess wfProcess = startWFProcess();

		//
		// Scan Barcode
		{
			wfProcess = workflowRestController.setScannedBarcode(
					wfProcess.getId(),
					getFirstActivityByComponentType(wfProcess, UIComponentType.SCAN_BARCODE).getActivityId(),
					JsonSetScannedBarcodeRequest.builder()
							.barcode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot).toGlobalQRCodeJsonString())
							.build()
			);
			assertEqualsToDatabaseVersion(wfProcess);
			record_WFProcess_PickingJob_AllHUs("After Scan PickingSlot", wfProcess);
		}

		//
		// Pick / Unpick
		{
			final PickingJob pickingJob = retrievePickingJobFromDatabase(wfProcess);
			final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
			final List<PickingJobStep> steps = line.getSteps();
			final JsonWFActivity pickingActivity = getFirstActivityByComponentType(wfProcess, ActualPickingWFActivityHandler.COMPONENTTYPE_PICK_PRODUCTS);

			//
			// Pick
			pickingRestController.postEvent(
					JsonPickingStepEvent.builder()
							.wfProcessId(wfProcess.getId())
							.wfActivityId(pickingActivity.getActivityId())
							.pickingLineId(line.getId().getAsString())
							.pickingStepId(steps.get(0).getId().getAsString())
							.type(JsonPickingStepEvent.EventType.PICK)
							.huQRCode(toQRCodeString(lu1))
							.qtyPicked(QtyTU.THREE.toBigDecimal()) // 3x25kg
							.build());
			wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
			record_WFProcess_PickingJob_AllHUs("After pick 1", wfProcess);

			//
			// Unpick
			pickingRestController.postEvent(
					JsonPickingStepEvent.builder()
							.wfProcessId(wfProcess.getId())
							.wfActivityId(pickingActivity.getActivityId())
							.pickingLineId(line.getId().getAsString())
							.pickingStepId(steps.get(0).getId().getAsString())
							.type(JsonPickingStepEvent.EventType.UNPICK)
							.huQRCode(toQRCodeString(lu1))
							.build());
			wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
			record_WFProcess_PickingJob_AllHUs("After unpick", wfProcess);
		}

		//
		// Abort
		{
			workflowRestController.abort(wfProcess.getId());
			wfProcess = workflowRestController.getWFProcessById(wfProcess.getId());
			record_WFProcess_PickingJob_AllHUs("After ABORT", wfProcess);
		}

		recorder.assertMatchesSnapshot();
	}
}