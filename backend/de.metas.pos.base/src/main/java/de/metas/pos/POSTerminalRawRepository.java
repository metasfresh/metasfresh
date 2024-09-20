package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.pricing.PriceListId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_POS;
import org.springframework.stereotype.Repository;

@Repository
public class POSTerminalRawRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public POSTerminalRaw getPOSTerminal()
	{
		return fromRecord(retrieveRecord());
	}

	private I_C_POS retrieveRecord()
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_POS.class);
	}

	private static POSTerminalRaw fromRecord(@NonNull final I_C_POS record)
	{
		return POSTerminalRaw.builder()
				.id(POSTerminalId.ofRepoId(record.getC_POS_ID()))
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.shipFromWarehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.walkInCustomerId(BPartnerId.ofRepoId(record.getC_BPartnerCashTrx_ID()))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(record.getC_DocTypeOrder_ID()))
				.cashbookId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.build();
	}
}
