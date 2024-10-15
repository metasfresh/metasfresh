package de.metas.material.planning.ddorder;

import de.metas.material.planning.ProductPlanning;
import de.metas.shipping.ShipperId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DDOrderUtilTests
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private ProductPlanning.ProductPlanningBuilder productPlanning()
	{
		return ProductPlanning.builder();
	}

	private DistributionNetworkLine.DistributionNetworkLineBuilder distributionNetworkLine()
	{
		return DistributionNetworkLine.builder()
				.id(DistributionNetworkLineId.ofRepoId(1))
				.sourceWarehouseId(WarehouseId.ofRepoId(2))
				.targetWarehouseId(WarehouseId.ofRepoId(3))
				.shipperId(ShipperId.ofRepoId(4))
				//.transferDuration()
				;
	}

	/**
	 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/1635">task</a>
	 */
	@Test
	public void testCalculateDurationDays()
	{
		assertThat(DDOrderUtil.calculateDurationDays(null, null)).isZero();

		{
			final ProductPlanning productPlanning = productPlanning().build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null)).isZero();
		}

		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null)).isEqualTo(3);
		}

		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).build();
			final DistributionNetworkLine networkDistributionLine = distributionNetworkLine().transferDuration(Duration.ZERO).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(3);
			assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine)).isZero();
		}

		{
			final DistributionNetworkLine networkDistributionLine = distributionNetworkLine().transferDuration(Duration.ofDays(4)).build();
			assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine)).isEqualTo(4);
		}

		// if the network distribution line has a transfer time, then the product planning value shall be ignored
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).build();
			final DistributionNetworkLine networkDistributionLine = distributionNetworkLine().transferDuration(Duration.ofDays(4)).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(4);
		}

		// the product planning delivery time and the network distribution line's transfer time shall be added up
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).leadTimeDays(2).build();
			final DistributionNetworkLine networkDistributionLine = distributionNetworkLine().transferDuration(Duration.ofDays(4)).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(6);
		}

		// if the network distribution line is missing, then the product planning's transfer and promised time shall be added up
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).leadTimeDays(2).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null)).isEqualTo(5);
		}
	}
}
