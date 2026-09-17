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
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.AtpReconciliationDetail;
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
 * The correction is always a delta-carrying candidate handed to the engine's ordinary
 * {@link CandidateChangeService#onCandidateNewOrChange(Candidate)}, never a direct write of a {@code STOCK}
 * candidate's {@code Qty} (unlike the existing {@code MD_Candidate_Remove_From_ATP.sql} cleanup) - that is what lets
 * the engine's own forward propagation re-derive every later candidate instead of the correction being silently
 * overwritten by it.
 * <p>
 * <b>Why {@link Profiles#PROFILE_MaterialDispo}-only:</b> its collaborator {@link CandidateChangeService} (and the
 * whole dispo engine behind it) carries the same profile, active only in the app server, never the webapi - both
 * component-scan {@code de.metas}, so an unconditional {@code @Service} here would abort webapi startup with
 * "required a bean of type CandidateChangeService that could not be found". Consequence: the webapi never calls this
 * class directly - a real run is enqueued as a {@code C_Queue_WorkPackage} and processed by
 * {@code AtpReconciliationWorkpackageProcessor} in the app server, where the profile is active.
 * {@link AtpTargetCalculator} is deliberately left unguarded so the webapi can still preview a dry run without this
 * bean.
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

	/** Convenience overload for {@link #reconcile} callers, which never touch the backup repository. */
	public AtpReconciliationCommand(
			@NonNull final AtpTargetCalculator atpTargetCalculator,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this(atpTargetCalculator, candidateChangeHandler, candidateRepository, new AtpReconciliationBackupRepositoryImpl());
	}

	/**
	 * @param date the run date {@code D}
	 * @param dryRun when {@code true}, only computes and returns the divergence - nothing is written
	 * @return the divergence this call acted on (or would have, on a dry run or a no-op)
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
	 * {@link AtpTargetCalculator#computeTarget(StockDataRecordIdentifier, Instant, Instant)}.
	 *
	 * @param livenessCutoff {@code null} means no cutoff
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

		// no caller-supplied run id on this path (unlike reconcileAndLog) - generate one just to tag the
		// correction candidate's own ATP_RECONCILIATION detail; nothing else reads it.
		writeCorrectionCandidate(key, date, divergence.getDifference(), UUID.randomUUID().toString());

		return divergence;
	}

	/**
	 * @return {@code true} when there's nothing to correct: a dry run, or a zero delta. Skipping the write here (not
	 * writing a zero-qty candidate) is what makes a repeated/overlapping-selection run idempotent - {@link
	 * #nextSeqNo}'s fresh-per-call seqNo would otherwise let each no-op run pile up a new candidate instead of
	 * matching the previous one.
	 */
	private static boolean isNoOpCorrection(@NonNull final AtpDivergence divergence, final boolean dryRun)
	{
		return dryRun || divergence.getDifference().signum() == 0;
	}

	private void writeCorrectionCandidate(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			@NonNull final BigDecimal delta,
			@NonNull final String runUuid)
	{
		final CandidateType type = delta.signum() > 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN;
		final Candidate candidate = buildCandidate(key, date, type, delta.abs(), runUuid);

		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	/**
	 * Same correction as {@link #reconcile}, plus a durable audit trail in {@link AtpReconciliationBackupRepository}:
	 * each changed candidate's pre-change {@code Qty} is persisted <b>before</b> the write, so it stays recoverable
	 * even if the process ends right after.
	 *
	 * @return the run's audit trail (empty on a dry run or a no-op, since nothing was written)
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

		final List<Candidate> stockCandidatesBeforeWrite = retrieveGeneralStockCandidatesFrom(key, date);
		backupRepository.backupBeforeWrite(runUuid, key, stockCandidatesBeforeWrite);

		writeCorrectionCandidate(key, date, divergence.getDifference(), runUuid);

		final List<Candidate> stockCandidatesAfterWrite = retrieveGeneralStockCandidatesFrom(key, date);
		final ImmutableList<AtpReconciliationRunLog.Entry> entries = buildEntries(stockCandidatesBeforeWrite, stockCandidatesAfterWrite);
		backupRepository.recordAfterWrite(runUuid, key, entries);

		return new AtpReconciliationRunLog(divergence, entries, runUuid);
	}

	/**
	 * @return every general (not customer-reserved) {@code STOCK} candidate of {@code key} at or after {@code date}.
	 * Deliberately wider than what {@link #reconcile} actually changes (no {@code SeqNo} cutoff) - the harmless
	 * over-inclusion just costs an unchanged-snapshot row, since {@link #buildEntries} drops before==after pairs.
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
	 * Builds the correction candidate with a fresh, per-call {@link Candidate#getSeqNo()} (see {@link #nextSeqNo}) -
	 * without it, a second same-{@code D} correction would natural-key-match the first on {@code DateProjected}
	 * alone ({@code RepositoryCommons#configureBuilderDateFilters} only adds a {@code SeqNo} filter when
	 * {@code seqNo > 0}) and overwrite it in place instead of adding a further delta. Measured: this turned a
	 * correctly-reconciled 100 into 0 before {@code seqNo} was assigned here (see
	 * {@code AtpReconciliationCommandTest#reconcilingTwiceInSuccession_leavesTheStoredValueUnchanged}).
	 */
	private Candidate buildCandidate(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			@NonNull final CandidateType type,
			@NonNull final BigDecimal qty,
			@NonNull final String runUuid)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(date)
				.productDescriptor(ProductDescriptor.forProductAndAttributes(key.getProductId().getRepoId(), key.getStorageAttributesKey()))
				.warehouseId(key.getWarehouseId())
				// targets the general chain, matching the customer-agnostic MD_Stock.QtyOnHand it's anchored on
				.customerId(null)
				.quantity(qty)
				.build();

		// null qtyBefore: this candidate did not exist before the run, matching the same convention
		// AtpReconciliationBackupRepositoryImpl.newRecordFor uses for the STOCK candidates a run creates.
		final AtpReconciliationDetail businessCaseDetail = AtpReconciliationDetail.builder()
				.reconciliationRunUUID(runUuid)
				.qtyBefore(null)
				.qtyAfter(qty)
				.build();

		return Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(key.getClientId(), key.getOrgId()))
				.type(type)
				.businessCase(CandidateBusinessCase.ATP_RECONCILIATION)
				.businessCaseDetail(businessCaseDetail)
				.materialDescriptor(materialDescriptor)
				.seqNo(nextSeqNo(key, date))
				.build();
	}

	/**
	 * @return one past the key's general {@code STOCK} candidate's current {@code SeqNo} at exactly {@code date}, or
	 * {@code 1} if none yet. Read fresh from the store (not a counter field) so a JVM restart can't collide with a
	 * value a previous JVM already wrote - same "next slot" pattern as
	 * {@code DDOrderAdvisedOrCreatedHandler#expectedSeqNoForDemandCandidate}.
	 * <p>
	 * <b>Known gap, accepted:</b> read-then-write with no lock/uniqueness constraint on {@code (key, date, SeqNo)} -
	 * two racing {@link #reconcile} calls for the same key/date could compute the same next value and overwrite
	 * each other. Not hardened: this is a single-operator workflow, and the two current callers are each already
	 * serialised - the webapi dry-run process via {@code AD_Process.IsOneInstanceOnly = 'Y'}, the async write path
	 * via {@code C_Queue_Processor.PoolSize = 1}. The gap itself stays open: a future caller of {@link #reconcile}
	 * is not automatically covered by either guard.
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
