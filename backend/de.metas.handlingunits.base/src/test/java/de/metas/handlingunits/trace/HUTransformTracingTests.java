package de.metas.handlingunits.trace;

import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.transfer.HUTransformServiceTests;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase;
import de.metas.handlingunits.allocation.transfer.HUTransformTestsBase.TestHUs;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.interceptor.HUTraceModuleInterceptor;
import de.metas.handlingunits.trace.repository.RetrieveDbRecordsUtil;
import de.metas.organization.OrgId;
import de.metas.printing.DoNothingMassPrintingService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.math.BigDecimal;
import java.util.List;
import java.util.OptionalInt;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
 * @author metas-dev <dev@metasfresh.com>
 */
public class HUTransformTracingTests
{
	/**
	 * Watches the current tests and dumps the database to console in case of failure
	 */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private HUTraceRepository huTraceRepository;

	private HUTransformTestsBase testsBase;

	private static I_C_UOM uomRecord;

	@Before
	public void init()
	{
		final QRCodeConfigurationService qrCodeConfigurationService = new QRCodeConfigurationService(new QRCodeConfigurationRepository());
		SpringContextHolder.registerJUnitBean(qrCodeConfigurationService);
		SpringContextHolder.registerJUnitBean(new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService(DoNothingMassPrintingService.instance), qrCodeConfigurationService));

		testsBase = new HUTransformTestsBase();

		// with this, we can avoid having to start the spring context
		huTraceRepository = new HUTraceRepository();
		final HUTraceEventsService huTraceEventsService = new HUTraceEventsService(huTraceRepository, new HUAccessService(), new InventoryRepository());
		HUTraceModuleInterceptor.INSTANCE.setHUTraceEventsService(huTraceEventsService);

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		// this also invokes onAfterInit() which registers our IHUTrxListener
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(HUTraceModuleInterceptor.SYSCONFIG_ENABLED);
		sysConfig.setValue(StringUtils.ofBoolean(true));
		save(sysConfig);
		modelInterceptorRegistry.addModelInterceptor(HUTraceModuleInterceptor.INSTANCE);

		uomRecord = testsBase.getData().helper.uomEach;
	}

	@Test
	public void testCU_To_NewCU_1Tomato()
	{
		final TestHUs result = testsBase.testCU_To_NewCU_1Tomato_DoIt();

		final List<HUTraceEvent> traceEvents = RetrieveDbRecordsUtil.queryAll();
		assertThat(traceEvents.size(), is(2));

		{
			final HUTraceEvent huTraceEvent = traceEvents.get(0);
			assertThat(huTraceEvent.getType(), is(HUTraceType.TRANSFORM_LOAD));

			assertThat(huTraceEvent.getVhuId().getRepoId(), is(result.getInput().getM_HU_ID()));
			assertThat(huTraceEvent.getVhuSourceId(), nullValue());
			assertThat(huTraceEvent.getTopLevelHuId().getRepoId(), is(result.getInititalParent().getM_HU_ID()));
			assertThat(huTraceEvent.getQty().toBigDecimal(), is(BigDecimal.ONE.negate()));
		}

		{
			final HUTraceEvent huTraceEvent = traceEvents.get(1);
			assertThat(huTraceEvent.getType(), is(HUTraceType.TRANSFORM_LOAD));

			assertThat(huTraceEvent.getVhuId().getRepoId(), is(result.getOutput().get(0).getM_HU_ID()));
			assertThat(huTraceEvent.getVhuSourceId().getRepoId(), is(result.getInput().getM_HU_ID()));
			assertThat(huTraceEvent.getTopLevelHuId(), is(huTraceEvent.getVhuId()));
			assertThat(huTraceEvent.getQty().toBigDecimal(), is(BigDecimal.ONE));
		}
	}

	/**
	 * Calls {@link HUTransformServiceTests#testCU_To_NewCU_MaxValueParent()} and then verifies the tracing info.
	 * There shall be two tracing events; one shall have the old TU as {@code topLevelHuId} the other one the now-standalone CU.
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

		final HUTraceEventQuery tuTraceQuery = HUTraceEventQuery.builder().topLevelHuId(HuId.ofRepoId(parentTU.getM_HU_ID())).build();
		final List<HUTraceEvent> tuTraceEvents = huTraceRepository.query(tuTraceQuery);
		assertThat(tuTraceEvents.size(), is(1));

		final HUTraceEventBuilder common = HUTraceEvent.builder()
				.orgId(OrgId.ofRepoIdOrAny(cuToSplit.getAD_Org_ID()))
				.vhuId(HuId.ofRepoId(cuToSplit.getM_HU_ID()))
				.vhuStatus(cuToSplit.getHUStatus())
				.eventTime(tuTraceEvents.get(0).getEventTime())
				.productId(tuTraceEvents.get(0).getProductId())
				.type(HUTraceType.TRANSFORM_PARENT);

		// when comparing with "common", we needs to keep the ID out
		final HUTraceEvent tuTraceEventToCompareWith = tuTraceEvents.get(0).toBuilder().huTraceEventId(OptionalInt.empty()).build();
		assertThat(tuTraceEventToCompareWith,
				   is(common
							  .qty(Quantity.of(new BigDecimal("-3"), uomRecord))
							  .topLevelHuId(HuId.ofRepoId(parentTU.getM_HU_ID()))
							  .build()));

		final HUTraceEventQuery cuTraceQuery = HUTraceEventQuery.builder().topLevelHuId(HuId.ofRepoId(cuToSplit.getM_HU_ID())).build();
		final List<HUTraceEvent> cuTraceEvents = huTraceRepository.query(cuTraceQuery);
		assertThat(cuTraceEvents.size(), is(1));

		// when comparing with "common", we needs to keep the ID out
		final HUTraceEvent cuTraceEventToCompareWith = cuTraceEvents.get(0).toBuilder().huTraceEventId(OptionalInt.empty()).build();
		assertThat(cuTraceEventToCompareWith,
				   is(common
							  .qty(Quantity.of(new BigDecimal("3"), uomRecord))
							  .topLevelHuId(HuId.ofRepoId(cuToSplit.getM_HU_ID()))
							  .build()));
	}

	@Test
	public void testCU_To_NewCU_MaxValueNoParent()
	{
		testsBase.testCU_To_NewCU_MaxValueNoParent_DoIt();
		assertThat(RetrieveDbRecordsUtil.queryAll().isEmpty(), is(true));
	}

	/**
	 * Performs {@link HUTransformServiceTests#testAggregateCU_To_NewTUs_1Tomato()} and verifies the generated HU-trace records.
	 */
	@Test
	public void testAggregateCU_To_NewTUs_1Tomato()
	{
		testsBase.testAggregateCU_To_NewTUs_1Tomato_DoIt(false); // isOwnPackingMaterials = false

		final List<HUTraceEvent> huTraceEvents = RetrieveDbRecordsUtil.queryAll();
		assertThat(huTraceEvents.size(), is(4));
	}

}
