package de.metas.adempiere.webui.util;

/*
 * #%L
 * de.metas.commission.zkwebui
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
import java.text.Format;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.I_C_OrderTax;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.webui.component.PropertyInfoPanel;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.interfaces.I_C_OrderLine;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class ZkUtil
{
	public static GridTab createGridTab(int windowNo, int AD_Window_ID, String tableName, MQuery query)
	{
		final GridWindow gridWindow = GridWindow.get(Env.getCtx(), windowNo, AD_Window_ID, false);
		if (gridWindow == null)
		{
			throw new AdempiereException("Can not load window AD_Window_ID=" + AD_Window_ID + "."
					+ " Please check permissions.");
		}
		for (int i = 0; i < gridWindow.getTabCount(); i++)
		{
			GridTab gridTab = gridWindow.getTab(i);
			if (tableName.equals(gridTab.getTableName()))
			{
				if (!gridTab.isLoadComplete())
				{
					gridWindow.initTab(i);
				}
				gridTab.setQuery(query);
				gridTab.query(false);

				return gridTab;
			}
		}
		return null;
	}
	
	public static void setOrderOverview(PropertyInfoPanel pip, IOrderBL orderBL, I_C_Order order)
	{
		Properties ctx = Env.getCtx();

		final BigDecimal taxAmt = orderBL.retrieveTaxAmt(order);
//		final IFreightCostBL freightCostBL = Services.get(IFreightCostBL.class);
//		final BigDecimal freightCostAmt = freightCostBL.computeFreightCostForOrder(ctx,order, null);
		final BigDecimal freightCostAmt = order.getFreightAmt();
		//
		final Format af = DisplayType.getNumberFormat(DisplayType.Amount, Env.getLanguage(ctx));
		final Format df = DisplayType.getDateFormat(Env.getLanguage(ctx));
		//
		String dnValue = order.getDocumentNo() + " / " + df.format(order.getDateOrdered());
		pip.setValues(I_C_Order.COLUMNNAME_DocumentNo, dnValue);

		String gtValue = af.format(order.getGrandTotal()).toString() + " " + order.getC_Currency().getCurSymbol();
		pip.setValues(I_C_Order.COLUMNNAME_GrandTotal, gtValue);

		String fcValue = af.format(freightCostAmt) + " " + order.getC_Currency().getCurSymbol();
		pip.setValues(I_C_Order.COLUMNNAME_FreightAmt, fcValue);

		pip.setValues(I_C_OrderTax.COLUMNNAME_TaxAmt, af.format(taxAmt) + " " + order.getC_Currency().getCurSymbol());

		BigDecimal points = getCommissionPoints(new MOrder(ctx, order.getC_Order_ID(), null));
		pip.setValues(I_C_OrderLine.COLUMNNAME_CommissionPointsSum, af.format(points));

	}

	public static BigDecimal getCommissionPoints(MOrder order)
	{
		BigDecimal points = Env.ZERO;
		for (MOrderLine ol : order.getLines())
		{
			de.metas.commission.interfaces.I_C_OrderLine orderLine = POWrapper.create(ol, de.metas.commission.interfaces.I_C_OrderLine.class);
			points = points.add(orderLine.getCommissionPointsSum());
		}
		return points;
	}
}
