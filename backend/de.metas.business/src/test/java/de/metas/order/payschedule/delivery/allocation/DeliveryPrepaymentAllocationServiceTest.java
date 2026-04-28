/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.order.payschedule.delivery.allocation;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for
 * {@link DeliveryPrepaymentAllocationService#computeAllocation(Money, IsPartialInvoiceFlag, Money, Percent)}.
 *
 * <p>All six tests are pure-function tests — no Spring context, no DB required.
 * Numbers are taken from the iter-3 REQUIREMENTS.md §3.3 examples.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class DeliveryPrepaymentAllocationServiceTest
{
	/** EUR-like currency — any non-zero consistent ID works in POJO test context. */
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final Percent LC_30 = Percent.of(30);

	private DeliveryPrepaymentAllocationService service;

	@BeforeEach
	void beforeEach()
	{
		service = new DeliveryPrepaymentAllocationService();
	}

	// -----------------------------------------------------------------------
	// PARTIAL flag tests
	// -----------------------------------------------------------------------

	/**
	 * PARTIAL invoice where proportional (receipt × LC%) < remainingPrepay.
	 * Expected: proportional is returned.
	 *
	 * <p>receipt=31 808 EUR, LC%=30 → proportional = 9 542.40
	 * remaining = 20 596.32 (> proportional) → return 9 542.40
	 */
	@Test
	void partial_underCap_allocatesProportional()
	{
		final Money receiptWithTax = money("31808.00");
		final Money remainingPrepay = money("20596.32");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.PARTIAL, remainingPrepay, LC_30);

		assertThat(result).isEqualTo(money("9542.40"));
	}

	/**
	 * PARTIAL invoice where proportional (receipt × LC%) > remainingPrepay.
	 * Expected: capped at remainingPrepay.
	 *
	 * <p>receipt=37 092 EUR, LC%=30 → proportional = 11 127.60
	 * remaining = 11 053.92 (< proportional) → return 11 053.92 (cap)
	 */
	@Test
	void partial_overCap_capsAtRemaining()
	{
		final Money receiptWithTax = money("37092.00");
		final Money remainingPrepay = money("11053.92");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.PARTIAL, remainingPrepay, LC_30);

		assertThat(result).isEqualTo(money("11053.92"));
	}

	// -----------------------------------------------------------------------
	// FINAL flag tests
	// -----------------------------------------------------------------------

	/**
	 * FINAL invoice where remainingPrepay > proportional.
	 * Expected: entire remainingPrepay is consumed (not just proportional).
	 *
	 * <p>receipt=35 272 EUR, LC%=30 → proportional would be 10 581.60
	 * remaining = 11 053.92 → return 11 053.92 (consumes all)
	 */
	@Test
	void final_consumesRemaining_evenIfMoreThanProportional()
	{
		final Money receiptWithTax = money("35272.00");
		final Money remainingPrepay = money("11053.92");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.FINAL, remainingPrepay, LC_30);

		assertThat(result).isEqualTo(money("11053.92"));
	}

	/**
	 * FINAL invoice where remainingPrepay < proportional.
	 * Expected: entire remainingPrepay is consumed (not the larger proportional).
	 *
	 * <p>receipt=30 000 EUR, LC%=30 → proportional would be 9 000
	 * remaining = 7 500 (< proportional) → return 7 500
	 */
	@Test
	void final_consumesRemaining_evenIfLessThanProportional()
	{
		final Money receiptWithTax = money("30000.00");
		final Money remainingPrepay = money("7500.00");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.FINAL, remainingPrepay, LC_30);

		assertThat(result).isEqualTo(money("7500.00"));
	}

	// -----------------------------------------------------------------------
	// Zero-remaining edge cases
	// -----------------------------------------------------------------------

	/**
	 * remainingPrepay = 0, PARTIAL flag → no allocation, no error.
	 */
	@Test
	void zeroRemaining_partial_noAllocation()
	{
		final Money receiptWithTax = money("31808.00");
		final Money remainingPrepay = money("0.00");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.PARTIAL, remainingPrepay, LC_30);

		assertThat(result.signum()).isZero();
	}

	/**
	 * remainingPrepay = 0, FINAL flag → no allocation, no error.
	 */
	@Test
	void zeroRemaining_final_noErrorNoAlloc()
	{
		final Money receiptWithTax = money("35272.00");
		final Money remainingPrepay = money("0.00");

		final Money result = service.computeAllocation(receiptWithTax, IsPartialInvoiceFlag.FINAL, remainingPrepay, LC_30);

		assertThat(result.signum()).isZero();
	}

	// -----------------------------------------------------------------------
	// Helper
	// -----------------------------------------------------------------------

	private static Money money(@lombok.NonNull final String amount)
	{
		return Money.of(amount, EUR);
	}
}
