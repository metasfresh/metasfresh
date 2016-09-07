package de.metas.ui.web.window.model.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.I_T_Query_Selection;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentView;
import de.metas.ui.web.window.model.IDocumentView;
import de.metas.ui.web.window.model.IDocumentViewSelection;

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

class SqlDocumentViewSelection implements IDocumentViewSelection
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(SqlDocumentViewSelection.class);

	private final DocumentEntityDescriptor entityDescriptor;
	private final List<DocumentFieldDescriptor> fieldDescriptors;

	private final String querySelectionUUID;
	private final String sql;
	private final int size;

	private transient String _toString;

	private SqlDocumentViewSelection(final Builder builder)
	{
		super();

		entityDescriptor = builder.query.getEntityDescriptor();

		fieldDescriptors = builder.query.getViewFields();
		Check.assumeNotEmpty(fieldDescriptors, "fieldDescriptors is not empty");

		querySelectionUUID = builder.querySelectionUUID;
		Check.assumeNotEmpty(querySelectionUUID, "querySelectionUUID is not empty");

		sql = builder.buildSql();
		size = builder.rowsCount;

		logger.debug("View created: {}", this);
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("viewId", querySelectionUUID)
					.add("size", size)
					.add("sql", sql)
					.toString();
		}
		return _toString;
	}

	@Override
	public String getId()
	{
		return querySelectionUUID;
	}

	@Override
	public int size()
	{
		return size;
	}

	public void close()
	{
		// nothing now. in future me might notify somebody to remove the temporary selection from database

		logger.debug("View closed: {}", this);
	}

	@Override
	public List<IDocumentView> getPage(final int firstRow, final int pageLength)
	{
		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final Object[] sqlParams = new Object[] { querySelectionUUID, firstRow, pageLength };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IDocumentView> page = ImmutableList.builder();
			while (rs.next())
			{
				final IDocumentView documentView = retriveDocumentView(rs);
				if (documentView == null)
				{
					continue;
				}

				page.add(documentView);
			}

			return page.build();
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

	private IDocumentView retriveDocumentView(final ResultSet rs)
	{
		final DocumentView.Builder documentBuilder = DocumentView.builder(entityDescriptor);
		for (final DocumentFieldDescriptor fieldDescriptor : fieldDescriptors)
		{
			final String fieldName = fieldDescriptor.getFieldName();
			final Object fieldValue = SqlDocumentRepository.retrieveDocumentFieldValue(fieldDescriptor, rs);

			if (fieldDescriptor.isKey())
			{
				// If document is not present anymore in our view (i.e. the Key is null) then we shall skip it.
				if (fieldValue == null)
				{
					if (logger.isDebugEnabled())
					{
						Integer recordId = null;
						Integer seqNo = null;
						try
						{
							recordId = rs.getInt(SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_Record_ID);
							seqNo = rs.getInt(SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo);
						}
						catch (final Exception e)
						{
						}

						logger.debug("Skip missing record: Record_ID={}, SeqNo={} -- {}", recordId, seqNo, this);
					}
					return null;
				}

				documentBuilder.setIdFieldName(fieldName);
			}

			final Object jsonValue = Values.valueToJsonObject(fieldValue);
			documentBuilder.putFieldValue(fieldName, jsonValue);
		}

		return documentBuilder.build();
	}

	public static final class Builder
	{
		private DocumentQuery query;

		private String querySelectionUUID;
		private int rowsCount = -1;

		private Builder()
		{
			super();
		}

		public SqlDocumentViewSelection build()
		{
			Preconditions.checkNotNull(query, "query");
			createSelection();

			return new SqlDocumentViewSelection(this);
		}

		public Builder setId(final String id)
		{
			querySelectionUUID = id;
			return this;
		}

		public Builder setQuery(final DocumentQuery query)
		{
			this.query = query;
			return this;
		}

		private String buildSql()
		{
			final SqlDocumentEntityDataBindingDescriptor dataBinding = getDataBinding();
			final String adLanguage = query.getAD_Language();
			final String sqlPagedSelectFrom = dataBinding.getSqlPagedSelectAllFrom(adLanguage);

			return sqlPagedSelectFrom
					+ "\n WHERE " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_UUID + "=?"
					+ "\n ORDER BY " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo
					+ "\n OFFSET ? LIMIT ?";

		}

		private void createSelection()
		{
			Check.assumeNotEmpty(querySelectionUUID, "querySelectionUUID is not empty");

			final SqlDocumentQueryBuilder queryBuilder = SqlDocumentQueryBuilder.of(query);

			final SqlDocumentEntityDataBindingDescriptor dataBinding = getDataBinding();
			final String sqlTableName = dataBinding.getSqlTableName();
			final String sqlTableAlias = dataBinding.getSqlTableAlias();
			final String keyColumnNameFQ = dataBinding.getSqlKeyColumnName();
			if (keyColumnNameFQ == null)
			{
				throw new DBException("No key column found in " + dataBinding);
			}
			final String orderBy = queryBuilder.getSqlOrderBy();

			final StringBuilder sqlRowNumber = new StringBuilder("row_number() OVER (");
			if (!Check.isEmpty(orderBy, true))
			{
				sqlRowNumber.append("ORDER BY ").append(orderBy);
			}
			sqlRowNumber.append(")");

			final List<Object> sqlParams = new ArrayList<>();
			final StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO " + I_T_Query_Selection.Table_Name + " ("
					+ " " + I_T_Query_Selection.COLUMNNAME_UUID
					+ ", " + I_T_Query_Selection.COLUMNNAME_Line
					+ ", " + I_T_Query_Selection.COLUMNNAME_Record_ID
					+ ")")
					.append(" SELECT ")
					.append(DB.TO_STRING(querySelectionUUID)) // UUID
					.append(", ").append(sqlRowNumber) // Line
					.append(", ").append(keyColumnNameFQ) // Record_ID
					.append(" FROM ").append(sqlTableName).append(" ").append(sqlTableAlias);

			final String sqlWhereClause = queryBuilder.getSqlWhere();
			if (!Check.isEmpty(sqlWhereClause, true))
			{
				sql.append(" WHERE ").append(sqlWhereClause);
				sqlParams.addAll(queryBuilder.getSqlWhereParams());
			}

			rowsCount = DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		}

		private SqlDocumentEntityDataBindingDescriptor getDataBinding()
		{
			final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
			final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
			return dataBinding;
		}

	}
}
