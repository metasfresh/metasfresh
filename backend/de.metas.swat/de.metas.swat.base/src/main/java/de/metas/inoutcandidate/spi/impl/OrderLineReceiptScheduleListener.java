package de.metas.inoutcandidate.spi.impl;

<<<<<<< HEAD
=======
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.order.OrderLineId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.model.I_C_OrderLine;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.ReceiptScheduleListenerAdapter;
import de.metas.order.IOrderBL;
import de.metas.util.Services;

public class OrderLineReceiptScheduleListener extends ReceiptScheduleListenerAdapter
{
	public static final OrderLineReceiptScheduleListener INSTANCE = new OrderLineReceiptScheduleListener();

<<<<<<< HEAD
=======
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private OrderLineReceiptScheduleListener()
	{
	}

	/**
	 * If a receipt schedule is closed then this method retrieves its <code>orderLine</code> and invokes {@link IOrderBL#closeLine(I_C_OrderLine)} on it.
	 *
	 * @task 08452
	 */
	@Override
	public void onAfterClose(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return; // shall not happen
		}

<<<<<<< HEAD
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.closeLine(orderLine);
=======
		orderBL.closeLine(orderLine);

		invoiceCandBL.closeDeliveryInvoiceCandidatesByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * If a receipt schedule is reopened then this method retrieves its <code>orderLine</code> and invokes {@link IOrderBL#reopenLine(I_C_OrderLine)} on it.
	 * <p>
	 * Note: we open the order line <b>after</b> the reopen,
	 * because this reopening will cause a synchronous updating of the receipt schedule via the {@link IReceiptScheduleProducer} framework
	 * and the <code>receiptSchedule</code> that is to be reopened uses the order line's <code>QtyOrdered</code> value.
	 *
	 * @task <a href="https://github.com/metasfresh/metasfresh/issues/315">issue #315</a>
	 */
	@Override
	public void onAfterReopen(final I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_OrderLine orderLine = receiptSchedule.getC_OrderLine();
		if (orderLine == null)
		{
			return; // shall not happen
		}

<<<<<<< HEAD
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.reopenLine(orderLine);
=======
		orderBL.reopenLine(orderLine);
		invoiceCandBL.openDeliveryInvoiceCandidatesByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
