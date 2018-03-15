package de.metas.ui.web.window.model.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;

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

public class SqlDocumentQueryBuilder
{

	public static SqlDocumentQueryBuilder newInstance(final DocumentEntityDescriptor entityDescriptor)
	{
		return new SqlDocumentQueryBuilder(entityDescriptor);
	}

	public static SqlDocumentQueryBuilder of(final DocumentQuery query)
	{
		return new SqlDocumentQueryBuilder(query.getEntityDescriptor())
				.setDocumentFilters(query.getFilters())
				.setParentDocument(query.getParentDocument())
				.setRecordId(query.getRecordId())
				//
				.noSorting(query.isNoSorting())
				.setOrderBys(query.getOrderBys())
				//
				.setPage(query.getFirstRow(), query.getPageLength())
		//
		;
	}

	private final Properties ctx;
	private final SqlDocumentEntityDataBindingDescriptor entityBinding;

	private transient Evaluatee _evaluationContext = null; // lazy
	private final List<DocumentFilter> documentFilters = new ArrayList<>();
	private Document parentDocument;
	private DocumentId recordId = null;

	private boolean noSorting = false;
	private List<DocumentQueryOrderBy> orderBys;

	private int firstRow;
	private int pageLength;

	//
	// Built values
	private IPair<IStringExpression, List<Object>> _sqlWhereAndParams;
	private IPair<IStringExpression, List<Object>> _sqlAndParams;

	private SqlDocumentQueryBuilder(final DocumentEntityDescriptor entityDescriptor)
	{
		ctx = Env.getCtx();

		Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");
		entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());

	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("TableName", entityBinding.getTableName())
				.toString();
	}

	private Evaluatee getEvaluationContext()
	{
		if (_evaluationContext == null)
		{
			_evaluationContext = createEvaluationContext();
		}
		return _evaluationContext;
	}

	private Evaluatee createEvaluationContext()
	{
		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put(Env.CTXNAME_AD_Language, getAD_Language())
				.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), getPermissionsKey())
				.build();

		final Evaluatee parentEvalCtx;
		if (parentDocument != null)
		{
			parentEvalCtx = parentDocument.asEvaluatee();
		}
		else
		{
			final Properties ctx = getCtx();
			final int windowNo = Env.WINDOW_MAIN; // TODO: get the proper windowNo
			final boolean onlyWindow = false;
			parentEvalCtx = Evaluatees.ofCtx(ctx, windowNo, onlyWindow);
		}

		return Evaluatees.compose(evalCtx, parentEvalCtx);
	}

	private Properties getCtx()
	{
		return ctx;
	}

	String getAD_Language()
	{
		// TODO: introduce AD_Language as parameter
		return Env.getAD_Language(getCtx());
	}

	private String getPermissionsKey()
	{
		// TODO: introduce permissionsKey as parameter
		return UserRolePermissionsKey.toPermissionsKeyString(getCtx());
	}

	/**
	 * SQL to fetch the parent's ID based on given child document.
	 * 
	 * @return SELECT KeyColumnName from ParentTableName WHERE ....
	 */
	public String getSqlSelectParentId(final List<Object> outSqlParams, final DocumentEntityDescriptor parentEntityDescriptor)
	{
		final String linkColumnName = entityBinding.getLinkColumnName();
		final String parentLinkColumnName = entityBinding.getParentLinkColumnName();
		if (parentLinkColumnName == null || linkColumnName == null)
		{
			throw new AdempiereException("Selecting parent ID is not possible because this entity does not have a parent link")
					.setParameter("linkColumnName", linkColumnName)
					.setParameter("parentLinkColumnName", parentLinkColumnName)
					.setParameter("entityBinding", entityBinding);
		}

		//
		// SELECT linkColumnName from current(child) tableName
		final List<Object> sqlSelectLinkColumnNameParams = new ArrayList<>();
		final CompositeStringExpression.Builder sqlSelectLinkColumnName = IStringExpression.composer();
		{
			final IPair<IStringExpression, List<Object>> sqlWhereAndParams = getSqlWhereAndParams();
			final IStringExpression sqlWhere = sqlWhereAndParams.getLeft();
			final List<Object> sqlWhereParams = sqlWhereAndParams.getRight();

			sqlSelectLinkColumnName
					.append("SELECT " + linkColumnName)
					.append(" FROM " + entityBinding.getTableName() + " " + entityBinding.getTableAlias()) // NOTE: we need table alias because the where clause is using it
					.append("\n WHERE ").append(sqlWhere);
			sqlSelectLinkColumnNameParams.addAll(sqlWhereParams);
		}

		//
		//
		final String parentKeyColumnName = extractSingleKeyColumnName(parentEntityDescriptor);
		if (Objects.equals(parentKeyColumnName, parentLinkColumnName))
		{
			final Evaluatee evalCtx = getEvaluationContext();

			final String sql = sqlSelectLinkColumnName.build().evaluate(evalCtx, OnVariableNotFound.Fail);
			outSqlParams.addAll(sqlSelectLinkColumnNameParams);
			return sql;
		}
		else
		{
			final Evaluatee evalCtx = getEvaluationContext();

			final String sql = IStringExpression.composer()
					.append("SELECT " + parentKeyColumnName + " FROM " + parentEntityDescriptor.getTableName())
					.append("\n WHERE " + parentLinkColumnName + " IN (").append(sqlSelectLinkColumnName).append(")")
					.build()
					.evaluate(evalCtx, OnVariableNotFound.Fail);
			outSqlParams.addAll(sqlSelectLinkColumnNameParams);
			return sql;
		}
	}

	/** @return SQL key column name; never returns null */
	private static final String extractSingleKeyColumnName(final DocumentEntityDescriptor entityDescriptor)
	{
		final DocumentFieldDescriptor idField = entityDescriptor.getSingleIdField();
		final SqlDocumentFieldDataBindingDescriptor idFieldBinding = SqlDocumentFieldDataBindingDescriptor.castOrNull(idField.getDataBinding());
		if (idFieldBinding == null)
		{
			throw new AdempiereException("Entity's ID field does not have SQL bindings: " + idField);
		}

		return idFieldBinding.getColumnName();
	}

	public String getSqlMaxLineNo(final List<Object> outSqlParams)
	{
		final StringBuilder sql = new StringBuilder("SELECT COALESCE(MAX(" + WindowConstants.FIELDNAME_Line + "), 0)")
				.append(" FROM " + entityBinding.getTableName() + " " + entityBinding.getTableAlias());

		String sqlWhere = getSqlWhere(outSqlParams);
		if (!Check.isEmpty(sqlWhere, true))
		{
			sql.append(" WHERE ").append(sqlWhere);
		}

		return sql.toString();
	}

	/**
	 * @return SQL to fully load the documents matched by this query.
	 */
	public String getSql(final List<Object> outSqlParams)
	{
		final Evaluatee evalCtx = getEvaluationContext();
		final IPair<IStringExpression, List<Object>> sqlAndParams = getSqlAndParams();
		final String sql = sqlAndParams.getLeft().evaluate(evalCtx, OnVariableNotFound.Fail);
		final List<Object> sqlParams = sqlAndParams.getRight();

		outSqlParams.addAll(sqlParams);
		return sql;
	}

	private IPair<IStringExpression, List<Object>> getSqlAndParams()
	{
		IPair<IStringExpression, List<Object>> sqlAndParams = _sqlAndParams;
		if (sqlAndParams == null)
		{
			sqlAndParams = buildSql();
			_sqlAndParams = sqlAndParams;
		}
		return sqlAndParams;
	}

	private final IPair<IStringExpression, List<Object>> buildSql()
	{
		final List<Object> sqlParams = new ArrayList<>();

		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();

		//
		// SELECT ... FROM ...
		sqlBuilder.append(getSqlSelectFrom());
		// NOTE: no need to add security here because it was already embedded in SqlSelectFrom

		//
		// WHERE
		{
			final IPair<IStringExpression, List<Object>> sqlWhereClauseAndParams = getSqlWhereAndParams();
			final IStringExpression sqlWhereClause = sqlWhereClauseAndParams.getLeft();
			if (!sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append("\n WHERE ").append(sqlWhereClause);
				sqlParams.addAll(sqlWhereClauseAndParams.getRight());
			}
		}

		//
		// ORDER BY
		if (isSorting())
		{
			final IStringExpression sqlOrderBy = getSqlOrderByEffective();
			if (sqlOrderBy != null && !sqlOrderBy.isNullExpression())
			{
				sqlBuilder.append("\n ORDER BY ").append(sqlOrderBy);
			}
		}

		//
		// LIMIT/OFFSET
		{
			final int firstRow = getFirstRow();
			if (firstRow > 0)
			{
				sqlBuilder.append("\n OFFSET ?");
				sqlParams.add(firstRow);
			}
			final int pageLength = getPageLength();
			if (pageLength > 0)
			{
				sqlBuilder.append("\n LIMIT ?");
				sqlParams.add(pageLength);
			}
		}

		//
		//
		return ImmutablePair.of(sqlBuilder.build(), Collections.unmodifiableList(sqlParams));
	}

	private IStringExpression getSqlSelectFrom()
	{
		return entityBinding.getSqlSelectAllFrom();
	}

	private String getSqlWhere(final List<Object> sqlParams)
	{
		final IPair<IStringExpression, List<Object>> sqlWhereAndParams = getSqlWhereAndParams();
		final Evaluatee evalCtx = getEvaluationContext();
		final String sqlWhere = sqlWhereAndParams.getLeft().evaluate(evalCtx, OnVariableNotFound.Fail);

		sqlParams.addAll(sqlWhereAndParams.getRight());

		return sqlWhere;
	}

	private IPair<IStringExpression, List<Object>> getSqlWhereAndParams()
	{
		IPair<IStringExpression, List<Object>> sqlWhereAndParams = _sqlWhereAndParams;
		if (sqlWhereAndParams == null)
		{
			sqlWhereAndParams = buildSqlWhereClause();
			_sqlWhereAndParams = sqlWhereAndParams;
		}
		return sqlWhereAndParams;
	}

	private IPair<IStringExpression, List<Object>> buildSqlWhereClause()
	{
		final SqlParamsCollector sqlParams = SqlParamsCollector.newInstance();

		final CompositeStringExpression.Builder sqlWhereClauseBuilder = IStringExpression.composer();

		//
		// Entity's WHERE clause
		{
			final IStringExpression entityWhereClauseExpression = entityBinding.getSqlWhereClause();
			if (!entityWhereClauseExpression.isNullExpression())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* entity where clause */ (").append(entityWhereClauseExpression).append(")");
			}
		}

		//
		// Key column
		// FIXME: handle AD_Reference/AD_Ref_List(s). In that case the recordId will be AD_Ref_List.Value,
		// so the SQL where clause which is currently build is AD_Ref_List_ID=<the AD_Ref_List.Value>.
		// The build SQL where clause shall be something like AD_Reference_ID=<the reference, i think we shall fetch it somehow from Lookup> AND Value=<the value, which currently is the recordId>
		final DocumentId recordId = getRecordId();
		if (recordId != null)
		{
			final List<SqlDocumentFieldDataBindingDescriptor> keyFields = entityBinding.getKeyFields();
			if (keyFields.isEmpty())
			{
				throw new AdempiereException("Failed building where clause because there is no Key Column defined in " + entityBinding);
			}
			// Single primary key
			else if (keyFields.size() == 1)
			{
				final String keyColumnName = keyFields.get(0).getColumnName();
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* key */ ").append(keyColumnName).append("=").append(sqlParams.placeholder(recordId.toInt()));
			}
			// Composed primary key
			else
			{
				final Map<String, Object> keyColumnName2value = extractComposedKey(recordId, keyFields);
				keyColumnName2value.forEach((keyColumnName, value) -> {
					sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
					sqlWhereClauseBuilder.append(" /* key */ ").append(keyColumnName).append("=").append(sqlParams.placeholder(value));
				});
			}
		}

		//
		// Parent link where clause (if any)
		final Document parentDocument = getParentDocument();
		if (parentDocument != null)
		{
			final String parentLinkColumnName = entityBinding.getParentLinkColumnName();
			final String linkColumnName = entityBinding.getLinkColumnName();
			if (parentLinkColumnName != null && linkColumnName != null)
			{
				final IDocumentFieldView parentLinkField = parentDocument.getFieldView(parentLinkColumnName);
				final Object parentLinkValue = parentLinkField.getValue();
				final DocumentFieldWidgetType parentLinkWidgetType = parentLinkField.getWidgetType();

				final Class<?> targetClass = entityBinding.getFieldByFieldName(linkColumnName).getSqlValueClass();
				final Object sqlParentLinkValue = SqlDocumentsRepository.convertValueToPO(parentLinkValue, parentLinkColumnName, parentLinkWidgetType, targetClass);

				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* parent link */ ").append(linkColumnName).append("=").append(sqlParams.placeholder(sqlParentLinkValue));
			}
		}

		//
		// Document filters
		{
			final String sqlFilters = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(entityBinding)
					.getSql(sqlParams, getDocumentFilters(), SqlOptions.usingTableAlias(entityBinding.getTableAlias()));
			if (!Check.isEmpty(sqlFilters, true))
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(sqlFilters).append(")\n");
			}
		}

		//
		// Build the final SQL where clause
		return ImmutablePair.of(sqlWhereClauseBuilder.build(), Collections.unmodifiableList(sqlParams.toList()));
	}

	private List<DocumentQueryOrderBy> getOrderBysEffective()
	{
		if (noSorting)
		{
			return ImmutableList.of();
		}

		final List<DocumentQueryOrderBy> queryOrderBys = getOrderBys();
		if (queryOrderBys != null && !queryOrderBys.isEmpty())
		{
			return queryOrderBys;
		}

		return entityBinding.getDefaultOrderBys();
	}

	private IStringExpression getSqlOrderByEffective()
	{
		final List<DocumentQueryOrderBy> orderBys = getOrderBysEffective();
		return SqlDocumentOrderByBuilder.newInstance(entityBinding::getFieldOrderBy).buildSqlOrderBy(orderBys);
	}

	private List<DocumentFilter> getDocumentFilters()
	{
		return documentFilters == null ? ImmutableList.of() : ImmutableList.copyOf(documentFilters);
	}

	public SqlDocumentQueryBuilder setDocumentFilters(final List<DocumentFilter> documentFilters)
	{
		this.documentFilters.clear();
		this.documentFilters.addAll(documentFilters);
		return this;
	}

	public SqlDocumentQueryBuilder addDocumentFilters(final List<DocumentFilter> documentFiltersToAdd)
	{
		this.documentFilters.addAll(documentFiltersToAdd);
		return this;
	}

	private Document getParentDocument()
	{
		return parentDocument;
	}

	public SqlDocumentQueryBuilder setParentDocument(final Document parentDocument)
	{
		this.parentDocument = parentDocument;
		return this;
	}

	public SqlDocumentQueryBuilder setRecordId(final DocumentId recordId)
	{
		this.recordId = recordId;
		return this;
	}

	private DocumentId getRecordId()
	{
		return recordId;
	}

	public SqlDocumentQueryBuilder noSorting()
	{
		noSorting = true;
		orderBys = null;
		return this;
	}

	public SqlDocumentQueryBuilder noSorting(final boolean noSorting)
	{
		this.noSorting = noSorting;
		if (noSorting)
		{
			orderBys = null;
		}
		return this;
	}

	public boolean isSorting()
	{
		return !noSorting;
	}

	public boolean isNoSorting()
	{
		return noSorting;
	}

	private List<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
	}

	public SqlDocumentQueryBuilder setOrderBys(final List<DocumentQueryOrderBy> orderBys)
	{
		// Don't throw exception if noSorting is true. Just do nothing.
		// REASON: it gives us better flexibility when this builder is handled by different methods, each of them adding stuff to it
		// Check.assume(!noSorting, "sorting enabled for {}", this);
		if (noSorting)
		{
			return this;
		}

		this.orderBys = orderBys;
		return this;
	}

	public SqlDocumentQueryBuilder setPage(final int firstRow, final int pageLength)
	{
		this.firstRow = firstRow;
		this.pageLength = pageLength;
		return this;
	}

	private int getFirstRow()
	{
		return firstRow;
	}

	private int getPageLength()
	{
		return pageLength;
	}
	
	/** @return map of (keyColumnName, value) pairs */
	public static Map<String, Object> extractComposedKey(final DocumentId recordId, final List<? extends SqlEntityFieldBinding> keyFields)
	{
		final int count = keyFields.size();
		if (count <= 1)
		{
			throw new AdempiereException("Invalid composed key: " + keyFields);
		}

		final List<Object> composedKeyParts = recordId.toComposedKeyParts();
		if (composedKeyParts.size() != count)
		{
			throw new AdempiereException("Invalid composed key '" + recordId + "'. Expected " + count + " parts but it has " + composedKeyParts.size());
		}

		final ImmutableMap.Builder<String, Object> composedKey = ImmutableMap.builder();
		for (int i = 0; i < count; i++)
		{
			final SqlEntityFieldBinding keyField = keyFields.get(i);
			final String keyColumnName = keyField.getColumnName();

			final Object valueObj = composedKeyParts.get(i);
			final Object valueConv = DocumentFieldDescriptor.convertToValueClass(
					keyColumnName,
					valueObj,
					keyField.getWidgetType(),
					keyField.getSqlValueClass(),
					(LookupValueByIdSupplier)null);

			composedKey.put(keyColumnName, valueConv);
		}

		return composedKey.build();
	}
}
