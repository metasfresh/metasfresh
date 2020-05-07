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

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;


/**
 *	System Registration Model
 *	
 *  @author Jorg Janke
 *  @version $Id: M_Registration.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class M_Registration extends X_AD_Registration
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7480114737626722067L;


	/**
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param AD_Registration_ID id
	 *	@param trxName transaction
	 */
	public M_Registration (Properties ctx, int AD_Registration_ID, String trxName)
	{
		super (ctx, AD_Registration_ID, trxName);
		setAD_Client_ID(0);
		setAD_Org_ID(0);
		setAD_System_ID(0);
	}	//	M_Registration

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public M_Registration (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	M_Registration

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		final I_AD_System system = Services.get(ISystemBL.class).get(getCtx());
		
		if (system.getName().equals("?") || system.getUserName().equals("?"))
		{
			throw new AdempiereException("Define System first");
		}
		return true;
	}	//	beforeSave
	
	
}	//	M_Registration
