package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleRepository;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupportUtil;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RawMaterialsIssueOnlyWhatWasReceivedActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssueOnlyWhatWasReceived");

	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	private final PPOrderIssueScheduleRepository issueScheduleRepository;
	private final ManufacturingJobService jobService;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity)
						.question("Are you sure?")
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
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(request.getWfProcess());

		final DraftPPOrderQuantities draftQtys = huPPOrderQtyBL.getDraftPPOrderQuantities(job.getPpOrderId());

		if (draftQtys.getQtyReceived().map(Quantity::signum).orElse(-1) <= 0)
		{
			throw new AdempiereException("Nothing was received yet!");
		}

		final ImmutableSet<WarehouseId> issueFromWarehouseIds = ppOrderBOMBL.getIssueFromWarehouseIds(ppOrderBL.getById(job.getPpOrderId()));

		job.getActivities()
				.stream()
				.filter(activity -> activity.getType() == PPRoutingActivityType.RawMaterialsIssue)
				.map(ManufacturingJobActivity::getRawMaterialsIssue)
				.filter(Objects::nonNull)
				.flatMap(rawMaterialsIssue -> rawMaterialsIssue.getLines().stream())
				.forEach(issueLine -> issueWhatWasReceived(job.getPpOrderId(), issueFromWarehouseIds, draftQtys, issueLine));

		final ManufacturingJob updatedJob = jobService.getJobById(job.getPpOrderId());

		return ManufacturingRestService.toWFProcess(updatedJob);
	}

	private void issueWhatWasReceived(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final ImmutableSet<WarehouseId> issueFromWarehouseIds,
			@NonNull final DraftPPOrderQuantities draftQtys,
			@NonNull final RawMaterialsIssueLine issueLine)
	{
		final I_PP_Order_BOMLine bomLine = getBomLine(issueLine);
		final Quantity quantityToIssueForWhatWasReceived = ppOrderBOMBL
				.computeQtyToIssueBasedOnFinishedGoodReceipt(bomLine, uomDao.getById(bomLine.getC_UOM_ID()), draftQtys);

		final ImmutableList<I_M_Source_HU> sourceHus = retrieveActiveSourceHus(issueFromWarehouseIds, issueLine.getProductId());
		final Map<Integer, I_M_Source_HU> huId2SourceHu = new HashMap<>();
		final ImmutableList<I_M_HU> husThatAreFlaggedAsSource = sourceHus.stream()
				.peek(sourceHu -> huId2SourceHu.put(sourceHu.getM_HU_ID(), sourceHu))
				.sorted(Comparator.comparing(I_M_Source_HU::getM_HU_ID))
				.map(I_M_Source_HU::getM_HU)
				.collect(ImmutableList.toImmutableList());
		final Quantity qtyToIssue = issueLine.getQtyToIssue().min(quantityToIssueForWhatWasReceived);

		issue(ppOrderId, husThatAreFlaggedAsSource, huId2SourceHu, issueLine.getProductId(), qtyToIssue);
	}

	@NonNull
	private ImmutableList<I_M_Source_HU> retrieveActiveSourceHus(
			@NonNull final ImmutableSet<WarehouseId> warehouseIds,
			@NonNull final ProductId productId)
	{
		final SourceHUsService.MatchingSourceHusQuery query = SourceHUsService.MatchingSourceHusQuery.builder()
				.productId(productId)
				.warehouseIds(warehouseIds)
				.build();
		return SourceHUsService.get().retrieveMatchingSourceHuMarkers(query)
				.stream()
				.filter(huSource -> X_M_HU.HUSTATUS_Active.equals(huSource.getM_HU().getHUStatus()))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_PP_Order_BOMLine getBomLine(@NonNull final RawMaterialsIssueLine issueLine)
	{
		final PPOrderIssueSchedule schedule = issueScheduleRepository.getById(issueLine.getSteps().get(0).getId());
		return ppOrderBOMBL.getOrderBOMLineById(schedule.getPpOrderBOMLineId());
	}

	private void issue(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final ImmutableList<I_M_HU> husThatAreFlaggedAsSource,
			@NonNull final Map<Integer, I_M_Source_HU> huId2SourceHu,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToIssue)
	{
		final HUTransformService.HUsToNewCUsRequest request = HUTransformService.HUsToNewCUsRequest
				.builder()
				.sourceHUs(husThatAreFlaggedAsSource)
				.productId(productId)
				.qtyCU(qtyToIssue)
				.build();

		final EmptyHUListener emptyHUListener = EmptyHUListener
				.doBeforeDestroyed(hu -> {
					if (huId2SourceHu.containsKey(hu.getM_HU_ID()))
					{
						SourceHUsService.get().snapshotSourceHU(huId2SourceHu.get(hu.getM_HU_ID()));
					}
				}, "Create snapshot of source-HU before it is destroyed");

		final List<I_M_HU> extractedCUs = HUTransformService.builder()
				.emptyHUListener(emptyHUListener)
				.build()
				.husToNewCUs(request);

		huPPOrderBL
				.createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue exactly the CUs we split
				.createIssues(extractedCUs);
	}
}
