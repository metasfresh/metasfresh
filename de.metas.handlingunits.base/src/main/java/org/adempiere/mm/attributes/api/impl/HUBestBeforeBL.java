package org.adempiere.mm.attributes.api.impl;

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


import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.IHUBestBeforeBL;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorage;

public class HUBestBeforeBL implements IHUBestBeforeBL
{
	@Override
	public I_M_Attribute getBestBeforeDateAttribute()
	{
		final I_M_Attribute attr_BestBeforeDate = Services.get(IAttributeDAO.class).retrieveAttributeByValue(Env.getCtx(), Constants.ATTR_BestBeforeDate, I_M_Attribute.class);
		return attr_BestBeforeDate;
	}
	
	@Override
	public void setBestBeforeDate(final IHUContext huContext, final Collection<I_M_HU> vhus, final Date dateReceipt)
	{
		// Do nothing if there are no VHUs
		if (vhus.isEmpty())
		{
			return;
		}

		//
		// Get the Best-Before attribute
		final I_M_Attribute attr_BestBefore = getBestBeforeDateAttribute();

		//
		// Iterate all VHUs, calculate and set their Best-Before date
		Services.get(IHUTrxBL.class).createHUContextProcessorExecutor(huContext)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						for (final I_M_HU vhu : vhus)
						{
							final IAttributeStorage vhuAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(vhu);
							if (!vhuAttributes.hasAttribute(attr_BestBefore))
							{
								continue;
							}

							final Date bestBeforeDate = calculateBestBeforeDate(huContext, vhu, dateReceipt);
							if (bestBeforeDate == null)
							{
								// Best-Before date is not available
								continue;
							}

							vhuAttributes.setValue(attr_BestBefore, bestBeforeDate);
						}

						return NULL_RESULT;
					}
				});
	}

	private Date calculateBestBeforeDate(final IHUContext huContext, final I_M_HU vhu, final Date dateReceipt)
	{
		Check.assumeNotNull(dateReceipt, "dateReceipt not null");

		//
		// Get HU's vendor bpartner
		final int vendorBPartnerId = vhu.getC_BPartner_ID();
		if (vendorBPartnerId <= 0)
		{
			// no vendor found
			// => we cannot calculate best before date
			return null;
		}

		//
		// Get HU's single product
		final IHUStorage vhuStorage = huContext.getHUStorageFactory().getStorage(vhu);
		final I_M_Product product = vhuStorage.getSingleProductOrNull();
		if (product == null)
		{
			// storage is empty or there are more products
			// => we cannot calculate best before date
			return null;
		}
		final int productId = product.getM_Product_ID();

		final Properties ctx = huContext.getCtx();
		return Services.get(IAttributesBL.class).calculateBestBeforeDate(ctx, productId, vendorBPartnerId, dateReceipt);
	}
}
