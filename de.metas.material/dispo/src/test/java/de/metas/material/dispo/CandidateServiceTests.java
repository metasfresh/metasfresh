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
import de.metas.material.event.PPOrderRequestedEvent;
import de.metas.material.event.pporder.PPOrder;

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
	public void testReqestPPOrder()
	{

		final CandidateRepository candidateRepository = new CandidateRepository();

		final Candidate candidate = Candidate.builder()
				.orgId(30)
				.type(Type.SUPPLY)
				.subType(SubType.PRODUCTION)
				.productId(300)
				.warehouseId(400)
				.date(SystemTime.asDate())
				.quantity(BigDecimal.valueOf(15))
				.reference(TableRecordReference.of("someTable", 23))
				.productionDetail(ProductionCandidateDetail.builder()
						.plantId(210)
						.productPlanningId(220)
						.uomId(230)
						.build())
				.build();

		final Candidate candidate2 = candidate
				.withType(Type.DEMAND)
				.withProductId(310)
				.withQuantity(BigDecimal.valueOf(20))
				.withProductionDetail(ProductionCandidateDetail.builder()
						.productBomLineId(500)
						.build());

		final Candidate candidate3 = candidate
				.withType(Type.DEMAND)
				.withProductId(320)
				.withQuantity(BigDecimal.valueOf(10))
				.withProductionDetail(ProductionCandidateDetail.builder()
						.productBomLineId(600)
						.build());

		final CandidateService candidateService = new CandidateService(candidateRepository);
		final PPOrderRequestedEvent productionOrderEvent = candidateService.requestProductionOrder(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(productionOrderEvent, notNullValue());

		final PPOrder ppOrder = productionOrderEvent.getPpOrder();
		assertThat(ppOrder, notNullValue());
		assertThat(ppOrder.getOrgId(), is(30));
		assertThat(ppOrder.getProductId(), is(300));
		assertThat(ppOrder.getLines().size(), is(2));

	}
}
