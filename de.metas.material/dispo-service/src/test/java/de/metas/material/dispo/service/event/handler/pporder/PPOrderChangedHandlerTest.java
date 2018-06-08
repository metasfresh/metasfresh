package de.metas.material.dispo.service.event.handler.pporder;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PPOrderChangedHandlerTest
{

	@Mocked
	private CandidateChangeService candidateChangeService;

	@Mocked
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@Test
	public void handleEvent()
	{
		final MaterialDescriptor materialDescriptor = EventTestHelper.createMaterialDescriptor();

		// setup a candidate to be updated
		final Candidate candidatetoUpdate = Candidate.builder()
				.status(CandidateStatus.doc_closed)
				.type(CandidateType.DEMAND)
				.materialDescriptor(materialDescriptor)
				.businessCaseDetail(ProductionDetail.builder()
						.plannedQty(BigDecimal.TEN)
						.advised(Flag.FALSE)
						.pickDirectlyIfFeasible(Flag.FALSE)
						.build())
				.build();

		// @formatter:off
		new Expectations()
		{{
			candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo((CandidatesQuery)any);
			result = ImmutableList.of(candidatetoUpdate);
		}};	// @formatter:on

		final PPOrderChangedEvent ppOrderChangedEvent = PPOrderChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.oldDocStatus("CO")
				.newDocStatus("CO")
				.oldDatePromised(SystemTime.asTimestamp())
				.newDatePromised(SystemTime.asTimestamp())
				.newQtyDelivered(ONE)
				.newQtyRequired(TEN)
				.oldQtyDelivered(ONE)
				.oldQtyRequired(TEN)
				.productDescriptor(materialDescriptor)
				.ppOrderId(30)
				.build();

		final PPOrderChangedHandler ppOrderDocStatusChangedHandler = new PPOrderChangedHandler(
				candidateRepositoryRetrieval,
				candidateChangeService);

		//
		// invoke the method under test
		ppOrderDocStatusChangedHandler.handleEvent(ppOrderChangedEvent);

		//
		// verify the updated candidate created by the handler
		// @formatter:off
		new Verifications()
		{{
			Candidate updatedCandidate;
			candidateChangeService.onCandidateNewOrChange(updatedCandidate = withCapture());

			assertThat(updatedCandidate.getQuantity())
				.isEqualByComparingTo(BigDecimal.TEN);
			assertThat(updatedCandidate.getStatus()).isEqualTo(CandidateStatus.doc_completed);

			final ProductionDetail productionDetail = ProductionDetail.castOrNull(updatedCandidate.getBusinessCaseDetail());
			assertThat(productionDetail).isNotNull();
			assertThat(productionDetail.getPpOrderDocStatus()).isEqualTo("CO");
		}};	// @formatter:on
	}

}
