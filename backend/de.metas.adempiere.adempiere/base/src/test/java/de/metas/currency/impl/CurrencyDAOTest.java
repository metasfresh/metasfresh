package de.metas.currency.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	private CurrencyConversionTypeId conversionTypeId_Spot;
	private CurrencyConversionTypeId conversionTypeId_Company;
	private CurrencyConversionTypeId conversionTypeId_PeriodEnd;

	@Before
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		adempiereTestHelper.setupContext_AD_Client_IfNotSet();
		ctx = Env.getCtx();

		currencyDAO = Services.get(ICurrencyDAO.class);

		conversionTypeId_Spot = currencyDAO.getConversionTypeId(ConversionTypeMethod.Spot);
		conversionTypeId_Company = currencyDAO.getConversionTypeId(ConversionTypeMethod.Company);
		conversionTypeId_PeriodEnd = currencyDAO.getConversionTypeId(ConversionTypeMethod.PeriodEnd);
	}

	@Test
	public void test()
	{
		clearConversionTypeDefaults();
		createConversionTypeDefault(conversionTypeId_Spot, LocalDate.of(1970, 1, 1));
		createConversionTypeDefault(conversionTypeId_Company, LocalDate.of(2016, 1, 1));
		createConversionTypeDefault(conversionTypeId_PeriodEnd, LocalDate.of(2017, 1, 1));

		assertNoDefaultConversionType(LocalDate.of(1969, 12, 31));

		assertDefaultConversionType(conversionTypeId_Spot, LocalDate.of(1970, 1, 1));
		assertDefaultConversionType(conversionTypeId_Spot, LocalDate.of(2015, 1, 1));
		assertDefaultConversionType(conversionTypeId_Spot, LocalDate.of(2015, 12, 31));

		assertDefaultConversionType(conversionTypeId_Company, LocalDate.of(2016, 1, 1));
		assertDefaultConversionType(conversionTypeId_Company, LocalDate.of(2016, 5, 1));
		assertDefaultConversionType(conversionTypeId_Company, LocalDate.of(2016, 12, 31));

		assertDefaultConversionType(conversionTypeId_PeriodEnd, LocalDate.of(2017, 1, 1));
		assertDefaultConversionType(conversionTypeId_PeriodEnd, LocalDate.of(2020, 1, 1));
	}

	private final void clearConversionTypeDefaults()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType_Default.class, ctx, ITrx.TRXNAME_None)
				.create()
				.deleteDirectly();
	}

	private final void createConversionTypeDefault(
			final CurrencyConversionTypeId conversionTypeId,
			final LocalDate validFrom)
	{
		final I_C_ConversionType_Default record = newInstanceOutOfTrx(I_C_ConversionType_Default.class);
		record.setC_ConversionType_ID(conversionTypeId.getRepoId());
		record.setValidFrom(TimeUtil.asTimestamp(validFrom));
		InterfaceWrapperHelper.save(record);
	}

	private final void assertDefaultConversionType(final CurrencyConversionTypeId expectedConversionTypeId, final LocalDate date)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);

		final CurrencyConversionTypeId actualConversionTypeId = currencyDAO.getDefaultConversionTypeId(adClientId, adOrgId, date);

		final String info = "AD_Client_ID=" + adClientId + ", AD_Org_ID=" + adOrgId
				+ ", date=" + date
				+ ", expectedConversionType=" + expectedConversionTypeId
				+ ", actualConversionType=" + actualConversionTypeId
		//
		;

		Assert.assertEquals("Invalid conversion type -- " + info, expectedConversionTypeId, actualConversionTypeId);
	}

	private final void assertNoDefaultConversionType(final LocalDate date)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);

		try
		{
			final CurrencyConversionTypeId actualConversionTypeId = currencyDAO.getDefaultConversionTypeId(adClientId, adOrgId, date);
			Assert.fail("We assume there is no default conversion type for " + date + " but we got " + actualConversionTypeId);
		}
		catch (final AdempiereException e)
		{
			// fine, this is what we expected.
		}

	}
}
