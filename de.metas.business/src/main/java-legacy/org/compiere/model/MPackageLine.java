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
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	Package Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MPackageLine.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MPackageLine extends X_M_PackageLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6018805803189654348L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_PackageLine_ID id
	 *	@param trxName transaction
	 */
	public MPackageLine (Properties ctx, int M_PackageLine_ID, String trxName)
	{
		super (ctx, M_PackageLine_ID, trxName);
		if (M_PackageLine_ID == 0)
		{
		//	setM_Package_ID (0);
		//	setM_InOutLine_ID (0);
			setQty (Env.ZERO);
		}
	}	//	MPackageLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPackageLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPackageLine
	
	/**
	 * 	Parent Constructor
	 *	@param parent header
	 */
	public MPackageLine (MPackage parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setM_Package_ID(parent.getM_Package_ID());
	}	//	MPackageLine
	
	/**
	 * 	Set Shipment Line
	 *	@param line line
	 */
	public void setInOutLine (MInOutLine line)
	{
		setM_InOutLine_ID (line.getM_InOutLine_ID());
		setQty (line.getMovementQty());
	}	//	setInOutLine
	
	/**
	 * get package line
	 * 
	 * @param ctx
	 *            context
	 * @param M_Package_ID
	 * @param trxName
	 *            transaction
	 * @return array of package lines
	 */
	public static List<MPackageLine> get(Properties ctx, int M_Package_ID, String trxName)
	{
		String whereClause = COLUMNNAME_M_Package_ID + "= ? ";
		return new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(M_Package_ID)
				.list(MPackageLine.class);
	} //	get
	
}	//	MPackageLine
