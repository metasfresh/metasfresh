package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MClient;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.IOrderLineBL;
import de.metas.logging.LogManager;

public class OrderLine implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(OrderLine.class);
	public static final String SYSCONFIG_IS_UPDATE_DISCOUNTS = "de.metas.commission.IsUpdateDiscounts";

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		// client = null for global validator
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
			OrderLine.logger.info("Initializing validator: " + this + " for client " + client.toString());
		}
		else
		{
			OrderLine.logger.info("Initializing global validator: " + this);
		}
		engine.addModelChange(org.compiere.model.I_C_OrderLine.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		assert po instanceof MOrderLine : po;

		final Properties ctx = po.getCtx();
		final String trxName = po.get_TrxName();

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			// Note: qty ordered is also changed when an order is closed or voided.
			// In these cases, the order is already processed, but still the value is changed
			// and therefore the commission points need to be changed, too.
			if (po.is_ValueChanged(org.compiere.model.I_C_OrderLine.COLUMNNAME_QtyOrdered))
			{
				final String errMsg = updateCommissionPoints(ol, ctx, trxName);
				if (!Check.isEmpty(errMsg))
				{
					return errMsg;
				}
			}
		}

		// metas: Order mit WP haben isProcessed = 'N' und werden sonst auch nach Fertigstellen aktualisiert
		final I_C_Order o = ol.getC_Order();
		if (ol.isProcessed() || DocAction.STATUS_WaitingPayment.equals(o.getDocStatus()))
		{
			// nothing more to do
			return null;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		if ((type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE) && ol.getM_Product_ID() > 0)
		{
			final I_M_Product product = InterfaceWrapperHelper.create(ol.getM_Product(), I_M_Product.class);
			Check.assume(product != null, "ol.getM_Product_ID()>0, so product can'tbe null");

			// check if we are dealing with manual commission points

			// US049: Set the commission points depending on the flag M_Product.IsDiverse
			if (po.is_ValueChanged(org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID)) // When product was changed ...
			{
				if (product.isDiverse()) // ... to a diverse Product
				{
					ol.setIsManualCommissionPoints(true);
				}
				else
				// ... to a not diverse Product ...
				{
					final Integer oldProductIdObj = (Integer)po.get_ValueOld(org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID);
					final int oldProductID = oldProductIdObj == null ? 0 : oldProductIdObj.intValue();
					final I_M_Product oldProduct = InterfaceWrapperHelper.create(ctx, oldProductID, I_M_Product.class, trxName);

					if (oldProduct.isDiverse()) // ... and previous Product was a diverse Product
					{
						ol.setIsManualCommissionPoints(false);
					}
				}
			}
		}

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE || type == ModelValidator.TYPE_AFTER_DELETE)
		{
			final String updateDiscounts = Services.get(ISysConfigDAO.class).retrieveSysConfigValue(SYSCONFIG_IS_UPDATE_DISCOUNTS, "N", po.getAD_Client_ID(), po.getAD_Org_ID());
			if ("Y".equals(updateDiscounts))
			{
				// update price and discount of 'ol' and/or its "siblings"
				final BigDecimal oldValue = (BigDecimal)po.get_ValueOld(IInstanceTrigger.COLUMNNAME_CommissionPoints);
				final String updatedDiscounts = orderLineBL.updateDiscounts(ctx, ol, oldValue, false, true, trxName);

				if (updatedDiscounts == null)
				{
					return "";
				}
			}
		}
		return null;
	}

	/**
	 * Set the ol's commission points columns
	 * 
	 */
	private String updateCommissionPoints(final I_C_OrderLine ol, final Properties ctx, final String trxName)
	{
		final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);
		String errorMsg = instanceTriggerBL.setCommissionPoints(ctx, ol, true, trxName);
		if (errorMsg != null)
		{
			return errorMsg;
		}

		errorMsg = instanceTriggerBL.setCommissionPointsSum(ctx, ol, true, trxName);
		if (errorMsg != null)
		{
			return errorMsg;
		}

		// update 'CommissionPointsNet'
		Services.get(IInstanceTriggerBL.class).updateCommissionPointsNet(ol);
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
