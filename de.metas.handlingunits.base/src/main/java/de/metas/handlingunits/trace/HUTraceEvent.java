package de.metas.handlingunits.trace;

import java.time.Instant;

import de.metas.handlingunits.trace.HUTraceSpecification.RecursionMode;
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
	final HUTraceType type;

	@NonNull
	final Instant eventTime;

	@NonNull
	final Integer vhuId;

	@NonNull
	final String vhuStatus;
	
	/**
	 * The topmost HU as seen from the vhu.
	 */
	@NonNull
	final Integer topLevelHuId;

	final int vhuSourceId;

	final int inOutId;

	final int shipmentScheduleId;

	final int movementId;

	final int costCollectorId;

	final int ppOrderId;
	
	final String docStatus;

	final int docTypeId;

	final int huTrxLineId;
	
	public HUTraceSpecification asQuery()
	{
		return new HUTraceSpecification(RecursionMode.NONE,
				type,
				eventTime,
				vhuId,
				vhuStatus,
				topLevelHuId,
				vhuSourceId,
				inOutId,
				shipmentScheduleId,
				movementId,
				costCollectorId,
				ppOrderId,
				docStatus,
				docTypeId,
				huTrxLineId);
	}
}
