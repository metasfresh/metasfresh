package org.eevolution.model.validator;

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
import java.sql.Timestamp;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.Adempiere;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.IPPOrderWorkflowBL;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.X_PP_Order;

import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import lombok.NonNull;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Order());

		Services.get(ITabCalloutFactory.class).registerTabCalloutForTable(I_PP_Order.Table_Name, org.eevolution.callout.PP_Order_TabCallout.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_PP_Order ppOrder, final ModelChangeType changeType)
	{
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		final boolean newRecord = changeType.isNew();

		//
		// If UOM not filled, get it from Product
		if (ppOrder.getC_UOM_ID() <= 0 && ppOrder.getM_Product_ID() > 0)
		{
			final I_M_Product product = ppOrder.getM_Product();
			final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(product);
			ppOrder.setC_UOM(uom);
		}

		//
		// If DateFinishSchedule is not filled, use DatePromised
		if (ppOrder.getDateFinishSchedule() == null)
		{
			ppOrder.setDateFinishSchedule(ppOrder.getDatePromised());
		}

		//
		// If Warehouse changed or Locator was never set, set it now
		if (ppOrder.getM_Locator_ID() <= 0 || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID))
		{
			final I_M_Warehouse warehouse = ppOrder.getM_Warehouse();
			final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
			ppOrder.setM_Locator(locator);
		}

		//
		// Order Stock
		if (!newRecord
				&& (InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_QtyDelivered)
						|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_QtyOrdered)
						|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_QtyScrap)
						|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID)
						|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Locator_ID)))
		{
			ppOrderBL.orderStock(ppOrder);
		}

		//
		// Set QtyBatchSize and QtyBatchs
		ppOrderBL.updateQtyBatchs(ppOrder, false);

		//
		// Set BPartner if not set
		if (ppOrder.getC_OrderLine_ID() > 0 && ppOrder.getC_BPartner_ID() <= 0)
		{
			final int bpartnerId = ppOrder.getC_OrderLine().getC_BPartner_ID();
			ppOrder.setC_BPartner_ID(bpartnerId);
		}

		//
		// Set PreparationDate from linked Sales Order, if not set (08181)
		if (ppOrder.getPreparationDate() == null && !ppOrder.isProcessed() && ppOrder.getC_OrderLine_ID() > 0)
		{
			final Timestamp preparationDate = ppOrder.getC_OrderLine().getC_Order().getPreparationDate();
			ppOrder.setPreparationDate(preparationDate);
		}

		//
		// Set ASI from OrderLine if not set (08074)
		if (ppOrder.getM_AttributeSetInstance_ID() <= 0
				&& ppOrder.getC_OrderLine_ID() > 0
				&& ppOrder.getC_OrderLine().getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance asi = ppOrder.getC_OrderLine().getM_AttributeSetInstance();
			final I_M_AttributeSetInstance asiCopy = Services.get(IAttributeDAO.class).copy(asi);
			ppOrder.setM_AttributeSetInstance(asiCopy);
		}

		//
		// Warehouse/Locator changed => update Order BOM Lines
		if (InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Warehouse_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_M_Locator_ID)
				|| InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_AD_Org_ID))
		{
			ppOrderBL.updateBOMOrderLinesWarehouseAndLocator(ppOrder);
		}

		//
		// DocTypeTarget:
		if (ppOrder.getC_DocTypeTarget_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_Order.COLUMNNAME_C_DocTypeTarget_ID);
		}

		//
		// DocType: OrderType
		if (newRecord || InterfaceWrapperHelper.isValueChanged(ppOrder, I_PP_Order.COLUMNNAME_C_DocType_ID))
		{
			final I_C_DocType docType = ppOrder.getC_DocType();
			if (docType != null && docType.getC_DocType_ID() > 0)
			{
				ppOrder.setOrderType(docType.getDocSubType());
			}
			else
			{
				ppOrder.setOrderType(null);
			}
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createWorkFlowAndBom(final I_PP_Order ppOrderRecord)
	{
		createWorkflowAndBOM(ppOrderRecord);
	}

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_PP_Order.COLUMNNAME_QtyEntered)
	public void updateAndPostEventOnQtyEnteredChange(final I_PP_Order ppOrderRecord)
	{
		final boolean delivered = Services.get(IPPOrderBL.class).isDelivered(ppOrderRecord);
		if (delivered)
		{
			throw new LiberoException("Cannot Change Quantity, Only is allow with Draft or In Process Status"); // TODO: Create Message for Translation
		}

		final PPOrderChangedEventFactory eventfactory = PPOrderChangedEventFactory.newWithPPOrderBeforeChange(ppOrderRecord);

		deleteWorkflowAndBOM(ppOrderRecord);
		createWorkflowAndBOM(ppOrderRecord);

		final PPOrderChangedEvent event = eventfactory.inspectPPOrderAfterChange();

		final PostMaterialEventService materialEventService = Adempiere.getBean(PostMaterialEventService.class);
		materialEventService.postEventAfterNextCommit(event);
	}

	private void deleteWorkflowAndBOM(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderWorkflowDAO.class).deleteOrderWorkflow(ppOrder);

		final I_PP_Order_BOM orderBOM = Services.get(IPPOrderBOMDAO.class).retrieveOrderBOM(ppOrder);
		if (orderBOM != null)
		{
			InterfaceWrapperHelper.delete(orderBOM);
		}
	}

	private void createWorkflowAndBOM(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderWorkflowBL.class).createOrderWorkflow(ppOrder);
		Services.get(IPPOrderBOMBL.class).createOrderBOMAndLines(ppOrder);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_PP_Order ppOrder)
	{
		final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
		final IPPOrderCostDAO ppOrderCostDAO = Services.get(IPPOrderCostDAO.class);

		//
		// Delete depending records
		final String docStatus = ppOrder.getDocStatus();
		if (X_PP_Order.DOCSTATUS_Drafted.equals(docStatus)
				|| X_PP_Order.DOCSTATUS_InProgress.equals(docStatus))
		{
			ppOrderCostDAO.deleteOrderCosts(ppOrder);
			deleteWorkflowAndBOM(ppOrder);
		}

		//
		// Un-Order Stock
		ppOrderBL.setQtyOrdered(ppOrder, BigDecimal.ZERO);
		ppOrderBL.orderStock(ppOrder);
	}
}
