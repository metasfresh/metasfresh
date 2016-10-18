package de.metas.purchasing.modelvalidator;

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


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.model.I_AD_Relation;
import org.adempiere.model.I_AD_RelationType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MRelationExplicitv1;
import org.adempiere.model.MRelationExplicitv1.SourceOrTarget;
import org.adempiere.model.MRelationType;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRequisition;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IStoragePA;
import de.metas.purchasing.model.MMPurchaseSchedule;
import de.metas.purchasing.service.IPurchaseScheduleBL;

/**
 * 
 * @author ts
 * 
 */
public final class PurchaseModelValidator implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(PurchaseModelValidator.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addDocValidate(I_C_Order.Table_Name, this);
		engine.addModelChange(I_C_Order.Table_Name, this);
		// engine.addDocValidate(I_M_Requisition.Table_Name, this);

		engine.addModelChange(I_M_Storage.Table_Name, this);
		// engine.addModelChange(I_M_PurchaseSchedule.Table_Name, this);
		// engine.addModelChange(I_C_OrderLine.Table_Name, this);

		// TODO: 01286 monitor M_Product.IsPurchased and make sure that is don't interferes with existing purchase
		// schedules
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (po instanceof I_M_Storage)
		{
			if (type != TYPE_AFTER_NEW && type != TYPE_AFTER_CHANGE)
			{
				return null;
			}
			if (!po.is_ValueChanged(I_M_Storage.COLUMNNAME_QtyOnHand)
					&& !po.is_ValueChanged(I_M_Storage.COLUMNNAME_QtyReserved))
			{
				return null;
			}

			logger.info(po + "; QtyOnHand: " + po.get_ValueOld(I_M_Storage.COLUMNNAME_QtyOnHand) + " => " + po.get_Value(I_M_Storage.COLUMNNAME_QtyOnHand)
					+ "; QtyReserved: " + po.get_ValueOld(I_M_Storage.COLUMNNAME_QtyReserved) + " => " + po.get_Value(I_M_Storage.COLUMNNAME_QtyReserved));
			final IPurchaseScheduleBL purchaseScheduleBL = Services.get(IPurchaseScheduleBL.class);
			final IStoragePA storagePA = Services.get(IStoragePA.class);

			final I_M_Storage storage = (I_M_Storage)po;

			final int productId = storage.getM_Product_ID();
			final int warehouseId = storagePA.retrieveWarehouseId(storage, po.get_TrxName());

			purchaseScheduleBL.updateStorageData(po.getCtx(), productId, warehouseId, po.get_TrxName());
			purchaseScheduleBL.updateQtyToOrder(po.getCtx(), productId, warehouseId, po.get_TrxName());
		}
		if (po instanceof MOrder)
		{
			// if a purchase order is deleted, we need to clear the references from our purchase schedules
			final MOrder order = (MOrder)po;
			if (!order.isSOTrx() && type == TYPE_BEFORE_DELETE)
			{
				unsetPurchaseOrderReference(order);
			}
		}
		return null;
	}

	private void unsetPurchaseOrderReference(final MOrder order)
	{
		final List<MMPurchaseSchedule> schedulesForOrder = MMPurchaseSchedule.mkQuery(order.getCtx(), order.get_TrxName()).setOrderPOId(order.get_ID()).list();
		for (final MMPurchaseSchedule ps : schedulesForOrder)
		{
			ps.setC_OrderPO_ID(0);
			ps.saveEx();
		}
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		final Set<MMPurchaseSchedule> purchaseScheds = new HashSet<MMPurchaseSchedule>();

		MOrder order = null;
		I_M_Requisition requisition = null;

		final IPurchaseScheduleBL purchaseScheduleBL = Services.get(IPurchaseScheduleBL.class);

		if (po instanceof MOrder)
		{
			order = (MOrder)po;

			final boolean doUpdate =
					timing == TIMING_AFTER_COMPLETE
							// || timing == TIMING_AFTER_PREPARE
							|| timing == TIMING_AFTER_CLOSE
							// || timing == TIMING_AFTER_REACTIVATE
							|| timing == TIMING_AFTER_VOID;

			if (doUpdate && order.isSOTrx())
			{
				// make sure that a purchase schedule exists for order's ols
				purchaseScheds.addAll(purchaseScheduleBL.retrieveOrCreateForSO(po.getCtx(), order, po.get_TrxName()));
			}

			if (doUpdate && !order.isSOTrx())
			{
				final I_AD_RelationType relType = MRelationType.retrieveForInternalName(order.getCtx(), MMPurchaseSchedule.RELTYPE_CURRENT_PO_INT_NAME, order.get_TrxName());
				for (final MOrderLine ol : order.getLines())
				{
					for (MRelationExplicitv1 relExp : MRelationExplicitv1.retrieveFor(ol, relType, SourceOrTarget.TARGET))
					{
						final I_AD_Relation relation = relExp.getAD_Relation();
						InterfaceWrapperHelper.delete(relation);
					}
				}
				unsetPurchaseOrderReference(order);
			}
		}

		if (po instanceof MRequisition)
		{
			requisition = (I_M_Requisition)po;

			if (timing == ModelValidator.TIMING_AFTER_COMPLETE)
			{
				purchaseScheds.addAll(purchaseScheduleBL.retrieveOrCreateForReq(po.getCtx(), requisition, po.get_TrxName()));
			}
		}

		for (final MMPurchaseSchedule purchaseSchedule : purchaseScheds)
		{
			if (purchaseSchedule == null)
			{
				continue;
			}
			purchaseScheduleBL.updateStorageData(po.getCtx(), purchaseSchedule, po.get_TrxName());
			purchaseScheduleBL.updateQtyToOrder(purchaseSchedule);
			purchaseSchedule.saveEx();
		}
		return null;
	}
}
