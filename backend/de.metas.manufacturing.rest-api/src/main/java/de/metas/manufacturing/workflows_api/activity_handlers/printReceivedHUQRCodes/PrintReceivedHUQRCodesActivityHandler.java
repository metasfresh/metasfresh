package de.metas.manufacturing.workflows_api.activity_handlers.printReceivedHUQRCodes;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupportUtil;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityAlwaysAvailableToUser;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Component
public class PrintReceivedHUQRCodesActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.printReceivedHUQRCodes");
	private static final AdMessageKey MSG_DoYouWantToPrint = AdMessageKey.of("manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?");
	private static final AdMessageKey MSG_NoFinishedGoodsAvailableForLabeling = AdMessageKey.of("manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling");

	private final ManufacturingJobService manufacturingJobService;
	private final HULabelService huLabelService;
	private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public PrintReceivedHUQRCodesActivityHandler(
			@NonNull final ManufacturingJobService manufacturingJobService,
			@NonNull final HULabelService huLabelService)
	{
		this.manufacturingJobService = manufacturingJobService;
		this.huLabelService = huLabelService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity)
						.question(TranslatableStrings.adMessage(MSG_DoYouWantToPrint).translate(jsonOpts.getAdLanguage()))
						.alwaysAvailableToUser(WFActivityAlwaysAvailableToUser.YES)
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return wfActivity.getStatus();
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		final WFProcess wfProcess = request.getWfProcess();
		final WFActivity wfActivity = request.getWfActivity();
		wfActivity.getWfActivityType().assertExpected(HANDLED_ACTIVITY_TYPE);

		final ImmutableList<HUToReport> hus = getHusToPrint(getPPOrderId(wfProcess));
		printReceivedFinishedGoods(hus);

		return withActivityCompleted(wfProcess, wfActivity);
	}

	private WFProcess withActivityCompleted(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		final ManufacturingJob job = getJob(wfProcess);
		final ManufacturingJobActivityId jobActivityId = wfActivity.getId().getAsId(ManufacturingJobActivityId.class);
		final ManufacturingJob changedJob = manufacturingJobService.withActivityCompleted(job, jobActivityId);
		return ManufacturingRestService.toWFProcess(changedJob);
	}

	@NonNull
	private static ManufacturingJob getJob(final WFProcess wfProcess)
	{
		return wfProcess.getDocumentAs(ManufacturingJob.class);
	}

	private PPOrderId getPPOrderId(@NonNull final WFProcess wfProcess)
	{
		return getJob(wfProcess).getPpOrderId();
	}

	private ImmutableList<HUToReport> getHusToPrint(final PPOrderId ppOrderId)
	{
		final Set<HuId> huIds = huPPOrderQtyBL.getFinishedGoodsReceivedHUIds(ppOrderId);
		if (huIds.isEmpty())
		{
			throw new AdempiereException(MSG_NoFinishedGoodsAvailableForLabeling)
					.markAsUserValidationError();
		}

		return handlingUnitsBL.getByIds(huIds)
				.stream()
				.filter(I_M_HU::isActive)
				.map(HUToReportWrapper::of)
				.collect(ImmutableList.toImmutableList());
	}

	private void printReceivedFinishedGoods(@NonNull final List<HUToReport> husToPrint)
	{
		final HULabelConfigProvider labelConfigProvider = new HULabelConfigProvider(huLabelService);
		final BatchToPrintCollector batches = new BatchToPrintCollector(labelConfigProvider);
		batches.addAllRecursive(husToPrint);

		batches.forEach(this::printLabelsBatch);
	}

	private void printLabelsBatch(final BatchToPrint batchToPrint)
	{
		final AdProcessId printFormatProcessId = batchToPrint.getPrintFormatProcessId();

		for (final HUToReport hu : batchToPrint.getHus())
		{
			// IMPORTANT: call the report with one HU only because label reports are working only if we provide M_HU_ID parameter.
			// We changed HUReportExecutor to provide the M_HU_ID parameter and as AD_Table_ID/Record_ID in case only one HU is provided.
			HUReportExecutor.newInstance()
					.printPreview(false)
					.executeNow(printFormatProcessId, ImmutableList.of(hu));
		}
	}

	//
	//
	//
	//
	//
	//

	private static class HULabelConfigProvider
	{
		private final HULabelService huLabelService;

		private final HashMap<HULabelConfigQuery, ExplainedOptional<HULabelConfig>> cache = new HashMap<>();

		private HULabelConfigProvider(final HULabelService huLabelService) {this.huLabelService = huLabelService;}

		private HULabelConfig getLabelConfig(final @NonNull HUToReport hu)
		{
			return cache.computeIfAbsent(toHULabelConfigQuery(hu), huLabelService::getFirstMatching)
					.orElseThrow();
		}

		private static HULabelConfigQuery toHULabelConfigQuery(final @NonNull HUToReport hu)
		{
			return HULabelConfigQuery.builder()
					.sourceDocType(HULabelSourceDocType.Manufacturing)
					.huUnitType(hu.getHUUnitType())
					.bpartnerId(hu.getBPartnerId())
					.build();
		}
	}

	private static class BatchToPrintCollector
	{
		private final HULabelConfigProvider labelConfigProvider;
		@Getter
		private final ArrayList<BatchToPrint> batches = new ArrayList<>();
		private final HashSet<HuId> huIdsCollected = new HashSet<>();

		private BatchToPrintCollector(final HULabelConfigProvider huLabelConfigProvider) {this.labelConfigProvider = huLabelConfigProvider;}

		public void addAllRecursive(@NonNull final List<HUToReport> hus)
		{
			for (final HUToReport hu : hus)
			{
				add(hu, labelConfigProvider.getLabelConfig(hu));

				final HuUnitType huUnitType = hu.getHUUnitType();
				if (huUnitType == HuUnitType.LU)
				{
					for (final HUToReport includedHU : hu.getIncludedHUs())
					{
						add(includedHU, labelConfigProvider.getLabelConfig(includedHU));
					}
				}
			}
		}

		public void add(@NonNull final HUToReport hu, @NonNull final HULabelConfig labelConfig)
		{
			// Don't add it if we already considered it
			if (!huIdsCollected.add(hu.getHUId()))
			{
				return;
			}

			final BatchToPrint lastBatch = !batches.isEmpty() ? batches.get(batches.size() - 1) : null;

			final BatchToPrint batch;
			if (lastBatch == null || !lastBatch.isMatching(labelConfig))
			{
				batch = new BatchToPrint(labelConfig.getPrintFormatProcessId());
				batches.add(batch);
			}
			else
			{
				batch = lastBatch;
			}

			batch.addHU(hu);
		}

		public void forEach(@NonNull final Consumer<BatchToPrint> action)
		{
			batches.forEach(action);
		}
	}

	@Getter
	private static class BatchToPrint
	{
		@NonNull private final AdProcessId printFormatProcessId;
		@NonNull private final ArrayList<HUToReport> hus = new ArrayList<>();

		private BatchToPrint(final @NonNull AdProcessId printFormatProcessId) {this.printFormatProcessId = printFormatProcessId;}

		public boolean isMatching(@NonNull final HULabelConfig labelConfig)
		{
			return AdProcessId.equals(printFormatProcessId, labelConfig.getPrintFormatProcessId());
		}

		public void addHU(@NonNull final HUToReport hu) {this.hus.add(hu);}
	}
}
