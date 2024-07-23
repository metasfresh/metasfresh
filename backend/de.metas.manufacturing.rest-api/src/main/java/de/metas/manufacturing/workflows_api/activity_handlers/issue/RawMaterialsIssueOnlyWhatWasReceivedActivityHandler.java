package de.metas.manufacturing.workflows_api.activity_handlers.issue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleCreateRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleRepository;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.i18n.AdMessageKey;
import de.metas.manufacturing.job.model.IssueOnlyWhatWasReceivedConfig;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
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
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RawMaterialsIssueOnlyWhatWasReceivedActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.rawMaterialsIssueOnlyWhatWasReceived");
	private static final AdMessageKey NO_QTY_TO_ISSUE = AdMessageKey.of("de.metas.manufacturing.NO_QTY_TO_ISSUE");
	private static final AdMessageKey NOTHING_WAS_RECEIVED_YET = AdMessageKey.of("de.metas.manufacturing.NOTHING_WAS_RECEIVED_YET");

	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsDAO huDao = Services.get(IHandlingUnitsDAO.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final PPOrderIssueScheduleRepository issueScheduleRepository;
	private final ManufacturingJobService jobService;
	private final PPOrderSourceHUService sourceHUService;

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
		final ManufacturingJob job = issueWhatWasReceived(request);

		final ManufacturingJobActivityId jobActivityId = request.getWfActivity().getId().getAsId(ManufacturingJobActivityId.class);
		final ManufacturingJob updatedJob = jobService.withActivityCompleted(job, jobActivityId);

		return ManufacturingRestService.toWFProcess(updatedJob);
	}

	@NonNull
	private ManufacturingJob issueWhatWasReceived(@NonNull final UserConfirmationRequest request)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(request.getWfProcess());
		final IssueWhatWasReceivedRequest.IssueWhatWasReceivedRequestBuilder requestBuilder = initIssueRequest(job, request);

		job.getActivities()
				.stream()
				.filter(activity -> activity.getType() == PPRoutingActivityType.RawMaterialsIssue)
				.map(ManufacturingJobActivity::getRawMaterialsIssue)
				.filter(Objects::nonNull)
				.flatMap(rawMaterialsIssue -> rawMaterialsIssue.getLines().stream())
				.map(requestBuilder::line)
				.map(IssueWhatWasReceivedRequest.IssueWhatWasReceivedRequestBuilder::build)
				.forEach(this::issueWhatWasReceived);

		return jobService.recomputeQtyToIssueForSteps(job.getPpOrderId());
	}

	private void issueWhatWasReceived(@NonNull final IssueWhatWasReceivedRequest request)
	{
		final Quantity quantityToIssueForWhatWasReceived = ppOrderBOMBL
				.computeQtyToIssueBasedOnFinishedGoodReceipt(getBomLine(request.getLine()),
															 uomDao.getById(request.getLineQtyToIssue().getUomId()),
															 request.getDraftQtys());

		final Quantity qtyToIssue = request.getLineQtyToIssue().min(quantityToIssueForWhatWasReceived);
		if (qtyToIssue.signum() <= 0)
		{
			throw new AdempiereException(NO_QTY_TO_ISSUE);
		}

		final ProductId productId = request.getLine().getProductId();
		issue(request.getPpOrderId(), productId, qtyToIssue, request.getLoadSourceHUsForProductId().apply(productId));
	}

	@NonNull
	private SourceHUsCollection retrieveActiveSourceHusFromWarehouse(
			@NonNull final ImmutableSet<WarehouseId> warehouseIds,
			@NonNull final ProductId productId)
	{
		final SourceHUsService.MatchingSourceHusQuery query = SourceHUsService.MatchingSourceHusQuery.builder()
				.productId(productId)
				.warehouseIds(warehouseIds)
				.build();
		final ImmutableMap<HuId, I_M_Source_HU> huId2SourceHu = SourceHUsService.get().retrieveMatchingSourceHuMarkers(query)
				.stream()
				.collect(ImmutableMap.toImmutableMap(sourceHu -> HuId.ofRepoId(sourceHu.getM_HU_ID()),
													 Function.identity()));

		final List<I_M_HU> hus = huDao.getByIds(huId2SourceHu.keySet());

		return SourceHUsCollection.builder()
				.husThatAreFlaggedAsSource(ImmutableList.copyOf(hus))
				.huId2SourceHu(huId2SourceHu)
				.build();
	}

	@NonNull
	private SourceHUsCollection retrieveActiveSourceHusForHus(
			@NonNull final ImmutableSet<HuId> ppOrderSourceHUIds,
			@NonNull final ProductId productId)
	{
		final ImmutableList<I_M_HU> activeHUsMatchingProduct = huDao.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setAllowEmptyStorage()
				.addOnlyHUIds(ppOrderSourceHUIds)
				.addOnlyWithProductId(productId)
				.createQuery()
				.listImmutable(I_M_HU.class);

		final ImmutableSet<HuId> activeHuIds = activeHUsMatchingProduct
				.stream()
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<HuId, I_M_Source_HU> huId2SourceHu = SourceHUsService.get().retrieveSourceHuMarkers(activeHuIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(sourceHu -> HuId.ofRepoId(sourceHu.getM_HU_ID()),
													 Function.identity()));

		return SourceHUsCollection.builder()
				.husThatAreFlaggedAsSource(activeHUsMatchingProduct)
				.huId2SourceHu(huId2SourceHu)
				.build();
	}

	@NonNull
	private I_PP_Order_BOMLine getBomLine(@NonNull final RawMaterialsIssueLine issueLine)
	{
		final PPOrderIssueSchedule schedule = issueScheduleRepository.getById(issueLine.getSteps().get(0).getId());
		return ppOrderBOMBL.getOrderBOMLineById(schedule.getPpOrderBOMLineId());
	}

	private void issue(
			@NonNull final PPOrderId ppOrderId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToIssue,
			@NonNull final SourceHUsCollection sourceHUsCollection)
	{
		final HUTransformService.HUsToNewCUsRequest request = HUTransformService.HUsToNewCUsRequest
				.builder()
				.sourceHUs(sourceHUsCollection.getHusThatAreFlaggedAsSource())
				.productId(productId)
				.qtyCU(qtyToIssue)
				.build();

		final EmptyHUListener emptyHUListener = EmptyHUListener
				.doBeforeDestroyed(hu -> sourceHUsCollection
										   .getSourceHU(HuId.ofRepoId(hu.getM_HU_ID()))
										   .ifPresent(sourceHU -> SourceHUsService.get().snapshotSourceHU(sourceHU)),
								   "Create snapshot of source-HU before it is destroyed");

		final List<I_M_HU> extractedCUs = HUTransformService.builder()
				.emptyHUListener(emptyHUListener)
				.build()
				.husToNewCUs(request);

		huPPOrderBL
				.createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue exactly the CUs we split
				.processCandidates(HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy.ALWAYS)
				.createIssues(extractedCUs)
				.stream()
				.map(ppOrderQty -> {
					final Quantity qtyIssued = Quantitys.create(ppOrderQty.getQty(), UomId.ofRepoId(ppOrderQty.getC_UOM_ID()));
					return PPOrderIssueScheduleCreateRequest.builder()
							.ppOrderId(ppOrderId)
							.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(ppOrderQty.getPP_Order_BOMLine_ID()))
							.seqNo(SeqNo.ofInt(0))
							.productId(productId)
							.qtyToIssue(qtyIssued)
							.issueFromHUId(HuId.ofRepoId(ppOrderQty.getM_HU_ID()))
							.issueFromLocatorId(warehouseDAO.getLocatorIdByRepoId(ppOrderQty.getM_Locator_ID()))
							.isAlternativeIssue(true)
							.qtyIssued(qtyIssued)
							.build();
				})
				.forEach(issueScheduleRepository::createSchedule);
	}

	@NonNull
	private Function<ProductId, SourceHUsCollection> getLoadSourceHUsForProductFunction(
			@NonNull final IssueOnlyWhatWasReceivedConfig config,
			@NonNull final PPOrderId ppOrderId)
	{
		switch (config.getIssueStrategy())
		{
			case AssignedHUsOnly:
				return productId -> {
					final ImmutableSet<HuId> sourceHUIds = sourceHUService.getSourceHUIds(ppOrderId);
					return retrieveActiveSourceHusForHus(sourceHUIds, productId);
				};
			case DEFAULT:
				return productId -> {
					final ImmutableSet<WarehouseId> issueFromWarehouseIds = ppOrderBOMBL
							.getIssueFromWarehouseIds(ppOrderBL.getById(ppOrderId));
					return retrieveActiveSourceHusFromWarehouse(issueFromWarehouseIds, productId);
				};
			default:
				throw new AdempiereException("Unknown issue strategy!");
		}
	}

	@NonNull
	private IssueWhatWasReceivedRequest.IssueWhatWasReceivedRequestBuilder initIssueRequest(
			@NonNull final ManufacturingJob job,
			@NonNull final UserConfirmationRequest request)
	{
		final boolean includeProcessed = true;
		final DraftPPOrderQuantities draftQtys = huPPOrderQtyBL.getPPOrderQuantities(job.getPpOrderId(), includeProcessed);

		if (!draftQtys.isSomethingReceived())
		{
			throw new AdempiereException(NOTHING_WAS_RECEIVED_YET);
		}

		final IssueOnlyWhatWasReceivedConfig issueConfig = getIssueWhatWasReceivedConfig(job, request);
		return IssueWhatWasReceivedRequest.builder()
				.ppOrderId(job.getPpOrderId())
				.draftQtys(draftQtys)
				.loadSourceHUsForProductId(getLoadSourceHUsForProductFunction(issueConfig, job.getPpOrderId()));
	}

	@NonNull
	private static IssueOnlyWhatWasReceivedConfig getIssueWhatWasReceivedConfig(
			@NonNull final ManufacturingJob job,
			@NonNull final UserConfirmationRequest request)
	{
		final ManufacturingJobActivityId issueWhatWasReceivedActivityId = request.getWfActivity()
				.getId()
				.getAsId(ManufacturingJobActivityId.class);

		final ManufacturingJobActivity activity = job.getActivityById(issueWhatWasReceivedActivityId);

		if (activity.getIssueOnlyWhatWasReceivedConfig() == null)
		{
			throw new AdempiereException("IssueOnlyWhatWasReceivedConfig cannot be missing for RawMaterialsIssueOnlyWhatWasReceivedActivity")
					.appendParametersToMessage()
					.setParameter("activityId", issueWhatWasReceivedActivityId);
		}

		return activity.getIssueOnlyWhatWasReceivedConfig();
	}

	@Value
	@Builder
	private static class SourceHUsCollection
	{
		@NonNull ImmutableList<I_M_HU> husThatAreFlaggedAsSource;
		@NonNull ImmutableMap<HuId, I_M_Source_HU> huId2SourceHu;

		@NonNull
		public Optional<I_M_Source_HU> getSourceHU(@NonNull final HuId huId)
		{
			return Optional.ofNullable(huId2SourceHu.get(huId));
		}
	}

	@Value
	@Builder
	private static class IssueWhatWasReceivedRequest
	{
		@NonNull PPOrderId ppOrderId;
		@NonNull DraftPPOrderQuantities draftQtys;
		@NonNull RawMaterialsIssueLine line;
		@NonNull Function<ProductId, SourceHUsCollection> loadSourceHUsForProductId;

		@NonNull
		public Quantity getLineQtyToIssue()
		{
			return line.getQtyToIssue();
		}
	}
}
