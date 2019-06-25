package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.service.IOrgDAO;
import org.adempiere.service.IOrgDAO.OrgQuery;
import org.adempiere.service.OrgId;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeRepository;
import de.metas.bpartner.composite.BPartnerCompositeRepository.ContactIdAndBPartner;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactQuery;
import de.metas.bpartner.composite.BPartnerContactQuery.BPartnerContactQueryBuilder;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.i18n.Language;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.rest_api.utils.MissingPropertyException;
import de.metas.rest_api.utils.MissingResourceException;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
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

@ToString
public class JsonPersisterService
{
	@Value
	private static class ShortTermContactIndex
	{
		Map<BPartnerContactId, BPartnerContact> id2Contact;
		Map<ExternalId, BPartnerContact> externalId2Contact;

		BPartnerId bpartnerId;
		BPartnerComposite bpartnerComposite;

		private ShortTermContactIndex(@NonNull final BPartnerComposite bpartnerComposite)
		{
			this.bpartnerComposite = bpartnerComposite;
			this.bpartnerId = bpartnerComposite.getBpartner().getId();  // might be null; we synched to BPartner, but didn't yet save it

			this.id2Contact = new HashMap<>();
			this.externalId2Contact = new HashMap<>();

			for (final BPartnerContact bpartnerContact : bpartnerComposite.getContacts())
			{
				this.id2Contact.put(bpartnerContact.getId(), bpartnerContact);
				this.externalId2Contact.put(bpartnerContact.getExternalId(), bpartnerContact);
			}
		}

		private BPartnerContact extract(@NonNull final JsonRequestContact jsonContact)
		{
			final BPartnerContactId bpartnerContactId;
			if (jsonContact.getMetasfreshId() != null && bpartnerId != null)
			{
				bpartnerContactId = BPartnerContactId.ofRepoId(bpartnerId, jsonContact.getMetasfreshId().getValue());
			}
			else
			{
				bpartnerContactId = null;
			}

			final ExternalId externalId = JsonConverters.fromJsonOrNull(jsonContact.getExternalId());

			final BPartnerContact existingContact = coalesceSuppliers(
					() -> id2Contact.get(bpartnerContactId),
					() -> externalId2Contact.get(externalId));
			return existingContact;
		}

		private Collection<BPartnerContact> getRemainingContacts()
		{
			return id2Contact.values();
		}

		private void remove(@NonNull final BPartnerContactId bpartnerContactId)
		{
			id2Contact.remove(bpartnerContactId);
		}
	}

	@Value
	private static class ShortTermLocationIndex
	{
		Map<BPartnerLocationId, BPartnerLocation> id2Location;
		Map<ExternalId, BPartnerLocation> externalId2Location;
		Map<String, BPartnerLocation> gln2Location;
		BPartnerId bpartnerId;
		BPartnerComposite bpartnerComposite;

		private ShortTermLocationIndex(@NonNull final BPartnerComposite bpartnerComposite)
		{
			this.bpartnerComposite = bpartnerComposite;
			this.bpartnerId = bpartnerComposite.getBpartner().getId(); // might be null; we synched to BPartner, but didn't yet save it
			this.id2Location = new HashMap<>();
			this.externalId2Location = new HashMap<>();
			this.gln2Location = new HashMap<>();

			for (final BPartnerLocation bpartnerLocation : bpartnerComposite.getLocations())
			{
				this.id2Location.put(bpartnerLocation.getId(), bpartnerLocation);
				this.externalId2Location.put(bpartnerLocation.getExternalId(), bpartnerLocation);
				this.gln2Location.put(bpartnerLocation.getGln(), bpartnerLocation);
			}
		}

		private BPartnerLocation extract(@NonNull final JsonRequestLocation jsonBPartnerLocation)
		{
			final BPartnerLocationId bpartnerLocationId;
			if (jsonBPartnerLocation.getMetasfreshId() != null && bpartnerId != null)
			{
				bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, jsonBPartnerLocation.getMetasfreshId().getValue());
			}
			else
			{
				bpartnerLocationId = null;
			}

			final ExternalId externalId = JsonConverters.fromJsonOrNull(jsonBPartnerLocation.getExternalId());

			final String gln = jsonBPartnerLocation.getGln();

			final BPartnerLocation existingLocation = coalesceSuppliers(
					() -> id2Location.get(bpartnerLocationId),
					() -> externalId2Location.get(externalId),
					() -> gln2Location.get(gln));
			return existingLocation;
		}

		private Collection<BPartnerLocation> getRemainingLocations()
		{
			return id2Location.values();
		}

		private void remove(@NonNull final BPartnerLocationId bpartnerLocationId)
		{
			id2Location.remove(bpartnerLocationId);
		}
	}

	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;

	private final transient BPGroupRepository bpGroupRepository;

	private final JsonRetrieverService jsonRetrieverService;

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

	private BPartnerContactQuery createContactQuery(@NonNull final JsonRequestContact contact)
	{
		final BPartnerContactQueryBuilder contactQuery = BPartnerContactQuery.builder();

		if (contact.getMetasfreshId() != null)
		{
			final UserId userId = UserId.ofRepoIdOrNull(contact.getMetasfreshId().getValue());
			contactQuery.userId(userId);
		}
		contactQuery.externalId(JsonConverters.fromJsonOrNull(contact.getExternalId()));
		contactQuery.value(contact.getCode());

		return contactQuery.build();
	}

	private ImmutableList<BPartnerCompositeLookupKey> extractBPartnerLookupKeys(@NonNull final JsonRequestComposite jsonBPartnerComposite)
	{
		final ImmutableList.Builder<BPartnerCompositeLookupKey> result = ImmutableList.builder();

		final String code = jsonBPartnerComposite.getBpartner().getCode();
		if (!isEmpty(code, true))
		{
			result.add(BPartnerCompositeLookupKey.ofCode(code));
		}

		final List<String> locationGlns = jsonBPartnerComposite.extractLocationGlns();
		for (final String locationGln : locationGlns)
		{
			result.add(BPartnerCompositeLookupKey.ofGln(locationGln));
		}

		final JsonExternalId externalId = jsonBPartnerComposite.getBpartner().getExternalId();
		if (externalId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofJsonExternalId(externalId));
		}

		final MetasfreshId metasfreshId = jsonBPartnerComposite.getBpartner().getMetasfreshId();
		if (metasfreshId != null)
		{
			result.add(BPartnerCompositeLookupKey.ofMetasfreshId(metasfreshId));
		}

		return result.build();
	}

	public BPartnerComposite persist(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final SyncAdvise defaultSyncAdvise)
	{
		final ImmutableList<BPartnerCompositeLookupKey> bpartnerLookupKeys = extractBPartnerLookupKeys(jsonBPartnerComposite);

		final Optional<BPartnerComposite> optionalBPartnerComposite = jsonRetrieverService.retrieveBPartnerComposite(bpartnerLookupKeys);

		final SyncAdvise effectiveSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), defaultSyncAdvise);

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
				throw new MissingResourceException("bpartner");
			}
			// create new aggregation root
			bpartnerComposite = BPartnerComposite.builder().build();
		}

		syncJsonToBPartnerComposite(jsonBPartnerComposite, bpartnerComposite, effectiveSyncAdvise);

		return bpartnerComposite;
	}

	public BPartnerContact persist(
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final BPartnerContactQuery contactQuery = createContactQuery(jsonContact);
		final Optional<ContactIdAndBPartner> optionalContactIdAndBPartner = bpartnerCompositeRepository.getByContact(contactQuery);

		final BPartnerContact contact;
		final BPartnerComposite bpartnerComposite;
		if (optionalContactIdAndBPartner.isPresent())
		{
			final ContactIdAndBPartner contactIdAndBPartner = optionalContactIdAndBPartner.get();

			bpartnerComposite = contactIdAndBPartner.getBpartnerComposite();

			contact = bpartnerComposite
					.getContact(contactIdAndBPartner.getBpartnerContactId())
					.get();
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException("jsonContact");
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

	/** Adds or update a given location. Leaves all unrelated location of the same bpartner untouched */
	public Optional<MetasfreshId> persist(
			@NonNull final String bpartnerIdentifierStr,
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final SyncAdvise defaultSyncAdvise)
	{
		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.retrieveBPartnerComposite(bpartnerIdentifierStr);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final ExternalId externalId = JsonConverters.fromJsonOrNull(jsonBPartnerLocation.getExternalId());
		if (externalId == null)
		{
			throw new MissingPropertyException("externalId", jsonBPartnerLocation);
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		syncJsonLocation(jsonBPartnerLocation, defaultSyncAdvise, shortTermIndex);

		bpartnerCompositeRepository.save(bpartnerComposite);

		final BPartnerLocation bpartnerLocation = bpartnerComposite
				.extractLocation(externalId)
				.get(); // it's there; we just made sure

		return Optional.of(MetasfreshId.of(bpartnerLocation.getId()));
	}

	public Optional<MetasfreshId> persist(
			@NonNull final String bpartnerIdentifierStr,
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise defaultSyncAdvise)
	{
		final Optional<BPartnerComposite> optBPartnerComposite = jsonRetrieverService.retrieveBPartnerComposite(bpartnerIdentifierStr);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty(); // 404
		}

		final ExternalId externalId = JsonConverters.fromJsonOrNull(jsonContact.getExternalId());
		if (externalId == null)
		{
			throw new MissingPropertyException("jsonContact.externalId", jsonContact);
		}

		final BPartnerComposite bpartnerComposite = optBPartnerComposite.get();
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		syncJsonContact(jsonContact, defaultSyncAdvise, shortTermIndex);

		bpartnerCompositeRepository.save(bpartnerComposite);

		final BPartnerContact bpartnerContact = bpartnerComposite
				.extractContact(externalId)
				.get(); // it's there; we just made sure

		return Optional.of(MetasfreshId.of(bpartnerContact.getId()));
	}

	private void syncJsonContact(
			@NonNull final JsonRequestContact jsonContact,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermContactIndex shortTermIndex)
	{
		final BPartnerContact existingContact = shortTermIndex.extract(jsonContact);

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
				throw new MissingResourceException("jsonContact");
			}
			contact = BPartnerContact.builder().build();
			shortTermIndex.getBpartnerComposite().getContacts().add(contact);
		}
		syncJsonToContact(jsonContact, contact, parentSyncAdvise);
	}

	private void syncJsonLocation(
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final SyncAdvise parentSyncAdvise,
			@NonNull final ShortTermLocationIndex shortTermIndex)
	{
		final BPartnerLocation existingLocation = shortTermIndex.extract(jsonBPartnerLocation);

		final BPartnerLocation location;
		if (existingLocation != null)
		{
			location = existingLocation;
			shortTermIndex.remove(existingLocation.getId());
		}
		else
		{
			if (parentSyncAdvise.isFailIfNotExists())
			{
				throw new MissingResourceException("bpartnerLocation");
			}
			location = BPartnerLocation.builder().build();
			shortTermIndex.getBpartnerComposite().getLocations().add(location);
		}

		syncJsonToLocation(jsonBPartnerLocation, location, parentSyncAdvise);
	}

	private void syncJsonToBPartnerComposite(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		syncJsonToOrg(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToBPartner(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToLocations(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

		syncJsonToContacts(jsonBPartnerComposite, bpartnerComposite, parentSyncAdvise);

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

		// name
		if (!isEmpty(jsonBPartner.getName(), true))
		{
			bpartner.setName(jsonBPartner.getName().trim());
		}
		else if (isUpdateRemove)
		{
			bpartner.setName(null);
		}

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
	}

	private void syncJsonToContact(
			@NonNull final JsonRequestContact jsonBPartnerContact,
			@NonNull final BPartnerContact contact,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final SyncAdvise syncAdvise = coalesce(jsonBPartnerContact.getSyncAdvise(), parentSyncAdvise);
		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

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
		if (jsonBPartnerContact.getFirstName() != null)
		{
			contact.setFirstName(jsonBPartnerContact.getFirstName().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setFirstName(null);
		}

		// lastName
		if (jsonBPartnerContact.getLastName() != null)
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
		if (jsonBPartnerContact.getName() != null)
		{
			contact.setName(jsonBPartnerContact.getName().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setName(null);
		}

		// phone
		if (jsonBPartnerContact.getPhone() != null)
		{
			contact.setPhone(jsonBPartnerContact.getPhone().trim());
		}
		else if (isUpdateRemove)
		{
			contact.setPhone(null);
		}
	}

	private void syncJsonToContacts(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermContactIndex shortTermIndex = new ShortTermContactIndex(bpartnerComposite);

		final SyncAdvise compositeSyncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		for (final JsonRequestContact jsonBPartnerContact : jsonBPartnerComposite.getContacts())
		{
			syncJsonContact(jsonBPartnerContact, compositeSyncAdvise, shortTermIndex);
		}

		if (compositeSyncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getContacts().removeAll(shortTermIndex.getRemainingContacts());
		}
	}

	private void syncJsonToLocation(
			@NonNull final JsonRequestLocation jsonBPartnerLocation,
			@NonNull final BPartnerLocation location,
			@NonNull final SyncAdvise parentSyncAdvise)
	{

		final SyncAdvise syncAdvise = coalesce(jsonBPartnerLocation.getSyncAdvise(), parentSyncAdvise);

		final boolean isUpdateRemove = syncAdvise.getIfExists().isUpdateRemove();

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
		if (!isEmpty(jsonBPartnerLocation.getGln(), true))
		{
			location.setGln(jsonBPartnerLocation.getGln().trim());
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
	}

	private void syncJsonToLocations(
			@NonNull final JsonRequestComposite jsonBPartnerComposite,
			@NonNull final BPartnerComposite bpartnerComposite,
			@NonNull final SyncAdvise parentSyncAdvise)
	{
		final ShortTermLocationIndex shortTermIndex = new ShortTermLocationIndex(bpartnerComposite);

		final SyncAdvise syncAdvise = coalesce(jsonBPartnerComposite.getSyncAdvise(), parentSyncAdvise);

		for (final JsonRequestLocation jsonBPartnerLocation : jsonBPartnerComposite.getLocations())
		{
			syncJsonLocation(jsonBPartnerLocation, syncAdvise, shortTermIndex);
		}

		if (syncAdvise.getIfExists().isUpdateRemove())
		{
			// deactivate the remaining bpartner locations that we did not see
			bpartnerComposite.getLocations().removeAll(shortTermIndex.getRemainingLocations());
		}
	}
}
