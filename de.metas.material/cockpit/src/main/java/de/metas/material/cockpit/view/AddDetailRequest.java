package de.metas.material.cockpit.view;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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
public class AddDetailRequest
{
	DataRecordIdentifier identifier;

	int orderLineId;
	int orderId;

	int subscriptionLineId;
	int subscriptionId;

	public AddDetailRequest(
			@NonNull DataRecordIdentifier identifier,
			int orderLineId,
			int orderId,
			int subscriptionLineId,
			int subscriptionId)
	{
		this.identifier = identifier;

		this.orderLineId = orderLineId;
		this.orderId = orderId;

		this.subscriptionLineId = subscriptionLineId;
		this.subscriptionId = subscriptionId;

		validate();
	}

	public final void validate()
	{
		final boolean orderLineIdSet = orderLineId > 0;
		final boolean subscriptionLineIdSet = subscriptionLineId > 0;

		Check.errorUnless(orderLineIdSet ^ subscriptionLineIdSet,
				"Either orderLineId or subscriptionLineId need to be > 0 (but not both!); orderLineId={}; subscriptionLineId={}",
				orderLineId, subscriptionLineId);

		Check.errorIf(orderLineIdSet && orderId <= 0,
				"If orderLineId is > 0, then orderId also needs to be > 0; orderLineId={}, orderId={}",
				orderLineId, orderId);

		Check.errorIf(subscriptionLineIdSet && subscriptionId <= 0,
				"If subscriptionLineId is > 0, then orderId also needs to be > 0; subscriptionLineId={}, subscriptionId={}",
				subscriptionLineId, subscriptionId);

	}
}
