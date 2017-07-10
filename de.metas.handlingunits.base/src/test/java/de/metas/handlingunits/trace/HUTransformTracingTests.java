package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.hasChanges;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformServiceTests;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class HUTransformTracingTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	//private HUTransformServiceTests huTransformServiceTests;
	private HUTraceRepository huTraceRepository;

	private LUTUProducerDestinationTestSupport data;

	private IHandlingUnitsDAO handlingUnitsDAO;

	@Before
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();

		huTraceRepository = new HUTraceRepository();
		final HUTraceEventsService huTraceEventsService = new HUTraceEventsService(huTraceRepository, new HUAccessService());
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE.setHUTraceEventsService(huTraceEventsService);
		
		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE);
	}

	/**
	 * Calls {@link HUTransformServiceTests#testCU_To_NewCU_MaxValueParent()} and then verifies the tracing info.
	 * 
	 */
	@Test
	public void testCU_To_NewCU_MaxValueParent()
	{
		final I_M_HU cuToSplit = mkRealCUWithTUToSplit("3");
		final I_M_HU parentTU = cuToSplit.getM_HU_Item_Parent().getM_HU();

		assertThat(hasChanges(cuToSplit), is(false)); // make sure the CU was stored
		
		// invoke the method under test
		final List<I_M_HU> newCUs = HUTransformService
				.get(data.helper.getHUContext())
				.cuToNewCU(cuToSplit, new BigDecimal("3"));
		
		// guards
		assertThat(newCUs.size(), is(1));
		assertThat(newCUs.get(0).getM_HU_ID(), is(cuToSplit.getM_HU_ID()));

		// retrieve the events that were added to the repo and make sure they are as expected
		final HUTraceSpecification tuTraceQuery = HUTraceSpecification.builder().topLevelHuId(parentTU.getM_HU_ID()).build();
		final List<HUTraceEvent> tuTraceEvents = huTraceRepository.query(tuTraceQuery);
		assertThat(tuTraceEvents.size(), is(1));
		assertThat(tuTraceEvents.get(0),
				is(HUTraceEvent.builder()
						.eventTime(tuTraceEvents.get(0).eventTime)
						.productId(tuTraceEvents.get(0).productId)
						.qty(BigDecimal.valueOf(-3))
						.topLevelHuId(parentTU.getM_HU_ID())
						.type(HUTraceType.TRANSFORM_PARENT)
						.vhuId(cuToSplit.getM_HU_ID())
						.vhuStatus(cuToSplit.getHUStatus())
						.build()));
		
		final HUTraceSpecification cuTraceQuery = HUTraceSpecification.builder().topLevelHuId(cuToSplit.getM_HU_ID()).build();
		final List<HUTraceEvent> cuTraceEvents = huTraceRepository.query(cuTraceQuery);
		assertThat(cuTraceEvents.size(), is(1));
		assertThat(cuTraceEvents.get(0),
				is(HUTraceEvent.builder()
						.eventTime(cuTraceEvents.get(0).eventTime)
						.productId(cuTraceEvents.get(0).productId)
						.vhuId(cuToSplit.getM_HU_ID())
						.vhuStatus(cuToSplit.getHUStatus())
						.qty(BigDecimal.valueOf(3))
						.topLevelHuId(cuToSplit.getM_HU_ID())
						.type(HUTraceType.TRANSFORM_PARENT)
						.build()));
		
		// we want two trace records, both with the same VHU (i.e. the CU that was moved). ne
	}

	private I_M_HU mkRealCUWithTUToSplit(final String strCuQty)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		final BigDecimal cuQty = new BigDecimal(strCuQty);
		data.helper.load(lutuProducer, data.helper.pTomato, cuQty, data.helper.uomKg);
		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();
		assertThat(createdTUs.size(), is(1));

		final List<I_M_HU> createdCUs = handlingUnitsDAO.retrieveIncludedHUs(createdTUs.get(0));
		assertThat(createdCUs.size(), is(1));

		final I_M_HU cuToSplit = createdCUs.get(0);

		return cuToSplit;
	}
}
