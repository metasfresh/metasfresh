package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonManufacturingExpectation
{
	@Nullable List<ReceivedHU> receivedHUs;
	@Nullable List<ReceivedHU> receivedCoProductHUs;
	@Nullable List<IssuedHU> issuedHUs;

	/** {@code PP_Order.DocStatus}, e.g. {@code "CL"} */
	@Nullable String docStatus;

	/**
	 * Polls every second, up to this many seconds, until all the order's cost collectors are posted
	 * ({@code Posted='Y'}); fails on timeout or when a collector has a posting error. Runs before the other cost assertions.
	 */
	@Nullable Integer waitUntilPostedSeconds;

	/** the order's cost collectors; each entry asserts that one of the given type and product is (or is not) there */
	@Nullable List<CostCollector> costCollectors;

	/** {@code PP_Order.CostDifference} */
	@Nullable BigDecimal costDifference;

	/** Σ(AmtAcctDr - AmtAcctCr) of the Fact_Acct rows of the order's cost collectors on the {@code P_WIP_Acct} account */
	@Nullable BigDecimal wipAcctBalance;

	/** like {@link #wipAcctBalance}, on the {@code P_COGS_Acct} account */
	@Nullable BigDecimal cogsAcctBalance;

	/**
	 * Whether the {@code PP_Order} record itself has {@code Fact_Acct} rows (accounting is booked on the order's cost
	 * collectors, never on the order); {@code false} catches a fact that was booked on the order.
	 */
	@Nullable Boolean orderFactAcctPresent;

	/** product identifier to {@code M_Cost.CurrentCostPrice} of the costing-method element of the accounting schema */
	@Nullable Map<String, BigDecimal> currentCostPrices;

	//
 	//
 	//

	@Value
	@Builder
	@Jacksonized
	public static class CostCollector
	{
		/** a {@code CostCollectorType} name, e.g. {@code ComponentIssue}, {@code MaterialReceipt}, {@code CoProduct} */
		@NonNull String type;
		@NonNull Identifier product;
		@Nullable BigDecimal qty;
		/** whether such a cost collector must exist; {@code null} means {@code true} */
		@Nullable Boolean present;
	}

	@Value
	@Builder
	@Jacksonized
	public static class ReceivedHU
	{
		/** The received HU itself — the only handle for a bare VHU/CU, which has neither an LU nor a TU parent. */
		@Nullable Identifier hu;
		@Nullable Identifier lu;
		@Nullable Identifier tu;
		@Nullable QtyAndUOMString qty;
		@Nullable Boolean looseCUs;
	}

	/**
	 * An HU issued to one of the order's BOM lines. It is created by the issue itself, so it has no
	 * masterdata identifier to address it by - the expectations are positional, ordered by HU id.
	 */
	@Value
	@Builder
	@Jacksonized
	public static class IssuedHU
	{
		@Nullable QtyAndUOMString qty;
		@Nullable Map<String, String> attributes;
	}
}

