package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUGraiSnapshot;
import de.metas.handlingunits.picking.slot.PickingSlotQueueQuery;
import de.metas.handlingunits.picking.slot.PickingSlotQueuesSummary;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJob;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobPickingSlot;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationTarget;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Services;
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
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
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
	@NonNull private final HUGraiService huGraiService;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

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

		final boolean graiScanEnabled = resolveGraiScanEnabled(job);

		return JsonHUConsolidationJob.builder()
				.id(job.getId())
				.shipToAddress(shipToAddress)
				.pickingSlots(toJsonHUConsolidationJobPickingSlots(job.getPickingSlotIds()))
				.graiScanEnabled(graiScanEnabled)
				.currentTarget(toJsonTarget(job.getCurrentTarget(), graiScanEnabled))
				.build();
	}

	/** GRAIRequired != No ⇒ graiScanEnabled=true (YesWithDummyGRAIs is treated as Yes). */
	boolean resolveGraiScanEnabled(@NonNull final HUConsolidationJob job)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(job.getCustomerId());
		final GRAIRequired graiRequired = GRAIRequired.optionalOfNullableCode(bpartner.getGRAIRequired())
				.orElse(GRAIRequired.No);
		return !graiRequired.isNo();
	}

	@Nullable
	private JsonHUConsolidationTarget toJsonTarget(
			@Nullable final HUConsolidationTarget target,
			final boolean graiScanEnabled)
	{
		if (target == null)
		{
			return null;
		}

		final HuId luId = target.getLuId();
		final HUGraiSnapshot graiSnapshot = (graiScanEnabled && luId != null)
				? huGraiService.getSnapshot(luId).orElse(null)
				: null;

		return JsonHUConsolidationTarget.of(target, graiSnapshot);
	}

	private ImmutableList<JsonHUConsolidationJobPickingSlot> toJsonHUConsolidationJobPickingSlots(final Set<PickingSlotId> pickingSlotIds)
	{
		if (pickingSlotIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<PickingSlotIdAndCaption> pickingSlotIdAndCaptions = pickingSlotService.getPickingSlotIdAndCaptions(pickingSlotIds);
		final PickingSlotQueuesSummary summary = pickingSlotService.getNotEmptyQueuesSummary(PickingSlotQueueQuery.onlyPickingSlotIds(pickingSlotIds));

		return pickingSlotIdAndCaptions.stream()
				.map(pickingSlotIdAndCaption -> JsonHUConsolidationJobPickingSlot.builder()
						.pickingSlotId(pickingSlotIdAndCaption.getPickingSlotId())
						.pickingSlotQRCode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption).toPrintableQRCode().toJsonDisplayableQRCode())
						.countHUs(summary.getCountHUs(pickingSlotIdAndCaption.getPickingSlotId()).orElse(0))
						.build())
				.collect(ImmutableList.toImmutableList());
	}
}
