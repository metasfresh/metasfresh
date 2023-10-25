/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.impl;

import de.metas.calendar.standard.CalendarId;
import de.metas.contracts.FlatrateTransitionId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlatrateTransitionBL
{
	@NonNull
	private final FlatrateTransitionDAO flatrateTransitionDAO;

	@NonNull
	public FlatrateTransitionId getOrCreateTransition(
			@NonNull final CalendarId calendarId,
			@NonNull final FlatrateTransitionId flatrateTransitionTemplateId,
			@NonNull final String namePrefix)
	{
		final FlatrateTransition templateFlatrateTransition = flatrateTransitionDAO.getById(flatrateTransitionTemplateId);

		return flatrateTransitionDAO.getOrCreate(calendarId, templateFlatrateTransition, namePrefix);
	}
}
