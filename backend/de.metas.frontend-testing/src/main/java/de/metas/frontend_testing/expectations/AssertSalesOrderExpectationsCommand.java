package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import de.metas.document.engine.DocStatus;
import de.metas.frontend_testing.expectations.request.JsonInOutExpectation;
import de.metas.frontend_testing.expectations.request.JsonSalesOrderExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
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
		if (expectation.getShipments() == null)
		{
			return; // field omitted – nothing to assert
		}

		final OrderId orderId = getOrderId(orderIdentifierStr);
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
				final DocStatus actualDocStatus = DocStatus.ofCode(actual.getDocStatus());
				assertThat(actualDocStatus)
						.as("DocStatus of shipment[" + index + "] M_InOut_ID=" + actual.getM_InOut_ID())
						.isEqualTo(expectation.getDocStatus());
			}

			if (expectation.getMovementQty() != null)
			{
				final BigDecimal actualMovementQty = services.getInOutLines(actual).stream()
						.map(I_M_InOutLine::getMovementQty)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				// Use compareTo (not equals) because BigDecimal.equals is scale-sensitive (2 != 2.0).
				if (actualMovementQty.compareTo(expectation.getMovementQty()) != 0)
				{
					assertThat(actualMovementQty)
							.as("total MovementQty of shipment[" + index + "] M_InOut_ID=" + actual.getM_InOut_ID())
							.isEqualTo(expectation.getMovementQty());
				}
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
				.filter(inout -> !DocStatus.Voided.equals(DocStatus.ofCode(inout.getDocStatus()))
						&& !DocStatus.Reversed.equals(DocStatus.ofCode(inout.getDocStatus())))
				.collect(Collectors.toList());
	}
}
