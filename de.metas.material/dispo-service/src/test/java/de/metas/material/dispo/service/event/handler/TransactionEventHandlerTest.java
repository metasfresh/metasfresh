package de.metas.material.dispo.service.event.handler;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.TransactionEvent;
import de.metas.material.event.TransactionEvent.TransactionEventBuilder;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;

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

public class TransactionEventHandlerTest
{
	@Tested
	private TransactionEventHandler transactionEventHandler;

	@Injectable
	private CandidateChangeService candidateChangeService;

	@Injectable
	private CandidateRepository candidateRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createCandidate_unrelated_transaction()
	{
		final TransactionEvent unrelatedEvent = createTransactionEventBuilder().build();

		final Candidate candidate = transactionEventHandler.createCandidate(unrelatedEvent);
		makeCommonAssertions(candidate);

		assertThat(candidate.getType()).isEqualTo(Type.UNRELATED_TRANSACTION);
		assertThat(candidate.getDemandDetail()).isNull();
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
	}

	@Test
	public void createCandidate_unrelated_transaction_with_shipmentSchedule()
	{
		final TransactionEvent relatedEvent = createTransactionEventBuilder().shipmentScheduleId(40).build();

		final Candidate candidate = transactionEventHandler.createCandidate(relatedEvent);
		makeCommonAssertions(candidate);

		new Verifications()
		{{
				CandidatesQuery segment;
				candidateRepository.retrieveMatchesOrderByDateAndSeqNo(segment = withCapture());
				assertThat(segment).isNotNull();
				assertThat(segment.getProductId()).isEqualTo(relatedEvent.getMaterialDescr().getProductId());
		}};

		assertThat(candidate.getType()).isEqualTo(Type.UNRELATED_TRANSACTION);
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getDemandDetail()).isNotNull();
		assertThat(candidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(40);
	}

	@Test
	public void createCandidate_related_transaction_with_shipmentSchedule()
	{
		final I_MD_Candidate demandCandidateRecord = newInstance(I_MD_Candidate.class);
		save(demandCandidateRecord);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(demandCandidateRecord);
		demandDetailRecord.setM_ShipmentSchedule_ID(40);
		save(demandDetailRecord);

		final TransactionEvent relatedEvent = createTransactionEventBuilder().shipmentScheduleId(40).build();

		final Candidate candidate = transactionEventHandler.createCandidate(relatedEvent);
		makeCommonAssertions(candidate);

		assertThat(candidate.getType()).isEqualTo(Type.DEMAND);
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getDemandDetail()).isNotNull();
		assertThat(candidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(40);
	}

	private TransactionEventBuilder createTransactionEventBuilder()
	{
		return TransactionEvent.builder()
				.eventDescr(new EventDescr(10, 20))
				.materialDescr(MaterialDescriptor.builder()
						.date(TimeUtil.parseTimestamp("2017-10-15"))
						.productId(40)
						.quantity(BigDecimal.TEN)
						.warehouseId(50)
						.build());

	}

	private void makeCommonAssertions(final Candidate candidateParameter)
	{
		assertThat(candidateParameter).isNotNull();
		assertThat(candidateParameter.getMaterialDescr()).isNotNull();
		assertThat(candidateParameter.getQuantity()).isEqualByComparingTo("10");
		assertThat(candidateParameter.getProductId()).isEqualByComparingTo(40);
		assertThat(candidateParameter.getWarehouseId()).isEqualByComparingTo(50);
	}
}
