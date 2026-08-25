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
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
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
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Selects and stamps the records a VAT-ID check runs against.
 *
 * <p>Repository Tables: C_BPartner, C_BPartner_Location
 * <p>Repository Cluster: VATaxIdCheckTargetRepo, BPartnerDAO
 *
 * <p>Reads only the VAT-ID check columns of those two tables, and writes only
 * {@code VATaxIDLastAttemptedAt}; the rest of both tables belongs to {@code BPartnerDAO}.
 */
@Repository
public class VATaxIdCheckTargetRepo
{
	/**
	 * Matches the scheduler's default MaxChecksPerRun, so a full-budget run pages once per grain.
	 */
	private static final int ITERATOR_BUFFER_SIZE = 500;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * The nightly run's single stream of the targets {@code orgId} owes a VAT-ID check: first every due
	 * {@code C_BPartner} of that organisation, then every due {@code C_BPartner_Location} of it, each grain
	 * ordered as {@link #iterateBPartnersDueForVATaxIDCheck(OrgId, Instant, int)} describes.
	 *
	 * <p>The two grains are CONCATENATED, not merged: each keeps its own oldest-attempt-first ordering
	 * instead of being interleaved into one ordering across both, which would need a merge over two
	 * separately ordered result sets to buy what raising {@code MaxChecksPerRun} buys more cheaply.
	 *
	 * <p><b>Lazy, and callers depend on it.</b> The {@code C_BPartner_Location} query is neither created nor
	 * executed until the partner grain is exhausted AND the caller asks for one more element. A caller with
	 * no budget left must therefore stop WITHOUT calling {@code hasNext()} again — see
	 * {@code VATaxIDMassCheckService#runNightly}.
	 *
	 * @param onBlankVATaxIDSkipped called with the log label of every record whose {@code VATaxID} turns out
	 * to be blank. Such a record is skipped instead of yielded — the queries can only filter
	 * {@code VATaxID IS NOT NULL} — and whether that deserves a log line is the caller's policy, not this
	 * repository's: on the nightly path it is a data defect worth naming, on the selection path the ordinary
	 * case.
	 * @return a guaranteed iterator
	 */
	@NonNull
	public Iterator<CheckTarget> iterateTargetsDueForVATaxIDCheck(
			@NonNull final OrgId orgId,
			@Nullable final Instant lastCheckedBefore,
			final int maxChecksPerRun,
			@NonNull final Consumer<String> onBlankVATaxIDSkipped)
	{
		final Supplier<Iterator<CheckTarget>> dueBPartnerTargets = () -> skippingBlankVATaxIDs(
				iterateBPartnersDueForVATaxIDCheck(orgId, lastCheckedBefore, maxChecksPerRun),
				bpartnerRecord -> CheckTarget.ofPartner(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()), bpartnerRecord),
				CheckTarget::logLabelOf,
				onBlankVATaxIDSkipped);

		final Supplier<Iterator<CheckTarget>> dueBPartnerLocationTargets = () -> skippingBlankVATaxIDs(
				iterateBPartnerLocationsDueForVATaxIDCheck(orgId, lastCheckedBefore, maxChecksPerRun),
				CheckTarget::ofLocation,
				CheckTarget::logLabelOf,
				onBlankVATaxIDSkipped);

		// Each grain sits behind a Supplier and the two-element meta-iterator is transformed lazily, so
		// Iterators.concat invokes a grain's Supplier -- and thereby runs its query -- only when it is asked
		// for an element the preceding grain can no longer provide.
		final Iterator<Iterator<CheckTarget>> lazyGrains = Iterators.transform(
				ImmutableList.of(dueBPartnerTargets, dueBPartnerLocationTargets).iterator(),
				Supplier::get);

		return Iterators.concat(lazyGrains);
	}

	/**
	 * Maps {@code records} to {@link CheckTarget}s, dropping every record whose {@code VATaxID} is blank and
	 * announcing each drop to {@code onBlankVATaxIDSkipped}. Reads nothing from {@code records} before the
	 * returned iterator is asked for an element.
	 */
	@NonNull
	private static <RecordType> Iterator<CheckTarget> skippingBlankVATaxIDs(
			@NonNull final Iterator<RecordType> records,
			@NonNull final Function<RecordType, CheckTarget> toCheckTargetOrNull,
			@NonNull final Function<RecordType, String> toLogLabel,
			@NonNull final Consumer<String> onBlankVATaxIDSkipped)
	{
		return new AbstractIterator<CheckTarget>()
		{
			@Override
			protected CheckTarget computeNext()
			{
				while (records.hasNext())
				{
					final RecordType record = records.next();
					final CheckTarget checkTarget = toCheckTargetOrNull.apply(record);
					if (checkTarget != null)
					{
						return checkTarget;
					}
					// Blank VAT-ID: skip this ONE record, so it costs the caller no budget and cannot abort
					// the caller's run.
					onBlankVATaxIDSkipped.accept(toLogLabel.apply(record));
				}
				return endOfData();
			}
		};
	}

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
	 * <p>One of the two grains of {@link #iterateTargetsDueForVATaxIDCheck(OrgId, Instant, int, Consumer)},
	 * which is what callers outside this class use; kept separately reachable so a test can substitute one
	 * grain's records without a database.
	 *
	 * @return a guaranteed iterator
	 */
	@VisibleForTesting
	Iterator<I_C_BPartner> iterateBPartnersDueForVATaxIDCheck(
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
				// Blanks are deliberately not excluded here; CheckTarget.ofPartner skips them one record at a
				// time, so one blank VAT-ID cannot abort the whole nightly run.
				.addNotNull(I_C_BPartner.COLUMNNAME_VATaxID);

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
	@VisibleForTesting
	Iterator<I_C_BPartner_Location> iterateBPartnerLocationsDueForVATaxIDCheck(
			@NonNull final OrgId orgId,
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
				// Blanks are deliberately not excluded here; CheckTarget.ofLocation skips them one record at a
				// time, so one blank VAT-ID cannot abort the whole nightly run.
				.addNotNull(I_C_BPartner_Location.COLUMNNAME_VATaxID);

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

	/**
	 * Stamps {@code checkTarget}'s {@code VATaxIDLastAttemptedAt} unconditionally, recording that an attempt
	 * is about to happen rather than that one succeeded, in its OWN committed transaction.
	 *
	 * <p>Deliberately {@code runInNewTrx} rather than {@code runInThreadInheritedTrx} — the one genuinely
	 * required use of the hack {@code docs/coding-rules/java-general.md} otherwise warns against. Joining the
	 * check-and-refresh transaction would let a rolled-back check erase the evidence that an attempt was
	 * made, which is the whole defect this mechanism prevents.
	 *
	 * <p>A chronic failure of this write itself is an accepted residual risk: such a target sorts first every
	 * run but consumes only its own slot, so it crowds out nobody. Diagnose it from the run log rather than
	 * auto-excluding after N failures.
	 */
	public void stampVATaxIDCheckAttemptInOwnTrx(@NonNull final CheckTarget checkTarget)
	{
		final Instant attemptedAt = SystemTime.asInstant();
		final BPartnerLocationId bpartnerLocationId = checkTarget.getBpartnerLocationId();

		trxManager.runInNewTrx(() -> {
			if (bpartnerLocationId != null)
			{
				stampVATaxIDCheckAttempt(bpartnerLocationId, attemptedAt);
			}
			else
			{
				stampVATaxIDCheckAttempt(checkTarget.getBpartnerId(), attemptedAt);
			}
		});
	}

	/**
	 * The user-triggered run's single stream of the targets {@code selectedBPartnersQuery} selects: first the
	 * header VAT-ID of every selected {@code C_BPartner} that carries one, then every VAT-ID-carrying
	 * {@code C_BPartner_Location} of any selected partner — regardless of whether that location's own partner
	 * header has a VAT-ID (see the class Javadoc, "Selecting a partner also covers its locations").
	 *
	 * <p>Streamed exactly like {@link #iterateTargetsDueForVATaxIDCheck(OrgId, Instant, int, Consumer)}: the
	 * selection is never materialised into an id list — the location grain reaches it through a SQL subquery
	 * ({@code addInSubQueryFilter}), so the query binds ZERO parameters however large the selection. That is
	 * the whole point: "select all" in the grid can carry tens of thousands of records, and one bind parameter
	 * per record is what produced {@code An I/O error occurred while sending to the backend}.
	 *
	 * <p>Unlike the nightly method there is NO staleness filter — the manual run re-checks whatever is
	 * selected regardless of {@code VATaxIDCheckedAt}.
	 *
	 * <p>The two grains are CONCATENATED and evaluated LAZILY, exactly as
	 * {@link #iterateTargetsDueForVATaxIDCheck(OrgId, Instant, int, Consumer)} documents: the location query
	 * is neither created nor executed until the partner grain is exhausted AND the caller asks for one more
	 * element, so a caller with no budget left must stop WITHOUT calling {@code hasNext()} again.
	 *
	 * @param onBlankVATaxIDSkipped called with the log label of every record whose {@code VATaxID} is blank;
	 * on this path a record without a VAT-ID is the ordinary case, so the caller passes a no-op — see the
	 * {@code onBlankVATaxIDSkipped} contract on {@link #iterateTargetsDueForVATaxIDCheck}.
	 * @return a guaranteed iterator
	 */
	@NonNull
	public Iterator<CheckTarget> iterateSelectedTargets(
			@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery,
			@NonNull final Consumer<String> onBlankVATaxIDSkipped)
	{
		final Supplier<Iterator<CheckTarget>> selectedBPartnerTargets = () -> skippingBlankVATaxIDs(
				iterateSelectedBPartners(selectedBPartnersQuery),
				bpartnerRecord -> CheckTarget.ofPartner(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()), bpartnerRecord),
				CheckTarget::logLabelOf,
				onBlankVATaxIDSkipped);

		final Supplier<Iterator<CheckTarget>> selectedBPartnerLocationTargets = () -> skippingBlankVATaxIDs(
				iterateSelectedBPartnerLocations(selectedBPartnersQuery),
				CheckTarget::ofLocation,
				CheckTarget::logLabelOf,
				onBlankVATaxIDSkipped);

		// Same lazy two-grain concatenation as iterateTargetsDueForVATaxIDCheck: the location grain's Supplier
		// -- and thereby its subquery -- runs only when the meta-iterator is asked for an element the partner
		// grain can no longer provide.
		final Iterator<Iterator<CheckTarget>> lazyGrains = Iterators.transform(
				ImmutableList.of(selectedBPartnerTargets, selectedBPartnerLocationTargets).iterator(),
				Supplier::get);

		return Iterators.concat(lazyGrains);
	}

	/**
	 * @return how many targets {@link #iterateSelectedTargets(IQuery, Consumer)} would yield, from the SAME
	 * two queries the iterator streams so the count and the stream cannot drift.
	 */
	public int countSelectedTargets(@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery)
	{
		return createSelectedBPartnersWithVATaxIDQuery(selectedBPartnersQuery).count()
				+ createSelectedBPartnerLocationsQuery(selectedBPartnersQuery).count();
	}

	/**
	 * One of the two grains of {@link #iterateSelectedTargets(IQuery, Consumer)}; kept separately reachable so
	 * a test can substitute one grain's records without a database — see
	 * {@link #iterateBPartnersDueForVATaxIDCheck(OrgId, Instant, int)}.
	 */
	@VisibleForTesting
	@NonNull
	Iterator<I_C_BPartner> iterateSelectedBPartners(@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery)
	{
		return createSelectedBPartnersWithVATaxIDQuery(selectedBPartnersQuery)
				.setOption(IQuery.OPTION_IteratorBufferSize, ITERATOR_BUFFER_SIZE)
				.iterateWithGuaranteedIterator(I_C_BPartner.class);
	}

	/** The {@code C_BPartner_Location} counterpart of {@link #iterateSelectedBPartners(IQuery)}. */
	@VisibleForTesting
	@NonNull
	Iterator<I_C_BPartner_Location> iterateSelectedBPartnerLocations(@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery)
	{
		return createSelectedBPartnerLocationsQuery(selectedBPartnersQuery)
				.setOption(IQuery.OPTION_IteratorBufferSize, ITERATOR_BUFFER_SIZE)
				.iterateWithGuaranteedIterator(I_C_BPartner_Location.class);
	}

	/**
	 * The VAT-ID-carrying {@code C_BPartner}s of {@code selectedBPartnersQuery}, reached through a SQL subquery
	 * so the selection is never bound parameter-by-parameter. Built by one method shared by
	 * {@link #iterateSelectedBPartners(IQuery)} and {@link #countSelectedTargets(IQuery)} so the streamed rows
	 * and their count cannot drift -- the same reason {@link #createSelectedBPartnerLocationsQuery(IQuery)}
	 * exists for the location grain: without the {@code VATaxID} filter here the count would include every
	 * selected partner that carries no VAT-ID (the ordinary case on a broad selection), while the stream
	 * yields only VAT-ID-bearing ones, and pendingCount would be inflated by the difference.
	 *
	 * <p>No {@code addOnlyActiveRecordsFilter} here, unlike the location grain: these rows ARE the caller's
	 * selection, whose active policy {@code retrieveSelectedRecordsQueryBuilder} already fixed; the location
	 * grain queries a different table not covered by that selection and so must scope active itself.
	 */
	@NonNull
	private IQuery<I_C_BPartner> createSelectedBPartnersWithVATaxIDQuery(@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery)
	{
		return queryBL.createQueryBuilder(I_C_BPartner.class)
				.addNotNull(I_C_BPartner.COLUMNNAME_VATaxID)
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID, selectedBPartnersQuery)
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.create();
	}

	/**
	 * Every VAT-ID-carrying, active {@code C_BPartner_Location} of any partner in {@code selectedBPartnersQuery},
	 * reached through a SQL subquery so the selection is never bound parameter-by-parameter. Built by one
	 * method shared by {@link #iterateSelectedBPartnerLocations(IQuery)} and
	 * {@link #countSelectedTargets(IQuery)} so the streamed rows and their count cannot drift.
	 */
	@NonNull
	private IQuery<I_C_BPartner_Location> createSelectedBPartnerLocationsQuery(@NonNull final IQuery<I_C_BPartner> selectedBPartnersQuery)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_BPartner_Location.COLUMNNAME_VATaxID)
				.addInSubQueryFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID, selectedBPartnersQuery)
				.orderBy(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID)
				.create();
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
		
		/**
		 * @return {@code null} if the record carries no VAT-ID to check; blank counts as none. Resolving a
		 * blank via {@link VATIdentifier#of(String)} instead would throw from outside any catch on the nightly
		 * path and abort the whole run, and the due-for-check query filters on {@code VATaxID IS NOT NULL} only.
		 */
		@Nullable
		private static CheckTarget ofPartner(@NonNull final BPartnerId bpartnerId, @NonNull final I_C_BPartner bpartnerRecord)
		{
			final VATIdentifier vataxID = VATIdentifier.ofNullable(bpartnerRecord.getVATaxID());
			if (vataxID == null)
			{
				return null;
			}

			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(null)
					.vataxID(vataxID)
					.previousStatus(resolveStatus(bpartnerRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerRecord.getVATaxIDLastAttemptedAt()))
					.logLabel(logLabelOf(bpartnerRecord))
					.orgId(OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()))
					.build();
		}

		/** @return {@code null} if the record carries no VAT-ID to check -- see {@link #ofPartner}. */
		@Nullable
		private static CheckTarget ofLocation(@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
		{
			final VATIdentifier vataxID = VATIdentifier.ofNullable(bpartnerLocationRecord.getVATaxID());
			if (vataxID == null)
			{
				return null;
			}

			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerLocationRecord.getC_BPartner_ID());
			return CheckTarget.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationRecord.getC_BPartner_Location_ID()))
					.vataxID(vataxID)
					.previousStatus(resolveStatus(bpartnerLocationRecord.getVATaxIDStatus()))
					.checkedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDCheckedAt()))
					.lastAttemptedAt(toInstantOrNull(bpartnerLocationRecord.getVATaxIDLastAttemptedAt()))
					.logLabel(logLabelOf(bpartnerLocationRecord))
					.orgId(OrgId.ofRepoId(bpartnerLocationRecord.getAD_Org_ID()))
					.build();
		}

		/**
		 * Shared with the caller's skip log, so a skipped record is named exactly as a checked one; a second,
		 * hand-built label would drift from {@link #logLabel}.
		 */
		@NonNull
		private static String logLabelOf(@NonNull final I_C_BPartner bpartnerRecord)
		{
			return "C_BPartner_ID=" + bpartnerRecord.getC_BPartner_ID();
		}

		/** @see #logLabelOf(I_C_BPartner) */
		@NonNull
		private static String logLabelOf(@NonNull final I_C_BPartner_Location bpartnerLocationRecord)
		{
			return "C_BPartner_ID=" + bpartnerLocationRecord.getC_BPartner_ID()
					+ ", C_BPartner_Location_ID=" + bpartnerLocationRecord.getC_BPartner_Location_ID();
		}
	}
}
