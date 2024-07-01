package de.metas.currency.impl;

import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

/**
 * Plain {@link ICurrencyDAO} implementation, exclusively to be used for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PlainCurrencyDAO extends CurrencyDAO
{
	public PlainCurrencyDAO()
	{
		Adempiere.assertUnitTestMode();

		createDefaultConversionTypes();
	}

	/**
	 * Creates all {@link I_C_ConversionType}s (from {@link ConversionTypeMethod}) and sets the {@link ConversionTypeMethod#Spot} as default.
	 */
	private void createDefaultConversionTypes()
	{
		Adempiere.assertUnitTestMode();

		final Properties ctx = Env.getCtx();
		for (final ConversionTypeMethod type : ConversionTypeMethod.values())
		{
			final I_C_ConversionType conversionType = InterfaceWrapperHelper.create(ctx, I_C_ConversionType.class, ITrx.TRXNAME_None);
			InterfaceWrapperHelper.setValue(conversionType, I_C_ConversionType.COLUMNNAME_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System);
			conversionType.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_System);
			conversionType.setValue(type.getCode());
			conversionType.setName(type.toString());
			InterfaceWrapperHelper.save(conversionType);

			if (type == ConversionTypeMethod.Spot)
			{
				final I_C_ConversionType_Default conversionTypeDefault = InterfaceWrapperHelper.newInstance(I_C_ConversionType_Default.class, conversionType, true);
				conversionTypeDefault.setC_ConversionType(conversionType);
				conversionTypeDefault.setValidFrom(TimeUtil.getDay(1970, 1, 1));
				InterfaceWrapperHelper.save(conversionTypeDefault);
			}
		}
	}

	/**
	 * Creates/Updates an {@link I_C_Conversion_Rate} using given parameters and following defaults:
	 * <ul>
	 * <li>ValidFrom: 1970-01-01
	 * <li>ConversionType: default
	 * <li>Client/Org: from {@link Env#getCtx()}
	 * </ul>
	 */
	public void setRate(
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId,
			@NonNull final BigDecimal rate)
	{
		Adempiere.assertUnitTestMode();

		final Properties ctx = Env.getCtx();
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(ctx));
		final OrgId orgId = OrgId.ofRepoId(Env.getAD_Org_ID(ctx));
		final Instant date = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
		final CurrencyConversionTypeId conversionTypeId = getDefaultConversionTypeId(clientId, orgId, date);
		final CurrencyConversionContext conversionCtx = CurrencyConversionContext.builder()
				.clientId(clientId)
				.orgId(orgId)
				.conversionTypeId(conversionTypeId)
				.conversionDate(date)
				.build();

		I_C_Conversion_Rate conversionRate = retrieveRateQuery(conversionCtx, currencyFromId, currencyToId)
				.create()
				.first();
		if (conversionRate == null)
		{
			conversionRate = newInstanceOutOfTrx(I_C_Conversion_Rate.class);
			conversionRate.setAD_Org_ID(orgId.getRepoId());
			conversionRate.setC_ConversionType_ID(conversionTypeId.getRepoId());
			conversionRate.setC_Currency_ID(currencyFromId.getRepoId());
			conversionRate.setC_Currency_ID_To(currencyToId.getRepoId());
			// FIXME: this one is not working due a bug in POJOWrapper or because it's not respecting the standard naming conventions (i.e. C_Currency_To_ID)
			// conversionRate.setC_Currency_To(currencyTo);
			conversionRate.setValidFrom(TimeUtil.asTimestamp(date));
		}

		conversionRate.setMultiplyRate(rate);
		conversionRate.setDivideRate(null); // not used

		InterfaceWrapperHelper.save(conversionRate);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * If the currency was not found, this method is automatically creating it.
	 */
	@Override
	public Currency getByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		Adempiere.assertUnitTestMode();

		return getOrCreateByCurrencyCode(currencyCode);
	}

	public Currency getOrCreateByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		Adempiere.assertUnitTestMode();

		return getCurrenciesMap()
				.getByCurrencyCodeIfExists(currencyCode)
				.orElseGet(() -> createCurrency(currencyCode));
	}

	public static CurrencyId createCurrencyId(@NonNull final CurrencyCode currencyCode)
	{
		Adempiere.assertUnitTestMode();

		return createCurrency(currencyCode).getId();
	}

	public static Currency createCurrency(@NonNull final CurrencyCode currencyCode)
	{
		Adempiere.assertUnitTestMode();

		return prepareCurrency()
				.currencyCode(currencyCode)
				.build();
	}

	public static Currency createCurrency(
			@NonNull final CurrencyCode currencyCode,
			@NonNull final CurrencyPrecision precision)
	{
		Adempiere.assertUnitTestMode();

		return prepareCurrency()
				.currencyCode(currencyCode)
				.precision(precision)
				.build();
	}

	@Builder(builderMethodName = "prepareCurrency", builderClassName = "CurrencyBuilder")
	private static Currency createCurrency(
			@NonNull final CurrencyCode currencyCode,
			@Nullable final CurrencyPrecision precision,
			@Nullable final CurrencyId currencyId)
	{
		Adempiere.assertUnitTestMode();

		final CurrencyPrecision precisionToUse = precision != null ? precision : CurrencyPrecision.TWO;

		final I_C_Currency record = newInstanceOutOfTrx(I_C_Currency.class);

		record.setISO_Code(currencyCode.toThreeLetterCode());
		record.setCurSymbol(currencyCode.toThreeLetterCode());
		record.setIsEuro(currencyCode.isEuro());

		record.setStdPrecision(precisionToUse.toInt());
		record.setCostingPrecision(precisionToUse.toInt() + 2);

		if (currencyId != null)
		{
			record.setC_Currency_ID(currencyId.getRepoId());
		}
		else if (currencyCode == CurrencyCode.EUR)
		{
			record.setC_Currency_ID(CurrencyId.EUR.getRepoId());
		}
		else if (currencyCode == CurrencyCode.USD)
		{
			record.setC_Currency_ID(CurrencyId.USD.getRepoId());
		}
		else if (currencyCode == CurrencyCode.CHF)
		{
			record.setC_Currency_ID(CurrencyId.CHF.getRepoId());
		}

		saveRecord(record);
		POJOWrapper.enableStrictValues(record);

		return toCurrency(record);
	}
}
