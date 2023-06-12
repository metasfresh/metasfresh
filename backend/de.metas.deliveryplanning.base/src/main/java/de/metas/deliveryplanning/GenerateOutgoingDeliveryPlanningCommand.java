package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;

public class GenerateOutgoingDeliveryPlanningCommand
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final DeliveryPlanningRepository deliveryPlanningRepository;

	@NonNull private final I_M_ShipmentSchedule shipmentSchedule;
	@NonNull private final DeliveryStatusColorPalette colorPalette;
	@NonNull private final DimensionService dimensionService;

	@Builder
	private GenerateOutgoingDeliveryPlanningCommand(
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final DeliveryStatusColorPalette colorPalette,
			@NonNull final DimensionService dimensionService)
	{
		this.deliveryPlanningRepository = deliveryPlanningRepository;

		this.shipmentSchedule = shipmentSchedule;
		this.colorPalette = colorPalette;
		this.dimensionService = dimensionService;
	}

	public void execute()
	{
		final DeliveryPlanningCreateRequest deliveryPlanningRequest = createDeliveryPlanningRequest();
		deliveryPlanningRepository.generateDeliveryPlanning(deliveryPlanningRequest);
	}

	private DeliveryPlanningCreateRequest createDeliveryPlanningRequest()
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID());

		final I_C_UOM uomOfProduct = shipmentScheduleBL.getUomOfProduct(shipmentSchedule);

		final Quantity qtyOrdered = Quantity.of(shipmentSchedule.getQtyOrdered(), uomOfProduct);
		final OrgId orgId = OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID());

		final String originCountryCode = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.CountryOfOrigin, asiId);
		final CountryId countryId = originCountryCode == null ? null : countryDAO.getCountryIdByCountryCode(originCountryCode);
		final String huBatchNo = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, asiId);

		final Timestamp deliveryDate_effective = CoalesceUtil.coalesce(shipmentSchedule.getDeliveryDate_Override(), shipmentSchedule.getDeliveryDate());

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(shipmentSchedule.getC_BPartner_ID(), shipmentSchedule.getC_BPartner_Location_ID());

		final DeliveryPlanningCreateRequest.DeliveryPlanningCreateRequestBuilder requestBuilder = DeliveryPlanningCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(shipmentSchedule.getAD_Client_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID()))
				.deliveryStatusColorId(colorPalette.getNotDeliveredColorId())
				.deliveryPlanningType(DeliveryPlanningType.Outgoing)
				.orderId(orderId)
				.orderLineId(orderLineId)
				.warehouseId(WarehouseId.ofRepoId(shipmentSchedule.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.partnerId(BPartnerId.ofRepoId(shipmentSchedule.getC_BPartner_ID()))
				.bPartnerLocationId(
						bPartnerLocationId)
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(shipmentSchedule.getM_SectionCode_ID()))
				.qtyOrdered(qtyOrdered)
				.qtyTotalOpen(qtyOrdered.subtract(shipmentScheduleBL.getQtyDelivered(shipmentSchedule)))
				.actualLoadedQty(Quantity.zero(uomOfProduct))
				.plannedLoadedQty(Quantity.zero(uomOfProduct))
				.plannedDischargeQty(Quantity.zero(uomOfProduct))
				.actualDischargeQty(Quantity.zero(uomOfProduct))
				.uom(uomOfProduct)
				.plannedDeliveryDate(TimeUtil.asInstant(deliveryDate_effective))
				.batch(huBatchNo)
				.originCountryId(countryId);

		if (orderId != null)
		{
			final I_C_Order order = orderDAO.getById(orderId);

			requestBuilder.isB2B(order.isDropShip())
					.incotermsId(IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID()))
					.incotermLocation(order.getIncotermLocation());

			final BPartnerLocationAndCaptureId bpartnerLocationId = OrderDocumentLocationAdapterFactory.locationAdapter(order).getBPartnerLocationAndCaptureId();
			final CountryId destinationCountryId = bPartnerBL.getCountryId(bpartnerLocationId);

			requestBuilder.destinationCountryId(destinationCountryId);
		}

		if (orderLineId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

			requestBuilder.actualDeliveryDate(TimeUtil.asInstant(orderLine.getDateDelivered()));

			if (deliveryDate_effective == null)
			{
				requestBuilder.plannedDeliveryDate(TimeUtil.asInstant(orderLine.getDatePromised()));
			}

			requestBuilder.shipperId(ShipperId.ofRepoIdOrNull(orderLine.getM_Shipper_ID()));

			final Dimension orderLineDimension = dimensionService.getFromRecord(orderLine);
			requestBuilder.dimension(orderLineDimension);

		}

		return requestBuilder.build();
	}

}
