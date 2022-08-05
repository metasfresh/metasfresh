package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.impl.CostRevaluation;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.costrevaluation.impl.CostRevaluationLine;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.X_M_CostRevaluation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CostRevaluationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrentCostsRepository currentCostsRepo = SpringContextHolder.instance.getBean(ICurrentCostsRepository.class);

	public List<I_M_CostRevaluationLine> retrieveLinesByCostRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		return queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMN_M_CostRevaluation_ID, costRevaluationId)
				.orderBy(I_M_CostRevaluationLine.COLUMN_M_CostRevaluationLine_ID)
				.create()
				.list();
	}

	public I_M_CostRevaluation getById(@NonNull final CostRevaluationId costRevaluationId)
	{
		return InterfaceWrapperHelper.load(costRevaluationId, I_M_CostRevaluation.class);
	}

	@NonNull
	public CostRevaluation retrieveById(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation costRevaluationPO = getById(costRevaluationId);

		return CostRevaluation.builder()
				.costRevaluationId(costRevaluationId)
				.costElementId(CostElementId.ofRepoId(costRevaluationPO.getM_CostElement_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(costRevaluationPO.getM_CostElement_ID()))
				.build();
	}

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation costRevaluation = getById(costRevaluationId);
		return costRevaluation.getDocStatus().equals(X_M_CostRevaluation.DOCSTATUS_Drafted);
	}

	public void createCostRevaluationLinesForProductIds(@NonNull final CostRevaluation costRevaluation,
														@NonNull final ImmutableSet<ProductId> productIds)
	{
		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final CostRevaluationId costRevaluationId = costRevaluation.getCostRevaluationId();

		final List<I_M_CostRevaluationLine> revaluationLines = currentCostsRepo.getByCostElementAndProduct(acctSchemaId, costElementId, productIds)
				.stream()
				.map(cost -> fromCurrentCost(cost, costRevaluationId))
				.map(line -> toCostRevaluationLine(line))
				.collect(Collectors.toList());

		InterfaceWrapperHelper.saveAll(revaluationLines);
	}

	private CostRevaluationLine fromCurrentCost(@NonNull final CurrentCost cost, @NonNull final CostRevaluationId costRevaluationId)
	{
		final CostRevaluationLine line = CostRevaluationLine.builder()
				.costRevaluationId(costRevaluationId)
				.productId(cost.getCostSegment().getProductId())
				.currentCostPrice(cost.getCostPrice())
				.currentQty(cost.getCurrentQty())
				.build();
		return line;
	}

	public I_M_CostRevaluationLine toCostRevaluationLine(@NonNull final CostRevaluationLine costRevaluationLine)
	{
		final I_M_CostRevaluationLine line = InterfaceWrapperHelper.loadOrNew(costRevaluationLine.getId(), I_M_CostRevaluationLine.class);
		updateCostRevaluationLineRecord(line, costRevaluationLine);
		return line;
	}

	private void updateCostRevaluationLineRecord(@NonNull final I_M_CostRevaluationLine line, @NonNull final CostRevaluationLine from)
	{
		final CostPrice costPrice = from.getCurrentCostPrice();

		line.setM_CostRevaluation_ID(from.getCostRevaluationId().getRepoId());
		line.setM_Product_ID(from.getProductId().getRepoId());

		line.setCurrentCostPrice(costPrice.getOwnCostPrice().getValue());
		line.setC_Currency_ID(costPrice.getCurrencyId().getRepoId());
		line.setC_UOM_ID(costPrice.getUomId().getRepoId());

		line.setCurrentQty(from.getCurrentQty().toBigDecimal());
		if (from.getNewCostPrice() != null)
		{
			line.setNewCostPrice(from.getNewCostPrice().getOwnCostPrice().getValue());
		}
		else
		{
			line.setNewCostPrice(null);
		}
	}

	public void save(@NonNull final CostRevaluationLine costRevaluationLine)
	{
		final I_M_CostRevaluationLine line = toCostRevaluationLine(costRevaluationLine);
		InterfaceWrapperHelper.save(line);
	}
}
