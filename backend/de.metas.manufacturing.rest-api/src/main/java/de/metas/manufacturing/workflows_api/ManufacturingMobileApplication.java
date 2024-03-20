package de.metas.manufacturing.workflows_api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTargetConverters;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEventResult;
import de.metas.product.ResourceId;
import de.metas.report.PrintCopies;
import de.metas.resource.UserWorkstationService;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

@Component
public class ManufacturingMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("mfg");
	
	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.manufacturing.appName");

	private final MobileUIManufacturingConfigRepository userProfileRepository;
	private final ManufacturingRestService manufacturingRestService;
	private final ManufacturingWorkflowLaunchersProvider wfLaunchersProvider;
	private final HUQRCodesService huQRCodesService;
	private final UserWorkstationService userWorkstationService;

	public ManufacturingMobileApplication(
			@NonNull final MobileUIManufacturingConfigRepository userProfileRepository,
			@NonNull final ManufacturingRestService manufacturingRestService,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final UserWorkstationService userWorkstationService)
	{
		this.userProfileRepository = userProfileRepository;
		this.manufacturingRestService = manufacturingRestService;
		this.wfLaunchersProvider = new ManufacturingWorkflowLaunchersProvider(manufacturingRestService);
		this.huQRCodesService = huQRCodesService;
		this.userWorkstationService = userWorkstationService;
	}

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}

	@Override
	public @NonNull MobileApplicationInfo getApplicationInfo(@NonNull final UserId loggedUserId)
	{
		final MobileUIManufacturingConfig config = userProfileRepository.getConfig(loggedUserId, ClientId.METASFRESH);
		return MobileApplicationInfo.builder()
				.id(APPLICATION_ID)
				.caption(TranslatableStrings.adMessage(MSG_Caption))
				.requiresWorkstation(config.getIsScanResourceRequired().isTrue())
				//.showFilters(true)
				.build();
	}

	@Override
	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		final ResourceId workstationId = getFilterByWorkstationId(query.getUserId());
		return wfLaunchersProvider.provideLaunchers(query, workstationId);
	}

	@Nullable
	private ResourceId getFilterByWorkstationId(final UserId userId)
	{
		final MobileApplicationInfo applicationInfo = getApplicationInfo(userId);
		return applicationInfo.isRequiresWorkstation()
				? userWorkstationService.getUserWorkstationId(userId).orElse(null)
				: null;
	}

	@Override
	public WorkflowLaunchersFacetGroupList getFacets(final WorkflowLaunchersFacetQuery query)
	{
		return wfLaunchersProvider.getFacets(query);
	}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final UserId invokerId = request.getInvokerId();
		final PPOrderId ppOrderId = ManufacturingWFProcessStartParams.ofParams(request.getWfParameters()).getPpOrderId();

		final ManufacturingJob job = manufacturingRestService.createJob(ppOrderId, invokerId);
		return ManufacturingRestService.toWFProcess(job);
	}

	@Override
	public WFProcess continueWorkflow(final WFProcessId wfProcessId, final UserId callerId)
	{
		final ManufacturingJob job = manufacturingRestService.createJob(toPPOrderId(wfProcessId), callerId);
		final ManufacturingJob updatedJob = manufacturingRestService.assignJob(job.getPpOrderId(), callerId);
		return ManufacturingRestService.toWFProcess(updatedJob);
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcessId);
		manufacturingRestService.abortJob(job.getPpOrderId(), callerId);
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		manufacturingRestService.abortAllJobs(callerId);
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcessId);
		return ManufacturingRestService.toWFProcess(job);
	}

	private ManufacturingJob getManufacturingJob(final WFProcessId wfProcessId)
	{
		final PPOrderId ppOrderId = toPPOrderId(wfProcessId);
		return manufacturingRestService.getJobById(ppOrderId);
	}

	@NonNull
	private static PPOrderId toPPOrderId(final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoId(PPOrderId::ofRepoId);
	}

	@NonNull
	public static ManufacturingJob getManufacturingJob(final WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(ManufacturingJob.class);
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
	}

	private WFProcess changeWFProcessById(
			@NonNull final WFProcessId wfProcessId,
			@NonNull final BiFunction<WFProcess, ManufacturingJob, ManufacturingJob> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return mapDocument(wfProcess, job -> remappingFunction.apply(wfProcess, job));
	}

	public static WFProcess mapDocument(@NonNull final WFProcess wfProcess, @NonNull final UnaryOperator<ManufacturingJob> mapper)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcess);
		final ManufacturingJob jobChanged = mapper.apply(job);
		return !Objects.equals(job, jobChanged)
				? ManufacturingRestService.toWFProcess(jobChanged)
				: wfProcess;
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcess);
		return WFProcessHeaderProperties.builder()
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DocumentNo"))
						.value(job.getDocumentNo())
						.build())
				.entry(WFProcessHeaderProperty.builder()
						.caption(TranslatableStrings.adElementOrMessage("DatePromised"))
						.value(job.getDatePromised())
						.build())
				.build();
	}

	public JsonManufacturingOrderEventResult processEvent(final JsonManufacturingOrderEvent event, final UserId callerId)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(event.getWfProcessId());
		final WFProcess changedWFProcess = changeWFProcessById(
				wfProcessId,
				(wfProcess, job) -> {
					wfProcess.assertHasAccess(callerId);
					return manufacturingRestService.processEvent(job, event);
				}
		);

		return extractProcessEventResult(changedWFProcess, event);
	}

	@NonNull
	private JsonManufacturingOrderEventResult extractProcessEventResult(
			final WFProcess changedWFProcess,
			final JsonManufacturingOrderEvent event)
	{
		final WFActivityId wfActivityId = WFActivityId.ofString(event.getWfActivityId());
		final JsonManufacturingOrderEventResult result = new JsonManufacturingOrderEventResult(changedWFProcess.getId().getAsString(), wfActivityId.getAsString());

		final JsonManufacturingOrderEvent.ReceiveFrom receiveFrom = event.getReceiveFrom();
		if (receiveFrom != null)
		{
			final FinishedGoodsReceiveLine receiveLine = getManufacturingJob(changedWFProcess)
					.getActivityById(wfActivityId)
					.getFinishedGoodsReceiveAssumingNotNull()
					.getLineById(receiveFrom.getFinishedGoodsReceiveLineId());

			result.setExistingLU(extractHUQRCodeTarget(receiveLine));
			result.setQtyReceivedTotal(receiveLine.getQtyReceived().toBigDecimal());
		}

		return result;
	}

	@Nullable
	private JsonHUQRCodeTarget extractHUQRCodeTarget(final FinishedGoodsReceiveLine receiveLine)
	{
		return JsonHUQRCodeTargetConverters.fromNullable(receiveLine.getReceivingTarget(), huQRCodesService);
	}

	public void generateFinishGoodsReceiveQRCodes(@NonNull final JsonFinishGoodsReceiveQRCodesGenerateRequest request)
	{
		final ManufacturingJob manufacturingJob = getManufacturingJob(getWFProcessById(request.getWfProcessId()));
		final FinishedGoodsReceiveLine finishedGoodsReceiveLine = manufacturingJob.getFinishedGoodsReceiveLineById(request.getFinishedGoodsReceiveLineId());

		final List<HUQRCode> qrCodes = huQRCodesService.generate(
				HUQRCodeGenerateRequest.builder()
						.count(request.getNumberOfHUs())
						.huPackingInstructionsId(request.getHuPackingInstructionsId())
						.productId(finishedGoodsReceiveLine.getProductId())
						.attributes(toHUQRCodeGenerateRequestAttributesList(finishedGoodsReceiveLine.getAttributes()))
						.build());

		final PrintCopies copies = request.getNumberOfCopies() != null
				? PrintCopies.ofInt(request.getNumberOfCopies()).minimumOne()
				: PrintCopies.ONE;

		huQRCodesService.print(qrCodes, copies);
	}

	private static List<HUQRCodeGenerateRequest.Attribute> toHUQRCodeGenerateRequestAttributesList(@NonNull final ImmutableAttributeSet attributes)
	{
		return attributes
				.getAttributeCodes()
				.stream()
				.map(attributeCode -> toHUQRCodeGenerateRequestAttribute(attributes, attributeCode))
				.collect(ImmutableList.toImmutableList());
	}

	private static HUQRCodeGenerateRequest.Attribute toHUQRCodeGenerateRequestAttribute(final ImmutableAttributeSet attributes, AttributeCode attributeCode)
	{
		final AttributeId attributeId = attributes.getAttributeIdByCode(attributeCode);

		final HUQRCodeGenerateRequest.Attribute.AttributeBuilder resultBuilder = HUQRCodeGenerateRequest.Attribute.builder()
				.attributeId(attributeId);

		return attributes.getAttributeValueType(attributeCode)
				.map(new AttributeValueType.CaseMapper<HUQRCodeGenerateRequest.Attribute>()
				{
					@Override
					public HUQRCodeGenerateRequest.Attribute string()
					{
						return resultBuilder.valueString(attributes.getValueAsString(attributeCode)).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute number()
					{
						return resultBuilder.valueNumber(attributes.getValueAsBigDecimal(attributeCode)).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute date()
					{
						return resultBuilder.valueDate(attributes.getValueAsLocalDate(attributeCode)).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute list()
					{
						return resultBuilder.valueListId(attributes.getAttributeValueIdOrNull(attributeCode)).build();
					}
				});
	}

	@Override
	public void logout(final @NonNull UserId userId)
	{
		abortAll(userId);
	}
}
