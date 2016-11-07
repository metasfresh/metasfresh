package org.compiere.apps.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Handles user queries.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UserQueryRepository
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final String FIELD_SEPARATOR = "<^>";
	private static final String SEGMENT_SEPARATOR = "<~>";

	/** Index Join Operator = 0 */
	private static final int INDEX_JOIN = 0;
	/** Index ColumnName = 1 */
	private static final int INDEX_COLUMNNAME = 1;
	/** Index Operator = 2 */
	private static final int INDEX_OPERATOR = 2;
	/** Index Value = 3 */
	private static final int INDEX_VALUE = 3;
	/** Index Value2 = 4 */
	private static final int INDEX_VALUE2 = 4;

	// services
	private static final transient Logger logger = LogManager.getLogger(UserQueryRepository.class);

	// Config
	private final List<IUserQueryField> searchFields;
	private final int adTabId;
	private final int adTableId;
	private final int adUserId;

	// State
	private final transient CCache<ArrayKey, List<I_AD_UserQuery>> adUserQueriesCache = CCache.newLRUCache(I_AD_UserQuery.Table_Name + "#by#AD_Tab_ID#AD_User_ID", 2, CCache.EXPIREMINUTES_Never);

	private UserQueryRepository(final Builder builder)
	{
		super();
		searchFields = ImmutableList.copyOf(builder.getSearchFields());
		adTabId = builder.getAD_Tab_ID();
		adTableId = builder.getAD_Table_ID();
		adUserId = builder.getAD_User_ID();
	}

	private Collection<IUserQueryField> getSearchFields()
	{
		return searchFields;
	}
	
	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private int getAD_Tab_ID()
	{
		return adTabId;
	}

	private int getAD_Table_ID()
	{
		return adTableId;
	}
	
	private int getAD_User_ID()
	{
		return adUserId;
	}

	public List<String> getUserQueryNames()
	{
		final List<String> adUserQueryNames = new ArrayList<>();
		for (final I_AD_UserQuery adUserQuery : getAD_UserQueries())
		{
			adUserQueryNames.add(adUserQuery.getName());
		}

		return adUserQueryNames;
	}

	private List<I_AD_UserQuery> getAD_UserQueries()
	{
		final Properties ctx = getCtx();
		final int adTabId = getAD_Tab_ID();
		final int adUserId = getAD_User_ID();
		final ArrayKey key = mkCacheKey(adTabId, adUserId);
		return adUserQueriesCache.getOrLoad(key, () -> retrieveAD_UserQueries(ctx, adTabId, adUserId));
	}

	private static final ArrayKey mkCacheKey(final int adTabId, final int adUserId)
	{
		return ArrayKey.of(adTabId, adUserId);
	}

	private static final List<I_AD_UserQuery> retrieveAD_UserQueries(final Properties ctx, final int adTabId, final int adUserId)
	{
		final IQueryBuilder<I_AD_UserQuery> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UserQuery.class, ctx, ITrx.TRXNAME_None)
				.addOnlyContextClientOrSystem()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UserQuery.COLUMNNAME_AD_Tab_ID, adTabId)
				//
				.orderBy()
				.addColumn(I_AD_UserQuery.COLUMNNAME_Name)
				.endOrderBy();
				//
		
		if(adUserId > 0 || adUserId == Env.CTXVALUE_AD_User_ID_System)
		{
			queryBuilder.addInArrayFilter(I_AD_UserQuery.COLUMNNAME_AD_User_ID, Env.CTXVALUE_AD_User_ID_System, adUserId, null);
		}
		
		
		return queryBuilder.create().listImmutable(I_AD_UserQuery.class);
	}

	private void resetUserQueriesCache()
	{
		adUserQueriesCache.reset();
	}

	public List<IUserQuery> getUserQueries()
	{
		return getAD_UserQueries()
				.stream()
				.map(adUserQuery -> createUserQuery(adUserQuery))
				.collect(GuavaCollectors.toImmutableList());
	}

	private I_AD_UserQuery getAD_UserQueryByName(final String userQueryName)
	{
		if (Check.isEmpty(userQueryName, true))
		{
			return null;
		}

		for (final I_AD_UserQuery userQuery : getAD_UserQueries())
		{
			if (userQueryName.equals(userQuery.getName()))
			{
				return userQuery;
			}
		}

		return null;
	}

	public IUserQuery getUserQueryByName(final String userQueryName)
	{
		final I_AD_UserQuery adUserQuery = getAD_UserQueryByName(userQueryName);
		if (adUserQuery == null)
		{
			return null;
		}

		return createUserQuery(adUserQuery);
	}

	private IUserQuery createUserQuery(final I_AD_UserQuery adUserQuery)
	{
		final String code = adUserQuery.getCode();
		if (code == null || code.isEmpty())
		{
			return null;
		}

		final List<IUserQueryRestriction> userQueryRestrictions = new ArrayList<>();
		final String[] segmentStrArray = code.split(Pattern.quote(SEGMENT_SEPARATOR));
		for (String segmentStr : segmentStrArray)
		{
			if (!segmentStr.trim().startsWith(Join.AND.getCode())
					&& !segmentStr.trim().startsWith(Join.OR.getCode()))
			{
				segmentStr = Join.AND.getCode() + FIELD_SEPARATOR + code;
			}

			final IUserQueryRestriction row = parseUserQuerySegment(segmentStr);
			if (row != null)
			{
				userQueryRestrictions.add(row);
			}
		}

		final int id = adUserQuery.getAD_UserQuery_ID();
		final String caption = adUserQuery.getName();
		final int adUserId = adUserQuery.getAD_User_ID();
		return UserQuery.of(id, caption, adUserId, userQueryRestrictions);
	}

	private final IUserQueryRestriction parseUserQuerySegment(final String segment)
	{
		final IUserQueryRestriction row = new UserQueryRestriction();
		row.setJoin(Join.AND);
		row.setOperator(Operator.EQUAL);

		final String[] fields = segment.split(Pattern.quote(FIELD_SEPARATOR));
		for (int j = 0; j < fields.length; j++)
		{
			if (j == INDEX_JOIN)
			{
				final String fieldStr = fields[j].trim();
				row.setJoin(Join.forCodeOrAND(fieldStr));
			}
			else if (j == INDEX_COLUMNNAME)
			{
				final IUserQueryField searchField = findSearchFieldByColumnName(fields[j]);
				if (searchField != null)
				{
					row.setSearchField(searchField);
				}
			}
			else if (j == INDEX_OPERATOR)
			{
				final Operator operator = Operator.forCodeOrNull(fields[j]);
				if(operator != null)
				{
					row.setOperator(operator);
				}
			}
			else if (j == INDEX_VALUE || j == INDEX_VALUE2)
			{
				final IUserQueryField searchField = row.getSearchField();
				if (searchField == null)
				{
					logger.warn("No search column found for {}", segment);
					continue;
				}
				final Object value = searchField.convertValueToFieldType(fields[j]);
				if (j == INDEX_VALUE)
				{
					row.setValue(value);
				}
				else
				{
					row.setValueTo(value);
				}
			}
		}

		return row;
	}

	/**
	 * Saves the given rows to {@link MQuery} and to given user query.
	 *
	 * @param rows
	 * @param query
	 * @param userQueryName user query name where to save or <code>null</code>
	 */
	public void saveRowsToQuery(final List<IUserQueryRestriction> rows, final MQuery query, final String userQueryName)
	{
		final StringBuilder userQueryCode = new StringBuilder();

		for (int rowIndex = 0, rowsCount = rows.size(); rowIndex < rowsCount; rowIndex++)
		{
			final IUserQueryRestriction row = rows.get(rowIndex);

			//
			// Join Operator
			Join joinOperator = row.getJoin();
			if (joinOperator == null || rowIndex == 0)
			{
				joinOperator = Join.AND;
			}
			final boolean andCondition = joinOperator == Join.AND;

			//
			// Column
			final IUserQueryField field = row.getSearchField();
			if (field == null)
			{
				continue;
			}
			final String columnName = field.getColumnName();
			final String columnDisplayName = field.getDisplayName().translate(Env.getAD_Language(getCtx()));
			final String columnSql = field.getColumnSQL();

			//
			// Operator
			final Operator operator = row.getOperator();
			if (operator == null)
			{
				continue;
			}

			//
			// Value
			final Object value = row.getValue();
			if (value == null)
			{
				continue;
			}
			final Object valueConverted = field.convertValueToFieldType(value);
			if (valueConverted == null)
			{
				continue;
			}
			final String valueDisplay = field.getValueDisplay(value);

			//
			// Value2
			Object valueTo = null;
			if (operator.isRangeOperator())
			{
				valueTo = row.getValueTo();
				if (valueTo == null)
				{
					continue;
				}

				final Object valueToConverted = field.convertValueToFieldType(valueTo);
				if (valueToConverted == null)
				{
					continue;
				}

				final String valueToDisplay = field.getValueDisplay(valueTo);

				query.addRangeRestriction(columnSql, valueConverted,
						valueToConverted, columnDisplayName, valueDisplay, valueToDisplay,
						andCondition);
			}
			else
			{
				query.addRestriction(columnSql, operator, valueConverted, columnDisplayName, valueDisplay, andCondition);
			}

			//
			// Write user query code
			if (userQueryCode.length() > 0)
			{
				userQueryCode.append(SEGMENT_SEPARATOR);
			}
			userQueryCode.append(joinOperator.getCode())
					.append(FIELD_SEPARATOR).append(columnName)
					.append(FIELD_SEPARATOR).append(operator.getCode())
					.append(FIELD_SEPARATOR).append(value.toString())
					.append(FIELD_SEPARATOR).append(valueTo != null ? valueTo.toString() : "");
		}

		//
		// Save/delete user query if any
		if (userQueryName != null)
		{
			final I_AD_UserQuery userQuery = getAD_UserQueryByName(userQueryName);
			if (userQueryCode.length() <= 0)
			{
				if(userQuery != null)
				{
					deleteUserQuery(userQuery);
				}
			}
			else
			{
				saveUserQuery(userQuery, userQueryName, userQueryCode.toString());
			}
		}
	}

	private void saveUserQuery(@Nullable I_AD_UserQuery userQuery, final String name, final String code)
	{
		Check.assumeNotEmpty(name, "name not empty");
		Check.assumeNotEmpty(code, "code not empty");

		// New user query instance
		if (userQuery == null)
		{
			if (Check.isEmpty(name, true))
			{
				throw new FillMandatoryException("Name");
			}

			final Properties ctx = getCtx();
			userQuery = InterfaceWrapperHelper.create(ctx, I_AD_UserQuery.class, ITrx.TRXNAME_None);
			userQuery.setName(name);
			userQuery.setAD_Tab_ID(getAD_Tab_ID());
			userQuery.setAD_Table_ID(getAD_Table_ID());
			userQuery.setAD_User_ID(getAD_User_ID());
		}

		//
		// Update user query PO
		userQuery.setCode(code);

		//
		// Save user query
		try
		{
			InterfaceWrapperHelper.save(userQuery);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("@SaveError@: " + userQuery.getName(), e);
		}

		resetUserQueriesCache();
	}

	private void deleteUserQuery(final I_AD_UserQuery userQuery)
	{
		if (userQuery == null)
		{
			// nothing to delete
			return;
		}

		try
		{
			InterfaceWrapperHelper.delete(userQuery);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("@DeleteError@: " + userQuery.getName(), e);
		}

		resetUserQueriesCache();
	}

	private final IUserQueryField findSearchFieldByColumnName(final String columnName)
	{
		if (columnName == null || columnName.isEmpty())
		{
			return null;
		}

		for (final IUserQueryField searchField : getSearchFields())
		{
			if (searchField.matchesColumnName(columnName))
			{
				return searchField;
			}
		}

		return null;
	}

	public static final class Builder
	{
		private Integer adTabId = null;
		private Integer adTableId = null;
		private Integer adUserId = null;
		private Collection<IUserQueryField> searchFields;

		private Builder()
		{
			super();
		}

		public UserQueryRepository build()
		{
			return new UserQueryRepository(this);
		}

		@SuppressWarnings("unchecked")
		public Builder setSearchFields(final Collection<? extends IUserQueryField> searchFields)
		{
			this.searchFields = (Collection<IUserQueryField>)searchFields;
			return this;
		}

		private Collection<IUserQueryField> getSearchFields()
		{
			Check.assumeNotNull(searchFields, "searchFields not null");
			return searchFields;
		}

		public Builder setAD_Tab_ID(final int adTabId)
		{
			this.adTabId = adTabId;
			return this;
		}

		private int getAD_Tab_ID()
		{
			Check.assumeNotNull(adTabId, "adTabId not null");
			return adTabId;
		}

		public Builder setAD_Table_ID(final int adTableId)
		{
			this.adTableId = adTableId;
			return this;
		}

		private int getAD_Table_ID()
		{
			Check.assumeNotNull(adTableId, "adTableId not null");
			Check.assume(adTableId > 0, "adTableId > 0");
			return adTableId;
		}
		
		public Builder setAD_User_ID(final int adUserId)
		{
			this.adUserId = adUserId;
			return this;
		}

		public Builder setAD_User_ID_Any()
		{
			this.adUserId = -1;
			return this;
		}

		private int getAD_User_ID()
		{
			Check.assumeNotNull(adUserId, "Parameter adUserId is not null");
			return adUserId;
		}

	}

}
