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

import org.adempiere.exceptions.FillMandatoryException;

/**
 *	Location City Model (Value Object)
 *
 *  @author 	Mario Calderon / Carlos Ruiz
 */
public final class MCity extends X_C_City
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5438111614429419874L;

	public MCity (Properties ctx, int C_City_ID, String trxName)
	{
		super (ctx, C_City_ID, trxName);
		if (C_City_ID == 0)
		{
		}
	}   //  MCity
	
	public MCity (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCity
	
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if(getC_Region_ID() > 0 && getC_Country_ID() <= 0)
		{
			setC_Country_ID(getC_Region().getC_Country_ID());
		}
		if(getC_Country_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_City.COLUMNNAME_C_Country_ID);
		}
		
		return true;
	}

}	//	MCity
