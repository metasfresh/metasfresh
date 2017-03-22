package org.adempiere.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Preference;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

public class ValuePreferenceBL implements IValuePreferenceBL
{
	@Override
	public IUserValuePreferences getWindowPreferences(final Properties ctx, final int adWindowId)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_User_ID = Env.getAD_User_ID(ctx);
		return retrieveAllWindowPreferences(AD_Client_ID, AD_Org_ID, AD_User_ID)
				.getOrDefault(adWindowId, UserValuePreferences.EMPTY);
	}

	@Override
	public Collection<IUserValuePreferences> getAllWindowPreferences(final int AD_Client_ID, final int AD_Org_ID, final int AD_User_ID)
	{
		return retrieveAllWindowPreferences(AD_Client_ID, AD_Org_ID, AD_User_ID).values();
	}

	@Cached(cacheName = I_AD_Preference.Table_Name + "#by#AD_Window_ID#Attribute")
	Map<Integer, IUserValuePreferences> retrieveAllWindowPreferences(final int AD_Client_ID, final int AD_Org_ID, final int AD_User_ID)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Preference.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_AD_Preference.COLUMNNAME_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System, AD_Client_ID)
				.addInArrayOrAllFilter(I_AD_Preference.COLUMNNAME_AD_Org_ID, Env.CTXVALUE_AD_Org_ID_System, AD_Org_ID)
				.addInArrayOrAllFilter(I_AD_Preference.COLUMNNAME_AD_User_ID, null, Env.CTXVALUE_AD_User_ID_System, AD_User_ID)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_Preference.COLUMNNAME_AD_Window_ID, Direction.Ascending, Nulls.First)
				.addColumn(I_AD_Preference.COLUMNNAME_Attribute, Direction.Ascending, Nulls.First)
				.addColumn(I_AD_Preference.COLUMNNAME_AD_Client_ID, Direction.Ascending, Nulls.First)
				.addColumn(I_AD_Preference.COLUMNNAME_AD_User_ID, Direction.Ascending, Nulls.First)
				.addColumn(I_AD_Preference.COLUMNNAME_AD_Org_ID, Direction.Ascending, Nulls.First)
				.endOrderBy()
				//
				.create().stream()
				.collect(UserValuePreferencesBuilder.byWindowIdCollector())
				//
				;
	}

	private static final class UserValuePreference implements IUserValuePreference
	{
		public static UserValuePreference of(final int adWindowId, final String name, final String value)
		{
			return new UserValuePreference(adWindowId, name, value);
		}

		private final int adWindowId;
		private final String name;
		private final String value;

		public UserValuePreference(final int adWindowId, final String name, final String value)
		{
			super();
			this.adWindowId = adWindowId;
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("name", name)
					.add("value", value)
					.add("AD_Window_ID", adWindowId)
					.toString();
		}

		@Override
		public int getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String getValue()
		{
			return value;
		}
	}

	private static final class UserValuePreferences implements IUserValuePreferences
	{
		public static final UserValuePreferences EMPTY = new UserValuePreferences();

		private final int adWindowId;
		private final Map<String, IUserValuePreference> name2value;

		private UserValuePreferences(final UserValuePreferencesBuilder builder)
		{
			super();
			adWindowId = builder.adWindowId;
			name2value = ImmutableMap.copyOf(builder.name2value);
		}

		/** empty constructor */
		private UserValuePreferences()
		{
			super();
			adWindowId = 0;
			name2value = ImmutableMap.of();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Window_ID", adWindowId)
					.add("values", name2value)
					.toString();
		}

		@Override
		public int getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public String getValue(final String name)
		{
			final IUserValuePreference userValuePreference = name2value.get(name);
			if (userValuePreference == null)
			{
				return null;
			}
			return userValuePreference.getValue();
		}

		@Override
		public Collection<IUserValuePreference> values()
		{
			return name2value.values();
		}
	}

	private static final class UserValuePreferencesBuilder
	{
		public static Collector<I_AD_Preference, ?, ImmutableMap<Integer, IUserValuePreferences>> byWindowIdCollector()
		{
			return Collectors.collectingAndThen(
					Collectors.groupingBy(adPreference -> extractAD_Window_ID(adPreference), collector()) // downstream collector: AD_Window_ID->IUserValuePreferences
					, ImmutableMap::copyOf // finisher
			);
		}

		public static Collector<I_AD_Preference, UserValuePreferencesBuilder, IUserValuePreferences> collector()
		{
			return Collector.of(
					UserValuePreferencesBuilder::new // supplier
					, UserValuePreferencesBuilder::add // accumulator
					, (l, r) -> l.addAll(r) // combiner
					, UserValuePreferencesBuilder::build // finisher
					, Collector.Characteristics.UNORDERED // characteristics
			);
		}

		private static final int extractAD_Window_ID(final I_AD_Preference adPreference)
		{
			final int adWindowId = adPreference.getAD_Window_ID();
			return adWindowId > 0 ? adWindowId : IUserValuePreference.AD_WINDOW_ID_NONE;
		}

		private int adWindowId = 0;
		private final Map<String, IUserValuePreference> name2value = new HashMap<>();

		private UserValuePreferencesBuilder()
		{
			super();
		}

		public UserValuePreferences build()
		{
			if (isEmpty())
			{
				return UserValuePreferences.EMPTY;
			}
			return new UserValuePreferences(this);
		}

		public boolean isEmpty()
		{
			return name2value.isEmpty();
		}

		public UserValuePreferencesBuilder add(final I_AD_Preference adPreference)
		{
			final int currentWindowId = extractAD_Window_ID(adPreference);
			if (isEmpty())
			{
				adWindowId = currentWindowId;
			}
			else if (adWindowId != currentWindowId)
			{
				throw new IllegalArgumentException("Preference " + adPreference + "'s AD_Window_ID=" + currentWindowId + " is not matching builder's AD_Window_ID=" + adWindowId);
			}

			final String attributeName = adPreference.getAttribute();
			final String attributeValue = adPreference.getValue();
			name2value.put(attributeName, UserValuePreference.of(adWindowId, attributeName, attributeValue));
			return this;
		}

		public UserValuePreferencesBuilder addAll(final UserValuePreferencesBuilder fromBuilder)
		{
			if (fromBuilder == null || fromBuilder.isEmpty())
			{
				return this;
			}

			if (!isEmpty() && adWindowId != fromBuilder.adWindowId)
			{
				throw new IllegalArgumentException("Builder " + fromBuilder + "'s AD_Window_ID=" + fromBuilder.adWindowId + " is not matching builder's AD_Window_ID=" + adWindowId);
			}

			name2value.putAll(fromBuilder.name2value);

			return this;
		}
	}
}
