package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.impl.CostRevaluation;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.costrevaluation.impl.CostRevaluationLine;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.util.List;

public class CostRevaluationService
{
	private final CostRevaluationRepository costRevaluationRepository;
	private final ICurrentCostsRepository currentCostsRepo;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	public CostRevaluationService(
			@NonNull final CostRevaluationRepository costRevaluationRepository,
			@NonNull final ICurrentCostsRepository currentCostsRepo)
	{
		this.costRevaluationRepository = costRevaluationRepository;
		this.currentCostsRepo = currentCostsRepo;
	}

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		return costRevaluationRepository.getById(costRevaluationId).getDocStatus().isDrafted();
	}

	public void createLines(@NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);

		final ClientId clientId = costRevaluation.getClientId();
		final ImmutableSet<ProductId> productIds = productDAO.retrieveStockedProductIds(clientId);
		if (productIds.isEmpty())
		{
			throw new AdempiereException("No stocked products found");
		}

		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final List<CurrentCost> currentCosts = currentCostsRepo.getByCostElementAndProduct(acctSchemaId, costElementId, productIds);

		costRevaluationRepository.createLinesForCurrentCosts(costRevaluationId, currentCosts);
	}

	public void createDetails(@NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluation costRevaluation = costRevaluationRepository.getById(costRevaluationId);
		for (final CostRevaluationLine line : costRevaluationRepository.getLinesByCostRevaluationId(costRevaluationId))
		{
			createDetails(costRevaluation, line);
		}
	}

	private void createDetails(@NonNull final CostRevaluation costRevaluation, @NonNull final CostRevaluationLine line)
	{
		// TODO
	}
}
