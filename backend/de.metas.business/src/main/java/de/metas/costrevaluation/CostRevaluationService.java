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
import de.metas.costing.CurrentCost;
import de.metas.costing.CurrentCostQuery;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.impl.CostingService;
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

@Service
public class CostRevaluationService
{
	private final CostRevaluationRepository costRevaluationRepository;
	private final ICurrentCostsRepository currentCostsRepo;
	private final CostingService costingService;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

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
		final ImmutableSet<ProductId> productIds = productDAO.retrieveStockedProductIds(clientId);
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

		for (final CostRevaluationLine line : linesToRevaluate)
		{
			createDetails(costRevaluation, line);
		}
	}

	private void createDetails(@NonNull final CostRevaluation costRevaluation, @NonNull final CostRevaluationLine line)
	{
		if (line.isRevaluated())
		{
			throw new AdempiereException("Line already revaluated: " + line.getId());
		}

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
}
