/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.model.MADBoilerPlate.SourceDocument;
import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.ui.web.window.invalidation.DocumentToInvalidate;
import de.metas.ui.web.window.invalidation.IncludedDocumentToInvalidate;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Component
public class DocumentCollection
{
	private static final String SYSCONFIG_CACHE_SIZE = "de.metas.ui.web.window.model.DocumentCollection.CacheSize";
	private static final int DEFAULT_CACHE_SIZE = 800;

	private static final Logger logger = LogManager.getLogger(DocumentCollection.class);
	public static final AdMessageKey MSG_CLONING_NOT_ALLOWED_FOR_CURRENT_WINDOW = AdMessageKey.of("de.metas.ui.web.window.model.DocumentCollection.CloningNotAllowedForCurrentWindow");
	public static final AdMessageKey MSG_CREATE_NOT_ALLOWED = AdMessageKey.of(("de.metas.ui.web.window.model.DocumentCollection.CreateNotAllowed"));

	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final UserSession userSession;
	private final DocumentWebsocketPublisher websocketPublisher;
	private final CopyRecordService copyRecordService;

	private final Cache<DocumentKey, Document> rootDocuments;
	private final ConcurrentHashMap<String, Set<WindowId>> tableName2windowIds = new ConcurrentHashMap<>();

	/* package */ DocumentCollection(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final UserSession userSession,
			@NonNull final DocumentWebsocketPublisher websocketPublisher,
			@NonNull final CopyRecordService copyRecordService)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.userSession = userSession;
		this.websocketPublisher = websocketPublisher;
		this.copyRecordService = copyRecordService;

		// setup the cache
		final int cacheSize = Services
				.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_CACHE_SIZE, DEFAULT_CACHE_SIZE);

		rootDocuments = CacheBuilder
				.newBuilder()
				.maximumSize(cacheSize)
				.build();
	}

	public DocumentDescriptorFactory getDocumentDescriptorFactory()
	{
		return documentDescriptorFactory;
	}

	/**
	 * Delegates to the {@link DocumentDescriptorFactory#isWindowIdSupported(WindowId)} of this instance's {@code documentDescriptorFactory}.
	 */
	public boolean isWindowIdSupported(
			@Nullable final WindowId windowId)
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

	private void addToTableName2WindowIdsCache(final DocumentEntityDescriptor entityDescriptor)
	{
		final String tableName = entityDescriptor.getTableNameOrNull();
		if (tableName == null)
		{
			return;
		}

		final Set<WindowId> windowIds = tableName2windowIds.computeIfAbsent(tableName, k -> Collections.newSetFromMap(new ConcurrentHashMap<>()));
		windowIds.add(entityDescriptor.getWindowId());
	}

	private Set<WindowId> getCachedWindowIdsForTableName(final String tableName)
	{
		final Set<WindowId> windowIds = tableName2windowIds.get(tableName);
		return windowIds != null && !windowIds.isEmpty() ? ImmutableSet.copyOf(windowIds) : ImmutableSet.of();
	}

	public Document getDocumentReadonly(
			@NonNull final DocumentPath documentPath)
	{
		return forDocumentReadonly(documentPath, Function.identity());
	}

	public <R> R forDocumentReadonly(
			@NonNull final DocumentPath documentPath,
			@NonNull final Function<Document, R> documentProcessor)
	{
		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();

		return forRootDocumentReadonly(rootDocumentPath, rootDocument -> {
			if (documentPath.isRootDocument())
			{
				return documentProcessor.apply(rootDocument);
			}
			else if (documentPath.isSingleIncludedDocument())
			{
				final Document includedDocument = rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId())
						.orElseThrow(() -> new DocumentNotFoundException(documentPath));
				DocumentPermissionsHelper.assertCanView(includedDocument, UserSession.getCurrentPermissions());

				return documentProcessor.apply(includedDocument);
			}
			else
			{
				throw new InvalidDocumentPathException(documentPath);
			}
		});
	}

	private Document getOrLoadDocument(
			@NonNull final DocumentKey documentKey)
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

	public <R> R forRootDocumentReadonly(
			@NonNull final DocumentPath documentPath, final Function<Document, R> rootDocumentProcessor)
	{
		final DocumentKey rootDocumentKey = DocumentKey.ofRootDocumentPath(documentPath.getRootDocumentPath());

		try (@SuppressWarnings("unused") final IAutoCloseable readLock = getOrLoadDocument(rootDocumentKey).lockForReading())
		{
			final Document rootDocument = getOrLoadDocument(rootDocumentKey).copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
			DocumentPermissionsHelper.assertCanView(rootDocument, UserSession.getCurrentPermissions());

			return rootDocumentProcessor.apply(rootDocument);
		}
	}

	public <R> R forDocumentWritable(
			@NonNull final DocumentPath documentPath,
			@NonNull final IDocumentChangesCollector changesCollector,
			@NonNull final Function<Document, R> documentProcessor)
	{
		final DocumentPath rootDocumentPath = documentPath.getRootDocumentPath();
		return forRootDocumentWritable(
				rootDocumentPath,
				changesCollector,
				rootDocument -> {

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
						document = rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId())
								.orElseThrow(() -> new DocumentNotFoundException(documentPath));
						DocumentPermissionsHelper.assertCanEdit(rootDocument);
					}

					return documentProcessor.apply(document);
				});
	}

	public <R> R forRootDocumentWritable(
			@NonNull final DocumentPath documentPathOrNew,
			final IDocumentChangesCollector changesCollector,
			@NonNull final Function<Document, R> rootDocumentProcessor)
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

		try (@SuppressWarnings("unused") final IAutoCloseable writeLock = lockHolder.lockForWriting())
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
		@SuppressWarnings("UnnecessaryLocalVariable") final Document document = documentsRepository.createNewDocument(entityDescriptor, Document.NULL, changesCollector);
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
			throw new AdempiereException(MSG_CREATE_NOT_ALLOWED);
		}
	}

	/**
	 * Retrieves document from repository
	 */
	private Document retrieveRootDocumentFromRepository(
			@NonNull final DocumentKey documentKey)
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

	public String cacheReset(final boolean forgetNotSavedDocuments)
	{
		final String result;

		if (forgetNotSavedDocuments)
		{
			final long count = rootDocuments.size();

			rootDocuments.invalidateAll();

			result = "invalidate all " + count + " documents";
		}
		else
		{
			long countDocumentsWithChanges = 0;
			final List<DocumentKey> documentKeysToInvalidate = new ArrayList<>();
			for (final Map.Entry<DocumentKey, Document> entry : rootDocuments.asMap().entrySet())
			{
				final Document document = entry.getValue();
				if (document.hasChangesRecursivelly())
				{
					countDocumentsWithChanges++;
				}
				else
				{
					documentKeysToInvalidate.add(entry.getKey());
				}
			}

			rootDocuments.invalidateAll(documentKeysToInvalidate);

			result = "invalidate " + documentKeysToInvalidate.size() + " documents with no changes;"
					+ " skipped " + countDocumentsWithChanges + " documents with changes";
		}

		rootDocuments.cleanUp();

		logger.info("cacheReset: {}", result);
		return result;
	}

	private void commitRootDocument(
			@NonNull final Document rootDocument)
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
		//
		// IMPORTANT: This case happens when the document is not yet saved, but it's not the first time we are patching it.
		// e.g. we have a document with multiple mandatory fields, user is filling them one by one, after each change a PATCH is sent
		if (wasNew)
		{
			rootDocument.getChangesCollector().collectFrom(rootDocument, () -> "new document, initially missed");
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
				final BooleanWithReason isDeleteForbidden = rootDocument.isDeleteForbidden();
				if (isDeleteForbidden.isTrue())
				{
					throw new AdempiereException(isDeleteForbidden.getReason())
							.markAsUserValidationError();
				}

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

	private void assertDeleteDocumentAllowed(
			@NonNull final DocumentEntityDescriptor entityDescriptor)
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

	public boolean isValidDocumentPath(final DocumentPath documentPath)
	{
		return documentPath != null
				&& documentPath.getWindowId().isInt()
				&& documentPath.getDocumentId().isInt();
	}

	public DocumentWebsocketPublisher getWebsocketPublisher()
	{
		return websocketPublisher;
	}

	/**
	 * Invalidates all root documents identified by tableName/recordId and notifies frontend (via websocket).
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
		documentKeys.forEach(documentKey -> websocketPublisher.staleRootDocument(documentKey.getWindowId(), documentKey.getDocumentId(), true));
	}

	public void invalidateDocumentsByWindowId(
			@NonNull final WindowId windowId)

	{
		final ImmutableList<DocumentKey> documentKeys = rootDocuments.asMap().keySet()
				.stream()
				.filter(documentKey -> windowId.equals(documentKey.getWindowId()))
				.collect(ImmutableList.toImmutableList());
		if (documentKeys.isEmpty())
		{
			return;
		}
		rootDocuments.invalidateAll(documentKeys);
	}

	public void invalidateAll(final Collection<DocumentToInvalidate> documentToInvalidateList)
	{
		for (final DocumentToInvalidate documentToInvalidate : documentToInvalidateList)
		{
			invalidate(documentToInvalidate);
		}
	}

	private void invalidate(@NonNull final DocumentToInvalidate documentToInvalidate)
	{
		final ImmutableList<DocumentEntityDescriptor> entityDescriptors = getCachedWindowIdsForTableName(documentToInvalidate.getTableName())
				.stream()
				.map(this::getDocumentEntityDescriptor)
				.collect(ImmutableList.toImmutableList());
		if (entityDescriptors.isEmpty())
		{
			return;
		}

		final DocumentId rootDocumentId = documentToInvalidate.getDocumentId();

		for (final DocumentEntityDescriptor entityDescriptor : entityDescriptors)
		{
			final WindowId windowId = entityDescriptor.getWindowId();
			final DocumentKey rootDocumentKey = DocumentKey.of(windowId, rootDocumentId);
			final Document rootDocument = rootDocuments.getIfPresent(rootDocumentKey);
			if (rootDocument != null)
			{
				//
				// Invalidate included documents
				// NOTE: we do this even if we will have to invalidate the whole document because we want to collect the events for frontend.
				// Ideally would be to just invalidate the root document if that was required and frontend had to deal with it.
				final Collection<IncludedDocumentToInvalidate> includedDocumentsToInvalidate = documentToInvalidate.getIncludedDocuments();
				if (!includedDocumentsToInvalidate.isEmpty())
				{
					try (final IAutoCloseable ignored = rootDocument.lockForWriting())
					{
						for (final IncludedDocumentToInvalidate includedDocumentToInvalidate : includedDocumentsToInvalidate)
						{
							final DocumentIdsSelection includedRowIds = includedDocumentToInvalidate.toDocumentIdsSelection();
							if (includedRowIds.isEmpty())
							{
								continue;
							}

							for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntitiesByTableName(includedDocumentToInvalidate.getTableName()))
							{
								final DetailId detailId = Check.assumeNotNull(includedEntityDescriptor.getDetailId(), "Expected detailId not null");
								rootDocument.getIncludedDocumentsCollection(detailId).markStale(includedRowIds);
							}
						}
					}
				}

				//
				// Invalidate the root document
				// NOTE: avoid invalidating if the document is new (and not saved) because in that case we will lose the document and we will never be able to recover.
				// As a symptom the user will get 404 or similar in his browser and the document will vanish completely.
				if (documentToInvalidate.isInvalidateDocument() && !rootDocument.isNew())
				{
					rootDocuments.invalidate(rootDocumentKey);
				}
			}

			//
			// Notify frontend, even if the root document does not exist (or it was not cached).
			sendWebsocketChangeEvents(documentToInvalidate, entityDescriptor);
		}
	}

	private void sendWebsocketChangeEvents(
			@NonNull final DocumentToInvalidate documentToInvalidate,
			@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		final WindowId windowId = entityDescriptor.getWindowId();
		final DocumentId rootDocumentId = documentToInvalidate.getDocumentId();

		websocketPublisher.staleRootDocument(windowId, rootDocumentId);

		for (final IncludedDocumentToInvalidate includedDocumentToInvalidate : documentToInvalidate.getIncludedDocuments())
		{
			final DocumentIdsSelection includedRowIds = includedDocumentToInvalidate.toDocumentIdsSelection();
			if (includedRowIds.isEmpty())
			{
				continue;
			}

			for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntitiesByTableName(includedDocumentToInvalidate.getTableName()))
			{
				final DetailId detailId = includedEntityDescriptor.getDetailId();
				websocketPublisher.staleIncludedDocuments(windowId, rootDocumentId, detailId, includedRowIds);
			}
		}
	}

	/**
	 * Invalidates all root documents identified by tableName/recordId and notifies frontend (via websocket).
	 */
	public void invalidateRootDocument(
			@NonNull final DocumentPath documentPath)
	{
		final DocumentKey documentKey = DocumentKey.ofRootDocumentPath(documentPath);

		//
		// Invalidate the root documents
		rootDocuments.invalidate(documentKey);

		//
		// Notify frontend
		websocketPublisher.staleRootDocument(documentKey.getWindowId(), documentKey.getDocumentId());
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

		final CopyRecordRequest copyRecordRequest = CopyRecordRequest.builder()
				.customErrorIfCloneNotAllowed(MSG_CLONING_NOT_ALLOWED_FOR_CURRENT_WINDOW)
				.fromAdWindowId(fromDocumentPath.getAdWindowIdOrNull())
				.tableRecordReference(fromRecordRef)
				.build();

		final PO toPO = copyRecordService.copyRecord(copyRecordRequest);

		return DocumentPath.rootDocumentPath(fromDocumentPath.getWindowId(), DocumentId.of(toPO.get_ID()));
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
			final Object value = document.getFieldView(fieldName).getValue();
			return value instanceof LookupValue ? ((LookupValue)value).getId() : value;
		}

		/**
		 * @return the given {@code defaultValue} if this document does not have a field with the given {@code fieldName} or if the field does not have a value.
		 */
		@Override
		public int getFieldValueAsInt(final String fieldName, final int defaultValue)
		{
			if (!document.hasField(fieldName))
			{
				return defaultValue;
			}
			return document.getFieldView(fieldName).getValueAsInt(defaultValue);
		}
	}

	@Immutable
	private static final class DocumentKey
	{
		public static DocumentKey of(@NonNull final Document document)
		{
			final DocumentPath documentPath = document.getDocumentPath();
			return ofRootDocumentPath(documentPath);
		}

		public static DocumentKey ofRootDocumentPath(@NonNull final DocumentPath documentPath)
		{
			if (!documentPath.isRootDocument())
			{
				throw new InvalidDocumentPathException(documentPath, "shall be a root document path");
			}
			if (documentPath.isNewDocument())
			{
				throw new InvalidDocumentPathException(documentPath, "document path for creating new documents is not allowed");
			}

			return new DocumentKey(
					documentPath.getDocumentType(),
					documentPath.getDocumentTypeId(),
					documentPath.getDocumentId());
		}

		public static DocumentKey of(
				@NonNull final WindowId windowId,
				@NonNull final DocumentId documentId)
		{
			return new DocumentKey(DocumentType.Window, windowId.toDocumentId(), documentId);
		}

		private final DocumentType documentType;
		private final DocumentId documentTypeId;
		private final DocumentId documentId;

		private Integer _hashcode = null;

		private DocumentKey(
				@NonNull final DocumentType documentType,
				@NonNull final DocumentId documentTypeId,
				@NonNull final DocumentId documentId)
		{
			this.documentType = documentType;
			this.documentTypeId = documentTypeId;
			this.documentId = documentId;
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
