package de.metas.material.dispo.service.candidatechange.handler;

import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;

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

public class SupplyRequiredEventCreatorTest
{
	@Test
	public void createMaterialDemandEvent()
	{
		final Candidate demandCandidate = Candidate.builderForEventDescr(EventDescriptor.ofClientAndOrg(20, 30))
				.id(10)
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.status(CandidateStatus.doc_closed)
				.groupId(40)
				.seqNo(50)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asTimestamp())
						.quantity(BigDecimal.TEN)
						.warehouseId(WAREHOUSE_ID)
						.build())
				.build();

		final SupplyRequiredEvent result = SupplyRequiredEventCreator.createSupplyRequiredEvent(demandCandidate, BigDecimal.TEN);
		assertThat(result).isNotNull();
		assertThat(result.getEventDescriptor().getClientId()).isEqualTo(20);
		assertThat(result.getEventDescriptor().getOrgId()).isEqualTo(30);
		assertThat(result.getSupplyRequiredDescriptor().getDemandCandidateId()).isEqualTo(10);
	}
}
