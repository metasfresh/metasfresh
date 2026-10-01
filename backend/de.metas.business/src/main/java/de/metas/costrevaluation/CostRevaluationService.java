package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
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
import de.metas.util.lang.SeqNo;
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
	 * Adds one line for {@code productId}, for the cost segment of the product's current cost, with the given {@code newCostPrice}.
	 * <p>
	 * A product without a current cost yet gets one at quantity 0; completing its line books no value difference.
	 * <p>
	 * Other lines of the revaluation are left untouched.
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

		final OrgId orgId = getOrgIdToMatch(costRevaluation, productId);

		CurrentCost currentCost = resolveCurrentCost(costRevaluation, productId, orgId).orElse(null);
		if (currentCost == null)
		{
			// a stocked product may have no current cost yet (e.g. a migrated product)
			currentCostsRepo.createDefaultProductCosts(productBL.getById(productId));
			currentCost = resolveCurrentCost(costRevaluation, productId, orgId)
					.orElseThrow(() -> new AdempiereException(MSG_NoCurrentCostForProduct, productBL.getProductValueAndName(productId)));
		}

		final CostAmount newCostAmount = CostAmount.of(newCostPrice, currentCost.getCurrencyId());
		return costRevaluationRepository.createLineForCurrentCost(costRevaluationId, currentCost, newCostAmount);
	}

	/**
	 * The org to match the product's current cost by: the revaluation's org when the product is costed at
	 * {@link CostingLevel#Organization}, {@code null} (any org) otherwise.
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
	 * The product's single current cost for the revaluation's costing context and the given {@code orgId} ({@code null} = any org).
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
	 * Evaluates all lines again, also those already evaluated, so the value differences reflect the current stock and cost price.
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
				.dateAcct(costRevaluation.getDateAcct())
				.newCostPrice(line.getNewCostPrice())
				.build());

		final CostsRevaluationResult.CurrentCostBeforeEvaluation currentCostBeforeEvaluation = result.getCurrentCostBeforeEvaluation();
		final Quantity qty = currentCostBeforeEvaluation.getQty();
		final CostAmount costPriceOld = currentCostBeforeEvaluation.getCostPriceOld();
		final CostAmount costPriceNew = currentCostBeforeEvaluation.getCostPriceNew();
		final CostAmount costAmountOld = costPriceOld.multiply(qty);
		final CostAmount costAmountNew = costPriceNew.multiply(qty);
		final CostAmount deltaAmount = costAmountNew.subtract(costAmountOld);

		costRevaluationRepository.createDetail(CostRevaluationDetailCreateRequest.builder()
				.lineId(line.getId())
				.seqNo(SeqNo.ofInt(10))
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

		costRevaluationRepository.save(line.markingAsEvaluated(deltaAmount));
	}
}
