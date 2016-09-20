package de.metas.ui.web.window.model.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.I_T_Query_Selection;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor.DocumentViewFieldValueLoader;
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

	private final int adWindowId;

	private final String querySelectionUUID;
	private final String sql;
	private final int size;
	private final List<DocumentViewFieldValueLoader> fieldLoaders;

	private transient String _toString;

	private SqlDocumentViewSelection(final Builder builder)
	{
		super();

		adWindowId = builder.getAD_Window_ID();

		querySelectionUUID = builder.querySelectionUUID;
		Check.assumeNotEmpty(querySelectionUUID, "querySelectionUUID is not empty");

		sql = builder.buildSql();
		size = builder.rowsCount;
		fieldLoaders = builder.createDocumentViewFieldValueLoaders();

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
					.add("AD_Window_ID", adWindowId)
					.add("size", size)
					.add("sql", sql)
					// .add("fieldLoaders", fieldLoaders) // no point to show them because all are lambdas
					.toString();
		}
		return _toString;
	}

	@Override
	public String getViewId()
	{
		return querySelectionUUID;
	}

	@Override
	public int getAD_Window_ID()
	{
		return adWindowId;
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
	public List<IDocumentView> getPage(final int firstRow, final int pageLength) throws DBException
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
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final ImmutableList.Builder<IDocumentView> page = ImmutableList.builder();
			while (rs.next())
			{
				final DocumentView.Builder documentViewBuilder = DocumentView.builder(adWindowId);
				final IDocumentView documentView = loadDocumentView(documentViewBuilder, rs);
				if (documentView == null)
				{
					continue;
				}

				page.add(documentView);
			}

			return page.build();
		}
		catch (final Exception e)
		{
			throw DBException.wrapIfNeeded(e)
					.setSqlIfAbsent(sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
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

		private ImmutableList<DocumentViewFieldValueLoader> createDocumentViewFieldValueLoaders()
		{
			final List<DocumentFieldDescriptor> fieldDescriptors = query.getViewFields();
			Check.assumeNotEmpty(fieldDescriptors, "fieldDescriptors is not empty");
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
					// If it's key column, add it fast, because in case the record is missing, we want to fail fast
					documentViewFieldLoaders.add(0, documentViewFieldLoader);
				}
				else
				{
					documentViewFieldLoaders.add(documentViewFieldLoader);
				}
			}
			return ImmutableList.copyOf(documentViewFieldLoaders);
		}

		private int getAD_Window_ID()
		{
			return query.getEntityDescriptor().getAD_Window_ID();
		}

		public Builder setQuery(final DocumentQuery query)
		{
			this.query = query;
			return this;
		}

		private String buildSql()
		{
			final SqlDocumentEntityDataBindingDescriptor dataBinding = getDataBinding();
			Evaluatee evalCtx = Evaluatees.ofMap(ImmutableMap.<String, String> builder()
					.put(Env.CTXNAME_AD_Language, query.getAD_Language())
					.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), query.getPermissionsKey())
					.build());
			final String sqlPagedSelectFrom = dataBinding
					.getSqlPagedSelectAllFrom()
					.evaluate(evalCtx, OnVariableNotFound.Fail);

			return sqlPagedSelectFrom
					+ "\n WHERE " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_UUID + "=?"
					+ "\n ORDER BY " + SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo
					+ "\n OFFSET ? LIMIT ?";

		}

		private void createSelection()
		{
			querySelectionUUID = UUID.randomUUID().toString();

			final SqlDocumentQueryBuilder helper = SqlDocumentQueryBuilder.of(query);

			final SqlDocumentEntityDataBindingDescriptor dataBinding = getDataBinding();
			final String sqlTableName = dataBinding.getSqlTableName();
			final String sqlTableAlias = dataBinding.getSqlTableAlias();
			final String keyColumnNameFQ = dataBinding.getSqlKeyColumnName();
			if (keyColumnNameFQ == null)
			{
				throw new DBException("No key column found in " + dataBinding);
			}

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
				final String orderBy = helper.getSqlOrderBy();
				final StringBuilder sqlRowNumber = new StringBuilder("row_number() OVER (");
				if (!Check.isEmpty(orderBy, true))
				{
					sqlRowNumber.append("ORDER BY ").append(orderBy);
				}
				sqlRowNumber.append(")");

				sqlBuilder.append(
						IStringExpression.composer()
								.append("\n SELECT ")
								.append("\n  ?") // UUID
								.append("\n, ").append(sqlRowNumber) // Line
								.append("\n, ").append(keyColumnNameFQ) // Record_ID
								.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
								.append("\n WHERE 1=1 ")
								.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
				);
				sqlParams.add(querySelectionUUID);
			}

			//
			// WHERE clause
			final IStringExpression sqlWhereClause = helper.getSqlWhere();
			if (!sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append(" AND (").append(sqlWhereClause).append(")");
				sqlParams.addAll(helper.getSqlWhereParams());
			}

			final Evaluatee ctx = helper.getEvaluationContext();
			final String sql = sqlBuilder
					.build()
					.evaluate(ctx, OnVariableNotFound.Fail);

			rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);

			logger.trace("Created selection {}, rowsCount={} -- {} -- {}", querySelectionUUID, rowsCount, sql, sqlParams);
		}

		private SqlDocumentEntityDataBindingDescriptor getDataBinding()
		{
			final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
			final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
			return dataBinding;
		}

	}
}
