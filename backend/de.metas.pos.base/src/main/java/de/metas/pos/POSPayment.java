package de.metas.pos;

import de.metas.money.Money;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.With;
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

	@NonNull @With private final POSPaymentProcessingStatus paymentProcessingStatus;
	@Nullable @With private final PaymentId paymentReceiptId;

	public POSPaymentId getLocalIdNotNull() {return Check.assumeNotNull(this.getLocalId(), "Expected POSPayment to be saved: {}", this);}

	public void assertNoPaymentReceipt()
	{
		if (paymentReceiptId != null)
		{
			throw new AdempiereException("Expected POS Payment to not have a payment receipt")
					.setParameter("posPayment", this);
		}
	}
}
