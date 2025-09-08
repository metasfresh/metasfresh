/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.adempiere.ad.process;

import de.metas.document.engine.IDocumentBL;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.GenericPO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TrxRunnable2;

import java.util.Iterator;

/**
 * Select all records from AD_Table_ID, with given optional WhereClause for each record, call processIt with given DocAction
 *
 * @implSpec 05502
 */
public abstract class AbstractProcessDocumentsTemplate extends JavaProcess
{
	//
	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	//
	// Statistics
	private int countOK = 0;
	private int countError = 0;

	@Override
	protected final String doIt()
	{
		this.countOK = 0;
		this.countError = 0;

		final Iterator<GenericPO> documents = retrieveDocumentsToProcess();
		processDocuments(documents, getDocAction());

		return "@Processed@ (OK=#" + countOK + ", Error=#" + countError + ")";
	}

	protected abstract Iterator<GenericPO> retrieveDocumentsToProcess();

	protected abstract String getDocAction();

	private void processDocuments(final Iterator<GenericPO> documents, final String docAction)
	{
		while (documents.hasNext())
		{
			final Object doc = documents.next();
			trxManager.runInNewTrx(new TrxRunnable2()
			{
				@Override
				public void run(final String localTrxName)
				{
					InterfaceWrapperHelper.refresh(doc, localTrxName);
					docActionBL.processEx(doc, docAction, null); // expectedDocStatus=null because it is *not* always equal to the docAaction (Prepay-Order)

					addLog("Document " + doc + ": Processed");
					countOK++;
				}

				@Override
				public boolean doCatch(final Throwable e)
				{
					final String msg = "Processing of document " + doc
							+ ": Failed - ProccessMsg: " + documentBL.getDocument(doc).getProcessMsg()
							+ "; ExceptionMsg: " + e.getMessage();
					addLog(msg);
					log.warn(msg, e);
					countError++;
					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}
	}
}
