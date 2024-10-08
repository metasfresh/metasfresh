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
			default:
				throw new AdempiereException("Unknown paymentProcessingStatus: " + paymentProcessingStatus);
		}
	}
}
