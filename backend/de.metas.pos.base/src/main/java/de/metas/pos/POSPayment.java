package de.metas.pos;

import de.metas.i18n.BooleanWithReason;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Data
@Builder(toBuilder = true)
public class POSPayment
{
	@NonNull private final POSPaymentExternalId externalId;
	@Nullable private POSPaymentId localId;

	@NonNull private final POSPaymentMethod paymentMethod;
	@NonNull private final Money amount;

	@NonNull private final POSPaymentProcessingStatus paymentProcessingStatus;
	@Nullable private final PaymentId paymentReceiptId;

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

	public POSPayment changingStatusFromRemote(final POSPaymentProcessingStatus newStatus)
	{
		if (paymentProcessingStatus == newStatus)
		{
			return this;
		}

		// NOTE: when changing status from remote we cannot validate if the status transition is OK
		// we have to accept what we have on remote.

		return toBuilder().paymentProcessingStatus(newStatus).build();
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
}
