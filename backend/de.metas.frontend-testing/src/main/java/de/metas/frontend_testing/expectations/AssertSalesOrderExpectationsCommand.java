package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import de.metas.document.engine.DocStatus;
import de.metas.frontend_testing.expectations.request.JsonInOutExpectation;
import de.metas.frontend_testing.expectations.request.JsonSalesOrderExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/**
 * Asserts M_InOut (shipment) state per sales order.
 *
 * <p>Consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   salesOrders: {
 *     // assert exactly one completed shipment
 *     'SO1': { shipments: [{ docStatus: 'CO' }] },
 *     // assert NO shipments (DO_NOT_CREATE policy)
 *     'SO2': { shipments: [] }
 *   }
 * });
 * </pre>
 */
@Builder
class AssertSalesOrderExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertSalesOrderExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonSalesOrderExpectation> expectations;

	/** How long to poll for async-generated shipments before failing. */
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

	void execute() throws InterruptedException
	{
		for (final Map.Entry<String, JsonSalesOrderExpectation> entry : expectations.entrySet())
		{
			assertSalesOrder(entry.getKey(), entry.getValue());
		}
	}

	private void assertSalesOrder(
			@NonNull final String orderIdentifierStr,
			@NonNull final JsonSalesOrderExpectation expectation) throws InterruptedException
	{
		final OrderId orderId = getOrderId(orderIdentifierStr);

		if (expectation.getShippedQty() != null)
		{
			assertShippedQty(orderId, expectation.getShippedQty());
		}

		if (expectation.getShipments() == null)
		{
			return; // field omitted – nothing to assert
		}

		final List<JsonInOutExpectation> shipmentExpectations = expectation.getShipments();

		final List<I_M_InOut> actualShipments;
		if (shipmentExpectations.isEmpty())
		{
			// Absence assertion: the caller says NO shipment should exist.
			// No polling needed — retrieve once and assert count is zero.
			actualShipments = excludeVoidedAndReversedShipments(services.getInOutsByOrderId(orderId));
		}
		else
		{
			// Presence assertion: poll until we have at least the expected number of shipments.
			actualShipments = pollForShipments(orderId, shipmentExpectations.size());
		}

		softly(() -> {
			softlyPutContext("orderId", orderId);
			softlyPutContext("shipmentExpectations", shipmentExpectations);
			softlyPutContext("actualShipments", actualShipments);

			assertThat(actualShipments)
					.as("shipments for order " + orderId)
					.hasSameSize(shipmentExpectations);

			final int size = Math.min(shipmentExpectations.size(), actualShipments.size());
			for (int i = 0; i < size; i++)
			{
				assertShipment(shipmentExpectations.get(i), actualShipments.get(i), i);
			}
		});
	}

	private OrderId getOrderId(@NonNull final String orderIdentifierStr)
	{
		final Identifier identifier = Identifier.ofString(orderIdentifierStr);
		return context.getOptionalId(identifier, OrderId.class)
				.orElseGet(() -> identifier.toId(OrderId.class));
	}

	/**
	 * Polls until {@code expectedCount} shipments are available for the order, or until the timeout elapses.
	 * Shipments are generated asynchronously after picking/scanning, so polling is required.
	 * Voided and reversed shipments (DocStatus='VO'/'RE') are excluded from the count.
	 */
	private List<I_M_InOut> pollForShipments(
			@NonNull final OrderId orderId,
			final int expectedCount) throws InterruptedException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		List<I_M_InOut> actuals = excludeVoidedAndReversedShipments(services.getInOutsByOrderId(orderId));

		while (actuals.size() < expectedCount && stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0)
		{
			logger.info("Waiting for {}/{} shipments for order {} (elapsed: {})", actuals.size(), expectedCount, orderId, stopwatch);
			//noinspection BusyWait
			Thread.sleep(1000);
			actuals = excludeVoidedAndReversedShipments(services.getInOutsByOrderId(orderId));
		}

		if (actuals.size() < expectedCount)
		{
			throw new AdempiereException("Expected " + expectedCount + " shipment(s) for order " + orderId
					+ " but only found " + actuals.size() + " after " + stopwatch);
		}

		return actuals;
	}

	private void assertShipment(
			@NonNull final JsonInOutExpectation expectation,
			@NonNull final I_M_InOut actual,
			final int index)
	{
		softly(() -> {
			softlyPutContext("shipmentIndex", index);
			softlyPutContext("shipment", actual);

			// Refresh to get the latest persisted state (e.g. after async completion).
			InterfaceWrapperHelper.refresh(actual);

			if (expectation.getDocStatus() != null)
			{
				// ofNullableCodeOrUnknown: null/unrecognised DB value maps to Unknown rather than NPE.
				final DocStatus actualDocStatus = DocStatus.ofNullableCodeOrUnknown(actual.getDocStatus());
				assertThat(actualDocStatus)
						.as("DocStatus of shipment[" + index + "] M_InOut_ID=" + actual.getM_InOut_ID())
						.isEqualTo(expectation.getDocStatus());
			}

			if (expectation.getMovementQty() != null)
			{
				final BigDecimal actualMovementQty = services.getInOutLines(actual).stream()
						.map(I_M_InOutLine::getMovementQty)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				// Strip trailing zeros so BigDecimal.equals is not scale-sensitive (2 == 2.0).
				assertThat(actualMovementQty.stripTrailingZeros())
						.as("total MovementQty of shipment[" + index + "] M_InOut_ID=" + actual.getM_InOut_ID())
						.isEqualTo(expectation.getMovementQty().stripTrailingZeros());
			}
		});
	}

	/**
	 * Excludes voided and reversed shipments from the list.
	 * M_InOut records with DocStatus='VO' (voided) or 'RE' (reversed) are not considered valid shipments
	 * for assertion purposes, even though they remain IsActive='Y' in the DB.
	 */
	private List<I_M_InOut> excludeVoidedAndReversedShipments(@NonNull final List<I_M_InOut> shipments)
	{
		return shipments.stream()
				.filter(inout -> {
					final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(inout.getDocStatus());
					return !docStatus.isReversedOrVoided();
				})
				.collect(Collectors.toList());
	}

	/**
	 * Asserts the total shipped quantity for the given order by summing {@code MovementQty} across
	 * all PROCESSED (completed) shipment lines (M_InOutLine) linked to the order's lines.
	 *
	 * <p>This is aggregation-independent: it does not matter whether the shipment lines belong to
	 * one or multiple M_InOut documents (e.g. mass-printing may create one combined per-customer
	 * shipment). The check verifies the order's demand ended up shipped on line(s) with the right
	 * total qty, regardless of grouping.
	 *
	 * <p>Shipment generation and completion are asynchronous. This method polls until the summed
	 * qty equals the expectation, or until the timeout elapses.
	 */
	private void assertShippedQty(
			@NonNull final OrderId orderId,
			@NonNull final BigDecimal expectedShippedQty) throws InterruptedException
	{
		final Set<OrderLineId> orderLineIds = services.getOrderLineIdsByOrderId(orderId);

		if (orderLineIds.isEmpty())
		{
			throw new AdempiereException("No order lines found for order " + orderId);
		}

		// Poll until processed shipment lines accumulate the expected total, or timeout
		final Stopwatch stopwatch = Stopwatch.createStarted();
		BigDecimal actualShippedQty = BigDecimal.ZERO;

		while (stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0)
		{
			final List<I_M_InOutLine> processedLines = services.getProcessedShipmentLinesByOrderLineIds(orderLineIds);
			actualShippedQty = processedLines.stream()
					.map(I_M_InOutLine::getMovementQty)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			if (actualShippedQty.stripTrailingZeros().equals(expectedShippedQty.stripTrailingZeros()))
			{
				break; // reached expected total — exit poll
			}

			logger.info("Waiting for shipped qty for order {} — actual={} expected={} (elapsed: {})",
					orderId, actualShippedQty, expectedShippedQty, stopwatch);
			//noinspection BusyWait
			Thread.sleep(1000);
		}

		final BigDecimal finalShippedQty = actualShippedQty;
		softly(() -> {
			softlyPutContext("orderId", orderId);
			assertThat(finalShippedQty.stripTrailingZeros())
					.as("total shipped qty (sum of processed M_InOutLine.MovementQty) for order " + orderId)
					.isEqualTo(expectedShippedQty.stripTrailingZeros());
		});
	}
}
