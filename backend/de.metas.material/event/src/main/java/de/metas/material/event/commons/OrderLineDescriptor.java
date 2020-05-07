package de.metas.material.event.commons;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
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
public class OrderLineDescriptor implements DocumentLineDescriptor
{
	int orderLineId;
	int orderId;
	int orderBPartnerId;
	int docTypeId;


	@Builder
	@JsonCreator
	public OrderLineDescriptor(
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("orderId") final int orderId,
			@JsonProperty("orderBPartnerId") final int orderBPartnerId,
			@JsonProperty("docTypeId") final int docTypeId)
	{
		this.orderLineId = orderLineId;
		this.orderId = orderId;
		this.orderBPartnerId = orderBPartnerId;
		this.docTypeId = docTypeId;
	}

	@Override
	public void validate()
	{
		checkIdGreaterThanZero("orderLineId", orderLineId);
		checkIdGreaterThanZero("orderId", orderId);
		checkIdGreaterThanZero("orderBPartnerId", orderBPartnerId);
		checkIdGreaterThanZero("docTypeId", docTypeId);
	}
}
