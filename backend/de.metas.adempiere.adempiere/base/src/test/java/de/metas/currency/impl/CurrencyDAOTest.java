package de.metas.currency.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

@ExtendWith(AdempiereTestWatcher.class)
public class CurrencyDAOTest
{
	private Properties ctx;
	/**
	 * service under test
	 */
	private ICurrencyDAO currencyDAO;

	private CurrencyConversionTypeId conversionTypeId_Spot;
	private CurrencyConversionTypeId conversionTypeId_Company;
	private CurrencyConversionTypeId conversionTypeId_PeriodEnd;

	private final ZoneId zoneId = ZoneId.of("Europe/Berlin");

	@BeforeEach
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
		ctx = Env.getCtx();

		SystemTime.setFixedTimeSource(LocalDate.parse("2022-11-12").atStartOfDay(zoneId));

		currencyDAO = Services.get(ICurrencyDAO.class);

		conversionTypeId_Spot = currencyDAO.getConversionTypeId(ConversionTypeMethod.Spot);
		conversionTypeId_Company = currencyDAO.getConversionTypeId(ConversionTypeMethod.Company);
		conversionTypeId_PeriodEnd = currencyDAO.getConversionTypeId(ConversionTypeMethod.PeriodEnd);
	}

	private Instant instant(final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(zoneId).toInstant();
	}

	@Test
	public void test()
	{
		clearConversionTypeDefaults();
		createConversionTypeDefault(conversionTypeId_Spot, instant("1970-01-01"));
		createConversionTypeDefault(conversionTypeId_Company, instant("2016-01-01"));
		createConversionTypeDefault(conversionTypeId_PeriodEnd, instant("2017-01-01"));

		assertNoDefaultConversionType(instant("1969-12-31"));

		assertDefaultConversionType(conversionTypeId_Spot, instant("1970-01-01"));
		assertDefaultConversionType(conversionTypeId_Spot, instant("2015-01-01"));
		assertDefaultConversionType(conversionTypeId_Spot, instant("2015-12-31"));

		assertDefaultConversionType(conversionTypeId_Company, instant("2016-01-01"));
		assertDefaultConversionType(conversionTypeId_Company, instant("2016-05-01"));
		assertDefaultConversionType(conversionTypeId_Company, instant("2016-12-31"));

		assertDefaultConversionType(conversionTypeId_PeriodEnd, instant("2017-01-01"));
		assertDefaultConversionType(conversionTypeId_PeriodEnd, instant("2020-01-01"));
	}

	private void clearConversionTypeDefaults()
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType_Default.class, ctx, ITrx.TRXNAME_None)
				.create()
				.deleteDirectly();
	}

	private void createConversionTypeDefault(
			final CurrencyConversionTypeId conversionTypeId,
			final Instant validFrom)
	{
		final I_C_ConversionType_Default record = newInstanceOutOfTrx(I_C_ConversionType_Default.class);
		record.setC_ConversionType_ID(conversionTypeId.getRepoId());
		record.setValidFrom(TimeUtil.asTimestamp(validFrom));
		InterfaceWrapperHelper.save(record);
	}

	private void assertDefaultConversionType(final CurrencyConversionTypeId expectedConversionTypeId, final Instant date)
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

		assertThat(actualConversionTypeId).as("Invalid conversion type -- " + info).isEqualTo(expectedConversionTypeId);
	}

	private void assertNoDefaultConversionType(final Instant date)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);

		assertThatThrownBy(() -> currencyDAO.getDefaultConversionTypeId(adClientId, adOrgId, date))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("NotFound C_ConversionType_ID");
	}
}
