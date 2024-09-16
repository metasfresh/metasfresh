package de.metas.pos;

import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.pricing.PriceListId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_POS;
import org.springframework.stereotype.Repository;

@Repository
public class POSConfigRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CCache<Integer, POSConfig> cache = CCache.<Integer, POSConfig>builder()
			.tableName(I_C_POS.Table_Name)
			.initialCapacity(1)
			.expireMinutes(0)
			.build();

	public POSConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	private POSConfig retrieveConfig()
	{
		return fromRecord(retrieveConfigRecord());
	}

	private I_C_POS retrieveConfigRecord()
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_POS.class);
	}

	private static POSConfig fromRecord(@NonNull final I_C_POS record)
	{
		return POSConfig.builder()
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.salesRepId(UserId.ofRepoIdOrNull(record.getSalesRep_ID()))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.build();
	}
}
