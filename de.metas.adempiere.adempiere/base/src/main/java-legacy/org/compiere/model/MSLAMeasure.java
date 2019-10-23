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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	Service Level Agreement Measure
 *	
 *  @author Jorg Janke
 *  @version $Id: MSLAMeasure.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MSLAMeasure extends X_PA_SLA_Measure
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844941499854811255L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param PA_SLA_Measure_ID id
	 *	@param trxName transaction
	 */
	public MSLAMeasure (Properties ctx, int PA_SLA_Measure_ID, String trxName)
	{
		super (ctx, PA_SLA_Measure_ID, trxName);
		if (PA_SLA_Measure_ID == 0)
		{
		//	setPA_SLA_Goal_ID (0);
			setDateTrx (new Timestamp(System.currentTimeMillis()));
			setMeasureActual (Env.ZERO);
			setProcessed (false);
		}
	}	//	MSLAMeasure

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSLAMeasure (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MSLAMeasure

	/**
	 * 	Parent Constructor
	 *	@param goal parent
	 *	@param DateTrx optional date
	 *	@param MeasureActual optional measure
	 *	@param Description optional description
	 */
	public MSLAMeasure (MSLAGoal goal, Timestamp DateTrx, BigDecimal MeasureActual,
		String Description)
	{
		super (goal.getCtx(), 0, goal.get_TrxName());
		setClientOrg(goal);
		setPA_SLA_Goal_ID(goal.getPA_SLA_Goal_ID());
		if (DateTrx != null)
			setDateTrx (DateTrx);
		else
			setDateTrx (new Timestamp(System.currentTimeMillis()));
		if (MeasureActual != null)
			setMeasureActual(MeasureActual);
		else
			setMeasureActual (Env.ZERO);
		if (Description != null)
			setDescription(Description);
	}	//	MSLAMeasure
	
	/**
	 * 	Set Link to Source
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 */
	public void setLink (int AD_Table_ID, int Record_ID)
	{
		setAD_Table_ID(AD_Table_ID);
		setRecord_ID(Record_ID);
	}	//	setLink
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MSLAMeasure[");
		sb.append(get_ID()).append("-PA_SLA_Goal_ID=").append(getPA_SLA_Goal_ID())
			.append(",").append(getDateTrx())
			.append(",Actual=").append(getMeasureActual())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MSLAMeasure
