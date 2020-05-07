package de.metas.material.event.ddorder;

import org.adempiere.util.Check;
import org.eevolution.model.I_DD_Order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
/**
 * Send by the material planner when it came up with a distribution plan that could be turned into an {@link I_DD_Order}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DDOrderAdvisedEvent extends AbstractDDOrderEvent
{
	public static final String TYPE = "DDOrderAdvisedEvent";

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual DD_Order to be created.
	 */
	@Getter
	private final boolean advisedToCreateDDrder;

	@Getter
	private final boolean pickIfFeasible;

	@JsonCreator
	@Builder
	public DDOrderAdvisedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("ddOrder") final DDOrder ddOrder,
			@JsonProperty("fromWarehouseId") final int fromWarehouseId,
			@JsonProperty("toWarehouseId") final int toWarehouseId,
			@JsonProperty("supplyRequiredDescriptor") final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@JsonProperty("advisedToCreateDDrder") final boolean advisedToCreateDDrder,
			@JsonProperty("pickIfFeasible") final boolean pickIfFeasible)
	{
		super(eventDescriptor, ddOrder, fromWarehouseId, toWarehouseId, supplyRequiredDescriptor);

		this.advisedToCreateDDrder = advisedToCreateDDrder;
		this.pickIfFeasible = pickIfFeasible;
	}

	@Override
	public void validate()
	{
		super.validate();

		final DDOrder ddOrder = getDdOrder();
		Check.errorIf(ddOrder.getDdOrderId() > 0,
				"The given ddOrderAdvisedEvent's ddOrder may not yet have an ID; ddOrder={}", ddOrder);

		checkRequiredPropertiesForLookup(ddOrder);
	}

	/**
	 * we all this to find out if a DDOrderAdvisedEvent belongs to already pre-existing candidates.
	 */
	private void checkRequiredPropertiesForLookup(final DDOrder ddOrder)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = getSupplyRequiredDescriptor();
		Check.errorIf(supplyRequiredDescriptor == null, "The given ppOrderAdvisedEvent has no supplyRequiredDescriptor");
		supplyRequiredDescriptor.validate();

		final int productPlanningId = ddOrder.getProductPlanningId();
		Check.errorIf(productPlanningId <= 0,
				"The given ppOrderAdvisedEvent event has a ppOrder with productPlanningId={}", productPlanningId);

		ddOrder.getLines().forEach(ddOrderLine -> {

			final int networkDistributionLineId = ddOrderLine.getNetworkDistributionLineId();
			Check.errorIf(networkDistributionLineId <= 0,
					"The given ppOrderAdvisedEvent event has a ppOrderLine with networkDistributionLineId={}; ddOrderLine={}",
					networkDistributionLineId, ddOrderLine);
		});
	}
}
