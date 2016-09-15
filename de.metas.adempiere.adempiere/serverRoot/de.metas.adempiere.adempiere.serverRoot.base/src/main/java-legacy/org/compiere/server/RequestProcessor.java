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
package org.compiere.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.StringTokenizer;

import org.compiere.model.MChangeRequest;
import org.compiere.model.MClient;
import org.compiere.model.MGroup;
import org.compiere.model.MRequest;
import org.compiere.model.MRequestProcessor;
import org.compiere.model.MRequestProcessorLog;
import org.compiere.model.MRequestProcessorRoute;
import org.compiere.model.MStatus;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;

/**
 *	Request Processor
 *	
 *  @author Jorg Janke
 *  @version $Id: RequestProcessor.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class RequestProcessor extends AdempiereServer
{
	/**
	 * 	RequestProcessor
	 *	@param model model
	 */
	public RequestProcessor (MRequestProcessor model)
	{
		super (model, 60);	//	1 minute delay
		m_model = model;
		m_client = MClient.get(model.getCtx(), model.getAD_Client_ID());
	}	//	RequestProcessor
	
	/**	The Concrete Model			*/
	private MRequestProcessor	m_model = null;
	/**	Last Summary				*/
	private StringBuffer 		m_summary = new StringBuffer();
	/** Client onfo					*/
	private MClient 			m_client = null;

	/**************************************************************************
	 * 	Do the actual Work
	 */
	@Override
	protected void doWork()
	{
		m_summary = new StringBuffer();
		//
		processEMail();
		findSalesRep ();
		processStatus();
		processRequests ();
		processECR();
		//
		int no = m_model.deleteLog();
		m_summary.append("Logs deleted=").append(no);
		//
		MRequestProcessorLog pLog = new MRequestProcessorLog(m_model, m_summary.toString());
		pLog.setReference("#" + String.valueOf(p_runCount) 
			+ " - " + TimeUtil.formatElapsed(new Timestamp(p_startWork)));
		pLog.save();
	}	//	doWork

	
	/**************************************************************************
	 *  Process requests.
	 *  Scheduled - are they due?
	 */
	private void processRequests ()
	{
		/**
		 *  Due Requests
		 */
		String sql = "SELECT * FROM R_Request "
			+ "WHERE DueType='" + MRequest.DUETYPE_Geplant + "' AND Processed='N'"
			+ " AND DateNextAction < now()"
			+ " AND AD_Client_ID=?"; 
		if (m_model.getR_RequestType_ID() != 0)
			sql += " AND R_RequestType_ID=?";
		PreparedStatement pstmt = null;
		int count = 0;
		int countEMails = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, m_model.getAD_Client_ID());
			if (m_model.getR_RequestType_ID() != 0)
				pstmt.setInt(2, m_model.getR_RequestType_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MRequest request = new MRequest (getCtx(), rs, null);
				request.setDueType();
				if (request.isDue())
				{
					if (request.getRequestType().isEMailWhenDue())
					{
						if (sendEmail (request, "RequestDue"))
						{
							request.setDateLastAlert();
							countEMails++;
						}
					}
					request.save();
					count++;
				}
			}
			rs.close ();
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}
		m_summary.append("New Due #").append(count);
		if (countEMails > 0)
			m_summary.append(" (").append(countEMails).append(" EMail)");
		m_summary.append (" - ");
		
		/**
		 *  Overdue Requests.
		 *  Due Requests - are they overdue?
		 */
		sql = "SELECT * FROM R_Request r "
			+ "WHERE r.DueType='" + MRequest.DUETYPE_Faellig + "' AND r.Processed='N'"
			+ " AND AD_Client_ID=?"
			+ " AND EXISTS (SELECT * FROM R_RequestType rt "
				+ "WHERE r.R_RequestType_ID=rt.R_RequestType_ID"
				+ " AND (r.DateNextAction+rt.DueDateTolerance) < now())";
		if (m_model.getR_RequestType_ID() != 0)
			sql += " AND r.R_RequestType_ID=?";
		count = 0;
		countEMails = 0;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, m_model.getAD_Client_ID());
			if (m_model.getR_RequestType_ID() != 0)
				pstmt.setInt(2, m_model.getR_RequestType_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MRequest request = new MRequest (getCtx(), rs, null);
				request.setDueType();
				if (request.isOverdue())
				{
					if (request.getRequestType().isEMailWhenOverdue()
						&& !TimeUtil.isSameDay(request.getDateLastAlert(), null))
					{
						if (sendEmail (request, "RequestDue"))
						{
							request.setDateLastAlert();
							countEMails++;
						}
					}
					request.save();
					count++;
				}
			}
			rs.close ();
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}
		m_summary.append("New Overdue #").append(count);
		if (countEMails > 0)
			m_summary.append(" (").append(countEMails).append(" EMail)");
		m_summary.append (" - ");
		
		/**
		 *  Send (over)due alerts
		 */
		if (m_model.getOverdueAlertDays() > 0)
		{
			sql = "SELECT * FROM R_Request "
				+ "WHERE Processed='N'"
				+ " AND AD_Client_ID=?"
				+ " AND (DateNextAction+" + m_model.getOverdueAlertDays() + ") < now()"
				+ " AND (DateLastAlert IS NULL";
			if (m_model.getRemindDays() > 0)
				sql += " OR (DateLastAlert+" + m_model.getRemindDays() 
					+ ") < now()";
			sql += ")";
			if (m_model.getR_RequestType_ID() != 0)
				sql += " AND R_RequestType_ID=?";
			count = 0;
			countEMails = 0;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_model.getAD_Client_ID());
				if (m_model.getR_RequestType_ID() != 0)
					pstmt.setInt(2, m_model.getR_RequestType_ID());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					MRequest request = new MRequest (getCtx(), rs, null);
					request.setDueType();
					if (request.getRequestType().isEMailWhenOverdue()
						&& (request.getDateLastAlert() == null
							|| !TimeUtil.isSameDay(request.getDateLastAlert(), null)))
					{
						if (sendEmail (request, "RequestAlert"))
						{
							request.setDateLastAlert();
							countEMails++;
						}
					}
					request.save();
					count++;
				}
				rs.close();
			}
			catch (SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(pstmt);
			}
			m_summary.append("Alerts #").append(count);
			if (countEMails > 0)
				m_summary.append(" (").append(countEMails).append(" EMail)");
			m_summary.append (" - ");
		}	//	Overdue
		
		/**
		 *  Escalate
		 */
		if (m_model.getOverdueAssignDays() > 0)
		{
			sql = "SELECT * FROM R_Request "
				+ "WHERE Processed='N'"
				+ " AND AD_Client_ID=?"
				+ " AND IsEscalated='N'"
				+ " AND (DateNextAction+" + m_model.getOverdueAssignDays() 
					+ ") < now()";
			if (m_model.getR_RequestType_ID() != 0)
				sql += " AND R_RequestType_ID=?";
			count = 0;
			countEMails = 0;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_model.getAD_Client_ID());
				if (m_model.getR_RequestType_ID() != 0)
					pstmt.setInt(2, m_model.getR_RequestType_ID());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					MRequest request = new MRequest (getCtx(), rs, null);
					if (escalate(request))
						count++;
				}
				rs.close();
			}
			catch (SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(pstmt);
			}
			m_summary.append("Escalated #").append(count).append(" - ");
		}	//	Esacalate
		
		/**
		 *  Send inactivity alerts
		 */
		if (m_model.getInactivityAlertDays() > 0)
		{
			sql = "SELECT * FROM R_Request "
				+ "WHERE Processed='N'"
				+ " AND AD_Client_ID=?"
				+ " AND (Updated+" + m_model.getInactivityAlertDays() + ") < now()"
				+ " AND (DateLastAlert IS NULL";
			if (m_model.getRemindDays() > 0)
				sql += " OR (DateLastAlert+" + m_model.getRemindDays() 
					+ ") < now()";
			sql += ")";
			if (m_model.getR_RequestType_ID() != 0)
				sql += " AND R_RequestType_ID=?";
			count = 0;
			countEMails = 0;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, m_model.getAD_Client_ID());
				if (m_model.getR_RequestType_ID() != 0)
					pstmt.setInt(2, m_model.getR_RequestType_ID());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next())
				{
					MRequest request = new MRequest (getCtx(), rs, null);
					request.setDueType();
					if (request.getDateLastAlert() == null
						|| !TimeUtil.isSameDay(request.getDateLastAlert(), null))
					{
						if (sendEmail (request, "RequestInactive"))
						{
							request.setDateLastAlert();
							countEMails++;
						}
						request.save();
						count++;
					}
				}
				rs.close();
			}
			catch (SQLException e)
			{
				log.error(sql, e);
			}
			finally
			{
				DB.close(pstmt);
			}
			m_summary.append("Inactivity #").append(count);
			if (countEMails > 0)
				m_summary.append(" (").append(countEMails).append(" EMail)");
			m_summary.append (" - ");
		}	//	Inactivity		
	}	//  processRequests

	/**
	 *  Send Alert EMail
	 *  @param request request
	 *  @param AD_Message message
	 *  @return true if sent
	 */
	private boolean sendEmail (MRequest request, String AD_Message)
	{
		//  Alert: Request {} overdue
		String subject = Msg.getMsg(m_client.getAD_Language(), AD_Message, 
			new String[] {request.getDocumentNo()});
		return m_client.sendEMail(request.getSalesRep_ID(), 
			subject, request.getSummary(), request.createPDF());
	}   //  sendAlert

	/**
	 *  Escalate
	 *  @param request request
	 * 	@return true if saved
	 */
	private boolean escalate (MRequest request)
	{
		//  Get Supervisor
		MUser supervisor = request.getSalesRep();	//	self
		int supervisor_ID = request.getSalesRep().getSupervisor_ID();
		if (supervisor_ID == 0 && m_model.getSupervisor_ID() != 0)
			supervisor_ID = m_model.getSupervisor_ID();
		if (supervisor_ID != 0 && supervisor_ID != request.getAD_User_ID())
			supervisor = MUser.get(getCtx(), supervisor_ID);
		
		//  Escalated: Request {} to {}
		String subject = Msg.getMsg(m_client.getAD_Language(), "RequestEscalate", 
			new String[] {request.getDocumentNo(), supervisor.getName()});
		String to = request.getSalesRep().getEMail();
		if (to == null || to.length() == 0)
			log.warn("SalesRep has no EMail - " + request.getSalesRep());
		else
			m_client.sendEMail(request.getSalesRep_ID(), 
				subject, request.getSummary(), request.createPDF());

		//	Not the same - send mail to supervisor
		if (request.getSalesRep_ID() != supervisor.getAD_User_ID())
		{
			to = supervisor.getEMail();
			if (to == null || to.length() == 0)
				log.warn("Supervisor has no EMail - " + supervisor);
			else
				m_client.sendEMail(supervisor.getAD_User_ID(), 
					subject, request.getSummary(), request.createPDF());
		}
		
		//  ----------------
		request.setDueType();
		request.setIsEscalated(true);
		request.setResult(subject);
		return request.save();
	}   //  escalate


	/**************************************************************************
	 * 	Process Request Status
	 */
	private void processStatus()
	{
		int count = 0;
		//	Requests with status with after timeout
		String sql = "SELECT * FROM R_Request r WHERE EXISTS ("
			+ "SELECT * FROM R_Status s "
			+ "WHERE r.R_Status_ID=s.R_Status_ID"
			+ " AND s.TimeoutDays > 0 AND s.Next_Status_ID > 0"
			+ " AND r.DateLastAction+s.TimeoutDays < now()"
			+ ") "
			+ "ORDER BY R_Status_ID";
		PreparedStatement pstmt = null;
		MStatus status = null;
		MStatus next = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MRequest r = new MRequest(getCtx(), rs, null);
				//	Get/Check Status
				if (status == null || status.getR_Status_ID() != r.getR_Status_ID())
					status = MStatus.get(getCtx(), r.getR_Status_ID());
				if (status.getTimeoutDays() <= 0
					|| status.getNext_Status_ID() == 0)
					continue;
				//	Next Status
				if (next == null || next.getR_Status_ID() != status.getNext_Status_ID())
					next = MStatus.get(getCtx(), status.getNext_Status_ID());
				//
				String result = Msg.getMsg(getCtx(), "RequestStatusTimeout")
					+ ": " + status.getName() + " -> " + next.getName();
				r.setResult(result);
				r.setR_Status_ID(status.getNext_Status_ID());
				if (r.save())
					count++;
			}
			rs.close ();
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}
		
		m_summary.append("Status Timeout #").append(count)
			.append(" - ");
	}	//	processStatus
	
	/**
	 * 	Create ECR
	 */
	private void processECR()
	{
		//	Get Requests with Request Type-AutoChangeRequest and Group with info
		String sql = "SELECT * FROM R_Request r "
			+ "WHERE M_ChangeRequest_ID IS NULL"
			+ " AND EXISTS ("
				+ "SELECT * FROM R_RequestType rt "
				+ "WHERE rt.R_RequestType_ID=r.R_RequestType_ID"
				+ " AND rt.IsAutoChangeRequest='Y')"
			+ "AND EXISTS ("
				+ "SELECT * FROM R_Group g "
				+ "WHERE g.R_Group_ID=r.R_Group_ID"
				+ " AND (g.M_BOM_ID IS NOT NULL OR g.M_ChangeNotice_ID IS NOT NULL)	)";
		//
		int count = 0;
		int failure = 0;
		//
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MRequest r = new MRequest (getCtx(), rs, null);
				MGroup rg = MGroup.get(getCtx(), r.getR_Group_ID());
				MChangeRequest ecr = new MChangeRequest (r, rg);
				if (r.save())
				{
					r.setM_ChangeRequest_ID(ecr.getM_ChangeRequest_ID());
					if (r.save())
						count++;
					else
						failure++;
				}
				else
					failure++;
			}
			rs.close ();
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(pstmt);
		}

		m_summary.append("Auto Change Request #").append(count);
		if (failure > 0)
			m_summary.append("(fail=").append(failure).append(")");
		m_summary.append(" - ");
	}	//	processECR
	
	
	/**************************************************************************
	 *	Create Reauest / Updates from EMail
	 */
	private void processEMail ()
	{
	//	m_summary.append("Mail #").append(count)
	//		.append(" - ");
	}   //  processEMail

	
	/**************************************************************************
	 * 	Allocate Sales Rep
	 */
	private void findSalesRep ()
	{
		int changed = 0;
		int notFound = 0;
		final Properties ctx = Env.newTemporaryCtx();
		//
		String sql = "SELECT * FROM R_Request "
			+ "WHERE AD_Client_ID=?"
			+ " AND SalesRep_ID=0 AND Processed='N'";
		if (m_model.getR_RequestType_ID() != 0)
			sql += " AND R_RequestType_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_model.getAD_Client_ID());
			if (m_model.getR_RequestType_ID() != 0)
				pstmt.setInt(2, m_model.getR_RequestType_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MRequest request = new MRequest (ctx, rs, null);
				if (request.getSalesRep_ID() != 0)
					continue;
				int SalesRep_ID = findSalesRep(request);
				if (SalesRep_ID != 0)
				{
					request.setSalesRep_ID(SalesRep_ID);
					request.save();
					changed++;
				}
				else
					notFound++;
			}
			rs.close();
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		finally
		{
			DB.close(pstmt);
		}
		pstmt = null;
		//
		if (changed == 0 && notFound == 0)
			m_summary.append("No unallocated Requests");
		else
			m_summary.append("Allocated SalesRep=").append(changed);
		if (notFound > 0)
			m_summary.append(",Not=").append(notFound);
		m_summary.append(" - ");
	}	//	findSalesRep

	/**
	 *  Find SalesRep/User based on Request Type and Question.
	 *  @param request request
	 *  @return SalesRep_ID user
	 */
	private int findSalesRep (MRequest request)
	{
		String QText = request.getSummary();
		if (QText == null)
			QText = "";
		else
			QText = QText.toUpperCase();
		//
		MRequestProcessorRoute[] routes = m_model.getRoutes(false);
		for (int i = 0; i < routes.length; i++)
		{
			MRequestProcessorRoute route = routes[i];
			
			//	Match first on Request Type
			if (request.getR_RequestType_ID() == route.getR_RequestType_ID()
				&& route.getR_RequestType_ID() != 0)
				return route.getAD_User_ID();
			
			//	Match on element of keyword
			String keyword = route.getKeyword();
			if (keyword != null)
			{
				StringTokenizer st = new StringTokenizer(keyword.toUpperCase(), " ,;\t\n\r\f");
				while (st.hasMoreElements())
				{
					if (QText.indexOf(st.nextToken()) != -1)
						return route.getAD_User_ID();
				}
			}
		}	//	for all routes

		return m_model.getSupervisor_ID();
	}   //  findSalesRep
	
	/**
	 * 	Get Server Info
	 *	@return info
	 */
	@Override
	public String getServerInfo()
	{
		return "#" + p_runCount + " - Last=" + m_summary.toString();
	}	//	getServerInfo
	
}	//	RequestProcessor
