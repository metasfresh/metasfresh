/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class DeliveryPlanningRepository
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);

	private DeliveryPlanningCreateRequest ofRecord(@NonNull final I_M_Delivery_Planning record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());

		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final I_C_UOM uomOfProduct = productBL.getStockUOM(productId);

		return DeliveryPlanningCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(record.getM_ShipmentSchedule_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoIdOrNull(record.getM_ReceiptSchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.productId(productId)
				.partnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.shipperTransportationId(ShipperTransportationId.ofRepoIdOrNull(record.getM_ShipperTransportation_ID()))
				.incotermsId(IncotermsId.ofRepoIdOrNull(record.getC_Incoterms_ID()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.deliveryPlanningType(DeliveryPlanningType.ofCode(record.getM_Delivery_Planning_Type()))
				.orderStatus(OrderStatus.ofNullableCode(record.getOrderStatus()))
				.meansOfTransportation(MeansOfTransportation.ofNullableCode(record.getMeansOfTransportation()))
				.isActive(record.isActive())
				.isB2B(record.isB2B())
				.qtyOredered(Quantity.of(record.getQtyOrdered(), uomOfProduct))
				.qtyTotalOpen(Quantity.of(record.getQtyTotalOpen(), uomOfProduct))
				.actualLoadQty(Quantity.of(record.getActualLoadQty(), uomOfProduct))
				.actualDeliveredQty(Quantity.of(record.getActualDeliveredQty(), uomOfProduct))
				.plannedLoadingDate(LocalDateAndOrgId.ofTimestamp(record.getPlannedLoadingDate(), orgId, orgDAO::getTimeZone))
				.actualLoadingDate(LocalDateAndOrgId.ofTimestamp(record.getActualLoadingDate(), orgId, orgDAO::getTimeZone))
				.plannedDeliveryDate(LocalDateAndOrgId.ofTimestamp(record.getPlannedDeliveryDate(), orgId, orgDAO::getTimeZone))
				.actualDeliveryDate(LocalDateAndOrgId.ofTimestamp(record.getActualDeliveryDate(), orgId, orgDAO::getTimeZone))
				.releaseNo(record.getReleaseNo())
				.wayBillNo(record.getWayBillNo())
				.batch(record.getBatch())
				.originCountry(record.getOriginCountry())
				.build();
	}

	public void generateDeliveryPlanning(@NonNull final DeliveryPlanningCreateRequest request)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = newInstance(I_M_Delivery_Planning.class);

		deliveryPlanningRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		deliveryPlanningRecord.setM_ReceiptSchedule_ID(ReceiptScheduleId.toRepoId(request.getReceiptScheduleId()));
		deliveryPlanningRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(request.getShipmentScheduleId()));
		deliveryPlanningRecord.setC_Order_ID(OrderId.toRepoId(request.getOrderId()));
		deliveryPlanningRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getOrderLineId()));
		deliveryPlanningRecord.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		deliveryPlanningRecord.setM_Warehouse_ID(WarehouseId.toRepoId(request.getWarehouseId()));
		deliveryPlanningRecord.setM_ShipperTransportation_ID(ShipperTransportationId.toRepoId(request.getShipperTransportationId()));
		deliveryPlanningRecord.setC_BPartner_ID(BPartnerId.toRepoId(request.getPartnerId()));
		deliveryPlanningRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBPartnerLocationId()));
		deliveryPlanningRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryPlanningRecord.setM_SectionCode_ID(SectionCodeId.toRepoId(request.getSectionCodeId()));

		final LocalDateAndOrgId plannedDeliveryDate = request.getPlannedDeliveryDate();
		final LocalDateAndOrgId actualDeliveryDate = request.getActualDeliveryDate();
		final LocalDateAndOrgId plannedLoadingDate = request.getPlannedLoadingDate();
		final LocalDateAndOrgId actualLoadingDate = request.getActualLoadingDate();

		deliveryPlanningRecord.setPlannedDeliveryDate(plannedDeliveryDate == null ? null : plannedDeliveryDate.toTimestamp(orgDAO::getTimeZone));
		deliveryPlanningRecord.setActualDeliveryDate(actualDeliveryDate == null ? null : actualDeliveryDate.toTimestamp(orgDAO::getTimeZone));
		deliveryPlanningRecord.setPlannedLoadingDate(plannedLoadingDate == null ? null : plannedLoadingDate.toTimestamp(orgDAO::getTimeZone));
		deliveryPlanningRecord.setActualLoadingDate(actualLoadingDate == null ? null : actualLoadingDate.toTimestamp(orgDAO::getTimeZone));

		final Quantity qtyOredered = request.getQtyOredered();
		final Quantity qtyTotalOpen = request.getQtyTotalOpen();
		final Quantity actualDeliveredQty = request.getActualDeliveredQty();
		final Quantity actualLoadQty = request.getActualLoadQty();

		deliveryPlanningRecord.setQtyOrdered(qtyOredered == null ? null : qtyOredered.toBigDecimal());
		deliveryPlanningRecord.setQtyTotalOpen(qtyTotalOpen == null ? null : qtyTotalOpen.toBigDecimal());
		deliveryPlanningRecord.setActualDeliveredQty(actualDeliveredQty == null ? null : actualDeliveredQty.toBigDecimal());
		deliveryPlanningRecord.setActualLoadQty(actualLoadQty == null ? null : actualLoadQty.toBigDecimal());

		deliveryPlanningRecord.setForwarder(request.getForwarder());
		deliveryPlanningRecord.setWayBillNo(request.getWayBillNo());
		deliveryPlanningRecord.setReleaseNo(request.getReleaseNo());
		deliveryPlanningRecord.setTransportDetails(request.getTransportDetails());

		deliveryPlanningRecord.setIsActive(request.isActive);
		deliveryPlanningRecord.setIsB2B(request.isB2B);

		deliveryPlanningRecord.setMeansOfTransportation(MeansOfTransportation.toCodeOrNull(request.getMeansOfTransportation()));
		deliveryPlanningRecord.setOrderStatus(OrderStatus.toCodeOrNull(request.getOrderStatus()));
		deliveryPlanningRecord.setM_Delivery_Planning_Type(DeliveryPlanningType.toCodeOrNull(request.getDeliveryPlanningType()));

		final String originCountry = asiBL.getAttributeValueOrNull(AttributeConstants.CountryOfOrigin, request.getAttributeSetInstanceId());
		final String huBatchNo = asiBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, request.getAttributeSetInstanceId());

		deliveryPlanningRecord.setBatch(huBatchNo);
		deliveryPlanningRecord.setOriginCountry(originCountry);

		save(deliveryPlanningRecord);
	}

}
