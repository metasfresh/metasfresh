package de.metas.order.paymentschedule.referenced_docs.prepayment;

import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPaySchedulePrepaymentService
{
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final ProformaOrderAllocRepository proformaOrderAllocRepository;

	public static OrderPaySchedulePrepaymentService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				OrderPaySchedulePrepaymentService.class,
				() -> new OrderPaySchedulePrepaymentService(
						ProformaOrderAllocRepository.newInstanceForUnitTesting()
				)
		);
	}

	public Prepayment getById(@NonNull final PaymentId prepayPaymentId, @NonNull final OrderId orderId)
	{
		final I_C_Payment record = paymentDAO.getById(prepayPaymentId);
		return fromRecord(record, orderId);
	}

	@NonNull
	public Optional<Prepayment> getByProformaInvoiceId(@NonNull final InvoiceId proformaInvoiceId, @NonNull final OrderId orderId)
	{
		return getByProformaInvoiceId(proformaInvoiceId, orderId, null);
	}

	@NonNull
	public Optional<Prepayment> getByProformaInvoiceId(
			@NonNull final InvoiceId proformaInvoiceId,
			@NonNull final OrderId orderId,
			@Nullable final I_C_Payment completingPaymentRecord)
	{
		if (completingPaymentRecord != null)
		{
			if (!isEligible(completingPaymentRecord, false))
			{
				return Optional.empty();
			}
			if (!InvoiceId.equals(extractProformaInvoiceIdOrNull(completingPaymentRecord), proformaInvoiceId))
			{
				return Optional.empty();
			}

			return Optional.of(fromRecord(completingPaymentRecord, orderId));
		}
		else
		{
			I_C_Payment paymentRecord = paymentDAO.findCompletedOrClosedByProformaInvoiceId(proformaInvoiceId).orElse(null);
			if (!isEligible(paymentRecord, true))
			{
				return Optional.empty();
			}

			return Optional.of(fromRecord(paymentRecord, orderId));
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isEligible(@Nullable final I_C_Payment prepayment, final boolean validateDocStatus)
	{
		if (prepayment == null) {return false;}
		if (!prepayment.isPrepayment() || extractProformaInvoiceIdOrNull(prepayment) == null) {return false;}
		if (prepayment.getReversal_ID() > 0) {return false;}
		
		//noinspection RedundantIfStatement
		if (validateDocStatus && !DocStatus.ofNullableCodeOrUnknown(prepayment.getDocStatus()).isCompletedOrClosed()) {return false;}

		return true;
	}

	@Nullable
	private static InvoiceId extractProformaInvoiceIdOrNull(final @NotNull I_C_Payment prepayment)
	{
		return InvoiceId.ofRepoIdOrNull(prepayment.getProforma_Invoice_ID());
	}

	public Optional<Prepayment> fromRecordIfEligible(@NonNull final I_C_Payment completingPaymentRecord)
	{
		if (!isEligible(completingPaymentRecord, false)) {return Optional.empty();}

		final OrderId orderId = findOrderId(completingPaymentRecord).orElse(null);
		if (orderId == null)
		{
			return Optional.empty();
		}

		return Optional.of(fromRecord(completingPaymentRecord, orderId));
	}

	private Prepayment fromRecord(@NonNull final I_C_Payment record, @NonNull final OrderId orderId)
	{
		final InvoiceId proformaInvoiceId = InvoiceId.ofRepoId(record.getProforma_Invoice_ID());

		return Prepayment.builder()
				.id(PaymentId.ofRepoId(record.getC_Payment_ID()))
				.dateTrx(TimeUtil.asLocalDateNonNull(record.getDateTrx()))
				.dateAcct(TimeUtil.asLocalDateNonNull(record.getDateAcct()))
				.amount(Money.of(record.getPayAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.proformaInvoiceId(proformaInvoiceId)
				.orderId(orderId)
				.build();
	}

	/**
	 * @return paid amount not yet allocated; amount is NOT AP adjusted
	 */
	public Money getAvailableAmount(@NonNull final Prepayment prepayment)
	{
		final BigDecimal availableAmountBD = paymentDAO.getAvailableAmount(prepayment.getId())
				// paymentAvailable() uses C_Payment_v.PayAmt which is negated for AP (IsReceipt='N').
				// Negate for AP to get a positive "remaining capacity" value; AR stays positive.
				.negate();
		return Money.of(availableAmountBD, prepayment.getCurrencyId());
	}

	public Optional<OrderId> findOrderId(@NonNull final I_C_Payment payment)
	{
		final InvoiceId proformaInvoiceId = extractProformaInvoiceIdOrNull(payment);
		if (proformaInvoiceId == null)
		{
			return Optional.empty();
		}

		return proformaOrderAllocRepository.findOrderIdByProformaInvoiceId(proformaInvoiceId);
	}

}
