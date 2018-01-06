package de.metas.material.planning.event;

import static de.metas.material.event.EventTestHelper.createSupplyRequiredDescriptor;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedOrCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ddorder.DDOrderDemandMatcher;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
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

public class DistributionAdvisedEventCreatorTest
{

	@Mocked
	DDOrderDemandMatcher ddOrderDemandMatcher;

	@Mocked
	DDOrderPojoSupplier ddOrderPojoSupplier;

	@Mocked
	IMutableMRPContext mrpContext;

	@Mocked
	DDOrder ddOrder;

	@Mocked
	DDOrderLine ddOrderLine;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createProductionAdvisedEvents_returns_same_supplyRequiredDescriptor()
	{
		final I_DD_NetworkDistributionLine networkDistributionLine = newInstance(I_DD_NetworkDistributionLine.class);
		save(networkDistributionLine);

		// @formatter:off
		new Expectations()
		{{
			ddOrderDemandMatcher.matches((IMaterialPlanningContext)any); result = true;
			ddOrderPojoSupplier.supplyPojos((IMaterialRequest)any, (IMRPNotesCollector)any); result = ddOrder;
			ddOrder.getLines(); result = ImmutableList.of(ddOrderLine);
			ddOrderLine.getNetworkDistributionLineId(); result = networkDistributionLine.getDD_NetworkDistributionLine_ID();
		}};	// @formatter:on
		final SupplyRequiredDescriptor supplyRequiredDescriptor = createSupplyRequiredDescriptor();

		final DDOrderAdvisedOrCreatedEventCreator productionAdvisedEventCreator = new DDOrderAdvisedOrCreatedEventCreator(ddOrderDemandMatcher, ddOrderPojoSupplier);
		final List<DDOrderAdvisedOrCreatedEvent> events = productionAdvisedEventCreator.createDistributionAdvisedEvents(supplyRequiredDescriptor, mrpContext);

		assertThat(events).hasSize(1);
		assertThat(events.get(0).getSupplyRequiredDescriptor()).isSameAs(supplyRequiredDescriptor);
	}

}
