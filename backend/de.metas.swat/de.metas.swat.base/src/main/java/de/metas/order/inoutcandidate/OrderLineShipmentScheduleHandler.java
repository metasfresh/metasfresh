package de.metas.order.inoutcandidate;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.picking_bom.PickingOrderConfig;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.interfaces.I_C_OrderLine;
import org.eevolution.api.PPOrderId;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/**
 * Default implementation for sales order lines.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public class OrderLineShipmentScheduleHandler extends ShipmentScheduleHandler
{
	private final static IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IUOMDAO uomdao = Services.get(IUOMDAO.class);

	private final OrderLineShipmentScheduleHandlerExtension extensions;

	public OrderLineShipmentScheduleHandler(
			@NonNull final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL,
			@NonNull final Optional<List<OrderLineShipmentScheduleHandlerExtension>> extensions)
	{
		this.shipmentScheduleInvalidateBL = shipmentScheduleInvalidateBL;
		this.extensions = CompositeOrderLineShipmentScheduleHandlerExtension.of(extensions);
	}

	public static OrderLineShipmentScheduleHandler newInstanceWithoutExtensions()
	{
		return new OrderLineShipmentScheduleHandler(
				Services.get(IShipmentScheduleInvalidateBL.class),
				Optional.empty());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("extensions", extensions)
				.toString();
	}

	@Override
	public List<I_M_ShipmentSchedule> createCandidatesFor(@NonNull final Object model)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);

		final PPOrderId pickingOrderId = createPickingOrderIfNeeded(orderLine);

		final I_M_ShipmentSchedule shipmentSchedule = createShipmentScheduleForOrderLine(orderLine, pickingOrderId);
		return ImmutableList.of(shipmentSchedule);
	}

	private I_M_ShipmentSchedule createShipmentScheduleForOrderLine(
			@NonNull final I_C_OrderLine orderLine,
			@Nullable final PPOrderId pickingOrderId)
	{

		// sanity check ol.getM_Product_ID()
		if (orderLine.getQtyOrdered().signum() <= 0 || orderLine.getQtyReserved().signum() < 0)
		{
			throw new AdempiereException(orderLine + " has QtyOrdered<=0 or QtyReserved<0");
		}

		final I_M_ShipmentSchedule newSched = newInstance(I_M_ShipmentSchedule.class);

		Check.errorUnless(newSched.getAD_Client_ID() == orderLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule needs to have the same AD_Client_ID as " + orderLine + ", i.e." + newSched.getAD_Client_ID() + " == " + orderLine.getAD_Client_ID());

		updateShipmentScheduleFromOrderLine(newSched, orderLine);

		newSched.setPickFrom_Order_ID(PPOrderId.toRepoId(pickingOrderId));

		InterfaceWrapperHelper.save(newSched);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return newSched;
	}

	private WarehouseId getWarehouseId(final I_C_OrderLine orderLine)
	{
		return Services.get(IWarehouseAdvisor.class).evaluateWarehouse(orderLine);
	}

	@Override
	public void updateShipmentScheduleFromReferencedRecord(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_OrderLine orderLineRecord = TableRecordReference
				.ofReferenced(shipmentSchedule) // Record_Id and AD_Table_ID are mandatory
				.getModel(I_C_OrderLine.class);

		updateShipmentScheduleFromOrderLine(shipmentSchedule, orderLineRecord);
	}

	private void updateShipmentScheduleFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		shipmentSchedule.setC_Order_ID(orderLine.getC_Order_ID());
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		shipmentSchedule.setAD_Table_ID(getTableId(I_C_OrderLine.class));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());

		shipmentSchedule.setQtyReserved(BigDecimal.ZERO.max(orderLine.getQtyReserved())); // task 09358: making sure that negative qtyOrdered are not propagated to the shipment sched

		// 08255 : initialize the qty order calculated
		shipmentSchedule.setQtyOrdered_Calculated(orderLine.getQtyOrdered());
		shipmentSchedule.setDateOrdered(orderLine.getDateOrdered());

		shipmentSchedule.setProductDescription(orderLine.getProductDescription());

		shipmentSchedule.setC_BPartner_Location_ID(orderLine.getC_BPartner_Location_ID());
		shipmentSchedule.setC_BPartner_ID(orderLine.getC_BPartner_ID());

		Check.assume(orderLine.getM_Product_ID() > 0, "{} has M_Product_ID set", orderLine);
		shipmentSchedule.setM_Product_ID(orderLine.getM_Product_ID());
		final BPartnerContactId adUserId = BPartnerContactId.ofRepoIdOrNull(orderLine.getC_BPartner_ID(), orderLine.getAD_User_ID());
		shipmentSchedule.setAD_User_ID(BPartnerContactId.toRepoId(adUserId));

		shipmentSchedule.setAD_Org_ID(orderLine.getAD_Org_ID());

		shipmentSchedule.setShipmentAllocation_BestBefore_Policy(orderLine.getShipmentAllocation_BestBefore_Policy());

		shipmentSchedule.setM_Shipper_ID(orderLine.getM_Shipper_ID());

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());
		if (asiId.isRegular())
		{
			final IAttributeSetInstanceAware asiAware = shipmentScheduleBL.toAttributeSetInstanceAware(shipmentSchedule);
			final ImmutableAttributeSet attributeSet = attributeSetInstanceBL.getImmutableAttributeSetById(asiId);
			attributeSetInstanceBL.syncAttributesToASIAware(attributeSet, asiAware);
		}

		// 04290
		shipmentSchedule.setM_Warehouse_ID(getWarehouseId(orderLine).getRepoId());
		final I_C_Order orderRecord = orderDAO.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()), I_C_Order.class);

		final String bPartnerAddress = firstNotEmptyTrimmed(
				orderLine.getBPartnerAddress(),
				orderRecord.getDeliveryToAddress(),
				orderRecord.getBPartnerAddress());
		shipmentSchedule.setBPartnerAddress(bPartnerAddress);

		updateShipmentScheduleFromOrder(shipmentSchedule, orderRecord);

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final I_C_UOM priceUOM = uomdao.getById(orderLine.getPrice_UOM_ID());
		final BigDecimal qtyReservedInPriceUOM = uomConversionBL.convertFromProductUOM(productId, priceUOM, orderLine.getQtyReserved());
		shipmentSchedule.setLineNetAmt(qtyReservedInPriceUOM.multiply(orderLine.getPriceActual()));

		// only display item products
		final boolean display = productBL.getProductType(productId).isItem();
		shipmentSchedule.setIsDisplayed(display);

		final String groupingOrderLineLabel = DB
				.getSQLValueStringEx(
						ITrx.TRXNAME_ThreadInherited,
						" select ( o.DocumentNo || ' - ' || spt.Line ) " +
								" from C_OrderLine ol INNER JOIN C_OrderLine spt ON spt.C_OrderLine_ID=ol.SinglePriceTag_ID INNER JOIN C_Order o ON o.C_Order_ID=spt.C_Order_ID " +
								" where ol.C_OrderLine_ID=?",
						orderLine.getC_OrderLine_ID());
		// TODO column SinglePriceTag_ID has entity type de.metas.orderlineGrouping
		// either "adopt" it into the core or into de.metas.inoutcandidate or add a way to let external modules set
		// tthier values
		shipmentSchedule.setSinglePriceTag_ID(groupingOrderLineLabel);
		// 03152 end

		extensions.updateShipmentScheduleFromOrderLine(shipmentSchedule, orderLine);
	}

	private static void updateShipmentScheduleFromOrder(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_Order order)
	{
		shipmentSchedule.setPriorityRule(order.getPriorityRule());

		final BPartnerLocationId billToLocationId = orderBL.getBillToLocationId(order);
		shipmentSchedule.setBill_BPartner_ID(billToLocationId.getBpartnerId().getRepoId());
		shipmentSchedule.setBill_Location_ID(billToLocationId.getRepoId());
		if(orderBL.hasBillToContactId(order))
		{
			final BPartnerContactId billToContactId = orderBL.getBillToContactId(order);
			shipmentSchedule.setBill_User_ID(BPartnerContactId.toRepoId(billToContactId));
		}

		shipmentSchedule.setDeliveryRule(order.getDeliveryRule());
		shipmentSchedule.setDeliveryViaRule(order.getDeliveryViaRule());
		shipmentSchedule.setM_Tour_ID(order.getM_Tour_ID());

		final DocTypeId orderDocTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		final DocBaseAndSubType orderDocBaseTypeAndSubType = Services.get(IDocTypeDAO.class).getDocBaseAndSubTypeById(orderDocTypeId);
		shipmentSchedule.setC_DocType_ID(orderDocTypeId.getRepoId());
		shipmentSchedule.setDocSubType(orderDocBaseTypeAndSubType.getDocSubType());
	}

	/**
	 * For a given order line this method invalidates all shipment schedule lines that have the same product or (if the given order has a "complete order" delivery rule) even does the same with all
	 * order lines of the given order.
	 */
	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		invalidateForOrderLine(orderLine, order, trxName);
	}

	public void invalidateForOrderLine(final I_C_OrderLine orderLine, final I_C_Order order, final String trxName)
	{

		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(order.getDeliveryRule());
		if (DeliveryRule.COMPLETE_ORDER.equals(deliveryRule))
		{
			for (final I_C_OrderLine ol : orderDAO.retrieveOrderLines(order, I_C_OrderLine.class))
			{
				shipmentScheduleInvalidateBL.invalidateJustForOrderLine(ol);
				shipmentScheduleInvalidateBL.notifySegmentChangedForOrderLine(ol);
			}
		}
		else
		{
			shipmentScheduleInvalidateBL.invalidateJustForOrderLine(orderLine);
			shipmentScheduleInvalidateBL.notifySegmentChangedForOrderLine(orderLine);
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public Iterator<?> retrieveModelsWithMissingCandidates(
			final Properties ctx,
			final String trxName)
	{
		// task 08896: don't use the where clause with all those INs.
		// Its performance can turn catastrophic for large numbers or orderlines and orders.
		// Instead use an efficient view (i.e. C_OrderLine_ID_With_Missing_ShipmentSchedule) and (bad enough) use an in because we still need to select from *one* table.
		// Note: still, this polling sucks, but that's a bigger task
		final String wc = " C_OrderLine_ID IN ( select C_OrderLine_ID from C_OrderLine_ID_With_Missing_ShipmentSchedule_v ) ";
		final TypedSqlQueryFilter<I_C_OrderLine> orderLinesFilter = TypedSqlQueryFilter.of(wc);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.filter(orderLinesFilter)
				.addOnlyContextClient(ctx)
				.orderBy().addColumn(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_OrderLine.class);
	}

	/**
	 * Returns an instance which in turn just returns as
	 * <ul>
	 * <li><code>QtyOrdered</code>: the sched's order line's QtyOrdered value
	 * </ul>
	 */
	@Override
	public IDeliverRequest createDeliverRequest(@NonNull final I_M_ShipmentSchedule sched, @NonNull final org.compiere.model.I_C_OrderLine salesOrderLine)
	{
		return salesOrderLine::getQtyOrdered;
	}

	@Nullable
	private PPOrderId createPickingOrderIfNeeded(@NonNull final I_C_OrderLine salesOrderLine)
	{
		final PickingBOMService pickingBOMService = SpringContextHolder.instance.getBean(PickingBOMService.class);
		final IPPOrderBL ppOrdersService = Services.get(IPPOrderBL.class);
		final IProductBL productsService = Services.get(IProductBL.class);

		final OrgId orgId = OrgId.ofRepoId(salesOrderLine.getAD_Org_ID());
		final WarehouseId warehouseId = getWarehouseId(salesOrderLine);
		final ProductId productId = ProductId.ofRepoId(salesOrderLine.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(salesOrderLine.getM_AttributeSetInstance_ID());
		final PickingOrderConfig config = pickingBOMService.getPickingOrderConfig(orgId, warehouseId, productId, asiId).orElse(null);
		if (config == null)
		{
			return null;
		}

		final I_C_UOM stockUOM = productsService.getStockUOM(productId);
		final Quantity qtyOrdered = Quantity.of(salesOrderLine.getQtyOrdered(), stockUOM);

		final I_PP_Order ppOrder = ppOrdersService.createOrder(PPOrderCreateRequest.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(salesOrderLine.getAD_Client_ID(), salesOrderLine.getAD_Org_ID()))
				.productPlanningId(config.getProductPlanningId())
				// .materialDispoGroupId(null)
				//
				.plantId(config.getPlantId())
				.warehouseId(warehouseId)
				.plannerId(config.getPlannerId())
				//
				.bomId(config.getBomId())
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.qtyRequired(qtyOrdered)
				//
				.dateOrdered(SystemTime.asInstant())
				.datePromised(TimeUtil.asInstant(salesOrderLine.getDatePromised()))
				.dateStartSchedule(SystemTime.asInstant())
				//
				.salesOrderLineId(OrderLineId.ofRepoId(salesOrderLine.getC_OrderLine_ID()))
				.customerId(BPartnerId.ofRepoId(salesOrderLine.getC_BPartner_ID()))
				//
				.pickingOrder(true)
				//
				.completeDocument(true)
				//
				.build());

		return PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
	}
}