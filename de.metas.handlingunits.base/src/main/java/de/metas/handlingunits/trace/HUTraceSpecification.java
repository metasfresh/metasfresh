package de.metas.handlingunits.trace;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Builder.Default;
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

/**
 * Used to pass to {@link HUTraceRepository#query(HUTraceSpecification)} to retrieve {@link HUTraceEvent}s.
 * 
 * This class has the properties that {@link HUTraceEvent} has, but the following differences:
 * <ul>
 * <li>none of those properties is mandatory, all may be {@code null}
 * <li>there is the mandatory {@link RecursionMode}
 * </ul>
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
@Builder
@Wither
public class HUTraceSpecification
{
	enum RecursionMode
	{
		BACKWARD, FORWARD, NONE
	}

	@NonNull
	@Default
	private RecursionMode recursionMode = RecursionMode.NONE;

	private final HUTraceType type;

	private final Instant eventTime;

	private final int vhuId;

	private final int productId;

	private final BigDecimal qty;

	private final String vhuStatus;

	private final int topLevelHuId;

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
}
