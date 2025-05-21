/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Profile(Profiles.PROFILE_MaterialDispo) // we want only one component to bother itself with SupplyRequiredEvents
@RequiredArgsConstructor
public class SupplyRequiredDecreasedHandler implements MaterialEventHandler<SupplyRequiredDecreasedEvent>
{
	private static final Logger log = LogManager.getLogger(SupplyRequiredDecreasedHandler.class);
	private static final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final List<SupplyRequiredAdvisor> supplyRequiredAdvisors;
	@NonNull private final SupplyRequiredHandlerHelper helper;

	@Override
	public Collection<Class<? extends SupplyRequiredDecreasedEvent>> getHandledEventType()
	{
		return ImmutableList.of(SupplyRequiredDecreasedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final SupplyRequiredDecreasedEvent event)
	{
		final SupplyRequiredDescriptor descriptor = event.getSupplyRequiredDescriptor();
		final MaterialPlanningContext context = helper.createContextOrNull(descriptor);
		final MaterialDescriptor materialDescriptor = descriptor.getMaterialDescriptor();
		Quantity remainingQtyToHandle = Quantitys.of(materialDescriptor.getQuantity(), ProductId.ofRepoId(materialDescriptor.getProductId()));
		if (context != null)
		{
			for (final SupplyRequiredAdvisor advisor : supplyRequiredAdvisors)
			{
				if (remainingQtyToHandle.signum() > 0)
				{
					remainingQtyToHandle = advisor.handleQuantityDecrease(event, remainingQtyToHandle);
				}
			}
		}

		if (remainingQtyToHandle.signum() > 0)
		{
			Loggables.withLogger(log, Level.WARN).addLog("Could not decrease the qty for order {}.", descriptor.getOrderId());
			//TODO notify and throw error
		}
	}
}