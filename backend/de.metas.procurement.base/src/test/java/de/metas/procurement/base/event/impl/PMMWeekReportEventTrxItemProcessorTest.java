package de.metas.procurement.base.event.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.impl.SyncUUIDs;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.base.model.I_PMM_WeekReport_Event;
import de.metas.procurement.base.model.X_PMM_WeekReport_Event;
import de.metas.procurement.sync.protocol.SyncWeeklySupply;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMMWeekReportEventTrxItemProcessorTest
{
	private static final int AD_Org_ID_1 = 1;

	private final List<String> ALL_TRENDS = Arrays.asList(null, X_PMM_WeekReport_Event.PMM_TREND_Down, X_PMM_WeekReport_Event.PMM_TREND_Same, X_PMM_WeekReport_Event.PMM_TREND_Up,
			X_PMM_WeekReport_Event.PMM_TREND_Zero);

	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	// services
	private IServerSyncBL serverSyncBL;
	private int nextSyncWeeklySupplyVersion = 0;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		nextSyncWeeklySupplyVersion = 0;
		serverSyncBL = Services.get(IServerSyncBL.class);
	}

	@Test
	public void test_ReportAllTrendsOnParticularSegment()
	{
		final I_C_BPartner bpartner = createBPartner("BP1");
		final I_M_Product product = createM_Product("P1");
		final I_PMM_Product pmmProduct = createPMM_Product(product, createASI(), bpartner);
		//
		final Date dateWeek = date(2016, 03, 28);

		for (final String pmmTrend : ALL_TRENDS)
		{
			final SyncWeeklySupply syncWeeklySupply = createWeeklySupply(pmmProduct, bpartner, dateWeek);
			syncWeeklySupply.setTrend(pmmTrend);
			reportAndProcess(syncWeeklySupply);

			//@formatter:off
			PMM_Week_Expectations.newExpectations()
					.setStrictMatching(true)
					.newExpectation().bpartner(bpartner).product(pmmProduct.getM_Product_ID()).asi(pmmProduct.getM_AttributeSetInstance_ID()).dateWeek(dateWeek).trend(pmmTrend).endExpectation()
					.assertExpected();
			//@formatter:on
		}
	}

	@Test
	public void test_ReportOnAllSegments()
	{
		final int count = 2;
		final List<I_C_BPartner> bpartners = createBPartners(count);
		final List<I_M_Product> products = createM_Products(count);
		final List<I_M_AttributeSetInstance> asis = createASIs(count);
		final List<Date> weekDates = createWeekDates(date(2016, 03, 28), count);

		PMM_Week_Expectations expectations = PMM_Week_Expectations.newExpectations();
		for (final I_C_BPartner bpartner : bpartners)
		{
			for (final I_M_Product product : products)
			{
				for (final I_M_AttributeSetInstance asi : asis)
				{
					for (final Date weekDate : weekDates)
					{
						PMM_Week_Expectations currentExpectations = null;
						for (final String pmmTrend : ALL_TRENDS)
						{
							final I_PMM_Product pmmProduct = createPMM_Product(product, asi, bpartner);
							final SyncWeeklySupply syncWeeklySupply = createWeeklySupply(pmmProduct, bpartner, weekDate);
							syncWeeklySupply.setTrend(pmmTrend);
							reportAndProcess(syncWeeklySupply);

							//@formatter:off
							currentExpectations = expectations.copy()
									.setStrictMatching(true)
									.newExpectation()
										.bpartner(bpartner)
										.product(pmmProduct.getM_Product_ID())
										.asi(pmmProduct.getM_AttributeSetInstance_ID())
										.dateWeek(weekDate)
										.trend(pmmTrend)
										.endExpectation()
									.assertExpected();
							//@formatter:on
						}

						expectations = currentExpectations;
					}
				}
			}
		}
	}

	@Test
	public void test_EventCollission()
	{
		final I_C_BPartner bpartner = createBPartner("BP1");
		final I_M_Product product = createM_Product("P1");
		final I_PMM_Product pmmProduct = createPMM_Product(product, createASI(), bpartner);
		final Date weekDate = date(2016, 03, 28);

		final SyncWeeklySupply syncWeeklySupply1 = createWeeklySupply(pmmProduct, bpartner, weekDate);
		syncWeeklySupply1.setTrend(X_PMM_WeekReport_Event.PMM_TREND_Up);
		serverSyncBL.reportWeekSupply(SyncWeeklySupplyRequest.of(syncWeeklySupply1));

		final SyncWeeklySupply syncWeeklySupply2 = createWeeklySupply(pmmProduct, bpartner, weekDate);
		syncWeeklySupply2.setTrend(X_PMM_WeekReport_Event.PMM_TREND_Down);
		serverSyncBL.reportWeekSupply(SyncWeeklySupplyRequest.of(syncWeeklySupply2));

		//
		// Mark all previously reported events as processed, to prevent them from processing
		final List<I_PMM_WeekReport_Event> lockedEvents = new ArrayList<>();
		for (final I_PMM_WeekReport_Event event : POJOLookupMap.get().getRecords(I_PMM_WeekReport_Event.class))
		{
			event.setProcessed(true);
			InterfaceWrapperHelper.save(event);
		}

		// Process all events
		// Expectation: nothing processed because all events are already locked
		PMMWeekReportEventsProcessor.newInstance().processAll();
		PMM_Week_Expectations.newExpectations()
				.setStrictMatching(true)
				.assertExpected();

		//
		// Report another event and process all
		// Expectation: only the last event shall be processed because the others are locked
		final SyncWeeklySupply syncWeeklySupply3 = createWeeklySupply(pmmProduct, bpartner, weekDate);
		syncWeeklySupply3.setTrend(X_PMM_WeekReport_Event.PMM_TREND_Zero);
		serverSyncBL.reportWeekSupply(SyncWeeklySupplyRequest.of(syncWeeklySupply3));
		PMMWeekReportEventsProcessor.newInstance().processAll();
		//
		//@formatter:off
		final PMM_Week_Expectations lastExpectations = PMM_Week_Expectations.newExpectations()
				.setStrictMatching(true)
				.newExpectation().bpartner(bpartner).product(pmmProduct.getM_Product_ID()).asi(pmmProduct.getM_AttributeSetInstance_ID()).dateWeek(weekDate).trend(X_PMM_WeekReport_Event.PMM_TREND_Zero).endExpectation()
				.assertExpected();
		//@formatter:on

		//
		// Unlock all other events
		for (final I_PMM_WeekReport_Event event : lockedEvents)
		{
			event.setProcessed(false);
			InterfaceWrapperHelper.save(event);
		}

		//
		// Process all events again
		// Expectation: aggregations shall look the same because the events which we processed were actually reported BEFORE our last event
		PMMWeekReportEventsProcessor.newInstance().processAll();
		lastExpectations.assertExpected();
		assertAllEventsProcessed();
	}

	private SyncWeeklySupply createWeeklySupply(final I_PMM_Product pmmProduct, final I_C_BPartner bpartner, final Date weekDay)
	{
		final SyncWeeklySupply syncWeeklySupply = new SyncWeeklySupply();
		syncWeeklySupply.setBpartner_uuid(SyncUUIDs.toUUIDString(bpartner));
		syncWeeklySupply.setProduct_uuid(SyncUUIDs.toUUIDString(pmmProduct));
		syncWeeklySupply.setWeekDay(weekDay);
		syncWeeklySupply.setVersion(nextSyncWeeklySupplyVersion++);

		return syncWeeklySupply;
	}

	private void reportAndProcess(final SyncWeeklySupply syncWeeklySupply)
	{
		serverSyncBL.reportWeekSupply(SyncWeeklySupplyRequest.of(syncWeeklySupply));

		//
		// Process all events
		PMMWeekReportEventsProcessor.newInstance().processAll();
	}

	private I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		bpartner.setAD_Org_ID(AD_Org_ID_1);
		bpartner.setValue(name);
		bpartner.setName(name);
		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	private List<I_C_BPartner> createBPartners(final int count)
	{
		final List<I_C_BPartner> bpartners = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			bpartners.add(createBPartner("BP" + i));
		}
		return bpartners;
	}

	private I_M_Product createM_Product(final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_ThreadInherited);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private List<I_M_Product> createM_Products(final int count)
	{
		final List<I_M_Product> products = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			products.add(createM_Product("P" + i));
		}
		return products;
	}

	private I_PMM_Product createPMM_Product(final I_M_Product product, final I_M_AttributeSetInstance asi, final I_C_BPartner bpartner)
	{
		final I_PMM_Product pmmProduct = InterfaceWrapperHelper.create(Env.getCtx(), I_PMM_Product.class, ITrx.TRXNAME_ThreadInherited);
		pmmProduct.setM_Product(product);
		pmmProduct.setM_AttributeSetInstance(asi);
		pmmProduct.setC_BPartner(bpartner);

		InterfaceWrapperHelper.save(pmmProduct);
		return pmmProduct;
	}

	private I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(Env.getCtx(), I_M_AttributeSetInstance.class, ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.save(asi);

		asi.setDescription("ASI=" + asi.getM_AttributeSetInstance_ID());
		InterfaceWrapperHelper.save(asi);

		return asi;
	}

	private List<I_M_AttributeSetInstance> createASIs(final int count)
	{
		final List<I_M_AttributeSetInstance> asis = new ArrayList<>();
		for (int i = 0; i <= count; i++)
		{
			if (i == 0)
			{
				asis.add(null);
			}
			else
			{
				asis.add(createASI());
			}
		}
		return asis;
	}

	private Date date(final int year, final int month, final int day)
	{
		return TimeUtil.getDay(year, month, day);
	}

	private List<Date> createWeekDates(final Date firstWeekDate, final int count)
	{
		final List<Date> weekDates = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			final Date weekDate = TimeUtil.addDays(firstWeekDate, i * 7);
			weekDates.add(weekDate);
		}
		return weekDates;
	}

	public void assertAllEventsProcessed()
	{
		for (final I_PMM_WeekReport_Event event : POJOLookupMap.get().getRecords(I_PMM_WeekReport_Event.class))
		{
			Assert.assertEquals("Processed: "+event, true, event.isProcessed());
		}
	}

}
