package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Brings a reconciliation key's stored projected ATP back onto {@link AtpTargetCalculator#computeTarget}, without
 * moving physical stock.
 * <p>
 * <b>The correction is always a delta-carrying candidate handed to the engine's ordinary
 * {@link CandidateChangeService#onCandidateNewOrChange(Candidate)}, never a direct write of a {@code STOCK}
 * candidate's {@code Qty}.</b> That is what makes the correction <i>compose</i> with later changes instead of
 * being silently discarded by them: the engine's {@code StockCandidateService.applyDeltaToMatchingLaterStockCandidates}
 * re-walks every later {@code STOCK} candidate whenever an earlier one changes, so a later candidate dated before
 * {@code D} shifts the reconciled balance by exactly its own effect instead of replacing it. The existing SQL
 * cleanup (see {@code MD_Candidate_Remove_From_ATP.sql}) does the opposite - an {@code UPDATE MD_Candidate SET
 * Qty = ...} overwrite - and that is exactly what any later propagation discards.
 * <p>
 * The delta is:
 *
 * <pre>
 * delta = computeTarget(key, D) - (the running balance of key immediately before D)
 *       = AtpDivergence.getDifference()                          (computed once, by AtpTargetCalculator)
 * skip  = delta.signum() == 0                                     // nothing to correct: no candidate is written -
 *                                                                  // same as a dry run, this leaves a pure read
 * type  = delta.signum() &gt; 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN
 * qty   = delta.abs()                                             // the engine expects a positive qty for both types
 * </pre>
 * <p>
 * Handing that candidate to {@link CandidateChangeService} at date {@code D} triggers the engine's ordinary forward
 * propagation, which re-derives every {@code STOCK} candidate after {@code D} through
 * {@code Candidate.getStockImpactPlannedQuantity()} - the same authoritative formula {@link AtpTargetCalculator}
 * used to compute the target - the two are one calculation invoked from two call sites, not two separate
 * implementations that merely happen to agree, so they cannot disagree.
 * <p>
 * <b>Why this bean is {@link Profiles#PROFILE_MaterialDispo}-only.</b> Its collaborator
 * {@link CandidateChangeService} - and the whole engine behind it: {@code StockCandidateService} and every
 * {@code CandidateHandler} it aggregates - carries that same profile, so the dispo engine is instantiated in exactly
 * <i>one</i> JVM and material events are never processed twice. That profile is added only by the app server
 * ({@code ServerBoot} reads it from the {@code de.metas.spring.profiles.active} sysconfigs); the webapi
 * ({@code WebRestApiApplication}) reads a different sysconfig prefix and so never activates it. Both applications
 * component-scan {@code de.metas}, so an <i>unconditional</i> {@code @Service} here is picked up by the webapi too,
 * where its constructor cannot be satisfied - which aborts webapi startup outright with "required a bean of type
 * CandidateChangeService that could not be found". The same annotation is carried by every other bean outside
 * {@code dispo-service} that collaborates with the engine, e.g.
 * {@code de.metas.material.cockpit.view.mainrecord.MaterialCandidateChangedHandler} and
 * {@code de.metas.material.planning.event.SupplyRequiredDecreasedHandler} - though note those guard against a
 * different failure: they are event handlers that would otherwise process the same material event in two JVMs,
 * whereas this class simply cannot have its constructor satisfied outside the profile. Same instrument, same
 * convention, different reason.
 * <p>
 * Consequence for callers: in a JVM without that profile this bean does not exist, so
 * {@code MD_Candidate_Reconcile_ATP} fails fast there with an explicit message instead of half-reconciling - see
 * that process's {@code reconciliationCommand()}. <b>That applies to a dry run too</b>: the process resolves this
 * whole bean before it inspects its dry-run parameter, so <i>no</i> path through that process is reachable from a
 * profile-less JVM - the operator gets the explicit failure whether or not the preview box is ticked.
 * <p>
 * {@link AtpTargetCalculator} is a separate matter and is deliberately left unguarded: it needs nothing from
 * {@code dispo-service}, so a caller that holds it directly can compute a divergence anywhere. That does not make
 * the process above universally runnable - the process does not use the calculator directly - it only means the
 * read-only computation is available to code that wants it.
 */
@Service
@Profile(Profiles.PROFILE_MaterialDispo)
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class AtpReconciliationCommand
{
	@NonNull private final AtpTargetCalculator atpTargetCalculator;
	@NonNull private final CandidateChangeService candidateChangeHandler;
	@NonNull private final CandidateRepositoryRetrieval candidateRepository;
	@NonNull private final AtpReconciliationBackupRepository backupRepository;

	/**
	 * Convenience overload matching this class's original 3-arg constructor, kept so that
	 * {@code AtpReconciliationCommandTest} - which only ever exercises {@link #reconcile}, never
	 * {@link #reconcileAndLog} - keeps compiling and passing unchanged: {@link #reconcile} never touches the backup
	 * repository, so a real one is all this overload needs to supply.
	 */
	public AtpReconciliationCommand(
			@NonNull final AtpTargetCalculator atpTargetCalculator,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this(atpTargetCalculator, candidateChangeHandler, candidateRepository, new AtpReconciliationBackupRepositoryImpl());
	}

	/**
	 * @param date the run date {@code D}
	 * @param dryRun when {@code true}, only computes and returns the divergence: no candidate is built and
	 * {@link CandidateChangeService} is never invoked, so nothing is written - physical stock, the candidate chain
	 * and {@code M_Transaction} all stay untouched.
	 * @return the divergence this call acted on (or, on a dry run - or when there is nothing to correct - would have
	 * acted on) - so a caller can report exactly what changed or would change.
	 */
	public AtpDivergence reconcile(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			final boolean dryRun)
	{
		return reconcile(key, date, dryRun, null);
	}

	/**
	 * Same as {@link #reconcile(StockDataRecordIdentifier, Instant, boolean)}, but a candidate dated strictly before
	 * {@code livenessCutoff} is treated as closed regardless of its source document - see
	 * {@link AtpTargetCalculator#computeTarget(StockDataRecordIdentifier, Instant, Instant)}. This is how the
	 * optional liveness-cutoff date reaches the operator-invoked run.
	 *
	 * @param livenessCutoff may be {@code null}, in which case no candidate is cut off - same result as the three-arg
	 * overload
	 */
	public AtpDivergence reconcile(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			final boolean dryRun,
			@Nullable final Instant livenessCutoff)
	{
		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(key, date, livenessCutoff);
		if (isNoOpCorrection(divergence, dryRun))
		{
			return divergence;
		}

		writeCorrectionCandidate(key, date, divergence.getDifference());

		return divergence;
	}

	/**
	 * @return {@code true} when this run has nothing to correct: a dry run (see {@link #reconcile}'s Javadoc), or a
	 * zero delta - the stored projection already matches {@code computeTarget}, so writing a candidate anyway would
	 * insert a redundant zero-qty INVENTORY_UP/STOCK pair on every no-op run. The operator restricts a run by
	 * warehouse/product/category filters, not by an exact, non-overlapping partition of keys - so a caller replaying
	 * the same selection repeatedly (or overlapping selections covering the same key) must not accumulate phantom
	 * candidates each time. This IS what makes a second, no-op run of {@link #reconcile} idempotent - not "write it
	 * anyway", which {@link #nextSeqNo}'s fresh-per-call seqNo (see its Javadoc) guarantees would never even
	 * natural-key-match the previous write, so it would keep piling up rows instead of being absorbed by one.
	 */
	private static boolean isNoOpCorrection(@NonNull final AtpDivergence divergence, final boolean dryRun)
	{
		return dryRun || divergence.getDifference().signum() == 0;
	}

	private void writeCorrectionCandidate(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			@NonNull final BigDecimal delta)
	{
		final CandidateType type = delta.signum() > 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN;
		final Candidate candidate = buildCandidate(key, date, type, delta.abs());

		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	/**
	 * Same correction as {@link #reconcile}, plus a durable audit trail: every {@code STOCK} candidate this call
	 * actually changes is backed up in {@link AtpReconciliationBackupRepository} - its pre-change {@code Qty}
	 * persisted <b>before</b> {@link #writeCorrectionCandidate} touches anything, so the value stays recoverable
	 * even if the process ends right after - and then completed with its after value once the change is known, in
	 * both the persisted rows and the returned {@link AtpReconciliationRunLog}.
	 * <p>
	 * Kept as a separate method rather than folded into {@link #reconcile} itself so that every existing caller of
	 * {@link #reconcile} keeps its exact contract - return exactly the {@link AtpDivergence}, nothing else -
	 * unchanged.
	 *
	 * @return the run's audit trail: the {@link AtpDivergence} this call acted on, plus one
	 * {@link AtpReconciliationRunLog.Entry} per {@code STOCK} candidate this call actually changed, and the
	 * {@code runUuid} under which the same rows are durably persisted. Empty on a dry run, or when there was nothing
	 * to correct - nothing was written, so there is nothing to back up or log.
	 */
	public AtpReconciliationRunLog reconcileAndLog(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			final boolean dryRun)
	{
		return reconcileAndLog(key, date, dryRun, null);
	}

	/**
	 * Same as {@link #reconcileAndLog(StockDataRecordIdentifier, Instant, boolean)}, but honours a liveness cutoff -
	 * see {@link #reconcile(StockDataRecordIdentifier, Instant, boolean, Instant)}.
	 */
	public AtpReconciliationRunLog reconcileAndLog(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			final boolean dryRun,
			@Nullable final Instant livenessCutoff)
	{
		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(key, date, livenessCutoff);
		if (isNoOpCorrection(divergence, dryRun))
		{
			return AtpReconciliationRunLog.empty(divergence);
		}

		final String runUuid = UUID.randomUUID().toString();

		// the pre-write snapshot IS the backup: persisted before writeCorrectionCandidate changes anything, so it
		// survives even a crash right after this call
		final List<Candidate> stockCandidatesBeforeWrite = retrieveGeneralStockCandidatesFrom(key, date);
		backupRepository.backupBeforeWrite(runUuid, key, stockCandidatesBeforeWrite);

		writeCorrectionCandidate(key, date, divergence.getDifference());

		final List<Candidate> stockCandidatesAfterWrite = retrieveGeneralStockCandidatesFrom(key, date);
		final ImmutableList<AtpReconciliationRunLog.Entry> entries = buildEntries(stockCandidatesBeforeWrite, stockCandidatesAfterWrite);
		backupRepository.recordAfterWrite(runUuid, key, entries);

		return new AtpReconciliationRunLog(divergence, entries, runUuid);
	}

	/**
	 * @return every general (i.e. not customer-reserved - see {@link #buildCandidate}) {@code STOCK} candidate of
	 * {@code key} dated at or after {@code date}. The filter is on {@code date} alone, with no {@code SeqNo}
	 * cutoff, so this is deliberately wider than the set {@link #reconcile} actually changes: a pre-existing
	 * candidate dated exactly at {@code date} but sequenced before the new correction (see {@link #nextSeqNo}) is
	 * included here too, even though the write never touches it. That over-inclusion is harmless - {@link
	 * #buildEntries} pairs it with an identical before/after {@code Qty} and produces no log entry for it - it
	 * only costs one superfluous unchanged-snapshot row in {@link AtpReconciliationBackupRepository}.
	 */
	private List<Candidate> retrieveGeneralStockCandidatesFrom(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(key.getWarehouseId())
				.productId(key.getProductId().getRepoId())
				.storageAttributesKey(key.getStorageAttributesKey())
				.customer(BPartnerClassifier.none())
				.timeRangeStart(DateAndSeqNo.atTimeNoSeqNo(date).withOperator(DateAndSeqNo.Operator.INCLUSIVE))
				.build();

		return candidateRepository.retrieveOrderedByDateAndSeqNo(
				CandidatesQuery.builder()
						.materialDescriptorQuery(materialDescriptorQuery)
						.matchExactStorageAttributesKey(true)
						.type(CandidateType.STOCK)
						.build());
	}

	/**
	 * Pairs each after-write candidate with whatever {@code Qty} it carried before the write - {@code null} when the
	 * candidate did not exist yet, i.e. this run created it - and keeps only the ones that actually changed, which
	 * is exactly "a candidate this run touched".
	 */
	private static ImmutableList<AtpReconciliationRunLog.Entry> buildEntries(
			@NonNull final List<Candidate> stockCandidatesBeforeWrite,
			@NonNull final List<Candidate> stockCandidatesAfterWrite)
	{
		final ImmutableMap<CandidateId, BigDecimal> qtyBeforeByCandidateId = stockCandidatesBeforeWrite.stream()
				.collect(ImmutableMap.toImmutableMap(Candidate::getId, Candidate::getQuantity));

		final ImmutableList.Builder<AtpReconciliationRunLog.Entry> entries = ImmutableList.builder();
		for (final Candidate candidateAfterWrite : stockCandidatesAfterWrite)
		{
			final BigDecimal qtyBefore = qtyBeforeByCandidateId.get(candidateAfterWrite.getId());
			final BigDecimal qtyAfter = candidateAfterWrite.getQuantity();
			if (qtyBefore == null || qtyBefore.compareTo(qtyAfter) != 0)
			{
				entries.add(new AtpReconciliationRunLog.Entry(
						candidateAfterWrite.getId(),
						candidateAfterWrite.getMaterialDescriptor().getDate(),
						qtyBefore,
						qtyAfter));
			}
		}
		return entries.build();
	}

	/**
	 * Builds the correction candidate - deliberately carrying a fresh, per-call {@link Candidate#getSeqNo()} (see
	 * {@link #nextSeqNo}) so that two corrections of the <b>same</b> key at the exact same date {@code D} (e.g. an
	 * idempotence re-run under a frozen clock, or a later, non-zero-delta correction) never natural-key-match one
	 * another as "the same candidate".
	 * <p>
	 * Without a distinguishing {@code seqNo}, {@code RepositoryCommons#configureBuilderDateFilters} matches an "at
	 * time" lookup on {@code DateProjected} alone whenever the candidate's own {@code seqNo} is left at its default
	 * 0 (it only adds a {@code SeqNo} filter when {@code seqNo > 0}), so a second correction with no other
	 * distinguishing business-case detail (unlike e.g. {@code StockEstimateCreatedHandler}'s document-backed one)
	 * would natural-key-match the first one and <i>update</i> it in place - which replaces its whole prior
	 * contribution instead of adding a further delta on top, corrupting the very run this method is trying to leave
	 * unchanged. Measured: a second same-{@code D} {@code reconcile()} call turned a correctly-reconciled 100 into 0
	 * before {@code seqNo} was assigned here (see
	 * {@code AtpReconciliationCommandTest#reconcilingTwiceInSuccession_leavesTheStoredValueUnchanged}).
	 */
	private Candidate buildCandidate(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			@NonNull final CandidateType type,
			@NonNull final BigDecimal qty)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(date)
				.productDescriptor(ProductDescriptor.forProductAndAttributes(key.getProductId().getRepoId(), key.getStorageAttributesKey()))
				.warehouseId(key.getWarehouseId())
				// the correction targets the general chain (see AtpTargetCalculator#retrieveStoredAtp), so it is
				// deliberately not reserved for any customer - matching the physical, customer-agnostic
				// MD_Stock.QtyOnHand it is anchored on
				.customerId(null)
				.quantity(qty)
				.build();

		return Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(key.getClientId(), key.getOrgId()))
				.type(type)
				.materialDescriptor(materialDescriptor)
				.seqNo(nextSeqNo(key, date))
				.build();
	}

	/**
	 * @return one past whatever {@code SeqNo} the key's general {@code STOCK} candidate at exactly {@code date}
	 * currently carries, or {@code 1} if there is none yet.
	 * <p>
	 * This is read fresh from the store on every call rather than kept in a counter field: a counter that starts
	 * over on every JVM start (or is not shared between nodes) would - on the very first candidate written after
	 * each restart - collide with whatever a previous JVM already wrote for this key and date, silently matching
	 * and overwriting an unrelated candidate. Deriving the value from the persisted chain instead means it can
	 * only ever collide with a {@code SeqNo} this exact key already carries at this exact date, which
	 * {@code StockCandidateService#createStockCandidate} always keeps in lock-step with the causing candidate's own
	 * {@code SeqNo} - so reading it back and adding one reproduces the same "assign the next slot" rule
	 * {@code DDOrderAdvisedOrCreatedHandler} already uses for a header/detail pair
	 * ({@code expectedSeqNoForDemandCandidate = supplyCandidate.getSeqNo() + 1}), instead of inventing a new,
	 * fragile source of uniqueness.
	 * <p>
	 * <b>Known gap, accepted:</b> this is a read-then-write with no lock and no DB uniqueness constraint on
	 * {@code (key, date, SeqNo)}. Two {@link #reconcile} calls racing on the exact same key and date could read the
	 * same latest {@code SeqNo} and compute the same next value; the second write would then natural-key-match the
	 * first (see {@link #buildCandidate}) and silently update it in place instead of adding a distinct candidate.
	 * Not hardened here: this reconciliation is a single-operator, selection-restricted workflow, so two calls for
	 * the same key and date are not expected to race in practice - and a lock or retry here would guard against a
	 * scenario this workflow does not produce.
	 * <p>
	 * The one realistic way two {@link #reconcile} calls for the same key and date could actually race is two
	 * concurrent operator-triggered runs - closed cheaply at that entry point instead of here:
	 * {@code de.metas.material.dispo.reconcile.process.MD_Candidate_Reconcile_ATP}'s {@code AD_Process.IsOneInstanceOnly
	 * = 'Y'} refuses to start a second instance of that process while one is already running. This method's
	 * read-then-write gap itself stays open and unhardened - any other future caller is not covered by that guard.
	 */
	private int nextSeqNo(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(key.getWarehouseId())
				.productId(key.getProductId().getRepoId())
				.storageAttributesKey(key.getStorageAttributesKey())
				.customer(BPartnerClassifier.none())
				.atTime(DateAndSeqNo.atTimeNoSeqNo(date))
				.build();

		final Candidate stockCandidateAtDate = candidateRepository.retrieveLatestMatchOrNull(
				CandidatesQuery.builder()
						.materialDescriptorQuery(materialDescriptorQuery)
						.matchExactStorageAttributesKey(true)
						.type(CandidateType.STOCK)
						.build());

		return stockCandidateAtDate != null ? stockCandidateAtDate.getSeqNo() + 1 : 1;
	}
}
