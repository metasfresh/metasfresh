package de.metas.ui.web.window.model.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.I_T_Query_Selection;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor.DocumentViewFieldValueLoader;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentView;
import de.metas.ui.web.window.model.DocumentViewResult;
import de.metas.ui.web.window.model.IDocumentView;
import de.metas.ui.web.window.model.IDocumentViewSelection;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private final int adWindowId;
	private final OrderedSelection defaultSelection;

	private final OrderedSelectionFactory orderedSelectionFactory;
	private final String sqlSelectPage;
	private final List<DocumentViewFieldValueLoader> fieldLoaders;

	/** Active filters */
	private final List<DocumentFilter> filters;
	private final ConcurrentHashMap<ImmutableList<DocumentQueryOrderBy>, OrderedSelection> selectionsByOrderBys = new ConcurrentHashMap<>();

	private transient String _toString;

	private SqlDocumentViewSelection(final Builder builder)
	{
		super();

		adWindowId = builder.getAD_Window_ID();

		defaultSelection = builder.buildInitialSelection();
		selectionsByOrderBys.put(defaultSelection.getOrderBys(), defaultSelection);
		filters = ImmutableList.copyOf(builder.getFilters());

		sqlSelectPage = builder.buildSqlSelectPage();
		fieldLoaders = builder.createDocumentViewFieldValueLoaders();

		orderedSelectionFactory = builder.buildSqlCreateSelectionFromSelection();

		logger.debug("View created: {}", this);
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			_toString = MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("viewId", defaultSelection.getUuid())
					.add("AD_Window_ID", adWindowId)
					.add("defaultSelection", defaultSelection)
					.add("sql", sqlSelectPage)
					// .add("fieldLoaders", fieldLoaders) // no point to show them because all are lambdas
					.toString();
		}
		return _toString;
	}

	@Override
	public String getViewId()
	{
		return defaultSelection.getUuid();
	}

	@Override
	public int getAD_Window_ID()
	{
		return adWindowId;
	}

	@Override
	public long size()
	{
		return defaultSelection.getSize();
	}

	@Override
	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultSelection.getOrderBys();
	}
	
	@Override
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	public void close()
	{
		if (closed.getAndSet(true))
		{
			return; // already closed
		}

		// nothing now. in future me might notify somebody to remove the temporary selections from database

		logger.debug("View closed: {}", this);
	}

	private final void assertNotClosed()
	{
		if (closed.get())
		{
			throw new IllegalStateException("View already closed: " + getViewId());
		}
	}

	@Override
	public DocumentViewResult getPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys) throws DBException
	{
		assertNotClosed();

		logger.debug("Getting page: firstRow={}, pageLength={} - {}", firstRow, pageLength, this);

		Check.assume(firstRow >= 0, "firstRow >= 0 but it was {}", firstRow);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final OrderedSelection orderedSelection = getOrderedSelection(orderBys);
		logger.debug("Using: {}", orderedSelection);

		final String uuid = orderedSelection.getUuid();
		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength - 1;

		final Object[] sqlParams = new Object[] { uuid, firstSeqNo, lastSeqNo };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectPage, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IDocumentView> pageBuilder = ImmutableList.builder();
			while (rs.next())
			{
				final DocumentView.Builder documentViewBuilder = DocumentView.builder(adWindowId);
				final IDocumentView documentView = loadDocumentView(documentViewBuilder, rs);
				if (documentView == null)
				{
					continue;
				}

				pageBuilder.add(documentView);
			}

			final List<IDocumentView> page = pageBuilder.build();
			return DocumentViewResult.of(this, firstRow, pageLength, orderedSelection.getOrderBys(), page);
		}
		catch (final Exception e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sqlSelectPage, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private OrderedSelection getOrderedSelection(final List<DocumentQueryOrderBy> orderBys) throws DBException
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return defaultSelection;
		}

		if (Objects.equals(defaultSelection.getOrderBys(), orderBys))
		{
			return defaultSelection;
		}

		final String fromUUID = defaultSelection.getUuid();
		final ImmutableList<DocumentQueryOrderBy> orderBysImmutable = ImmutableList.copyOf(orderBys);
		return selectionsByOrderBys.computeIfAbsent(orderBysImmutable, (newOrderBys) -> orderedSelectionFactory.create(fromUUID, orderBysImmutable));
	}

	private IDocumentView loadDocumentView(final DocumentView.Builder documentViewBuilder, final ResultSet rs) throws SQLException
	{
		for (final DocumentViewFieldValueLoader fieldLoader : fieldLoaders)
		{
			final boolean loaded = fieldLoader.loadDocumentViewValue(documentViewBuilder, rs);
			if (!loaded)
			{
				return null;
			}
		}

		return documentViewBuilder.build();
	}

	public static final class Builder
	{
		private SqlDocumentQueryBuilder queryBuilder;

		private Builder()
		{
			super();
		}

		public SqlDocumentViewSelection build()
		{
			Preconditions.checkNotNull(queryBuilder, "queryBuilder");

			return new SqlDocumentViewSelection(this);
		}

		private ImmutableList<DocumentViewFieldValueLoader> createDocumentViewFieldValueLoaders()
		{
			final List<DocumentFieldDescriptor> fieldDescriptors = queryBuilder.getViewFields();

			final List<DocumentViewFieldValueLoader> documentViewFieldLoaders = new ArrayList<>();
			for (final DocumentFieldDescriptor fieldDescriptor : fieldDescriptors)
			{
				final SqlDocumentFieldDataBindingDescriptor fieldDataBinding = SqlDocumentFieldDataBindingDescriptor.castOrNull(fieldDescriptor.getDataBinding());
				if (fieldDataBinding == null)
				{
					logger.warn("No SQL databinding provided for {}. Skip creating the field loader", fieldDescriptor);
					continue;
				}

				final boolean keyColumn = fieldDataBinding.isKeyColumn();
				final DocumentViewFieldValueLoader documentViewFieldLoader = fieldDataBinding.getDocumentViewFieldValueLoader();

				if (keyColumn)
				{
					// If it's key column, add it first, because in case the record is missing, we want to fail fast
					documentViewFieldLoaders.add(0, documentViewFieldLoader);
				}
				else
				{
					documentViewFieldLoaders.add(documentViewFieldLoader);
				}
			}
			return ImmutableList.copyOf(documentViewFieldLoaders);
		}

		public Builder setQuery(final DocumentQuery query)
		{
			queryBuilder = SqlDocumentQueryBuilder.of(query);
			return this;
		}

		private int getAD_Window_ID()
		{
			return queryBuilder.getEntityDescriptor().getAD_Window_ID();
		}

		private String buildSqlSelectPage()
		{
			final Evaluatee evalCtx = queryBuilder.getEvaluationContext();
			final SqlDocumentEntityDataBindingDescriptor dataBinding = queryBuilder.getEntityBinding();
			final String sqlPagedSelectFrom = dataBinding
					.getSqlPagedSelectAllFrom()
					.evaluate(evalCtx, OnVariableNotFound.Fail);

			return sqlPagedSelectFrom
					+ "\n WHERE " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_UUID + "=?"
					+ "\n AND " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?"
					+ "\n ORDER BY " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo;
		}

		private OrderedSelection buildInitialSelection()
		{
			final Evaluatee evalCtx = queryBuilder.getEvaluationContext();
			final SqlDocumentEntityDataBindingDescriptor dataBinding = queryBuilder.getEntityBinding();

			final String sqlTableName = dataBinding.getTableName();
			final String sqlTableAlias = dataBinding.getTableAlias();
			final String keyColumnNameFQ = dataBinding.getKeyColumnName();
			if (keyColumnNameFQ == null)
			{
				throw new DBException("No key column found in " + dataBinding);
			}

			final IStringExpression sqlWhereClause = queryBuilder.getSqlWhere();
			final List<Object> sqlWhereClauseParams = queryBuilder.getSqlWhereParams();

			final List<DocumentQueryOrderBy> orderBys = queryBuilder.getOrderBysEffective();

			final String uuid = UUID.randomUUID().toString();

			//
			// INSERT INTO T_Query_Selection (UUID, Line, Record_ID)
			final List<Object> sqlParams = new ArrayList<>();
			final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
					.append("INSERT INTO " + I_T_Query_Selection.Table_Name + " ("
							+ " " + I_T_Query_Selection.COLUMNNAME_UUID
							+ ", " + I_T_Query_Selection.COLUMNNAME_Line
							+ ", " + I_T_Query_Selection.COLUMNNAME_Record_ID
							+ ")");

			//
			// SELECT ... FROM ... WHERE 1=1
			{
				IStringExpression orderBy = dataBinding.buildSqlFullOrderBy(orderBys);
				if (orderBy.isNullExpression())
				{
					orderBy = ConstantStringExpression.of(keyColumnNameFQ);
				}

				sqlBuilder.append(
						IStringExpression.composer()
								.append("\n SELECT ")
								.append("\n  ?") // UUID
								.append("\n, ").append("row_number() OVER (ORDER BY ").append(orderBy).append(")") // Line
								.append("\n, ").append(keyColumnNameFQ) // Record_ID
								.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
								.append("\n WHERE 1=1 ")
								.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
				);
				sqlParams.add(uuid);
			}

			//
			// WHERE clause (from query)
			if (!sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
				sqlParams.addAll(sqlWhereClauseParams);
			}

			//
			// Evaluate the final SQL query
			final String sql = sqlBuilder.build().evaluate(evalCtx, OnVariableNotFound.Fail);

			//
			// Execute it, so we insert in our T_Query_Selection
			final long rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
			logger.trace("Created selection {}, rowsCount={} -- {} -- {}", uuid, rowsCount, sql, sqlParams);

			return new OrderedSelection(uuid, rowsCount, orderBys);
		}

		private OrderedSelectionFactory buildSqlCreateSelectionFromSelection()
		{
			final Evaluatee evalCtx = queryBuilder.getEvaluationContext();
			final SqlDocumentEntityDataBindingDescriptor dataBinding = queryBuilder.getEntityBinding();

			final String sqlTableName = dataBinding.getTableName();
			final String sqlTableAlias = dataBinding.getTableAlias();
			final String keyColumnName = dataBinding.getKeyColumnName();
			if (keyColumnName == null)
			{
				throw new DBException("No key column found in " + dataBinding);
			}
			final String keyColumnNameFQ = sqlTableAlias + "." + keyColumnName;

			//
			// INSERT INTO T_Query_Selection (UUID, Line, Record_ID)
			final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
					.append("INSERT INTO " + I_T_Query_Selection.Table_Name + " ("
							+ " " + I_T_Query_Selection.COLUMNNAME_UUID
							+ ", " + I_T_Query_Selection.COLUMNNAME_Line
							+ ", " + I_T_Query_Selection.COLUMNNAME_Record_ID
							+ ")");

			//
			// SELECT ... FROM T_Query_Selection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
			{
				// final IStringExpression orderBy = dataBinding.buildSqlFullOrderBy(orderBys);

				sqlBuilder.append(
						IStringExpression.composer()
								.append("\n SELECT ")
								.append("\n  ?") // newUUID
								.append("\n, ").append("row_number() OVER (ORDER BY ").append(OrderedSelectionFactory.PLACEHOLDER_OrderBy).append(")") // Line
								.append("\n, ").append(keyColumnNameFQ) // Record_ID
								.append("\n FROM ").append(I_T_Query_Selection.Table_Name).append(" sel")
								.append("\n LEFT OUTER JOIN ").append(sqlTableName).append(" ").append(sqlTableAlias).append(" ON (").append(keyColumnNameFQ).append("=").append("sel.")
								.append(I_T_Query_Selection.COLUMNNAME_Record_ID).append(")")
								.append("\n WHERE sel.").append(I_T_Query_Selection.COLUMNNAME_UUID).append("=?") // fromUUID
				);
			}

			final String sql = sqlBuilder.build().evaluate(evalCtx, OnVariableNotFound.Fail);
			final Map<String, String> fieldName2sqlDictionary = dataBinding.getAvailableFieldFullOrderBys(evalCtx);

			return new OrderedSelectionFactory(sql, fieldName2sqlDictionary);
		}
		
		private List<DocumentFilter> getFilters()
		{
			return queryBuilder.getDocumentFilters();
		}
	}

	private static final class OrderedSelectionFactory
	{
		private static final String PLACEHOLDER_OrderBy = "/* ORDER BY PLACEHOLDER */";

		private final String sql;
		private final Map<String, String> fieldName2sqlDictionary;

		private OrderedSelectionFactory(final String sql, final Map<String, String> fieldName2sqlDictionary)
		{
			super();
			this.sql = sql;
			this.fieldName2sqlDictionary = fieldName2sqlDictionary;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("sql", sql)
					.add("fieldName2sqlDictionary", fieldName2sqlDictionary)
					.toString();
		}

		public OrderedSelection create(final String fromUUID, final List<DocumentQueryOrderBy> orderBys)
		{
			final String newUUID = UUID.randomUUID().toString();
			final String sqlOrderBys = buildOrderBys(orderBys); // NOTE: we assume it's not empty!
			final String sqlFinal = sql.replace(PLACEHOLDER_OrderBy, sqlOrderBys);
			final int rowCount = DB.executeUpdateEx(sqlFinal, new Object[] { newUUID, fromUUID }, ITrx.TRXNAME_ThreadInherited);
			return new OrderedSelection(newUUID, rowCount, orderBys);
		}

		private final String buildOrderBys(final List<DocumentQueryOrderBy> orderBys)
		{
			if (orderBys == null || orderBys.isEmpty())
			{
				return "";
			}

			return orderBys
					.stream()
					.map(orderBy -> buildOrderBy(orderBy))
					.filter(orderBy -> !Check.isEmpty(orderBy, true))
					.collect(Collectors.joining(", "));
		}

		private final String buildOrderBy(final DocumentQueryOrderBy orderBy)
		{
			final String fieldName = orderBy.getFieldName();
			final String fieldSql = fieldName2sqlDictionary.get(fieldName);
			if (fieldSql == null)
			{
				throw new DBException("No SQL field mapping found for: " + fieldName);
			}

			return "(" + fieldSql + ") " + (orderBy.isAscending() ? " ASC" : " DESC");
		}

	};

	private static final class OrderedSelection
	{
		private final String uuid;
		private final long size;
		private final ImmutableList<DocumentQueryOrderBy> orderBys;

		private OrderedSelection(final String uuid, final long size, final List<DocumentQueryOrderBy> orderBys)
		{
			super();
			this.uuid = uuid;
			this.size = size;
			this.orderBys = ImmutableList.copyOf(orderBys);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("uuid", uuid)
					.add("size", size)
					.add("orderBys", orderBys.isEmpty() ? null : orderBys)
					.toString();
		}

		public String getUuid()
		{
			return uuid;
		}

		public long getSize()
		{
			return size;
		}

		private ImmutableList<DocumentQueryOrderBy> getOrderBys()
		{
			return orderBys;
		}
	}
}
