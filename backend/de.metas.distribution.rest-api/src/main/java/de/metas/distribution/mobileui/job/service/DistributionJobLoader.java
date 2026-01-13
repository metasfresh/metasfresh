package de.metas.distribution.mobileui.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.DDOrderQuery.DDOrderQueryBuilder;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.external_services.sourcedoc.PlantInfo;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.model.DistributionJobLine;
import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import de.metas.distribution.mobileui.job.model.DistributionJobStep;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.document.engine.DocStatus;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DistributionJobLoader
{
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;

	private final HashMap<DDOrderId, I_DD_Order> ddOrdersCache = new HashMap<>();
	private final HashMap<DDOrderId, List<I_DD_OrderLine>> ddOrderLinesCache = new HashMap<>();
	private final HashMap<DDOrderLineId, List<DDOrderMoveSchedule>> schedulesCache = new HashMap<>();

	public DistributionJob loadByRecord(@NonNull final I_DD_Order ddOrder)
	{
		addToCache(ddOrder);
		return loadByRecord0(ddOrder);
	}

	public DistributionJob loadByJobId(final DistributionJobId jobId)
	{
		return loadByDDOrderId(jobId.toDDOrderId());
	}

	public DistributionJob loadByDDOrderId(final DDOrderId ddOrderId)
	{
		warmUpById(ddOrderId);
		return loadByRecord0(getDDOrder(ddOrderId));
	}

	public List<DistributionJob> loadByQuery(@NonNull final DDOrderQueryBuilder queryBuilder)
	{
		return loadByQuery(queryBuilder.build());
	}

	public List<DistributionJob> loadByQuery(@NonNull final DDOrderQuery query)
	{
		final ImmutableList<I_DD_Order> ddOrders = loadingSupportServices.stream(query).collect(ImmutableList.toImmutableList());
		return loadByRecords(ddOrders);
	}

	public List<DistributionJob> loadByRecords(@NonNull final List<I_DD_Order> ddOrders)
	{
		if (ddOrders.isEmpty()) {return ImmutableList.of();}

		addToCache(ddOrders);

		return ddOrders.stream()
				.map(this::loadByRecord0)
				.collect(ImmutableList.toImmutableList());
	}

	private DistributionJob loadByRecord0(final I_DD_Order ddOrder)
	{
		final MobileUIDistributionConfig config = loadingSupportServices.getConfig();

		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());
		final DocStatus docStatus = DocStatus.ofCode(ddOrder.getDocStatus());

		final ZonedDateTime dateRequired = InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID())
				.toZonedDateTime(loadingSupportServices::getTimeZone);
		final ZonedDateTime pickDate = Optional.ofNullable(ddOrder.getPickDate())
				.map(date -> InstantAndOrgId.ofTimestamp(date, ddOrder.getAD_Org_ID()))
				.map(instant -> instant.toZonedDateTime(loadingSupportServices::getTimeZone))
				.orElse(dateRequired);

		return DistributionJob.builder()
				.id(DistributionJobId.ofDDOrderId(ddOrderId))
				.documentNo(ddOrder.getDocumentNo())
				.seqNo(SeqNo.ofInt(ddOrder.getSeqNo()))
				.customerId(BPartnerId.ofRepoId(ddOrder.getC_BPartner_ID()))
				.dateRequired(dateRequired)
				.pickDate(pickDate)
				.pickFromWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_From_ID()))
				.dropToWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_To_ID()))
				.plantInfo(extractPlantInfo(ddOrder))
				.priority(ddOrder.getPriorityRule())
				.responsibleId(extractResponsibleId(ddOrder))
				.isClosed(!docStatus.isCompleted()) // NOTE: we consider closed (for us) anything which is not completed
				.salesOrderRef(loadingSupportServices.getSalesOderRef(ddOrder))
				.manufacturingOrderRef(loadingSupportServices.getManufacturingOrderRef(ddOrder))
				.pickingInstruction(loadingSupportServices.getPickingInstruction(ddOrder))
				.allowPickingAnyHU(config.isAllowPickingAnyHU())
				.lines(getDDOrderLines(ddOrderId)
						.stream()
						.map(this::toDistributionJobLine)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private @org.jetbrains.annotations.Nullable PlantInfo extractPlantInfo(final I_DD_Order ddOrder)
	{
		return Optional.ofNullable(ResourceId.ofRepoIdOrNull(ddOrder.getPP_Plant_ID()))
				.map(loadingSupportServices::getPlantInfo)
				.orElse(null);
	}

	@Nullable
	static UserId extractResponsibleId(final I_DD_Order ddOrder)
	{
		return UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID());
	}

	private DistributionJobLine toDistributionJobLine(final I_DD_OrderLine ddOrderLine)
	{
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());

		return DistributionJobLine.builder()
				.id(DistributionJobLineId.ofDDOrderLineId(ddOrderLineId))
				.product(loadingSupportServices.getProductInfo(ProductId.ofRepoId(ddOrderLine.getM_Product_ID())))
				.qtyToMove(extractQtyEntered(ddOrderLine))
				.pickFromLocator(loadingSupportServices.getLocatorInfoByRepoId(ddOrderLine.getM_Locator_ID()))
				.dropToLocator(loadingSupportServices.getLocatorInfoByRepoId(ddOrderLine.getM_LocatorTo_ID()))
				.steps(getSchedules(ddOrderLineId)
						.stream()
						.map(schedule -> toDistributionJobStep(schedule, loadingSupportServices))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static Quantity extractQtyEntered(final I_DD_OrderLine ddOrderLine)
	{
		return Quantitys.of(ddOrderLine.getQtyEntered(), UomId.ofRepoId(ddOrderLine.getC_UOM_ID()));
	}

	public static DistributionJobStep toDistributionJobStep(
			@NonNull final DDOrderMoveSchedule schedule,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices)
	{
		return DistributionJobStep.builder()
				.id(DistributionJobStepId.ofScheduleId(schedule.getId()))
				.qtyToMoveTarget(schedule.getQtyToPick())
				//
				// Pick From
				.pickFromHU(loadingSupportServices.getHUInfo(schedule.getPickFromHUId()))
				.qtyPicked(schedule.getQtyPicked())
				.qtyNotPickedReasonCode(schedule.getQtyNotPickedReason())
				.isPickedFromLocator(schedule.isPickedFrom())
				.inTransitLocatorId(schedule.getInTransitLocatorId().orElse(null))
				//
				// Drop To
				.isDroppedToLocator(schedule.isDropTo())
				//
				.build();

	}

	private void warmUpById(final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = loadingSupportServices.getDDOrderById(ddOrderId);
		addToCache(ddOrder);
	}

	private void addToCache(@NonNull final I_DD_Order ddOrder)
	{
		addToCache(ImmutableList.of(ddOrder));
	}

	private void addToCache(@NonNull final List<I_DD_Order> ddOrders)
	{
		ddOrders.forEach(ddOrder -> ddOrdersCache.put(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrder));

		CollectionUtils.getAllOrLoad(ddOrderLinesCache, ddOrdersCache.keySet(), loadingSupportServices::getLinesByDDOrderIds);

		final ImmutableSet<DDOrderLineId> ddOrderLineIds = ddOrderLinesCache.values()
				.stream()
				.flatMap(Collection::stream)
				.map(ddOrderLine -> DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
		CollectionUtils.getAllOrLoad(schedulesCache, ddOrderLineIds, loadingSupportServices::getSchedulesByDDOrderLineIds);
	}

	I_DD_Order getDDOrder(final DDOrderId ddOrderId)
	{
		return ddOrdersCache.computeIfAbsent(ddOrderId, loadingSupportServices::getDDOrderById);
	}

	private List<I_DD_OrderLine> getDDOrderLines(final DDOrderId ddOrderId)
	{
		return CollectionUtils.getOrLoad(ddOrderLinesCache, ddOrderId, loadingSupportServices::getLinesByDDOrderIds);
	}

	private List<DDOrderMoveSchedule> getSchedules(final DDOrderLineId ddOrderLineId)
	{
		return CollectionUtils.getOrLoad(schedulesCache, ddOrderLineId, loadingSupportServices::getSchedulesByDDOrderLineIds);
	}
}
