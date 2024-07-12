package de.metas.material.planning.ddorder;

import de.metas.material.planning.ProductPlanning;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
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

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1635
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
			final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(3);
			assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine)).isZero();
		}

		{
			final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
			networkDistributionLine.setTransfertTime(new BigDecimal("4"));
			assertThat(DDOrderUtil.calculateDurationDays(null, networkDistributionLine)).isEqualTo(4);
		}

		// if the network distribution line has a transfer time, then the product planning value shall be ignored
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).build();
			final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
			networkDistributionLine.setTransfertTime(new BigDecimal("4"));
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(4);
		}

		// the product planning delivery time and the network distribution line's transfer time shall be added up
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).leadTimeDays(2).build();
			final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
			networkDistributionLine.setTransfertTime(new BigDecimal("4"));
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, networkDistributionLine)).isEqualTo(6);
		}

		// if the network distribution line is missing, then the product planning's transfer and promised time shall be added up
		{
			final ProductPlanning productPlanning = productPlanning().transferTimeDays(3).leadTimeDays(2).build();
			assertThat(DDOrderUtil.calculateDurationDays(productPlanning, null)).isEqualTo(5);
		}
	}
}
