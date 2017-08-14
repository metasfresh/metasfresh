package de.metas.handlingunits.trace;

import java.math.BigDecimal;
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
	private final HUTraceType type;

	@NonNull
	private final Instant eventTime;

	@NonNull
	private final Integer vhuId;

	private final int productId;

	@NonNull
	private final BigDecimal qty;

	@NonNull
	private final String vhuStatus;

	/**
	 * The topmost HU as seen from the vhu.
	 */
	@NonNull
	private final Integer topLevelHuId;

	private final int vhuSourceId;

	private final int inOutId;

	private final int shipmentScheduleId;

	private final int movementId;

	private final int costCollectorId;

	private final int ppOrderId;

	private final String docStatus;

	/**
	 * Needs to be {@code null} if not set, because {@code C_DocType_ID=0} means "new".
	 */
	private final Integer docTypeId;

	private final int huTrxLineId;

	public HUTraceSpecification asQuery()
	{
		return new HUTraceSpecification(RecursionMode.NONE,
				type,
				eventTime,
				vhuId,
				productId,
				qty,
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
