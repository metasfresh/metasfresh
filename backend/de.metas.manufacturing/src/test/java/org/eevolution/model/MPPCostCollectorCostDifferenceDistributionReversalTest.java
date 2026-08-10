/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.model;

import de.metas.material.planning.pporder.LiberoException;
import org.eevolution.api.CostCollectorType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Guards {@link MPPCostCollector#assertReverseCorrectSupported(CostCollectorType)} — the type check
 * {@code reverseCorrectIt()} performs at its very first statement, before it builds/completes any mirrored
 * collector.
 * <p>
 * Reversing a {@code CostDifferenceDistribution} collector must be refused: {@code reverseCorrectIt()} would
 * complete a mirrored collector that re-runs {@code Doc_PPCostCollector.createFacts_CostDifferenceDistribution},
 * recomputing the identical non-negated split (posting never checks for a reversal) and DOUBLING the GL. Every
 * OTHER cost-collector type must still be reversible.
 * <p>
 * The guard is intentionally keyed on the {@link CostCollectorType} at the {@code reverseCorrectIt} entry point,
 * NOT on {@code isReversal()} inside {@code completeIt()}: the reversal is completed on the mirrored document,
 * whose {@code isReversal()} is false (its {@code Reversal_ID} points to the lower-id original), so a
 * {@code completeIt}-time check would never fire.
 * <p>
 * NOTE: this tests the extracted decision, not an end-to-end {@code reverseCorrectIt()} call — constructing an
 * {@code MPPCostCollector} (a {@code PO}) needs {@code POInfo} / a DB connection, unavailable in the plain unit
 * layer (no manufacturing test constructs a {@code PO} subclass for this reason). The wiring is a single
 * unconditional call at the top of {@code reverseCorrectIt()}, verifiable by reading.
 */
class MPPCostCollectorCostDifferenceDistributionReversalTest
{
	@Test
	void costDifferenceDistribution_cannotBeReversed()
	{
		assertThatThrownBy(() -> MPPCostCollector.assertReverseCorrectSupported(CostCollectorType.CostDifferenceDistribution))
				.isInstanceOf(LiberoException.class)
				.hasMessageContaining("Reversing a Cost Difference Distribution is not supported");
	}

	@Test
	void otherCostCollectorTypes_remainReversible()
	{
		for (final CostCollectorType type : CostCollectorType.values())
		{
			if (type.isCostDifferenceDistribution())
			{
				continue;
			}
			assertThatCode(() -> MPPCostCollector.assertReverseCorrectSupported(type))
					.as("reverseCorrectIt must stay supported for %s", type)
					.doesNotThrowAnyException();
		}
	}
}
