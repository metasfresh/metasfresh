package de.metas.ui.web.window.model.sql;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlAndParamsExpression;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

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
				.setRecordIds(query.getRecordIds())
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
	private DocumentFilterList documentFilters = DocumentFilterList.EMPTY;
	private Document parentDocument;
	@Getter
	private ImmutableSet<DocumentId> recordIds = ImmutableSet.of();

	@Getter
	private boolean noSorting = false;
	@Getter(AccessLevel.PRIVATE)
	private DocumentQueryOrderByList orderBys = DocumentQueryOrderByList.EMPTY;

	private int firstRow;
	private int pageLength;

	//
	// Built values
	private SqlAndParamsExpression _sqlWhereAndParams;
	private SqlAndParamsExpression _sqlAndParams;

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
				.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), getPermissionsKeyString())
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
		return Env.getADLanguageOrBaseLanguage(getCtx());
	}

	private String getPermissionsKeyString()
	{
		// TODO: introduce permissionsKey as parameter
		return UserRolePermissionsKey.toPermissionsKeyString(getCtx());
	}

	private UserRolePermissionsKey getPermissionsKey()
	{
		// TODO: introduce permissionsKey as parameter
		return UserRolePermissionsKey.fromContext(getCtx());
	}

	/**
	 * SQL to fetch the parent's ID based on given child document.
	 *
	 * @return SELECT KeyColumnName from ParentTableName WHERE ....
	 */
	public SqlAndParams getSqlSelectParentId(final DocumentEntityDescriptor parentEntityDescriptor)
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
		final SqlAndParamsExpression.Builder sqlSelectLinkColumnName = SqlAndParamsExpression.builder();
		{
			final SqlAndParamsExpression sqlWhereAndParams = getSqlWhereAndParams();

			sqlSelectLinkColumnName
					.append("SELECT " + linkColumnName)
					.append(" FROM " + entityBinding.getTableName() + " " + entityBinding.getTableAlias()) // NOTE: we need table alias because the where clause is using it
					.append("\n WHERE ").append(sqlWhereAndParams);
		}

		//
		//
		final String parentKeyColumnName = extractSingleKeyColumnName(parentEntityDescriptor);
		if (Objects.equals(parentKeyColumnName, parentLinkColumnName))
		{
			final Evaluatee evalCtx = getEvaluationContext();
			return sqlSelectLinkColumnName.build().evaluate(evalCtx);
		}
		else
		{
			final Evaluatee evalCtx = getEvaluationContext();

			return SqlAndParamsExpression.builder()
					.append("SELECT " + parentKeyColumnName + " FROM " + parentEntityDescriptor.getTableName())
					.append("\n WHERE " + parentLinkColumnName + " IN (").append(sqlSelectLinkColumnName).append(")")
					.build()
					.evaluate(evalCtx);
		}
	}

	/**
	 * @return SQL key column name; never returns null
	 */
	private static String extractSingleKeyColumnName(final DocumentEntityDescriptor entityDescriptor)
	{
		final DocumentFieldDescriptor idField = entityDescriptor.getSingleIdField();
		final SqlDocumentFieldDataBindingDescriptor idFieldBinding = SqlDocumentFieldDataBindingDescriptor.castOrNull(idField.getDataBinding());
		if (idFieldBinding == null)
		{
			throw new AdempiereException("Entity's ID field does not have SQL bindings: " + idField);
		}

		return idFieldBinding.getColumnName();
	}

	public SqlAndParams getSqlMaxLineNo()
	{
		final SqlAndParams.Builder sql = SqlAndParams.builder()
				.append("SELECT COALESCE(MAX(" + WindowConstants.FIELDNAME_Line + "), 0)")
				.append(" FROM " + entityBinding.getTableName() + " " + entityBinding.getTableAlias());

		final SqlAndParams sqlWhere = getSqlWhere();
		if (!sqlWhere.isEmpty())
		{
			sql.append(" WHERE ").append(sqlWhere);
		}

		return sql.build();
	}

	/**
	 * @return SQL to fully load the documents matched by this query.
	 */
	public SqlAndParams getSql()
	{
		final Evaluatee evalCtx = getEvaluationContext();
		return getSqlAndParams().evaluate(evalCtx);
	}

	private SqlAndParamsExpression getSqlAndParams()
	{
		SqlAndParamsExpression sqlAndParams = _sqlAndParams;
		if (sqlAndParams == null)
		{
			sqlAndParams = buildSql();
			_sqlAndParams = sqlAndParams;
		}
		return sqlAndParams;
	}

	private SqlAndParamsExpression buildSql()
	{
		final SqlAndParamsExpression.Builder sqlBuilder = SqlAndParamsExpression.builder();

		//
		// SELECT ... FROM ...
		sqlBuilder.append(getSqlSelectFrom());
		// NOTE: no need to add security here because it was already embedded in SqlSelectFrom

		//
		// WHERE
		{
			final SqlAndParamsExpression sqlWhereClauseAndParams = getSqlWhereAndParams();
			if (!sqlWhereClauseAndParams.isEmpty())
			{
				sqlBuilder.append("\n WHERE ").append(sqlWhereClauseAndParams);
			}
		}

		//
		// ORDER BY
		if (isSorting())
		{
			final SqlAndParamsExpression sqlOrderBy = getSqlOrderByEffective().orElse(null);
			if (sqlOrderBy != null && !sqlOrderBy.isEmpty())
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
				sqlBuilder.append("\n OFFSET ?", firstRow);
			}
			final int pageLength = getPageLength();
			if (pageLength > 0)
			{
				sqlBuilder.append("\n LIMIT ?", pageLength);
			}
		}

		//
		//
		return sqlBuilder.build();
	}

	private IStringExpression getSqlSelectFrom()
	{
		return entityBinding.getSqlSelectAllFrom();
	}

	private SqlAndParams getSqlWhere()
	{
		final SqlAndParamsExpression sqlWhereAndParams = getSqlWhereAndParams();
		final Evaluatee evalCtx = getEvaluationContext();
		return sqlWhereAndParams.evaluate(evalCtx);
	}

	private SqlAndParamsExpression getSqlWhereAndParams()
	{
		SqlAndParamsExpression sqlWhereAndParams = _sqlWhereAndParams;
		if (sqlWhereAndParams == null)
		{
			sqlWhereAndParams = buildSqlWhereClause();
			_sqlWhereAndParams = sqlWhereAndParams;
		}
		return sqlWhereAndParams;
	}

	private SqlAndParamsExpression buildSqlWhereClause()
	{
		final SqlAndParamsExpression.Builder sqlWhereClauseBuilder = SqlAndParamsExpression.builder();

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
		final Set<DocumentId> recordIds = getRecordIds();
		if (!recordIds.isEmpty())
		{
			final List<SqlDocumentFieldDataBindingDescriptor> keyFields = entityBinding.getKeyFields();
			if (keyFields.isEmpty())
			{
				throw new AdempiereException("Failed building where clause because there is no Key Column defined in " + entityBinding);
			}

			// Single primary key
			if (keyFields.size() == 1)
			{
				final String singleKeyColumnName = keyFields.get(0).getColumnName();
				final ImmutableSet<Integer> recordIdsIntSet = recordIds.stream()
						.map(DocumentId::toInt)
						.collect(ImmutableSet.toImmutableSet());
				sqlWhereClauseBuilder.appendIfNotEmpty("\n /* key */ AND ");
				sqlWhereClauseBuilder.appendSqlList(singleKeyColumnName, recordIdsIntSet);
			}
			// Composed primary key
			else
			{
				final boolean parenthesesRequired = !sqlWhereClauseBuilder.isEmpty();

				if (parenthesesRequired)
				{
					sqlWhereClauseBuilder.append(" AND ( ");
				}

				boolean firstRecord = true;
				final boolean appendParentheses = recordIds.size() > 1;
				for (final DocumentId recordId : recordIds)
				{
					if (!firstRecord)
					{
						sqlWhereClauseBuilder.append("\n OR ");
					}

					if (appendParentheses)
					{
						sqlWhereClauseBuilder.append("(");
					}

					final SqlComposedKey composedKey = extractComposedKey(recordId, keyFields);
					boolean firstKeyPart = true;
					for (final String keyColumnName : composedKey.getKeyColumnNames())
					{
						if (!firstKeyPart)
						{
							sqlWhereClauseBuilder.append(" AND ");
						}

						sqlWhereClauseBuilder.append(" ").append(keyColumnName);

						final Object value = composedKey.getValue(keyColumnName);
						if (!JSONNullValue.isNull(value))
						{
							sqlWhereClauseBuilder.append("=?", value);
						}
						else
						{
							sqlWhereClauseBuilder.append(" IS NULL");
						}

						firstKeyPart = false;
					}

					if (appendParentheses)
					{
						sqlWhereClauseBuilder.append(")");
					}

					firstRecord = false;
				}

				if (parenthesesRequired)
				{
					sqlWhereClauseBuilder.append(")");
				}
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
				sqlWhereClauseBuilder.append(" /* parent link */ ").append(linkColumnName).append("=?", sqlParentLinkValue);
			}
		}

		//
		// Document filters
		{
			final FilterSql filtersSql = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(entityBinding)
					.getSql(
							getDocumentFilters(),
							SqlOptions.usingTableAlias(entityBinding.getTableAlias()),
							SqlDocumentFilterConverterContext.builder()
									.userRolePermissionsKey(getPermissionsKey())
									.build());

			if (filtersSql.getWhereClause() != null && !filtersSql.getWhereClause().isEmpty())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(filtersSql.getWhereClause()).append(")\n");
			}

			if(filtersSql.getFilterByFTS() != null)
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* FTS */ (\n").append(filtersSql.getFilterByFTS().buildExistsWhereClause(entityBinding.getTableAlias())).append(")\n");
			}

			if(filtersSql.getAlwaysIncludeSql() != null)
			{
				// TODO implement support
				// but atm is quite unusual to have it here
				throw new AdempiereException("Always include SQL is not supported");
			}
		}

		//
		// Build the final SQL where clause
		return sqlWhereClauseBuilder.build();
	}

	public DocumentQueryOrderByList getOrderBysEffective()
	{
		if (noSorting)
		{
			return DocumentQueryOrderByList.EMPTY;
		}

		final DocumentQueryOrderByList queryOrderBys = getOrderBys();
		if (queryOrderBys != null && !queryOrderBys.isEmpty())
		{
			return queryOrderBys;
		}

		return entityBinding.getDefaultOrderBys();
	}

	private Optional<SqlAndParamsExpression> getSqlOrderByEffective()
	{
		final DocumentQueryOrderByList orderBys = getOrderBysEffective();
		return SqlDocumentOrderByBuilder.newInstance(entityBinding::getFieldOrderBy)
				.joinOnTableNameOrAlias(entityBinding.getTableAlias())
				.buildSqlOrderBy(orderBys);
	}

	private DocumentFilterList getDocumentFilters()
	{
		return documentFilters;
	}

	public SqlDocumentQueryBuilder setDocumentFilters(@NonNull final DocumentFilterList documentFilters)
	{
		this.documentFilters = documentFilters;
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

	public SqlDocumentQueryBuilder setRecordIds(final Set<DocumentId> recordIds)
	{
		this.recordIds = recordIds != null
				? ImmutableSet.copyOf(recordIds)
				: ImmutableSet.of();

		return this;
	}

	public SqlDocumentQueryBuilder noSorting(final boolean noSorting)
	{
		this.noSorting = noSorting;
		if (noSorting)
		{
			orderBys = DocumentQueryOrderByList.EMPTY;
		}
		return this;
	}

	public boolean isSorting()
	{
		return !isNoSorting();
	}

	public SqlDocumentQueryBuilder setOrderBys(final DocumentQueryOrderByList orderBys)
	{
		// Don't throw exception if noSorting is true. Just do nothing.
		// REASON: it gives us better flexibility when this builder is handled by different methods, each of them adding stuff to it
		// Check.assume(!noSorting, "sorting enabled for {}", this);
		if (noSorting)
		{
			return this;
		}

		this.orderBys = orderBys != null ? orderBys : DocumentQueryOrderByList.EMPTY;
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

	public static SqlComposedKey extractComposedKey(
			final DocumentId recordId,
			final List<? extends SqlEntityFieldBinding> keyFields)
	{
		final int count = keyFields.size();
		if (count < 1)
		{
			throw new AdempiereException("Invalid composed key: " + keyFields);
		}

		final List<Object> composedKeyParts = recordId.toComposedKeyParts();
		if (composedKeyParts.size() != count)
		{
			throw new AdempiereException("Invalid composed key '" + recordId + "'. Expected " + count + " parts but it has " + composedKeyParts.size());
		}

		final ImmutableSet.Builder<String> keyColumnNames = ImmutableSet.builder();
		final ImmutableMap.Builder<String, Object> values = ImmutableMap.builder();
		for (int i = 0; i < count; i++)
		{
			final SqlEntityFieldBinding keyField = keyFields.get(i);
			final String keyColumnName = keyField.getColumnName();
			keyColumnNames.add(keyColumnName);

			final Object valueObj = composedKeyParts.get(i);

			@Nullable final Object valueConv = DataTypes.convertToValueClass(
					keyColumnName,
					valueObj,
					keyField.getWidgetType(),
					keyField.getSqlValueClass(),
					null);

			if (!JSONNullValue.isNull(valueConv))
			{
				values.put(keyColumnName, valueConv);
			}
		}

		return SqlComposedKey.of(keyColumnNames.build(), values.build());
	}
}
