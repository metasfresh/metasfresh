package de.metas.pos;

import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Data
public class POSPayment
{
	@NonNull private final POSPaymentExternalId externalId;
	@Nullable private POSPaymentId localId;

	@NonNull private final POSPaymentMethod paymentMethod;
	@NonNull private final Money amount;

	@NonNull private final Money cashTenderedAmount;
	@NonNull private final Money cashGiveBackAmount;

	@NonNull private final POSPaymentProcessingStatus paymentProcessingStatus;
	@Nullable private final POSPaymentCardProcessingDetails cardProcessingDetails;
	@Nullable private final PaymentId paymentReceiptId;

	@Builder(toBuilder = true)
	private POSPayment(
			@NonNull final POSPaymentExternalId externalId,
			@Nullable final POSPaymentId localId,
			@NonNull final POSPaymentMethod paymentMethod,
			@NonNull final Money amount,
			@Nullable final Money cashTenderedAmount,
			@NonNull final POSPaymentProcessingStatus paymentProcessingStatus,
			@Nullable final POSPaymentCardProcessingDetails cardProcessingDetails,
			@Nullable final PaymentId paymentReceiptId)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
		this.paymentProcessingStatus = paymentProcessingStatus;
		this.cardProcessingDetails = cardProcessingDetails;
		this.paymentReceiptId = paymentReceiptId;

		final Money zero = amount.toZero();
		if (paymentMethod.isCash())
		{
			this.cashTenderedAmount = CoalesceUtil.coalesceNotNull(cashTenderedAmount, zero);
			this.cashGiveBackAmount = this.cashTenderedAmount.subtract(this.amount);
		}
		else
		{
			this.cashTenderedAmount = zero;
			this.cashGiveBackAmount = zero;
		}
		Money.assertSameCurrency(this.amount, this.cashTenderedAmount, this.cashGiveBackAmount);
	}

	public POSPaymentId getLocalIdNotNull() {return Check.assumeNotNull(this.getLocalId(), "Expected POSPayment to be saved: {}", this);}

	public boolean isNew() {return paymentProcessingStatus.isNew();}

	public boolean isPending() {return paymentProcessingStatus.isPending();}

	public boolean isSuccessful() {return paymentProcessingStatus.isSuccessful();}

	public boolean isDeleted() {return paymentProcessingStatus.isDeleted();}

	public boolean isAllowDeleteFromDB() {return paymentProcessingStatus.isAllowDeleteFromDB();}

	public void assertAllowDelete()
	{
		paymentProcessingStatus.assertAllowDelete();
		if (paymentReceiptId != null)
		{
			throw new AdempiereException("Deleting a POS payment with receipt is not allowed");
		}
	}

	public void assertAllowDeleteFromDB()
	{
		assertAllowDelete();
		paymentProcessingStatus.assertAllowDeleteFromDB();
	}

	public void assertAllowCheckout()
	{
		if (paymentMethod.isCash() && paymentProcessingStatus.isPending())
			return; // FIXME workaround!

		paymentProcessingStatus.assertAllowCheckout();
	}

	public boolean isAllowRefund()
	{
		return paymentMethod.isCard() && paymentProcessingStatus.isAllowRefund();
	}

	public void assertAllowRefund()
	{
		if (!isAllowRefund())
		{
			throw new AdempiereException("Payments with " + paymentMethod + "/" + paymentProcessingStatus + " cannot be refunded");
		}
	}

	public BooleanWithReason checkAllowVoid()
	{
		return paymentProcessingStatus.isPendingOrSuccessful()
				? BooleanWithReason.falseBecause("Pending or Successful payments cannot be voided")
				: BooleanWithReason.TRUE;
	}

	public void assertAllowVoid() {checkAllowVoid().assertTrue();}

	public void assertNoPaymentReceipt()
	{
		if (paymentReceiptId != null)
		{
			throw new AdempiereException("Expected POS Payment to not have a payment receipt")
					.setParameter("posPayment", this);
		}
	}

	public POSPayment changingStatusToPending()
	{
		if (paymentProcessingStatus.isPending())
		{
			return this;
		}
		if (!paymentProcessingStatus.isNew())
		{
			throw new AdempiereException("Cannot change status from " + paymentProcessingStatus + " to Pending");
		}

		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.PENDING).build();
	}

	public POSPayment changingStatusToSuccessful()
	{
		if (paymentProcessingStatus == POSPaymentProcessingStatus.SUCCESSFUL)
		{
			return this;
		}

		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.SUCCESSFUL).build();
	}

	public POSPayment changingStatusToDeleted()
	{
		if (isDeleted())
		{
			return this;
		}

		assertAllowDelete();
		return toBuilder().paymentProcessingStatus(POSPaymentProcessingStatus.DELETED).build();
	}

	public POSPayment changingStatusFromRemote(@NonNull final POSPaymentProcessResponse response)
	{
		paymentMethod.assertCard();

		// NOTE: when changing status from remote we cannot validate if the status transition is OK
		// we have to accept what we have on remote.

		return toBuilder()
				.paymentProcessingStatus(response.getStatus())
				.cardProcessingDetails(POSPaymentCardProcessingDetails.builder()
						.config(response.getConfig())
						.transactionId(response.getTransactionId())
						.summary(response.getSummary())
						.build())
				.build();
	}

	public POSPayment withPaymentReceipt(@Nullable final PaymentId paymentReceiptId)
	{
		if (PaymentId.equals(this.paymentReceiptId, paymentReceiptId))
		{
			return this;
		}

		if (paymentReceiptId != null && !paymentProcessingStatus.isSuccessful())
		{
			throw new AdempiereException("Cannot set a payment receipt if status is not successful");
		}

		if (this.paymentReceiptId != null && paymentReceiptId != null)
		{
			throw new AdempiereException("Changing the payment receipt is not allowed");
		}

		return toBuilder().paymentReceiptId(paymentReceiptId).build();
	}

	public POSPayment withCashTenderedAmount(@NonNull final BigDecimal cashTenderedAmountBD)
	{
		paymentMethod.assertCash();
		Check.assume(cashTenderedAmountBD.signum() > 0, "Cash Tendered Amount must be positive");

		final Money cashTenderedAmountNew = Money.of(cashTenderedAmountBD, this.cashTenderedAmount.getCurrencyId());
		if (Money.equals(this.cashTenderedAmount, cashTenderedAmountNew))
		{
			return this;
		}

		return toBuilder().cashTenderedAmount(cashTenderedAmountNew).build();
	}
}
