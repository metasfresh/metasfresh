/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.currency.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionType;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyConversionTypesMapTest
{
	private final ZoneId zoneId = ZoneId.of("Europe/Berlin");

	private static final CurrencyConversionType SPOT = CurrencyConversionType.builder().id(CurrencyConversionTypeId.ofRepoId(114)).method(ConversionTypeMethod.Spot).build();
	private static final CurrencyConversionType COMPANY = CurrencyConversionType.builder().id(CurrencyConversionTypeId.ofRepoId(201)).method(ConversionTypeMethod.Company).build();

	@BeforeEach
	public void beforeEach()
	{
		SystemTime.setFixedTimeSource(LocalDate.parse("2022-11-12").atStartOfDay(zoneId));
	}

	private Instant date(final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(zoneId).toInstant();
	}

	@Test
	void standardCase()
	{
		final CurrencyConversionTypesMap map = CurrencyConversionTypesMap.builder()
				.types(ImmutableList.of(SPOT, COMPANY))
				.routings(ImmutableList.of(
						CurrencyConversionTypeRouting.builder()
								.conversionTypeId(SPOT.getId())
								.clientId(ClientId.SYSTEM).orgId(OrgId.ANY)
								.validFrom(date("1970-01-01"))
								.build(),
						CurrencyConversionTypeRouting.builder()
								.conversionTypeId(COMPANY.getId())
								.clientId(ClientId.METASFRESH).orgId(OrgId.ANY)
								.validFrom(date("2016-01-01"))
								.build()))
				.build();

		assertThat(map.getDefaultConversionType(ClientId.METASFRESH, OrgId.ANY, date("2022-05-13")))
				.isSameAs(COMPANY);
	}
}