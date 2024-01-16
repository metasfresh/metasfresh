package de.metas.distribution.ddorder.hu_spis;

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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

/**
 * A handler for the <code>DD_OrderLine</code> table.
 *
 *
 */
public class DDOrderLineHUDocumentHandler implements IHUDocumentHandler
{
	private final static String SYSCONFIG_DD_OrderLine_Enforce_M_HU_PI_Item_Product = "de.metas.handlingunits.DD_OrderLine_Enforce_M_HU_PI_Item_Product";
	/**
	 * This method assumes that the given document is <code>DD_OrderLine</code> and returns
	 * <ul>
	 * <li><code>null</code> is the line has no product</li>
	 * <li>the line's current PIIP f the line is new and already has a PIIP</li>
	 * <li>the result of {@link IHUPIItemProductDAO#retrieveMaterialItemProduct(ProductId, BPartnerId, java.time.ZonedDateTime, String, boolean) (with type="transport unit")
	 * otherwise</li>
	 * </ul>
	 */
	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(final Object document, final ProductId productId)
	{
		if (productId == null)
		{
			// No product selected. Nothing to do.
			return null;
		}

		final de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine ddOrderLine = getDDOrderLine(document);
		final I_M_HU_PI_Item_Product piip;

		// the record is new
		// search for the right combination
		if (InterfaceWrapperHelper.isNew(ddOrderLine)
				&& ddOrderLine.getM_HU_PI_Item_Product_ID() > 0)
		{
			piip = ddOrderLine.getM_HU_PI_Item_Product();
		}
		else
		{
			final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
			final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

			piip = Services.get(IHUPIItemProductDAO.class).retrieveMaterialItemProduct(
					productId, 
					BPartnerId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID()), 
					TimeUtil.asZonedDateTime(ddOrder.getDateOrdered()), 
					huUnitType,
					false); // allowInfiniteCapacity = false
		}

		return piip;
	}

	/**
	 * Calls {@link #getM_HU_PI_ItemProductFor(Object, ProductId)} with the given <code>document</code> which is a <code>DD_OrderLine</code> and sets the PIIP to it. Does <b>not</b> save the dd order line.
	 */
	@Override
	public void applyChangesFor(final Object document)
	{

		final de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine ddOrderLine = getDDOrderLine(document);
		
		final boolean enforcePIIP = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DD_OrderLine_Enforce_M_HU_PI_Item_Product, false);
		
		if(!enforcePIIP)
		{
			return;
		}
		
		final ProductId productId = ProductId.ofRepoIdOrNull(ddOrderLine.getM_Product_ID());
		final I_M_HU_PI_Item_Product piip = getM_HU_PI_ItemProductFor(ddOrderLine, productId);
		ddOrderLine.setM_HU_PI_Item_Product(piip);
	}

	private de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine getDDOrderLine(final Object document)
	{
		Check.assumeInstanceOf(document, I_DD_OrderLine.class, "document");

		return InterfaceWrapperHelper.create(document, de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine.class);
	}
}
