/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.alberta.common;

import org.junit.jupiter.api.Test;
import org.threeten.bp.OffsetDateTime;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

class AlbertaUtilTest
{
	@Test
	void asInstant1()
	{
		final Instant instant1 = AlbertaUtil.asInstant(OffsetDateTime.parse("2021-07-15T10:20:37.123Z"));
		assertThat(instant1).isEqualTo(Instant.parse("2021-07-15T10:20:37.123Z"));
	}

	@Test
	void asInstant2()
	{
		final Instant instant2 = AlbertaUtil.asInstant(OffsetDateTime.parse("2021-07-15T10:20:37.999Z"));
		assertThat(instant2).isEqualTo(Instant.parse("2021-07-15T10:20:37.999Z"));
	}

	@Test
	void asInstant3()
	{
		final Instant instant3 = AlbertaUtil.asInstant(OffsetDateTime.parse("2021-07-15T10:20:37Z"));
		assertThat(instant3).isEqualTo(Instant.parse("2021-07-15T10:20:37.000Z"));
	}

	@Test
	void asInstantNotNull()
	{
		final Instant instant3 = AlbertaUtil.asInstantNotNull("2021-11-11T15:55:07.478Z");
		assertThat(instant3).isEqualTo(Instant.parse("2021-11-11T15:55:07.478Z"));
	}

	
}