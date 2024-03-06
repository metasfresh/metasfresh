package de.metas.material.dispo.commons.repository.query;

import de.metas.common.util.IdConstants;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.common.util.IdConstants.UNSPECIFIED_REPO_ID;
import static de.metas.common.util.IdConstants.toUnspecifiedIfZero;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class DemandDetailsQuery
{

	public static DemandDetailsQuery ofShipmentScheduleId(final int shipmentScheduleId)
	{
		Check.assumeGreaterThanZero(shipmentScheduleId, "shipmentScheduleId");
		return new DemandDetailsQuery(
				shipmentScheduleId,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID);
	}

	public static DemandDetailsQuery ofForecastLineId(final int forecastLineId)
	{
		return new DemandDetailsQuery(
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				forecastLineId,
				UNSPECIFIED_REPO_ID);
	}

	public static DemandDetailsQuery ofDemandDetailOrNull(@Nullable final DemandDetail demandDetail)
	{
		return demandDetail != null ? ofDemandDetail(demandDetail) : null;
	}

	public static DemandDetailsQuery ofDemandDetail(@NonNull final DemandDetail demandDetail)
	{
		return new DemandDetailsQuery(
				toUnspecifiedIfZero(demandDetail.getShipmentScheduleId()),
				toUnspecifiedIfZero(demandDetail.getOrderLineId()),
				toUnspecifiedIfZero(demandDetail.getSubscriptionProgressId()),
				toUnspecifiedIfZero(demandDetail.getForecastLineId()),
				toUnspecifiedIfZero(demandDetail.getInOutLineId()));
	}

	public static DemandDetailsQuery forDocumentLine(
			@NonNull final DocumentLineDescriptor documentLineDescriptor)
	{
		if (documentLineDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.cast(documentLineDescriptor);
			return new DemandDetailsQuery(
					UNSPECIFIED_REPO_ID,
					orderLineDescriptor.getOrderLineId(),
					UNSPECIFIED_REPO_ID,
					UNSPECIFIED_REPO_ID,
					UNSPECIFIED_REPO_ID);
		}
		else if (documentLineDescriptor instanceof SubscriptionLineDescriptor)
		{
			final SubscriptionLineDescriptor subscriptionLineDescriptor = SubscriptionLineDescriptor.cast(documentLineDescriptor);
			return new DemandDetailsQuery(
					UNSPECIFIED_REPO_ID,
					UNSPECIFIED_REPO_ID,
					subscriptionLineDescriptor.getSubscriptionProgressId(),
					UNSPECIFIED_REPO_ID,
					UNSPECIFIED_REPO_ID);
		}
		else
		{
			Check.fail("documentLineDescriptor has unsupported type={}; documentLineDescriptor={}", documentLineDescriptor.getClass(), documentLineDescriptor);
		}

		return null;
	}

	public static DemandDetailsQuery ofShipmentLineId(final int inOutLineId)
	{
		Check.assumeGreaterThanZero(inOutLineId, "inOutLineId");
		return new DemandDetailsQuery(
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				UNSPECIFIED_REPO_ID,
				inOutLineId);
	}

	int shipmentScheduleId;

	int orderLineId;
	int subscriptionLineId;

	int forecastLineId;

	int inOutLineId;

	private DemandDetailsQuery(
			final int shipmentScheduleId,
			final int orderLineId,
			final int subscriptionLineId,
			final int forecastLineId,
			final int inOutLineId)
	{
		this.shipmentScheduleId = shipmentScheduleId;
		this.orderLineId = orderLineId;
		this.subscriptionLineId = subscriptionLineId;
		this.forecastLineId = forecastLineId;
		this.inOutLineId = inOutLineId;

		IdConstants.assertValidId(shipmentScheduleId, "shipmentScheduleId");
		IdConstants.assertValidId(orderLineId, "orderLineId");
		IdConstants.assertValidId(subscriptionLineId, "subscriptionLineId");
		IdConstants.assertValidId(forecastLineId, "forecastLineId");
		IdConstants.assertValidId(inOutLineId, "inOutLineId");
	}
}
