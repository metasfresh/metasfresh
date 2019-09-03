package de.metas.rest_api.bpartner.impl;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.jgoodies.common.base.Objects;

import de.metas.bpartner.composite.BPartnerCompositeRepository.NextPageQuery;
import de.metas.bpartner.composite.BPartnerCompositeRepository.SinceQuery;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.bpartner.response.JsonResponseComposite;
import de.metas.rest_api.bpartner.response.JsonResponseCompositeList;
import de.metas.rest_api.bpartner.response.JsonResponseContact;
import de.metas.rest_api.bpartner.response.JsonResponseContactList;
import de.metas.rest_api.bpartner.response.JsonResponseLocation;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.util.Services;
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

@Service
class BPartnerEndpointService
{
	public static final String SYSCFG_BPARTNER_PAGE_SIZE = "de.metas.rest_api.bpartner.PageSize";
	private final JsonRetrieverService jsonRetriever;

	public BPartnerEndpointService(@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.jsonRetriever = jsonServiceFactory.createRetriever(); // we can have one long-term-instance
	}

	public Optional<JsonResponseComposite> retrieveBPartner(@NonNull final IdentifierString bpartnerIdentifier)
	{
		final Optional<JsonResponseComposite> optBpartnerComposite = jsonRetriever.retrieveJsonBPartnerComposite(bpartnerIdentifier);
		return optBpartnerComposite;
	}

	public Optional<JsonResponseLocation> retrieveBPartnerLocation(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final IdentifierString locationIdentifier)
	{
		final Optional<JsonResponseComposite> optBpartnerComposite = jsonRetriever.retrieveJsonBPartnerComposite(bpartnerIdentifier);
		if (!optBpartnerComposite.isPresent())
		{
			return Optional.empty();
		}

		final Optional<JsonResponseLocation> result = optBpartnerComposite.get()
				.getLocations()
				.stream()
				.filter(l -> isBPartnerLocationMatches(l, locationIdentifier))
				.findAny();
		return result;
	}

	private boolean isBPartnerLocationMatches(
			@NonNull final JsonResponseLocation jsonBPartnerLocation,
			@NonNull final IdentifierString locationIdentifier)
	{
		switch (locationIdentifier.getType())
		{
			case EXTERNAL_ID:
				return Objects.equals(jsonBPartnerLocation.getExternalId(), locationIdentifier.asJsonExternalId());
			case GLN:
				return Objects.equals(jsonBPartnerLocation.getGln(), locationIdentifier.asGLN());
			case METASFRESH_ID:
				return Objects.equals(locationIdentifier.asMetasfreshId(), jsonBPartnerLocation.getMetasfreshId());
			default:
				// note: currently, "val-" is not supported with bpartner locations
				throw new AdempiereException("Unexpected type=" + locationIdentifier.getType());
		}
	}

	public Optional<JsonResponseContact> retrieveBPartnerContact(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final IdentifierString contactIdentifier)
	{
		final Optional<JsonResponseComposite> optBPartnerComposite = jsonRetriever.retrieveJsonBPartnerComposite(bpartnerIdentifier);
		if (!optBPartnerComposite.isPresent())
		{
			return Optional.empty();
		}

		return optBPartnerComposite.get()
				.getContacts()
				.stream()
				.filter(l -> isJsonContactMatches(l, contactIdentifier))
				.findAny();
	}

	private boolean isJsonContactMatches(
			@NonNull final JsonResponseContact jsonContact,
			@NonNull final IdentifierString contactIdentifier)
	{
		switch (contactIdentifier.getType())
		{
			case EXTERNAL_ID:
				return Objects.equals(jsonContact.getExternalId(), contactIdentifier.asJsonExternalId());
			case METASFRESH_ID:
				final MetasfreshId contactIdentifierMetasfreshId = contactIdentifier.asMetasfreshId();
				final MetasfreshId contactMetasfreshId = jsonContact.getMetasfreshId();
				return Objects.equals(contactIdentifierMetasfreshId, contactMetasfreshId);
			default:
				// note: currently, "val-" and GLN are supported with contacts
				throw new AdempiereException("Unexpected type=" + contactIdentifier.getType());
		}
	}

	public Optional<JsonResponseCompositeList> retrieveBPartnersSince(
			@Nullable final Long epochMilli,
			@Nullable final String nextPageId)
	{
		final SinceQuery sinceQuery = SinceQuery.anyEntity(
				extractInstant(epochMilli),
				getPageSize());

		final NextPageQuery nextPageQuery = NextPageQuery.anyEntityOrNull(nextPageId);

		final Optional<QueryResultPage<JsonResponseComposite>> optionalPage = jsonRetriever.retrieveJsonBPartnerComposites(nextPageQuery, sinceQuery);
		if (!optionalPage.isPresent())
		{
			return Optional.empty();
		}

		final QueryResultPage<JsonResponseComposite> page = optionalPage.get();

		final ImmutableList<JsonResponseComposite> jsonItems = page
				.getItems()
				.stream()
				.collect(ImmutableList.toImmutableList());

		final JsonPagingDescriptor jsonPagingDescriptor = JsonConverters.createJsonPagingDescriptor(page);

		final JsonResponseCompositeList result = JsonResponseCompositeList.builder()
				.items(jsonItems)
				.pagingDescriptor(jsonPagingDescriptor)
				.build();

		return Optional.of(result);
	}

	public Optional<JsonResponseContactList> retrieveContactsSince(
			@Nullable final Long epochMilli,
			@Nullable final String nextPageId)
	{
		final SinceQuery sinceQuery = SinceQuery.onlyContacts(
				extractInstant(epochMilli),
				getPageSize());

		final NextPageQuery nextPageQuery = NextPageQuery.onlyContactsOrNull(nextPageId);

		final Optional<QueryResultPage<JsonResponseComposite>> optionalPage = jsonRetriever.retrieveJsonBPartnerComposites(nextPageQuery, sinceQuery);
		if (!optionalPage.isPresent())
		{
			return Optional.empty();
		}

		final QueryResultPage<JsonResponseComposite> page = optionalPage.get();

		final ImmutableList<JsonResponseContact> jsonContacts = page
				.getItems()
				.stream()
				.flatMap(bpc -> bpc.getContacts().stream())
				.collect(ImmutableList.toImmutableList());

		final QueryResultPage<JsonResponseContact> sizeAdjustedPage = page.withItems(jsonContacts);
		final JsonPagingDescriptor jsonPagingDescriptor = JsonConverters.createJsonPagingDescriptor(sizeAdjustedPage);

		final JsonResponseContactList result = JsonResponseContactList.builder()
				.contacts(jsonContacts)
				.pagingDescriptor(jsonPagingDescriptor)
				.build();

		return Optional.of(result);
	}

	private Instant extractInstant(@Nullable final Long epochMilli)
	{
		if (epochMilli == null || epochMilli <= 0)
		{
			return Instant.EPOCH;
		}
		return Instant.ofEpochMilli(epochMilli);
	}

	private int getPageSize()
	{
		return Services.get(ISysConfigBL.class).getIntValue(
				SYSCFG_BPARTNER_PAGE_SIZE,
				50,
				Env.getAD_Client_ID(),
				Env.getOrgId().getRepoId());
	}

	public Optional<JsonResponseContact> retrieveContact(@NonNull final IdentifierString contactIdentifier)
	{
		return jsonRetriever.retrieveContact(contactIdentifier);
	}
}
