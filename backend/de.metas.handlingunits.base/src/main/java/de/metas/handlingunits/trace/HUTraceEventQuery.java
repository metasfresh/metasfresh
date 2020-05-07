package de.metas.handlingunits.trace;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.OptionalInt;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
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
 * Used to pass to {@link HUTraceRepository#query(HUTraceEventQuery)} to retrieve {@link HUTraceEvent}s.
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
@Value
@Builder
@Wither
public class HUTraceEventQuery
{
	public enum RecursionMode
	{
		BACKWARD, FORWARD, BOTH, NONE
	}

	@NonNull
	@Default
	RecursionMode recursionMode = RecursionMode.NONE;

	public enum EventTimeOperator
	{
		/**
		 * only expects {@link #eventTime} to be set.
		 */
		EQUAL,

		/**
		 * Expects both {@link #eventTime} and {@link #eventTimeTo} to be set.
		 */
		BETWEEN
	}

	@Default
	EventTimeOperator eventTimeOperator = EventTimeOperator.EQUAL;

	Instant eventTime;

	Instant eventTimeTo;

	@NonNull
	@Default
	OptionalInt huTraceEventId = OptionalInt.empty();

	int orgId;

	HUTraceType type;

	int vhuId;

	int productId;

	BigDecimal qty;

	String vhuStatus;

	int topLevelHuId;

	int vhuSourceId;

	int inOutId;

	int shipmentScheduleId;

	int movementId;

	int ppCostCollectorId;

	int ppOrderId;

	String docStatus;

	/**
	 * Can't be zero if not set, because {@code C_DocType_ID=0} means "new".
	 */
	@NonNull
	@Default
	OptionalInt docTypeId = OptionalInt.empty();

	int huTrxLineId;
}
