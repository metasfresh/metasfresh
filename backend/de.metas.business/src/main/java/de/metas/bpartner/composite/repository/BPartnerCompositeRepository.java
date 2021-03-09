package de.metas.bpartner.composite.repository;

import static de.metas.util.Check.isEmpty;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.LogEntriesRepository;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Recent_V;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
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

@Repository
public class BPartnerCompositeRepository
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final LogEntriesRepository recordChangeLogRepository;
	private final BPartnerCompositeCacheById bpartnerCompositeCache = new BPartnerCompositeCacheById();

	public BPartnerCompositeRepository(@NonNull final LogEntriesRepository recordChangeLogRepository)
	{
		this.recordChangeLogRepository = recordChangeLogRepository;
	}

	public BPartnerComposite getById(@NonNull final BPartnerId bpartnerId)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableList.of(bpartnerId)));
	}

	public Optional<BPartnerCompositeAndContactId> getByContact(@NonNull final BPartnerContactQuery contactQuery)
	{
		final BPartnerContactId bpartnerContactId = bpartnersRepo.getBPartnerContactIdBy(contactQuery).orElse(null);
		if (bpartnerContactId == null)
		{
			return Optional.empty();
		}

		final BPartnerComposite bpartnerComposite = getById(bpartnerContactId.getBpartnerId());
		final BPartnerCompositeAndContactId result = BPartnerCompositeAndContactId.of(bpartnerComposite, bpartnerContactId);
		return Optional.of(result);
	}

	public QueryResultPage<BPartnerComposite> getSince(@NonNull final SinceQuery sinceQuery)
	{
		switch (sinceQuery.getSinceEntity())
		{
			case ALL:
				return getCompleteBPartnerCompositsSince(sinceQuery);
			case CONTACT_ONLY:
				return getContactFilteredBPartnerComposites(sinceQuery);
			default:
				throw new AdempiereException("Unexpected sinceEntity=" + sinceQuery.getSinceEntity());
		}
	}

	public QueryResultPage<BPartnerComposite> getNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		switch (nextPageQuery.getSinceEntity())
		{
			case ALL:
				return getCompleteBPartnerCompositesNextPage(nextPageQuery);
			case CONTACT_ONLY:
				return getContactFilteredBPartnerCompositesNextPage(nextPageQuery);
			default:
				throw new AdempiereException("Unexpected sinceEntity=" + nextPageQuery.getSinceEntity());
		}

	}

	private QueryResultPage<BPartnerComposite> getCompleteBPartnerCompositsSince(@NonNull final SinceQuery sinceQuery)
	{
		final QueryResultPage<BPartnerId> page = retrievePageAllEntities(sinceQuery, null);
		return page.mapAllTo(this::getByIds);
	}

	private QueryResultPage<BPartnerComposite> getContactFilteredBPartnerComposites(@NonNull final SinceQuery sinceQuery)
	{
		final QueryResultPage<BPartnerContactId> page = retrievePageOnlyContactEntities(sinceQuery, null);

		return extractContactFilteredBPartnerComposites(page);
	}

	private QueryResultPage<BPartnerComposite> extractContactFilteredBPartnerComposites(@NonNull final QueryResultPage<BPartnerContactId> page)
	{
		final ImmutableSet<BPartnerContactId> contactIdsToReturn = ImmutableSet.copyOf(page.getItems());

		final Set<BPartnerId> bpartnerIds = contactIdsToReturn.stream()
				.map(BPartnerContactId::getBpartnerId)
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableList<BPartnerComposite> bpartnerComposites = getByIds(bpartnerIds);

		for (final BPartnerComposite bpartnerComposite : bpartnerComposites)
		{
			bpartnerComposite.retainContacts(contactIdsToReturn);
		}

		return page.withItems(bpartnerComposites);
	}

	private QueryResultPage<BPartnerComposite> getCompleteBPartnerCompositesNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		final QueryResultPage<BPartnerId> page = retrievePageAllEntities(null, nextPageQuery.getNextPageId());
		return page.mapAllTo(this::getByIds);
	}

	private QueryResultPage<BPartnerComposite> getContactFilteredBPartnerCompositesNextPage(@NonNull final NextPageQuery nextPageQuery)
	{
		final QueryResultPage<BPartnerContactId> page = retrievePageOnlyContactEntities(null, nextPageQuery.getNextPageId());

		return extractContactFilteredBPartnerComposites(page);
	}

	private QueryResultPage<BPartnerId> retrievePageAllEntities(
			@Nullable final SinceQuery sinceQuery,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_C_BPartner_Recent_V> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(sinceQuery.getSinceInstant());
			page = queryBL.createQueryBuilder(I_C_BPartner_Recent_V.class)
					.addCompareFilter(I_C_BPartner_Recent_V.COLUMNNAME_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.create()
					.paginate(I_C_BPartner_Recent_V.class, sinceQuery.getPageSize());
		}
		else
		{
			page = queryBL.retrieveNextPage(I_C_BPartner_Recent_V.class, nextPageId);
		}

		return page.mapTo(this::extractBPartnerId);
	}

	private BPartnerId extractBPartnerId(final I_C_BPartner_Recent_V record)
	{
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	private QueryResultPage<BPartnerContactId> retrievePageOnlyContactEntities(
			@Nullable final SinceQuery sinceQuery,
			@Nullable final String nextPageId)
	{
		final QueryResultPage<I_AD_User> page;
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		if (isEmpty(nextPageId, true))
		{
			final Timestamp timestamp = Timestamp.from(sinceQuery.getSinceInstant());
			page = queryBL
					.createQueryBuilder(I_AD_User.class)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.addCompareFilter(I_AD_User.COLUMNNAME_Updated, Operator.GREATER_OR_EQUAL, timestamp)
					.addNotEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, null)
					.create()
					.paginate(I_AD_User.class, sinceQuery.getPageSize());
		}
		else
		{
			page = queryBL.retrieveNextPage(I_AD_User.class, nextPageId);
		}

		return page.mapTo(record -> BPartnerContactId.ofRepoId(record.getC_BPartner_ID(), record.getAD_User_ID()));
	}

	public ImmutableList<BPartnerComposite> getByQuery(@NonNull final BPartnerQuery query)
	{
		final ImmutableSet<BPartnerId> bpartnerIds = getIdsByQuery(query);
		return getByIds(bpartnerIds);
	}

	public Optional<BPartnerComposite> getSingleByQuery(@NonNull final BPartnerQuery query)
	{
		final ImmutableList<BPartnerComposite> byQuery = getByQuery(query);
		if (byQuery.size() > 1)
		{
			throw new AdempiereException("The given query needs to yield max one BPartnerComposite; items yielded instead: " + byQuery.size())
					.appendParametersToMessage()
					.setParameter("query", query);
		}
		return Optional.ofNullable(CollectionUtils.singleElementOrNull(byQuery));
	}

	private ImmutableSet<BPartnerId> getIdsByQuery(@NonNull final BPartnerQuery query)
	{
		return bpartnersRepo.retrieveBPartnerIdsBy(query);
	}

	public ImmutableList<BPartnerComposite> getByIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		final Collection<BPartnerComposite> bpartnerComposites = bpartnerCompositeCache.getAllOrLoad(bpartnerIds, this::retrieveByIds);

		final ImmutableList.Builder<BPartnerComposite> result = ImmutableList.builder();
		for (final BPartnerComposite bpartnerComposite : bpartnerComposites)
		{
			result.add(bpartnerComposite.deepCopy()); // important because the instance is mutable!
		}

		return result.build();
	}

	private ImmutableMap<BPartnerId, BPartnerComposite> retrieveByIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		final BPartnerCompositesLoader loader = BPartnerCompositesLoader.builder()
				.recordChangeLogRepository(recordChangeLogRepository)
				.build();

		return loader.retrieveByIds(bpartnerIds);
	}

	public void save(@NonNull final BPartnerComposite bpartnerComposite)
	{
		final BPartnerCompositeSaver saver = new BPartnerCompositeSaver();
		saver.save(bpartnerComposite);
	}
}
