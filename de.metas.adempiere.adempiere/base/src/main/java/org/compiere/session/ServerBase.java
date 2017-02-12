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
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.MTask;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
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
	private static final AtomicInteger s_no = new AtomicInteger(0);
	private final int m_no;
	//
	private final AtomicInteger m_postCount = new AtomicInteger(0);
	private final AtomicInteger m_processCount = new AtomicInteger(0);
	private final AtomicInteger m_cacheResetCount = new AtomicInteger(0);

	public ServerBase()
	{
		super();
		m_no = s_no.incrementAndGet();
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
		log.info("[{}] postImmediate: Table={}, Record_ID={}", m_no, AD_Table_ID, Record_ID);
		m_postCount.incrementAndGet();

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
	 * @return
	 * @return resulting Process Info
	 */
	@Override
	public ProcessExecutionResult process(final int adPInstanceId)
	{
		m_processCount.incrementAndGet();

		final Thread currentThread = Thread.currentThread();
		final String threadNameBkp = currentThread.getName();
		try
		{
			currentThread.setName("Server_process_" + adPInstanceId);

			final ProcessExecutionResult result = ProcessInfo.builder()
					.setAD_PInstance_ID(adPInstanceId)
					.setCreateTemporaryCtx()
					//
					.buildAndPrepareExecution()
					.switchContextWhenRunning()
					.executeSync()
					.getResult();

			return result;
		}
		finally
		{
			currentThread.setName(threadNameBkp);
		}
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
		log.info("[{}] cacheReset: Table={}, Record_ID={}", m_no, tableName, Record_ID);
		m_cacheResetCount.incrementAndGet();

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
		return MoreObjects.toStringHelper(this)
				.add("instanceNo", m_no)
				.add("Post", m_postCount)
				.add("Process", m_processCount)
				.add("CacheReset", m_cacheResetCount)
				.toString();
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
