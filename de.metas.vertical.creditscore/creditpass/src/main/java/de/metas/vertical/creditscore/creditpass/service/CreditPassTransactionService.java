package de.metas.vertical.creditscore.creditpass.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.base.spi.repository.TransactionResultId;
import de.metas.vertical.creditscore.base.spi.service.TransactionResultService;
import de.metas.vertical.creditscore.creditpass.CreditPassClient;
import de.metas.vertical.creditscore.creditpass.CreditPassClientFactory;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPRFallback;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.StringUtils;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public List<TransactionResult> getAndSaveCreditScore(@NonNull final String paymentRule,
			@NonNull final BPartnerId bPartnerId) throws Exception
	{
		final CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		final CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		final ImmutableList.Builder<TransactionResult> transactionResults = ImmutableList.builder();
		final CreditPassConfig config = creditScoreClient.getCreditPassConfig();
		if (StringUtils.isEmpty(paymentRule))
		{
			for (CreditPassConfigPaymentRule configPaymentRule : creditScoreClient.getCreditPassConfig().getCreditPassConfigPaymentRuleList())
			{
				final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, configPaymentRule.getPaymentRule());
				transactionResults.add(handleResult(null, bPartnerId, config, creditScore));
			}
		}
		else
		{
			final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
			transactionResults.add(handleResult(null, bPartnerId, config, creditScore));
		}
		return transactionResults.build();
	}

	public List<TransactionResult> getAndSaveCreditScore(@NonNull final String paymentRule,
			@NonNull final OrderId orderId, @NonNull final BPartnerId bPartnerId) throws Exception
	{
		final CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		final CreditPassConfig config = creditScoreClient.getCreditPassConfig();
		final CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		final CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
		final TransactionResult result = handleResult(orderId, bPartnerId, config, creditScore);
		final List<TransactionResult> transactionResults = new ArrayList<>();
		transactionResults.add(result);
		if (result.getResultCodeEffective() != ResultCode.P)
		{
			//perform fallback requests
			Optional<CreditPassConfigPaymentRule> creditPassConfigPaymentRule = config.getCreditPassConfigPaymentRuleList()
					.stream()
					.filter(configPR -> StringUtils.equals(configPR.getPaymentRule(), paymentRule))
					.findAny();
			if (creditPassConfigPaymentRule.isPresent())
			{
				for (CreditPassConfigPRFallback configPRFallback : creditPassConfigPaymentRule.get().getCreditPassConfigPRFallbacks())
				{
					boolean hasPaymentRuleConfig = config.getCreditPassConfigPaymentRuleList().stream()
							.anyMatch(configPr -> StringUtils.equals(configPr.getPaymentRule(), configPRFallback.getFallbackPaymentRule()));
					if (hasPaymentRuleConfig)
					{
						final CreditScore fallbackCreditScore = creditScoreClient.getCreditScore(creditPassTransactionData, configPRFallback.getFallbackPaymentRule());
						final TransactionResult fallbackResult = handleResult(orderId, bPartnerId, config, fallbackCreditScore);
						transactionResults.add(fallbackResult);
					}
				}
			}
		}
		return transactionResults;
	}

	private TransactionResult handleResult(final OrderId orderId, @NonNull final BPartnerId bPartnerId,
			@NonNull final CreditPassConfig config, @NonNull final CreditScore creditScore)
	{
		final CreditScore finalCreditScore = convertResult(config, creditScore);
		final TransactionResult result = transactionResultService.createAndSaveResult(finalCreditScore, bPartnerId, orderId);
		if (finalCreditScore.isConverted())
		{
			sendNotification(config, result.getTransactionResultId());
		}
		return result;
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

	private CreditScore convertResult(@NonNull final CreditPassConfig config, @NonNull final CreditScore creditScore)
	{
		if (creditScore.getResultCode() == ResultCode.M)
		{
			return CreditScore.builder()
					.requestLogData(creditScore.getRequestLogData())
					.resultCode(creditScore.getResultCode())
					.resultCodeOverride(config.getResultCode())
					.resultText(creditScore.getResultText())
					.paymentRule(creditScore.getPaymentRule())
					.requestPrice(creditScore.getRequestPrice())
					.currency(creditScore.getCurrency())
					.converted(true)
					.build();
		}
		return creditScore;
	}

	private void sendNotification(@NonNull final CreditPassConfig config, @NonNull final TransactionResultId transactionResultId)
	{
		final String message = Services.get(IMsgBL.class).getMsg(Env.getCtx(), CreditPassConstants.CREDITPASS_NOTIFICATION_MESSAGE_KEY);
		final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
				.recipientUserId(config.getNotificationUserId())
				.contentPlain(message)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(TableRecordReference.of(I_CS_Transaction_Result.Table_Name, transactionResultId.getRepoId())))
				.build();
		Services.get(INotificationBL.class).sendAfterCommit(userNotificationRequest);
	}
}
