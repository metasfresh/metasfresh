package de.metas.pos;

import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_POS;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class POSTerminalRepository
{
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
