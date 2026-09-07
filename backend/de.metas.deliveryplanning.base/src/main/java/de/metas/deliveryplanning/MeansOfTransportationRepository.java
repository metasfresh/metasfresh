package de.metas.deliveryplanning;

import de.metas.cache.CCache;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_MeansOfTransportation;
import org.springframework.stereotype.Repository;

@Repository
class MeansOfTransportationRepository
{
	private final CCache<MeansOfTransportationId, MeansOfTransportation> cacheById = CCache.<MeansOfTransportationId, MeansOfTransportation>builder()
			.cacheName(I_M_MeansOfTransportation.Table_Name)
			.build();

	public MeansOfTransportation getById(@NonNull MeansOfTransportationId id)
	{
		return cacheById.getOrLoad(id, this::retrieveById);
	}

	public MeansOfTransportation retrieveById(@NonNull MeansOfTransportationId id)
	{
		final I_M_MeansOfTransportation record = InterfaceWrapperHelper.load(id, I_M_MeansOfTransportation.class);
		return fromRecord(record);
	}

	private static MeansOfTransportation fromRecord(@NonNull I_M_MeansOfTransportation record)
	{
		return MeansOfTransportation.builder()
				.id(MeansOfTransportationId.ofRepoId(record.getM_MeansOfTransportation_ID()))
				.type(MeansOfTransportationType.ofNullableCode(record.getType_MoT()))
				.code(record.getValue())
				.name(record.getName())
				.IMO_MMSI_Number(StringUtils.trimBlankToNull(record.getIMO_MMSI_Number()))
				.plateNumber(StringUtils.trimBlankToNull(record.getPlate_Number()))
				.RTC(StringUtils.trimBlankToNull(record.getRTC()))
				.plane(StringUtils.trimBlankToNull(record.getPlane()))
				.build();
	}

}
