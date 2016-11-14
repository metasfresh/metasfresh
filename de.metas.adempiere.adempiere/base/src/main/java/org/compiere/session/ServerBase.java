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

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.MTask;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IServerService;

/**
 * Base implementation of {@link Server}.
 *
 * AIM: have a technology (EJB or any other) implementaton of {@link Server}
 *
 * @author tsa
 *
 */
public class ServerBase implements IServerService
{
	/** Logger */
	protected final transient Logger log = LogManager.getLogger(getClass());
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
			final boolean force)
	{
		log.info("[" + m_no + "] Table=" + AD_Table_ID + ", Record=" + Record_ID);

		m_postCount++;

		final String trxName = ITrx.TRXNAME_None;

		//
		// Ask the document to be posted immediate
		return Services.get(IPostingService.class)
				.newPostingRequest()
				.setContext(ctx, trxName)
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
	public void process(final Properties ctx, final int adPInstanceId)
	{
		// TODO: implement remote process
		m_processCount++;
		throw new UnsupportedOperationException();
	}	// process

	@Override
	public EMailSentStatus sendEMail(final EMail email)
	{
		return email.send();
	}

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
		log.info(tableName + " - " + Record_ID);
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
