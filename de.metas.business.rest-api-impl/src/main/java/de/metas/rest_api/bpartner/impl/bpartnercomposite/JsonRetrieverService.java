package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import static de.metas.util.Check.isEmpty;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.ad.table.RecordChangeLogRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeQuery;
import de.metas.bpartner.composite.BPartnerCompositeQuery.BPartnerCompositeQueryBuilder;
import de.metas.bpartner.composite.BPartnerCompositeRepository;
import de.metas.bpartner.composite.BPartnerCompositeRepository.ContactIdAndBPartner;
import de.metas.bpartner.composite.BPartnerCompositeRepository.NextPageQuery;
import de.metas.bpartner.composite.BPartnerCompositeRepository.SinceQuery;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactQuery;
import de.metas.bpartner.composite.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.dao.selection.pagination.UnknownPageIdentifierException;
import de.metas.i18n.Language;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.response.JsonResponseBPartner;
import de.metas.rest_api.bpartner.response.JsonResponseComposite;
import de.metas.rest_api.bpartner.response.JsonResponseComposite.JsonResponseCompositeBuilder;
import de.metas.rest_api.changelog.JsonChangeInfo;
import de.metas.rest_api.changelog.JsonChangeInfo.JsonChangeInfoBuilder;
import de.metas.rest_api.changelog.JsonChangeLogItem;
import de.metas.rest_api.changelog.JsonChangeLogItem.JsonChangeLogItemBuilder;
import de.metas.rest_api.bpartner.response.JsonResponseContact;
import de.metas.rest_api.bpartner.response.JsonResponseLocation;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.user.UserId;

import de.metas.util.collections.CollectionUtils;
import de.metas.util.rest.ExternalId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public class JsonRetrieverService
{
	private static final ImmutableMap<String, String> BPARTNER_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put("value", JsonResponseBPartner.CODE)
			.put("companyName", JsonResponseBPartner.COMPANY_NAME)
			.put("externalId", JsonResponseBPartner.EXTERNAL_ID)
			.put("groupId", JsonResponseBPartner.GROUP)
			.put("language", JsonResponseBPartner.LANGUAGE)
			.put("id", JsonResponseBPartner.METASFRESH_ID)
			.put("name", JsonResponseBPartner.NAME)
			.put("parentId", JsonResponseBPartner.PARENT_ID)
			.put("phone", JsonResponseBPartner.PHONE)
			.put("url", JsonResponseBPartner.URL)
			.build();

	private static final ImmutableMap<String, String> CONTACT_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put("email", JsonResponseContact.EMAIL)
			.put("externalId", JsonResponseContact.EXTERNAL_ID)
			.put("firstName", JsonResponseContact.FIRST_NAME)
			.put("lastName", JsonResponseContact.LAST_NAME)
			.put("metasfreshBPartnerId", JsonResponseContact.METASFRESH_B_PARTNER_ID)
			.put("id", JsonResponseContact.METASFRESH_ID)
			.put("name", JsonResponseContact.NAME)
			.put("phone", JsonResponseContact.PHONE)
			.build();

	private static final ImmutableMap<String, String> LOCATION_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put("externalId", JsonResponseLocation.EXTERNAL_ID)
			.put("gln", JsonResponseLocation.GLN)
			.put("id", JsonResponseLocation.METASFRESH_ID)
			.put("address1", JsonResponseLocation.ADDRESS1)
			.put("address2", JsonResponseLocation.ADDRESS2)
			.put("city", JsonResponseLocation.CITY)
			.put("poBox", JsonResponseLocation.PO_BOX)
			.put("postal", JsonResponseLocation.POSTAL)
			.put("region", JsonResponseLocation.REGION)
			.put("district", JsonResponseLocation.DISTRICT)
			.put("countryCode", JsonResponseLocation.COUNTRY_CODE)
			.build();

	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;
	private final transient BPartnerCompositeCache cache;
	private final transient RecordChangeLogRepository recordChangeLogRepository;

	@Getter
	private final String identifier;

	public JsonRetrieverService(
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final RecordChangeLogRepository recordChangeLogRepository,
			@NonNull final String identifier)
	{
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.recordChangeLogRepository = recordChangeLogRepository;
		this.identifier = identifier;

		this.cache = new BPartnerCompositeCache(identifier);
	}

	public Optional<JsonResponseComposite> retrieveJsonBPartnerComposite(@NonNull final String bpartnerIdentifierStr)
	{
		return retrieveBPartnerComposite(bpartnerIdentifierStr).map(this::toJson);
	}

	public Optional<QueryResultPage<JsonResponseComposite>> retrieveJsonBPartnerComposites(
			@Nullable final NextPageQuery nextPageQuery,
			@Nullable final SinceQuery sinceRequest)
	{
		final QueryResultPage<BPartnerComposite> page;
		if (nextPageQuery == null)
		{
			page = bpartnerCompositeRepository.getSince(sinceRequest);
		}
		else
		{
			try
			{
				page = bpartnerCompositeRepository.getNextPage(nextPageQuery);
			}
			catch (final UnknownPageIdentifierException e)
			{
				return Optional.empty();
			}
		}
		final ImmutableList<JsonResponseComposite> jsonBPartnerComposites = page
				.getItems()
				.stream()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		return Optional.of(page.withItems(jsonBPartnerComposites));
	}

	private JsonResponseComposite toJson(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final JsonResponseCompositeBuilder result = JsonResponseComposite.builder();

		// bpartner
		result.bpartner(toJson(bpartnerComposite.getBpartner()));

		// contacts
		for (final BPartnerContact contact : bpartnerComposite.getContacts())
		{
			result.contact(toJson(contact));
		}

		// locations
		for (final BPartnerLocation location : bpartnerComposite.getLocations())
		{
			result.location(toJson(location));
		}
		return result.build();
	}

	private JsonResponseBPartner toJson(@NonNull final BPartner bpartner)
	{
		final BPGroup bpGroup = bpGroupRepository.getbyId(bpartner.getGroupId());

		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(bpartner.getChangeLog(), BPARTNER_FIELD_MAP);

		return JsonResponseBPartner.builder()
				.code(bpartner.getValue())
				.companyName(bpartner.getCompanyName())
				.externalId(JsonConverters.toJsonOrNull(bpartner.getExternalId()))
				.group(bpGroup.getName())
				.language(Language.asLanguageString(bpartner.getLanguage()))
				.metasfreshId(MetasfreshId.ofOrNull(bpartner.getId()))
				.name(bpartner.getName())
				.parentId(MetasfreshId.ofOrNull(bpartner.getParentId()))
				.phone(bpartner.getPhone())
				.url(bpartner.getUrl())
				.changeInfo(jsonChangeInfo)
				.build();
	}

	private JsonChangeInfo createJsonChangeInfo(
			@Nullable final RecordChangeLog recordChangeLog,
			@NonNull final ImmutableMap<String, String> columnMap)
	{
		if (recordChangeLog == null)
		{
			return null;
		}

		final JsonChangeInfoBuilder jsonChangeInfo = JsonChangeInfo.builder()
				.createdBy(MetasfreshId.of(recordChangeLog.getCreatedByUserId()))
				.createdMillis(recordChangeLog.getCreatedTimestamp().toEpochMilli())
				.lastUpdatedBy(MetasfreshId.of(recordChangeLog.getLastChangedByUserId()))
				.lastUpdatedMillis(recordChangeLog.getLastChangedTimestamp().toEpochMilli());

		for (final RecordChangeLogEntry entry : recordChangeLog.getEntries())
		{
			final String columnName = entry.getColumnName();
			if (!columnMap.containsKey(columnName))
			{
				continue; // we don't care for the respective change
			}

			final JsonChangeLogItemBuilder jsonChangeLogItem = JsonChangeLogItem.builder()
					.fieldName(columnMap.get(columnName))
					.updatedBy(MetasfreshId.of(entry.getChangedByUserId()))
					.updatedMillis(entry.getChangedTimestamp().toEpochMilli())
					.newValue(String.valueOf(entry.getValueNew()))
					.oldValue(String.valueOf(entry.getValueOld()));

			jsonChangeInfo.changeLog(jsonChangeLogItem.build());
		}
		return jsonChangeInfo.build();
	}

	private JsonResponseContact toJson(@NonNull final BPartnerContact contact)
	{
		final MetasfreshId metasfreshId = MetasfreshId.of(contact.getId());
		final MetasfreshId metasfreshBPartnerId = MetasfreshId.of(contact.getId().getBpartnerId());

		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(contact.getChangeLog(), CONTACT_FIELD_MAP);

		return JsonResponseContact.builder()
				.email(contact.getEmail())
				.externalId(JsonConverters.toJsonOrNull(contact.getExternalId()))
				.firstName(contact.getFirstName())
				.lastName(contact.getLastName())
				.metasfreshBPartnerId(metasfreshBPartnerId)
				.metasfreshId(metasfreshId)
				.name(contact.getName())
				.phone(contact.getPhone())
				.changeInfo(jsonChangeInfo)
				.build();
	}

	private JsonResponseLocation toJson(@NonNull final BPartnerLocation location)
	{
		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(location.getChangeLog(), LOCATION_FIELD_MAP);

		return JsonResponseLocation.builder()
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.city(location.getCity())
				.countryCode(location.getCountryCode())
				.district(location.getDistrict())
				.externalId(JsonConverters.toJsonOrNull(location.getExternalId()))
				.gln(location.getGln())
				.metasfreshId(MetasfreshId.of(location.getId()))
				.poBox(location.getPoBox())
				.postal(location.getPostal())
				.region(location.getRegion())
				.changeInfo(jsonChangeInfo)
				.build();
	}

	public Optional<BPartnerComposite> retrieveBPartnerComposite(@NonNull final String bpartnerIdentifierStr)
	{
		final BPartnerCompositeLookupKey bpartnerIdLookupKey = createBPartnerIdLookupKey(bpartnerIdentifierStr);

		return retrieveBPartnerComposite(ImmutableList.of(bpartnerIdLookupKey));
	}

	private BPartnerCompositeLookupKey createBPartnerIdLookupKey(@NonNull final String bpartnerIdentifier)
	{
		final IdentifierString identifier = IdentifierString.of(bpartnerIdentifier);
		switch (identifier.getType())
		{
			case EXTERNAL_ID:
				return BPartnerCompositeLookupKey.ofJsonExternalId(identifier.asJsonExternalId());
			case VALUE:
				return BPartnerCompositeLookupKey.ofCode(identifier.getValue());
			case GLN:
				return BPartnerCompositeLookupKey.ofGln(identifier.getValue());
			case METASFRESH_ID:
				return BPartnerCompositeLookupKey.ofMetasfreshId(identifier.asMetasfreshId());
			default:
				throw new AdempiereException("Unexpected type=" + identifier.getType());
		}
	}

	public Optional<BPartnerComposite> retrieveBPartnerComposite(@NonNull final ImmutableList<BPartnerCompositeLookupKey> bpartnerLookupKeys)
	{
		final Collection<BPartnerComposite> allOrLoad = cache.getAllOrLoad(
				bpartnerLookupKeys,
				this::lookupBPartnerByKeys0);

		return extractResult(allOrLoad);
	}

	private Optional<BPartnerComposite> extractResult(@NonNull final Collection<BPartnerComposite> allOrLoad)
	{
		final ImmutableList<BPartnerComposite> distinctComposites = CollectionUtils.extractDistinctElements(allOrLoad, Function.identity());

		final BPartnerComposite result = CollectionUtils.singleElementOrNull(distinctComposites); // we made sure there's not more than one in lookupBPartnerByKeys0
		return result == null ? Optional.empty() : Optional.of(result.deepCopy());
	}

	/** Used to verify that changing actually works the was we expect it to (=> performance) */
	@VisibleForTesting
	Optional<BPartnerComposite> retrieveBPartnerCompositeAssertCacheHit(@NonNull final ImmutableList<BPartnerCompositeLookupKey> bpartnerLookupKeys)
	{
		final Collection<BPartnerComposite> allOrLoad = cache.getAssertAllCached(bpartnerLookupKeys);

		return extractResult(allOrLoad);
	}

	private ImmutableMap<BPartnerCompositeLookupKey, BPartnerComposite> lookupBPartnerByKeys0(
			@NonNull final Collection<BPartnerCompositeLookupKey> queryLookupKeys)
	{
		final BPartnerCompositeQuery query = createBPartnerQuery(queryLookupKeys);

		final List<BPartnerComposite> byQuery = bpartnerCompositeRepository.getByQuery(query);
		if (byQuery.size() > 1)
		{
			throw new AdempiereException("The given lookup keys needs to yield max one BPartnerComposite; items yielded instead: " + byQuery.size())
					.appendParametersToMessage()
					.setParameter("BPartnerIdLookupKeys", queryLookupKeys);
		}
		if (byQuery.isEmpty())
		{
			return ImmutableMap.of();
		}

		final BPartnerComposite singleElement = CollectionUtils.singleElement(byQuery);

		final HashSet<BPartnerCompositeLookupKey> allLookupKeys = new HashSet<>(queryLookupKeys);
		allLookupKeys.addAll(extractBPartnerLookupKeys(singleElement));

		final ImmutableMap.Builder<BPartnerCompositeLookupKey, BPartnerComposite> result = ImmutableMap.builder();
		for (final BPartnerCompositeLookupKey bpartnerLookupKey : allLookupKeys)
		{
			result.put(bpartnerLookupKey, singleElement);
		}
		return result.build();
	}

	private BPartnerCompositeQuery createBPartnerQuery(@NonNull final Collection<BPartnerCompositeLookupKey> bpartnerLookupKeys)
	{
		final BPartnerCompositeQueryBuilder query = BPartnerCompositeQuery
				.builder()
				.onlyOrgId(OrgId.ofRepoIdOrAny(Env.getAD_Org_ID(Env.getCtx())));

		for (final BPartnerCompositeLookupKey bpartnerLookupKey : bpartnerLookupKeys)
		{
			final JsonExternalId jsonExternalId = bpartnerLookupKey.getJsonExternalId();
			if (jsonExternalId != null)
			{
				query.externalId(JsonConverters.fromJsonOrNull(jsonExternalId));
			}

			final String value = bpartnerLookupKey.getCode();
			if (!isEmpty(value, true))
			{
				query.bpartnerValue(value);
			}

			final String gln = bpartnerLookupKey.getGln();
			if (!isEmpty(gln, true))
			{
				query.locationGln(gln);
			}

			final MetasfreshId metasfreshId = bpartnerLookupKey.getMetasfreshId();
			if (metasfreshId != null)
			{
				query.bPartnerId(BPartnerId.ofRepoId(metasfreshId.getValue()));
			}
		}
		return query.build();
	}

	private final Collection<BPartnerCompositeLookupKey> extractBPartnerLookupKeys(@NonNull final BPartnerComposite bPartnerComposite)
	{
		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();

		final BPartner bpartner = bPartnerComposite.getBpartner();
		if (bpartner != null)
		{
			if (bpartner.getId() != null)
			{
				result.add(BPartnerCompositeLookupKey.ofMetasfreshId(MetasfreshId.of(bpartner.getId())));
			}
			if (bpartner.getExternalId() != null)
			{
				result.add(BPartnerCompositeLookupKey.ofJsonExternalId(JsonExternalIds.of(bpartner.getExternalId())));
			}
			if (!isEmpty(bpartner.getValue(), true))
			{
				result.add(BPartnerCompositeLookupKey.ofCode(bpartner.getValue().trim()));
			}
		}

		for (BPartnerLocation location : bPartnerComposite.getLocations())
		{
			if (isEmpty(location.getGln(), true))
			{
				result.add(BPartnerCompositeLookupKey.ofGln(location.getGln().trim()));
			}
		}

		return result.build();
	}

	public Optional<JsonResponseContact> retrieveContact(@NonNull final String contactIdentifierStr)
	{
		final IdentifierString contactIdentifier = IdentifierString.of(contactIdentifierStr);

		final BPartnerContactQuery contactQuery = createContactQuery(contactIdentifier);

		final Optional<ContactIdAndBPartner> optionalContactIdAndBPartner = bpartnerCompositeRepository.getByContact(contactQuery);
		if (!optionalContactIdAndBPartner.isPresent())
		{
			return Optional.empty();
		}
		final ContactIdAndBPartner contactIdAndBPartner = optionalContactIdAndBPartner.get();
		final BPartnerContactId contactId = contactIdAndBPartner.getBpartnerContactId();

		return contactIdAndBPartner
				.getBpartnerComposite()
				.getContact(contactId)
				.map(this::toJson);
	}

	private BPartnerContactQuery createContactQuery(@NonNull final IdentifierString identifier)
	{
		final BPartnerContactQueryBuilder query = BPartnerContactQuery.builder();

		switch (identifier.getType())
		{
			case EXTERNAL_ID:
				query.externalId(ExternalId.of(identifier.getValue()));
				break;
			case VALUE:
				query.value(identifier.getValue());
				break;
			case METASFRESH_ID:
				final int repoId = Integer.parseInt(identifier.getValue());
				query.userId(UserId.ofRepoId(repoId));
				break;
			default:
				throw new AdempiereException("Unexpected type=" + identifier.getType());
		}
		return query.build();
	}
}
