package de.metas.location.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.AddressDisplaySequence;
import de.metas.location.CountryCode;
import de.metas.location.CountryCustomInfo;
import de.metas.location.CountryId;
import de.metas.location.CountrySequences;
import de.metas.location.ICountryDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_User_SaveCustomInfo;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.I_C_Region;
import org.compiere.model.MCountry;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * @author cg
 */
public class CountryDAO implements ICountryDAO
{
	/**
	 * Country Cache
	 */
	private final CCache<Integer, IndexedCountries> countriesCache = CCache.<Integer, IndexedCountries>builder()
			.tableName(I_C_Country.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	/**
	 * C_Country_ID by AD_Client_ID
	 */
	private final CCache<Integer, String> countryCodeByADClientId = CCache.<Integer, String>builder()
			.cacheName(I_C_Country.Table_Name + "#CountryCodeByAD_Client_ID")
			.tableName(I_C_Country.Table_Name)
			.initialCapacity(3)
			.additionalTableNameToResetFor(I_AD_Client.Table_Name)
			.build();

	private static final CountryId DEFAULT_C_Country_ID = CountryId.ofRepoId(101); // Germany

	@NonNull
	private static IndexedCountries retrieveIndexedCountries()
	{
		final List<I_C_Country> countries = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Country.class)
				// .addOnlyActiveRecordsFilter() also include inactive-countries, in case they are sought for by C_Country_ID
				.create()
				.list(I_C_Country.class);

		return new IndexedCountries(countries);
	}

	private static String retrieveCountryCodeIdByADClientId(final int adClientId)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).getById(adClientId);
		final I_AD_Language lang = Services.get(ILanguageDAO.class).retrieveByAD_Language(client.getAD_Language());
		return lang.getCountryCode();
	}

	private static boolean countrySequenceMatches(
			final CountrySequences sequence,
			final OrgId orgId,
			final String adLanguage)
	{
		if (!sequence.getOrgId().equals(orgId))
		{
			return false;
		}

		final String countrySequenceLanguage = sequence.getAdLanguage();
		return countrySequenceLanguage == null || Check.isBlank(countrySequenceLanguage) || countrySequenceLanguage.equals(adLanguage);
	}

	public static CountrySequences toCountrySequences(final I_C_Country_Sequence record)
	{
		return CountrySequences.builder()
				.adLanguage(record.getAD_Language())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				//
				.addressDisplaySequence(AddressDisplaySequence.ofNullable(record.getDisplaySequence()))
				.localAddressDisplaySequence(AddressDisplaySequence.ofNullable(record.getDisplaySequenceLocal()))
				.build();
	}

	@Nullable
	@Override
	public CountryCustomInfo retriveCountryCustomInfo(final Properties ctx, final String trxName)
	{
		final I_C_Country country = getDefault(ctx);

		final I_AD_User_SaveCustomInfo info = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_SaveCustomInfo.class, Env.getCtx(), trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SaveCustomInfo.COLUMNNAME_C_Country_ID, country.getC_Country_ID())
				.orderByDescending(I_AD_User_SaveCustomInfo.COLUMNNAME_Created)
				.create()
				.first();

		if (info != null)
		{
			return CountryCustomInfo.builder()
					.countryId(CountryId.ofRepoId(info.getC_Country_ID()))
					.captureSequence(info.getCaptureSequence())
					.build();
		}

		return null;
	}

	@Override
	public I_C_Country getDefault(final Properties ctx)
	{
		final IndexedCountries countries = getIndexedCountries();

		final String countryCode = getCountryCodeByADClientId(ctx);
		final I_C_Country country = countries.getByCountryCodeOrNull(countryCode);
		if (country != null)
		{
			return country;
		}

		// default
		return countries.getById(DEFAULT_C_Country_ID);
	}

	@Override
	public I_C_Country getById(@NonNull final CountryId id)
	{
		return getIndexedCountries().getByIdOrNull(id);
	}

	@Override
	public I_C_Country get(final Properties ctx_NOTNUSED, final int C_Country_ID)
	{
		return getById(CountryId.ofRepoId(C_Country_ID));
	}

	@Override
	public List<I_C_Country> getCountries(final Properties ctx)
	{
		final List<I_C_Country> countries = new ArrayList<>(getIndexedCountries().getAll());

		final Comparator<Object> cmp = new MCountry(ctx, 0, null);
		countries.sort(cmp);

		return countries;
	} // getCountries

	@NonNull
	private IndexedCountries getIndexedCountries()
	{
		return Check.assumeNotNull(countriesCache.getOrLoad(0, CountryDAO::retrieveIndexedCountries), "retrieveIndexedCountries doesn't return null");
	}

	private String getCountryCodeByADClientId(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return countryCodeByADClientId.getOrLoad(adClientId, () -> retrieveCountryCodeIdByADClientId(adClientId));
	}

	@Override
	@Cached(cacheName = I_C_Region.Table_Name + "#by#C_Country_ID")
	public List<I_C_Region> retrieveRegions(@CacheCtx final Properties ctx, final int countryId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Region.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Region.COLUMNNAME_C_Country_ID, countryId)
				//
				.orderBy()
				.addColumn(I_C_Region.COLUMNNAME_Name)
				.addColumn(I_C_Region.COLUMNNAME_C_Region_ID)
				.endOrderBy()
				//
				.create()
				.listImmutable(I_C_Region.class);

	}

	@Override
	public Optional<CountrySequences> getCountrySequences(
			@NonNull final CountryId countryId,
			@NonNull final OrgId orgId,
			final String adLanguage)
	{
		return retrieveCountrySequences(countryId)
				.stream()
				.filter(countrySequence -> countrySequenceMatches(countrySequence, orgId, adLanguage))
				.findFirst();
	}

	@Cached(cacheName = I_C_Country_Sequence.Table_Name + "#by#C_Country_ID")
	public ImmutableList<CountrySequences> retrieveCountrySequences(@NonNull final CountryId countryId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Country_Sequence.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Country_Sequence.COLUMN_C_Country_ID, countryId)
				//
				.orderBy()
				.addColumn(I_C_Country_Sequence.COLUMNNAME_AD_Language, Direction.Ascending, Nulls.Last)
				.endOrderBy()
				//
				.create()
				.stream()
				.map(CountryDAO::toCountrySequences)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public I_C_Country retrieveCountryByCountryCode(final String countryCode)
	{
		return getIndexedCountries().getByCountryCode(countryCode);
	}

	@Override
	public CountryId getCountryIdByCountryCode(final String countryCode)
	{
		return getIndexedCountries().getIdByCountryCode(countryCode);
	}

	@Nullable
	@Override
	public CountryId getCountryIdByCountryCodeOrNull(@Nullable final String countryCode)
	{
		return getIndexedCountries().getIdByCountryCodeOrNull(countryCode);
	}

	@Override
	public String retrieveCountryCode2ByCountryId(@NonNull final CountryId countryId)
	{
		return getIndexedCountries().getById(countryId).getCountryCode();
	}

	@Override
	public ITranslatableString getCountryNameById(final CountryId countryId)
	{
		final I_C_Country country = getIndexedCountries().getByIdOrNull(countryId);
		if (country == null)
		{
			return TranslatableStrings.constant("<" + countryId + ">");
		}

		return InterfaceWrapperHelper.getModelTranslationMap(country)
				.getColumnTrl(I_C_Country.COLUMNNAME_Name, country.getName());
	}

	@Override
	public Optional<CurrencyId> getCountryCurrencyId(@NonNull final CountryId countryId)
	{
		final I_C_Country country = getById(countryId);
		return CurrencyId.optionalOfRepoId(country.getC_Currency_ID());
	}

	@NonNull
	@Override
	public CountryCode getCountryCode(@NonNull final CountryId countryId)
	{
		final I_C_Country country = getById(countryId);
		return CountryCode.ofAlpha2(country.getCountryCode());
	}

	private static final class IndexedCountries
	{
		/**
		 * contains also inactive countries
		 */
		private final ImmutableList<I_C_Country> countries;

		/**
		 * contains also inactive countries
		 */
		private final ImmutableMap<CountryId, I_C_Country> countriesById;

		/**
		 * contains only active countries
		 */
		private final ImmutableMap<String, I_C_Country> countriesByCountryCode;

		private IndexedCountries(final List<I_C_Country> countries)
		{
			this.countries = ImmutableList.copyOf(countries);
			this.countriesById = Maps.uniqueIndex(countries, c -> CountryId.ofRepoId(c.getC_Country_ID()));
			this.countriesByCountryCode = countries.stream()
					.filter(I_C_Country::isActive)
					.filter(country -> Check.isNotBlank(country.getCountryCode())) // NOTE: in DB the CountryCode is mandatory but not in some unit tests
					.collect(GuavaCollectors.toImmutableMapByKey(I_C_Country::getCountryCode));
		}

		public List<I_C_Country> getAll()
		{
			return countries;
		}

		/**
		 * @return the country with the given ID, even if the `C_Country` record is not active.
		 */
		public I_C_Country getByIdOrNull(final CountryId countryId)
		{
			return countriesById.get(countryId);
		}

		/**
		 * @return the country with the given ID, even if the `C_Country` record is not active.
		 */
		public I_C_Country getById(final CountryId countryId)
		{
			final I_C_Country country = getByIdOrNull(countryId);
			if (country == null)
			{
				throw new AdempiereException("No country found for countryId=" + countryId);
			}
			return country;
		}

		/**
		 * @return the country with the given code, unless the `C_Country` record is inactive.
		 */
		public I_C_Country getByCountryCodeOrNull(@Nullable final String countryCode)
		{
			return countriesByCountryCode.get(countryCode);
		}

		/**
		 * @return the country with the given code, unless the `C_Country` record is inactive.
		 */
		public I_C_Country getByCountryCode(final String countryCode)
		{
			final I_C_Country country = getByCountryCodeOrNull(countryCode);
			if (country == null)
			{
				throw new AdempiereException("No active country found for countryCode=" + countryCode);
			}
			return country;
		}

		/**
		 * @return the country with the given code, unless the `C_Country` record is inactive.
		 */
		@NonNull
		public CountryId getIdByCountryCode(@NonNull final String countryCode)
		{
			final I_C_Country country = getByCountryCode(countryCode);
			return CountryId.ofRepoId(country.getC_Country_ID());
		}

		@Nullable
		public CountryId getIdByCountryCodeOrNull(@Nullable final String countryCode)
		{
			final I_C_Country country = getByCountryCodeOrNull(countryCode);

			return country != null ? CountryId.ofRepoId(country.getC_Country_ID()) : null;
		}

	}

}
