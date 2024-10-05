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
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

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

		final AtomicBoolean isAnyPaymentPending = new AtomicBoolean(false);
		posOrder.updateAllPayments(posPaymentToProcess -> {
			// Don't go forward if we already have a pending payment
			// We are processing payments one by one to cashier can take care step by step
			if (isAnyPaymentPending.get())
			{
				return posPaymentToProcess;
			}

			POSPayment posPayment = posPaymentToProcess.getPaymentProcessingStatus().isNew()
					? processPOSPayment(posPaymentToProcess, posOrder, posTerminal.getPaymentProcessorConfigNotNull())
					: posPaymentToProcess;

			posPayment = generatePaymentReceiptIfNeeded(posPayment, posOrder);

			if (posPayment.getPaymentProcessingStatus().isPending())
			{
				isAnyPaymentPending.set(true);
			}

			return posPayment;
		});

		posCashJournalService.changeJournalById(cashJournalId, journal -> journal.addPayments(posOrder));
	}

	public POSPayment processPOSPayment(@NonNull final POSPayment posPaymentToProcess, @NonNull POSOrder posOrder, @NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig)
	{
		posPaymentToProcess.getPaymentProcessingStatus().assertAllowCheckout();

		//
		// Process payment 
		POSPayment posPayment = posPaymentToProcess;
		switch (posPayment.getPaymentMethod())
		{
			case CASH:
			{
				posPayment = processPOSPayment_CASH(posPayment);
				break;
			}
			case CARD:
			{
				posPayment = processPOSPayment_CARD(posPayment, posOrder, paymentProcessorConfig);
				break;
			}
			default:
			{
				throw new AdempiereException("Unexpected payment method: " + posPayment.getPaymentMethod());
			}
		}

		posPayment = generatePaymentReceiptIfNeeded(posPayment, posOrder);

		return posPayment;
	}

	private POSPayment generatePaymentReceiptIfNeeded(POSPayment posPayment, final @NonNull POSOrder posOrder)
	{
		if (posPayment.getPaymentProcessingStatus().isSuccessful() && posPayment.getPaymentReceiptId() == null)
		{
			final PaymentId paymentReceiptId = createPaymentReceipt(posPayment, posOrder);
			posPayment = posPayment.withPaymentReceiptId(paymentReceiptId);
		}
		return posPayment;
	}

	private POSPayment processPOSPayment_CASH(@NonNull final POSPayment posPayment)
	{
		posPayment.getPaymentMethod().assertCash();

		return posPayment.withPaymentProcessingStatus(POSPaymentProcessingStatus.SUCCESSFUL);
	}

	private POSPayment processPOSPayment_CARD(
			@NonNull final POSPayment posPayment,
			@NonNull final POSOrder posOrder,
			@NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig)
	{
		posPayment.getPaymentMethod().assertCard();

		final POSPaymentProcessResponse processResponse = posPaymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
				.paymentProcessorConfig(paymentProcessorConfig)
				.clientAndOrgId(posOrder.getClientAndOrgId())
				.posOrderId(posOrder.getLocalIdNotNull())
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

	public void scheduleCreateSalesOrderInvoiceAndShipment(@NonNull final POSOrderId posOrderId, @NonNull final UserId cashierId)
	{
		final UserId adUserId = Env.getLoggedUserIdIfExists().orElse(cashierId);
		try (IAutoCloseable ignored = Env.temporaryChangeLoggedUserId(adUserId))
		{
			workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), C_POSOrder_CreateInvoiceAndShipment.class)
					.newWorkPackage()
					.addElement(TableRecordReference.of(I_C_POS_Order.Table_Name, posOrderId))
					.bindToThreadInheritedTrx()
					.buildAndEnqueue();
		}
	}
}
