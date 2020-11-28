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

import java.sql.Timestamp;
import org.compiere.model.MSLACriteria;
import org.compiere.model.MSLAGoal;
import org.compiere.util.AdempiereUserError;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;


/**
 *	Service Level Agreement Goal.
 *	If automatic, capture measures - and calculate/update the actual measure.
 *	
 *  @author Jorg Janke
 *  @version $Id: SLAGoalProcess.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
public class SLAGoalProcess extends JavaProcess
{
	/** Goal					*/
	private int			p_PA_SLA_Goal_ID;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
		p_PA_SLA_Goal_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("PA_SLA_Goal_ID=" + p_PA_SLA_Goal_ID);
		MSLAGoal goal = new MSLAGoal(getCtx(), p_PA_SLA_Goal_ID, get_TrxName());
		if (goal.get_ID() == 0)
			throw new AdempiereUserError("@PA_SLA_Goal_ID@ " + p_PA_SLA_Goal_ID);

		MSLACriteria criteria = MSLACriteria.get(getCtx(), goal.getPA_SLA_Criteria_ID(), get_TrxName());
		if (criteria.get_ID() == 0)
			throw new AdempiereUserError("@PA_SLA_Criteria_ID@ " + goal.getPA_SLA_Criteria_ID());
		
		SLACriteria pgm = criteria.newSLACriteriaInstance();
		int no = pgm.createMeasures(goal);
		//
		goal.setMeasureActual(pgm.calculateMeasure(goal));
		goal.setDateLastRun(new Timestamp(System.currentTimeMillis()));
		goal.save();
		//
		return "@Created@ " + no + " - @MeasureActual@=" + goal.getMeasureActual();
	}	//	doIt

}	//	SLAGoalProcess
