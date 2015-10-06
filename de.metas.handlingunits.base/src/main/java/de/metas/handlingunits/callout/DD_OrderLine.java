package de.metas.handlingunits.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.DDOrderLineHUPackingAware;
import de.metas.handlingunits.model.I_DD_OrderLine;

/**
 * @author al
 */
@Callout(I_DD_OrderLine.class)
public class DD_OrderLine
{
	/**
	 * Task 07255: only updating on QtyEntered, but not on M_HU_PI_Item_Product_ID, because when the HU_PI_Item_Product changes, we want QtyEnteredTU to stay the same.
	 *
	 * @param orderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_QtyEntered, I_DD_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void updateQtyTU(final I_DD_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(orderLine);
		Services.get(IHUPackingAwareBL.class).setQtyPacks(packingAware);
		packingAware.setQty(packingAware.getQty());
	}

	/**
	 * Task 07255: If QtyEnteredTU or M_HU_PI_Item_Product_ID change, then update QtyEntered (i.e. the CU qty).
	 *
	 * @param ddOrderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_DD_OrderLine.COLUMNNAME_QtyEnteredTU })
	public void updateQtyCU(final I_DD_OrderLine ddOrderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);
		final Integer qtyPacks = packingAware.getQtyPacks().intValue();
		Services.get(IHUPackingAwareBL.class).setQty(packingAware, qtyPacks);
	}
}
