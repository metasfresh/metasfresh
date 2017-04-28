package de.metas.material.dispo;

import java.math.BigDecimal;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.event.ProductionOrderEvent;
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
				.build();

		final Candidate candidate2 = candidate
				.withType(Type.DEMAND)
				.withProductId(310)
				.withQuantity(BigDecimal.valueOf(20));

		final Candidate candidate3 = candidate
				.withType(Type.DEMAND)
				.withProductId(320)
				.withQuantity(BigDecimal.valueOf(10));

		final CandidateService candidateService = new CandidateService(candidateRepository);
		final ProductionOrderEvent productionOrderEvent = candidateService.requestProductionOrder(ImmutableList.of(candidate, candidate2, candidate3));
		assertThat(productionOrderEvent, notNullValue());

		final PPOrder ppOrder = productionOrderEvent.getPpOrder();
		assertThat(ppOrder, notNullValue());
		assertThat(productionOrderEvent.getPpOrder().getOrgId(), is(30));

	}
}
