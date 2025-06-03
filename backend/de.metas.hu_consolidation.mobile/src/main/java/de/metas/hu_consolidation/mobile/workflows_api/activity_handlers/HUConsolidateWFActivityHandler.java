package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationTarget;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJob;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobPickingSlot;
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
		final String shipToAddress = renderedAddressProvider.getAddress(job.getShipToAddress());

		return JsonHUConsolidationJob.builder()
				.id(job.getId())
				.shipToAddress(shipToAddress)
				.pickingSlots(job.getPickingSlotIds()
						.stream()
						.map(this::toJsonHUConsolidationJobPickingSlot)
						.collect(ImmutableList.toImmutableList())
				)
				.currentTarget(JsonHUConsolidationTarget.ofNullable(job.getCurrentTarget()))
				.build();
	}

	private JsonHUConsolidationJobPickingSlot toJsonHUConsolidationJobPickingSlot(final PickingSlotId pickingSlotId)
	{
		final PickingSlotIdAndCaption pickingSlotIdAndCaption = pickingSlotService.getPickingSlotIdAndCaption(pickingSlotId);

		return JsonHUConsolidationJobPickingSlot.builder()
				.pickingSlotId(pickingSlotId)
				.pickingSlotQRCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption).toPrintableQRCode().toJsonDisplayableQRCode())
				.build();
	}

}
