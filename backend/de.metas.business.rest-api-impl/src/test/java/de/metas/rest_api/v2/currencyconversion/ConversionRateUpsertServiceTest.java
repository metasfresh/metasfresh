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
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.build());

		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseConversionRateUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(responseItem.getError()).isNull();

		// forward + auto-written reciprocal
		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate rate = rateFor(eur, cny);
		assertThat(rate).isNotNull();
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
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId usd = createActiveCurrency("USD");

		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item()
						.toCurrencyCode("USD")
						.multiplyRate(new BigDecimal("3")) // 1/3 = 0.333333333333 (HALF_UP at 12)
						.build())
				.build());

		// select the forward row explicitly; the auto-reciprocal also exists (list order is not guaranteed)
		final I_C_Conversion_Rate rate = rateFor(eur, usd);
		assertThat(rate).isNotNull();
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

		// only the valid record's two directions were written; the unknown currency was NOT auto-created
		assertThat(allRates()).hasSize(2);
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
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().conversionTypeCode("P").build()) // PeriodEnd, valid
						.requestItem(item().conversionTypeCode("Z").build()) // unknown code
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(response.getResponseItems().get(1).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);

		// the valid "P" record wrote its forward + auto-reciprocal (both PeriodEnd); "Z" wrote nothing
		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		// the explicit "P" (PeriodEnd) type differs from the default (Spot); assert it was applied
		final int periodEndConversionTypeId = currencyDAO
				.getConversionTypeId(de.metas.currency.ConversionTypeMethod.PeriodEnd).getRepoId();
		assertThat(forward.getC_ConversionType_ID()).isEqualTo(periodEndConversionTypeId);
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
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		// forward + auto-reciprocal
		assertThat(allRates()).hasSize(2);

		final JsonResponseConversionRateUpsert second = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(new BigDecimal("9.00")).build())
						.build());

		assertThat(second.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.UPDATED);
		// still exactly the same two rows, updated in place
		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		assertThat(forward.getMultiplyRate()).isEqualByComparingTo("9.00");
		assertThat(forward.getDivideRate()).isEqualByComparingTo("0.111111111111");
		// the auto-reciprocal was updated in place too: multiplyRate = 1/9 = 0.111111111111 (scale 12,
		// HALF_UP); its own divideRate = 1/0.111111111111 = 9.000000000009 (derived from the already-
		// rounded reciprocal, so it is not exactly 9 — this is the value the service actually stores).
		final I_C_Conversion_Rate reciprocal = rateFor(cny, eur);
		assertThat(reciprocal).isNotNull();
		assertThat(reciprocal.getMultiplyRate()).isEqualByComparingTo("0.111111111111");
		assertThat(reciprocal.getDivideRate()).isEqualByComparingTo("9.000000000009");
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

	private I_C_Conversion_Rate rateFor(final CurrencyId fromCurrencyId, final CurrencyId toCurrencyId)
	{
		return allRates().stream()
				.filter(r -> r.getC_Currency_ID() == fromCurrencyId.getRepoId()
						&& r.getC_Currency_ID_To() == toCurrencyId.getRepoId())
				.findFirst()
				.orElse(null);
	}

	@Test
	void forwardOnly_autoWritesReciprocal()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		// only the forward EUR->CNY @ 8 is supplied; the reverse must be auto-written as 1/8
		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
						.build());

		// caller only asked for the forward direction -> one response item
		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);

		// but BOTH directions are stored
		assertThat(allRates()).hasSize(2);

		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		assertThat(forward.getMultiplyRate()).isEqualByComparingTo("8.00");

		final I_C_Conversion_Rate reverse = rateFor(cny, eur);
		assertThat(reverse).isNotNull();
		// reciprocal multiplyRate = 1/8 = 0.125 (scale 12, HALF_UP)
		assertThat(reverse.getMultiplyRate()).isEqualByComparingTo("0.125000000000");
		// its own divideRate = 1 / (1/8) = 8
		assertThat(reverse.getDivideRate()).isEqualByComparingTo("8.000000000000");
	}

	@Test
	void bothDirectionsSupplied_callersReverse_isUntouched()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		// caller supplies BOTH directions; the reverse rate is deliberately NOT the reciprocal (0.13 != 1/8)
		final JsonResponseConversionRateUpsert response = conversionRateUpsertService.upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item()
								.fromCurrencyCode("EUR").toCurrencyCode("CNY")
								.multiplyRate(new BigDecimal("8.00")).build())
						.requestItem(item()
								.fromCurrencyCode("CNY").toCurrencyCode("EUR")
								.multiplyRate(new BigDecimal("0.13")).build())
						.build());

		// both supplied items reported
		assertThat(response.getResponseItems()).hasSize(2);

		// exactly the two supplied rows exist (no synthetic reciprocal added)
		assertThat(allRates()).hasSize(2);

		final I_C_Conversion_Rate reverse = rateFor(cny, eur);
		assertThat(reverse).isNotNull();
		// caller's reverse honored as-is, NOT overwritten with the computed reciprocal 0.125
		assertThat(reverse.getMultiplyRate()).isEqualByComparingTo("0.13");
	}

	@Test
	void reUpsertPair_isIdempotent_noDuplicateReciprocal()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		// forward-only -> forward + auto reciprocal = 2 rows
		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);

		// re-upsert the same forward-only request -> still exactly 2 rows (update in place, no dup)
		conversionRateUpsertService.upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);
	}
}
