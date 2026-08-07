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

import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert.BatchSyncOutcome;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonRequestConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsert;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem;
import de.metas.common.rest_api.v2.currencyconversion.JsonResponseConversionRateUpsertItem.SyncOutcome;
import de.metas.currency.ConversionRateRepository;
import de.metas.currency.ConversionTypeMethod;
import de.metas.i18n.Language;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.model.X_AD_OrgInfo;
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
	private ConversionRateRepository conversionRateRepository;

	private static final LocalDate VALID_FROM = LocalDate.parse("2026-06-02");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		conversionRateUpsertService = ConversionRateUpsertService.newInstanceForUnitTesting();
		conversionRateRepository = ConversionRateRepository.newInstanceForUnitTesting();
	}

	private JsonResponseConversionRateUpsert upsert(final JsonRequestConversionRateUpsert request)
	{
		return conversionRateUpsertService.upsert(request, Language.getBaseLanguage());
	}

	/**
	 * Active {@code AD_Org} + matching {@code AD_OrgInfo} carrying a timezone.
	 * The {@code AD_OrgInfo} is required: the converter's date conversion ({@code OrgDAO.getTimeZone}) throws
	 * {@code @NotFound@ @AD_OrgInfo@} for a regular org without one.
	 */
	private OrgId createOrg(final String value)
	{
		final I_AD_Org org = newInstanceOutOfTrx(I_AD_Org.class);
		org.setValue(value);
		org.setName(value);
		org.setIsActive(true);
		saveRecord(org);
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_AD_OrgInfo orgInfo = newInstanceOutOfTrx(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(orgId.getRepoId());
		orgInfo.setTimeZone("UTC");
		// StoreCreditCardData must be a valid code: OrgDAO.toOrgInfo maps it via StoreCreditCardNumberMode.ofCode,
		// which throws on a null/blank value.
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen);
		saveRecord(orgInfo);

		return orgId;
	}

	/** Create an <b>active</b> {@code C_Currency} row for the given ISO code and return its id. */
	private CurrencyId createActiveCurrency(final String isoCode)
	{
		final I_C_Currency record = newInstanceOutOfTrx(I_C_Currency.class);
		record.setISO_Code(isoCode);
		record.setDescription(isoCode);
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
		record.setDescription(isoCode);
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

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.SUCCESS);
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseConversionRateUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(responseItem.getError()).isNull();

		// forward + auto-written reciprocal
		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate rate = rateFor(eur, cny);
		assertThat(rate).isNotNull();
		assertThat(rate.getMultiplyRate()).isEqualByComparingTo("8.00");
		assertThat(rate.getDivideRate()).isEqualByComparingTo("0.125000000000");
		assertThat(rate.getDivideRate().scale()).isEqualTo(12);
		assertThat(rate.getAD_Org_ID()).isEqualTo(0);
		assertThat(rate.getC_ConversionType_ID()).isGreaterThan(0);
		// ValidTo left null by the service (interceptor would default it on the real save path)
		assertThat(rate.getValidTo()).isNull();
	}

	@Test
	void divideRate_isRoundedHalfUpAtScale12()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId usd = createActiveCurrency("USD");

		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item()
						.toCurrencyCode("USD")
						.multiplyRate(new BigDecimal("3")) // 1/3 = 0.333333333333 (HALF_UP at 12)
						.build())
				.build());

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

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.requestItem(item().toCurrencyCode("XXX").build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.PARTIAL_SUCCESS);
		assertThat(response.getResponseItems()).hasSize(2);

		final JsonResponseConversionRateUpsertItem ok = response.getResponseItems().get(0);
		assertThat(ok.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);

		final JsonResponseConversionRateUpsertItem err = response.getResponseItems().get(1);
		assertThat(err.getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(err.getError()).isNotNull();
		assertThat(err.getToCurrencyCode()).isEqualTo("XXX");

		// the unknown currency was NOT auto-created
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

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void explicitConversionType_isHonored_unknownTypeCode_isError()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().conversionTypeCode("P").build()) // PeriodEnd, valid
						.requestItem(item().conversionTypeCode("Z").build()) // unknown code
						.build());

		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(response.getResponseItems().get(1).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);

		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		// the explicit "P" (PeriodEnd) type differs from the default (Spot); assert it was applied
		final int periodEndConversionTypeId = conversionRateRepository
				.getConversionTypeId(ConversionTypeMethod.PeriodEnd).getRepoId();
		assertThat(forward.getC_ConversionType_ID()).isEqualTo(periodEndConversionTypeId);
	}

	@Test
	void explicitOrgCode_storesOrgSpecificRow()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");
		// orgCode resolves by AD_Org.Value (RestUtils.retrieveOrgIdOrDefault), not by numeric id
		final OrgId orgId = createOrg("orgABC");

		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().orgCode("orgABC").build())
				.build());

		final I_C_Conversion_Rate rate = allRates().get(0);
		assertThat(rate.getAD_Org_ID()).isEqualTo(orgId.getRepoId());
	}

	@Test
	void unknownOrgCode_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");
		// no org with Value "orgNOPE": the unknown-org throw must become a per-record ERROR, writing nothing

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().orgCode("orgNOPE").build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseConversionRateUpsertItem err = response.getResponseItems().get(0);
		assertThat(err.getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(err.getError()).isNotNull();
		assertThat(allRates()).isEmpty();
	}

	@Test
	void unknownOrgCode_isPerRecordError_validRecordStillApplied_noBatchAbort()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");
		// unknown org must ERROR per-record without aborting the batch; the sibling valid item still applies

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().build()) // valid, default org 0
						.requestItem(item().orgCode("orgNOPE").build()) // unknown org Value
						.build());

		assertThat(response.getResponseItems()).hasSize(2);

		final JsonResponseConversionRateUpsertItem ok = response.getResponseItems().get(0);
		assertThat(ok.getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(ok.getError()).isNull();

		final JsonResponseConversionRateUpsertItem err = response.getResponseItems().get(1);
		assertThat(err.getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(err.getError()).isNotNull();

		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		assertThat(forward.getAD_Org_ID()).isEqualTo(0);
	}

	@Test
	void reUpsert_sameNaturalKey_updatesInPlace_noDuplicate()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);

		final JsonResponseConversionRateUpsert second = upsert(
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
		// reciprocal divideRate = 1/0.111111111111 = 9.000000000009 (derived from the already-rounded
		// reciprocal, so not exactly 9 — this is what the service stores).
		final I_C_Conversion_Rate reciprocal = rateFor(cny, eur);
		assertThat(reciprocal).isNotNull();
		assertThat(reciprocal.getMultiplyRate()).isEqualByComparingTo("0.111111111111");
		assertThat(reciprocal.getDivideRate()).isEqualByComparingTo("9.000000000009");
	}

	@Test
	void fromEqualsTo_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().toCurrencyCode("EUR").build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void nonPositiveRate_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(BigDecimal.ZERO).build())
						.requestItem(item().multiplyRate(new BigDecimal("-1")).build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(response.getResponseItems().get(1).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(allRates()).isEmpty();
	}

	@Test
	void validToBeforeValidFrom_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().validTo(VALID_FROM.minusDays(1)).build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
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
		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
						.build());

		// one supplied direction -> one response item, but both directions stored
		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);
		assertThat(allRates()).hasSize(2);

		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		assertThat(forward.getMultiplyRate()).isEqualByComparingTo("8.00");

		final I_C_Conversion_Rate reverse = rateFor(cny, eur);
		assertThat(reverse).isNotNull();
		assertThat(reverse.getMultiplyRate()).isEqualByComparingTo("0.125000000000");
		assertThat(reverse.getDivideRate()).isEqualByComparingTo("8.000000000000");
	}

	@Test
	void bothDirectionsSupplied_callersReverse_isUntouched()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		// reverse rate deliberately NOT the reciprocal (0.13 != 1/8) to prove a caller-supplied reverse is kept as-is
		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.requestItem(item()
								.fromCurrencyCode("EUR").toCurrencyCode("CNY")
								.multiplyRate(new BigDecimal("8.00")).build())
						.requestItem(item()
								.fromCurrencyCode("CNY").toCurrencyCode("EUR")
								.multiplyRate(new BigDecimal("0.13")).build())
						.build());

		assertThat(response.getResponseItems()).hasSize(2);
		// exactly the two supplied rows exist (no synthetic reciprocal added)
		assertThat(allRates()).hasSize(2);

		final I_C_Conversion_Rate reverse = rateFor(cny, eur);
		assertThat(reverse).isNotNull();
		assertThat(reverse.getMultiplyRate()).isEqualByComparingTo("0.13");
	}

	@Test
	void reUpsertPair_isIdempotent_noDuplicateReciprocal()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);

		// re-upsert the same request -> still exactly 2 rows (update in place, no dup)
		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);
	}

	@Test
	void syncAdvise_dontUpdateOnExisting_isNothingDone_noOverwrite()
	{
		final CurrencyId eur = createActiveCurrency("EUR");
		final CurrencyId cny = createActiveCurrency("CNY");

		// seed an existing rate (default CREATE_OR_MERGE)
		upsert(JsonRequestConversionRateUpsert.builder()
				.requestItem(item().multiplyRate(new BigDecimal("8.00")).build())
				.build());
		assertThat(allRates()).hasSize(2);

		// re-post the same key with READ_ONLY (don't-update-on-exists) and a different rate
		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.syncAdvise(SyncAdvise.READ_ONLY)
						.requestItem(item().multiplyRate(new BigDecimal("9.99")).build())
						.build());

		// NOTHING_DONE counts as applied, not failed -> aggregate SUCCESS
		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.SUCCESS);
		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.NOTHING_DONE);
		assertThat(response.getResponseItems().get(0).getError()).isNull();

		// the forward rate was NOT overwritten with 9.99
		assertThat(allRates()).hasSize(2);
		final I_C_Conversion_Rate forward = rateFor(eur, cny);
		assertThat(forward).isNotNull();
		assertThat(forward.getMultiplyRate()).isEqualByComparingTo("8.00");
	}

	@Test
	void syncAdvise_failIfNotExists_onMissingRate_isPerRecordError_noRowWritten()
	{
		createActiveCurrency("EUR");
		createActiveCurrency("CNY");

		// no rate exists yet; READ_ONLY (fail-if-not-exists) must error and write nothing (not even the reciprocal)
		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.syncAdvise(SyncAdvise.READ_ONLY)
						.requestItem(item().build())
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.ERROR);
		assertThat(response.getResponseItems()).hasSize(1);
		assertThat(response.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.ERROR);
		assertThat(response.getResponseItems().get(0).getError()).isNotNull();
		assertThat(allRates()).isEmpty();
	}

	@Test
	void emptyBatch_isSuccess()
	{
		// a batch with no items has nothing to fail -> aggregate SUCCESS
		final JsonResponseConversionRateUpsert response = upsert(
				JsonRequestConversionRateUpsert.builder()
						.build());

		assertThat(response.getSyncOutcome()).isEqualTo(BatchSyncOutcome.SUCCESS);
		assertThat(response.getResponseItems()).isEmpty();
		assertThat(allRates()).isEmpty();
	}
}
