package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
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

public class CandidateServiceTests
{
	private RequestMaterialOrderService requestMaterialOrderService;

	@Mocked
	private PostMaterialEventService postMaterialEventService;

	@Before
	public void init()
	{
		requestMaterialOrderService = new RequestMaterialOrderService(
				new CandidateRepositoryRetrieval(),
				postMaterialEventService);
	}

	@Test
	public void testcreatePPOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.materialDescriptor(createMaterialDescriptor())
				.productionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(310))
						.withQuantity(BigDecimal.valueOf(20)))
				.withProductionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(320))
						.withQuantity(BigDecimal.valueOf(10)))
				.withProductionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(600)
						.build());

		final PPOrderRequestedEvent ppOrderRequestedEvent = requestMaterialOrderService
				.createPPOrderRequestedEvent(
						ImmutableList.of(candidate, candidate2, candidate3));

		assertThat(ppOrderRequestedEvent).isNotNull();
		assertThat(ppOrderRequestedEvent.getEventDescriptor()).isNotNull();

		assertThat(ppOrderRequestedEvent.getEventDescriptor().getClientId()).isEqualTo(20);
		assertThat(ppOrderRequestedEvent.getEventDescriptor().getOrgId()).isEqualTo(30);

		final PPOrder ppOrder = ppOrderRequestedEvent.getPpOrder();
		assertThat(ppOrder).isNotNull();
		assertThat(ppOrder.getOrgId()).isEqualTo(30);
		assertThat(ppOrder.getProductDescriptor().getProductId()).isEqualTo(PRODUCT_ID);

		assertThat(ppOrder.getLines()).hasSize(2);
	}

	@Test
	public void testcreateDDOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(createMaterialDescriptor())
				.distributionDetail(DistributionDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(310))
						.withQuantity(BigDecimal.valueOf(20)))
				.withDistributionDetail(DistributionDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.networkDistributionLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(320))
						.withQuantity(BigDecimal.valueOf(10)))
				.withDistributionDetail(DistributionDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.networkDistributionLineId(501)
						.build());

		final DDOrderRequestedEvent distributionOrderEvent = requestMaterialOrderService.createDDOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(distributionOrderEvent).isNotNull();

		assertThat(distributionOrderEvent.getEventDescriptor()).isNotNull();
		assertThat(distributionOrderEvent.getEventDescriptor().getClientId()).isEqualTo(20);
		assertThat(distributionOrderEvent.getEventDescriptor().getOrgId()).isEqualTo(30);

		final DDOrder ddOrder = distributionOrderEvent.getDdOrder();
		assertThat(ddOrder).isNotNull();
		assertThat(ddOrder.getOrgId()).isEqualTo(30);
		assertThat(ddOrder.getProductPlanningId()).isEqualTo(220);
		assertThat(ddOrder.getPlantId()).isEqualTo(230);
		assertThat(ddOrder.getShipperId()).isEqualTo(240);
		assertThat(ddOrder.getLines().isEmpty()).isFalse();

	}

}
