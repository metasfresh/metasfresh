package de.metas.acct.process;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.IDocumentBL;

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

public class Documents_FactAcct_Creation_For_Posted extends SvrProcess
{
	final ILoggable loggable = ILoggable.THREADLOCAL.getLoggable();

	@Override
	protected String doIt() throws Exception
	{
		/*
		 * "GL_Journal"
		 * "M_Movement"
		 * "C_Payment"
		 * "C_BankStatement"
		 * "M_MatchInv"
		 * "C_AllocationHdr"
		 * "C_Invoice"
		 * "M_Inventory"
		 * "M_InOut"
		 * 
		 */

		// today, 00:00:00
		// @SuppressWarnings("deprecation")
		// final Date startDate = new Date(116,4,2);
		//final Timestamp startTime = TimeUtil.getPrevDay(new Timestamp(System.currentTimeMillis()));
		final Timestamp startTime = TimeUtil.asTimestamp(new Date(114,4,2));

		final List<Object> documentsPostedNoFacts = Services.get(IDocumentBL.class).retrievePostedWithoutFactActt(getCtx(), startTime);
		if (documentsPostedNoFacts.isEmpty())
		{
			// do nothing
			return "No Allocation Headers to post";
		}
		
		final ILoggable loggable = ILoggable.THREADLOCAL.getLoggable();

		final IPostingService postingService = Services.get(IPostingService.class);

		for (final Object document : documentsPostedNoFacts)
		{
			System.out.println(getAD_PInstance_ID());
			loggable.addLog("Document Reposted {0}: ", document);

			postingService.newPostingRequest()
					// Post it in same context and transaction as this document is posted
					.setContext(getCtx(), getTrxName())
					.setAD_Client_ID(getAD_Client_ID())
					.setDocument(document) // the document to be posted
					.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
					.setPostWithoutServer() // we are on server side now, so don't try to contact the server again
					.setPostImmediate(PostImmediate.Yes) // yes, post it immediate
					.setForce(false) // don't force it
					.setPostWithoutServer() // post directly (don't contact the server) because we want to post on client or server like the main document
					.postIt(); // do it!
		}

		return MSG_OK;
	}

}
