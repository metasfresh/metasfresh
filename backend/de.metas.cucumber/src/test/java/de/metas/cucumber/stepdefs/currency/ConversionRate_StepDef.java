/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.currency;

import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Conversion_Rate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Test helper: forces a 1:1 conversion rate between two currencies for a date range.
 * <p>
 * Used by accounting-report scenarios so the same feature passes regardless of whether the
 * AD_Client's accounting schema is in the same currency as the invoice or not — at rate 1
 * the view's acct-currency {@code taxamt} equals the source-currency value.
 */
public class ConversionRate_StepDef
{
	private static final int SYSTEM_ORG_ID = 0;

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	@And("a 1:1 {string} <-> {string} conversion rate is in place between {string} and {string}")
	public void insert_one_to_one_rate(
			@NonNull final String fromIsoCode,
			@NonNull final String toIsoCode,
			@NonNull final String validFromStr,
			@NonNull final String validToStr)
	{
		final CurrencyConversionTypeId spotTypeId = currencyDAO.getConversionTypeId(ConversionTypeMethod.Spot);
		final CurrencyId fromId = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(fromIsoCode)).getId();
		final CurrencyId toId = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(toIsoCode)).getId();
		final Timestamp validFrom = Timestamp.valueOf(LocalDate.parse(validFromStr).atStartOfDay());
		final Timestamp validTo = Timestamp.valueOf(LocalDate.parse(validToStr).atStartOfDay());

		createRate(spotTypeId, fromId, toId, validFrom, validTo);
		createRate(spotTypeId, toId, fromId, validFrom, validTo);
	}

	private static void createRate(
			@NonNull final CurrencyConversionTypeId conversionTypeId,
			@NonNull final CurrencyId fromId,
			@NonNull final CurrencyId toId,
			@NonNull final Timestamp validFrom,
			@NonNull final Timestamp validTo)
	{
		final I_C_Conversion_Rate rate = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		rate.setAD_Org_ID(SYSTEM_ORG_ID);
		rate.setC_ConversionType_ID(conversionTypeId.getRepoId());
		rate.setC_Currency_ID(fromId.getRepoId());
		rate.setC_Currency_ID_To(toId.getRepoId());
		rate.setValidFrom(validFrom);
		rate.setValidTo(validTo);
		rate.setMultiplyRate(BigDecimal.ONE);
		rate.setDivideRate(BigDecimal.ONE);
		InterfaceWrapperHelper.saveRecord(rate);
	}
}
