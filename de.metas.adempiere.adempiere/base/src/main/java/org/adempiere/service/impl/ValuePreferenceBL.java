package org.adempiere.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Preference;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Services;
import lombok.Value;

import javax.annotation.Nullable;

public class ValuePreferenceBL implements IValuePreferenceBL
{
	@Override
	public IUserValuePreferences getWindowPreferences(final Properties ctx, final AdWindowId adWindowId)
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
	Map<Optional<AdWindowId>, IUserValuePreferences> retrieveAllWindowPreferences(final int AD_Client_ID, final int AD_Org_ID, final int AD_User_ID)
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

	@Value(staticConstructor = "of")
	private static final class UserValuePreference implements IUserValuePreference
	{
		private final Optional<AdWindowId> adWindowIdOptional;
		private final String name;
		private final String value;

		@Override
		public <T> T getValue(final Class<T> clazz)
		{
			final String valueStr = getValue();
			if (String.class.equals(clazz))
			{
				@SuppressWarnings("unchecked") final T value = (T)valueStr;
				return value;
			}

			if (Integer.class.equals(clazz))
			{
				try
				{
					@SuppressWarnings("unchecked") final T value = (T)(Integer)Integer.parseInt(valueStr);
					return value;
				}
				catch (final NumberFormatException e)
				{
					@SuppressWarnings("unchecked") final T value = (T)Integer.valueOf(0);
					return value;
				}
			}

			if (Boolean.class.equals(clazz))
			{
				@SuppressWarnings("unchecked") final T value = (T)(Boolean)DisplayType.toBoolean(valueStr);
				return value;
			}

			final int tableIdOrNull = InterfaceWrapperHelper.getTableIdOrNull(clazz);
			if (tableIdOrNull > 0)
			{
				final int recordId;
				try
				{
					recordId = Integer.parseInt(valueStr);
				}
				catch (final NumberFormatException e)
				{
					return null;
				}
				final T value = create(TableRecordReference.of(tableIdOrNull, recordId).getModel(PlainContextAware.newOutOfTrx()), clazz);
				return value;
			}

			return null;
		}

		@Override public AdWindowId getAdWindowId()
		{
			return adWindowIdOptional.orElse(null);
		}

	}

	private static final class UserValuePreferences implements IUserValuePreferences
	{
		public static final UserValuePreferences EMPTY = new UserValuePreferences();

		private final Optional<AdWindowId> adWindowIdOptional;
		private final Map<String, IUserValuePreference> name2value;

		private UserValuePreferences(@NonNull final UserValuePreferencesBuilder builder)
		{
			adWindowIdOptional = builder.adWindowIdOptional;
			name2value = ImmutableMap.copyOf(builder.name2value);
		}

		private UserValuePreferences()
		{
			adWindowIdOptional = Optional.empty();
			name2value = ImmutableMap.of();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Window_ID", adWindowIdOptional)
					.add("values", name2value)
					.toString();
		}

		@Nullable @Override
		public AdWindowId getAdWindowId()
		{
			return adWindowIdOptional.orElse(null);
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

		@Override
		public <T> T getValue(final String name, final Class<T> clazz)
		{
			final IUserValuePreference userValuePreference = name2value.get(name);
			if (userValuePreference == null)
			{
				return null;
			}
			return userValuePreference.getValue(clazz);
		}
	}

	private static final class UserValuePreferencesBuilder
	{
		public static Collector<I_AD_Preference, ?, ImmutableMap<Optional<AdWindowId>, IUserValuePreferences>> byWindowIdCollector()
		{
			return Collectors.collectingAndThen(
					Collectors.groupingBy((I_AD_Preference adPreference) -> extractAdWindowId(adPreference), collector()) // downstream collector: AD_Window_ID->IUserValuePreferences
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

		private static Optional<AdWindowId> extractAdWindowId(final I_AD_Preference adPreference)
		{
			return AdWindowId.optionalOfRepoId(adPreference.getAD_Window_ID());
		}

		private Optional<AdWindowId> adWindowIdOptional;
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
			final Optional<AdWindowId> currentWindowId = extractAdWindowId(adPreference);
			if (isEmpty())
			{
				adWindowIdOptional = currentWindowId;
			}

			else if (!adWindowIdOptional.equals(currentWindowId))
			{
				throw new IllegalArgumentException("Preference " + adPreference + "'s AD_Window_ID=" + currentWindowId + " is not matching builder's AD_Window_ID=" + adWindowIdOptional);
			}

			final String attributeName = adPreference.getAttribute();
			final String attributeValue = adPreference.getValue();
			name2value.put(attributeName, UserValuePreference.of(adWindowIdOptional, attributeName, attributeValue));
			return this;
		}

		public UserValuePreferencesBuilder addAll(final UserValuePreferencesBuilder fromBuilder)
		{
			if (fromBuilder == null || fromBuilder.isEmpty())
			{
				return this;
			}

			if (!isEmpty() && !adWindowIdOptional.equals(fromBuilder.adWindowIdOptional))
			{
				throw new IllegalArgumentException("Builder " + fromBuilder + "'s AD_Window_ID=" + fromBuilder.adWindowIdOptional + " is not matching builder's AD_Window_ID=" + adWindowIdOptional);
			}

			name2value.putAll(fromBuilder.name2value);

			return this;
		}
	}
}
