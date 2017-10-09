package de.metas.material.dispo;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderRequestedEvent;

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
	@Test
	public void testcreatePPOrderRequestEvent()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(300)
				.warehouseId(400)
				.date(SystemTime.asDate())
				.quantity(BigDecimal.valueOf(15))
				.build();

		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(Type.SUPPLY)
				.subType(SubType.PRODUCTION)
				.materialDescr(materialDescr)
				.reference(TableRecordReference.of("someTable", 23))
				.productionDetail(ProductionCandidateDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.uomId(230)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(Type.DEMAND)
				.withMaterialDescr(candidate.getMaterialDescr()
						.withProductId(310)
						.withQuantity(BigDecimal.valueOf(20)))
				.withProductionDetail(ProductionCandidateDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(Type.DEMAND)
				.withMaterialDescr(candidate.getMaterialDescr()
						.withProductId(320)
						.withQuantity(BigDecimal.valueOf(10)))
				.withProductionDetail(ProductionCandidateDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.productBomLineId(600)
						.build());

		final CandidateService candidateService = new CandidateService(
				new CandidateRepository(),
				MaterialEventService.createLocalServiceThatIsReadyToUse());

		final PPOrderRequestedEvent productionOrderEvent = candidateService.createPPOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(productionOrderEvent, notNullValue());
		assertThat(productionOrderEvent.getEventDescr(), notNullValue());

		assertThat(productionOrderEvent.getEventDescr().getClientId(), is(20));
		assertThat(productionOrderEvent.getEventDescr().getOrgId(), is(30));

		final PPOrder ppOrder = productionOrderEvent.getPpOrder();
		assertThat(ppOrder, notNullValue());
		assertThat(ppOrder.getOrgId(), is(30));
		assertThat(ppOrder.getProductId(), is(300));
		assertThat(ppOrder.getLines().size(), is(2));
	}

	@Test
	public void testcreateDDOrderRequestEvent()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(300)
				.warehouseId(400)
				.date(SystemTime.asDate())
				.quantity(BigDecimal.valueOf(15))
				.build();

		final Candidate candidate = Candidate.builder()
				.clientId(20)
				.orgId(30)
				.type(Type.SUPPLY)
				.subType(SubType.DISTRIBUTION)
				.materialDescr(materialDescr)
				.reference(TableRecordReference.of("someTable", 23))
				.distributionDetail(DistributionCandidateDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(Type.DEMAND)
				.withMaterialDescr(candidate.getMaterialDescr()
						.withProductId(310)
						.withQuantity(BigDecimal.valueOf(20)))
				.withDistributionDetail(DistributionCandidateDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.networkDistributionLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(Type.DEMAND)
				.withMaterialDescr(candidate.getMaterialDescr()
						.withProductId(320)
						.withQuantity(BigDecimal.valueOf(10)))
				.withDistributionDetail(DistributionCandidateDetail.builder()
						.productPlanningId(220)
						.plantId(230)
						.shipperId(240)
						.networkDistributionLineId(501)
						.build());

		final CandidateService candidateService = new CandidateService(
				new CandidateRepository(),
				MaterialEventService.createLocalServiceThatIsReadyToUse());

		final DDOrderRequestedEvent distributionOrderEvent = candidateService.createDDOrderRequestEvent(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(distributionOrderEvent, notNullValue());

		assertThat(distributionOrderEvent.getEventDescr(), notNullValue());
		assertThat(distributionOrderEvent.getEventDescr().getClientId(), is(20));
		assertThat(distributionOrderEvent.getEventDescr().getOrgId(), is(30));

		final DDOrder ddOrder = distributionOrderEvent.getDdOrder();
		assertThat(ddOrder, notNullValue());
		assertThat(ddOrder.getOrgId(), is(30));
		assertThat(ddOrder.getProductPlanningId(), is(220));
		assertThat(ddOrder.getPlantId(), is(230));
		assertThat(ddOrder.getShipperId(), is(240));
		assertThat(ddOrder.getLines().isEmpty(), is(false));

	}

}
