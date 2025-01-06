/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.inout;

import com.google.common.annotations.VisibleForTesting;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.lang.SOTrx;
import de.metas.material.MovementType;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

@Repository
public class InOutRepository
{
    @NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
    @NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
    @NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
    @VisibleForTesting
    public static InOutRepository newInstanceForUnitTesting()
    {
        Adempiere.assertUnitTestMode();
        return new InOutRepository();
    }

    @NonNull
    public InOut getById (@NonNull final InOutId inOutId)
    {
        return fromRecord(getRecordById(inOutId));
    }

    @NonNull
    private I_M_InOut getRecordById (@NonNull final InOutId inOutId)
    {
        return InterfaceWrapperHelper.load(inOutId, I_M_InOut.class);
    }

    private InOut fromRecord(@NonNull final I_M_InOut inOutRecord)
    {
        final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(inOutRecord.getAD_Client_ID(), inOutRecord.getAD_Org_ID());
        final OrgId orgId = clientAndOrgId.getOrgId();
        final OrderId orderId = OrderId.ofRepoId(inOutRecord.getC_Order_ID());

        return InOut.builder()
                .clientAndOrgId(clientAndOrgId)
                .docTypeId(DocTypeId.ofRepoId(inOutRecord.getC_DocType_ID()))
                .soTrx(SOTrx.ofBoolean(inOutRecord.isSOTrx()))
                .bPartnerLocationAndCaptureId(InOutDocumentLocationAdapterFactory
                                                      .locationAdapter(inOutRecord)
                                                      .getBPartnerLocationAndCaptureId())
                .orderId(orderId)
                .dateAcct(LocalDateAndOrgId.ofTimestamp(inOutRecord.getDateAcct(), orgId, orgDAO::getTimeZone))
                .movementDate(LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(), orgId, orgDAO::getTimeZone))
                .movementType(MovementType.ofCode(inOutRecord.getMovementType()))
                .deliveryRule(DeliveryRule.ofCode(inOutRecord.getDeliveryRule()))
                .deliveryViaRule(DeliveryViaRule.ofCode(inOutRecord.getDeliveryViaRule()))
                .dateOrdered(LocalDateAndOrgId.ofNullableTimestamp(inOutRecord.getDateOrdered(), orgId, orgDAO::getTimeZone))
                .poReference(inOutRecord.getPOReference())
                .description(inOutRecord.getDescription())
                .warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
                .docStatus(DocStatus.Drafted)
                .docAction(IDocument.ACTION_Complete)
                .lines(queryBL.createQueryBuilder(I_M_InOutLine.class)
                               .addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOutRecord.getM_InOut_ID())
                               .create()
                               .stream()
                               .map(this::fromRecord)
                               .toList())
                .build();
    }

    private InOutLine fromRecord(@NonNull final I_M_InOutLine inOutLineRecord)
    {
        final UomId uomId = UomId.ofRepoId(inOutLineRecord.getC_UOM_ID());

        return InOutLine.builder()
                .inOutAndLineId(InOutAndLineId.ofRepoId(inOutLineRecord.getM_InOut_ID(), inOutLineRecord.getM_InOutLine_ID()))
                .productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
                .attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(inOutLineRecord.getM_AttributeSetInstance_ID()))
                .qtyEntered(Quantitys.of(inOutLineRecord.getQtyEntered(), uomId))
                .movementQuantity(Quantitys.of(inOutLineRecord.getMovementQty(), uomId))
                .orderAndLineId(OrderAndLineId.ofRepoIdsOrNull(inOutLineRecord.getC_Order_ID(), inOutLineRecord.getC_OrderLine_ID()))
                .lineNo(SeqNo.ofInt(inOutLineRecord.getLine()))
                .locatorId(warehouseBL.getLocatorIdByRepoId(inOutLineRecord.getM_Locator_ID()))
                .yearAndCalendarId(YearAndCalendarId.ofRepoIdOrNull(inOutLineRecord.getHarvesting_Year_ID(), inOutLineRecord.getC_Harvesting_Calendar_ID()))
                .build();
    }

    public I_M_InOut save(final InOut inOut)
    {
        final I_M_InOut inOutRecord = inOut.getInOutId() != null
                ? getRecordById(inOut.getInOutId())
                : InterfaceWrapperHelper.newInstance(I_M_InOut.class);

        updateRecord(inOutRecord, inOut);
        InterfaceWrapperHelper.saveRecord(inOutRecord);

        final InOutId inOutId = InOutId.ofRepoId(inOutRecord.getM_InOut_ID());
        for(final InOutLine line : inOut.getLines() )
        {
            final I_M_InOutLine inOutLineRecord = line.getId() != null
                    ? getLineRecordById(line.getId())
                    : InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
            updateRecord(inOutLineRecord, line, inOutId);
            InterfaceWrapperHelper.saveRecord(inOutLineRecord);
        }

        return inOutRecord;
    }

    private void updateRecord(@NonNull final I_M_InOut inOutRecord, @NonNull final InOut inOut)
    {
        inOutRecord.setIsSOTrx(inOut.getSoTrx().toBoolean());
        inOutRecord.setC_DocType_ID(inOut.getDocTypeId().getRepoId());
        inOutRecord.setDocStatus(inOut.getDocStatus().getCode());
        inOutRecord.setDocAction(inOut.getDocAction());
        inOutRecord.setC_Order_ID(inOut.getOrderId().getRepoId());
        inOutRecord.setPOReference(inOut.getPoReference());
        inOutRecord.setDescription(inOut.getDescription());
        inOutRecord.setM_Warehouse_ID(inOut.getWarehouseId().getRepoId());
        inOutRecord.setAD_Org_ID(inOut.getOrgId().getRepoId());
        inOutRecord.setDateAcct(inOut.getDateAcct().toTimestamp(orgDAO::getTimeZone));
        inOutRecord.setDateOrdered(LocalDateAndOrgId.toTimestamp(inOut.getDateOrdered(), orgDAO::getTimeZone));
        inOutRecord.setMovementDate(inOut.getMovementDate().toTimestamp(orgDAO::getTimeZone));
        inOutRecord.setMovementType(inOut.getMovementType().getCode());
        inOutRecord.setDeliveryRule(inOut.getDeliveryRule().getCode());
        inOutRecord.setDeliveryViaRule(inOut.getDeliveryViaRule().getCode());

        InOutDocumentLocationAdapterFactory.locationAdapter(inOutRecord)
                .setFrom(inOut.getBPartnerLocationAndCaptureId());
    }

    @NonNull
    private I_M_InOutLine getLineRecordById (@NonNull final InOutLineId inOutLineId)
    {
        return InterfaceWrapperHelper.load(inOutLineId, I_M_InOutLine.class);
    }

    private void updateRecord(
            @NonNull final I_M_InOutLine inOutLineRecord,
            @NonNull final InOutLine inOutLine,
            @NonNull final InOutId inOutId)
    {


        inOutLineRecord.setM_InOut_ID(inOutId.getRepoId());
        inOutLineRecord.setC_Order_ID(OrderId.toRepoId(inOutLine.getOrderId()));
        inOutLineRecord.setC_OrderLine_ID(OrderLineId.toRepoId(inOutLine.getOrderLineId()));
        inOutLineRecord.setM_Product_ID(inOutLine.getProductId().getRepoId());
        inOutLineRecord.setM_AttributeSetInstance_ID(inOutLine.getAttributeSetInstanceId().getRepoId());
        inOutLineRecord.setQtyEntered(inOutLine.getQtyEntered().toBigDecimal());
        inOutLineRecord.setMovementQty(inOutLine.getMovementQuantity().toBigDecimal());
        inOutLineRecord.setM_Locator_ID(inOutLine.getLocatorId().getRepoId());
        inOutLineRecord.setC_UOM_ID(inOutLine.getQtyEntered().getUomId().getRepoId());
        inOutLineRecord.setLine(inOutLine.getLineNo().toInt());
        inOutLineRecord.setC_Harvesting_Calendar_ID(YearAndCalendarId.toCalendarRepoId(inOutLine.getYearAndCalendarId()));
        inOutLineRecord.setHarvesting_Year_ID(YearAndCalendarId.toYearRepoId(inOutLine.getYearAndCalendarId()));
    }
}
