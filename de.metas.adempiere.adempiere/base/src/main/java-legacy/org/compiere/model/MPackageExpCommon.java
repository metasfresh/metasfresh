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
 * Contributor(s): ______________________________________.                    *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 *	Package Export Commons
 *	
 * @author Rob Klein
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1826279 ] MPackageExpCommon.afterSave: bad implementation
 */
public class MPackageExpCommon extends X_AD_Package_Exp_Common
{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5909037163754280398L;


	/**
	 * 	MPackageExpDetail
	 *	@param ctx
	 *	@param int
	 */
	public MPackageExpCommon (Properties ctx, int AD_Package_Exp_Common_ID, String trxName)
	{
		super(ctx, AD_Package_Exp_Common_ID, trxName);		
		
	}	//	MPackageExp

	/**
	 * 	MPackageExp
	 *	@param ctx
	 *	@param rs
	 */
	public MPackageExpCommon (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);		
		
	}	//	MPackageExp
	
	
	/* (non-Javadoc)
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (getLine() == 0) {
			String sql = "SELECT max(Line) FROM AD_Package_Exp_Common"
							+ " WHERE AD_Package_Exp_Common_ID<>?";
			int lineNo = DB.getSQLValue(get_TrxName(), sql, getAD_Package_Exp_Common_ID());
			if (lineNo >= 0)
				setLine(lineNo+10);
		}
		return true;
	}
}	//	MPackageExpCommon
