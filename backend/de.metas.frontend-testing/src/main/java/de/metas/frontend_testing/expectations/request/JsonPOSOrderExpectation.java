package de.metas.frontend_testing.expectations.request;

import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Expectation for the single POS order a cashier created on a POS terminal (the terminal is the map key).
 * The sales order behind it must exist; each non-null field adds an assertion on that sales order's documents.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSOrderExpectation
{
	/**
	 * Identifier of the cashier (the login user) who created the order.
	 */
	@NonNull String cashier;

	/**
	 * Expected DocStatus of the sales order (wire value is the 2-char code, e.g. "CL").
	 */
	@Nullable DocStatus salesOrderDocStatus;

	/**
	 * Expected DocStatus of the sales order's single invoice.
	 */
	@Nullable DocStatus invoiceDocStatus;

	/**
	 * Expected IsPaid of the sales order's single invoice.
	 */
	@Nullable Boolean invoicePaid;

	/**
	 * Expected DocStatus of the sales order's single shipment.
	 */
	@Nullable DocStatus shipmentDocStatus;
}
