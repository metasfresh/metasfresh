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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Project Phase Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProjectPhase.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MProjectPhase extends X_C_ProjectPhase
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445836323245259566L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_ProjectPhase_ID id
	 *	@param trxName transaction
	 */
	public MProjectPhase (Properties ctx, int C_ProjectPhase_ID, String trxName)
	{
		super (ctx, C_ProjectPhase_ID, trxName);
		if (C_ProjectPhase_ID == 0)
		{
		//	setC_ProjectPhase_ID (0);	//	PK
		//	setC_Project_ID (0);		//	Parent
		//	setC_Phase_ID (0);			//	FK
			setCommittedAmt (Env.ZERO);
			setIsCommitCeiling (false);
			setIsComplete (false);
			setSeqNo (0);
		//	setName (null);
			setQty (Env.ZERO);
		}
	}	//	MProjectPhase

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProjectPhase (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProjectPhase

	/**
	 * 	Parent Constructor
	 *	@param project parent
	 */
	public MProjectPhase (MProject project)
	{
		this (project.getCtx(), 0, project.get_TrxName());
		setClientOrg(project);
		setC_Project_ID(project.getC_Project_ID());
	}	//	MProjectPhase

	/**
	 * 	Copy Constructor
	 *	@param project parent
	 *	@param phase copy
	 */
	public MProjectPhase (MProject project, MProjectTypePhase phase)
	{
		this (project);
		//
		setC_Phase_ID (phase.getC_Phase_ID());			//	FK
		setName (phase.getName());
		setSeqNo (phase.getSeqNo());
		setDescription(phase.getDescription());
		setHelp(phase.getHelp());
		if (phase.getM_Product_ID() != 0)
			setM_Product_ID(phase.getM_Product_ID());
		setQty(phase.getStandardQty());
	}	//	MProjectPhase

	/**
	 * 	Get Project Phase Tasks.
	 *	@return Array of tasks
	 */
	public MProjectTask[] getTasks()
	{
		ArrayList<MProjectTask> list = new ArrayList<MProjectTask>();
		String sql = "SELECT * FROM C_ProjectTask WHERE C_ProjectPhase_ID=? ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_ProjectPhase_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MProjectTask (getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.log(Level.SEVERE, sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		MProjectTask[] retValue = new MProjectTask[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getTasks


	/**
	 * 	Copy Tasks from other Phase
	 *	@param fromPhase from phase
	 *	@return number of tasks copied
	 */
	public int copyTasksFrom (MProjectPhase fromPhase)
	{
		if (fromPhase == null)
			return 0;
		int count = 0;
		//
		MProjectTask[] myTasks = getTasks();
		MProjectTask[] fromTasks = fromPhase.getTasks();
		//	Copy Project Tasks
		for (int i = 0; i < fromTasks.length; i++)
		{
			//	Check if Task already exists
			int C_Task_ID = fromTasks[i].getC_Task_ID();
			boolean exists = false;
			if (C_Task_ID == 0)
				exists = false;
			else
			{
				for (int ii = 0; ii < myTasks.length; ii++)
				{
					if (myTasks[ii].getC_Task_ID() == C_Task_ID)
					{
						exists = true;
						break;
					}
				}
			}
			//	Phase exist
			if (exists)
				log.info("Task already exists here, ignored - " + fromTasks[i]);
			else
			{
				MProjectTask toTask = new MProjectTask (getCtx (), 0, get_TrxName());
				PO.copyValues (fromTasks[i], toTask, getAD_Client_ID (), getAD_Org_ID ());
				toTask.setC_ProjectPhase_ID (getC_ProjectPhase_ID ());
				if (toTask.save ())
					count++;
			}
		}
		if (fromTasks.length != count)
			log.warning("Count difference - ProjectPhase=" + fromTasks.length + " <> Saved=" + count);

		return count;
	}	//	copyTasksFrom

	/**
	 * 	Copy Tasks from other Phase
	 *	@param fromPhase from phase
	 *	@return number of tasks copied
	 */
	public int copyTasksFrom (MProjectTypePhase fromPhase)
	{
		if (fromPhase == null)
			return 0;
		int count = 0;
		//	Copy Type Tasks
		MProjectTypeTask[] fromTasks = fromPhase.getTasks();
		for (int i = 0; i < fromTasks.length; i++)
		{
			MProjectTask toTask = new MProjectTask (this, fromTasks[i]);
			if (toTask.save())
				count++;
		}
		log.fine("#" + count + " - " + fromPhase);
		if (fromTasks.length != count)
			log.log(Level.SEVERE, "Count difference - TypePhase=" + fromTasks.length + " <> Saved=" + count);

		return count;
	}	//	copyTasksFrom

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MProjectPhase[");
		sb.append (get_ID())
			.append ("-").append (getSeqNo())
			.append ("-").append (getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MProjectPhase
