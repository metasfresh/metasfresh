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

import java.util.Properties;

/**
 *	Product Category Callouts
 *	
 *  @author Karsten Thiemann kthiemann@adempiere.org
 *  
 */
public class CalloutProductCategory extends CalloutEngine
{
	/**
	 *	Loop detection of product category tree.
	 *
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return "" or error message
	 */
	public  String testForLoop (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (isCalloutActive() || value == null)
			return "";

		Integer productCategoryId = (Integer) mTab.getValue(MProductCategory.COLUMNNAME_M_Product_Category_ID);
		if (productCategoryId == null)
			productCategoryId = new Integer(0);
		
		if (productCategoryId.intValue() > 0) {
			MProductCategory pc = new MProductCategory(ctx, productCategoryId.intValue(), null);
			pc.setM_Product_Category_Parent_ID(((Integer) value).intValue());
			if (pc.hasLoopInTree())
				return "ProductCategoryLoopDetected";
		}
		
		return "";
	}	//	testForLoop
	
}	//	CalloutProductCategory