package de.metas.shipper.gateway.nshift;

import de.metas.shipper.gateway.commons.model.ShipmentOrderRepository;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NShiftDeliveryOrderService implements DeliveryOrderService
{
	private final NShiftDraftDeliveryOrderCreator deliveryOrderCreator;
	private final ShipmentOrderRepository shipmentOrderRepository;
	private final NShiftShipperGatewayClientFactory clientFactory;

	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@NonNull
	@Override
	public @NotNull DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		return deliveryOrderCreator.createDraftDeliveryOrder(request);
	}

	//TODO Adrian refactor in next iteration to take a DeliveryOrderParcel as param, since now parcels are 1-to-1 with Shipment
	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null for deliveryOrder " + deliveryOrder);
		return TableRecordReference.of(I_Carrier_ShipmentOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderRepoId)
	{
		return shipmentOrderRepository.getById(deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder order)
	{
		return shipmentOrderRepository.save(order);
	}

	@Override
	public @NonNull @NotNull ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		return clientFactory.newClientForShipperId(shipperId);
	}
}
