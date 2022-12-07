package de.metas.handlingunits.trace;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;

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
@With
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

	OrgId orgId;

	HUTraceType type;

	@NonNull
	@Singular
	ImmutableSet<HuId> vhuIds;

	ProductId productId;

	BigDecimal qty;

	String vhuStatus;

	@Singular
	@NonNull
	ImmutableSet<HuId> topLevelHuIds;

	HuId vhuSourceId;

	int inOutId;

	ShipmentScheduleId shipmentScheduleId;

	int movementId;

	int ppCostCollectorId;

	int ppOrderId;

	String docStatus;

	@NonNull
	@Default
	Optional<DocTypeId> docTypeId = Optional.empty();

	int huTrxLineId;
}
