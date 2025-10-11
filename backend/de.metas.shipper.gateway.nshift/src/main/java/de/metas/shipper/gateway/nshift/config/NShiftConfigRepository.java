package de.metas.shipper.gateway.nshift.config;

import de.metas.cache.CCache;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Shipper;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class NShiftConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, NShiftConfigMap> cache = CCache.newCache("NShift_Shipper_Config", 1, CCache.EXPIREMINUTES_Never);

	@NonNull
	public NShiftConfig getByShipperId(@NonNull final ShipperId shipperId)
	{
		return getAll().getByShipperId(shipperId);
	}

	private @NonNull NShiftConfigMap getAll()
	{
		//noinspection DataFlowIssue
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private NShiftConfigMap retrieveAll()
	{
		return queryBL.createQueryBuilder(I_M_Shipper.class) // TODO use I_NShift_Shipper_Config table
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(NShiftConfigRepository::fromRecord)
				.collect(NShiftConfigMap.collect());
	}

	private static NShiftConfig fromRecord(I_M_Shipper record) // TODO use I_NShift_Shipper_Config as param
	{
		return NShiftConfig.builder()
				.shipperId(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				// TODO
				.build();
	}
}
