package de.metas.payment.sumup;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.money.MoneyService;
import de.metas.payment.sumup.SumUpTransaction.SumUpTransactionBuilder;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.client.json.JsonGetReadersResponse;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonPairReaderRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutResponse;
import de.metas.payment.sumup.repository.BulkUpdateByQueryResult;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SumUpService
{
	@NonNull private static final Logger logger = LogManager.getLogger(SumUpService.class);
	@NonNull private final MoneyService moneyService;
	@NonNull private final SumUpClientFactory clientFactory;
	@NonNull private final SumUpConfigRepository configRepository;
	@NonNull private final SumUpTransactionRepository trxRepository;

	public SumUpService(
			@NonNull final MoneyService moneyService,
			@NonNull final SumUpClientFactory clientFactory,
			@NonNull final SumUpConfigRepository configRepository,
			@NonNull final SumUpTransactionRepository trxRepository)
	{
		this.moneyService = moneyService;
		this.clientFactory = clientFactory;
		this.configRepository = configRepository;
		this.trxRepository = trxRepository;
	}

	public SumUpConfig getConfig(@NonNull SumUpConfigId configId)
	{
		return configRepository.getById(configId);
	}

	public void updateCardReadersFromRemote(@NonNull SumUpConfigId configId)
	{
		configRepository.updateById(configId, config -> {
			final JsonGetReadersResponse remoteCardReaders = clientFactory.newClient(config).getCardReaders();
			return updateTransactionFromRemote(config, remoteCardReaders);
		});
	}

	private static SumUpConfig updateTransactionFromRemote(final SumUpConfig config, final JsonGetReadersResponse remoteResponse)
	{
		final HashMap<SumUpCardReaderExternalId, SumUpCardReader> cardReadersByExternalId = config.getCardReaders()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(SumUpCardReader::getExternalId));

		final ArrayList<SumUpCardReader> newCardReaders = new ArrayList<>();
		for (JsonGetReadersResponse.Item remoteCardReader : remoteResponse.getItems())
		{
			SumUpCardReader cardReader = updateTransactionFromRemote(cardReadersByExternalId.get(remoteCardReader.getId()), remoteCardReader);
			newCardReaders.add(cardReader);
		}

		return config.toBuilder()
				.cardReaders(newCardReaders)
				.build();
	}

	private static SumUpCardReader updateTransactionFromRemote(@Nullable final SumUpCardReader cardReader, @NonNull final JsonGetReadersResponse.Item remote)
	{
		final SumUpCardReader.SumUpCardReaderBuilder builder = cardReader != null
				? cardReader.toBuilder()
				: SumUpCardReader.builder().isActive(true);

		return builder
				.name(remote.getName())
				.externalId(remote.getId())
				.build();
	}

	public void pairCardReader(@NonNull SumUpConfigId configId, @NonNull String name, @NonNull String pairingCode)
	{
		configRepository.updateById(configId, config -> {
			final SumUpClient client = clientFactory.newClient(config);
			client.pairCardReader(JsonPairReaderRequest.builder()
					.name(name)
					.pairing_code(pairingCode)
					.build());

			final JsonGetReadersResponse remoteCardReaders = client.getCardReaders();
			return updateTransactionFromRemote(config, remoteCardReaders);
		});
	}

	public void deleteCardReaders(@NonNull SumUpConfigId configId, @NonNull Set<SumUpCardReaderExternalId> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}

		configRepository.updateById(configId, config -> {
			final SumUpClient client = clientFactory.newClient(config);
			ids.forEach(client::deleteCardReader);

			final JsonGetReadersResponse remoteCardReaders = client.getCardReaders();
			return updateTransactionFromRemote(config, remoteCardReaders);
		});
	}

	public SumUpTransaction cardReaderCheckout(@NonNull SumUpCardReaderCheckoutRequest request)
	{
		final SumUpConfigId configId = request.getConfigId();
		final SumUpConfig config = configRepository.getById(configId);
		final SumUpClient client = clientFactory.newClient(config);
		client.setPosRef(request.getPosRef());

		final Amount amount = request.getAmount();
		final CurrencyPrecision currencyPrecision = moneyService.getStdPrecision(amount.getCurrencyCode());

		final JsonReaderCheckoutResponse checkoutResponse = client.cardReaderCheckout(
				request.getCardReaderId(),
				JsonReaderCheckoutRequest.builder()
						.description(request.getDescription())
						.return_url(getValidCallbackUrlOrNull(request))
						.total_amount(JsonReaderCheckoutRequest.JsonAmount.ofAmount(amount, currencyPrecision))
						.build()
		);

		final SumUpTransaction trx = toSumUpTransaction(checkoutResponse, request, config);
		trxRepository.saveNew(trx);

		return trx;
	}

	private static String getValidCallbackUrlOrNull(@NonNull SumUpCardReaderCheckoutRequest request)
	{
		final String callbackUrl = StringUtils.trimBlankToNull(request.getCallbackUrl());
		if (callbackUrl == null)
		{
			return null;
		}

		if (!callbackUrl.startsWith("https://"))
		{
			logger.warn("SumUp callback URL must be a valid https URL: {}. Ignoring it", callbackUrl);
			return null;
		}

		return callbackUrl;
	}

	private static SumUpTransaction toSumUpTransaction(
			@NonNull final JsonReaderCheckoutResponse checkoutResponse,
			@NonNull SumUpCardReaderCheckoutRequest request,
			@NonNull final SumUpConfig config)
	{
		final SumUpClientTransactionId clientTransactionId = checkoutResponse.getData().getClient_transaction_id();

		return SumUpTransaction.builder()
				.configId(config.getId())
				.externalId(SumUpTransactionExternalId.ofString("UNKNOWN-" + clientTransactionId))
				.clientTransactionId(clientTransactionId)
				.merchantCode(config.getMerchantCode())
				.timestamp(SystemTime.asInstant())
				.status(SumUpTransactionStatus.PENDING)
				.amount(request.getAmount())
				.json(null)
				.clientAndOrgId(request.getClientAndOrgId())
				.posRef(request.getPosRef())
				.build();
	}

	public void updateTransactionFromRemote(@NonNull final SumUpClientTransactionId id)
	{
		trxRepository.updateById(id, this::updateTransactionFromRemote);
	}

	private SumUpTransaction updateTransactionFromRemote(final SumUpTransaction trx)
	{
		final SumUpConfigId configId = trx.getConfigId();
		final SumUpConfig config = configRepository.getById(configId);
		final SumUpClient client = clientFactory.newClient(config);
		client.setPosRef(trx.getPosRef());

		final JsonGetTransactionResponse remoteTrx = client.getTransactionById(trx.getClientTransactionId());

		return updateTransactionFromRemote(trx, remoteTrx);
	}

	private static SumUpTransaction updateTransactionFromRemote(@NonNull SumUpTransaction trx, @NonNull final JsonGetTransactionResponse remote)
	{
		final SumUpTransactionBuilder trxBuilder = trx.toBuilder();
		updateTransactionFromRemote(trxBuilder, remote);
		return trxBuilder.build();
	}

	private static void updateTransactionFromRemote(@NonNull SumUpTransactionBuilder trxBuilder, @NonNull final JsonGetTransactionResponse remote)
	{
		trxBuilder
				.externalId(remote.getId())
				.clientTransactionId(remote.getClient_transaction_id())
				.merchantCode(remote.getMerchant_code())
				.timestamp(Instant.parse(remote.getTimestamp()))
				.status(SumUpTransactionStatus.ofString(remote.getStatus()))
				.amount(Amount.of(remote.getAmount(), remote.getCurrency()))
				.amountRefunded(Amount.of(remote.getAmountRefunded(), remote.getCurrency()))
				.card(extractSumUpTransactionCard(remote.getCard()))
				.json(remote.getJson())
		;
	}

	private static SumUpTransaction.Card extractSumUpTransactionCard(@Nullable JsonGetTransactionResponse.Card from)
	{
		if (from == null)
		{
			return null;
		}

		final String type = StringUtils.trimBlankToNull(from.getType());
		if (type == null)
		{
			return null;
		}

		final String last4Digits = StringUtils.trimBlankToNull(from.getLast_4_digits());
		if (last4Digits == null)
		{
			return null;
		}

		return SumUpTransaction.Card.builder()
				.type(type)
				.last4Digits(last4Digits)
				.build();
	}

	public BulkUpdateByQueryResult bulkUpdatePendingTransactions(boolean isForceSendingChangeEvents)
	{
		return bulkUpdateTransactions(SumUpTransactionQuery.ofStatus(SumUpTransactionStatus.PENDING), isForceSendingChangeEvents);
	}

	public BulkUpdateByQueryResult bulkUpdateTransactions(@NonNull SumUpTransactionQuery query, boolean isForceSendingChangeEvents)
	{
		return trxRepository.bulkUpdateByQuery(query, isForceSendingChangeEvents, this::updateTransactionFromRemote);
	}

	public SumUpTransaction refundTransaction(@NonNull final SumUpPOSRef posRef)
	{
		final SumUpTransactionExternalId id = findTransactionToRefundByPOSRef(posRef);
		return refundTransaction(id);
	}

	private @NonNull SumUpTransactionExternalId findTransactionToRefundByPOSRef(final @NonNull SumUpPOSRef posRef)
	{
		if (posRef.getPosPaymentId() <= 0)
		{
			throw new AdempiereException("posPaymentId not provided");
		}

		final List<SumUpTransaction> trxs = trxRepository.stream(SumUpTransactionQuery.builder()
						.status(SumUpTransactionStatus.SUCCESSFUL)
						.posRef(posRef)
						.build())
				.filter(trx -> !trx.isRefunded())
				.collect(Collectors.toList());
		if (trxs.isEmpty())
		{
			throw new AdempiereException("No successful transactions found");
		}
		else if (trxs.size() != 1)
		{
			throw new AdempiereException("More than successful transaction found");
		}
		else
		{
			return trxs.get(0).getExternalId();
		}
	}

	public SumUpTransaction refundTransaction(@NonNull final SumUpTransactionExternalId id)
	{
		return trxRepository.updateById(id, trx -> {
			final SumUpConfig config = configRepository.getById(trx.getConfigId());
			final SumUpClient client = clientFactory.newClient(config);
			client.setPosRef(trx.getPosRef());

			client.refundTransaction(trx.getExternalId());
			final JsonGetTransactionResponse remoteTrx = client.getTransactionById(trx.getExternalId());
			return updateTransactionFromRemote(trx, remoteTrx);
		});
	}
}
