package de.metas.rest_api.utils;

import static de.metas.util.Check.isEmpty;
import java.util.Collection;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.BPartnerQuery.BPartnerQueryBuilder;
import de.metas.organization.OrgId;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
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
public class BPartnerQueryService
{
	public BPartnerQuery createQuery(@NonNull final OrgAndBPartnerCompositeLookupKeyList queryLookupKeys)
	{
		return createBPartnerQuery(queryLookupKeys.getCompositeLookupKeys(), queryLookupKeys.getOrgId());
	}

	public BPartnerQuery createQuery(@NonNull final Collection<BPartnerCompositeLookupKey> queryLookupKeys, @NonNull final OrgId onlyOrgId)
	{
		return createBPartnerQuery(queryLookupKeys, onlyOrgId);
	}

	/** Creates a query that advises the repo to fail if no matching bpartner is found. */
	public BPartnerQuery createQueryFailIfNotExists(@NonNull final BPartnerCompositeLookupKey queryLookupKey)
	{
		return createQueryFailIfNotExists(queryLookupKey, null/* orgId */);
	}

	public BPartnerQuery createQueryFailIfNotExists(
			@NonNull final BPartnerCompositeLookupKey queryLookupKey,
			@Nullable final OrgId orgId)
	{
		final BPartnerQueryBuilder queryBuilder = BPartnerQuery.builder()
				.failIfNotExists(true);
		if (orgId != null)
		{
			queryBuilder.onlyOrgId(orgId);
		}

		addKeyToQueryBuilder(queryLookupKey, queryBuilder);

		return queryBuilder.build();
	}

	private static BPartnerQuery createBPartnerQuery(
			@NonNull final Collection<BPartnerCompositeLookupKey> bpartnerLookupKeys,
			@Nullable final OrgId onlyOrgId)
	{
		final BPartnerQueryBuilder query = BPartnerQuery.builder();
		if (onlyOrgId != null)
		{
			query.onlyOrgId(onlyOrgId);
		}

		for (final BPartnerCompositeLookupKey bpartnerLookupKey : bpartnerLookupKeys)
		{
			addKeyToQueryBuilder(bpartnerLookupKey, query);
		}

		return query.build();
	}

	private static void addKeyToQueryBuilder(final BPartnerCompositeLookupKey bpartnerLookupKey, final BPartnerQueryBuilder queryBuilder)
	{
		final JsonExternalId jsonExternalId = bpartnerLookupKey.getJsonExternalId();
		if (jsonExternalId != null)
		{
			queryBuilder.externalId(JsonConverters.fromJsonOrNull(jsonExternalId));
		}

		final String value = bpartnerLookupKey.getCode();
		if (!isEmpty(value, true))
		{
			queryBuilder.bpartnerValue(value);
		}

		final GLN gln = bpartnerLookupKey.getGln();
		if (gln != null)
		{
			queryBuilder.gln(gln);
		}

		final MetasfreshId metasfreshId = bpartnerLookupKey.getMetasfreshId();
		if (metasfreshId != null)
		{
			queryBuilder.bPartnerId(BPartnerId.ofRepoId(metasfreshId.getValue()));
		}
	}

}
