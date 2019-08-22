package de.metas.bpartner.composite;

import static de.metas.util.Check.assumeGreaterThanZero;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Recent_V;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerLocation.BPartnerLocationBuilder;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CachingKeysMapper;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.impl.PostalQueryFilter;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class BPartnerCompositeRepository
{
	private final CachingKeysMapper<BPartnerId> invalidationKeysMapper = new CachingKeysMapper<BPartnerId>()
	{
		@Override
		public Collection<BPartnerId> computeCachingKeys(@NonNull final TableRecordReference recordRef)
		{
			return extractBPartnerId(recordRef);
		}

		private Collection<BPartnerId> extractBPartnerId(@NonNull final TableRecordReference recordRef)
		{
			if (I_C_BPartner.Table_Name.equals(recordRef.getTableName()))
			{
				return ImmutableList.of(BPartnerId.ofRepoId(recordRef.getRecord_ID()));
			}

			if (I_C_BPartner_Location.Table_Name.equals(recordRef.getTableName()))
			{
				final I_C_BPartner_Location bpartnerLocationRecord = recordRef.getModel(I_C_BPartner_Location.class);
				return ImmutableList.of(BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID()));
			}

			if (I_AD_User.Table_Name.equals(recordRef.getTableName()))
			{
				final I_AD_User userRecord = recordRef.getModel(I_AD_User.class);
				if (userRecord.getC_BPartner_ID() <= 0)
				{
					return ImmutableList.of();
				}
				return ImmutableList.of(BPartnerId.ofRepoId(userRecord.getC_BPartner_ID()));
			}
			throw new AdempiereException("Given recordRef has unexpected tableName=" + recordRef.getTableName() + "; recordRef=" + recordRef);
		}
	};

	private final CCache<BPartnerId, BPartnerComposite> bpartnerCompositeCache = CCache
			.<BPartnerId, BPartnerComposite> builder()
			.cacheName("BPartnerCompositeCache")
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
			.additionalTableNameToResetFor(I_AD_User.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(500)
			.invalidationKeysMapper(invalidationKeysMapper)
			.build();

	private final LogEntriesRepository recordChangeLogRepository;

	public BPartnerCompositeRepository(@NonNull final LogEntriesRepository recordChangeLogRepository)
	{
		this.recordChangeLogRepository = recordChangeLogRepository;
	}

	public BPartnerComposite getById(@NonNull final BPartnerId bPartnerId)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableList.of(bPartnerId)));
	}

	public Optional<ContactIdAndBPartner> getByContact(@NonNull final BPartnerContactQuery contactQuery)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInArrayFilter(I_AD_User.COLUMN_AD_Org_ID, Env.getOrgId(), OrgId.ANY);

		if (contactQuery.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, contactQuery.getBPartnerId());
		}
		else
		{ // we don't know which one, but is has to be some C_BPartner_ID
			queryBuilder.addNotEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, null);
		}

		if (contactQuery.getUserId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_AD_User_ID, contactQuery.getUserId());
		}
		else if (contactQuery.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_ExternalId, contactQuery.getExternalId().getValue());
		}
		else if (!Check.isEmpty(contactQuery.getValue(), true))
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_Value, contactQuery.getValue().trim());
		}

		final I_AD_User userRecord = queryBuilder
				.create()
				.firstOnlyOrNull(I_AD_User.class);
		if (userRecord == null)
		{
			return Optional.empty();
		}
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(userRecord.getC_BPartner_ID());
		final BPartnerComposite bpartnerComposite = getById(bpartnerId);

		final ContactIdAndBPartner result = ContactIdAndBPartner.of(
				BPartnerContactId.ofRepoId(bpartnerId, userRecord.getAD_User_ID()),
				bpartnerComposite);

		return Optional.of(result);
	}

	@Value(staticConstructor = "of")
	public static class ContactIdAndBPartner
	{
		BPartnerContactId bpartnerContactId;
		BPartnerComposite bpartnerComposite;
	}

	public enum SinceEntity
	{
		CONTACT_ONLY, ALL;
	}

	@Value
	public static class SinceQuery
	{
		public static SinceQuery anyEntity(@Nullable final Instant sinceInstant, final int pageSize)
		{
			return new SinceQuery(SinceEntity.ALL, sinceInstant, pageSize);
		}

		public static SinceQuery onlyContacts(@Nullable final Instant sinceInstant, final int pageSize)
		{
			return new SinceQuery(SinceEntity.CONTACT_ONLY, sinceInstant, pageSize);
		}

		SinceEntity sinceEntity;

		Instant sinceInstant;

		int pageSize;

		private SinceQuery(
				@NonNull final SinceEntity sinceEntity,
				@NonNull final Instant sinceInstant,
				final int pageSize)
		{
			this.sinceEntity = sinceEntity;
			this.sinceInstant = sinceInstant;
			this.pageSize = assumeGreaterThanZero(pageSize, "pageSize");
		}
	}

	@Value
	public static class NextPageQuery
	{
		public static NextPageQuery anyEntityOrNull(@Nullable final String nextPageId)
		{
			if (isEmpty(nextPageId, true))
			{
				return null;
			}
			return new NextPageQuery(SinceEntity.ALL, nextPageId);
		}

		public static NextPageQuery onlyContactsOrNull(@Nullable final String nextPageId)
		{
			if (isEmpty(nextPageId, true))
			{
				return null;
			}
			return new NextPageQuery(SinceEntity.CONTACT_ONLY, nextPageId);
		}

		SinceEntity sinceEntity;

		String nextPageId;

		private NextPageQuery(
				@NonNull final SinceEntity sinceEntity,
				@NonNull final String nextPageId)
		{
			this.sinceEntity = sinceEntity;
			this.nextPageId = assumeNotEmpty(nextPageId, "nextPageId");
		}
	}

	public QueryResultPage<BPartnerComposite> getSince(@NonNull final SinceQuery sinceQuery)
	{
		switch (sinceQuery.getSinceEntity())
		{
			case ALL:
				return getCompleteBPartnerCompositsSince(sinceQuery);
			case CONTACT_ONLY:
				return getContactFilteredBPartnerComposites(sinceQuery);
			default:
				throw new AdempiereException("Unexpected sinceEntity=" + sinceQuery.getSinceEntity());
		}
	}

	public QueryResultPage<BPartnerComposite> getNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		switch (nextPageQuery.getSinceEntity())
		{
			case ALL:
				return getCompleteBPartnerCompositesNextPage(nextPageQuery);
			case CONTACT_ONLY:
				return getContactFilteredBPartnerCompositesNextPage(nextPageQuery);
			default:
				throw new AdempiereException("Unexpected sinceEntity=" + nextPageQuery.getSinceEntity());
		}

	}

	private QueryResultPage<BPartnerComposite> getCompleteBPartnerCompositsSince(@NonNull final SinceQuery sinceQuery)
	{
		final QueryResultPage<BPartnerId> page = retrievePageAllEntities(sinceQuery, null);
		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(page.getItems());
		return page.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerComposite> getContactFilteredBPartnerComposites(@NonNull final SinceQuery sinceQuery)
	{
		final QueryResultPage<BPartnerContactId> page = retrievePageOnlyContactEntities(sinceQuery, null);

		return extractContactFilteredBPartnerComposites(page);
	}

	private QueryResultPage<BPartnerComposite> extractContactFilteredBPartnerComposites(@NonNull final QueryResultPage<BPartnerContactId> page)
	{
		final ImmutableList<BPartnerContactId> contactIdsToReturn = page.getItems();

		final Set<BPartnerId> distinctBPartnerIds = contactIdsToReturn.stream()
				.map(BPartnerContactId::getBpartnerId)
				.collect(Collectors.toSet());
		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(distinctBPartnerIds);

		for (final BPartnerComposite bpartnerComposite : bpartnerComposites)
		{
			bpartnerComposite.retainContacts(new HashSet<>(contactIdsToReturn));
		}

		return page.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerComposite> getCompleteBPartnerCompositesNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		final QueryResultPage<BPartnerId> page = retrievePageAllEntities(null, nextPageQuery.getNextPageId());
		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(page.getItems());
		return page.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerComposite> getContactFilteredBPartnerCompositesNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		final QueryResultPage<BPartnerContactId> page = retrievePageOnlyContactEntities(null, nextPageQuery.getNextPageId());

		return extractContactFilteredBPartnerComposites(page);
	}

	private QueryResultPage<BPartnerId> retrievePageAllEntities(
			@Nullable final SinceQuery sinceQuery,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_C_BPartner_Recent_V> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(sinceQuery.getSinceInstant());
			page = queryBL.createQueryBuilder(I_C_BPartner_Recent_V.class)
					.addCompareFilter(I_C_BPartner_Recent_V.COLUMNNAME_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.create()
					.paginate(I_C_BPartner_Recent_V.class, sinceQuery.getPageSize());
		}
		else
		{
			page = queryBL.retrieveNextPage(I_C_BPartner_Recent_V.class, nextPageId);
		}

		return page
				.mapTo(record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()));
	}

	private QueryResultPage<BPartnerContactId> retrievePageOnlyContactEntities(
			@Nullable final SinceQuery sinceQuery,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_AD_User> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(sinceQuery.getSinceInstant());
			page = queryBL
					.createQueryBuilder(I_AD_User.class)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.addCompareFilter(I_AD_User.COLUMN_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.addNotEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, null)
					.create()
					.paginate(I_AD_User.class, sinceQuery.getPageSize());
		}
		else
		{
			page = queryBL.retrieveNextPage(I_AD_User.class, nextPageId);
		}

		return page.mapTo(record -> BPartnerContactId.ofRepoId(record.getC_BPartner_ID(), record.getAD_User_ID()));
	}

	public ImmutableList<BPartnerComposite> getByQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final List<BPartnerId> pageWithIds = getIdsByQuery(query);

		return getByIds(pageWithIds);
	}

	private ImmutableList<BPartnerId> getIdsByQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final IQuery<I_C_BPartner> bpartnerRecordQuery = createBPartnerRecordQuery(query);

		final ImmutableList<BPartnerId> bPartnerIds = bpartnerRecordQuery.listIds().stream()
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		return bPartnerIds;
	}

	private IQuery<I_C_BPartner> createBPartnerRecordQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyContextClient()
				//.addOnlyActiveRecordsFilter() also load inactive records!
				;

		if (!query.getOnlyOrgIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
		}

		if (query.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, query.getBPartnerId());
		}

		// ..BPartner external-id
		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_ExternalId, query.getExternalId().getValue());
		}

		// ..BPartner code (aka value)
		if (!isEmpty(query.getBpartnerValue(), true))
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, query.getBpartnerValue());
		}

		// ..BPartner Name
		if (!isEmpty(query.getBpartnerName(), true))
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_Name, query.getBpartnerName());
		}

		// BPLocation's GLN
		if (!query.getLocationGlns().isEmpty())
		{
			final IQueryBuilder<I_C_BPartner_Location> glnQueryBuilder = queryBL
					.createQueryBuilder(I_C_BPartner_Location.class)
					.addOnlyActiveRecordsFilter();
			if (!query.getOnlyOrgIds().isEmpty())
			{
				glnQueryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
			}

			for (final String locationGln : query.getLocationGlns())
			{
				glnQueryBuilder.addEqualsFilter(I_C_BPartner_Location.COLUMN_GLN, locationGln);
			}
			queryBuilder.addInSubQueryFilter(
					I_C_BPartner.COLUMNNAME_C_BPartner_ID,
					I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID,
					glnQueryBuilder.create());
		}

		return queryBuilder.create();
	}

	public ImmutableList<BPartnerComposite> getByIds(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final Collection<BPartnerComposite> result = bpartnerCompositeCache.getAllOrLoad(bPartnerIds, this::getByIds0);

		return result
				.stream()
				.map(BPartnerComposite::deepCopy) // important because the instance is mutable!
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableMap<BPartnerId, BPartnerComposite> getByIds0(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final IQuery<I_C_BPartner> bpartnerRecordQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyContextClient()
				.addInArrayFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create();
		final List<I_C_BPartner> bPartnerRecords = bpartnerRecordQuery.list();

		return createBPartnerComposites(bPartnerRecords);
	}

	private ImmutableMap<BPartnerId, BPartnerComposite> createBPartnerComposites(@NonNull final List<I_C_BPartner> bPartnerRecords)
	{
		final ImmutableListMultimap<Integer, I_C_BPartner> id2bpartners = Multimaps.index(bPartnerRecords, I_C_BPartner::getC_BPartner_ID);

		final ImmutableList<BPartnerId> bPartnerIds = id2bpartners
				.keySet()
				.stream()
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final CompositeRelatedRecords relatedRecords = retrieveRelatedRecords(bPartnerIds);

		final Builder<BPartnerId, BPartnerComposite> result = ImmutableMap.<BPartnerId, BPartnerComposite> builder();

		for (final I_C_BPartner bPartnerRecord : bPartnerRecords)
		{
			final BPartnerId id = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

			final BPartner bpartner = ofBPartnerRecord(bPartnerRecord, relatedRecords.getRecordRef2LogEntries());

			final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
					.orgId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID()))
					.bpartner(bpartner)
					.contacts(ofContactRecords(id, relatedRecords))
					.locations(ofLocationRecords(id, relatedRecords))
					.build();

			result.put(id, bpartnerComposite);
		}
		return result.build();
	}

	private CompositeRelatedRecords retrieveRelatedRecords(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final List<TableRecordReference> allTableRecordRefs = new ArrayList<>();
		bPartnerIds.forEach(bPartnerId -> allTableRecordRefs.add(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId.getRepoId())));

		final List<I_AD_User> contactRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				// .addOnlyActiveRecordsFilter()  also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_AD_User> bpartnerId2Users = Multimaps.index(contactRecords, I_AD_User::getC_BPartner_ID);
		contactRecords.forEach(contactRecord -> allTableRecordRefs.add(TableRecordReference.of(contactRecord)));

		final List<I_C_BPartner_Location> bPartnerLocationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				// .addOnlyActiveRecordsFilter()  also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_C_BPartner_Location> bpartnerId2BPartnerLocations = Multimaps.index(bPartnerLocationRecords, I_C_BPartner_Location::getC_BPartner_ID);
		bPartnerLocationRecords.forEach(bPartnerLocationRecord -> allTableRecordRefs.add(TableRecordReference.of(bPartnerLocationRecord)));

		final ImmutableList<Integer> locationIds = CollectionUtils.extractDistinctElements(bPartnerLocationRecords, I_C_BPartner_Location::getC_Location_ID);
		final List<I_C_Location> locationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Location.class)
				// .addOnlyActiveRecordsFilter()  also load inactive records!
				.addOnlyContextClient()
				.addInArrayFilter(I_C_Location.COLUMNNAME_C_Location_ID, locationIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Location> locationId2Location = Maps.uniqueIndex(locationRecords, I_C_Location::getC_Location_ID);
		locationRecords.forEach(locationRecord -> allTableRecordRefs.add(TableRecordReference.of(locationRecord)));

		final ImmutableList<Integer> postalIds = CollectionUtils.extractDistinctElements(locationRecords, I_C_Location::getC_Postal_ID);
		final List<I_C_Postal> postalRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Postal.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Postal.COLUMNNAME_C_Postal_ID, postalIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Postal> postalId2Postal = Maps.uniqueIndex(postalRecords, I_C_Postal::getC_Postal_ID);
		postalRecords.forEach(postalRecord -> allTableRecordRefs.add(TableRecordReference.of(postalRecord)));

		final ImmutableList<Integer> countryIds = CollectionUtils.extractDistinctElements(locationRecords, I_C_Location::getC_Country_ID);
		final List<I_C_Country> countryRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Country.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Country.COLUMNNAME_C_Country_ID, countryIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Country> countryId2Country = Maps.uniqueIndex(countryRecords, I_C_Country::getC_Country_ID);
		countryRecords.forEach(countryRecord -> allTableRecordRefs.add(TableRecordReference.of(countryRecord)));

		final LogEntriesQuery logEntriesQuery = LogEntriesQuery.builder()
				.tableRecordReferences(allTableRecordRefs)
				.followLocationIdChanges(true)
				.build();
		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> //
		recordRef2LogEntries = recordChangeLogRepository.getLogEntriesForRecordReferences(logEntriesQuery);

		return new CompositeRelatedRecords(
				bpartnerId2Users,
				bpartnerId2BPartnerLocations,
				locationId2Location,
				postalId2Postal,
				countryId2Country,
				recordRef2LogEntries);
	}

	@Value
	public static final class CompositeRelatedRecords
	{
		@NonNull
		ImmutableListMultimap<Integer, I_AD_User> bpartnerId2Users;

		@NonNull
		ImmutableListMultimap<Integer, I_C_BPartner_Location> bpartnerId2BPartnerLocations;

		@NonNull
		ImmutableMap<Integer, I_C_Location> locationId2Location;

		@NonNull
		ImmutableMap<Integer, I_C_Postal> postalId2Postal;

		@NonNull
		ImmutableMap<Integer, I_C_Country> countryId2Country;

		@NonNull
		ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries;
	}

	private BPartner ofBPartnerRecord(
			@NonNull final I_C_BPartner bpartnerRecord,
			@NonNull ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> immutableListMultimap)
	{
		final RecordChangeLog recordChangeLog = ChangeLogUtil.createBPartnerChangeLog(bpartnerRecord, immutableListMultimap);

		return BPartner.builder()
				.active(bpartnerRecord.isActive())
				.value(bpartnerRecord.getValue())
				.companyName(bpartnerRecord.getCompanyName())
				.externalId(ExternalId.ofOrNull(bpartnerRecord.getExternalId()))
				.groupId(BPGroupId.ofRepoId(bpartnerRecord.getC_BP_Group_ID()))
				.language(Language.asLanguage(bpartnerRecord.getAD_Language()))
				.id(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.name(bpartnerRecord.getName())
				.name2(bpartnerRecord.getName2())
				.name3(bpartnerRecord.getName3())
				.parentId(BPartnerId.ofRepoIdOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				.phone(bpartnerRecord.getPhone2())
				.url(bpartnerRecord.getURL())
				.url2(bpartnerRecord.getURL2())
				.url3(bpartnerRecord.getURL3())
				.changeLog(recordChangeLog)
				.build();
	}

	private ImmutableList<BPartnerLocation> ofLocationRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_C_BPartner_Location> bpartnerLocationRecords = relatedRecords
				.getBpartnerId2BPartnerLocations()
				.get(bpartnerId.getRepoId());

		final ImmutableList.Builder<BPartnerLocation> result = ImmutableList.builder();
		for (final I_C_BPartner_Location bPartnerLocationRecord : bpartnerLocationRecords)
		{
			final BPartnerLocation location = ofLocationRecord(bPartnerLocationRecord, relatedRecords);
			result.add(location);
		}

		return result.build();
	}

	private BPartnerLocation ofLocationRecord(
			@NonNull final I_C_BPartner_Location bPartnerLocationRecord,
			@NonNull final CompositeRelatedRecords locationRelatedRecords)
	{
		final I_C_Location locationRecord = locationRelatedRecords.getLocationId2Location().get(bPartnerLocationRecord.getC_Location_ID());
		final I_C_Country countryRecord = locationRelatedRecords.getCountryId2Country().get(locationRecord.getC_Country_ID());
		final I_C_Postal postalRecord = locationRelatedRecords.getPostalId2Postal().get(locationRecord.getC_Postal_ID());

		final RecordChangeLog changeLog = ChangeLogUtil.createBPartnerLocationChangeLog(bPartnerLocationRecord, locationRelatedRecords);

		final BPartnerLocationBuilder location = BPartnerLocation.builder()
				.active(bPartnerLocationRecord.isActive())
				.name(bPartnerLocationRecord.getName())
				.locationType(BPartnerLocationType.builder()
						.billTo(bPartnerLocationRecord.isBillTo())
						.billToDefault(bPartnerLocationRecord.isBillToDefault())
						.shipTo(bPartnerLocationRecord.isShipTo())
						.shipToDefault(bPartnerLocationRecord.isShipToDefault())
						.build())
				.address1(locationRecord.getAddress1())
				.address2(locationRecord.getAddress2())
				.address3(locationRecord.getAddress3())
				.address4(locationRecord.getAddress4())
				.city(locationRecord.getCity())
				.countryCode(countryRecord.getCountryCode())
				.externalId(ExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(bPartnerLocationRecord.getGLN())
				.id(BPartnerLocationId.ofRepoId(bPartnerLocationRecord.getC_BPartner_ID(), bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.poBox(locationRecord.getPOBox())
				.postal(locationRecord.getPostal())
				.region(locationRecord.getRegionName())
				.changeLog(changeLog);

		if (locationRecord.getC_Postal_ID() > 0)
		{
			location.district(postalRecord.getDistrict());
		}

		return location.build();
	}

	private ImmutableList<BPartnerContact> ofContactRecords(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final ImmutableList<I_AD_User> userRecords = relatedRecords
				.getBpartnerId2Users()
				.get(bpartnerId.getRepoId());

		final ImmutableList.Builder<BPartnerContact> result = ImmutableList.builder();
		for (final I_AD_User userRecord : userRecords)
		{
			final BPartnerContact contact = ofContactRecord(userRecord, relatedRecords);
			result.add(contact);
		}

		return result.build();
	}

	private BPartnerContact ofContactRecord(
			@NonNull final I_AD_User contactRecord,
			@NonNull final CompositeRelatedRecords relatedRecords)
	{
		final RecordChangeLog changeLog = ChangeLogUtil.createcontactChangeLog(contactRecord, relatedRecords);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contactRecord.getC_BPartner_ID());
		return BPartnerContact.builder()
				.active(contactRecord.isActive())
				.id(BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID()))
				.contactType(BPartnerContactType.builder()
						.defaultContact(contactRecord.isDefaultContact())
						.billToDefault(contactRecord.isBillToContact_Default())
						.shipToDefault(contactRecord.isShipToContact_Default())
						.sales(contactRecord.isSalesContact())
						.salesDefault(contactRecord.isSalesContact_Default())
						.purchase(contactRecord.isPurchaseContact())
						.purchaseDefault(contactRecord.isPurchaseContact_Default())
						.subjectMatter(contactRecord.isSubjectMatterContact())
						.build())
				.email(contactRecord.getEMail())
				.externalId(ExternalId.ofOrNull(contactRecord.getExternalId()))
				.firstName(contactRecord.getFirstname())
				.lastName(contactRecord.getLastname())
				.name(contactRecord.getName())
				.newsletter(contactRecord.isNewsletter())
				.phone(contactRecord.getPhone())
				.mobilePhone(contactRecord.getMobilePhone())
				.description(contactRecord.getDescription())
				.fax(contactRecord.getFax())
				.greetingId(GreetingId.ofRepoIdOrNull(contactRecord.getC_Greeting_ID()))
				.changeLog(changeLog)
				.build();
	}

	public void save(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final ImmutableList<ITranslatableString> validateResult = bpartnerComposite.validate();

		if (!validateResult.isEmpty())
		{
			final String errors = validateResult
					.stream()
					.map(trl -> trl.translate(Env.getADLanguageOrBaseLanguage()))
					.collect(Collectors.joining("\n"));

			throw new AdempiereException("Can't save an invalid bpartnerComposite")
					.appendParametersToMessage()
					.setParameter("bpartnerComposite", bpartnerComposite)
					.setParameter("errors", errors);
		}

		final BPartner bpartner = bpartnerComposite.getBpartner();
		saveBPartner(bpartner);

		saveBPartnerLocations(bpartner.getId(), bpartnerComposite.getLocations());

		saveBPartnerContacts(bpartner.getId(), bpartnerComposite.getContacts());
	}

	private void saveBPartner(@NonNull final BPartner bpartner)
	{
		final I_C_BPartner bpartnerRecord = loadOrNew(bpartner.getId(), I_C_BPartner.class);
		bpartnerRecord.setIsActive(bpartner.isActive());

		// companyName
		if (isEmpty(bpartner.getCompanyName(), true))
		{
			bpartnerRecord.setIsCompany(false);
			bpartnerRecord.setCompanyName(null);
		}
		else
		{
			bpartnerRecord.setIsCompany(true);
			bpartnerRecord.setCompanyName(bpartner.getCompanyName().trim());
		}

		bpartnerRecord.setExternalId(ExternalId.toValue(bpartner.getExternalId()));
		bpartnerRecord.setC_BP_Group_ID(bpartner.getGroupId().getRepoId()); // since we validated, we know it's set
		// bpartner.getId() used only for lookup

		bpartnerRecord.setAD_Language(Language.asLanguageString(bpartner.getLanguage()));
		bpartnerRecord.setName(bpartner.getName());
		bpartnerRecord.setName2(bpartner.getName2());
		bpartnerRecord.setName3(bpartner.getName3());

		bpartnerRecord.setBPartner_Parent_ID(BPartnerId.toRepoId(bpartner.getParentId()));
		bpartnerRecord.setPhone2(bpartner.getPhone());
		bpartnerRecord.setURL(bpartner.getUrl());
		bpartnerRecord.setURL2(bpartner.getUrl2());
		bpartnerRecord.setURL3(bpartner.getUrl3());

		bpartnerRecord.setValue(bpartner.getValue());

		saveRecord(bpartnerRecord);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
		bpartner.setId(bpartnerId);
	}

	private void saveBPartnerLocations(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerLocation> bpartnerLocations)
	{
		final ArrayList<BPartnerLocationId> savedBPartnerLocationIds = new ArrayList<>();
		for (final BPartnerLocation bPartnerLocation : bpartnerLocations)
		{
			saveBPartnerLocation(bpartnerId, bPartnerLocation);
			savedBPartnerLocationIds.add(bPartnerLocation.getId());
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryUpdater<I_C_BPartner_Location> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_C_BPartner_Location.class)
				.addSetColumnValue(I_C_BPartner_Location.COLUMNNAME_IsActive, false);
		queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_BPartner_Location.COLUMN_C_BPartner_Location_ID, savedBPartnerLocationIds)
				.create()
				.update(columnUpdater);
	}

	private void saveBPartnerLocation(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerLocation bpartnerLocation)
	{
		final I_C_BPartner_Location bpartnerLocationRecord = loadOrNew(bpartnerLocation.getId(), I_C_BPartner_Location.class);
		bpartnerLocationRecord.setIsActive(bpartnerLocation.isActive());
		bpartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpartnerLocationRecord.setName(bpartnerLocation.getName());

		final BPartnerLocationType locationType = bpartnerLocation.getLocationType();
		if (locationType != null)
		{
			locationType.getBillTo().ifPresent(b -> bpartnerLocationRecord.setIsBillTo(b));
			locationType.getBillToDefault().ifPresent(b -> bpartnerLocationRecord.setIsBillToDefault(b));
			locationType.getShipTo().ifPresent(b -> bpartnerLocationRecord.setIsShipTo(b));
			locationType.getShipToDefault().ifPresent(b -> bpartnerLocationRecord.setIsShipToDefault(b));
		}


		// C_Location is immutable; never update an existing record, but create a new one
		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setIsActive(bpartnerLocation.isActive());

		locationRecord.setAddress1(bpartnerLocation.getAddress1());
		locationRecord.setAddress2(bpartnerLocation.getAddress2());
		locationRecord.setAddress3(bpartnerLocation.getAddress3());
		locationRecord.setAddress4(bpartnerLocation.getAddress4());

		if (isEmpty(bpartnerLocation.getCountryCode(), true))
		{
			locationRecord.setC_Country(null);
		}
		else
		{
			final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
			final CountryId countryId = countryDAO.getCountryIdByCountryCode(bpartnerLocation.getCountryCode());
			locationRecord.setC_Country_ID(CountryId.toRepoId(countryId));
		}

		boolean postalDataSetFromPostalRecord = false;
		if (!isEmpty(bpartnerLocation.getPostal(), true))
		{
			final IQueryBuilder<I_C_Postal> postalQueryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Postal.class)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.filter(PostalQueryFilter.of(bpartnerLocation.getPostal().trim()));
			if (!isEmpty(bpartnerLocation.getDistrict(), true))
			{
				postalQueryBuilder.addEqualsFilter(I_C_Postal.COLUMN_District, bpartnerLocation.getDistrict());
			}
			else
			{
				// prefer C_Postal records that have no district set
			}

			postalQueryBuilder.orderBy().addColumn(I_C_Postal.COLUMNNAME_District, Direction.Ascending, Nulls.First);

			final List<I_C_Postal> postalRecords = postalQueryBuilder
					.create()
					.list();

			final I_C_Postal postalRecord;
			if (postalRecords.isEmpty())
			{
				postalRecord = null;
			}
			else if (postalRecords.size() == 1)
			{
				postalRecord = postalRecords.get(0);
			}
			else if (locationRecord.getC_Country_ID() > 0)
			{
				postalRecord = postalRecords
						.stream()
						.filter(r -> (r.getC_Country_ID() == locationRecord.getC_Country_ID()))
						.findFirst()
						.orElse(null);
			}
			else
			{
				postalRecord = null;
			}

			if (postalRecord != null)
			{
				locationRecord.setC_Country_ID(postalRecord.getC_Country_ID());
				locationRecord.setC_Postal_ID(postalRecord.getC_Postal_ID());
				locationRecord.setPostal(postalRecord.getPostal());
				locationRecord.setCity(postalRecord.getCity());
				locationRecord.setRegionName(postalRecord.getRegionName());

				postalDataSetFromPostalRecord = true;
			}
		}

		bpartnerLocationRecord.setExternalId(ExternalId.toValue(bpartnerLocation.getExternalId()));
		bpartnerLocationRecord.setGLN(bpartnerLocation.getGln());
		// bpartnerLocation.getId() // id is only for lookup and won't be updated later

		locationRecord.setPOBox(bpartnerLocation.getPoBox());
		if (!postalDataSetFromPostalRecord)
		{
			locationRecord.setPostal(bpartnerLocation.getPostal());
			locationRecord.setCity(bpartnerLocation.getCity());
			locationRecord.setRegionName(bpartnerLocation.getRegion());
		}

		saveRecord(locationRecord);

		bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());

		Services.get(IBPartnerBL.class).setAddress(bpartnerLocationRecord);

		saveRecord(bpartnerLocationRecord);

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID(), bpartnerLocationRecord.getC_BPartner_Location_ID());
		bpartnerLocation.setId(bpartnerLocationId);
	}

	private void saveBPartnerContacts(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final List<BPartnerContact> contacts)
	{
		final ArrayList<BPartnerContactId> savedBPartnerContactIds = new ArrayList<>();
		for (final BPartnerContact bpartnerContact : contacts)
		{
			saveBPartnerContact(bpartnerId, bpartnerContact);
			savedBPartnerContactIds.add(bpartnerContact.getId());
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryUpdater<I_AD_User> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_AD_User.class)
				.addSetColumnValue(I_AD_User.COLUMNNAME_IsActive, false);
		queryBL
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_AD_User.COLUMN_AD_User_ID, savedBPartnerContactIds)
				.create()
				.update(columnUpdater);
	}

	private void saveBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerContact bpartnerContact)
	{
		final I_AD_User bpartnerContactRecord = loadOrNew(bpartnerContact.getId(), I_AD_User.class);
		bpartnerContactRecord.setExternalId(ExternalId.toValue(bpartnerContact.getExternalId()));
		bpartnerContactRecord.setIsActive(bpartnerContact.isActive());
		bpartnerContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpartnerContactRecord.setName(bpartnerContact.getName());
		bpartnerContactRecord.setEMail(bpartnerContact.getEmail());

		bpartnerContactRecord.setFirstname(bpartnerContact.getFirstName());
		bpartnerContactRecord.setLastname(bpartnerContact.getLastName());

		bpartnerContactRecord.setIsNewsletter(bpartnerContact.isNewsletter());

		final BPartnerContactType contactType = bpartnerContact.getContactType();
		if (contactType != null)
		{
			contactType.getDefaultContact().ifPresent(b -> bpartnerContactRecord.setIsDefaultContact(b));
			contactType.getBillToDefault().ifPresent(b -> bpartnerContactRecord.setIsBillToContact_Default(b));
			contactType.getShipToDefault().ifPresent(b -> bpartnerContactRecord.setIsShipToContact_Default(b));
			contactType.getSales().ifPresent(b -> bpartnerContactRecord.setIsSalesContact(b));
			contactType.getSalesDefault().ifPresent(b -> bpartnerContactRecord.setIsSalesContact_Default(b));
			contactType.getPurchase().ifPresent(b -> bpartnerContactRecord.setIsPurchaseContact(b));
			contactType.getPurchaseDefault().ifPresent(b -> bpartnerContactRecord.setIsPurchaseContact_Default(b));
			contactType.getSubjectMatter().ifPresent(b -> bpartnerContactRecord.setIsSubjectMatterContact(b));
		}

		bpartnerContactRecord.setDescription(bpartnerContact.getDescription());

		bpartnerContactRecord.setPhone(bpartnerContact.getPhone());
		bpartnerContactRecord.setFax(bpartnerContact.getFax());
		bpartnerContactRecord.setMobilePhone(bpartnerContact.getMobilePhone());

		bpartnerContactRecord.setC_Greeting_ID(GreetingId.toRepoIdOr(bpartnerContact.getGreetingId(), 0));

		saveRecord(bpartnerContactRecord);
		final BPartnerContactId bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, bpartnerContactRecord.getAD_User_ID());

		bpartnerContact.setId(bpartnerContactId);
	}
}
