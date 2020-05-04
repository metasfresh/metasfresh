/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Services;

/**
 * Expense Type Model
 * 
 * @author Jorg Janke
 * @version $Id: MExpenseType.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MExpenseType extends X_S_ExpenseType
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5721855125106737886L;

	/**
	 * Default Constructor
	 * 
	 * @param ctx context
	 * @param S_ExpenseType_ID id
	 * @param trxName transaction
	 */
	public MExpenseType(Properties ctx, int S_ExpenseType_ID, String trxName)
	{
		super(ctx, S_ExpenseType_ID, trxName);
	}	// MExpenseType

	/**
	 * MExpenseType
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MExpenseType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MExpenseType

	/** Cached Product */
	private I_M_Product product = null;

	/**
	 * Get Product
	 * 
	 * @return product
	 */
	private I_M_Product getProduct()
	{
		if (product == null)
		{
			product = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_Product.class, getCtx(), get_TrxName())
					.addEqualsFilter(I_M_Product.COLUMN_S_ExpenseType_ID, getS_ExpenseType_ID())
					.create()
					.firstOnly(I_M_Product.class);
		}
		return product;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord)
		{
			if (getValue() == null || getValue().length() == 0)
			{
				setValue(getName());
			}
			
			product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product.setProductType(X_M_Product.PRODUCTTYPE_ExpenseType);
			updateProductFromExpenseType(product, this);
			InterfaceWrapperHelper.save(product);
		}
		return true;
	}	// beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;

		final I_M_Product product = getProduct();
		if(updateProductFromExpenseType(product, this))
		{
			InterfaceWrapperHelper.save(product);
		}
		
		return success;
	}

	/**
	 * Set Expense Type
	 *
	 * @param parent expense type
	 * @return true if changed
	 */
	private static boolean updateProductFromExpenseType(final I_M_Product product, final I_S_ExpenseType parent)
	{
		boolean changed = false;
		if (!X_M_Product.PRODUCTTYPE_ExpenseType.equals(product.getProductType()))
		{
			product.setProductType(X_M_Product.PRODUCTTYPE_ExpenseType);
			changed = true;
		}
		if (parent.getS_ExpenseType_ID() != product.getS_ExpenseType_ID())
		{
			product.setS_ExpenseType_ID(parent.getS_ExpenseType_ID());
			changed = true;
		}
		if (parent.isActive() != product.isActive())
		{
			product.setIsActive(parent.isActive());
			changed = true;
		}
		//
		if (!parent.getValue().equals(product.getValue()))
		{
			product.setValue(parent.getValue());
			changed = true;
		}
		if (!parent.getName().equals(product.getName()))
		{
			product.setName(parent.getName());
			changed = true;
		}
		if ((parent.getDescription() == null && product.getDescription() != null)
				|| (parent.getDescription() != null && !parent.getDescription().equals(product.getDescription())))
		{
			product.setDescription(parent.getDescription());
			changed = true;
		}
		if (parent.getC_UOM_ID() != product.getC_UOM_ID())
		{
			product.setC_UOM_ID(parent.getC_UOM_ID());
			changed = true;
		}
		if (parent.getM_Product_Category_ID() != product.getM_Product_Category_ID())
		{
			product.setM_Product_Category_ID(parent.getM_Product_Category_ID());
			changed = true;
		}

		// metas 05129 end
		return changed;
	}

}	// MExpenseType
