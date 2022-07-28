package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

class DistributionJobLoader
{
	private final DistributionJobLoaderSupportingServices loadingSupportServices;

	private final HashMap<DDOrderId, I_DD_Order> ddOrdersCache = new HashMap<>();
	private final HashMap<DDOrderId, ImmutableList<I_DD_OrderLine>> ddOrderLinesCache = new HashMap<>();
	private final HashMap<DDOrderId, ImmutableListMultimap<DDOrderLineId, DDOrderMoveSchedule>> schedulesCache = new HashMap<>();

	public DistributionJobLoader(@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices)
	{
		this.loadingSupportServices = loadingSupportServices;
	}

	public DistributionJob load(final I_DD_Order ddOrder)
	{
		addToCache(ddOrder);
		return load0(ddOrder);
	}

	public DistributionJob load(final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = getDDOrder(ddOrderId);
		return load0(ddOrder);
	}

	private DistributionJob load0(final I_DD_Order ddOrder)
	{
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

		final ZonedDateTime dateRequired = InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID())
				.toZonedDateTime(loadingSupportServices::getTimeZone);

		return DistributionJob.builder()
				.ddOrderId(ddOrderId)
				.documentNo(ddOrder.getDocumentNo())
				.dateRequired(dateRequired)
				.pickFromWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_From_ID()))
				.dropToWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_To_ID()))
				.responsibleId(UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID()))
				.lines(getDDOrderLines(ddOrderId)
						.stream()
						.map(this::toDistributionJobLine)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private DistributionJobLine toDistributionJobLine(final I_DD_OrderLine ddOrderLine)
	{
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrderLine.getDD_Order_ID());
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());

		return DistributionJobLine.builder()
				.ddOrderLineId(ddOrderLineId)
				.product(loadingSupportServices.getProductInfo(ProductId.ofRepoId(ddOrderLine.getM_Product_ID())))
				.pickFromLocator(loadingSupportServices.getLocatorInfoByRepoId(ddOrderLine.getM_Locator_ID()))
				.dropToLocator(loadingSupportServices.getLocatorInfoByRepoId(ddOrderLine.getM_LocatorTo_ID()))
				.steps(getSchedules(ddOrderId, ddOrderLineId)
						.stream()
						.map(DistributionJobLoader::toDistributionJobStep)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public static DistributionJobStep toDistributionJobStep(final DDOrderMoveSchedule schedule)
	{
		return DistributionJobStep.builder()
				.id(schedule.getId())
				.qtyToMoveTarget(schedule.getQtyToPick())
				//
				// Pick From
				.pickFromHUId(schedule.getPickFromHUId())
				.actualHUPicked(schedule.getActualHUIdPicked())
				.qtyPicked(schedule.getQtyPicked())
				.qtyNotPickedReasonCode(schedule.getQtyNotPickedReason())
				//
				// Drop To
				.droppedToLocator(schedule.getDropToMovementLineId() != null)
				//
				.build();

	}

	private void addToCache(@NonNull final I_DD_Order ddOrder)
	{
		ddOrdersCache.put(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrder);
	}

	private I_DD_Order getDDOrder(final DDOrderId ddOrderId)
	{
		return ddOrdersCache.computeIfAbsent(ddOrderId, loadingSupportServices::getDDOrderById);
	}

	private List<I_DD_OrderLine> getDDOrderLines(final DDOrderId ddOrderId)
	{
		return ddOrderLinesCache.computeIfAbsent(ddOrderId, this::retrieveDDOrderLines);
	}

	private ImmutableList<I_DD_OrderLine> retrieveDDOrderLines(final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = getDDOrder(ddOrderId);
		return ImmutableList.copyOf(loadingSupportServices.getLines(ddOrder));
	}

	private List<DDOrderMoveSchedule> getSchedules(final DDOrderId ddOrderId, final DDOrderLineId ddOrderLineId)
	{
		return schedulesCache.computeIfAbsent(ddOrderId, this::retrieveSchedules)
				.get(ddOrderLineId);
	}

	private ImmutableListMultimap<DDOrderLineId, DDOrderMoveSchedule> retrieveSchedules(final DDOrderId ddOrderId)
	{
		return Multimaps.index(loadingSupportServices.getSchedules(ddOrderId), DDOrderMoveSchedule::getDdOrderLineId);
	}

}
