package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;

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
	public void createOrNull_when_null_then_null()
	{
		assertThat(DemandDetail.createOrNull(null)).isNull();
	}

	@Test
	public void createOrNull_when_not_empty()
	{
		final SupplyRequiredDescriptor descriptor = SupplyRequiredDescriptor.builder()
				.eventDescriptor(new EventDescriptor(10, 20))
				.forecastLineId(1)
				.shipmentScheduleId(2)
				.orderLineId(3)
				.demandCandidateId(30)
				.materialDescriptor(createMaterialDescriptor())
				.build();

		final DemandDetail demandCandidateDetail = DemandDetail.createOrNull(descriptor);
		assertThat(demandCandidateDetail.getForecastLineId()).isEqualTo(1);
		assertThat(demandCandidateDetail.getShipmentScheduleId()).isEqualTo(2);
		assertThat(demandCandidateDetail.getOrderLineId()).isEqualTo(3);
	}
}
