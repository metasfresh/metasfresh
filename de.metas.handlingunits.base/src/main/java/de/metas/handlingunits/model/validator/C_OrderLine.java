/**
 *
 */
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


import java.util.Properties;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MOrderLinePOCopyRecordSupport;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.adempiere.service.impl.OrderLineBL;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderBL;

/**
 * @author cg
 *
 */
@Validator(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final Predicate<org.compiere.model.I_C_OrderLine> orderLineCopyRecordSkipPredicate = new Predicate<org.compiere.model.I_C_OrderLine>()
	{

		@Override
		public boolean evaluate(final org.compiere.model.I_C_OrderLine orderLine)
		{
			final I_C_OrderLine huOrderLine = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);

			// Skip copying packing material order lines
			if (huOrderLine.isPackagingMaterial())
			{
				return true;
			}

			// Default: allow copying
			return false; // don't skip
		}
	};

	@Init
	public void setupCopyRecordSupport()
	{
		MOrderLinePOCopyRecordSupport.addSkipPredicate(orderLineCopyRecordSkipPredicate);
	}

	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.handlingunits.callout.C_OrderLine());
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_C_BPartner_ID
					, I_C_OrderLine.COLUMNNAME_M_Product_ID
					, I_C_OrderLine.COLUMNNAME_QtyEntered
					, de.metas.handlingunits.model.I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
			})
	public void add_M_HU_PI_Item_Product(final I_C_OrderLine olPO)
	{

		// avoid price recalculation in some specific cases (e.g. on reactivation)
		final Boolean doNotRecalculatePrices = InterfaceWrapperHelper.getDynAttribute(olPO, OrderLineBL.DYNATTR_DoNotRecalculatePrices);

		if (doNotRecalculatePrices != null && doNotRecalculatePrices)
		{
			return;
		}

		// We call with null because we get the changed values from the object.
		Services.get(IHUOrderBL.class).updateOrderLine(olPO, null);

		final IHUDocumentHandler handler = Services.get(IHUDocumentHandlerFactory.class).createHandler(org.compiere.model.I_C_OrderLine.Table_Name);
		if (null != handler)
		{
			final de.metas.handlingunits.model.I_C_OrderLine olEx = InterfaceWrapperHelper.create(olPO, de.metas.handlingunits.model.I_C_OrderLine.class);
			handler.applyChangesFor(olEx);

			// First, we update the quantity packs (aka QtyTU)
			updateQtyPacks(olEx);

			// Finally, update prices
			final Properties ctx = InterfaceWrapperHelper.getCtx(olEx);
			final String trxName = InterfaceWrapperHelper.getTrxName(olEx);
			Services.get(IOrderLineBL.class).setPricesIfNotIgnored(ctx, olEx,
					InterfaceWrapperHelper.isNew(olEx), // usePriceUOM
					trxName);
		}
	}

	private void updateQtyPacks(final de.metas.handlingunits.model.I_C_OrderLine orderLine)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		Services.get(IHUPackingAwareBL.class).setQtyPacks(packingAware);
	}
}
