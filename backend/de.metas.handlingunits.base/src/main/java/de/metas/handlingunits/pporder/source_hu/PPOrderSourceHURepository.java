package de.metas.handlingunits.pporder.source_hu;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_SourceHU;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PPOrderSourceHURepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void addSourceHU(final @NonNull PPOrderId ppOrderId, final @NonNull HuId huId)
	{
		I_PP_Order_SourceHU record = retrieveRecord(ppOrderId, huId).orElse(null);
		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_PP_Order_SourceHU.class);
			record.setPP_Order_ID(ppOrderId.getRepoId());
			record.setM_HU_ID(huId.getRepoId());
		}

		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);
	}

	private Optional<I_PP_Order_SourceHU> retrieveRecord(
			final @NonNull PPOrderId ppOrderId,
			final @NonNull HuId huId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_SourceHU.class)
				//.addOnlyActiveRecordsFilter() // all
				.addEqualsFilter(I_PP_Order_SourceHU.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addEqualsFilter(I_PP_Order_SourceHU.COLUMNNAME_M_HU_ID, huId)
				.create()
				.firstOnlyOptional(I_PP_Order_SourceHU.class);
	}

	public ImmutableSet<HuId> getSourceHUIds(final PPOrderId ppOrderId)
	{
		final List<HuId> huIds = queryBL.createQueryBuilder(I_PP_Order_SourceHU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_SourceHU.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.listDistinct(I_PP_Order_SourceHU.COLUMNNAME_M_HU_ID, HuId.class);
		return ImmutableSet.copyOf(huIds);
	}
}
