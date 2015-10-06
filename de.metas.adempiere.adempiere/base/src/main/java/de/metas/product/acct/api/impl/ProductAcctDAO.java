package de.metas.product.acct.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;

import de.metas.product.acct.api.IProductAcctDAO;

/**
 * @author al
 */
public class ProductAcctDAO implements IProductAcctDAO
{
	@Override
	public I_C_Activity retrieveActivityForAcct(
			final IContextAware contextProvider,
			final I_AD_Org org,
			final I_M_Product product)
	{
		final I_C_AcctSchema acctSchema = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(contextProvider.getCtx(), org.getAD_Client_ID(), org.getAD_Org_ID());

		final I_M_Product_Acct acctInfo =
				Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_Acct.class)
						.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchema.getC_AcctSchema_ID())
						.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
						.addOnlyActiveRecordsFilter()
						.setContext(contextProvider)
						.create()
						.firstOnly(I_M_Product_Acct.class);
		
		if (acctInfo == null)
		{
			return null;
		}

		final I_C_Activity activity = acctInfo.getC_Activity();
		return activity;
	}
}
