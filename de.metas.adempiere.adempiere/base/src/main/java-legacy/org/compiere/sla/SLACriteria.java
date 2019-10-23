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
import java.sql.Timestamp;

import org.compiere.model.MSLACriteria;
import org.compiere.model.MSLAGoal;

/**
 *	Service Level Agreement Criteria
 *	
 *  @author Jorg Janke
 *  @version $Id: SLACriteria.java,v 1.3 2006/07/30 00:51:06 jjanke Exp $
 */
public abstract class SLACriteria
{
	/**
	 * 	Create new Measures for the Goal
	 * 	@param goal the goal
	 * 	@return number created
	 */
	public abstract int createMeasures (MSLAGoal goal);

	/**
	 * 	Calculate Goal Actual from unprocessed Measures of the Goal
	 * 	@param goal the goal
	 * 	@return new Actual Measure
	 */
	public abstract BigDecimal calculateMeasure (MSLAGoal goal);


	/**
	 * 	Create new Measures for the Criteria
	 * 	@param criteria the criteria
	 * 	@return measures created
	 */
	public int createMeasures (MSLACriteria criteria)
	{
		int counter = 0;
		MSLAGoal[] goals = criteria.getGoals();
		for (int i = 0; i < goals.length; i++)
		{
			MSLAGoal goal = goals[i];
			if (goal.isActive())
				counter += createMeasures (goal);
		}
		return counter;
	}	//	createMeasures

	/**
	 * 	Calculate Goal Actual from unprocessed Measures of the Goal
	 * 	@param criteria SLA criteria
	 */
	public void calculateMeasures (MSLACriteria criteria)
	{
		MSLAGoal[] goals = criteria.getGoals();
		for (int i = 0; i < goals.length; i++)
		{
			MSLAGoal goal = goals[i];
			if (goal.isActive())
			{
				goal.setMeasureActual(calculateMeasure(goal));
				goal.setDateLastRun(new Timestamp(System.currentTimeMillis()));
				goal.save();
			}
		}
	}	//	calculateMeasures
	
}	//	SLACriteria
