package de.metas.inventory;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
