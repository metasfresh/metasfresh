/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.distributionorder;

import org.adempiere.warehouse.LocatorId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link DD_Order_StepDef#isPerLocatorReconcileSettled(Set, Map, Set, Map)} — the readiness gate of
 * the "per-locator DD_Orders linked to picking job schedule are found" assertion step.
 * <p>
 * When a demand change keeps the SAME contributing locators and only changes the quantity, the source-locator set is
 * unchanged, so a set-only readiness check reports "ready" immediately against the stale pre-change DD_Orders and
 * races the asynchronous reconcile — the assertion then intermittently reads the pre-change {@code QtyEntered}.
 * {@link DD_Order_StepDef#isPerLocatorReconcileSettled} gates on the per-locator quantity too, so it only reports
 * ready once the reconcile has settled the line quantity.
 */
class DD_Order_StepDef_ReconcileSettledTest
{
	private static final LocatorId locatorA = LocatorId.ofRepoId(100, 10);
	private static final LocatorId locatorB = LocatorId.ofRepoId(100, 20);

	private static BigDecimal bd(final String v)
	{
		return new BigDecimal(v);
	}

	private static Set<LocatorId> set(final LocatorId... ids)
	{
		return new LinkedHashSet<>(Arrays.asList(ids));
	}

	/**
	 * The defect this fix targets: same contributing locators, only the quantity lowered (15→12 ⇒ locatorB 5→2).
	 * The live DD_Orders still show the stale pre-change quantity (locatorB=5) while the async reconcile is in
	 * flight. A set-only check would pass here (set {A,B} matches) and let the hard assertion read the stale 5 —
	 * must NOT be ready until locatorB settles to 2.
	 */
	@Test
	void staleQuantity_sameLocatorSet_isNotReady()
	{
		final Map<LocatorId, BigDecimal> expectedQty = new LinkedHashMap<>();
		expectedQty.put(locatorA, bd("10"));
		expectedQty.put(locatorB, bd("2"));

		final Map<LocatorId, BigDecimal> liveQty = new LinkedHashMap<>();
		liveQty.put(locatorA, bd("10"));
		liveQty.put(locatorB, bd("5")); // stale: reconcile not yet applied

		assertThat(DD_Order_StepDef.isPerLocatorReconcileSettled(
				set(locatorA, locatorB), expectedQty, liveQty.keySet(), liveQty))
				.as("stale per-locator quantity (same locator set) must NOT be reported as settled")
				.isFalse();
	}

	/** Once the reconcile has settled the quantity (locatorB=2), the gate reports ready. */
	@Test
	void settledQuantity_sameLocatorSet_isReady()
	{
		final Map<LocatorId, BigDecimal> expectedQty = new LinkedHashMap<>();
		expectedQty.put(locatorA, bd("10"));
		expectedQty.put(locatorB, bd("2"));

		final Map<LocatorId, BigDecimal> liveQty = new LinkedHashMap<>();
		liveQty.put(locatorA, bd("10"));
		liveQty.put(locatorB, bd("2"));

		assertThat(DD_Order_StepDef.isPerLocatorReconcileSettled(
				set(locatorA, locatorB), expectedQty, liveQty.keySet(), liveQty))
				.as("settled per-locator quantity must be reported as settled")
				.isTrue();
	}

	/** Quantity comparison ignores scale (2 vs 2.00), matching the hard assertion's isEqualByComparingTo. */
	@Test
	void settledQuantity_differentScale_isReady()
	{
		final Map<LocatorId, BigDecimal> expectedQty = new LinkedHashMap<>();
		expectedQty.put(locatorB, bd("2"));
		final Map<LocatorId, BigDecimal> liveQty = new LinkedHashMap<>();
		liveQty.put(locatorB, bd("2.000"));

		assertThat(DD_Order_StepDef.isPerLocatorReconcileSettled(
				set(locatorB), expectedQty, liveQty.keySet(), liveQty))
				.isTrue();
	}

	/** A locator that drops out (void) / joins (create) changes the SET — the gate waits for the set to match. */
	@Test
	void locatorSetMismatch_isNotReady()
	{
		final Map<LocatorId, BigDecimal> expectedQty = new LinkedHashMap<>();
		expectedQty.put(locatorA, bd("10"));
		final Map<LocatorId, BigDecimal> liveQty = new LinkedHashMap<>();
		liveQty.put(locatorA, bd("10"));
		liveQty.put(locatorB, bd("5")); // locatorB still present live, but not expected

		assertThat(DD_Order_StepDef.isPerLocatorReconcileSettled(
				set(locatorA), expectedQty, liveQty.keySet(), liveQty))
				.as("a live locator set that differs from expected must NOT be reported as settled")
				.isFalse();
	}

	/** No QtyEntered expectations (optional column omitted) ⇒ the gate is set-only, preserving prior behaviour. */
	@Test
	void noQuantityExpectations_gatesOnSetOnly()
	{
		final Map<LocatorId, BigDecimal> noQty = new LinkedHashMap<>();
		final Map<LocatorId, BigDecimal> liveQty = new LinkedHashMap<>();
		liveQty.put(locatorA, bd("10"));
		liveQty.put(locatorB, bd("5"));

		assertThat(DD_Order_StepDef.isPerLocatorReconcileSettled(
				set(locatorA, locatorB), noQty, liveQty.keySet(), liveQty))
				.as("with no quantity expectations, a matching locator set is settled")
				.isTrue();
	}
}
