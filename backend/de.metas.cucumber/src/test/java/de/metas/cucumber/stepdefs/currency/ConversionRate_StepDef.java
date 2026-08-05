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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.currency.ConversionRate;
import de.metas.currency.ConversionRateKey;
import de.metas.currency.ConversionRateRepository;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the currency-conversion domain:
 * <ul>
 *     <li>activating / deactivating a currency (test precondition, since the core seed ships most currencies inactive),</li>
 *     <li>asserting the {@code C_Conversion_Rate} rows an upsert produced,</li>
 *     <li>and the legacy 1:1-rate helper used by accounting-report scenarios.</li>
 * </ul>
 */
public class ConversionRate_StepDef
{
	private static final int SYSTEM_ORG_ID = 0;

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final CurrencyRepository currencyRepository = new CurrencyRepository();
	private final ConversionRateRepository conversionRateRepository = new ConversionRateRepository();

	/**
	 * Natural keys of the {@code C_Conversion_Rate} rows this scenario asserts into existence (captured during the
	 * scenario, when the DB context is up — a {@code @Before} would run before the infrastructure step). Each key's
	 * reverse is added too, so the auto-created reciprocal is cleaned even when the scenario only asserts the forward
	 * direction. {@link #cleanup_created_conversion_rates} deletes them so the shared executor DB is left as found.
	 */
	private final Set<ConversionRateKey> createdRateKeys = new HashSet<>();

	/**
	 * Per-ISO snapshot of {@code C_Currency.IsActive} taken by {@link #remember_currency_active_state} before the
	 * scenario toggles it, so {@link #restore_currency_active_state} can put the shared executor DB back the way it
	 * found it (this feature activates/deactivates currencies on the shared DB, which would otherwise leak into
	 * sibling features on the same executor). Insertion-ordered for deterministic restore.
	 */
	private final Map<String, Boolean> rememberedActiveByIsoCode = new LinkedHashMap<>();

	private final TestContext testContext;

	public ConversionRate_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	/**
	 * Sets {@code IsActive='Y'} on the given ISO currencies. Real-world trigger: activating a currency is a
	 * master-data configuration a key user performs (or a migration ships); the core seed ships most currencies
	 * (e.g. CNY) inactive, so a scenario that upserts rates for such a currency must activate it first rather than
	 * depend on a customer activation migration.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Given the following currencies are active:
	 *   | ISO_Code |
	 *   | EUR      |
	 *   | CNY      |
	 * }</pre>
	 */
	@Given("the following currencies are active:")
	public void the_following_currencies_are_active(@NonNull final DataTable dataTable)
	{
		setCurrenciesActive(dataTable, true);
	}

	/**
	 * Sets {@code IsActive='N'} on the given ISO currencies. Real-world trigger: deactivating a currency is a
	 * master-data configuration a key user performs; used to establish a known-inactive currency so a scenario can
	 * prove the active-only currency listing excludes it.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Given the following currencies are inactive:
	 *   | ISO_Code |
	 *   | RUB      |
	 * }</pre>
	 */
	@Given("the following currencies are inactive:")
	public void the_following_currencies_are_inactive(@NonNull final DataTable dataTable)
	{
		setCurrenciesActive(dataTable, false);
	}

	private void setCurrenciesActive(@NonNull final DataTable dataTable, final boolean active)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String isoCode = row.getAsString("ISO_Code");
			final I_C_Currency currency = currencyRepository.getRecordByCurrencyCodeOrNull(CurrencyCode.ofThreeLetterCode(isoCode));
			assertThat(currency).as("C_Currency with ISO_Code=%s must exist", isoCode).isNotNull();
			currency.setIsActive(active);
			InterfaceWrapperHelper.saveRecord(currency);
		});
	}

	/**
	 * Records the current {@code C_Currency.IsActive} of each listed ISO code, so a later
	 * {@code Then the remembered currency active-states are restored} step can put the shared executor DB back to its
	 * original state. Call this in the {@code Background} BEFORE the scenario activates/deactivates any currency, so
	 * this feature does not leak an activation into sibling features that run on the same executor DB.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And I remember the active-state of the following currencies:
	 *   | ISO_Code |
	 *   | EUR      |
	 *   | CNY      |
	 * }</pre>
	 */
	@Given("I remember the active-state of the following currencies:")
	public void remember_currency_active_state(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String isoCode = row.getAsString("ISO_Code");
			final I_C_Currency currency = currencyRepository.getRecordByCurrencyCodeOrNull(CurrencyCode.ofThreeLetterCode(isoCode));
			assertThat(currency).as("C_Currency with ISO_Code=%s must exist", isoCode).isNotNull();
			rememberedActiveByIsoCode.put(isoCode, currency.isActive());
		});
	}

	/**
	 * Deletes the {@code C_Conversion_Rate} rows this scenario asserted into existence (and their reciprocals),
	 * captured in {@link #createdRateKeys}, so the shared executor DB is left as found and re-runs start clean (rates
	 * upsert as CREATED, not UPDATED). Runs after the scenario (DB context up); a no-op when nothing was captured.
	 */
	@After
	public void cleanup_created_conversion_rates()
	{
		conversionRateRepository.deleteByKeys(createdRateKeys);
		createdRateKeys.clear();
	}

	/**
	 * Restores every currency remembered by {@link #remember_currency_active_state} to its recorded
	 * {@code IsActive}, undoing the scenario's activations/deactivations on the shared executor DB — so the feature
	 * file stays free of teardown plumbing and no scenario can forget to restore. A no-op when nothing was remembered
	 * (a scenario with no {@code I remember ...} step).
	 */
	@After
	public void restore_currency_active_state()
	{
		rememberedActiveByIsoCode.forEach((isoCode, wasActive) -> {
			final I_C_Currency currency = currencyRepository.getRecordByCurrencyCodeOrNull(CurrencyCode.ofThreeLetterCode(isoCode));
			if (currency != null)
			{
				currency.setIsActive(wasActive);
				InterfaceWrapperHelper.saveRecord(currency);
			}
		});
	}

	/**
	 * Asserts that exactly one {@code C_Conversion_Rate} row exists for the given natural key, with the given rates
	 * and open/closed {@code ValidTo}. The key uses org 0 (the shared rows the upsert writes); client is not part of
	 * the rate's identity (the read is client-less, per the natural-key unique index).
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then this C_Conversion_Rate exists:
	 *   | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate  | DivideRate    | ValidTo    |
	 *   | EUR          | CNY        | S              | 2026-06-01 | 7.6000000000  | 0.131578947368| 2056-12-31 |
	 * }</pre>
	 * {@code ConversionType} is optional (omitted = the org default). {@code ValidTo} is optional and given as an
	 * ISO date; when the upsert caller omits ValidTo, the {@code C_Conversion_Rate} interceptor defaults it to the
	 * far-future sentinel {@code 2056-12-31} (an open-ended rate), so that is the value to assert. A literal
	 * {@code null} (or an omitted cell) instead asserts a genuinely NULL ValidTo.
	 */
	@Then("this C_Conversion_Rate exists:")
	public void this_conversion_rate_exists(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final CurrencyId fromId = currencyId(row.getAsString("FromCurrency"));
			final CurrencyId toId = currencyId(row.getAsString("ToCurrency"));
			final CurrencyConversionTypeId typeId = conversionTypeId(row.getAsOptionalString("ConversionType").orElse(null));
			final LocalDate validFrom = row.getAsLocalDate("ValidFrom");

			final ConversionRateKey key = ConversionRateKey.builder()
					.orgId(OrgId.ofRepoId(SYSTEM_ORG_ID))
					.fromCurrencyId(fromId)
					.toCurrencyId(toId)
					.conversionTypeId(typeId)
					.validFrom(validFrom)
					.build();
			// Remember this key (and its reverse, for the auto-created reciprocal) so the @After cleanup deletes it.
			createdRateKeys.add(key);
			createdRateKeys.add(key.getReverseKey());

			final ConversionRate rate = conversionRateRepository.getByKey(key);
			assertThat(rate)
					.as("C_Conversion_Rate %s->%s type=%s validFrom=%s must exist",
							row.getAsString("FromCurrency"), row.getAsString("ToCurrency"), typeId.getRepoId(), validFrom)
					.isNotNull();

			row.getAsOptionalBigDecimal("MultiplyRate").ifPresent(expected -> assertThat(rate.getMultiplyRate())
					.as("MultiplyRate of %s->%s", row.getAsString("FromCurrency"), row.getAsString("ToCurrency"))
					.isEqualByComparingTo(expected));
			row.getAsOptionalBigDecimal("DivideRate").ifPresent(expected -> assertThat(rate.getDivideRate())
					.as("DivideRate of %s->%s", row.getAsString("FromCurrency"), row.getAsString("ToCurrency"))
					.isEqualByComparingTo(expected));

			final Optional<LocalDate> expectedValidTo = row.getAsOptionalString("ValidTo")
					.filter(s -> !"null".equalsIgnoreCase(s.trim()))
					.map(LocalDate::parse);
			if (expectedValidTo.isPresent())
			{
				assertThat(rate.getValidTo())
						.as("ValidTo of %s->%s", row.getAsString("FromCurrency"), row.getAsString("ToCurrency"))
						.isEqualTo(expectedValidTo.get());
			}
			else
			{
				assertThat(rate.getValidTo())
						.as("ValidTo of %s->%s must be open (null)", row.getAsString("FromCurrency"), row.getAsString("ToCurrency"))
						.isNull();
			}
		});
	}

	/**
	 * Asserts that NO {@code C_Conversion_Rate} row exists for the given natural key — used to prove an invalid
	 * upsert item wrote nothing.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then no C_Conversion_Rate exists:
	 *   | FromCurrency | ToCurrency | ConversionType | ValidFrom  |
	 *   | EUR          | CNY        | S              | 2026-06-05 |
	 * }</pre>
	 */
	@Then("no C_Conversion_Rate exists:")
	public void no_conversion_rate_exists(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final CurrencyId fromId = currencyId(row.getAsString("FromCurrency"));
			final CurrencyId toId = currencyId(row.getAsString("ToCurrency"));
			final CurrencyConversionTypeId typeId = conversionTypeId(row.getAsOptionalString("ConversionType").orElse(null));
			final LocalDate validFrom = row.getAsLocalDate("ValidFrom");

			assertThat(findRate(fromId, toId, typeId, validFrom))
					.as("no C_Conversion_Rate %s->%s type=%s validFrom=%s must exist",
							row.getAsString("FromCurrency"), row.getAsString("ToCurrency"), typeId.getRepoId(), validFrom)
					.isNull();
		});
	}

	/**
	 * Asserts that NO {@code C_Currency} row exists for the given ISO codes — proves the upsert never auto-creates a
	 * currency for an unknown ISO.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * Then the following currencies do not exist:
	 *   | ISO_Code |
	 *   | XXX      |
	 * }</pre>
	 */
	@Then("the following currencies do not exist:")
	public void the_following_currencies_do_not_exist(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String isoCode = row.getAsString("ISO_Code");
			assertThat(currencyRepository.existsByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode)))
					.as("no C_Currency with ISO_Code=%s must exist", isoCode)
					.isFalse();
		});
	}

	/**
	 * Asserts the last GET-currencies response body contains a currency with the given ISO code.
	 */
	@Then("the currencies response contains {string}")
	public void the_currencies_response_contains(@NonNull final String isoCode)
	{
		assertThat(currencyCodesInResponse())
				.as("GET currencies response must contain %s", isoCode)
				.contains(isoCode);
	}

	/**
	 * Asserts the last GET-currencies response body does NOT contain a currency with the given ISO code.
	 */
	@Then("the currencies response does not contain {string}")
	public void the_currencies_response_does_not_contain(@NonNull final String isoCode)
	{
		assertThat(currencyCodesInResponse())
				.as("GET currencies response must NOT contain %s", isoCode)
				.doesNotContain(isoCode);
	}

	/**
	 * Asserts how many rate entries the last GET-newestRates response body carries.
	 */
	@Then("the newestRates response has {int} rate(s)")
	public void the_newest_rates_response_has_n_rates(final int expectedCount)
	{
		final JsonNode rates = responseTree().at("/rates");
		assertThat(rates.isArray()).as("newestRates response must carry a 'rates' array").isTrue();
		assertThat(rates.size()).as("number of rates in newestRates response").isEqualTo(expectedCount);
	}

	private List<String> currencyCodesInResponse()
	{
		final JsonNode currencies = responseTree().at("/currencies");
		assertThat(currencies.isArray()).as("GET currencies response must carry a 'currencies' array").isTrue();
		final List<String> codes = new ArrayList<>();
		currencies.forEach(node -> codes.add(node.path("currencyCode").asText()));
		return codes;
	}

	private JsonNode responseTree()
	{
		try
		{
			final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
			return mapper.readTree(testContext.getApiResponseBodyAsString());
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed parsing API response body: " + testContext.getApiResponseBodyAsString(), ex);
		}
	}

	@NonNull
	private CurrencyId currencyId(@NonNull final String isoCode)
	{
		return currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode)).getId();
	}

	@NonNull
	private CurrencyConversionTypeId conversionTypeId(@Nullable final String conversionTypeCode)
	{
		if (Check.isBlank(conversionTypeCode))
		{
			return currencyDAO.getDefaultConversionTypeId(Env.getClientId(), OrgId.ANY, Instant.now());
		}
		return currencyDAO.getConversionTypeId(ConversionTypeMethod.forCode(conversionTypeCode.trim()));
	}

	@Nullable
	private ConversionRate findRate(
			@NonNull final CurrencyId fromId,
			@NonNull final CurrencyId toId,
			@NonNull final CurrencyConversionTypeId typeId,
			@NonNull final LocalDate validFrom)
	{
		// The exact-key lookup on the C_Conversion_Rate direction (org 0 = the shared rows the upsert writes).
		// The repository owns the ValidFrom-in-org-zone conversion and the typed-id -> repo-int mapping (and the
		// read is client-less, per the natural-key unique index), so this passes the typed ids straight through.
		final ConversionRateKey key = ConversionRateKey.builder()
				.orgId(OrgId.ofRepoId(SYSTEM_ORG_ID))
				.fromCurrencyId(fromId)
				.toCurrencyId(toId)
				.conversionTypeId(typeId)
				.validFrom(validFrom)
				.build();

		return conversionRateRepository.getByKey(key);
	}

	/**
	 * Forces a 1:1 spot conversion rate between two ISO currency codes for the given date range.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And a 1:1 "EUR" <-> "CHF" conversion rate is in place between "2024-01-01" and "2024-01-31"
	 * }</pre>
	 */
	@And("a 1:1 {string} <-> {string} conversion rate is in place between {string} and {string}")
	public void insert_one_to_one_rate(
			@NonNull final String fromIsoCode,
			@NonNull final String toIsoCode,
			@NonNull final String validFromStr,
			@NonNull final String validToStr)
	{
		final CurrencyConversionTypeId spotTypeId = currencyDAO.getConversionTypeId(ConversionTypeMethod.Spot);
		final CurrencyId fromId = currencyId(fromIsoCode);
		final CurrencyId toId = currencyId(toIsoCode);
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
