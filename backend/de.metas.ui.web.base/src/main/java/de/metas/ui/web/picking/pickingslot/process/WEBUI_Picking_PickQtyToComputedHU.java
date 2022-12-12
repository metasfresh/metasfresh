/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.picking.pickingslot.process;

import de.metas.handlingunits.picking.PickingService;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;

public class WEBUI_Picking_PickQtyToComputedHU extends WEBUI_Picking_PickQtyToNewHU
{
	private final PickingService pickingService = SpringContextHolder.instance.getBean(PickingService.class);

	private static final String PARAM_NO_HUs = "HUQty";
	@Param(parameterName = PARAM_NO_HUs, mandatory = true)
	protected BigDecimal noOfHUs;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return preconditionsResolution.get();
		}

		if (isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_ForcePickToNewHU' in case of force shipping! ");
		}

		if (noSourceHUAvailable())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Consumer<Quantity> pickHU = this::pickHu;

		pickQtyToComputedHUs(pickHU);

		invalidatePackablesView(); // left side view
		invalidatePickingSlotsView(); // right side view

		return MSG_OK;
	}

	protected void pickQtyToComputedHUs(@NonNull final Consumer<Quantity> pickHUConsumer)
	{
		final Quantity qtyToPack = getQtyToPack();
		if (qtyToPack.signum() <= 0)
		{
			throw new AdempiereException("@QtyCU@ > 0");
		}

		final Quantity actualCapacity = getActualCapacityAsQty();

		Quantity qtyCULeftToBeProcess = Quantity.of(qtyCU, getCurrentShipmentScheuduleUOM());

		while (qtyCULeftToBeProcess.signum() > 0)
		{
			final Quantity qtyCUToProcess = actualCapacity.min(qtyCULeftToBeProcess);

			pickHUConsumer.accept(qtyCUToProcess);

			qtyCULeftToBeProcess = qtyCULeftToBeProcess.subtract(qtyCUToProcess.toBigDecimal());
		}
	}

	@NonNull
	protected Quantity getActualCapacityAsQty()
	{
		final Capacity piipCapacity = pickingService.calculatePIIPCapacity(getCalculatePIIPCapacityRequest(qtyCU, huPIItemProduct));
		return piipCapacity.isInfiniteCapacity()
				? Quantity.of(qtyCU, getCurrentShipmentScheuduleUOM())
				: piipCapacity.toQuantity();
	}
}
