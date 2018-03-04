package de.metas.ui.web.window.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.model.MADBoilerPlate.SourceDocument;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.lookup.DocumentZoomIntoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

@Component
public class DocumentCollection
{
	private static final Logger logger = LogManager.getLogger(DocumentCollection.class);

	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentWebsocketPublisher websocketPublisher;

	private final Cache<DocumentKey, Document> rootDocuments = CacheBuilder.newBuilder().build();

	private final ConcurrentHashMap<String, Set<WindowId>> tableName2windowIds = new ConcurrentHashMap<>();

	/* package */ DocumentCollection()
	{
	}

	public DocumentDescriptorFactory getDocumentDescriptorFactory()
	{
		return documentDescriptorFactory;
	}

	/**
	 * Delegates to the {@link DocumentDescriptorFactory#isWindowIdSupported(WindowId)} of this instance's {@code documentDescriptorFactory}.
	 *
	 * @param windowId
	 * @return
	 */
	public boolean isWindowIdSupported(@Nullable final WindowId windowId)
	{
		return documentDescriptorFactory.isWindowIdSupported(windowId);
	}

	public final DocumentDescriptor getDocumentDescriptor(final WindowId windowId)
	{
		return documentDescriptorFactory.getDocumentDescriptor(windowId);
	}

	public final DocumentEntityDescriptor getDocumentEntityDescriptor(final WindowId windowId)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
		return descriptor.getEntityDescriptor();
	}

	private final void addToTableName2WindowIdsCache(final DocumentEntityDescriptor entityDescriptor)
	{
		final String tableName = entityDescriptor.getTableNameOrNull();
		if (tableName == null)
		{
			return;
		}

		final Set<WindowId> windowIds = tableName2windowIds.computeIfAbsent(tableName, k -> Collections.newSetFromMap(new ConcurrentHashMap<>()));
		windowIds.add(entityDescriptor.getWindowId());
	}

	private final Set<WindowId> getCachedWindowIdsForTableName(final String tableName)
	{
		final Set<WindowId> windowIds = tableName2windowIds.get(tableName);
		return windowIds != null && !windowIds.isEmpty() ? ImmutableSet.copyOf(windowIds) : ImmutableSet.of();
	}

	public Document getDocumentReadonly(@NonNull final DocumentPath documentPath)
	{
		return forDocumentReadonly(documentPath, Function.identity());
	}

	public <R> R forDocumentReadonly(@NonNull final DocumentPath documentPath, @NonNull final Function<Document, R> documentProcessor)
	{
		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();

		return forRootDocumentReadonly(rootDocumentPath, rootDocument -> {
			if (documentPath.isRootDocument())
			{
				return documentProcessor.apply(rootDocument);
			}
			else if (documentPath.isSingleIncludedDocument())
			{
				final Document includedDocument = rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId());
				DocumentPermissionsHelper.assertCanView(includedDocument, UserSession.getCurrentPermissions());

				return documentProcessor.apply(includedDocument);
			}
			else
			{
				throw new InvalidDocumentPathException(documentPath);
			}
		});
	}

	private Document getOrLoadDocument(@NonNull final DocumentKey documentKey)
	{
		try
		{
			return rootDocuments.get(documentKey, () -> {

				final Document rootDocument = retrieveRootDocumentFromRepository(documentKey)
						.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);

				addToTableName2WindowIdsCache(rootDocument.getEntityDescriptor());
				return rootDocument;
			});
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public <R> R forRootDocumentReadonly(@NonNull final DocumentPath documentPath, final Function<Document, R> rootDocumentProcessor)
	{
		final DocumentKey rootDocumentKey = DocumentKey.ofRootDocumentPath(documentPath.getRootDocumentPath());

		try (final IAutoCloseable readLock = getOrLoadDocument(rootDocumentKey).lockForReading())
		{
			final Document rootDocument = getOrLoadDocument(rootDocumentKey).copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
			DocumentPermissionsHelper.assertCanView(rootDocument, UserSession.getCurrentPermissions());

			return rootDocumentProcessor.apply(rootDocument);
		}
	}

	public <R> R forDocumentWritable(
			@NonNull final DocumentPath documentPath,
			final IDocumentChangesCollector changesCollector,
			final Function<Document, R> documentProcessor)
	{
		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();
		return forRootDocumentWritable(rootDocumentPath, changesCollector, rootDocument -> {

			final Document document;
			if (documentPath.isRootDocument())
			{
				document = rootDocument;
			}
			else if (documentPath.isSingleNewIncludedDocument())
			{
				document = rootDocument.createIncludedDocument(documentPath.getDetailId());
			}
			else
			{
				document = rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId());
				DocumentPermissionsHelper.assertCanEdit(rootDocument);
			}

			return documentProcessor.apply(document);
		});
	}

	public <R> R forRootDocumentWritable(final DocumentPath documentPathOrNew, final IDocumentChangesCollector changesCollector, final Function<Document, R> rootDocumentProcessor)
	{
		final DocumentPath rootDocumentPathOrNew = documentPathOrNew.getRootDocumentPath();

		final Document lockHolder;
		final boolean isNewRootDocument;
		final DocumentKey rootDocumentKey;
		if (rootDocumentPathOrNew.isNewDocument())
		{
			final Document newRootDocument = createRootDocument(rootDocumentPathOrNew, changesCollector);
			lockHolder = newRootDocument;
			rootDocumentKey = DocumentKey.ofRootDocumentPath(newRootDocument.getDocumentPath());
			isNewRootDocument = true;
		}
		else
		{
			rootDocumentKey = DocumentKey.ofRootDocumentPath(rootDocumentPathOrNew);
			lockHolder = getOrLoadDocument(rootDocumentKey);
			isNewRootDocument = false;
		}

		try (final IAutoCloseable writeLock = lockHolder.lockForWriting())
		{
			final Document rootDocument;
			if (isNewRootDocument)
			{
				rootDocument = lockHolder;
			}
			else
			{
				rootDocument = getOrLoadDocument(rootDocumentKey)
						.copy(CopyMode.CheckOutWritable, changesCollector)
						.refreshFromRepositoryIfStaled();

				DocumentPermissionsHelper.assertCanEdit(rootDocument);
			}

			//
			// Execute the actual processor
			final R result = rootDocumentProcessor.apply(rootDocument);

			//
			// Commit or remove it from cache if deleted
			if (rootDocument.isDeleted())
			{
				rootDocuments.invalidate(rootDocumentKey);
				changesCollector.collectDeleted(rootDocument.getDocumentPath());
			}
			else
			{
				commitRootDocument(rootDocument);
			}

			// Return the result
			return result;
		}
	}

	/**
	 * Creates a new root document.
	 *
	 * @param documentPath
	 * @return new root document (writable)
	 */
	private Document createRootDocument(final DocumentPath documentPath, final IDocumentChangesCollector changesCollector)
	{
		if (!documentPath.isNewDocument())
		{
			throw new InvalidDocumentPathException(documentPath, "new document ID was expected");
		}

		final WindowId windowId = documentPath.getWindowId();
		final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(windowId);
		assertNewDocumentAllowed(entityDescriptor);

		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		final Document document = documentsRepository.createNewDocument(entityDescriptor, Document.NULL, changesCollector);
		// NOTE: we assume document is writable
		// NOTE: we are not adding it to index. That shall be done on "commit".
		return document;
	}

	private void assertNewDocumentAllowed(final DocumentEntityDescriptor entityDescriptor)
	{
		final ILogicExpression allowExpr = entityDescriptor.getAllowCreateNewLogic();
		final LogicExpressionResult allow = allowExpr.evaluateToResult(userSession.toEvaluatee(), OnVariableNotFound.ReturnNoResult);
		if (allow.isFalse())
		{
			throw new AdempiereException("Create not allowed");
		}
	}

	/** Retrieves document from repository */
	private Document retrieveRootDocumentFromRepository(final DocumentKey documentKey)
	{
		final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(documentKey.getWindowId());

		if (documentKey.getDocumentId().isNew())
		{
			throw new InvalidDocumentPathException("documentId cannot be NEW");
		}

		final Document document = DocumentQuery.ofRecordId(entityDescriptor, documentKey.getDocumentId())
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.retriveDocumentOrNull();
		if (document == null)
		{
			throw new DocumentNotFoundException(documentKey.getDocumentPath());
		}

		return document;
	}

	public void cacheReset()
	{
		// TODO: invalidate only those which are: 1. NOT new; 2. NOT currently editing
		rootDocuments.invalidateAll();
		rootDocuments.cleanUp();
	}

	private void commitRootDocument(@NonNull final Document rootDocument)
	{
		Preconditions.checkState(rootDocument.isRootDocument(), "{} is not a root document", rootDocument);

		final boolean wasNew = rootDocument.isNew();

		//
		// Try saving it if possible
		rootDocument.saveIfValidAndHasChanges();

		//
		// Make sure all included detail (tab) statuses are up2date.
		// IMPORTANT: we have to do this after saving because some of the logics depends on if they are any new included documents or not
		rootDocument.updateIncludedDetailsStatus();

		//
		// Add the saved and changed document back to index
		final DocumentKey rootDocumentKey = DocumentKey.of(rootDocument);
		rootDocuments.put(rootDocumentKey, rootDocument.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance));
		addToTableName2WindowIdsCache(rootDocument.getEntityDescriptor());

		//
		// Make sure all events were collected for the case when we just created the new document
		// FIXME: this is a workaround and in case we find out all events were collected, we just need to remove this.
		if (wasNew)
		{
			logger.debug("Checking if we collected all events for the new document");
			final Set<String> collectedFieldNames = rootDocument.getChangesCollector().collectFrom(rootDocument, () -> "new document, initially missed");
			if (!collectedFieldNames.isEmpty())
			{
				logger.warn("We would expect all events to be auto-magically collected but it seems that not all of them were collected!"
						+ "\n Missed (but collected now) field names were: {}" //
						+ "\n Document path: {}", collectedFieldNames, rootDocument.getDocumentPath());
			}
		}

	}

	public void delete(final DocumentPath documentPath, final IDocumentChangesCollector changesCollector)
	{
		if (documentPath.isRootDocument())
		{
			final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(documentPath);
			assertDeleteDocumentAllowed(entityDescriptor);
		}

		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();
		if (rootDocumentPath.isNewDocument())
		{
			throw new InvalidDocumentPathException(rootDocumentPath);
		}

		forRootDocumentWritable(rootDocumentPath, changesCollector, rootDocument -> {
			if (documentPath.isRootDocument())
			{
				if (!rootDocument.isNew())
				{
					rootDocument.deleteFromRepository();
				}

				rootDocument.markAsDeleted();
			}
			else if (documentPath.hasIncludedDocuments())
			{
				rootDocument.deleteIncludedDocuments(documentPath.getDetailId(), documentPath.getRowIds());
			}
			else
			{
				throw new InvalidDocumentPathException(documentPath);
			}

			return null; // nothing to return
		});
	}

	private void assertDeleteDocumentAllowed(final DocumentEntityDescriptor entityDescriptor)
	{
		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put(WindowConstants.FIELDNAME_Processed, false)
				.build()
				.andComposeWith(userSession.toEvaluatee());
		final ILogicExpression allowExpr = entityDescriptor.getAllowDeleteLogic();
		final LogicExpressionResult allow = allowExpr.evaluateToResult(evalCtx, OnVariableNotFound.ReturnNoResult);
		if (allow.isFalse())
		{
			throw new AdempiereException("Delete not allowed");
		}
	}

	public void deleteAll(final List<DocumentPath> documentPaths, final IDocumentChangesCollector changesCollector)
	{
		// FIXME: i think we shall refactor this method and make sure that "deleteAll" is atomic

		for (final DocumentPath documentPath : documentPaths)
		{
			delete(documentPath, changesCollector);
		}
	}

	public TableRecordReference getTableRecordReference(final DocumentPath documentPath)
	{
		return documentDescriptorFactory.getTableRecordReference(documentPath);
	}

	public WindowId getWindowId(@NonNull final DocumentZoomIntoInfo zoomIntoInfo)
	{
		if (zoomIntoInfo.getWindowId() != null)
		{
			return zoomIntoInfo.getWindowId();
		}

		final RecordZoomWindowFinder zoomWindowFinder;
		if (zoomIntoInfo.isRecordIdPresent())
		{
			zoomWindowFinder = RecordZoomWindowFinder.newInstance(zoomIntoInfo.getTableName(), zoomIntoInfo.getRecordId());
		}
		else
		{
			zoomWindowFinder = RecordZoomWindowFinder.newInstance(zoomIntoInfo.getTableName());
		}
		final int zoomInto_adWindowId = zoomWindowFinder.findAD_Window_ID();
		if (zoomInto_adWindowId <= 0)
		{
			throw new EntityNotFoundException("No windowId found")
					.setParameter("zoomIntoInfo", zoomIntoInfo);
		}

		return WindowId.of(zoomInto_adWindowId);
	}

	public boolean isValidDocumentPath(final DocumentPath documentPath)
	{
		return documentPath != null
				&& documentPath.getWindowId().isInt()
				&& documentPath.getDocumentId().isInt();
	}

	/**
	 * Retrieves document path for given ZoomInto info.
	 *
	 * @param zoomIntoInfo
	 */
	public DocumentPath getDocumentPath(@NonNull final DocumentZoomIntoInfo zoomIntoInfo)
	{
		if (!zoomIntoInfo.isRecordIdPresent())
		{
			throw new IllegalArgumentException("recordId must be set in " + zoomIntoInfo);
		}

		//
		// Find the root window ID
		final WindowId zoomIntoWindowIdEffective = getWindowId(zoomIntoInfo);

		final DocumentEntityDescriptor rootEntityDescriptor = getDocumentEntityDescriptor(zoomIntoWindowIdEffective);
		final String zoomIntoTableName = zoomIntoInfo.getTableName();

		//
		// We are dealing with a root document
		// (i.e. root descriptor's table is matching record's table)
		if (Objects.equals(rootEntityDescriptor.getTableName(), zoomIntoTableName))
		{
			final DocumentId rootDocumentId = DocumentId.of(zoomIntoInfo.getRecordId());
			return DocumentPath.rootDocumentPath(zoomIntoWindowIdEffective, rootDocumentId);
		}
		//
		// We are dealing with an included document
		else
		{
			// Search the root descriptor for any child entity descriptor which would match record's TableName
			final List<DocumentEntityDescriptor> childEntityDescriptors = rootEntityDescriptor.getIncludedEntities().stream()
					.filter(includedEntityDescriptor -> Objects.equals(includedEntityDescriptor.getTableName(), zoomIntoTableName))
					.collect(ImmutableList.toImmutableList());
			if (childEntityDescriptors.isEmpty())
			{
				throw new EntityNotFoundException("Cannot find the detail tab to zoom into")
						.setParameter("zoomIntoInfo", zoomIntoInfo)
						.setParameter("zoomIntoWindowId", zoomIntoWindowIdEffective)
						.setParameter("rootEntityDescriptor", rootEntityDescriptor);
			}
			else if (childEntityDescriptors.size() > 1)
			{
				logger.warn("More then one child descriptors matched our root descriptor. Picking the fist one. \nRoot descriptor: {} \nChild descriptors: {}", rootEntityDescriptor, childEntityDescriptors);
			}
			//
			final DocumentEntityDescriptor childEntityDescriptor = childEntityDescriptors.get(0);

			// Find the root DocumentId
			final DocumentId rowId = DocumentId.of(zoomIntoInfo.getRecordId());
			final DocumentId rootDocumentId = DocumentQuery.ofRecordId(childEntityDescriptor, rowId)
					.retrieveParentDocumentId(rootEntityDescriptor);

			//
			return DocumentPath.includedDocumentPath(zoomIntoWindowIdEffective, rootDocumentId, childEntityDescriptor.getDetailId(), rowId);
		}
	}

	public DocumentPrint createDocumentPrint(final DocumentPath documentPath)
	{
		final Document document = getDocumentReadonly(documentPath);
		final int windowNo = document.getWindowNo();
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();

		final int printProcessId = entityDescriptor.getPrintProcessId();
		final TableRecordReference recordRef = getTableRecordReference(documentPath);

		final ProcessExecutionResult processExecutionResult = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(printProcessId)
				.setWindowNo(windowNo) // important: required for ProcessInfo.findReportingLanguage
				.setRecord(recordRef)
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				//
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.switchContextWhenRunning()
				.executeSync()
				.getResult();

		return DocumentPrint.builder()
				.filename(processExecutionResult.getReportFilename())
				.reportContentType(processExecutionResult.getReportContentType())
				.reportData(processExecutionResult.getReportData())
				.build();
	}

	public DocumentWebsocketPublisher getWebsocketPublisher()
	{
		return websocketPublisher;
	}

	/**
	 * Invalidates all root documents identified by tableName/recordId and notifies frontend (via websocket).
	 *
	 * @param tableName
	 * @param recordId
	 */
	public void invalidateDocumentByRecordId(final String tableName, final int recordId)
	{
		//
		// Create possible documentKeys for given tableName/recordId
		final DocumentId documentId = DocumentId.of(recordId);
		final Set<DocumentKey> documentKeys = getCachedWindowIdsForTableName(tableName)
				.stream()
				.map(windowId -> DocumentKey.of(windowId, documentId))
				// .filter(documentKey -> rootDocuments.getIfPresent(documentKey) != null) // not needed
				.collect(ImmutableSet.toImmutableSet());

		// stop here if no document keys found
		if (documentKeys.isEmpty())
		{
			return;
		}

		//
		// Invalidate the root documents
		rootDocuments.invalidateAll(documentKeys);

		//
		// Notify frontend
		documentKeys.forEach(documentKey -> websocketPublisher.staleRootDocument(documentKey.getWindowId(), documentKey.getDocumentId()));
	}

	public void invalidateIncludedDocumentsByRecordId(final String tableName, final int recordId, final String childTableName, final int childRecordId)
	{
		final DocumentId documentId = DocumentId.of(recordId);
		final DocumentId rowId = childRecordId > 0 ? DocumentId.of(childRecordId) : null;

		final Function<DocumentEntityDescriptor, DocumentPath> toDocumentPath;
		if (rowId != null)
		{
			toDocumentPath = includedEntity -> DocumentPath.includedDocumentPath(includedEntity.getWindowId(), documentId, includedEntity.getDetailId(), rowId);
		}
		else
		{
			// all rows for given tab/detail
			toDocumentPath = includedEntity -> DocumentPath.includedDocumentPath(includedEntity.getWindowId(), documentId, includedEntity.getDetailId());
		}

		//
		// Create possible documentKeys for given tableName/recordId
		final ImmutableSet<DocumentPath> documentPaths = getCachedWindowIdsForTableName(tableName)
				.stream()
				.map(this::getDocumentEntityDescriptor)
				.flatMap(rootEntity -> rootEntity.streamIncludedEntitiesByTableName(childTableName))
				.map(toDocumentPath)
				.collect(ImmutableSet.toImmutableSet());

		documentPaths.forEach(this::invalidateIncludedDocuments);
	}

	private void invalidateIncludedDocuments(final DocumentPath documentPath)
	{
		Check.assume(!documentPath.isRootDocument(), "included document path: {}", documentPath);

		//
		// Get the root document if exists
		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();
		final DocumentKey documentKey = DocumentKey.ofRootDocumentPath(rootDocumentPath);
		final Document document = rootDocuments.getIfPresent(documentKey);

		// Invalidate
		if (document != null)
		{
			try (final IAutoCloseable lock = document.lockForWriting())
			{
				document.getIncludedDocumentsCollection(documentPath.getDetailId()).markStale(documentPath.getSingleRowId());
			}
		}

		//
		// Notify frontend, even if the root document does not exist (or it was not cached).
		websocketPublisher.staleByDocumentPath(documentPath);
	}

	/**
	 * Invalidates all root documents identified by tableName/recordId and notifies frontend (via websocket).
	 *
	 * @param tableName
	 * @param recordId
	 */
	public void invalidateRootDocument(@NonNull final DocumentPath documentPath)
	{
		final DocumentKey documentKey = DocumentKey.ofRootDocumentPath(documentPath);

		//
		// Invalidate the root documents
		rootDocuments.invalidate(documentKey);

		//
		// Notify frontend
		websocketPublisher.staleByDocumentPath(documentPath);
	}

	public Document duplicateDocument(final DocumentPath fromDocumentPath)
	{
		// NOTE: assume running out of transaction

		// Clone the document in transaction.
		// One of the reasons of doing this is because for some documents there are events which are triggered on each change (but on trx commit).
		// If we would run out of transaction, those events would be triggered 10k times.
		// e.g. copying the AD_Role. Each time a record like AD_Window_Access is created, the UserRolePermissionsEventBus.fireCacheResetEvent() is called.
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final DocumentPath toDocumentPath = trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> duplicateDocumentInTrx(fromDocumentPath));

		return getDocumentReadonly(toDocumentPath);
	}

	private DocumentPath duplicateDocumentInTrx(final DocumentPath fromDocumentPath)
	{
		// NOTE: assume it's already running in transaction

		final TableRecordReference fromRecordRef = getTableRecordReference(fromDocumentPath);

		final Object fromModel = fromRecordRef.getModel(PlainContextAware.newWithThreadInheritedTrx());
		final String tableName = InterfaceWrapperHelper.getModelTableName(fromModel);
		final PO fromPO = InterfaceWrapperHelper.getPO(fromModel);

		final PO toPO = TableModelLoader.instance.newPO(Env.getCtx(), tableName, ITrx.TRXNAME_ThreadInherited);
		toPO.setDynAttribute(PO.DYNATTR_CopyRecordSupport, CopyRecordFactory.getCopyRecordSupport(tableName)); // set "getValueToCopy" advisor
		PO.copyValues(fromPO, toPO, true);
		InterfaceWrapperHelper.save(toPO);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(tableName);
		childCRS.setAD_Window_ID(fromDocumentPath.getAD_Window_ID(-1));
		childCRS.setParentPO(toPO);
		childCRS.setBase(true);
		childCRS.copyRecord(fromPO, ITrx.TRXNAME_ThreadInherited);

		final DocumentPath toDocumentPath = DocumentPath.rootDocumentPath(fromDocumentPath.getWindowId(), DocumentId.of(toPO.get_ID()));
		return toDocumentPath;
	}

	public BoilerPlateContext createBoilerPlateContext(final DocumentPath documentPath)
	{
		if (documentPath == null)
		{
			return BoilerPlateContext.EMPTY;
		}

		final Document document = getDocumentReadonly(documentPath);
		final SourceDocument sourceDocument = new DocumentAsTemplateSourceDocument(document);
		return MADBoilerPlate.createEditorContext(sourceDocument);
	}

	@AllArgsConstructor
	private static final class DocumentAsTemplateSourceDocument implements SourceDocument
	{
		@NonNull
		private final Document document;

		@Override
		public boolean hasFieldValue(final String fieldName)
		{
			return document.hasField(fieldName);
		}

		@Override
		public Object getFieldValue(final String fieldName)
		{
			return document.getFieldView(fieldName).getValue();
		}

		@Override
		public int getFieldValueAsInt(final String fieldName, final int defaultValue)
		{
			return document.getFieldView(fieldName).getValueAsInt(defaultValue);
		}
	}

	@Immutable
	@Value
	@Builder
	public static final class DocumentPrint
	{
		@NonNull
		private final String filename;
		@NonNull
		private final String reportContentType;
		@NonNull
		private final byte[] reportData;
	}

	@Immutable
	private static final class DocumentKey
	{
		public static final DocumentKey of(final Document document)
		{
			final DocumentPath documentPath = document.getDocumentPath();
			return ofRootDocumentPath(documentPath);
		}

		public static final DocumentKey ofRootDocumentPath(final DocumentPath documentPath)
		{
			if (!documentPath.isRootDocument())
			{
				throw new InvalidDocumentPathException(documentPath, "shall be a root document path");
			}
			if (documentPath.isNewDocument())
			{
				throw new InvalidDocumentPathException(documentPath, "document path for creating new documents is not allowed");
			}
			return new DocumentKey(documentPath.getDocumentType(), documentPath.getDocumentTypeId(), documentPath.getDocumentId());
		}

		public static final DocumentKey of(@NonNull final WindowId windowId, @NonNull final DocumentId documentId)
		{
			return new DocumentKey(DocumentType.Window, windowId.toDocumentId(), documentId);
		}

		private final DocumentType documentType;
		private final DocumentId documentTypeId;
		private final DocumentId documentId;

		private Integer _hashcode = null;

		private DocumentKey(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
		{
			super();
			this.documentType = Preconditions.checkNotNull(documentType, "documentType");
			this.documentTypeId = Preconditions.checkNotNull(documentTypeId, "documentTypeId");
			this.documentId = Preconditions.checkNotNull(documentId, "documentId");
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("type", documentType)
					.add("typeId", documentTypeId)
					.add("documentId", documentId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			if (_hashcode == null)
			{
				_hashcode = Objects.hash(documentType, documentTypeId, documentId);
			}
			return _hashcode;
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (!(obj instanceof DocumentKey))
			{
				return false;
			}

			final DocumentKey other = (DocumentKey)obj;
			return Objects.equals(documentType, other.documentType)
					&& Objects.equals(documentTypeId, other.documentTypeId)
					&& Objects.equals(documentId, other.documentId);
		}

		public WindowId getWindowId()
		{
			Check.assume(documentType == DocumentType.Window, "documentType shall be {} but it was {}", DocumentType.Window, documentType);
			return WindowId.of(documentTypeId);
		}

		public DocumentId getDocumentId()
		{
			return documentId;
		}

		public DocumentPath getDocumentPath()
		{
			return DocumentPath.rootDocumentPath(documentType, documentTypeId, documentId);
		}
	} // DocumentKey
}
