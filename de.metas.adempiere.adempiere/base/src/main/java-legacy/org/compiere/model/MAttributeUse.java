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

import org.compiere.util.DB;


/**
 *	Attribute Use Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MAttributeUse.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAttributeUse extends X_M_AttributeUse
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3727204159034073907L;


	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MAttributeUse (Properties ctx, int ignored, String trxName)
	{
		super (ctx, ignored, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MAttributeUse

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttributeUse (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttributeUse

	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		//	also used for afterDelete
		String sql = "UPDATE M_AttributeSet mas"
			+ " SET IsInstanceAttribute='Y' "
			+ "WHERE M_AttributeSet_ID=" + getM_AttributeSet_ID()
			+ " AND IsInstanceAttribute='N'"
			+ " AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y'"
				+ " OR EXISTS (SELECT * FROM M_AttributeUse mau"
					+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
					+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
					+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
					+ " AND ma.IsInstanceAttribute='Y')"
					+ ")";
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.fine("afterSave - Set Instance Attribute");
		//
		sql = "UPDATE M_AttributeSet mas"
			+ " SET IsInstanceAttribute='N' "
			+ "WHERE M_AttributeSet_ID=" + getM_AttributeSet_ID()
			+ " AND IsInstanceAttribute='Y'"
			+ "	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N'"
			+ " AND NOT EXISTS (SELECT * FROM M_AttributeUse mau"
				+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
				+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
				+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
				+ " AND ma.IsInstanceAttribute='Y')";
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
			log.fine("afterSave - Reset Instance Attribute");
		
		return success;
	}	//	afterSave
	
	
	/**
	 * 	After Delete
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterDelete (boolean success)
	{
		afterSave(false, success);
		return success;
	}	//	afterDelete
	
}	//	MAttributeUse
