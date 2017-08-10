package de.metas.handlingunits.trace;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_AD_SysConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.allocation.transfer.HUTransformServiceTests;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase.TestHUs;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor;
import mockit.Mocked;

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

/**
 * Leans on {@link HUTransformTestsBase} and verifies that the correct {@link HUTraceEvent}s were created.
 * To test additional use cases, move the respective testing code from {@link HUTransformServiceTests} to {@link HUTransformTestsBase}.
 * 
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUTransformTracingTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	// private HUTransformServiceTests huTransformServiceTests;
	private HUTraceRepository huTraceRepository;

	private HUTransformTestsBase testsBase;

	@Mocked
	private IHUPackingMaterialsCollector<I_M_InOutLine> noopPackingMaterialsCollector;

	@Before
	public void init()
	{
		testsBase = new HUTransformTestsBase(noopPackingMaterialsCollector);

		// with this, we can avoid having to start the spring context
		huTraceRepository = new HUTraceRepository();
		final HUTraceEventsService huTraceEventsService = new HUTraceEventsService(huTraceRepository, new HUAccessService());
		HUTraceModuleInterceptor.INSTANCE.setHUTraceEventsService(huTraceEventsService);

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		// this also invokes onAfterInit() which registers our IHUTrxListener
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(HUTraceModuleInterceptor.SYSCONFIG_ENABLED);
		sysConfig.setValue(StringUtils.toBooleanString(true));
		save(sysConfig);
		modelInterceptorRegistry.addModelInterceptor(HUTraceModuleInterceptor.INSTANCE);
	}

	@Test
	public void testCU_To_NewCU_1Tomato()
	{
		final TestHUs result = testsBase.testCU_To_NewCU_1Tomato_DoIt();

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
	 * There shall be two tracing events; one shall have the old TU as {@code topLevelHuId} the other one the now-standalone CU.
	 * 
	 */
	@Test
	public void testCU_To_NewCU_MaxValueParent()
	{
		final TestHUs result = testsBase.testCU_To_NewCU_MaxValueParent_DoIt();

		final I_M_HU parentTU = result.getInititalParent();
		assertThat(parentTU.getHUStatus(), is(X_M_HU.HUSTATUS_Active)); // guard
		final I_M_HU cuToSplit = result.getInput();
		assertThat(cuToSplit.getHUStatus(), is(X_M_HU.HUSTATUS_Active)); // guard

		// retrieve the events that were added to the repo and make sure they are as expected

		final HUTraceSpecification tuTraceQuery = HUTraceSpecification.builder().topLevelHuId(parentTU.getM_HU_ID()).build();
		final List<HUTraceEvent> tuTraceEvents = huTraceRepository.query(tuTraceQuery);
		assertThat(tuTraceEvents.size(), is(1));

		final HUTraceEventBuilder common = HUTraceEvent.builder()
				.vhuId(cuToSplit.getM_HU_ID())
				.vhuStatus(cuToSplit.getHUStatus())
				.eventTime(tuTraceEvents.get(0).getEventTime())
				.productId(tuTraceEvents.get(0).getProductId())
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

	@Test
	public void testCU_To_NewCU_MaxValueNoParent()
	{
		testsBase.testCU_To_NewCU_MaxValueNoParent_DoIt();

		assertThat(huTraceRepository.queryAll().isEmpty(), is(true));
	}

	/**
	 * Performs {@link HUTransformServiceTests#testAggregateCU_To_NewTUs_1Tomato()} and verifies the generated HU-trace records.
	 */
	@Test
	public void testAggregateCU_To_NewTUs_1Tomato()
	{
		testsBase.testAggregateCU_To_NewTUs_1Tomato_DoIt(false); // isOwnPackingMaterials = false

		final List<HUTraceEvent> huTraceEvents = huTraceRepository.queryAll();
		assertThat(huTraceEvents.size(), is(4));
	}

}
