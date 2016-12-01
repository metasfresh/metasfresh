package de.metas.ui.web.window.model.lookup;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Check;
import org.compiere.util.CtxName;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.NamePair;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;

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
 * Effective context used to validate lookups data.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@SuppressWarnings("serial")
public final class LookupDataSourceContext implements Evaluatee2, IValidationContext, Serializable
{
	public static final Builder builder(final String lookupTableName)
	{
		return new Builder(lookupTableName);
	}

	public static final String FILTER_Any = "%";
	private static final String FILTER_Any_SQL = "'%'";

	public static final CtxName PARAM_AD_Language = CtxName.parse(Env.CTXNAME_AD_Language);
	public static final CtxName PARAM_UserRolePermissionsKey = AccessSqlStringExpression.PARAM_UserRolePermissionsKey;

	public static final CtxName PARAM_Filter = CtxName.parse("Filter");
	public static final CtxName PARAM_FilterSql = CtxName.parse("FilterSql");
	public static final CtxName PARAM_Offset = CtxName.parse("Offset/0");
	public static final CtxName PARAM_Limit = CtxName.parse("Limit/1000");

	private final String lookupTableName;
	private final ImmutableMap<String, Object> parameterValues;
	private final Object idToFilter;
	private final INamePairPredicate postQueryPredicate;

	private LookupDataSourceContext(
			final String lookupTableName //
			, final Map<String, Object> values //
			, final Object idToFilter //
			, final INamePairPredicate postQueryPredicate //
	)
	{
		super();
		this.lookupTableName = lookupTableName;
		parameterValues = ImmutableMap.copyOf(values);
		this.idToFilter = idToFilter;
		this.postQueryPredicate = postQueryPredicate;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("lookupTableName", lookupTableName)
				.add("parameterValues", parameterValues.isEmpty() ? null : parameterValues)
				.add("idToFilter", idToFilter)
				.add("postFilterPredicate", postQueryPredicate)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(lookupTableName, parameterValues, idToFilter, postQueryPredicate);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}
		final LookupDataSourceContext other = (LookupDataSourceContext)obj;

		return Objects.equals(lookupTableName, other.lookupTableName)
				&& Objects.equals(parameterValues, other.parameterValues)
				&& Objects.equals(idToFilter, other.idToFilter)
				&& Objects.equals(postQueryPredicate, other.postQueryPredicate);
	}

	public String getFilter()
	{
		return get_ValueAsString(PARAM_Filter.getName());
	}

	public int getLimit(final int defaultValue)
	{
		return get_ValueAsInt(PARAM_Limit.getName(), defaultValue);
	}

	public int getOffset(final int defaultValue)
	{
		return get_ValueAsInt(PARAM_Offset.getName(), defaultValue);
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return parameterValues.containsKey(variableName);
	}

	@Override
	public <T> T get_ValueAsObject(final String variableName)
	{
		@SuppressWarnings("unchecked")
		final T valueCasted = (T)parameterValues.get(variableName);
		return valueCasted;
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		final Object value = parameterValues.get(variableName);
		return value == null ? null : value.toString();
	}

	@Override
	public String get_ValueOldAsString(final String variableName)
	{
		// TODO implement get_ValueOldAsString
		return null;
	}

	@Override
	public String getTableName()
	{
		return lookupTableName;
	}

	public boolean acceptItem(final NamePair item)
	{
		if (postQueryPredicate == null)
		{
			return true;
		}
		else
		{
			return postQueryPredicate.accept(this, item);
		}
	}

	public Object getIdToFilter()
	{
		return idToFilter;
	}

	public Integer getIdToFilterAsInt(final Integer defaultValue)
	{
		if(idToFilter == null)
		{
			return defaultValue;
		}
		else if(idToFilter instanceof Number)
		{
			return ((Number)idToFilter).intValue();
		}
		else
		{
			final String idToFilterStr = idToFilter.toString();
			if(idToFilterStr.isEmpty())
			{
				return defaultValue;
			}
			return Integer.parseInt(idToFilterStr);
		}
	}

	public static final class Builder
	{
		private Evaluatee parentEvaluatee;
		private final String lookupTableName;
		private INamePairPredicate postQueryPredicate = INamePairPredicate.NULL;
		private final Map<String, Object> name2value = new HashMap<>();
		private Object idToFilter;
		private Collection<String> requiredParameters;

		private final Map<String, Object> valuesCollected = new LinkedHashMap<>();

		private Builder(final String lookupTableName)
		{
			super();

			this.lookupTableName = lookupTableName;

			//
			// Defaults
			putShowInactive(false);
		}

		public LookupDataSourceContext build()
		{
			//
			// Pre-build preparations
			{
				//
				// Standard values, needed by each query
				final Properties ctx = Env.getCtx();
				final String adLanguage = Env.getAD_Language(ctx);
				final String permissionsKey = UserRolePermissionsKey.toPermissionsKeyString(ctx);
				putValue(PARAM_AD_Language, adLanguage);
				putValue(PARAM_UserRolePermissionsKey, permissionsKey);
			}

			//
			// Collect all values required for given query
			// failIfNotFound=true
			collectContextValues(requiredParameters, true);

			//
			// Collect all values required by the post-query predicate
			// failIfNotFound=false because it might be that NOT all postQueryPredicate's parameters are mandatory!
			collectContextValues(postQueryPredicate.getParameters(), false);

			//
			// Build the effective context
			return new LookupDataSourceContext(lookupTableName, valuesCollected, idToFilter, postQueryPredicate);
		}

		public Builder setRequiredParameters(final Collection<String> requiredParameters)
		{
			this.requiredParameters = requiredParameters;
			return this;
		}

		public Builder setParentEvaluatee(final Evaluatee parentEvaluatee)
		{
			this.parentEvaluatee = parentEvaluatee;
			return this;
		}

		public Builder putPostQueryPredicate(final INamePairPredicate postQueryPredicate)
		{
			this.postQueryPredicate = postQueryPredicate;
			return this;
		}

		private Builder putValue(final CtxName name, final Object value)
		{
			name2value.put(name.getName(), value);
			return this;
		}

		public Builder putFilter(final String filter, final int offset, final int limit)
		{
			putValue(PARAM_Filter, filter);
			putValue(PARAM_FilterSql, convertFilterToSql(filter));
			putValue(PARAM_Offset, offset);
			putValue(PARAM_Limit, limit);

			return this;
		}

		private static final String convertFilterToSql(final String filter)
		{
			if (filter == FILTER_Any)
			{
				return FILTER_Any_SQL;
			}

			String searchSql = filter;
			if (!searchSql.startsWith("%"))
			{
				searchSql = "%" + searchSql;
			}
			if (!searchSql.endsWith("%"))
			{
				searchSql += "%";
			}

			return DB.TO_STRING(searchSql);
		}

		protected Builder putFilterByIdParameterName(final String sqlId)
		{
			putValue(SqlLookupDescriptor.SQL_PARAM_KeyId, sqlId);
			return this;
		}

		public Builder putFilterById(final Object id)
		{
			Check.assumeNotNull(id, "Parameter id is not null");
			idToFilter = id;
			return this;
		}

		public Builder putShowInactive(final boolean showInactive)
		{
			final String sqlShowInactive = showInactive ? SqlLookupDescriptor.SQL_PARAM_VALUE_ShowInactive_Yes : SqlLookupDescriptor.SQL_PARAM_VALUE_ShowInactive_No;
			putValue(SqlLookupDescriptor.SQL_PARAM_ShowInactive, sqlShowInactive);
			return this;
		}

		private Builder collectContextValues(final Collection<String> parameters, final boolean failIfNotFound)
		{
			if (parameters == null || parameters.isEmpty())
			{
				return this;
			}

			for (final String parameterName : parameters)
			{
				collectContextValue(parameterName, failIfNotFound);
			}

			return this;
		}

		private void collectContextValue(final String variableName, final boolean failIfNotFound)
		{
			if (valuesCollected.containsKey(variableName))
			{
				return;
			}

			final Object value = findContextValueOrNull(variableName);
			if (value == null)
			{
				if (failIfNotFound)
				{
					throw new ExpressionEvaluationException("@NotFound@: " + variableName);
				}
			}
			else
			{
				valuesCollected.put(variableName, value);
			}
		}

		private final Object findContextValueOrNull(final String variableName)
		{
			//
			// Check given parameters
			if (name2value.containsKey(variableName))
			{
				final Object valueObj = name2value.get(variableName);
				if (valueObj != null)
				{
					return valueObj;
				}
			}

			//
			// Fallback to document evaluatee
			if (parentEvaluatee != null)
			{
				final Object value = parentEvaluatee.get_ValueAsObject(variableName);
				if (value != null)
				{
					return value;
				}
			}

			//
			// Value not found
			return null;
		}
	}
}
