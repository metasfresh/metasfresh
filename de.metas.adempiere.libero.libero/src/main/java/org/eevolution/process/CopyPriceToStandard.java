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

import org.adempiere.model.engines.CostDimension;
import org.adempiere.util.Services;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.eevolution.exceptions.LiberoException;

import de.metas.currency.ICurrencyBL;

/**
 * CopyPriceToStandard
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: CopyPriceToStandard.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 */
public class CopyPriceToStandard extends SvrProcess
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

		MPriceListVersion plv = new MPriceListVersion(getCtx(), p_M_PriceList_Version_ID, get_TrxName());
		for (final MProductPrice pprice : plv.getProductPrice(" AND " + MProductPrice.COLUMNNAME_PriceStd + "<>0"))
		{
			BigDecimal price = pprice.getPriceStd();
			int C_Currency_ID = plv.getPriceList().getC_Currency_ID();
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
}
