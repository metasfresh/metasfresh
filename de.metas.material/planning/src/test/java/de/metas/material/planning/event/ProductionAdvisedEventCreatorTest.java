package de.metas.material.planning.event;

import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-material-planning
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

public class ProductionAdvisedEventCreatorTest
{
	@Mocked
	PPOrderDemandMatcher ppOrderDemandMatcher;

	@Mocked
	PPOrderPojoSupplier ppOrderPojoSupplier;

	@Mocked
	IMutableMRPContext mrpContext;

	@Mocked
	PPOrder somePPOrderPojo;

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		// @formatter:off
		new Expectations() {{
			ppOrderDemandMatcher.matches((IMaterialPlanningContext)any); result = true;
			ppOrderPojoSupplier.supplyPPOrderPojoWithLines((IMaterialRequest)any, (IMRPNotesCollector)any); result = somePPOrderPojo;
		}};	// @formatter:on

		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor();

		final PPOrderAdvisedOrCreatedEventCreator pPOrderAdvisedOrCreatedEventCreator = new PPOrderAdvisedOrCreatedEventCreator(ppOrderDemandMatcher, ppOrderPojoSupplier);
		final List<PPOrderAdvisedOrCreatedEvent> events = pPOrderAdvisedOrCreatedEventCreator.createProductionAdvisedEvents(supplyRequiredDescriptor, mrpContext);
		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}
}
