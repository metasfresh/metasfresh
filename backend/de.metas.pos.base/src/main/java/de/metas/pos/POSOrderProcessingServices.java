package de.metas.pos;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.money.MoneyService;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
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
	@NonNull private final MoneyService moneyService;
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final POSPaymentProcessorService posPaymentProcessorService;

	public void processPOSPayments(@NonNull POSOrder posOrder)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId());
		final POSCashJournalId cashJournalId = posTerminal.getCashJournalIdNotNull();

		posOrder.updateAllPayments(posPayment -> processPOSPayment(posPayment, posOrder, posTerminal));

		posCashJournalService.changeJournalById(cashJournalId, journal -> journal.addPayments(posOrder));
	}

	private POSPayment processPOSPayment(@NonNull final POSPayment posPaymentToProcess, @NonNull POSOrder posOrder, @NonNull POSTerminal posTerminal)
	{
		//
		// Process payment 
		POSPayment posPayment = posPaymentToProcess;
		if (posPayment.getPaymentProcessingStatus().isNewOrCanTryAgain())
		{
			switch (posPayment.getPaymentMethod())
			{
				case CASH:
				{
					posPayment = processPOSPayment_CASH(posPayment);
					break;
				}
				case CARD:
				{
					posPayment = processPOSPayment_CARD(posPayment, posOrder.getLocalIdNotNull(), posTerminal.getPaymentProcessorConfigNotNull());
					break;
				}
				default:
				{
					throw new AdempiereException("Unexpected payment method: " + posPayment.getPaymentMethod());
				}
			}
		}

		//
		// Make sure payment receipt is generated if payment processing was successful
		if (posPayment.getPaymentProcessingStatus().isSuccessful() && posPayment.getPaymentReceiptId() == null)
		{
			final PaymentId paymentReceiptId = createPaymentReceipt(posPayment, posOrder);
			posPayment = posPayment.withPaymentReceiptId(paymentReceiptId);
		}

		return posPayment;
	}

	private POSPayment processPOSPayment_CASH(@NonNull final POSPayment posPayment)
	{
		return posPayment.withPaymentProcessingStatus(POSPaymentProcessingStatus.SUCCESSFUL);
	}

	private POSPayment processPOSPayment_CARD(
			@NonNull final POSPayment posPayment,
			@NonNull final POSOrderId posOrderId,
			@NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig)
	{
		final POSPaymentProcessResponse processResponse = posPaymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
				.paymentProcessorConfig(paymentProcessorConfig)
				.posOrderId(posOrderId)
				.posPaymentId(posPayment.getLocalIdNotNull())
				.amount(posPayment.getAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
				.build());

		return posPayment.withPaymentProcessingStatus(processResponse.getStatus());
	}

	private PaymentId createPaymentReceipt(@NonNull final POSPayment posPayment, @NonNull POSOrder posOrder)
	{
		posPayment.assertNoPaymentReceipt();

		final I_C_Payment paymentReceipt = paymentBL.newInboundReceiptBuilder()
				.adOrgId(posOrder.getOrgId())
				.orgBankAccountId(posOrder.getCashbookId())
				.bpartnerId(posOrder.getShipToCustomerId())
				.payAmt(posPayment.getAmount().toBigDecimal())
				.currencyId(posPayment.getAmount().getCurrencyId())
				.tenderType(posPayment.getPaymentMethod().getTenderType())
				.dateTrx(posOrder.getDate())
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
