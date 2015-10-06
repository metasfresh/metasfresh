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
package org.compiere.sla;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MInOut;
import org.compiere.model.MSLAGoal;
import org.compiere.model.MSLAMeasure;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	SLA Delivery Accuracy.
 *	How accurate is the promise date?
 *	<p>
 *	The measure are the average days between promise date (PO/SO) and delivery date 
 *	(Material receipt/shipment) It is positive if before, negative if later. 
 *	The lower the number, the better
 *	
 *  @author Jorg Janke
 *  @version $Id: DeliveryAccuracy.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
public class DeliveryAccuracy extends SLACriteria
{

	/**
	 * 	DeliveryAccuracy
	 */
	public DeliveryAccuracy ()
	{
		super ();
	}	//	DeliveryAccuracy
	
	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());
	
	
	/**
	 * 	Create new Measures for the Goal
	 * 	@param goal the goal
	 * 	@return number created
	 */
	public int createMeasures (MSLAGoal goal)
	{
		String sql = "SELECT M_InOut_ID, io.MovementDate-o.DatePromised," 	//	1..2
			+ " io.MovementDate, o.DatePromised, o.DocumentNo "
			+ "FROM M_InOut io"
			+ " INNER JOIN C_Order o ON (io.C_Order_ID=o.C_Order_ID) "
			+ "WHERE io.C_BPartner_ID=?"
			+ " AND NOT EXISTS "
				+ "(SELECT * FROM PA_SLA_Measure m "
				+ "WHERE m.PA_SLA_Goal_ID=?"
				+ " AND m.AD_Table_ID=" + MInOut.Table_ID
				+ " AND m.Record_ID=io.M_InOut_ID)";
		int counter = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, goal.getC_BPartner_ID());
			pstmt.setInt (2, goal.getPA_SLA_Goal_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				int M_InOut_ID = rs.getInt(1);
				BigDecimal MeasureActual = rs.getBigDecimal(2);
				Timestamp MovementDate = rs.getTimestamp(3);
				String Description = rs.getString(5) + ": " + rs.getTimestamp(4);
				if (goal.isDateValid(MovementDate))
				{
					MSLAMeasure measure = new MSLAMeasure(goal, MovementDate, 
						MeasureActual, Description);
					measure.setLink(MInOut.Table_ID, M_InOut_ID);
					if (measure.save())
						counter++;
				}
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "createMeasures", e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return counter;
	}	//	createMeasures

	
	/**************************************************************************
	 * 	Calculate Goal Actual from unprocessed Measures
	 *	@return goal actual measure
	 */
	public BigDecimal calculateMeasure (MSLAGoal goal)
	{
		//	Average
		BigDecimal retValue = Env.ZERO;
		BigDecimal total = Env.ZERO;
		int count = 0;
		//
		MSLAMeasure[] measures = goal.getAllMeasures();
		for (int i = 0; i < measures.length; i++)
		{
			MSLAMeasure measure = measures[i];
			if (!measure.isActive() 
				|| (goal.getValidFrom() != null && measure.getDateTrx().before(goal.getValidFrom()))
				|| (goal.getValidTo() != null && measure.getDateTrx().after(goal.getValidTo())))
				continue;
			//
			total = total.add(measure.getMeasureActual());
			count++;
			//
			if (!measure.isProcessed())
			{
				measure.setProcessed(true);
				measure.save();
			}
		}
		//	Goal Expired
		if (goal.getValidTo() != null 
			&& goal.getValidTo().after(new Timestamp(System.currentTimeMillis())))
			goal.setProcessed(true);
			
		//	Calculate with 2 digits precision
		if (count != 0)
			retValue = total.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
		return retValue;
	}	//	calculateMeasure

}	//	DeliveryAccuracy
