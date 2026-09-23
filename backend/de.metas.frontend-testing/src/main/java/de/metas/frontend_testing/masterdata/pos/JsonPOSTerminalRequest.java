package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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
	 * Identifiers (keys of the request's top-level {@code products} map) to be priced into the
	 * terminal's own {@code M_PriceList_Version}.
	 */
	@Nullable Set<Identifier> products;

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
	@NonNull List<String> paymentMethods = ImmutableList.of("CASH");
}
