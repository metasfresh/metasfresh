package de.metas.inoutcandidate.spi.impl;

import com.google.common.base.MoreObjects;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.OnMaterialReceiptWithDestWarehouse;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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

/**
 *
 */
public class OrderLineReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	private final static OnMaterialReceiptWithDestWarehouse DEFAULT_OnMaterialReceiptWithDestWarehouse = OnMaterialReceiptWithDestWarehouse.CREATE_MOVEMENT;

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

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

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

			final Timestamp dateOrdered = CoalesceUtil.coalesceSuppliers(
					line::getDateOrdered,
					() -> line.getC_Order().getDateOrdered());
			receiptSchedule.setDateOrdered(dateOrdered);

			final Timestamp datePromised = CoalesceUtil.coalesceSuppliers(
					line::getDatePromised,
					() -> line.getC_Order().getDatePromised());
			receiptSchedule.setMovementDate(datePromised);
		}

		//
		// BPartner & Location
		receiptSchedule.setC_BPartner_ID(line.getC_BPartner_ID());
		receiptSchedule.setC_BPartner_Location_ID(line.getC_BPartner_Location_ID());
		final I_C_Order order = line.getC_Order();
		receiptSchedule.setAD_User_ID(order.getAD_User_ID());
		receiptSchedule.setPOReference(order.getPOReference());
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
			final WarehouseId lineWarehouseId = WarehouseId.ofRepoIdOrNull(line.getM_Warehouse_ID());

			final WarehouseId warehouseIdToUse;

			if (lineWarehouseId != null)
			{
				warehouseIdToUse = lineWarehouseId;
			}
			else
			{
				warehouseIdToUse = Services.get(IWarehouseAdvisor.class).evaluateWarehouse(line);
				if (warehouseIdToUse == null)
				{
					throw new AdempiereException("No warehouse found for " + line);
				}
			}

			receiptSchedule.setM_Warehouse_ID(warehouseIdToUse.getRepoId());

			//
			// Destination Warehouse

			final I_M_Warehouse warehouseDest = getWarehouseDest(ctx, line);
			receiptSchedule.setM_Warehouse_Dest_ID(warehouseDest == null ? 0 : warehouseDest.getM_Warehouse_ID());
		}

		//
		// Product, ASI, UOM
		{
			receiptSchedule.setM_Product_ID(line.getM_Product_ID());

			receiptSchedule.setC_UOM_ID(line.getC_UOM_ID());

			attributeSetInstanceBL.cloneOrCreateASI(receiptSchedule, line);

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
		// Contract
		receiptSchedule.setC_Flatrate_Term_ID(line.getC_Flatrate_Term_ID());

		//
		// Update aggregation key
		final String headerAggregationKey = receiptScheduleBL.getHeaderAggregationKeyBuilder().buildKey(receiptSchedule);
		receiptSchedule.setHeaderAggregationKey(headerAggregationKey);

		// #3549
		receiptSchedule.setOnMaterialReceiptWithDestWarehouse(getOnMaterialReceiptWithDestWarehouse(line).getCode());

		final Dimension orderLineDimension = dimensionService.getFromRecord(line);
		dimensionService.updateRecord(receiptSchedule, orderLineDimension);

		//
		// Save & return
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule;
	}

	@NonNull
	private OnMaterialReceiptWithDestWarehouse getOnMaterialReceiptWithDestWarehouse(final I_C_OrderLine orderLine)
	{

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(orderLine.getAD_Org_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());

		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				// no warehouse, no plant
				.build();
		final ProductPlanning productPlanning = productPlanningDAO.find(query).orElse(null);
		if (productPlanning == null)
		{
			// fallback to old behavior -> a movement is created instead of dd_Order
			return DEFAULT_OnMaterialReceiptWithDestWarehouse;
		}

		final OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse = productPlanning.getOnMaterialReceiptWithDestWarehouse();

		return onMaterialReceiptWithDestWarehouse != null ? onMaterialReceiptWithDestWarehouse : DEFAULT_OnMaterialReceiptWithDestWarehouse;
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
			final ProductId productId = ProductId.ofRepoId(receiptSchedule.getM_Product_ID());
			rsASI = Services.get(IAttributeSetInstanceBL.class).createASI(productId);
		}

		final AttributeId lotNumberDateAttrId = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberDateAttributeId();
		if (lotNumberDateAttrId == null)
		{
			// nothing to do
		}
		else
		{
			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

			final AttributeSetInstanceId rsASIId = AttributeSetInstanceId.ofRepoIdOrNone(rsASI.getM_AttributeSetInstance_ID());
			I_M_AttributeInstance lotNumberDateAI = attributeDAO.retrieveAttributeInstance(rsASIId, lotNumberDateAttrId);
			if (lotNumberDateAI == null)
			{
				lotNumberDateAI = attributeDAO.createNewAttributeInstance(ctx, rsASI, lotNumberDateAttrId, trxName);
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

		final AttributeId lotNumberAttrId = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttributeId();
		if (lotNumberAttrId == null)
		{
			// nothing to do
		}
		else
		{

			final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

			final AttributeSetInstanceId rsASIId = AttributeSetInstanceId.ofRepoIdOrNone(rsASI.getM_AttributeSetInstance_ID());
			I_M_AttributeInstance lotNumberAI = attributeDAO.retrieveAttributeInstance(rsASIId, lotNumberAttrId);

			if (lotNumberAI == null)
			{
				lotNumberAI = attributeDAO.createNewAttributeInstance(ctx, rsASI, lotNumberAttrId, trxName);
			}

			// provide the lotNumber in the ASI
			final String lotNumberString = Services.get(ILotNumberBL.class).calculateLotNumber(lotNumberDate);
			lotNumberAI.setValue(lotNumberString);

			// save the attribute instance
			InterfaceWrapperHelper.save(lotNumberAI);
		}

	}

	/**
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
	 * <p>
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
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final I_C_Order order = orderLine.getC_Order();

		//
		// Get Document Type from order
		DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId == null)
		{
			docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		}

		//
		// If document type is set, get it's C_DocTypeShipment_ID (if any)
		if (docTypeId != null)
		{
			final I_C_DocType docType = docTypeDAO.getById(docTypeId);
			final int receiptDocTypeId = docType.getC_DocTypeShipment_ID();
			if (receiptDocTypeId > 0)
			{
				return receiptDocTypeId;
			}
		}

		//
		// Fallback: get standard Material Receipt document type
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(orderLine.getAD_Client_ID())
				.adOrgId(orderLine.getAD_Org_ID())
				.build();
		return DocTypeId.toRepoId(docTypeDAO.getDocTypeIdOrNull(query));
	}

	/**
	 * Wraps {@link I_C_OrderLine} as {@link IReceiptScheduleWarehouseDestProvider.IContext}
	 */
	private static final class OrderLineWarehouseDestProviderContext implements IReceiptScheduleWarehouseDestProvider.IContext
	{
		public static OrderLineWarehouseDestProviderContext of(final Properties ctx, final org.compiere.model.I_C_OrderLine orderLine)
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
