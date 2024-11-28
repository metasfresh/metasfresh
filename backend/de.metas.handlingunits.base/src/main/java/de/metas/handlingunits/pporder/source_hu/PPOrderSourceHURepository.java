package de.metas.handlingunits.pporder.source_hu;

import com.google.common.collect.ImmutableMap;
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Repository
public class PPOrderSourceHURepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void addSourceHUs(final @NonNull PPOrderId ppOrderId, final @NonNull Set<HuId> huIds)
	{
		final Map<HuId, I_PP_Order_SourceHU> huId2ExistingSource = retrieveRecordsIfExist(ppOrderId, huIds);

		huIds.stream()
				.map(huId -> Optional
						.ofNullable(huId2ExistingSource.get(huId))
						.orElseGet(() -> initRecord(huId, ppOrderId)))
				.peek(sourceRecord -> sourceRecord.setIsActive(true))
				.forEach(InterfaceWrapperHelper::save);
	}

	public void addSourceHU(final @NonNull PPOrderId ppOrderId, final @NonNull HuId huId)
	{
		addSourceHUs(ppOrderId, ImmutableSet.of(huId));
	}

	@NonNull
	private Map<HuId, I_PP_Order_SourceHU> retrieveRecordsIfExist(
			final @NonNull PPOrderId ppOrderId,
			final @NonNull Set<HuId> huIds)
	{
		return queryBL.createQueryBuilder(I_PP_Order_SourceHU.class)
				//.addOnlyActiveRecordsFilter() // all
				.addEqualsFilter(I_PP_Order_SourceHU.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addInArrayFilter(I_PP_Order_SourceHU.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(sourceHU -> HuId.ofRepoId(sourceHU.getM_HU_ID()),
													 Function.identity()));
	}

	@NonNull
	public ImmutableSet<HuId> getSourceHUIds(final PPOrderId ppOrderId)
	{
		final List<HuId> huIds = queryBL.createQueryBuilder(I_PP_Order_SourceHU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_SourceHU.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.listDistinct(I_PP_Order_SourceHU.COLUMNNAME_M_HU_ID, HuId.class);
		return ImmutableSet.copyOf(huIds);
	}

	@NonNull
	private I_PP_Order_SourceHU initRecord(@NonNull final HuId huId, @NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order_SourceHU record = InterfaceWrapperHelper.newInstance(I_PP_Order_SourceHU.class);
		record.setPP_Order_ID(ppOrderId.getRepoId());
		record.setM_HU_ID(huId.getRepoId());

		return record;
	}
}
