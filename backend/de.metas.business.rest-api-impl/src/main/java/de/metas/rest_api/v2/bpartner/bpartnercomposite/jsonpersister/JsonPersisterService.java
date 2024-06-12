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
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerContactQuery.BPartnerContactQueryBuilder;
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
import de.metas.common.externalreference.v2.JsonSingleExternalReferenceCreateReq;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.SyncAdvise.IfExists;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.JsonRequestConsolidateService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.BPartnerCompositeRestUtils;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingPropertyException;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;
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

	private final transient JsonRetrieverService jsonRetrieverService;
	private final transient JsonRequestConsolidateService jsonRequestConsolidateService;
	private final transient ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;
	private final transient CurrencyRepository currencyRepository;
	private final transient AlbertaBPartnerCompositeService albertaBPartnerCompositeService;

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
			@NonNull final String identifier)
	{
		this.jsonRetrieverService = jsonRetrieverService;
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.currencyRepository = currencyRepository;
		this.albertaBPartnerCompositeService = albertaBPartnerCompositeService;

		this.identifier = assumeNotEmpty(identifier, "Param Identifier may not be empty");
	}

	public JsonResponseBPartnerCompositeUpsertItem persist(
			@Nullable final String orgCode,
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistWithinTrx(orgCode, requestItem, parentSyncAdvise));
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	private JsonResponseBPartnerCompositeUpsertItem persistWithinTrx(
			@Nullable final String orgCode,
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		// TODO: add support to retrieve without changelog; we don't need changelog here;
		// but! make sure we don't screw up caching

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
		handleExternalReferenceRecords(requestItem, result, bpartnerComposite.getOrgId());

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
					.build();
		}

	}

	public JsonResponseUpsertItem persist(
			@NonNull final ExternalIdentifier contactIdentifier,
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		return trxManager.callInNewTrx(() -> persistWithinTrx(contactIdentifier, jsonContact, parentSyncAdvise));

	}

	public JsonResponseUpsertItem persistWithinTrx(
			@NonNull final ExternalIdentifier contactIdentifier,
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
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
			handleExternalReference(contactIdentifier, metasfreshId, ExternalUserReferenceType.USER_ID);
		}

		return responseUpsertItem;
	}

	private void handleExternalReference(
			@NonNull final ExternalIdentifier externalIdentifier,
			@NonNull final JsonMetasfreshId metasfreshId,
			@NonNull final IExternalReferenceType externalReferenceType)
	{
		if (EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
					.id(externalIdentifier.asExternalValueAndSystem().getValue())
					.type(externalReferenceType.getCode())
					.build();

			final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.of(externalReferenceLookupItem, metasfreshId);

			final JsonSingleExternalReferenceCreateReq externalReferenceCreateRequest = JsonSingleExternalReferenceCreateReq
					.builder()
					.systemName(JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem()))
					.externalReferenceItem(externalReferenceItem)
					.build();

			externalReferenceRestControllerService.performInsertIfMissing(externalReferenceCreateRequest, null);
		}
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

				final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getLocationIdentifier());
				handleExternalReference(externalIdentifier, metasfreshId, BPLocationExternalReferenceType.BPARTNER_LOCATION);

				final JsonResponseUpsertItem responseItem = identifierToBuilder
						.get(requestItem.getLocationIdentifier())
						.metasfreshId(metasfreshId)
						.build();
				response.responseItem(responseItem);
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
			final BPartnerContact bpartnerContact = bpartnerComposite.extractContactByHandle(requestItem.getContactIdentifier()).get();

			final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(BPartnerContactId.toRepoId(bpartnerContact.getId()));

			final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getContactIdentifier());
			handleExternalReference(externalIdentifier, metasfreshId, ExternalUserReferenceType.USER_ID);

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getContactIdentifier())
					.metasfreshId(JsonMetasfreshId.of(BPartnerContactId.toRepoId(bpartnerContact.getId())))
					.build();
			response.responseItem(responseItem);
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
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonBankAccountsUpsert.getRequestItems())
		{
			final String iban = requestItem.getIban();
			final BPartnerBankAccount bankAccount = shortTermIndex.extract(iban);

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getIban())
					.metasfreshId(JsonMetasfreshId.of(BPartnerBankAccountId.toRepoId(bankAccount.getId())))
					.build();
			response.responseItem(responseItem);
		}

		return Optional.of(response.build());
	}

	private void syncJsonToBPartnerComposite(
			@NonNull final JsonResponseBPartnerCompositeUpsertItemUnderConstrunction resultBuilder,
			@NonNull final JsonRequestComposite jsonRequestComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		syncJsonToOrg(jsonRequestComposite, bpartnerComposite, parentSyncAdvise);

		final BooleanWithReason anythingWasSynced = syncJsonToBPartner(
				jsonRequestComposite,
				bpartnerComposite,
				resultBuilder.isNewBPartner(),
				parentSyncAdvise);
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
			final Optional<BPartnerBankAccount> bankAccount = bpartnerComposite.getBankAccountByIBAN(requestItem.getIban());
			builder.metasfreshId(JsonMetasfreshId.of(BPartnerBankAccountId.toRepoId(bankAccount.get().getId())));
		}
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

		if (hasOrgId && (!syncAdvise.getIfExists().isUpdate() || Check.isBlank(jsonBPartnerComposite.getOrgCode())))
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
			@NonNull final SyncAdvise parentSyncAdvise)
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
						.getByNameAndOrgId(jsonBPartner.getGroup(), bpartnerComposite.getOrgId());

				final BPGroup bpGroup;
				if (optionalBPGroup.isPresent())
				{
					bpGroup = optionalBPGroup.get();
					bpGroup.setName(jsonBPartner.getGroup().trim());
				}
				else
				{
					bpGroup = BPGroup.of(bpartnerComposite.getOrgId(), null, jsonBPartner.getGroup().trim());
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
		final String iban = jsonBankAccount.getIban();
		final BPartnerBankAccount existingBankAccount = shortTermIndex.extract(iban);

		final JsonResponseUpsertItemBuilder resultBuilder = JsonResponseUpsertItem.builder()
				.identifier(iban);

		final BPartnerBankAccount bankAccount;
		if (existingBankAccount != null)
		{
			bankAccount = existingBankAccount;
			shortTermIndex.remove(existingBankAccount.getId());
			resultBuilder.syncOutcome(SyncOutcome.UPDATED);
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder().resourceName("bankAccount").resourceIdentifier(iban).parentResource(jsonBankAccount).build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}

			final CurrencyId currencyId = extractCurrencyIdOrNull(jsonBankAccount);
			if (currencyId == null)
			{
				throw MissingResourceException.builder().resourceName("bankAccount.currencyId").resourceIdentifier(iban).parentResource(jsonBankAccount).build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}

			bankAccount = shortTermIndex.newBankAccount(iban, currencyId);
			resultBuilder.syncOutcome(SyncOutcome.CREATED);
		}

		syncJsonToBankAccount(jsonBankAccount, bankAccount);

		return resultBuilder;
	}

	private void syncJsonToBankAccount(
			@NonNull final JsonRequestBankAccountUpsertItem jsonBankAccount,
			@NonNull final BPartnerBankAccount bankAccount)
	{
<<<<<<< HEAD
		// ignoring syncAdvise.isUpdateRemove because both active and currencyId can't be NULLed
=======
		bankAccount.setIban(jsonBankAccount.getIban());
>>>>>>> e6a3b50d6b6 (JsonPersisterService: Fix bug when IBAN is updated (#18213))

		// active
		if (jsonBankAccount.getActive() != null)
		{
			bankAccount.setActive(jsonBankAccount.getActive());
		}

		// currency
		final CurrencyId currencyId = extractCurrencyIdOrNull(jsonBankAccount);
		if (currencyId != null)
		{
			bankAccount.setCurrencyId(currencyId);
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
			location.setName(StringUtils.trim(jsonBPartnerLocation.getName()));
		}

		// bpartnerName
		if (jsonBPartnerLocation.isNameSet())
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

		if (jsonBPartnerLocation.isVisitorsAddressSet())
		{
			if (jsonBPartnerLocation.getVisitorsAddress() == null)
			{
				logger.debug("Ignoring boolean property \"visitorsAddress\" : null ");
			}
			else
			{
				locationType.visitorsAddress(jsonBPartnerLocation.getVisitorsAddress());
			}
		}

		return locationType.build();
	}

	private Optional<JsonRequestExternalReferenceUpsert> mapToJsonRequestExternalReferenceUpsert(
			@NonNull final JsonResponseUpsertItem responseUpsertItem,
			@NonNull final IExternalReferenceType externalReferenceType,
			@Nullable final String externalVersion,
			@Nullable final String externalReferenceURL)
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

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.id(externalIdentifier.asExternalValueAndSystem().getValue())
				.type(externalReferenceType.getCode())
				.build();

		final JsonExternalReferenceItem externalReferenceItem = JsonExternalReferenceItem.builder()
				.lookupItem(externalReferenceLookupItem)
				.metasfreshId(responseUpsertItem.getMetasfreshId())
				.version(externalVersion)
				.externalReferenceUrl(externalReferenceURL)
				.build();

		final JsonRequestExternalReferenceUpsert jsonRequestExternalReferenceUpsert = JsonRequestExternalReferenceUpsert
				.builder()
				.systemName(JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem()))
				.externalReferenceItem(externalReferenceItem)
				.build();

		return Optional.of(jsonRequestExternalReferenceUpsert);

	}

	private void handleExternalReferenceRecords(
			@NonNull final JsonRequestBPartnerUpsertItem requestItem,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem result,
			@NonNull final OrgId orgId)
	{
		final Set<JsonRequestExternalReferenceUpsert> externalReferenceCreateReqs = new HashSet<>();

		final JsonResponseUpsertItem bPartnerResult = result.getResponseBPartnerItem();
		final Optional<JsonRequestExternalReferenceUpsert> upsertExternalRefRequest =
				mapToJsonRequestExternalReferenceUpsert(bPartnerResult,
														BPartnerExternalReferenceType.BPARTNER,
														requestItem.getExternalVersion(),
														requestItem.getExternalReferenceUrl());

		upsertExternalRefRequest.ifPresent(externalReferenceCreateReqs::add);

		final List<JsonResponseUpsertItem> bPartnerLocationsResult = result.getResponseLocationItems();
		if (!CollectionUtils.isEmpty(bPartnerLocationsResult))
		{

			final Map<String,String> bpartnerLocationIdentifier2externalVersion = requestItem
					.getBpartnerComposite()
					.getLocationsNotNull()
					.getRequestItems()
					.stream()
					.filter(bpLocation -> bpLocation.getExternalVersion() != null)
					.collect(Collectors.toMap(JsonRequestLocationUpsertItem::getLocationIdentifier, JsonRequestLocationUpsertItem::getExternalVersion));

			externalReferenceCreateReqs.addAll(bPartnerLocationsResult
													   .stream()
													   .map(bPartnerLocation -> mapToJsonRequestExternalReferenceUpsert(bPartnerLocation,
																														BPLocationExternalReferenceType.BPARTNER_LOCATION,
																														bpartnerLocationIdentifier2externalVersion.get(bPartnerLocation.getIdentifier()),
																														null))
													   .filter(Optional::isPresent)
													   .map(Optional::get)
													   .collect(Collectors.toSet()));
		}

		final List<JsonResponseUpsertItem> bPartnerContactItems = result.getResponseContactItems();
		if (!CollectionUtils.isEmpty(bPartnerContactItems))
		{
			externalReferenceCreateReqs.addAll(bPartnerContactItems
													   .stream()
													   .map(bPartnerContact -> mapToJsonRequestExternalReferenceUpsert(bPartnerContact,
																													   ExternalUserReferenceType.USER_ID,
																													   null,
																													   null))
													   .filter(Optional::isPresent)
													   .map(Optional::get)
													   .collect(Collectors.toSet()));
		}

		for (final JsonRequestExternalReferenceUpsert request : externalReferenceCreateReqs)
		{
			externalReferenceRestControllerService.performUpsert(request, orgId);
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
			final Map<String,JsonMetasfreshId> contactIdentifierToMetasfreshId = result.getResponseContactItems()
					.stream()
					.collect(ImmutableMap.toImmutableMap(JsonResponseUpsertItem::getIdentifier, JsonResponseUpsertItem::getMetasfreshId));

			final SyncAdvise effectiveSyncAdvise =  CoalesceUtil.coalesceNotNull(requestBPartnerComposite.getContactsNotNull().getSyncAdvise(), syncAdvise);

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
}
