package de.metas.payment.sumup;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpTransaction.LastSync;
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
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SumUpService
{
	@NonNull private static final Logger logger = LogManager.getLogger(SumUpService.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
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

		// SumUp accepted the checkout: the reader may charge the card now, so any failure from here on leaves the outcome
		// unknown (never a definite rejection), see SumUpCallException.ofFailureAfterAccepted
		try
		{
			final SumUpTransaction trx = toSumUpTransaction(checkoutResponse, request, config);
			trxRepository.saveNew(trx);
			return trx;
		}
		catch (final Exception ex)
		{
			throw SumUpCallException.ofFailureAfterAccepted(ex, checkoutResponse.getData().getClient_transaction_id());
		}
	}

	/**
	 * Imports a SumUp transaction whose checkout SumUp accepted, but for which metasfresh has no {@link SumUpTransaction}
	 * (e.g. because saving it failed right after the checkout, see {@link SumUpCallException#getClientTransactionId()}).
	 * <p>
	 * No-op if a transaction with that client transaction id exists (it is returned). Otherwise the transaction is looked
	 * up at SumUp and saved with the given POS reference, which fires the status change event. The outcome of the lookup is
	 * told by the result: the transaction, {@link SumUpImportResult#isNotFound() not found} (SumUp does not know the id,
	 * nothing saved) or a {@link SumUpImportResult#getLookupError() lookup error}, e.g. an outage (nothing saved). A lookup
	 * failure is not recorded as AD_Issue: the caller reports an outage once. Any other failure (config, saving) is thrown.
	 * <p>
	 * Safe against a concurrent import of the same id: the database allows one transaction per client transaction id, and
	 * the loser of the race returns the winner's transaction.
	 */
	public SumUpImportResult importAcceptedCheckout(
			@NonNull final SumUpConfigId configId,
			@NonNull final SumUpClientTransactionId clientTransactionId,
			@NonNull final SumUpPOSRef posRef,
			@NonNull final SumUpCardReaderExternalId cardReaderId,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final SumUpTransaction existingTrx = retrieveByClientTransactionIdOrNull(clientTransactionId);
		if (existingTrx != null)
		{
			return SumUpImportResult.ofTransaction(existingTrx);
		}

		final SumUpConfig config = configRepository.getById(configId);
		final SumUpCardReader cardReader = config.getCardReaderById(cardReaderId);
		final SumUpClient client = clientFactory.newClient(config);
		client.setPosRef(posRef);
		client.setCreateIssueOnError(false);

		final JsonGetTransactionResponse remoteTrx;
		try
		{
			remoteTrx = client.getTransactionById(clientTransactionId);
		}
		catch (final Exception ex)
		{
			logger.warn("Could not get SumUp transaction {} from SumUp; nothing imported", clientTransactionId, ex);
			return isNotFound(ex) ? SumUpImportResult.notFound() : SumUpImportResult.ofLookupError(ex);
		}

		final SumUpTransaction trx = toSumUpTransaction(remoteTrx, config, cardReader, posRef, clientAndOrgId);
		try
		{
			trxRepository.saveNew(trx);
		}
		catch (final DBUniqueConstraintException ex)
		{
			// a concurrent import saved it meanwhile (unique index): then that one is the result
			final SumUpTransaction concurrentTrx = retrieveByClientTransactionIdOrNull(clientTransactionId);
			if (concurrentTrx == null)
			{
				throw ex;
			}
			logger.info("SumUpTransaction {} was imported concurrently; using it", clientTransactionId, ex);
			return SumUpImportResult.ofTransaction(concurrentTrx);
		}
		return SumUpImportResult.ofTransaction(trx);
	}

	@Nullable
	private SumUpTransaction retrieveByClientTransactionIdOrNull(@NonNull final SumUpClientTransactionId clientTransactionId)
	{
		final List<SumUpTransaction> trxs = trxRepository.list(SumUpTransactionQuery.ofClientTransactionId(clientTransactionId));
		return trxs.isEmpty() ? null : trxs.get(0);
	}

	/**
	 * @return the transaction with the given client transaction id, if metasfresh has it (at most one exists, by a unique index)
	 */
	public Optional<SumUpTransaction> findTransactionByClientTransactionId(@NonNull final SumUpClientTransactionId clientTransactionId)
	{
		return Optional.ofNullable(retrieveByClientTransactionIdOrNull(clientTransactionId));
	}

	/**
	 * @param posRef must reference something (at least one id &gt; 0)
	 * @return whether a transaction carrying the given POS reference was created at or after the given time
	 */
	public boolean existsTransactionByPOSRefCreatedSince(@NonNull final SumUpPOSRef posRef, @NonNull final Instant createdSince)
	{
		return trxRepository.existsByPOSRefCreatedSince(posRef, createdSince);
	}

	/**
	 * @return true if SumUp answered that it does not know the transaction (HTTP 404), as opposed to a failure of the call
	 */
	private static boolean isNotFound(@NonNull final Exception ex)
	{
		return ex instanceof SumUpCallException
				&& ex.getCause() instanceof HttpClientErrorException
				&& ((HttpClientErrorException)ex.getCause()).getStatusCode() == HttpStatus.NOT_FOUND;
	}

	private static SumUpTransaction toSumUpTransaction(
			@NonNull final JsonGetTransactionResponse remote,
			@NonNull final SumUpConfig config,
			@NonNull final SumUpCardReader cardReader,
			@NonNull final SumUpPOSRef posRef,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return SumUpTransaction.builder()
				.configId(config.getId())
				.externalId(remote.getId())
				.clientTransactionId(remote.getClient_transaction_id())
				.merchantCode(remote.getMerchant_code())
				.cardReader(SumUpTransaction.CardReader.builder()
						.externalId(cardReader.getExternalId())
						.name(cardReader.getName())
						.build())
				.timestamp(Instant.parse(remote.getTimestamp()))
				.status(SumUpTransactionStatus.ofString(remote.getStatus()))
				.amount(Amount.of(remote.getAmount(), remote.getCurrency()))
				.amountRefunded(Amount.of(remote.getAmountRefunded(), remote.getCurrency()))
				.card(extractSumUpTransactionCard(remote.getCard()))
				.json(remote.getJson())
				.lastSync(LastSync.ok())
				.clientAndOrgId(clientAndOrgId)
				.posRef(posRef)
				.build();
	}

	public void terminateCardReaderCheckout(@NonNull final SumUpConfigId configId, @NonNull final SumUpCardReaderExternalId cardReaderId)
	{
		final SumUpConfig config = configRepository.getById(configId);
		clientFactory.newClient(config).terminateCardReaderCheckout(cardReaderId);
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

		final SumUpCardReader cardReader = config.getCardReaderById(request.getCardReaderId());

		return SumUpTransaction.builder()
				.configId(config.getId())
				.externalId(SumUpTransactionExternalId.ofString("UNKNOWN-" + clientTransactionId))
				.clientTransactionId(clientTransactionId)
				.merchantCode(config.getMerchantCode())
				.cardReader(SumUpTransaction.CardReader.builder()
						.externalId(cardReader.getExternalId())
						.name(cardReader.getName())
						.build())
				.timestamp(SystemTime.asInstant())
				.status(SumUpTransactionStatus.PENDING)
				.amount(request.getAmount())
				.json(null)
				.clientAndOrgId(request.getClientAndOrgId())
				.posRef(request.getPosRef())
				.build();
	}

	/**
	 * Refreshes the transaction from SumUp; a failed lookup is recorded as AD_Issue and stored as the transaction's
	 * {@link LastSync}.
	 */
	public void updateTransactionFromRemote(@NonNull final SumUpClientTransactionId id)
	{
		updateTransactionFromRemote(id, true);
	}

	/**
	 * Refreshes the transaction from SumUp; a failed lookup is stored as the transaction's {@link LastSync}.
	 *
	 * @param isCreateIssueOnError whether a failed lookup is recorded as AD_Issue; if not, it is only logged (for a
	 *                             caller that repeats a lookup while SumUp is unreachable and reports the outage once
	 *                             itself)
	 */
	public void updateTransactionFromRemote(@NonNull final SumUpClientTransactionId id, final boolean isCreateIssueOnError)
	{
		trxRepository.updateById(id, trx -> updateTransactionFromRemote(trx, isCreateIssueOnError));
	}

	private SumUpTransaction updateTransactionFromRemote(final SumUpTransaction trx)
	{
		return updateTransactionFromRemote(trx, true);
	}

	private SumUpTransaction updateTransactionFromRemote(final SumUpTransaction trx, final boolean isCreateIssueOnError)
	{
		final SumUpConfigId configId = trx.getConfigId();
		final SumUpConfig config = configRepository.getById(configId);
		final SumUpClient client = clientFactory.newClient(config);
		client.setPosRef(trx.getPosRef());
		client.setCreateIssueOnError(isCreateIssueOnError);

		try
		{
			final JsonGetTransactionResponse remoteTrx = client.getTransactionById(trx.getClientTransactionId());
			return updateTransactionFromRemote(trx, remoteTrx);
		}
		catch (Exception ex)
		{
			if (!isCreateIssueOnError)
			{
				logger.warn("Could not get SumUp transaction {} from SumUp; not recorded as AD_Issue, as requested by the caller", trx.getClientTransactionId(), ex);
				return trx.toBuilder()
						.lastSync(LastSync.errorWithoutIssue())
						.build();
			}

			final AdIssueId errorId = errorManager.createIssue(ex);
			return trx.toBuilder()
					.lastSync(LastSync.error(errorId))
					.build();
		}
	}

	private static SumUpTransaction updateTransactionFromRemote(@NonNull SumUpTransaction trx, @NonNull final JsonGetTransactionResponse remote)
	{
		return trx.toBuilder()
				.externalId(remote.getId())
				.clientTransactionId(remote.getClient_transaction_id())
				.merchantCode(remote.getMerchant_code())
				.timestamp(Instant.parse(remote.getTimestamp()))
				.status(SumUpTransactionStatus.ofString(remote.getStatus()))
				.amount(Amount.of(remote.getAmount(), remote.getCurrency()))
				.amountRefunded(Amount.of(remote.getAmountRefunded(), remote.getCurrency()))
				.card(extractSumUpTransactionCard(remote.getCard()))
				.json(remote.getJson())
				.lastSync(LastSync.ok())
				.build();
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
