package de.metas.costrevaluation;

import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_CostRevaluation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CostRevaluationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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

	public boolean isDraftedDocument(@NonNull final CostRevaluationId costRevaluationId)
	{
		final I_M_CostRevaluation costRevaluation = getById(costRevaluationId);
		return costRevaluation.getDocStatus().equals(X_M_CostRevaluation.DOCSTATUS_Drafted);
	}
}
