package de.metas.material.dispo.service.event.handler;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent.TransactionCreatedEventBuilder;
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

public class TransactionCreatedHandlerTests
{
	private static final BigDecimal SIXTY_THREE = new BigDecimal("63");

	private static final BigDecimal SIXTY_FOUR = new BigDecimal("65");

	private static final int TRANSACTION_ID = 60;

	private static final int SHIPMENT_SCHEDULE_ID = 40;

	@Tested
	private TransactionEventHandler transactionEventHandler;

	@Injectable
	private CandidateChangeService candidateChangeService;

	@Injectable
	private CandidateRepositoryRetrieval candidateRepository;

	@Injectable
	private PostMaterialEventService postMaterialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createCommonCandidateBuilder_negative_qantity()
	{
		final TransactionCreatedEvent event = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate()).build();

		final Candidate candidate = TransactionEventHandler.createBuilderForNewUnrelatedCandidate(
				event,
				event.getQuantity()).build();

		assertThat(candidate.getType()).isSameAs(CandidateType.UNRELATED_DECREASE);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCommonCandidateBuilder_positive_qantity()
	{
		final TransactionCreatedEvent event = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		final Candidate candidate = TransactionEventHandler.createBuilderForNewUnrelatedCandidate(
				event,
				event.getQuantity())
				.build();

		assertThat(candidate.getType()).isSameAs(CandidateType.UNRELATED_INCREASE);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCandidate_unrelated_transaction_no_existing_candiate()
	{
		final TransactionCreatedEvent unrelatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		// @formatter:off
		new Expectations()
		{{
			candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = null;
		}}; // @formatter:on

		final List<Candidate> candidates = transactionEventHandler.createCandidatesForTransactionEvent(unrelatedEvent);
		assertThat(candidates).hasSize(1);
		final Candidate candidate = candidates.get(0);

		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
		new Verifications()
		{{
			CandidatesQuery query;
			candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
			assertThat(query).isNotNull();
			assertThat(query.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
		}}; // @formatter:on

		assertThat(candidate.getType()).isEqualTo(CandidateType.UNRELATED_INCREASE);
		assertThat(candidate.getAdditionalDemandDetail()).isNull();
		assertThat(candidate.getBusinessCaseDetail()).isNull();
		assertThat(candidate.getTransactionDetails().get(0).getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void createCandidate_unrelated_transaction_already_existing_candiate_with_different_transaction()
	{
		final TransactionCreatedEvent unrelatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN).build();

		final Candidate exisitingCandidate = Candidate.builder()
				.type(CandidateType.UNRELATED_INCREASE)
				.id(11)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.ONE)
						.date(SystemTime.asTimestamp())
						.build())
				.transactionDetail(TransactionDetail.forCandidateOrQuery(BigDecimal.ONE, TRANSACTION_ID + 1))
				.build();

		// @formatter:off
		new Expectations()
		{{
			candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = exisitingCandidate;
		}}; // @formatter:on

		final List<Candidate> candidates = transactionEventHandler.createCandidatesForTransactionEvent(unrelatedEvent);
		assertThat(candidates).hasSize(1);
		final Candidate candidate = candidates.get(0);

		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
		new Verifications()
		{{
			CandidatesQuery query;
			candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
			assertThat(query).isNotNull();
			assertThat(query.getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
		}}; // @formatter:on

		assertThat(candidate.getType()).isEqualTo(CandidateType.UNRELATED_INCREASE);
		assertThat(candidate.getId()).isEqualTo(11);
		assertThat(candidate.getQuantity()).isEqualByComparingTo("11");
		assertThat(candidate.getAdditionalDemandDetail()).isNull();
		assertThat(candidate.getBusinessCaseDetail()).isNull();
		assertThat(candidate.getTransactionDetails()).hasSize(2);

		assertThat(candidate.getTransactionDetails()).anySatisfy(transactionDetail -> {
			assertThat(transactionDetail.getTransactionId()).isEqualTo(TRANSACTION_ID);
			assertThat(transactionDetail.getQuantity()).isEqualByComparingTo("10");
		});

		assertThat(candidate.getTransactionDetails()).anySatisfy(transactionDetail -> {
			assertThat(transactionDetail.getTransactionId()).isEqualTo(TRANSACTION_ID + 1);
			assertThat(transactionDetail.getQuantity()).isEqualByComparingTo("1");
		});
	}

	@Test
	public void createCandidate_unrelated_transaction_with_shipmentSchedule()
	{
		final TransactionCreatedEvent relatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate())
				.shipmentScheduleIds2Qty(SHIPMENT_SCHEDULE_ID, BigDecimal.TEN.negate()).build();

		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1; result = null;
		}}; // @formatter:on

		final List<Candidate> candidates = transactionEventHandler.createCandidatesForTransactionEvent(relatedEvent);
		assertThat(candidates).hasSize(1);
		final Candidate candidate = candidates.get(0);

		makeCommonAssertions(candidate);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
		new Verifications()
		{{
				CandidatesQuery query;
				candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
				assertDemandDetailQuery(query);
		}}; // @formatter:on

		assertThat(candidate.getType()).isEqualTo(CandidateType.UNRELATED_DECREASE);
		final DemandDetail demandDetail = DemandDetail.castOrNull(candidate.getBusinessCaseDetail());
		assertThat(demandDetail).as("created candidate shall have a demand detail").isNotNull();
		assertThat(demandDetail.getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
		assertThat(candidate.getTransactionDetails()).hasSize(1);
		assertThat(candidate.getTransactionDetails().get(0).getQuantity()).isEqualByComparingTo("-10");
	}

	@Test
	public void createCandidate_related_transaction_with_shipmentSchedule()
	{
		final Candidate exisitingCandidate = Candidate.builder()
				.id(11)
				.type(CandidateType.DEMAND)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WAREHOUSE_ID)
						.quantity(SIXTY_THREE)
						.date(SystemTime.asTimestamp())
						.build())

				.businessCase(CandidateBusinessCase.SHIPMENT)
				.businessCaseDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(
						SHIPMENT_SCHEDULE_ID,
						-1,
						-1,
						SIXTY_FOUR))
				.build();

		exisitingCandidate.validate();

		// @formatter:off
		new Expectations()
		{{
				candidateRepository.retrieveLatestMatchOrNull((CandidatesQuery)any); times = 1;	result = exisitingCandidate;
		}}; // @formatter:on

		final TransactionCreatedEvent relatedEvent = createTransactionEventBuilderWithQuantity(BigDecimal.TEN.negate())
				.shipmentScheduleIds2Qty(SHIPMENT_SCHEDULE_ID, BigDecimal.TEN.negate())
				.transactionId(TRANSACTION_ID)
				.build();

		final List<Candidate> candidates = transactionEventHandler.createCandidatesForTransactionEvent(relatedEvent);
		assertThat(candidates).hasSize(1);
		final Candidate candidate = candidates.get(0);

		// @formatter:off verify that candidateRepository was called to decide if the event is related to anything we know
		new Verifications()
		{{
				CandidatesQuery query;
				candidateRepository.retrieveLatestMatchOrNull(query = withCapture());
				assertDemandDetailQuery(query);
		}}; // @formatter:on

		assertThat(candidate.getId()).isEqualTo(11);
		assertThat(candidate.getType()).isEqualTo(CandidateType.DEMAND);
		assertThat(candidate.getQuantity())
				.as("The demand candidate's quantity needs to be updated because there is now a transaction with a real qty that is bigger")
				.isEqualByComparingTo(SIXTY_FOUR);
		makeCommonAssertions(candidate);

		assertThat(candidate.getBusinessCaseDetail()).isNotNull();
		assertThat(candidate.getBusinessCaseDetail()).isInstanceOf(DemandDetail.class);
		assertThat(DemandDetail.cast(candidate.getBusinessCaseDetail()).getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
		assertThat(candidate.getTransactionDetails()).hasSize(1);
		assertThat(candidate.getTransactionDetails().get(0).getTransactionId()).isEqualTo(TRANSACTION_ID);
		assertThat(candidate.getTransactionDetails().get(0).getQuantity()).isEqualByComparingTo("-10");
	}

	private static void assertDemandDetailQuery(final CandidatesQuery query)
	{
		assertThat(query).isNotNull();
		assertThat(query.getDemandDetail().getShipmentScheduleId()).isEqualTo(SHIPMENT_SCHEDULE_ID);
		assertThat(query.getMaterialDescriptorQuery())
				.as("If we have a demand detail, then only query via that demand detail")
				.isNull();
		assertThat(query.getTransactionDetail()).as("only search via the demand detail, if we have one").isNull();
	}

	private TransactionCreatedEventBuilder createTransactionEventBuilderWithQuantity(@NonNull final BigDecimal quantity)
	{
		return TransactionCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.transactionId(TRANSACTION_ID)
				.materialDescriptor(MaterialDescriptor.builder()
						.date(TimeUtil.parseTimestamp("2017-10-15"))
						.productDescriptor(createProductDescriptor())
						.quantity(quantity)
						.warehouseId(WAREHOUSE_ID)
						.build());
	}

	private void makeCommonAssertions(final Candidate candidate)
	{
		assertThat(candidate).isNotNull();
		assertThat(candidate.getMaterialDescriptor()).isNotNull();
		assertThat(candidate.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(candidate.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(candidate.getTransactionDetails()).isNotEmpty();
	}
}
