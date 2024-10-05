package de.metas.pos.payment_gateway.sumup;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.SumUp;
import de.metas.payment.sumup.SumUpCardReaderCheckoutRequest;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessor;
import de.metas.pos.payment_gateway.POSPaymentProcessorType;
import de.metas.pos.payment_gateway.POSRefundRequest;
import de.metas.pos.payment_gateway.POSRefundResponse;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
class SumUpPaymentProcessor implements POSPaymentProcessor
{
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final WebuiURLs webuiURLs = WebuiURLs.newInstance();
	@NonNull private final SumUpService sumUpService;

	@Override
	public POSPaymentProcessorType getType() {return POSPaymentProcessorType.SumUp;}

	@Override
	public POSPaymentProcessResponse process(@NonNull final POSPaymentProcessRequest request)
	{
		try
		{
			final SumUpConfig sumUpConfig = getSumUpConfig(request.getPaymentProcessorConfig());

			final SumUpTransaction sumUpTrx = sumUpService.cardReaderCheckout(
					SumUpCardReaderCheckoutRequest.builder()
							.configId(sumUpConfig.getId())
							.cardReaderId(sumUpConfig.getDefaultCardReaderExternalIdNotNull())
							.amount(request.getAmount())
							.callbackUrl(getCallbackUrl())
							.clientAndOrgId(request.getClientAndOrgId())
							.posRef(SumUpUtils.toPOSRef(request.getPosOrderAndPaymentId()))
							.build()
			);

			return POSPaymentProcessResponse.builder()
					.status(SumUpUtils.toResponseStatus(sumUpTrx.getStatus(), sumUpTrx.isRefunded()))
					.build();
		}
		catch (final Exception ex)
		{
			return toProcessErrorResponse(ex);
		}
	}

	private SumUpConfig getSumUpConfig(@NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig)
	{
		Check.assumeEquals(paymentProcessorConfig.getType(), POSPaymentProcessorType.SumUp, "payment processor type is SumUp: {}", paymentProcessorConfig);
		final SumUpConfigId sumUpConfigId = Check.assumeNotNull(paymentProcessorConfig.getSumUpConfigId(), "sumUpConfigId is set for {}", paymentProcessorConfig);

		return sumUpService.getConfig(sumUpConfigId);
	}

	private POSPaymentProcessResponse toProcessErrorResponse(@NonNull final Exception ex)
	{
		final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
		final AdIssueId errorId = errorManager.createIssue(metasfreshException);
		return POSPaymentProcessResponse.error(AdempiereException.extractMessage(metasfreshException), errorId);
	}

	@Nullable
	private String getCallbackUrl()
	{
		final String appApiUrl = webuiURLs.getAppApiUrl();
		if (appApiUrl == null)
		{
			return null;
		}

		return appApiUrl + SumUp.ENDPOINT_PaymentCheckoutCallback;
	}

	@Override
	public POSRefundResponse refund(@NonNull final POSRefundRequest request)
	{
		final SumUpPOSRef posRef = SumUpUtils.toPOSRef(request.getPosOrderAndPaymentId());
		final SumUpTransaction trx = sumUpService.refundTransaction(posRef);

		return POSRefundResponse.builder()
				.status(SumUpUtils.toResponseStatus(trx.getStatus(), trx.isRefunded()))
				.build();
	}

}
