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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MFreightCost;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.IOrderLineBL;

/**
 * This model validator checks for each new invoice line if there needs to be an additional invoice line for freight cost.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 * 
 */
public class Order implements ModelValidator
{

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
		engine.addDocValidate(org.compiere.model.I_C_Order.Table_Name, this);
		engine.addModelChange(org.compiere.model.I_C_Order.Table_Name, this);
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
		if (timing == ModelValidator.TIMING_AFTER_VOID)
		{
			final MOrder order = (MOrder)po;
			for (final MOrderLine olPO : order.getLines())
			{
				final I_C_OrderLine ol = InterfaceWrapperHelper.create(olPO, I_C_OrderLine.class);
				ol.setCommissionPoints(BigDecimal.ZERO);
				ol.setCommissionPointsNet(BigDecimal.ZERO);
				ol.setCommissionPointsSum(BigDecimal.ZERO);

				olPO.saveEx();
			}
		}

		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == ModelValidator.TYPE_AFTER_CHANGE || type == ModelValidator.TYPE_AFTER_NEW)
		{
			// If the Price List is Changed (may be because of a PricingSystem change) the prices in the order
			// lines need to be updated.
			if (po.is_ValueChanged(org.compiere.model.I_C_Order.COLUMNNAME_M_PriceList_ID))
			{
				final MOrder mOrder = (MOrder)po;

				// Set Commission points in every order line
				for (final MOrderLine ol : mOrder.getLines())
				{
					if (!MFreightCost.retriveFor(po.getCtx(), ol.getM_Product_ID(), po.get_TrxName()).isEmpty())
					{
						continue;
					}

					final I_C_OrderLine oline = InterfaceWrapperHelper.create(ol, I_C_OrderLine.class);
					final IOrderLineBL olinebl = Services.get(IOrderLineBL.class);

					// Put order line on the ignore list to avoid the updateDiscounts method
					// from resetting the commission points in the order line modelValidator.
					olinebl.ignore(ol.get_ID());
					Services.get(IInstanceTriggerBL.class).setCommissionPoints(po.getCtx(), oline, false, po.get_TrxName());
					Services.get(IInstanceTriggerBL.class).setCommissionPointsSum(po.getCtx(), oline, false, po.get_TrxName());
					ol.saveEx();
					olinebl.unignore(ol.get_ID());
				}
				Services.get(IOrderLineBL.class).updateDiscounts(po.getCtx(), mOrder, true, po.get_TrxName());
			}
		}
		return null;
	}
}
