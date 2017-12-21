package de.metas.material.event.pporder;

import java.math.BigDecimal;
import java.util.List;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
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
public class PPOrderQtyEnteredChangedEvent implements MaterialEvent
{
	public static final String TYPE = "PPOrderQtyEnteredChangedEvent";

	@NonNull
	private final EventDescriptor eventDescriptor;

	// TODO: handle
	int ppOrderId;

	BigDecimal quantity;

	BigDecimal quantityDelta;

	@Singular
	List<PPOrderLineChangeDescriptor> ppOrderLineChanges;

	@Singular
	List<Integer> deletedPPOrderLineIDs;

	@Singular
	List<PPOrderLine> newPPOrderLines;

	@Value
	@Builder
	public static class PPOrderLineChangeDescriptor
	{
		int oldPPOrderLineId;

		int newPPOrderLineId;

		BigDecimal oldQuantity;

		BigDecimal newQuantity;
	}
}
