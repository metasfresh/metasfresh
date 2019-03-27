package de.metas.vertical.creditscore.creditpass.service;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.base.spi.repository.TransactionResultId;
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
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	public List<TransactionResultId> getAndSaveCreditScore(@NonNull final String paymentRule,
			@NonNull final BPartnerId bPartnerId) throws Exception
	{
		final CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		final CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		final List<TransactionResultId> transactionResultIds = new ArrayList<>();
		if (StringUtils.isEmpty(paymentRule))
		{
			for (CreditPassConfigPaymentRule configPaymentRule : creditScoreClient.getCreditPassConfig().getCreditPassConfigPaymentRuleList())
			{
				final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, configPaymentRule.getPaymentRule());
				TransactionResultId id = transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore), bPartnerId);
				transactionResultIds.add(id);
			}
		}
		else
		{
			final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
			TransactionResultId id = transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore), bPartnerId);
			transactionResultIds.add(id);
		}
		return transactionResultIds;
	}

	public TransactionResult getAndSaveCreditScore(@NonNull final String paymentRule,
			@NonNull final OrderId orderId, @NonNull final BPartnerId bPartnerId) throws Exception
	{
		final CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		final CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
		return transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore), bPartnerId, orderId);
	}

	public boolean hasConfigForPartnerId(
			@NonNull final BPartnerId bPartnerId)
	{

		boolean hasConfig = false;
		final CreditPassConfig config = creditPassConfigRepository.getConfigByBPartnerId(bPartnerId);

		if (config != null)
		{
			hasConfig = true;
		}
		return hasConfig;
	}

	private CreditScore convertResult(@NonNull final CreditPassClient client, @NonNull final CreditScore creditScore)
	{
		if (creditScore.getResultCode() == CreditPassConstants.MANUAL_RESPONSE_CODE)
		{
			final CreditScore convertedCreditScore = CreditScore.builder()
					.requestLogData(creditScore.getRequestLogData())
					.resultCode(client.getCreditPassConfig().getDefaultResult().getResultCode())
					.resultText(CreditPassConstants.MANUAL_RESPONSE_CONVERSION_TEXT)
					.paymentRule(creditScore.getPaymentRule())
					.build();
			final String message = Services.get(IMsgBL.class).getMsg(Env.getCtx(), CreditPassConstants.CREDITPASS_NOTIFICATION_MESSAGE_KEY);
			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.recipientUserId(client.getCreditPassConfig().getNotificationUserId())
					.contentPlain(message)
					.build();
			Services.get(INotificationBL.class).sendAfterCommit(userNotificationRequest);
			return convertedCreditScore;
		}
		return creditScore;
	}
}
