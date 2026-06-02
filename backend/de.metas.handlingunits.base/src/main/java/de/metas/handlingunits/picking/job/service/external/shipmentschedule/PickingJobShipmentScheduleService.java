package de.metas.handlingunits.picking.job.service.external.shipmentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.ShipmentScheduleLoadingCache;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PickingJobShipmentScheduleService
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;

	public static PickingJobShipmentScheduleService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PickingJobShipmentScheduleService.class,
				() -> new PickingJobShipmentScheduleService(ShipmentScheduleRepository.newInstanceForUnitTesting())
		);
	}

	public ShipmentScheduleInfoLoadingCache newLoadingCache()
	{
		return new ShipmentScheduleInfoLoadingCache(this);
	}

	public ShipmentScheduleInfo getById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = huShipmentScheduleBL.getById(shipmentScheduleId);

		return fromRecord(shipmentSchedule);
	}

	public Map<ShipmentScheduleId, ShipmentScheduleInfo> getByIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		final Map<ShipmentScheduleId, de.metas.handlingunits.model.I_M_ShipmentSchedule> map = huShipmentScheduleBL.getByIds(shipmentScheduleIds);
		final HashMap<ShipmentScheduleId, ShipmentScheduleInfo> result = new HashMap<>(map.size());
		map.forEach((shipmentScheduleId, shipmentSchedule) -> result.put(shipmentScheduleId, fromRecord(shipmentSchedule)));
		return result;
	}

	public Map<ShipmentScheduleId, de.metas.handlingunits.model.I_M_ShipmentSchedule> getByIdsAsRecordMap(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return huShipmentScheduleBL.getByIds(ids);
	}

	public I_M_ShipmentSchedule getByIdAsRecord(@NonNull final ShipmentScheduleId id)
	{
		return huShipmentScheduleBL.getById(id);
	}

	public Quantity getQtyToDeliver(@NonNull final I_M_ShipmentSchedule schedule)
	{
		return huShipmentScheduleBL.getQtyToDeliver(schedule);
	}

	public Quantity getQtyScheduledForPicking(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		return huShipmentScheduleBL.getQtyScheduledForPicking(shipmentScheduleRecord);
	}

	public ImmutableList<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		return shipmentScheduleRepository.getBy(query);
	}

	private ShipmentScheduleInfo fromRecord(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShipmentScheduleInfo.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID()))
				.warehouseId(shipmentScheduleBL.getWarehouseId(shipmentSchedule))
				.bpartnerId(shipmentScheduleBL.getBPartnerId(shipmentSchedule))
				.salesOrderLineId(Optional.ofNullable(OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID())))
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID()))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(shipmentSchedule.getShipmentAllocation_BestBefore_Policy()))
				.record(shipmentSchedule)
				.build();
	}

	public void addQtyPickedAndUpdateHU(final AddQtyPickedRequest request)
	{
		huShipmentScheduleBL.addQtyPickedAndUpdateHU(request);
	}

	public void deleteByTopLevelHUsAndShipmentScheduleId(@NonNull final Collection<I_M_HU> topLevelHUs, @NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		huShipmentScheduleBL.deleteByTopLevelHUsAndShipmentScheduleId(topLevelHUs, shipmentScheduleId);
	}

	public Stream<Packageable> stream(@NonNull final PackageableQuery query)
	{
		return packagingDAO.stream(query);
	}

	/**
	 * Excludes packageables whose pending pick qty is entirely bound to a draft (not-yet-completed) shipment.
	 * <p>
	 * A fully-picked order that has been put on a draft shipment leaves its shipment schedule with a positive
	 * QtyToDeliver/QtyPickList (the draft shipment is not deducted until completed), so it would otherwise
	 * reappear in the mobileUI picking launcher. We discriminate those schedules via the very same criterion
	 * used by shipment generation ({@code M_InOutLine_ID IS NOT NULL AND Processed='N'}).
	 * <p>
	 * Reversed shipments replay picked rows with {@code M_InOutLine_ID IS NULL} and are therefore NOT matched by
	 * the DAO, so they correctly stay visible for re-picking.
	 * <p>
	 * The stream is materialized once and the draft-allocation lookup is a single batched DAO call (no N+1).
	 */
	public Stream<Packageable> filterOutDraftShipmentBound(@NonNull final Stream<Packageable> packageables)
	{
		final ImmutableList<Packageable> materialized = packageables.collect(ImmutableList.toImmutableList());
		if (materialized.isEmpty())
		{
			return Stream.of();
		}

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = materialized.stream()
				.map(Packageable::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<ShipmentScheduleId> draftShipmentBoundIds =
				shipmentScheduleAllocDAO.getScheduleIdsWithDraftShipmentAllocations(shipmentScheduleIds);
		if (draftShipmentBoundIds.isEmpty())
		{
			return materialized.stream();
		}

		return materialized.stream()
				.filter(packageable -> !draftShipmentBoundIds.contains(packageable.getShipmentScheduleId()));
	}

	public Quantity getQtyRemainingToScheduleForPicking(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		return huShipmentScheduleBL.getQtyRemainingToScheduleForPicking(shipmentScheduleRecord);
	}

	public void flagForRecompute(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		huShipmentScheduleBL.flagForRecompute(shipmentScheduleIds);
	}

	public ShipmentScheduleLoadingCache<de.metas.handlingunits.model.I_M_ShipmentSchedule> newHuShipmentLoadingCache()
	{
		return huShipmentScheduleBL.newLoadingCache();
	}

}
