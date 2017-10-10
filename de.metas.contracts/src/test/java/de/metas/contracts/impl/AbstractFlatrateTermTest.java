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
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_Year;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.interfaces.I_M_Warehouse;
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
	private final String sequence = "@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@";
	
	public FlatrateTermTestHelper helper;
	
	private I_C_Calendar calendar;
	private I_C_AcctSchema acctSchema;
	private I_C_Country country;

	public I_C_Calendar getCalendar()
	{
		return calendar;
	}
	
	public I_C_AcctSchema getAcctSchema()
	{
		return acctSchema;
	}
	
	public I_C_Country getCountry()
	{
		return country;
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
		final POJOLookupMap db = POJOLookupMap.get();
		db.clear();

		setupMasterData();
		initialize();
	}

	
	protected void initialize()
	{
	}

	protected FlatrateTermTestHelper createFlatrateTermTestHelper()
	{
		return new FlatrateTermTestHelper();
	}

	protected void setupMasterData()
	{
		helper = createFlatrateTermTestHelper();
		createCalendar();
		createAcctSchema();
		createTax();
		createWarehouse();
		createDocType();
		createCountry();
	}

	private void createCalendar()
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
	
	
	private void createAcctSchema()
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
	
	private void createTax()
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
	
	private void createWarehouse()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setName("WH" );
		warehouse.setAD_Org(helper.getOrg());
		save(warehouse);
	}

	private void createDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setAD_Org(helper.getOrg());
		docType.setDocSubType(I_C_DocType.DocSubType_Abonnement);
		docType.setDocBaseType(I_C_DocType.DocBaseType_CustomerContract);
		save(docType);
	}
	
	private void createCountry()
	{
		country = newInstance(I_C_Country.class);
		country.setAD_Org(helper.getOrg());
		country.setAD_Language("de_DE");
		country.setCountryCode("DE");
		country.setDisplaySequence(sequence);
		country.setDisplaySequenceLocal(sequence);
		country.setCaptureSequence(sequence);
		save(country);
	}

}
