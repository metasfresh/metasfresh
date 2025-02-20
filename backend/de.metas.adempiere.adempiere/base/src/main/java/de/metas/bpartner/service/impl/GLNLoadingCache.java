package de.metas.bpartner.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

final class GLNLoadingCache
{
	private final CCache<GLN, GLNLocations> cache = CCache.<GLN, GLNLocations>builder()
			.tableName(I_C_BPartner_Location.Table_Name)
			.build();

	public Optional<BPartnerId> getSingleBPartnerId(@NonNull final GLNQuery glnsQuery)
	{
		final ImmutableSet<BPartnerId> bpartnerIds = getBPartnerIds(glnsQuery);
		if (bpartnerIds.isEmpty())
		{
			return Optional.empty();
		}
		else if (bpartnerIds.size() == 1)
		{
			return Optional.of(bpartnerIds.iterator().next());
		}
		else
		{
			throw new AdempiereException("More than one bpartnerId found: " + bpartnerIds)
					.setParameter("query", glnsQuery)
					.appendParametersToMessage();
		}
	}

	@NonNull
	public Optional<BPartnerLocationId> getSingleBPartnerLocationId(@NonNull final GLNQuery glnsQuery)
	{
		final ImmutableSet<BPartnerLocationId> bPartnerLocationIds = getBPartnerLocationIds(glnsQuery);
		if (bPartnerLocationIds.isEmpty())
		{
			return Optional.empty();
		}
		else if (bPartnerLocationIds.size() == 1)
		{
			return Optional.of(bPartnerLocationIds.iterator().next());
		}
		else
		{
			throw new AdempiereException("More than one BPartnerLocationId found: " + bPartnerLocationIds)
					.appendParametersToMessage()
					.setParameter("query", glnsQuery);
		}
	}

	@NonNull
	public ImmutableSet<BPartnerId> getBPartnerIds(@NonNull final GLNQuery glnsQuery)
	{
		return getBPartnerLocationIds(glnsQuery)
				.stream()
				.map(BPartnerLocationId::getBpartnerId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public ImmutableSet<BPartnerLocationId> getBPartnerLocationIds(@NonNull final GLNQuery glnsQuery)
	{
		final ImmutableSet<OrgId> onlyOrgIds = glnsQuery.getOnlyOrgIds();

		return getGLNLocations(glnsQuery)
				.stream()
				.flatMap(glnLocation -> glnLocation.streamBPartnerLocationIds(onlyOrgIds))
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Collection<GLNLocations> getGLNLocations(@NonNull final GLNQuery glnsQuery)
	{
		final boolean outOfTrx = glnsQuery.isOutOfTrx();
		if (outOfTrx)
		{
			return cache.getAllOrLoad(glnsQuery.getGlns(), glns -> retrieveGLNLocationsMap(glns, outOfTrx));
		}
		else
		{
			return retrieveGLNLocationsMap(glnsQuery.getGlns(), outOfTrx).values();
		}
	}

	private Map<GLN, GLNLocations> retrieveGLNLocationsMap(@NonNull final Collection<GLN> glns, final boolean outOfTrx)
	{
		Check.assumeNotEmpty(glns, "glns is not empty");

		final IQueryBuilder<I_C_BPartner_Location> queryBuilder;
		if (outOfTrx)
		{
			queryBuilder = Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_C_BPartner_Location.class);
		}
		else
		{
			queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Location.class);
		}

		final ImmutableListMultimap<GLN, GLNLocation> locationsByGLN = queryBuilder
				.addInArrayFilter(I_C_BPartner_Location.COLUMN_GLN, GLN.toStringSet(glns))
				.create()
				.stream()
				.map(record -> toGLNLocation(record))
				.collect(GuavaCollectors.toImmutableListMultimap(GLNLocation::getGln));

		return locationsByGLN.asMap()
				.entrySet()
				.stream()
				.map(e -> GLNLocations.builder()
						.gln(e.getKey())
						.locations(e.getValue())
						.build())
				.collect(GuavaCollectors.toImmutableMapByKey(GLNLocations::getGln));
	}

	private static GLNLocation toGLNLocation(final I_C_BPartner_Location record)
	{
		return GLNLocation.builder()
				.gln(GLN.ofString(record.getGLN()))
				.orgId(OrgId.ofRepoIdOrAny(record.getAD_Org_ID()))
				.bpLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.build();
	}

	@Value
	@Builder
	private static class GLNLocations
	{
		@NonNull
		GLN gln;

		@NonNull
		@Singular
		ImmutableList<GLNLocation> locations;

		/**
		 * @param onlyOrgIds {@code null} or empty means "no restriction".
		 */
		@NonNull
		Stream<BPartnerLocationId> streamBPartnerLocationIds(@Nullable final Set<OrgId> onlyOrgIds)
		{
			return locations.stream()
					.filter(location -> location.isMatching(onlyOrgIds))
					.map(GLNLocation::getBpLocationId);
		}
	}

	@Value
	@Builder
	private static class GLNLocation
	{
		@NonNull
		GLN gln;

		@NonNull
		OrgId orgId;

		@NonNull
		BPartnerLocationId bpLocationId;

		boolean isMatching(@Nullable final Set<OrgId> onlyOrgIds)
		{
			if (onlyOrgIds == null || onlyOrgIds.isEmpty())
			{
				return true;
			}
			return onlyOrgIds.contains(orgId);
		}
	}
}
