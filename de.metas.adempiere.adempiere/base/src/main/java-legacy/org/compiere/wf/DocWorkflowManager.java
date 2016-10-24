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
package org.compiere.wf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.util.Check;
import org.compiere.model.DocWorkflowMgr;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;


/**
 *	Document Workflow Manager
 *	
 *  @author Jorg Janke
 *  @version $Id: DocWorkflowManager.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class DocWorkflowManager implements DocWorkflowMgr
{
	/**
	 * 	Get Document Workflow Manager
	 *	@return mgr
	 */
	public static DocWorkflowManager get()
	{
		if (s_mgr == null)
			s_mgr = new DocWorkflowManager();
		return s_mgr;
	}	//	get

	//	Set PO Workflow Manager
	static {
		PO.setDocWorkflowMgr(get());
	}
	
	/**	Document Workflow Manager		*/
	private static DocWorkflowManager	s_mgr = null;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(DocWorkflowManager.class);
	
	
	/**
	 * 	Doc Workflow Manager
	 */
	private DocWorkflowManager ()
	{
		super ();
		if (s_mgr == null)
			s_mgr = this;
	}	//	DocWorkflowManager

	private int	m_noCalled = 0;
	private int	m_noStarted = 0;
	
	/**
	 * 	Process Document Value Workflow
	 *	@param document document
	 *	@param AD_Table_ID table
	 *	@return true if WF started
	 */
	@Override
	public boolean process (final PO document)
	{
		m_noCalled++;
		
		final int AD_Table_ID = document.get_Table_ID();
		MWorkflow[] wfs = MWorkflow.getDocValue (document.getCtx(), 
			document.getAD_Client_ID(), AD_Table_ID
			, document.get_TrxName() // Bug 1568766 Trx should be kept all along the road	
		);
		if (wfs == null || wfs.length == 0)
			return false;
		
		boolean started = false;
		for (final MWorkflow wf : wfs)
		{
			//	We have a Document Workflow
			final String logic = wf.getDocValueLogic();
			if(Check.isEmpty(logic, true))
			{
				log.error("Workflow has no Logic - {}", wf);
				continue;
			}
		
			//	Re-check: Document must be same Client as workflow
			if (wf.getAD_Client_ID() != document.getAD_Client_ID())
			{
				continue;
			}
		
			//	Check Logic
			final boolean sql = logic.startsWith("SQL=");
			if (sql && !testStart(wf, document))
			{
				log.debug("SQL Logic evaluated to false ({})", logic);
				continue;
			}
			if (!sql && !Evaluator.evaluateLogic(document, logic))
			{
				log.debug("Logic evaluated to false ({})", logic);
				continue;
			}
		
			//	Start Workflow
			log.debug(logic);
			final ProcessInfo pi = ProcessInfo.builder()
					.setAD_Process_ID(305) // FIXME HARDCODED
					.setTitle(wf.getName())
					.setRecord(AD_Table_ID, document.get_ID())
					.build();
			pi.setAD_User_ID (Env.getAD_User_ID(document.getCtx()));
			pi.setAD_Client_ID(document.getAD_Client_ID());
			//
			if (wf.start(pi) != null)
			{
				log.info("Workflow {} started for {}", wf, document);
				m_noStarted++;
				started = true;
			}
		}
		return started;
	}	//	process

	/**
	 * 	Test Start condition
	 *	@param wf workflow
	 *	@param document document
	 *	@return true if WF should be started
	 */
	private boolean testStart (MWorkflow wf, PO document)
	{
		boolean retValue = false;
		String logic = wf.getDocValueLogic();
		logic = logic.substring(4);		//	"SQL="
		//
		String tableName = document.get_TableName();
		String[] keyColumns = document.get_KeyColumns();
		if (keyColumns.length != 1)
		{
			log.error("Tables with more then one key column not supported - " 
				+ tableName + " = " + keyColumns.length);
			return false;
		}
		String keyColumn = keyColumns[0];
		StringBuffer sql = new StringBuffer("SELECT ")
			.append(keyColumn).append(" FROM ").append(tableName)
			.append(" WHERE AD_Client_ID=? AND ")		//	#1
				.append(keyColumn).append("=? AND ")	//	#2
			.append(logic)
		//	Duplicate Open Workflow test
			.append(" AND NOT EXISTS (SELECT * FROM AD_WF_Process wfp ")
				.append("WHERE wfp.AD_Table_ID=? AND wfp.Record_ID=")	//	#3
				.append(tableName).append(".").append(keyColumn)
				.append(" AND wfp.AD_Workflow_ID=?")	//	#4
				.append(" AND SUBSTR(wfp.WFState,1,1)='O')");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), document.get_TrxName());
			pstmt.setInt (1, wf.getAD_Client_ID());
			pstmt.setInt (2, document.get_ID());
			pstmt.setInt (3, document.get_Table_ID());
			pstmt.setInt (4, wf.getAD_Workflow_ID());
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = true;
		}
		catch (Exception e)
		{
			log.error("Logic=" + logic
				+ " - SQL=" + sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
		}

		return retValue;
	}	//	testStart
	
	
	/**
	* 	String Representation
	*	@return info
	*/
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("DocWorkflowManager[");
		sb.append("Called=").append(m_noCalled)
			.append(",Stated=").append(m_noStarted)
			.append("]");
		return sb.toString();
	}	//	toString
}	//	DocWorkflowManager
