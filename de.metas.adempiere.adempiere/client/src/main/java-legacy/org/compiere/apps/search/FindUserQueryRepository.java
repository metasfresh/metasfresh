package org.compiere.apps.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import java.util.regex.Pattern;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.search.FindAdvancedSearchTableModelRow.Join;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.model.MUserQuery;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;

import com.google.common.collect.ImmutableList;

/**
 * Handles user queries.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
class FindUserQueryRepository
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
	private static final transient Logger logger = LogManager.getLogger(FindUserQueryRepository.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	// Config
	private final List<FindPanelSearchField> searchFields;
	private final int adTabId;
	private final int adTableId;

	// State
	private List<I_AD_UserQuery> userQueries = null;

	private FindUserQueryRepository(final Builder builder)
	{
		super();
		searchFields = ImmutableList.copyOf(builder.getSearchFields());
		adTabId = builder.getAD_Tab_ID();
		adTableId = builder.getAD_Table_ID();
	}

	private Collection<FindPanelSearchField> getSearchFields()
	{
		return searchFields;
	}

	private int getAD_Tab_ID()
	{
		return adTabId;
	}

	private int getAD_Table_ID()
	{
		return adTableId;
	}

	public List<String> getUserQueryNames()
	{
		final List<String> userQueryNames = new ArrayList<>();
		for (final I_AD_UserQuery userQuery : getUserQueries())
		{
			userQueryNames.add(userQuery.getName());
		}

		return userQueryNames;
	}

	private List<I_AD_UserQuery> getUserQueries()
	{
		if (userQueries == null)
		{
			userQueries = new ArrayList<>();
			for (final I_AD_UserQuery userQuery : MUserQuery.get(Env.getCtx(), getAD_Tab_ID()))
			{
				userQueries.add(userQuery);
			}
		}
		return userQueries;
	}

	private void resetUserQueriesCache()
	{
		userQueries = null;
	}

	private I_AD_UserQuery getUserQueryByName(final String userQueryName)
	{
		if (Check.isEmpty(userQueryName, true))
		{
			return null;
		}

		for (final I_AD_UserQuery userQuery : getUserQueries())
		{
			if (userQueryName.equals(userQuery.getName()))
			{
				return userQuery;
			}
		}

		return null;
	}

	public List<FindAdvancedSearchTableModelRow> parseUserQuery(final String userQueryName)
	{
		final List<FindAdvancedSearchTableModelRow> rows = new ArrayList<>();

		final I_AD_UserQuery userQuery = getUserQueryByName(userQueryName);
		if (userQuery == null)
		{
			return null;
		}
		final String code = userQuery.getCode();
		if (code == null || code.isEmpty())
		{
			return null;
		}
		final String[] segments = code.split(Pattern.quote(SEGMENT_SEPARATOR));
		for (String segment : segments)
		{
			if (!segment.trim().startsWith(Join.AND.getCode())
					&& !segment.trim().startsWith(Join.OR.getCode()))
			{
				segment = Join.AND.getCode() + FIELD_SEPARATOR + code;
			}

			final FindAdvancedSearchTableModelRow row = parseUserQuerySegment(segment);
			if (row != null)
			{
				rows.add(row);
			}
		}

		return rows;
	}

	private final FindAdvancedSearchTableModelRow parseUserQuerySegment(final String segment)
	{
		final FindAdvancedSearchTableModelRow row = new FindAdvancedSearchTableModelRow();
		row.setJoin(Join.AND);
		row.setOperator(MQuery.OPERATORS[MQuery.EQUAL_INDEX]);

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
				final FindPanelSearchField searchField = findSearchFieldByColumnName(fields[j]);
				if (searchField != null)
				{
					row.setSearchField(searchField);
				}
			}
			else if (j == INDEX_OPERATOR)
			{
				for (final ValueNamePair vnp : MQuery.OPERATORS)
				{
					if (vnp.getValue().equals(fields[j]))
					{
						row.setOperator(vnp);
						break;
					}
				}
			}
			else if (j == INDEX_VALUE || j == INDEX_VALUE2)
			{
				final FindPanelSearchField searchField = row.getSearchField();
				if (searchField == null)
				{
					logger.warn("No search column found for {}", segment);
					continue;
				}
				final Object value = searchField.parseValueFromString(fields[j]);
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
	public void saveRowsToQuery(final List<FindAdvancedSearchTableModelRow> rows, final MQuery query, final String userQueryName)
	{
		final StringBuilder userQueryCode = new StringBuilder();

		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++)
		{
			final FindAdvancedSearchTableModelRow row = rows.get(rowIndex);

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
			final FindPanelSearchField field = row.getSearchField();
			if (field == null)
			{
				continue;
			}
			final String columnName = field.getColumnName();
			final String infoName = field.getDisplayName();
			final boolean isProductCategoryField = field.isProductCategoryField();
			final String columnSQL = field.getColumnSQL(false);

			//
			// Operator
			final ValueNamePair operatorVNP = row.getOperator();
			if (operatorVNP == null)
			{
				continue;
			}
			final String operatorSql = operatorVNP.getValue();
			final boolean isBinaryOperator = row.isBinaryOperator();

			//
			// Value
			final Object value = row.getValue();
			if (value == null)
			{
				continue;
			}
			final Object parsedValue = field.convertValueToFieldType(value);
			if (parsedValue == null)
			{
				continue;
			}
			String infoDisplay = value.toString();
			if (field.isLookup())
			{
				final Lookup lookup = field.getLookup();
				if (lookup != null)
				{
					infoDisplay = lookup.getDisplay(value);
				}
			}
			else if (field.getDisplayType() == DisplayType.YesNo)
			{
				infoDisplay = msgBL.getMsg(Env.getCtx(), infoDisplay);
			}

			//
			// Value2
			Object value2 = null;
			if (isBinaryOperator)
			{
				value2 = row.getValueTo();
				if (value2 == null)
				{
					continue;
				}
				final Object parsedValue2 = field.convertValueToFieldType(value2);
				final String infoDisplay_to = value2.toString();
				if (parsedValue2 == null)
				{
					continue;
				}
				query.addRangeRestriction(columnSQL, parsedValue,
						parsedValue2, infoName, infoDisplay, infoDisplay_to,
						andCondition);
			}
			else if (isProductCategoryField && MQuery.OPERATORS[MQuery.EQUAL_INDEX].equals(operatorVNP))
			{
				if (!(parsedValue instanceof Integer))
				{
					continue;
				}
				final int productCategoryId = ((Integer)parsedValue).intValue();
				query.addRestriction(field.getSubCategoryWhereClause(productCategoryId), andCondition);
			}
			else
			{
				query.addRestriction(columnSQL, operatorSql, parsedValue, infoName, infoDisplay, andCondition);
			}

			//
			// Write user query code
			if (userQueryCode.length() > 0)
			{
				userQueryCode.append(SEGMENT_SEPARATOR);
			}
			userQueryCode.append(joinOperator.getCode())
					.append(FIELD_SEPARATOR).append(columnName)
					.append(FIELD_SEPARATOR).append(operatorSql)
					.append(FIELD_SEPARATOR).append(value.toString())
					.append(FIELD_SEPARATOR).append(value2 != null ? value2.toString() : "");
		}

		//
		// Save/delete user query if any
		if (userQueryName != null)
		{
			final I_AD_UserQuery userQuery = getUserQueryByName(userQueryName);
			if (userQueryCode.length() <= 0)
			{
				deleteUserQuery(userQuery);
			}
			else
			{
				saveUserQuery(userQuery, userQueryName, userQueryCode.toString());
			}
		}
	}

	private void saveUserQuery(I_AD_UserQuery userQuery, final String name, final String code)
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

			final Properties ctx = Env.getCtx();
			userQuery = InterfaceWrapperHelper.create(ctx, I_AD_UserQuery.class, ITrx.TRXNAME_None);
			userQuery.setName(name);
			userQuery.setAD_Tab_ID(getAD_Tab_ID());
			userQuery.setAD_Table_ID(getAD_Table_ID());
			userQuery.setAD_User_ID(Env.getAD_User_ID(ctx));
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

	private final FindPanelSearchField findSearchFieldByColumnName(final String columnName)
	{
		if (columnName == null || columnName.isEmpty())
		{
			return null;
		}

		for (final FindPanelSearchField searchField : getSearchFields())
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
		private Collection<FindPanelSearchField> searchFields;

		private Builder()
		{
			super();
		}

		public FindUserQueryRepository build()
		{
			return new FindUserQueryRepository(this);
		}

		public Builder setSearchFields(final Collection<FindPanelSearchField> searchFields)
		{
			this.searchFields = searchFields;
			return this;
		}

		private Collection<FindPanelSearchField> getSearchFields()
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

	}
}
