package de.metas.material.dispo.service.event;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.dispo.service.event.handler.DDOrderAdvisedHandlerTests;
import de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedHandler;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.order.OrderLineRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo
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

@ExtendWith(AdempiereTestWatcher.class)
public class SupplyProposalEvaluatorTests
{
	private final Instant t1 = SystemTime.asInstant();
	private final Instant t2 = t1.plus(10, ChronoUnit.MINUTES);
	private final Instant t3 = t1.plus(20, ChronoUnit.MINUTES);

	private static final WarehouseId SUPPLY_WAREHOUSE_ID = WarehouseId.ofRepoId(4);
	private static final WarehouseId DEMAND_WAREHOUSE_ID = WarehouseId.ofRepoId(6);

	private DDOrderAdvisedHandler ddOrderAdvisedHandler;

	/**
	 * This is the code under test
	 */

	private SupplyProposalEvaluator supplyProposalEvaluator;

	private CandidateRepositoryWriteService candidateRepositoryCommands;

	private AvailableToPromiseRepository availableToPromiseRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		candidateRepositoryCommands = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepositoryRetrieval);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		availableToPromiseRepository = new AvailableToPromiseRepository();

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryCommands, stockCandidateService);

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				supplyCandidateHandler,
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						postMaterialEventService,
						availableToPromiseRepository,
						stockCandidateService,
						supplyCandidateHandler)));

		ddOrderAdvisedHandler = new DDOrderAdvisedHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new RequestMaterialOrderService(candidateRepositoryRetrieval, postMaterialEventService, new OrderLineRepository()),
				new DDOrderDetailRequestHandler(),
				new MainDataRequestHandler());
	}

	/**
	 * If there was nothing persisted yet, the testee shall return <code>true</code>.
	 */
	@Test
	public void testEmpty()
	{
		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.demandWarehouseId(DEMAND_WAREHOUSE_ID)
				.supplyWarehouseId(SUPPLY_WAREHOUSE_ID)
				.demandDetail(createDemandDetail())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}

	@Test
	public void testWithSameDirectionData()
	{
		addSimpleSupplyDemand();

		// At this point we have in our plan something like DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID in the repo.

		// now let's propose again to do DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID,
		// with and equal same demand detail; that might be part of a loop and generally makes no sense
		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.demandWarehouseId(DEMAND_WAREHOUSE_ID)
				.supplyWarehouseId(SUPPLY_WAREHOUSE_ID)
				.demandDetail(createDemandDetail())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isFalse();
	}

	@Test
	public void testWithOppositeDirectionData()
	{
		addSimpleSupplyDemand();

		// At this point we have in our plan something like DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID in the repo.

		// To balance the demand in DEMAND_WAREHOUSE_ID that we just created, let's propose something that amounts to SUPPLY_WAREHOUSE_ID => DEMAND_WAREHOUSE_ID.
		// It doesn't make sense to play ping-pong like that, but this step is not yet a repetition of a previous step, so it's allowed.
		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.demandWarehouseId(SUPPLY_WAREHOUSE_ID)
				.supplyWarehouseId(DEMAND_WAREHOUSE_ID)
				.demandDetail(createDemandDetail())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}

	/**
	 * Creates a distribution-like supply-demand pair. The supply occurs at {@link #t3}, the corresponding demand (in a different warehouse ofc!) at {@link #t2}.
	 */
	private void addSimpleSupplyDemand()
	{
		final MaterialDescriptor supplyMaterialDescriptor = MaterialDescriptor.builder()
				.date(t3)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.TEN)
				.warehouseId(SUPPLY_WAREHOUSE_ID)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.additionalDemandDetail(createDemandDetail())
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.businessCaseDetail(DistributionDetail.builder().qty(ONE).build()) // just a dummy detail that currently doesn't play a role in the actual test
				.materialDescriptor(supplyMaterialDescriptor)
				.build();

		final Candidate supplyCandidateWithId = candidateRepositoryCommands
				.addOrUpdateOverwriteStoredSeqNo(supplyCandidate)
				.getCandidate();

		final MaterialDescriptor demandDescr = MaterialDescriptor.builder()
				.date(t2)
				.productDescriptor(createProductDescriptor())
				.warehouseId(DEMAND_WAREHOUSE_ID)
				.quantity(BigDecimal.TEN)
				.build();

		final Candidate demandCandidate = Candidate.builder()
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.parentId(supplyCandidateWithId.getId())
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.businessCaseDetail(DistributionDetail.builder().qty(ONE).build()) // just a dummy detail that currently doesn't play a role in the actual test
				.additionalDemandDetail(createDemandDetail())
				.materialDescriptor(demandDescr)
				.build();

		candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(demandCandidate);
	}

	/**
	 * Create a chain A to B to C. Then verify that it's NOT ok to add a proposal of A to B and B to C nor A to C.
	 */
	@Test
	public void testWithChain()
	{
		DDOrderAdvisedHandlerTests.perform_twoAdvisedEvents(ddOrderAdvisedHandler);

		// propose what would create an additional demand on A and an additional supply on B.
		// shall be rejected because its repeats something we already did.
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.demandWarehouseId(MaterialEventHandlerRegistryTests.fromWarehouseId)
				.supplyWarehouseId(MaterialEventHandlerRegistryTests.intermediateWarehouseId)
				.demandDetail(createDemandDetail())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal1)).isFalse();

		// propose what would create an additional demand on B and an additional supply on C.
		// shall also be rejected
		final SupplyProposal supplyProposal2 = SupplyProposal.builder()
				.demandWarehouseId(MaterialEventHandlerRegistryTests.intermediateWarehouseId)
				.supplyWarehouseId(MaterialEventHandlerRegistryTests.toWarehouseId)
				.demandDetail(createDemandDetail())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal2)).isFalse();

		// propose what would create an additional demand on A and an additional supply on C.
		// we also have this already with the same demand detail (even if indirectly)
		final SupplyProposal supplyProposal3 = SupplyProposal.builder()
				.demandWarehouseId(MaterialEventHandlerRegistryTests.fromWarehouseId)
				.supplyWarehouseId(MaterialEventHandlerRegistryTests.toWarehouseId)
				.demandDetail(createDemandDetail())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal3)).isFalse();
	}

	/**
	 * Create a chain A to B to C. Then verify that it's <b>not</b> ok to add a proposal of C to A
	 */
	@Test
	public void testWithChainOpposite()
	{
		DDOrderAdvisedHandlerTests.perform_twoAdvisedEvents(ddOrderAdvisedHandler);
		// we now have an unbalanced demand with a stock of -10 in "fromWarehouseId" (because that's where the "last" demand of the "last" DistibutionPlan is)
		// and we have a stock of +10 in "toWarehouseId"

		// now assume that the planner would create another DistibutionPlanEvent that suggests to balance the -10 in "fromWarehouseId" with the +10 in "toWarehouseId"
		// kindof questionable, but not repeating anything that we already did
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.demandWarehouseId(MaterialEventHandlerRegistryTests.toWarehouseId)
				.supplyWarehouseId(MaterialEventHandlerRegistryTests.fromWarehouseId)
				.demandDetail(createDemandDetail())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal1)).isTrue();
	}

	private DemandDetail createDemandDetail()
	{
		return DemandDetail.builder().shipmentScheduleId(EventTestHelper.SHIPMENT_SCHEDULE_ID).build();
	}
}
