package de.metas.material.dispo.service.event;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
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
	/**
	 * This is the code under test
	 */
	private SupplyProposalEvaluator supplyProposalEvaluator;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	//
	// Master data
	private final Instant t1 = SystemTime.asInstant();
	private final Instant t2 = t1.plus(10, ChronoUnit.MINUTES);
	private final Instant t3 = t1.plus(20, ChronoUnit.MINUTES);

	private static final WarehouseId WH_A = WarehouseId.ofRepoId(4);
	private static final WarehouseId WH_B = WarehouseId.ofRepoId(5);
	private static final WarehouseId WH_C = WarehouseId.ofRepoId(6);
	private DemandDetail demandDetail;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		setupServices();
		setupMasterData();
	}

	private void setupServices()
	{
		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		this.candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval);
		this.supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepositoryRetrieval);
	}

	private void setupMasterData()
	{
		this.demandDetail = DemandDetail.builder().shipmentScheduleId(EventTestHelper.SHIPMENT_SCHEDULE_ID).build();
	}

	@Builder(builderMethodName = "simpleDemandSupply", builderClassName = "$SimpleDemandSupplyBuilder")
	private void addSimpleDemandSupply(
			final WarehouseId supplyWarehouseId,
			final WarehouseId demandWarehouseId)
	{
		final CandidateId supplyCandidateId = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(
						Candidate.builder()
								.clientAndOrgId(CLIENT_AND_ORG_ID)
								.additionalDemandDetail(demandDetail)
								.type(CandidateType.SUPPLY)
								.businessCase(CandidateBusinessCase.DISTRIBUTION)
								.businessCaseDetail(DistributionDetail.builder().qty(BigDecimal.valueOf(1)).build()) // just a dummy detail that currently doesn't play a role in the actual test
								.materialDescriptor(MaterialDescriptor.builder()
										.date(t2)
										.productDescriptor(createProductDescriptor())
										.quantity(BigDecimal.valueOf(10))
										.warehouseId(supplyWarehouseId)
										.build())
								.build()
				)
				.getId();

		candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(
				Candidate.builder()
						.clientAndOrgId(CLIENT_AND_ORG_ID)
						.parentId(supplyCandidateId)
						.type(CandidateType.DEMAND)
						.businessCase(CandidateBusinessCase.DISTRIBUTION)
						.businessCaseDetail(DistributionDetail.builder().qty(BigDecimal.valueOf(1)).build()) // just a dummy detail that currently doesn't play a role in the actual test
						.additionalDemandDetail(demandDetail)
						.materialDescriptor(MaterialDescriptor.builder()
								.date(t2)
								.productDescriptor(createProductDescriptor())
								.warehouseId(demandWarehouseId)
								.quantity(BigDecimal.valueOf(10))
								.build())
						.build()
		);
	}

	/**
	 * If there was nothing persisted yet, the tested shall return <code>true</code>.
	 */
	@Test
	public void testEmpty()
	{
		assertThat(supplyProposalEvaluator.isProposalAccepted(
				SupplyProposal.builder()
						.demandWarehouseId(WH_A)
						.supplyWarehouseId(WH_B)
						.demandDetail(demandDetail)
						.date(t2)
						.productId(PRODUCT_ID)
						.build())
		).isTrue();
		assertThat(supplyProposalEvaluator.isProposalAccepted(
				SupplyProposal.builder()
						.demandWarehouseId(WH_B)
						.supplyWarehouseId(WH_A)
						.demandDetail(demandDetail)
						.date(t2)
						.productId(PRODUCT_ID)
						.build())
		).isTrue();
	}

	@Test
	public void testWithSameDirectionData()
	{
		simpleDemandSupply().demandWarehouseId(WH_A).supplyWarehouseId(WH_B).build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(
				SupplyProposal.builder()
						.demandWarehouseId(WH_A)
						.supplyWarehouseId(WH_B)
						.demandDetail(demandDetail)
						.date(t2)
						.productId(PRODUCT_ID)
						.build())
		).isTrue();
	}

	@Test
	public void testWithOppositeDirectionData()
	{
		simpleDemandSupply()
				.demandWarehouseId(WH_A)
				.supplyWarehouseId(WH_B)
				.build();

		assertThat(supplyProposalEvaluator.isProposalAccepted(
				SupplyProposal.builder()
						.demandWarehouseId(WH_B)
						.supplyWarehouseId(WH_A)
						.demandDetail(demandDetail)
						.date(t2)
						.productId(PRODUCT_ID)
						.build())
		).isFalse();
	}

	/**
	 * Create a chain <code>A <- B <- C</code>. Then verify that it's <b>not</b> ok to add a proposal of <code>C <- A</code>
	 */
	@Test
	public void testWithChainOpposite()
	{
		simpleDemandSupply().supplyWarehouseId(WH_A).demandWarehouseId(WH_B).build();
		simpleDemandSupply().supplyWarehouseId(WH_B).demandWarehouseId(WH_C).build();

		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.supplyWarehouseId(WH_C)
				.demandWarehouseId(WH_A)
				.demandDetail(demandDetail)
				.date(t2)
				.productId(PRODUCT_ID)
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isFalse();
	}

	/**
	 * Create a chain <code>A <- B <- C</code> on date t2. Then verify that it's ok to add a proposal of <code>C <- A</code> in t3
	 */
	@Test
	public void testWithChainOpposite_onDifferentDate()
	{
		simpleDemandSupply().supplyWarehouseId(WH_A).demandWarehouseId(WH_B).build();
		simpleDemandSupply().supplyWarehouseId(WH_B).demandWarehouseId(WH_C).build();

		final SupplyProposal supplyProposal = SupplyProposal.builder()
				.supplyWarehouseId(WH_C)
				.demandWarehouseId(WH_A)
				.demandDetail(demandDetail)
				.date(t3)
				.productId(PRODUCT_ID)
				.build();
		assertThat(supplyProposalEvaluator.isProposalAccepted(supplyProposal)).isTrue();
	}
}
