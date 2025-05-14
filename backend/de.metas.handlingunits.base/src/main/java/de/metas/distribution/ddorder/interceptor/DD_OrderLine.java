package de.metas.distribution.ddorder.interceptor;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.model.DDOrderLineHUPackingAware;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.QtyTU;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Validator(I_DD_OrderLine.class)
@Callout(value = I_DD_OrderLine.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
public class DD_OrderLine
{
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;

	public DD_OrderLine(
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
	}

	@Init
	public void registerCallout()
	{
		// this class serves as both model validator a callout
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	/**
	 * Change PIIP when product changes
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_M_Product_ID
	})
	public void add_M_HU_PI_Item_Product(final I_DD_OrderLine ddOrderLine)
	{
		final I_C_OrderLine soOrderLine = ddOrderLine.getC_OrderLineSO();
		if (isSODDOrderLine(ddOrderLine, soOrderLine))
		{
			return;
		}

		//
		// Find the PIIP
		final IHUDocumentHandler handler = Services.get(IHUDocumentHandlerFactory.class).createHandler(I_DD_OrderLine.Table_Name);
		handler.applyChangesFor(ddOrderLine);

		//
		// Refresh the QtyEnteredTU according to the PIIP
		updateQtyPacks(ddOrderLine);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID
	})
	public void autoFillForSODDOrderLine(I_DD_OrderLine ddOrderLine)
	{

		if (ddOrderLine.getM_HU_PI_Item_Product() != null)
		{
			return; // already filled, don't fill again
		}

		final I_C_OrderLine soOrderLine = ddOrderLine.getC_OrderLineSO();
		if (!isSODDOrderLine(ddOrderLine, soOrderLine))
		{
			return;
		}

		//
		// Copy from C_OrderLine
		final de.metas.handlingunits.model.I_C_OrderLine soOrderLineExt = InterfaceWrapperHelper.create(soOrderLine, de.metas.handlingunits.model.I_C_OrderLine.class);
		ddOrderLine.setM_HU_PI_Item_Product(soOrderLineExt.getM_HU_PI_Item_Product());

		//
		// Refresh TU qty according to PI specification
		updateQtyPacks(ddOrderLine);
	}

	private static boolean isSODDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine, @Nullable final I_C_OrderLine soOrderLine)
	{
		if (soOrderLine == null)
		{
			return false; // No available C_OrderLine to use
		}

		//
		// We need to make sure that we're matching sales documents and that the M_HU_PI_Item_Product are for the product of the SO and Distribution Order
		return ddOrderLine.getDD_Order().isSOTrx() == soOrderLine.getC_Order().isSOTrx()
				&& ddOrderLine.getM_Product_ID() == soOrderLine.getM_Product_ID();
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeNotStartedSchedules(final I_DD_OrderLine ddOrderLine)
	{
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());
		ddOrderMoveScheduleService.removeNotStarted(ddOrderLineId);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void onM_HU_PI_Item_Product_Set(final I_DD_OrderLine ddOrderLine)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);

		// if the M_HU_PI_Item_Product was deleted:
		// QtyTU must be 0
		// QtyCU must remain the same
		if (packingAware.getM_HU_PI_Item_Product_ID() <= 0)
		{
			packingAware.setQtyTU(BigDecimal.ZERO);
			return;
		}

		// if there is no QtyTU set yet, it will be initially set to 1
		if (packingAware.getQtyTU().signum() <= 0)
		{
			packingAware.setQtyTU(BigDecimal.ONE);
		}

		// if the M_HU_PI_Item_Product was changed and the QtyTU was already set, change the CU accordingly
		final QtyTU qtyPacks = QtyTU.ofBigDecimal(packingAware.getQtyTU());
		Services.get(IHUPackingAwareBL.class).setQtyCUFromQtyTU(packingAware, qtyPacks.toInt());
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_QtyEnteredTU
	})
	public void onQtyTU_Set_Intercept(final I_DD_OrderLine ddOrderLine)
	{
		// Make sure the QtyCU is not changed for the second time if the QtyTU change was triggered by the QtyCU setting in the first place
		final boolean qtyCUChanged = InterfaceWrapperHelper.isValueChanged(ddOrderLine, I_DD_OrderLine.COLUMNNAME_QtyEntered);

		if (!qtyCUChanged)
		{
			updateQtyCU(ddOrderLine);
		}

	}

	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_QtyEnteredTU })
	public void onQtyTU_Set_Callout(final I_DD_OrderLine ddOrderLine)
	{
		// update QtyCU
		updateQtyCU(ddOrderLine);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_DD_OrderLine.COLUMNNAME_QtyEntered
	})
	public void onQtyCU_Set_Intercept(final I_DD_OrderLine ddOrderLine)
	{
		// Make sure the QtyTU is not changed for the second time if the QtyCU change was triggered by the QtyTU setting in the first place
		final boolean qtyTUChanged = InterfaceWrapperHelper.isValueChanged(ddOrderLine, I_DD_OrderLine.COLUMNNAME_QtyEnteredTU);

		if (!qtyTUChanged)
		{
			updateQtyPacks(ddOrderLine);
		}
	}

	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_QtyEntered })
	public void onQtyCU_Set_Callout(final I_DD_OrderLine ddOrderLine)
	{
		// update QtyTU
		updateQtyPacks(ddOrderLine);
	}

	private void updateQtyPacks(final I_DD_OrderLine ddOrderLine)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);
		Services.get(IHUPackingAwareBL.class).setQtyTU(packingAware);
	}

	private void updateQtyCU(final I_DD_OrderLine ddOrderLine)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);

		// if the QtyTU was set to 0 or deleted, do nothing
		if (packingAware.getQtyTU().signum() <= 0)
		{
			return;
		}

		// if the QtyTU was changed but there is no M_HU_PI_Item_Product set, do nothing
		if (packingAware.getM_HU_PI_Item_Product_ID() <= 0)
		{
			return;
		}

		// update the QtyCU only if the QtyTU requires it. If the QtyCU is already fine and fits the QtyTU and M_HU_PI_Item_Product, leave it like it is.
		final QtyTU qtyPacks = QtyTU.ofBigDecimal(packingAware.getQtyTU());
		final Quantity qtyCU = Quantitys.of(packingAware.getQty(), UomId.ofRepoId(packingAware.getC_UOM_ID()));
		Services.get(IHUPackingAwareBL.class).updateQtyIfNeeded(packingAware, qtyPacks.toInt(), qtyCU);
	}
}
