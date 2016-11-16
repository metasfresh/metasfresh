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
package org.compiere.process;

import java.math.BigDecimal;
import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	Delete Notes (Notice)
 *	
 *  @author Jorg Janke
 *  @version $Id: NoteDelete.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *  
 *  CarlosRuiz - globalqss
 *  [ 1639204 ] Delete Old Notes is deleting all notes
 *  Add parameter KeepLogDays
 */
public class NoteDelete extends SvrProcess
{
	private int		p_AD_User_ID = -1;
	
	private int		p_KeepLogDays = 0;
	
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
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("KeepLogDays"))
				p_KeepLogDays = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("doIt - AD_User_ID=" + p_AD_User_ID);
		
		String sql = "DELETE FROM AD_Note WHERE AD_Client_ID=" + getAD_Client_ID();
		if (p_AD_User_ID > 0)
			sql += " AND AD_User_ID=" + p_AD_User_ID;
		if (p_KeepLogDays > 0)
			sql += " AND (Created+" + p_KeepLogDays + ") < now()";
		//
		int no = DB.executeUpdate(sql, get_TrxName());
		return "@Deleted@ = " + no;
	}	//	doIt

}	//	NoteDelete
