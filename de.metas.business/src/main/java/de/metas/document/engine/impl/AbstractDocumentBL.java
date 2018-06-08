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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;

import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import lombok.NonNull;

public abstract class AbstractDocumentBL implements IDocumentBL
{
	private static final transient Logger logger = LogManager.getLogger(AbstractDocumentBL.class);

	private final Supplier<Map<String, DocumentHandlerProvider>> docActionHandlerProvidersByTableName = Suppliers.memoize(() -> retrieveDocActionHandlerProvidersIndexedByTableName());

	protected abstract String retrieveString(int adTableId, int recordId, final String columnName);

	protected abstract Object retrieveModelOrNull(Properties ctx, int adTableId, int recordId);

	protected final DocumentHandlerProvider getDocActionHandlerProviderByTableNameOrNull(@NonNull final String tableName)
	{
		return docActionHandlerProvidersByTableName.get().get(tableName);
	}

	private static final Map<String, DocumentHandlerProvider> retrieveDocActionHandlerProvidersIndexedByTableName()
	{
		final Map<String, DocumentHandlerProvider> providersByTableName = Adempiere.getSpringApplicationContext()
				.getBeansOfType(DocumentHandlerProvider.class)
				.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(DocumentHandlerProvider::getHandledTableName, Function.identity()));
		logger.debug("Retrieved providers: {}", providersByTableName);
		return providersByTableName;
	}

	@Override
	public boolean processIt(final IDocument document, final String processAction)
	{
		final DocumentEngine engine = DocumentEngine.ofDocument(document);
		return engine.processIt(processAction, document.getDocAction());
	}

	@Override
	public boolean processIt(final Object documentObj, final String action)
	{
		final boolean throwExIfNotSuccess = false;
		return processIt(documentObj, action, throwExIfNotSuccess);
	}

	private final boolean processIt(final Object documentObj, final String action, final boolean throwExIfNotSuccess)
	{
		final IDocument document = getDocument(documentObj);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = document.get_TrxName();

		final Boolean processed = trxManager.call(trxName, new TrxCallable<Boolean>()
		{

			@Override
			public Boolean call() throws Exception
			{
				document.set_TrxName(ITrx.TRXNAME_ThreadInherited);
				final boolean processed = processIt0(document, action);
				if (!processed && throwExIfNotSuccess)
				{
					throw new DocumentProcessingException(document, action);
				}

				return processed;
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
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

		return processed != null && processed.booleanValue();
	}

	protected boolean processIt0(final IDocument doc, final String action) throws Exception
	{
		Check.assumeNotNull(doc, "doc not null");

		//
		// Guard: save the document if new, else the processing could be corrupted.
		if (doc.get_ID() <= 0)
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
		final IDocument doc = getDocument(document);

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
			public void run(final String localTrxName) throws Exception
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
	public IDocument getDocument(final Object document)
	{
		final boolean throwEx = true;
		return getDocument(document, throwEx);
	}

	@Override
	public IDocument getDocumentOrNull(final Object document)
	{
		final boolean throwEx = false;
		return getDocument(document, throwEx);
	}

	protected abstract IDocument getDocument(final Object document, boolean throwEx);

	@Override
	public boolean issDocumentDraftedOrInProgress(final Object document)
	{
		return isDocumentStatusOneOf(document,
				IDocument.STATUS_Drafted,
				IDocument.STATUS_InProgress);
	}

	@Override
	public boolean isDocumentCompleted(final Object document)
	{
		return isDocumentStatusOneOf(document,
				IDocument.STATUS_Completed);
	}

	@Override
	public boolean isDocumentClosed(final Object document)
	{
		return isDocumentStatusOneOf(document,
				IDocument.STATUS_Closed);
	}

	@Override
	public boolean isDocumentCompletedOrClosed(final Object document)
	{
		return isDocumentStatusOneOf(document,
				IDocument.STATUS_Completed,
				IDocument.STATUS_Closed);
	}

	@Override
	public boolean issDocumentCompletedOrClosedOrReversed(final Object document)
	{
		final IDocument doc = getDocument(document);
		final String docStatus = doc.getDocStatus();

		return isStatusCompletedOrClosedOrReversed(docStatus);
	}

	@Override
	public boolean isStatusCompletedOrClosedOrReversed(final String docStatus)
	{
		return isStatusStrOneOf(docStatus,
				IDocument.STATUS_Completed,
				IDocument.STATUS_Closed,
				IDocument.STATUS_Reversed);
	}

	@Override
	public boolean isDocumentReversedOrVoided(final Object document)
	{
		final IDocument doc = getDocument(document);
		final String docStatus = doc.getDocStatus();
		return isStatusStrOneOf(docStatus,
				IDocument.STATUS_Reversed,
				IDocument.STATUS_Voided);
	}

	@Override
	public String getDocStatusOrNull(final Properties ctx_NOTUSED, final int adTableId, final int recordId)
	{
		return retrieveString(adTableId, recordId, DocumentTableFields.COLUMNNAME_DocStatus);
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
		final String documentNo = retrieveString(adTableId, recordId, DocumentTableFields.COLUMNNAME_DocumentNo);
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
	public String getDocumentNo(final Object model)
	{
		//
		// First try: document's DocumentNo if available
		final IDocument doc = getDocumentOrNull(model);
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
	public boolean isDocumentStatusOneOf(@NonNull final Object document, @NonNull final String... docStatusesToCheckFor)
	{
		final IDocument doc = getDocument(document);
		final String docStatus = doc.getDocStatus();
		return isStatusStrOneOf(docStatus, docStatusesToCheckFor);
	}

	@Override
	public boolean isStatusStrOneOf(final String docStatus, final String... docStatusesToCheckFor)
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

		// FIXME: hardcoded... we shall introduce it in IDocument

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

		final IDocument doc = getDocumentOrNull(model);
		if (doc != null)
		{
			return doc.getSummary();
		}

		// Fallback: use toString()
		return String.valueOf(model);
	}

	@Override
	public boolean isReversalDocument(@NonNull final Object model)
	{
		// Try Reversal_ID column if available
		final Integer original_ID = InterfaceWrapperHelper.getValueOrNull(model, IDocument.Reversal_ID);
		if (original_ID != null && original_ID > 0)
		{
			final int reversal_id = InterfaceWrapperHelper.getId(model);
			if (reversal_id > original_ID)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public final Map<String, IDocActionItem> retrieveDocActionItemsIndexedByValue()
	{
		final IADReferenceDAO referenceDAO = Services.get(IADReferenceDAO.class);
		final Properties ctx = Env.getCtx();
		final String adLanguage = Env.getAD_Language(ctx);

		final Map<String, IDocActionItem> docActionItemsByValue = referenceDAO.retrieveListItems(X_C_Order.DOCACTION_AD_Reference_ID) // 135
				.stream()
				.map(adRefListItem -> new DocActionItem(adRefListItem, adLanguage))
				.sorted(Comparator.comparing(DocActionItem::toString))
				.collect(GuavaCollectors.toImmutableMapByKey(IDocActionItem::getValue));
		return docActionItemsByValue;
	}

	private static final class DocActionItem implements IDocActionItem
	{
		private final String value;
		private final String caption;
		private String description;

		private DocActionItem(final ADRefListItem adRefListItem, final String adLanguage)
		{
			this.value = adRefListItem.getValue();
			this.caption = adRefListItem.getName().translate(adLanguage);
			this.description = adRefListItem.getDescription().translate(adLanguage);
		}

		@Override
		public String toString()
		{
			// IMPORTANT: this is how it will be displayed to user
			return caption;
		}

		@Override
		public int hashCode()
		{
			return Objects.hashCode(value);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj instanceof DocActionItem)
			{
				final DocActionItem other = (DocActionItem)obj;
				return Objects.equal(value, other.value);
			}
			else
			{
				return false;
			}
		}

		@Override
		public String getValue()
		{
			return value;
		}

		@Override
		public String getDescription()
		{
			return description;
		}
	}
}
