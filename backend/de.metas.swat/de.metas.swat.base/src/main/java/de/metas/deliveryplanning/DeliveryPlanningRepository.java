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
import de.metas.location.CountryId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class DeliveryPlanningRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	protected I_M_Delivery_Planning getById(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return load(deliveryPlanningId, I_M_Delivery_Planning.class);
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

		deliveryPlanningRecord.setPlannedDeliveryDate(TimeUtil.asTimestamp(request.getPlannedDeliveryDate()));
		deliveryPlanningRecord.setActualDeliveryDate(TimeUtil.asTimestamp(request.getActualDeliveryDate()));
		deliveryPlanningRecord.setPlannedLoadingDate(TimeUtil.asTimestamp(request.getPlannedLoadingDate()));
		deliveryPlanningRecord.setActualLoadingDate(TimeUtil.asTimestamp(request.getActualLoadingDate()));

		final Quantity qtyOrdered = request.getQtyOredered();
		final Quantity qtyTotalOpen = request.getQtyTotalOpen();
		final Quantity actualDeliveredQty = request.getActualDeliveredQty();
		final Quantity actualLoadQty = request.getActualLoadQty();

		deliveryPlanningRecord.setC_UOM_ID(request.getUom().getC_UOM_ID());

		deliveryPlanningRecord.setQtyOrdered(qtyOrdered.toBigDecimal());
		deliveryPlanningRecord.setQtyTotalOpen(qtyTotalOpen.toBigDecimal());
		deliveryPlanningRecord.setActualDeliveredQty( actualDeliveredQty.toBigDecimal());
		deliveryPlanningRecord.setActualLoadQty(actualLoadQty.toBigDecimal());

		deliveryPlanningRecord.setForwarder(request.getForwarder());
		deliveryPlanningRecord.setWayBillNo(request.getWayBillNo());
		deliveryPlanningRecord.setReleaseNo(request.getReleaseNo());
		deliveryPlanningRecord.setTransportDetails(request.getTransportDetails());

		deliveryPlanningRecord.setIsB2B(request.isB2B);

		deliveryPlanningRecord.setMeansOfTransportation(MeansOfTransportation.toCodeOrNull(request.getMeansOfTransportation()));
		deliveryPlanningRecord.setOrderStatus(OrderStatus.toCodeOrNull(request.getOrderStatus()));
		deliveryPlanningRecord.setM_Delivery_Planning_Type(DeliveryPlanningType.toCodeOrNull(request.getDeliveryPlanningType()));

		deliveryPlanningRecord.setBatch(request.getBatch());
		deliveryPlanningRecord.setC_OriginCountry_ID(CountryId.toRepoId(request.getOriginCountryId()));
		deliveryPlanningRecord.setC_DestinationCountry_ID(CountryId.toRepoId(request.getDestinationCountryId()));

		save(deliveryPlanningRecord);
	}

	public boolean isOtherDeliveryPlanningsExistForOrderLine(@NonNull final OrderLineId orderLineId, @NonNull final DeliveryPlanningId excludeDeliveryPlanningId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.addNotEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, excludeDeliveryPlanningId)
				.create()
				.anyMatch();
	}
}
