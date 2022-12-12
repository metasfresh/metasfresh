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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.picking.PickingService;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

public class WEBUI_Picking_ForcePickToComputedHU extends WEBUI_Picking_PickQtyToComputedHU
		implements IProcessPrecondition, IProcessParametersCallout
{
	private final PickingService pickingService = SpringContextHolder.instance.getBean(PickingService.class);

	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(preconditionsResolution.get().getRejectReason());
		}

		if (!isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_PickQtyToComputedHU' for non force shipping records!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (!parameterName.equals(PARAM_M_HU_PI_Item_Product_ID))
		{
			return;
		}

		noOfHUs = hupiItemProductBL.isVirtualHUPIItemProduct(huPIItemProduct) ? BigDecimal.ZERO : computeNumberOfHUs();
	}

	protected String doIt()
	{
		final Consumer<Quantity> forcePickHU = this::forcePick;

		pickQtyToComputedHUs(forcePickHU);

		invalidatePackablesView();
		invalidatePickingSlotsView();
		return MSG_OK;
	}

	private void forcePick(@NonNull final Quantity qtyCUToProcess)
	{
		final HuId packToHuId = createNewHuId();
		forcePick(qtyCUToProcess, packToHuId);

		printPickingLabel(packToHuId);
	}

	@NonNull
	private BigDecimal computeNumberOfHUs()
	{
		final Capacity capacity = pickingService.calculatePIIPCapacity(getCalculatePIIPCapacityRequest(qtyCU == null ? BigDecimal.ZERO : qtyCU, huPIItemProduct));

		final QuantityTU quantityTU = capacity.calculateQtyTU(qtyCU, getCurrentShipmentScheuduleUOM(), uomConversionBL)
				.orElseThrow(() -> new AdempiereException("Invalid capacity: " + capacity));

		return quantityTU.toBigDecimal();
	}
}
