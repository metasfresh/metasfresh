package de.metas.material.dispo.service.candidatechange.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.material.dispo.CandidateSpecification.Status;
import de.metas.material.dispo.CandidateSpecification.SubType;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class MaterialDemandEventCreatorTest
{

	@Test
	public void createMaterialDemandEvent()
	{
		final Candidate demandCandidate = Candidate.builderForEventDescr(new EventDescr(20, 30))
				.id(10)
				.type(Type.DEMAND)
				.subType(SubType.PRODUCTION)
				.status(Status.doc_closed)
				.groupId(40)
				.seqNo(50)
				.materialDescr(MaterialDescriptor.builder()
						.productId(60)
						.date(SystemTime.asTimestamp())
						.quantity(BigDecimal.TEN)
						.warehouseId(70)
						.build())
				.build();
		final MaterialDemandEvent result = MaterialDemandEventCreator.createMaterialDemandEvent(demandCandidate, BigDecimal.TEN);
		assertThat(result).isNotNull();
		assertThat(result.getEventDescr().getClientId()).isEqualTo(20);
		assertThat(result.getEventDescr().getOrgId()).isEqualTo(30);
		assertThat(result.getMaterialDemandDescr().getDemandCandidateId()).isEqualTo(10);
	}

}
