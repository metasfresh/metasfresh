package de.metas.product.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category_Acct;

/**
 * @author al
 */
public interface IProductAcctDAO extends ISingletonService
{
	/**
	 * Calls {@link IAcctSchemaDAO#retrieveAcctSchema(java.util.Properties, int, int)} to get the accounting schema for the given <code>org</code>. Then retrieves the
	 * {@link org.compiere.model.I_M_Product_Acct} for the given <code>product</code> and the found <code>C_AcctSchema</code> and returns that <code>I_M_Product_Acct</code>'s record.
	 * 
	 * 
	 * @param contextProvider
	 * @param org
	 * @param product
	 *
	 * @return activity for found {@link org.compiere.model.I_M_Product_Acct}
	 *
	 * @throws AdempiereException if more than one records are found
	 */
	I_C_Activity retrieveActivityForAcct(IContextAware contextProvider, I_AD_Org org, I_M_Product product) throws AdempiereException;

	I_M_Product_Acct retrieveProductAcctOrNull(Properties ctx, int acctSchemaId, int productId);

	I_M_Product_Acct retrieveProductAcctOrNull(I_C_AcctSchema acctSchema, int productId);

	I_M_Product_Acct retrieveProductAcctOrNull(I_M_Product product);

	/**
	 * 
	 * @param ctx
	 * @param acctSchemaId
	 * @return default product category accounting; never returns null
	 */
	I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(Properties ctx, int acctSchemaId);

	/**
	 * @param acctSchema
	 * @return default product category accounting; never returns null
	 */
	I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(I_C_AcctSchema acctSchema);
}
