package de.metas.procurement.base.balance;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import de.metas.util.Services;

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

@Ignore
public abstract class AbstractBalanceScenariosTest
{
	private static final boolean STRICTMATCHING_Yes = true;
	private static final boolean STRICTMATCHING_No = false;

	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private final Random random = new Random();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_standardCase()
	{
		final List<Integer> bpartnerIds = Arrays.asList(1, 2);
		final List<Integer> productIds = Arrays.asList(1, 2);
		final List<Integer> asiIds = Arrays.asList(1, 2);
		final List<Integer> flatrateDataEntryIds = Arrays.asList(1, -1);

		boolean strictMatching = STRICTMATCHING_Yes;
		for (final Integer bpartnerId : bpartnerIds)
		{
			for (final Integer productId : productIds)
			{
				for (final Integer asiId : asiIds)
				{
					for (final Integer flatrateDataEntryId : flatrateDataEntryIds)
					{
						final PMMBalanceSegment segment = segmentBuilder()
								.setC_BPartner_ID(bpartnerId)
								.setM_Product_ID(productId)
								.setM_AttributeSetInstance_ID(asiId)
								// .setM_HU_PI_Item_Product_ID(random.nextInt(1000000)) // does not matter
								.setC_Flatrate_DataEntry_ID(flatrateDataEntryId)
								.build();
						test_standardCase(segment, strictMatching);

						strictMatching = STRICTMATCHING_No; // after first run we shall not do strict matching
					}
				}
			}
		}
	}

	public void test_standardCase(final PMMBalanceSegment segment, final boolean strictMatching)
	{
		System.out.println("Testing for segment: " + segment + ", strictMatching=" + strictMatching);

		//
		// Add QtyPromised=10/20
		addQtyPromised(segment, TimeUtil.getDay(2016, 03, 25), new BigDecimal("10"), new BigDecimal("20"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(10, 20).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(10, 20).endExpectation()
				.assertExpected();

		//
		// Add QtyPromised=1/2
		addQtyPromised(segment, TimeUtil.getDay(2016, 03, 25), new BigDecimal("1"), new BigDecimal("2"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(11, 22).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(11, 22).endExpectation()
				.assertExpected();

		//
		// Add QtyOrdered=5/6
		addQtyOrdered(segment, TimeUtil.getDay(2016, 03, 25), new BigDecimal("5"), new BigDecimal("6"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(11, 22).setQtyOrdered(5, 6).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(11, 22).setQtyOrdered(5, 6).endExpectation()
				.assertExpected();

		//
		// Add QtyOrdered=100/200
		addQtyOrdered(segment, TimeUtil.getDay(2016, 03, 25), new BigDecimal("100"), new BigDecimal("200"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(11, 22).setQtyOrdered(105, 206).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(11, 22).setQtyOrdered(105, 206).endExpectation()
				.assertExpected();

		//
		// Add QtyPromised=1000/2000 to next week
		addQtyPromised(segment, TimeUtil.getDay(2016, 03, 30), new BigDecimal("1000"), new BigDecimal("2000"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(11, 22).setQtyOrdered(105, 206).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 28).setQtyPromised(1000, 2000).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(1011, 2022).setQtyOrdered(105, 206).endExpectation()
				.assertExpected();

		//
		// Add QtyOrdered=100/200
		subtractQtyOrdered(segment, TimeUtil.getDay(2016, 03, 25), new BigDecimal("1"), new BigDecimal("1"));
		PMM_Balance_Expectations.newExpectations()
				.setStrictMatching(strictMatching)
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 21).setQtyPromised(11, 22).setQtyOrdered(104, 205).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setDateWeek(2016, 03, 28).setQtyPromised(1000, 2000).endExpectation()
				.newExpectation().setSegmentFrom(segment).setDateMonth(2016, 03, 01).setQtyPromised(1011, 2022).setQtyOrdered(104, 205).endExpectation()
				.assertExpected();
	}

	protected abstract void addQtyPromised(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyPromised, final BigDecimal qtyPromisedTU);

	protected abstract void addQtyOrdered(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU);

	protected void subtractQtyOrdered(final PMMBalanceSegment segment, final Date date, final BigDecimal qtyOrdered, final BigDecimal qtyOrderedTU)
	{
		addQtyOrdered(segment, date, qtyOrdered.negate(), qtyOrderedTU.negate());
	}

	public PMMBalanceSegment.Builder segmentBuilder()
	{
		return PMMBalanceSegment.builder();
	}

	public PMMBalanceChangeEvent.Builder eventBuilder(final PMMBalanceSegment segment)
	{
		return PMMBalanceChangeEvent.builder()
				.setC_BPartner_ID(segment.getC_BPartner_ID())
				.setM_Product_ID(segment.getM_Product_ID())
				.setM_AttributeSetInstance_ID(segment.getM_AttributeSetInstance_ID())
				.setM_HU_PI_Item_Product_ID(random.nextInt(1000000)) // does not matter
				.setC_Flatrate_DataEntry_ID(segment.getC_Flatrate_DataEntry_ID())
				//
				;
	}

	protected void processEvent(final PMMBalanceChangeEvent event)
	{
		Services.get(IPMMBalanceChangeEventProcessor.class).addEvent(event);
	}

}
