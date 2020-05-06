package de.metas.material.dispo.commons.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DateAndSeqNoTest
{
	private final Instant t1 = Instant.parse("2017-11-22T00:00:00.00Z");
	private final Instant t2 = t1.plus(10, ChronoUnit.MINUTES);

	@Test
	public void isAfter()
	{
		final DateAndSeqNo dateAndSeqNo1 = DateAndSeqNo.atTimeNoSeqNo(t1);
		final DateAndSeqNo dateAndSeqNo2 = DateAndSeqNo.atTimeNoSeqNo(t2);

		assertThat(dateAndSeqNo1.isAfter(dateAndSeqNo2)).isFalse();
		assertThat(dateAndSeqNo2.isAfter(dateAndSeqNo2)).isFalse();
		assertThat(dateAndSeqNo2.isAfter(dateAndSeqNo1)).isTrue();
	}

	@Test
	public void isBefore()
	{
		final DateAndSeqNo dateAndSeqNo1 = DateAndSeqNo.atTimeNoSeqNo(t1);
		final DateAndSeqNo dateAndSeqNo2 = DateAndSeqNo.atTimeNoSeqNo(t2);

		assertThat(dateAndSeqNo1.isBefore(dateAndSeqNo2)).isTrue();
		assertThat(dateAndSeqNo2.isBefore(dateAndSeqNo2)).isFalse();
		assertThat(dateAndSeqNo2.isBefore(dateAndSeqNo1)).isFalse();
	}

}
