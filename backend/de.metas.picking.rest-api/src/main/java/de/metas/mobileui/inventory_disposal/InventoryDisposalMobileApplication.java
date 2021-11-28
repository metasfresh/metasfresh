package de.metas.mobileui.inventory_disposal;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.HuId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.MobileApplicationInfo;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.service.MobileApplication;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

@Component
public class InventoryDisposalMobileApplication implements MobileApplication
{
	private final HUDisposalJobService huDisposalJobService;

	static final MobileApplicationId HANDLER_ID = MobileApplicationId.ofString("inventoryDisposal");

	private static final AdMessageKey MSG_Caption = AdMessageKey.of("mobileui.inventoryDisposal.appName");
	private static final MobileApplicationInfo APPLICATION_INFO = MobileApplicationInfo.builder()
			.id(HANDLER_ID)
			.caption(TranslatableStrings.adMessage(MSG_Caption))
			.build();

	public InventoryDisposalMobileApplication(final HUDisposalJobService huDisposalJobService) {this.huDisposalJobService = huDisposalJobService;}

	@Override
	public @NonNull MobileApplicationInfo getApplicationInfo()
	{
		return APPLICATION_INFO;
	}

	@Override
	public WorkflowLaunchersList provideLaunchers(final @NonNull UserId userId, final @NonNull QueryLimit suggestedLimit, final @NonNull Duration maxStaleAccepted)
	{
		return WorkflowLaunchersList.builder()
				.scanBarcodeToStartJobSupport(true)
				.launchers(huDisposalJobService.getJobsByInvokerId(userId)
						.stream()
						.map(InventoryDisposalMobileApplication::toWorkflowLauncher)
						.collect(ImmutableList.toImmutableList()))
				.timestamp(SystemTime.asInstant())
				.build();
	}

	@Override
	public WFProcess startWorkflow(final WorkflowStartRequest request)
	{
		final HUDisposalJob job = huDisposalJobService.createJob(request);
		return toWFProcess(job);
	}

	private static WFProcess toWFProcess(final HUDisposalJob huDisposalJob)
	{
		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(HANDLER_ID, huDisposalJob.getHuId()))
				.caption(TranslatableStrings.anyLanguage(huDisposalJob.getHuDisplayName()))
				.document(huDisposalJob)
				.invokerId(huDisposalJob.getInvokerId())
				.activities(ImmutableList.of(
						WFActivity.builder()
								.id(WFActivityId.ofString("complete"))
								.wfActivityType(CompleteHUDisposalWFActivityHandler.HANDLED_ACTIVITY_TYPE)
								.caption(TranslatableStrings.adRefList(IDocument.ACTION_AD_Reference_ID, IDocument.ACTION_Complete))

								.build()
				))
				.build();
	}

	private static WorkflowLauncher toWorkflowLauncher(final HUDisposalJob job)
	{
		return WorkflowLauncher.builder()
				.applicationId(HANDLER_ID)
				.caption(job.getLauncherCaption())
				.startedWFProcessId(WFProcessId.ofIdPart(HANDLER_ID, job.getHuId()))
				.build();
	}

	@Override
	public WFProcess getWFProcessById(final WFProcessId wfProcessId)
	{
		final HUDisposalJob job = huDisposalJobService.getJobById(toHuId(wfProcessId));
		return toWFProcess(job);
	}

	@NonNull
	private static HuId toHuId(final WFProcessId wfProcessId)
	{
		return wfProcessId.getRepoId(HuId::ofRepoId);
	}

	@Override
	public void abort(final WFProcessId wfProcessId, final UserId callerId)
	{
		huDisposalJobService.abort(toHuId(wfProcessId), callerId);
	}

	@Override
	public void abortAll(final UserId callerId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public WFProcess changeWFProcessById(final WFProcessId wfProcessId, final UnaryOperator<WFProcess> remappingFunction)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public WFProcessHeaderProperties getHeaderProperties(final @NonNull WFProcess wfProcess)
	{
		final HUDisposalJob job = wfProcess.getDocumentAs(HUDisposalJob.class);

		final ArrayList<WFProcessHeaderProperty> entries = new ArrayList<>();

		for (final HUStorageInfo productStorage : huDisposalJobService.getStorageInfo(job.getHuId()))
		{
			entries.add(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("M_Product_ID"))
					.value(productStorage.getProductName())
					.build());
			entries.add(WFProcessHeaderProperty.builder()
					.caption(TranslatableStrings.adElementOrMessage("Qty"))
					.value(TranslatableStrings.builder().appendQty(productStorage.getQty().toBigDecimal(), productStorage.getQty().getUOMSymbol()).build())
					.build());
		}

		return WFProcessHeaderProperties.builder()
				.entries(entries)
				.build();
	}
}
