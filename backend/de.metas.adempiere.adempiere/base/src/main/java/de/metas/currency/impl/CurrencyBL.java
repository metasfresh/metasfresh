package de.metas.currency.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

public class CurrencyBL implements ICurrencyBL
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	@Override
	public final Currency getBaseCurrency(final Properties ctx)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final OrgId adOrgId = Env.getOrgId(ctx);

		return getBaseCurrency(adClientId, adOrgId);
	}

	@Override
	public Currency getBaseCurrency(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		final CurrencyId currencyId = getBaseCurrencyId(adClientId, adOrgId);

		return currencyDAO.getById(currencyId);
	}

	@Override
	public CurrencyId getBaseCurrencyId(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
		final AcctSchema as = acctSchemaDAO.getByClientAndOrg(adClientId, adOrgId);
		Check.assumeNotNull(as, "Missing C_AcctSchema for AD_Client_ID={} and AD_Org_ID={}", adClientId, adOrgId);

		return as.getCurrencyId();
	}

	@Override
	public BigDecimal convertBase(
			final BigDecimal amt,
			final CurrencyId currencyFromId,
			final LocalDate convDate,
			final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final CurrencyId currencyToId = getBaseCurrencyId(clientId, orgId);
		return convert(amt, currencyFromId, currencyToId, convDate, conversionTypeId, clientId, orgId);
	}

	@NonNull
	@Override
	public final BigDecimal convert(
			final BigDecimal amt,
			final CurrencyId currencyFromId,
			final CurrencyId currencyToId,
			final LocalDate convDate,
			final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				convDate,
				conversionTypeId,
				clientId,
				orgId);
		final CurrencyConversionResult conversionResult = convert(
				conversionCtx,
				amt,
				currencyFromId,
				currencyToId);
		return conversionResult.getAmount();
	}

	@NonNull
	@Override
	public final CurrencyConversionResult convert(
			@NonNull final CurrencyConversionContext conversionCtx,
			@NonNull final BigDecimal amt,
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId)
	{
		final BigDecimal amtConv;
		final BigDecimal conversionRateBD;
		if (amt.signum() == 0)
		{
			amtConv = BigDecimal.ZERO;
			conversionRateBD = null; // N/A
		}
		// Same Currency
		else if (CurrencyId.equals(currencyFromId, currencyToId))
		{
			amtConv = amt;
			conversionRateBD = BigDecimal.ONE;
		}
		else
		{
			final CurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, currencyFromId, currencyToId);
			if (currencyRate == null)
			{
				throw new NoCurrencyRateFoundException(
						currencyDAO.getCurrencyCodeById(currencyFromId),
						currencyDAO.getCurrencyCodeById(currencyToId),
						conversionCtx.getConversionDate(),
						currencyDAO.getConversionTypeMethodById(conversionCtx.getConversionTypeId()));
			}

			conversionRateBD = currencyRate.getConversionRate();

			//
			// Calculate the converted amount
			amtConv = currencyRate.convertAmount(amt);
		}

		return prepareCurrencyConversionResult(conversionCtx)
				.sourceAmount(amt)
				.sourceCurrencyId(currencyFromId)
				.amount(amtConv)
				.currencyId(currencyToId)
				.conversionRateOrNull(conversionRateBD)
				.build();
	}    // convert

	private CurrencyPrecision getStdPrecision(final CurrencyId currencyId)
	{
		return currencyDAO.getStdPrecision(currencyId);
	}

	@Override
	@NonNull
	public final BigDecimal convert(
			final BigDecimal amt,
			final CurrencyId currencyFromId,
			final CurrencyId currencyToId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				null, // convDate
				(CurrencyConversionTypeId)null, // C_ConversionType_ID,
				clientId,
				orgId);
		final CurrencyConversionResult conversionResult = convert(
				conversionCtx,
				amt,
				currencyFromId,
				currencyToId);
		return conversionResult.getAmount();
	}

	@Override
	public final Optional<CurrencyRate> getCurrencyRateIfExists(
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId,
			@Nullable final LocalDate convDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				convDate,
				conversionTypeId,
				clientId,
				orgId);
		final CurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, currencyFromId, currencyToId);
		return Optional.ofNullable(currencyRate);
	}

	@Override
	@NonNull
	public final CurrencyConversionContext createCurrencyConversionContext(
			@Nullable final LocalDate conversionDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final LocalDate conversionDateEffective = conversionDate != null ? conversionDate : SystemTime.asLocalDate();
		final CurrencyConversionTypeId conversionTypeIdEffective = conversionTypeId != null
				? conversionTypeId
				: getDefaultConversionTypeId(clientId, orgId, conversionDateEffective);

		return CurrencyConversionContext.builder()
				.conversionDate(conversionDateEffective)
				.conversionTypeId(conversionTypeIdEffective)
				.clientId(clientId)
				.orgId(orgId)
				.build();
	}

	@Override
	@NonNull
	public final CurrencyConversionContext createCurrencyConversionContext(
			@NonNull final LocalDate conversionDate,
			@Nullable final ConversionTypeMethod conversionType,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		// Find C_ConversionType_ID
		final ConversionTypeMethod conversionTypeEffective = conversionType != null ? conversionType : ConversionTypeMethod.Spot;
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getConversionTypeId(conversionTypeEffective);

		return createCurrencyConversionContext(conversionDate, conversionTypeId, clientId, orgId);
	}

	private CurrencyConversionTypeId getDefaultConversionTypeId(
			final ClientId adClientId,
			final OrgId adOrgId,
			final LocalDate date)
	{
		return currencyDAO.getDefaultConversionTypeId(adClientId, adOrgId, date);
	}

	@Override
	public CurrencyConversionTypeId getCurrencyConversionTypeId(@NonNull final ConversionTypeMethod type)
	{
		return currencyDAO.getConversionTypeId(type);
	}

	@Nullable
	private CurrencyRate getCurrencyRateOrNull(
			@NonNull final CurrencyConversionContext conversionCtx,
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId)
	{
		final CurrencyConversionTypeId conversionTypeId = conversionCtx.getConversionTypeId();
		final LocalDate conversionDate = conversionCtx.getConversionDate();

		final BigDecimal conversionRate;
		if (currencyFromId.equals(currencyToId))
		{
			conversionRate = BigDecimal.ONE;
		}
		else if (conversionCtx.hasFixedConversionRate(currencyFromId, currencyToId))
		{
			conversionRate = conversionCtx.getFixedConversionRate(currencyFromId, currencyToId);
		}
		else
		{
			conversionRate = currencyDAO.retrieveRateOrNull(conversionCtx, currencyFromId, currencyToId);
			if (conversionRate == null)
			{
				return null;
			}
		}

		final CurrencyPrecision toCurrencyPrecision = conversionCtx.getPrecision().orElseGet(() -> getStdPrecision(currencyToId));
		final CurrencyPrecision fromCurrencyPrecision = conversionCtx.getPrecision().orElseGet(() -> getStdPrecision(currencyFromId));

		return CurrencyRate.builder()
				.conversionRate(conversionRate)
				.fromCurrencyId(currencyFromId)
				.toCurrencyId(currencyToId)
				.toCurrencyPrecision(toCurrencyPrecision)
				.fromCurrencyPrecision(fromCurrencyPrecision)
				.conversionTypeId(conversionTypeId)
				.conversionDate(conversionDate)
				.build();
	}

	@Override
	public final CurrencyRate getCurrencyRate(
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId,
			@Nullable final LocalDate convDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				convDate,
				conversionTypeId,
				clientId,
				orgId);
		return getCurrencyRate(conversionCtx, currencyFromId, currencyToId);
	}

	@Override
	public @NonNull CurrencyRate getCurrencyRate(
			@NonNull final CurrencyConversionContext conversionCtx,
			@NonNull final CurrencyId currencyFromId,
			@NonNull final CurrencyId currencyToId)
	{
		final CurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, currencyFromId, currencyToId);
		if (currencyRate == null)
		{
			final CurrencyCode currencyFrom = currencyDAO.getCurrencyCodeById(currencyFromId);
			final CurrencyCode currencyTo = currencyDAO.getCurrencyCodeById(currencyToId);
			final ConversionTypeMethod conversionTypeMethod = conversionCtx.getConversionTypeId() != null
					? currencyDAO.getConversionTypeMethodById(conversionCtx.getConversionTypeId())
					: null;

			throw new NoCurrencyRateFoundException(
					currencyFrom,
					currencyTo,
					conversionCtx.getConversionDate(),
					conversionTypeMethod)
					.setParameter("conversionCtx", conversionCtx);
		}

		return currencyRate;
	}

	@Override
	public @NonNull CurrencyCode getCurrencyCodeById(@NonNull final CurrencyId currencyId)
	{
		return currencyDAO.getCurrencyCodeById(currencyId);
	}

	private static CurrencyConversionResult.CurrencyConversionResultBuilder prepareCurrencyConversionResult(@NonNull final CurrencyConversionContext conversionCtx)
	{
		return CurrencyConversionResult.builder()
				.clientId(conversionCtx.getClientId())
				.orgId(conversionCtx.getOrgId())
				.conversionDate(conversionCtx.getConversionDate())
				.conversionTypeId(conversionCtx.getConversionTypeId());
	}

}
