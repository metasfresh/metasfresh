package de.metas.ui.web.window.model.sql;

import com.google.common.base.Joiner;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.ui.web.window.model.sql.save.CompositeSaveHandlers;
import de.metas.ui.web.window.model.sql.save.SaveHandler;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.GridTabVO;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

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

/**
 * @implNote IMPORTANT: please make sure this is thread-safe
 */
@Component
@RequiredArgsConstructor
public final class SqlDocumentsRepository implements DocumentsRepository
{
	@NonNull private static final Logger logger = LogManager.getLogger(SqlDocumentsRepository.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final CompositeSaveHandlers saveHandlers;

	private static final String VERSION_DEFAULT = "0";

	private static final String SYSCONFIG_LoadLimitWarn = "webui.documents.LoadLimitWarn";
	private static final int DEFAULT_LoadLimitWarn = 250;

	private static final String SYSCONFIG_LoadLimitMax = "webui.documents.LoadLimitMax";
	private static final int DEFAULT_LoadLimitMax = 300;

	private int getLoadLimitWarn() {return sysConfigBL.getIntValue(SYSCONFIG_LoadLimitWarn, DEFAULT_LoadLimitWarn);}

	private int getLoadLimitMax() {return sysConfigBL.getIntValue(SYSCONFIG_LoadLimitMax, DEFAULT_LoadLimitMax);}

	private static DocumentId retrieveNextDocumentId(final DocumentEntityDescriptor entityDescriptor)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());

		final ClientId adClientId = UserSession.getCurrent().getClientId();
		final String tableName = dataBinding.getTableName();
		final int nextId = DB.getNextID(adClientId.getRepoId(), tableName, ITrx.TRXNAME_ThreadInherited);
		if (nextId <= 0)
		{
			throw new DBException("Cannot retrieve next ID from database for " + entityDescriptor);
		}

		logger.trace("Acquired next ID={} for {}", nextId, entityDescriptor);
		return DocumentId.of(nextId);
	}

	@Override
	public OrderedDocumentsList retrieveDocuments(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		final int limit = query.getPageLength();
		return retriveDocuments(query, limit, changesCollector);
	}

	public OrderedDocumentsList retriveDocuments(final DocumentQuery query, final int limit, final IDocumentChangesCollector changesCollector)
	{
		logger.debug("Retrieving records: query={}, limit={}", query, limit);

		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		assertThisRepository(entityDescriptor);
		final Document parentDocument = query.getParentDocument();
		final Function<DocumentId, Document> existingDocumentsSupplier = query.getExistingDocumentsSupplier();

		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final SqlAndParams sql = sqlBuilder.getSql();
		final String adLanguage = sqlBuilder.getAD_Language();
		final DocumentQueryOrderByList orderBys = sqlBuilder.getOrderBysEffective();
		logger.debug("Retrieving records: {}", sql);

		final int loadLimitWarn = getLoadLimitWarn();
		final int loadLimitMax = getLoadLimitMax();
		int maxRowsToFetch = limit;
		if (maxRowsToFetch <= 0)
		{
			maxRowsToFetch = loadLimitMax;
		}

		final OrderedDocumentsList documentsCollector = OrderedDocumentsList.newEmpty(orderBys);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.getSql(), ITrx.TRXNAME_ThreadInherited);
			if (maxRowsToFetch > 0)
			{
				pstmt.setMaxRows(maxRowsToFetch);
			}
			DB.setParameters(pstmt, sql.getSqlParams());
			rs = pstmt.executeQuery();

			boolean loadLimitWarnReported = false;
			while (rs.next())
			{
				final ResultSetDocumentValuesSupplier documentValuesSupplier = new ResultSetDocumentValuesSupplier(entityDescriptor, adLanguage, rs);

				Document document = null;
				if (existingDocumentsSupplier != null)
				{
					final DocumentId documentId = documentValuesSupplier.getDocumentId();
					document = existingDocumentsSupplier.apply(documentId);
				}
				if (document == null)
				{
					document = Document.builder(entityDescriptor)
							.setParentDocument(parentDocument)
							.setChangesCollector(changesCollector)
							.initializeAsExistingRecord(documentValuesSupplier);
				}
				documentsCollector.addDocument(document);

				final int loadCount = documentsCollector.size();

				// Stop if we reached the limit
				if (limit > 0 && loadCount >= limit)
				{
					break;
				}

				// Stop if we reached the MAXIMUM limit
				if (loadLimitMax > 0 && loadCount >= loadLimitMax)
				{
					logger.warn("""
									Reached load count MAXIMUM level. Stop loading.\s
									 SQL: {}\s
									 loadCount: {}
									 To change this limit check {} sysconfig.""",
							sql, loadCount, SYSCONFIG_LoadLimitMax);
					break;
				}

				// WARN if we reached the Warning limit
				if (!loadLimitWarnReported && loadLimitWarn > 0 && loadCount >= loadLimitWarn)
				{
					logger.warn("""
									Reached load count Warning level. Continue loading.\s
									 SQL: {}\s
									 loadCount: {}
									 To change this limit check {} sysconfig.""",
							sql, loadCount, SYSCONFIG_LoadLimitWarn);
					loadLimitWarnReported = true;
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.getSql(), sql.getSqlParams());
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		logger.debug("Retrieved {} records.", documentsCollector.size());
		return documentsCollector;
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		final int limit = 2;
		final OrderedDocumentsList documents = retriveDocuments(query, limit, changesCollector);
		if (documents.isEmpty())
		{
			return null;
		}
		else if (documents.size() > 1)
		{
			throw new DBMoreThanOneRecordsFoundException("More than one record found for " + query + " on " + this
					+ "\n First " + limit + " records: " + Joiner.on("\n").join(documents.toList()));
		}
		else
		{
			return documents.get(0);
		}
	}

	@Override
	public DocumentId retrieveParentDocumentId(final DocumentEntityDescriptor parentEntityDescriptor, final DocumentQuery childDocumentQuery)
	{
		final SqlAndParams sql = SqlDocumentQueryBuilder.of(childDocumentQuery)
				.getSqlSelectParentId(parentEntityDescriptor);

		final int parentRecordId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql.getSql(), sql.getSqlParams());
		if (parentRecordId < 0)
		{
			throw new EntityNotFoundException("Parent documentId was not found")
					.setParameter("parentEntityDescriptor", parentEntityDescriptor)
					.setParameter("childDocumentQuery", childDocumentQuery)
					.setParameter("sql", sql);
		}

		return DocumentId.of(parentRecordId);
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final IDocumentChangesCollector changesCollector)
	{
		assertThisRepository(entityDescriptor);
		// TODO: check permissions if we can create a new record

		final DocumentId documentId = retrieveNextDocumentId(entityDescriptor);

		return Document.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(changesCollector)
				.initializeAsNewDocument(documentId, VERSION_DEFAULT);
	}

	private static final class ResultSetDocumentValuesSupplier implements DocumentValuesSupplier
	{
		private static final AtomicInteger _nextMissingId = new AtomicInteger(-10000);

		private final DocumentEntityDescriptor entityDescriptor;
		private final String adLanguage;
		private final ResultSet rs;

		private boolean idAquired = false;
		private DocumentId id;

		private String version;

		public ResultSetDocumentValuesSupplier(@NonNull final DocumentEntityDescriptor entityDescriptor, final String adLanguage, @NonNull final ResultSet rs)
		{
			this.entityDescriptor = entityDescriptor;
			this.adLanguage = adLanguage;
			this.rs = rs;
		}

		@Override
		public DocumentId getDocumentId()
		{
			if (idAquired)
			{
				return id;
			}
			else
			{
				id = retrieveDocumentId();
				idAquired = true;
				return id;
			}
		}

		private DocumentId retrieveDocumentId()
		{
			final List<DocumentFieldDescriptor> idFields = entityDescriptor.getIdFields();
			if (idFields.isEmpty())
			{
				// FIXME: workaround to bypass the missing ID field for views
				final int idInt = _nextMissingId.decrementAndGet();
				return DocumentId.of(idInt);
			}
			else if (idFields.size() == 1)
			{
				final Object idObj = getValue(idFields.getFirst());
				return DocumentId.ofObject(idObj);
			}
			else
			{
				final List<Object> idParts = idFields.stream()
						.map(this::getValue)
						.collect(Collectors.toList());
				return DocumentId.ofComposedKeyParts(idParts);
			}
		}

		@Override
		public String getVersion()
		{
			if (version != null)
			{
				return version;
			}

			final DocumentFieldDescriptor versionField = entityDescriptor.getFieldOrNull(SqlDocumentEntityDataBindingDescriptor.FIELDNAME_Version);
			if (versionField == null)
			{
				version = VERSION_DEFAULT;
				return version;
			}
			else
			{
				final Instant versionDate = TimeUtil.asInstant(getValue(versionField));
				version = versionDate == null ? VERSION_DEFAULT : String.valueOf(versionDate.toEpochMilli());
				return version;
			}
		}

		@Override
		public Object getValue(final DocumentFieldDescriptor fieldDescriptor)
		{
			final SqlDocumentFieldDataBindingDescriptor fieldDataBinding = SqlDocumentFieldDataBindingDescriptor.castOrNull(fieldDescriptor.getDataBinding());

			// If there is no SQL databinding, we cannot provide a value
			if (fieldDataBinding == null)
			{
				return NO_VALUE;
			}

			final DocumentFieldValueLoader fieldValueLoader = fieldDataBinding.getDocumentFieldValueLoader();
			final boolean isDisplayColumnAvailable = true;
			final LookupDescriptor lookupDescriptor = fieldDescriptor.getLookupDescriptor().orElse(null);

			try
			{
				return fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable, adLanguage, lookupDescriptor);
			}
			catch (final SQLException e)
			{
				throw new DBException("Failed retrieving the value for " + fieldDescriptor + " using " + fieldValueLoader, e);
			}
		}
	}

	@Override
	public void refresh(final @NotNull Document document)
	{
		assertThisRepository(document.getEntityDescriptor());

		final RefreshResult result = refresh(document, document.getDocumentId());
		switch (result)
		{
			case MISSING:
				throw new DocumentNotFoundException(document.getDocumentPath());
			case OK:
				return;
			default:
				throw new AdempiereException("Unknow refresh result: " + result);
		}
	}

	public enum RefreshResult
	{
		OK, MISSING
	}

	private RefreshResult refresh(@NonNull final Document document, @NonNull final DocumentId documentId)
	{
		logger.debug("Refreshing: {}, using ID={}", document, documentId);
		if (documentId.isNew())
		{
			throw new AdempiereException("Invalid documentId to refresh: " + documentId);
		}

		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
		final DocumentQuery query = DocumentQuery.ofRecordId(entityDescriptor, documentId)
				.setChangesCollector(document.getChangesCollector())
				.build();

		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final SqlAndParams sql = sqlBuilder.getSql();
		final String adLanguage = sqlBuilder.getAD_Language();
		logger.debug("Retrieving records: {}", sql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.getSql(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sql.getSqlParams());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final ResultSetDocumentValuesSupplier fieldValueSupplier = new ResultSetDocumentValuesSupplier(entityDescriptor, adLanguage, rs);
				document.refreshFromSupplier(fieldValueSupplier);
			}
			else
			{
				// Document is no longer in our repository
				return RefreshResult.MISSING;
			}

			if (rs.next())
			{
				throw new AdempiereException("More than one record found while trying to reload document: " + document);
			}

			return RefreshResult.OK;
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.getSql(), sql.getSqlParams());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public SaveResult save(final @NotNull Document document)
	{
		trxManager.assertThreadInheritedTrxExists();
		assertThisRepository(document.getEntityDescriptor());
		DocumentPermissionsHelper.assertCanEdit(document);

		final SaveHandler.SaveResult handlerResult = saveHandlers.save(document);

		//
		// Reload the document
		boolean deleted = false;
		if (handlerResult.isNeedsRefresh())
		{
			final DocumentId idNew = handlerResult.getIdNew();
			final RefreshResult refreshResult = refresh(document, idNew);
			if (refreshResult == RefreshResult.MISSING)
			{
				deleted = true;
			}
		}

		//
		// Notify the parent document that one of it's children were saved
		if (!document.isRootDocument())
		{
			document.getParentDocument().onChildSaved(document);
		}

		//
		return deleted ? SaveResult.DELETED : SaveResult.SAVED;
	}

	public static Object convertValueToPO(final Object value, final String columnName, final DocumentFieldWidgetType widgetType, final Class<?> targetClass)
	{
		return SqlValueConverters.convertToPOValue(value, columnName, widgetType, targetClass);
	}

	@Override
	public void delete(@NonNull final Document document)
	{
		trxManager.assertThreadInheritedTrxExists();
		assertThisRepository(document.getEntityDescriptor());
		DocumentPermissionsHelper.assertCanEdit(document, UserSession.getCurrentPermissions());

		if (document.isNew())
		{
			throw new IllegalArgumentException("Cannot delete new document: " + document);
		}

		saveHandlers.delete(document);
	}

	@Override
	public String retrieveVersion(final DocumentEntityDescriptor entityDescriptor, final int documentIdAsInt)
	{
		final SqlDocumentEntityDataBindingDescriptor binding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());

		final String sql = binding.getSqlSelectVersionById()
				.orElseThrow(() -> new AdempiereException("Versioning is not supported for " + entityDescriptor));

		final Timestamp version = DB.getSQLValueTSEx(ITrx.TRXNAME_ThreadInherited, sql, documentIdAsInt);
		return version == null ? VERSION_DEFAULT : String.valueOf(version.getTime());
	}

	@Override
	public int retrieveLastLineNo(final DocumentQuery query)
	{
		logger.debug("Retrieving last LineNo: query={}", query);

		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		assertThisRepository(entityDescriptor);

		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final SqlAndParams sql = sqlBuilder.getSqlMaxLineNo();

		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql.getSql(), sql.getSqlParams());
	}

	public boolean isReadonly(@NonNull final GridTabVO gridTabVO)
	{
		return saveHandlers.isReadonly(gridTabVO);
	}
}
