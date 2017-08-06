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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.LogRecord;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Secure;
import org.compiere.util.Util;

/**
 * 	Issue Report Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MIssue.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MIssue extends X_AD_Issue
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3680542992654002121L;
	
	/** Answer Delimiter		*/
	public static String	DELIMITER = "|";
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Issue_ID issue
	 *	@param trxName transaction
	 */
	public MIssue (Properties ctx, int AD_Issue_ID, String trxName)
	{
		super (ctx, AD_Issue_ID, trxName);
		if (AD_Issue_ID == 0)
		{
			setProcessed (false);	// N
			setSystemStatus(SYSTEMSTATUS_Evaluation);
			try
			{
				init(ctx);
			}
			catch (Exception e)
			{
				e.getStackTrace();
			}
		}
	}	//	MIssue

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MIssue (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MIssue
	
	/**
	 * 	Log Record Constructor
	 *	@param record
	 */
	@Deprecated
	public MIssue(final LogRecord record)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_None);
		String summary = record.getMessage();
		setSourceClassName(record.getSourceClassName());
		setSourceMethodName(record.getSourceMethodName());
		setLoggerName(record.getLoggerName());

		final Throwable t = record.getThrown();
		if (t != null)
		{
			//
			// Summary
			if (summary != null && summary.length() > 0)
				summary = t.toString() + " " + summary;
			if (summary == null || summary.length() == 0)
				summary = t.toString();

			//
			// ErrorTrace
			final StringBuilder errorTrace = new StringBuilder();
			final StackTraceElement[] tes = t.getStackTrace();
			int count = 0;
			for (int i = 0; i < tes.length; i++)
			{
				final StackTraceElement element = tes[i];
				String s = element.toString();
				if (s.indexOf("adempiere") != -1)
				{
					errorTrace.append(s).append("\n");
					if (count == 0)
					{
						final String source = element.getClassName() + "." + element.getMethodName();
						setSourceClassName(source);
						setLineNo(element.getLineNumber());
					}
					count++;
				}
				if (count > 5 || errorTrace.length() > 2000)
				{
					break;
				}
			}
			setErrorTrace(errorTrace.toString());

			//
			// StackTrace
			final String stackTrace = Util.dumpStackTraceToString(t);
			setStackTrace(stackTrace);
		}

		if (summary == null || summary.isEmpty())
		{
			summary = "??";
		}

		setIssueSummary(summary);
		setRecord_ID(1); // just to have something there because it's mandatory
	}	//	MIssue

	/**
	 * 	Initialize
	 * 	@param ctx context
	 * 	@throws Exception
	 */
	private void init(Properties ctx) throws Exception
	{
		final ISystemBL systemBL = Services.get(ISystemBL.class);
		final I_AD_System system = systemBL.get(ctx);

		setName(system.getName());
		setUserName(system.getUserName());
		setDBAddress(system.getDBAddress());
		setSystemStatus(system.getSystemStatus());
		setReleaseNo(system.getReleaseNo());	//	DB
		
		String dateVersion = Adempiere.getDateVersion();
		if (Check.isEmpty(dateVersion, true))
		{
			dateVersion = "?";
		}
		setVersion(dateVersion);		// Code
		
		setDatabaseInfo(DB.getDatabaseInfo());
		setOperatingSystemInfo(Adempiere.getOSInfo());
		setJavaInfo(Adempiere.getJavaInfo());
		setReleaseTag(Adempiere.getImplementationVersion());
		setLocal_Host(NetUtils.getLocalHost().toString());
		if (system.isAllowStatistics())
		{
			// NOTE: there is no need to recalculate how many tenants, bpartners, invoices etc are in the system.
			// An aproximative information is also acceptable.
			// Also counting all those infos could be quite expensive.
			final boolean recalc = false;
			setStatisticsInfo(systemBL.getStatisticsInfo(recalc));
			setProfileInfo(systemBL.getProfileInfo(recalc));
		}
	}	//	init
	
	/** Length of Info Fields			*/
	private static final int	INFOLENGTH = 2000;
	
	/**
	 * 	Set Issue Summary.
	 * 	Truncate it to 2000 char
	 *	@param IssueSummary summary
	 */
	@Override
	public void setIssueSummary (String IssueSummary)
	{
		if (IssueSummary == null)
			return;
		IssueSummary = IssueSummary.replace("java.lang.", "");
		IssueSummary = IssueSummary.replace("java.sql.", "");
		if (IssueSummary.length() > INFOLENGTH)
			IssueSummary = IssueSummary.substring(0,INFOLENGTH-1);
		super.setIssueSummary (IssueSummary);
	}	//	setIssueSummary
	
	/**
	 * 	Set Stack Trace.
	 * 	Truncate it to 2000 char
	 *	@param StackTrace trace
	 */
	@Override
	public void setStackTrace (String StackTrace)
	{
		if (StackTrace == null)
			return;
		StackTrace = StackTrace.replace("java.lang.", "");
		StackTrace = StackTrace.replace("java.sql.", "");

		// Truncate the stacktrace if necessary
		final int maxLength = getPOInfo().getFieldLength(COLUMNNAME_StackTrace);
		if (maxLength > 0 && StackTrace.length() > maxLength)
			StackTrace = StackTrace.substring(0, maxLength - 1);
		
		super.setStackTrace(StackTrace);
	}	//	setStackTrace
	
	
	/**
	 * 	Set Error Trace.
	 * 	Truncate it to 2000 char
	 *	@param ErrorTrace trace
	 */
	@Override
	public void setErrorTrace (String ErrorTrace)
	{
		if (ErrorTrace == null)
			return;
		ErrorTrace = ErrorTrace.replace("java.lang.", "");
		ErrorTrace = ErrorTrace.replace("java.sql.", "");
		if (ErrorTrace.length() > INFOLENGTH)
			ErrorTrace = ErrorTrace.substring(0,INFOLENGTH-1);
		super.setErrorTrace (ErrorTrace);
	}	//	setErrorTrace

	/**
	 * 	Add Comments
	 *	@param Comments
	 */
	public void addComments (String Comments)
	{
		if (Comments == null || Comments.length() == 0)
			return;
		String old = getComments();
		if (old == null || old.length() == 0)
			setComments (Comments);
		else if (!old.equals(Comments) 
			&& old.indexOf(Comments) == -1)		//	 something new
			setComments (Comments + " | " + old);
	}	//	addComments
	
	/**
	 * 	Set Comments.
	 * 	Truncate it to 2000 char
	 *	@param Comments
	 */
	@Override
	public void setComments (String Comments)
	{
		if (Comments == null)
			return;
		if (Comments.length() > INFOLENGTH)
			Comments = Comments.substring(0,INFOLENGTH-1);
		super.setComments (Comments);
	}	//	setComments
	
	/**
	 * 	Set ResponseText.
	 * 	Truncate it to 2000 char
	 *	@param ResponseText
	 */
	@Override
	public void setResponseText (String ResponseText)
	{
		if (ResponseText == null)
			return;
		if (ResponseText.length() > INFOLENGTH)
			ResponseText = ResponseText.substring(0,INFOLENGTH-1);
		super.setResponseText(ResponseText);
	}	//	setResponseText

	/**
	 * 	Process Request.
	 * 	@return answer
	 */
	public String process()
	{
		MIssueProject.get(this);	//	sets also Asset
		MIssueSystem.get(this);
		MIssueUser.get(this);
		//
	//	setR_IssueKnown_ID(0);
	//	setR_Request_ID(0);
		return createAnswer();
	}	//	process
	
	/**
	 * 	Create Answer to send to User
	 *	@return answer
	 */
	public String createAnswer()
	{
		StringBuilder sb = new StringBuilder();
		if (getA_Asset_ID() != 0)
			sb.append("Sign up for support at http://www.adempiere.org to receive answers.");
		else
		{
			if (getR_IssueKnown_ID() != 0)
				sb.append("Known Issue\n");
			if (getR_Request_ID() != 0)
				sb.append("Request: ")
					.append(getRequest().getDocumentNo())
					.append("\n");
		}
		return sb.toString();
	}	//	createAnswer
	
	/**
	 * 	Get Request
	 *	@return request or null
	 */
	public X_R_Request getRequest()
	{
		if (getR_Request_ID() == 0)
			return null;
		return new X_R_Request(getCtx(), getR_Request_ID(), null);
	}	//	getRequestDocumentNo

	/**
	 * 	Get Request Document No
	 *	@return request Document No
	 */
	@Override
	public String getRequestDocumentNo()
	{
		if (getR_Request_ID() == 0)
			return "";
		X_R_Request r = getRequest();
		return r.getDocumentNo();
	}	//	getRequestDocumentNo
	
	/**
	 * 	Get System Status
	 *	@return system status
	 */
	@Override
	public String getSystemStatus ()
	{
		String s = super.getSystemStatus ();
		if (s == null || s.length() == 0)
			s = SYSTEMSTATUS_Evaluation;
		return s;
	}	//	getSystemStatus
	
	
	/**************************************************************************
	 * 	Report/Update Issue.
	 *	@return error message
	 */
	public String report()
	{
		if (true)
			return "-";
		StringBuilder parameter = new StringBuilder("?");
		if (getRecord_ID() == 0)	//	don't report
			return "ID=0";
		if (getRecord_ID() == 1)	//	new
		{
			parameter.append("ISSUE=");
			HashMap htOut = get_HashMap();
			try		//	deserializing in create
			{
				ByteArrayOutputStream bOut = new ByteArrayOutputStream();
				ObjectOutput oOut = new ObjectOutputStream(bOut);
				oOut.writeObject(htOut);
				oOut.flush();
				String hexString = Secure.convertToHexString(bOut.toByteArray());
				parameter.append(hexString);
			}
			catch (Exception e) 
			{
				log.error(e.getLocalizedMessage());
				return "New-" + e.getLocalizedMessage();
			}
		}
		else	//	existing
		{
			try
			{
				parameter.append("RECORDID=").append(getRecord_ID());
				parameter.append("&DBADDRESS=").append(URLEncoder.encode(getDBAddress(), "UTF-8"));
				parameter.append("&COMMENTS=").append(URLEncoder.encode(getComments(), "UTF-8"));
			}
			catch (Exception e) 
			{
				log.error(e.getLocalizedMessage());
				return "Update-" + e.getLocalizedMessage();
			}
		}
		
		InputStreamReader in = null;
		String target = "http://dev1/wstore/issueReportServlet";
		try		//	Send GET Request
		{
			StringBuilder urlString = new StringBuilder(target)
				.append(parameter);
			URL url = new URL (urlString.toString());
			URLConnection uc = url.openConnection();
			in = new InputStreamReader(uc.getInputStream());
		}
		catch (Exception e)
		{
			String msg = "Cannot connect to http://" + target; 
			if (e instanceof FileNotFoundException || e instanceof ConnectException)
				msg += "\nServer temporarily down - Please try again later";
			else
			{
				msg += "\nCheck connection - " + e.getLocalizedMessage();
				log.debug(msg);
			}
			return msg;
		}
		return readResponse(in);
	}	//	report
	
	/**
	 * 	Read Response
	 *	@param in input stream
	 *	@return error message
	 */
	private String readResponse(InputStreamReader in)
	{
		StringBuilder sb = new StringBuilder();
		int Record_ID = 0;
		String ResponseText = null;
		String RequestDocumentNo = null;
		try		//	Get Answer
		{
			int c;
			while ((c = in.read()) != -1)
				sb.append((char)c);
			in.close();
			log.debug(sb.toString());
			String clear = URLDecoder.decode(sb.toString(), "UTF-8");
			log.debug(clear);
			//	Interpret Data
			StringTokenizer st = new StringTokenizer(clear, DELIMITER);
			while (st.hasMoreElements())
			{
				String pair = st.nextToken();
				try
				{
					int index = pair.indexOf('=');
					if (pair.startsWith("RECORDID="))
					{
						String info = pair.substring(index+1);
						Record_ID = Integer.parseInt(info);
					}
					else if (pair.startsWith("RESPONSE="))
						ResponseText = pair.substring(index+1);
					else if (pair.startsWith("DOCUMENTNO="))
						RequestDocumentNo = pair.substring(index+1);
				}
				catch (Exception e)
				{
					log.warn(pair + " - " + e.getMessage());
				}
			}
		}
		catch (Exception ex)
		{
			log.debug("", ex);
			return "Reading-" + ex.getLocalizedMessage();
		}

		if (Record_ID != 0)
			setRecord_ID(Record_ID);
		if (ResponseText != null)
			setResponseText(ResponseText);
		if (RequestDocumentNo != null)
			setRequestDocumentNo(RequestDocumentNo);
		return null;
	}	//	readResponse
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder("MIssue[");
		sb.append (get_ID())
			.append ("-").append (getIssueSummary())
			.append (",Record=").append (getRecord_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	
}	//	MIssue
