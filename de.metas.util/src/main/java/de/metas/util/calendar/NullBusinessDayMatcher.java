package de.metas.util.calendar;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.util
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

/**
 * {@link IBusinessDayMatcher} implementation which considers each day as a working/business day.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class NullBusinessDayMatcher implements IBusinessDayMatcher
{
	public static final transient NullBusinessDayMatcher instance = new NullBusinessDayMatcher();

	@Override
	public boolean isBusinessDay(final LocalDate date)
	{
		return true;
	}

}
