package de.metas.procurement.base.event.impl;

import java.util.Date;

import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_C_BPartner;
import org.junit.Assert;

import de.metas.procurement.base.model.I_PMM_Week;

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

public class PMM_Week_Expectation
{
	Integer C_BPartner_ID;
	Integer M_Product_ID;
	Integer M_AttributeSetInstance_ID;
	//
	Date dateWeek;
	//
	String PMM_Trend;

	@ToStringBuilder(skip = true)
	private final PMM_Week_Expectations parent;

	PMM_Week_Expectation(final PMM_Week_Expectations parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public PMM_Week_Expectations endExpectation()
	{
		return parent;
	}

	public PMM_Week_Expectation copy(final PMM_Week_Expectations newParent)
	{
		final PMM_Week_Expectation expectationNew = new PMM_Week_Expectation(newParent);
		expectationNew.C_BPartner_ID = C_BPartner_ID;
		expectationNew.M_Product_ID = M_Product_ID;
		expectationNew.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		expectationNew.dateWeek = (Date)(dateWeek == null ? null : dateWeek.clone());
		expectationNew.PMM_Trend = PMM_Trend;
		return expectationNew;
	}

	public PMM_Week_Expectation assertExpected(final String msg, final I_PMM_Week actual)
	{
		final String msgToUse = "" + msg
				+ "\n Actual: " + actual
				+ "\n Expectation: " + this;

		Assert.assertNotNull("exists - " + msgToUse, actual);
		Assert.assertEquals("C_BPartner_ID - " + msgToUse, C_BPartner_ID, (Integer)actual.getC_BPartner_ID());
		Assert.assertEquals("M_Product_ID - " + msgToUse, M_Product_ID, (Integer)actual.getM_Product_ID());
		Assert.assertEquals("M_AttributeSetInstance_ID - " + msgToUse, M_AttributeSetInstance_ID, (Integer)actual.getM_AttributeSetInstance_ID());

		Assert.assertEquals("DateWeek - " + msgToUse, dateWeek, actual.getWeekDate());

		Assert.assertEquals("PMM_Trend - " + msgToUse, PMM_Trend, actual.getPMM_Trend());

		return this;
	}

	public PMM_Week_Expectation setC_BPartner_ID(final int c_BPartner_ID)
	{
		C_BPartner_ID = c_BPartner_ID;
		return this;
	}

	public PMM_Week_Expectation bpartner(final int c_BPartner_ID)
	{
		setC_BPartner_ID(c_BPartner_ID);
		return this;
	}

	public PMM_Week_Expectation bpartner(final I_C_BPartner bpartner)
	{
		setC_BPartner_ID(bpartner.getC_BPartner_ID());
		return this;
	}

	public PMM_Week_Expectation setM_Product_ID(final int m_Product_ID)
	{
		M_Product_ID = m_Product_ID;
		return this;
	}

	public PMM_Week_Expectation product(final int m_Product_ID)
	{
		setM_Product_ID(m_Product_ID);
		return this;
	}

	public PMM_Week_Expectation asi(final int m_AttributeSetInstance_ID)
	{
		M_AttributeSetInstance_ID = m_AttributeSetInstance_ID;
		return this;
	}

	public PMM_Week_Expectation dateWeek(final Date dateWeek)
	{
		this.dateWeek = dateWeek;
		return this;
	}

	public PMM_Week_Expectation trend(final String pMM_Trend)
	{
		PMM_Trend = pMM_Trend;
		return this;
	}
}
