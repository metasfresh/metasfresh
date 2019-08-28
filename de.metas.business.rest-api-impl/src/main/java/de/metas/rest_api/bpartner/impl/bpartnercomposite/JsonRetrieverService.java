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
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
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
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.dao.selection.pagination.UnknownPageIdentifierException;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.Language;
import de.metas.organization.OrgId;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.response.JsonResponseBPartner;
import de.metas.rest_api.bpartner.response.JsonResponseComposite;
import de.metas.rest_api.bpartner.response.JsonResponseComposite.JsonResponseCompositeBuilder;
import de.metas.rest_api.bpartner.response.JsonResponseContact;
import de.metas.rest_api.bpartner.response.JsonResponseLocation;
import de.metas.rest_api.changelog.JsonChangeInfo;
import de.metas.rest_api.changelog.JsonChangeInfo.JsonChangeInfoBuilder;
import de.metas.rest_api.changelog.JsonChangeLogItem;
import de.metas.rest_api.changelog.JsonChangeLogItem.JsonChangeLogItemBuilder;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.RepoIdAware;
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
	/** Mapping between {@link JsonResponseBPartner} property names and REST-API properties names */
	private static final ImmutableMap<String, String> BPARTNER_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put(BPartner.VALUE, JsonResponseBPartner.CODE)
			.put(BPartner.COMPANY_NAME, JsonResponseBPartner.COMPANY_NAME)
			.put(BPartner.EXTERNAL_ID, JsonResponseBPartner.EXTERNAL_ID)
			.put(BPartner.ACTIVE, JsonResponseBPartner.ACTIVE)
			.put(BPartner.GROUP_ID, JsonResponseBPartner.GROUP_NAME)
			.put(BPartner.LANGUAGE, JsonResponseBPartner.LANGUAGE)
			.put(BPartner.ID, JsonResponseBPartner.METASFRESH_ID)
			.put(BPartner.NAME, JsonResponseBPartner.NAME)
			.put(BPartner.NAME_2, JsonResponseBPartner.NAME_2)
			.put(BPartner.NAME_3, JsonResponseBPartner.NAME_3)
			.put(BPartner.PARENT_ID, JsonResponseBPartner.PARENT_ID)
			.put(BPartner.PHONE, JsonResponseBPartner.PHONE)
			.put(BPartner.URL, JsonResponseBPartner.URL)
			.put(BPartner.URL_2, JsonResponseBPartner.URL_2)
			.put(BPartner.URL_3, JsonResponseBPartner.URL_3)
			.build();

	/** Mapping between {@link JsonResponseContact} property names and REST-API properties names */
	private static final ImmutableMap<String, String> CONTACT_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put(BPartnerContact.EMAIL, JsonResponseContact.EMAIL)
			.put(BPartnerContact.EXTERNAL_ID, JsonResponseContact.EXTERNAL_ID)
			.put(BPartnerContact.ACTIVE, JsonResponseContact.ACTIVE)
			.put(BPartnerContact.FIRST_NAME, JsonResponseContact.FIRST_NAME)
			.put(BPartnerContact.LAST_NAME, JsonResponseContact.LAST_NAME)
			.put(BPartnerContact.ID, JsonResponseContact.METASFRESH_ID)
			.put(BPartnerContact.BPARTNER_ID, JsonResponseContact.METASFRESH_BPARTNER_ID)
			.put(BPartnerContact.NAME, JsonResponseContact.NAME)
			.put(BPartnerContact.GREETING_ID, JsonResponseContact.GREETING)
			.put(BPartnerContact.PHONE, JsonResponseContact.PHONE)
			.put(BPartnerContact.MOBILE_PHONE, JsonResponseContact.MOBILE_PHONE)
			.put(BPartnerContact.FAX, JsonResponseContact.FAX)
			.put(BPartnerContact.DESCRIPTION, JsonResponseContact.DESCRIPTION)
			.put(BPartnerContact.NEWSLETTER, JsonResponseContact.NEWSLETTER)

			.put(BPartnerContactType.SHIP_TO_DEFAULT, JsonResponseContact.SHIP_TO_DEFAULT)
			.put(BPartnerContactType.BILL_TO_DEFAULT, JsonResponseContact.BILL_TO_DEFAULT)
			.put(BPartnerContactType.DEFAULT_CONTACT, JsonResponseContact.DEFAULT_CONTACT)
			.put(BPartnerContactType.SALES, JsonResponseContact.SALES)
			.put(BPartnerContactType.SALES_DEFAULT, JsonResponseContact.SALES_DEFAULT)
			.put(BPartnerContactType.PURCHASE, JsonResponseContact.PURCHASE)
			.put(BPartnerContactType.PURCHASE_DEFAULT, JsonResponseContact.PURCHASE_DEFAULT)
			.put(BPartnerContactType.SUBJECT_MATTER, JsonResponseContact.SUBJECT_MATTER)

			.build();

	/** Mapping between {@link JsonResponseLocation} property names and REST-API properties names */
	private static final ImmutableMap<String, String> LOCATION_FIELD_MAP = ImmutableMap
			.<String, String> builder()
			.put(BPartnerLocation.EXTERNAL_ID, JsonResponseLocation.EXTERNAL_ID)
			.put(BPartnerLocation.GLN, JsonResponseLocation.GLN)
			.put(BPartnerLocation.ID, JsonResponseLocation.METASFRESH_ID)
			.put(BPartnerLocation.ACTIVE, JsonResponseLocation.ACTIVE)
			.put(BPartnerLocation.NAME, JsonResponseLocation.NAME)
			.put(BPartnerLocation.ADDRESS_1, JsonResponseLocation.ADDRESS_1)
			.put(BPartnerLocation.ADDRESS_2, JsonResponseLocation.ADDRESS_2)
			.put(BPartnerLocation.ADDRESS_3, JsonResponseLocation.ADDRESS_3)
			.put(BPartnerLocation.ADDRESS_4, JsonResponseLocation.ADDRESS_4)
			.put(BPartnerLocation.CITY, JsonResponseLocation.CITY)
			.put(BPartnerLocation.PO_BOX, JsonResponseLocation.PO_BOX)
			.put(BPartnerLocation.POSTAL, JsonResponseLocation.POSTAL)
			.put(BPartnerLocation.REGION, JsonResponseLocation.REGION)
			.put(BPartnerLocation.DISTRICT, JsonResponseLocation.DISTRICT)
			.put(BPartnerLocation.COUNTRYCODE, JsonResponseLocation.COUNTRY_CODE)
			.put(BPartnerLocationType.BILL_TO, JsonResponseLocation.BILL_TO)
			.put(BPartnerLocationType.BILL_TO_DEFAULT, JsonResponseLocation.BILL_TO_DEFAULT)
			.put(BPartnerLocationType.SHIP_TO, JsonResponseLocation.SHIP_TO)
			.put(BPartnerLocationType.SHIP_TO_DEFAULT, JsonResponseLocation.SHIP_TO_DEFAULT)
			.build();

	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;

	private final transient GreetingRepository greetingRepository;

	private final transient BPartnerCompositeCache cache;
	private final transient RecordChangeLogRepository recordChangeLogRepository;

	@Getter
	private final String identifier;

	public JsonRetrieverService(
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final GreetingRepository greetingRepository,
			@NonNull final RecordChangeLogRepository recordChangeLogRepository,
			@NonNull final String identifier)
	{
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.greetingRepository = greetingRepository;
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
			final Language language = bpartnerComposite.getBpartner().getLanguage();
			result.contact(toJson(contact, language));
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
		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(bpartner.getChangeLog(), BPARTNER_FIELD_MAP);

		return JsonResponseBPartner.builder()
				.active(bpartner.isActive())
				.code(bpartner.getValue())
				.companyName(bpartner.getCompanyName())
				.externalId(JsonConverters.toJsonOrNull(bpartner.getExternalId()))
				.group(convertIdToGroupName(bpartner.getGroupId()))
				.language(convertLanguageToString(bpartner.getLanguage()))
				.metasfreshId(MetasfreshId.ofOrNull(bpartner.getId()))
				.name(bpartner.getName())
				.name2(bpartner.getName2())
				.name3(bpartner.getName3())
				.parentId(convertIdToMetasFreshId(bpartner.getParentId()))
				.phone(bpartner.getPhone())
				.url(bpartner.getUrl())
				.url2(bpartner.getUrl2())
				.url3(bpartner.getUrl3())
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

	private String convertIdToGroupName(@Nullable final BPGroupId bpGroupId)
	{
		if (bpGroupId == null)
		{
			return null;
		}
		final BPGroup bpGroup = bpGroupRepository.getbyId(bpGroupId);
		final String groupName = bpGroup.getName();
		return groupName;
	}

	private MetasfreshId convertIdToMetasFreshId(@Nullable final RepoIdAware repoIdAware)
	{
		if (repoIdAware == null)
		{
			return null;
		}
		return MetasfreshId.of(repoIdAware);
	}

	private String convertLanguageToString(@Nullable final Language language)
	{
		if (language == null)
		{
			return null;
		}
		return Language.asLanguageString(language);
	}

	private JsonResponseContact toJson(
			@NonNull final BPartnerContact contact,
			@Nullable final Language language)
	{
		final MetasfreshId metasfreshId = MetasfreshId.of(contact.getId());
		final MetasfreshId metasfreshBPartnerId = MetasfreshId.of(contact.getId().getBpartnerId());

		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(contact.getChangeLog(), CONTACT_FIELD_MAP);

		final BPartnerContactType contactType = contact.getContactType();

		String greetingTrl = null;
		if (contact.getGreetingId() != null)
		{
			final Greeting greeting = greetingRepository.getByIdAndLang(contact.getGreetingId(), language);
			greetingTrl = greeting.getGreeting();
		}
		return JsonResponseContact.builder()
				.active(contact.isActive())
				.email(contact.getEmail())
				.externalId(JsonConverters.toJsonOrNull(contact.getExternalId()))
				.firstName(contact.getFirstName())
				.lastName(contact.getLastName())
				.metasfreshBPartnerId(metasfreshBPartnerId)
				.metasfreshId(metasfreshId)
				.name(contact.getName())
				.greeting(greetingTrl)
				.newsletter(contact.isNewsletter())
				.phone(contact.getPhone())
				.mobilePhone(contact.getMobilePhone())
				.fax(contact.getFax())
				.description(contact.getDescription())
				.defaultContact(contactType.getDefaultContactNotNull())
				.billToDefault(contactType.getBillToDefaultNotNull())
				.shipToDefault(contactType.getShipToDefaultNotNull())
				.sales(contactType.getSalesNotNull())
				.salesDefault(contactType.getSalesDefaultNotNull())
				.purchase(contactType.getPurchaseNotNull())
				.purchaseDefault(contactType.getPurchaseDefaultNotNull())
				.subjectMatter(contactType.getSubjectMatterNotNull())
				.changeInfo(jsonChangeInfo)
				.build();
	}

	private JsonResponseLocation toJson(@NonNull final BPartnerLocation location)
	{
		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(location.getChangeLog(), LOCATION_FIELD_MAP);

		final BPartnerLocationType locationType = location.getLocationType();

		return JsonResponseLocation.builder()
				.active(location.isActive())
				.name(location.getName())
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.address3(location.getAddress3())
				.address4(location.getAddress4())
				.city(location.getCity())
				.countryCode(location.getCountryCode())
				.district(location.getDistrict())
				.externalId(JsonConverters.toJsonOrNull(location.getExternalId()))
				.gln(location.getGln())
				.metasfreshId(MetasfreshId.of(location.getId()))
				.poBox(location.getPoBox())
				.postal(location.getPostal())
				.region(location.getRegion())
				.shipTo(locationType.getShipToNotNull())
				.shipToDefault(locationType.getShipToDefaultNotNull())
				.billTo(locationType.getBillToNotNull())
				.billToDefault(locationType.getBillToDefaultNotNull())
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
				return BPartnerCompositeLookupKey.ofCode(identifier.asValue());
			case GLN:
				return BPartnerCompositeLookupKey.ofGln(identifier.asGLN());
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

	/** Used to verify that changing actually works the way we expect it to (=> performance) */
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

		for (final BPartnerLocation location : bPartnerComposite.getLocations())
		{
			if (!isEmpty(location.getGln(), true))
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

		final BPartnerComposite bpartnerComposite = contactIdAndBPartner.getBpartnerComposite();

		return bpartnerComposite
				.getContact(contactId)
				.map(c -> toJson(c, bpartnerComposite.getBpartner().getLanguage()));
	}

	private BPartnerContactQuery createContactQuery(@NonNull final IdentifierString identifier)
	{
		final BPartnerContactQueryBuilder query = BPartnerContactQuery.builder();

		switch (identifier.getType())
		{
			case EXTERNAL_ID:
				query.externalId(identifier.asExternalId());
				break;
			case VALUE:
				query.value(identifier.asValue());
				break;
			case METASFRESH_ID:
				final int repoId = identifier.asMetasfreshId().getValue();
				query.userId(UserId.ofRepoId(repoId));
				break;
			default:
				throw new AdempiereException("Unexpected type=" + identifier.getType());
		}
		return query.build();
	}
}
