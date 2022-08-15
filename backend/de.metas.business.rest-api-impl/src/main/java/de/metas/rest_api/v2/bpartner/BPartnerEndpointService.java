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

package de.metas.rest_api.v2.bpartner;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.RestUtils;
import de.metas.bpartner.composite.repository.NextPageQuery;
import de.metas.bpartner.composite.repository.SinceQuery;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseCompositeList;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseContactList;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.product.v2.response.JsonResponseProductBPartner;
import de.metas.common.rest_api.v2.JsonPagingDescriptor;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.organization.OrgId;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.util.JsonConverters;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Service
@VisibleForTesting
public class BPartnerEndpointService
{
	public static final String SYSCFG_BPARTNER_PAGE_SIZE = "de.metas.rest_api.bpartner.PageSize";
	private final JsonRetrieverService jsonRetriever;

	public BPartnerEndpointService(@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.jsonRetriever = jsonServiceFactory.createRetriever(); // we can have one long-term-instance
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	public Optional<JsonResponseComposite> retrieveBPartner(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return jsonRetriever.getJsonBPartnerComposite(orgId, bpartnerIdentifier);
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	public Optional<JsonResponseLocation> retrieveBPartnerLocation(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final ExternalIdentifier locationIdentifier)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return jsonRetriever.resolveExternalBPartnerLocationId(orgId, bpartnerIdentifier, locationIdentifier);
	}

	/**
	 * @param orgCode @{@code AD_Org.Value} of the bpartner in question. If {@code null}, the system will fall back to the current context-OrgId.
	 */
	public Optional<JsonResponseContact> retrieveBPartnerContact(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier,
			@NonNull final ExternalIdentifier contactIdentifier)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		final Optional<JsonResponseComposite> optBPartnerComposite = jsonRetriever.getJsonBPartnerComposite(orgId, bpartnerIdentifier);

		return optBPartnerComposite.flatMap(jsonResponseComposite -> jsonResponseComposite
				.getContacts()
				.stream()
				.filter(jsonContact -> isJsonContactMatches(orgId, jsonContact, contactIdentifier))
				.findAny());

	}

	@NonNull
	public JsonResponseProductBPartner retrieveBPartnerProduct(
			@Nullable final String orgCode,
			@NonNull final ExternalIdentifier bpartnerIdentifier)
	{
		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(orgCode);

		return jsonRetriever.getJsonResponseProductBPartner(orgId, bpartnerIdentifier);
	}

	private boolean isJsonContactMatches(
			@NonNull final OrgId orgId,
			@NonNull final JsonResponseContact jsonContact,
			@NonNull final ExternalIdentifier contactIdentifier)
	{
		switch (contactIdentifier.getType())
		{
			case EXTERNAL_REFERENCE:
				final Optional<MetasfreshId> metasfreshId =
						jsonRetriever.resolveExternalReference(orgId, contactIdentifier, ExternalUserReferenceType.USER_ID);

				return metasfreshId.isPresent() &&
						MetasfreshId.equals(metasfreshId.get(), MetasfreshId.of(jsonContact.getMetasfreshId()));
			case METASFRESH_ID:
				return MetasfreshId.equals(contactIdentifier.asMetasfreshId(), MetasfreshId.of(jsonContact.getMetasfreshId()));
			default:
				// note: currently, "val-" and GLN are not supported with contacts
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

		final QueryResultPage<JsonResponseComposite> page = jsonRetriever.getJsonBPartnerComposites(nextPageQuery, sinceQuery).orElse(null);
		if (page == null)
		{
			return Optional.empty();
		}

		final ImmutableList<JsonResponseComposite> jsonItems = page.getItems();
		final JsonPagingDescriptor jsonPagingDescriptor = JsonConverters.createJsonPagingDescriptor(page);
		final JsonResponseCompositeList result = JsonResponseCompositeList.ok(jsonPagingDescriptor, jsonItems);

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

		final Optional<QueryResultPage<JsonResponseComposite>> optionalPage = jsonRetriever.getJsonBPartnerComposites(nextPageQuery, sinceQuery);
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

	private static Instant extractInstant(@Nullable final Long epochMilli)
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

	public Optional<JsonResponseContact> retrieveContact(@NonNull final ExternalIdentifier contactIdentifier)
	{
		return jsonRetriever.getContact(contactIdentifier);
	}
}
