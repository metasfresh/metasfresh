package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.pos.POSPaymentMethod;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Request to create a POS terminal ({@code C_POS}) for frontend/mobile testing.
 *
 * @see CreatePOSTerminalCommand
 */
@Value
@Builder
@Jacksonized
public class JsonPOSTerminalRequest
{
	/** ISO-4217 currency code (e.g. {@code "EUR"}) of the POS-specific sales price list created for this terminal. */
	@NonNull String priceListCurrency;

	/**
	 * Explicit {@code M_ProductPrice} to create in this terminal's own {@code M_PriceList_Version}, keyed by the
	 * request's top-level {@code products} map identifier. Never defaulted or copied from elsewhere — a
	 * catch-weight (scale-label) product needs its per-{@code uom} price stated here, e.g. a per-kg price for an
	 * {@link InvoicableQtyBasedOn#CatchWeight} product.
	 */
	@Builder.Default
	@NonNull Map<String, ProductPrice> products = ImmutableMap.of();

	boolean isTaxIncluded;

	/**
	 * Identifier of an existing {@code bpartners} entry to use as the walk-in ("cash sale") customer
	 * ({@code C_POS.C_BPartnerCashTrx_ID}). When omitted, a new walk-in BPartner is created.
	 */
	@Nullable Identifier walkInCustomer;

	/**
	 * Payment methods offered at this terminal. Only {@code CASH} is provisioned by
	 * {@link CreatePOSTerminalCommand} so far; defaults to {@code ["CASH"]}.
	 */
	@Builder.Default
	@NonNull List<POSPaymentMethod> paymentMethods = ImmutableList.of(POSPaymentMethod.CASH);

	@Value
	@Builder
	@Jacksonized
	public static class ProductPrice
	{
		@NonNull BigDecimal price;
		@Nullable X12DE355 uom;
		@Nullable InvoicableQtyBasedOn invoicableQtyBasedOn;
	}
}
