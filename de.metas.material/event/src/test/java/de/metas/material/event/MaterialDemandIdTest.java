package de.metas.material.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * metasfresh-material-event
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MaterialDemandIdTest
{

	@Test
	public void forForecastLineId()
	{
		final MaterialDemandId result = MaterialDemandId.forForecastLineId(30);
		assertThat(result.getForecastLineId()).isEqualTo(30);
		assertThat(result.getShipmentScheduleId()).isLessThanOrEqualTo(0);
	}
	@Test
	
	public void forShipmentScheduleId()
	{
		final MaterialDemandId result = MaterialDemandId.forShipmentScheduleId(20);
		assertThat(result.getForecastLineId()).isLessThanOrEqualTo(0);
		assertThat(result.getShipmentScheduleId()).isEqualTo(20);
	}
}
