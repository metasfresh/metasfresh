/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.model;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.MUOMConversion;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.IProductPlanningDAO;

/**
 * Callout (Manufacturing) Order
 * 
 * @author Victor Perez
 * @author Teo Sarca, www.arhipac.ro
 */
public class CalloutOrder extends CalloutEngine
{
	/** Debug Steps */
	private boolean steps = false;

	/**
	 * Order Line - Quantity. - called from C_UOM_ID, QtyEntered, QtyOrdered - enforces qty UOM relationship
	 * 
	 * @param ctx Context
	 * @param WindowNo current Window No
	 * @param mTab Model Tab
	 * @param mField Model Field
	 * @param value The new value
	 */
	@SuppressWarnings("deprecation") // hide those to not polute our Warnings
	public String qty(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null)
			return "";

		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		if (steps)
			log.warn("qty - init - M_Product_ID=" + M_Product_ID + " - ");
		BigDecimal QtyOrdered = Env.ZERO;
		BigDecimal QtyEntered = Env.ZERO;

		// No Product
		if (M_Product_ID == 0)
		{
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			mTab.setValue("QtyOrdered", QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID"))
		{

			int C_UOM_To_ID = ((Integer)value).intValue();
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			QtyOrdered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		// QtyEntered changed - calculate QtyOrdered
		else if (mField.getColumnName().equals("QtyEntered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal)value;
			QtyOrdered = MUOMConversion.convertToProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			log.debug("qty - UOM=" + C_UOM_To_ID
					+ ", QtyEntered=" + QtyEntered
					+ " -> " + conversion
					+ " QtyOrdered=" + QtyOrdered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		// QtyOrdered changed - calculate QtyEntered
		else if (mField.getColumnName().equals("QtyOrdered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyOrdered = (BigDecimal)value;
			QtyEntered = MUOMConversion.convertFromProductUOM(ctx, M_Product_ID,
					C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.debug("qty - UOM=" + C_UOM_To_ID
					+ ", QtyOrdered=" + QtyOrdered
					+ " -> " + conversion
					+ " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		return qtyBatch(ctx, WindowNo, mTab, mField, value);
		// return "";
	}	// qty

	public String qtyBatch(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_PP_Order order = InterfaceWrapperHelper.create(mTab, I_PP_Order.class);
		Services.get(IPPOrderBL.class).updateQtyBatchs(order, true);
		return "";
	}

	public String product(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())
			return "";

		final I_PP_Order order = InterfaceWrapperHelper.create(mTab, I_PP_Order.class);
		final I_M_Product product = order.getM_Product();
		if (product == null)
		{
			return "";
		}
		order.setC_UOM_ID(product.getC_UOM_ID());

		final I_PP_Product_Planning pp = getPP_Product_Planning(ctx, order);
		order.setAD_Workflow_ID(pp.getAD_Workflow_ID());
		order.setPP_Product_BOM_ID(pp.getPP_Product_BOM_ID());

		if (pp.getPP_Product_BOM_ID() > 0)
		{
			final I_PP_Product_BOM bom = pp.getPP_Product_BOM();
			order.setC_UOM_ID(bom.getC_UOM_ID());
		}

		Services.get(IPPOrderBL.class).updateQtyBatchs(order, true);

		return "";
	}

	/**
	 * Find Product Planning Data for given manufacturing order. If not planning found, a new one is created and filled with default values.
	 * <p>
	 * TODO: refactor with org.eevolution.process.MRP.getProductPlanning method
	 * 
	 * @param ctx context
	 * @param order manufacturing order
	 * @return product planning data (never return null)
	 */
	protected static I_PP_Product_Planning getPP_Product_Planning(Properties ctx, I_PP_Order order)
	{
		I_PP_Product_Planning pp = Services.get(IProductPlanningDAO.class).find(ctx,
				order.getAD_Org_ID(), order.getM_Warehouse_ID(),
				order.getS_Resource_ID(), order.getM_Product_ID(),
				null);
		if (pp == null)
		{
			pp = InterfaceWrapperHelper.create(ctx, I_PP_Product_Planning.class, ITrx.TRXNAME_None);
			pp.setAD_Org_ID(order.getAD_Org_ID());
			pp.setM_Warehouse_ID(order.getM_Warehouse_ID());
			pp.setS_Resource_ID(order.getS_Resource_ID());
			pp.setM_Product(order.getM_Product());
		}
		InterfaceWrapperHelper.setSaveDeleteDisabled(pp, true);

		final I_M_Product product = pp.getM_Product();
		//
		if (pp.getAD_Workflow_ID() <= 0)
		{
			final I_AD_Workflow workflow = Services.get(IPPWorkflowDAO.class).retrieveWorkflowForProduct(product);
			pp.setAD_Workflow(workflow);
		}
		if (pp.getPP_Product_BOM_ID() <= 0)
		{
			final I_PP_Product_BOM bom = Services.get(IProductBOMDAO.class).retrieveDefaultBOM(product);
			pp.setPP_Product_BOM(bom);
		}
		//
		return pp;
	}
}	// CalloutOrder

