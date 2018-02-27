package de.metas.handlingunits.ddorder.spi.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_ForecastLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/**
 * A handler for the <code>DD_OrderLine</code> table.
 *
 *
 */
public class ForecastLineHUDocumentHandler implements IHUDocumentHandler
{
	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(final Object document, final I_M_Product product)
	{
		if (product == null || product.getM_Product_ID() <= 0)
		{
			return null;
		}

		final I_M_ForecastLine forecastLine = getForecastLine(document);
		final I_M_HU_PI_Item_Product piip;

		if (InterfaceWrapperHelper.isNew(forecastLine)
				&& forecastLine.getM_HU_PI_Item_Product_ID() > 0)
		{
			piip = forecastLine.getM_HU_PI_Item_Product();
		}
		else
		{
			final I_M_Forecast forecast = forecastLine.getM_Forecast();
			final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;

			piip = Services.get(IHUPIItemProductDAO.class).retrieveMaterialItemProduct(product, forecast.getC_BPartner(), forecast.getDatePromised(), huUnitType,
					false);
		}

		return piip;
	}

	@Override
	public void applyChangesFor(final Object document)
	{
		final I_M_ForecastLine forecastLine = getForecastLine(document);
		final I_M_Product product = forecastLine.getM_Product();
		final I_M_HU_PI_Item_Product piip = getM_HU_PI_ItemProductFor(forecastLine, product);
		forecastLine.setM_HU_PI_Item_Product(piip);
	}

	private I_M_ForecastLine getForecastLine(final Object document)
	{
		Check.assumeInstanceOf(document, I_M_ForecastLine.class, "document");

		final I_M_ForecastLine forecastLine = InterfaceWrapperHelper.create(document, I_M_ForecastLine.class);
		return forecastLine;
	}
}
