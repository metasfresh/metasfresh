/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.shippingnotification;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingNotificationService
{
	private final ShippingNotificationRepository shippingNotificationRepository;
	private final DocTypeService docTypeService;
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public ShippingNotification getByRecord(@NonNull final I_M_Shipping_Notification record) {return shippingNotificationRepository.getByRecord(record);}

	public void generateShippingNotificationAndPropagatePhysicalClearanceDate(
			@NonNull final OrderId salesOrderId,
			@NonNull final Instant physicalClearanceDate)
	{
		reverseBySalesOrderId(salesOrderId);

		final I_C_Order salesOrderRecord = orderBL.getById(salesOrderId);
		final OrgId orgId = OrgId.ofRepoId(salesOrderRecord.getAD_Org_ID());

		final Collection<I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulePA.getByIds(shipmentSchedulePA.retrieveScheduleIdsByOrderId(salesOrderId), I_M_ShipmentSchedule.class).values();

		final ShippingNotification shippingNotification = ShippingNotification.builder()
				.orgId(orgId)
				.docTypeId(docTypeService.getDocTypeId(DocBaseType.ShippingNotification, null, orgId))
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

		shippingNotificationRepository.save(shippingNotification);

		completeIt(shippingNotification);
	}

	public ShippingNotificationLine toShippingNotificationLine(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShippingNotificationLine.builder()
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID()))
				.qty(shipmentScheduleEffectiveBL.getQtyToDeliver(shipmentSchedule))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID()))
				.build();
	}

	public void updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Consumer<ShippingNotification> consumer)
	{
		shippingNotificationRepository.updateWhileSaving(record, shippingNotification -> {
			consumer.accept(shippingNotification);
			return null;
		});
	}

	public <R> R updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Function<ShippingNotification, R> consumer)
	{
		return shippingNotificationRepository.updateWhileSaving(record, consumer);
	}

	public void save(final ShippingNotification shippingNotification)
	{
		shippingNotificationRepository.save(shippingNotification);
	}

	public void completeIt(final ShippingNotification shippingNotification)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = shippingNotificationRepository.saveAndGetRecord(shippingNotification);
		documentBL.processEx(shippingNotificationRecord, IDocument.ACTION_Complete, null);
	}

	public void reverseBySalesOrderId(@NonNull final OrderId orderId)
	{
		final Set<ShippingNotificationId> shippingNotificationIds = shippingNotificationRepository.listIds(ShippingNotificationQuery.completedOrClosedByOrderId(orderId));
		reverseByIds(shippingNotificationIds);
	}

	public void reverseByIds(final Set<ShippingNotificationId> shippingNotificationIds)
	{
		shippingNotificationRepository.getRecordsByIds(shippingNotificationIds)
				.forEach(this::reverseByRecord);
	}

	private void reverseByRecord(final I_M_Shipping_Notification record)
	{
		documentBL.processEx(record, IDocument.ACTION_Reverse_Correct, null);
	}

	public boolean hasCompletedOrClosedShippingNotifications(@NonNull final OrderId orderId)
	{
		return shippingNotificationRepository.anyMatch(ShippingNotificationQuery.completedOrClosedByOrderId(orderId));
	}

}
