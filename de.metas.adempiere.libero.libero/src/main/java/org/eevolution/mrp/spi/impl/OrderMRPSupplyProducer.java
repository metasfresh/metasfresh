package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IMutable;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.IProductPlanningBL;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.exceptions.NoPlantForWarehouseException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.RoutingService;
import org.eevolution.model.RoutingServiceFactory;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Order;
import org.eevolution.model.X_PP_Order_BOM;
import org.eevolution.model.X_PP_Product_BOM;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;
import org.slf4j.Logger;

import de.metas.adempiere.service.IOrderDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;

public class OrderMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	private static final String MSG_OrderMRPNoBalanceDemand = "Order supply producer is never used to balance demand";

	private final transient Logger logger = LogManager.getLogger(getClass());

	public OrderMRPSupplyProducer()
	{
		super();
		addSourceColumnNames(I_C_Order.Table_Name, new String[] {
				I_C_Order.COLUMNNAME_DatePromised,
				I_C_Order.COLUMNNAME_DocStatus,
				I_C_Order.COLUMNNAME_PreparationDate,

		});
		addSourceColumnNames(I_C_OrderLine.Table_Name, new String[] {
				I_C_OrderLine.COLUMNNAME_AD_Org_ID,
				I_C_OrderLine.COLUMNNAME_DateOrdered,
				I_C_OrderLine.COLUMNNAME_DatePromised,
				I_C_OrderLine.COLUMNNAME_C_BPartner_ID,
				I_C_OrderLine.COLUMNNAME_M_Warehouse_ID,
				I_C_OrderLine.COLUMNNAME_M_Product_ID,
				I_C_OrderLine.COLUMNNAME_C_UOM_ID,
				I_C_OrderLine.COLUMNNAME_QtyOrdered,
				I_C_OrderLine.COLUMNNAME_QtyDelivered,
		});

		addSourceTableName(I_M_InOut.Table_Name);
	}

	@Override
	public Class<?> getDocumentClass()
	{
		return I_C_Order.class;
	}

	@Override
	public boolean applies(final IMRPContext mrpContext, IMutable<String> notAppliesReason)
	{
		// always false; it's never used to balance demand
		notAppliesReason.setValue(MSG_OrderMRPNoBalanceDemand);
		return false;
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		log.debug("onDocumentChange: {}, Timing: {}", tableName, timing);

		if (model instanceof I_M_InOut && timing == DocTimingType.BEFORE_COMPLETE)
		{
			final I_M_InOut inout = (I_M_InOut)model;
			if (inout.isSOTrx())
			{
				final List<I_PP_Order> orders = Services.get(IPPOrderDAO.class).retrieveMakeToOrderForInOut(inout);
				for (final I_PP_Order order : orders)
				{
					final String description = order.getDescription() != null ? order.getDescription() : ""
							+ Services.get(IMsgBL.class).translate(ctx, I_M_InOut.COLUMNNAME_M_InOut_ID)
							+ " : "
							+ Services.get(IMsgBL.class).translate(ctx, I_M_InOut.COLUMNNAME_DocumentNo);

					order.setDescription(description);
					Services.get(IDocActionBL.class).processIt(order, DocAction.ACTION_Close);
					order.setDocStatus(X_PP_Order.DOCACTION_Close);
					order.setDocAction(X_PP_Order.DOCACTION_None);
					InterfaceWrapperHelper.save(order);
				}
			}
		}
		// FIXME: drop this old code
		// else if (model instanceof I_M_InOut && timing == ModelValidator.TIMING_AFTER_COMPLETE)
		// {
		// final I_M_InOut inout = (I_M_InOut)model;
		// for (final I_M_InOutLine line : Services.get(IInOutDAO.class).retrieveLines(inout))
		// {
		// final String whereClause = "C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
		// List<I_C_OrderLine> olines = new Query(ctx, I_C_OrderLine.Table_Name, whereClause, trxName)
		// .setParameters(new Object[] { line.getC_OrderLine_ID() })
		// .list(I_C_OrderLine.class);
		// for (I_C_OrderLine oline : olines)
		// {
		// if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) >= 0)
		// {
		// // MPPCostCollector cc = new MPPCostCollector(po.getCtx(), oline.getPP_Cost_Collector_ID(), po.get_TrxName());
		// // String docStatus = cc.completeIt();
		// // cc.setDocStatus(docStatus);
		// // cc.setDocAction(MPPCostCollector.DOCACTION_Close);
		// // cc.saveEx();
		// return null;
		// }
		// }
		// }
		// }
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		// shall never been called
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void cleanup(final IMRPContext mrpContext, final IMRPExecutor executor)
	{
		// nothing
	}

	@Override
	protected void onRecordChange(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();

		if (InterfaceWrapperHelper.isInstanceOf(model, I_C_Order.class))
		{
			final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
			// Create/Update a planning supply when isPurchase Order
			// or when you change DatePromised or DocStatus and is Purchase Order
			if (event.isChange() && !order.isSOTrx())
			{
				C_Order(order);
			}
			// Update MRP when you change the status order to complete or in process for a sales order
			// or you change DatePromised
			else if (event.getChangeType() == ModelChangeType.AFTER_CHANGE && order.isSOTrx())
			{
				if (event.isReleased() || isChanged(order))
				{
					C_Order(order);
				}
			}
		}
		//
		else if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_C_OrderLine.class))
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
			final I_C_Order order = ol.getC_Order();
			// Create/Update a planning supply when isPurchase Order or you change relevant fields
			if (!order.isSOTrx())
			{
				C_OrderLine(ol);
			}
			// Update MRP when Sales Order have document status in process or complete and
			// you change relevant fields
			else if (order.isSOTrx() && event.isReleased())
			{
				C_OrderLine(ol);
			}
		}
	}

	/**
	 * Create MRP record based in Order
	 * 
	 * @param MOrder
	 */
	private void C_Order(final I_C_Order order)
	{
		final I_C_DocType dt = order.getC_DocTypeTarget();
		final String DocSubTypeSO = dt.getDocSubType();
		if (X_C_DocType.DOCSUBTYPE_StandardOrder.equals(DocSubTypeSO) || !order.isSOTrx())
		{
			final String docStatus = order.getDocStatus();
			if (X_C_Order.DOCSTATUS_InProgress.equals(docStatus)
					|| X_C_Order.DOCSTATUS_Completed.equals(docStatus)
					|| !order.isSOTrx())
			{
				for (final I_C_OrderLine line : Services.get(IOrderDAO.class).retrieveOrderLines(order))
				{
					C_OrderLine(line);
				}
			}

			//
			// Trigger only if follwing order fields were changed: DocStatus, C_BPartner_ID
			final I_C_Order orderOld = InterfaceWrapperHelper.createOld(order, I_C_Order.class);
			if (!Check.equals(orderOld.getDocStatus(), docStatus)
					|| orderOld.getC_BPartner_ID() != order.getC_BPartner_ID())
			{
				final List<I_PP_MRP> list = mrpDAO.retrieveMRPRecords(order);
				for (final I_PP_MRP mrp : list)
				{
					setC_Order(mrp, order);
					InterfaceWrapperHelper.save(mrp);
				}
			}
		}
	}

	/**
	 * Create MRP record based in Order Line
	 * 
	 * @param MOrderLine
	 */
	private void C_OrderLine(final I_C_OrderLine orderLine)
	{
		final I_M_Warehouse warehouse = getM_Warehouse(orderLine);
		final I_C_Order order = orderLine.getC_Order();

		I_PP_MRP mrp = mrpDAO.retrieveMRPRecord(orderLine);
		if (mrp == null)
		{
			mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, orderLine);
			mrp.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		}

		mrp.setAD_Org_ID(orderLine.getAD_Org_ID());
		setC_Order(mrp, order);
		mrp.setDescription(orderLine.getDescription());
		mrp.setName("MRP");
		mrp.setDatePromised(orderLine.getDatePromised());
		mrp.setDateStartSchedule(orderLine.getDatePromised());
		mrp.setDateFinishSchedule(orderLine.getDatePromised());
		mrp.setDateOrdered(orderLine.getDateOrdered());
		mrp.setM_Warehouse(warehouse);

		final I_M_Product product = orderLine.getM_Product();
		mrp.setM_Product(product);

		Services.get(IAttributeSetInstanceBL.class).cloneASI(mrp, orderLine);
		// mrp.setM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID());

		I_S_Resource orderLinePlant = null;
		try
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
			orderLinePlant = Services.get(IProductPlanningDAO.class).findPlant(ctx, orderLine.getAD_Org_ID(), warehouse, product.getM_Product_ID());
		}
		catch (final NoPlantForWarehouseException e)
		{
			// just ignore it
			// NOTE: we are logging as FINE because it's a common case if u are not doing manufacturing to get this error
			logger.debug("No plant was found. Returning null.", e);
		}

		BigDecimal qtyOrdered = orderLine.getQtyOrdered();
		BigDecimal qtyToDeliver = qtyOrdered.subtract(orderLine.getQtyDelivered());
		// In case of an over-delivery, don't create negative supply
		if (qtyToDeliver.signum() < 0)
		{
			qtyToDeliver = BigDecimal.ZERO;
		}
		mrpBL.setQty(mrp, qtyOrdered, qtyToDeliver, orderLine.getC_UOM());

		if (order.isSOTrx())
		{
			mrp.setC_OrderLineSO_ID(orderLine.getC_OrderLine_ID());
			mrp.setS_Resource(orderLinePlant);
		}

		InterfaceWrapperHelper.save(mrp);

		//
		// Generate Make-To-Order Manufacturing order if necessary
		final I_C_DocType dt = order.getC_DocTypeTarget();
		final String DocSubTypeSO = dt.getDocSubType();
		if (X_C_DocType.DOCSUBTYPE_StandardOrder.equals(DocSubTypeSO))
		{
			createMakeToOrderMO(orderLine, qtyOrdered, orderLinePlant);
		}
		return;
	}

	/**
	 * Gets warehouse ID from given order line.
	 * 
	 * If there is no warehouse in order line, ask current warehouse advisor.
	 * 
	 * @param ol
	 * @return warehouse ID
	 */
	private I_M_Warehouse getM_Warehouse(final I_C_OrderLine ol)
	{
		int warehouseId = ol.getM_Warehouse_ID();
		if (warehouseId > 0)
		{
			return ol.getM_Warehouse();
		}
		final I_M_Warehouse suggestedWarehouse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(ol);
		Check.assumeNotNull(suggestedWarehouse, LiberoException.class, "Warehouse shall be found for {}", ol);
		return suggestedWarehouse;
	}

	/**
	 * Create/Make to Order MO
	 *
	 * @param ol
	 * @param qty
	 * @param orderLinePlant the order line's detected plant
	 * @return manufacturing order
	 */
	private final I_PP_Order createMakeToOrderMO(final I_C_OrderLine ol, final BigDecimal qty, final I_S_Resource orderLinePlant)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);

		I_PP_Order order = Services.get(IPPOrderDAO.class).retrieveMakeToOrderForOrderLine(ol);
		if (order == null)
		{
			I_PP_Product_BOM bom = Services.get(IProductBOMDAO.class).retrieveMakeToOrderProductBOM(ctx, ol.getM_Product_ID(), trxName);

			I_PP_Product_Planning productPlanning = null;
			// Validate the BOM based in planning data
			if (bom == null)
			{
				productPlanning = Services.get(IProductPlanningDAO.class)
						.find(ctx,
								ol.getAD_Org_ID(),
								0,   // warehouse
								0,   // plant
								ol.getM_Product_ID(),   // product
								ITrx.TRXNAME_None);
				if (productPlanning != null)
				{
					bom = productPlanning.getPP_Product_BOM();
					if (bom != null
							&& !X_PP_Product_BOM.BOMTYPE_Make_To_Order.equals(bom.getBOMType())
							&& !X_PP_Product_BOM.BOMTYPE_Make_To_Kit.equals(bom.getBOMType()))
					{
						bom = null;
					}
				}
			}
			if (bom != null)
			{
				final I_M_Product product = ol.getM_Product();
				final I_M_Warehouse warehouse = getM_Warehouse(ol);

				//
				// Find the plant
				I_S_Resource plant = productPlanning == null ? null : productPlanning.getS_Resource();
				if (plant == null || plant.getS_Resource_ID() <= 0)
				{
					plant = orderLinePlant; // fallback if there is no plant on the product planning
				}

				//
				// Find workflow
				I_AD_Workflow workflow = Services.get(IPPWorkflowDAO.class).retrieveWorkflowForProduct(product);
				// Validate the workflow based in planning data
				if (workflow == null && productPlanning != null)
				{
					workflow = productPlanning.getAD_Workflow();
				}

				if (plant != null && workflow != null)
				{
					String description = Services.get(IMsgBL.class).translate(ctx, Services.get(IADReferenceDAO.class).retriveListName(ctx, X_PP_Order_BOM.BOMTYPE_AD_Reference_ID, bom.getBOMType()))
							+ " "
							+ Services.get(IMsgBL.class).translate(ctx, I_C_Order.COLUMNNAME_C_Order_ID)
							+ " : "
							+ ol.getC_Order().getDocumentNo();
					// Create temporary data planning to create Manufacturing Order
					productPlanning = Services.get(IProductPlanningBL.class).createPlainProductPlanning(ctx);
					productPlanning.setAD_Org_ID(ol.getAD_Org_ID());
					productPlanning.setM_Product(product);
					productPlanning.setPlanner_ID(ol.getC_Order().getSalesRep_ID());
					productPlanning.setPP_Product_BOM(bom);
					productPlanning.setAD_Workflow(workflow);
					productPlanning.setM_Warehouse(warehouse);
					productPlanning.setS_Resource(plant);

					int asiIdToUse = ol.getM_AttributeSetInstance_ID();
					if (asiIdToUse > 0)
					{
						final I_M_AttributeSetInstance newASI = Services.get(IAttributeDAO.class).copy(ol.getM_AttributeSetInstance());
						asiIdToUse = newASI.getM_AttributeSetInstance_ID();
					}
					order = createMakeToOrderMO(productPlanning,
							ol.getC_OrderLine_ID(),
							asiIdToUse,
							qty,
							ol.getDateOrdered(),
							ol.getDatePromised(),
							description);

					description = "";
					if (ol.getDescription() != null)
					{
						description = ol.getDescription();
					}

					description = description
							+ " " + Services.get(IADReferenceDAO.class).retriveListName(ctx, X_PP_Order_BOM.BOMTYPE_AD_Reference_ID, bom.getBOMType())
							+ " "
							+ Services.get(IMsgBL.class).translate(ctx, I_PP_Order.COLUMNNAME_PP_Order_ID)
							+ " : "
							+ order.getDocumentNo();

					ol.setDescription(description);
					InterfaceWrapperHelper.save(ol);
				}
			}
		}
		else
		{
			if (!order.isProcessed())
			{
				// if you chance product in order line the Manufacturing order is void
				if (order.getM_Product_ID() != ol.getM_Product_ID())
				{
					order.setDescription("");
					Services.get(IPPOrderBL.class).setQtyEntered(order, Env.ZERO);
					order.setC_OrderLine(null);
					order.setC_OrderLine_MTO(null);

					Services.get(IDocActionBL.class).processIt(order, DocAction.ACTION_Void);
					// order.voidIt();

					order.setDocStatus(X_PP_Order.DOCSTATUS_Voided);
					order.setDocAction(X_PP_Order.DOCACTION_None);
					InterfaceWrapperHelper.save(order);
					ol.setDescription("");
					InterfaceWrapperHelper.save(ol);
				}
				if (order.getQtyEntered().compareTo(ol.getQtyEntered()) != 0)
				{
					Services.get(IPPOrderBL.class).setQty(order, ol.getQtyEntered());
					InterfaceWrapperHelper.save(order);
				}
				if (order.getDatePromised().compareTo(ol.getDatePromised()) != 0)
				{
					order.setDatePromised(ol.getDatePromised());
					InterfaceWrapperHelper.save(order);
				}
			}
		}
		return order;
	}

	/**
	 * Create Make-to-Order Manufacturing Order base on Planning Data
	 * 
	 * @param pp Product Planning
	 * @param C_OrderLine_ID Sales Order Line
	 * @param M_AttributeSetInstance_ID ASI
	 * @param qty Quantity
	 * @param dateOrdered Data Ordered
	 * @param datePromised Data Promised
	 * @param description Order Description
	 * @return Manufacturing Order or null
	 */
	private I_PP_Order createMakeToOrderMO(final I_PP_Product_Planning pp,
			final int C_OrderLine_ID,
			final int M_AttributeSetInstance_ID,
			final BigDecimal qty,
			final Timestamp dateOrdered,
			final Timestamp datePromised,
			final String description)
	{

		final I_PP_Product_BOM bom = pp.getPP_Product_BOM();
		if (bom == null || bom.getPP_Product_BOM_ID() <= 0)
		{
			// No BOM, nothing to do
			return null;
		}

		final I_AD_Workflow wf = pp.getAD_Workflow();
		if (wf == null || wf.getAD_Workflow_ID() <= 0)
		{
			// No routing, nothing to do
			return null;
		}

		final int plantId = pp.getS_Resource_ID();
		if (plantId <= 0)
		{
			// No Plant, nothing to do
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(pp);

		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
		final int duration = routingService.calculateDuration(wf, pp.getS_Resource(), qty).intValueExact();
		//
		final I_PP_Order order = InterfaceWrapperHelper.newInstance(I_PP_Order.class, pp);
		order.setAD_Org_ID(pp.getAD_Org_ID());
		order.setDescription(description);
		order.setC_OrderLine_ID(C_OrderLine_ID);
		order.setC_OrderLine_MTO_ID(C_OrderLine_ID);
		order.setS_Resource_ID(plantId);
		order.setM_Warehouse_ID(pp.getM_Warehouse_ID());
		order.setM_Product_ID(pp.getM_Product_ID());
		order.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		order.setPP_Product_BOM_ID(pp.getPP_Product_BOM_ID());
		order.setAD_Workflow_ID(pp.getAD_Workflow_ID());
		order.setPlanner_ID(pp.getPlanner_ID());
		order.setLine(10);
		order.setDateOrdered(dateOrdered);
		order.setDatePromised(datePromised);
		order.setDateStartSchedule(TimeUtil.addDays(datePromised, 0 - duration));
		order.setDateFinishSchedule(datePromised);
		order.setC_UOM_ID(pp.getM_Product().getC_UOM_ID());
		Services.get(IPPOrderBL.class).setQty(order, qty);
		order.setPriorityRule(X_PP_Order.PRIORITYRULE_High);
		InterfaceWrapperHelper.save(order);

		Services.get(IDocActionBL.class).processIt(order, DocAction.ACTION_Prepare);
		// order.setDocStatus(order.prepareIt());

		order.setDocAction(X_PP_Order.DOCACTION_Complete); // TODO: legacy code, but why we do this???
		InterfaceWrapperHelper.save(order);
		return order;
	}

	private final void setC_Order(final I_PP_MRP mrp, final I_C_Order order)
	{
		mrpBL.updateMRPFromContext(mrp);

		mrp.setC_Order_ID(order.getC_Order_ID());
		mrp.setC_BPartner_ID(order.getC_BPartner_ID());
		mrp.setDocStatus(order.getDocStatus());
		if (order.isSOTrx())
		{
			mrp.setOrderType(X_PP_MRP.ORDERTYPE_SalesOrder);
			mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Demand);
		}
		else
		{
			mrp.setOrderType(X_PP_MRP.ORDERTYPE_PurchaseOrder);
			mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
		}

		mrp.setPreparationDate(order.getPreparationDate());
	}

}
