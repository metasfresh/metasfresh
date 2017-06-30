package de.metas.handlingunits.trace;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Data
@Builder
@Wither
public class HUTraceEvent
{
	@NonNull
	final Integer huId;

	@NonNull
	final Instant eventTime;

	@NonNull
	final HUTraceType type;
	
	final int inOutId;

	final int shipmentScheduleId;

	final int movementId;

	final int costCollectorId;

	final int huSourceId;

	final String docStatus;

	final int docTypeId;
}
