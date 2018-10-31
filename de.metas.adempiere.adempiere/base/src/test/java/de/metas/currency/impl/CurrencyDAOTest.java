package de.metas.currency.impl;

import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class CurrencyDAOTest
{
	private Properties ctx;
	/** service under test */
	private ICurrencyDAO currencyDAO;

	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private I_C_ConversionType conversionType_Spot;
	private I_C_ConversionType conversionType_Company;
	private I_C_ConversionType conversionType_PeriodEnd;

	@Before
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		adempiereTestHelper.setupContext_AD_Client_IfNotSet();
		ctx = Env.getCtx();

		currencyDAO = Services.get(ICurrencyDAO.class);

		conversionType_Spot = currencyDAO.retrieveConversionType(ctx, ConversionType.Spot);
		conversionType_Company = currencyDAO.retrieveConversionType(ctx, ConversionType.Company);
		conversionType_PeriodEnd = currencyDAO.retrieveConversionType(ctx, ConversionType.PeriodEnd);
	}

	@Test
	public void test()
	{
		clearConversionTypeDefaults();
		createConversionTypeDefault(conversionType_Spot, TimeUtil.getDay(1970, 1, 1));
		createConversionTypeDefault(conversionType_Company, TimeUtil.getDay(2016, 1, 1));
		createConversionTypeDefault(conversionType_PeriodEnd, TimeUtil.getDay(2017, 1, 1));

		assertNoDefaultConversionType(TimeUtil.getDay(1969, 12, 31));

		assertDefaultConversionType(conversionType_Spot, TimeUtil.getDay(1970, 1, 1));
		assertDefaultConversionType(conversionType_Spot, TimeUtil.getDay(2015, 1, 1));
		assertDefaultConversionType(conversionType_Spot, TimeUtil.getDay(2015, 12, 31));

		assertDefaultConversionType(conversionType_Company, TimeUtil.getDay(2016, 1, 1));
		assertDefaultConversionType(conversionType_Company, TimeUtil.getDay(2016, 5, 1));
		assertDefaultConversionType(conversionType_Company, TimeUtil.getDay(2016, 12, 31));

		assertDefaultConversionType(conversionType_PeriodEnd, TimeUtil.getDay(2017, 1, 1));
		assertDefaultConversionType(conversionType_PeriodEnd, TimeUtil.getDay(2020, 1, 1));
	}

	private final void clearConversionTypeDefaults()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType_Default.class, ctx, ITrx.TRXNAME_None)
				.create()
				.deleteDirectly();
	}

	private final I_C_ConversionType createConversionTypeDefault(final I_C_ConversionType conversionType, final Date validFrom)
	{
		final I_C_ConversionType_Default conversionTypeDefault = InterfaceWrapperHelper.create(ctx, I_C_ConversionType_Default.class, ITrx.TRXNAME_None);
		conversionTypeDefault.setC_ConversionType(conversionType);
		conversionTypeDefault.setValidFrom(TimeUtil.asTimestamp(validFrom));
		InterfaceWrapperHelper.save(conversionTypeDefault);

		return conversionType;
	}

	private final void assertDefaultConversionType(final I_C_ConversionType expectedConversionType, final Date date)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		final I_C_ConversionType actualConversionType = currencyDAO.retrieveDefaultConversionType(ctx, adClientId, adOrgId, date);

		final String info = "AD_Client_ID=" + adClientId + ", AD_Org_ID=" + adOrgId
				+ ", date=" + date
				+ ", expectedConversionType=" + (expectedConversionType == null ? null : expectedConversionType.getValue())
				+ ", actualConversionType=" + (actualConversionType == null ? null : actualConversionType.getValue())
		//
		;

		Assert.assertEquals("Invalid conversion type -- " + info, expectedConversionType.getC_ConversionType_ID(), actualConversionType.getC_ConversionType_ID());
	}

	private final void assertNoDefaultConversionType(final Date date)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		try
		{
			final I_C_ConversionType actualConversionType = currencyDAO.retrieveDefaultConversionType(ctx, adClientId, adOrgId, date);
			Assert.fail("We assume there is no default conversion type for " + date + " but we got " + actualConversionType);
		}
		catch (final AdempiereException e)
		{
			// fine, this is what we expected.
		}

	}
}
