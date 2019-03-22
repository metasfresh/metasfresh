package de.metas.vertical.creditscore.creditpass.service;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.impl.ADMessageDAO;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.service.TransactionResultService;
import de.metas.vertical.creditscore.creditpass.CreditPassClient;
import de.metas.vertical.creditscore.creditpass.CreditPassClientFactory;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class CreditPassTransactionService
{
	private final CreditPassTransactionDataFactory creditPassTransactionDataService;

	private final TransactionResultService transactionResultService;

	private final CreditPassClientFactory clientFactory;

	private final CreditPassConfigRepository creditPassConfigRepository;

	public CreditPassTransactionService(@NonNull final CreditPassTransactionDataFactory creditPassTransactionDataService,
			@NonNull final TransactionResultService transactionResultService,
			@NonNull final CreditPassClientFactory clientFactory,
			@NonNull final CreditPassConfigRepository creditPassConfigRepository)
	{
		this.creditPassTransactionDataService = creditPassTransactionDataService;
		this.transactionResultService = transactionResultService;
		this.clientFactory = clientFactory;
		this.creditPassConfigRepository = creditPassConfigRepository;
	}

	public void getAndSaveCreditScore(@NonNull final String paymentRule,
			@NonNull final BPartnerId bPartnerId, @NonNull final Properties ctx) throws Exception
	{
		// TODO check if creation can be avoided sometimes
		CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		if (StringUtils.isEmpty(paymentRule))
		{
			for (CreditPassConfigPaymentRule configPaymentRule : creditScoreClient.getCreditPassConfig().getCreditPassConfigPaymentRuleList())
			{
				CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, configPaymentRule.getPaymentRule());
				transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore, ctx), bPartnerId);
			}
		}
		else
		{
			CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
			transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore, ctx), bPartnerId);
		}
	}

	public boolean hasConfigForPartnerId(
			@NonNull final BPartnerId bPartnerId)
	{

		boolean hasConfig = false;
		CreditPassConfig config = creditPassConfigRepository.getByBPartnerId(bPartnerId);

		if (config != null)
		{
			hasConfig = true;
		}
		return hasConfig;
	}

	public void shouldPerformRequestForSalesOrder(
			@NonNull final BPartnerId bPartnerId)
	{
		CreditPassConfig config =  creditPassConfigRepository.getByBPartnerId(bPartnerId);
		config.getRetryDays();

	}


	private CreditScore convertResult(@NonNull final CreditPassClient client, @NonNull final CreditScore creditScore,
			@NonNull final Properties ctx)
	{
		if (creditScore.getResultCode() == CreditPassConstants.MANUAL_RESPONSE_CODE)
		{
			CreditScore convertedCreditScore = CreditScore.builder()
					.requestLogData(creditScore.getRequestLogData())
					.resultCode(client.getCreditPassConfig().getDefaultResult().getResultCode())
					.resultText(CreditPassConstants.MANUAL_RESPONSE_CONVERSION_TEXT)
					.build();
			String message = Services.get(ADMessageDAO.class).retrieveByValue(ctx, CreditPassConstants.CREDITPASS_NOTIFICATION_MESSAGE_KEY).getMsgText();
			UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.recipientUserId(client.getCreditPassConfig().getNotificationUserId())
					.contentADMessage(message)
					.build();
			Services.get(INotificationBL.class).sendAfterCommit(userNotificationRequest);
			return convertedCreditScore;
		}
		return creditScore;
	}
}
