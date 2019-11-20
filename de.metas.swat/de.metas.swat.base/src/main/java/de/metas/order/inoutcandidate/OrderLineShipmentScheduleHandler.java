package de.metas.order.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.PickingOrderConfig;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.DeliveryRule;
import de.metas.order.IOrderDAO;
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
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/**
 * Default implementation for sales order lines.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderLineShipmentScheduleHandler extends ShipmentScheduleHandler
{
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
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		// sanity check ol.getM_Product_ID()
		if (orderLine.getQtyOrdered().signum() <= 0 || orderLine.getQtyReserved().signum() < 0)
		{
			throw new AdempiereException(orderLine + " has QtyOrdered<=0 or QtyReserved<0");
		}

		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		final I_M_ShipmentSchedule newSched = InterfaceWrapperHelper.create(ctx, I_M_ShipmentSchedule.class, trxName);

		updateShipmentScheduleFromOrderLine(newSched, orderLine);

		updateShipmentScheduleFromOrder(newSched, order);

		Services.get(IAttributeSetInstanceBL.class).cloneASI(newSched, orderLine);

		Check.errorUnless(newSched.getAD_Client_ID() == orderLine.getAD_Client_ID(),
				"The new M_ShipmentSchedule needs to have the same AD_Client_ID as " + orderLine + ", i.e." + newSched.getAD_Client_ID() + " == " + orderLine.getAD_Client_ID());

		// 04290
		newSched.setM_Warehouse_ID(getWarehouseId(orderLine).getRepoId());

		final String bPartnerAddress;
		if (!Check.isEmpty(orderLine.getBPartnerAddress()))
		{
			bPartnerAddress = orderLine.getBPartnerAddress();
		}
		else if (!Check.isEmpty(order.getDeliveryToAddress()))
		{
			bPartnerAddress = order.getDeliveryToAddress();
		}
		else
		{
			bPartnerAddress = order.getBPartnerAddress();
		}
		newSched.setBPartnerAddress(bPartnerAddress);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final I_C_UOM priceUOM = Services.get(IUOMDAO.class).getById(orderLine.getPrice_UOM_ID());
		final BigDecimal qtyReservedInPriceUOM = uomConversionBL.convertFromProductUOM(productId, priceUOM, orderLine.getQtyReserved());
		newSched.setLineNetAmt(qtyReservedInPriceUOM.multiply(orderLine.getPriceActual()));

		final String groupingOrderLineLabel = DB
				.getSQLValueStringEx(
						trxName,
						" select ( o.DocumentNo || ' - ' || spt.Line ) " +
								" from C_OrderLine ol INNER JOIN C_OrderLine spt ON spt.C_OrderLine_ID=ol.SinglePriceTag_ID INNER JOIN C_Order o ON o.C_Order_ID=spt.C_Order_ID " +
								" where ol.C_OrderLine_ID=?",
						orderLine.getC_OrderLine_ID());
		// TODO column SinglePriceTag_ID has entity type de.metas.orderlineGrouping
		// either "adopt" it into the core or into de.metas.inoutcandidate or add a way to let external modules set
		// tthier values
		newSched.setSinglePriceTag_ID(groupingOrderLineLabel);
		// 03152 end

		// only display item products
		final boolean display = Services.get(IProductBL.class).isItem(productId);
		newSched.setIsDisplayed(display);

		newSched.setPickFrom_Order_ID(PPOrderId.toRepoId(pickingOrderId));

		InterfaceWrapperHelper.save(newSched);

		// Note: AllowConsolidateInOut and PostageFreeAmt is set on the first update of this schedule
		return newSched;
	}

	private WarehouseId getWarehouseId(final I_C_OrderLine orderLine)
	{
		return Services.get(IWarehouseAdvisor.class).evaluateWarehouse(orderLine);
	}

	private static void updateShipmentScheduleFromOrderLine(
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

		shipmentSchedule.setAD_User_ID(orderLine.getAD_User_ID());

		shipmentSchedule.setAD_Org_ID(orderLine.getAD_Org_ID());

		shipmentSchedule.setShipmentAllocation_BestBefore_Policy(orderLine.getShipmentAllocation_BestBefore_Policy());

		shipmentSchedule.setM_Shipper_ID(orderLine.getM_Shipper_ID());
	}

	private static void updateShipmentScheduleFromOrder(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_Order order)
	{
		shipmentSchedule.setPriorityRule(order.getPriorityRule());
		shipmentSchedule.setBill_BPartner_ID(order.getBill_BPartner_ID());
		shipmentSchedule.setDeliveryRule(order.getDeliveryRule());
		shipmentSchedule.setDeliveryViaRule(order.getDeliveryViaRule());

		final DocTypeId orderDocTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		final DocBaseAndSubType orderDocBaseTypeAndSubType = Services.get(IDocTypeDAO.class).getDocBaseAndSubTypeById(orderDocTypeId);
		shipmentSchedule.setC_DocType_ID(orderDocTypeId.getRepoId());
		shipmentSchedule.setDocSubType(orderDocBaseTypeAndSubType.getDocSubType());
	}

	/**
	 * For a given order line this method invalidates all shipment schedule lines that have the same product or (if the given order has a "complete order" delivery rule) even does the same with all
	 * order lines of the given order.
	 *
	 * @param orderLine
	 * @param order
	 * @param trxName
	 */
	@Override
	public void invalidateCandidatesFor(Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		invalidateForOrderLine(orderLine, order, trxName);
	}

	public void invalidateForOrderLine(final I_C_OrderLine orderLine, final I_C_Order order, final String trxName)
	{
		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);

		final DeliveryRule deliveryRule = DeliveryRule.ofNullableCode(order.getDeliveryRule());
		if (DeliveryRule.COMPLETE_ORDER.equals(deliveryRule))
		{
			for (final I_C_OrderLine ol : Services.get(IOrderDAO.class).retrieveOrderLines(order, I_C_OrderLine.class))
			{
				shipmentScheduleInvalidateBL.invalidateJustForOrderLine(ol);
				shipmentScheduleInvalidateBL.invalidateSegmentForOrderLine(ol);
			}
		}
		else
		{
			shipmentScheduleInvalidateBL.invalidateJustForOrderLine(orderLine);
			shipmentScheduleInvalidateBL.invalidateSegmentForOrderLine(orderLine);
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public Iterator<? extends Object> retrieveModelsWithMissingCandidates(
			final Properties ctx,
			final String trxName)
	{
		// task 08896: don't use the where clause with all those INs.
		// Its performance can turn catastrophic for large numbers or orderlines and orders.
		// Instead use an efficient view (i.e. C_OrderLine_ID_With_Missing_ShipmentSchedule) and (bad enough) use an in because we still need to select from *one* table.
		// Note: still, this polling sucks, but that's a bigger task
		final String wc = " C_OrderLine_ID IN ( select C_OrderLine_ID from C_OrderLine_ID_With_Missing_ShipmentSchedule_v ) ";
		final TypedSqlQueryFilter<I_C_OrderLine> orderLinesFilter = TypedSqlQueryFilter.of(wc);

		final Iterator<I_C_OrderLine> orderLines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.filter(orderLinesFilter)
				.addOnlyContextClient(ctx)
				.orderBy().addColumn(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).endOrderBy()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_OrderLine.class);

		return orderLines;
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

	private PPOrderId createPickingOrderIfNeeded(final I_C_OrderLine salesOrderLine)
	{
		final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
		final IPPOrderBL ppOrdersService = Services.get(IPPOrderBL.class);
		final IProductBL productsService = Services.get(IProductBL.class);

		final OrgId orgId = OrgId.ofRepoId(salesOrderLine.getAD_Org_ID());
		final WarehouseId warehouseId = getWarehouseId(salesOrderLine);
		final ProductId productId = ProductId.ofRepoId(salesOrderLine.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(salesOrderLine.getM_AttributeSetInstance_ID());
		final PickingOrderConfig config = productPlanningsRepo.getPickingOrderConfig(orgId, warehouseId, productId, asiId).orElse(null);
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
