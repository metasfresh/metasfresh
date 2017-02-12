package de.metas.acct.process;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.POInfo;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.IDocumentBL;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.acct.base
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

/**
 * The documents (created one day before) that were marked as posted but have no fact accounts will be reposted by this process
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Documents_FactAcct_Creation_For_Posted extends JavaProcess
{
	@Param(parameterName = "DateStart")
	private Date p_Date;

	@Override
	protected String doIt() throws Exception
	{
		// this process is posting documents that were created one day before the process runs
		final Timestamp startTime;

		if (p_Date != null)
		{
			startTime = TimeUtil.getDay(p_Date);
		}
		else
		{
			startTime = TimeUtil.getPrevDay(new Timestamp(System.currentTimeMillis()));
		}

		// list all the documents that are marked as posted but have no fact accounts.
		// this list will not include the documents with no fact accounts that were not supposed to be posted (always 0 in posting)
		final List<DocAction> documentsPostedNoFacts = Services.get(IDocumentBL.class).retrievePostedWithoutFactActt(getCtx(), startTime);

		if (documentsPostedNoFacts.isEmpty())
		{
			// do nothing
			return "All documents are posted";
		}

		final ILoggable loggable = Loggables.get();

		final IPostingService postingService = Services.get(IPostingService.class);

		for (final DocAction document : documentsPostedNoFacts)
		{
			final int tableID = document.get_Table_ID();

			final int recordID = document.get_ID();

			final String documentNo = document.getDocumentNo();

			final POInfo modelPOInfo = POInfo.getPOInfo(getCtx(), tableID);
			final String tableName = modelPOInfo.getTableName();

			// Note: Do not change this message!
			// The view de_metas_acct.Reposted_Documents is based on it.
			loggable.addLog("Document Reposted: AD_Table_ID = {}, Record_ID = {}, TableName = {}, DocumentNo = {}.",
					tableID,
					recordID,
					tableName,
					documentNo);

			postingService.newPostingRequest()
					// Post it in same context and transaction as the process
					.setContext(getCtx(), getTrxName())
					.setAD_Client_ID(getAD_Client_ID())
					.setDocument(document) // the document to be posted
					.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
					.setPostImmediate(PostImmediate.Yes) // yes, post it immediate
					.setForce(false) // don't force it
					.setPostWithoutServer() // post directly (don't contact the server) because we want to post on client or server like the main document
					.postIt(); // do it!
		}

		return MSG_OK;
	}

}
