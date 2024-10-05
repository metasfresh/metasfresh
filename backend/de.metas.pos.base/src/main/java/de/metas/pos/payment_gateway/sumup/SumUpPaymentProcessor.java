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
			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = request.getPaymentProcessorConfig();
			Check.assumeEquals(paymentProcessorConfig.getType(), POSPaymentProcessorType.SumUp, "payment processor type is SumUp: {}", paymentProcessorConfig);
			final SumUpConfigId sumUpConfigId = Check.assumeNotNull(paymentProcessorConfig.getSumUpConfigId(), "sumUpConfigId is set for {}", paymentProcessorConfig);

			final SumUpConfig sumUpConfig = sumUpService.getConfig(sumUpConfigId);

			final SumUpTransaction sumUpTrx = sumUpService.cardReaderCheckout(
					SumUpCardReaderCheckoutRequest.builder()
							.configId(sumUpConfigId)
							.cardReaderId(sumUpConfig.getDefaultCardReaderExternalIdNotNull())
							.amount(request.getAmount())
							.callbackUrl(getCallbackUrl())
							.clientAndOrgId(request.getClientAndOrgId())
							.posRef(extractPOSRef(request))
							.build()
			);

			return POSPaymentProcessResponse.builder().status(SumUpUtils.toResponseStatus(sumUpTrx.getStatus())).build();
		}
		catch (final Exception ex)
		{
			return errorResponse(ex);
		}
	}

	@Nullable
	private static SumUpPOSRef extractPOSRef(final POSPaymentProcessRequest request)
	{
		return SumUpPOSRef.builder()
				.posOrderId(request.getPosOrderId().getRepoId())
				.posPaymentId(request.getPosPaymentId().getRepoId())
				.build();
	}

	private POSPaymentProcessResponse errorResponse(final Exception ex)
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
}
