package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.currency.CurrencyCode;
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
	@NonNull CurrencyCode priceListCurrency;

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

	/**
	 * Labels of the cash withdrawal categories to offer at this terminal. Each label becomes a {@code C_Charge} of a
	 * fresh {@code C_ChargeType}, named with a per-run unique suffix (charge names are unique per client); the actual
	 * names are returned in {@link JsonPOSTerminalResponse#getCashWithdrawalCategories()}. Sysconfig
	 * {@code de.metas.pos.CashWithdrawal.C_ChargeType_ID} is pointed at that charge type, so it is global: at most one
	 * terminal per request may declare categories.
	 */
	@Builder.Default
	@NonNull List<String> cashWithdrawalCategories = ImmutableList.of();

	@Value
	@Builder
	@Jacksonized
	public static class ProductPrice
	{
		@NonNull BigDecimal price;
		@Nullable X12DE355 uom;
		@Nullable InvoicableQtyBasedOn invoicableQtyBasedOn;

		/**
		 * Overrides the tax rate this product is priced under on the terminal (percentage, e.g. {@code 7} for
		 * 7&nbsp;%). POS order lines need a LINE-level tax (see {@link CreatePOSTerminalCommand}'s own Javadoc on
		 * {@code getTaxCategoryId}), so every rate used here gets its own dedicated line-level tax category —
		 * created once per rate and reused across runs, the same find-or-create shape as the default 19&nbsp;%
		 * category. {@code null} keeps the pre-existing default (19&nbsp;%).
		 */
		@Nullable BigDecimal taxRatePercent;
	}
}
