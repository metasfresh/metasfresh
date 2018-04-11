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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * 	Performance Achievement
 *	
 *  @author Jorg Janke
 *  @version $Id: MAchievement.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *  red1 - [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MAchievement extends X_PA_Achievement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1438593600498523664L;

	/**
	 * 	Get achieved Achievements Of Measure
	 *	@param measure Measure
	 *	@return array of Achievements
	 */
	public static MAchievement[] get (MMeasure measure)
	{
		return getOfMeasure(measure.getCtx(), measure.getPA_Measure_ID());
	}	//	get

	/**
	 * 	Get achieved Achievements Of Measure
	 * 	@param ctx context
	 * 	@param PA_Measure_ID measure id
	 *	@return array of Achievements
	 */
	public static MAchievement[] getOfMeasure (Properties ctx, int PA_Measure_ID)
	{
		String whereClause ="PA_Measure_ID=? AND IsAchieved='Y'"; 
				List <MAchievement> list = new Query(ctx,MAchievement.Table_Name,  whereClause, null)
				.setParameters(new Object[]{PA_Measure_ID}).setOrderBy("SeqNo, DateDoc").list(MAchievement.class);
				
				MAchievement[] retValue = new MAchievement[list.size ()];
				retValue = list.toArray (retValue);
				return retValue;			
	 
	}	//	getOfMeasure


	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(MAchievement.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param PA_Achievement_ID id
	 *	@param trxName trx
	 */
	public MAchievement (Properties ctx, int PA_Achievement_ID, String trxName)
	{
		super (ctx, PA_Achievement_ID, trxName);
	}	//	MAchievement

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MAchievement (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MAchievement
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAchievement[");
		sb.append (get_ID()).append ("-").append (getName()).append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (isAchieved())
		{
			if (getManualActual().signum() == 0)
				setManualActual(Env.ONE);
			if (getDateDoc() == null)
				setDateDoc(new Timestamp(System.currentTimeMillis()));
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (success)
			updateAchievementGoals();
		return success;
	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (success)
			updateAchievementGoals();
		return success;
	}	//	afterDelete

	/**
	 * 	Update Goals with Achievement
	 */
	private void updateAchievementGoals()
	{
		MMeasure measure = MMeasure.get (getCtx(), getPA_Measure_ID());
		measure.updateGoals();
	}	//	updateAchievementGoals
	
}	//	MAchievement
