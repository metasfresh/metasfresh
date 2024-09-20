package de.metas.pos;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import static de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment.WP_PARAM_POSOrderExternalId;

@Service
@RequiredArgsConstructor
public class POSOrderProcessingServices
{
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public void createPayments(@NonNull POSOrder posOrder)
	{
		posOrder.getPayments().forEach(posPayment -> createPayment(posPayment, posOrder));
	}

	public void createPayment(@NonNull final POSPayment posPayment, @NonNull POSOrder posOrder)
	{
		paymentBL.newInboundReceiptBuilder()
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

		//return PaymentId.ofRepoId(payment.getC_Payment_ID());
	}

	public void scheduleCreateSalesOrderInvoiceAndShipment(@NonNull final POSOrder posOrder)
	{
		workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), C_POSOrder_CreateInvoiceAndShipment.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.parameter(WP_PARAM_POSOrderExternalId, posOrder.getExternalId().getAsString())
				.buildAndEnqueue();
	}
}
