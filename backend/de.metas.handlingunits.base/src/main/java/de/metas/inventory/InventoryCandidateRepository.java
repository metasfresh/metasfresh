package de.metas.inventory;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Tables: M_Inventory_Candidate
 * <br>
 * Repository Cluster: {@link InventoryCandidateService}
 */
@Repository
public class InventoryCandidateRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @return the active {@code M_Inventory_Candidate} records of the given HU, regardless of their {@code Processed} state.
	 */
	public List<I_M_Inventory_Candidate> getByHuId(@NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_Inventory_Candidate.class)
				.addEqualsFilter(I_M_Inventory_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	/**
	 * @return the active, not yet processed {@code M_Inventory_Candidate} records of the given HU.
	 */
	public List<I_M_Inventory_Candidate> getNotProcessedByHuId(@NonNull final HuId huId)
	{
		return queryNotProcessedByHuId(huId)
				.create()
				.list();
	}

	/**
	 * @return true if the given HU has an active, not yet processed {@code M_Inventory_Candidate} with a dispose reason.
	 */
	public boolean hasNotProcessedWithDisposeReason(@NonNull final HuId huId)
	{
		return queryNotProcessedByHuId(huId)
				.addNotNull(I_M_Inventory_Candidate.COLUMNNAME_DisposeReason)
				.create()
				.anyMatch();
	}

	private IQueryBuilder<I_M_Inventory_Candidate> queryNotProcessedByHuId(@NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_Inventory_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Inventory_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Inventory_Candidate.COLUMNNAME_Processed, false);
	}
}
