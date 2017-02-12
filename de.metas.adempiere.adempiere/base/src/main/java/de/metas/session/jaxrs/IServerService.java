package de.metas.session.jaxrs;

import java.util.Properties;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.ISingletonService;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.process.ProcessExecutionResult;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Path("/server/")
public interface IServerService extends ISingletonService
{
	/**
	 * Post Immediate.<br>
	 * Note: Use {@link ITrx#TRXNAME_None}, i.e. out of transaction because we are after-commit so the document shall be accessible for anyone
	 *
	 * @param ctx Client Context
	 * @param AD_Client_ID Client ID of Document
	 * @param AD_Table_ID Table ID of Document
	 * @param Record_ID Record ID of this document
	 * @param force force posting
	 * @param trxName ignore, retained for backward compatibility
	 * @return null, if success or error message
	 */
	@POST
	@Path("postImmediate/{AD_Client_ID}/{AD_Table_ID}/{Record_ID}/{force}")
	public String postImmediate(
			Properties ctx,
			@PathParam("AD_Client_ID") int AD_Client_ID,
			@PathParam("AD_Table_ID") int AD_Table_ID,
			@PathParam("Record_ID") int Record_ID,
			@PathParam("force") boolean force);

	/**
	 * Execute process
	 *
	 * @param adPInstanceId
	 * @return process execution result
	 */
	@POST
	@Path("process")
	public ProcessExecutionResult process(int adPInstanceId);

	/**
	 * Send the email
	 * 
	 * @param email
	 * @return email sent status
	 */
	@POST
	@Path("sendEMail")
	EMailSentStatus sendEMail(EMail email);

	/**
	 * Execute task on server
	 *
	 * @param AD_Task_ID task
	 * @return execution trace
	 */
	@POST
	@Path("executeTask")
	public String executeTask(int AD_Task_ID);

	/**
	 * Cash Reset
	 *
	 * @param tableName table name
	 * @param Record_ID record or 0 for all
	 * @return number of records reset
	 */
	@POST
	@Path("cacheReset")
	public int cacheReset(String tableName, int Record_ID);

	/**
	 * Describes the instance and its content for debugging purpose
	 *
	 * @return Debugging information about the instance and its content
	 */
	@POST
	@Path("getStatus")
	public String getStatus();
}
