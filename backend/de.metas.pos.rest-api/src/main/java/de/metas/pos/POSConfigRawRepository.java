package de.metas.pos;

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
public class POSConfigRawRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public POSConfigRaw getConfig()
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

	private static POSConfigRaw fromRecord(@NonNull final I_C_POS record)
	{
		return POSConfigRaw.builder()
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.salesRepId(UserId.ofRepoIdOrNull(record.getSalesRep_ID()))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.build();
	}
}
