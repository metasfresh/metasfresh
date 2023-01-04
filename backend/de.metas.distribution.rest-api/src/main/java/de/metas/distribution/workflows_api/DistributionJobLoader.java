package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import javax.annotation.Nullable;
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
		final DocStatus docStatus = DocStatus.ofCode(ddOrder.getDocStatus());

		final ZonedDateTime dateRequired = InstantAndOrgId.ofTimestamp(ddOrder.getDatePromised(), ddOrder.getAD_Org_ID())
				.toZonedDateTime(loadingSupportServices::getTimeZone);

		return DistributionJob.builder()
				.ddOrderId(ddOrderId)
				.documentNo(ddOrder.getDocumentNo())
				.customerId(BPartnerId.ofRepoId(ddOrder.getC_BPartner_ID()))
				.dateRequired(dateRequired)
				.pickFromWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_From_ID()))
				.dropToWarehouse(loadingSupportServices.getWarehouseInfoByRepoId(ddOrder.getM_Warehouse_To_ID()))
				.responsibleId(extractResponsibleId(ddOrder))
				.isClosed(!docStatus.isCompleted()) // NOTE: we consider closed (for us) anything which is not completed
				.lines(getDDOrderLines(ddOrderId)
						.stream()
						.map(this::toDistributionJobLine)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	static UserId extractResponsibleId(final I_DD_Order ddOrder)
	{
		return UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID());
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
						.map(schedule -> toDistributionJobStep(schedule, loadingSupportServices))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public static DistributionJobStep toDistributionJobStep(
			@NonNull final DDOrderMoveSchedule schedule,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices)
	{
		return DistributionJobStep.builder()
				.id(schedule.getId())
				.qtyToMoveTarget(schedule.getQtyToPick())
				//
				// Pick From
				.pickFromHU(toHUInfo(schedule.getPickFromHUId(), loadingSupportServices))
				.qtyPicked(schedule.getQtyPicked())
				.qtyNotPickedReasonCode(schedule.getQtyNotPickedReason())
				.isPickedFromLocator(schedule.isPickedFrom())
				//
				// Drop To
				.isDroppedToLocator(schedule.isDropTo())
				//
				.build();

	}

	private static HUInfo toHUInfo(
			@NonNull HuId huId,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices)
	{
		return HUInfo.builder()
				.id(huId)
				.qrCode(loadingSupportServices.getQRCodeByHuId(huId))
				.build();
	}

	private void addToCache(@NonNull final I_DD_Order ddOrder)
	{
		ddOrdersCache.put(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), ddOrder);
	}

	I_DD_Order getDDOrder(final DDOrderId ddOrderId)
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
