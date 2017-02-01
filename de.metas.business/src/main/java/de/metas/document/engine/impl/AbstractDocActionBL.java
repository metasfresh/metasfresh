package de.metas.document.engine.impl;

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


import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

public abstract class AbstractDocActionBL implements IDocActionBL
{
	private static final Logger logger = LogManager.getLogger(AbstractDocActionBL.class);

	protected static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	protected abstract String retrieveString(Properties ctx, int adTableId, int recordId, final String columnName);

	protected abstract Object retrieveModelOrNull(Properties ctx, int adTableId, int recordId);

	@Override
	public boolean processIt(final DocAction document, final String processAction)
	{
		DocumentEngine engine = new DocumentEngine (document, document.getDocStatus());
		return engine.processIt (processAction, document.getDocAction());
	}
		
	@Override
	public boolean processIt(final Object documentObj, final String action)
	{
		final boolean throwExIfNotSuccess = false;
		return processIt(documentObj, action, throwExIfNotSuccess);
	}

	private final boolean processIt(final Object documentObj, final String action, final boolean throwExIfNotSuccess)
	{
		final DocAction document = getDocAction(documentObj);

		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = document.get_TrxName();

		final boolean[] processed = new boolean[] { false };
		trxManager.run(trxName, new TrxRunnable2()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				document.set_TrxName(localTrxName);
				processed[0] = processIt0(document, action);
				if (!processed[0] && throwExIfNotSuccess)
				{
					throw new DocumentProcessingException(document, action);
				}
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				processed[0] = false;
				final DocumentProcessingException dpe = new DocumentProcessingException(document, action, e);
				MetasfreshLastError.saveError(logger, dpe.getLocalizedMessage(), dpe);
				throw dpe;
			}

			@Override
			public void doFinally()
			{
				// put back the transaction which document had initially
				document.set_TrxName(trxName);
			}
		});

		return processed[0];
	}

	protected boolean processIt0(final DocAction doc, final String action) throws Exception
	{
		Check.assumeNotNull(doc, "doc not null");
		
		//
		// Guard: save the document if new, else the processing could be corrupted.
		if (InterfaceWrapperHelper.isNew(doc))
		{
			new AdempiereException("Please make sure the document is saved before processing it: " + doc)
					.throwIfDeveloperModeOrLogWarningElse(logger);
			InterfaceWrapperHelper.save(doc);
		}
		
		// Actual document processing
		return doc.processIt(action);
	}

	@Override
	public void processEx(final Object document, final String docAction, final String expectedDocStatus)
	{
		final DocAction doc = getDocAction(document);

		final boolean throwExIfNotSuccess = true;
		processIt(doc, docAction, throwExIfNotSuccess);

		// IMPORTANT: we need to save 'doc', not 'document', because in case 'document' is a grid tab, then the PO 'doc'
		// is a different instance. If we save 'document' in that case, the changes to 'doc' will be lost.
		InterfaceWrapperHelper.save(doc);
		InterfaceWrapperHelper.refresh(document);

		if (expectedDocStatus != null && !expectedDocStatus.equals(doc.getDocStatus()))
		{
			final String errmsg = "Document does not have expected status (Expected=" + expectedDocStatus + ", actual=" + doc.getDocStatus() + ")";
			throw new DocumentProcessingException(errmsg, doc, docAction);
		}
	}

	@Override
	public <T> void processDocumentsList(final Collection<T> documentsToProcess, final String docAction, final String expectedDocStatus)
	{
		Check.assumeNotEmpty(documentsToProcess, "documents not empty");

		// Make sure all documents are out of transaction.
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.assertModelsTrxName(ITrx.TRXNAME_None, documentsToProcess);

		// Get documents uniquely
		final Map<Integer, T> documents = new HashMap<>(documentsToProcess.size());
		for (final T document : documentsToProcess)
		{
			final int documentId = InterfaceWrapperHelper.getId(document);
			documents.put(documentId, document);
		}

		trxManager.run(new TrxRunnable()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				for (final T document : documents.values())
				{
					final String trxNameOld = InterfaceWrapperHelper.getTrxName(document);
					try
					{
						InterfaceWrapperHelper.setTrxName(document, localTrxName);
						processEx(document, docAction, expectedDocStatus);
					}
					finally
					{
						InterfaceWrapperHelper.setTrxName(document, trxNameOld);
					}
				}
			}
		});
	}

	@Override
	public DocAction getDocAction(final Object document)
	{
		final boolean throwEx = true;
		return getDocAction(document, throwEx);
	}

	@Override
	public DocAction getDocActionOrNull(final Object document)
	{
		final boolean throwEx = false;
		return getDocAction(document, throwEx);
	}

	protected abstract DocAction getDocAction(final Object document, boolean throwEx);

	@Override
	public boolean isStatusDraftedOrInProgress(final Object document)
	{
		return isStatusOneOf(document,
				DocumentEngine.STATUS_Drafted,
				DocumentEngine.STATUS_InProgress);
	}

	@Override
	public boolean isStatusCompleted(final Object document)
	{
		return isStatusOneOf(document,
				DocumentEngine.STATUS_Completed);
	}
	
	
	@Override
	public boolean isStatusClosed(Object document)
	{
		return isStatusOneOf(document,
				DocumentEngine.STATUS_Closed);
	}

	@Override
	public boolean isStatusCompletedOrClosed(Object document)
	{
		return isStatusOneOf(document,
				DocumentEngine.STATUS_Completed,
				DocumentEngine.STATUS_Closed);
	}

	@Override
	public boolean isStatusCompletedOrClosedOrReversed(final Object document)
	{
		final DocAction doc = getDocAction(document);
		final String docStatus = doc.getDocStatus();

		return isStatusCompletedOrClosedOrReversed(docStatus);
	}

	@Override
	public boolean isStatusCompletedOrClosedOrReversed(final String docStatus)
	{
		return isStatusOneOf(docStatus,
				DocAction.STATUS_Completed,
				DocAction.STATUS_Closed,
				DocAction.STATUS_Reversed);
	}

	@Override
	public boolean isStatusReversedOrVoided(final Object document)
	{
		final DocAction doc = getDocAction(document);
		final String docStatus = doc.getDocStatus();
		return isStatusOneOf(docStatus,
				DocAction.STATUS_Reversed,
				DocAction.STATUS_Voided);
	}


	@Override
	public String getDocStatusOrNull(Properties ctx, int adTableId, int recordId)
	{
		return retrieveString(ctx, adTableId, recordId, "DocStatus");
	}

	@Override
	public String getDocumentNo(final Properties ctx, final int adTableId, final int recordId)
	{
		if (adTableId <= 0 || recordId <= 0)
		{
			return null;
		}

		//
		// First we try to fetch the DocumentNo column from database
		final String documentNo = retrieveString(ctx, adTableId, recordId, "DocumentNo");
		if (documentNo != null)
		{
			return documentNo;
		}

		//
		// Second, we load the model and we use the algorithm from getDocumentNo(model)
		final Object model = retrieveModelOrNull(ctx, adTableId, recordId);
		if (model == null)
		{
			return null;
		}
		return getDocumentNo(model);
	}

	@Override
	public String getDocumentNo(Object model)
	{
		//
		// First try: document's DocumentNo if available
		final DocAction doc = getDocActionOrNull(model);
		if (doc != null)
		{
			return doc.getDocumentNo();
		}

		// Try DocumentNo column if available
		final String documentNo = InterfaceWrapperHelper.getValueOrNull(model, "DocumentNo");
		if (documentNo != null)
		{
			return documentNo;
		}

		// Try Value column if available
		final String value = InterfaceWrapperHelper.getValueOrNull(model, "Value");
		if (value != null)
		{
			return value;
		}

		// Try Name column if available
		final String name = InterfaceWrapperHelper.getValueOrNull(model, "Name");
		if (name != null)
		{
			return name;
		}

		// Fallback: use model's ID
		final int recordId = InterfaceWrapperHelper.getId(model);
		return String.valueOf(recordId);
	}

	@Override
	public I_C_DocType getDocTypeOrNull(final Object model)
	{
		final Integer docTypeId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_C_DocType_ID);
		if (docTypeId == null)
		{
			return null;
		}

		if (docTypeId <= 0)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		return InterfaceWrapperHelper.create(ctx, docTypeId, I_C_DocType.class, trxName);
	}

	@Override
	public boolean isStatusOneOf(final Object document, final String... docStatusesToCheckFor)
	{
		final DocAction doc = getDocAction(document);
		final String docStatus = doc.getDocStatus();
		return isStatusOneOf(docStatus, docStatusesToCheckFor);
	}

	@Override
	public boolean isStatusOneOf(final String docStatus, final String... docStatusesToCheckFor)
	{
		for (final String currentDocStatus : docStatusesToCheckFor)
		{
			if (currentDocStatus.equals(docStatus))
			{
				return true;
			}
		}
		return false;
	}

	protected final Timestamp getDocumentDate(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof I_C_Invoice)
		{
			return ((I_C_Invoice)model).getDateInvoiced();
		}

		if (model instanceof I_C_Order)
		{
			return ((I_C_Order)model).getDateOrdered();
		}

		if (model instanceof I_C_OrderLine)
		{
			return ((I_C_OrderLine)model).getDateOrdered();
		}

		if (model instanceof I_M_InOut)
		{
			return ((I_M_InOut)model).getMovementDate();
		}

		// in case the log is not made for one of these table,s leave the dateDoc empty
		return null;
	}

	@Override
	public final String getSummary(final Object model)
	{
		Check.assumeNotNull(model, "model not null");

		final DocAction doc = getDocActionOrNull(model);
		if (doc != null)
		{
			return doc.getSummary();
		}

		// Fallback: use toString()
		return String.valueOf(model);
	}
}
