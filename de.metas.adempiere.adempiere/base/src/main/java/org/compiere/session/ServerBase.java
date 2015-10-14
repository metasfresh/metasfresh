package org.compiere.session;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.acct.api.IPostingService;
import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.ProcessUtil;
import org.adempiere.util.Services;
import org.compiere.interfaces.Server;
import org.compiere.model.MClient;
import org.compiere.model.MTask;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.CacheMgt;
import org.compiere.util.EMail;
import org.compiere.util.Env;

/**
 * Base implementation of {@link Server}.
 * 
 * AIM: have a technology (EJB or any other) implementaton of {@link Server}
 *
 * @author tsa
 *
 */
public class ServerBase implements Server
{
	/** Logger */
	protected final transient CLogger log = CLogger.getCLogger(getClass());
	//
	private static int s_no = 0;
	private final int m_no;
	//
	private final int m_windowCount = 0;
	private int m_postCount = 0;
	private int m_processCount = 0;
	private int m_workflowCount = 0;
	private final int m_paymentCount = 0;
	private final int m_nextSeqCount = 0;
	private final int m_stmt_rowSetCount = 0;
	private final int m_stmt_updateCount = 0;
	private int m_cacheResetCount = 0;
	private final int m_updateLOBCount = 0;

	public ServerBase()
	{
		super();
		m_no = ++s_no;
	}

	/**
	 * Post Immediate
	 *
	 * @param ctx Client Context
	 * @param AD_Client_ID Client ID of Document
	 * @param AD_Table_ID Table ID of Document
	 * @param Record_ID Record ID of this document
	 * @param force force posting
	 * @param trxName ignore, retained for backward compatibility
	 * @return null, if success or error message
	 */
	@Override
	public String postImmediate(final Properties ctx,
			final int AD_Client_ID,
			final int AD_Table_ID, final int Record_ID,
			final boolean force,
			final String trxName)
	{
		log.info("[" + m_no + "] Table=" + AD_Table_ID + ", Record=" + Record_ID);

		m_postCount++;

		//
		// If we got a transaction from the remote client and that transaction does not exist in our instance
		// => run out of transaction and hope for the best
		final String trxNameToUse;
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (!trxManager.isNull(trxName) && trxManager.getTrxOrNull(trxName) == null)
		{
			log.log(Level.WARNING, "Got a transaction which does not exist ({0}). Using trxName=null", trxName);
			trxNameToUse = ITrx.TRXNAME_None;
		}
		else
		{
			trxNameToUse = trxName;
		}

		//
		// Ask the document to be posted immediate
		return Services.get(IPostingService.class)
				.newPostingRequest()
				.setContext(ctx, trxNameToUse)
				.setAD_Client_ID(AD_Client_ID)
				.setDocument(AD_Table_ID, Record_ID)
				.setForce(force)
				.setFailOnError(false) // backward compatibile
				.setPostWithoutServer() // we are on server side now, so don't try to contact the server again
				.setPostImmediate(PostImmediate.Yes) // make sure we are posting it immediate
				//
				// Execute the posting
				.postIt()
				// Get and return the error message (if any)
				.getPostedErrorMessage();
	}	// postImmediate

	/*************************************************************************
	 * Process Remote
	 *
	 * @param ctx Context
	 * @param pi Process Info
	 * @return resulting Process Info
	 */
	@Override
	public ProcessInfo process(final Properties ctx, final ProcessInfo pi)
	{

		m_processCount++;

		// Start Process
		ProcessUtil.startJavaProcess(ctx, pi, ITrx.TRX_None);
		return pi;
	}	// process

	/*************************************************************************
	 * Run Workflow (and wait) on Server
	 *
	 * @param ctx Context
	 * @param pi Process Info
	 * @param AD_Workflow_ID id
	 * @return process info
	 */
	@Override
	public ProcessInfo workflow(final Properties ctx, final ProcessInfo pi, final int AD_Workflow_ID)
	{
		log.info("[" + m_no + "] " + AD_Workflow_ID);
		m_workflowCount++;
		ProcessUtil.startWorkFlow(ctx, pi, AD_Workflow_ID);
		return pi;
	}	// workflow

	/**
	 * Create EMail from Server (Request User)
	 *
	 * @param ctx Context
	 * @param AD_Client_ID client
	 * @param to recipient email address
	 * @param subject subject
	 * @param message message
	 * @return EMail
	 */
	@Override
	public EMail createEMail(final Properties ctx, final int AD_Client_ID,
			final String to, final String subject, String message)
	{
		final MClient client = MClient.get(ctx, AD_Client_ID);
		boolean html = false;
		if (message != null && message.startsWith(EMail.HTML_MAIL_MARKER))
		{
			html = true;
			message = message.substring(EMail.HTML_MAIL_MARKER.length());
		}
		return client.createEMail(to, subject, message, html);
	}	// createEMail

	/**
	 * Create EMail from Server (Request User)
	 *
	 * @param ctx Context
	 * @param AD_Client_ID client
	 * @param AD_User_ID user to send email from
	 * @param to recipient email address
	 * @param subject subject
	 * @param message message
	 * @return EMail
	 */
	@Override
	public EMail createEMail(final Properties ctx, final int AD_Client_ID,
			final int AD_User_ID,
			final String to, final String subject, String message)
	{
		final MClient client = MClient.get(ctx, AD_Client_ID);
		final MUser from = new MUser(ctx, AD_User_ID, null);
		boolean html = false;
		if (message != null && message.startsWith(EMail.HTML_MAIL_MARKER))
		{
			html = true;
			message = message.substring(EMail.HTML_MAIL_MARKER.length());
		}
		return client.createEMail(from, to, subject, message, html);
	}	// createEMail

	/**
	 * Execute task on server
	 *
	 * @param AD_Task_ID task
	 * @return execution trace
	 */
	@Override
	public String executeTask(final int AD_Task_ID)
	{
		final MTask task = new MTask(Env.getCtx(), AD_Task_ID, ITrx.TRXNAME_None);	// Server Context
		return task.execute();
	}	// executeTask

	/**
	 * Cash Reset
	 *
	 * @param tableName table name
	 * @param Record_ID record or 0 for all
	 * @return number of records reset
	 */
	@Override
	public int cacheReset(final String tableName, final int Record_ID)
	{
		log.config(tableName + " - " + Record_ID);
		m_cacheResetCount++;
		return CacheMgt.get().reset(tableName, Record_ID);
	}	// cacheReset

	/**************************************************************************
	 * Describes the instance and its content for debugging purpose
	 *
	 * @return Debugging information about the instance and its content
	 */
	@Override
	public String getStatus()
	{
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append("[")
				.append(m_no)
				.append("-Window=").append(m_windowCount)
				.append(",Post=").append(m_postCount)
				.append(",Process=").append(m_processCount)
				.append(",NextSeq=").append(m_nextSeqCount)
				.append(",Workflow=").append(m_workflowCount)
				.append(",Payment=").append(m_paymentCount)
				.append(",RowSet=").append(m_stmt_rowSetCount)
				.append(",Update=").append(m_stmt_updateCount)
				.append(",CacheReset=").append(m_cacheResetCount)
				.append(",UpdateLob=").append(m_updateLOBCount)
				.append("]");
		return sb.toString();
	}	// getStatus

	/**
	 * Execute db proces on server
	 *
	 * @param processInfo
	 * @param procedureName
	 * @return ProcessInfo
	 */
	@Override
	public ProcessInfo dbProcess(final ProcessInfo processInfo, final String procedureName)
	{
		ProcessUtil.startDatabaseProcedure(processInfo, procedureName, ITrx.TRX_None);
		return processInfo;
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return getStatus();
	}	// toString
}
