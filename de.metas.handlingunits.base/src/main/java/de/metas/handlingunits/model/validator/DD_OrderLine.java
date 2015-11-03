package de.metas.handlingunits.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.DDOrderLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;

/**
 * @author al
 */
@Validator(I_DD_OrderLine.class)
public class DD_OrderLine
{
	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.handlingunits.callout.DD_OrderLine());
	}

	private void updateQtyPacks(final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);
		Services.get(IHUPackingAwareBL.class).setQtyPacks(packingAware);
	}

	/**
	 * Change PIIP when product changes
	 *
	 * @param ddOLPO
	 */
	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_DD_OrderLine.COLUMNNAME_M_Product_ID
			})
	public void add_M_HU_PI_Item_Product(final org.eevolution.model.I_DD_OrderLine ddOLPO)
	{
		final I_C_OrderLine soOrderLine = ddOLPO.getC_OrderLineSO();
		if (isSODDOrderLine(ddOLPO, soOrderLine))
		{
			return;
		}
		final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.create(ddOLPO, de.metas.handlingunits.model.I_DD_OrderLine.class);

		//
		// Find the PIIP
		final IHUDocumentHandler handler = Services.get(IHUDocumentHandlerFactory.class).createHandler(I_DD_OrderLine.Table_Name);
		handler.applyChangesFor(ddOLPO);

		//
		// Refresh the QtyEnteredTU according to the PIIP
		updateQtyPacks(ddOrderLine);
	}

	/**
	 * Update calculated qty depending on qtyEntered / PIIP changes
	 *
	 * @param ddOLPO
	 */
	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_DD_OrderLine.COLUMNNAME_QtyEntered
					, de.metas.handlingunits.model.I_DD_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
			})
	public void recalculateQtyPacks(final org.eevolution.model.I_DD_OrderLine ddOLPO)
	{
		final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.create(ddOLPO, de.metas.handlingunits.model.I_DD_OrderLine.class);
		updateQtyPacks(ddOrderLine);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID
			})
	public void autoFillForSODDOrderLine(final org.eevolution.model.I_DD_OrderLine ddOLPO)
	{
		final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.create(ddOLPO, de.metas.handlingunits.model.I_DD_OrderLine.class);
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

	private static final boolean isSODDOrderLine(final I_DD_OrderLine ddOrderLine, final I_C_OrderLine soOrderLine)
	{
		if (soOrderLine == null)
		{
			return false; // No available C_OrderLine to use
		}

		//
		// We need to make sure that we're matching sales documents and that the M_HU_PI_Item_Product are for the product of the SO and Distribution Order
		if (ddOrderLine.getDD_Order().isSOTrx() != soOrderLine.getC_Order().isSOTrx()
				|| ddOrderLine.getM_Product_ID() != soOrderLine.getM_Product_ID())
		{
			return false;
		}

		return true;
	}
	
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void clearHUsScheduledToMoveList(final I_DD_OrderLine ddOrderline)
	{
		Services.get(IHUDDOrderDAO.class).clearHUsScheduledToMoveList(ddOrderline);
	}
}
