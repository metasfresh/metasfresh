package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateSubType;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DistributionRequestedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.ProductionRequestedEvent;

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
	private CandidateService candidateService;

	@Before
	public void init()
	{
		candidateService = new CandidateService(
				new CandidateRepositoryRetrieval(),
				MaterialEventService.createLocalServiceThatIsReadyToUse());
	}

	@Test
	public void testcreatePPOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(CandidateType.SUPPLY)
				.subType(CandidateSubType.PRODUCTION)
				.materialDescriptor(createMaterialDescriptor())
				.productionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.uomId(230)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.forProductIdAndEmptyAttribute(310))
						.withQuantity(BigDecimal.valueOf(20)))
				.withProductionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(CandidateType.DEMAND)
				.withMaterialDescriptor(candidate.getMaterialDescriptor()
						.withProductDescriptor(ProductDescriptor.forProductIdAndEmptyAttribute(320))
						.withQuantity(BigDecimal.valueOf(10)))
				.withProductionDetail(ProductionDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(600)
						.build());

		final ProductionRequestedEvent productionOrderEvent = candidateService.createPPOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(productionOrderEvent, notNullValue());
		assertThat(productionOrderEvent.getEventDescriptor(), notNullValue());

		assertThat(productionOrderEvent.getEventDescriptor().getClientId(), is(20));
		assertThat(productionOrderEvent.getEventDescriptor().getOrgId(), is(30));

		final PPOrder ppOrder = productionOrderEvent.getPpOrder();
		assertThat(ppOrder, notNullValue());
		assertThat(ppOrder.getOrgId(), is(30));
		assertThat(ppOrder.getProductDescriptor().getProductId(), is(PRODUCT_ID));

		assertThat(ppOrder.getLines().size(), is(2));
	}

	@Test
	public void testcreateDDOrderRequestEvent()
	{
		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(CandidateType.SUPPLY)
				.subType(CandidateSubType.DISTRIBUTION)
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
						.withProductDescriptor(ProductDescriptor.forProductIdAndEmptyAttribute(310))
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
						.withProductDescriptor(ProductDescriptor.forProductIdAndEmptyAttribute(320))
						.withQuantity(BigDecimal.valueOf(10)))
				.withDistributionDetail(DistributionDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.networkDistributionLineId(501)
						.build());

		final DistributionRequestedEvent distributionOrderEvent = candidateService.createDDOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(distributionOrderEvent, notNullValue());

		assertThat(distributionOrderEvent.getEventDescriptor(), notNullValue());
		assertThat(distributionOrderEvent.getEventDescriptor().getClientId(), is(20));
		assertThat(distributionOrderEvent.getEventDescriptor().getOrgId(), is(30));

		final DDOrder ddOrder = distributionOrderEvent.getDdOrder();
		assertThat(ddOrder, notNullValue());
		assertThat(ddOrder.getOrgId(), is(30));
		assertThat(ddOrder.getProductPlanningId(), is(220));
		assertThat(ddOrder.getPlantId(), is(230));
		assertThat(ddOrder.getShipperId(), is(240));
		assertThat(ddOrder.getLines().isEmpty(), is(false));

	}

}
