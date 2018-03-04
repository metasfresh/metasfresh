package de.metas.ui.web.window.model.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.DocumentPermissionsHelper;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.DocumentFieldValueLoader;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.DocumentValuesSupplier;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import lombok.NonNull;

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
 *
 * IMPORTANT: please make sure this is state-less and thread-safe
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class SqlDocumentsRepository implements DocumentsRepository
{
	public static final transient SqlDocumentsRepository instance = new SqlDocumentsRepository();

	private static final transient Logger logger = LogManager.getLogger(SqlDocumentsRepository.class);

	private static final String VERSION_DEFAULT = "0";

	private int loadLimitWarn = 100;
	private int loadLimitMax = 300;

	private SqlDocumentsRepository()
	{
		super();
	}

	private final void assertThisRepository(final DocumentEntityDescriptor entityDescriptor)
	{
		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		if (documentsRepository != this)
		{
			// shall not happen
			throw new IllegalArgumentException("Entity descriptor's repository is invalid: " + entityDescriptor
					+ "\n Expected: " + this
					+ "\n But it was: " + documentsRepository);
		}
	}

	public void setLoadLimitWarn(final int loadLimitWarn)
	{
		final int loadLimitWarnOld = this.loadLimitWarn;
		this.loadLimitWarn = loadLimitWarn;
		logger.warn("Changed LoadLimitWarn: {} -> {}", loadLimitWarnOld, this.loadLimitWarn);
	}

	public void setLoadLimitMax(final int loadLimitMax)
	{
		final int loadLimitMaxOld = this.loadLimitMax;
		this.loadLimitMax = loadLimitMax;
		logger.warn("Changed LoadLimitWarn: {} -> {}", loadLimitMaxOld, this.loadLimitMax);
	}

	private static DocumentId retrieveNextDocumentId(final DocumentEntityDescriptor entityDescriptor)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());

		final int adClientId = UserSession.getCurrent().getAD_Client_ID();
		final String tableName = dataBinding.getTableName();
		final int nextId = DB.getNextID(adClientId, tableName, ITrx.TRXNAME_ThreadInherited);
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

		final List<Object> sqlParams = new ArrayList<>();
		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final String sql = sqlBuilder.getSql(sqlParams);
		final String adLanguage = sqlBuilder.getAD_Language();
		logger.debug("Retrieving records: SQL={} -- {}", sql, sqlParams);

		final int loadLimitWarn = this.loadLimitWarn;
		final int loadLimitMax = this.loadLimitMax;
		int maxRowsToFetch = limit;
		if (maxRowsToFetch <= 0)
		{
			maxRowsToFetch = loadLimitMax;
		}

		final OrderedDocumentsList documentsCollector = OrderedDocumentsList.newEmpty(query.getOrderBys());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			if (maxRowsToFetch > 0)
			{
				pstmt.setMaxRows(maxRowsToFetch);
			}
			DB.setParameters(pstmt, sqlParams);
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
					logger.warn("Reached load count MAXIMUM level. Stop loading. \n SQL: {} \n SQL Params: {} \n loadCount: {}", sql, sqlParams, loadCount);
					break;
				}

				// WARN if we reached the Warning limit
				if (!loadLimitWarnReported && loadLimitWarn > 0 && loadCount >= loadLimitWarn)
				{
					logger.warn("Reached load count Warning level. Continue loading. \n SQL: {} \n SQL Params: {} \n loadCount: {}", sql, sqlParams, loadCount);
					loadLimitWarnReported = true;
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
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
			throw new DBMoreThenOneRecordsFoundException("More than one record found for " + query + " on " + this
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
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = SqlDocumentQueryBuilder.of(childDocumentQuery)
				.getSqlSelectParentId(sqlParams, parentEntityDescriptor);

		final int parentRecordId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
		if (parentRecordId < 0)
		{
			throw new EntityNotFoundException("Parent documentId was not found")
					.setParameter("parentEntityDescriptor", parentEntityDescriptor)
					.setParameter("childDocumentQuery", childDocumentQuery)
					.setParameter("sql", sql)
					.setParameter("sqlParams", sqlParams);
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
	private static interface FieldValueSupplier
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

		public ResultSetDocumentValuesSupplier(final DocumentEntityDescriptor entityDescriptor, final String adLanguage, final ResultSet rs)
		{
			super();
			Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");
			Check.assumeNotNull(rs, "Parameter rs is not null");
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

			final DocumentFieldDescriptor idField = entityDescriptor.getIdField();
			if (idField == null)
			{
				// FIXME: workaround to bypass the missing ID field for views
				final int idInt = _nextMissingId.decrementAndGet();
				id = DocumentId.of(idInt);
				idAquired = true;
				return id;
			}

			final Object idObj = getValue(idField);
			if (JSONNullValue.isNull(idObj))
			{
				throw new NullPointerException("Null id");
			}
			else if (idObj instanceof Number)
			{
				final int idInt = ((Number)idObj).intValue();
				id = DocumentId.of(idInt);
				idAquired = true;
				return id;
			}
			else if (idObj instanceof IntegerLookupValue)
			{
				final int idInt = ((IntegerLookupValue)idObj).getIdAsInt();
				id = DocumentId.of(idInt);
				idAquired = true;
				return id;
			}
			else if (idObj instanceof String)
			{
				id = DocumentId.of(idObj.toString());
				idAquired = true;
				return id;
			}
			else
			{
				throw new IllegalStateException("Cannot convert id value '" + idObj + "' (" + idObj.getClass() + ") to DocumentId");
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
				final java.util.Date versionDate = (java.util.Date)getValue(versionField);
				version = versionDate == null ? VERSION_DEFAULT : String.valueOf(versionDate.getTime());
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
			final LookupDescriptor lookupDescriptor = fieldDescriptor.getLookupDescriptor(LookupScope.DocumentField);

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

		refresh(document, document.getDocumentId());
	}

	private void refresh(final Document document, final DocumentId documentId)
	{
		logger.debug("Refreshing: {}, using ID={}", document, documentId);

		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
		final DocumentQuery query = DocumentQuery.ofRecordId(entityDescriptor, documentId)
				.setChangesCollector(document.getChangesCollector())
				.build();

		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = sqlBuilder.getSql(sqlParams);
		final String adLanguage = sqlBuilder.getAD_Language();
		logger.debug("Retrieving records: SQL={} -- {}", sql, sqlParams);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final ResultSetDocumentValuesSupplier fieldValueSupplier = new ResultSetDocumentValuesSupplier(entityDescriptor, adLanguage, rs);
				document.refreshFromSupplier(fieldValueSupplier);
			}
			else
			{
				// Document is no longer in our repository
				final DocumentPath documentPathEffective = document.getDocumentPath().withDocumentId(documentId);
				throw new DocumentNotFoundException(documentPathEffective);
			}

			if (rs.next())
			{
				throw new AdempiereException("More than one record found while trying to reload document: " + document);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public void save(final Document document)
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
		if (needsRefresh)
		{
			final DocumentId idNew = DocumentId.of(InterfaceWrapperHelper.getId(po));
			refresh(document, idNew);
		}

		//
		// Notify the parent document that one of it's children were saved
		if (!document.isRootDocument())
		{
			document.getParentDocument().onChildSaved(document);
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
		else
		{
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(document.getCtx(), sqlTableName, document.getDocumentIdAsInt(), checkCache, ITrx.TRXNAME_ThreadInherited);

			if (po == null)
			{
				throw new DBException("No PO found for " + document);
			}
		}

		// TODO: handle the case of composed primary key!
		if (po.getPOInfo().getKeyColumnName() == null)
		{
			throw new UnsupportedOperationException("Composed primary key is not supported for " + sqlTableName);
		}

		//
		//
		po.set_ManualUserAction(document.getWindowNo());
		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(po, true); // allow changing any columns

		return po;
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

			// TODO: handle not updateable columns... i think we shall set them only if the PO is new

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

	/** @return true if PO field's values can be considered the same */
	private static final boolean poFieldValueEqual(final Object value1, final Object value2)
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

	private static final boolean isEmptyPOFieldValue(final Object value)
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
		final Class<?> valueClass = JSONNullValue.isNull(value) ? null : value.getClass();

		if (valueClass != null && targetClass.isAssignableFrom(valueClass))
		{
			return value;
		}
		else if (int.class.equals(targetClass) || Integer.class.equals(targetClass))
		{
			if (JSONNullValue.isNull(value))
			{
				return null;
			}
			else if (LookupValue.class.isAssignableFrom(valueClass))
			{
				return ((LookupValue)value).getIdAsInt();
			}
			else if (Number.class.isAssignableFrom(valueClass))
			{
				return ((Number)value).intValue();
			}
			else if (String.class.equals(valueClass))
			{
				return Integer.parseInt((String)value);
			}
			else if (Map.class.isAssignableFrom(valueClass))
			{
				@SuppressWarnings("unchecked")
				final Map<String, Object> map = (Map<String, Object>)value;
				final IntegerLookupValue lookupValue = JSONLookupValue.integerLookupValueFromJsonMap(map);
				return lookupValue == null ? null : lookupValue.getIdAsInt();
			}
		}
		else if (String.class.equals(targetClass))
		{
			if (JSONNullValue.isNull(value))
			{
				return null;
			}
			else if (LookupValue.class.isAssignableFrom(valueClass))
			{
				return ((LookupValue)value).getIdAsString();
			}
			else if (Map.class.isAssignableFrom(valueClass))
			{
				@SuppressWarnings("unchecked")
				final Map<String, Object> map = (Map<String, Object>)value;
				final StringLookupValue lookupValue = JSONLookupValue.stringLookupValueFromJsonMap(map);
				return lookupValue == null ? null : lookupValue.getIdAsString();
			}
			else if(Password.class.isAssignableFrom(valueClass))
			{
				final Password password = (Password)value;
				return password.getAsString();
			}
		}
		else if (Timestamp.class.equals(targetClass))
		{
			if (JSONNullValue.isNull(value))
			{
				return null;
			}
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				return new Timestamp(((java.util.Date)value).getTime());
			}
			else if (value instanceof String)
			{
				final java.util.Date valueDate = JSONDate.fromJson(value.toString(), widgetType);
				return TimeUtil.asTimestamp(valueDate);
			}
		}
		else if (Boolean.class.equals(targetClass) || boolean.class.equals(targetClass))
		{
			if (JSONNullValue.isNull(value))
			{
				return false;
			}
			else if (String.class.equals(valueClass))
			{
				return DisplayType.toBoolean(value);
			}
			else if (StringLookupValue.class.isAssignableFrom(valueClass))
			{
				// Corner case: e.g. Posted column which is a List but the PO is handling it as boolean
				final StringLookupValue stringLookupValue = (StringLookupValue)value;
				final Boolean valueBoolean = DisplayType.toBoolean(stringLookupValue.getIdAsString(), null);
				if (valueBoolean != null)
				{
					return valueBoolean;
				}
			}
		}

		// Better return the original value and let the PO fail.
		return value;
		// throw new AdempiereException("Cannot convert value '" + value + "' from " + valueClass + " to " + targetClass
		// + "\n ColumnName: " + columnName
		// + "\n PO: " + po);
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

		final List<Object> sqlParams = new ArrayList<>();
		final SqlDocumentQueryBuilder sqlBuilder = SqlDocumentQueryBuilder.of(query);
		final String sql = sqlBuilder.getSqlMaxLineNo(sqlParams);

		return DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams);
	}

	private static final void saveLabels(final Document document, final IDocumentFieldView documentField)
	{
		final LabelsLookup lookup = LabelsLookup.cast(documentField.getDescriptor().getLookupDescriptor(LookupScope.DocumentField));

		final int linkId = document.getFieldView(lookup.getLinkColumnName()).getValueAsInt(-1);
		final Set<Object> listValuesInDatabase = lookup.retrieveExistingValues(linkId).getKeys();

        final LookupValuesList lookupValuesList = documentField.getValueAs(LookupValuesList.class);
        final Set<Object> listValuesToSave = lookupValuesList != null ? new HashSet<>(lookupValuesList.getKeys()) : new HashSet<>();

		//
		// Delete removed labels
		{
			final Set<Object> listValuesToDelete = new HashSet<>(listValuesInDatabase);
			listValuesToDelete.removeAll(listValuesToSave);
			if (!listValuesToDelete.isEmpty())
			{
				final int countDeleted = lookup.retrieveExistingValuesRecordQuery(linkId)
						.addInArrayFilter(lookup.getLabelsValueColumnName(), listValuesToDelete)
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
			final Set<Object> listValuesToSaveEffective = new HashSet<>(listValuesToSave);
			listValuesToSaveEffective.removeAll(listValuesInDatabase);
			listValuesToSaveEffective.forEach(listValueToSave -> createLabelPORecord(listValueToSave, linkId, lookup));
		}
	}

	private static final void createLabelPORecord(@NonNull final Object listValueObj, final int linkId, @NonNull final LabelsLookup lookup)
	{
		final String listValue = listValueObj.toString();
		final PO labelPO = TableModelLoader.instance.newPO(lookup.getLabelsTableName());
		labelPO.set_ValueNoCheck(lookup.getLabelsLinkColumnName(), linkId);
		labelPO.set_ValueNoCheck(lookup.getLabelsValueColumnName(), listValue);
		InterfaceWrapperHelper.save(labelPO);
	}
}
