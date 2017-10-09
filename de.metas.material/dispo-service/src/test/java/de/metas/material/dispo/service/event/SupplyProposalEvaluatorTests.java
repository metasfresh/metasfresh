package de.metas.material.dispo.service.event;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.dispo.service.event.handler.DistributionPlanEventHandler;
import de.metas.material.dispo.service.event.handler.DistributionPlanEventHandlerTests;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
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

	// private MDEventListener mdEventListener;

	private DistributionPlanEventHandler distributionPlanEventHandler;

	/**
	 * This is the code under test
	 */

	private SupplyProposalEvaluator supplyProposalEvaluator;

	private CandidateRepository candidateRepository;

	@Mocked
	private MaterialEventService materialEventService;

	private I_AD_Org org;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		candidateRepository = new CandidateRepository();
		supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateRepository, new StockCandidateService(candidateRepository), materialEventService);

		distributionPlanEventHandler = new DistributionPlanEventHandler(
				candidateRepository,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new CandidateService(candidateRepository, materialEventService));
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
				.productId(3)
				.build();

		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal), is(true));
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
				.productId(3)
				.build();

		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal), is(true));
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
				.productId(3)
				.build();

		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal), is(true));
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
				.productId(3)
				.build();

		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal), is(false));
	}

	/**
	 * Creates a distribution-like supply-demand pair. The supply occurs at {@link #t3}, the corresponding demand (in a different warehouse ofc!) at {@link #t2}.
	 */
	private void addSimpleSupplyDemand()
	{
		final MaterialDescriptor supplyMaterialDescr = MaterialDescriptor.builder()
				.date(t3)
				.productId(3)
				.quantity(BigDecimal.TEN)
				.warehouseId(SUPPLY_WAREHOUSE_ID)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.type(Type.SUPPLY)
				.materialDescr(supplyMaterialDescr)
				.build();

		final Candidate supplyCandidateWithId = candidateRepository.addOrUpdateOverwriteStoredSeqNo(supplyCandidate);

		final MaterialDescriptor demandDescr = MaterialDescriptor.builder()
				.date(t2)
				.productId(3)
				.warehouseId(DEMAND_WAREHOUSE_ID)
				.quantity(BigDecimal.TEN)
				.build();
		
		final Candidate demandCandidate = Candidate.builder()
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.parentId(supplyCandidateWithId.getId())
				.type(Type.DEMAND)
				.materialDescr(demandDescr)
				.build();

		candidateRepository.addOrUpdateOverwriteStoredSeqNo(demandCandidate);
	}

	/**
	 * Create a chain A to B to C. Then verify that it's ok to add a proposal of A to B, A to C and B to C
	 */
	@Test
	public void testWithChain()
	{
		DistributionPlanEventHandlerTests.performTestTwoDistibutionPlanEvents(distributionPlanEventHandler, org);

		// propose what would create an additional demand on A and an additional supply on B. nothing wrong with that
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.intermediateWarehouseId)
				.productId(MaterialDispoEventListenerFacadeTests.productId)
				.build();
		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal1), is(true));

		// propose what would create an additional demand on B and an additional supply on C. nothing wrong with that either
		final SupplyProposal supplyProposal2 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.intermediateWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.productId(MaterialDispoEventListenerFacadeTests.productId)
				.build();
		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal2), is(true));

		// propose what would create an additional demand on A and an additional supply on C. nothing wrong with that either
		final SupplyProposal supplyProposal3 = SupplyProposal.builder()
				.date(t1)
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.productId(MaterialDispoEventListenerFacadeTests.productId)
				.build();
		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal3), is(true));
	}

	/**
	 * Create a chain A to B to C. Then verify that it's <b>not</b> ok to add a proposal of C to A
	 */
	@Test
	public void testWithChainOpposite()
	{
		DistributionPlanEventHandlerTests.performTestTwoDistibutionPlanEvents(distributionPlanEventHandler, org);
		// we now have an unbalanced demand with a stock of -10 in "fromWarehouseId" (because that's where the "last" demand of the "last" DistibutionPlan is)
		// and we have a stock of +10 in "toWarehouseId"

		// now assume that the planner would create create another DistibutionPlanEvent that suggests to balance the -10 in "fromWarehouseId" with the +10 in "toWarehouseId"
		// note that we don't need to look at the qty at all
		final SupplyProposal supplyProposal1 = SupplyProposal.builder()
				.date(t0) // the proposal needs to be made for the time before the two DistibutionPlanEvents occured
				.sourceWarehouseId(MaterialDispoEventListenerFacadeTests.toWarehouseId)
				.destWarehouseId(MaterialDispoEventListenerFacadeTests.fromWarehouseId)
				.productId(MaterialDispoEventListenerFacadeTests.productId)
				.build();
		assertThat(supplyProposalEvaluator.evaluateSupply(supplyProposal1), is(false));
	}
}
