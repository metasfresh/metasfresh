package de.metas.inoutcandidate.api;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IContextAware;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.quantity.StockQtyAndUOMQty;

public interface IReceiptScheduleAllocBuilder
{
	I_M_ReceiptSchedule_Alloc build();

	I_M_ReceiptSchedule_Alloc buildAndSave();

	ReceiptScheduleAllocBuilder setContext(IContextAware context);

	IReceiptScheduleAllocBuilder setM_ReceiptSchedule(I_M_ReceiptSchedule receiptSchedule);

	IReceiptScheduleAllocBuilder setQtyToAllocate(StockQtyAndUOMQty qtyToAllocate);

	ReceiptScheduleAllocBuilder setQtyWithIssues(StockQtyAndUOMQty qtyWithIssues);

	/**
	 * @param receiptLine can be {@code null} because M_ReceiptSchedule_Alloc are also used to allocate a HU to a receipt schedule even *before* there is any inOutLine.
	 */
	IReceiptScheduleAllocBuilder setM_InOutLine(@Nullable I_M_InOutLine receiptLine);

}
