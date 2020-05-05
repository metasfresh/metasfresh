package de.metas.ui.web.window.model.lookup;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.NamePair;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookups;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates.LookupValueFilterPredicate;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

/**
 * Effective context used to validate lookups data.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@ToString
@EqualsAndHashCode
public final class LookupDataSourceContext implements Evaluatee2, IValidationContext
{
	public static Builder builder(final String lookupTableName)
	{
		return new Builder(lookupTableName);
	}

	public static Builder builderWithoutTableName()
	{
		return new Builder(null);
	}

	private static final Logger logger = LogManager.getLogger(LookupDataSourceContext.class);

	public static final String FILTER_Any = "%";
	private static final String FILTER_Any_SQL = "'%'";

	public static final CtxName PARAM_AD_Language = CtxNames.parse(Env.CTXNAME_AD_Language);
	public static final CtxName PARAM_UserRolePermissionsKey = AccessSqlStringExpression.PARAM_UserRolePermissionsKey;

	public static final CtxName PARAM_Filter = CtxNames.parse("Filter");
	public static final CtxName PARAM_FilterSql = CtxNames.parse("FilterSql");
	public static final CtxName PARAM_ViewId = CtxNames.parse("ViewId");
	public static final CtxName PARAM_ViewSize = CtxNames.parse("ViewSize");

	private final String lookupTableName;
	private final ImmutableMap<String, Object> parameterValues;
	private final Object idToFilter;
	private final INamePairPredicate postQueryPredicate;

	private LookupDataSourceContext(
			final String lookupTableName,
			final Map<String, Object> values,
			final Object idToFilter,
			final INamePairPredicate postQueryPredicate)
	{
		this.lookupTableName = lookupTableName;
		parameterValues = ImmutableMap.copyOf(values);
		this.idToFilter = idToFilter;
		this.postQueryPredicate = postQueryPredicate;
	}

	public String getFilter()
	{
		return get_ValueAsString(PARAM_Filter.getName());
	}

	public String getFilterOrIfAny(final String whenAnyFilter)
	{
		final String filterStr = getFilter();
		if (filterStr == FILTER_Any)
		{
			return whenAnyFilter;
		}
		return filterStr;
	}

	public boolean isAnyFilter()
	{
		final String filterStr = getFilter();
		return filterStr == FILTER_Any;
	}

	public LookupValueFilterPredicate getFilterPredicate()
	{
		final String filterStr = getFilter();
		if (filterStr == FILTER_Any)
		{
			return LookupValueFilterPredicates.MATCH_ALL;
		}
		return LookupValueFilterPredicates.ofFilterAndLanguage(filterStr, getAD_Language());
	}

	public int getLimit(final int defaultValue)
	{
		return get_ValueAsInt(SqlForFetchingLookups.PARAM_Limit.getName(), defaultValue);
	}

	public int getOffset(final int defaultValue)
	{
		return get_ValueAsInt(SqlForFetchingLookups.PARAM_Offset.getName(), defaultValue);
	}

	public String getAD_Language()
	{
		return get_ValueAsString(PARAM_AD_Language.getName());
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
		if (value == null)
		{
			return null;
		}
		else if (value instanceof LookupValue)
		{
			return ((LookupValue)value).getIdAsString();
		}
		else if (value instanceof Boolean)
		{
			return StringUtils.ofBoolean((Boolean)value);
		}
		else if (TimeUtil.isDateOrTimeObject(value))
		{
			// NOTE: because this evaluatee is used to build SQL expressions too,
			// we have to make sure the dates are converted to JDBC format
			final Timestamp jdbcTimestamp = TimeUtil.asTimestamp(value);
			return Env.toString(jdbcTimestamp);
		}
		else
		{
			return value.toString();
		}
	}

	@Override
	public Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
	{
		final Object value = parameterValues.get(variableName);
		return convertValueToInteger(value, defaultValue);
	}

	private static Integer convertValueToInteger(final Object value, final Integer defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}

		try
		{
			if (value instanceof Number)
			{
				return ((Number)value).intValue();
			}
			else if (value instanceof LookupValue)
			{
				return ((LookupValue)value).getIdAsInt();
			}
			else
			{
				return NumberUtils.asInteger(value, defaultValue);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed lookup's ID to integer: {}. Returning default value={}.", value, defaultValue, ex);
			return defaultValue;
		}
	}

	@Override
	public Date get_ValueAsDate(final String variableName, final Date defaultValue)
	{
		final Object value = parameterValues.get(variableName);
		return convertValueToDate(value, defaultValue);
	}

	private static Date convertValueToDate(final Object value, final Date defaultValue)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			// note: always use TimeUtil.asDate to make sure that we really get a "Date"-date and not a "Timestamp"-date
			// goal: avoid trouble with equals()
			if (value instanceof String)
			{
				return TimeUtil.asDate(Env.parseTimestamp(value.toString()));
			}
			else
			{
				return TimeUtil.asDate(value);
			}
		}
		catch (Exception ex)
		{
			logger.warn("Cannot convert '{}' ({}) to to Date. Returning default value: {}.", value, value.getClass(), defaultValue, ex);
			return defaultValue;
		}
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
		if (idToFilter == null)
		{
			return defaultValue;
		}
		else if (idToFilter instanceof Number)
		{
			return ((Number)idToFilter).intValue();
		}
		else
		{
			final String idToFilterStr = idToFilter.toString();
			if (idToFilterStr.isEmpty())
			{
				return defaultValue;
			}
			return Integer.parseInt(idToFilterStr);
		}
	}

	public String getIdToFilterAsString()
	{
		return idToFilter != null ? idToFilter.toString() : null;
	}

	public ViewId getViewId()
	{
		final ViewId viewId = get_ValueAsObject(PARAM_ViewId.getName());
		if (viewId == null)
		{
			throw new AdempiereException("@NotFound@ ViewId: ")
					.setParameter("context", this);
		}

		return viewId;
	}

	//
	//
	//
	// -----------------------------------------------------------------------------------------------------------
	//
	//
	//

	public static final class Builder
	{
		private Evaluatee parentEvaluatee;
		private final String lookupTableName;
		private INamePairPredicate postQueryPredicate = INamePairPredicate.NULL;
		private final Map<String, Object> name2value = new HashMap<>();
		private Object idToFilter;
		private Collection<CtxName> _requiredParameters;
		private boolean _requiredParameters_copyOnAdd = false;

		private final Map<String, Object> valuesCollected = new LinkedHashMap<>();

		private Builder(final String lookupTableName)
		{
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
			collectContextValues(getRequiredParameters(), true);

			//
			// Collect all values required by the post-query predicate
			// failIfNotFound=false because it might be that NOT all postQueryPredicate's parameters are mandatory!
			collectContextValues(CtxNames.parseAll(postQueryPredicate.getParameters()), false);

			//
			// Build the effective context
			return new LookupDataSourceContext(lookupTableName, valuesCollected, idToFilter, postQueryPredicate);
		}

		private Collection<CtxName> getRequiredParameters()
		{
			return _requiredParameters;
		}

		/**
		 * Advises the builder that provided parameters shall be present the context that will be build.
		 *
		 * NOTE: previous required parameters, if any, will be lost.
		 *
		 * @param requiredParameters the required parameters which might also contain default values to fall back to.
		 */
		public Builder setRequiredParameters(@NonNull final Collection<CtxName> requiredParameters)
		{
			_requiredParameters = requiredParameters;
			_requiredParameters_copyOnAdd = true;
			return this;
		}

		/**
		 * Advises the builder that given parameter shall be present the context that will be build
		 *
		 * @param requiredParameter
		 */
		public Builder requiresParameter(@NonNull final CtxName requiredParameter)
		{
			if (_requiredParameters != null && _requiredParameters.contains(requiredParameter))
			{
				// we already have the parameter => do nothing
				return this;
			}

			if (_requiredParameters == null)
			{
				_requiredParameters = new HashSet<>();
				_requiredParameters_copyOnAdd = false;
			}
			else if (_requiredParameters_copyOnAdd)
			{
				_requiredParameters = new HashSet<>(_requiredParameters);
				_requiredParameters_copyOnAdd = false;
			}

			_requiredParameters.add(requiredParameter);

			return this;
		}

		public Builder requiresParameters(@NonNull final Collection<CtxName> requiredParameters)
		{
			requiredParameters.forEach(this::requiresParameter);
			return this;
		}

		/**
		 * Advises the builder that {@link LookupDataSourceContext#PARAM_AD_Language} shall be present the context that will be build
		 */
		public Builder requiresAD_Language()
		{
			requiresParameter(PARAM_AD_Language);
			return this;
		}

		/**
		 * Advises the builder that filter, filterSql, limit and offset parameters shall be present the context that will be build
		 */
		public Builder requiresFilterAndLimit()
		{
			requiresParameter(PARAM_Filter);
			requiresParameter(PARAM_FilterSql);
			requiresParameter(SqlForFetchingLookups.PARAM_Limit);
			requiresParameter(SqlForFetchingLookups.PARAM_Offset);
			return this;
		}

		/**
		 * Advises the builder that {@link LookupDataSourceContext#PARAM_UserRolePermissionsKey} shall be present the context that will be build
		 */
		public Builder requiresUserRolePermissionsKey()
		{
			requiresParameter(PARAM_UserRolePermissionsKey);
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
			putValue(SqlForFetchingLookups.PARAM_Offset, offset);
			putValue(SqlForFetchingLookups.PARAM_Limit, limit);

			return this;
		}

		private static String convertFilterToSql(final String filter)
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
			putValue(SqlForFetchingLookupById.SQL_PARAM_KeyId, sqlId);
			return this;
		}

		public Builder putFilterById(@NonNull final Object id)
		{
			idToFilter = id;
			return this;
		}

		public Builder putShowInactive(final boolean showInactive)
		{
			final String sqlShowInactive = showInactive ? SqlForFetchingLookupById.SQL_PARAM_VALUE_ShowInactive_Yes : SqlForFetchingLookupById.SQL_PARAM_VALUE_ShowInactive_No;
			putValue(SqlForFetchingLookupById.SQL_PARAM_ShowInactive, sqlShowInactive);
			return this;
		}

		private Builder collectContextValues(
				@Nullable final Collection<CtxName> parameters,
				final boolean failIfNotFound)
		{
			if (parameters == null || parameters.isEmpty())
			{
				return this;
			}

			for (final CtxName parameterName : parameters)
			{
				collectContextValue(parameterName, failIfNotFound);
			}

			return this;
		}

		private void collectContextValue(
				@NonNull final CtxName variableName,
				final boolean failIfNotFound)
		{
			if (valuesCollected.containsKey(variableName.getName()))
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
				valuesCollected.put(variableName.getName(), value);
			}
		}

		private Object findContextValueOrNull(@NonNull final CtxName variableName)
		{
			//
			// Check given parameters
			if (name2value.containsKey(variableName.getName()))
			{
				final Object valueObj = name2value.get(variableName.getName());
				if (valueObj != null)
				{
					return valueObj;
				}
			}

			// Fallback to document evaluatee
			if (parentEvaluatee != null)
			{
				final Object value = parentEvaluatee.get_ValueIfExists(variableName.getName(), Object.class).orElse(null);
				if (value != null)
				{
					return value;
				}
			}

			// Fallback to the variableName's default value
			if (variableName.getDefaultValue() != CtxNames.VALUE_NULL)
			{
				return variableName.getDefaultValue();
			}

			// Value not found
			return null;
		}
	}
}
