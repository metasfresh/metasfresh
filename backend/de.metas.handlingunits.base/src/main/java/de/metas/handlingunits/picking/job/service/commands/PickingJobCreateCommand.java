package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobCreateRepoRequest;
import de.metas.handlingunits.picking.job.repository.PickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.plan.model.PickingPlanLine;
import de.metas.handlingunits.picking.plan.model.PickingPlanLineType;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public class PickingJobCreateCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	private final PickingJobRepository pickingJobRepository;
	private final PickingJobLockService pickingJobLockService;
	private final PickingCandidateService pickingCandidateService;
	private final PickingJobHUReservationService pickingJobHUReservationService;

	private final PickingJobCreateRequest request;
	private static final boolean considerAttributes = false; // TODO make it configurable

	private final PickingJobLoaderSupportingServices loadingSupportServices;

	@Builder
	private PickingJobCreateCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobLockService pickingJobLockService,
			@NonNull final PickingJobSlotService pickingJobSlotService,
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final PickingJobHUReservationService pickingJobHUReservationService,
			@NonNull final IBPartnerBL bpartnerBL,
			//
			@NonNull PickingJobCreateRequest request)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingCandidateService = pickingCandidateService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;

		this.request = request;

		this.loadingSupportServices = new PickingJobLoaderSupportingServices(bpartnerBL, pickingJobSlotService);
	}

	public PickingJob execute() {return trxManager.callInThreadInheritedTrx(this::executeInTrx);}

	private PickingJob executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		final ImmutableList<Packageable> items = packagingDAO
				.stream(toPackageableQuery(request))
				.collect(ImmutableList.toImmutableList());
		if (items.isEmpty())
		{
			throw new AdempiereException("Nothing to pick");
		}

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = Packageable.extractShipmentScheduleIds(items);
		pickingJobLockService.lockShipmentSchedules(shipmentScheduleIds, request.getPickerId());
		try
		{
			final PickingJobHeaderKey headerKey = items.stream()
					.map(PickingJobCreateCommand::extractPickingJobHeaderKey)
					.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one job found")));

			final PickingJob pickingJob = pickingJobRepository.createNewAndGet(
					PickingJobCreateRepoRequest.builder()
							.orgId(headerKey.getOrgId())
							.salesOrderId(headerKey.getSalesOrderId())
							.preparationDate(headerKey.getPreparationDate())
							.deliveryBPLocationId(headerKey.getDeliveryBPLocationId())
							.deliveryRenderedAddress(headerKey.getDeliveryRenderedAddress())
							.pickerId(request.getPickerId())
							.lines(toLineRepoRequests(items))
							.build(),
					loadingSupportServices);

			pickingJobHUReservationService.reservePickFromHUs(pickingJob);

			return pickingJob;
		}
		catch (final Exception ex)
		{
			try
			{
				pickingJobLockService.unlockShipmentSchedules(shipmentScheduleIds, request.getPickerId());
			}
			catch (Exception unlockException)
			{
				ex.addSuppressed(unlockException);
			}

			throw AdempiereException.wrapIfNeeded(ex);
		}
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

	private ImmutableList<PickingJobCreateRepoRequest.Line> toLineRepoRequests(final ImmutableList<Packageable> items)
	{
		loadingSupportServices.warmUpCachesFrom(items);

		return Multimaps.index(items, PickingJobCreateCommand::extractPickingJobLineKey)
				.asMap()
				.values()
				.stream()
				.map(this::toLineRepoRequest)
				.collect(ImmutableList.toImmutableList());
	}

	private PickingJobCreateRepoRequest.Line toLineRepoRequest(@NonNull final Collection<Packageable> itemsForProduct)
	{
		Check.assumeNotEmpty(itemsForProduct, "itemsForProduct");

		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(itemsForProduct)
				.considerAttributes(considerAttributes)
				.build());

		return toLineRepoRequest(plan);
	}

	@Value
	@Builder
	private static class PickingJobLineKey
	{
		ProductId productId;
	}

	private static PickingJobLineKey extractPickingJobLineKey(@NonNull Packageable item)
	{
		return PickingJobLineKey.builder()
				.productId(item.getProductId())
				.build();
	}

	private static PickingJobCreateRepoRequest.Line toLineRepoRequest(@NonNull final PickingPlan plan)
	{
		return PickingJobCreateRepoRequest.Line.builder()
				.productId(plan.getSingleProductId())
				.steps(plan.getLines()
						.stream()
						.map(PickingJobCreateCommand::toStepCreateRepoRequest)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private static PickingJobCreateRepoRequest.Step toStepCreateRepoRequest(@NonNull final PickingPlanLine planLine)
	{
		if (PickingPlanLineType.PICK_FROM_HU.equals(planLine.getType()))
		{
			final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());
			final LocatorId locatorId = pickFromHU.getLocatorId();
			final ProductId productId = planLine.getProductId();

			return PickingJobCreateRepoRequest.Step.builder()
					.shipmentScheduleId(planLine.getSourceDocumentInfo().getShipmentScheduleId())
					.salesOrderLineId(Objects.requireNonNull(planLine.getSourceDocumentInfo().getSalesOrderLineId()))
					.locatorId(locatorId)
					.productId(productId)
					.pickFromHUId(pickFromHU.getHuId())
					.qtyToPick(planLine.getQty())
					.build();
		}
		else
		{
			// TODO return some unsupported/not allocatable line
			return null;
		}
	}
}
