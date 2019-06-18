package de.metas.rest_api.bpartner.impl.bpartnercomposite;

import static de.metas.util.Check.isEmpty;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.compiere.util.Env;

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
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerComposite.JsonBPartnerCompositeBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonConverters;
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
	private final transient BPartnerCompositeRepository bpartnerCompositeRepository;
	private final transient BPGroupRepository bpGroupRepository;
	private final transient BPartnerCompositeCache cache;

	@Getter
	private final String identifier;

	public JsonRetrieverService(
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final BPGroupRepository bpGroupRepository,
			@NonNull final String identifier)
	{
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.bpGroupRepository = bpGroupRepository;
		this.identifier = identifier;
		this.cache = new BPartnerCompositeCache(identifier);
	}

	public Optional<JsonBPartnerComposite> retrieveJsonBPartnerComposite(@NonNull final String bpartnerIdentifierStr)
	{
		return retrieveBPartnerComposite(bpartnerIdentifierStr).map(this::toJson);
	}

	public Optional<QueryResultPage<JsonBPartnerComposite>> retrieveJsonBPartnerComposites(
			@Nullable final String nextPageId,
			@Nullable final SinceQuery sinceRequest)
	{
		final QueryResultPage<BPartnerComposite> page;
		if (isEmpty(nextPageId, true))
		{
			page = bpartnerCompositeRepository.getSince(sinceRequest);
		}
		else
		{
			try
			{
				page = bpartnerCompositeRepository.getNextPage(nextPageId);
			}
			catch (final UnknownPageIdentifierException e)
			{
				return Optional.empty();
			}
		}
		final ImmutableList<JsonBPartnerComposite> jsonBPartnerComposites = page
				.getItems()
				.stream()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		return Optional.of(page.withItems(jsonBPartnerComposites));
	}

	private JsonBPartnerComposite toJson(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final JsonBPartnerCompositeBuilder result = JsonBPartnerComposite.builder()
				.bpartner(toJson(bpartnerComposite.getBpartner()));

		for (final BPartnerContact contact : bpartnerComposite.getContacts())
		{
			result.contact(toJson(contact));
		}
		for (final BPartnerLocation location : bpartnerComposite.getLocations())
		{
			result.location(toJson(location));
		}
		return result.build();
	}

	private JsonBPartner toJson(@NonNull final BPartner bpartner)
	{
		final BPGroup bpGroup = bpGroupRepository.getbyId(bpartner.getGroupId());

		return JsonBPartner.builder()
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
				.build();
	}

	private JsonContact toJson(@NonNull final BPartnerContact contact)
	{
		final MetasfreshId metasfreshId = MetasfreshId.of(contact.getId());
		final MetasfreshId metasfreshBPartnerId = MetasfreshId.of(contact.getId().getBpartnerId());

		return JsonContact.builder()
				.email(contact.getEmail())
				.externalId(JsonConverters.toJsonOrNull(contact.getExternalId()))
				.firstName(contact.getFirstName())
				.lastName(contact.getLastName())
				.metasfreshBPartnerId(metasfreshBPartnerId)
				.metasfreshId(metasfreshId)
				.name(contact.getName())
				.phone(contact.getPhone())
				.build();
	}

	private JsonBPartnerLocation toJson(@NonNull final BPartnerLocation location)
	{
		return JsonBPartnerLocation.builder()
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.city(location.getCity())
				.countryCode(location.getCountryCode())
				.district(location.getDistrict())
				.externalId(JsonConverters.toJsonOrNull(location.getExternalId()))
				.gln(location.getGln())
				// .metasfreshBPartnerId(MetasfreshId.of(location.getBpartnerId()))
				.metasfreshId(MetasfreshId.of(location.getId()))
				.poBox(location.getPoBox())
				.postal(location.getPostal())
				.region(location.getRegion())
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

		final BPartnerComposite result = CollectionUtils.singleElementOrNull(allOrLoad); // we made sure there's not more than one in lookupBPartnerByKeys0
		return result == null ? Optional.empty() : Optional.of(result.deepCopy());
	}

	private ImmutableMap<BPartnerCompositeLookupKey, BPartnerComposite> lookupBPartnerByKeys0(
			@NonNull final Collection<BPartnerCompositeLookupKey> bpartnerLookupKeys)
	{
		final BPartnerCompositeQuery query = createBPartnerQuery(bpartnerLookupKeys);

		final QueryResultPage<BPartnerComposite> byQuery = bpartnerCompositeRepository.getByQuery(query);
		if (byQuery.getItems().size() > 1)
		{
			throw new AdempiereException("The given lookup keys needs to yield max one BPartnerComposite; items yielded instead: " + byQuery.getItems().size())
					.appendParametersToMessage()
					.setParameter("BPartnerIdLookupKeys", bpartnerLookupKeys);
		}
		if (byQuery.getItems().isEmpty())
		{
			ImmutableMap.of();
		}

		final ImmutableMap.Builder<BPartnerCompositeLookupKey, BPartnerComposite> result = ImmutableMap.builder();
		for (final BPartnerCompositeLookupKey bpartnerLookupKey : bpartnerLookupKeys)
		{
			result.put(bpartnerLookupKey, byQuery.getItems().get(0));
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


	public Optional<JsonContact> retrieveContact(@NonNull final String contactIdentifierStr)
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
