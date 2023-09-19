package de.metas.shippingnotification;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_Order;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Builder
class ShippingNotificationFromShipmentScheduleProducer
{
	private final ShippingNotificationService shippingNotificationService;
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IOrderBL orderBL;
	private final DocTypeService docTypeService;

	@NonNull final OrderId salesOrderId;
	@NonNull final Instant physicalClearanceDate;

	public void execute()
	{
		shippingNotificationService.reverseBySalesOrderId(salesOrderId);

		final I_C_Order salesOrderRecord = orderBL.getById(salesOrderId);
		final OrgId orgId = OrgId.ofRepoId(salesOrderRecord.getAD_Org_ID());

		final Collection<I_M_ShipmentSchedule> shipmentSchedules = shipmentScheduleBL.getByOrderId(salesOrderId);

		final ShippingNotification shippingNotification = ShippingNotification.builder()
				.orgId(orgId)
				.docTypeId(docTypeService.getDocTypeId(DocBaseType.ShippingNotification, orgId))
				.bpartnerAndLocationId(orderBL.getShipToLocationId(salesOrderRecord).getBpartnerLocationId())
				.contactId(orderBL.getShipToContactId(salesOrderRecord).orElse(null))
				.salesOrderId(OrderId.ofRepoId(salesOrderRecord.getC_Order_ID()))
				.auctionId(salesOrderRecord.getC_Auction_ID())
				.dateAcct(physicalClearanceDate)
				.physicalClearanceDate(physicalClearanceDate)
				.locatorId(LocatorId.ofRepoId(salesOrderRecord.getM_Warehouse_ID(), salesOrderRecord.getM_Locator_ID()))
				.harvestingYearId(YearAndCalendarId.ofRepoId(salesOrderRecord.getHarvesting_Year_ID(), salesOrderRecord.getC_Harvesting_Calendar_ID()))
				.poReference(salesOrderRecord.getPOReference())
				.description(salesOrderRecord.getDescription())
				.docStatus(DocStatus.Drafted)
				.docAction(IDocument.ACTION_Complete)
				.lines(shipmentSchedules.stream().map(this::toShippingNotificationLine).collect(Collectors.toList()))
				.build();

		shippingNotification.renumberLines();

		shippingNotificationService.completeIt(shippingNotification);
	}

	private ShippingNotificationLine toShippingNotificationLine(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShippingNotificationLine.builder()
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID()))
				.qty(shipmentScheduleBL.getQtyToDeliver(shipmentSchedule))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID()))
				.build();
	}

}
