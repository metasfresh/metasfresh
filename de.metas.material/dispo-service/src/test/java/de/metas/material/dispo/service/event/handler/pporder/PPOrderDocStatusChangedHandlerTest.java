package de.metas.material.dispo.service.event.handler.pporder;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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
import de.metas.material.event.pporder.PPOrderDocStatusChangedEvent;
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

public class PPOrderDocStatusChangedHandlerTest
{

	@Mocked
	private CandidateChangeService candidateChangeService;

	@Mocked
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@Test
	public void handleEvent()
	{
		//
		// setup a candidate to be updated
		final Candidate candidatetoUpdate = Candidate.builder()
				.status(CandidateStatus.doc_closed)
				.type(CandidateType.DEMAND)
				.materialDescriptor(EventTestHelper.createMaterialDescriptor())
				.productionDetail(ProductionDetail.builder()
						.actualQty(BigDecimal.ONE)
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

		final PPOrderDocStatusChangedEvent ppOrderChangedDocStatusEvent = PPOrderDocStatusChangedEvent.builder()
				.eventDescriptor(new EventDescriptor(10, 20))
				.newDocStatus("CO")
				.ppOrderId(30).build();

		final PPOrderDocStatusChangedHandler ppOrderDocStatusChangedHandler = new PPOrderDocStatusChangedHandler(
				candidateRepositoryRetrieval,
				candidateChangeService);

		//
		// invoke the method under test
		ppOrderDocStatusChangedHandler.handleEvent(ppOrderChangedDocStatusEvent);

		//
		// verify the updated candidate created by the handler
		// @formatter:off
		new Verifications()
		{{
			Candidate updatedCandidate;
			candidateChangeService.onCandidateNewOrChange(updatedCandidate = withCapture());

			assertThat(updatedCandidate.getQuantity())
				.as("if docstatus is not 'closed' the qty needs to be the max of planned and actual")
				.isEqualByComparingTo(BigDecimal.TEN);
			assertThat(updatedCandidate.getStatus()).isEqualTo(CandidateStatus.doc_completed);

			final ProductionDetail productionDetail = updatedCandidate.getProductionDetail();
			assertThat(productionDetail).isNotNull();
			assertThat(updatedCandidate.getProductionDetail().getPpOrderDocStatus()).isEqualTo("CO");
		}};	// @formatter:on
	}

}
