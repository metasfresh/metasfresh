package de.metas.handlingunits.trace;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.OptionalInt;

import de.metas.handlingunits.trace.HUTraceEventQuery.HUTraceEventQueryBuilder;
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
@Value
@Builder
@Wither
public class HUTraceEvent
{
	/**
	 * If the event is coming out of the repo, the ID is present. If we create a new huTraceEvent to be added to the repo, it's not.
	 */
	@NonNull
	@Default
	OptionalInt huTraceEventId = OptionalInt.empty();

	@NonNull
	Integer orgId;

	@NonNull
	HUTraceType type;

	@NonNull
	Instant eventTime;

	@NonNull
	Integer vhuId;

	int productId;

	@NonNull
	BigDecimal qty;

	@NonNull
	String vhuStatus;

	/**
	 * The topmost HU as seen from the vhu.
	 */
	@NonNull
	Integer topLevelHuId;

	int vhuSourceId;

	int inOutId;

	int shipmentScheduleId;

	int movementId;

	int ppCostCollectorId;

	int ppOrderId;

	String docStatus;

	/**
	 * Needs to be {@code null} if not set, because {@code C_DocType_ID=0} means "new".
	 */
	@NonNull
	@Default
	OptionalInt docTypeId = OptionalInt.empty();

	int huTrxLineId;

	public HUTraceEventQueryBuilder asQueryBuilder()
	{
		return HUTraceEventQuery.builder()
				.huTraceEventId(huTraceEventId)
				.orgId(orgId)
				.type(type)
				.eventTime(eventTime)
				.vhuId(vhuId)
				.productId(productId)
				.qty(qty)
				.vhuStatus(vhuStatus)
				.inOutId(inOutId)
				.shipmentScheduleId(shipmentScheduleId)
				.movementId(movementId)
				.ppCostCollectorId(ppCostCollectorId)
				.ppOrderId(ppOrderId)
				.docStatus(docStatus)
				.docTypeId(docTypeId)
				.huTrxLineId(huTrxLineId);

	}
}
