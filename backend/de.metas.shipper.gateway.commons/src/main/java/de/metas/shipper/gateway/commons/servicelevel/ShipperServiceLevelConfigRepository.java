package de.metas.shipper.gateway.commons.servicelevel;

import com.google.common.annotations.VisibleForTesting;
import de.metas.cache.CCache;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Shipper_ServiceLevel_Config;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class ShipperServiceLevelConfigRepository
{
	@VisibleForTesting
	public static ShipperServiceLevelConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ShipperServiceLevelConfigRepository();
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ShipperServiceLevelConfigList> cache = CCache.<Integer, ShipperServiceLevelConfigList>builder()
			.tableName(I_M_Shipper_ServiceLevel_Config.Table_Name)
			.build();

	public ShipperServiceLevelConfigList getByShipperId(@NonNull final ShipperId shipperId)
	{
		return getList().subsetOf(shipperId);
	}

	private ShipperServiceLevelConfigList getList()
	{
		//noinspection DataFlowIssue
		return cache.getOrLoad(0, this::retrieveList);
	}

	private ShipperServiceLevelConfigList retrieveList()
	{
		return ShipperServiceLevelConfigList.ofCollection(queryBL.createQueryBuilder(I_M_Shipper_ServiceLevel_Config.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.map(ShipperServiceLevelConfigRepository::fromRecord)
				.collect(Collectors.toList()));
	}

	private static ShipperServiceLevelConfig fromRecord(@NonNull final I_M_Shipper_ServiceLevel_Config record)
	{
		return ShipperServiceLevelConfig.builder()
				.id(ShipperServiceLevelConfigId.ofRepoId(record.getM_Shipper_ServiceLevel_Config_ID()))
				.shipperId(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.externalSystemId(ExternalSystemId.ofRepoIdOrNull(record.getExternal_System_ID()))
				.serviceLevel(record.getServiceLevel())
				.build();
	}
}
