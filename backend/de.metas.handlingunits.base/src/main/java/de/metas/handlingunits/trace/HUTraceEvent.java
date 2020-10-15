package de.metas.handlingunits.trace;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.trace.HUTraceEventQuery.HUTraceEventQueryBuilder;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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
@Value
@Builder(toBuilder = true)
public class HUTraceEvent
{
	/**
	 * If the event is coming out of the repo, the ID is present. If we create a new huTraceEvent to be added to the repo, it's not.
	 */
	@NonNull
	@Default
	OptionalInt huTraceEventId = OptionalInt.empty();

	@NonNull
	OrgId orgId;

	@NonNull
	HUTraceType type;

	@NonNull
	Instant eventTime;

	@NonNull
	HuId vhuId;

	ProductId productId;

	@NonNull
	BigDecimal qty;

	@NonNull
	String vhuStatus;

	/**
	 * The topmost HU as seen from the vhu.
	 */
	@NonNull
	HuId topLevelHuId;

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
				.huTrxLineId(huTrxLineId)
		        .vhuSourceId(vhuSourceId);

	}
}
