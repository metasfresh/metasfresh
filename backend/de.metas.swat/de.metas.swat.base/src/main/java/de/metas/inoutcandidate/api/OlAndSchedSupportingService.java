package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class OlAndSchedSupportingService
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public IDeliverRequest createDeliverRequest(final @NonNull I_M_ShipmentSchedule shipmentSchedule, @Nullable final I_C_OrderLine orderLine)
	{
		return shipmentScheduleHandlerBL.createDeliverRequest(shipmentSchedule, orderLine);
	}

	public @NonNull WarehouseId getWarehouseId(final @NonNull I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule);
	}

	public @NonNull BPartnerId getBPartnerId(final @NonNull I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
	}

	public DeliveryRule getDeliveryRule(final @NonNull I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getDeliveryRule(shipmentSchedule);
	}

	public I_C_UOM getUOMById(final UomId uomId)
	{
		return uomDAO.getById(uomId);
	}

	public Map<OrderAndLineId, de.metas.interfaces.I_C_OrderLine> getOrderLinesByIds(final Set<OrderAndLineId> orderLineIds)
	{
		return orderDAO.getOrderLinesByIds(orderLineIds);
	}

	public Iterable<I_C_Order> getOrdersByIds(final ImmutableSet<OrderId> orderIds, final Class<I_C_Order> orderType)
	{
		return orderDAO.getByIds(orderIds, orderType);
	}
}
