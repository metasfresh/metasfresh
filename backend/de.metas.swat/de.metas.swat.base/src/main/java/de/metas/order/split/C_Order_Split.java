package de.metas.order.split;

import de.metas.order.OrderId;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

/**
 * AD_Process Java implementation for {@code C_Order_Split}.
 * <p>
 * Wired by the AD_Process row with {@code Value='C_Order_Split'} via the {@code Classname} column.
 * Invoked from the sales-order window action menu (bound via {@code AD_Table_Process}).
 * <p>
 * Reads the current {@code C_Order} record, delegates to {@link OrderSplitCommand#split(OrderSplitRequest)},
 * and reports the new continuation-order ID + copied line count in the process result summary.
 */
public class C_Order_Split extends JavaProcess
{
	private final OrderSplitCommand splitCommand =
			SpringContextHolder.instance.getBean(OrderSplitCommand.class);

	@Override
	@NonNull
	protected String doIt()
	{
		final I_C_Order order = getRecord(I_C_Order.class);
		final OrderSplitResult result = splitCommand.split(OrderSplitRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.build());

		return "Split complete — new C_Order_ID = " + result.getNewOrderId().getRepoId()
				+ ", copied " + result.getCopiedLineCount() + " line(s).";
	}
}
