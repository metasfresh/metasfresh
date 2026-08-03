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

import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(AdempiereTestWatcher.class)
class NewestConversionRatesServiceTest
{
	private NewestConversionRatesService newestConversionRatesService;
	private PlainCurrencyDAO currencyDAO;

	private CurrencyId eur;
	private CurrencyId cny;
	private CurrencyId usd;
	private CurrencyConversionTypeId spotTypeId;
	private CurrencyConversionTypeId periodEndTypeId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();

		currencyDAO = (PlainCurrencyDAO)Services.get(de.metas.currency.ICurrencyDAO.class);

		newestConversionRatesService = new NewestConversionRatesService();

		eur = createActiveCurrency("EUR");
		cny = createActiveCurrency("CNY");
		usd = createActiveCurrency("USD");
		spotTypeId = currencyDAO.getConversionTypeId(ConversionTypeMethod.Spot);
		periodEndTypeId = currencyDAO.getConversionTypeId(ConversionTypeMethod.PeriodEnd);
	}

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

	private void createRate(
			final ClientId clientId,
			final OrgId orgId,
			final CurrencyId fromCurrencyId,
			final CurrencyId toCurrencyId,
			final CurrencyConversionTypeId conversionTypeId,
			final LocalDate validFrom,
			final String multiplyRate)
	{
		final I_C_Conversion_Rate record = newInstanceOutOfTrx(I_C_Conversion_Rate.class);
		InterfaceWrapperHelper.setValue(record, I_C_Conversion_Rate.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_Currency_ID(fromCurrencyId.getRepoId());
		record.setC_Currency_ID_To(toCurrencyId.getRepoId());
		record.setC_ConversionType_ID(conversionTypeId.getRepoId());
		record.setValidFrom(TimeUtil.asTimestamp(validFrom));
		record.setMultiplyRate(new BigDecimal(multiplyRate));
		record.setDivideRate(BigDecimal.ONE.divide(new BigDecimal(multiplyRate), 12, java.math.RoundingMode.HALF_UP));
		record.setIsActive(true);
		saveRecord(record);
	}

	private ClientId sessionClientId()
	{
		return Env.getClientId();
	}

	@Test
	void newestPerCombo_returnsOnlyMaxValidFromRow_perCombo()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		// EUR->CNY spot: three posted dates; newest = 2026-06-03
		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-01"), "8.00");
		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-03"), "8.20");

		// EUR->USD spot: two dates; newest = 2026-06-02
		createRate(clientId, org0, eur, usd, spotTypeId, LocalDate.parse("2026-06-01"), "1.10");
		createRate(clientId, org0, eur, usd, spotTypeId, LocalDate.parse("2026-06-02"), "1.11");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder().build());

		// exactly one row per combo (2 combos), each the newest ValidFrom
		assertThat(result).hasSize(2);
		assertThat(result)
				.extracting(
						JsonNewestConversionRate::getFromCurrencyCode,
						JsonNewestConversionRate::getToCurrencyCode,
						JsonNewestConversionRate::getConversionTypeCode,
						JsonNewestConversionRate::getValidFrom,
						JsonNewestConversionRate::getMultiplyRate)
				.containsExactlyInAnyOrder(
						tuple("EUR", "CNY", "S", LocalDate.parse("2026-06-03"), new BigDecimal("8.20")),
						tuple("EUR", "USD", "S", LocalDate.parse("2026-06-02"), new BigDecimal("1.11")));
	}

	@Test
	void sameCurrencyPair_differentType_areDistinctCombos()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		createRate(clientId, org0, eur, cny, periodEndTypeId, LocalDate.parse("2026-06-02"), "8.15");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder().build());

		assertThat(result).hasSize(2);
		assertThat(result)
				.extracting(JsonNewestConversionRate::getConversionTypeCode)
				.containsExactlyInAnyOrder("S", "P");
	}

	@Test
	void fromCurrencyFilter_narrowsResult()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		createRate(clientId, org0, usd, cny, spotTypeId, LocalDate.parse("2026-06-02"), "7.00");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder()
						.fromCurrencyCode("USD")
						.build());

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getFromCurrencyCode()).isEqualTo("USD");
		assertThat(result.get(0).getToCurrencyCode()).isEqualTo("CNY");
	}

	@Test
	void toCurrencyFilter_narrowsResult()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		createRate(clientId, org0, eur, usd, spotTypeId, LocalDate.parse("2026-06-02"), "1.11");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder()
						.toCurrencyCode("USD")
						.build());

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getToCurrencyCode()).isEqualTo("USD");
	}

	@Test
	void conversionTypeFilter_narrowsResult()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		createRate(clientId, org0, eur, cny, periodEndTypeId, LocalDate.parse("2026-06-02"), "8.15");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder()
						.conversionTypeCode("P")
						.build());

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getConversionTypeCode()).isEqualTo("P");
		assertThat(result.get(0).getMultiplyRate()).isEqualByComparingTo("8.15");
	}

	@Test
	void otherClientRows_areExcluded_clientScopingRespected()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;
		final ClientId otherClientId = ClientId.ofRepoId(clientId.getRepoId() + 1000);

		// session client's row
		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.10");
		// a DIFFERENT, later row belonging to another client -> must NOT leak into the result
		createRate(otherClientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-05"), "9.99");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder().build());

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getValidFrom()).isEqualTo(LocalDate.parse("2026-06-02"));
		assertThat(result.get(0).getMultiplyRate()).isEqualByComparingTo("8.10");
	}

	@Test
	void divideRate_isMappedThrough()
	{
		final ClientId clientId = sessionClientId();
		final OrgId org0 = OrgId.ANY;

		createRate(clientId, org0, eur, cny, spotTypeId, LocalDate.parse("2026-06-02"), "8.00");

		final List<JsonNewestConversionRate> result = newestConversionRatesService.list(
				NewestConversionRatesService.NewestConversionRatesFilter.builder().build());

		assertThat(result).hasSize(1);
		// 1/8 = 0.125 000 000 000 (scale 12, HALF_UP)
		assertThat(result.get(0).getDivideRate()).isEqualByComparingTo("0.125000000000");
	}
}
