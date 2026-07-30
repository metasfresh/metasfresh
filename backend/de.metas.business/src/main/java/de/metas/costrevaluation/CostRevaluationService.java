package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostsRevaluationRequest;
import de.metas.costing.CostsRevaluationResult;
import de.metas.costing.CurrentCost;
import de.metas.costing.CurrentCostQuery;
import de.metas.costing.ICostingService;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CostRevaluationService
{
	private final CostRevaluationRepository costRevaluationRepository;
	private final ICurrentCostsRepository currentCostsRepo;
	private final ICostingService costingService;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	public CostRevaluationService(
			@NonNull final CostRevaluationRepository costRevaluationRepository,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostingService costingService)
	{
		this.costRevaluationRepository = costRevaluationRepository;
		this.currentCostsRepo = currentCostsRepo;
		this.costingService = costingService;
	}

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		return costRevaluationRepository.getById(costRevaluationId).getDocStatus().isDrafted();
	}

	public boolean hasActiveLines(@NonNull final CostRevaluationId costRevaluationId)
	{
		return costRevaluationRepository.hasActiveLines(costRevaluationId);
	}

	public void deleteLinesAndDetailsByRevaluationId(@NonNull CostRevaluationId costRevaluationId)
	{
		costRevaluationRepository.deleteDetailsByRevaluationId(costRevaluationId);
		costRevaluationRepository.deleteLinesByRevaluationId(costRevaluationId);
	}

	public void createLines(@NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);

		if (costRevaluation.getRevaluationSource().isCopyFromCostElement())
		{
			createLinesFromCopyFromCostElement(costRevaluationId, costRevaluation);
		}
		else
		{
			createLinesFromCalculated(costRevaluationId, costRevaluation);
		}
	}

	/** {@code Calculated}: seeds lines from the document's own (target) cost element's current costs. */
	private void createLinesFromCalculated(
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final CostRevaluation costRevaluation)
	{
		final ClientId clientId = costRevaluation.getClientId();
		final OrgId orgId = costRevaluation.getOrgId();
		final ImmutableSet<ProductId> productIds = retrieveStockedProductIdsOrThrow(clientId);

		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final ImmutableList<CurrentCost> currentCosts = queryCurrentCosts(clientId, orgId, acctSchemaId, costElementId, productIds);

		costRevaluationRepository.createLinesForCurrentCosts(costRevaluationId, currentCosts);
	}

	/**
	 * {@code CopyFromCostElement}: seeds lines from the SOURCE element's cost as of the cut-off
	 * ({@code CopyFrom_M_CostElement_ID}), one per source {@code M_Cost} row including zero-on-hand — so the drafted
	 * lines preview exactly the numbers {@link #createDetailsForCopyFromCostElement} will write at complete time.
	 */
	private void createLinesFromCopyFromCostElement(
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final CostRevaluation costRevaluation)
	{
		final CostElementId sourceCostElementId = costRevaluation.getCopyFromCostElementId();
		if (sourceCostElementId == null)
		{
			throw new AdempiereException("CopyFrom_M_CostElement_ID is not set for " + costRevaluation.getCostRevaluationId());
		}
		assertSourceAndTargetElementDiffer(costRevaluation, sourceCostElementId);

		final ClientId clientId = costRevaluation.getClientId();
		final OrgId orgId = costRevaluation.getOrgId();
		final ImmutableSet<ProductId> productIds = retrieveStockedProductIdsOrThrow(clientId);

		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();

		final ImmutableList<CurrentCost> sourceCurrentCosts = queryCurrentCosts(clientId, orgId, acctSchemaId, sourceCostElementId, productIds);
		if (sourceCurrentCosts.isEmpty())
		{
			throw new AdempiereException("No current costs found for source cost element " + sourceCostElementId);
		}

		final ImmutableList<CurrentCost> sourceCostsAsOfCutoff = toCostsAsOf(sourceCurrentCosts, costRevaluation.getEvaluationStartDate());

		costRevaluationRepository.createLinesForCopyFromCostElement(costRevaluationId, costRevaluation.getCostElementId(), sourceCostsAsOfCutoff);
	}

	/**
	 * Restates each given live {@link CurrentCost} as the cost its element carried as of {@code asOfDate}. Only the
	 * {@code CopyFromCostElement} preview uses this; the {@code Calculated} source keeps revaluating the live cost.
	 */
	private ImmutableList<CurrentCost> toCostsAsOf(
			@NonNull final ImmutableList<CurrentCost> currentCosts,
			@NonNull final Instant asOfDate)
	{
		return currentCosts.stream()
				.map(currentCost -> toCostAsOf(currentCost, asOfDate))
				.collect(ImmutableList.toImmutableList());
	}

	private CurrentCost toCostAsOf(@NonNull final CurrentCost currentCost, @NonNull final Instant asOfDate)
	{
		final CostSegmentAndElement segmentAndElement = CostSegmentAndElement.of(currentCost.getCostSegment(), currentCost.getCostElementId());

		// Fails loudly rather than silently falling back to the live cost — the very bug the as-of read exists to fix.
		final CostDetailPreviousAmounts costAsOf = costingService.getCostAsOf(segmentAndElement, asOfDate)
				.orElseThrow(() -> new AdempiereException("No current cost found for source cost element " + segmentAndElement));

		final CurrentCost restated = currentCost.copy();
		restated.setFrom(costAsOf);
		return restated;
	}

	/**
	 * Refuses a self-copy: copying a cost element onto itself is a nonsensical no-op. Checked at create-lines
	 * (validate) time — the earliest point the source element is resolved — so the user gets a clear message
	 * instead of the confusing "no current costs for source" that the self-directed query would otherwise raise.
	 */
	private static void assertSourceAndTargetElementDiffer(
			@NonNull final CostRevaluation costRevaluation,
			@NonNull final CostElementId sourceCostElementId)
	{
		if (sourceCostElementId.equals(costRevaluation.getCostElementId()))
		{
			throw new AdempiereException("Cannot copy a cost element onto itself: CopyFrom_M_CostElement_ID equals"
					+ " M_CostElement_ID (" + sourceCostElementId + ") for " + costRevaluation.getCostRevaluationId() + ".");
		}
	}

	private ImmutableSet<ProductId> retrieveStockedProductIdsOrThrow(@NonNull final ClientId clientId)
	{
		final ImmutableSet<ProductId> productIds = productDAO.retrieveStockedProductIds(clientId);
		if (productIds.isEmpty())
		{
			throw new AdempiereException("No stocked products found");
		}
		return productIds;
	}

	private ImmutableList<CurrentCost> queryCurrentCosts(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ImmutableSet<ProductId> productIds)
	{
		return currentCostsRepo.stream(
						CurrentCostQuery.builder()
								.clientId(clientId)
								// NOTE: don't filter by OrgId here because we don't know the costing level yet
								.acctSchemaId(acctSchemaId)
								.costElementId(costElementId)
								.productIds(productIds)
								.build()
				)
				.filter(currentCost -> isMatching(currentCost, orgId))
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isMatching(@NonNull CurrentCost currentCost, @NonNull OrgId orgId)
	{
		return currentCost.getCostSegment().isMatching(orgId);
	}

	public void deleteDetailsByLineId(@NonNull final CostRevaluationLineId lineId)
	{
		costRevaluationRepository.deleteDetailsByLineId(lineId);
	}

	public void createDetails(@NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		final ImmutableList<CostRevaluationLine> linesToRevaluate = costRevaluationRepository.getLinesByCostRevaluationId(costRevaluationId)
				.stream()
				.filter(line -> !line.isRevaluated())
				.collect(ImmutableList.toImmutableList());
		if (linesToRevaluate.isEmpty())
		{
			return;
		}

		final ImmutableSet<CostRevaluationLineId> lineIds = linesToRevaluate.stream().map(CostRevaluationLine::getId).collect(ImmutableSet.toImmutableSet());
		costRevaluationRepository.deleteDetailsByLineIds(lineIds);

		// Idempotency / safety: snapshot — via ONE batch query, BEFORE any seed in this run — which of this run's products
		// already carry a cost detail written by ANY completed M_CostRevaluation line on the target element. Re-seeding an
		// already-seeded (possibly in-use) cost would be a value change requiring a GL adjustment (the separate, deferred
		// feature), so here we skip it value-neutrally.
		//
		// DESIGN DECISION — the "already seeded" signal is deliberately BROAD (any completed cost-revaluation line on the
		// element/product, not only a prior CopyFromCostElement switch), and that is the safer choice:
		//  - In scope for this feature is the PRE-activation switch, which only ever writes cost details on the MAI
		//    (target) element via CopyFromCostElement — so the broad signal and the narrow one coincide here.
		//  - The only case where they diverge is a POST-activation Calculated-then-switch interleave (an ordinary
		//    Calculated revaluation runs on the MAI element after the accounting method was activated, then a switch is
		//    attempted). That method-activation is a separate, decoupled feature and is OUT OF SCOPE here; in that
		//    interleave a broad silent skip (leave the existing history untouched) is safer than a narrow re-seed that
		//    would silently overwrite an in-use cost with a fresh zero-GL opening.
		final ImmutableSet<ProductId> alreadySeededProductIds = retrieveAlreadySeededProductIds(costRevaluation, linesToRevaluate);

		for (final CostRevaluationLine line : linesToRevaluate)
		{
			if (alreadySeededProductIds.contains(line.getCostSegmentAndElement().getProductId()))
			{
				// Skip the seed and deactivate the line so THIS document owns only the products it freshly seeds
				// (keeps each switch self-contained and its reversal symmetric — a redundant re-switch cannot later
				// disturb the product the first switch still owns).
				costRevaluationRepository.deactivateLine(line.getId());
				continue;
			}
			createDetails(costRevaluation, line);
		}
	}

	/**
	 * Returns this run's products that MUST be skipped because the target element already carries a cost detail written by
	 * a completed {@code M_CostRevaluation} line — a broad, source-agnostic signal (see the DESIGN DECISION note in
	 * {@link #createDetails}); it fires for ANY completed cost-revaluation line, not only a prior {@code CopyFromCostElement}
	 * switch. Empty for non-{@code CopyFromCostElement} sources (only the copy-seed path is value-neutral to skip).
	 */
	private ImmutableSet<ProductId> retrieveAlreadySeededProductIds(
			@NonNull final CostRevaluation costRevaluation,
			@NonNull final ImmutableList<CostRevaluationLine> linesToRevaluate)
	{
		if (!costRevaluation.getRevaluationSource().isCopyFromCostElement())
		{
			return ImmutableSet.of();
		}

		final ImmutableSet<ProductId> productIds = linesToRevaluate.stream()
				.map(line -> line.getCostSegmentAndElement().getProductId())
				.collect(ImmutableSet.toImmutableSet());

		return costingService.retrieveProductIdsAlreadySeededOnCostElement(
				costRevaluation.getAcctSchemaId(),
				costRevaluation.getCostElementId(),
				productIds);
	}

	/**
	 * {@code CopyFromCostElement} reversal: value-neutral, symmetric undo of {@link #createDetailsForCopyFromCostElement}
	 * for every line — removes each target element's opening anchor and resets its {@code M_Cost} to the pre-switch
	 * (absent/zero) state. Refused (per line) if a cost event after the cut-off has already built on the seed.
	 */
	public void reverseDetails(@NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		if (!costRevaluation.getRevaluationSource().isCopyFromCostElement())
		{
			// Reversal for the history-replay (Calculated) source is not implemented yet.
			throw new AdempiereException("Reversal is only implemented for the CopyFromCostElement source: " + costRevaluationId);
		}

		for (final CostRevaluationLine line : costRevaluationRepository.getLinesByCostRevaluationId(costRevaluationId))
		{
			costingService.reverseSeededCurrentCost(
					line.getCostSegmentAndElement(),
					costRevaluation.getEvaluationStartDate(),
					line.getId());
		}
	}

	private void createDetails(@NonNull final CostRevaluation costRevaluation, @NonNull final CostRevaluationLine line)
	{
		if (line.isRevaluated())
		{
			throw new AdempiereException("Line already revaluated: " + line.getId());
		}

		if (costRevaluation.getRevaluationSource().isCopyFromCostElement())
		{
			createDetailsForCopyFromCostElement(costRevaluation, line);
		}
		else
		{
			createDetailsForCalculated(costRevaluation, line);
		}
	}

	/** {@code Calculated}: history-replay revaluation writing the before / adjustment / after cost details. */
	private void createDetailsForCalculated(@NonNull final CostRevaluation costRevaluation, @NonNull final CostRevaluationLine line)
	{
		final CostSegmentAndElement costSegmentAndElement = line.getCostSegmentAndElement();
		final CostsRevaluationResult result = costingService.revaluateCosts(CostsRevaluationRequest.builder()
				.costSegmentAndElement(costSegmentAndElement)
				.evaluationStartDate(costRevaluation.getEvaluationStartDate())
				.newCostPrice(line.getNewCostPrice())
				.build());

		final CostRevaluationLineId lineId = line.getId();
		final SeqNoProvider seqNo = SeqNoProvider.ofInt(10);
		CostAmount deltaAmountTotal = CostAmount.zero(line.getNewCostPrice().getCurrencyId());

		//
		// Current Cost Before Revaluation Adjustment:
		{
			final CostsRevaluationResult.CurrentCostBeforeEvaluation currentCostBeforeEvaluation = result.getCurrentCostBeforeEvaluation();
			final Quantity qty = currentCostBeforeEvaluation.getQty();
			final CostAmount costPriceOld = currentCostBeforeEvaluation.getCostPriceOld();
			final CostAmount costPriceNew = currentCostBeforeEvaluation.getCostPriceNew();
			final CostAmount costAmountOld = costPriceOld.multiply(qty);
			final CostAmount costAmountNew = costPriceNew.multiply(qty);
			final CostAmount deltaAmount = costAmountNew.subtract(costAmountOld);
			deltaAmountTotal = deltaAmountTotal.add(deltaAmount);

			costRevaluationRepository.createDetail(CostRevaluationDetailCreateRequest.builder()
					.lineId(lineId)
					.seqNo(seqNo.getAndIncrement())
					.type(CostRevaluationDetailType.CurrentCostBeforeRevaluation)
					.costSegmentAndElement(costSegmentAndElement)
					//
					.qty(qty)
					.oldCostPrice(costPriceOld)
					.newCostPrice(costPriceNew)
					.oldAmount(costAmountOld)
					.newAmount(costAmountNew)
					.deltaAmount(deltaAmount)
					//
					.build());
		}

		//
		// Cost Detail Adjustments:
		for (final CostDetailAdjustment costDetailAdjustment : result.getCostDetailAdjustments())
		{
			final CostAmount oldCostAmount = costDetailAdjustment.getOldCostAmount();
			final CostAmount newCostAmount = costDetailAdjustment.getNewCostAmount();
			final CostAmount deltaAmount = newCostAmount.subtract(oldCostAmount);
			deltaAmountTotal = deltaAmountTotal.add(deltaAmount);

			costRevaluationRepository.createDetail(CostRevaluationDetailCreateRequest.builder()
					.lineId(lineId)
					.seqNo(seqNo.getAndIncrement())
					.type(CostRevaluationDetailType.CostDetailAdjustment)
					.costSegmentAndElement(costSegmentAndElement)
					//
					.qty(costDetailAdjustment.getQty())
					.oldCostPrice(costDetailAdjustment.getOldCostPrice())
					.newCostPrice(costDetailAdjustment.getNewCostPrice())
					.oldAmount(oldCostAmount)
					.newAmount(newCostAmount)
					.deltaAmount(deltaAmount)
					//
					.costDetailId(costDetailAdjustment.getCostDetailId())
					.build());
		}

		//
		// Current Cost After Revaluation Adjustment:
		{
			final CostsRevaluationResult.CurrentCostAfterEvaluation currentCostAfterEvaluation = result.getCurrentCostAfterEvaluation();
			final CostAmount oldCostPrice = currentCostAfterEvaluation.getCostPriceComputed();
			final CostAmount newCostPrice = line.getNewCostPrice();

			if (!oldCostPrice.compareToEquals(newCostPrice))
			{
				final Quantity qty = currentCostAfterEvaluation.getQty();
				final CostAmount oldCostAmount = oldCostPrice.multiply(qty);
				final CostAmount newCostAmount = newCostPrice.multiply(qty);
				final CostAmount deltaAmount = newCostAmount.subtract(oldCostAmount);
				deltaAmountTotal = deltaAmountTotal.add(deltaAmount);

				costRevaluationRepository.createDetail(CostRevaluationDetailCreateRequest.builder()
						.lineId(lineId)
						.seqNo(seqNo.getAndIncrement())
						.type(CostRevaluationDetailType.CurrentCostAfterRevaluation)
						.costSegmentAndElement(costSegmentAndElement)
						//
						.qty(qty)
						.oldCostPrice(oldCostPrice)
						.newCostPrice(newCostPrice)
						.oldAmount(oldCostAmount)
						.newAmount(newCostAmount)
						.deltaAmount(deltaAmount)
						//
						.build());
			}
		}

		costRevaluationRepository.save(line.markingAsEvaluated(deltaAmountTotal));
	}

	/**
	 * {@code CopyFromCostElement} complete-time path: directly sets the target element's {@code M_Cost} from the source's
	 * opening amounts and writes one opening-anchor {@code M_CostDetail}, instead of the {@code Calculated} history-replay.
	 * <p>
	 * The opening is the source's cost <b>as of the cut-off</b> ({@code EvaluationStartDate}) — not its live cost at
	 * complete time. For a back-dated switch those differ: the live {@code M_Cost} row already reflects every movement
	 * booked since the cut-off, so copying it would open the target with a value the source never had at the cut-off.
	 * <p>
	 * <b>THE "OPENING ANCHOR" — CANONICAL DEFINITION.</b> This is the one place the <i>anchor</i> is created, so this is
	 * where the term is defined; the Java, SQL and tests of this feature all mean exactly the row described here.
	 * <p>
	 * <b>ELI5:</b> a product is moved to a new costing method as of a date in the PAST. Somebody has to write down
	 * "on that date the product was worth X" — otherwise the new method has nothing to start counting from. The anchor
	 * is that written-down number.
	 * <p>
	 * <b>What it is:</b> the single {@code M_CostDetail} row written here (via
	 * {@link ICostingService#seedCurrentCostFromOpening}). It is dated AT the cut-off and its {@code Prev_*} fields carry
	 * the PREVIOUS method's cost <b>as of that date</b> — not that method's later value. It is the new method's
	 * <b>opening balance</b>.
	 * <p>
	 * <b>Why "anchor":</b> it is the fixed point the product's cost history under the new method hangs from — every later
	 * cost detail is computed forward from it.
	 * <p>
	 * <b>How to recognise it:</b> {@code M_CostRevaluationLine_ID IS NOT NULL} (that nullable FK is set only on a cost
	 * detail written by a completed {@code M_CostRevaluation} line), {@code IsChangingCosts='Y'}, own {@code Qty} and
	 * {@code Amt} zero, and {@code Prev_*} equal to the seeded opening.
	 * <p>
	 * <b>Its three jobs:</b>
	 * <ol>
	 *     <li>it is the only record of the opening balance — no other row carries it;</li>
	 *     <li>it is the row {@link ICostingService#reverseSeededCurrentCost} deletes to undo the switch;</li>
	 *     <li>it is the marker the double-seed guard keys on — {@link #retrieveAlreadySeededProductIds} →
	 *         {@code CostDetailRepository#retrieveProductIdsWithCostRevaluationSeed}, i.e. the same
	 *         {@code M_CostRevaluationLine_ID IS NOT NULL} — so an already-switched product is never re-seeded.</li>
	 * </ol>
	 * <p>
	 * <b>Hence the guard in {@code de_metas_acct.m_costdetail_delete_from_date}:</b> a cost recompute whose start date
	 * reaches back over the cut-off would delete the anchor, so that function refuses the range instead. Note what is NOT
	 * the problem: deleting the anchor does not zero-base the new method — the anchor is {@code IsChangingCosts='Y'} and
	 * its {@code Prev_*} ARE the seeded opening, so the function's "Approach 1" restores {@code M_Cost} to identical
	 * numbers. The damage is losing the ROW: no opening-balance record, no basis for reversal, and a blind double-seed
	 * guard that would let a re-switch re-seed an already-in-use cost.
	 */
	private void createDetailsForCopyFromCostElement(
			@NonNull final CostRevaluation costRevaluation,
			@NonNull final CostRevaluationLine line)
	{
		final CostElementId sourceCostElementId = costRevaluation.getCopyFromCostElementId();
		if (sourceCostElementId == null)
		{
			throw new AdempiereException("CopyFrom_M_CostElement_ID is not set for " + costRevaluation.getCostRevaluationId());
		}

		final CostSegmentAndElement targetSegmentAndElement = line.getCostSegmentAndElement();
		final CostSegment targetSegment = targetSegmentAndElement.toCostSegment();
		final CostSegmentAndElement sourceSegmentAndElement = CostSegmentAndElement.of(targetSegment, sourceCostElementId);

		final CostDetailPreviousAmounts sourceCostAsOfCutoff = costingService.getCostAsOf(sourceSegmentAndElement, costRevaluation.getEvaluationStartDate())
				.orElseThrow(() -> new AdempiereException("No current cost found for source cost element " + sourceSegmentAndElement));

		// Value-neutral seed: snapshot own price + LL + qty together from that SINGLE as-of-the-cut-off read of the
		// source — never a stale-own/qty + fresh-LL mix. The line's own/qty are only the drafted preview frozen at
		// create-lines time; a gap during which the still-active source keeps moving would make them inconsistent with
		// a fresh LL and silently corrupt the target's forward-costing base.
		final CostPrice sourceCostPrice = sourceCostAsOfCutoff.getCostPrice();
		final Quantity sourceQty = sourceCostAsOfCutoff.getQty();
		final CostAmount cumulatedAmt = sourceCostPrice.getOwnCostPrice().multiply(sourceQty);

		// ONE opening object, reused for both the M_Cost seed and the anchor's Prev_*.
		final CostDetailPreviousAmounts opening = CostDetailPreviousAmounts.builder()
				.costPrice(sourceCostPrice)
				.qty(sourceQty)
				.cumulatedAmt(cumulatedAmt)
				.cumulatedQty(sourceQty)
				.build();

		costingService.seedCurrentCostFromOpening(
				targetSegmentAndElement,
				opening,
				costRevaluation.getEvaluationStartDate(),
				line.getId());

		// No GL delta at this stage (GL posting is a separate concern); mark the line evaluated with a zero delta.
		costRevaluationRepository.save(line.markingAsEvaluated(CostAmount.zero(line.getNewCostPrice().getCurrencyId())));
	}
}
