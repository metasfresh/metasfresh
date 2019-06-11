package de.metas.rest_api.bpartner.impl;

import java.util.Collection;
import java.util.List;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Multimaps;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CacheInvalidationKeysMapper;
import de.metas.cache.CCache.CacheMapType;
import de.metas.interfaces.I_C_BPartner;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerLocation.JsonBPartnerLocationBuilder;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

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
public class JsonBPartnerCompositeRepository
{
	private final CacheInvalidationKeysMapper<BPartnerId> invalidationKeysMapper = new CacheInvalidationKeysMapper<BPartnerId>()
	{
		@Override
		public Collection<BPartnerId> computeKeysToInvalidate(@NonNull final TableRecordReference recordRef)
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

	private final CCache<BPartnerId, JsonBPartnerComposite> cache = CCache
			.<BPartnerId, JsonBPartnerComposite> builder()
			.cacheName("JsonBPartnerComposite")
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BPartner_Location.Table_Name)
			.additionalTableNameToResetFor(I_AD_User.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(500)
			.invalidationKeysMapper(invalidationKeysMapper)
			.build();

	public JsonBPartnerComposite getById(@NonNull final BPartnerId bPartnerId)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableList.of(bPartnerId)));
	}

	public ImmutableList<JsonBPartnerComposite> getByIds(@NonNull final Collection<BPartnerId> bPartnerIds)
	{
		final Collection<JsonBPartnerComposite> result = cache.getAllOrLoad(bPartnerIds, this::getByIds0);
		return ImmutableList.copyOf(result);
	}

	private ImmutableMap<BPartnerId, JsonBPartnerComposite> getByIds0(final Collection<BPartnerId> bPartnerIds)
	{
		final List<I_C_BPartner> bPartnerRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();

		final List<I_C_BPartner_Location> bPartnerLocationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_C_BPartner_Location> id2Locations = Multimaps.index(bPartnerLocationRecords, I_C_BPartner_Location::getC_BPartner_ID);

		final List<I_AD_User> contactRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bPartnerIds)
				.create()
				.list();
		final ImmutableListMultimap<Integer, I_AD_User> id2Contacts = Multimaps.index(contactRecords, I_AD_User::getC_BPartner_ID);

		final Builder<BPartnerId, JsonBPartnerComposite> result = ImmutableMap.<BPartnerId, JsonBPartnerComposite> builder();

		for (final I_C_BPartner bPartnerRecord : bPartnerRecords)
		{
			final int id = bPartnerRecord.getC_BPartner_ID();

			final JsonBPartnerComposite jsonBPartnerComposite = JsonBPartnerComposite.builder()
					.bpartner(ofRecord(bPartnerRecord))
					.contacts(ofContactRecords(id2Contacts.get(id)))
					.locations(ofLocationRecords(id2Locations.get(id)))
					.build();

			result.put(BPartnerId.ofRepoId(id), jsonBPartnerComposite);
		}
		return result.build();
	}

	private JsonBPartner ofRecord(@NonNull final I_C_BPartner bpartnerRecord)
	{
		return JsonBPartner.builder()
				.code(bpartnerRecord.getValue())
				.companyName(bpartnerRecord.getCompanyName())
				.externalId(JsonExternalId.ofOrNull(bpartnerRecord.getExternalId()))
				.group(bpartnerRecord.getC_BP_Group().getName())
				.language(bpartnerRecord.getAD_Language())
				.metasfreshId(MetasfreshId.of(bpartnerRecord.getC_BPartner_ID()))
				.name(bpartnerRecord.getName())
				.parentId(MetasfreshId.ofOrNull(bpartnerRecord.getBPartner_Parent_ID()))
				// .phone(bpartnerRecord.get)
				.url(bpartnerRecord.getURL())
				.build();
	}

	private ImmutableList<JsonBPartnerLocation> ofLocationRecords(@NonNull final ImmutableList<I_C_BPartner_Location> immutableList)
	{
		return immutableList.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private JsonBPartnerLocation ofRecord(@NonNull final I_C_BPartner_Location bPartnerLocationRecord)
	{
		final I_C_Location locationRecord = bPartnerLocationRecord.getC_Location();

		final JsonBPartnerLocationBuilder location = JsonBPartnerLocation.builder()
				.address1(locationRecord.getAddress1())
				.address2(locationRecord.getAddress2())
				.city(locationRecord.getCity())
				.countryCode(locationRecord.getC_Country().getCountryCode())
				.externalId(JsonExternalId.ofOrNull(bPartnerLocationRecord.getExternalId()))
				.gln(bPartnerLocationRecord.getGLN())
				.metasfreshBPartnerId(MetasfreshId.of(bPartnerLocationRecord.getC_BPartner_ID()))
				.metasfreshId(MetasfreshId.of(bPartnerLocationRecord.getC_BPartner_Location_ID()))
				.poBox(locationRecord.getPOBox())
				.postal(locationRecord.getPostal())
				.region(locationRecord.getRegionName());

		if (locationRecord.getC_Postal_ID() > 0)
		{
			location.district(locationRecord.getC_Postal().getDistrict());
		}

		return location.build();
	}

	private ImmutableList<JsonContact> ofContactRecords(@NonNull final ImmutableList<I_AD_User> immutableList)
	{
		return immutableList.stream()
				.map(this::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private JsonContact ofRecord(I_AD_User contactRecord)
	{
		return JsonContact.builder()
				.email(contactRecord.getEMail())
				.externalId(JsonExternalId.ofOrNull(contactRecord.getExternalId()))
				.firstName(contactRecord.getFirstname())
				.lastName(contactRecord.getLastname())
				.metasfreshBPartnerId(MetasfreshId.of(contactRecord.getC_BPartner_ID()))
				.name(contactRecord.getName())
				.phone(contactRecord.getPhone())
				.build();
	}
}
