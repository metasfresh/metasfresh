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
import java.util.stream.Collectors;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

/**
 * Cost Element Model
 *
 * @author Jorg Janke
 * @version $Id: MCostElement.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2664529 ] More then one Labor/Burden//Overhead is not allowed
 *         <li>BF [ 2667470 ] MCostElement.getMaterialCostElement should check only material
 */
public class MCostElement extends X_M_CostElement
{
	private static final long serialVersionUID = 8676787942212800906L;

	public MCostElement(Properties ctx, int M_CostElement_ID, String trxName)
	{
		super(ctx, M_CostElement_ID, trxName);
		if (is_new())
		{
			// setName (null);
			setCostElementType(COSTELEMENTTYPE_Material);
			setIsCalculated(false);
		}
	}

	public MCostElement(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Check Unique Costing Method
		if ((COSTELEMENTTYPE_Material.equals(getCostElementType())
				// || COSTELEMENTTYPE_Resource.equals(getCostElementType())
				// || COSTELEMENTTYPE_BurdenMOverhead.equals(getCostElementType())
				// || COSTELEMENTTYPE_Overhead.equals(getCostElementType())
				|| COSTELEMENTTYPE_OutsideProcessing.equals(getCostElementType()))
				&& (newRecord || is_ValueChanged(COLUMNNAME_CostingMethod)))
		{
			final boolean costingMethodAlreadyExists = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_CostElement.class)
					.addNotEqualsFilter(I_M_CostElement.COLUMN_M_CostElement_ID, getM_CostElement_ID())
					.addEqualsFilter(I_M_CostElement.COLUMN_AD_Client_ID, getAD_Client_ID())
					.addEqualsFilter(I_M_CostElement.COLUMN_CostingMethod, getCostingMethod())
					.addEqualsFilter(I_M_CostElement.COLUMN_CostElementType, getCostElementType())
					.create()
					.match();
			if (costingMethodAlreadyExists)
			{
				throw new AdempiereException("@AlreadyExists@ @CostingMethod@");
			}
		}

		// Maintain Calclated
		/*
		 * if (COSTELEMENTTYPE_Material.equals(getCostElementType()))
		 * {
		 * String cm = getCostingMethod();
		 * if (cm == null || cm.length() == 0
		 * || COSTINGMETHOD_StandardCosting.equals(cm))
		 * setIsCalculated(false);
		 * else
		 * setIsCalculated(true);
		 * }
		 * else
		 * {
		 * if (isCalculated())
		 * setIsCalculated(false);
		 * if (getCostingMethod() != null)
		 * setCostingMethod(null);
		 * }
		 */

		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		return true;
	}	// beforeSave

	@Override
	protected boolean beforeDelete()
	{
		if (!isCostingMethod())
		{
			return true;
		}

		// Costing Methods on AS level
		for (final I_C_AcctSchema as : Services.get(IAcctSchemaDAO.class).retrieveClientAcctSchemas(getCtx(), getAD_Client_ID()))
		{
			if (as.getCostingMethod().equals(getCostingMethod()))
			{
				throw new AdempiereException("@CannotDeleteUsed@ @C_AcctSchema_ID@");
			}
		}

		// Costing Methods on PC level
		final String productCategoriesUsingCostingMethod = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Category_Acct.class)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMN_AD_Client_ID, getAD_Client_ID())
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMN_CostingMethod, getCostingMethod())
				.andCollect(I_M_Product_Category_Acct.COLUMN_M_Product_Category_ID)
				.orderBy(I_M_Product_Category.COLUMN_Name)
				.create()
				.setLimit(50)
				.listDistinct(I_M_Product_Category.COLUMNNAME_Name, String.class)
				.stream()
				.collect(Collectors.joining(", "));
		if (!Check.isEmpty(productCategoriesUsingCostingMethod, true))
		{
			throw new AdempiereException("@CannotDeleteUsed@ @M_Product_Category_ID@ (" + productCategoriesUsingCostingMethod + ")");
		}

		return true;
	}

	/**
	 * @return true if not Material cost or no costing method.
	 */
	private boolean isCostingMethod()
	{
		return COSTELEMENTTYPE_Material.equals(getCostElementType())
				&& getCostingMethod() != null;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MCostElement[");
		sb.append(get_ID())
				.append("-").append(getName())
				.append(",Type=").append(getCostElementType())
				.append(",Method=").append(getCostingMethod())
				.append("]");
		return sb.toString();
	}

}
