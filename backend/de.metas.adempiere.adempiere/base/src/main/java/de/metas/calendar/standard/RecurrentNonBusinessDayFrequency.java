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

package de.metas.calendar.standard;

import de.metas.util.GuavaCollectors;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_NonBusinessDay;

import java.util.Map;
import java.util.stream.Stream;

public enum RecurrentNonBusinessDayFrequency
{
	WEEKLY(X_C_NonBusinessDay.FREQUENCY_Weekly), //
	YEARLY(X_C_NonBusinessDay.FREQUENCY_Yearly) //
	;

	@Getter
	private final String code;

	RecurrentNonBusinessDayFrequency(final String code)
	{
		this.code = code;
	}

	public static RecurrentNonBusinessDayFrequency forCode(final String code)
	{
		final RecurrentNonBusinessDayFrequency value = code2value.get(code);
		if (value == null)
		{
			throw new AdempiereException("No value found for: " + code);
		}
		return value;
	}

	private static final Map<String, RecurrentNonBusinessDayFrequency> code2value = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(RecurrentNonBusinessDayFrequency::getCode));
}
