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

import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.commons.candidate.Candidate;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

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
 */
@Service
@RequiredArgsConstructor
public class AtpReconciliationCommand
{
	@NonNull private final AtpTargetCalculator atpTargetCalculator;
	@NonNull private final CandidateChangeService candidateChangeHandler;
	@NonNull private final CandidateRepositoryRetrieval candidateRepository;

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
		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(key, date);
		final BigDecimal delta = divergence.getDifference();
		// dryRun: see above. delta == 0: the stored projection already matches computeTarget, so there is nothing to
		// correct - writing a candidate anyway would insert a redundant zero-qty INVENTORY_UP/STOCK pair on every
		// no-op run. The operator restricts a run by warehouse/product/category filters, not by an exact,
		// non-overlapping partition of keys - so a caller replaying the same selection repeatedly (or overlapping
		// selections covering the same key) must not accumulate phantom candidates each time. This IS what makes a
		// second, no-op run of this method idempotent - not
		// "write it anyway", which nextSeqNo's fresh-per-call seqNo (see its Javadoc) guarantees would never even
		// natural-key-match the previous write, so it would keep piling up rows instead of being absorbed by one.
		if (dryRun || delta.signum() == 0)
		{
			return divergence;
		}

		final CandidateType type = delta.signum() > 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN;
		final Candidate candidate = buildCandidate(key, date, type, delta.abs());

		candidateChangeHandler.onCandidateNewOrChange(candidate);

		return divergence;
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
