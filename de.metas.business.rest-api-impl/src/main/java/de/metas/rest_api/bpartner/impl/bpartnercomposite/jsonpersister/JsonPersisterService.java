package de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.List;
import java.util.Optional;

import org.compiere.util.Env;

import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.i18n.Language;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.rest_api.bpartner.response.JsonResponseUpsert;
import de.metas.rest_api.bpartner.response.JsonResponseUpsertItem;
import de.metas.rest_api.bpartner.response.JsonResponseUpsert.JsonResponseUpsertBuilder;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.rest_api.utils.InvalidIdentifierException;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.rest_api.utils.MissingPropertyException;
import de.metas.rest_api.utils.MissingResourceException;
import de.metas.user.UserId;
import de.metas.util.Services;
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

	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;

	private final transient BPGroupRepository bpGroupRepository;

	private final transient JsonRetrieverService jsonRetrieverService;

	@Getter
	private final String identifier;

	public JsonPersisterService(
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final JsonRetrieverService jsonRetrieverService,
			@NonNull final String identifier)
	{
		this.jsonRetrieverService = jsonRetrieverService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.identifier = assumeNotEmpty(identifier, "Param Identifier may not be empty");
	}

	public BPartnerComposite persist(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		// TODO: add support to retrieve without changelog; we don't need changelog here;
		// but! make sure we don't screw up caching
		final Optional<BPartnerComposite> optionalBPartnerComposite = jsonRetrieverService.getBPartnerComposite(bpartnerIdentifier);

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final BPartnerComposite bpartnerComposite;
		if (optionalBPartnerComposite.isPresent())
		{
			// load and mutate existing aggregation root
			bpartnerComposite = optionalBPartnerComposite.get();
		}
		else
		{
			if (effectiveSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException(
						"Did not find an existing partner with identifier '" + bpartnerIdentifier + "'",
						jsonBPartnerComposite)
								.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
			}
			// create new aggregation root
			bpartnerComposite = BPartnerComposite.builder().build();
		}

		syncJsonToBPartnerComposite(jsonBPartnerComposite, bpartnerComposite, effectiveSyncAdvise);

		return bpartnerComposite;
	}

	public BPartnerContact persist(
			@NonNull final IdentifierString contactIdentifier,
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final BPartnerContactQuery contactQuery = createContactQuery(contactIdentifier);
		final Optional<BPartnerCompositeAndContactId> optionalContactIdAndBPartner = bpartnerCompositeRepository.getByContact(contactQuery);

		final BPartnerContact contact;
		final BPartnerComposite bpartnerComposite;
		if (optionalContactIdAndBPartner.isPresent())
		{
			final BPartnerCompositeAndContactId contactIdAndBPartner = optionalContactIdAndBPartner.get();

			bpartnerComposite = contactIdAndBPartner.getBpartnerComposite();

			contact = bpartnerComposite
					.extractContact(contactIdAndBPartner.getBpartnerContactId())
					.get(); // it's there, or we wouldn't be in this if-block
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException(
						"Did not find an existing contact with identifier '" + contactIdentifier + "'")
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
		}

		syncJsonToContact(jsonContact, contact, parentSyncAdvise);

		bpartnerCompositeRepository.save(bpartnerComposite);

		return contact;
	}

	private static BPartnerContactQuery createContactQuery(@NonNull final IdentifierString contactIdentifier)
	{
		final BPartnerContactQueryBuilder contactQuery = BPartnerContactQuery.builder();

		switch (contactIdentifier.getType())
		{
			case EXTERNAL_ID:
				contactQuery.externalId(JsonExternalIds.toExternalIdOrNull((contactIdentifier.asJsonExternalId())));
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

	/** Adds or update a given location. Leaves all unrelated location of the same bpartner untouched */
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
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			syncJsonLocation(requestItem, effectiveSyncAdvise, shortTermIndex);
		}

		bpartnerCompositeRepository.save(bpartnerComposite);

		// now collect what we got
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestLocationUpsertItem requestItem : requestItems)
		{
			final IdentifierString locationIdentifier = IdentifierString.of(requestItem.getLocationIdentifier());

			final BPartnerLocation bpartnerLocation = shortTermIndex.extract(locationIdentifier);

			response.responseItem(JsonResponseUpsertItem.builder()
					.identifier(requestItem.getLocationIdentifier())
					.metasfreshId(MetasfreshId.of(bpartnerLocation.getId()))
					.build());
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

		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			syncJsonContact(requestItem, effectiveSyncAdvise, shortTermIndex);
		}

		bpartnerCompositeRepository.save(bpartnerComposite);

		// now collect what we got
		final JsonResponseUpsertBuilder response = JsonResponseUpsert.builder();
		for (final JsonRequestContactUpsertItem requestItem : jsonContactUpsert.getRequestItems())
		{
			final IdentifierString locationIdentifier = IdentifierString.of(requestItem.getContactIdentifier());

			final BPartnerContact bpartnerContact = shortTermIndex.extract(locationIdentifier);

			response.responseItem(JsonResponseUpsertItem.builder()
					.identifier(requestItem.getContactIdentifier())
					.metasfreshId(MetasfreshId.of(bpartnerContact.getId()))
					.build());
		}

		return Optional.of(response.build());
	}

	private void syncJsonToBPartnerComposite(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		syncJsonToOrg(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToBPartner(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToContacts(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToLocations(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		bpartnerCompositeRepository.save(bpartnerComposite);
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

	private void syncJsonToBPartner(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final JsonRequestBPartner jsonBPartner = jsonBPartnerComposite.getBpartner();
		if (jsonBPartner == null)
		{
			return; // the requests contains no bpartner to sync
		}

		// note that if the BPartner wouldn't exists, we weren't here
		final SyncAdvise effCompositeSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		final BPartner bpartner = bpartnerComposite.getBpartner();
		if (bpartner == null && effCompositeSyncAdvise.isFailIfNotExists())
		{
			throw new MissingResourceException("JsonBPartner");
		}

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartner.getSyncAdvise(), effCompositeSyncAdvise);
		final IfExists ifExistsAdvise = effectiveSyncAdvise.getIfExists();

		if (!ifExistsAdvise.isUpdate())
		{
			return; // nothing to do
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

		else if (isUpdateRemove)
		{
			bpartner.setGroupId(null);
		}

		// language
		if (!isEmpty(jsonBPartner.getLanguage(), true))
		{
			bpartner.setLanguage(Language.asLanguage(jsonBPartner.getLanguage().trim()));
		}
		else if (isUpdateRemove)
		{
			bpartner.setLanguage(null);
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
	}

	private void syncJsonToContacts(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		resetDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);

		final JsonRequestContactUpsert contacts = jsonBPartnerComposite.getContactsNotNull();

		final SyncAdvise contactsSyncAdvise = coalesce(contacts.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		for (final JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			syncJsonContact(contactRequestItem, contactsSyncAdvise, shortTermIndex);
		}

		if (contactsSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getContacts().removeAll(shortTermIndex.getRemainingContacts());
		}
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

	private void syncJsonContact(
			@NonNull final JsonRequestContactUpsertItem jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermContactIndex shortTermIndex)
	{
		final IdentifierString contactIdentifier = IdentifierString.of(jsonContact.getContactIdentifier());
		final BPartnerContact existingContact = shortTermIndex.extract(contactIdentifier);

		final BPartnerContact contact;
		if (existingContact != null)
		{
			contact = existingContact;
			shortTermIndex.remove(existingContact.getId());
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException("Missing contact with identifier=" + jsonContact.getContactIdentifier())
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			else if (Type.METASFRESH_ID.equals(contactIdentifier.getType()))
			{
				throw new MissingResourceException("Missing contact with identifier=" + jsonContact.getContactIdentifier() + "; with this type, only updates are allowed.")
						.setParameter("parentSyncAdvise", parentSyncAdvise);
			}
			contact = shortTermIndex.newContact(contactIdentifier);
		}
		syncJsonToContact(jsonContact.getContact(), contact, parentSyncAdvise);
	}

	private void syncJsonToContact(
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

	private void syncJsonToLocations(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		resetDefaultFlagsIfNeeded(jsonBPartnerComposite, shortTermIndex);

		final JsonRequestLocationUpsert locations = jsonBPartnerComposite.getLocationsNotNull();

		final SyncAdvise locationsSyncAdvise = coalesce(locations.getSyncAdvise(), jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			syncJsonLocation(locationRequestItem, locationsSyncAdvise, shortTermIndex);
		}

		if (locationsSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getLocations().removeAll(shortTermIndex.getRemainingLocations());
		}
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

	private void syncJsonLocation(
			@NonNull final JsonRequestLocationUpsertItem jsonBPartnerLocation,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermLocationIndex shortTermIndex)
	{
		final IdentifierString locationIdentifier = IdentifierString.of(jsonBPartnerLocation.getLocationIdentifier());
		final BPartnerLocation existingLocation = shortTermIndex.extract(locationIdentifier);

		final BPartnerLocation location;
		if (existingLocation != null)
		{
			location = existingLocation;
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException("Missing location with identifier=" + jsonBPartnerLocation.getLocationIdentifier() + " of type=" + locationIdentifier.getType() + "; sync-advise IfNotExists=" + parentSyncAdvise.getIfNotExists());
			}
			else if (Type.METASFRESH_ID.equals(locationIdentifier.getType()))
			{
				throw new MissingResourceException("Missing location with identifier=" + jsonBPartnerLocation.getLocationIdentifier() + " of type=" + locationIdentifier.getType() + "; with this identifier-type, only updates are allowed.");
			}

			location = shortTermIndex.newLocation(locationIdentifier);
		}

		syncJsonToLocation(jsonBPartnerLocation.getLocation(), location, parentSyncAdvise);
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
