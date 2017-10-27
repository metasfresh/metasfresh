package de.metas.material.dispo;

import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDemandDescriptor;

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
	public void createOrNull_when_empty_optional_then_null()
	{
		final Optional<MaterialDemandDescriptor> materialDemandDescr = Optional.empty();
		assertThat(DemandDetail.createOrNull(materialDemandDescr)).isNull();
	}

	@Test
	public void createOrNull_when_not_empty()
	{
		final MaterialDemandDescriptor descriptor = MaterialDemandDescriptor.builder()
				.eventDescr(new EventDescr(10, 20))
				.forecastLineId(1)
				.shipmentScheduleId(2)
				.orderLineId(3)
				.demandCandidateId(30)
				.materialDescriptor(createMaterialDescriptor())
				.build();

		final DemandDetail demandCandidateDetail = DemandDetail.createOrNull(Optional.of(descriptor));
		assertThat(demandCandidateDetail.getForecastLineId()).isEqualTo(1);
		assertThat(demandCandidateDetail.getShipmentScheduleId()).isEqualTo(2);
		assertThat(demandCandidateDetail.getOrderLineId()).isEqualTo(3);
	}
}
