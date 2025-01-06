package de.metas.pos;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.money.MoneyService;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.pos.payment_gateway.POSRefundRequest;
import de.metas.pos.repository.model.I_C_POS_Order;
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

			final POSPayment posPayment = posPaymentToProcess.isNew()
					? processPOSPayment(posPaymentToProcess, posOrder)
					: posPaymentToProcess;

			if (posPayment.isPending())
			{
				isAnyPaymentPending.set(true);
			}

			return posPayment;
		});

		posCashJournalService.changeJournalById(cashJournalId, journal -> journal.addPayments(posOrder));
	}

	public POSPayment processPOSPayment(@NonNull final POSPayment posPaymentToProcess, @NonNull final POSOrder posOrder)
	{
		posPaymentToProcess.assertAllowCheckout();

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
				posPayment = processPOSPayment_CARD(posPayment, posOrder);
				break;
			}
			default:
			{
				throw new AdempiereException("Unexpected payment method: " + posPayment.getPaymentMethod());
			}
		}

		return posPayment;
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

	private POSPayment processPOSPayment_CARD(
			@NonNull final POSPayment posPayment,
			@NonNull final POSOrder posOrder)
	{
		posPayment.getPaymentMethod().assertCard();

		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId());
		final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminal.getPaymentProcessorConfigNotNull();

		final POSPaymentProcessResponse processResponse = posPaymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
				.paymentProcessorConfig(paymentProcessorConfig)
				.clientAndOrgId(posOrder.getClientAndOrgId())
				.posOrderAndPaymentId(POSOrderAndPaymentId.of(posOrder.getLocalIdNotNull(), posPayment.getLocalIdNotNull()))
				.amount(posPayment.getAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
				.build());

		return posPayment.changingStatusFromRemote(processResponse);
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
		posPayment = posPayment.changingStatusFromRemote(refundResponse);

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
