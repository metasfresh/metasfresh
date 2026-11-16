package de.metas.pos.rest_api.json;

import de.metas.pos.POSPaymentProcessingStatus;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public enum JsonPOSPaymentStatus
{
	SUCCESSFUL,
	FAILED,
	PENDING,
	NEW,
	UNKNOWN,
	;

	public static JsonPOSPaymentStatus of(@NonNull POSPaymentProcessingStatus paymentProcessingStatus)
	{
		switch (paymentProcessingStatus)
		{
			case SUCCESSFUL:
				return SUCCESSFUL;
			case CANCELLED:
			case FAILED:
				return FAILED;
			case PENDING:
				return PENDING;
			case NEW:
				return NEW;
			case UNKNOWN:
				return UNKNOWN;
			default:
				throw new AdempiereException("Unknown paymentProcessingStatus: " + paymentProcessingStatus);
		}
	}

	/**
	 * @return the processing status this one stands for; {@link #FAILED} stands for {@link POSPaymentProcessingStatus#FAILED}
	 * only, so a cancelled payment never matches it
	 */
	@NonNull
	public POSPaymentProcessingStatus toPOSPaymentProcessingStatus()
	{
		switch (this)
		{
			case SUCCESSFUL:
				return POSPaymentProcessingStatus.SUCCESSFUL;
			case FAILED:
				return POSPaymentProcessingStatus.FAILED;
			case PENDING:
				return POSPaymentProcessingStatus.PENDING;
			case NEW:
				return POSPaymentProcessingStatus.NEW;
			case UNKNOWN:
				return POSPaymentProcessingStatus.UNKNOWN;
			default:
				throw new AdempiereException("Unknown status: " + this);
		}
	}
}
