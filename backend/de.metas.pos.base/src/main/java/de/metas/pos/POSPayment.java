package de.metas.pos;

import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class POSPayment
{
	@NonNull String externalId;
	@NonNull POSPaymentMethod paymentMethod;
	@NonNull BigDecimal amount;

	@Nullable @With PaymentId paymentReceiptId;

	public void assertNoPaymentReceipt()
	{
		if (paymentReceiptId != null)
		{
			throw new AdempiereException("Expected POS Payment to not have a payment receipt")
					.setParameter("posPayment", this);
		}
	}
}
