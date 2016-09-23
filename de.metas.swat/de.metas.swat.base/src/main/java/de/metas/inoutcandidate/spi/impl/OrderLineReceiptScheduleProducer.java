package de.metas.inoutcandidate.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.document.IDocTypeDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;
import de.metas.interfaces.I_C_OrderLine;

/**
 *
 */
public class OrderLineReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	private final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);

	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	@Override
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptScheduleFromOrderLine(orderLine);
		return Collections.singletonList(receiptSchedule);
	}

	private I_M_ReceiptSchedule createReceiptScheduleFromOrderLine(final I_C_OrderLine line)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		//
		// Create/update Receipt Schedule
		I_M_ReceiptSchedule receiptSchedule = Services.get(IReceiptScheduleDAO.class).retrieveForRecord(line);

		final boolean isNewReceiptSchedule = (receiptSchedule == null);
		if (isNewReceiptSchedule)
		{
			receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class, line);
		}
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
			final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
			final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);

			final I_M_Warehouse warehouseDest = getWarehouseDest(ctx, line, trxName);
			receiptSchedule.setM_Warehouse_Dest(warehouseDest);
		}

		//
		// Product, ASI, UOM
		{
			receiptSchedule.setM_Product_ID(line.getM_Product_ID());

			Services.get(IAttributeSetInstanceBL.class).cloneASI(receiptSchedule, line);

			receiptSchedule.setC_UOM_ID(line.getC_UOM_ID());
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

		//
		// Save & return
		InterfaceWrapperHelper.save(receiptSchedule);
		return receiptSchedule;
	}

	/**
	 * @param ctx
	 * @param line
	 * @param trxName
	 * @return destination warehouse for given order line or <code>null</code>
	 */
	private I_M_Warehouse getWarehouseDest(final Properties ctx, final org.compiere.model.I_C_OrderLine line, final String trxName)
	{
		//
		// If we deal with a drop shipment we shall not have any destination warehouse where we need to move after receipt (08402)
		final I_C_Order order = line.getC_Order();
		if (order.isDropShip())
		{
			return null;
		}

		//
		// Try to retrieve destination warehouse from planning
		// see: http://dewiki908/mediawiki/index.php/07058_Destination_Warehouse_Wareneingang_%28102083181965%29#Development_infrastructure
		final I_M_Warehouse distributionNetworkWarehouseDestination = getDistributionNetworkWarehouseDestination(ctx, line, trxName);
		if (distributionNetworkWarehouseDestination != null)
		{
			return distributionNetworkWarehouseDestination;
		}

		//
		// Fallback if no planning destination warehouse was found
		// see: http://dewiki908/mediawiki/index.php/05940_Wareneingang_Lagerumbuchung
		final I_M_Locator locator = line.getM_Product().getM_Locator();
		if (locator != null && locator.getM_Locator_ID() > 0)
		{
			return locator.getM_Warehouse();
		}

		//
		// We don't have anything to match
		return null;
	}

	/**
	 * @param ctx
	 * @param line
	 * @param trxName
	 * @return first planning-distribution (i.e. with lowest <code>PriorityNo</code>) network-warehouse destination found for product (no warehouse / source filter).
	 */
	private I_M_Warehouse getDistributionNetworkWarehouseDestination(final Properties ctx, final org.compiere.model.I_C_OrderLine line, final String trxName)
	{
		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(ctx,
				line.getAD_Org_ID(),
				0, // M_Warehouse_ID
				0, // S_Resource_ID
				line.getM_Product_ID(), trxName);

		if (productPlanning == null)
		{
			return null;
		}

		final I_DD_NetworkDistribution distributionNetwork = productPlanning.getDD_NetworkDistribution();
		if (distributionNetwork == null)
		{
			return null;
		}

		final List<I_DD_NetworkDistributionLine> distributionNetworkLines = distributionNetworkDAO
				.retrieveNetworkLinesBySourceWarehouse(distributionNetwork, line.getM_Warehouse_ID());

		if (distributionNetworkLines.isEmpty())
		{
			return null;
		}

		// the lines are ordered by PriorityNo, M_Shipper_ID
		final I_DD_NetworkDistributionLine firstFoundDistributionNetworkLine = distributionNetworkLines.get(0);
		return firstFoundDistributionNetworkLine.getM_Warehouse();
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
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);
		return Services.get(IDocTypeDAO.class).getDocTypeIdOrNull(ctx, X_C_DocType.DOCBASETYPE_MaterialReceipt,
				orderLine.getAD_Client_ID(), orderLine.getAD_Org_ID(),
				trxName);
	}
}
