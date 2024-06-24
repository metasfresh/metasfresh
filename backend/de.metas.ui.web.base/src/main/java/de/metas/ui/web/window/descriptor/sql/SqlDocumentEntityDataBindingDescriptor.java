package de.metas.ui.web.window.descriptor.sql;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.security.IUserRolePermissions;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.security.permissions.Access;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.sql.SqlDocumentQueryBuilder;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.expression.api.ICachedStringExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IPair;
import org.compiere.model.POInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class SqlDocumentEntityDataBindingDescriptor implements DocumentEntityDataBindingDescriptor, SqlEntityBinding
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static SqlDocumentEntityDataBindingDescriptor cast(final DocumentEntityDataBindingDescriptor descriptor)
	{
		return (SqlDocumentEntityDataBindingDescriptor)descriptor;
	}

	private static final String TABLEALIAS_Master = "master";

	public static final String FIELDNAME_Version = "Updated";

	// //
	// // Paging constants
	// public static final String COLUMNNAME_Paging_UUID = "_sel_UUID";
	// public static final String COLUMNNAME_Paging_SeqNo = "_sel_SeqNo";
	// public static final String COLUMNNAME_Paging_Record_ID = "_sel_Record_ID";

	private final DocumentsRepository documentsRepository;
	private final String sqlTableName;
	private final String sqlTableAlias;
	private final String sqlLinkColumnName;
	private final String sqlParentLinkColumnName;

	private final ICachedStringExpression sqlSelectAllFrom;
	private final ICachedStringExpression sqlWhereClause;
	private final DocumentQueryOrderByList defaultOrderBys;

	private final ImmutableMap<String, SqlDocumentFieldDataBindingDescriptor> _fieldsByFieldName;
	private final ImmutableList<SqlDocumentFieldDataBindingDescriptor> keyFields;

	private final Optional<String> sqlSelectVersionById;

	private SqlDocumentEntityDataBindingDescriptor(final Builder builder)
	{
		documentsRepository = builder.getDocumentsRepository();

		sqlTableName = builder.getTableName();
		Check.assumeNotEmpty(sqlTableName, "sqlTableName is not empty");

		sqlTableAlias = builder.getTableAlias();
		Check.assumeNotEmpty(sqlTableAlias, "sqlTableAlias is not empty");

		sqlLinkColumnName = builder.getSqlLinkColumnName();
		sqlParentLinkColumnName = builder.getSqlParentLinkColumnName();

		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		keyFields = ImmutableList.copyOf(builder.getKeyFields());

		sqlSelectAllFrom = builder.getSqlSelectAll()
				.caching();
		sqlWhereClause = builder.getSqlWhereClauseExpression()
				.caching();

		defaultOrderBys = builder.getDefaultOrderBys();

		sqlSelectVersionById = builder.getSqlSelectVersionById();
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("sqlTableAlias", sqlTableAlias)
				// .add("sqlKeyColumnName", sqlKeyColumnName)
				// .add("sqlLinkColumnName", sqlLinkColumnName)
				// .add("sqlParentLinkColumnName", sqlParentLinkColumnName)
				// .add("orderBys", orderBys)
				// .add("fields", _fieldsByFieldName.isEmpty() ? null : _fieldsByFieldName.values())
				.toString();
	}

	@Override
	public DocumentsRepository getDocumentsRepository()
	{
		return documentsRepository;
	}

	@Override
	public String getTableName()
	{
		return sqlTableName;
	}

	@Override
	public String getTableAlias()
	{
		return sqlTableAlias;
	}

	public boolean isSingleKey()
	{
		return keyFields.size() == 1;
	}

	public List<SqlDocumentFieldDataBindingDescriptor> getKeyFields()
	{
		return keyFields;
	}

	private String getSingleKeyColumnName()
	{
		Check.assume(keyFields.size() == 1, "Single key field: {}", this);
		return keyFields.get(0).getColumnName();
	}

	/**
	 * @return the column name from this entity which will link to parent
	 */
	public String getLinkColumnName()
	{
		return sqlLinkColumnName;
	}

	/**
	 * @return the column name from parent entity on which {@link #getLinkColumnName()} shall join
	 */
	public String getParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public IStringExpression getSqlSelectAllFrom()
	{
		return sqlSelectAllFrom;
	}

	@Override
	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public String getSqlWhereClauseById(final int recordId)
	{
		return sqlTableName + "." + getSingleKeyColumnName() + " = " + recordId;
	}

	public String getSqlWhereClauseById(@NonNull final DocumentId documentId)
	{
		if (documentId.isInt())
		{
			return getSqlWhereClauseById(documentId.toInt());
		}
		else
		{
			return SqlDocumentQueryBuilder
					.extractComposedKey(documentId, getKeyFields())
					.getSqlWhereClauseById(sqlTableName);
		}

	}

	public DocumentQueryOrderByList getDefaultOrderBys()
	{
		return defaultOrderBys;
	}

	@Override
	public SqlDocumentFieldDataBindingDescriptor getFieldByFieldName(final String fieldName)
	{
		final SqlDocumentFieldDataBindingDescriptor field = _fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field found for fieldName=" + fieldName + " in " + this);
		}
		return field;
	}

	public Collection<SqlDocumentFieldDataBindingDescriptor> getFields()
	{
		return _fieldsByFieldName.values();
	}

	public Optional<String> getSqlSelectVersionById()
	{
		return sqlSelectVersionById;
	}

	@Override
	public boolean isVersioningSupported()
	{
		return sqlSelectVersionById.isPresent();
	}

	@SuppressWarnings("UnusedReturnValue")
	@ToString(doNotUseGetters = true)
	public static final class Builder implements DocumentEntityDataBindingDescriptorBuilder
	{
		private SqlDocumentEntityDataBindingDescriptor _built = null;

		private DocumentsRepository documentsRepository;
		private String _sqlTableName;
		private String _tableAlias;
		private String _sqlLinkColumnName;
		private String _sqlParentLinkColumnName;
		private String _sqlWhereClause = null;
		private IStringExpression _sqlWhereClauseExpression;

		//
		private IStringExpression _sqlSelectAll; // will be built

		private final LinkedHashMap<String, SqlDocumentFieldDataBindingDescriptor> _fieldsByFieldName = new LinkedHashMap<>();

		private Builder()
		{
		}

		@Override
		public SqlDocumentEntityDataBindingDescriptor getOrBuild()
		{
			if (_built == null)
			{
				_built = new SqlDocumentEntityDataBindingDescriptor(this);
			}
			return _built;
		}

		private void assertNotBuilt()
		{
			if (_built != null)
			{
				throw new IllegalStateException("Already built: " + this);
			}
		}

		private IStringExpression getSqlSelectAll()
		{
			if (_sqlSelectAll == null)
			{
				buildSqlSelects();
			}
			return _sqlSelectAll;
		}

		/**
		 * SELECT ... FROM ....
		 */
		private void buildSqlSelects()
		{
			final Collection<SqlDocumentFieldDataBindingDescriptor> fields = getFieldsByFieldName().values();
			if (fields.isEmpty())
			{
				throw new AdempiereException("No SQL fields found; this=" + this);
			}

			final List<String> sqlSelectValuesList = new ArrayList<>(fields.size());
			final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>(fields.size());
			for (final SqlDocumentFieldDataBindingDescriptor sqlField : fields)
			{
				//
				// Value column
				final SqlSelectValue sqlSelectValue = sqlField.getSqlSelectValue();
				sqlSelectValuesList.add(sqlSelectValue.toSqlStringWithColumnNameAlias());

				//
				// Display column, if any
				if (sqlField.getSqlSelectDisplayValue() != null)
				{
					final SqlSelectDisplayValue sqlSelectDisplayValue = sqlField.getSqlSelectDisplayValue();
					sqlSelectDisplayNamesList.add(sqlSelectDisplayValue.toStringExpressionWithColumnNameAlias());
				}
			}

			//
			_sqlSelectAll = buildSqlSelect(sqlSelectValuesList, sqlSelectDisplayNamesList);
		}

		private IStringExpression buildSqlSelect(final List<String> sqlSelectValuesList, final List<IStringExpression> sqlSelectDisplayNamesList)
		{
			final String sqlTableName = getTableName();
			final String sqlTableAlias = getTableAlias();

			final IStringExpression sqlInnerExpr = IStringExpression.composer()
					.append("SELECT ")
					.append("\n ").append(Joiner.on("\n, ").join(sqlSelectValuesList))
					.append("\n FROM ").append(sqlTableName)
					.wrap(AccessSqlStringExpression.wrapper(sqlTableName, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ)) // security
					.build();

			final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
					.append("SELECT ")
					.append("\n").append(sqlTableAlias).append(".*"); // Value fields

			// DisplayName fields
			if (!sqlSelectDisplayNamesList.isEmpty())
			{
				sqlBuilder.append("\n, ").appendAllJoining("\n, ", sqlSelectDisplayNamesList);
			}

			sqlBuilder.append("\n FROM (").append(sqlInnerExpr).append(") ").append(sqlTableAlias); // FROM

			return sqlBuilder.build();
		}

		private IStringExpression getSqlWhereClauseExpression()
		{
			if (_sqlWhereClauseExpression == null)
			{
				_sqlWhereClauseExpression = buildSqlWhereClause();
			}
			return _sqlWhereClauseExpression;
		}

		private IStringExpression buildSqlWhereClause()
		{
			if (Check.isEmpty(_sqlWhereClause, true))
			{
				return IStringExpression.NULL;
			}

			final String sqlWhereClausePrepared = _sqlWhereClause.trim()
					// NOTE: because current AD_Tab.WhereClause contain fully qualified TableNames, we shall replace them with our table alias
					// (e.g. "R_Request.SalesRep_ID=@#AD_User_ID@" shall become ""tableAlias.SalesRep_ID=@#AD_User_ID@"
					.replace(getTableName() + ".", getTableAlias() + ".") //
					;

			return Services.get(IExpressionFactory.class).compileOrDefault(sqlWhereClausePrepared, IStringExpression.NULL, IStringExpression.class);
		}

		private DocumentQueryOrderByList getDefaultOrderBys()
		{
			// Build the ORDER BY from fields
			return getFieldsByFieldName()
					.values()
					.stream()
					.filter(field -> field.isDefaultOrderBy())
					.sorted(Comparator.comparing(SqlDocumentFieldDataBindingDescriptor::getDefaultOrderByPriority))
					.map(field -> DocumentQueryOrderBy.byFieldName(field.getFieldName(), field.isDefaultOrderByAscending()))
					.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());
		}

		public Builder setDocumentsRepository(final DocumentsRepository documentsRepository)
		{
			assertNotBuilt();
			this.documentsRepository = documentsRepository;
			return this;
		}

		private DocumentsRepository getDocumentsRepository()
		{
			Check.assumeNotNull(documentsRepository, "Parameter documentsRepository is not null");
			return documentsRepository;
		}

		public Builder setTableName(final String sqlTableName)
		{
			assertNotBuilt();
			_sqlTableName = sqlTableName;
			return this;
		}

		public String getTableName()
		{
			return _sqlTableName;
		}

		public POInfo getPOInfo()
		{
			return POInfo.getPOInfo(getTableName());
		}

		private Builder setTableAlias(final String sqlTableAlias)
		{
			assertNotBuilt();
			_tableAlias = sqlTableAlias;
			return this;
		}

		public Builder setTableAliasFromDetailId(@Nullable final DetailId detailId)
		{
			if (detailId == null)
			{
				setTableAlias(TABLEALIAS_Master);
			}
			else
			{
				setTableAlias(detailId.getTableAlias());
			}

			return this;
		}

		public String getTableAlias()
		{
			return _tableAlias;
		}

		public Builder setChildToParentLinkColumnNames(@Nullable final IPair<String, String> childToParentLinkColumnNames)
		{
			assertNotBuilt();

			if (childToParentLinkColumnNames != null)
			{
				_sqlLinkColumnName = childToParentLinkColumnNames.getLeft();
				_sqlParentLinkColumnName = childToParentLinkColumnNames.getRight();
			}
			else
			{
				_sqlLinkColumnName = null;
				_sqlParentLinkColumnName = null;
			}
			return this;
		}

		public String getSqlLinkColumnName()
		{
			return _sqlLinkColumnName;
		}

		public String getSqlParentLinkColumnName()
		{
			return _sqlParentLinkColumnName;
		}

		public Builder setSqlWhereClause(final String sqlWhereClause)
		{
			assertNotBuilt();
			Check.assumeNotNull(sqlWhereClause, "Parameter sqlWhereClause is not null");
			_sqlWhereClause = sqlWhereClause;
			return this;
		}

		public Builder addField(@NonNull final DocumentFieldDataBindingDescriptor field)
		{
			assertNotBuilt();

			final SqlDocumentFieldDataBindingDescriptor sqlField = SqlDocumentFieldDataBindingDescriptor.castOrNull(field);
			_fieldsByFieldName.put(sqlField.getFieldName(), sqlField);

			return this;
		}

		private Map<String, SqlDocumentFieldDataBindingDescriptor> getFieldsByFieldName()
		{
			return _fieldsByFieldName;
		}

		public SqlDocumentFieldDataBindingDescriptor getField(final String fieldName)
		{
			final SqlDocumentFieldDataBindingDescriptor field = getFieldsByFieldName().get(fieldName);
			if (field == null)
			{
				throw new AdempiereException("Field " + fieldName + " not found in " + this);
			}
			return field;
		}

		private List<SqlDocumentFieldDataBindingDescriptor> getKeyFields()
		{
			return getFieldsByFieldName()
					.values()
					.stream()
					.filter(SqlDocumentFieldDataBindingDescriptor::isKeyColumn)
					.collect(ImmutableList.toImmutableList());
		}

		private Optional<String> getSqlSelectVersionById()
		{
			if (getFieldsByFieldName().get(FIELDNAME_Version) == null)
			{
				return Optional.empty();
			}

			final List<SqlDocumentFieldDataBindingDescriptor> keyColumns = getKeyFields();
			if (keyColumns.size() != 1)
			{
				return Optional.empty();
			}

			final String keyColumnName = keyColumns.get(0).getColumnName();
			final String sql = "SELECT " + FIELDNAME_Version + " FROM " + getTableName() + " WHERE " + keyColumnName + "=?";
			return Optional.of(sql);
		}
	}
}
