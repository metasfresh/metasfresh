package de.metas.pos.payment_gateway.sumup;

import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@UtilityClass
class SumUpUtils
{
	public static POSPaymentProcessResponse extractProcessResponse(final SumUpTransaction sumUpTrx)
	{
		return POSPaymentProcessResponse.builder()
				.status(SumUpUtils.toResponseStatus(sumUpTrx.getStatus(), sumUpTrx.isRefunded()))
				.config(extractPaymentProcessorConfig(sumUpTrx))
				.transactionId(sumUpTrx.getExternalId().getAsString())
				.summary(sumUpTrx.getCard() != null ? sumUpTrx.getCard().getAsString() : null)
				.build();
	}

	public static POSTerminalPaymentProcessorConfig extractPaymentProcessorConfig(final SumUpTransaction sumUpTrx)
	{
		return POSTerminalPaymentProcessorConfig.builder()
				.type(POSPaymentProcessorType.SumUp)
				.sumUpConfigId(sumUpTrx.getConfigId())
				.build();
	}

	@NonNull
	public static POSPaymentProcessingStatus toResponseStatus(final @NonNull SumUpTransactionStatus status, final boolean isRefunded)
	{
		if (SumUpTransactionStatus.SUCCESSFUL.equals(status))
		{
			return isRefunded
					? POSPaymentProcessingStatus.DELETED
					: POSPaymentProcessingStatus.SUCCESSFUL;
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

	public static SumUpPOSRef toPOSRef(@NonNull final POSOrderAndPaymentId posOrderAndPaymentId)
	{
		return SumUpPOSRef.builder()
				.posOrderId(posOrderAndPaymentId.getOrderId().getRepoId())
				.posPaymentId(posOrderAndPaymentId.getPaymentId().getRepoId())
				.build();
	}

	@Nullable
	public static POSOrderAndPaymentId toPOSOrderAndPaymentId(@NonNull final SumUpPOSRef posRef)
	{
		return POSOrderAndPaymentId.ofRepoIdsOrNull(posRef.getPosOrderId(), posRef.getPosPaymentId());
	}
}
