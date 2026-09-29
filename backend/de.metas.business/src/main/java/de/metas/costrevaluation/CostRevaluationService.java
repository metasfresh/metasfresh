package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailAdjustment;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostsRevaluationRequest;
import de.metas.costing.CostsRevaluationResult;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.CurrentCostQuery;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostingService;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CostRevaluationService
{
	static final AdMessageKey MSG_LineAlreadyExistsForProduct = AdMessageKey.of("M_CostRevaluation.LineAlreadyExistsForProduct");
	static final AdMessageKey MSG_NoCurrentCostForProduct = AdMessageKey.of("M_CostRevaluation.NoCurrentCostForProduct");
	static final AdMessageKey MSG_OrgRequiredForOrgCostingLevel = AdMessageKey.of("M_CostRevaluation.OrgRequiredForOrgCostingLevel");
	static final AdMessageKey MSG_AmbiguousCurrentCost = AdMessageKey.of("M_CostRevaluation.AmbiguousCurrentCost");
	static final AdMessageKey MSG_NewCostPriceNegative = AdMessageKey.of("M_CostRevaluation.NewCostPriceNegative");
	static final AdMessageKey MSG_DocumentNotDraft = AdMessageKey.of("M_CostRevaluation.DocumentNotDraft");

	private final CostRevaluationRepository costRevaluationRepository;
	private final ICurrentCostsRepository currentCostsRepo;
	private final CostingService costingService;
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	public CostRevaluationService(
			@NonNull final CostRevaluationRepository costRevaluationRepository,
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final CostingService costingService)
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

		final ClientId clientId = costRevaluation.getClientId();
		final OrgId orgId = costRevaluation.getOrgId();
		final ImmutableSet<ProductId> productIds = productBL.retrieveStockedProductIds(clientId);
		if (productIds.isEmpty())
		{
			throw new AdempiereException("No stocked products found");
		}

		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final ImmutableList<CurrentCost> currentCosts = currentCostsRepo.stream(
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

		costRevaluationRepository.createLinesForCurrentCosts(costRevaluationId, currentCosts);
	}

	private static boolean isMatching(@NonNull CurrentCost currentCost, @NonNull OrgId orgId)
	{
		return currentCost.getCostSegment().isMatching(orgId);
	}

	/**
	 * Manual, single-product counterpart of {@link #createLines(CostRevaluationId)}: adds one line for {@code productId},
	 * deriving its cost segment from the product's live {@link CurrentCost} (same derivation as the bulk path) and setting
	 * {@code NewCostPrice} to the given {@code newCostPrice} instead of defaulting it to the live cost.
	 * <p>
	 * When the product has no {@link CurrentCost} row yet for the revaluation's costing context, the row is seeded at
	 * quantity 0 (reusing the product interceptor's {@link ICurrentCostsRepository#createDefaultProductCosts}) and the
	 * line proceeds through the normal path; completing it books a zero delta, so no accounting is written.
	 * <p>
	 * Additive only: never touches any other line of the revaluation.
	 *
	 * @throws AdempiereException if {@code newCostPrice} is negative, if the revaluation is not drafted / in progress,
	 * if an active line already exists for {@code productId} (duplicate guard), if the product still has
	 * no current cost after seeding (unsupported costing setup), or if it has more than one (ambiguous multi-segment product).
	 * @return the id of the newly created line.
	 */
	@NonNull
	public CostRevaluationLineId createLineForProduct(
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final ProductId productId,
			@NonNull final BigDecimal newCostPrice)
	{
		if (newCostPrice.signum() < 0)
		{
			throw new AdempiereException(MSG_NewCostPriceNegative);
		}

		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		if (!costRevaluation.getDocStatus().isDraftedOrInProgress())
		{
			throw new AdempiereException(MSG_DocumentNotDraft);
		}

		if (costRevaluationRepository.hasActiveLineForProduct(costRevaluationId, productId))
		{
			throw new AdempiereException(MSG_LineAlreadyExistsForProduct, productBL.getProductValueAndName(productId));
		}

		// The caller owns the costing-level decision: filter the current-cost lookup by the header org only when the
		// product is costed at organization level; otherwise (client / batch-lot level) don't filter by org.
		final OrgId orgId = getOrgIdToMatch(costRevaluation, productId);

		CurrentCost currentCost = resolveCurrentCost(costRevaluation, productId, orgId).orElse(null);
		if (currentCost == null)
		{
			// Seed-cost path: a stocked product may genuinely have no M_Cost row yet (e.g. migrated/legacy product).
			// Materialize the missing row(s) at quantity 0 by reusing the same creator the product interceptor uses at
			// product creation (idempotent: only missing rows are created), then re-resolve.
			currentCostsRepo.createDefaultProductCosts(productBL.getById(productId));
			currentCost = resolveCurrentCost(costRevaluation, productId, orgId)
					.orElseThrow(() -> new AdempiereException(MSG_NoCurrentCostForProduct, productBL.getProductValueAndName(productId)));
		}

		final CostAmount newCostAmount = CostAmount.of(newCostPrice, currentCost.getCurrencyId());
		return costRevaluationRepository.createLineForCurrentCost(costRevaluationId, currentCost, newCostAmount);
	}

	/**
	 * The org to match the product's current cost by, decided from the product's costing level (the same costing level
	 * {@code CurrentCostsLoader} uses, {@link IProductCostingBL#getCostingLevel}; the bulk path reaches the same result via
	 * {@link de.metas.costing.CostSegment#isMatching}): the header document's org when the product is costed at
	 * {@link CostingLevel#Organization}, {@code null} (no org filter) otherwise.
	 *
	 * @throws AdempiereException if the product is costed at organization level but the header org is {@link OrgId#ANY}.
	 */
	@Nullable
	private OrgId getOrgIdToMatch(@NonNull final CostRevaluation costRevaluation, @NonNull final ProductId productId)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, costRevaluation.getAcctSchemaId());
		if (!costingLevel.isOrg())
		{
			return null;
		}

		final OrgId orgId = costRevaluation.getOrgId();
		if (orgId.isAny())
		{
			throw new AdempiereException(MSG_OrgRequiredForOrgCostingLevel, productBL.getProductValueAndName(productId));
		}
		return orgId;
	}

	/**
	 * Resolves the product's single current cost for the revaluation's costing context, filtering the query by
	 * {@code orgId} (nullable — no org filter when {@code null}).
	 *
	 * @return the matching {@link CurrentCost}, or {@link Optional#empty()} when the product has none yet.
	 * @throws AdempiereException if more than one current cost matches (ambiguous multi-segment product).
	 */
	private Optional<CurrentCost> resolveCurrentCost(
			@NonNull final CostRevaluation costRevaluation,
			@NonNull final ProductId productId,
			@Nullable final OrgId orgId)
	{
		final ImmutableList<CurrentCost> currentCosts = currentCostsRepo.list(
				CurrentCostQuery.builder()
						.clientId(costRevaluation.getClientId())
						.orgId(orgId)
						.acctSchemaId(costRevaluation.getAcctSchemaId())
						.costElementId(costRevaluation.getCostElementId())
						.productId(productId)
						.build());

		if (currentCosts.size() > 1)
		{
			throw new AdempiereException(MSG_AmbiguousCurrentCost, productBL.getProductValueAndName(productId), currentCosts.size());
		}

		return currentCosts.isEmpty() ? Optional.empty() : Optional.of(currentCosts.get(0));
	}

	public void deleteDetailsByLineId(@NonNull final CostRevaluationLineId lineId)
	{
		costRevaluationRepository.deleteDetailsByLineId(lineId);
	}

	/**
	 * Evaluates the lines not evaluated yet ("Run").
	 */
	public void createDetails(@NonNull final CostRevaluationId costRevaluationId)
	{
		createDetails(costRevaluationId, false);
	}

	/**
	 * Evaluates all lines again, also those already evaluated by "Run": used when completing, so the refusals and the deltas
	 * reflect the state at completion (e.g. another revaluation completed since "Run").
	 */
	public void reevaluateAllLines(@NonNull final CostRevaluationId costRevaluationId)
	{
		createDetails(costRevaluationId, true);
	}

	private void createDetails(@NonNull final CostRevaluationId costRevaluationId, final boolean includeEvaluatedLines)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		final ImmutableList<CostRevaluationLine> linesToRevaluate = costRevaluationRepository.getLinesByCostRevaluationId(costRevaluationId)
				.stream()
				.filter(line -> includeEvaluatedLines || !line.isRevaluated())
				.collect(ImmutableList.toImmutableList());
		if (linesToRevaluate.isEmpty())
		{
			return;
		}

		final ImmutableSet<CostRevaluationLineId> lineIds = linesToRevaluate.stream().map(CostRevaluationLine::getId).collect(ImmutableSet.toImmutableSet());
		costRevaluationRepository.deleteDetailsByLineIds(lineIds);

		for (final CostRevaluationLine line : linesToRevaluate)
		{
			createDetails(costRevaluation, line);
		}
	}

	private void createDetails(@NonNull final CostRevaluation costRevaluation, @NonNull final CostRevaluationLine line)
	{
		final CostSegmentAndElement costSegmentAndElement = line.getCostSegmentAndElement();
		final CostsRevaluationResult result = costingService.revaluateCosts(CostsRevaluationRequest.builder()
				.costSegmentAndElement(costSegmentAndElement)
				.evaluationStartDate(costRevaluation.getEvaluationStartDate())
				.dateAcct(costRevaluation.getDateAcct())
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
}
