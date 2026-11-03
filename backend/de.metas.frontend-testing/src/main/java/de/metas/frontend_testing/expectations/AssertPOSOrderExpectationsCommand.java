package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.document.engine.DocStatus;
import de.metas.frontend_testing.expectations.request.JsonPOSOrderExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.pos.POSOrder;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/**
 * Asserts the sales order, invoice and shipment that the POS checkout creates asynchronously for a POS order.
 *
 * <p>Consumer-side JSON shape:
 * <pre>
 * Backend.expect({
 *   posOrders: {
 *     'T1': { cashier: 'user', salesOrderDocStatus: 'CL', invoiceDocStatus: 'CO', invoicePaid: true, shipmentDocStatus: 'CO' }
 *   }
 * });
 * </pre>
 */
@Builder
class AssertPOSOrderExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertPOSOrderExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonPOSOrderExpectation> expectations;

	/**
	 * The sales order is linked to the POS order only at the end of the checkout work package (after the invoice
	 * and shipment were created), so polling for that link is enough to wait for the whole chain.
	 */
	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

	void execute() throws InterruptedException
	{
		for (final Map.Entry<String, JsonPOSOrderExpectation> entry : expectations.entrySet())
		{
			assertPOSOrder(entry.getKey(), entry.getValue());
		}
	}

	private void assertPOSOrder(
			@NonNull final String posTerminalIdentifierStr,
			@NonNull final JsonPOSOrderExpectation expectation) throws InterruptedException
	{
		final POSTerminalId posTerminalId = context.getId(Identifier.ofString(posTerminalIdentifierStr), POSTerminalId.class);
		final UserId cashierId = context.getId(Identifier.ofString(expectation.getCashier()), UserId.class);

		final OrderId salesOrderId = pollForSalesOrderId(posTerminalId, cashierId);

		softly(() -> {
			softlyPutContext("posTerminalId", posTerminalId);
			softlyPutContext("cashierId", cashierId);
			softlyPutContext("salesOrderId", salesOrderId);
			softlyPutContext("expectation", expectation);

			if (expectation.getSalesOrderDocStatus() != null)
			{
				final I_C_Order salesOrder = services.getOrderById(salesOrderId);
				assertThat(DocStatus.ofNullableCodeOrUnknown(salesOrder.getDocStatus()))
						.as("DocStatus of sales order " + salesOrderId)
						.isEqualTo(expectation.getSalesOrderDocStatus());
			}

			if (expectation.getInvoiceDocStatus() != null || expectation.getInvoicePaid() != null)
			{
				final List<I_C_Invoice> invoices = services.getInvoicesByOrderId(salesOrderId);
				softlyPutContext("invoices", invoices);
				assertThat(invoices.size()).as("number of invoices of sales order " + salesOrderId).isEqualTo(1);
				if (invoices.size() == 1)
				{
					final I_C_Invoice invoice = invoices.get(0);
					if (expectation.getInvoiceDocStatus() != null)
					{
						assertThat(DocStatus.ofNullableCodeOrUnknown(invoice.getDocStatus()))
								.as("DocStatus of invoice C_Invoice_ID=" + invoice.getC_Invoice_ID())
								.isEqualTo(expectation.getInvoiceDocStatus());
					}
					if (expectation.getInvoicePaid() != null)
					{
						assertThat(invoice.isPaid())
								.as("IsPaid of invoice C_Invoice_ID=" + invoice.getC_Invoice_ID())
								.isEqualTo(expectation.getInvoicePaid());
					}
				}
			}

			if (expectation.getShipmentDocStatus() != null)
			{
				final List<I_M_InOut> shipments = services.getInOutsByOrderId(salesOrderId);
				softlyPutContext("shipments", shipments);
				assertThat(shipments.size()).as("number of shipments of sales order " + salesOrderId).isEqualTo(1);
				if (shipments.size() == 1)
				{
					final I_M_InOut shipment = shipments.get(0);
					assertThat(DocStatus.ofNullableCodeOrUnknown(shipment.getDocStatus()))
							.as("DocStatus of shipment M_InOut_ID=" + shipment.getM_InOut_ID())
							.isEqualTo(expectation.getShipmentDocStatus());
				}
			}
		});
	}

	private OrderId pollForSalesOrderId(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final UserId cashierId) throws InterruptedException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		POSOrder posOrder = services.getSinglePOSOrder(posTerminalId, cashierId);
		while (posOrder.getSalesOrderId() == null && stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0)
		{
			logger.info("Waiting for the sales order of {} (elapsed: {})", posOrder.getLocalId(), stopwatch);
			//noinspection BusyWait
			Thread.sleep(1000);
			posOrder = services.getSinglePOSOrder(posTerminalId, cashierId);
		}

		final OrderId salesOrderId = posOrder.getSalesOrderId();
		if (salesOrderId == null)
		{
			throw new AdempiereException("No sales order was created for POS order " + posOrder.getLocalId()
					+ " (status " + posOrder.getStatus() + ") after " + stopwatch);
		}
		return salesOrderId;
	}
}
