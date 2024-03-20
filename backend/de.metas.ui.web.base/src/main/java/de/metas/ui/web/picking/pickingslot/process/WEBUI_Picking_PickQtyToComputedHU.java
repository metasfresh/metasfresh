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

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;

public class WEBUI_Picking_PickQtyToComputedHU extends WEBUI_Picking_PickQtyToNewHU implements IProcessParametersCallout
{
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private static final String PARAM_NO_HUs = "HUQty";
	@Param(parameterName = PARAM_NO_HUs, mandatory = true)
	protected BigDecimal noOfHUs;

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (qtyCUsPerTU == null || huPIItemProduct == null)
		{
			return;
		}

		if (parameterName.equals(PARAM_M_HU_PI_Item_Product_ID) || parameterName.equals(PARAM_QtyCUsPerTU))
		{
			noOfHUs = getQtyTU().toBigDecimal();
		}
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_NO_HUs, parameter.getColumnName()))
		{
			return getDefaultNrOfHUs();
		}
		else
		{
			return super.getParameterDefaultValue(parameter);
		}
	}

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
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_ForcePickToComputedHU' in case of force shipping! ");
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
		pickQtyToNewHUs(this::pickQtyToNewHU);

		invalidatePackablesView(); // left side view
		invalidatePickingSlotsView(); // right side view

		return MSG_OK;
	}

	protected void pickQtyToNewHUs(@NonNull final Consumer<Quantity> pickQtyConsumer)
	{
		Quantity qtyToPack = getQtyToPack();
		if (qtyToPack.signum() <= 0)
		{
			throw new AdempiereException("@QtyCU@ > 0");
		}

		final Capacity piipCapacity = getPIIPCapacity();

		if (piipCapacity.isInfiniteCapacity())
		{
			pickQtyConsumer.accept(qtyToPack);
			return;
		}

		final Quantity piipQtyCapacity = piipCapacity.toQuantity();

		while (qtyToPack.toBigDecimal().signum() > 0)
		{
			final Quantity qtyToProcess = piipQtyCapacity.min(qtyToPack);

			pickQtyConsumer.accept(qtyToProcess);

			qtyToPack = qtyToPack.subtract(qtyToProcess);
		}
	}

	@NonNull
	private QuantityTU getQtyTU()
	{
		return getPIIPCapacity().calculateQtyTU(qtyCUsPerTU, getCurrentShipmentScheuduleUOM(), uomConversionBL)
				.orElseThrow(() -> new AdempiereException("QtyTU cannot be obtained for the current request!")
						.appendParametersToMessage()
						.setParameter("QtyCUsPerTU", qtyCUsPerTU)
						.setParameter("ShipmentScheduleId", getCurrentShipmentScheduleId()));
	}

	@NonNull
	protected Capacity getPIIPCapacity()
	{
		final I_M_ShipmentSchedule currentShipmentSchedule = getCurrentShipmentSchedule();

		final ProductId productId = ProductId.ofRepoId(currentShipmentSchedule.getM_Product_ID());
		final I_C_UOM stockUOM = productBL.getStockUOM(productId);

		return huCapacityBL.getCapacity(huPIItemProduct, productId, stockUOM);
	}

	@NonNull
	private BigDecimal getDefaultNrOfHUs()
	{
		if (qtyCUsPerTU == null || huPIItemProduct == null)
		{
			return BigDecimal.ONE;
		}

		return getQtyTU().toBigDecimal();
	}
}
