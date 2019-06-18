package de.metas.bpartner.composite;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Multimaps;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerLocation.BPartnerLocationBuilder;
import de.metas.bpartner.model.I_C_BPartner_Recent_ID;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CachingKeysMapper;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.LocationId;
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
	private static final int BPARTNER_PAGE_SIZE = 50;

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

	private final CCache<BPartnerId, BPartnerComposite> cache = CCache
			.<BPartnerId, BPartnerComposite> builder()
			.cacheName("BPartnerComposite")
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
			.additionalTableNameToResetFor(I_AD_User.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(500)
			.invalidationKeysMapper(invalidationKeysMapper)
			.build();

	public BPartnerComposite getById(@NonNull final BPartnerId bPartnerId)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableList.of(bPartnerId)));
	}

	public Optional<ContactIdAndBPartner> getByContact(@NonNull final BPartnerContactQuery contactQuery)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
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

	@Value
	public static class SinceQuery
	{
		public enum SinceEntity
		{
			CONTACT_ONLY, ALL;
		}

		public static SinceQuery anyEntity(@Nullable final Long epochMilli)
		{
			return new SinceQuery(SinceEntity.ALL, epochMilli);
		}

		public static SinceQuery onlyContacts(@Nullable final Long epochMilli)
		{
			return new SinceQuery(SinceEntity.CONTACT_ONLY, epochMilli);
		}

		SinceEntity sinceEntity;

		long epochMilli;

		private SinceQuery(
				@NonNull final SinceEntity sinceEntity,
				@Nullable final Long epochMilli)
		{
			this.sinceEntity = sinceEntity;
			this.epochMilli = coalesce(epochMilli, 0L);
		}
	}

	public QueryResultPage<BPartnerComposite> getSince(@NonNull final SinceQuery sinceRequest)
	{
		final QueryResultPage<BPartnerId> page;
		switch (sinceRequest.getSinceEntity())
		{
			case ALL:
				page = retrievePageAllEntities(sinceRequest.getEpochMilli(), null);
				break;
			case CONTACT_ONLY:
				page = retrievePageOnlyContactEntities(sinceRequest.getEpochMilli(), null);
				break;
			default:
				throw new AdempiereException("Unexpected sinceEntity=" + sinceRequest.getSinceEntity());
		}

		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(page.getItems());
		return page.withItems(bpartnerComposites);
	}

	public QueryResultPage<BPartnerComposite> getNextPage(@NonNull final String nextPageId)
	{
		final QueryResultPage<BPartnerId> page = retrievePageAllEntities(null, nextPageId);

		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(page.getItems());
		return page.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerId> retrievePageAllEntities(
			@Nullable final Long epochTimestampMillis,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_C_BPartner_Recent_ID> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochTimestampMillis));
			page = queryBL.createQueryBuilder(I_C_BPartner_Recent_ID.class)
					.addCompareFilter(I_C_BPartner_Recent_ID.COLUMNNAME_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.create()
					.paginate(I_C_BPartner_Recent_ID.class, BPARTNER_PAGE_SIZE);
		}
		else
		{
			page = queryBL.retrieveNextPage(I_C_BPartner_Recent_ID.class, nextPageId);
		}

		return page
				.mapTo(record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()));
	}

	private QueryResultPage<BPartnerId> retrievePageOnlyContactEntities(
			@Nullable final Long epochMilli,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_AD_User> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
			page = queryBL
					.createQueryBuilder(I_AD_User.class)
					.addOnlyActiveRecordsFilter()
					.addCompareFilter(I_AD_User.COLUMN_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.addNotEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, null)
					.create()
					.paginate(I_AD_User.class, BPARTNER_PAGE_SIZE);
		}
		else
		{
			page = queryBL.retrieveNextPage(I_AD_User.class, nextPageId);
		}

		return page.mapTo(record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()));
	}

	public QueryResultPage<BPartnerComposite> getByQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final QueryResultPage<BPartnerId> pageWithIds = getIdsByQuery(query);

		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(pageWithIds.getItems());
		return pageWithIds.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerId> getIdsByQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final IQuery<I_C_BPartner> bpartnerRecordQuery = createBPartnerRecordQuery(query);
		final QueryResultPage<I_C_BPartner> page = bpartnerRecordQuery.paginate(I_C_BPartner.class, BPARTNER_PAGE_SIZE);

		final ImmutableList<BPartnerId> bPartnerIds = bpartnerRecordQuery.listIds().stream()
				.map(BPartnerId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		return page.withItems(bPartnerIds);
	}

	private IQuery<I_C_BPartner> createBPartnerRecordQuery(@NonNull final BPartnerCompositeQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter();

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
		final Collection<BPartnerComposite> result = cache.getAllOrLoad(bPartnerIds, this::getByIds0);

		return result
				.stream()
				.map(BPartnerComposite::deepCopy) // important because the instance is mutable!
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableMap<BPartnerId, BPartnerComposite> getByIds0(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final IQuery<I_C_BPartner> bpartnerRecordQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
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

		final ImmutableListMultimap<Integer, I_C_BPartner_Location> id2Locations = retrieveLocationRecords(bPartnerIds);

		final ImmutableListMultimap<Integer, I_AD_User> id2Contacts = retrieveContactRecords(bPartnerIds);

		final Builder<BPartnerId, BPartnerComposite> result = ImmutableMap.<BPartnerId, BPartnerComposite> builder();

		for (final I_C_BPartner bPartnerRecord : bPartnerRecords)
		{
			final int id = bPartnerRecord.getC_BPartner_ID();

			final BPartnerComposite bpartnerComposite = BPartnerComposite.builder()
					.orgId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID()))
					.bpartner(ofRecord(bPartnerRecord))
					.contacts(ofContactRecords(id2Contacts.get(id)))
					.locations(ofLocationRecords(id2Locations.get(id)))
					.build();

			result.put(BPartnerId.ofRepoId(id), bpartnerComposite);
		}
		return result.build();
	}

	private ImmutableListMultimap<Integer, I_C_BPartner_Location> retrieveLocationRecords(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final List<I_C_BPartner_Location> bPartnerLocationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_C_BPartner_Location> id2Locations = Multimaps.index(bPartnerLocationRecords, I_C_BPartner_Location::getC_BPartner_ID);
		return id2Locations;
	}

	private ImmutableListMultimap<Integer, I_AD_User> retrieveContactRecords(final Collection<BPartnerId> bPartnerIds)
	{
		final List<I_AD_User> contactRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_AD_User> id2Contacts = Multimaps.index(contactRecords, I_AD_User::getC_BPartner_ID);
		return id2Contacts;
	}

	private BPartner ofRecord(@NonNull final I_C_BPartner bpartnerRecord)
	{
		return BPartner.builder()
				.value(bpartnerRecord.getValue())
				.companyName(bpartnerRecord.getCompanyName())
				.externalId(ExternalId.ofOrNull(bpartnerRecord.getExternalId()))
				.groupId(BPGroupId.ofRepoId(bpartnerRecord.getC_BP_Group_ID()))
				.language(Language.asLanguage(bpartnerRecord.getAD_Language()))
				.id(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.name(bpartnerRecord.getName())
				.parentId(BPartnerId.ofRepoIdOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				.phone(bpartnerRecord.getPhone2())
				.url(bpartnerRecord.getURL())
				.build();
	}

	private ImmutableList<BPartnerLocation> ofLocationRecords(@NonNull final ImmutableList<I_C_BPartner_Location> immutableList)
	{
		return immutableList.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private BPartnerLocation ofRecord(@NonNull final I_C_BPartner_Location bPartnerLocationRecord)
	{
		final I_C_Location locationRecord = bPartnerLocationRecord.getC_Location();

		final BPartnerLocationBuilder location = BPartnerLocation.builder()
				.address1(locationRecord.getAddress1())
				.address2(locationRecord.getAddress2())
				.city(locationRecord.getCity())
				.countryCode(locationRecord.getC_Country().getCountryCode())
				.externalId(ExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(bPartnerLocationRecord.getGLN())
				.id(BPartnerLocationId.ofRepoId(bPartnerLocationRecord.getC_BPartner_ID(), bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.poBox(locationRecord.getPOBox())
				.postal(locationRecord.getPostal())
				.region(locationRecord.getRegionName());

		if (locationRecord.getC_Postal_ID() > 0)
		{
			location.district(locationRecord.getC_Postal().getDistrict());
		}

		return location.build();
	}

	private ImmutableList<BPartnerContact> ofContactRecords(@NonNull final ImmutableList<I_AD_User> immutableList)
	{
		return immutableList.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private BPartnerContact ofRecord(@NonNull final I_AD_User contactRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contactRecord.getC_BPartner_ID());
		return BPartnerContact.builder()
				.id(BPartnerContactId.ofRepoId(bpartnerId, contactRecord.getAD_User_ID()))
				.email(contactRecord.getEMail())
				.externalId(ExternalId.ofOrNull(contactRecord.getExternalId()))
				.firstName(contactRecord.getFirstname())
				.lastName(contactRecord.getLastname())
				.name(contactRecord.getName())
				.phone(contactRecord.getPhone())
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

	final void saveBPartner(@NonNull final BPartner bpartner)
	{
		final I_C_BPartner bpartnerRecord = loadOrNew(bpartner.getId(), I_C_BPartner.class);
		bpartnerRecord.setIsActive(true);

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

		bpartnerRecord.setBPartner_Parent_ID(BPartnerId.toRepoId(bpartner.getParentId()));
		bpartnerRecord.setPhone2(bpartner.getPhone());
		bpartnerRecord.setURL(bpartner.getUrl());
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
		bpartnerLocationRecord.setIsActive(true);
		bpartnerLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());

		final I_C_Location locationRecord = loadOrNew(LocationId.ofRepoIdOrNull(bpartnerLocationRecord.getC_Location_ID()), I_C_Location.class);
		locationRecord.setIsActive(true);

		locationRecord.setAddress1(bpartnerLocation.getAddress1());
		locationRecord.setAddress2(bpartnerLocation.getAddress2());
		locationRecord.setCity(bpartnerLocation.getCity());

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

		if (!isEmpty(bpartnerLocation.getPostal(), true))
		{
			final IQueryBuilder<I_C_Postal> postalQueryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Postal.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Postal.COLUMN_C_Country_ID, locationRecord.getC_Country_ID())
					.filter(PostalQueryFilter.of(bpartnerLocation.getPostal().trim()));
			if (!isEmpty(bpartnerLocation.getDistrict(), true))
			{
				postalQueryBuilder.addEqualsFilter(I_C_Postal.COLUMN_District, bpartnerLocation.getDistrict());
			}
			else
			{
				// prefer C_Postal records that have no district set
				postalQueryBuilder.orderBy().addColumn(I_C_Postal.COLUMNNAME_District, Direction.Ascending, Nulls.First);
			}

			final I_C_Postal postalRecord = postalQueryBuilder.create().first();
			locationRecord.setC_Postal(postalRecord);
			locationRecord.setPostal(bpartnerLocation.getPostal().trim());
		}

		bpartnerLocationRecord.setExternalId(ExternalId.toValue(bpartnerLocation.getExternalId()));
		bpartnerLocationRecord.setGLN(bpartnerLocation.getGln());
		// bpartnerLocation.getId() // id is only for lookup and won't be updated later

		locationRecord.setPOBox(bpartnerLocation.getPoBox());
		locationRecord.setPostal(bpartnerLocation.getPostal());
		locationRecord.setRegionName(bpartnerLocation.getRegion());

		saveRecord(locationRecord);

		bpartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
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
		bpartnerContactRecord.setIsActive(true);
		bpartnerContactRecord.setC_BPartner_ID(bpartnerId.getRepoId());

		bpartnerContactRecord.setEMail(bpartnerContact.getEmail());
		bpartnerContactRecord.setExternalId(ExternalId.toValue(bpartnerContact.getExternalId()));
		bpartnerContactRecord.setFirstname(bpartnerContact.getFirstName());
		// bpartnerContact.getId() // id is only for lookup and won't be updated later

		bpartnerContactRecord.setLastname(bpartnerContact.getLastName());
		bpartnerContactRecord.setPhone(bpartnerContact.getPhone());

		saveRecord(bpartnerContactRecord);
		final BPartnerContactId bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, bpartnerContactRecord.getAD_User_ID());

		bpartnerContact.setId(bpartnerContactId);
	}
}
