/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.currency;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The newest-rates read is done DB-side (native {@code DISTINCT ON}), which the in-memory POJO layer cannot run,
 * so it is covered by the cucumber {@code CurrencyConversion_api} {@code GET newestRates} scenario. Only the
 * pure-Java empty-query guard is POJO-runnable and lives here.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ConversionRateRepositoryTest
{
	private ConversionRateRepository conversionRateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		conversionRateRepository = new ConversionRateRepository();
	}

	@Test
	void getByQuery_emptyQuery_isRejected()
	{
		assertThat(ConversionRateQuery.builder().build()).isEqualTo(ConversionRateQuery.EMPTY);

		assertThatThrownBy(() -> conversionRateRepository.getByQuery(ConversionRateQuery.EMPTY))
				.as("an empty query must be rejected (it would match every row)")
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("non-empty query");
	}
}
