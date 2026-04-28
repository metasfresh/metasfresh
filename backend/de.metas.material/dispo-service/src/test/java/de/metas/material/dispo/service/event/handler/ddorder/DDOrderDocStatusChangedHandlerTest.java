package de.metas.material.dispo.service.event.handler.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DDOrderDocStatusChangedHandlerTest
{
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private CandidateChangeService candidateChangeService;
	private DDOrderCreatedHandler ddOrderCreatedHandler;
	private DDOrderDocStatusChangedHandler handler;

	@BeforeEach
	void beforeEach()
	{
		candidateRepositoryRetrieval = mock(CandidateRepositoryRetrieval.class);
		candidateChangeService = mock(CandidateChangeService.class);
		ddOrderCreatedHandler = mock(DDOrderCreatedHandler.class);

		handler = new DDOrderDocStatusChangedHandler(
				candidateRepositoryRetrieval,
				candidateChangeService,
				ddOrderCreatedHandler);
	}

	@Test
	void noCandidates_withDDOrder_delegatesToCreatedHandler()
	{
		// Given: no existing candidates for this DD_Order
		mockNoCandidatesForDDOrderId(10);

		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ddOrderId(10)
				.newDocStatus(DocStatus.Completed)
				.ddOrder(createDDOrder())
				.build();

		// When
		handler.handleEvent(event);

		// Then: DDOrderCreatedHandler was called with a synthetic DDOrderCreatedEvent
		final ArgumentCaptor<DDOrderCreatedEvent> captor = ArgumentCaptor.forClass(DDOrderCreatedEvent.class);
		verify(ddOrderCreatedHandler).handleEvent(captor.capture());
		final DDOrderCreatedEvent createdEvent = captor.getValue();
		assertThat(createdEvent.getDdOrder()).isNotNull();
		assertThat(createdEvent.getDdOrder().getDdOrderId()).isEqualTo(20);
		assertThat(createdEvent.getEventDescriptor()).isEqualTo(event.getEventDescriptor());

		// And: CandidateChangeService was NOT called directly (DDOrderCreatedHandler handles that)
		verify(candidateChangeService, never()).onCandidateNewOrChange(any());
	}

	@Test
	void noCandidates_withoutDDOrder_logsWarningAndSkips()
	{
		// Given: no existing candidates and no DDOrder in event (e.g., old event format or loader failure)
		mockNoCandidatesForDDOrderId(10);

		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ddOrderId(10)
				.newDocStatus(DocStatus.Completed)
				.build();

		// When
		handler.handleEvent(event);

		// Then: neither DDOrderCreatedHandler nor CandidateChangeService was called
		verify(ddOrderCreatedHandler, never()).handleEvent(any());
		verify(candidateChangeService, never()).onCandidateNewOrChange(any());
	}

	@Test
	void existingCandidates_updatesDocStatusAndQuantity()
	{
		// Given: existing candidate for this DD_Order
		final DistributionDetail distributionDetail = DistributionDetail.builder()
				.ddOrderRef(DDOrderRef.builder().ddOrderId(10).ddOrderLineId(11).build())
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIds(80, 85))
				.ddOrderDocStatus(DocStatus.InProgress)
				.qty(BigDecimal.TEN)
				.build();

		final Candidate existingCandidate = Candidate.builder()
				.id(CandidateId.ofRepoId(100))
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(createProductDescriptor())
						.warehouseId(WarehouseId.ofRepoId(30))
						.quantity(BigDecimal.TEN)
						.date(NOW)
						.build())
				.businessCaseDetail(distributionDetail)
				.build();

		mockCandidatesForDDOrderId(10, ImmutableList.of(existingCandidate));

		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ddOrderId(10)
				.newDocStatus(DocStatus.Completed)
				.build();

		// When
		handler.handleEvent(event);

		// Then: CandidateChangeService was called to update the candidate
		final ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);
		verify(candidateChangeService).onCandidateNewOrChange(captor.capture());
		final Candidate updatedCandidate = captor.getValue();
		final DistributionDetail updatedDetail = DistributionDetail.cast(updatedCandidate.getBusinessCaseDetail());
		assertThat(updatedDetail.getDdOrderDocStatus()).isEqualTo(DocStatus.Completed);

		// And: DDOrderCreatedHandler was NOT called (existing candidates path)
		verify(ddOrderCreatedHandler, never()).handleEvent(any());
	}

	private void mockNoCandidatesForDDOrderId(final int ddOrderId)
	{
		mockCandidatesForDDOrderId(ddOrderId, Collections.emptyList());
	}

	private void mockCandidatesForDDOrderId(final int ddOrderId, final List<Candidate> candidates)
	{
		// DDOrderUtil.retrieveCandidatesForDDOrderId uses candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo
		when(candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(any()))
				.thenReturn(candidates);
	}

	private static DDOrder createDDOrder()
	{
		return DDOrder.builder()
				.ddOrderId(20)
				.docStatus(DocStatus.Completed)
				.supplyDate(NOW)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 2))
				.sourceWarehouseId(WarehouseId.ofRepoId(10))
				.targetWarehouseId(WarehouseId.ofRepoId(30))
				.plantId(ResourceId.ofRepoId(50))
				.productPlanningId(ProductPlanningId.ofRepoId(60))
				.shipperId(ShipperId.ofRepoId(70))
				.materialDispoGroupId(MaterialDispoGroupId.ofInt(35))
				.line(DDOrderLine.builder()
						.ddOrderLineId(21)
						.productDescriptor(createProductDescriptor())
						.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIds(80, 85))
						.demandDate(NOW.minusSeconds(86400))
						.qtyToMove(BigDecimal.TEN)
						.qtyMoved(BigDecimal.ZERO)
						.build())
				.build();
	}
}
