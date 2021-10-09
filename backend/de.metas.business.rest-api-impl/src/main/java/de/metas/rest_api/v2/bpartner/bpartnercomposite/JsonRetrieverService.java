/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.SalesRep;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.composite.repository.NextPageQuery;
import de.metas.bpartner.composite.repository.SinceQuery;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseComposite.JsonResponseCompositeBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseContactRole;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.bpartner.v2.response.JsonResponseSalesRep;
import de.metas.common.changelog.JsonChangeInfo;
import de.metas.common.changelog.JsonChangeInfo.JsonChangeInfoBuilder;
import de.metas.common.changelog.JsonChangeLogItem;
import de.metas.common.changelog.JsonChangeLogItem.JsonChangeLogItemBuilder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JSONPaymentRule;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.dao.selection.pagination.UnknownPageIdentifierException;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.utils.OrgAndBPartnerCompositeLookupKey;
import de.metas.rest_api.utils.OrgAndBPartnerCompositeLookupKeyList;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidEntityException;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.util.Check.isEmpty;

@ToString
public class JsonRetrieverService
{
	/**
	 * Mapping between {@link JsonResponseBPartner} property names and REST-API properties names
	 */
	private static final ImmutableMap<String, String> BPARTNER_FIELD_MAP = ImmutableMap
			.<String, String>builder()
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
			.put(BPartner.VENDOR, JsonResponseBPartner.VENDOR)
			.put(BPartner.CUSTOMER, JsonResponseBPartner.CUSTOMER)
			.put(BPartner.COMPANY, JsonResponseBPartner.COMPANY)
			.put(BPartner.SALES_PARTNER_CODE, JsonResponseBPartner.SALES_PARTNER_CODE)
			.put(BPartner.C_BPARTNER_SALES_REP_ID, JsonResponseSalesRep.SALES_REP_ID)
			.put(BPartner.INTERNAL_NAME, JsonResponseBPartner.INTERNAL_NAME)
			.put(BPartner.PAYMENT_RULE, JsonResponseBPartner.PAYMENT_RULE)
			.put(BPartner.VAT_ID, JsonResponseBPartner.VAT_ID)
			.build();

	/**
	 * Mapping between {@link JsonResponseContact} property names and REST-API properties names
	 */
	private static final ImmutableMap<String, String> CONTACT_FIELD_MAP = ImmutableMap
			.<String, String>builder()
			.put(BPartnerContact.EMAIL, JsonResponseContact.EMAIL)
			.put(BPartnerContact.EXTERNAL_ID, JsonResponseContact.EXTERNAL_ID)
			.put(BPartnerContact.VALUE, JsonResponseContact.CODE)
			.put(BPartnerContact.ACTIVE, JsonResponseContact.ACTIVE)
			.put(BPartnerContact.FIRST_NAME, JsonResponseContact.FIRST_NAME)
			.put(BPartnerContact.LAST_NAME, JsonResponseContact.LAST_NAME)
			.put(BPartnerContact.BIRTHDAY, JsonResponseContact.BIRTHDAY)
			.put(BPartnerContact.ID, JsonResponseContact.METASFRESH_ID)
			.put(BPartnerContact.BPARTNER_ID, JsonResponseContact.METASFRESH_BPARTNER_ID)
			.put(BPartnerContact.NAME, JsonResponseContact.NAME)
			.put(BPartnerContact.GREETING_ID, JsonResponseContact.GREETING)
			.put(BPartnerContact.PHONE, JsonResponseContact.PHONE)
			.put(BPartnerContact.MOBILE_PHONE, JsonResponseContact.MOBILE_PHONE)
			.put(BPartnerContact.FAX, JsonResponseContact.FAX)
			.put(BPartnerContact.DESCRIPTION, JsonResponseContact.DESCRIPTION)
			.put(BPartnerContact.NEWSLETTER, JsonResponseContact.NEWSLETTER)
			.put(BPartnerContact.SUBJECT_MATTER, JsonResponseContact.SUBJECT_MATTER)

			.put(BPartnerContactType.SHIP_TO_DEFAULT, JsonResponseContact.SHIP_TO_DEFAULT)
			.put(BPartnerContactType.BILL_TO_DEFAULT, JsonResponseContact.BILL_TO_DEFAULT)
			.put(BPartnerContactType.DEFAULT_CONTACT, JsonResponseContact.DEFAULT_CONTACT)
			.put(BPartnerContactType.SALES, JsonResponseContact.SALES)
			.put(BPartnerContactType.SALES_DEFAULT, JsonResponseContact.SALES_DEFAULT)
			.put(BPartnerContactType.PURCHASE, JsonResponseContact.PURCHASE)
			.put(BPartnerContactType.PURCHASE_DEFAULT, JsonResponseContact.PURCHASE_DEFAULT)

			.build();

	/**
	 * Mapping between {@link JsonResponseLocation} property names and REST-API properties names
	 */
	private static final ImmutableMap<String, String> LOCATION_FIELD_MAP = ImmutableMap
			.<String, String>builder()
			.put(BPartnerLocation.EXTERNAL_ID, JsonResponseLocation.EXTERNAL_ID)
			.put(BPartnerLocation.GLN, JsonResponseLocation.GLN)
			.put(BPartnerLocation.ID, JsonResponseLocation.METASFRESH_ID)
			.put(BPartnerLocation.ACTIVE, JsonResponseLocation.ACTIVE)
			.put(BPartnerLocation.NAME, JsonResponseLocation.NAME)
			.put(BPartnerLocation.BPARTNERNAME, JsonResponseLocation.BPARTNERNAME)
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

	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private final transient BPartnerQueryService bPartnerQueryService;
	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;

	private final transient GreetingRepository greetingRepository;
	private final ExternalReferenceRestControllerService externalReferenceService;

	private final transient BPartnerCompositeCacheByLookupKey cache;

	@Getter
	private final String identifier;

	public JsonRetrieverService(
			@NonNull final BPartnerQueryService bPartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final GreetingRepository greetingRepository,
			final ExternalReferenceRestControllerService externalReferenceService,
			@NonNull final String identifier)
	{
		this.bPartnerQueryService = bPartnerQueryService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.greetingRepository = greetingRepository;
		this.externalReferenceService = externalReferenceService;
		this.identifier = identifier;

		this.cache = new BPartnerCompositeCacheByLookupKey(identifier);
	}

	public Optional<JsonResponseComposite> getJsonBPartnerComposite(
			@NonNull final OrgId orgId,
			@NonNull final ExternalIdentifier bpartnerIdentifier)
	{
		return getBPartnerComposite(orgId, bpartnerIdentifier).map(this::toJson);
	}

	public Optional<QueryResultPage<JsonResponseComposite>> getJsonBPartnerComposites(
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

		return Optional.of(page.mapTo(this::toJson));
	}

	private JsonResponseComposite toJson(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final BPartner bpartner = bpartnerComposite.getBpartner();

		try (final MDCCloseable ignored = MDC.putCloseable("method", "JsonRetrieverService.toJson(BPartnerComposite)");
				final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, bpartner != null ? bpartner.getId() : null))
		{
			final JsonResponseCompositeBuilder result = JsonResponseComposite.builder();

			// bpartner
			result.bpartner(toJson(bpartner));

			// contacts
			for (final BPartnerContact contact : bpartnerComposite.getContacts())
			{
				final Language language = bpartner.getLanguage();
				result.contact(toJson(contact, language));
			}

			// locations
			for (final BPartnerLocation location : bpartnerComposite.getLocations())
			{
				result.location(toJson(location));
			}
			return result.build();
		}
	}

	private JsonResponseBPartner toJson(@NonNull final BPartner bpartner)
	{
		final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(bpartner.getChangeLog(), BPARTNER_FIELD_MAP);

		return JsonResponseBPartner.builder()
				.active(bpartner.isActive())
				.code(bpartner.getValue())
				.globalId(bpartner.getGlobalId())
				.companyName(bpartner.getCompanyName())
				.group(convertIdToGroupName(bpartner.getGroupId()))
				.language(Language.asLanguageStringOrNull(bpartner.getLanguage()))
				.metasfreshId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(bpartner.getId())))
				.name(bpartner.getName())
				.name2(bpartner.getName2())
				.name3(bpartner.getName3())
				.parentId(JsonMetasfreshId.ofOrNull(BPartnerId.toRepoId(bpartner.getParentId())))
				.phone(bpartner.getPhone())
				.url(bpartner.getUrl())
				.url2(bpartner.getUrl2())
				.url3(bpartner.getUrl3())
				.vendor(bpartner.isVendor())
				.customer(bpartner.isCustomer())
				.company(bpartner.isCompany())
				.salesPartnerCode(bpartner.getSalesPartnerCode())
				.responseSalesRep(getJsonResponseSalesRep(bpartner.getSalesRep()))
				.paymentRule(Optional.ofNullable(bpartner.getPaymentRule())
									 .map(PaymentRule::getCode)
									 .map(JSONPaymentRule::ofCode)
									 .orElse(null))
				.internalName(bpartner.getInternalName())
				.vatId(bpartner.getVatId())
				.changeInfo(jsonChangeInfo)
				.build();
	}

	private static JsonChangeInfo createJsonChangeInfo(
			@Nullable final RecordChangeLog recordChangeLog,
			@NonNull final ImmutableMap<String, String> columnMap)
	{
		if (recordChangeLog == null)
		{
			return null;
		}

		final JsonChangeInfoBuilder jsonChangeInfo = JsonChangeInfo.builder()
				.createdBy(JsonMetasfreshId.ofOrNull(UserId.toRepoId(recordChangeLog.getCreatedByUserId())))
				.createdMillis(recordChangeLog.getCreatedTimestamp().toEpochMilli())
				.lastUpdatedBy(JsonMetasfreshId.ofOrNull(UserId.toRepoId(recordChangeLog.getLastChangedByUserId())))
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
					.updatedBy(JsonMetasfreshId.ofOrNull(UserId.toRepoId(entry.getChangedByUserId())))
					.updatedMillis(entry.getChangedTimestamp().toEpochMilli())
					.newValue(String.valueOf(entry.getValueNew()))
					.oldValue(String.valueOf(entry.getValueOld()));

			jsonChangeInfo.changeLog(jsonChangeLogItem.build());
		}
		return jsonChangeInfo.build();
	}

	@Nullable
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

	private JsonResponseContact toJson(
			@NonNull final BPartnerContact contact,
			@Nullable final Language language)
	{
		try
		{
			final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(BPartnerContactId.toRepoId(contact.getId()));
			final JsonMetasfreshId metasfreshBPartnerId = JsonMetasfreshId.of(BPartnerId.toRepoId(contact.getId().getBpartnerId()));

			final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(contact.getChangeLog(), CONTACT_FIELD_MAP);

			final BPartnerContactType contactType = contact.getContactType();

			String greetingTrl = null;
			if (contact.getGreetingId() != null)
			{
				final Greeting greeting = greetingRepository.getById(contact.getGreetingId());
				final String ad_language = language != null ? language.getAD_Language() : Env.getAD_Language();
				greetingTrl = greeting.getGreeting(ad_language);
			}
			final List<JsonResponseContactRole> roles = contact.getRoles()
					.stream()
					.map(role -> JsonResponseContactRole.builder()
							.name(role.getName())
							.isUniquePerBpartner(role.isUniquePerBpartner())
							.build())
					.collect(Collectors.toList());

			return JsonResponseContact.builder()
					.active(contact.isActive())
					.email(contact.getEmail())
					.firstName(contact.getFirstName())
					.lastName(contact.getLastName())
					.birthday(contact.getBirthday())
					.metasfreshBPartnerId(metasfreshBPartnerId)
					.metasfreshId(metasfreshId)
					.name(contact.getName())
					.greeting(greetingTrl)
					.newsletter(contact.isNewsletter())
					.invoiceEmailEnabled(contact.getInvoiceEmailEnabled())
					.phone(contact.getPhone())
					.mobilePhone(contact.getMobilePhone())
					.fax(contact.getFax())
					.description(contact.getDescription())
					.defaultContact(contactType.getIsDefaultContactOr(false))
					.billToDefault(contactType.getIsBillToDefaultOr(false))
					.shipToDefault(contactType.getIsShipToDefaultOr(false))
					.sales(contactType.getIsSalesOr(false))
					.salesDefault(contactType.getIsSalesDefaultOr(false))
					.purchase(contactType.getIsPurchaseOr(false))
					.purchaseDefault(contactType.getIsPurchaseDefaultOr(false))
					.subjectMatter(contact.isSubjectMatterContact())
					.roles(roles)
					.changeInfo(jsonChangeInfo)
					.build();
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte).appendParametersToMessage()
					.setParameter("AD_User_ID", BPartnerContactId.toRepoId(contact.getId()))
					.setParameter("externalId", ExternalId.toValue(contact.getExternalId()));
		}
	}

	private static JsonResponseLocation toJson(@NonNull final BPartnerLocation location)
	{
		try
		{
			final JsonChangeInfo jsonChangeInfo = createJsonChangeInfo(location.getChangeLog(), LOCATION_FIELD_MAP);

			final BPartnerLocationType locationType = location.getLocationType();
			return JsonResponseLocation.builder()
					.active(location.isActive())
					.name(location.getName())
					.bpartnerName(location.getBpartnerName())
					.address1(location.getAddress1())
					.address2(location.getAddress2())
					.address3(location.getAddress3())
					.address4(location.getAddress4())
					.city(location.getCity())
					.countryCode(location.getCountryCode())
					.district(location.getDistrict())
					.gln(GLN.toCode(location.getGln()))
					.metasfreshId(JsonMetasfreshId.of(BPartnerLocationId.toRepoId(location.getId())))
					.poBox(location.getPoBox())
					.postal(location.getPostal())
					.region(location.getRegion())
					.shipTo(locationType.getIsShipToOr(false))
					.shipToDefault(locationType.getIsShipToDefaultOr(false))
					.billTo(locationType.getIsBillToOr(false))
					.billToDefault(locationType.getIsBillToDefaultOr(false))
					.changeInfo(jsonChangeInfo)
					.build();
		}
		catch (final RuntimeException rte)
		{
			throw AdempiereException.wrapIfNeeded(rte).appendParametersToMessage()
					.setParameter("C_BPartner_Location_ID", BPartnerLocationId.toRepoId(location.getId()))
					.setParameter("externalId", ExternalId.toValue(location.getExternalId()));
		}
	}

	public Optional<BPartnerComposite> getBPartnerComposite(
			@NonNull final OrgId orgId,
			@NonNull final ExternalIdentifier bpartnerIdentifier)
	{
		final OrgAndBPartnerCompositeLookupKeyList bpartnerIdLookupKey;

		if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(bpartnerIdentifier.getType()))
		{
			final Optional<MetasfreshId> metasfreshId = resolveExternalReference(orgId, bpartnerIdentifier, BPartnerExternalReferenceType.BPARTNER);
			if (metasfreshId.isPresent())
			{
				bpartnerIdLookupKey = OrgAndBPartnerCompositeLookupKeyList.ofMetasfreshId(orgId, metasfreshId.get());
			}
			else
			{
				return Optional.empty();
			}
		}
		else if (ExternalIdentifier.Type.GLN.equals(bpartnerIdentifier.getType()))
		{
			bpartnerIdLookupKey = OrgAndBPartnerCompositeLookupKeyList.ofGLN(orgId, bpartnerIdentifier.asGLN());
		}
		else if (ExternalIdentifier.Type.METASFRESH_ID.equals(bpartnerIdentifier.getType()))
		{
			bpartnerIdLookupKey = OrgAndBPartnerCompositeLookupKeyList.ofMetasfreshId(orgId, bpartnerIdentifier.asMetasfreshId());
		}
		else
		{
			return Optional.empty();
		}

		return getBPartnerComposite(bpartnerIdLookupKey);
	}

	Optional<BPartnerComposite> getBPartnerComposite(
			@NonNull final OrgAndBPartnerCompositeLookupKeyList bpartnerLookupKeys)
	{
		final ImmutableList<OrgAndBPartnerCompositeLookupKey> singleBPartnerLookupKeys = bpartnerLookupKeys.asSingeKeys();

		final Collection<BPartnerComposite> bpartnerComposites = cache.getAllOrLoad(singleBPartnerLookupKeys, this::retrieveBPartnerComposites);
		return extractResult(bpartnerComposites);
	}

	@NonNull
	public Optional<MetasfreshId> resolveExternalReference(
			@Nullable final OrgId orgId,
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		final Optional<JsonMetasfreshId> jsonMetasfreshId =
				externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, externalIdentifier, externalReferenceType);

		return jsonMetasfreshId.map(metasfreshId -> MetasfreshId.of(metasfreshId.getValue()));
	}

	public Optional<BPartnerId> resolveBPartnerExternalIdentifier(@NonNull final ExternalIdentifier bPartnerExternalIdentifier, @NonNull final OrgId orgId)
	{
		switch (bPartnerExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerExternalIdentifier.asMetasfreshId().getValue());
				return Optional.of(bPartnerId);
			case EXTERNAL_REFERENCE:
				return externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, bPartnerExternalIdentifier, BPartnerExternalReferenceType.BPARTNER)
						.map(JsonMetasfreshId::getValue)
						.map(BPartnerId::ofRepoId);
			case VALUE:
				final BPartnerQuery valQuery = BPartnerQuery.builder()
						.onlyOrgId(orgId)
						.bpartnerValue(bPartnerExternalIdentifier.asValue())
						.build();
				return bpartnersRepo.retrieveBPartnerIdBy(valQuery);
			case GLN:
				final BPartnerQuery glnQuery = BPartnerQuery.builder()
						.onlyOrgId(orgId)
						.gln(bPartnerExternalIdentifier.asGLN())
						.build();
				return bpartnersRepo.retrieveBPartnerIdBy(glnQuery);
			default:
				throw new InvalidIdentifierException("Given external identifier type is not supported!")
						.setParameter("externalIdentifierType", bPartnerExternalIdentifier.getType())
						.setParameter("rawExternalIdentifier", bPartnerExternalIdentifier.getRawValue());
		}
	}

	@NonNull
	public Optional<JsonResponseLocation> resolveExternalBPartnerLocationId(
			@NonNull final OrgId orgId,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final ExternalIdentifier bPartnerLocationExternalId)
	{
		final Optional<JsonResponseComposite> optBpartnerComposite = getJsonBPartnerComposite(orgId, bpartnerIdentifier);

		return optBpartnerComposite.flatMap(jsonResponseComposite -> jsonResponseComposite
				.getLocations()
				.stream()
				.filter(jsonBPartnerLocation -> isBPartnerLocationMatches(orgId, jsonBPartnerLocation, bPartnerLocationExternalId))
				.findAny());
	}

	/**
	 * Visible to verify that caching actually works the way we expect it to (=> performance)
	 */
	@VisibleForTesting
	Optional<BPartnerComposite> getBPartnerCompositeAssertCacheHit(@NonNull final ImmutableList<OrgAndBPartnerCompositeLookupKey> bpartnerLookupKeys)
	{
		final Collection<BPartnerComposite> bpartnerComposites = cache.getAssertAllCached(bpartnerLookupKeys);
		return extractResult(bpartnerComposites);
	}

	private static Optional<BPartnerComposite> extractResult(@NonNull final Collection<BPartnerComposite> bpartnerComposites)
	{
		final ImmutableList<BPartnerComposite> distinctComposites = CollectionUtils.extractDistinctElements(bpartnerComposites, Function.identity());

		// There might be no bpartner yet. in that case we get not error but null.
		// We made sure there's not more than by calling bpartnerCompositeRepository.getSingleByQuery.
		final BPartnerComposite result = CollectionUtils.singleElementOrNull(distinctComposites);

		return result == null ? Optional.empty() : Optional.of(result.deepCopy());
	}

	/**
	 * @return a map where multiple lookup keys all map to the single BPartnerComposite that we just retrieved.
	 */
	private ImmutableMap<OrgAndBPartnerCompositeLookupKey, BPartnerComposite> retrieveBPartnerComposites(
			@NonNull final Collection<OrgAndBPartnerCompositeLookupKey> singleQueryLookupKeys)
	{
		final OrgAndBPartnerCompositeLookupKeyList queryLookupKeys = OrgAndBPartnerCompositeLookupKeyList.ofSingleLookupKeys(singleQueryLookupKeys);
		final BPartnerQuery query = bPartnerQueryService.createQuery(queryLookupKeys);
		final Optional<BPartnerComposite> bpartnerComposite;
		try
		{
			bpartnerComposite = bpartnerCompositeRepository.getSingleByQuery(query);
		}
		catch (final AdempiereException e)
		{
			throw new InvalidEntityException(TranslatableStrings.constant("Unable to retrieve single BPartnerComposite"), e)
					.appendParametersToMessage()
					.setParameter("BPartnerCompositeLookupKeys", queryLookupKeys);
		}
		if (!bpartnerComposite.isPresent())
		{
			return ImmutableMap.of();
		}

		final BPartnerComposite singleElement = bpartnerComposite.get();

		final OrgAndBPartnerCompositeLookupKeyList allLookupKeys = queryLookupKeys.union(extractBPartnerLookupKeys(singleElement));

		final ImmutableMap.Builder<OrgAndBPartnerCompositeLookupKey, BPartnerComposite> result = ImmutableMap.builder();
		for (final BPartnerCompositeLookupKey bpartnerLookupKey : allLookupKeys.getCompositeLookupKeys())
		{
			final OrgAndBPartnerCompositeLookupKey singleLookupKey = OrgAndBPartnerCompositeLookupKey.of(bpartnerLookupKey, allLookupKeys.getOrgId());
			result.put(singleLookupKey, singleElement);
		}
		return result.build();
	}

	private static OrgAndBPartnerCompositeLookupKeyList extractBPartnerLookupKeys(@NonNull final BPartnerComposite bPartnerComposite)
	{
		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();

		final BPartner bpartner = bPartnerComposite.getBpartner();
		if (bpartner != null)
		{
			if (bpartner.getId() != null)
			{
				result.add(BPartnerCompositeLookupKey.ofMetasfreshId(bpartner.getId()));
			}
			if (bpartner.getExternalId() != null)
			{
				result.add(BPartnerCompositeLookupKey.ofExternalId(bpartner.getExternalId()));
			}
			if (!isEmpty(bpartner.getValue(), true))
			{
				result.add(BPartnerCompositeLookupKey.ofCode(bpartner.getValue().trim()));
			}
		}

		for (final BPartnerLocation location : bPartnerComposite.getLocations())
		{
			if (location.getGln() != null)
			{
				result.add(BPartnerCompositeLookupKey.ofGln(location.getGln()));
			}
		}

		return OrgAndBPartnerCompositeLookupKeyList.of(bPartnerComposite.getOrgId(), result.build());
	}

	public Optional<JsonResponseContact> getContact(@NonNull final ExternalIdentifier contactIdentifier)
	{
		final Optional<BPartnerContactQuery> contactQuery = createContactQuery(contactIdentifier);
		if (!contactQuery.isPresent())
		{
			return Optional.empty();
		}

		final Optional<BPartnerCompositeAndContactId> optionalContactIdAndBPartner = bpartnerCompositeRepository.getByContact(contactQuery.get());
		if (!optionalContactIdAndBPartner.isPresent())
		{
			return Optional.empty();
		}
		final BPartnerCompositeAndContactId contactIdAndBPartner = optionalContactIdAndBPartner.get();
		final BPartnerContactId contactId = contactIdAndBPartner.getBpartnerContactId();

		final BPartnerComposite bpartnerComposite = contactIdAndBPartner.getBpartnerComposite();

		return bpartnerComposite
				.extractContact(contactId)
				.map(c -> toJson(c, bpartnerComposite.getBpartner().getLanguage()));
	}

	private Optional<BPartnerContactQuery> createContactQuery(@NonNull final ExternalIdentifier identifier)
	{
		final BPartnerContactQueryBuilder query = BPartnerContactQuery.builder();

		switch (identifier.getType())
		{
			case EXTERNAL_REFERENCE:
				final Optional<MetasfreshId> metasfreshId =
						resolveExternalReference(null, identifier, ExternalUserReferenceType.USER_ID);

				if (metasfreshId.isPresent())
				{
					query.userId(UserId.ofRepoId(metasfreshId.get().getValue()));
				}
				else
				{
					return Optional.empty();
				}

				break;
			case METASFRESH_ID:
				final UserId userId = UserId.ofRepoId(identifier.asMetasfreshId().getValue());
				query.userId(userId);
				break;
			default:
				throw new AdempiereException("Unexpected type=" + identifier.getType());
		}

		return Optional.of(query.build());
	}

	private boolean isBPartnerLocationMatches(
			@NonNull final OrgId orgId,
			@NonNull final JsonResponseLocation jsonBPartnerLocation,
			@NonNull final ExternalIdentifier locationIdentifier)
	{
		switch (locationIdentifier.getType())
		{
			case EXTERNAL_REFERENCE:
				final Optional<MetasfreshId> metasfreshId =
						resolveExternalReference(orgId, locationIdentifier, BPLocationExternalReferenceType.BPARTNER_LOCATION);

				return metasfreshId.isPresent() &&
						MetasfreshId.equals(metasfreshId.get(), MetasfreshId.of(jsonBPartnerLocation.getMetasfreshId()));
			case GLN:
				return GLN.equals(GLN.ofNullableString(jsonBPartnerLocation.getGln()), locationIdentifier.asGLN());
			case METASFRESH_ID:
				return MetasfreshId.equals(locationIdentifier.asMetasfreshId(), MetasfreshId.of(jsonBPartnerLocation.getMetasfreshId()));
			default:
				throw new AdempiereException("Unexpected type=" + locationIdentifier.getType());
		}
	}

	@Nullable
	private JsonResponseSalesRep getJsonResponseSalesRep(@Nullable final SalesRep salesRep)
	{
		if (salesRep == null)
		{
			return null;
		}

		return JsonResponseSalesRep.builder()
				.salesRepId(JsonMetasfreshId.of(salesRep.getId().getRepoId()))
				.salesRepValue(salesRep.getValue())
				.build();
	}
}
