package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.picking.slot.PickingSlotQuery;
import de.metas.handlingunits.picking.slot.PickingSlotQueuesSummary;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJob;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobPickingSlot;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationTarget;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.api.Params;
import org.springframework.stereotype.Component;

import java.util.Set;

import static de.metas.hu_consolidation.mobile.HUConsolidationApplication.getHUConsolidationJob;

@Component
@RequiredArgsConstructor
public class HUConsolidateWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("huConsolidation.consolidate");
	public static final UIComponentType COMPONENT_TYPE = UIComponentType.ofString("huConsolidation/consolidate");

	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final IDocumentLocationBL documentLocationBL;

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);

		return UIComponent.builderFrom(COMPONENT_TYPE, wfActivity)
				.properties(Params.builder()
						.valueObj("job", toJson(job))
						// TODO
						// .valueObj("lines", lines)
						// .valueObj("qtyRejectedReasons", qtyRejectedReasons)
						.build())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity completeDistributionWFActivity)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);
		return computeActivityState(job);
	}

	public static WFActivityStatus computeActivityState(final HUConsolidationJob ignoredJob)
	{
		// TODO
		return WFActivityStatus.NOT_STARTED;
	}

	private JsonHUConsolidationJob toJson(@NonNull final HUConsolidationJob job)
	{
		final RenderedAddressProvider renderedAddressProvider = documentLocationBL.newRenderedAddressProvider();
		final String shipToAddress = renderedAddressProvider.getAddress(job.getShipToBPLocationId());

		return JsonHUConsolidationJob.builder()
				.id(job.getId())
				.shipToAddress(shipToAddress)
				.pickingSlots(toJsonHUConsolidationJobPickingSlots(job.getPickingSlotIds()))
				.currentTarget(JsonHUConsolidationTarget.ofNullable(job.getCurrentTarget()))
				.build();
	}

	private ImmutableList<JsonHUConsolidationJobPickingSlot> toJsonHUConsolidationJobPickingSlots(final Set<PickingSlotId> pickingSlotIds)
	{
		if (pickingSlotIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<PickingSlotIdAndCaption> pickingSlotIdAndCaptions = pickingSlotService.getPickingSlotIdAndCaptions(pickingSlotIds);
		final PickingSlotQueuesSummary summary = pickingSlotService.getNotEmptyQueuesSummary(PickingSlotQuery.onlyPickingSlotIds(pickingSlotIds));

		return pickingSlotIdAndCaptions.stream()
				.map(pickingSlotIdAndCaption -> JsonHUConsolidationJobPickingSlot.builder()
						.pickingSlotId(pickingSlotIdAndCaption.getPickingSlotId())
						.pickingSlotQRCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption).toPrintableQRCode().toJsonDisplayableQRCode())
						.countHUs(summary.getCountHUs(pickingSlotIdAndCaption.getPickingSlotId()).orElse(0))
						.build())
				.collect(ImmutableList.toImmutableList());
	}
}
