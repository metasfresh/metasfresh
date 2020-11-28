package de.metas.procurement.base.balance;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.TimeUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.procurement.base.model.I_PMM_Balance;

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

public class PMM_Balance_Expectation
{
	Integer C_BPartner_ID;
	Integer M_Product_ID;
	Integer M_AttributeSetInstance_ID;
	Integer M_HU_PI_Item_Product_ID;
	Integer C_Flatrate_DataEntry_ID;
	Date dateMonth;
	Date dateWeek;
	BigDecimal qtyPromised = BigDecimal.ZERO;
	BigDecimal qtyPromised_TU = BigDecimal.ZERO;
	BigDecimal qtyOrdered = BigDecimal.ZERO;
	BigDecimal qtyOrdered_TU = BigDecimal.ZERO;

	@ToStringBuilder(skip = true)
	private final PMM_Balance_Expectations parent;

	PMM_Balance_Expectation(final PMM_Balance_Expectations parent)
	{
		super();
		this.parent = parent;
	}

	public PMM_Balance_Expectation assertExpected(final String msg, final I_PMM_Balance actualBalance)
	{
		final String msgToUse = "" + msg 
				+ "\n Actual: " + actualBalance
				+ "\n Expectation: " + this;

		Assert.assertNotNull("exists - " + msgToUse, actualBalance);
		Assert.assertEquals("C_BPartner_ID - " + msgToUse, C_BPartner_ID, (Integer)actualBalance.getC_BPartner_ID());
		Assert.assertEquals("M_Product_ID - " + msgToUse, M_Product_ID, (Integer)actualBalance.getM_Product_ID());
		Assert.assertEquals("M_AttributeSetInstance_ID - " + msgToUse, M_AttributeSetInstance_ID, (Integer)actualBalance.getM_AttributeSetInstance_ID());
		
		final Integer actualM_HU_PI_Item_Product_ID = actualBalance.getM_HU_PI_Item_Product_ID() > 0 ? actualBalance.getM_HU_PI_Item_Product_ID() : null;
		Assert.assertEquals("M_HU_PI_Item_Product_ID - " + msgToUse, M_HU_PI_Item_Product_ID, actualM_HU_PI_Item_Product_ID);

		final Integer actual_C_Flatrate_DataEntry_ID = actualBalance.getC_Flatrate_DataEntry_ID() > 0 ? actualBalance.getC_Flatrate_DataEntry_ID() : null;
		Assert.assertEquals("C_Flatrate_DataEntry_ID - " + msgToUse, C_Flatrate_DataEntry_ID, actual_C_Flatrate_DataEntry_ID);

		//
		Assert.assertEquals("DateMonth - " + msgToUse, dateMonth, actualBalance.getMonthDate());
		Assert.assertEquals("DateWeek - " + msgToUse, dateWeek, actualBalance.getWeekDate());

		//
		Assert.assertThat("QtyPromised - " + msgToUse, actualBalance.getQtyPromised(), Matchers.comparesEqualTo(qtyPromised));
		Assert.assertThat("QtyPromised_TU - " + msgToUse, actualBalance.getQtyPromised_TU(), Matchers.comparesEqualTo(qtyPromised_TU));
		Assert.assertThat("QtyOrdered - " + msgToUse, actualBalance.getQtyOrdered(), Matchers.comparesEqualTo(qtyOrdered));
		Assert.assertThat("QtyOrdered_TU - " + msgToUse, actualBalance.getQtyOrdered_TU(), Matchers.comparesEqualTo(qtyOrdered_TU));

		return this;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public PMM_Balance_Expectations endExpectation()
	{
		return parent;
	}

	public PMM_Balance_Expectation setSegmentFrom(final PMMBalanceSegment segment)
	{
		setC_BPartner_ID(segment.getC_BPartner_ID());
		setM_Product_ID(segment.getM_Product_ID());
		setM_AttributeSetInstance_ID(segment.getM_AttributeSetInstance_ID());
		//setM_HU_PI_Item_Product_ID(segment.getM_HU_PI_Item_Product_ID());
		setC_Flatrate_DataEntry_ID(segment.getC_Flatrate_DataEntry_ID());

		return this;
	}

	public PMM_Balance_Expectation setC_BPartner_ID(final int C_BPartner_ID)
	{
		this.C_BPartner_ID = C_BPartner_ID;
		return this;
	}

	public PMM_Balance_Expectation setM_Product_ID(final int M_Product_ID)
	{
		this.M_Product_ID = M_Product_ID;
		return this;
	}
	
	public PMM_Balance_Expectation setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		return this;
	}

	public PMM_Balance_Expectation setM_HU_PI_Item_Product_ID(final int M_HU_PI_Item_Product_ID)
	{
		this.M_HU_PI_Item_Product_ID = M_HU_PI_Item_Product_ID;
		return this;
	}

	public PMM_Balance_Expectation setC_Flatrate_DataEntry_ID(final Integer C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID != null && C_Flatrate_DataEntry_ID > 0)
		{
			this.C_Flatrate_DataEntry_ID = C_Flatrate_DataEntry_ID;
		}
		else
		{
			this.C_Flatrate_DataEntry_ID = null;
		}
		return this;
	}

	public PMM_Balance_Expectation setDateMonth(final int year, final int month, final int day)
	{
		dateMonth = TimeUtil.getDay(year, month, day);
		return this;
	}

	public PMM_Balance_Expectation setDateWeek(final int year, final int month, final int day)
	{
		dateWeek = TimeUtil.getDay(year, month, day);
		return this;
	}

	public PMM_Balance_Expectation setQtyPromised(final int qtyPromised, final int qtyPromised_TU)
	{
		this.qtyPromised = BigDecimal.valueOf(qtyPromised);
		this.qtyPromised_TU = BigDecimal.valueOf(qtyPromised_TU);
		return this;
	}

	public PMM_Balance_Expectation setQtyOrdered(final int qtyOrdered, final int qtyOrdered_TU)
	{
		this.qtyOrdered = BigDecimal.valueOf(qtyOrdered);
		this.qtyOrdered_TU = BigDecimal.valueOf(qtyOrdered_TU);
		return this;
	}

}
