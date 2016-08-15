package de.metas.ui.web.window.model.sql;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import org.compiere.util.Evaluatee;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentRepository;
import de.metas.ui.web.window.model.DocumentRepositoryQuery;
import de.metas.ui.web.window.model.IDocumentFieldView;
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

	@Override
	public List<Document> retriveDocuments(final DocumentRepositoryQuery query)
	{
		final int limit = -1;
		return retriveDocuments(query, limit);
	}

	public List<Document> retriveDocuments(final DocumentRepositoryQuery query, final int limit)
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
	public Document retriveDocument(final DocumentRepositoryQuery query)
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
		return Document.builder()
				.setDocumentRepository(this)
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.initializeAsNewDocument()
				.build();
	}

	private final String buildSql(final List<Object> sqlParams, final DocumentRepositoryQuery query)
	{
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(query.getEntityDescriptor().getDataBinding());

		final StringBuilder sql = new StringBuilder();

		//
		// SELECT ... FROM ...
		sql.append(entityBinding.getSqlSelectFrom());

		//
		// WHERE
		final String sqlWhereClause = buildSqlWhereClause(sqlParams, query);
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

		return sql.toString();
	}

	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentRepositoryQuery query)
	{
		final SqlDocumentEntityDataBindingDescriptor entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(query.getEntityDescriptor().getDataBinding());

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
				throw new RuntimeException("Failed building where clause for " + query + " because there is no Key Column defined in " + entityBinding);
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
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(" /* parent link */ ");
			sqlWhereClause.append(sqlParentLinkColumnName).append("=?");
			sqlParams.add(query.getParentLinkId());
		}

		return sqlWhereClause.toString();
	}

	private Document retriveDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final ResultSet rs)
	{
		return Document.builder()
				.setDocumentRepository(this)
				.setEntityDescriptor(entityDescriptor)
				.setParentDocument(parentDocument)
				.initializeAsExistingRecord((document, fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, rs))
				.build();
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

		final DocumentRepositoryQuery query = DocumentRepositoryQuery.ofRecordId(document.getEntityDescriptor(), documentId);

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
				document.refresh((document2, fieldDescriptor) -> retrieveDocumentFieldValue(fieldDescriptor, rsFinal));
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
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(ITrx.TRXNAME_ThreadInherited, (trxName) -> saveRecord0(document));
	}

	private void saveRecord0(final Document document)
	{
		//
		// Load the PO / Create new PO instance
		final PO po;
		if (document.isNew())
		{
			// new
			final String sqlTableName = InterfaceWrapperHelper.getModelTableName(document);
			po = TableModelLoader.instance.newPO(document.getCtx(), sqlTableName, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			final String sqlTableName = InterfaceWrapperHelper.getModelTableName(document);
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(document.getCtx(), sqlTableName, document.getDocumentId(), checkCache, ITrx.TRXNAME_ThreadInherited);
		}
		Check.assumeNotNull(po, "po is not null");
		po.set_ManualUserAction(document.getWindowNo());

		//
		// Set values to PO
		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			setPOValue(po, documentField);
		}

		//
		// Actual save
		InterfaceWrapperHelper.save(po);
		
		// TODO: save included documents if any
		// * first, update their parent link if needed
		// * save them
		// * refresh them AFTER header document (this one) is refreshed

		//
		// Reload the document
		final int idNew = InterfaceWrapperHelper.getId(po);
		refresh(document, idNew);
	}

	private void setPOValue(final PO po, final IDocumentFieldView documentField)
	{
		final POInfo poInfo = po.getPOInfo();
		final String columnName = SqlDocumentFieldDataBindingDescriptor.getSqlColumnName(documentField);

		if (poInfo.isVirtualColumn(columnName))
		{
			logger.trace("Skip setting PO's virtual column: {} -- PO={}", columnName, po);
			return;
		}
		else if (poInfo.isKey(columnName))
		{
			logger.trace("Skip setting PO's key column: {} -- PO={}", columnName, po);
			return;
		}
		else if (DocumentDescriptorFactory.COLUMNNAMES_CreatedUpdated.contains(columnName))
		{
			logger.trace("Skip setting PO's created/updated column: {} -- PO={}", columnName, po);
			return;
		}

		final Object valueOld = po.get_Value(columnName);
		final Object valueConv = convertValueToPO(documentField.getValue(), columnName, po);
		if (Objects.equals(valueConv, valueOld))
		{
			logger.trace("Skip setting PO's column because it was not changed: {}={} (old={}) -- PO={}", columnName, valueConv, valueOld, po);
			return;
		}

		final boolean valueSet = po.set_ValueOfColumnReturningBoolean(columnName, valueConv);
		if (!valueSet)
		{
			logger.warn("Failed setting PO's column: {}={} (old={}) -- PO={}", columnName, valueConv, valueOld, po);
			return;
		}

		logger.trace("Setting PO value: {}={} (old={}) -- PO={}", columnName, valueConv, valueOld, po);
	}

	private static Object convertValueToPO(final Object value, final String columnName, final PO po)
	{
		final Class<?> valueClass = value == null ? null : value.getClass();
		final Class<?> targetClass = po.getPOInfo().getColumnClass(columnName);

		if (valueClass != null && targetClass.isAssignableFrom(valueClass))
		{
			return value;
		}
		if (int.class.equals(targetClass) || Integer.class.equals(targetClass))
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

		// Better return the original value and let the PO fail.
		return value;
		// throw new AdempiereException("Cannot convert value '" + value + "' from " + valueClass + " to " + targetClass
		// + "\n ColumnName: " + columnName
		// + "\n PO: " + po);
	}

}
