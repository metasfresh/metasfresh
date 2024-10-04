package de.metas.payment.sumup;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.MoneyService;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.client.json.JsonGetReadersResponse;
import de.metas.payment.sumup.client.json.JsonGetTransactionResponse;
import de.metas.payment.sumup.client.json.JsonPairReaderRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutResponse;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.Mutable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SumUpService
{
	@NonNull private final MoneyService moneyService;
	@NonNull private final SumUpClientFactory clientFactory;
	@NonNull private final SumUpConfigRepository configRepository;
	@NonNull private final SumUpTransactionRepository trxRepository;
	@NonNull private final SumUpTransactionListenersRegistry listeners;

	public void updateCardReadersFromRemote(@NonNull SumUpConfigId configId)
	{
		configRepository.updateById(configId, config -> {
			final JsonGetReadersResponse remoteCardReaders = clientFactory.newClient(config).getCardReaders();
			return updateFromRemote(config, remoteCardReaders);
		});
	}

	private static SumUpConfig updateFromRemote(final SumUpConfig config, final JsonGetReadersResponse remoteResponse)
	{
		final HashMap<SumUpCardReaderExternalId, SumUpCardReader> cardReadersByExternalId = config.getCardReaders()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(SumUpCardReader::getExternalId));

		final ArrayList<SumUpCardReader> newCardReaders = new ArrayList<>();
		for (JsonGetReadersResponse.Item remoteCardReader : remoteResponse.getItems())
		{
			SumUpCardReader cardReader = updateFromRemote(cardReadersByExternalId.get(remoteCardReader.getId()), remoteCardReader);
			newCardReaders.add(cardReader);
		}

		return config.toBuilder()
				.cardReaders(newCardReaders)
				.build();
	}

	private static SumUpCardReader updateFromRemote(@Nullable final SumUpCardReader cardReader, @NonNull final JsonGetReadersResponse.Item remote)
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
			return updateFromRemote(config, remoteCardReaders);
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
			return updateFromRemote(config, remoteCardReaders);
		});
	}

	public SumUpTransaction cardReaderCheckout(@NonNull SumUpCardReaderCheckoutRequest request)
	{
		final SumUpConfigId configId = request.getConfigId();
		final SumUpConfig config = configRepository.getById(configId);

		final Amount amount = request.getAmount();
		final CurrencyPrecision currencyPrecision = moneyService.getStdPrecision(amount.getCurrencyCode());

		final SumUpClient client = clientFactory.newClient(config);
		final JsonReaderCheckoutResponse checkoutResponse = client.cardReaderCheckout(
				request.getCardReaderId(),
				JsonReaderCheckoutRequest.builder()
						.description(request.getDescription())
						.return_url(request.getCallbackUrl())
						.total_amount(JsonReaderCheckoutRequest.JsonAmount.ofAmount(amount, currencyPrecision))
						.build()
		);

		final JsonGetTransactionResponse jsonTrx = client.getTransactionById(checkoutResponse.getData().getClient_transaction_id());
		final SumUpTransaction trx = toSumUpTransaction(jsonTrx, configId);
		trxRepository.saveNew(trx);
		listeners.fireNewTransaction(trx);

		return trx;
	}

	private static SumUpTransaction toSumUpTransaction(@NonNull final JsonGetTransactionResponse remote, @NonNull SumUpConfigId configId)
	{
		return SumUpTransaction.builder()
				.configId(configId)
				.externalId(remote.getId())
				.clientTransactionId(remote.getClient_transaction_id())
				.merchantCode(remote.getMerchant_code())
				.timestamp(Instant.parse(remote.getTimestamp()))
				.status(SumUpTransactionStatus.ofString(remote.getStatus()))
				.amount(Amount.of(remote.getAmount(), remote.getCurrency()))
				.json(remote.getJson())
				.posOrderId(-1) // TODO
				.posPaymentId(-1) // TODO
				.build();
	}

	public SumUpTransaction updateTransactionFromRemote(@NonNull final SumUpClientTransactionId id)
	{
		final Mutable<SumUpTransaction> trxBeforeChangeRef = new Mutable<>();

		final SumUpTransaction trx = trxRepository.updateById(id, trxBeforeChange -> {
			trxBeforeChangeRef.setValue(trxBeforeChange);
			final SumUpConfigId configId = trxBeforeChange.getConfigId();
			final SumUpConfig config = configRepository.getById(configId);
			final SumUpClient client = clientFactory.newClient(config);
			final JsonGetTransactionResponse remoteTrx = client.getTransactionById(trxBeforeChange.getClientTransactionId());
			return toSumUpTransaction(remoteTrx, configId);
		});

		listeners.fireStatusChangedIfNeeded(trx, trxBeforeChangeRef.getValueNotNull());

		return trx;
	}

}
