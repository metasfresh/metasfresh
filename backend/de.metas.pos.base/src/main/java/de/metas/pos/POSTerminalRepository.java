package de.metas.pos;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_POS;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Repository Tables: C_POS
 * Repository Cluster: POSTerminalRepository, POSTerminalService
 */
@Repository
public class POSTerminalRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Locks the terminal's {@code C_POS} row for the rest of the caller's transaction, serializing two concurrent
	 * callers against the SAME terminal (e.g. two in-flight requests carrying the same idempotency key) — the
	 * second blocks here until the first commits, by which point its result already exists for the second to find.
	 */
	@NonNull
	public I_C_POS lockForUpdate(@NonNull final POSTerminalId posTerminalId)
	{
		return queryBL.createQueryBuilder(I_C_POS.class)
				.addEqualsFilter(I_C_POS.COLUMNNAME_C_POS_ID, posTerminalId)
				.create()
				.setForUpdate(ForUpdate.FOR_UPDATE)
				.firstOnlyNotNull(I_C_POS.class);
	}

	@NonNull
	public POSTerminalId createPOSTerminal(@NonNull final POSTerminalCreateRequest request)
	{
		final I_C_POS record = InterfaceWrapperHelper.newInstance(I_C_POS.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setName(request.getName());
		record.setIsActive(true);
		record.setIsModifyPrice(false);
		record.setCashLastBalance(BigDecimal.ZERO);
		record.setC_BPartnerCashTrx_ID(request.getWalkInCustomerId().getRepoId());
		record.setC_BP_BankAccount_ID(request.getCashbookId().getRepoId());
		record.setC_DocTypeOrder_ID(request.getSalesOrderDocTypeId().getRepoId());
		record.setM_PriceList_ID(request.getPriceListId().getRepoId());
		record.setM_Warehouse_ID(request.getShipFromWarehouseId().getRepoId());
		InterfaceWrapperHelper.saveRecord(record);

		return POSTerminalId.ofRepoId(record.getC_POS_ID());
	}
}
