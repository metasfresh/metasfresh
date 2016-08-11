package de.metas.ui.web.window.model.sql;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentField;
import de.metas.ui.web.window.model.DocumentRepository;
import de.metas.ui.web.window.model.DocumentRepositoryQuery;
import de.metas.ui.web.window_old.model.ModelPropertyDescriptorValueTypeHelper;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;
import de.metas.ui.web.window_old.shared.datatype.NullValue;

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

	private static final AtomicInteger nextWindowNo = new AtomicInteger(1);
	private static final AtomicInteger nextTemporaryId = new AtomicInteger(-1000);

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

		final List<Document> documentsCollector = new ArrayList<>();
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
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (logger.isTraceEnabled())
		{
			logger.trace("Retrieved {} records.", documentsCollector.size());
		}
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
		final int windowNo = nextWindowNo.incrementAndGet();
		final Document document = new Document(this, entityDescriptor, windowNo, parentDocument);

		//
		// Set default values
		for (final DocumentField documentField : document.getFields())
		{
			final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

			if (fieldDescriptor.isKey())
			{
				final int value = generateNextTemporaryId();
				documentField.setValue(value);
			}
			else
			{
				// TODO: optimize: here instead of IStringExpression we would need some generic expression which parses to a given type.
				final IStringExpression defaultValueExpression = fieldDescriptor.getDefaultValueExpression();
				try
				{
					String valueStr = defaultValueExpression.evaluate(document.asEvaluatee(), OnVariableNotFound.Fail);
					if (valueStr != null && valueStr.isEmpty())
					{
						valueStr = null;
					}

					documentField.setValue(valueStr);
					documentField.setInitialValue(valueStr);
				}
				catch (Exception e)
				{
					logger.warn("Failed evaluating default value expression {} for {}", defaultValueExpression, documentField, e);
				}
			}
		}

		document.updateAllDependencies();
		return document;
	}

	private int generateNextTemporaryId()
	{
		return nextTemporaryId.decrementAndGet();
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

		// TODO: implement LIMIT
		//
		// final String limit = buildSqlLimit(query);
		// if (!Strings.isNullOrEmpty(limit))
		// {
		// sql.append("\n LIMIT ").append(limit);
		// }

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
		if (query.getRecordId() != null)
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

	private Document retriveDocument(final DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final ResultSet rs) throws SQLException
	{
		final int windowNo = nextWindowNo.incrementAndGet();
		final Document document = new Document(this, entityDescriptor, windowNo, parentDocument);

		//
		// Retrieve main record values
		Object keyColumn_Value = null;
		for (final DocumentField documentField : document.getFields())
		{
			final DocumentFieldDescriptor fieldDescriptor = documentField.getDescriptor();

			final Object value = retrieveDocumentFieldValue(fieldDescriptor, rs);
			final Object valueNotNull = NullValue.makeNotNull(value);

			documentField.setValue(valueNotNull);
			documentField.setInitialValue(valueNotNull);

			if (fieldDescriptor.isKey())
			{
				keyColumn_Value = value;
			}
		}

		//
		// Update Mandatory, ReadOnly, Displayed properties
		document.updateAllDependencies();

		//
		// TODO Retrieved values from included data sources
		// final ModelDataSourceQuery query = ModelDataSourceQuery.builder()
		// .setParentLinkId(keyColumn_Value)
		// .build();
		// for (final Map.Entry<PropertyName, ModelDataSource> e : includedDataSources.entrySet())
		// {
		// final PropertyName propertyName = e.getKey();
		// final ModelDataSource dataSource = e.getValue();
		//
		// final LazyPropertyValuesListDTO dataSourceValue = dataSource.retrieveRecordsSupplier(query);
		// data.put(propertyName, dataSourceValue);
		// }

		return document;
	}

	/*
	 * Based on org.compiere.model.GridTable.readData(ResultSet)
	 */
	private Object retrieveDocumentFieldValue(final DocumentFieldDescriptor fieldDescriptor, final ResultSet rs) throws SQLException
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
				value = rs.wasNull() ? null : LookupValue.of(id, displayName);
			}
			else
			{
				final String key = rs.getString(columnName);
				value = rs.wasNull() ? null : LookupValue.of(key, displayName);
			}
			decryptRequired = false;
		}
		// else if (valueType == PropertyDescriptorValueType.Location)
		// {
		// // FIXME: implement it efficiently - see https://metasfresh.atlassian.net/browse/FRESH-287 - Precalculate C_Location display address
		// final int locationId = rs.getInt(columnName);
		// value = locationService.findLookupValueById(locationId);
		// encrypted = false;
		// }
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

		logger.trace("Retrived value for {}: {} ({})", fieldDataBinding, valueDecrypted, valueDecrypted == null ? "null" : valueDecrypted.getClass());

		return valueDecrypted;
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
	public void refresh(Document document)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void save(Document document)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
