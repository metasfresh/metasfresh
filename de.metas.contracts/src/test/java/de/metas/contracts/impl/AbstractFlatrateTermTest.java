/**
 * 
 */
package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.impl.AcctSchemaDAO;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_Year;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.impl.TaxDAO;

/*
 * #%L
 * de.metas.contracts
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractFlatrateTermTest
{

	public FlatrateTermTestHelper helper;
	
	private I_C_Calendar calendar;

	public I_C_Calendar getCalendar()
	{
		return calendar;
	}
	
	private I_C_AcctSchema acctSchema;
	
	public I_C_AcctSchema getAcctSchema()
	{
		return acctSchema;
	}

	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	@Before
	public final void init()
	{
		setupMasterData();

		initialize();
	}

	/**
	 * Balled by the test's {@link Before} method, after the basic master data was set up.
	 */
	abstract protected void initialize();

	protected FlatrateTermTestHelper createFlatrateTermTestHelper()
	{
		return new FlatrateTermTestHelper();
	}

	protected void setupMasterData()
	{
		helper = createFlatrateTermTestHelper();
		prepareCalendar();
		prepareAcctSchema();
		prepareTax();
	}

	private void prepareCalendar()
	{
		calendar = newInstance(I_C_Calendar.class);
		save(calendar);

		final I_C_Year currentYear = newInstance(I_C_Year.class);
		currentYear.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(currentYear);

		for (int i = 1; i <= 12; i++)
		{
			final Timestamp startDate = TimeUtil.getDay(2017, i, 1);
			final Timestamp endDate = TimeUtil.getMonthLastDay(startDate);
			final I_C_Period period = newInstance(I_C_Period.class);
			period.setStartDate(startDate);
			period.setEndDate(endDate);
			period.setC_Year_ID(currentYear.getC_Year_ID());
			save(period);
		}


		final I_C_Year nextYear = newInstance(I_C_Year.class);
		nextYear.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(nextYear);
		
		for (int i = 1; i <= 12; i++)
		{
			final Timestamp startDate = TimeUtil.getDay(2018, i, 1);
			final Timestamp endDate = TimeUtil.getMonthLastDay(startDate);
			final I_C_Period period = newInstance(I_C_Period.class);
			period.setStartDate(startDate);
			period.setEndDate(endDate);
			period.setC_Year_ID(nextYear.getC_Year_ID());
			save(period);
		}

	}
	
	
	private void prepareAcctSchema()
	{
		acctSchema = newInstance(I_C_AcctSchema.class);
		save(acctSchema);
		
		Services.registerService(IAcctSchemaDAO.class, new AcctSchemaDAO()
		{
			@Override
			public I_C_AcctSchema retrieveAcctSchema(final Properties ctx, final int ad_Client_ID, final int ad_Org_ID)
			{
				return acctSchema;
			}
		});
	}
	
	private void prepareTax()
	{
		final I_C_Tax tax  = newInstance(I_C_Tax.class);
		save(tax);
		
		Services.registerService(ITaxDAO.class, new TaxDAO()
		{
			@Override
			public I_C_Tax retrieveNoTaxFound(Properties ctx)
			{
				return tax;
			}
		});
	}

}
