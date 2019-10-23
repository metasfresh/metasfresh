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

/**
 * 	Performance Goal Restriction
 *	
 *  @author Jorg Janke
 *  @version $Id: MGoalRestriction.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MGoalRestriction extends X_PA_GoalRestriction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4027980875091517732L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param PA_GoalRestriction_ID id
	 *	@param trxName trx
	 */
	public MGoalRestriction (Properties ctx, int PA_GoalRestriction_ID,
		String trxName)
	{
		super (ctx, PA_GoalRestriction_ID, trxName);
	}	//	MGoalRestriction

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MGoalRestriction (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MGoalRestriction
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MGoalRestriction[");
		sb.append (get_ID()).append ("-").append (getName()).append ("]");
		return sb.toString ();
	}	//	toString
}	//	MGoalRestriction
