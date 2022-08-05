package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.impl.CostRevaluation;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.costrevaluation.impl.CostRevaluationLine;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.X_M_CostRevaluation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CostRevaluationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrentCostsRepository currentCostsRepo;

	public CostRevaluationRepository(
			@NonNull final ICurrentCostsRepository currentCostsRepo)
	{
		this.currentCostsRepo = currentCostsRepo;
	}

	public List<I_M_CostRevaluationLine> retrieveLinesByCostRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		return queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMN_M_CostRevaluation_ID, costRevaluationId)
				.orderBy(I_M_CostRevaluationLine.COLUMN_M_CostRevaluationLine_ID)
				.create()
				.list();
	}

	@NonNull
	public CostRevaluation getById(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation record = getRecordById(costRevaluationId);

		return CostRevaluation.builder()
				.costRevaluationId(costRevaluationId)
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getM_CostElement_ID()))
				.build();
	}

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation costRevaluation = getRecordById(costRevaluationId);
		return costRevaluation.getDocStatus().equals(X_M_CostRevaluation.DOCSTATUS_Drafted);
	}

	private I_M_CostRevaluation getRecordById(@NonNull final CostRevaluationId costRevaluationId)
	{
		return InterfaceWrapperHelper.load(costRevaluationId, I_M_CostRevaluation.class);
	}

	public void createCostRevaluationLinesForProductIds(
			@NonNull final CostRevaluation costRevaluation,
			@NonNull final ImmutableSet<ProductId> productIds)
	{
		final AcctSchemaId acctSchemaId = costRevaluation.getAcctSchemaId();
		final CostElementId costElementId = costRevaluation.getCostElementId();
		final CostRevaluationId costRevaluationId = costRevaluation.getCostRevaluationId();

		final HashMap<CostRevaluationLineKey, I_M_CostRevaluationLine> existingRecords = retrieveLinesByCostRevaluationId(costRevaluationId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(CostRevaluationRepository::extractCostRevaluationLineKey));

		for (final CurrentCost currentCost : currentCostsRepo.getByCostElementAndProduct(acctSchemaId, costElementId, productIds))
		{
			final CostRevaluationLineKey key = extractCostRevaluationLineKey(currentCost);
			I_M_CostRevaluationLine existingRecord = existingRecords.remove(key);
			if (existingRecord == null)
			{
				existingRecord = InterfaceWrapperHelper.newInstance(I_M_CostRevaluationLine.class);
				existingRecord.setM_CostRevaluation_ID(costRevaluationId.getRepoId());
				existingRecord.setAD_Org_ID(key.getClientAndOrgId().getOrgId().getRepoId());
			}

			updateRecordFrom(existingRecord, currentCost);
			InterfaceWrapperHelper.save(existingRecord);
		}

		InterfaceWrapperHelper.deleteAll(existingRecords.values());
	}

	private static CostRevaluationLineKey extractCostRevaluationLineKey(CurrentCost currentCost)
	{
		final CostSegment costSegment = currentCost.getCostSegment();

		return CostRevaluationLineKey.builder()
				.productId(costSegment.getProductId())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(costSegment.getClientId(), costSegment.getOrgId()))
				.build();
	}

	private static CostRevaluationLineKey extractCostRevaluationLineKey(I_M_CostRevaluationLine record)
	{
		return CostRevaluationLineKey.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.build();
	}

	@Value
	@Builder
	private static class CostRevaluationLineKey
	{
		@NonNull ProductId productId;
		@NonNull ClientAndOrgId clientAndOrgId;
	}

	private static CostRevaluationLine fromCurrentCost(@NonNull final CurrentCost cost, @NonNull final CostRevaluationId costRevaluationId)
	{
		return CostRevaluationLine.builder()
				.costRevaluationId(costRevaluationId)
				.productId(cost.getCostSegment().getProductId())
				.currentCostPrice(cost.getCostPrice())
				.currentQty(cost.getCurrentQty())
				.build();
	}

	public I_M_CostRevaluationLine toCostRevaluationLine(@NonNull final CostRevaluationLine costRevaluationLine)
	{
		final I_M_CostRevaluationLine line = InterfaceWrapperHelper.loadOrNew(costRevaluationLine.getId(), I_M_CostRevaluationLine.class);
		updateCostRevaluationLineRecord(line, costRevaluationLine);
		return line;
	}

	private static void updateRecordFrom(@NonNull final I_M_CostRevaluationLine record, @NonNull final CurrentCost from)
	{
		final CostPrice costPrice = from.getCostPrice();
		//record.setM_CostRevaluation_ID();
		record.setM_Product_ID(from.getCostSegment().getProductId().getRepoId());

		record.setCurrentCostPrice(costPrice.getOwnCostPrice().getValue());
		record.setC_Currency_ID(costPrice.getCurrencyId().getRepoId());
		record.setC_UOM_ID(costPrice.getUomId().getRepoId());

		record.setCurrentQty(from.getCurrentQty().toBigDecimal());
	}

	private static void updateCostRevaluationLineRecord(@NonNull final I_M_CostRevaluationLine line, @NonNull final CostRevaluationLine from)
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
