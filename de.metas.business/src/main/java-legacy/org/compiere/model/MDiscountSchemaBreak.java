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

/**
 * Discount Break Schema (Model)
 *
 * @author Jorg Janke
 * @version $Id: MDiscountSchemaBreak.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 2012439 ] DiscountSchemaBreak: setting product & category is allowed
 */
public class MDiscountSchemaBreak extends X_M_DiscountSchemaBreak
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6413962940750128351L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_DiscountSchemaBreak_ID id
	 * @param trxName transaction
	 */
	public MDiscountSchemaBreak(Properties ctx, int M_DiscountSchemaBreak_ID, String trxName)
	{
		super(ctx, M_DiscountSchemaBreak_ID, trxName);
	}	// MDiscountSchemaBreak

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDiscountSchemaBreak(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDiscountSchemaBreak

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// If M_Product_ID and M_Product_Category_ID is set, M_Product_ID has priority - teo_sarca [ 2012439 ]
		if (getM_Product_ID() > 0 && getM_Product_Category_ID() > 0)
			setM_Product_Category_ID(I_ZERO);
		//
		return true;
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MDiscountSchemaBreak[");
		sb.append(get_ID()).append("-Seq=").append(getSeqNo());
		if (getM_Product_Category_ID() != 0)
			sb.append(",M_Product_Category_ID=").append(getM_Product_Category_ID());
		if (getM_Product_ID() != 0)
			sb.append(",M_Product_ID=").append(getM_Product_ID());
		sb.append(",Break=").append(getBreakValue());
		if (isBPartnerFlatDiscount())
			sb.append(",FlatDiscount");
		else
			sb.append(",Discount=").append(getBreakDiscount());
		sb.append("]");
		return sb.toString();
	}	// toString

}	// MDiscountSchemaBreak
