package de.metas.material.dispo.service.event;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.dispo.service.event.handler.DDOrderAdvisedOrCreatedHandlerTests;
import de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedOrCreatedHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import mockit.Mocked;

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

public class SupplyProposalEvaluatorTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date t1 = SystemTime.asDate();

	private final Date t0 = TimeUtil.addMinutes(t1, -10);

	private final Date t2 = TimeUtil.addMinutes(t1, 10);
	private final Date t3 = TimeUtil.addMinutes(t1, 20);
	private final Date t4 = TimeUtil.addMinutes(t1, 30);

	private static final int SUPPLY_WAREHOUSE_ID = 4;

	private static final int DEMAND_WAREHOUSE_ID = 6;

	private DDOrderAdvisedOrCreatedHandler distributionAdvisedEventHandler;

	/**
	 * This is the code under test
	 */

	private SupplyProposalEvaluator supplyProposalEvaluator;

	private CandidateRepositoryWriteService candidateRepositoryCommands;

	@Mocked
	private PostMaterialEventService postMaterialEventService;


	private StockRepository stockRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryCommands = new CandidateRepositoryWriteService();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepositoryRetrieval);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		stockRepository = new StockRepository();

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new SupplyCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, stockCandidateService),
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						postMaterialEventService,
						stockRepository,
						stockCandidateService
						)));

		distributionAdvisedEventHandler = new DDOrderAdvisedOrCreatedHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new RequestMaterialOrderService(candidateRepositoryRetrieval, postMaterialEventService));
	}

	/**
	 * If there was nothing persisted yet, the testee shall return <code>true</code>.
	 */
	@Test
	public void testEmpty()
	{
		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.date(t1)
				.destWarehouseId(1)
				.sourceWarehouseId(2)
				.productDescriptor(createProductDescriptor())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}

	/**
	 * If there was nothing persisted <b>after</b> the proposal, the testee shall return <code>true</code>.
	 */
	@Test
	public void testNothingAfter()
	{
		addSimpleSupplyDemand();

		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.date(t4)
				.destWarehouseId(1)
				.sourceWarehouseId(2)
				.productDescriptor(createProductDescriptor())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}

	@Test
	public void testWithSameDirectionData()
	{
		addSimpleSupplyDemand();

		// OK now we have in our plan something like DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID in the repo
		// now let's propose something that once again amounts to DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID.
		// there should be nothing wrong with that.

		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(DEMAND_WAREHOUSE_ID)
				.destWarehouseId(SUPPLY_WAREHOUSE_ID)
				.productDescriptor(createProductDescriptor())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}

	@Test
	public void testWithOppositeDirectionData()
	{
		addSimpleSupplyDemand();

		// OK now we have in our plan something like DEMAND_WAREHOUSE_ID => SUPPLY_WAREHOUSE_ID in the repo
		// To balance the demand in DEMAND_WAREHOUSE_ID that we just created, let's propose something that amounts to SUPPLY_WAREHOUSE_ID => DEMAND_WAREHOUSE_ID.
		// this should be rejected because it doesn't make sense to play ping-pong like that

		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.date(t1)
				.destWarehouseId(DEMAND_WAREHOUSE_ID)
				.sourceWarehouseId(SUPPLY_WAREHOUSE_ID)
				.productDescriptor(createProductDescriptor())
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isFalse();
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
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.type(CandidateType.SUPPLY)
				.materialDescriptor(supplyMaterialDescriptor)
				.build();

		final Candidate supplyCandidateWithId = candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(supplyCandidate);

		final MaterialDescriptor demandDescr = MaterialDescriptor.builder()
				.date(t2)
				.productDescriptor(createProductDescriptor())
				.warehouseId(DEMAND_WAREHOUSE_ID)
				.quantity(BigDecimal.TEN)
				.build();

		final Candidate demandCandidate = Candidate.builder()
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.parentId(supplyCandidateWithId.getId())
				.type(CandidateType.DEMAND)
				.materialDescriptor(demandDescr)
				.build();

		candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(demandCandidate);
	}

	/**
	 * Create a chain A to B to C. Then verify that it's ok to add a proposal of A to B, A to C and B to C
	 */
	@Test
	public void testWithChain()
	{
		DDOrderAdvisedOrCreatedHandlerTests.handleDistributionAdvisedEvent_with_two_events_chronological(distributionAdvisedEventHandler);

		// propose what would create an additional demand on A and an additional supply on B. nothing wrong with that
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.intermediateWarehouseId)
				.productDescriptor(createProductDescriptor())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal1)).isTrue();

		// propose what would create an additional demand on B and an additional supply on C. nothing wrong with that either
		final SupplyProposal supplyProposal2 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.intermediateWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.productDescriptor(createProductDescriptor())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal2)).isTrue();

		// propose what would create an additional demand on A and an additional supply on C. nothing wrong with that either
		final SupplyProposal supplyProposal3 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.productDescriptor(createProductDescriptor())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal3)).isTrue();
	}

	/**
	 * Create a chain A to B to C. Then verify that it's <b>not</b> ok to add a proposal of C to A
	 */
	@Test
	public void testWithChainOpposite()
	{
		DDOrderAdvisedOrCreatedHandlerTests.handleDistributionAdvisedEvent_with_two_events_chronological(distributionAdvisedEventHandler);
		// we now have an unbalanced demand with a stock of -10 in "fromWarehouseId" (because that's where the "last" demand of the "last" DistibutionPlan is)
		// and we have a stock of +10 in "toWarehouseId"

		// now assume that the planner would create another DistibutionPlanEvent that suggests to balance the -10 in "fromWarehouseId" with the +10 in "toWarehouseId"
		// note that we don't need to look at the qty at all
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.date(t0) // the proposal needs to be made for the time before the two DistibutionPlanEvents occured
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.productDescriptor(createProductDescriptor())
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal1)).isFalse();
	}
}
