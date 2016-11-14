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


import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
 
/**
 *  Close Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectClose.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 *
 * @author Teo Sarca, wwww.arhipac.ro
 * 			<li>FR [ 2791635 ] Use saveEx whenever is possible
 * 				https://sourceforge.net/tracker/?func=detail&aid=2791635&group_id=176962&atid=879335
 */
public class ProjectClose extends SvrProcess
{
	/**	Project from Record			*/
	private int 		m_C_Project_ID = 0;

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
		m_C_Project_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (translated text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MProject project = new MProject (getCtx(), m_C_Project_ID, get_TrxName());
		log.info("doIt - " + project);

		MProjectLine[] projectLines = project.getLines();
		if (MProject.PROJECTCATEGORY_WorkOrderJob.equals(project.getProjectCategory())
			|| MProject.PROJECTCATEGORY_AssetProject.equals(project.getProjectCategory()))
		{
			/** @todo Check if we should close it */
		}

		//	Close lines
		for (int line = 0; line < projectLines.length; line++)
		{
			projectLines[line].setProcessed(true);
			projectLines[line].saveEx();
		}

		project.setProcessed(true);
		project.saveEx();

		return "";
	}	//	doIt

}	//	ProjectClose
