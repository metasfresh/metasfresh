package de.metas.pos.payment_gateway.sumup;

import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.POSPaymentProcessingStatus;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
class SumUpUtils
{
	@NonNull
	public static POSPaymentProcessingStatus toResponseStatus(final @NonNull SumUpTransactionStatus status)
	{
		if (SumUpTransactionStatus.SUCCESSFUL.equals(status))
		{
			return POSPaymentProcessingStatus.SUCCESSFUL;
		}
		else if (SumUpTransactionStatus.CANCELLED.equals(status))
		{
			return POSPaymentProcessingStatus.CANCELLED;
		}
		else if (SumUpTransactionStatus.FAILED.equals(status))
		{
			return POSPaymentProcessingStatus.FAILED;
		}
		else if (SumUpTransactionStatus.PENDING.equals(status))
		{
			return POSPaymentProcessingStatus.PENDING;
		}
		else
		{
			throw new AdempiereException("Unknown SumUp status: " + status);
		}
	}
}
