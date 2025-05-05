package de.metas.inoutcandidate.shippingnotification;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.ShippingNotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_Order;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShippingNotificationFromShipmentScheduleProducer
{
	private static final AdMessageKey MSG_M_Shipment_Notification_NoShipmentSchedule = AdMessageKey.of("de.metas.shippingnotification.NoShipmentSchedule");

	private final ShippingNotificationService shippingNotificationService;
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IOrderBL orderBL;
	private final DocTypeService docTypeService;
	private final IDocumentLocationBL documentLocationBL;

	public ProcessPreconditionsResolution checkCanCreateShippingNotification(@NonNull final OrderId salesOrderId)
	{
		return checkCanCreateShippingNotification(ImmutableSet.of(salesOrderId));
	}

	public ProcessPreconditionsResolution checkCanCreateShippingNotification(@NonNull final Collection<OrderId> salesOrderIds)
	{
		if (salesOrderIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		final List<I_C_Order> salesOrders = orderBL.getByIds(salesOrderIds);
		if (salesOrders.stream().anyMatch(salesOrder -> !isCompleted(salesOrder)))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only completed orders");
		}

		if (!shipmentScheduleBL.anyMatchByOrderIds(salesOrderIds))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(MSG_M_Shipment_Notification_NoShipmentSchedule);
		}

		return ProcessPreconditionsResolution.accept();
	}

	private boolean isCompleted(final I_C_Order salesOrder)
	{
		return DocStatus.ofNullableCodeOrUnknown(salesOrder.getDocStatus()).isCompleted();
	}

	public void createShippingNotification(
			@NonNull final OrderId salesOrderId,
			@NonNull final Instant physicalClearanceDate)
	{
		createShippingNotification(ImmutableSet.of(salesOrderId), physicalClearanceDate);
	}

	public void createShippingNotification(
			@NonNull final Set<OrderId> salesOrderIds,
			@NonNull final Instant physicalClearanceDate)
	{
		if (salesOrderIds.isEmpty())
		{
			return;
		}

		shippingNotificationService.reverseBySalesOrderIds(salesOrderIds);

		final ImmutableMap<OrderId, I_C_Order> salesOrderRecords = Maps.uniqueIndex(
				orderBL.getByIds(salesOrderIds),
				salesOrderRecord -> OrderId.ofRepoId(salesOrderRecord.getC_Order_ID())
		);
		final ImmutableListMultimap<OrderId, I_M_ShipmentSchedule> shipmentSchedulesByOrderId = Multimaps.index(
				shipmentScheduleBL.getByOrderIds(salesOrderIds),
				shipmentSchedule -> OrderId.ofRepoId(shipmentSchedule.getC_Order_ID())
		);

		for (final OrderId salesOrderId : salesOrderIds)
		{
			final I_C_Order salesOrderRecord = salesOrderRecords.get(salesOrderId);
			final ImmutableList<I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulesByOrderId.get(salesOrderId);
			if (shipmentSchedules.isEmpty())
			{
				continue;
			}

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(salesOrderRecord.getAD_Client_ID(), salesOrderRecord.getAD_Org_ID());
			final ShippingNotification shippingNotification = ShippingNotification.builder()
					.clientAndOrgId(clientAndOrgId)
					.docTypeId(docTypeService.getDocTypeId(DocBaseType.ShippingNotification, clientAndOrgId.getOrgId()))
					.bpartnerAndLocationId(orderBL.getShipToLocationId(salesOrderRecord).getBpartnerLocationId())
					.contactId(orderBL.getShipToContactId(salesOrderRecord).orElse(null))
					.salesOrderId(OrderId.ofRepoId(salesOrderRecord.getC_Order_ID()))
					.auctionId(salesOrderRecord.getC_Auction_ID())
					.dateAcct(physicalClearanceDate)
					.physicalClearanceDate(physicalClearanceDate)
					.locatorId(LocatorId.ofRepoId(salesOrderRecord.getM_Warehouse_ID(), salesOrderRecord.getM_Locator_ID()))
					.harvestingYearId(extractHarvestingYearId(salesOrderRecord).orElse(null))
					.poReference(salesOrderRecord.getPOReference())
					.description(salesOrderRecord.getDescription())
					.docStatus(DocStatus.Drafted)
					.docAction(IDocument.ACTION_Complete)
					.lines(shipmentSchedules.stream().map(this::toShippingNotificationLine).collect(Collectors.toList()))
					.build();

			shippingNotification.updateBPAddress(documentLocationBL::computeRenderedAddressString);
			shippingNotification.renumberLines();

			shippingNotificationService.completeIt(shippingNotification);
		}
	}

	@NonNull
	private static Optional<YearAndCalendarId> extractHarvestingYearId(final I_C_Order salesOrderRecord)
	{
		return Optional.ofNullable(YearAndCalendarId.ofRepoIdOrNull(salesOrderRecord.getHarvesting_Year_ID(), salesOrderRecord.getC_Harvesting_Calendar_ID()));
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
