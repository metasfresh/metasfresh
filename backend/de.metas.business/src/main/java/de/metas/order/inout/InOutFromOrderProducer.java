/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.order.inout;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.inout.InOut;
import de.metas.inout.InOutLine;
import de.metas.inout.InOutService;
import de.metas.inout.event.InOutUserNotificationsProducer;
import de.metas.lang.SOTrx;
import de.metas.material.MovementType;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class InOutFromOrderProducer
{
    @NonNull private final IOrderBL orderBL;
    @NonNull private final IOrgDAO orgDAO;
    @NonNull private final IOrderLineBL orderLineBL;
    @NonNull private final DocTypeService docTypeService;
    @NonNull private final InOutService inOutService;
    @NonNull private final InOutUserNotificationsProducer inOutUserNotificationsProducer;
    @NonNull private final IWarehouseBL warehouseBL;

    public ProcessPreconditionsResolution checkCanCreateShipment(@NonNull final OrderId salesOrderId)
    {
        return checkCanCreateShipment(ImmutableSet.of(salesOrderId));
    }

    public ProcessPreconditionsResolution checkCanCreateShipment(@NonNull final Collection<OrderId> orderIds)
    {
        if (orderIds.isEmpty())
        {
            return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
        }

        if(orderIds.stream().anyMatch(orderId -> !orderBL.isProformaSO(orderId)))
        {
            return ProcessPreconditionsResolution.rejectWithInternalReason("Only proforma sales orders");
        }

        if (orderIds.stream().anyMatch(orderId -> !orderBL.getDocStatus(orderId).isCompleted()))
        {
            return ProcessPreconditionsResolution.rejectWithInternalReason("Only completed orders");
        }
        return ProcessPreconditionsResolution.accept();
    }

    public void createShipment(
            @NonNull final OrderId orderId,
            @NonNull final LocalDate movementDateParam)
    {
        createShipment(ImmutableSet.of(orderId), movementDateParam);
    }

    public void createShipment(
            @NonNull final Set<OrderId> orderIds,
            @NonNull final LocalDate movementDateParam)
    {
        if (orderIds.isEmpty())
        {
            return;
        }

        final ImmutableMap<OrderId, I_C_Order> salesOrderRecords = Maps.uniqueIndex(
                orderBL.getByIds(orderIds),
                salesOrderRecord -> OrderId.ofRepoId(salesOrderRecord.getC_Order_ID())
        );

        final List<I_M_InOut> generatedInOutRecords = new ArrayList<>();
        for (final OrderId orderId : orderIds)
        {
            final I_C_Order orderRecord = salesOrderRecords.get(orderId);

            final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(orderRecord.getAD_Client_ID(), orderRecord.getAD_Org_ID());
            final OrgId orgId = clientAndOrgId.getOrgId();
            final LocalDateAndOrgId movementDate = LocalDateAndOrgId.ofLocalDate(movementDateParam, orgId);

            final InOut inout = InOut.builder()
                    .clientAndOrgId(clientAndOrgId)
                    .docTypeId(docTypeService.getDocTypeId(DocBaseType.Shipment, DocSubType.Proforma, clientAndOrgId.getOrgId()))
                    .soTrx(SOTrx.ofBoolean(orderRecord.isSOTrx()))
                    .bPartnerLocationAndCaptureId(orderBL.getShipToLocationId(orderRecord))
                    .orderId(OrderId.ofRepoId(orderRecord.getC_Order_ID()))
                    .dateAcct(movementDate)
                    .movementDate(movementDate)
                    .movementType(MovementType.CustomerShipment)
                    .deliveryRule(DeliveryRule.ofCode(orderRecord.getDeliveryRule()))
                    .deliveryViaRule(DeliveryViaRule.ofCode(orderRecord.getDeliveryViaRule()))
                    .dateOrdered(LocalDateAndOrgId.ofTimestamp(orderRecord.getDateOrdered(), orgId, orgDAO::getTimeZone))
                    .poReference(orderRecord.getPOReference())
                    .description(orderRecord.getDescription())
                    .warehouseId(WarehouseId.ofRepoId(orderRecord.getM_Warehouse_ID()))
                    .docStatus(DocStatus.Drafted)
                    .docAction(IDocument.ACTION_Complete)
                    .lines(orderLineBL.getByOrderIds(ImmutableSet.of(orderId)).stream()
                                   .map(line -> toInOutLine(line, orderRecord))
                                   .filter(Objects::nonNull)
                                   .toList())
                    .build();

            if(!inout.getLines().isEmpty())
            {
                final I_M_InOut inoutRecord = inOutService.save(inout);
                inOutService.completeIt(inoutRecord);
                generatedInOutRecords.add(inoutRecord);
            }
            inOutUserNotificationsProducer.notifyInOutsProcessed(generatedInOutRecords);
        }
    }

    @Nullable
    private InOutLine toInOutLine(@NonNull final I_C_OrderLine orderLine, @NonNull final I_C_Order order)
    {
        final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID());
        final Quantity qty = orderLineBL.getQtyToDeliver(orderAndLineId);
        if(!qty.isPositive())
        {
            return null;
        }

        final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
        final LocatorId locatorId = LocatorId.ofRepoIdOrNull(warehouseId, order.getM_Locator_ID());

        final Quantity qtyOrdered = orderLineBL.getQtyOrdered(orderAndLineId);

        return InOutLine.builder()
                .productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
                .attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID()))
                .qtyEntered(qtyOrdered)
                .movementQuantity(qtyOrdered)
                .orderAndLineId(orderAndLineId)
                .lineNo(SeqNo.ofInt(orderLine.getLine()))
                .locatorId(locatorId != null ? locatorId : warehouseBL.getOrCreateDefaultLocatorId(warehouseId))
                .yearAndCalendarId(YearAndCalendarId.ofRepoIdOrNull(order.getHarvesting_Year_ID(), order.getC_Harvesting_Calendar_ID()))
                .build();
    }
}
