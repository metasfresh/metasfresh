package de.metas.costrevaluation;

import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_CostRevaluationLine;
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
}
