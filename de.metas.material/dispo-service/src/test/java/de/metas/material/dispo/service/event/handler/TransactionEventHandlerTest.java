package de.metas.material.dispo.service.event.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.CandidatesQuery;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.TransactionEvent;
import de.metas.material.event.TransactionEvent.TransactionEventBuilder;
import lombok.NonNull;
import mockit.Expectations;
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
	private static final int WAREHOUSE_ID = 50;

	private static final int TRANSACTION_ID = 60;

	private static final int SHIPMENT_SCHEDULE_ID = 40;

	private static final int PRODUCT_ID = 30;

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
	public void createCommonCandidateBuilder_negative_qantity()
	{
		final TransactionEvent event = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate()).build();

		final Candidate candidate = TransactionEventHandler.createCommonCandidateBuilder(event).build();

		assertThat(candidate.getType()).isSameAs(Type.UNRELATED_DECREASE);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCommonCandidateBuilder_positive_qantity()
	{
		final TransactionEvent event = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		final Candidate candidate = TransactionEventHandler.createCommonCandidateBuilder(event).build();

		assertThat(candidate.getType()).isSameAs(Type.UNRELATED_INCREASE);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCandidate_unrelated_transaction_no_existing_candiate()
	{
		final TransactionEvent unrelatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = null;
		}}; // @formatter:on
		
		final Candidate candidate = transactionEventHandler.createCandidate(unrelatedEvent);
		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
				new Verifications()
				{{
						CandidatesQuery query;
						candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
						assertThat(query).isNotNull();
						assertThat(query.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
				}}; // @formatter:on
		
		assertThat(candidate.getType()).isEqualTo(Type.UNRELATED_INCREASE);
		assertThat(candidate.getDemandDetail()).isNull();
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getTransactionDetail().getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCandidate_unrelated_transaction_already_existing_candiate()
	{
		final TransactionEvent unrelatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		final Candidate exisitingCandidate = Candidate.builder()
				.type(Type.UNRELATED_INCREASE)
				.id(11)
				.materialDescr(MaterialDescriptor.builderForCandidateOrQuery()
						.productId(PRODUCT_ID)
						.warehouseId(WAREHOUSE_ID)
						.quantity(new BigDecimal("63"))
						.date(SystemTime.asTimestamp())
						.build())
				.build();
		
		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = exisitingCandidate;
		}}; // @formatter:on
		
		final Candidate candidate = transactionEventHandler.createCandidate(unrelatedEvent);
		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
				new Verifications()
				{{
						CandidatesQuery query;
						candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
						assertThat(query).isNotNull();
						assertThat(query.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
				}}; // @formatter:on
		
		assertThat(candidate.getType()).isEqualTo(Type.UNRELATED_INCREASE);
		assertThat(candidate.getId()).isEqualTo(11);
		assertThat(candidate.getDemandDetail()).isNull();
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
		assertThat(candidate.getTransactionDetail().getQuantity()).isEqualByComparingTo("10");
	}
	
	@Test
	public void createCandidate_unrelated_transaction_with_shipmentSchedule()
	{
		final TransactionEvent relatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate())
				.shipmentScheduleId(SHIPMENT_SCHEDULE_ID).build();

		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = null;
		}}; // @formatter:on

		final Candidate candidate = transactionEventHandler.createCandidate(relatedEvent);
		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
		new Verifications()
		{{
				CandidatesQuery query;
				candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
				assertThat(query).isNotNull();
				assertThat(query.getDemandDetail().getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
				assertThat(query.getProductId()).isEqualTo(relatedEvent.getMaterialDescr().getProductId());
		}}; // @formatter:on

		assertThat(candidate.getType()).isEqualTo(Type.UNRELATED_DECREASE);
		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getDemandDetail()).as("created candidate shall have a demand detail").isNotNull();
		assertThat(candidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
		assertThat(candidate.getTransactionDetail().getQuantity()).isEqualByComparingTo("-10");
	}

	@Test
	public void createCandidate_related_transaction_with_shipmentSchedule()
	{
		final Candidate exisitingCandidate = Candidate.builder()
				.id(11)
				.type(Type.DEMAND)
				.materialDescr(MaterialDescriptor.builderForCandidateOrQuery()
						.productId(PRODUCT_ID)
						.warehouseId(WAREHOUSE_ID)
						.quantity(new BigDecimal("63"))
						.date(SystemTime.asTimestamp())
						.build())
				.demandDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(SHIPMENT_SCHEDULE_ID, -1))
				.build();

		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1;	result = exisitingCandidate;
		}}; // @formatter:on

		final TransactionEvent relatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate())
				.shipmentScheduleId(SHIPMENT_SCHEDULE_ID)
				.transactionId(TRANSACTION_ID)
				.build();

		final Candidate candidate = transactionEventHandler.createCandidate(relatedEvent);
		assertThat(candidate.getId()).isEqualTo(11);
		assertThat(candidate.getType()).isEqualTo(Type.DEMAND);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("63");
		makeCommonAssertions(candidate);

		assertThat(candidate.getDistributionDetail()).isNull();
		assertThat(candidate.getProductionDetail()).isNull();
		assertThat(candidate.getDemandDetail()).as("created candidate shall have a demand detail").isNotNull();
		assertThat(candidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
		assertThat(candidate.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
		assertThat(candidate.getTransactionDetail().getQuantity()).isEqualByComparingTo("-10");
	}

	private TransactionEventBuilder createTransactionEventBuilderWithQuantity(@NonNull final BigDecimal quantity)
	{
		return TransactionEvent.builder()
				.eventDescr(new EventDescr(10, 20))
				.transactionId(TRANSACTION_ID)
				.materialDescr(MaterialDescriptor.builder()
						.date(TimeUtil.parseTimestamp("2017-10-15"))
						.productId(PRODUCT_ID)
						.quantity(quantity)
						.warehouseId(WAREHOUSE_ID)
						.build());
	}

	private void makeCommonAssertions(final Candidate candidate)
	{
		assertThat(candidate).isNotNull();
		assertThat(candidate.getMaterialDescr()).isNotNull();
		assertThat(candidate.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(candidate.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(candidate.getTransactionDetail()).isNotNull();
	}
}
