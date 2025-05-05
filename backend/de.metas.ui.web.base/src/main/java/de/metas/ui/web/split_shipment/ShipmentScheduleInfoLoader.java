package de.metas.ui.web.split_shipment;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.order.IOrderBL;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ShipmentScheduleInfoLoader
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;

	public ShipmentScheduleInfo getById(final ShipmentScheduleId shipmentScheduleId)
	{
		final ShipmentSchedule shipmentSchedule = shipmentScheduleRepository.getById(shipmentScheduleId);

		final String salesOrderDocumentNo = shipmentSchedule.getOrderAndLineId() != null
				? orderBL.getDocumentNoById(shipmentSchedule.getOrderAndLineId().getOrderId())
				: null;

		final String shipBPartnerName = bpartnerBL.getBPartnerValueAndName(shipmentSchedule.getShipBPartnerId());
		final String billBPartnerName = shipmentSchedule.getBillBPartnerId() != null && !BPartnerId.equals(shipmentSchedule.getBillBPartnerId(), shipmentSchedule.getShipBPartnerId())
				? bpartnerBL.getBPartnerValueAndName(shipmentSchedule.getBillBPartnerId())
				: shipBPartnerName;

		final String productName = Services.get(IProductBL.class).getProductValueAndName(shipmentSchedule.getProductId());

		return ShipmentScheduleInfo.builder()
				.shipBPartnerName(shipBPartnerName)
				.billBPartnerName(billBPartnerName)
				.salesOrderDocumentNo(salesOrderDocumentNo)
				.productName(productName)
				.qtyToDeliver(shipmentSchedule.getQuantityToDeliver())
				.readonly(shipmentSchedule.isProcessed() || shipmentSchedule.isClosed() || shipmentSchedule.isDeliveryStop())
				.build();
	}
}
