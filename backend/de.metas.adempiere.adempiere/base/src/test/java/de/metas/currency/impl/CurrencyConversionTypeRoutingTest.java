package de.metas.currency.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class CurrencyConversionTypeRoutingTest
{
	private final ZoneId zoneId = ZoneId.of("Europe/Berlin");

	@BeforeEach
	public void beforeEach()
	{
		SystemTime.setFixedTimeSource(LocalDate.parse("2022-11-12").atStartOfDay(zoneId));
	}

	private LocalDate date(final String localDate)
	{
		return LocalDate.parse(localDate);
	}

	@Nested
	class isMoreSpecificThan
	{
		@Test
		void sameClientAndOrg_differentValidFrom()
		{
			final CurrencyConversionTypeRouting.CurrencyConversionTypeRoutingBuilder routingBuilder = CurrencyConversionTypeRouting.builder()
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY);

			final CurrencyConversionTypeRouting r1970 = routingBuilder.validFrom(date("1970-01-01")).conversionTypeId(CurrencyConversionTypeId.ofRepoId(1)).build();
			final CurrencyConversionTypeRouting r2016 = routingBuilder.validFrom(date("2016-01-01")).conversionTypeId(CurrencyConversionTypeId.ofRepoId(2)).build();

			assertThat(r1970.isMoreSpecificThan(r2016)).isFalse();
			assertThat(r2016.isMoreSpecificThan(r1970)).isTrue();
		}
	}

	@Nested
	class moreSpecificFirstComparator
	{
		@Test
		void sameClientAndOrg_differentValidFrom()
		{
			final CurrencyConversionTypeRouting.CurrencyConversionTypeRoutingBuilder routingBuilder = CurrencyConversionTypeRouting.builder()
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY);

			final CurrencyConversionTypeRouting r1970 = routingBuilder.validFrom(date("1970-01-01")).conversionTypeId(CurrencyConversionTypeId.ofRepoId(1)).build();
			final CurrencyConversionTypeRouting r2016 = routingBuilder.validFrom(date("2016-01-01")).conversionTypeId(CurrencyConversionTypeId.ofRepoId(2)).build();

			final CurrencyConversionTypeRouting result = Stream.of(r1970, r2016)
					.min(CurrencyConversionTypeRouting.moreSpecificFirstComparator())
					.orElse(null);

			assertThat(result).isSameAs(r2016);
		}
	}
}