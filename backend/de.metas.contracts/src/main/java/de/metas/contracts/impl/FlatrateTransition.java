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
import de.metas.contracts.TermDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class FlatrateTransition
{
	@NonNull FlatrateTransitionId id;

	@NonNull CalendarId calendarId;
	
	@NonNull Boolean endsWithCalendarYear;

	@NonNull Integer termDuration;

	@NonNull TermDurationUnit termDurationUnit;

	@NonNull Integer termOfNotice;

	@NonNull TermDurationUnit termOfNoticeUnit;

	@NonNull Boolean notifyUserInCharge;
}
