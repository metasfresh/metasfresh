package de.metas.order.shippingnotification;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
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
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_DocType;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShippingNotificationFromOrderProducer
{
	private final ShippingNotificationService shippingNotificationService;
	private final IOrderBL orderBL;
	private final IOrderLineBL orderLineBL;
	private final DocTypeService docTypeService;
	private final IDocumentLocationBL documentLocationBL;
	private final IWarehouseBL warehouseBL;

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

		for (final OrderId salesOrderId : salesOrderIds)
		{
			final I_C_Order salesOrderRecord = salesOrderRecords.get(salesOrderId);

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(salesOrderRecord.getAD_Client_ID(), salesOrderRecord.getAD_Org_ID());
			final WarehouseId warehouseId = WarehouseId.ofRepoId(salesOrderRecord.getM_Warehouse_ID());
			final ShippingNotification shippingNotification = ShippingNotification.builder()
					.clientAndOrgId(clientAndOrgId)
					.docTypeId(docTypeService.getDocTypeId(DocBaseType.ShippingNotification, X_C_DocType.DOCSUBTYPE_ProForma, clientAndOrgId.getOrgId()))
					.bpartnerAndLocationId(orderBL.getShipToLocationId(salesOrderRecord).getBpartnerLocationId())
					.contactId(orderBL.getShipToContactId(salesOrderRecord).orElse(null))
					.salesOrderId(OrderId.ofRepoId(salesOrderRecord.getC_Order_ID()))
					.auctionId(salesOrderRecord.getC_Auction_ID())
					.dateAcct(physicalClearanceDate)
					.physicalClearanceDate(physicalClearanceDate)
					.locatorId(LocatorId.ofRepoId(warehouseId, salesOrderRecord.getM_Locator_ID()))
					.shipFromBPartnerAndLocationId(warehouseBL.getBPartnerLocationId(warehouseId))
					.shipFromContactId(warehouseBL.getBPartnerContactId(warehouseId))
					.harvestingYearId(extractHarvestingYearId(salesOrderRecord).orElse(null))
					.poReference(salesOrderRecord.getPOReference())
					.description(salesOrderRecord.getDescription())
					.docStatus(DocStatus.Drafted)
					.docAction(IDocument.ACTION_Complete)
					.lines(orderLineBL.getByOrderIds(ImmutableSet.of(salesOrderId)).stream().map(this::toShippingNotificationLine).collect(Collectors.toList()))
					.build();

			shippingNotification.updateBPAddress(documentLocationBL::computeRenderedAddressString);
			shippingNotification.updateShipFromBPAddress(documentLocationBL::computeRenderedAddressString);
			shippingNotification.renumberLines();

			shippingNotificationService.completeIt(shippingNotification);
		}
	}

	@NonNull
	private static Optional<YearAndCalendarId> extractHarvestingYearId(final I_C_Order salesOrderRecord)
	{
		return Optional.ofNullable(YearAndCalendarId.ofRepoIdOrNull(salesOrderRecord.getHarvesting_Year_ID(), salesOrderRecord.getC_Harvesting_Calendar_ID()));
	}

	private ShippingNotificationLine toShippingNotificationLine(@NonNull final I_C_OrderLine orderLine)
	{
		return ShippingNotificationLine.builder()
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID()))
				.qty(orderLineBL.getQtyOrdered((de.metas.interfaces.I_C_OrderLine)orderLine))
				.shipmentScheduleId(null) //TODO nullable
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID()))
				.build();
	}
}
