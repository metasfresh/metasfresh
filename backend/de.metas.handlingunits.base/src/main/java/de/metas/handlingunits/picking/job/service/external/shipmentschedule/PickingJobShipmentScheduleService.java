package de.metas.handlingunits.picking.job.service.external.shipmentschedule;

import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class PickingJobShipmentScheduleService
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IPackagingDAO packagingDAO = Services.get(IPackagingDAO.class);

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

}
