package de.metas.material.dispo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class DemandCandidateDetailTests
{
	@Test
	public void testForOrderLine()
	{
		DemandCandidateDetail result;

		result = DemandCandidateDetail.forOrderLineId(0);
		assertThat(result.getOrderLineId()).isZero();
		assertThat(result.getForecastLineId()).isLessThanOrEqualTo(0);

		result = DemandCandidateDetail.forOrderLineId(23);
		assertThat(result.getOrderLineId()).isEqualTo(23);
		assertThat(result.getForecastLineId()).isLessThanOrEqualTo(0);

	}

	@Test
	public void testForForecastLine()
	{
		DemandCandidateDetail result;

		result = DemandCandidateDetail.forForecastLineId(0);
		assertThat(result.getForecastLineId()).isZero();
		assertThat(result.getOrderLineId()).isLessThanOrEqualTo(0);

		result = DemandCandidateDetail.forForecastLineId(23);
		assertThat(result.getForecastLineId()).isEqualTo(23);
		assertThat(result.getOrderLineId()).isLessThanOrEqualTo(0);
	}
}
