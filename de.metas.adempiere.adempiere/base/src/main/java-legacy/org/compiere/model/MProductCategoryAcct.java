/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCache;

/**
 * 	Product Category Account Model
 *  @author Jorg Janke
 *  @version $Id: MProductCategoryAcct.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProductCategoryAcct extends X_M_Product_Category_Acct
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2075372131034904732L;
	/** Static cache */
	private static CCache<String, I_M_Product_Category_Acct> s_cache = new CCache<>(Table_Name, 40, 5);
	
	
	/**
	 * 	Get Category Acct. If there aren't any records at all, the system creates them the way it does for a newly create product category.
	 *	@param ctx context
	 *	@param M_Product_Category_ID category
	 *	@param C_AcctSchema_ID acct schema
	 *	@param trxName trx
	 *	@return category acct
	 */
	public static I_M_Product_Category_Acct get (Properties ctx, int M_Product_Category_ID, int C_AcctSchema_ID, String trxName)
	{
		final boolean createIfNotExists = true;
		return get(ctx, M_Product_Category_ID, C_AcctSchema_ID, createIfNotExists, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param M_Product_Category_ID
	 * @param C_AcctSchema_ID
	 * @param createIfNotExists if <code>true</code> and there aren't any Acct records yet, the system automatically creates them the way it does for a newly create product category.
	 * @param trxName
	 * @return
	 */
	private static I_M_Product_Category_Acct get (final Properties ctx, 
							final int M_Product_Category_ID,
							final int C_AcctSchema_ID, final boolean createIfNotExists,
							final String trxName)
	{
		final String key = M_Product_Category_ID+"#"+C_AcctSchema_ID;
		return s_cache.get(key, new Callable<I_M_Product_Category_Acct>()
		{
			
			@Override
			public I_M_Product_Category_Acct call()
			{
				return retriveCreateAcct(ctx, M_Product_Category_ID, C_AcctSchema_ID, createIfNotExists, trxName);
			}
		});
	}
	
	private static final I_M_Product_Category_Acct retriveCreateAcct(final Properties ctx,
			final int M_Product_Category_ID,
			final int C_AcctSchema_ID, final boolean createIfNotExists,
			final String trxName)
	{
		// NOTE: because we currently have some bugs with "ctx" on server side, we are not filtering the query by AD_Client_ID
		// but we just rely on M_Product_Category_ID/C_AcctSchema_ID.
		
		final IQuery<I_M_Product_Category_Acct> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Category_Acct.class, ctx, trxName)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID)
				.addOnlyActiveRecordsFilter()
				.create();
		I_M_Product_Category_Acct productCategoryAcct = query.firstOnly(I_M_Product_Category_Acct.class);

		//
		// If no product category accounting record was not found and we are asked to create one on fly
		// We are asing the MProductCategory to create all that are missing
		if (productCategoryAcct == null && createIfNotExists)
		{
			final I_M_Product_Category pc = InterfaceWrapperHelper.create(ctx, M_Product_Category_ID, I_M_Product_Category.class, trxName);
			InterfaceWrapperHelper.getPO(pc).insert_Accounting(
					I_M_Product_Category_Acct.Table_Name,
					I_C_AcctSchema_Default.Table_Name,
					(String)null // whereClause
					);

			// Run the query again => we expect our product category acct record to be created
			productCategoryAcct = query.firstOnly(I_M_Product_Category_Acct.class);
			Check.assumeNotNull(productCategoryAcct, "productCategoryAcct not null");
		}

		return productCategoryAcct;
	}
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param ignored ignored 
	 *	@param trxName
	 */
	public MProductCategoryAcct (Properties ctx, int ignored, String trxName)
	{
		super (ctx, ignored, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MProductCategoryAcct

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MProductCategoryAcct (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MProductCategoryAcct
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder ("MProductCategoryAcct[");
		sb.append (get_ID())
			.append (",M_Product_Category_ID=").append (getM_Product_Category_ID())
			.append (",C_AcctSchema_ID=").append(getC_AcctSchema_ID())
			.append (",CostingLevel=").append(getCostingLevel())
			.append (",CostingMethod=").append(getCostingMethod())
			.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MProductCategoryAcct
