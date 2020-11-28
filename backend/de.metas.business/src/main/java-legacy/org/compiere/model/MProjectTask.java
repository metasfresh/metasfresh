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

import org.compiere.util.Env;

/**
 * 	Project Phase Task Model
 *
 *	@author Jorg Janke
 *	@version $Id: MProjectTask.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProjectTask extends X_C_ProjectTask
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4031011454351572357L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_ProjectTask_ID id
	 *	@param trxName transaction
	 */
	public MProjectTask (Properties ctx, int C_ProjectTask_ID, String trxName)
	{
		super (ctx, C_ProjectTask_ID, trxName);
		if (C_ProjectTask_ID == 0)
		{
		//	setC_ProjectTask_ID (0);	//	PK
		//	setC_ProjectPhase_ID (0);	//	Parent
		//	setC_Task_ID (0);			//	FK
			setSeqNo (0);
		//	setName (null);
			setQty (Env.ZERO);
		}
	}	//	MProjectTask

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProjectTask (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProjectTask

	/**
	 * 	Parent Constructor
	 * 	@param phase parent
	 */
	public MProjectTask (MProjectPhase phase)
	{
		this (phase.getCtx(), 0, phase.get_TrxName());
		setClientOrg(phase);
		setC_ProjectPhase_ID(phase.getC_ProjectPhase_ID());
	}	//	MProjectTask

	/**
	 * 	Copy Constructor
	 *	@param phase parent
	 *	@param task type copy
	 */
	public MProjectTask (MProjectPhase phase, MProjectTypeTask task)
	{
		this (phase);
		//
		setC_Task_ID (task.getC_Task_ID());			//	FK
		setSeqNo (task.getSeqNo());
		setName (task.getName());
		setDescription(task.getDescription());
		setHelp(task.getHelp());
		if (task.getM_Product_ID() != 0)
			setM_Product_ID(task.getM_Product_ID());
		setQty(task.getStandardQty());
	}	//	MProjectTask

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MProjectTask[");
		sb.append (get_ID())
			.append ("-").append (getSeqNo())
			.append ("-").append (getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MProjectTask
