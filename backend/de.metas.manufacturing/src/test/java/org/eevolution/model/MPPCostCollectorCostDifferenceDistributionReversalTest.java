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
 * Covers {@link MPPCostCollector#assertReverseCorrectSupported(CostCollectorType)}: a
 * {@code CostDifferenceDistribution} collector must be refused, because the mirrored collector would repost the
 * same non-negated split and double the GL; every other type stays reversible.
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
