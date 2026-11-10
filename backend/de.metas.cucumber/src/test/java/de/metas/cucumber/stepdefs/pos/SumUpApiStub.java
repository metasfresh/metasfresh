package de.metas.cucumber.stepdefs.pos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * In-process stand-in for the SumUp REST API, for cucumber. A JDK {@link HttpServer} on an ephemeral port; the base
 * URL is handed to metasfresh via the {@code sumup.baseUrl} property (see {@code CucumberLifeCycleSupport}).
 * <p>
 * Endpoints (all below {@code /v0.1}):
 * <ul>
 *   <li>{@code POST /merchants/{merchant}/readers/{reader}/checkout} - answers per the programmed {@link CheckoutBehavior}; a successful answer echoes a fresh {@code client_transaction_id}. A checkout can be held ({@link #holdNextCheckout()}) and answered later ({@link #answerHeldCheckout})</li>
 *   <li>{@code GET /me/transactions?client_transaction_id=} - the programmed transaction state, or the programmed lookup failure, or an unreadable answer ({@link #answerLookupUnreadable}) (each request is counted per id, see {@link #getStatusLookupCount}); an unknown id (e.g. a PENDING row left by an earlier scenario, since {@link #reset()} forgets all transactions) is answered as PENDING with a synthetic body</li>
 *   <li>{@code POST /merchants/{merchant}/readers/{reader}/terminate} - answers 202 Accepted with no body (as documented: the reader aborts asynchronously) or the programmed 4xx; the reader of each request is recorded</li>
 * </ul>
 * State is static: one stub per JVM, reset between scenarios via {@link #reset()}.
 */
public final class SumUpApiStub
{
	public enum CheckoutBehavior
	{SUCCESS, HTTP_4XX, HTTP_5XX, CONNECTION_RESET}

	@Value
	@Builder
	public static class ReceivedCheckout
	{
		@NonNull String merchantCode;
		@NonNull String readerId;
		@NonNull BigDecimal amount;
		@NonNull String currency;
		@Nullable String clientTransactionId;
		@Nullable String description;
	}

	@Value
	@Builder
	private static class TransactionState
	{
		@NonNull String externalId;
		@NonNull String clientTransactionId;
		@NonNull String merchantCode;
		@NonNull BigDecimal amount;
		@NonNull String currency;
		@NonNull String timestamp;
		@NonNull String status;
		@Nullable BigDecimal amountRefunded;
	}

	private static final ObjectMapper jsonMapper = new ObjectMapper();
	private static final Object lock = new Object();

	private static HttpServer server;
	private static String baseUrl;

	private static CheckoutBehavior nextCheckoutBehavior = CheckoutBehavior.SUCCESS;
	private static boolean failStatusLookup;
	private static boolean terminateFails;
	// set while the next checkout is to be held; the held request waits until the latch is counted down
	@Nullable private static CountDownLatch heldCheckoutRelease;
	@Nullable private static CountDownLatch heldCheckoutReceived;
	private static final List<ReceivedCheckout> receivedCheckouts = new ArrayList<>();
	// the reader ids of the terminate requests received, in order
	private static final List<String> receivedTerminateReaderIds = new ArrayList<>();
	private static final Map<String, TransactionState> transactionsByClientTransactionId = new LinkedHashMap<>();
	// the status lookups (GET /me/transactions) received, per client_transaction_id, whatever they were answered
	private static final Map<String, Integer> statusLookupCountsByClientTransactionId = new HashMap<>();
	// the client_transaction_ids whose status lookup is answered with a transaction metasfresh cannot read (see answerLookupUnreadable)
	private static final Set<String> unreadableLookupClientTransactionIds = new HashSet<>();

	private SumUpApiStub() {}

	/**
	 * Starts the stub (once per JVM) and returns its base URL, e.g. {@code http://127.0.0.1:41234/v0.1}.
	 */
	public static String start()
	{
		synchronized (lock)
		{
			if (server != null)
			{
				return baseUrl;
			}

			try
			{
				server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
			}
			catch (final IOException e)
			{
				throw new RuntimeException("Failed to start the SumUp API stub", e);
			}
			server.createContext("/", SumUpApiStub::handle);
			server.setExecutor(Executors.newCachedThreadPool(r -> {
				final Thread thread = new Thread(r, "SumUpApiStub");
				thread.setDaemon(true);
				return thread;
			}));
			server.start();

			baseUrl = "http://127.0.0.1:" + server.getAddress().getPort() + "/v0.1";
			return baseUrl;
		}
	}

	public static void reset()
	{
		synchronized (lock)
		{
			nextCheckoutBehavior = CheckoutBehavior.SUCCESS;
			failStatusLookup = false;
			terminateFails = false;
			releaseHeldCheckout();
			receivedCheckouts.clear();
			receivedTerminateReaderIds.clear();
			transactionsByClientTransactionId.clear();
			statusLookupCountsByClientTransactionId.clear();
			unreadableLookupClientTransactionIds.clear();
		}
	}

	/**
	 * From now on, the status lookup of the given transaction is answered with HTTP 200 and a transaction whose timestamp
	 * is not a timestamp: the call succeeds, but metasfresh cannot read the answer (it fails while converting it).
	 */
	public static void answerLookupUnreadable(@NonNull final String clientTransactionId)
	{
		synchronized (lock)
		{
			unreadableLookupClientTransactionIds.add(clientTransactionId);
		}
	}

	public static void setNextCheckoutBehavior(@NonNull final CheckoutBehavior behavior)
	{
		synchronized (lock)
		{
			nextCheckoutBehavior = behavior;
		}
	}

	public static void setFailStatusLookup(final boolean fail)
	{
		synchronized (lock)
		{
			failStatusLookup = fail;
		}
	}

	public static void setTerminateFails(final boolean fails)
	{
		synchronized (lock)
		{
			terminateFails = fails;
		}
	}

	/**
	 * @param status         as SumUp reports it: PENDING, SUCCESSFUL, FAILED, CANCELLED, or REFUNDED (refunded in full or in
	 *                       part; per the SumUp API reference it comes with a REFUND event, so pass {@code amountRefunded})
	 * @param amountRefunded if not null, the transaction reports a REFUND event of that amount
	 */
	public static void setTransactionStatus(
			@NonNull final String clientTransactionId,
			@NonNull final String status,
			@Nullable final BigDecimal amountRefunded)
	{
		synchronized (lock)
		{
			final TransactionState trx = transactionsByClientTransactionId.get(clientTransactionId);
			if (trx == null)
			{
				throw new IllegalStateException("The SumUp API stub knows no transaction with client_transaction_id=" + clientTransactionId);
			}
			transactionsByClientTransactionId.put(clientTransactionId, TransactionState.builder()
					.externalId(trx.getExternalId())
					.clientTransactionId(trx.getClientTransactionId())
					.merchantCode(trx.getMerchantCode())
					.amount(trx.getAmount())
					.currency(trx.getCurrency())
					.timestamp(trx.getTimestamp())
					.status(status)
					.amountRefunded(amountRefunded)
					.build());
		}
	}

	/**
	 * The next checkout request is not answered until {@link #answerHeldCheckout} is called, as when the reader
	 * request is slow; meanwhile the caller's send is still in flight.
	 */
	public static void holdNextCheckout()
	{
		synchronized (lock)
		{
			heldCheckoutRelease = new CountDownLatch(1);
			heldCheckoutReceived = new CountDownLatch(1);
		}
	}

	/**
	 * Waits until the held checkout request has arrived (it stays held: the caller's send is still in flight).
	 */
	public static void awaitHeldCheckoutReceived() throws InterruptedException
	{
		final CountDownLatch received;
		synchronized (lock)
		{
			received = heldCheckoutReceived;
		}
		if (received == null)
		{
			throw new IllegalStateException("No checkout is held");
		}
		if (!received.await(60, TimeUnit.SECONDS))
		{
			throw new IllegalStateException("The held checkout request did not arrive within 60 s");
		}
	}

	/**
	 * Waits until the held checkout request has arrived, then answers it per the given behavior.
	 */
	public static void answerHeldCheckout(@NonNull final CheckoutBehavior behavior) throws InterruptedException
	{
		final CountDownLatch received;
		synchronized (lock)
		{
			received = heldCheckoutReceived;
		}
		if (received == null)
		{
			throw new IllegalStateException("No checkout is held");
		}
		if (!received.await(60, TimeUnit.SECONDS))
		{
			throw new IllegalStateException("The held checkout request did not arrive within 60 s");
		}

		synchronized (lock)
		{
			nextCheckoutBehavior = behavior;
			releaseHeldCheckout();
		}
	}

	/**
	 * Lets a held checkout go on (answered per the programmed behavior) and stops holding.
	 */
	public static void releaseHeldCheckout()
	{
		synchronized (lock)
		{
			if (heldCheckoutRelease != null)
			{
				heldCheckoutRelease.countDown();
			}
			heldCheckoutRelease = null;
			heldCheckoutReceived = null;
		}
	}

	/**
	 * @return the reader ids of the terminate requests received since the last {@link #reset()}, whatever they were answered
	 */
	public static List<String> getReceivedTerminateReaderIds()
	{
		synchronized (lock)
		{
			return new ArrayList<>(receivedTerminateReaderIds);
		}
	}

	/**
	 * @return how many status lookups of the given transaction the stub received since the last {@link #reset()}
	 */
	public static int getStatusLookupCount(@NonNull final String clientTransactionId)
	{
		synchronized (lock)
		{
			return statusLookupCountsByClientTransactionId.getOrDefault(clientTransactionId, 0);
		}
	}

	public static List<ReceivedCheckout> getReceivedCheckouts()
	{
		synchronized (lock)
		{
			return new ArrayList<>(receivedCheckouts);
		}
	}

	//
	// HTTP handling
	//

	private static void handle(final HttpExchange exchange) throws IOException
	{
		try
		{
			final URI uri = exchange.getRequestURI();
			final String method = exchange.getRequestMethod();
			final String[] segments = uri.getPath().replaceFirst("^/+", "").split("/");
			// segments[0] is the version prefix "v0.1"

			if ("POST".equals(method) && segments.length == 6 && "merchants".equals(segments[1]) && "readers".equals(segments[3]) && "checkout".equals(segments[5]))
			{
				handleCheckout(exchange, segments[2], segments[4]);
			}
			else if ("POST".equals(method) && segments.length == 6 && "merchants".equals(segments[1]) && "readers".equals(segments[3]) && "terminate".equals(segments[5]))
			{
				handleTerminate(exchange, segments[4]);
			}
			else if ("GET".equals(method) && segments.length == 3 && "me".equals(segments[1]) && "transactions".equals(segments[2]))
			{
				handleGetTransaction(exchange, uri.getRawQuery());
			}
			else
			{
				respond(exchange, 404, "{\"error\":\"not found\"}");
			}
		}
		catch (final Exception ex)
		{
			respond(exchange, 500, "{\"error\":\"stub failure: " + String.valueOf(ex.getMessage()).replace('"', '\'') + "\"}");
		}
	}

	private static void awaitReleaseIfHeld()
	{
		final CountDownLatch release;
		synchronized (lock)
		{
			release = heldCheckoutRelease;
			// only the next checkout is held, later ones are answered at once
			if (release == null || heldCheckoutReceived == null || heldCheckoutReceived.getCount() == 0)
			{
				return;
			}
			heldCheckoutReceived.countDown();
		}

		try
		{
			if (!release.await(60, TimeUnit.SECONDS))
			{
				throw new IllegalStateException("The held checkout was not answered within 60 s");
			}
		}
		catch (final InterruptedException ex)
		{
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interrupted while holding the checkout", ex);
		}
	}

	private static void handleCheckout(final HttpExchange exchange, final String merchantCode, final String readerId) throws IOException
	{
		final JsonNode body = jsonMapper.readTree(readBody(exchange));
		final JsonNode totalAmount = body.get("total_amount");
		final int minorUnit = totalAmount.get("minor_unit").asInt();
		final BigDecimal amount = BigDecimal.valueOf(totalAmount.get("value").asLong(), minorUnit);
		final String currency = totalAmount.get("currency").asText();

		awaitReleaseIfHeld();

		final CheckoutBehavior behavior;
		final String clientTransactionId = UUID.randomUUID().toString();
		synchronized (lock)
		{
			behavior = nextCheckoutBehavior;
			nextCheckoutBehavior = CheckoutBehavior.SUCCESS; // one-shot

			receivedCheckouts.add(ReceivedCheckout.builder()
					.merchantCode(merchantCode)
					.readerId(readerId)
					.amount(amount)
					.currency(currency)
					.clientTransactionId(behavior == CheckoutBehavior.SUCCESS ? clientTransactionId : null)
					.description(body.hasNonNull("description") ? body.get("description").asText() : null)
					.build());

			if (behavior == CheckoutBehavior.SUCCESS)
			{
				transactionsByClientTransactionId.put(clientTransactionId, TransactionState.builder()
						.externalId(UUID.randomUUID().toString())
						.clientTransactionId(clientTransactionId)
						.merchantCode(merchantCode)
						.amount(amount)
						.currency(currency)
						.timestamp(Instant.now().toString())
						.status("PENDING")
						.build());
			}
		}

		switch (behavior)
		{
			case SUCCESS:
				respond(exchange, 201, "{\"data\":{\"client_transaction_id\":\"" + clientTransactionId + "\"}}");
				break;
			case HTTP_4XX:
				respond(exchange, 400, "{\"error\":\"stub: checkout rejected\"}");
				break;
			case HTTP_5XX:
				respond(exchange, 500, "{\"error\":\"stub: checkout failed\"}");
				break;
			case CONNECTION_RESET:
				// announce a body, then hang up: the client sees a premature end of the response
				exchange.sendResponseHeaders(200, 1000);
				exchange.close();
				break;
			default:
				throw new IllegalStateException("Unexpected " + behavior);
		}
	}

	private static void handleTerminate(final HttpExchange exchange, final String readerId) throws IOException
	{
		readBody(exchange);
		final boolean fails;
		synchronized (lock)
		{
			receivedTerminateReaderIds.add(readerId);
			fails = terminateFails;
		}

		if (fails)
		{
			respond(exchange, 400, "{\"error\":\"stub: terminate rejected\"}");
		}
		else
		{
			// SumUp API reference: 202 Accepted, no body
			exchange.sendResponseHeaders(202, -1);
			exchange.close();
		}
	}

	private static void handleGetTransaction(final HttpExchange exchange, @Nullable final String rawQuery) throws IOException
	{
		final String clientTransactionId = extractQueryParam(rawQuery, "client_transaction_id");

		TransactionState trx;
		final boolean unreadable;
		synchronized (lock)
		{
			statusLookupCountsByClientTransactionId.merge(String.valueOf(clientTransactionId), 1, Integer::sum);
			if (failStatusLookup)
			{
				respond(exchange, 500, "{\"error\":\"stub: status lookup failed\"}");
				return;
			}
			trx = clientTransactionId != null ? transactionsByClientTransactionId.get(clientTransactionId) : null;
			unreadable = unreadableLookupClientTransactionIds.contains(String.valueOf(clientTransactionId));
		}

		if (trx == null)
		{
			trx = TransactionState.builder()
					.externalId(UUID.nameUUIDFromBytes(String.valueOf(clientTransactionId).getBytes(StandardCharsets.UTF_8)).toString())
					.clientTransactionId(String.valueOf(clientTransactionId))
					.merchantCode("STUBMERCHANT")
					.amount(BigDecimal.ZERO)
					.currency("EUR")
					.timestamp(Instant.now().toString())
					.status("PENDING")
					.build();
		}

		final Map<String, Object> json = new LinkedHashMap<>();
		json.put("id", trx.getExternalId());
		json.put("client_transaction_id", trx.getClientTransactionId());
		json.put("merchant_code", trx.getMerchantCode());
		json.put("timestamp", unreadable ? "not-a-timestamp" : trx.getTimestamp());
		json.put("status", trx.getStatus());
		json.put("amount", trx.getAmount());
		json.put("currency", trx.getCurrency());
		// a refunded charge (status REFUNDED: "refunded in full or in part") was a card charge, too
		if ("SUCCESSFUL".equals(trx.getStatus()) || "REFUNDED".equals(trx.getStatus()))
		{
			final Map<String, Object> card = new HashMap<>();
			card.put("type", "VISA");
			card.put("last_4_digits", "0000");
			json.put("card", card);
		}
		if (trx.getAmountRefunded() != null)
		{
			final Map<String, Object> event = new LinkedHashMap<>();
			event.put("id", 1);
			event.put("type", "REFUND");
			event.put("status", "REFUNDED");
			event.put("timestamp", trx.getTimestamp());
			event.put("amount", trx.getAmountRefunded());
			event.put("fee_amount", BigDecimal.ZERO);
			final List<Object> events = new ArrayList<>();
			events.add(event);
			json.put("events", events);
		}

		respond(exchange, 200, jsonMapper.writeValueAsString(json));
	}

	@Nullable
	private static String extractQueryParam(@Nullable final String rawQuery, @NonNull final String name)
	{
		if (rawQuery == null)
		{
			return null;
		}
		for (final String pair : rawQuery.split("&"))
		{
			final int idx = pair.indexOf('=');
			if (idx > 0 && name.equals(pair.substring(0, idx)))
			{
				return pair.substring(idx + 1);
			}
		}
		return null;
	}

	private static String readBody(final HttpExchange exchange) throws IOException
	{
		try (final InputStream in = exchange.getRequestBody())
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final byte[] buf = new byte[4096];
			int n;
			while ((n = in.read(buf)) > 0)
			{
				out.write(buf, 0, n);
			}
			return new String(out.toByteArray(), StandardCharsets.UTF_8);
		}
	}

	private static void respond(final HttpExchange exchange, final int status, final String json) throws IOException
	{
		final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		exchange.sendResponseHeaders(status, bytes.length);
		try (final OutputStream out = exchange.getResponseBody())
		{
			out.write(bytes);
		}
	}
}
