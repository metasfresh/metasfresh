package de.metas.inoutcandidate.spi.impl;

import java.sql.Timestamp;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;

import com.google.common.base.MoreObjects;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;

/**
 *
 */
public class OrderLineReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	private final static String DEFAULT_OnMaterialReceiptWithDestWarehouse = X_PP_Product_Planning.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement;

	@Override
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final boolean createReceiptScheduleIfNotExists = true;
		final I_M_ReceiptSchedule receiptSchedule = createOrReceiptScheduleFromOrderLine(orderLine, createReceiptScheduleIfNotExists);
		return Collections.singletonList(receiptSchedule);
	}

	@Override
	public void updateReceiptSchedules(final Object model)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final boolean createReceiptScheduleIfNotExists = false;
		createOrReceiptScheduleFromOrderLine(orderLine, createReceiptScheduleIfNotExists);
	}

	private I_M_ReceiptSchedule createOrReceiptScheduleFromOrderLine(final I_C_OrderLine line, final boolean createReceiptScheduleIfNotExists)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		//
		// Create/update Receipt Schedule
		I_M_ReceiptSchedule receiptSchedule = Services.get(IReceiptScheduleDAO.class).retrieveForRecord(line);

		final boolean isNewReceiptSchedule = (receiptSchedule == null);
		if (isNewReceiptSchedule)
		{
			if (!createReceiptScheduleIfNotExists)
			{
				return null;
			}

			receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, line);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);

		receiptSchedule.setAD_Org_ID(line.getAD_Org_ID());
		receiptSchedule.setIsActive(true); // make sure it's active

		//
		// Source Document Line link
		{
			final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(org.compiere.model.I_C_OrderLine.Table_Name);
			receiptSchedule.setAD_Table_ID(adTableId);
			receiptSchedule.setRecord_ID(line.getC_OrderLine_ID());
			receiptSchedule.setC_Order_ID(line.getC_Order_ID());
			receiptSchedule.setC_OrderLine_ID(line.getC_OrderLine_ID());

			// #388
			// set isPackagingMaterial according to the order line
			receiptSchedule.setIsPackagingMaterial(line.isPackagingMaterial());

			final int docTypeId = retrieveReceiptDocTypeId(line);
			receiptSchedule.setC_DocType_ID(docTypeId);
		}

		//
		// Dates
		{
			receiptSchedule.setDateOrdered(line.getDateOrdered());
			receiptSchedule.setMovementDate(line.getDatePromised());
		}

		//
		// BPartner & Location
		receiptSchedule.setC_BPartner_ID(line.getC_BPartner_ID());
		receiptSchedule.setC_BPartner_Location_ID(line.getC_BPartner_Location_ID());
		final I_C_Order order = line.getC_Order();
		receiptSchedule.setAD_User_ID(order.getAD_User_ID());

		//
		// Delivery rule, Priority rule
		{
			receiptSchedule.setDeliveryRule(order.getDeliveryRule());
			receiptSchedule.setDeliveryViaRule(order.getDeliveryViaRule());

			receiptSchedule.setPriorityRule(order.getPriorityRule());
		}

		//
		// task 07185: we only set (or "reset") warehouse if we have new receipt schedule. Otherwise, the warehouses are already set (on complete)
		if (isNewReceiptSchedule)
		//
		// Warehouses
		{
			//
			// From Warehouse
			//
			// This can be null
			final I_M_Warehouse lineWarehouse = line.getM_Warehouse();

			final I_M_Warehouse warehouseToUse;

			if (lineWarehouse != null)
			{
				warehouseToUse = lineWarehouse;
			}
			else
			{
				warehouseToUse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(line);
			}

			receiptSchedule.setM_Warehouse(warehouseToUse);

			//
			// Destination Warehouse

			final I_M_Warehouse warehouseDest = getWarehouseDest(ctx, line);
			receiptSchedule.setM_Warehouse_Dest(warehouseDest);
		}

		//
		// Product, ASI, UOM
		{
			receiptSchedule.setM_Product_ID(line.getM_Product_ID());

			receiptSchedule.setC_UOM_ID(line.getC_UOM_ID());

			Services.get(IAttributeSetInstanceBL.class).cloneASI(receiptSchedule, line);

			// task #653
			// Set the LotNumberDate as attribute in the new receipt schedule's ASI
			createLotNumberDateAI(receiptSchedule, order);

		}

		//
		// Quantities
		if (isNewReceiptSchedule)
		{
			// qty moved is the qty delivered
			// FIXME: i think is better to create an RSA for this instead of setting qtyMoved here
			// But create a RSA only for initial Receipt Schedule, because else you will get duplicate allocations
			receiptSchedule.setQtyMoved(line.getQtyDelivered());
		}
		receiptSchedule.setQtyOrdered(line.getQtyOrdered());
		// receiptSchedule.setQtyToMove(line.getQtyOrdered()); // QtyToMove will be computed in IReceiptScheduleQtysBL

		//
		// Update aggregation key
		final String headerAggregationKey = receiptScheduleBL.getHeaderAggregationKeyBuilder().buildKey(receiptSchedule);
		receiptSchedule.setHeaderAggregationKey(headerAggregationKey);

		// #3549
		receiptSchedule.setOnMaterialReceiptWithDestWarehouse(getOnMaterialReceiptWithDestWarehouse(line));

		//
		// Save & return
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule;
	}

	private String getOnMaterialReceiptWithDestWarehouse(final I_C_OrderLine orderLine)
	{

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final int productId = orderLine.getM_Product_ID();
		final int orgId = orderLine.getAD_Org_ID();
		final int asiId = orderLine.getM_AttributeSetInstance_ID();

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(
				 orgId //
				, 0  // M_Warehouse_ID
				, 0  // S_Resource_ID
				, productId //
				, asiId);

		if (productPlanning == null)
		{
			// fallback to old behaviour -> a movement is created instead of dd_Order
			return DEFAULT_OnMaterialReceiptWithDestWarehouse;
		}
		
		final String onMaterialReceiptWithDestWarehouse = productPlanning.getOnMaterialReceiptWithDestWarehouse();

		return Check.isEmpty(onMaterialReceiptWithDestWarehouse) ? DEFAULT_OnMaterialReceiptWithDestWarehouse : onMaterialReceiptWithDestWarehouse;

	}

	/**
	 * Create LotNumberDate Attribute instance and set it in the receipt shcedule's ASI
	 * 
	 * @param receiptSchedule
	 * @param order
	 */
	private void createLotNumberDateAI(final I_M_ReceiptSchedule receiptSchedule, final I_C_Order order)
	{

		final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);

		I_M_AttributeSetInstance rsASI = receiptSchedule.getM_AttributeSetInstance();

		// #653 In case the ASI doesn't exist, create it
		if (rsASI == null)
		{
			rsASI = Services.get(IAttributeSetInstanceBL.class).createASI(receiptSchedule.getM_Product());
		}

		final I_M_Attribute lotNumberDateAttr = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberDateAttribute(ctx);

		if (lotNumberDateAttr == null)
		{
			// nothing to do

		}
		else
		{

			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
			final int lotNumberDateAttrID = lotNumberDateAttr.getM_Attribute_ID();
			I_M_AttributeInstance lotNumberDateAI = attributeDAO.retrieveAttributeInstance(rsASI, lotNumberDateAttrID);

			if (lotNumberDateAI == null)
			{
				lotNumberDateAI = attributeDAO.createNewAttributeInstance(ctx, rsASI, lotNumberDateAttrID, trxName);
			}

			final de.metas.order.model.I_C_Order orderModel = InterfaceWrapperHelper.create(order, de.metas.order.model.I_C_Order.class);
			final Timestamp lotNumberDate = orderModel.getLotNumberDate();

			// provide the lotNumberDate in the ASI
			if (lotNumberDate != null)
			{
				lotNumberDateAI.setValueDate(lotNumberDate);

				createLotNumberAI(ctx, rsASI, lotNumberDate, trxName);

				// save the attribute instance
				InterfaceWrapperHelper.save(lotNumberDateAI);
			}

		}
	}

	private void createLotNumberAI(Properties ctx, I_M_AttributeSetInstance rsASI, Timestamp lotNumberDate, String trxName)
	{
		Check.assume(lotNumberDate != null, "Lot number date attribute not null");

		final I_M_Attribute lotNumberAttr = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttribute(ctx);

		if (lotNumberAttr == null)
		{
			// nothing to do

		}
		else
		{

			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

			final int lotNumberAttrID = lotNumberAttr.getM_Attribute_ID();
			I_M_AttributeInstance lotNumberAI = attributeDAO.retrieveAttributeInstance(rsASI, lotNumberAttrID);

			if (lotNumberAI == null)
			{
				lotNumberAI = attributeDAO.createNewAttributeInstance(ctx, rsASI, lotNumberAttrID, trxName);
			}

			// provide the lotNumber in the ASI
			final String lotNumberString = Services.get(ILotNumberBL.class).calculateLotNumber(lotNumberDate);
			lotNumberAI.setValue(lotNumberString);

			// save the attribute instance
			InterfaceWrapperHelper.save(lotNumberAI);
		}

	}

	/**
	 * @param ctx
	 * @param line
	 * @param trxName
	 * @return destination warehouse for given order line or <code>null</code>
	 */
	private I_M_Warehouse getWarehouseDest(final Properties ctx, final org.compiere.model.I_C_OrderLine line)
	{
		//
		// If we deal with a drop shipment we shall not have any destination warehouse where we need to move after receipt (08402)
		final I_C_Order order = line.getC_Order();
		if (order.isDropShip())
		{
			return null;
		}

		final IReceiptScheduleWarehouseDestProvider warehouseDestProvider = getWarehouseDestProvider();
		return warehouseDestProvider.getWarehouseDest(OrderLineWarehouseDestProviderContext.of(ctx, line));
	}

	@Override
	public void inactivateReceiptSchedules(final Object model)
	{
		final org.compiere.model.I_C_OrderLine line = InterfaceWrapperHelper.create(model, org.compiere.model.I_C_OrderLine.class);

		final I_M_ReceiptSchedule receiptSchedule = Services.get(IReceiptScheduleDAO.class).retrieveForRecord(line);
		if (receiptSchedule == null)
		{
			return;
		}
		if (receiptSchedule.isProcessed())
		{
			return;
		}
		receiptSchedule.setIsActive(false);
		InterfaceWrapperHelper.delete(receiptSchedule);
	}

	/**
	 * Suggests the C_DocType_ID to be used for the future Material Receipt.
	 *
	 * Basically, it checks:
	 * <ul>
	 * <li>order's C_DocType.C_DocType_Shipment_ID if set
	 * <li>standard Material Receipt document type
	 * </ul>
	 *
	 * @param orderLine
	 * @return
	 */
	private int retrieveReceiptDocTypeId(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderLine.getC_Order();

		//
		// Get Document Type from order
		I_C_DocType docType = order.getC_DocType();
		if (docType == null || docType.getC_DocType_ID() <= 0)
		{
			docType = order.getC_DocTypeTarget();
		}

		//
		// If document type is set, get it's C_DocTypeShipment_ID (if any)
		if (docType != null)
		{
			final int receiptDocTypeId = docType.getC_DocTypeShipment_ID();
			if (receiptDocTypeId > 0)
			{
				return receiptDocTypeId;
			}
		}

		//
		// Fallback: get standard Material Receipt document type
		return Services.get(IDocTypeDAO.class)
				.getDocTypeIdOrNull(DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt)
						.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
						.adClientId(orderLine.getAD_Client_ID())
						.adOrgId(orderLine.getAD_Org_ID())
						.build());
	}

	/** Wraps {@link I_C_OrderLine} as {@link IReceiptScheduleWarehouseDestProvider.IContext} */
	private static final class OrderLineWarehouseDestProviderContext implements IReceiptScheduleWarehouseDestProvider.IContext
	{
		public static final OrderLineWarehouseDestProviderContext of(final Properties ctx, final org.compiere.model.I_C_OrderLine orderLine)
		{
			return new OrderLineWarehouseDestProviderContext(ctx, orderLine);
		}

		private final Properties ctx;
		private final org.compiere.model.I_C_OrderLine orderLine;

		private OrderLineWarehouseDestProviderContext(final Properties ctx, final org.compiere.model.I_C_OrderLine orderLine)
		{
			super();
			this.ctx = ctx;
			this.orderLine = orderLine;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(orderLine).toString();
		}

		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public int getAD_Client_ID()
		{
			return orderLine.getAD_Client_ID();
		}

		@Override
		public int getAD_Org_ID()
		{
			return orderLine.getAD_Org_ID();
		}

		@Override
		public I_M_Product getM_Product()
		{
			return orderLine.getM_Product();
		}

		@Override
		public int getM_Product_ID()
		{
			return orderLine.getM_Product_ID();
		}

		@Override
		public int getM_Warehouse_ID()
		{
			return orderLine.getM_Warehouse_ID();
		}

		@Override
		public I_M_AttributeSetInstance getM_AttributeSetInstance()
		{
			return orderLine.getM_AttributeSetInstance();
		}
	}
}
