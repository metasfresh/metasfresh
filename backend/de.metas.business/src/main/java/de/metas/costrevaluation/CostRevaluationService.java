package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.impl.CostRevaluation;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.I_M_CostRevaluation;

import java.util.List;

public class CostRevaluationService
{
	private final CostRevaluationRepository costRevaluationRepository;
	private final ICurrentCostsRepository currentCostsRepo;

	public CostRevaluationService(
			@NonNull final CostRevaluationRepository costRevaluationRepository,
			@NonNull final ICurrentCostsRepository currentCostsRepo)
	{
		this.costRevaluationRepository = costRevaluationRepository;
		this.currentCostsRepo = currentCostsRepo;
	}

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation costRevaluation = costRevaluationRepository.getRecordById(costRevaluationId);
		return DocStatus.ofCode(costRevaluation.getDocStatus()).isDrafted();
	}


	public void createCostRevaluationLinesForProductIds(
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final ImmutableSet<ProductId> productIds)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final List<CurrentCost> currentCosts = currentCostsRepo.getByCostElementAndProduct(acctSchemaId, costElementId, productIds);

		costRevaluationRepository.createCostRevaluationLinesForCurrentCosts(costRevaluationId, currentCosts);
	}
}
