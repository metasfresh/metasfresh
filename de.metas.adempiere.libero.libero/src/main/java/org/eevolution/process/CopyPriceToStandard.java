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

package org.eevolution.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;

import de.metas.currency.ICurrencyBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 * CopyPriceToStandard
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: CopyPriceToStandard.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 */
public class CopyPriceToStandard extends JavaProcess
{
	// services
	private final transient ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

	// parameters
	private int p_AD_Org_ID = 0;
	private int p_C_AcctSchema_ID = 0;
	private int p_M_CostType_ID = 0;
	private int p_M_CostElement_ID = 0;
	private int p_M_PriceList_Version_ID = 0;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
				;
			else if (name.equals("M_CostType_ID"))
			{
				p_M_CostType_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("C_AcctSchema_ID"))
			{
				p_C_AcctSchema_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("M_CostElement_ID"))
			{
				p_M_CostElement_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("M_PriceList_Version_ID"))
			{
				p_M_PriceList_Version_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		MAcctSchema as = MAcctSchema.get(getCtx(), p_C_AcctSchema_ID);
		MCostElement element = MCostElement.get(getCtx(), p_M_CostElement_ID);
		if (!MCostElement.COSTELEMENTTYPE_Material.equals(element.getCostElementType()))
		{
			throw new LiberoException("Only Material Cost Elements are allowed");
		}

		int count_updated = 0;

		final I_M_PriceList_Version plv = load(p_M_PriceList_Version_ID, I_M_PriceList_Version.class);
		for (final I_M_ProductPrice pprice : getProductPrice(p_M_PriceList_Version_ID))
		{
			BigDecimal price = pprice.getPriceStd();
			final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(plv.getM_PriceList_ID());
			int C_Currency_ID = pl.getC_Currency_ID();
			if (C_Currency_ID != as.getC_Currency_ID())
			{
				price = currencyConversionBL.convert(getCtx(), pprice.getPriceStd(),
						C_Currency_ID, as.getC_Currency_ID(),
						getAD_Client_ID(), p_AD_Org_ID);
			}
			MProduct product = MProduct.get(getCtx(), pprice.getM_Product_ID());
			CostDimension d = new CostDimension(product, as, p_M_CostType_ID, p_AD_Org_ID, 0, p_M_CostElement_ID);
			Collection<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
			for (MCost cost : costs)
			{
				if (cost.getM_CostElement_ID() == element.get_ID())
				{
					cost.setFutureCostPrice(price);
					cost.saveEx();
					count_updated++;
					break;
				}
			}
		}
		return "@Updated@ #" + count_updated;
	}
	
	private static List<I_M_ProductPrice> getProductPrice(int priceListVersionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, priceListVersionId)
				.addNotEqualsFilter(I_M_ProductPrice.COLUMN_PriceStd, BigDecimal.ZERO)
				.create()
				.list();
	}
}
