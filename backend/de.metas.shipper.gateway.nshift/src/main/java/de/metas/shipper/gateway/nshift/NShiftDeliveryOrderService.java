package de.metas.shipper.gateway.nshift;

import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperGatewayId;
import org.adempiere.util.lang.ITableRecordReference;
import org.springframework.stereotype.Component;

@Component
public class NShiftDeliveryOrderService implements DeliveryOrderService
{
	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@Override
	public ITableRecordReference toTableRecordReference(final DeliveryOrder deliveryOrder)
	{
		throw new UnsupportedOperationException("not implemented yet"); // TODO
	}

	@Override
	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{
		throw new UnsupportedOperationException("not implemented yet"); // TODO
	}

	@Override
	public DeliveryOrder save(final DeliveryOrder order)
	{
		throw new UnsupportedOperationException("not implemented yet"); // TODO
	}

}
