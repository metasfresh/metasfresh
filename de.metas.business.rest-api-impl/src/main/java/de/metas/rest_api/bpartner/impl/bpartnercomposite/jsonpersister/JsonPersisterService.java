package de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isBlank;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.CurrencyId;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.rest_api.bpartner.impl.JsonRequestConsolidateService;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.BPartnerCompositeRestUtils;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestBankAccountUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestBankAccountsUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.rest_api.bpartner.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.rest_api.bpartner.response.JsonResponseBPartnerCompositeUpsertItem.JsonResponseBPartnerCompositeUpsertItemBuilder;
import de.metas.rest_api.bpartner.response.JsonResponseUpsert;
import de.metas.rest_api.bpartner.response.JsonResponseUpsert.JsonResponseUpsertBuilder;
import de.metas.rest_api.bpartner.response.JsonResponseUpsertItem;
import de.metas.rest_api.bpartner.response.JsonResponseUpsertItem.JsonResponseUpsertItemBuilder;
import de.metas.rest_api.bpartner.response.JsonResponseUpsertItem.SyncOutcome;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.common.SyncAdvise.IfExists;
import de.metas.rest_api.exception.InvalidIdentifierException;
import de.metas.rest_api.exception.MissingPropertyException;
import de.metas.rest_api.exception.MissingResourceException;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Data;
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
public class JsonPersisterService
{
	private final transient JsonRetrieverService jsonRetrieverService;
	private final transient JsonRequestConsolidateService jsonRequestConsolidateService;
	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;
	private final transient CurrencyRepository currencyRepository;

	@Getter
	private final String identifier;

	public JsonPersisterService(
			@NonNull final JsonRetrieverService jsonRetrieverService,
			@NonNull final JsonRequestConsolidateService jsonRequestConsolidateService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final String identifier)
	{
		this.jsonRetrieverService = jsonRetrieverService;
		this.jsonRequestConsolidateService = jsonRequestConsolidateService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.currencyRepository = currencyRepository;

		this.identifier = assumeNotEmpty(identifier, "Param Identifier may not be empty");
	}

	public JsonResponseBPartnerCompositeUpsertItem persist(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		// TODO: add support to retrieve without changelog; we don't need changelog here;
		// but! make sure we don't screw up caching
		final Optional<BPartnerComposite> optionalBPartnerComposite = jsonRetrieverService.getBPartnerComposite(bpartnerIdentifier);

		final JsonResponseBPartnerCompositeUpsertItemUnderConstrunction resultBuilder = new JsonResponseBPartnerCompositeUpsertItemUnderConstrunction();
		resultBuilder.setJsonResponseBPartnerUpsertItemBuilder(JsonResponseUpsertItem.builder().identifier(bpartnerIdentifier.getRawIdentifierString()));

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);
		final BPartnerComposite bpartnerComposite;
		if (optionalBPartnerComposite.isPresent())
		{
			// load and mutate existing aggregation root
			bpartnerComposite = optionalBPartnerComposite.get();
			resultBuilder.setNewBPartner(false);
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("bpartner")
						.resourceIdentifier(bpartnerIdentifier.toJson())
						.parentResource(jsonBPartnerComposite)
						.build()
						.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
			}
			// create new aggregation root
			bpartnerComposite = BPartnerComposite.builder().build();
			resultBuilder.setNewBPartner(true);
		}

		syncJsonToBPartnerComposite(
				resultBuilder,
				jsonBPartnerComposite,
				bpartnerComposite,
				effectiveSyncAdvise);

		return resultBuilder.build();
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
					.jsonResponseBPartnerUpsertItem(jsonResponseBPartnerUpsertItemBuilder.build());

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
					.jsonResponseContactUpsertItems(contactUpsertItems)
					.jsonResponseLocationUpsertItems(locationUpsertItems)
					.jsonResponseBankAccountUpsertItems(bankAccountUpsertItems)
					.build();
		}

	}

	public JsonResponseUpsertItem persist(
			@NonNull final IdentifierString contactIdentifier,
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final BPartnerContactQuery contactQuery = createContactQuery(contactIdentifier);
		final Optional<BPartnerCompositeAndContactId> optionalContactIdAndBPartner = bpartnerCompositeRepository.getByContact(contactQuery);

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
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(contactIdentifier.toJson()).parentResource(jsonContact).build()
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

		syncJsonToContact(contactIdentifier, jsonContact, contact, parentSyncAdvise);

		bpartnerCompositeRepository.save(bpartnerComposite);

		// might be different from contactIdentifier
		final IdentifierString identifierAfterUpdate = jsonRequestConsolidateService.extractIdentifier(contactIdentifier.getType(), contact);

		final Optional<BPartnerContact> persistedContact = bpartnerComposite.extractContact(BPartnerCompositeRestUtils.createContactFilterFor(identifierAfterUpdate));

		return JsonResponseUpsertItem.builder()
				.identifier(contactIdentifier.getRawIdentifierString())
				.metasfreshId(MetasfreshId.of(persistedContact.get().getId()))
				.syncOutcome(syncOutcome)
				.build();
	}

	private static BPartnerContactQuery createContactQuery(@NonNull final IdentifierString contactIdentifier)
	{
		final BPartnerContactQueryBuilder contactQuery = BPartnerContactQuery.builder();

		switch (contactIdentifier.getType())
		{
			case EXTERNAL_ID:
				contactQuery.externalId(JsonExternalIds.toExternalIdOrNull(contactIdentifier.asJsonExternalId()));
				break;
			case METASFRESH_ID:
				final UserId userId = contactIdentifier.asMetasfreshId(UserId::ofRepoId);
				contactQuery.userId(userId);
				break;
			case VALUE:
				contactQuery.value(contactIdentifier.asValue());
				break;
			default:
				throw new InvalidIdentifierException(contactIdentifier);
		}
		return contactQuery.build();
	}

	/**
	 * Adds or updates the given locations. Leaves all unrelated locations of the same bPartner untouched
	 */
	public Optional<JsonResponseUpsert> persistForBPartner(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final JsonRequestLocationUpsert jsonBPartnerLocations,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.getBPartnerComposite(bpartnerIdentifier);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartnerLocations.getSyncAdvise(), parentSyncAdvise);

		final List<JsonRequestLocationUpsertItem> requestItems = jsonBPartnerLocations.getRequestItems();

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonLocation(requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getLocationIdentifier(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite);

		// now collect the metasfreshIds that we got after having invoked save
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			final IdentifierString locationIdentifier = IdentifierString.of(requestItem.getLocationIdentifier());
			final BPartnerLocation bpartnerLocation = shortTermIndex.extract(locationIdentifier);

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getLocationIdentifier())
					.metasfreshId(MetasfreshId.of(bpartnerLocation.getId()))
					.build();
			response.responseItem(responseItem);
		}

		return Optional.of(response.build());
	}

	public Optional<JsonResponseUpsert> persistForBPartner(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final JsonRequestContactUpsert jsonContactUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.getBPartnerComposite(bpartnerIdentifier);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonContactUpsert.getSyncAdvise(), parentSyncAdvise);

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonContact(requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getContactIdentifier(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite);

		// now collect what we got
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			final IdentifierString contactIdentifier = IdentifierString.of(requestItem.getContactIdentifier());
			final BPartnerContact bpartnerContact = shortTermIndex.extract(contactIdentifier);

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getContactIdentifier())
					.metasfreshId(MetasfreshId.of(bpartnerContact.getId()))
					.build();
			response.responseItem(responseItem);
		}

		return Optional.of(response.build());
	}

	public Optional<JsonResponseUpsert> persistForBPartner(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final JsonRequestBankAccountsUpsert jsonBankAccountsUpsert,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final BPartnerComposite bpartnerComposite = jsonRetrieverService.getBPartnerComposite(bpartnerIdentifier).orElse(null);
		if (bpartnerComposite == null)
		{
			return Optional.empty(); // 404
		}

		final ShortTermBankAccountIndex shortTermIndex = new ShortTermBankAccountIndex(bpartnerComposite);

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBankAccountsUpsert.getSyncAdvise(), parentSyncAdvise);

		final Map<String, JsonResponseUpsertItemBuilder> identifierToBuilder = new HashMap<>();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonBankAccountsUpsert.getRequestItems())
		{
			final JsonResponseUpsertItemBuilder responseItemBuilder = syncJsonBankAccount(requestItem, effectiveSyncAdvise, shortTermIndex);

			// we don't know the metasfreshId yet, so for now just store what we know into the map
			identifierToBuilder.put(requestItem.getIban(), responseItemBuilder);
		}

		bpartnerCompositeRepository.save(bpartnerComposite);

		// now collect what we got
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonBankAccountsUpsert.getRequestItems())
		{
			final String iban = requestItem.getIban();
			final BPartnerBankAccount bankAccount = shortTermIndex.extract(iban);

			final JsonResponseUpsertItem responseItem = identifierToBuilder
					.get(requestItem.getIban())
					.metasfreshId(MetasfreshId.of(bankAccount.getId()))
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
		resultBuilder.setJsonResponseLocationUpsertItems(syncJsonToLocations(jsonRequestComposite, bpartnerComposite, parentSyncAdvise));
		resultBuilder.setJsonResponseBankAccountUpsertItems(syncJsonToBankAccounts(jsonRequestComposite, bpartnerComposite, parentSyncAdvise));

		bpartnerCompositeRepository.save(bpartnerComposite);

		//
		// supplement the metasfreshiId which we now have after the "save()"
		resultBuilder.getJsonResponseBPartnerUpsertItemBuilder().metasfreshId(MetasfreshId.of(bpartnerComposite.getBpartner().getId()));

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseContactUpsertItemBuilders = resultBuilder.getJsonResponseContactUpsertItems();
		for (final JsonRequestContactUpsertItem requestItem : jsonRequestComposite.getContactsNotNull().getRequestItems())
		{
			final JsonResponseUpsertItemBuilder builder = jsonResponseContactUpsertItemBuilders.get(requestItem.getContactIdentifier());
			final Optional<BPartnerContact> contact = bpartnerComposite.extractContact(BPartnerCompositeRestUtils.createContactFilterFor(IdentifierString.of(requestItem.getContactIdentifier())));
			builder.metasfreshId(MetasfreshId.of(contact.get().getId()));
		}

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseLocationUpsertItemBuilders = resultBuilder.getJsonResponseLocationUpsertItems();
		for (final JsonRequestLocationUpsertItem requestItem : jsonRequestComposite.getLocationsNotNull().getRequestItems())
		{
			final JsonResponseUpsertItemBuilder builder = jsonResponseLocationUpsertItemBuilders.get(requestItem.getLocationIdentifier());
			final Optional<BPartnerLocation> location = bpartnerComposite.extractLocation(BPartnerCompositeRestUtils.createLocationFilterFor(IdentifierString.of(requestItem.getLocationIdentifier())));
			builder.metasfreshId(MetasfreshId.of(location.get().getId()));
		}

		final ImmutableMap<String, JsonResponseUpsertItemBuilder> jsonResponseBankAccountUpsertItemBuilders = resultBuilder.getJsonResponseBankAccountUpsertItems();
		for (final JsonRequestBankAccountUpsertItem requestItem : jsonRequestComposite.getBankAccountsNotNull().getRequestItems())
		{
			final JsonResponseUpsertItemBuilder builder = jsonResponseBankAccountUpsertItemBuilders.get(requestItem.getIban());
			final Optional<BPartnerBankAccount> bankAccount = bpartnerComposite.getBankAccountByIBAN(requestItem.getIban());
			builder.metasfreshId(MetasfreshId.of(bankAccount.get().getId()));
		}
	}

	private void syncJsonToOrg(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final SyncAdvise syncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);
		final boolean hasOrgId = bpartnerComposite.getOrgId() != null;

		if (hasOrgId && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}

		final OrgId orgId;
		final String orgCode = jsonBPartnerComposite.getOrgCode();
		if (!isEmpty(orgCode, true))
		{
			orgId = Services.get(IOrgDAO.class)
					.retrieveOrgIdBy(OrgQuery.ofValue(orgCode))
					.orElse(Env.getOrgId());
		}
		else
		{
			orgId = Env.getOrgId();
		}
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

		// note that if the BPartner wouldn't exists, we weren't here
		final SyncAdvise effCompositeSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		if (bpartnerCompositeIsNew && effCompositeSyncAdvise.isFailIfNotExists())
		{
			throw MissingResourceException.builder().resourceName("bpartner").parentResource(jsonBPartnerComposite).build();
		}

		final BPartner bpartner = bpartnerComposite.getBpartner();
		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartner.getSyncAdvise(), effCompositeSyncAdvise);
		final IfExists ifExistsAdvise = effectiveSyncAdvise.getIfExists();

		if (!bpartnerCompositeIsNew && !ifExistsAdvise.isUpdate())
		{
			return BooleanWithReason.falseBecause("JsonRequestBPartner exists and effectiveSyncAdvise.ifExists=" + ifExistsAdvise);
		}

		final boolean isUpdateRemove = ifExistsAdvise.isUpdateRemove();

		// active
		if (jsonBPartner.getActive() != null)
		{
			bpartner.setActive(jsonBPartner.getActive());
		}

		// code / value
		if (!isEmpty(jsonBPartner.getCode(), true))
		{
			bpartner.setValue(jsonBPartner.getCode().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setValue(null);
		}

		// companyName
		if (!isEmpty(jsonBPartner.getCompanyName(), true))
		{
			bpartner.setCompanyName(jsonBPartner.getCompanyName().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setCompanyName(null);
		}

		// name
		if (!isEmpty(jsonBPartner.getName(), true))
		{
			bpartner.setName(jsonBPartner.getName().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setName(null);
		}

		// name2
		if (!isEmpty(jsonBPartner.getName2(), true))
		{
			bpartner.setName2(jsonBPartner.getName2().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setName2(null);
		}

		// name3
		if (!isEmpty(jsonBPartner.getName3(), true))
		{
			bpartner.setName3(jsonBPartner.getName3().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setName3(null);
		}

		// externalId
		if (jsonBPartner.getExternalId() != null)
		{
			bpartner.setExternalId(JsonConverters.fromJsonOrNull(jsonBPartner.getExternalId()));
		}
		else if (isUpdateRemove)
		{
			bpartner.setExternalId(null);
		}

		if (jsonBPartner.getCustomer() != null)
		{
			bpartner.setCustomer(jsonBPartner.getCustomer());
		}
		if (jsonBPartner.getVendor() != null)
		{
			bpartner.setVendor(jsonBPartner.getVendor());
		}

		// group
		if (!isEmpty(jsonBPartner.getGroup(), true))
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
		else if (bpartner.getGroupId() == null)
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
		}
		// note that BP_Group_ID is mandatory, so we won't unset it even if isUpdateRemove

		// language
		if (!isEmpty(jsonBPartner.getLanguage(), true))
		{
			bpartner.setLanguage(Language.asLanguage(jsonBPartner.getLanguage().trim()));
		}
		else if (isUpdateRemove)
		{
			bpartner.setLanguage(null);
		}

		// invoiceRule
		if (jsonBPartner.getInvoiceRule() != null)
		{
			bpartner.setInvoiceRule(InvoiceRule.ofCode(jsonBPartner.getInvoiceRule().toString()));
		}
		else if (isUpdateRemove)
		{
			bpartner.setInvoiceRule(null);
		}

		// metasfreshId - we will never update it

		// parentId
		if (jsonBPartner.getParentId() != null)
		{
			// TODO make sure in the repo that the parent-bpartner is reachable
			bpartner.setParentId(BPartnerId.ofRepoId(jsonBPartner.getParentId().getValue()));
		}
		else if (isUpdateRemove)
		{
			bpartner.setParentId(null);
		}

		// phone
		if (!isEmpty(jsonBPartner.getPhone(), true))
		{
			bpartner.setPhone(jsonBPartner.getPhone().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setPhone(null);
		}

		// url
		if (!isEmpty(jsonBPartner.getUrl(), true))
		{
			bpartner.setUrl(jsonBPartner.getUrl().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setUrl(null);
		}

		// url2
		if (!isEmpty(jsonBPartner.getUrl2(), true))
		{
			bpartner.setUrl2(jsonBPartner.getUrl2().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setUrl2(null);
		}

		// url3
		if (!isEmpty(jsonBPartner.getUrl3(), true))
		{
			bpartner.setUrl3(jsonBPartner.getUrl3().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setUrl3(null);
		}
		return BooleanWithReason.TRUE;
	}

	private ImmutableMap<String, JsonResponseUpsertItemBuilder> syncJsonToContacts(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		resetDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);

		final JsonRequestContactUpsert contacts = jsonBPartnerComposite.getContactsNotNull();

		final SyncAdvise contactsSyncAdvise = coalesce(contacts.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			result.put(
					contactRequestItem.getContactIdentifier(),
					syncJsonContact(contactRequestItem, contactsSyncAdvise, shortTermIndex));
		}

		if (contactsSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getContacts().removeAll(shortTermIndex.getRemainingContacts());
		}

		return result.build();
	}

	/**
	 * If the json contacts have default flags set, then this method unsets all corresponding default flags of the shortTermIndex's {@link BPartnerContact}s.
	 */
	private void resetDefaultFlagsIfNeeded(
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
			@NonNull final JsonRequestContactUpsertItem jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermContactIndex shortTermIndex)
	{
		final IdentifierString contactIdentifier = IdentifierString.of(jsonContact.getContactIdentifier());
		final BPartnerContact existingContact = shortTermIndex.extract(contactIdentifier);

		final JsonResponseUpsertItemBuilder result = JsonResponseUpsertItem.builder()
				.identifier(jsonContact.getContactIdentifier());

		final BPartnerContact contact;
		if (existingContact != null)
		{
			contact = existingContact;
			result.syncOutcome(SyncOutcome.UPDATED);
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(jsonContact.getContactIdentifier()).parentResource(jsonContact).build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			else if (Type.METASFRESH_ID.equals(contactIdentifier.getType()))
			{
				throw MissingResourceException.builder().resourceName("contact").resourceIdentifier(jsonContact.getContactIdentifier()).parentResource(jsonContact)
						.detail(TranslatableStrings.constant("With this type, only updates are allowed."))
						.build()
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			contact = shortTermIndex.newContact(contactIdentifier);
			result.syncOutcome(SyncOutcome.CREATED);
		}

		syncJsonToContact(contactIdentifier, jsonContact.getContact(), contact, parentSyncAdvise);
		return result;
	}

	private void syncJsonToContact(
			@NonNull final IdentifierString contactIdentifier,
			@NonNull final JsonRequestContact jsonBPartnerContact,
			@NonNull final BPartnerContact contact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final SyncAdvise syncAdvise = coalesce(jsonBPartnerContact.getSyncAdvise(), parentSyncAdvise);
		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

		// active
		if (jsonBPartnerContact.getActive() != null)
		{
			contact.setActive(jsonBPartnerContact.getActive());
		}

		// email
		if (!isEmpty(jsonBPartnerContact.getEmail(), true))
		{
			contact.setEmail(jsonBPartnerContact.getEmail().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setEmail(null);
		}

		// externalId
		if (jsonBPartnerContact.getExternalId() != null)
		{
			contact.setExternalId(JsonConverters.fromJsonOrNull(jsonBPartnerContact.getExternalId()));
		}
		else if (isUpdateRemove)
		{
			contact.setExternalId(null);
		}

		// firstName
		if (!isEmpty(jsonBPartnerContact.getFirstName(), true))
		{
			contact.setFirstName(jsonBPartnerContact.getFirstName().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setFirstName(null);
		}

		// lastName
		if (!isEmpty(jsonBPartnerContact.getLastName(), true))
		{
			contact.setLastName(jsonBPartnerContact.getLastName().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setLastName(null);
		}

		// metasfreshBPartnerId - never updated;

		// metasfreshId - never updated;

		// name
		if (!isEmpty(jsonBPartnerContact.getName(), true))
		{
			contact.setName(jsonBPartnerContact.getName().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setName(null);
		}

		// value
		if (!isEmpty(jsonBPartnerContact.getCode(), true))
		{
			contact.setValue(jsonBPartnerContact.getCode().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setValue(null);
		}

		// description
		if (!isEmpty(jsonBPartnerContact.getDescription(), true))
		{
			contact.setDescription(jsonBPartnerContact.getDescription().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setDescription(null);
		}

		// phone
		if (!isEmpty(jsonBPartnerContact.getPhone(), true))
		{
			contact.setPhone(jsonBPartnerContact.getPhone().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setPhone(null);
		}

		// fax
		if (!isEmpty(jsonBPartnerContact.getFax(), true))
		{
			contact.setFax(jsonBPartnerContact.getFax().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setFax(null);
		}

		// mobilePhone
		if (!isEmpty(jsonBPartnerContact.getMobilePhone(), true))
		{
			contact.setMobilePhone(jsonBPartnerContact.getMobilePhone().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setMobilePhone(null);
		}

		// newsletter
		if (jsonBPartnerContact.getNewsletter() != null)
		{
			contact.setNewsletter(jsonBPartnerContact.getNewsletter());
		}

		final BPartnerContactType contactType = BPartnerContactType.builder()
				.defaultContact(jsonBPartnerContact.getDefaultContact())
				.shipToDefault(jsonBPartnerContact.getShipToDefault())
				.billToDefault(jsonBPartnerContact.getBillToDefault())
				.purchase(jsonBPartnerContact.getPurchase())
				.purchaseDefault(jsonBPartnerContact.getPurchaseDefault())
				.sales(jsonBPartnerContact.getSales())
				.salesDefault(jsonBPartnerContact.getSalesDefault())
				.subjectMatter(jsonBPartnerContact.getSubjectMatter())
				.build();
		contact.setContactType(contactType);
	}

	private ImmutableMap<String, JsonResponseUpsertItemBuilder> syncJsonToLocations(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		resetDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);

		final JsonRequestLocationUpsert locations = jsonBPartnerComposite.getLocationsNotNull();

		final SyncAdvise locationsSyncAdvise = coalesce(locations.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			result.put(
					locationRequestItem.getLocationIdentifier(),
					syncJsonLocation(locationRequestItem, locationsSyncAdvise, shortTermIndex));
		}

		if (locationsSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getLocations().removeAll(shortTermIndex.getRemainingLocations());
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
		final SyncAdvise bankAccountsSyncAdvise = coalesce(bankAccounts.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final ImmutableMap.Builder<String, JsonResponseUpsertItemBuilder> result = ImmutableMap.builder();
		for (final JsonRequestBankAccountUpsertItem bankAccountRequestItem : bankAccounts.getRequestItems())
		{
			result.put(
					bankAccountRequestItem.getIban(),
					syncJsonBankAccount(bankAccountRequestItem, bankAccountsSyncAdvise, shortTermIndex));
		}

		if (bankAccountsSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getBankAccounts().removeAll(shortTermIndex.getRemainingBankAccounts());
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

		syncJsonToBankAccount(jsonBankAccount, bankAccount, parentSyncAdvise);

		return resultBuilder;
	}

	private void syncJsonToBankAccount(
			@NonNull final JsonRequestBankAccountUpsertItem jsonBankAccount,
			@NonNull final BPartnerBankAccount bankAccount,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final SyncAdvise syncAdvise = coalesce(jsonBankAccount.getSyncAdvise(), parentSyncAdvise);
		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

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
		else if (isUpdateRemove)
		{
			bankAccount.setCurrencyId(null);
		}

	}

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
	 */
	private void resetDefaultFlagsIfNeeded(
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
			@NonNull final JsonRequestLocationUpsertItem jsonBPartnerLocation,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermLocationIndex shortTermIndex)
	{
		final IdentifierString locationIdentifier = IdentifierString.of(jsonBPartnerLocation.getLocationIdentifier());
		final BPartnerLocation existingLocation = shortTermIndex.extract(locationIdentifier);

		final JsonResponseUpsertItemBuilder resultBuilder = JsonResponseUpsertItem.builder()
				.identifier(jsonBPartnerLocation.getLocationIdentifier());

		final BPartnerLocation location;
		if (existingLocation != null)
		{
			location = existingLocation;
			resultBuilder.syncOutcome(SyncOutcome.UPDATED);
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw MissingResourceException.builder()
						.resourceName("location")
						.resourceIdentifier(jsonBPartnerLocation.getLocationIdentifier())
						.parentResource(jsonBPartnerLocation)
						.detail(TranslatableStrings.constant("Type of locationlocationIdentifier=" + locationIdentifier.getType()))
						.build()
						.setParameter("effectiveSyncAdvise", parentSyncAdvise);
			}
			else if (Type.METASFRESH_ID.equals(locationIdentifier.getType()))
			{
				throw MissingResourceException.builder()
						.resourceName("location")
						.resourceIdentifier(jsonBPartnerLocation.getLocationIdentifier())
						.parentResource(jsonBPartnerLocation)
						.detail(TranslatableStrings.constant("Type of locationlocationIdentifier=" + locationIdentifier.getType() + "; with this identifier-type, only updates are allowed."))
						.build()
						.setParameter("effectiveSyncAdvise", parentSyncAdvise);
			}
			location = shortTermIndex.newLocation(locationIdentifier);
			resultBuilder.syncOutcome(SyncOutcome.CREATED);
		}

		syncJsonToLocation(jsonBPartnerLocation.getLocation(), location, parentSyncAdvise);

		return resultBuilder;
	}

	private void syncJsonToLocation(
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final BPartnerLocation location,
			@NonNull final SyncAdvise parentSyncAdvise)
	{

		final SyncAdvise syncAdvise = coalesce(jsonBPartnerLocation.getSyncAdvise(), parentSyncAdvise);

		// active
		if (jsonBPartnerLocation.getActive() != null)
		{
			location.setActive(jsonBPartnerLocation.getActive());
		}

		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

		// name
		if (!isEmpty(jsonBPartnerLocation.getName(), true))
		{
			location.setName(jsonBPartnerLocation.getName().trim());
		}
		else if (isUpdateRemove)
		{
			location.setName(null);
		}

		// bpartnerName
		if (!isEmpty(jsonBPartnerLocation.getBpartnerName(), true))
		{
			location.setBpartnerName(jsonBPartnerLocation.getBpartnerName().trim());
		}
		else if (isUpdateRemove)
		{
			location.setBpartnerName(null);
		}

		// address1
		if (!isEmpty(jsonBPartnerLocation.getAddress1(), true))
		{
			location.setAddress1(jsonBPartnerLocation.getAddress1().trim());
		}
		else if (isUpdateRemove)
		{
			location.setAddress1(null);
		}

		// address2
		if (!isEmpty(jsonBPartnerLocation.getAddress2(), true))
		{
			location.setAddress2(jsonBPartnerLocation.getAddress2().trim());
		}
		else if (isUpdateRemove)
		{
			location.setAddress2(null);
		}
		// address3
		if (!isEmpty(jsonBPartnerLocation.getAddress3(), true))
		{
			location.setAddress3(jsonBPartnerLocation.getAddress3().trim());
		}
		else if (isUpdateRemove)
		{
			location.setAddress3(null);
		}

		// address4
		if (!isEmpty(jsonBPartnerLocation.getAddress4(), true))
		{
			location.setAddress4(jsonBPartnerLocation.getAddress4().trim());
		}
		else if (isUpdateRemove)
		{
			location.setAddress4(null);
		}

		// city
		if (!isEmpty(jsonBPartnerLocation.getCity(), true))
		{
			location.setCity(jsonBPartnerLocation.getCity().trim());
		}
		else if (isUpdateRemove)
		{
			location.setCity(null);
		}

		// countryCode
		if (!isEmpty(jsonBPartnerLocation.getCountryCode(), true))
		{
			location.setCountryCode(jsonBPartnerLocation.getCountryCode().trim());
		}
		else if (isUpdateRemove)
		{
			location.setCountryCode(null);
		}

		// district
		if (!isEmpty(jsonBPartnerLocation.getDistrict(), true))
		{
			location.setDistrict(jsonBPartnerLocation.getDistrict().trim());
		}
		else if (isUpdateRemove)
		{
			location.setDistrict(null);
		}

		// externalId
		if (jsonBPartnerLocation.getExternalId() != null)
		{
			location.setExternalId(JsonConverters.fromJsonOrNull(jsonBPartnerLocation.getExternalId()));
		}
		else if (isUpdateRemove)
		{
			location.setExternalId(null);
		}

		// gln
		final GLN gln = GLN.ofNullableString(jsonBPartnerLocation.getGln());
		if (gln != null)
		{
			location.setGln(gln);
		}
		else if (isUpdateRemove)
		{
			location.setGln(null);
		}

		// poBox
		if (!isEmpty(jsonBPartnerLocation.getPoBox(), true))
		{
			location.setPoBox(jsonBPartnerLocation.getPoBox().trim());
		}
		else if (isUpdateRemove)
		{
			location.setPoBox(null);
		}

		// postal
		if (!isEmpty(jsonBPartnerLocation.getPostal(), true))
		{
			location.setPostal(jsonBPartnerLocation.getPostal().trim());
		}
		else if (isUpdateRemove)
		{
			location.setPostal(null);
		}

		// region
		if (!isEmpty(jsonBPartnerLocation.getRegion(), true))
		{
			location.setRegion(jsonBPartnerLocation.getRegion().trim());
		}
		else if (isUpdateRemove)
		{
			location.setRegion(null);
		}

		final BPartnerLocationType locationType = BPartnerLocationType.builder()
				.billToDefault(jsonBPartnerLocation.getBillToDefault())
				.billTo(jsonBPartnerLocation.getBillTo())
				.shipToDefault(jsonBPartnerLocation.getShipToDefault())
				.shipTo(jsonBPartnerLocation.getShipTo())
				.build();
		location.setLocationType(locationType);
	}
}
