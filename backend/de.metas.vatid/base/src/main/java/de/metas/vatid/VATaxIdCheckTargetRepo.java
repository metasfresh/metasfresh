/*
 * #%L
 * metasfresh-vatid-base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.vatid;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.X_C_BPartner;
import org.compiere.model.X_C_BPartner_Location;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Repository
public class VATaxIdCheckTargetRepo
{
	/**
	 * Matches the scheduler's default MaxChecksPerRun, so a full-budget run pages once per grain.
	 */
	private static final int ITERATOR_BUFFER_SIZE = 500;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	/**
	 * @return how many records {@link #iterateBPartnersDueForVATaxIDCheck(OrgId, Instant, int)} would yield.
	 */
	public int countBPartnersDueForVATaxIDCheck(@NonNull final OrgId orgId,
												@Nullable final Instant lastCheckedBefore)
	{
		return createBPartnersDueForVATaxIDCheckQuery(orgId, lastCheckedBefore).count();
	}

	/**
	 * Walks the {@code C_BPartner} records of {@code orgId} that are due a VAT-ID online check, oldest
	 * attempt first ({@code VATaxIDLastAttemptedAt} ascending, nulls — never attempted — first).
	 *
	 * <p>Due means: non-blank header {@code VATaxID}, and either no conclusive status yet or a
	 * {@code VATaxIDCheckedAt} older than {@code lastCheckedBefore}. Pass {@code lastCheckedBefore == null} to mean
	 * "no staleness window", i.e. every VAT-ID-bearing record of the organisation is due.
	 *
	 * @return a guaranteed iterator
	 */
	public Iterator<I_C_BPartner> iterateBPartnersDueForVATaxIDCheck(
			@NonNull final OrgId orgId,
			@Nullable final Instant lastCheckedBefore,
			final int maxChecksPerRun)
	{
		final IQuery<I_C_BPartner> query = createBPartnersDueForVATaxIDCheckQuery(orgId, lastCheckedBefore);

		final int effectiveBuffersize = computeEffectiveBuffersize(maxChecksPerRun);

		query.setOption(IQuery.OPTION_IteratorBufferSize, effectiveBuffersize);

		return query.iterateWithGuaranteedIterator(I_C_BPartner.class);
	}

	private static int computeEffectiveBuffersize(final int maxChecksPerRun)
	{
		final int effectiveBuffersize;
		if (maxChecksPerRun > 0)
		{
			effectiveBuffersize = Math.min(ITERATOR_BUFFER_SIZE, maxChecksPerRun);
		}
		else
		{
			effectiveBuffersize = ITERATOR_BUFFER_SIZE;
		}
		return effectiveBuffersize;
	}

	@NonNull
	private IQuery<I_C_BPartner> createBPartnersDueForVATaxIDCheckQuery(@NonNull final OrgId orgId,
																		@Nullable final Instant lastCheckedBefore)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgId)
				.addNotNull(I_C_BPartner.COLUMNNAME_VATaxID)
				.addNotEqualsFilter(I_C_BPartner.COLUMNNAME_VATaxID, "");

		if (lastCheckedBefore != null)
		{
			// A null VATaxIDStatus counts as never-checked, matching the caller's own resolveStatus.
			queryBuilder.filter(queryBL.createCompositeQueryFilter(I_C_BPartner.class).setJoinOr()
					.addEqualsFilter(I_C_BPartner.COLUMNNAME_VATaxIDStatus, null)
					.addEqualsFilter(I_C_BPartner.COLUMNNAME_VATaxIDStatus, X_C_BPartner.VATAXIDSTATUS_NotChecked)
					.addEqualsFilter(I_C_BPartner.COLUMNNAME_VATaxIDCheckedAt, null)
					.addCompareFilter(I_C_BPartner.COLUMNNAME_VATaxIDCheckedAt, CompareQueryFilter.Operator.LESS, Timestamp.from(lastCheckedBefore)));
		}

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner.COLUMNNAME_VATaxIDLastAttemptedAt, IQueryOrderBy.Direction.Ascending, IQueryOrderBy.Nulls.First)
				.addColumn(I_C_BPartner.COLUMNNAME_C_BPartner_ID);

		return queryBuilder.create();
	}

	/**
	 * The {@code C_BPartner_Location} counterpart of
	 * {@link #iterateBPartnersDueForVATaxIDCheck(OrgId, Instant, int)}, scoped by the LOCATION's own
	 * {@code AD_Org_ID} — the organisation whose configuration governs a location's check.
	 */
	public Iterator<I_C_BPartner_Location> iterateBPartnerLocationsDueForVATaxIDCheck(@NonNull final OrgId orgId,
																					  @Nullable final Instant lastCheckedBefore,
																					  final int maxChecksPerRun)
	{
		final int effectiveBuffersize = computeEffectiveBuffersize(maxChecksPerRun);

		final IQuery<I_C_BPartner_Location> query = createBPartnerLocationsDueForVATaxIDCheckQuery(orgId, lastCheckedBefore)
				.setOption(IQuery.OPTION_IteratorBufferSize, effectiveBuffersize);
		
		return query.iterateWithGuaranteedIterator(I_C_BPartner_Location.class);
	}

	/**
	 * @return how many records {@link #iterateBPartnerLocationsDueForVATaxIDCheck(OrgId, Instant, int)} would yield.
	 */
	public int countBPartnerLocationsDueForVATaxIDCheck(@NonNull final OrgId orgId, @Nullable final Instant lastCheckedBefore)
	{
		return createBPartnerLocationsDueForVATaxIDCheckQuery(orgId, lastCheckedBefore).count();
	}

	@NonNull
	private IQuery<I_C_BPartner_Location> createBPartnerLocationsDueForVATaxIDCheckQuery(@NonNull final OrgId orgId, @Nullable final Instant lastCheckedBefore)
	{
		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_AD_Org_ID, orgId)
				.addNotNull(I_C_BPartner_Location.COLUMNNAME_VATaxID)
				.addNotEqualsFilter(I_C_BPartner_Location.COLUMNNAME_VATaxID, "");

		if (lastCheckedBefore != null)
		{
			queryBuilder.filter(queryBL.createCompositeQueryFilter(I_C_BPartner_Location.class).setJoinOr()
					.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_VATaxIDStatus, null)
					.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_VATaxIDStatus, X_C_BPartner_Location.VATAXIDSTATUS_NotChecked)
					.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_VATaxIDCheckedAt, null)
					.addCompareFilter(I_C_BPartner_Location.COLUMNNAME_VATaxIDCheckedAt, CompareQueryFilter.Operator.LESS, Timestamp.from(lastCheckedBefore)));
		}

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_VATaxIDLastAttemptedAt, IQueryOrderBy.Direction.Ascending, IQueryOrderBy.Nulls.First)
				.addColumn(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);

		return queryBuilder.create();
	}

	/**
	 * Stamps {@code C_BPartner.VATaxIDLastAttemptedAt} unconditionally, whether the check that follows
	 * succeeds, fails or throws — unlike {@code VATaxIDCheckedAt}, which advances only on a completed check.
	 * Without it a permanently failing target would never advance any timestamp and would sort first of
	 * every nightly run forever. The caller must commit this in its own transaction so it survives the
	 * check's rollback.
	 */
	public void stampVATaxIDCheckAttempt(@NonNull final BPartnerId bpartnerId, @NonNull final Instant attemptedAt)
	{
		final I_C_BPartner bpartnerRecord = load(bpartnerId, I_C_BPartner.class);
		bpartnerRecord.setVATaxIDLastAttemptedAt(TimeUtil.asTimestampNotNull(attemptedAt));
		InterfaceWrapperHelper.saveRecord(bpartnerRecord);
	}

	/**
	 * The {@code C_BPartner_Location} counterpart of {@link #stampVATaxIDCheckAttempt(BPartnerId, Instant)}.
	 */
	public void stampVATaxIDCheckAttempt(@NonNull final BPartnerLocationId bpartnerLocationId, @NonNull final Instant attemptedAt)
	{
		final I_C_BPartner_Location bpartnerLocationRecord = load(bpartnerLocationId, I_C_BPartner_Location.class);
		bpartnerLocationRecord.setVATaxIDLastAttemptedAt(TimeUtil.asTimestampNotNull(attemptedAt));
		InterfaceWrapperHelper.saveRecord(bpartnerLocationRecord);
	}

	@VisibleForTesting
	ImmutableList<I_C_BPartner_Location> retrieveBPartnerLocationsWithVATaxID(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_BPartner_Location.COLUMNNAME_VATaxID)
				.addNotEqualsFilter(I_C_BPartner_Location.COLUMNNAME_VATaxID, "")
				.orderBy(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID)
				.orderBy(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID)
				.create()
				.listImmutable(I_C_BPartner_Location.class);
	}


	/**
	 * @return every VAT-ID to check for {@code selectedBPartnerIds}: the header of every one of them that
	 * carries one, plus every {@code C_BPartner_Location} of every one of them that carries one —
	 * regardless of whether that location's own partner header has a VAT-ID (see the class Javadoc,
	 * "Selecting a partner also covers its locations").
	 *
	 * <p>Ordered per {@code selectedBPartnerIds}'s own order and, within one partner, the header before
	 * that partner's locations (themselves ordered by {@code C_BPartner_Location_ID}) — see the class
	 * Javadoc, "Ordering". A partner with neither its own VAT-ID nor any location VAT-ID contributes
	 * nothing and is not counted towards either the checked or the pending count.
	 */
	@NonNull
	public ImmutableList<CheckTarget> retrieveCheckTargets(@NonNull final ImmutableList<BPartnerId> selectedBPartnerIds)
	{
		
		// The persistence access to C_BPartner / C_BPartner_Location belongs on IBPartnerDAO, which already
		// owns it (getByIds, retrieveBPartnerLocationsWithVATaxID, ...) — this service must not build its
		// own IQueryBL query for another module's table (docs/REVIEW.md).
		final ImmutableMap<BPartnerId, I_C_BPartner> selectedBPartnersById = bpartnerDAO.getByIds(selectedBPartnerIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()),
						Function.identity()));

		final ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> locationsByBPartnerId = retrieveBPartnerLocationsWithVATaxID(selectedBPartnerIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						locationRecord -> BPartnerId.ofRepoId(locationRecord.getC_BPartner_ID()),
						Function.identity()));

		final ImmutableList.Builder<CheckTarget> checkTargets = ImmutableList.builder();
		for (final BPartnerId bpartnerId : selectedBPartnerIds)
		{
			final I_C_BPartner bpartnerRecord = selectedBPartnersById.get(bpartnerId);
			if (bpartnerRecord == null)
			{
				// Selected by the caller but no longer resolvable by the time this run fetched it (e.g. the
				// record was deleted in between) — nothing to check for it, and skipping is the only way to
				// avoid an NPE from a null bpartnerRecord below.
				continue;
			}

			if (!Check.isEmpty(bpartnerRecord.getVATaxID()))
			{
				checkTargets.add(CheckTarget.ofPartner(bpartnerId, bpartnerRecord));
			}

			for (final I_C_BPartner_Location bpartnerLocationRecord : locationsByBPartnerId.get(bpartnerId))
			{
				checkTargets.add(CheckTarget.ofLocation(bpartnerLocationRecord));
			}
		}
		return checkTargets.build();
	}

	/**
	 * One VAT-ID to check: either a partner header ({@link #bpartnerLocationId} {@code null}) or one of its locations. 
	 */
	@Value
	@Builder
	public static class CheckTarget
	{
		@NonNull BPartnerId bpartnerId;

		@Nullable BPartnerLocationId bpartnerLocationId;

		@NonNull VATIdentifier vataxID;

		@NonNull VATaxIDStatus previousStatus;

		/**
		 * When this record's status was last successfully determined — {@code null} if it never was. See
		 * the class javadoc, "Starvation guard": this is DIFFERENT from {@link #lastAttemptedAt}, which
		 * advances on every attempt regardless of outcome.
		 */
		@Nullable Instant checkedAt;

		/**
		 * When this record's check was last ATTEMPTED, regardless of outcome — {@code null} if it never
		 * was. Used by the nightly query's ordering (oldest attempt first, never-attempted first) to
		 * run's targets; unrelated to {@link #checkedAt}. See the class javadoc, "Starvation guard".
		 */
		@Nullable Instant lastAttemptedAt;

		@NonNull String logLabel;

		/**
		 * The organisation whose {@link VATaxIDConfig} governs this target's check: the record's OWN
		 * {@code AD_Org_ID} — the location's for a location target, not its parent partner's. Same
		 * organisation {@code VATaxIDCheckTrigger} gates the enqueue on and
		 * {@code VATaxIDParentStatusRepository} resolves at processing time; the three must agree or one
		 * gate answers a different question from the next.
		 */
		@NonNull OrgId orgId;

		@NonNull
		private static VATaxIDStatus resolveStatus(@Nullable final String statusCode)
		{
			return VATaxIDStatus.optionalOfNullableCode(statusCode).orElse(VATaxIDStatus.NotChecked);
		}

		@Nullable
		private static Instant toInstantOrNull(@Nullable final Timestamp timestamp)
		{
			return timestamp != null ? timestamp.toInstant() : null;
		}
		
		@NonNull
		public static CheckTarget ofPartner(@NonNull final BPartnerId bpartnerId, @NonNull final I_C_BPartner bpartnerRecord)
		{
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(null)
					.vataxID(VATIdentifier.of(bpartnerRecord.getVATaxID()))
					.previousStatus(resolveStatus(bpartnerRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerRecord.getVATaxIDLastAttemptedAt()))
					.logLabel("C_BPartner_ID=" + bpartnerRecord.getC_BPartner_ID())
					.orgId(OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()))
					.build();
		}

		@NonNull
		public static CheckTarget ofLocation(@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID());
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationRecord.getC_BPartner_Location_ID()))
					.vataxID(VATIdentifier.of(bpartnerLocationRecord.getVATaxID()))
					.previousStatus(resolveStatus(bpartnerLocationRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDLastAttemptedAt()))
					.logLabel("C_BPartner_ID=" + bpartnerLocationRecord.getC_BPartner_ID()
							+ ", C_BPartner_Location_ID=" + bpartnerLocationRecord.getC_BPartner_Location_ID())
					.orgId(OrgId.ofRepoId(bpartnerLocationRecord.getAD_Org_ID()))
					.build();
		}
	}
}
