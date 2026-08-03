/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.currencyconversion;

import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem.SyncOutcome;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class ConversionRateUpsertServiceTest
{
	private ConversionRateUpsertService conversionRateUpsertService;
	private PlainCurrencyDAO currencyDAO;

	private static final LocalDate VALID_FROM = LocalDate.parse("2026-06-02");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();

		currencyDAO = (PlainCurrencyDAO)Services.get(de.metas.currency.ICurrencyDAO.class);

		conversionRateUpsertService = new ConversionRateUpsertService(currencyDAO);
	}

	/** Create an <b>active</b> {@code C_Currency} row for the given ISO code and return its id. */
	private CurrencyId createActiveCurrency(final String isoCode)
	{
		final I_C_Currency record = newInstanceOutOfTrx(I_C_Currency.class);
		record.setISO_Code(isoCode);
		record.setCurSymbol(isoCode);
		record.setStdPrecision(2);
		record.setCostingPrecision(4);
		record.setIsActive(true);
		saveRecord(record);
		return CurrencyId.ofRepoId(record.getC_Currency_ID());
	}

	private void createInactiveCurrency(final String isoCode)
	{
		final I_C_Currency record = newInstanceOutOfTrx(I_C_Currency.class);
		record.setISO_Code(isoCode);
		record.setCurSymbol(isoCode);
		record.setStdPrecision(2);
		record.setCostingPrecision(4);
		record.setIsActive(false);
		saveRecord(record);
	}

	private JsonRequestConversionRateUpsertItem.JsonRequestConversionRateUpsertItemBuilder item()
	{
		return JsonRequestConversionRateUpsertItem.builder()
				.fromCurrencyCode("EUR")
				.toCurrencyCode("CNY")
				.multiplyRate(new BigDecimal("8.00"))
				.validFrom(VALID_FROM);
	}

	private List<I_C_Conversion_Rate> allRates()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Conversion_Rate.class)
				.create()
				.list(I_C_Conversion_Rate.class);
	}

	@Test
	void singleRate_resolvesCurrencies_derivesDivideRate_defaultTypeAndOrg()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.build());

		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseConversionRateUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(responseItem.getError()).isNull();

		final List<I_C_Conversion_Rate> rates = allRates();
		assertThat(rates).hasSize(1);
		final I_C_Conversion_Rate rate = rates.get(0);
		assertThat(rate.getMultiplyRate()).isEqualByComparingTo("8.00");
		// DivideRate = 1/8 = 0.125 000 000 000 (scale 12, HALF_UP)
		assertThat(rate.getDivideRate()).isEqualByComparingTo("0.125000000000");
		assertThat(rate.getDivideRate().scale()).isEqualTo(12);
		// default org 0
		assertThat(rate.getAD_Org_ID()).isEqualTo(0);
		// default conversion type resolved (non-zero)
		assertThat(rate.getC_ConversionType_ID()).isGreaterThan(0);
		// ValidTo left null by the service (interceptor would default it on the real save path)
		assertThat(rate.getValidTo()).isNull();
	}

	@Test
	void divideRate_isRoundedHalfUpAtScale12()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("USD");

		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item()
						.toCurrencyCode("USD")
						.multiplyRate(new BigDecimal("3")) // 1/3 = 0.333333333333 (HALF_UP at 12)
						.build())
				.build());

		final I_C_Conversion_Rate rate = allRates().get(0);
		assertThat(rate.getDivideRate()).isEqualByComparingTo("0.333333333333");
		assertThat(rate.getDivideRate().scale()).isEqualTo(12);
	}

	@Test
	void unknownCurrency_isPerRecordError_validRecordStillApplied()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");
		// "XXX" intentionally not created

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build()) // valid EUR->CNY
						.requestItem(item().toCurrencyCode("XXX").build()) // unknown target
						.build());

		assertThat(response.getResponseItems()).hasSize(2);

		final JsonResponseConversionRateUpsertItem ok = response.getResponseItems().get(0);
		assertThat(ok.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);

		final JsonResponseConversionRateUpsertItem err = response.getResponseItems().get(1);
		assertThat(err.getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(err.getError()).isNotNull();
		assertThat(err.getToCurrencyCode()).isEqualTo("XXX");

		// only the valid record was written; the unknown currency was NOT auto-created
		assertThat(allRates()).hasSize(1);
		final long xxxCount = POJOLookupMap.get().getRecords(I_C_Currency.class).stream()
				.filter(c -> "XXX".equals(c.getISO_Code()))
				.count();
		assertThat(xxxCount).isEqualTo(0);
	}

	@Test
	void inactiveCurrency_isPerRecordError()
	{
		createActiveCurrency("EUR");
		createInactiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.build());

		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void explicitConversionType_isHonored_unknownTypeCode_isError()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().conversionTypeCode("P").build()) // PeriodEnd, valid
						.requestItem(item().conversionTypeCode("Z").build()) // unknown code
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(response.getResponseItems().get(1).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);

		final List<I_C_Conversion_Rate> rates = allRates();
		assertThat(rates).hasSize(1);
		// the explicit "P" (PeriodEnd) type differs from the default (Spot); assert it was applied
		final int periodEndConversionTypeId = currencyDAO
				.getConversionTypeId(de.metas.currency.ConversionTypeMethod.PeriodEnd).getRepoId();
		assertThat(rates.get(0).getC_ConversionType_ID()).isEqualTo(periodEndConversionTypeId);
	}

	@Test
	void explicitOrgCode_storesOrgSpecificRow()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().orgCode("1000000").build())
				.build());

		final I_C_Conversion_Rate rate = allRates().get(0);
		assertThat(rate.getAD_Org_ID()).isEqualTo(1000000);
	}

	@Test
	void reUpsert_sameNaturalKey_updatesInPlace_noDuplicate()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(1);

		final JsonResponseConversionRateUpsert second = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(new BigDecimal("9.00")).build())
						.build());

		assertThat(second.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.UPDATED);
		final List<I_C_Conversion_Rate> rates = allRates();
		assertThat(rates).hasSize(1);
		assertThat(rates.get(0).getMultiplyRate()).isEqualByComparingTo("9.00");
		assertThat(rates.get(0).getDivideRate()).isEqualByComparingTo("0.111111111111");
	}

	@Test
	void fromEqualsTo_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().toCurrencyCode("EUR").build())
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void nonPositiveRate_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(BigDecimal.ZERO).build())
						.requestItem(item().multiplyRate(new BigDecimal("-1")).build())
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(response.getResponseItems().get(1).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void validToBeforeValidFrom_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().validTo(VALID_FROM.minusDays(1)).build())
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}
}
