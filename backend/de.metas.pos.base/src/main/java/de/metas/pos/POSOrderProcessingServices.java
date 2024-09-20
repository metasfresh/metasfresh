package de.metas.pos;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class POSOrderProcessingServices
{
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public void createPayments(@NonNull POSOrder posOrder)
	{
		posOrder.updateAllPayments(posPayment -> {
			if (posPayment.getPaymentReceiptId() != null)
			{
				return posPayment;
			}
			else
			{
				final PaymentId paymentReceiptId = createPaymentReceipt(posPayment, posOrder);
				return posPayment.withPaymentReceiptId(paymentReceiptId);
			}
		});
	}

	public PaymentId createPaymentReceipt(@NonNull final POSPayment posPayment, @NonNull POSOrder posOrder)
	{
		posPayment.assertNoPaymentReceipt();

		final I_C_Payment paymentReceipt = paymentBL.newInboundReceiptBuilder()
				.adOrgId(posOrder.getOrgId())
				.orgBankAccountId(posOrder.getCashbookId())
				.bpartnerId(posOrder.getShipToCustomerId())
				.payAmt(posPayment.getAmount())
				.currencyId(posOrder.getCurrencyId())
				.tenderType(posPayment.getPaymentMethod().getTenderType())
				.dateTrx(posOrder.getDate())
				// TODO: isAutoAllocateAvailableAmt(true) somehow set posOrderId so we know to allocate only to invoices for that POSOrder
				.createAndProcess();

		// TODO: add the payment to bank statement

		return PaymentId.ofRepoId(paymentReceipt.getC_Payment_ID());
	}

	public void scheduleCreateSalesOrderInvoiceAndShipment(@NonNull final POSOrderId posOrderId)
	{
		workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), C_POSOrder_CreateInvoiceAndShipment.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.addElement(TableRecordReference.of(I_C_POS_Order.Table_Name, posOrderId))
				.buildAndEnqueue();
	}
}
