package de.metas.document.engine.impl;

import com.google.common.base.Objects;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.DocumentWrapper;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.setTrxName;

public abstract class AbstractDocumentBL implements IDocumentBL
{
	private static final Logger logger = LogManager.getLogger(AbstractDocumentBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final Supplier<Map<String, DocumentHandlerProvider>> docActionHandlerProvidersByTableName = Suppliers.memoize(AbstractDocumentBL::retrieveDocActionHandlerProvidersIndexedByTableName);

	private static final String PERF_MON_SYSCONFIG_NAME = "de.metas.monitoring.docAction.enable";

	protected abstract String retrieveString(int adTableId, int recordId, final String columnName);

	protected abstract Object retrieveModelOrNull(Properties ctx, int adTableId, int recordId);

	protected final DocumentHandlerProvider getDocActionHandlerProviderByTableNameOrNull(@NonNull final String tableName)
	{
		return docActionHandlerProvidersByTableName.get().get(tableName);
	}

	private static Map<String, DocumentHandlerProvider> retrieveDocActionHandlerProvidersIndexedByTableName()
	{
		if (!SpringContextHolder.instance.isApplicationContextSet())
		{
			// here we support the case of a unit test that
			// * doesn't care about DocumentHandlerProviders
			// * and does not want to do the @SpringBootTest dance
			return ImmutableMap.of();
		}
		final Map<String, DocumentHandlerProvider> providersByTableName = SpringContextHolder.instance.getBeansOfType(DocumentHandlerProvider.class)
				.stream()
				.collect(ImmutableMap.toImmutableMap(DocumentHandlerProvider::getHandledTableName, Function.identity()));
		logger.info("Retrieved providers: {}", providersByTableName);
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
		final IDocument document = getDocument(documentObj);
		final boolean throwExIfNotSuccess = false;

		return processIt(document, action, throwExIfNotSuccess);
	}

	private boolean processIt(
			@NonNull final IDocument document,
			@NonNull final String action,
			final boolean throwExIfNotSuccess)
	{
		final PerformanceMonitoringService perfMonServicew = SpringContextHolder.instance.getBeanOr(PerformanceMonitoringService.class, NoopPerformanceMonitoringService.INSTANCE);
		final boolean perfMonIsActive = sysConfigBL.getBooleanValue(PERF_MON_SYSCONFIG_NAME, false);
		if (perfMonIsActive)
		{
			return perfMonServicew.monitor(
					() -> processIt0(document, action, throwExIfNotSuccess),
					DocactionPerformanceMonitoringHelper.createMetadataFor(document, action));
		}
		else
		{
			return processIt0(document, action, throwExIfNotSuccess);
		}

	}

	private boolean processIt0(@NonNull final IDocument document,
							   @NonNull final String action,
							   final boolean throwExIfNotSuccess)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = getTrxName(document.getDocumentModel(), true /* ignoreIfNotHandled */);

		final Boolean processed = trxManager.call(trxName, new TrxCallable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				setTrxName(document.getDocumentModel(), ITrx.TRXNAME_ThreadInherited, true /* ignoreIfNotHandled */);
				final boolean processed = processIt0(document, action);
				if (!processed && throwExIfNotSuccess)
				{
					throw new DocumentProcessingException(document, action);
				}

				return processed;
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("Document", document.getDocumentInfo())
						.setParameter("DocAction", action);
			}

			@Override
			public void doFinally()
			{
				// put back the transaction which document had initially
				setTrxName(document.getDocumentModel(), trxName, true /* ignoreIfNotHandled */);
			}
		});

		return processed != null && processed;
	}

	protected boolean processIt0(@NonNull final IDocument doc, final String action) throws Exception
	{
		Check.assumeNotEmpty(action, "The given 'action' parameter needs to be not-empty");

		// Guard: save the document if new, else the processing could be corrupted.
		if (InterfaceWrapperHelper.isNew(doc.getDocumentModel()))
		{
			new AdempiereException("Please make sure the document is saved before processing it: " + doc)
					.throwIfDeveloperModeOrLogWarningElse(logger);
			InterfaceWrapperHelper.save(doc.getDocumentModel());
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
		final Object documentModel = doc.getDocumentModel();
		InterfaceWrapperHelper.save(documentModel);
		InterfaceWrapperHelper.refresh(documentModel);

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

		trxManager.runInNewTrx((TrxRunnable)localTrxName -> {
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

	private IDocument getDocument(
			@Nullable final Object documentObj,
			final boolean throwEx)
	{
		if (documentObj == null)
		{
			if (throwEx)
			{
				throw new AdempiereException("document is null");
			}
			return null;
		}

		//
		if (documentObj instanceof IDocument)
		{
			return (IDocument)documentObj;
		}

		final String tableName = InterfaceWrapperHelper.getModelTableNameOrNull(documentObj);
		final DocumentHandlerProvider handlerProvider = getDocActionHandlerProviderByTableNameOrNull(tableName);
		if (handlerProvider != null)
		{
			final DocumentHandler handler = handlerProvider.provideForDocument(documentObj);
			final Object documentObjToUse;
			if (POJOWrapper.isHandled(documentObj))
			{
				documentObjToUse = documentObj;
			}
			else if (documentObj instanceof TableRecordReference)
			{
				documentObjToUse = ((TableRecordReference)documentObj).getModel();
			}
			else
			{
				// if possible, try to make sure that we work on the PO. Otherwise all changes might get lost when we try to save documentObjToUse after processing.
				documentObjToUse = InterfaceWrapperHelper.getPO(documentObj);
			}
			return DocumentWrapper.wrapModelUsingHandler(documentObjToUse, handler);
		}

		return getLegacyDocumentOrNull(documentObj, throwEx);
	}

	protected abstract IDocument getLegacyDocumentOrNull(Object documentObj, boolean throwEx);

	@Override
	public boolean issDocumentDraftedOrInProgress(final Object document)
	{
		final DocStatus docStatus = getDocStatusOrNull(document);
		return docStatus != null && docStatus.isDraftedOrInProgress();
	}

	@Override
	public boolean isDocumentCompleted(final Object document)
	{
		final DocStatus docStatus = getDocStatusOrNull(document);
		return docStatus != null && docStatus.isCompleted();
	}

	@Override
	public boolean isDocumentClosed(final Object document)
	{
		final DocStatus docStatus = getDocStatusOrNull(document);
		return docStatus != null && docStatus.isClosed();
	}

	@Override
	public boolean isDocumentCompletedOrClosed(final Object document)
	{
		final DocStatus docStatus = getDocStatusOrNull(document);
		return docStatus != null && docStatus.isCompletedOrClosed();
	}

	@Override
	public boolean isDocumentReversedOrVoided(final Object document)
	{
		final IDocument doc = getDocument(document);
		final String docStatus = doc.getDocStatus();
		if (docStatus == null)
		{
			return false;
		}

		return DocStatus.ofCode(docStatus).isReversedOrVoided();
	}

	@Override
	public DocStatus getDocStatusOrNull(final Object documentObj)
	{
		final IDocument doc = getDocumentOrNull(documentObj);
		if (doc == null)
		{
			return null;
		}
		final DocStatus docStatus = DocStatus.ofNullableCode(doc.getDocStatus());
		return docStatus;
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
	public String getDocumentNo(@NonNull final Object model)
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
		final DocTypeId docTypeId = getDocTypeId(model).orElse(null);
		if (docTypeId == null)
		{
			return null;
		}

		return docTypeDAO.getRecordById(docTypeId);
	}

	@Override
	public Optional<DocTypeId> getDocTypeId(final Object model)
	{
		final Integer docTypeId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_C_DocType_ID);
		return DocTypeId.optionalOfRepoId(docTypeId);
	}

	@Override
	public Optional<DocBaseType> getDocBaseType(@NonNull final Object model)
	{
		return getDocTypeId(model).map(docTypeDAO::getDocBaseTypeById);
	}

	@Nullable
	protected final InstantAndOrgId getDocumentDate(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (model instanceof I_C_OrderLine)
		{
			final I_C_OrderLine orderLine = (I_C_OrderLine)model;
			return InstantAndOrgId.ofTimestamp(orderLine.getDateOrdered(), OrgId.ofRepoId(orderLine.getAD_Org_ID()));
		}

		final IDocument doc = getDocumentOrNull(model);
		if (doc != null)
		{
			return doc.getDocumentDate();
		}

		// in case the log is not made for one of these tables leave the dateDoc empty
		return null;
	}

	@Override
	public final String getSummary(@NonNull final Object model)
	{
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
		final ADReferenceService adReferenceService = ADReferenceService.get();
		final Properties ctx = Env.getCtx();
		final String adLanguage = Env.getAD_Language(ctx);

		return adReferenceService.retrieveListItems(X_C_Order.DOCACTION_AD_Reference_ID) // 135
				.stream()
				.map(adRefListItem -> new DocActionItem(adRefListItem, adLanguage))
				.sorted(Comparator.comparing(DocActionItem::toString))
				.collect(GuavaCollectors.toImmutableMapByKey(IDocActionItem::getValue));
	}

	private static final class DocActionItem implements IDocActionItem
	{
		private final String value;
		private final String caption;
		private final String description;

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
