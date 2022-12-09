package de.metas.manufacturing.workflows_api;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTargetConverters;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEventResult;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.WorkflowBasedMobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
public class ManufacturingMobileApplication implements WorkflowBasedMobileApplication
{
	@VisibleForTesting
	public static final MobileApplicationId HANDLER_ID = MobileApplicationId.ofString("mfg");

	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.manufacturing.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(HANDLER_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	private final ManufacturingRestService manufacturingRestService;
	private final ManufacturingWorkflowLaunchersProvider wfLaunchersProvider;
	private final HUQRCodesService huQRCodesService;

	public ManufacturingMobileApplication(
			@NonNull final ManufacturingRestService manufacturingRestService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		this.manufacturingRestService = manufacturingRestService;
		this.wfLaunchersProvider = new ManufacturingWorkflowLaunchersProvider(manufacturingRestService);
		this.huQRCodesService = huQRCodesService;
	}

	@Override
	@NonNull
	public MobileApplicationInfo getApplicationInfo() {return APPLICATION_INFO;}

	@Override
	public WorkflowLaunchersList provideLaunchers(
			@NonNull final UserId userId,
			@NonNull final QueryLimit suggestedLimit,
			@NonNull final Duration maxStaleAccepted)
	{
		return wfLaunchersProvider.provideLaunchers(userId, suggestedLimit);
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
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcessId);
		manufacturingRestService.abortJob(job.getPpOrderId(), callerId);
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcessId);
		return ManufacturingRestService.toWFProcess(job);
	}

	private ManufacturingJob getManufacturingJob(final WFProcessId wfProcessId)
	{
		final PPOrderId ppOrderId = wfProcessId.getRepoId(PPOrderId::ofRepoId);
		return manufacturingRestService.getJobById(ppOrderId);
	}

	@NonNull
	private static ManufacturingJob getManufacturingJob(final WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(ManufacturingJob.class);
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		final WFProcess wfProcess = getWFProcessById(wfProcessId);
		return remappingFunction.apply(wfProcess);
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
				wfProcess -> {
					wfProcess.assertHasAccess(callerId);
					return wfProcess.<ManufacturingJob>mapDocument(job -> manufacturingRestService.processEvent(job, event));
				});

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
						.count(request.getQtyTUs().toInt())
						.huPackingInstructionsId(request.getTuPackingInstructionsId())
						.productId(finishedGoodsReceiveLine.getProductId())
						.attributes(toHUQRCodeGenerateRequestAttributesList(finishedGoodsReceiveLine.getAttributes()))
						.build());

		huQRCodesService.print(qrCodes);
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
}
