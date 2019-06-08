package de.metas.rest_api.bpartner.impl;

import static de.metas.util.Check.isEmpty;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.BPartnerQuery.BPartnerQueryBuilder;
import de.metas.dao.selection.pagination.PageDescriptor;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.dao.selection.pagination.UnknownPageIdentifierException;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.rest_api.JsonPagingDescriptor;
import de.metas.rest_api.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.rest_api.model.I_C_BPartner_Recent_ID;
import de.metas.rest_api.utils.Identifier;
import de.metas.util.Services;
import de.metas.util.rest.ExternalId;
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
public class BPartnerEndpointService implements IBPartnerEndpointService
{

	private final JsonBPartnerCompositeRepository jsonBPartnerCompositeRepository;

	public BPartnerEndpointService(@NonNull final JsonBPartnerCompositeRepository jsonBPartnerCompositeRepository)
	{
		this.jsonBPartnerCompositeRepository = jsonBPartnerCompositeRepository;

	}

	@Override
	public JsonBPartnerComposite retrieveBPartner(@NonNull final String bpartnerIdentifier)
	{
		final BPartnerQueryBuilder query = createBPartnerQuery(bpartnerIdentifier);

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final Optional<BPartnerId> bBartnerId = bPartnerDAO.retrieveBPartnerIdBy(query.build());
		if (!bBartnerId.isPresent())
		{
			return null;
		}

		return jsonBPartnerCompositeRepository.getById(bBartnerId.get());
	}

	private BPartnerQueryBuilder createBPartnerQuery(@NonNull final String bpartnerIdentifier)
	{
		final Identifier identifier = Identifier.of(bpartnerIdentifier);

		final BPartnerQueryBuilder query = BPartnerQuery.builder();
		switch (identifier.getType())
		{
			case EXTERNAL_ID:
				query.externalId(ExternalId.of(identifier.getString()));
				break;
			case VALUE:
				query.bpartnerValue(identifier.getString());
				break;
			case GLN:
				query.locationGln(identifier.getString());
				break;
			case METASFRESH_ID:
				final int repoId = Integer.parseInt(identifier.getString());
				query.bBartnerId(BPartnerId.ofRepoId(repoId));
				break;
			default:
				throw new AdempiereException("Unexpected type=" + identifier.getType());
		}
		return query;
	}

	@Override
	public JsonBPartnerLocation retrieveBPartnerLocation(
			@NonNull final String bpartnerIdentifier,
			@NonNull final String locationIdentifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonContact retrieveBPartnerContact(
			@NonNull final String bpartnerIdentifier,
			@NonNull final String contactIdentifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JsonBPartnerCompositeList> retrieveBPartnersSince(
			final Long epochMilli,
			final String nextPageId)
	{
		final QueryResultPage<I_C_BPartner_Recent_ID> page;
		try
		{
			page = retrievePage(epochMilli, nextPageId);
		}
		catch (UnknownPageIdentifierException e)
		{
			return Optional.empty();
		}

		final JsonPagingDescriptor jsonPagingDescriptor = createJsonPagingDescriptor(page);

		final ImmutableList<JsonBPartnerComposite> jsonBPartners = retrieveJsonBPartnerComposites(page);

		final JsonBPartnerCompositeList result = JsonBPartnerCompositeList.builder()
				.items(jsonBPartners)
				.pagingDescriptor(jsonPagingDescriptor)
				.build();

		return Optional.of(result);
	}

	private QueryResultPage<I_C_BPartner_Recent_ID> retrievePage(Long epochMilli, String nextPageId)
	{
		final QueryResultPage<I_C_BPartner_Recent_ID> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		if (isEmpty(nextPageId, true))
		{
			// TODO: instead of C_BPArtner, load a view that contains *all* the IDs we need to load it all
			page = queryBL.createQueryBuilder(I_C_BPartner_Recent_ID.class)
					.addEqualsFilter(I_C_BPartner_Recent_ID.COLUMNNAME_Updated, Timestamp.from(Instant.ofEpochMilli(epochMilli)))
					.create()
					.paginate(I_C_BPartner_Recent_ID.class, 50);

		}
		else
		{
			page = queryBL.retrieveNextPage(I_C_BPartner_Recent_ID.class, nextPageId);
		}
		return page;
	}

	private ImmutableList<JsonBPartnerComposite> retrieveJsonBPartnerComposites(@NonNull final QueryResultPage<I_C_BPartner_Recent_ID> page)
	{
		final ImmutableList<BPartnerId> bPartnerIds = page
				.getItems()
				.stream()
				.map(i -> BPartnerId.ofRepoId(i.getC_BPartner_ID()))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JsonBPartnerComposite> jsonBPartners = jsonBPartnerCompositeRepository.getByIds(bPartnerIds);
		return jsonBPartners;
	}

	private JsonPagingDescriptor createJsonPagingDescriptor(@NonNull final QueryResultPage<I_C_BPartner_Recent_ID> page)
	{
		final JsonPagingDescriptorBuilder jsonPagingDescriptor = JsonPagingDescriptor.builder()
				.pageSize(page.getItems().size())
				.resultTimestamp(page.getResultTimestamp().toEpochMilli())
				.totalSize(page.getTotalSize());
		final PageDescriptor nextPageDescriptor = page.getNextPageDescriptor();
		if (nextPageDescriptor != null)
		{
			jsonPagingDescriptor.nextPage(nextPageDescriptor.getPageIdentifier().getCombinedUid());
		}
		return jsonPagingDescriptor.build();
	}

	@Override
	public JsonContact retrieveContact(String contactIdentifier)
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<JsonContactList> retrieveContactsSince(Long epochTimestampMillis, String next)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
