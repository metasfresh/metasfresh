package de.metas.ui.web.window.model.sql;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryFilter;
import de.metas.ui.web.window.model.DocumentRepository;
import de.metas.ui.web.window.model.DocumentSideListView;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.IDocumentSideListView;
import de.metas.ui.web.window_old.model.ModelPropertyDescriptorValueTypeHelper;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
@Repository
public class SqlDocumentRepository implements DocumentRepository
{
	private static final transient Logger logger = LogManager.getLogger(SqlDocumentRepository.class);

	/* package */ SqlDocumentRepository()
	{
		super();
	}

	private int getNextId(final DocumentEntityDescriptor entityDescriptor)
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final String tableName = entityDescriptor.getDataBinding().getTableName();
		final int nextId = DB.getNextID(adClientId, tableName, ITrx.TRXNAME_ThreadInherited);
		if (nextId <= 0)
		{
			throw new DBException("Cannot retrieve next ID from database for " + entityDescriptor);
		}

		logger.trace("Acquired next ID={} for {}", nextId, entityDescriptor);
		return nextId;
	}

	@Override
	public List<Document> retriveDocuments(final DocumentQuery query)
	{
		final int limit = -1;
		return retriveDocuments(query, limit);
	}

	public List<Document> retriveDocuments(final DocumentQuery query, final int limit)
	{
		logger.debug("Retrieving records: query={}, limit={}", query, limit);

		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final Document parentDocument = query.getParentDocument();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(sqlParams, query);
		logger.debug("Retrieving records: SQL={} -- {}", sql, sqlParams);

		final List<Document> documentsCollector = new ArrayList<>(limit + 1);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final Document document = retriveDocument(entityDescriptor, parentDocument, rs);
				documentsCollector.add(document);

				// Stop if we reached the limit
				if (limit > 0 && documentsCollector.size() > limit)
				{
					break;
				}
			}
		}
		catch (final Exception e)
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
	public Document retriveDocument(final DocumentQuery query)
	{
		final int limit = 2;
		final List<Document> documents = retriveDocuments(query, limit);
		if (documents.isEmpty())
		{
			return null;
		}
		else if (documents.size() > 1)
		{
			throw new DBMoreThenOneRecordsFoundException("More than one record found for " + query + " on " + this
					+ "\n First " + limit + " records: " + Joiner.on("\n").join(documents));
		}
		else
		{
			return documents.get(0);
		}
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument)
	{
		final int documentId = getNextId(entityDescriptor);
		return Document.builder()
				.setDocumentRepository(this)
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.setDocumentIdSupplier(() -> documentId)
				.initializeAsNewDocument()
				.build();
	}

	private final String buildSql(final List<Object> sqlParams, final DocumentQuery query)
	{
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(query.getEntityDescriptor().getDataBinding());
		final POInfo poInfo = entityBinding.getPOInfo();

		final StringBuilder sql = new StringBuilder();

		//
		// SELECT ... FROM ...
		final String adLanguage = Env.getAD_Language(Env.getCtx()); // TODO: introduce DocumentRepositoryQuery.getAD_Language()
		sql.append(entityBinding.getSqlSelectAllFrom(adLanguage));

		//
		// WHERE
		final String sqlWhereClause = buildSqlWhereClause(sqlParams, query, poInfo);
		if (!Strings.isNullOrEmpty(sqlWhereClause))
		{
			sql.append("\n WHERE ").append(sqlWhereClause);
		}

		//
		// ORDER BY
		final String sqlOrderBy = entityBinding.getSqlOrderBy();
		if (!Check.isEmpty(sqlOrderBy))
		{
			sql.append("\n ORDER BY ").append(sqlOrderBy);
		}
		
		//
		// LIMIT/OFFSET
		final int firstRow = query.getFirstRow();
		if(firstRow > 0)
		{
			sql.append("\n OFFSET ").append(firstRow);
		}
		final int pageLength = query.getPageLength();
		if(pageLength > 0)
		{
			sql.append("\n LIMIT ").append(pageLength);
		}

		return sql.toString();
	}

	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentQuery query, final POInfo poInfo)
	{
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());

		final StringBuilder sqlWhereClause = new StringBuilder();

		//
		// Entity's WHERE clause
		{
			final IStringExpression entityWhereClauseExpression = entityBinding.getSqlWhereClause();
			final Evaluatee evalCtx = query.getEvaluationContext();
			final String entityWhereClause = entityWhereClauseExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
			if (!Check.isEmpty(entityWhereClause, true))
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append("\n AND ");
				}
				sqlWhereClause.append(" /* entity where clause */ ");
				sqlWhereClause.append("(").append(entityWhereClause).append(")");
			}
		}

		//
		// Key column
		if (query.isRecordIdSet())
		{
			final String sqlKeyColumnName = entityBinding.getSqlKeyColumnName();
			if (sqlKeyColumnName == null)
			{
				throw new AdempiereException("Failed building where clause for " + query + " because there is no Key Column defined in " + entityBinding);
			}

			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(" /* key */ ");
			sqlWhereClause.append(sqlKeyColumnName).append("=?");
			sqlParams.add(query.getRecordId());
		}

		//
		// Parent link where clause (if any)
		final String sqlParentLinkColumnName = entityBinding.getSqlParentLinkColumnName();
		if (sqlParentLinkColumnName != null)
		{
			if (query.isParentLinkIdSet())
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append("\n AND ");
				}
				sqlWhereClause.append(" /* parent link */ ");
				sqlWhereClause.append(sqlParentLinkColumnName).append("=?");
				sqlParams.add(query.getParentLinkId());
			}
			else if (!query.isRecordIdSet())
			{
				throw new AdempiereException("Query shall filter at least by recordId or parentLinkId: " + query);
			}
		}

		for (final DocumentQueryFilter filter : query.getFilters())
		{
			final String sqlFilter = buildSqlWhereClause(sqlParams, filter, entityDescriptor, poInfo);
			if (Check.isEmpty(sqlFilter, true))
			{
				continue;
			}

			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(" /* filter */ ( ").append(sqlFilter).append(" )");
		}

		return sqlWhereClause.toString();
	}

	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentQueryFilter filter, final DocumentEntityDescriptor entityDescriptor, final POInfo poInfo)
	{
		// FIXME: refactor and introduce DocumentQueryFilter Descriptor and SQL DataBinding
		// TODO: improve SQL logic

		final String fieldName = filter.getFieldName();
		final DocumentFieldDescriptor field = entityDescriptor.getField(fieldName);
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = SqlDocumentFieldDataBindingDescriptor.cast(field.getDataBinding());

		final String sqlColumn = fieldBinding.getSqlColumnSql();
		final String columnName = fieldBinding.getColumnName();
		final Class<?> targetClass = poInfo.getColumnClass(columnName);
		final Object sqlValue = convertValueToPO(filter.getValue(), columnName, targetClass);
		final Object sqlValueTo = convertValueToPO(filter.getValueTo(), columnName, targetClass);

		if (sqlValueTo == null)
		{
			if (sqlValue == null)
			{
				return "(" + sqlColumn + ") IS NULL";
			}

			sqlParams.add(sqlValue);
			return "(" + sqlColumn + ") = ?";
		}
		else
		{
			if (sqlValue == null)
			{
				sqlParams.add(sqlValueTo);
				return "(" + sqlColumn + ") <= ?";
			}

			sqlParams.add(sqlValue);
			sqlParams.add(sqlValueTo);
			return "(" + sqlColumn + ") BETWEEN ? AND ?";
		}
	}

	private Document retriveDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final ResultSet rs)
	{
		return Document.builder()
				.setDocumentRepository(this)
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.setDocumentIdSupplier(() -> retrieveDocumentId(entityDescriptor.getIdField(), rs))
				.initializeAsExistingRecord((fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, rs))
				.build();
	}

	private int retrieveDocumentId(final DocumentFieldDescriptor idFieldDescriptor, final ResultSet rs)
	{
		final Integer valueInt = (Integer)retrieveDocumentFieldValue(idFieldDescriptor, rs);
		return valueInt;
	}

	/*
	 * Based on org.compiere.model.GridTable.readData(ResultSet).
	 */
	private Object retrieveDocumentFieldValue(final DocumentFieldDescriptor fieldDescriptor, final ResultSet rs) throws DBException
	{
		try
		{
			final SqlDocumentFieldDataBindingDescriptor fieldDataBinding = SqlDocumentFieldDataBindingDescriptor.cast(fieldDescriptor.getDataBinding());

			final String columnName = fieldDataBinding.getSqlColumnName();
			final Class<?> valueClass = fieldDescriptor.getValueClass();
			boolean decryptRequired = fieldDataBinding.isEncrypted();

			final Object value;

			if (fieldDataBinding.isUsingDisplayColumn())
			{
				final String displayName = rs.getString(fieldDataBinding.getDisplayColumnName());
				if (fieldDataBinding.isNumericKey())
				{
					final int id = rs.getInt(columnName);
					value = rs.wasNull() ? null : IntegerLookupValue.of(id, displayName);
				}
				else
				{
					final String key = rs.getString(columnName);
					value = rs.wasNull() ? null : StringLookupValue.of(key, displayName);
				}
				decryptRequired = false;
			}
			else if (java.lang.String.class == valueClass)
			{
				value = rs.getString(columnName);
			}
			else if (java.lang.Integer.class == valueClass)
			{
				final int valueInt = rs.getInt(columnName);
				value = rs.wasNull() ? null : valueInt;
			}
			else if (java.math.BigDecimal.class == valueClass)
			{
				value = rs.getBigDecimal(columnName);
			}
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				final Timestamp valueTS = rs.getTimestamp(columnName);
				value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
			}
			// YesNo
			else if (Boolean.class == valueClass)
			{
				String valueStr = rs.getString(columnName);
				if (decryptRequired)
				{
					valueStr = decrypt(valueStr).toString();
					decryptRequired = false;
				}

				value = ModelPropertyDescriptorValueTypeHelper.convertToBoolean(valueStr);
			}
			// LOB
			else if (byte[].class == valueClass)
			{
				final Object valueObj = rs.getObject(columnName);
				final byte[] valueBytes;
				if (rs.wasNull())
				{
					valueBytes = null;
				}
				else if (valueObj instanceof Clob)
				{
					final Clob lob = (Clob)valueObj;
					final long length = lob.length();
					valueBytes = lob.getSubString(1, (int)length).getBytes();
				}
				else if (valueObj instanceof Blob)
				{
					final Blob lob = (Blob)valueObj;
					final long length = lob.length();
					valueBytes = lob.getBytes(1, (int)length);
				}
				else if (valueObj instanceof String)
				{
					valueBytes = ((String)valueObj).getBytes();
				}
				else if (valueObj instanceof byte[])
				{
					valueBytes = (byte[])valueObj;
				}
				else
				{
					logger.warn("Unknown LOB value '{}' for {}. Considering it null.", valueObj, fieldDataBinding);
					valueBytes = null;
				}
				//
				value = valueBytes;
			}
			else
			{
				value = rs.getString(columnName);
			}

			// Decrypt if needed
			final Object valueDecrypted = decryptRequired ? decrypt(value) : value;

			logger.trace("Retrived value for {}: {} ({})", fieldDataBinding, valueDecrypted, valueDecrypted == null ? "no class" : valueDecrypted.getClass());

			return valueDecrypted;
		}
		catch (final SQLException e)
		{
			throw new DBException("Failed retrieving the value for " + fieldDescriptor, e);
		}
	}

	private static final Object decrypt(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return SecureEngine.decrypt(value);
	}	// decrypt

	@Override
	public void refresh(final Document document)
	{
		refresh(document, document.getDocumentId());
	}

	private void refresh(final Document document, final int documentId)
	{
		logger.debug("Refreshing: {}, using ID={}", document, documentId);

		final DocumentQuery query = DocumentQuery.ofRecordId(document.getEntityDescriptor(), documentId);

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(sqlParams, query);
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
				final ResultSet rsFinal = rs;
				document.refresh((fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, rsFinal));
			}
			else
			{
				throw new AdempiereException("No data found while trying to reload the document: " + document);
			}

			if (rs.next())
			{
				throw new AdempiereException("More than one record found while trying to reload document: " + document);
			}
		}
		catch (final Exception e)
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

		//
		// Load the PO / Create new PO instance
		final PO po = retrieveOrCreatePO(document);

		// TODO: handle the case of composed primary key!
		if (po.getPOInfo().getKeyColumnName() == null)
		{
			throw new UnsupportedOperationException("Composed primary key is not supported");
		}

		//
		// Set values to PO
		final boolean isNew = document.isNew();
		boolean changes = false;
		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			if (!isNew && !documentField.hasChanges())
			{
				logger.trace("Skip setting PO value because document field has no changes: {}", documentField);
				continue;
			}

			if (setPOValue(po, documentField))
			{
				changes = true;
			}
		}

		if (!changes)
		{
			logger.trace("Skip saving {} because there was no actual change", po);
			return;
		}

		//
		// Actual save
		// TODO: advice the PO to not reload after save.
		InterfaceWrapperHelper.save(po);
		document.markAsNotNew();

		//
		// Reload the document
		final int idNew = InterfaceWrapperHelper.getId(po);
		refresh(document, idNew);
	}

	private PO retrieveOrCreatePO(final Document document)
	{
		final String sqlTableName = document.getEntityDescriptor().getDataBinding().getTableName();

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
			po = TableModelLoader.instance.getPO(document.getCtx(), sqlTableName, document.getDocumentId(), checkCache, ITrx.TRXNAME_ThreadInherited);

			if (po == null)
			{
				throw new DBException("No PO found for " + document);
			}
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
	private boolean setPOValue(final PO po, final IDocumentFieldView documentField)
	{
		final POInfo poInfo = po.getPOInfo();
		final String columnName = documentField.getDescriptor().getDataBinding().getColumnName();

		//
		// Virtual column => skip setting it
		if (poInfo.isVirtualColumn(columnName))
		{
			logger.trace("Skip setting PO's virtual column: {} -- PO={}", columnName, po);
			return false; // no change
		}
		//
		// ID
		else if (poInfo.isKey(columnName))
		{
			final int id = documentField.getValueAsInt(-1);
			if (id >= 0)
			{
				final int idOld = po.get_ValueAsInt(columnName);
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
			final Object poValue = po.get_Value(columnName);
			final Class<?> poValueClass = poInfo.getColumnClass(columnName);
			final Object fieldValueConv = convertValueToPO(documentField.getValue(), columnName, poValueClass);
			if (DataTypes.equals(fieldValueConv, poValue))
			{
				logger.trace("Skip setting PO's column because it was not changed: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			//
			// Check if the field value was changed from when we last queried it
			if (!po.is_new())
			{
				final Object fieldInitialValueConv = convertValueToPO(documentField.getInitialValue(), columnName, poValueClass);
				if (!DataTypes.equals(fieldInitialValueConv, poValue))
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
			final boolean valueSet = po.set_ValueOfColumnReturningBoolean(columnName, fieldValueConv);
			if (!valueSet)
			{
				logger.warn("Failed setting PO's column: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			logger.trace("Setting PO value: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
			return true;
		}
	}

	private static Object convertValueToPO(final Object value, final String columnName, final Class<?> targetClass)
	{
		final Class<?> valueClass = value == null ? null : value.getClass();

		if (valueClass != null && targetClass.isAssignableFrom(valueClass))
		{
			return value;
		}
		else if (int.class.equals(targetClass) || Integer.class.equals(targetClass))
		{
			if (value == null)
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
		}
		else if (String.class.equals(targetClass))
		{
			if (value == null)
			{
				return null;
			}
			else if (LookupValue.class.isAssignableFrom(valueClass))
			{
				return ((LookupValue)value).getIdAsString();
			}
		}
		else if (Timestamp.class.equals(targetClass))
		{
			if (value == null)
			{
				return null;
			}
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				return new Timestamp(((java.util.Date)value).getTime());
			}
		}
		else if (Boolean.class.equals(targetClass) || boolean.class.equals(targetClass))
		{
			if (value == null)
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

		if (document.isNew())
		{
			throw new IllegalArgumentException("Cannot delete new document: " + document);
		}

		final PO po = retrieveOrCreatePO(document);

		InterfaceWrapperHelper.delete(po);
	}

	@Override
	public List<IDocumentSideListView> retrieveDocumentsSideList(final DocumentQuery query)
	{
		logger.debug("Retrieving records: query={}", query);
		
		final int firstRow = query.getFirstRow();
		final int pageLength = query.getPageLength();
		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(sqlParams, query);
		logger.debug("Retrieving records: SQL={} -- {}", sql, sqlParams);

		final List<IDocumentSideListView> sideListDocuments = new ArrayList<>(pageLength + 1);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final IDocumentSideListView sideListDocument = retriveDocumentSideListView(entityDescriptor, rs);
				sideListDocuments.add(sideListDocument);

				// Stop if we reached the limit
				if (pageLength > 0 && sideListDocuments.size() > pageLength)
				{
					break;
				}
			}
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		logger.debug("Retrieved {} records.", sideListDocuments.size());
		return sideListDocuments;
	}

	private IDocumentSideListView retriveDocumentSideListView(final DocumentEntityDescriptor entityDescriptor, final ResultSet rs)
	{
		final List<DocumentFieldDescriptor> fieldDescriptors = entityDescriptor.getSideListFields();
		if (fieldDescriptors.isEmpty())
		{
			throw new IllegalStateException("No side list fields were defined by " + entityDescriptor);
		}

		final DocumentSideListView.Builder documentBuilder = DocumentSideListView.builder(entityDescriptor);
		for (final DocumentFieldDescriptor fieldDescriptor : fieldDescriptors)
		{
			final Object fieldValue = retrieveDocumentFieldValue(fieldDescriptor, rs);
			final Object jsonValue = Values.valueToJsonObject(fieldValue);

			if (fieldDescriptor.isKey())
			{
				documentBuilder.putKeyFieldValue(fieldDescriptor.getFieldName(), jsonValue);
			}
			else
			{
				documentBuilder.putFieldValue(fieldDescriptor.getFieldName(), jsonValue);
			}
		}

		return documentBuilder.build();
	}

}
