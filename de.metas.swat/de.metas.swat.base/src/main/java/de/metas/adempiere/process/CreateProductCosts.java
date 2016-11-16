/**
 * 
 */
package de.metas.adempiere.process;

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


import java.util.Iterator;

import org.compiere.model.I_M_Product;
import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.model.Query;

import de.metas.process.SvrProcess;

/**
 * @author tsa
 * 
 */
public class CreateProductCosts extends SvrProcess
{
	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		int count_all = 0;
		Iterator<PO> it = getProductsQuery().iterate(null, false); // metas: guaranteed = false because we are not changing the products 
		while(it.hasNext())
		{
			MProduct product = (MProduct)it.next();
			process(product);
			count_all++;
		}
		return "@Updated@ #"+count_all;
	}

	private void process(MProduct product)
	{
		try
		{
			MCost.create(product);
			commitEx();
		}
		catch (Exception e)
		{
			addLog("Error on "+product.getName()+": "+e.getLocalizedMessage());
		}
	}

	private Query getProductsQuery()
	{
		final String whereClause = I_M_Product.COLUMNNAME_AD_Client_ID + "=?";
		return new Query(getCtx(), I_M_Product.Table_Name, whereClause, get_TrxName())
				.setParameters(getAD_Client_ID())
				.setOrderBy(I_M_Product.COLUMNNAME_M_Product_ID);
	}
}
