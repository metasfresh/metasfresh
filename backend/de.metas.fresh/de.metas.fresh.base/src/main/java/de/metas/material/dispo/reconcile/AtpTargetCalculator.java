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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Computes, for one reconciliation key and one date {@code D}, the projected ATP that key <i>ought</i> to
 * have:
 *
 * <pre>
 * target(key, D) = MD_Stock.QtyOnHand(key)
 *                + &Sigma; signed(Qty - QtyFulfilled)   over the non-STOCK candidates c of key
 *                                                  with date(c) &le; D and liveness(c) == STILL_OPEN
 * </pre>
 * <p>
 * Candidates dated <b>after</b> D are deliberately not part of the sum: the reconciliation hands its
 * correction to the engine's ordinary candidate-change handling at D, and the engine's forward propagation
 * then re-derives every stock candidate after D from exactly those later positions. Adding them here as
 * well would count them twice.
 * <p>
 * This class is the single expression behind both the reconciliation (which writes the delta) and the
 * divergence report (which only shows it), so that the two cannot disagree about what "correct" means.
 */
@Service
@RequiredArgsConstructor
public class AtpTargetCalculator
{
	@NonNull private final StockRepository stockRepository;
	@NonNull private final CandidateRepositoryRetrieval candidateRepository;
	@NonNull private final SourceDocumentLivenessService livenessService;

	@VisibleForTesting
	public static AtpTargetCalculator newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				AtpTargetCalculator.class,
				() -> new AtpTargetCalculator(
						SpringContextHolder.getBeanOrSupply(StockRepository.class, StockRepository::new),
						SpringContextHolder.getBeanOrSupply(
								CandidateRepositoryRetrieval.class,
								() -> new CandidateRepositoryRetrieval(
										new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory())),
										new StockChangeDetailRepo())),
						SourceDocumentLivenessService.newInstanceForUnitTesting()));
	}

	/**
	 * @param date the run date {@code D}; candidates dated after it are excluded
	 * @return the projected ATP the given key ought to have at the given date
	 */
	public BigDecimal computeTarget(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date)
	{
		final List<Candidate> candidates = candidateRepository
				.retrieveOrderedByDateAndSeqNo(createCandidatesQueryUntilDate(key, date, BPartnerClassifier.any()));

		// QtyFulfilled is not part of the Candidate value object, so it is fetched separately - in one query
		// for the whole chain, see CandidateRepositoryRetrieval#getQtyFulfilledByCandidateIds
		final ImmutableMap<CandidateId, BigDecimal> qtyFulfilledByCandidateId = candidateRepository.getQtyFulfilledByCandidateIds(
				candidates.stream()
						.map(Candidate::getId)
						.collect(ImmutableList.toImmutableList()));

		BigDecimal target = stockRepository.getQtyOnHand(key);
		for (final Candidate candidate : candidates)
		{
			if (candidate.getType() == CandidateType.STOCK)
			{
				// a STOCK candidate is the running balance itself, not a position contributing to it
				continue;
			}
			if (!livenessService.getStatus(candidate).isContributingToAtp())
			{
				continue;
			}

			target = target.add(computeSignedOpenQty(candidate, qtyFulfilledByCandidateId));
		}

		return target;
	}

	/**
	 * @return the divergence between {@link #computeTarget(StockDataRecordIdentifier, Instant)} and the
	 * projected ATP currently stored for the given key at the given date
	 */
	public AtpDivergence computeDivergence(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date)
	{
		return AtpDivergence.of(
				computeTarget(key, date),
				retrieveStoredAtp(key, date));
	}

	/**
	 * @return the {@code Qty} of the key's youngest <b>general</b> {@code STOCK} candidate dated at or before
	 * the given date - i.e. the running balance the system currently believes in - or
	 * {@link BigDecimal#ZERO} if the key has no such stock candidate up to that date.
	 * <p>
	 * "General" means {@code C_BPartner_Customer_ID IS NULL}, which is what {@link BPartnerClassifier#none()}
	 * emits (see {@code RepositoryCommons#addMaterialDescriptorToQueryBuilderIfNotNull}). Unlike the sum in
	 * {@link #computeTarget(StockDataRecordIdentifier, Instant)}, this lookup must NOT be customer-agnostic:
	 * {@code StockCandidateService} propagates the customer id onto the {@code STOCK} candidate it creates
	 * for a position that is reserved for a customer, and such a candidate's {@code Qty} is the balance over
	 * {that customer's rows} &cup; {the null-customer rows} only - not over the general population. Reading
	 * it as "the stored balance" would compare it against an {@code expectedAtp} anchored on
	 * {@code MD_Stock.QtyOnHand}, which has no customer dimension at all, i.e. two different populations.
	 */
	private BigDecimal retrieveStoredAtp(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date)
	{
		final Candidate stockCandidate = candidateRepository.retrieveLatestMatchOrNull(
				createCandidatesQueryUntilDate(key, date, BPartnerClassifier.none())
						.withType(CandidateType.STOCK));

		return stockCandidate != null ? stockCandidate.getQuantity() : BigDecimal.ZERO;
	}

	/**
	 * The open remainder of a candidate is {@code Qty - QtyFulfilled}; its <i>sign</i> depends on the
	 * candidate's type, and that mapping exists exactly once in the codebase, in
	 * {@link Candidate#getStockImpactPlannedQuantity()}. So instead of switching on the type again here, the
	 * remainder is put back onto the candidate and run through that one authoritative formula.
	 * <p>
	 * Doing it this way rather than via {@code openQty.abs()} plus a {@code signum()} of the candidate's own
	 * signed quantity is not cosmetic: a candidate with {@code Qty = 0} has a signed quantity of {@code 0},
	 * whose {@code signum()} carries no direction at all - and zero-quantity {@code INVENTORY_DOWN}
	 * candidates do occur in real data (a zero-movement inventory creates one). Feeding the remainder
	 * through the type switch stays correct there.
	 *
	 * @return the candidate's still-open quantity, signed by its effect on stock
	 */
	private static BigDecimal computeSignedOpenQty(
			@NonNull final Candidate candidate,
			@NonNull final ImmutableMap<CandidateId, BigDecimal> qtyFulfilledByCandidateId)
	{
		final BigDecimal qtyFulfilled = qtyFulfilledByCandidateId.getOrDefault(candidate.getId(), BigDecimal.ZERO);
		final BigDecimal openQty = candidate.getQuantity().subtract(qtyFulfilled);

		return candidate.withQuantity(openQty).getStockImpactPlannedQuantity();
	}

	/**
	 * @param customer how the query treats {@code MD_Candidate.C_BPartner_Customer_ID}. The two terms of the
	 * reconciliation need different answers here: the sum over the key's positions is customer-agnostic
	 * ({@link BPartnerClassifier#any()}) because a customer-reserved demand still consumes real physical
	 * stock and so must count towards the target, whereas the stored-balance lookup has to stay on the
	 * general chain ({@link BPartnerClassifier#none()}) - see {@link #retrieveStoredAtp}.
	 */
	private static CandidatesQuery createCandidatesQueryUntilDate(
			@NonNull final StockDataRecordIdentifier key,
			@NonNull final Instant date,
			@NonNull final BPartnerClassifier customer)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(key.getWarehouseId())
				.productId(key.getProductId().getRepoId())
				.storageAttributesKey(key.getStorageAttributesKey())
				.customer(customer)
				// the key's client and org are not filtered here, and need not be: a warehouse belongs to
				// exactly one org of one client, so M_Warehouse_ID already pins both. (The MD_Stock side of
				// the expression does filter them, because MD_Stock carries them as part of its unique key.)
				// seqNo is deliberately left at 0, which yields a plain "DateProjected <= date"; a seqNo > 0
				// would additionally build a "(date < D OR (date = D AND seqNo <= n))" composite
				.timeRangeEnd(DateAndSeqNo.atTimeNoSeqNo(date).withOperator(DateAndSeqNo.Operator.INCLUSIVE))
				.build();

		return CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.matchExactStorageAttributesKey(true)
				// The type is left unset on purpose: CandidatesQuery cannot express "type != STOCK" (it only
				// ever emits an equals filter on the type), so the STOCK candidates come back too and are
				// dropped in computeTarget. Reading them is immaterial here - the largest real candidate
				// chain measured for this feature was 913 MD_Candidate rows for a single product.
				// (retrieveStoredAtp narrows this query to exactly CandidateType.STOCK instead.)
				.build();
	}
}
