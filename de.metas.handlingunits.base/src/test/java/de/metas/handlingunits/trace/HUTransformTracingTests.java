package de.metas.handlingunits.trace;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.handlingunits.allocation.transfer.HUTransformServiceTests;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;

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
public class HUTransformTracingTests extends HUTransformServiceTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	// private HUTransformServiceTests huTransformServiceTests;
	private HUTraceRepository huTraceRepository;

	@Before
	public void init()
	{
		super.init();

		huTraceRepository = new HUTraceRepository();
		final HUTraceEventsService huTraceEventsService = new HUTraceEventsService(huTraceRepository, new HUAccessService());
		de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE.setHUTraceEventsService(huTraceEventsService);

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		// this also invokes onAfterInit() which registers our IHUTrxListener
		modelInterceptorRegistry.addModelInterceptor(de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor.INSTANCE);
	}

	@Test
	public void testCU_To_NewCU_1Tomato()
	{
		final TestHUs result = super.testCU_To_NewCU_1Tomato_DoIt();

		final List<HUTraceEvent> traceEvents = huTraceRepository.queryAll();
		assertThat(traceEvents.size(), is(2));

		{
			final HUTraceEvent huTraceEvent = traceEvents.get(0);
			assertThat(huTraceEvent.getType(), is(HUTraceType.TRANSFORM_LOAD));

			assertThat(huTraceEvent.getVhuId(), is(result.getInput().getM_HU_ID()));
			assertThat(huTraceEvent.getVhuSourceId(), is(0));
			assertThat(huTraceEvent.getTopLevelHuId(), is(result.getInititalParent().getM_HU_ID()));
			assertThat(huTraceEvent.getQty(), is(BigDecimal.ONE.negate()));
		}

		{
			final HUTraceEvent huTraceEvent = traceEvents.get(1);
			assertThat(huTraceEvent.getType(), is(HUTraceType.TRANSFORM_LOAD));

			assertThat(huTraceEvent.getVhuId(), is(result.getOutput().get(0).getM_HU_ID()));
			assertThat(huTraceEvent.getVhuSourceId(), is(result.getInput().getM_HU_ID()));
			assertThat(huTraceEvent.getTopLevelHuId(), is(huTraceEvent.getVhuId()));
			assertThat(huTraceEvent.getQty(), is(BigDecimal.ONE));
		}
	}

	/**
	 * Calls {@link HUTransformServiceTests#testCU_To_NewCU_MaxValueParent()} and then verifies the tracing info.
	 * there shall be two tracing events; one shall have the old TU as {@code topLevelHuId} the other one the now-standalone CU.
	 * 
	 */
	@Test
	public void testCU_To_NewCU_MaxValueParent()
	{
		final TestHUs result = super.testCU_To_NewCU_MaxValueParent_DoIt();

		final I_M_HU parentTU = result.getInititalParent();
		final I_M_HU cuToSplit = result.getInput();

		// retrieve the events that were added to the repo and make sure they are as expected

		final HUTraceSpecification tuTraceQuery = HUTraceSpecification.builder().topLevelHuId(parentTU.getM_HU_ID()).build();
		final List<HUTraceEvent> tuTraceEvents = huTraceRepository.query(tuTraceQuery);
		assertThat(tuTraceEvents.size(), is(1));

		final HUTraceEventBuilder common = HUTraceEvent.builder()
				.vhuId(cuToSplit.getM_HU_ID())
				.vhuStatus(cuToSplit.getHUStatus())
				.eventTime(tuTraceEvents.get(0).eventTime)
				.productId(tuTraceEvents.get(0).productId)
				.type(HUTraceType.TRANSFORM_PARENT);

		assertThat(tuTraceEvents.get(0),
				is(common
						.qty(new BigDecimal("-3"))
						.topLevelHuId(parentTU.getM_HU_ID())
						.build()));

		final HUTraceSpecification cuTraceQuery = HUTraceSpecification.builder().topLevelHuId(cuToSplit.getM_HU_ID()).build();
		final List<HUTraceEvent> cuTraceEvents = huTraceRepository.query(cuTraceQuery);
		assertThat(cuTraceEvents.size(), is(1));
		assertThat(cuTraceEvents.get(0),
				is(common
						.qty(new BigDecimal("3"))
						.topLevelHuId(cuToSplit.getM_HU_ID())
						.build()));
	}

	@Override
	public void testCU_To_NewCU_MaxValueNoParent()
	{
		super.testCU_To_NewCU_MaxValueNoParent_DoIt();
		
		assertThat(huTraceRepository.queryAll().isEmpty(), is(true));
	}

	/**
	 * Performs {@link HUTransformServiceTests#testAggregateCU_To_NewTUs_1Tomato()} and verifies the generated HU-trace records.
	 */
	@Test
	public void testAggregateCU_To_NewTUs_1Tomato()
	{
		final TestHUs testHUs = super.testAggregateCU_To_NewTUs_1Tomato_DoIt(false);
		
		final List<HUTraceEvent> huTraceEvents = huTraceRepository.queryAll();
		assertThat(huTraceEvents.size(), is(4));
	}

	@Override
	@Ignore
	public void testRealCU_To_NewTUs_1Tomato_TU_Capacity_40(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testRealCU_To_NewTUs_1Tomato_TU_Capacity_40(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testRealCU_To_NewTUs_40Tomatoes_TU_Capacity_8(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testRealCU_To_ExistingRealTU()
	{
		// TODO Auto-generated method stub
		super.testRealCU_To_ExistingRealTU();
	}

	@Override
	@Ignore
	public void testRealCU_To_ExistingRealTU_overfill()
	{
		// TODO Auto-generated method stub
		super.testRealCU_To_ExistingRealTU_overfill();
	}

	@Override
	@Ignore
	public void testRealCU_To_ExistingAggregateTU()
	{
		// TODO Auto-generated method stub
		super.testRealCU_To_ExistingAggregateTU();
	}

	@Override
	@Ignore
	public void testAggregateTU_To_NewTUs_MaxValueParent()
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_To_NewTUs_MaxValueParent();
	}

	@Override
	@Ignore
	public void testAggregateTU_To_NewTUs(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_To_NewTUs(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void test_TakeOutTUsFromCustomLU(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.test_TakeOutTUsFromCustomLU(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testRealTU_To_NewTUs_MaxValue()
	{
		// TODO Auto-generated method stub
		super.testRealTU_To_NewTUs_MaxValue();
	}

	@Override
	@Ignore
	public void testRealTU_To_NewTUs(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testRealTU_To_NewTUs(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testAggregateTU_To_OneNewLU(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_To_OneNewLU(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testAggregateTU_To_MultipleNewLUs(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_To_MultipleNewLUs(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testRealStandaloneTU_To_NewLU(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testRealStandaloneTU_To_NewLU(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testRealTUwithLU_To_NewLU(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testRealTUwithLU_To_NewLU(isOwnPackingMaterials);
	}

	@Override
	@Ignore
	public void testAggregateTU_to_existingLU_withRealTU()
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_to_existingLU_withRealTU();
	}

	@Override
	@Ignore
	public void testAggregateTU_To_existingLU_withAggregateTU()
	{
		// TODO Auto-generated method stub
		super.testAggregateTU_To_existingLU_withAggregateTU();
	}

	@Override
	@Ignore
	public void test_CUToExistingTU_create_mixed_TU_partialCU()
	{
		// TODO Auto-generated method stub
		super.test_CUToExistingTU_create_mixed_TU_partialCU();
	}

	@Override
	@Ignore
	public void test_CUToExistingTU_create_mixed_TU_completeCU()
	{
		// TODO Auto-generated method stub
		super.test_CUToExistingTU_create_mixed_TU_completeCU();
	}

	@Override
	@Ignore
	public void testAggregateSingleLUFullyLoaded_non_int(boolean isOwnPackingMaterials)
	{
		// TODO Auto-generated method stub
		super.testAggregateSingleLUFullyLoaded_non_int(isOwnPackingMaterials);
	}

}
