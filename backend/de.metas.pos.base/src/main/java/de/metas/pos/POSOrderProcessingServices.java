package de.metas.pos;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.error.IErrorManager;
import de.metas.money.MoneyService;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.payment_gateway.POSRefundRequest;
import de.metas.pos.repository.model.I_C_POS_Order;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class POSOrderProcessingServices
{
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
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

			// A card payment's charge is sent only by the till's card checkout, outside any transaction
			// (POSOrdersService.checkoutPayment); never from here, which runs inside the order's transaction.
			// A NEW card payment therefore stays NEW, and the order stays waiting for payment.
			final POSPayment posPayment = posPaymentToProcess.isNew() && posPaymentToProcess.getPaymentMethod().isCash()
					? processPOSPayment(posPaymentToProcess)
					: posPaymentToProcess;

			if (posPayment.isPending())
			{
				isAnyPaymentPending.set(true);
			}

			return posPayment;
		});

		posCashJournalService.changeJournalById(cashJournalId, journal -> journal.addPayments(posOrder));
	}

	/**
	 * Checks out a cash payment. A card payment is checked out by {@code POSOrdersService.checkoutPayment} only, which sends its
	 * charge outside any transaction (see {@link #sendCardPayment}).
	 */
	public POSPayment processPOSPayment(@NonNull final POSPayment posPaymentToProcess)
	{
		posPaymentToProcess.getPaymentMethod().assertCash();
		posPaymentToProcess.assertAllowCheckout();
		return processPOSPayment_CASH(posPaymentToProcess);
	}

	private POSPayment processPOSPayment_CASH(@NonNull final POSPayment posPayment)
	{
		posPayment.getPaymentMethod().assertCash();

		final POSPaymentProcessingStatus paymentProcessingStatus = posPayment.getPaymentProcessingStatus();
		if (paymentProcessingStatus.isNew() || paymentProcessingStatus.isPending())
		{
			if (posPayment.getCashTenderedAmount().signum() > 0)
			{
				return posPayment.changingStatusToSuccessful();
			}
			else
			{
				return posPayment.changingStatusToPending();
			}
		}
		else
		{
			return posPayment;
		}
	}

	/**
	 * Fails unless a card payment of the order can be sent, i.e. its terminal has a payment processor configured. The card
	 * checkout checks this before it saves the payment as pending, so that a missing configuration refuses the checkout.
	 */
	public void assertCardPaymentCanBeSent(@NonNull final POSOrder posOrder)
	{
		posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId()).getPaymentProcessorConfigNotNull();
	}

	/**
	 * Sends the card payment's charge to the terminal's payment processor and returns its answer; changes nothing.
	 * The call can take long (the cashier presents the card), so a card checkout runs it outside any transaction.
	 * <p>
	 * The processors report their own failures, incl. the failed calls, in the answer. An exception thrown here was raised
	 * before any processor sent anything (e.g. no processor registered for the configured type), so it is answered as a
	 * definite failure, recorded as an AD_Issue on the payment.
	 */
	public POSPaymentProcessResponse sendCardPayment(@NonNull final POSPayment posPayment, @NonNull final POSOrder posOrder)
	{
		posPayment.getPaymentMethod().assertCard();

		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId());
		final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminal.getPaymentProcessorConfigNotNull();
		final POSOrderAndPaymentId posOrderAndPaymentId = POSOrderAndPaymentId.of(posOrder.getLocalIdNotNull(), posPayment.getLocalIdNotNull());

		try
		{
			return posPaymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
					.paymentProcessorConfig(paymentProcessorConfig)
					.clientAndOrgId(posOrder.getClientAndOrgId())
					.posOrderAndPaymentId(posOrderAndPaymentId)
					.amount(posPayment.getAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
					.build());
		}
		catch (final Exception ex)
		{
			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex)
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, posOrderAndPaymentId.getPaymentId()));
			return POSPaymentProcessResponse.error(
					paymentProcessorConfig,
					AdempiereException.extractMessage(metasfreshException),
					errorManager.createIssue(metasfreshException));
		}
	}

	public POSPayment refundPOSPayment(@NonNull final POSPayment posPaymentToProcess, @NonNull POSOrderId posOrderId, @NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig)
	{
		posPaymentToProcess.assertAllowRefund();

		POSPayment posPayment = posPaymentToProcess;

		//
		// Reverse payment
		// (usually this field is not populated at this moment)
		// TODO run this async!
		if (posPayment.getPaymentReceiptId() != null)
		{
			paymentBL.reversePaymentById(posPayment.getPaymentReceiptId());
			posPayment = posPayment.withPaymentReceipt(null);
		}

		//
		// Ask payment processor to refund
		final POSPaymentProcessResponse refundResponse = posPaymentProcessorService.refund(
				POSRefundRequest.builder()
						.paymentProcessorConfig(paymentProcessorConfig)
						.posOrderAndPaymentId(POSOrderAndPaymentId.of(posOrderId, posPayment.getLocalIdNotNull()))
						.build()
		);
		posPayment = posPayment.changingStatusFromRemote(refundResponse).getPayment();

		return posPayment;
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
