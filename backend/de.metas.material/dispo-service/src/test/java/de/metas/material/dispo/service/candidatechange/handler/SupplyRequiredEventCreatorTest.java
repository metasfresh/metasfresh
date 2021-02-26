package de.metas.material.dispo.service.candidatechange.handler;

import de.metas.common.util.time.SystemTime;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

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
				.id(CandidateId.ofRepoId(10))
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.businessCaseDetail(ProductionDetail.builder()
						.advised(Flag.FALSE)
						.pickDirectlyIfFeasible(Flag.FALSE)
						.qty(new BigDecimal("10")).build())
				.groupId(MaterialDispoGroupId.ofInt(40))
				.seqNo(50)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.date(SystemTime.asInstant())
						.quantity(BigDecimal.TEN)
						.warehouseId(WAREHOUSE_ID)
						.build())
				.build();

		final SupplyRequiredEvent result = SupplyRequiredEventCreator.createSupplyRequiredEvent(
				demandCandidate, BigDecimal.TEN, CandidateId.ofRepoId(60));

		assertThat(result).isNotNull();
		assertThat(result.getEventDescriptor().getClientId().getRepoId()).isEqualTo(20);
		assertThat(result.getEventDescriptor().getOrgId().getRepoId()).isEqualTo(30);
		assertThat(result.getSupplyRequiredDescriptor().getDemandCandidateId()).isEqualTo(10);
		assertThat(result.getSupplyRequiredDescriptor().getSupplyCandidateId()).isEqualTo(60);
	}
}
