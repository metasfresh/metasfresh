package de.metas.invoice;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import de.metas.invoice.InvoiceSchedule.Frequency;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InvoiceScheduleTest
{

	@Test
	public void calcualteNextDateToInvoice_MONTLY_every_month_at11th()
	{
		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(10))
				.frequency(Frequency.MONTLY)
				.invoiceDistance(1)
				.invoiceDayOfMonth(11)
				.build();

		final LocalDate result = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-10"));
		assertThat(result).isEqualTo("2018-09-11");

		final LocalDate result2 = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-11"));
		assertThat(result2).isEqualTo("2018-09-11");

		final LocalDate result3 = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-12"));
		assertThat(result3).isEqualTo("2018-10-11");
	}

	@Test
	public void calcualteNextDateToInvoice_MONTLY_every_12months_at11th()
	{
		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(10))
				.frequency(Frequency.MONTLY)
				.invoiceDistance(11)
				.invoiceDayOfMonth(11)
				.build();

		final LocalDate result = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-10"));
		assertThat(result).isEqualTo("2018-09-11");

		final LocalDate result2 = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-11"));
		assertThat(result2).isEqualTo("2018-09-11");

		final LocalDate result3 = invoiceSchedule.calculateNextDateToInvoice(LocalDate.parse("2018-09-12"));
		assertThat(result3).isEqualTo("2019-09-11");
	}

}
