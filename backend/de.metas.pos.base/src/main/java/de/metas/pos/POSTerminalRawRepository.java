package de.metas.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.pricing.PriceListId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_POS;
import org.springframework.stereotype.Repository;

@Repository
public class POSTerminalRawRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public POSTerminalId retrievePOSTerminalId()
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnlyOptional(POSTerminalId::ofRepoIdOrNull)
				.orElseThrow(() -> new AdempiereException("No POS Terminal found"));
	}

	public POSTerminalRaw retrievePOSTerminalById(@NonNull POSTerminalId posTerminalId)
	{
		return fromRecord(retrieveRecordById(posTerminalId));
	}

	private I_C_POS retrieveRecordById(@NonNull POSTerminalId posTerminalId)
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS.COLUMNNAME_C_POS_ID, posTerminalId)
				.create()
				.firstOnly(I_C_POS.class);
	}

	private static POSTerminalRaw fromRecord(@NonNull final I_C_POS record)
	{
		final POSTerminalId posTerminalId = POSTerminalId.ofRepoId(record.getC_POS_ID());

		return POSTerminalRaw.builder()
				.id(posTerminalId)
				.priceListId(PriceListId.ofRepoId(record.getM_PriceList_ID()))
				.shipFromWarehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.walkInCustomerId(BPartnerId.ofRepoId(record.getC_BPartnerCashTrx_ID()))
				.salesOrderDocTypeId(DocTypeId.ofRepoId(record.getC_DocTypeOrder_ID()))
				.cashbookId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.cashJournalId(POSCashJournalId.ofRepoIdOrNull(record.getC_POS_Journal_ID()))
				.cashLastBalance(record.getCashLastBalance())
				.build();
	}

	public void save(@NonNull final POSTerminalRaw raw)
	{
		final I_C_POS record = InterfaceWrapperHelper.load(raw.getId(), I_C_POS.class);
		updateRecord(record, raw);
		InterfaceWrapperHelper.save(record);
	}

	private static void updateRecord(final I_C_POS record, final @NonNull POSTerminalRaw from)
	{
		record.setC_POS_Journal_ID(POSCashJournalId.toRepoId(from.getCashJournalId()));
		record.setCashLastBalance(from.getCashLastBalance());
	}
}
