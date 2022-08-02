package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.config.PickingConfigV2;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobCreateRepoRequest;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.plan.model.PickingPlanLine;
import de.metas.handlingunits.picking.plan.model.PickingPlanLineType;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Objects;

public class PickingJobCreateCommand
{
	private static final AdMessageKey MSG_NotAllItemsAreAvailableToBePicked = AdMessageKey.of("PickingJobCreateCommand.notAllItemsAreAvailableToBePicked");

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final PickingJobRepository pickingJobRepository;
	private final PickingJobLockService pickingJobLockService;
	private final PickingCandidateService pickingCandidateService;
	private final PickingJobHUReservationService pickingJobHUReservationService;
	private final PickingConfigRepositoryV2 pickingConfigRepo;

	private final PickingJobCreateRequest request;

	private final PickingJobLoaderSupportingServices loadingSupportServices;

	//
	// State
	private PickingConfigV2 _pickingConfig; // lazy

	@Builder
	private PickingJobCreateCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobLockService pickingJobLockService,
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final PickingJobHUReservationService pickingJobHUReservationService,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices,
			@NonNull final PickingConfigRepositoryV2 pickingConfigRepo,
			//
			@NonNull PickingJobCreateRequest request)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.loadingSupportServices = loadingSupportServices;
		this.pickingConfigRepo = pickingConfigRepo;

		this.request = request;
	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		final ImmutableList<Packageable> items = getItemsToPick();

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = Packageable.extractShipmentScheduleIds(items);
		pickingJobLockService.lockShipmentSchedules(shipmentScheduleIds, request.getPickerId());
		try
		{
			final PickingJobHeaderKey headerKey = items.stream()
					.map(PickingJobCreateCommand::extractPickingJobHeaderKey)
					.distinct()
					.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one job found")));

			final PickingJob pickingJob = pickingJobRepository.createNewAndGet(
					PickingJobCreateRepoRequest.builder()
							.orgId(headerKey.getOrgId())
							.salesOrderId(headerKey.getSalesOrderId())
							.preparationDate(headerKey.getPreparationDate())
							.deliveryBPLocationId(headerKey.getDeliveryBPLocationId())
							.deliveryRenderedAddress(headerKey.getDeliveryRenderedAddress())
							.pickerId(request.getPickerId())
							.isPickingReviewRequired(getPickingConfig().isPickingReviewRequired())
							.lines(createLinesRequest(items))
							.build(),
					loadingSupportServices);

			pickingJobHUReservationService.reservePickFromHUs(pickingJob);

			return pickingJob;
		}
		catch (final Exception createJobException)
		{
			try
			{
				pickingJobLockService.unlockShipmentSchedules(shipmentScheduleIds, request.getPickerId());
			}
			catch (final Exception unlockException)
			{
				createJobException.addSuppressed(unlockException);
			}

			throw AdempiereException.wrapIfNeeded(createJobException);
		}
	}

	private PickingConfigV2 getPickingConfig()
	{
		PickingConfigV2 pickingConfig = this._pickingConfig;
		if (pickingConfig == null)
		{
			pickingConfig = this._pickingConfig = pickingConfigRepo.getPickingConfig();
		}
		return pickingConfig;
	}

	private ImmutableList<Packageable> getItemsToPick()
	{
		final PackageableQuery query = toPackageableQuery(request);

		final ImmutableList<Packageable> items = packagingDAO
				.stream(query)
				.collect(ImmutableList.toImmutableList());

		if (items.isEmpty())
		{
			throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
					.markAsUserValidationError()
					.setParameter("detail", "Absolutely nothing to pick")
					.setParameter("query", query);
		}

		return items;
	}

	private static PackageableQuery toPackageableQuery(@NonNull final PickingJobCreateRequest request)
	{
		return PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderId(request.getSalesOrderId())
				.deliveryBPLocationId(request.getDeliveryBPLocationId())
				.warehouseTypeId(request.getWarehouseTypeId())
				.build();
	}

	@Value
	@Builder
	private static class PickingJobHeaderKey
	{
		@NonNull OrgId orgId;
		@NonNull OrderId salesOrderId;
		@NonNull InstantAndOrgId preparationDate;
		@NonNull BPartnerLocationId deliveryBPLocationId;
		@NonNull String deliveryRenderedAddress;
	}

	private static PickingJobHeaderKey extractPickingJobHeaderKey(@NonNull final Packageable item)
	{
		return PickingJobHeaderKey.builder()
				.orgId(item.getOrgId())
				.salesOrderId(Objects.requireNonNull(item.getSalesOrderId()))
				.preparationDate(item.getPreparationDate())
				.deliveryBPLocationId(item.getCustomerLocationId())
				.deliveryRenderedAddress(item.getCustomerAddress())
				.build();
	}

	private ImmutableList<PickingJobCreateRepoRequest.Line> createLinesRequest(final ImmutableList<Packageable> items)
	{
		loadingSupportServices.warmUpCachesFrom(items);

		return Multimaps.index(items, PickingJobCreateCommand::extractPickingJobLineKey)
				.asMap()
				.values()
				.stream()
				.map(this::createLineRequest)
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	@Builder
	private static class PickingJobLineKey
	{
		ProductId productId;
	}

	private static PickingJobLineKey extractPickingJobLineKey(@NonNull final Packageable item)
	{
		return PickingJobLineKey.builder().productId(item.getProductId()).build();
	}

	private PickingJobCreateRepoRequest.Line createLineRequest(@NonNull final Collection<Packageable> itemsForProduct)
	{
		Check.assumeNotEmpty(itemsForProduct, "itemsForProduct");

		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(itemsForProduct)
				.considerAttributes(getPickingConfig().isConsiderAttributes())
				.build());

		final ImmutableList<PickingPlanLine> lines = plan.getLines();
		if (lines.isEmpty())
		{
			throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
					.markAsUserValidationError()
					.setParameter("detail", "Not all materials are available")
					.setParameter("itemsForProduct", itemsForProduct)
					.setParameter("plan", plan);
		}

		return PickingJobCreateRepoRequest.Line.builder()
				.productId(plan.getSingleProductId())
				.steps(lines.stream()
						.map(this::createStepRequest)
						.collect(ImmutableList.toImmutableList()))
				.pickFromAlternatives(plan.getAlternatives()
						.stream()
						.map(alt -> PickingJobCreateRepoRequest.PickFromAlternative.of(alt.getLocatorId(), alt.getHuId(), alt.getAvailableQty()))
						.collect(ImmutableSet.toImmutableSet()))
				.build();
	}

	private PickingJobCreateRepoRequest.Step createStepRequest(@NonNull final PickingPlanLine planLine)
	{
		final PickingPlanLineType type = planLine.getType();
		switch (type)
		{
			case PICK_FROM_HU:
			{
				return createStepRequest_PickFromHU(planLine);
			}
			case UNALLOCABLE:
			{
				throw new AdempiereException(MSG_NotAllItemsAreAvailableToBePicked)
						.markAsUserValidationError()
						.setParameter("planLine", planLine);
			}
			default:
			{
				throw new AdempiereException("Line type not supported: " + planLine);
			}
		}
	}

	private PickingJobCreateRepoRequest.Step createStepRequest_PickFromHU(final @NonNull PickingPlanLine planLine)
	{
		final ProductId productId = planLine.getProductId();
		final Quantity qtyToPick = planLine.getQty();

		final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());

		final PickingJobCreateRepoRequest.StepPickFrom mainPickFrom = PickingJobCreateRepoRequest.StepPickFrom.builder()
				.pickFromLocatorId(pickFromHU.getLocatorId())
				.pickFromHUId(extractTopLevelCUIfNeeded(pickFromHU.getTopLevelHUId(), productId, qtyToPick))
				.build();

		final ImmutableSet<PickingJobCreateRepoRequest.StepPickFrom> pickFromAlternatives = pickFromHU.getAlternatives()
				.stream()
				.map(alt -> PickingJobCreateRepoRequest.StepPickFrom.builder()
						.pickFromLocatorId(alt.getLocatorId())
						.pickFromHUId(alt.getHuId())
						.build())
				.collect(ImmutableSet.toImmutableSet());

		return PickingJobCreateRepoRequest.Step.builder()
				.shipmentScheduleId(planLine.getSourceDocumentInfo().getShipmentScheduleId())
				.salesOrderLineId(Objects.requireNonNull(planLine.getSourceDocumentInfo().getSalesOrderLineId()))
				.productId(productId)
				.qtyToPick(qtyToPick)
				.mainPickFrom(mainPickFrom)
				.pickFromAlternatives(pickFromAlternatives)
				.packToSpec(planLine.getSourceDocumentInfo().getPackToSpec())
				.build();
	}

	/**
	 * If the given HU is a top level CU, and it has the storage quantity greater than the qty we have to pick,
	 * then split out a top level CU for the qty we have to pick.
	 * <p>
	 * Why we do this?
	 * We do this because when we reserve the HU/Qty, the reservation service is splitting out the reserved Qty and tries to keep the CU under the same HU,
	 * but in this case our CU is a top level one, so we will end up with a new top level CU which is not our <code>pickFromHUId</code>.
	 * The <code>pickFromHUId</code> will remain there but with qtyToPick less quantity which might not be enough for picking.
	 * <p>
	 * In this case we will deliberately split out a new CU, and we will use it for picking.
	 */
	private HuId extractTopLevelCUIfNeeded(
			@NonNull final HuId pickFromHUId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToPick)
	{
		final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);

		// Not a top level CU
		if (!handlingUnitsBL.isTopLevel(pickFromHU) || !handlingUnitsBL.isVirtual(pickFromHU))
		{
			return pickFromHUId;
		}

		final Quantity storageQty = handlingUnitsBL.getStorageFactory()
				.getStorage(pickFromHU)
				.getProductStorage(productId)
				.getQty(qtyToPick.getUOM());

		// Nothing to split
		if (storageQty.compareTo(qtyToPick) <= 0)
		{
			return pickFromHUId;
		}

		final I_M_HU extractedCU = HUTransformService.newInstance()
				.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(pickFromHU)
						.productId(productId)
						.qtyCU(qtyToPick)
						//.keepNewCUsUnderSameParent(true) // not needed, our HU is top level anyways
						.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
						.build());

		return HuId.ofRepoId(extractedCU.getM_HU_ID());
	}
}
