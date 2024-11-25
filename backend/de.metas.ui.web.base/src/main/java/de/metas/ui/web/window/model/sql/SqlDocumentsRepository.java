package de.metas.ui.web.window.model.sql;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.model.POCacheSourceModel;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
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
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * IMPORTANT: please make sure this is state-less and thread-safe
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class SqlDocumentsRepository implements DocumentsRepository
{
	public static final transient SqlDocumentsRepository instance = new SqlDocumentsRepository();

	private static final transient Logger logger = LogManager.getLogger(SqlDocumentsRepository.class);

	private static final String VERSION_DEFAULT = "0";

	private static final String SYSCONFIG_LoadLimitWarn = "webui.documents.LoadLimitWarn";
	private static final int DEFAULT_LoadLimitWarn = 250;

	private static final String SYSCONFIG_LoadLimitMax = "webui.documents.LoadLimitMax";
	private static final int DEFAULT_LoadLimitMax = 300;

	private SqlDocumentsRepository()
	{
	}

	private int getLoadLimitWarn()
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_LoadLimitWarn, DEFAULT_LoadLimitWarn);
	}

	private int getLoadLimitMax()
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_LoadLimitMax, DEFAULT_LoadLimitMax);
	}

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
					logger.warn("Reached load count MAXIMUM level. Stop loading. \n SQL: {} \n loadCount: {}"
									+ "\n To change this limit check {} sysconfig.",
							sql, loadCount, SYSCONFIG_LoadLimitMax);
					break;
				}

				// WARN if we reached the Warning limit
				if (!loadLimitWarnReported && loadLimitWarn > 0 && loadCount >= loadLimitWarn)
				{
					logger.warn("Reached load count Warning level. Continue loading. \n SQL: {} \n loadCount: {}"
									+ "\n To change this limit check {} sysconfig.",
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

	@FunctionalInterface
	private interface FieldValueSupplier
	{
		/**
		 * @param fieldDescriptor
		 * @return initial value or {@link DocumentValuesSupplier#NO_VALUE} if it cannot provide a value
		 */
		Object getValue(final DocumentFieldDescriptor fieldDescriptor);
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
				final Object idObj = getValue(idFields.get(0));
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
	public void refresh(final Document document)
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
	public SaveResult save(final Document document)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		assertThisRepository(document.getEntityDescriptor());
		DocumentPermissionsHelper.assertCanEdit(document);

		// Runnables to be executed after the PO is saved
		final List<Runnable> afterSaveRunnables = new ArrayList<>();

		//
		// Load the PO / Create new PO instance
		final PO po = retrieveOrCreatePO(document);

		//
		// Set values to PO
		final boolean isNew = document.isNew();
		boolean changes = false;
		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			if (!isNew && !documentField.hasChangesToSave())
			{
				logger.trace("Skip setting PO value because document field has no changes: {}", documentField);
				continue;
			}

			if (DocumentFieldWidgetType.Labels == documentField.getWidgetType())
			{
				// save labels after PO is saved because we want to make sure it's not new (so we can link to it)
				afterSaveRunnables.add(() -> saveLabels(document, documentField));
			}

			if (setPOValue(po, documentField))
			{
				changes = true;
			}
		}

		//
		// Save the PO
		boolean needsRefresh = false;
		if (changes)
		{
			//
			// Actual save
			// TODO: advice the PO to not reload after save.
			InterfaceWrapperHelper.save(po);
			document.markAsNotNew();
			needsRefresh = true;
		}
		else
		{
			logger.trace("Skip saving {} because there was no actual change", po);
		}

		//
		// Execute after save runnables
		if (!afterSaveRunnables.isEmpty())
		{
			afterSaveRunnables.forEach(r -> r.run());
			needsRefresh = true;
		}

		//
		// Reload the document
		boolean deleted = false;
		if (needsRefresh)
		{
			final SqlDocumentEntityDataBindingDescriptor dataBinding = document.getEntityDescriptor().getDataBinding(SqlDocumentEntityDataBindingDescriptor.class);
			final DocumentId idNew = extractDocumentId(po, dataBinding);
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

	private DocumentId extractDocumentId(final PO po, SqlDocumentEntityDataBindingDescriptor dataBinding)
	{
		if (dataBinding.isSingleKey())
		{
			return DocumentId.of(InterfaceWrapperHelper.getId(po));
		}
		else
		{
			final List<SqlDocumentFieldDataBindingDescriptor> keyFields = dataBinding.getKeyFields();
			if (keyFields.isEmpty())
			{
				throw new AdempiereException("No primary key defined for " + dataBinding.getTableName());
			}

			final List<Object> keyParts = keyFields.stream()
					.map(keyField -> po.get_Value(keyField.getColumnName()))
					.collect(ImmutableList.toImmutableList());

			return DocumentId.ofComposedKeyParts(keyParts);
		}
	}

	private PO retrieveOrCreatePO(final Document document)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(document.getEntityDescriptor().getDataBinding());
		final String sqlTableName = dataBinding.getTableName();

		//
		// Load the PO / Create new PO instance
		final PO po;
		if (document.isNew())
		{
			po = TableModelLoader.instance.newPO(document.getCtx(), sqlTableName, ITrx.TRXNAME_ThreadInherited);
		}
		else if (dataBinding.isSingleKey())
		{
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(document.getCtx(), sqlTableName, document.getDocumentIdAsInt(), checkCache, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			po = toQueryBuilder(dataBinding, document.getDocumentId())
					.create()
					.firstOnly(PO.class);
		}

		if (po == null)
		{
			throw new DBException("No PO found for " + document);
		}

		//
		//
		po.set_ManualUserAction(document.getWindowNo());
		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(po, true); // allow changing any columns

		//
		final TableRecordReference rootRecordReference = extractRootRecordReference(document);
		POCacheSourceModel.setRootRecordReference(po, rootRecordReference);

		return po;
	}

	private static TableRecordReference extractRootRecordReference(final Document includedDocument)
	{
		if (includedDocument.isRootDocument())
		{
			return null;
		}

		final Document rootDocument = includedDocument.getRootDocument();
		final String rootTableName = rootDocument.getEntityDescriptor().getTableNameOrNull();
		if (rootTableName == null)
		{
			return null;
		}

		final int rootRecordId = rootDocument.getDocumentId().toIntOr(-1);
		if (rootRecordId < 0)
		{
			return null;
		}

		return TableRecordReference.of(rootTableName, rootRecordId);
	}

	private static IQueryBuilder<Object> toQueryBuilder(final SqlDocumentEntityDataBindingDescriptor dataBinding, final DocumentId documentId)
	{
		final String tableName = dataBinding.getTableName();

		final List<SqlDocumentFieldDataBindingDescriptor> keyFields = dataBinding.getKeyFields();
		final int keyFieldsCount = keyFields.size();
		if (keyFieldsCount == 0)
		{
			throw new AdempiereException("No primary key defined for " + tableName);
		}
		final List<Object> keyParts = documentId.toComposedKeyParts();
		if (keyFieldsCount != keyParts.size())
		{
			throw new AdempiereException("Invalid documentId '" + documentId + "'. It shall have " + keyFieldsCount + " parts but it has " + keyParts.size());
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName, PlainContextAware.newWithThreadInheritedTrx());
		for (int i = 0; i < keyFieldsCount; i++)
		{
			final SqlDocumentFieldDataBindingDescriptor keyField = keyFields.get(i);
			final String keyColumnName = keyField.getColumnName();
			final Object keyValue = convertValueToPO(keyParts.get(i), keyColumnName, keyField.getWidgetType(), keyField.getSqlValueClass());
			queryBuilder.addEqualsFilter(keyColumnName, keyValue);
		}

		return queryBuilder;
	}

	/**
	 * Sets PO's value from given <code>documentField</code>.
	 *
	 * @param po
	 * @param documentField
	 * @return true if value was set and really changed
	 */
	private static boolean setPOValue(final PO po, final IDocumentFieldView documentField)
	{
		final DocumentFieldDataBindingDescriptor dataBinding = documentField.getDescriptor().getDataBinding().orElse(null);
		if (dataBinding == null)
		{
			logger.trace("Skip setting PO's column because it has no databinding: {}", documentField);
			return false;
		}

		final POInfo poInfo = po.getPOInfo();
		final String columnName = dataBinding.getColumnName();

		final int poColumnIndex = poInfo.getColumnIndex(columnName);
		if (poColumnIndex < 0)
		{
			logger.trace("Skip setting PO's column because it's missing: {} -- PO={}", columnName, po);
			return false;
		}

		//
		// Virtual column => skip setting it
		if (poInfo.isVirtualColumn(poColumnIndex))
		{
			logger.trace("Skip setting PO's virtual column: {} -- PO={}", columnName, po);
			return false; // no change
		}
		//
		// ID
		else if (poInfo.isKey(poColumnIndex))
		{
			final int id = documentField.getValueAsInt(-1);
			if (id >= 0)
			{
				final int idOld = po.get_ValueAsInt(poColumnIndex);
				if (id == idOld)
				{
					logger.trace("Skip setting PO's key column because it's the same as the old value: {} (old={}), PO={}", columnName, idOld, po);
					return false; // no change
				}

				final boolean idSet = po.set_ValueNoCheck(columnName, id);
				if (!idSet)
				{
					throw new AdempiereException("Failed setting ID=" + id + " to " + po);
				}

				logger.trace("Setting PO ID: {}={} -- PO={}", columnName, id, po);
				return true;
			}
			else
			{
				logger.trace("Skip setting PO's key column: {} -- PO={}", columnName, po);
				return false; // no change
			}
		}
		//
		// Created/Updated columns
		else if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(columnName))
		{
			logger.trace("Skip setting PO's created/updated column: {} -- PO={}", columnName, po);
			return false; // no change
		}
		//
		// Regular column
		else
		{
			//
			// Check if value was changed, compared with PO's current value
			final Object poValue = po.get_Value(poColumnIndex);
			final Class<?> poValueClass = poInfo.getColumnClass(poColumnIndex);
			final Object fieldValueConv = convertValueToPO(documentField.getValue(), columnName, documentField.getWidgetType(), poValueClass);
			if (poFieldValueEqual(fieldValueConv, poValue))
			{
				logger.trace("Skip setting PO's column because it was not changed: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			//
			// Check if the field value was changed from when we last queried it
			if (!po.is_new())
			{
				final Object fieldInitialValueConv = convertValueToPO(documentField.getInitialValue(), columnName, documentField.getWidgetType(), poValueClass);
				if (!poFieldValueEqual(fieldInitialValueConv, poValue))
				{
					throw new AdempiereException("Document's field was changed from when we last queried it. Please re-query."
							+ "\n Document field initial value: " + fieldInitialValueConv
							+ "\n PO value: " + poValue
							+ "\n Document field: " + documentField
							+ "\n PO: " + po);
				}
			}

			// TODO: handle not updatable columns... i think we shall set them only if the PO is new

			// NOTE: at this point we shall not do any other validations like "mandatory but null", value min/max range check,
			// because we shall rely completely on Document level validations and not duplicate the logic here.

			//
			// Try setting the value
			final boolean valueSet = po.set_ValueOfColumn(columnName, fieldValueConv);
			if (!valueSet)
			{
				logger.warn("Failed setting PO's column: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			logger.trace("Setting PO value: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
			return true;
		}
	}

	/**
	 * @return true if PO field's values can be considered the same
	 */
	private static boolean poFieldValueEqual(final Object value1, final Object value2)
	{
		if (value1 == value2)
		{
			return true;
		}

		// If both values are empty we can consider they are equal
		// (see task https://github.com/metasfresh/metasfresh-webui-api/issues/276)
		if (isEmptyPOFieldValue(value1) && isEmptyPOFieldValue(value2))
		{
			return true;
		}

		return DataTypes.equals(value1, value2);
	}

	private static boolean isEmptyPOFieldValue(final Object value)
	{
		if (value == null)
		{
			return true;
		}
		else if (value instanceof String)
		{
			return ((String)value).isEmpty();
		}
		else
		{
			return false;
		}
	}

	public static Object convertValueToPO(final Object value, final String columnName, final DocumentFieldWidgetType widgetType, final Class<?> targetClass)
	{
		return SqlValueConverters.convertToPOValue(value, columnName, widgetType, targetClass);
	}

	@Override
	public void delete(final Document document)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();
		assertThisRepository(document.getEntityDescriptor());
		DocumentPermissionsHelper.assertCanEdit(document, UserSession.getCurrentPermissions());

		if (document.isNew())
		{
			throw new IllegalArgumentException("Cannot delete new document: " + document);
		}

		final PO po = retrieveOrCreatePO(document);

		InterfaceWrapperHelper.delete(po);
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

	private static void saveLabels(final Document document, final IDocumentFieldView documentField)
	{
		final LabelsLookup lookup = LabelsLookup.cast(documentField.getDescriptor().getLookupDescriptor().orElse(null));

		final int linkId = document.getFieldView(lookup.getLinkColumnName()).getValueAsInt(-1);
		final Set<String> listValuesInDatabase = lookup.retrieveExistingValuesByLinkId(linkId).getKeysAsString();

		final LookupValuesList lookupValuesList = documentField.getValueAs(LookupValuesList.class);
		final HashSet<String> listValuesToSave = lookupValuesList != null ? new HashSet<>(lookupValuesList.getKeysAsString()) : new HashSet<>();

		//
		// Delete removed labels
		{
			final HashSet<String> listValuesToDelete = new HashSet<>(listValuesInDatabase);
			listValuesToDelete.removeAll(listValuesToSave);
			if (!listValuesToDelete.isEmpty())
			{
				final int countDeleted = lookup.queryValueRecordsByLinkId(linkId)
						.addInArrayFilter(lookup.getLabelsValueColumnName(), lookup.normalizeStringIds(listValuesToDelete))
						.create()
						.delete();
				if (countDeleted != listValuesToDelete.size())
				{
					logger.warn("Possible issue while deleting labels for linkId={}: listValuesToDelete={}, countDeleted={}", linkId, listValuesToDelete, countDeleted);
				}
			}
		}

		//
		// Create new labels
		{
			final Set<String> listValuesToSaveEffective = new HashSet<>(listValuesToSave);
			listValuesToSaveEffective.removeAll(listValuesInDatabase);
			listValuesToSaveEffective.forEach(listValueToSave -> createLabelPORecord(listValueToSave, linkId, lookup));
		}
	}

	private static void createLabelPORecord(
			@NonNull final String listValue,
			final int linkId,
			@NonNull final LabelsLookup lookup)
	{
		final PO labelPO = TableModelLoader.instance.newPO(lookup.getLabelsTableName());
		labelPO.set_ValueNoCheck(lookup.getLabelsLinkColumnName(), linkId);
		labelPO.set_ValueNoCheck(lookup.getLabelsValueColumnName(), listValue);
		InterfaceWrapperHelper.save(labelPO);
	}
}
