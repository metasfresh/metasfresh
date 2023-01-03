package de.metas.material.dispo.commons;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.order.OrderLineRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static java.math.BigDecimal.TEN;
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

public class CandidateServiceTests
{
	private RequestMaterialOrderService requestMaterialOrderService;

	@BeforeEach
	public void init()
	{
		final DimensionService dimensionService = Mockito.mock(DimensionService.class);
		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		requestMaterialOrderService = new RequestMaterialOrderService(
				new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo),
				postMaterialEventService,
				new OrderLineRepository());
	}

	@Test
	public void testcreatePPOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(20, 30))
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.materialDescriptor(createMaterialDescriptor())
				.businessCaseDetail(ProductionDetail.builder()
											.plantId(ResourceId.ofRepoId(210))
											.productPlanningId(220)
											.advised(Flag.FALSE)
											.pickDirectlyIfFeasible(Flag.FALSE)
											.qty(TEN)
											.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
												.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(310))
												.withQuantity(BigDecimal.valueOf(20)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(ProductionDetail.builder()
												.plantId(ResourceId.ofRepoId(210))
												.productPlanningId(220)
												.productBomLineId(500)
												.advised(Flag.TRUE)
												.pickDirectlyIfFeasible(Flag.FALSE)
												.qty(TEN)
												.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
												.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(320))
												.withQuantity(BigDecimal.valueOf(10)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(ProductionDetail.builder()
												.plantId(ResourceId.ofRepoId(210))
												.productPlanningId(220)
												.productBomLineId(600)
												.advised(Flag.FALSE)
												.pickDirectlyIfFeasible(Flag.TRUE)
												.qty(TEN)
												.build());

		final PPOrderRequestedEvent ppOrderRequestedEvent = requestMaterialOrderService
				.createPPOrderRequestedEvent(
						ImmutableList.of(candidate, candidate2, candidate3));

		assertThat(ppOrderRequestedEvent).isNotNull();
		assertThat(ppOrderRequestedEvent.getEventDescriptor()).isNotNull();

		assertThat(ppOrderRequestedEvent.getEventDescriptor().getClientId().getRepoId()).isEqualTo(20);
		assertThat(ppOrderRequestedEvent.getEventDescriptor().getOrgId().getRepoId()).isEqualTo(30);

		final PPOrder ppOrder = ppOrderRequestedEvent.getPpOrder();
		assertThat(ppOrder).isNotNull();
		assertThat(ppOrder.getPpOrderData().getClientAndOrgId().getOrgId().getRepoId()).isEqualTo(30);
		assertThat(ppOrder.getPpOrderData().getProductDescriptor().getProductId()).isEqualTo(PRODUCT_ID);

		assertThat(ppOrder.getLines()).hasSize(2);
	}

	@Test
	public void testcreateDDOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(20, 30))
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(createMaterialDescriptor())
				.businessCaseDetail(DistributionDetail.builder()
											.productPlanningId(220)
											.plantId(230)
											.shipperId(240)
											.qty(TEN)
											.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
												.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(310))
												.withQuantity(BigDecimal.valueOf(20)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(DistributionDetail.builder()
												.productPlanningId(220)
												.plantId(230)
												.shipperId(240)
												.networkDistributionLineId(500)
												.qty(TEN)
												.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
												.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(320))
												.withQuantity(BigDecimal.valueOf(10)))
				.withDimension(candidate.getDimension())
				.withBusinessCaseDetail(DistributionDetail.builder()
												.productPlanningId(220)
												.plantId(230)
												.shipperId(240)
												.networkDistributionLineId(501)
												.qty(TEN)
												.build());

		final DDOrderRequestedEvent distributionOrderEvent = requestMaterialOrderService.createDDOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3), null);
		assertThat(distributionOrderEvent).isNotNull();

		assertThat(distributionOrderEvent.getEventDescriptor()).isNotNull();
		assertThat(distributionOrderEvent.getEventDescriptor().getClientId().getRepoId()).isEqualTo(20);
		assertThat(distributionOrderEvent.getEventDescriptor().getOrgId().getRepoId()).isEqualTo(30);

		final DDOrder ddOrder = distributionOrderEvent.getDdOrder();
		assertThat(ddOrder).isNotNull();
		assertThat(ddOrder.getOrgId().getRepoId()).isEqualTo(30);
		assertThat(ddOrder.getProductPlanningId()).isEqualTo(220);
		assertThat(ddOrder.getPlantId()).isEqualTo(230);
		assertThat(ddOrder.getShipperId()).isEqualTo(240);
		assertThat(ddOrder.getLines().isEmpty()).isFalse();

	}

}
