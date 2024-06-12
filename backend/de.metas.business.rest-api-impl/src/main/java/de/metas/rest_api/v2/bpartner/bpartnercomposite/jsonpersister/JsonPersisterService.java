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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerContactType.BPartnerContactTypeBuilder;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.BPartnerLocationType.BPartnerLocationTypeBuilder;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountsUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import de.metas.common.bpartner.v2.request.creditLimit.JsonMoney;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsert;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem.JsonResponseBPartnerCompositeUpsertItemBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert.JsonResponseUpsertBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem.JsonResponseUpsertItemBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem.SyncOutcome;
import de.metas.common.externalreference.v2.JsonExternalReferenceItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.SyncAdvise.IfExists;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.BPartnerCompositeRestUtils;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.ValueMappingHelper;
import de.metas.sectionCode.SectionCodeId;
import de.metas.sectionCode.SectionCodeService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_CreditLimit_Type;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;
import static de.metas.bpartner.interceptor.MakeUniqueNameCommand.BPARTNER_LOCATION_NAME_DEFAULT;
import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.externalreference.ExternalIdentifier.Type.EXTERNAL_REFERENCE;
import static de.metas.externalreference.ExternalIdentifier.Type.METASFRESH_ID;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isBlank;

@ToString
public class JsonPersisterService
{
	private static final Logger logger = LogManager.getLogger(JsonPersisterService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final transient JsonRetrieverService jsonRetrieverService;
	private final transient JsonRequestConsolidateService jsonRequestConsolidateService;
	private final transient ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;
	private final transient CurrencyRepository currencyRepository;
	private final transient AlbertaBPartnerCompositeService albertaBPartnerCompositeService;
	private final transient SectionCodeService sectionCodeService;
	private final transient IncotermsRepository incotermsRepository;
	private final transient BPartnerCreditLimitRepository bPartnerCreditLimitRepository;

	/**
	 * A unique indentifier for this instance.
	 */
	@Getter
	private final String identifier;

	public JsonPersisterService(
			@NonNull final JsonRetrieverService jsonRetrieverService,
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final AlbertaBPartnerCompositeService albertaBPartnerCompositeService,
			@NonNull final SectionCodeService sectionCodeService,
			@NonNull final IncotermsRepository incotermsRepository,
			@NonNull final BPartnerCreditLimitRepository bPartnerCreditLimitRepository,
			@NonNull final String identifier)
	{
		this.jsonRetrieverService = jsonRetrieverService;
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.currencyRepository = currencyRepository;
		this.albertaBPartnerCompositeService = albertaBPartnerCompositeService;
		this.sectionCodeService = sectionCodeService;
		this.incotermsRepository = incotermsRepository;
		this.bPartnerCreditLimitRepository = bPartnerCreditLimitRepository;

		this.identifier = assumeNotEmpty(identifier, "Param Identifier may not be empty");
	}

	public JsonResponseBPartnerCompositeUpsertItem persist(
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistWithinTrx(requestItem, parentSyncAdvise));
	}

	private JsonResponseBPartnerCompositeUpsertItem persistWithinTrx(
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		// TODO: add support to retrieve without changelog; we don't need changelog here;
		// but! make sure we don't screw up caching

		final String orgCode = requestItem.getBpartnerComposite().getOrgCode();

		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);
		final String rawBpartnerIdentifier = requestItem.getBpartnerIdentifier();
		final ExternalIdentifier bpartnerIdentifier = ExternalIdentifier.of(rawBpartnerIdentifier);
		final Optional<BPartnerComposite> optionalBPartnerComposite = jsonRetrieverService.getBPartnerComposite(orgId, bpartnerIdentifier);

		final JsonResponseBPartnerCompositeUpsertItemUnderConstrunction resultBuilder = new JsonResponseBPartnerCompositeUpsertItemUnderConstrunction();
		resultBuilder.setJsonResponseBPartnerUpsertItemBuilder(JsonResponseUpsertItem.builder().identifier(rawBpartnerIdentifier));

		final JsonRequestComposite jsonRequestComposite = requestItem.getBpartnerComposite();
		final SyncAdvise effectiveSyncAdvise = CoalesceUtil.coalesceNotNull(jsonRequestComposite.getSyncAdvise(), parentSyncAdvise);

		final BPartnerComposite bpartnerComposite;
		if (optionalBPartnerComposite.isPresent())
		{
			logger.debug("Found BPartner with id={} for identifier={} (orgCode={})", optionalBPartnerComposite.get().getBpartner().getId(), rawBpartnerIdentifier, jsonRequestComposite.getOrgCode());
			// load and mutate existing aggregation root
			bpartnerComposite = optionalBPartnerComposite.get();
			resultBuilder.setNewBPartner(false);
		}
		else
		{
			logger.debug("Found no BPartner for identifier={} (orgCode={})", rawBpartnerIdentifier, jsonRequestComposite.getOrgCode());
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("bpartner")
						.resourceIdentifier(rawBpartnerIdentifier)
						.parentResource(jsonRequestComposite)
						.build()
						.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
			}
			// create new aggregation root
			logger.debug("Going to create a new bpartner-composite (orgCode={})", jsonRequestComposite.getOrgCode());
			bpartnerComposite = BPartnerComposite.builder().build();
			resultBuilder.setNewBPartner(true);
		}

		syncJsonToBPartnerComposite(
				resultBuilder,
				jsonRequestComposite,
				bpartnerComposite,
				effectiveSyncAdvise);

		final JsonResponseBPartnerCompositeUpsertItem result = resultBuilder.build();
		handleExternalReferenceRecords(requestItem, result, orgCode);

		handleAlbertaInfo(bpartnerComposite.getOrgId(), effectiveSyncAdvise, requestItem, result);

		return result;
	}

	@Data
	private static final class JsonResponseBPartnerCompositeUpsertItemUnderConstrunction
	{
		private JsonResponseUpsertItemBuilder jsonResponseBPartnerUpsertItemBuilder;
		private boolean newBPartner;

		private ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseContactUpsertItems;

		private ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseLocationUpsertItems;

		private ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseBankAccountUpsertItems;

		@NonNull
		@Getter(AccessLevel.NONE)
		@Setter(AccessLevel.NONE)
		private final HashMap<String, JsonResponseUpsertItem> jsonResponseCreditLimitUpsertItems = new HashMap<>();

		public JsonResponseBPartnerCompositeUpsertItem build()
		{
			final JsonResponseBPartnerCompositeUpsertItemBuilder itemBuilder = JsonResponseBPartnerCompositeUpsertItem
					.builder()
					.responseBPartnerItem(jsonResponseBPartnerUpsertItemBuilder.build());

			final ImmutableList<JsonResponseUpsertItem> contactUpsertItems = jsonResponseContactUpsertItems
					.values()
					.stream()
					.map(JsonResponseUpsertItemBuilder::build)
					.collect(ImmutableList.toImmutableList());

			final ImmutableList<JsonResponseUpsertItem> locationUpsertItems = jsonResponseLocationUpsertItems
					.values()
					.stream()
					.map(JsonResponseUpsertItemBuilder::build)
					.collect(ImmutableList.toImmutableList());

			final ImmutableList<JsonResponseUpsertItem> bankAccountUpsertItems = jsonResponseBankAccountUpsertItems
					.values()
					.stream()
					.map(JsonResponseUpsertItemBuilder::build)
					.collect(ImmutableList.toImmutableList());

			return itemBuilder
					.responseContactItems(contactUpsertItems)
					.responseLocationItems(locationUpsertItems)
					.responseBankAccountItems(bankAccountUpsertItems)
					.responseCreditLimitItems(jsonResponseCreditLimitUpsertItems.values())
					.build();
		}

		public void addCreditLimitIdToResult(@NonNull final JsonMetasfreshId creditLimitId)
		{
			final String creditLimitIdentifier = String.valueOf(creditLimitId.getValue());

			if (jsonResponseCreditLimitUpsertItems.get(creditLimitIdentifier) != null)
			{
				return;
			}

			jsonResponseCreditLimitUpsertItems.put(creditLimitIdentifier, JsonResponseUpsertItem
					.builder()
					.identifier(creditLimitIdentifier)
					.metasfreshId(creditLimitId)
					.syncOutcome(SyncOutcome.CREATED)
					.build());
		}

		public void addCreditLimitResult(@NonNull final List<JsonResponseUpsertItem> creditLimitUpsertItemList)
		{
			creditLimitUpsertItemList.forEach(creditLimitUpsertItem -> {
				if (jsonResponseCreditLimitUpsertItems.get(creditLimitUpsertItem.getIdentifier()) != null)
				{
					return;
				}

				jsonResponseCreditLimitUpsertItems.put(creditLimitUpsertItem.getIdentifier(), creditLimitUpsertItem);
			});
		}

	}

	public JsonResponseUpsertItem persist(
			@NonNull final JsonRequestContactUpsertItem contactRequestUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistWithinTrx(contactRequestUpsertItem, parentSyncAdvise));

	}

	public JsonResponseUpsertItem persistWithinTrx(
			@NonNull final JsonRequestContactUpsertItem contactRequestUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ExternalIdentifier contactIdentifier = ExternalIdentifier.of(contactRequestUpsertItem.getContactIdentifier());

		final JsonRequestContact jsonContact = contactRequestUpsertItem.getContact();

		final Optional<BPartnerContactQuery> contactQuery = createContactQuery(contactIdentifier);
		final Optional<BPartnerCompositeAndContactId> optionalContactIdAndBPartner = contactQuery
				.flatMap(bpartnerCompositeRepository::getByContact);

		final BPartnerContact contact;
		final BPartnerComposite bpartnerComposite;
		final SyncOutcome syncOutcome;
		if (optionalContactIdAndBPartner.isPresent())
		{
			final BPartnerCompositeAndContactId contactIdAndBPartner = optionalContactIdAndBPartner.get();

			bpartnerComposite = contactIdAndBPartner.getBpartnerComposite();

			contact = bpartnerComposite
					.extractContact(contactIdAndBPartner.getBpartnerContactId())
					.get(); // it's there, or we wouldn't be in this if-block
			syncOutcome = SyncOutcome.UPDATED;
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(contactIdentifier.getRawValue()).parentResource(jsonContact).build()
						.setParameter("effectiveSyncAdvise", parentSyncAdvise);
			}

			if (jsonContact.getMetasfreshBPartnerId() == null)
			{
				throw new MissingPropertyException("JsonContact.metasfreshBPartnerId", jsonContact);
			}

			final BPartnerId bpartnerId = BPartnerId.ofRepoId(jsonContact.getMetasfreshBPartnerId().getValue());

			bpartnerComposite = bpartnerCompositeRepository.getById(bpartnerId);

			contact = BPartnerContact.builder().build();
			bpartnerComposite.getContacts().add(contact);
			syncOutcome = SyncOutcome.CREATED;
		}

		contact.addHandle(contactIdentifier.getRawValue()); // always add the handle; we'll need it later, even if the contact existed and was not updated

		syncJsonToContact(jsonContact, contact);

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		final Optional<BPartnerContact> persistedContact = bpartnerComposite.extractContactByHandle(contactIdentifier.getRawValue());

		final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(BPartnerContactId.toRepoId(persistedContact.get().getId()));

		final JsonResponseUpsertItem responseUpsertItem = JsonResponseUpsertItem.builder()
				.identifier(contactIdentifier.getRawValue())
				.metasfreshId(metasfreshId)
				.syncOutcome(syncOutcome)
				.build();

		if (SyncOutcome.CREATED.equals(syncOutcome))
		{
			final OrgId orgId = bpartnerComposite.getOrgId();
			Check.assumeNotNull(orgId, "BPartner was just saved! OrgId will for sure be there!");

			JsonExternalReferenceHelper.getExternalReferenceItem(contactRequestUpsertItem)
					.filter(referenceItem -> referenceItem.getExternalReference() != null)
					.flatMap(referenceItem -> mapToJsonRequestExternalReferenceUpsert(responseUpsertItem,
																					  ImmutableMap.of(referenceItem.getExternalReference(), referenceItem)))
					.ifPresent(upsertReferenceReq -> externalReferenceRestControllerService.performUpsert(upsertReferenceReq, bpartnerComposite.getOrgCode(orgDAO::getOrgCode)));
		}

		return responseUpsertItem;
	}

	private Optional<BPartnerContactQuery> createContactQuery(
			@NonNull final ExternalIdentifier contactIdentifier)
	{
		final BPartnerContactQueryBuilder contactQuery = BPartnerContactQuery.builder();

		switch (contactIdentifier.getType())
		{
			case EXTERNAL_REFERENCE:
				final Optional<MetasfreshId> metasfreshId =
						jsonRetrieverService.resolveExternalReference(null, contactIdentifier, ExternalUserReferenceType.USER_ID);

				if (metasfreshId.isPresent())
				{
					contactQuery.userId(UserId.ofRepoId(metasfreshId.get().getValue()));
				}
				else
				{
					return Optional.empty();
				}
				break;
			case METASFRESH_ID:
				final UserId userId = UserId.ofRepoId(contactIdentifier.asMetasfreshId().getValue());
				contactQuery.userId(userId);
				break;
			default:
				throw new InvalidIdentifierException(contactIdentifier.getRawValue());
		}
		return Optional.of(contactQuery.build());
	}

	public Optional<JsonResponseUpsert> persistForBPartner(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestLocationUpsert jsonRequestLocationUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistForBPartnerWithinTrx(orgCode, bpartnerIdentifier, jsonRequestLocationUpsert, parentSyncAdvise));
	}

	/**
	 * Adds or updates the given locations. Leaves all unrelated locations of the same bPartner untouched
	 *
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	private Optional<JsonResponseUpsert> persistForBPartnerWithinTrx(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestLocationUpsert jsonRequestLocationUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);

		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.getBPartnerComposite(orgId, bpartnerIdentifier);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesceNotNull(jsonRequestLocationUpsert.getSyncAdvise(), parentSyncAdvise);

		final List<JsonRequestLocationUpsertItem> requestItems = jsonRequestLocationUpsert.getRequestItems();

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonLocation(bpartnerComposite.getOrgId(), requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getLocationIdentifier(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		// now collect the metasfreshIds that we got after having invoked save
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			final Optional<BPartnerLocation> bpartnerLocation = bpartnerComposite.extractLocationByHandle(requestItem.getLocationIdentifier());

			if (bpartnerLocation.isPresent())
			{
				final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(BPartnerLocationId.toRepoId(bpartnerLocation.get().getId()));

				final JsonResponseUpsertItem responseItem = identifierToBuilder
						.get(requestItem.getLocationIdentifier())
						.metasfreshId(metasfreshId)
						.build();
				response.responseItem(responseItem);

				JsonExternalReferenceHelper.getExternalReferenceItem(requestItem)
						.filter(referenceItem -> referenceItem.getExternalReference() != null)
						.flatMap(referenceItem -> mapToJsonRequestExternalReferenceUpsert(responseItem,
																						  ImmutableMap.of(referenceItem.getExternalReference(), referenceItem)))
						.ifPresent(upsertReferenceReq -> externalReferenceRestControllerService.performUpsert(upsertReferenceReq, bpartnerComposite.getOrgCode(orgDAO::getOrgCode)));
			}

		}

		return Optional.of(response.build());
	}

	public Optional<JsonResponseUpsert> persistForBPartner(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestContactUpsert jsonContactUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistForBPartnerWithinTrx(orgCode, bpartnerIdentifier, jsonContactUpsert, parentSyncAdvise));
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	private Optional<JsonResponseUpsert> persistForBPartnerWithinTrx(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestContactUpsert jsonContactUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);

		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.getBPartnerComposite(orgId, bpartnerIdentifier);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesceNotNull(jsonContactUpsert.getSyncAdvise(), parentSyncAdvise);

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonContact(bpartnerComposite.getOrgId(), requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getContactIdentifier(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			final BPartnerContact bpartnerContact = bpartnerComposite.extractContactByHandle(requestItem.getContactIdentifier()).orElse(null);

			if (bpartnerContact == null)
			{
				continue;
			}

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getContactIdentifier())
					.metasfreshId(JsonMetasfreshId.of(BPartnerContactId.toRepoId(bpartnerContact.getId())))
					.build();
			response.responseItem(responseItem);

			JsonExternalReferenceHelper.getExternalReferenceItem(requestItem)
					.filter(referenceItem -> referenceItem.getExternalReference() != null)
					.flatMap(referenceItem -> mapToJsonRequestExternalReferenceUpsert(responseItem,
																					  ImmutableMap.of(referenceItem.getExternalReference(), referenceItem)))
					.ifPresent(upsertReferenceReq -> externalReferenceRestControllerService.performUpsert(upsertReferenceReq, bpartnerComposite.getOrgCode(orgDAO::getOrgCode)));
		}

		return Optional.of(response.build());
	}

	public Optional<JsonResponseUpsert> persistForBPartner(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestBankAccountsUpsert jsonBankAccountsUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistForBPartnerWithinTrx(orgCode, bpartnerIdentifier, jsonBankAccountsUpsert, parentSyncAdvise));
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	public Optional<JsonResponseUpsert> persistForBPartnerWithinTrx(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final JsonRequestBankAccountsUpsert jsonBankAccountsUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);

		final BPartnerComposite bpartnerComposite = jsonRetrieverService
				.getBPartnerComposite(orgId, bpartnerIdentifier)
				.orElse(null);
		if (bpartnerComposite == null)
		{
			return Optional.empty(); // 404
		}

		final ShortTermBankAccountIndex shortTermIndex = new ShortTermBankAccountIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesceNotNull(jsonBankAccountsUpsert.getSyncAdvise(), parentSyncAdvise);

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonBankAccountsUpsert.getRequestItems())
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonBankAccount(requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getIban(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		// now collect what we got
		final JsonResponseUpsertBuilder responseBuilder = JsonResponseUpsert.builder();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonBankAccountsUpsert.getRequestItems())
		{
			final BPartnerBankAccount bankAccount = bpartnerComposite.getBankAccountByIBAN(requestItem.getIban())
					.orElseThrow(() -> new AdempiereException("Something went wrong! No BPBankAccount found for IBAN=" + requestItem.getIban()));

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getIban())
					.metasfreshId(JsonMetasfreshId.of(BPartnerBankAccountId.toRepoId(bankAccount.getId())))
					.build();
			responseBuilder.responseItem(responseItem);
		}

		final JsonResponseUpsert responseUpsert = responseBuilder.build();

		getExternalReferenceUpsertRequestList(responseUpsert.getResponseItems(), jsonBankAccountsUpsert)
				.forEach(externalRefUpsert -> externalReferenceRestControllerService.performUpsert(externalRefUpsert, orgCode));

		return Optional.of(responseUpsert);
	}

	private void syncJsonToBPartnerComposite(
			@NonNull final JsonResponseBPartnerCompositeUpsertItemUnderConstrunction resultBuilder,
			@NonNull final JsonRequestComposite jsonRequestComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		syncJsonToOrg(jsonRequestComposite, bpartnerComposite, parentSyncAdvise);

		Check.assumeNotNull(bpartnerComposite.getOrgId(), "bpartnerComposite.orgId must be resolved at this point!");

		final BooleanWithReason anythingWasSynced = syncJsonToBPartner(
				jsonRequestComposite,
				bpartnerComposite,
				resultBuilder.isNewBPartner(),
				parentSyncAdvise,
				bpartnerComposite.getOrgId());
		if (anythingWasSynced.isTrue())
		{
			resultBuilder.getJsonResponseBPartnerUpsertItemBuilder().syncOutcome(resultBuilder.isNewBPartner() ? SyncOutcome.CREATED : SyncOutcome.UPDATED);
		}
		else
		{
			resultBuilder.getJsonResponseBPartnerUpsertItemBuilder().syncOutcome(SyncOutcome.NOTHING_DONE);
		}
		resultBuilder.setJsonResponseContactUpsertItems(syncJsonToContacts(jsonRequestComposite, bpartnerComposite, parentSyncAdvise));

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> identifierToLocationResponse = syncJsonToLocations(jsonRequestComposite, bpartnerComposite, parentSyncAdvise);
		resultBuilder.setJsonResponseLocationUpsertItems(identifierToLocationResponse);

		resultBuilder.setJsonResponseBankAccountUpsertItems(syncJsonToBankAccounts(jsonRequestComposite, bpartnerComposite, parentSyncAdvise));

		resultBuilder.addCreditLimitResult(syncJsonToCreditLimits(jsonRequestComposite, bpartnerComposite, parentSyncAdvise, bpartnerComposite.getOrgId()));

		bpartnerCompositeRepository.save(bpartnerComposite, true);

		//
		// supplement the metasfreshiId which we now have after the "save()"
		resultBuilder.getJsonResponseBPartnerUpsertItemBuilder().metasfreshId(JsonMetasfreshId.of(BPartnerId.toRepoId(bpartnerComposite.getBpartner().getId())));

		if (jsonRequestComposite.getBpartner() != null)
		{
			handleBPartnerValueExternalReference(
					JsonMetasfreshId.of(bpartnerComposite.getBpartner().getId().getRepoId()),
					jsonRequestComposite.getBpartner().getCode(),
					jsonRequestComposite.getOrgCode());
		}

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseContactUpsertItemBuilders = resultBuilder.getJsonResponseContactUpsertItems();
		for (final JsonRequestContactUpsertItem requestItem : jsonRequestComposite.getContactsNotNull().getRequestItems())
		{
			final String originalIdentifier = requestItem.getContactIdentifier();

			final JsonResponseUpsertItemBuilder builder = jsonResponseContactUpsertItemBuilders.get(originalIdentifier);
			final Optional<BPartnerContact> contact = bpartnerComposite.extractContactByHandle(originalIdentifier);
			builder.metasfreshId(JsonMetasfreshId.of(BPartnerContactId.toRepoId(contact.get().getId())));
		}

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseLocationUpsertItemBuilders = resultBuilder.getJsonResponseLocationUpsertItems();
		for (final JsonRequestLocationUpsertItem requestItem : jsonRequestComposite.getLocationsNotNull().getRequestItems())
		{
			final String originalIdentifier = requestItem.getLocationIdentifier();

			final JsonResponseUpsertItemBuilder builder = jsonResponseLocationUpsertItemBuilders.get(originalIdentifier);
			final Optional<BPartnerLocation> location = bpartnerComposite.extractLocationByHandle(originalIdentifier);
			builder.metasfreshId(JsonMetasfreshId.of(BPartnerLocationId.toRepoId(location.get().getId())));
		}

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseBankAccountUpsertItemBuilders = resultBuilder.getJsonResponseBankAccountUpsertItems();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonRequestComposite.getBankAccountsNotNull().getRequestItems())
		{
			final JsonResponseUpsertItemBuilder builder = jsonResponseBankAccountUpsertItemBuilders.get(requestItem.getIban());
			final BPartnerBankAccount bankAccount = bpartnerComposite.getBankAccountByIBAN(requestItem.getIban())
					.orElseThrow(() -> new AdempiereException("No BPBankAccount found for IBAN=" + requestItem.getIban()));
			builder.metasfreshId(JsonMetasfreshId.of(bankAccount.getIdNotNull().getRepoId()));
		}

		bpartnerComposite.getCreditLimits()
				.stream()
				.peek(creditLimit -> Check.assumeNotNull(creditLimit.getId(), "BPartnerComposite was already stored, so we except all the items to have ids by now"))
				.map(BPartnerCreditLimit::getId)
				.map(BPartnerCreditLimitId::getRepoId)
				.map(JsonMetasfreshId::of)
				.forEach(resultBuilder::addCreditLimitIdToResult);
	}

	private void handleBPartnerValueExternalReference(
			@NonNull final JsonMetasfreshId metasfreshId,
			@Nullable final String bPartnerCode,
			@Nullable final String orgCode)
	{
		if (bPartnerCode == null)
		{
			return;
		}

		final ExternalBusinessKey externalBusinessKey = ExternalBusinessKey.of(bPartnerCode);

		if (!externalBusinessKey.getType().equals(ExternalBusinessKey.Type.EXTERNAL_REFERENCE))
		{
			return;
		}

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(externalBusinessKey.asExternalValueAndSystem().getValue())
				.type(BPartnerExternalReferenceType.BPARTNER_VALUE.getCode())
				.build();

		final JsonExternalReferenceItem externalReferenceItem =
				JsonExternalReferenceItem.of(externalReferenceLookupItem, metasfreshId);

		final JsonRequestExternalReferenceUpsert externalReferenceUpsert = JsonRequestExternalReferenceUpsert.builder()
				.systemName(JsonExternalSystemName.of(externalBusinessKey.asExternalValueAndSystem().getExternalSystem()))
				.externalReferenceItem(externalReferenceItem)
				.build();

		externalReferenceRestControllerService.performUpsert(externalReferenceUpsert, orgCode);

	}

	private void syncJsonToOrg(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final SyncAdvise syncAdvise = coalesceNotNull(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);
		final boolean hasOrgId = bpartnerComposite.getOrgId() != null;

		if (hasOrgId && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}

		final OrgId orgId = retrieveOrgIdOrDefault(jsonBPartnerComposite.getOrgCode());
		bpartnerComposite.setOrgId(orgId);
	}

	private BooleanWithReason syncJsonToBPartner(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			final boolean bpartnerCompositeIsNew,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final OrgId orgId)
	{
		final JsonRequestBPartner jsonBPartner = jsonBPartnerComposite.getBpartner();
		if (jsonBPartner == null)
		{
			return BooleanWithReason.falseBecause("JsonRequestComposite does not include a JsonRequestBPartner");
		}

		final SyncAdvise effCompositeSyncAdvise = coalesceNotNull(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		if (bpartnerCompositeIsNew && effCompositeSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder().resourceName("bpartner").parentResource(jsonBPartnerComposite).build();
		}

		final BPartner bpartner = bpartnerComposite.getBpartner();
		final SyncAdvise effectiveSyncAdvise = coalesceNotNull(jsonBPartner.getSyncAdvise(), effCompositeSyncAdvise);
		final IfExists ifExistsAdvise = effectiveSyncAdvise.getIfExists();

		if (!bpartnerCompositeIsNew && !ifExistsAdvise.isUpdate())
		{
			return BooleanWithReason.falseBecause("JsonRequestBPartner exists and effectiveSyncAdvise.ifExists=" + ifExistsAdvise);
		}

		bpartner.setIdentifiedByExternalReference(true);

		// active
		if (jsonBPartner.getActive() != null)
		{
			bpartner.setActive(jsonBPartner.getActive());
		}
		if (jsonBPartner.isActiveSet())
		{
			if (jsonBPartner.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				bpartner.setActive(jsonBPartner.getActive());
			}
		}

		// code / value
		if (jsonBPartner.isCodeSet())
		{
			final ExternalBusinessKey externalBusinessKey = ExternalBusinessKey.of(jsonBPartner.getCode());
			if (externalBusinessKey.getType().equals(ExternalBusinessKey.Type.VALUE))
			{
				bpartner.setValue(StringUtils.trim(externalBusinessKey.asValue()));

			}
		}

		// globalId
		if (jsonBPartner.isGlobalIdset())
		{
			bpartner.setGlobalId(StringUtils.trim(jsonBPartner.getGlobalId()));
		}

		// companyName
		if (jsonBPartner.isCompanyNameSet())
		{
			bpartner.setCompanyName(StringUtils.trim(jsonBPartner.getCompanyName()));
			bpartner.setCompany(Check.isNotBlank(jsonBPartner.getCompanyName()));
		}

		// name
		if (jsonBPartner.isNameSet())
		{
			bpartner.setName(StringUtils.trim(jsonBPartner.getName()));
		}

		// name2
		if (jsonBPartner.isName2Set())
		{
			bpartner.setName2(StringUtils.trim(jsonBPartner.getName2()));
		}

		// name3
		if (jsonBPartner.isName3Set())
		{
			bpartner.setName3(StringUtils.trim(jsonBPartner.getName3()));
		}

		if (jsonBPartner.isCustomerSet())
		{
			if (jsonBPartner.getCustomer() == null)
			{
				logger.debug("Ignoring boolean property \"customer\" : null ");
			}
			else
			{
				bpartner.setCustomer(jsonBPartner.getCustomer());
			}
		}
		if (jsonBPartner.isVendorSet())
		{
			if (jsonBPartner.getVendor() == null)
			{
				logger.debug("Ignoring boolean property \"vendor\" : null ");
			}
			else
			{
				bpartner.setVendor(jsonBPartner.getVendor());
			}
		}

		// group - attempt to fall back to default group
		if (jsonBPartner.isGroupSet())
		{
			if (jsonBPartner.getGroup() == null)
			{
				logger.debug("Setting \"groupId\" to null; -> will attempt to insert default groupId");
				bpartner.setGroupId(null);
			}
			else
			{
				final Optional<BPGroup> optionalBPGroup = bpGroupRepository
						.getByNameAndOrgId(jsonBPartner.getGroup(), orgId);

				final BPGroup bpGroup;
				if (optionalBPGroup.isPresent())
				{
					bpGroup = optionalBPGroup.get();
					bpGroup.setName(jsonBPartner.getGroup().trim());
				}
				else
				{
					bpGroup = BPGroup.of(orgId, null, jsonBPartner.getGroup().trim());
				}

				final BPGroupId bpGroupId = bpGroupRepository.save(bpGroup);
				bpartner.setGroupId(bpGroupId);
			}
		}

		if (bpartner.getGroupId() == null)
		{
			final Optional<BPGroup> optionalBPGroup = bpGroupRepository.getDefaultGroup();
			if (!optionalBPGroup.isPresent())
			{
				throw MissingResourceException.builder()
						.resourceName("group")
						.detail(TranslatableStrings.constant("JsonRequestBPartner specifies no bpartner-group and there is no default group"))
						.build();
			}
			bpartner.setGroupId(optionalBPGroup.get().getId());
			logger.debug("\"groupId\" = null; -> using default groupId={}", bpartner.getGroupId().getRepoId());
		}

		// language
		if (jsonBPartner.isLanguageSet())
		{
			bpartner.setLanguage(Language.asLanguage(StringUtils.trim(jsonBPartner.getLanguage())));
		}

		// invoiceRule
		if (jsonBPartner.isInvoiceRuleSet())
		{
			if (jsonBPartner.getInvoiceRule() == null)
			{
				bpartner.setCustomerInvoiceRule(null);
			}
			else
			{
				bpartner.setCustomerInvoiceRule(BPartnerCompositeRestUtils.getInvoiceRule(jsonBPartner.getInvoiceRule()));
			}
		}

		// poInvoiceRule
		if (jsonBPartner.isPoInvoiceRuleSet())
		{
			if (jsonBPartner.getPoInvoiceRule() == null)
			{
				bpartner.setVendorInvoiceRule(null);
			}
			else
			{
				bpartner.setVendorInvoiceRule(BPartnerCompositeRestUtils.getInvoiceRule(jsonBPartner.getPoInvoiceRule()));
			}
		}

		// metasfreshId - we will never update it

		// parentId
		if (jsonBPartner.isParentIdSet())
		{
			// TODO make sure in the repo that the parent-bpartner is reachable
			bpartner.setParentId(BPartnerId.ofRepoIdOrNull(JsonMetasfreshId.toValue(jsonBPartner.getParentId())));
		}

		// phone
		if (jsonBPartner.isPhoneSet())
		{
			bpartner.setPhone(StringUtils.trim(jsonBPartner.getPhone()));
		}

		// url
		if (jsonBPartner.isUrlSet())
		{
			bpartner.setUrl(StringUtils.trim(jsonBPartner.getUrl()));
		}

		// url2
		if (jsonBPartner.isUrl2Set())
		{
			bpartner.setUrl2(StringUtils.trim(jsonBPartner.getUrl2()));
		}

		// url3
		if (jsonBPartner.isUrl3Set())
		{
			bpartner.setUrl3(StringUtils.trim(jsonBPartner.getUrl3()));
		}

		// VAT ID
		if (jsonBPartner.isVatIdSet())
		{
			bpartner.setVatId(StringUtils.trim(jsonBPartner.getVatId()));
		}

		//memo
		if (jsonBPartner.isMemoIsSet())
		{
			bpartner.setMemo(jsonBPartner.getMemo());
		}

		// priceListId
		if (jsonBPartner.isPriceListIdSet())
		{
			final Integer priceListId = JsonMetasfreshId.toValue(jsonBPartner.getPriceListId());
			if (priceListId != null && priceListId > 0)
			{
				final PricingSystemId pricingSystemId = priceListDAO.getPricingSystemId(PriceListId.ofRepoId(priceListId));
				bpartner.setCustomerPricingSystemId(pricingSystemId);
			}
		}

		if (jsonBPartner.isSectionCodeValueSet())
		{
			final SectionCodeId sectionCodeId = Optional.ofNullable(jsonBPartner.getSectionCodeValue())
					.filter(Check::isNotBlank)
					.map(StringUtils::trim)
					.map(code -> sectionCodeService.getSectionCodeIdByValue(orgId, code))
					.orElse(null);

			bpartner.setSectionCodeId(sectionCodeId);
		}

		if (jsonBPartner.isDescriptionSet())
		{
			bpartner.setDescription(StringUtils.trim(jsonBPartner.getDescription()));
		}

		if (jsonBPartner.isDeliveryRuleSet())
		{
			bpartner.setDeliveryRule(Optional.ofNullable(jsonBPartner.getDeliveryRule())
											 .map(ValueMappingHelper::getDeliveryRule)
											 .orElse(null));
		}

		if (jsonBPartner.isDeliveryViaRuleSet())
		{
			bpartner.setDeliveryViaRule(Optional.ofNullable(jsonBPartner.getDeliveryViaRule())
												.map(ValueMappingHelper::getDeliveryViaRule)
												.orElse(null));
		}

		if (jsonBPartner.isStorageWarehouseSet())
		{
			bpartner.setStorageWarehouse(Boolean.TRUE.equals(jsonBPartner.getStorageWarehouse()));
		}

		if (jsonBPartner.isIncotermsCustomerValueSet())
		{
			final IncotermsId incotermsCustomerId = Optional.ofNullable(jsonBPartner.getIncotermsCustomerValue())
					.filter(Check::isNotBlank)
					.map(StringUtils::trim)
					.map(incotermsRepository::getByValue)
					.map(Incoterms::getIncotermsId)
					.orElse(null);

			bpartner.setIncotermsCustomerId(incotermsCustomerId);
		}

		if (jsonBPartner.isIncotermsVendorValueSet())
		{
			final IncotermsId incotermsVendorId = Optional.ofNullable(jsonBPartner.getIncotermsVendorValue())
					.filter(Check::isNotBlank)
					.map(StringUtils::trim)
					.map(incotermsRepository::getByValue)
					.map(Incoterms::getIncotermsId)
					.orElse(null);

			bpartner.setIncotermsVendorId(incotermsVendorId);
		}

		if (jsonBPartner.isCustomerPaymentTermIdentifierSet())
		{
			final PaymentTermId customerPaymentTermId = Optional.ofNullable(jsonBPartner.getCustomerPaymentTermIdentifier())
					.filter(Check::isNotBlank)
					.map(StringUtils::trim)
					.map(ExternalIdentifier::of)
					.map(paymentIdentifier -> jsonRetrieverService.getPaymentTermId(paymentIdentifier, orgId))
					.orElse(null);

			bpartner.setCustomerPaymentTermId(customerPaymentTermId);
		}

		if (jsonBPartner.isVendorPaymentTermIdentifierSet())
		{
			final PaymentTermId vendorPaymentTermId = Optional.ofNullable(jsonBPartner.getVendorPaymentTermIdentifier())
					.filter(Check::isNotBlank)
					.map(StringUtils::trim)
					.map(ExternalIdentifier::of)
					.map(paymentIdentifier -> jsonRetrieverService.getPaymentTermId(paymentIdentifier, orgId))
					.orElse(null);

			bpartner.setVendorPaymentTermId(vendorPaymentTermId);
		}

		if (jsonBPartner.isParentIdentifierSet())
		{
			final BPartnerId parentBPartnerId = Optional.ofNullable(StringUtils.trim(jsonBPartner.getParentIdentifier()))
					.filter(Check::isNotBlank)
					.map(ExternalIdentifier::of)
					.flatMap(parentIdentifier -> jsonRetrieverService.resolveBPartnerExternalIdentifier(parentIdentifier, orgId))
					.orElse(null);

			bpartner.setParentId(parentBPartnerId);
		}

		if (jsonBPartner.isPaymentRuleSet())
		{
			bpartner.setPaymentRule(Optional.ofNullable(jsonBPartner.getPaymentRule())
											.map(ValueMappingHelper::getPaymentRule)
											.orElse(null));
		}

		if (jsonBPartner.isPaymentRulePOSet())
		{
			bpartner.setPaymentRulePO(Optional.ofNullable(jsonBPartner.getPaymentRulePO())
											  .map(ValueMappingHelper::getPaymentRule)
											  .orElse(null));
		}

		if (jsonBPartner.isSectionGroupPartnerIdentifierSet())
		{
			final BPartnerId sectionGroupPartnerId = Optional.ofNullable(StringUtils.trim(jsonBPartner.getSectionGroupPartnerIdentifier()))
					.filter(Check::isNotBlank)
					.map(ExternalIdentifier::of)
					.flatMap(sectionGroupPartnerIdentifier -> jsonRetrieverService.resolveBPartnerExternalIdentifier(sectionGroupPartnerIdentifier, orgId))
					.orElse(null);

			bpartner.setSectionGroupPartnerId(sectionGroupPartnerId);
		}

		if (jsonBPartner.isProspectSet())
		{
			bpartner.setProspect(jsonBPartner.getProspect());
		}

		if (jsonBPartner.isSapBPartnerCodeSet())
		{
			bpartner.setSapBPartnerCode(jsonBPartner.getSapBPartnerCode());
		}

		if (jsonBPartner.isSectionGroupPartnerSet())
		{
			bpartner.setSectionGroupPartner(jsonBPartner.isSectionGroupPartner());
		}

		if (jsonBPartner.isSectionPartnerSet())
		{
			bpartner.setSectionPartner(jsonBPartner.isSectionPartner());
		}

		if (jsonBPartner.isUrproduzentSet())
		{
			bpartner.setUrproduzent(jsonBPartner.isUrproduzent());
		}

		return BooleanWithReason.TRUE;
	}

	private ImmutableMap<String, JsonResponseUpsertItemBuilder> syncJsonToContacts(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		final JsonRequestContactUpsert contacts = jsonBPartnerComposite.getContactsNotNull();

		final SyncAdvise contactsSyncAdvise = coalesceNotNull(contacts.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			result.put(
					contactRequestItem.getContactIdentifier(),
					syncJsonContact(bpartnerComposite.getOrgId(), contactRequestItem, contactsSyncAdvise, shortTermIndex));
		}

		// if we have contacts with e.g. isBillToDefault, then make sure that none of the previously existing locations also have such a default flag
		final boolean mustTakeCareOfUnusedContactDefaultFlags = contactsSyncAdvise.getIfNotExists().isCreate() || contactsSyncAdvise.getIfExists().isUpdate();
		if (mustTakeCareOfUnusedContactDefaultFlags)
		{
			resetUnusedContactDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);
		}

		return result.build();
	}

	/**
	 * If the json contacts have default flags set, then this method unsets all corresponding default flags of the shortTermIndex's {@link BPartnerContact}s.
	 */
	private void resetUnusedContactDefaultFlagsIfNeeded(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final ShortTermContactIndex shortTermIndex)
	{
		final List<JsonRequestContactUpsertItem> contactRequestItems = jsonBPartnerComposite.getContactsNotNull().getRequestItems();

		boolean hasDefaultContact = false;
		boolean hasBillToDefault = false;
		boolean hasShipToDefault = false;
		boolean hasPurchaseDefault = false;
		boolean hasSalesDefault = false;
		for (final JsonRequestContactUpsertItem contactRequestItem : contactRequestItems)
		{
			final JsonRequestContact contact = contactRequestItem.getContact();
			final Boolean defaultContact = contact.getDefaultContact();
			if (!hasDefaultContact && defaultContact != null && defaultContact)
			{
				hasDefaultContact = true;
			}
			final Boolean billToDefault = contact.getBillToDefault();
			if (!hasBillToDefault && billToDefault != null && billToDefault)
			{
				hasBillToDefault = true;
			}
			final Boolean shipToDefault = contact.getShipToDefault();
			if (!hasShipToDefault && shipToDefault != null && shipToDefault)
			{
				hasShipToDefault = true;
			}
			final Boolean purchaseDefault = contact.getPurchaseDefault();
			if (!hasPurchaseDefault && purchaseDefault != null && purchaseDefault)
			{
				hasPurchaseDefault = true;
			}
			final Boolean salesDefault = contact.getSalesDefault();
			if (!hasSalesDefault && salesDefault != null && salesDefault)
			{
				hasSalesDefault = true;
			}
		}
		if (hasDefaultContact)
		{
			shortTermIndex.resetDefaultContactFlags();
		}
		if (hasBillToDefault)
		{
			shortTermIndex.resetBillToDefaultFlags();
		}
		if (hasShipToDefault)
		{
			shortTermIndex.resetShipToDefaultFlags();
		}
		if (hasPurchaseDefault)
		{
			shortTermIndex.resetPurchaseDefaultFlags();
		}
		if (hasSalesDefault)
		{
			shortTermIndex.resetSalesDefaultFlags();
		}
	}

	private JsonResponseUpsertItemBuilder syncJsonContact(
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestContactUpsertItem jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermContactIndex shortTermIndex)
	{
		final ExternalIdentifier contactIdentifier = ExternalIdentifier.of(jsonContact.getContactIdentifier());
		BPartnerContact existingContact = null;

		if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(contactIdentifier.getType()))
		{
			final Optional<MetasfreshId> metasfreshId =
					jsonRetrieverService.resolveExternalReference(orgId, contactIdentifier, ExternalUserReferenceType.USER_ID);
			if (metasfreshId.isPresent())
			{
				existingContact = shortTermIndex.extract(metasfreshId.get());
			}
		}
		else
		{
			existingContact = shortTermIndex.extract(contactIdentifier);
		}

		final JsonResponseUpsertItemBuilder result = JsonResponseUpsertItem.builder()
				.identifier(jsonContact.getContactIdentifier());

		final SyncOutcome syncOutcome;
		final BPartnerContact contact;

		if (existingContact != null)
		{
			contact = existingContact;
			syncOutcome = parentSyncAdvise.getIfExists().isUpdate() ? SyncOutcome.UPDATED : SyncOutcome.NOTHING_DONE;
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(jsonContact.getContactIdentifier()).parentResource(jsonContact).build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			else if (METASFRESH_ID.equals(contactIdentifier.getType()))
			{
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(jsonContact.getContactIdentifier()).parentResource(jsonContact)
						.detail(TranslatableStrings.constant("With this type, only updates are allowed."))
						.build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			contact = shortTermIndex.newContact(contactIdentifier);
			syncOutcome = SyncOutcome.CREATED;
		}

		contact.addHandle(contactIdentifier.getRawValue()); // always add the handle; we'll need it later, even if the contact existed and was not updated

		result.syncOutcome(syncOutcome);
		if (!Objects.equals(SyncOutcome.NOTHING_DONE, syncOutcome))
		{
			syncJsonToContact(jsonContact.getContact(), contact);
		}
		return result;
	}

	private void syncJsonToContact(
			@NonNull final JsonRequestContact jsonBPartnerContact,
			@NonNull final BPartnerContact contact)
	{
		// active
		if (jsonBPartnerContact.isActiveSet())
		{
			if (jsonBPartnerContact.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				contact.setActive(jsonBPartnerContact.getActive());
			}
		}

		// email
		if (jsonBPartnerContact.isEmailSet())
		{
			contact.setEmail(StringUtils.trim(jsonBPartnerContact.getEmail()));
		}

		// firstName
		if (jsonBPartnerContact.isFirstNameSet())
		{
			contact.setFirstName(StringUtils.trim(jsonBPartnerContact.getFirstName()));
		}

		// lastName
		if (jsonBPartnerContact.isLastNameSet())
		{
			contact.setLastName(StringUtils.trim(jsonBPartnerContact.getLastName()));
		}

		// metasfreshBPartnerId - never updated;

		// metasfreshId - never updated;

		// name
		if (jsonBPartnerContact.isNameSet())
		{
			contact.setName(StringUtils.trim(jsonBPartnerContact.getName()));
		}

		// value
		if (jsonBPartnerContact.isCodeSet())
		{
			contact.setValue(StringUtils.trim(jsonBPartnerContact.getCode()));
		}

		// description
		if (jsonBPartnerContact.isDescriptionSet())
		{
			contact.setDescription(StringUtils.trim(jsonBPartnerContact.getDescription()));
		}

		// phone
		if (jsonBPartnerContact.isPhoneSet())
		{
			contact.setPhone(StringUtils.trim(jsonBPartnerContact.getPhone()));
		}

		// fax
		if (jsonBPartnerContact.isFaxSet())
		{
			contact.setFax(StringUtils.trim(jsonBPartnerContact.getFax()));
		}

		// mobilePhone
		if (jsonBPartnerContact.isMobilePhoneSet())
		{
			contact.setMobilePhone(StringUtils.trim(jsonBPartnerContact.getMobilePhone()));
		}

		// newsletter
		if (jsonBPartnerContact.isNewsletterSet())
		{
			if (jsonBPartnerContact.getNewsletter() == null)
			{
				logger.debug("Ignoring boolean property \"newsLetter\" : null ");
			}
			else
			{
				contact.setNewsletter(jsonBPartnerContact.getNewsletter());
			}
		}

		// subjectMatter
		if (jsonBPartnerContact.isSubjectMatterSet())
		{
			if (jsonBPartnerContact.getSubjectMatter() == null)
			{
				logger.debug("Ignoring boolean property \"subjectMatter\" : null ");
			}
			else
			{
				contact.setSubjectMatterContact(jsonBPartnerContact.getSubjectMatter());
			}
		}

		if (jsonBPartnerContact.isBirthdaySet())
		{
			contact.setBirthday(jsonBPartnerContact.getBirthday());
		}

		if (jsonBPartnerContact.isInvoiceEmailEnabledSet())
		{
			if (jsonBPartnerContact.getInvoiceEmailEnabled() == null)
			{
				logger.debug("Ignoring boolean property \"isInvoiceEmailEnabled\" : null ");
			}
			else
			{
				contact.setInvoiceEmailEnabled(jsonBPartnerContact.getInvoiceEmailEnabled());
			}
		}

		// title
		if (jsonBPartnerContact.isTitleSet())
		{
			contact.setTitle(StringUtils.trim(jsonBPartnerContact.getTitle()));
		}

		// phone2
		if (jsonBPartnerContact.isPhone2Set())
		{
			contact.setPhone2(StringUtils.trim(jsonBPartnerContact.getPhone2()));
		}

		final BPartnerContactType bpartnerContactType = syncJsonToContactType(jsonBPartnerContact);
		contact.setContactType(bpartnerContactType);
	}

	private BPartnerContactType syncJsonToContactType(@NonNull final JsonRequestContact jsonBPartnerContact)
	{
		final BPartnerContactTypeBuilder contactType = BPartnerContactType.builder();

		if (jsonBPartnerContact.isDefaultContactSet())
		{
			if (jsonBPartnerContact.getDefaultContact() == null)
			{
				logger.debug("Ignoring boolean property \"defaultContact\" : null ");
			}
			else
			{
				contactType.defaultContact(jsonBPartnerContact.getDefaultContact());
			}
		}
		if (jsonBPartnerContact.isShipToDefaultSet())
		{
			if (jsonBPartnerContact.getShipToDefault() == null)
			{
				logger.debug("Ignoring boolean property \"shipToDefault\" : null ");
			}
			else
			{
				contactType.shipToDefault(jsonBPartnerContact.getShipToDefault());
			}
		}
		if (jsonBPartnerContact.isBillToDefaultSet())
		{
			if (jsonBPartnerContact.getBillToDefault() == null)
			{
				logger.debug("Ignoring boolean property \"billToDefault\" : null ");
			}
			else
			{
				contactType.billToDefault(jsonBPartnerContact.getBillToDefault());
			}
		}
		if (jsonBPartnerContact.isPurchaseSet())
		{
			if (jsonBPartnerContact.getPurchase() == null)
			{
				logger.debug("Ignoring boolean property \"purchase\" : null ");
			}
			else
			{
				contactType.purchase(jsonBPartnerContact.getPurchase());
			}
		}
		if (jsonBPartnerContact.isPurchaseDefaultSet())
		{
			if (jsonBPartnerContact.getPurchaseDefault() == null)
			{
				logger.debug("Ignoring boolean property \"purchaseDefault\" : null ");
			}
			else
			{
				contactType.purchaseDefault(jsonBPartnerContact.getPurchaseDefault());
			}
		}
		if (jsonBPartnerContact.isSalesSet())
		{
			if (jsonBPartnerContact.getSales() == null)
			{
				logger.debug("Ignoring boolean property \"sales\" : null ");
			}
			else
			{
				contactType.sales(jsonBPartnerContact.getSales());
			}
		}
		if (jsonBPartnerContact.isSalesDefaultSet())
		{
			if (jsonBPartnerContact.getSalesDefault() == null)
			{
				logger.debug("Ignoring boolean property \"salesDefault\" : null ");
			}
			else
			{
				contactType.salesDefault(jsonBPartnerContact.getSalesDefault());
			}
		}

		return contactType.build();
	}

	private ImmutableMap<String, JsonResponseUpsertItemBuilder> syncJsonToLocations(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		final JsonRequestLocationUpsert locations = jsonBPartnerComposite.getLocationsNotNull();

		final SyncAdvise locationsSyncAdvise = coalesceNotNull(locations.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			result.put(
					locationRequestItem.getLocationIdentifier(),
					syncJsonLocation(bpartnerComposite.getOrgId(), locationRequestItem, locationsSyncAdvise, shortTermIndex));
		}

		// if we have location with isBillToDefault or isShipToDefault, then make sure that none of the previously existing locations also have such a default flag
		final boolean mustTakeCareOfUnusedLocationDefaultFlags = locationsSyncAdvise.getIfNotExists().isCreate() || locationsSyncAdvise.getIfExists().isUpdate();
		if (mustTakeCareOfUnusedLocationDefaultFlags)
		{
			resetUnusedLocationDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);
		}
		return result.build();
	}

	private ImmutableMap<String, JsonResponseUpsertItemBuilder> syncJsonToBankAccounts(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermBankAccountIndex shortTermIndex = new ShortTermBankAccountIndex(bpartnerComposite);

		final JsonRequestBankAccountsUpsert bankAccounts = jsonBPartnerComposite.getBankAccountsNotNull();
		final SyncAdvise bankAccountsSyncAdvise = coalesceNotNull(bankAccounts.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestBankAccountUpsertItem bankAccountRequestItem : bankAccounts.getRequestItems())
		{
			result.put(
					bankAccountRequestItem.getIban(),
					syncJsonBankAccount(bankAccountRequestItem, bankAccountsSyncAdvise, shortTermIndex));
		}

		return result.build();
	}

	private JsonResponseUpsertItemBuilder syncJsonBankAccount(
			@NonNull final JsonRequestBankAccountUpsertItem jsonBankAccount,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermBankAccountIndex shortTermIndex)
	{
		final SyncAdvise effectiveSyncAdvise = CoalesceUtil.coalesceNotNull(jsonBankAccount.getSyncAdvise(), parentSyncAdvise);

		final BPartnerBankAccount existingBankAccount = jsonRetrieverService
				.resolveBankAccountId(ExternalIdentifier.of(jsonBankAccount.getIdentifier()), shortTermIndex.getBpartnerComposite())
				.map(shortTermIndex::extract)
				.orElseGet(() -> shortTermIndex.extractOrNull(jsonBankAccount.getIban()));

		final JsonResponseUpsertItemBuilder resultBuilder = JsonResponseUpsertItem.builder()
				.identifier(jsonBankAccount.getIdentifier());

		final SyncOutcome syncOutcome;
		final BPartnerBankAccount bankAccount;
		if (existingBankAccount != null)
		{
			bankAccount = existingBankAccount;
			shortTermIndex.remove(existingBankAccount.getId());

			syncOutcome = effectiveSyncAdvise.getIfExists().isUpdate() ? SyncOutcome.UPDATED : SyncOutcome.NOTHING_DONE;
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder().resourceName("bankAccount")
						.resourceIdentifier(jsonBankAccount.getIdentifier())
						.parentResource(jsonBankAccount)
						.build()
						.setParameter("syncAdvise", effectiveSyncAdvise);
			}

			final CurrencyId currencyId = extractCurrencyIdOrNull(jsonBankAccount);
			if (currencyId == null)
			{
				throw MissingResourceException.builder()
						.resourceName("bankAccount.currencyId")
						.resourceIdentifier(jsonBankAccount.getCurrencyCode())
						.parentResource(jsonBankAccount)
						.build()
						.setParameter("syncAdvise", effectiveSyncAdvise);
			}

			bankAccount = shortTermIndex.newBankAccount(jsonBankAccount.getIban(), currencyId);
			syncOutcome = SyncOutcome.CREATED;
		}

		if (syncOutcome != SyncOutcome.NOTHING_DONE)
		{
			syncJsonToBankAccount(jsonBankAccount, bankAccount);
		}

		return resultBuilder.syncOutcome(syncOutcome);
	}

	private void syncJsonToBankAccount(
			@NonNull final JsonRequestBankAccountUpsertItem jsonBankAccount,
			@NonNull final BPartnerBankAccount bankAccount)
	{
		bankAccount.setIban(jsonBankAccount.getIban());

		// active
		if (jsonBankAccount.isActiveSet())
		{
			bankAccount.setActive(jsonBankAccount.getIsActive());
		}

		// isDefault
		if (jsonBankAccount.isDefaultSet())
		{
			bankAccount.setDefault(Boolean.TRUE.equals(jsonBankAccount.getIsDefault()));
		}


		// currency
		if (jsonBankAccount.isCurrencyCodeSet())
		{
			final CurrencyId currencyId = extractCurrencyIdOrNull(jsonBankAccount);
			if (currencyId == null)
			{
				throw new AdempiereException("CurrencyId cannot be set to null!")
						.appendParametersToMessage()
						.setParameter("BPBankAccountIdentifier", jsonBankAccount.getIdentifier());
			}

			bankAccount.setCurrencyId(currencyId);
		}

		// qrIban
		if (jsonBankAccount.isQrIbanSet())
		{
			bankAccount.setQrIban(StringUtils.trim(jsonBankAccount.getQrIban()));
		}

		// name
		if (jsonBankAccount.isNameSet())
		{
			bankAccount.setName(StringUtils.trim(jsonBankAccount.getName()));
		}
	}

	@Nullable
	private CurrencyId extractCurrencyIdOrNull(final JsonRequestBankAccountUpsertItem jsonBankAccount)
	{
		if (isBlank(jsonBankAccount.getCurrencyCode()))
		{
			return null;
		}

		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(jsonBankAccount.getCurrencyCode().trim());
		return currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
	}

	/**
	 * If the json locations have default flags set, then this method unsets all corresponding default flags of the shortTermIndex's {@link BPartnerLocation}s.
	 * Goal: make sure that a former default-flagged location is not left untouched, to avoid multiple locations with the same default flag set to true.
	 */
	private void resetUnusedLocationDefaultFlagsIfNeeded(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final ShortTermLocationIndex shortTermIndex)
	{
		boolean hasBillToDefault = false;
		boolean hasShipToDefault = false;

		final List<JsonRequestLocationUpsertItem> locationRequestItems = jsonBPartnerComposite
				.getLocationsNotNull()
				.getRequestItems();
		for (final JsonRequestLocationUpsertItem locationRequestItem : locationRequestItems)
		{
			final JsonRequestLocation location = locationRequestItem.getLocation();
			final Boolean billToDefault = location.getBillToDefault();
			if (!hasBillToDefault && billToDefault != null && billToDefault)
			{
				hasBillToDefault = true;
			}
			final Boolean shipToDefault = location.getShipToDefault();
			if (!hasShipToDefault && shipToDefault != null && shipToDefault)
			{
				hasShipToDefault = true;
			}
		}
		if (hasBillToDefault)
		{
			shortTermIndex.resetBillToDefaultFlags();
		}
		if (hasShipToDefault)
		{
			shortTermIndex.resetShipToDefaultFlags();
		}
	}

	private JsonResponseUpsertItemBuilder syncJsonLocation(
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestLocationUpsertItem locationUpsertItem,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermLocationIndex shortTermIndex)
	{
		final ExternalIdentifier locationIdentifier = ExternalIdentifier.of(locationUpsertItem.getLocationIdentifier());

		BPartnerLocation existingLocation = null;

		if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(locationIdentifier.getType()))
		{
			final Optional<MetasfreshId> metasfreshId =
					jsonRetrieverService.resolveExternalReference(orgId, locationIdentifier, BPLocationExternalReferenceType.BPARTNER_LOCATION);
			if (metasfreshId.isPresent())
			{
				existingLocation = shortTermIndex.extractAndMarkUsed(metasfreshId.get());
			}
		}
		else
		{
			existingLocation = shortTermIndex.extractAndMarkUsed(locationIdentifier);
		}

		final JsonResponseUpsertItemBuilder resultBuilder = JsonResponseUpsertItem.builder()
				.identifier(locationUpsertItem.getLocationIdentifier());

		final SyncOutcome syncOutcome;
		final BPartnerLocation location;
		if (existingLocation != null)
		{
			location = existingLocation;
			syncOutcome = parentSyncAdvise.getIfExists().isUpdate() ? SyncOutcome.UPDATED : SyncOutcome.NOTHING_DONE;
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("location")
						.resourceIdentifier(locationUpsertItem.getLocationIdentifier())
						.parentResource(locationUpsertItem)
						.detail(TranslatableStrings.constant("Type of locationlocationIdentifier=" + locationIdentifier.getType()))
						.build()
						.setParameter("effectiveSyncAdvise", parentSyncAdvise);
			}
			else if (METASFRESH_ID.equals(locationIdentifier.getType()))
			{
				throw MissingResourceException.builder()
						.resourceName("location")
						.resourceIdentifier(locationUpsertItem.getLocationIdentifier())
						.parentResource(locationUpsertItem)
						.detail(TranslatableStrings.constant("Type of locationlocationIdentifier=" + locationIdentifier.getType() + "; with this identifier-type, only updates are allowed."))
						.build()
						.setParameter("effectiveSyncAdvise", parentSyncAdvise);
			}
			location = shortTermIndex.newLocation(locationIdentifier);
			syncOutcome = SyncOutcome.CREATED;
		}

		location.addHandle(locationUpsertItem.getLocationIdentifier());

		resultBuilder.syncOutcome(syncOutcome);
		if (!Objects.equals(SyncOutcome.NOTHING_DONE, syncOutcome))
		{
			syncJsonToLocation(locationUpsertItem.getLocation(), location);
		}
		return resultBuilder;
	}

	private void syncJsonToLocation(
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final BPartnerLocation location)
	{
		// active
		if (jsonBPartnerLocation.isActiveSet())
		{
			if (jsonBPartnerLocation.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				location.setActive(jsonBPartnerLocation.getActive());
			}
		}

		// name
		if (jsonBPartnerLocation.isNameSet())
		{
			final String name = jsonBPartnerLocation.getName();
			if (Check.isEmpty(name, true))
			{
				location.setName(BPARTNER_LOCATION_NAME_DEFAULT);
			}
			else
			{
				location.setName(StringUtils.trim(name));
			}
		}

		// bpartnerName
		if (jsonBPartnerLocation.isBpartnerNameSet())
		{
			location.setBpartnerName(StringUtils.trim(jsonBPartnerLocation.getBpartnerName()));
		}

		// address1
		if (jsonBPartnerLocation.isAddress1Set())
		{
			location.setAddress1(StringUtils.trim(jsonBPartnerLocation.getAddress1()));
		}

		// address2
		if (jsonBPartnerLocation.isAddress2Set())
		{
			location.setAddress2(StringUtils.trim(jsonBPartnerLocation.getAddress2()));
		}

		// address3
		if (jsonBPartnerLocation.isAddress3Set())
		{
			location.setAddress3(StringUtils.trim(jsonBPartnerLocation.getAddress3()));
		}

		// address4
		if (jsonBPartnerLocation.isAddress4Set())
		{
			location.setAddress4(StringUtils.trim(jsonBPartnerLocation.getAddress4()));
		}

		// city
		if (jsonBPartnerLocation.isCitySet())
		{
			location.setCity(StringUtils.trim(jsonBPartnerLocation.getCity()));
		}

		// countryCode
		if (jsonBPartnerLocation.isCountryCodeSet())
		{
			location.setCountryCode(StringUtils.trim(jsonBPartnerLocation.getCountryCode()));
		}

		// district
		if (jsonBPartnerLocation.isDistrictSet())
		{
			location.setDistrict(StringUtils.trim(jsonBPartnerLocation.getDistrict()));
		}

		// gln
		if (jsonBPartnerLocation.isGlnSet())
		{
			final GLN gln = GLN.ofNullableString(jsonBPartnerLocation.getGln());
			location.setGln(gln);
		}

		// poBox
		if (jsonBPartnerLocation.isPoBoxSet())
		{
			location.setPoBox(StringUtils.trim(jsonBPartnerLocation.getPoBox()));
		}

		// postal
		if (jsonBPartnerLocation.isPostalSet())
		{
			location.setPostal(StringUtils.trim(jsonBPartnerLocation.getPostal()));
		}

		// region
		if (jsonBPartnerLocation.isRegionSet())
		{
			location.setRegion(StringUtils.trim(jsonBPartnerLocation.getRegion()));
		}

		// ephemeral
		if (jsonBPartnerLocation.isEphemeralSet())
		{
			location.setEphemeral(jsonBPartnerLocation.isEphemeral());
		}

		// bpartnerName
		if (jsonBPartnerLocation.isBpartnerNameSet())
		{
			location.setBpartnerName(jsonBPartnerLocation.getBpartnerName());
		}

		// email
		if (jsonBPartnerLocation.isEmailSet())
		{
			location.setEmail(jsonBPartnerLocation.getEmail());
		}

		// phone
		if (jsonBPartnerLocation.isPhoneSet())
		{
			location.setPhone(jsonBPartnerLocation.getPhone());
		}

		if (jsonBPartnerLocation.isVisitorsAddressSet())
		{
			location.setVisitorsAddress(Boolean.TRUE.equals(jsonBPartnerLocation.getVisitorsAddress()));
		}

		if (jsonBPartnerLocation.isHandoverLocationSet())
		{
			location.setHandOverLocation(Boolean.TRUE.equals(jsonBPartnerLocation.getHandoverLocation()));
		}

		if (jsonBPartnerLocation.isRemitToAddressSet())
		{
			location.setRemitTo(Boolean.TRUE.equals(jsonBPartnerLocation.getRemitTo()));
		}

		if (jsonBPartnerLocation.isReplicationLookupDefaultSet())
		{
			location.setReplicationLookupDefault(Boolean.TRUE.equals(jsonBPartnerLocation.getReplicationLookupDefault()));
		}

		// VAT ID
		if (jsonBPartnerLocation.isVatIdSet())
		{
			location.setVatTaxId(StringUtils.trim(jsonBPartnerLocation.getVatId()));
		}

		if (jsonBPartnerLocation.isSapPaymentMethodSet())
		{
			location.setSapPaymentMethod(jsonBPartnerLocation.getSapPaymentMethod());
		}

		if (jsonBPartnerLocation.isSapBPartnerCodeSet())
		{
			location.setSapBPartnerCode(jsonBPartnerLocation.getSapBPartnerCode());
		}

		final BPartnerLocationType locationType = syncJsonToLocationType(jsonBPartnerLocation);
		location.setLocationType(locationType);
	}

	private BPartnerLocationType syncJsonToLocationType(@NonNull final JsonRequestLocation jsonBPartnerLocation)
	{
		final BPartnerLocationTypeBuilder locationType = BPartnerLocationType.builder();

		if (jsonBPartnerLocation.isBillToSet())
		{
			if (jsonBPartnerLocation.getBillTo() == null)
			{
				logger.debug("Ignoring boolean property \"billTo\" : null ");
			}
			else
			{
				locationType.billTo(jsonBPartnerLocation.getBillTo());
			}
		}
		if (jsonBPartnerLocation.isBillToDefaultSet())
		{
			if (jsonBPartnerLocation.getBillToDefault() == null)
			{
				logger.debug("Ignoring boolean property \"billToDefault\" : null ");
			}
			else
			{
				locationType.billToDefault(jsonBPartnerLocation.getBillToDefault());
			}
		}
		if (jsonBPartnerLocation.isShipToSet())
		{
			if (jsonBPartnerLocation.getShipTo() == null)
			{
				logger.debug("Ignoring boolean property \"shipTo\" : null ");
			}
			else
			{
				locationType.shipTo(jsonBPartnerLocation.getShipTo());
			}
		}
		if (jsonBPartnerLocation.isShipToDefaultSet())
		{
			if (jsonBPartnerLocation.getShipToDefault() == null)
			{
				logger.debug("Ignoring boolean property \"shipToDefault\" : null ");
			}
			else
			{
				locationType.shipToDefault(jsonBPartnerLocation.getShipToDefault());
			}
		}

		return locationType.build();
	}

	@NonNull
	private static Optional<JsonRequestExternalReferenceUpsert> mapToJsonRequestExternalReferenceUpsert(
			@NonNull final JsonResponseUpsertItem responseUpsertItem,
			@NonNull final Map<String, JsonExternalReferenceItem> externalRef2item)
	{
		if (SyncOutcome.NOTHING_DONE.equals(responseUpsertItem.getSyncOutcome()))
		{
			return Optional.empty();
		}

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(responseUpsertItem.getIdentifier());
		if (!externalIdentifier.getType().equals(EXTERNAL_REFERENCE))
		{
			return Optional.empty();
		}

		final JsonExternalReferenceItem externalReferenceItem = externalRef2item.get(externalIdentifier.asExternalValueAndSystem().getValue());

		if (externalReferenceItem == null)
		{
			throw new AdempiereException("No JsonExternalReferenceItem found for externalIdentifier=" + externalIdentifier.getRawValue())
					.appendParametersToMessage()
					.setParameter("JsonResponseUpsertItem", responseUpsertItem);
		}

		final JsonExternalReferenceItem externalReferenceItemWithMetasfreshId = externalReferenceItem
				.withMetasfreshId(responseUpsertItem.getMetasfreshId());

		final JsonRequestExternalReferenceUpsert jsonRequestExternalReferenceUpsert = JsonRequestExternalReferenceUpsert
				.builder()
				.systemName(JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem()))
				.externalReferenceItem(externalReferenceItemWithMetasfreshId)
				.build();

		return Optional.of(jsonRequestExternalReferenceUpsert);
	}

	private void handleExternalReferenceRecords(
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem result,
			@Nullable final String orgCode)
	{
		final Set<JsonRequestExternalReferenceUpsert> externalReferenceCreateReqs = new HashSet<>();

		JsonExternalReferenceHelper.getExternalReferenceItem(requestItem)
				.filter(externalRefItem -> externalRefItem.getExternalReference() != null)
				.flatMap(referenceItem -> mapToJsonRequestExternalReferenceUpsert(result.getResponseBPartnerItem(),
																				  ImmutableMap.of(referenceItem.getExternalReference(), referenceItem)))
				.ifPresent(externalReferenceCreateReqs::add);

		final List<JsonResponseUpsertItem> bPartnerLocationsResult = result.getResponseLocationItems();
		if (!CollectionUtils.isEmpty(bPartnerLocationsResult))
		{
			final Map<String, JsonExternalReferenceItem> locationIdentifier2ExternalReferenceItem = requestItem
					.getBpartnerComposite()
					.getLocationsNotNull()
					.getRequestItems()
					.stream()
					.map(JsonExternalReferenceHelper::getExternalReferenceItem)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(externalRefItem -> externalRefItem.getExternalReference() != null)
					.collect(Collectors.toMap(
							JsonExternalReferenceItem::getExternalReference,
							Function.identity()));

			externalReferenceCreateReqs.addAll(bPartnerLocationsResult
													   .stream()
													   .map(bPartnerLocationResult -> mapToJsonRequestExternalReferenceUpsert(bPartnerLocationResult,
																															  locationIdentifier2ExternalReferenceItem))
													   .filter(Optional::isPresent)
													   .map(Optional::get)
													   .collect(Collectors.toSet()));
		}

		final List<JsonResponseUpsertItem> bPartnerContactItems = result.getResponseContactItems();
		if (!CollectionUtils.isEmpty(bPartnerContactItems))
		{

			final Map<String, JsonExternalReferenceItem> contactIdentifier2ExternalReferenceItem = requestItem
					.getBpartnerComposite()
					.getContactsNotNull()
					.getRequestItems()
					.stream()
					.map(JsonExternalReferenceHelper::getExternalReferenceItem)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(externalRefItem -> externalRefItem.getExternalReference() != null)
					.collect(Collectors.toMap(
							JsonExternalReferenceItem::getExternalReference,
							Function.identity()));

			externalReferenceCreateReqs.addAll(bPartnerContactItems
													   .stream()
													   .map(bpContactResult -> mapToJsonRequestExternalReferenceUpsert(bpContactResult,
																													   contactIdentifier2ExternalReferenceItem))
													   .filter(Optional::isPresent)
													   .map(Optional::get)
													   .collect(Collectors.toSet()));
		}

		externalReferenceCreateReqs.addAll(getExternalReferenceUpsertRequestList(result.getResponseBankAccountItems(),
																				 requestItem.getBpartnerComposite().getBankAccountsNotNull()));

		for (final JsonRequestExternalReferenceUpsert request : externalReferenceCreateReqs)
		{
			externalReferenceRestControllerService.performUpsert(request, orgCode);
		}
	}

	private void handleAlbertaInfo(
			@NonNull final OrgId orgId,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem result)
	{
		final JsonRequestComposite requestBPartnerComposite = requestItem.getBpartnerComposite();

		final JsonCompositeAlbertaBPartner compositeAlbertaBPartner = requestBPartnerComposite.getCompositeAlbertaBPartner();

		if (compositeAlbertaBPartner != null)
		{
			final BPartnerId bPartnerId = BPartnerId.ofRepoId(result.getResponseBPartnerItem().getMetasfreshId().getValue());
			albertaBPartnerCompositeService.upsertAlbertaCompositeInfo(orgId, bPartnerId, compositeAlbertaBPartner, syncAdvise);
		}

		if (!requestBPartnerComposite.getContactsNotNull().getRequestItems().isEmpty()
				&& !Check.isEmpty(result.getResponseContactItems()))
		{
			final Map<String, JsonMetasfreshId> contactIdentifierToMetasfreshId = result.getResponseContactItems()
					.stream()
					.collect(ImmutableMap.toImmutableMap(JsonResponseUpsertItem::getIdentifier, JsonResponseUpsertItem::getMetasfreshId));

			final SyncAdvise effectiveSyncAdvise = CoalesceUtil.coalesceNotNull(requestBPartnerComposite.getContactsNotNull().getSyncAdvise(), syncAdvise);

			requestItem.getBpartnerComposite().getContactsNotNull().getRequestItems()
					.stream()
					.filter(contactRequestItem -> contactRequestItem.getJsonAlbertaContact() != null)
					.forEach(contactRequestItem -> {
						final JsonMetasfreshId contactMetasfreshId = contactIdentifierToMetasfreshId.get(contactRequestItem.getContactIdentifier());

						if (contactMetasfreshId == null)
						{
							throw MissingResourceException.builder()
									.resourceName("BPartnerContact")
									.resourceIdentifier(contactRequestItem.getContactIdentifier())
									.parentResource(requestBPartnerComposite)
									.build();
						}

						albertaBPartnerCompositeService.upsertAlbertaContact(UserId.ofRepoId(contactMetasfreshId.getValue()),
																			 contactRequestItem.getJsonAlbertaContact(),
																			 effectiveSyncAdvise);
					});
		}
	}

	@NonNull
	private List<JsonResponseUpsertItem> syncJsonToCreditLimits(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final OrgId orgId)
	{
		final JsonRequestCreditLimitUpsert creditLimits = jsonBPartnerComposite.getCreditLimitsNotNull();

		final SyncAdvise creditLimitsSyncAdvise = coalesceNotNull(creditLimits.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableList.Builder<JsonResponseUpsertItem> responseItemsForExistingRecords = ImmutableList.builder();

		for (final JsonRequestCreditLimitUpsertItem creditLimitRequestItem : creditLimits.getRequestItems())
		{
			final SyncOutcome syncOutcome = syncJsonCreditLimit(bpartnerComposite, creditLimitRequestItem, creditLimitsSyncAdvise, orgId);

			if (syncOutcome != SyncOutcome.CREATED)
			{
				Check.assumeNotNull(creditLimitRequestItem.getCreditLimitId(),
									"Since syncOutcome is not CREATED, the creditLimitId must've been set on creditLimitRequestItem!");

				responseItemsForExistingRecords.add(JsonResponseUpsertItem.builder()
															.identifier(String.valueOf(creditLimitRequestItem.getCreditLimitId().getValue()))
															.metasfreshId(creditLimitRequestItem.getCreditLimitId())
															.syncOutcome(syncOutcome)
															.build());
			}
		}

		return responseItemsForExistingRecords.build();
	}

	@NonNull
	private SyncOutcome syncJsonCreditLimit(
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final JsonRequestCreditLimitUpsertItem creditLimitUpsertItem,
			@NonNull final SyncAdvise effectiveSyncAdvise,
			@NonNull final OrgId orgId)
	{
		BPartnerCreditLimit existingCreditLimit = null;
		if (creditLimitUpsertItem.getCreditLimitId() != null)
		{
			existingCreditLimit = bPartnerCreditLimitRepository.getById(BPartnerCreditLimitId.ofRepoId(creditLimitUpsertItem.getCreditLimitId().getValue()));
		}

		final SyncOutcome syncOutcome;
		final BPartnerCreditLimit.BPartnerCreditLimitBuilder creditLimitBuilder;
		if (existingCreditLimit != null)
		{
			creditLimitBuilder = existingCreditLimit.toBuilder();
			syncOutcome = effectiveSyncAdvise.getIfExists().isUpdate() ? SyncOutcome.UPDATED : SyncOutcome.NOTHING_DONE;
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("creditLimit")
						.parentResource(bpartnerComposite)
						.build()
						.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
			}

			creditLimitBuilder = BPartnerCreditLimit.builder();

			syncOutcome = SyncOutcome.CREATED;
		}

		if (!Objects.equals(SyncOutcome.NOTHING_DONE, syncOutcome))
		{
			syncJsonToCreditLimit(creditLimitUpsertItem, creditLimitBuilder, orgId);
		}

		bpartnerComposite
				.getCreditLimits()
				.add(creditLimitBuilder.build());

		return syncOutcome;
	}

	private void syncJsonToCreditLimit(
			@NonNull final JsonRequestCreditLimitUpsertItem jsonBPartnerCreditLimit,
			@NonNull final BPartnerCreditLimit.BPartnerCreditLimitBuilder creditLimitBuilder,
			@NonNull final OrgId orgId)
	{
		// active
		if (jsonBPartnerCreditLimit.isActiveSet())
		{
			if (jsonBPartnerCreditLimit.getActive() == null)
			{
				logger.debug("Ignoring boolean property \"active\" : null ");
			}
			else
			{
				creditLimitBuilder.active(jsonBPartnerCreditLimit.getActive());
			}
		}

		// amount & currency
		if (jsonBPartnerCreditLimit.isAmountSet())
		{
			if (jsonBPartnerCreditLimit.getAmount() == null)
			{
				throw new AdempiereException("JsonBPartnerCreditLimit.money cannot be null!");
			}

			final ClientId clientId = orgDAO.getClientIdByOrgId(orgId);

			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(clientId, orgId);

			final Money amountInOrgCurrency = convertToOrgCurrency(jsonBPartnerCreditLimit.getAmount(), clientAndOrgId);

			creditLimitBuilder.amount(amountInOrgCurrency);
		}

		// dataFrom
		if (jsonBPartnerCreditLimit.isDateFromSet())
		{
			Instant dateFrom = null;
			if (jsonBPartnerCreditLimit.getDateFrom() != null)
			{
				final ZoneId timeZone = orgDAO.getTimeZone(orgId);
				dateFrom = jsonBPartnerCreditLimit.getDateFrom().atStartOfDay(timeZone).toInstant();
			}

			creditLimitBuilder.dateFrom(dateFrom);
		}

		// creditLimitType
		if (jsonBPartnerCreditLimit.isTypeSet())
		{
			if (jsonBPartnerCreditLimit.getType() == null)
			{
				throw new AdempiereException("JsonBPartnerCreditLimit.type cannot be null!");
			}

			final I_C_CreditLimit_Type creditLimitType = bPartnerCreditLimitRepository.getCreditLimitTypeByName(jsonBPartnerCreditLimit.getType());
			creditLimitBuilder.creditLimitTypeId(CreditLimitTypeId.ofRepoId(creditLimitType.getC_CreditLimit_Type_ID()));
		}

		// processed
		if (jsonBPartnerCreditLimit.isProcessedSet())
		{
			if (jsonBPartnerCreditLimit.getProcessed() == null)
			{
				logger.debug("Ignoring boolean property \"processed\" : null ");
			}
			else
			{
				creditLimitBuilder.processed(jsonBPartnerCreditLimit.getProcessed());
			}
		}

		// approvedBy
		if (jsonBPartnerCreditLimit.isApprovedBySet())
		{
			final UserId approvedById = UserId.ofRepoIdOrNullIfSystem(JsonMetasfreshId.toValueInt(jsonBPartnerCreditLimit.getApprovedBy()));

			creditLimitBuilder.approvedBy(approvedById);
		}
	}

	@NonNull
	private Money convertToOrgCurrency(@NonNull final JsonMoney jsonMoney, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final CurrencyConversionContext currencyConversionContext =
				currencyBL.createCurrencyConversionContext(Instant.now(),
														   clientAndOrgId.getClientId(),
														   clientAndOrgId.getOrgId());

		final Money money = convertJsonToMoney(jsonMoney);

		return currencyBL.convertToBase(currencyConversionContext, money);
	}

	@NonNull
	private Money convertJsonToMoney(@NonNull final JsonMoney jsonMoney)
	{
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(jsonMoney.getCurrencyCode().trim());

		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
		return Money.of(jsonMoney.getAmount(), currencyId);
	}

	@NonNull
	private static ImmutableSet<JsonRequestExternalReferenceUpsert> getExternalReferenceUpsertRequestList(
			@Nullable final List<JsonResponseUpsertItem> bankAccountItems,
			@NonNull final JsonRequestBankAccountsUpsert bankAccountUpsertRequest)
	{
		if (CollectionUtils.isEmpty(bankAccountItems))
		{
			return ImmutableSet.of();
		}

		final Map<String, JsonExternalReferenceItem> identifier2ExternalReferenceItem = bankAccountUpsertRequest
				.getRequestItems()
				.stream()
				.map(JsonExternalReferenceHelper::getExternalReferenceItem)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(externalRefItem -> externalRefItem.getExternalReference() != null)
				.collect(Collectors.toMap(
						JsonExternalReferenceItem::getExternalReference,
						Function.identity()));

		return bankAccountItems
				.stream()
				.map(bankAccountResponseItem -> mapToJsonRequestExternalReferenceUpsert(bankAccountResponseItem,
																						identifier2ExternalReferenceItem))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableSet.toImmutableSet());
	}
}
