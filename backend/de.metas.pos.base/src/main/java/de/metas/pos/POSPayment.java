package de.metas.pos;

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

	public boolean isAllowRefund()
	{
		return paymentMethod.isCard() && paymentProcessingStatus.isAllowRefund();
	}

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
		if (paymentProcessingStatus == POSPaymentProcessingStatus.DELETED)
		{
			return this;
		}

		paymentProcessingStatus.assertAllowDelete();
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
