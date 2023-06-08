package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.incoterms.IncotermsId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;

public class GenerateIncomingDeliveryPlanningCommand
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IReceiptScheduleQtysBL receiptScheduleQtysBL = Services.get(IReceiptScheduleQtysBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final DeliveryPlanningRepository deliveryPlanningRepository;

	@NonNull private final I_M_ReceiptSchedule receiptSchedule;
	@NonNull private final DeliveryStatusColorPalette colorPalette;
	@NonNull private final DimensionService dimensionService;

	@Builder
	private GenerateIncomingDeliveryPlanningCommand(
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final DeliveryStatusColorPalette colorPalette,
			@NonNull final DimensionService dimensionService)
	{
		this.deliveryPlanningRepository = deliveryPlanningRepository;

		this.receiptSchedule = receiptSchedule;
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
		final OrderId orderId = OrderId.ofRepoIdOrNull(receiptSchedule.getC_Order_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(receiptSchedule.getC_OrderLine_ID());

		final I_C_UOM uom = uomDAO.getById(receiptSchedule.getC_UOM_ID());

		final Quantity qtyOrdered = Quantity.of(receiptScheduleQtysBL.getQtyOrdered(receiptSchedule), uom);

		final Quantity qtyMoved = Quantity.of(receiptScheduleQtysBL.getQtyMoved(receiptSchedule), uom);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(receiptSchedule.getM_AttributeSetInstance_ID());

		final CountryId originCountryId = getOriginCountryId(asiId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(receiptSchedule.getM_Warehouse_ID());
		final BPartnerLocationId destinationBPLocationId = warehouseBL.getBPartnerLocationId(warehouseId);
		final CountryId destinationCountryId = warehouseBL.getCountryId(warehouseId);

		final String huBatchNo = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, asiId);

		final DeliveryPlanningCreateRequest.DeliveryPlanningCreateRequestBuilder requestBuilder = DeliveryPlanningCreateRequest.builder()
				.orgId(OrgId.ofRepoId(receiptSchedule.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(receiptSchedule.getAD_Client_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoId(receiptSchedule.getM_ReceiptSchedule_ID()))
				.deliveryStatusColorId(colorPalette.getNotDeliveredColorId())
				.deliveryPlanningType(DeliveryPlanningType.Incoming)
				.orderId(orderId)
				.orderLineId(orderLineId)
				.warehouseId(warehouseId)
				.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
				.partnerId(BPartnerId.ofRepoId(receiptSchedule.getC_BPartner_ID()))
				.bPartnerLocationId(destinationBPLocationId)
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(receiptSchedule.getM_SectionCode_ID()))
				.qtyOrdered(qtyOrdered)
				.qtyTotalOpen(qtyOrdered.subtract(qtyMoved))
				.actualLoadedQty(Quantity.zero(uom))
				.plannedLoadedQty(Quantity.zero(uom))
				.plannedDischargeQty(Quantity.zero(uom))
				.actualDischargeQty(Quantity.zero(uom))
				.uom(uom)
				.plannedDeliveryDate(TimeUtil.asInstant(receiptSchedule.getMovementDate()))
				.batch(huBatchNo)
				.originCountryId(originCountryId)
				.destinationCountryId(destinationCountryId);

		if (orderId != null)
		{
			final I_C_Order order = orderDAO.getById(orderId);

			requestBuilder.isB2B(order.isDropShip())
					.incotermsId(IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID()))
					.incotermLocation(order.getIncotermLocation());
		}

		if (orderLineId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);
			final Dimension orderLineDimension = dimensionService.getFromRecord(orderLine);

			requestBuilder.actualDeliveryDate(TimeUtil.asInstant(orderLine.getDateDelivered()));
			requestBuilder.shipperId(ShipperId.ofRepoIdOrNull(orderLine.getM_Shipper_ID()));
			requestBuilder.dimension(orderLineDimension);
		}

		return requestBuilder.build();
	}

	@Nullable
	private CountryId getOriginCountryId(final AttributeSetInstanceId asiId)
	{
		final String originCountryCode = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.CountryOfOrigin, asiId);
		return originCountryCode == null ? null : countryDAO.getCountryIdByCountryCode(originCountryCode);
	}
}
