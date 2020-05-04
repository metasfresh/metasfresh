package de.metas.invoicecandidate.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PlainInvoicingParamsTest
{
	private PlainInvoicingParams defaults;
	private PlainInvoicingParams params;

	@Test
	public void testOverrides()
	{
		defaults = new PlainInvoicingParams();
		params = new PlainInvoicingParams(defaults);

		testBoolean(params::isOnlyApprovedForInvoicing, defaults::setOnlyApprovedForInvoicing);
		testBoolean(params::isConsolidateApprovedICs, defaults::setConsolidateApprovedICs);
		testBoolean(params::isIgnoreInvoiceSchedule, defaults::setIgnoreInvoiceSchedule);
		testBoolean(params::isSupplementMissingPaymentTermIds, defaults::setSupplementMissingPaymentTermIds);

		testBoolean(params::isStoreInvoicesInResult, defaults::setStoreInvoicesInResult);
		testBoolean(params::isAssumeOneInvoice, defaults::setAssumeOneInvoice);

		testValue(
				PlainInvoicingParams::getDateInvoiced,
				(params, value) -> params.setDateInvoiced(value),
				LocalDate.of(2019, Month.SEPTEMBER, 01),
				LocalDate.of(2019, Month.SEPTEMBER, 02));

		testValue(
				PlainInvoicingParams::getDateAcct,
				(params, value) -> params.setDateAcct(value),
				LocalDate.of(2019, Month.SEPTEMBER, 01),
				LocalDate.of(2019, Month.SEPTEMBER, 02));

		testValue(
				PlainInvoicingParams::getPOReference,
				(params, value) -> params.setPOReference(value),
				"poReference1",
				"poReference2");

		testValue(
				PlainInvoicingParams::getCheck_NetAmtToInvoice,
				(params, value) -> params.setCheck_NetAmtToInvoice(value),
				new BigDecimal("123.45"),
				new BigDecimal("123.46"));
	}

	private void testBoolean(
			final BooleanSupplier getter,
			final Consumer<Boolean> setter)
	{
		// Default value
		assertThat(getter.getAsBoolean()).isFalse();

		setter.accept(false);
		assertThat(getter.getAsBoolean()).isFalse();

		setter.accept(true);
		assertThat(getter.getAsBoolean()).isTrue();
	}

	private <T> void testValue(
			final Function<PlainInvoicingParams, T> getter,
			final BiConsumer<PlainInvoicingParams, T> setter,
			final T value1,
			final T value2)
	{
		// Default value
		assertThat(getter.apply(defaults)).isNull();
		assertThat(getter.apply(params)).isNull();

		setter.accept(defaults, value1);
		assertThat(getter.apply(defaults)).isSameAs(value1);
		assertThat(getter.apply(params)).isSameAs(value1);

		setter.accept(params, value2);
		assertThat(getter.apply(defaults)).isSameAs(value1);
		assertThat(getter.apply(params)).isSameAs(value2);
	}

}
