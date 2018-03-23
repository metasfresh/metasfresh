package de.metas.adempiere.modelvalidator;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MFreightCost;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.Util;

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


import org.slf4j.Logger;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.impl.OrderLineBL;

/**
 * @deprecated the code form this class shall be moved a new MV de.metas.modelvalidator.C_OrderLine.
 * @author ts
 *
 */
@Deprecated
public class OrderLine implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(OrderLine.class);

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

		engine.addModelChange(I_C_OrderLine.Table_Name, this);

		// register this service for callouts and model validators
		Services.registerService(IOrderLineBL.class, new OrderLineBL());
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, int type)
	{
		onNewAndChange(po, type);
		onNewAndChangeAndDelete(po, type);
		return null;
	}

	private void onNewAndChange(final PO po, final int type)
	{
		final ModelChangeType changeType = ModelChangeType.valueOf(type);
		if (!changeType.isBefore() || !changeType.isNewOrChange())
		{
			return;
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		if (!ol.isProcessed())
		{
			orderLineBL.setPricesIfNotIgnored(po.getCtx(), ol,
					true, // usePriceUOM
					po.get_TrxName());

			logger.debug("Setting TaxAmtInfo for {}", ol);
			orderLineBL.setTaxAmtInfoIfNotIgnored(po.getCtx(), ol, po.get_TrxName());
		}

		logger.debug("Making sure {} has a M_Shipper_ID", ol);
		orderLineBL.setShipperIfNotIgnored(po.getCtx(), ol, false, po.get_TrxName());
	}

	private void onNewAndChangeAndDelete(final PO po, int type)
	{
		if (!(type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_DELETE))
		{
			return;
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		//
		// updating the freight cost amount, if necessary
		final MOrder orderPO = (MOrder)ol.getC_Order();

		final String dontUpdateOrder = Env.getContext(po.getCtx(), OrderFastInput.OL_DONT_UPDATE_ORDER + orderPO.get_ID());
		if (Util.isEmpty(dontUpdateOrder) || !"Y".equals(dontUpdateOrder))
		{
			final boolean newOrDelete = type == TYPE_AFTER_NEW || type == TYPE_AFTER_DELETE;
			final boolean linesAmtChanged = po.is_ValueChanged(I_C_OrderLine.COLUMNNAME_LineNetAmt);
			final boolean notFixPrice = !X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(orderPO.getFreightCostRule());
			final boolean isCopy = InterfaceWrapperHelper.isCopy(po); // metas: cg: task US215
			if (!isCopy && (linesAmtChanged || notFixPrice || newOrDelete))
			{
				if (MFreightCost.retriveFor(po.getCtx(), ol.getM_Product_ID(), po.get_TrxName()).isEmpty())
				{
					// this ol is not a freight cost order line

					final I_C_Order order = InterfaceWrapperHelper.create(orderPO, I_C_Order.class);

					final IOrderBL orderBL = Services.get(IOrderBL.class);
					if (orderBL.updateFreightAmt(po.getCtx(), order, po.get_TrxName()))
					{
						orderPO.saveEx();
					}
				}
			}
		}
	}
}
