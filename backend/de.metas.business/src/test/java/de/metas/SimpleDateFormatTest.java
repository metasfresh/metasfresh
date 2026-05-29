/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

public class SimpleDateFormatTest
{
    /**
     * Make sure that when parsing dates with the pattern {@code "dd.MM.yy"}, both yy and yyyy works.
     * <p> 
     * Background: we sometimes get 21.03.2025 and sometimes 21.03.25 when importing csv-Data.
     */
	@Test
	public void parse() throws ParseException
	{
        // given
		final String date1 = "21.03.2025";
        final String date2 = "21.03.25";

        // when
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        final Date parsedDate1 = simpleDateFormat.parse(date1);
        final Date parsedDate2 = simpleDateFormat.parse(date2);

        // then
		assertThat(parsedDate1).isEqualTo(parsedDate2);
	}
}