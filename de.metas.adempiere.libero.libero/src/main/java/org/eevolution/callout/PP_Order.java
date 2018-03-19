package org.eevolution.callout;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IDocumentNoInfo;
import de.metas.material.planning.IProductPlanningDAO;

/**
 * Manufacturing order callout
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Order.class)
public class PP_Order extends CalloutEngine
{
	/**
	 * When document type (target) is changed, update the DocumentNo.
	 */
	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID)
	public void onC_DocTypeTarget_ID(I_PP_Order ppOrder)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(ppOrder.getC_DocTypeTarget())
				.setOldDocType_ID(ppOrder.getC_DocType_ID())
				.setOldDocumentNo(ppOrder.getDocumentNo())
				.setDocumentModel(ppOrder)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			ppOrder.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		return;
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_M_Product_ID)
	public void onProductChanged(final I_PP_Order ppOrder)
	{
		final I_M_Product product = ppOrder.getM_Product();
		if (product == null)
		{
			return;
		}

		ppOrder.setC_UOM_ID(product.getC_UOM_ID());

		final I_PP_Product_Planning pp = findPP_Product_Planning(ppOrder);
		ppOrder.setAD_Workflow_ID(pp.getAD_Workflow_ID());
		ppOrder.setPP_Product_BOM_ID(pp.getPP_Product_BOM_ID());

		if (pp.getPP_Product_BOM_ID() > 0)
		{
			final I_PP_Product_BOM bom = pp.getPP_Product_BOM();
			ppOrder.setC_UOM_ID(bom.getC_UOM_ID());
		}

		Services.get(IPPOrderBL.class).updateQtyBatchs(ppOrder, true); // override
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_QtyEntered)
	public void onQtyEnteredChanged(final I_PP_Order ppOrder)
	{
		updateQtyOrdered(ppOrder);

		Services.get(IPPOrderBL.class).updateQtyBatchs(ppOrder, true); // override
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_C_UOM_ID)
	public void onC_UOM_ID(final I_PP_Order ppOrder)
	{
		updateQtyOrdered(ppOrder);
	}

	@CalloutMethod(columnNames = I_PP_Order.COLUMNNAME_M_Warehouse_ID)
	public void onM_Warehouse_ID(final I_PP_Order ppOrder)
	{
		final I_M_Warehouse warehouse = ppOrder.getM_Warehouse();
		if (warehouse == null)
		{
			return;
		}

		I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
		ppOrder.setM_Locator(locator);
	}

	/** Calculates and sets QtyOrdered from QtyEntered and UOM */
	private final void updateQtyOrdered(final I_PP_Order ppOrder)
	{
		final int productId = ppOrder.getM_Product_ID();
		final int uomToId = ppOrder.getC_UOM_ID();
		final BigDecimal qtyEntered = ppOrder.getQtyEntered();

		BigDecimal qtyOrdered;
		if (productId <= 0 || uomToId <= 0)
		{
			qtyOrdered = qtyEntered;
		}
		else
		{
			qtyOrdered = Services.get(IUOMConversionBL.class)
					.convertToProductUOM(Env.getCtx(), ppOrder.getM_Product(), ppOrder.getC_UOM(), qtyEntered);
			if (qtyOrdered == null)
			{
				qtyOrdered = qtyEntered;
			}
		}

		ppOrder.setQtyOrdered(qtyOrdered);
	}

	/**
	 * Find Product Planning Data for given manufacturing order. If not planning found, a new one is created and filled with default values.
	 * <p>
	 * TODO: refactor with org.eevolution.process.MRP.getProductPlanning method
	 *
	 * @param ctx context
	 * @param ppOrder manufacturing order
	 * @return product planning data (never return null)
	 */
	protected static I_PP_Product_Planning findPP_Product_Planning(final I_PP_Order ppOrder)
	{
		I_PP_Product_Planning pp = Services.get(IProductPlanningDAO.class).find(
				ppOrder.getAD_Org_ID(),
				ppOrder.getM_Warehouse_ID(),
				ppOrder.getS_Resource_ID(),
				ppOrder.getM_Product_ID(),
				ppOrder.getM_AttributeSetInstance_ID());

		if (pp == null)
		{
			pp = newInstance(I_PP_Product_Planning.class);
			pp.setAD_Org_ID(ppOrder.getAD_Org_ID());
			pp.setM_Warehouse_ID(ppOrder.getM_Warehouse_ID());
			pp.setS_Resource_ID(ppOrder.getS_Resource_ID());
			pp.setM_Product(ppOrder.getM_Product());
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
}
