package de.metas.handlingunits.model.validator;

import java.math.BigDecimal;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ForecastLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.model.I_M_ForecastLine;

@Validator(I_M_ForecastLine.class)
@Callout(I_M_ForecastLine.class)
public class M_ForecastLine
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_M_ForecastLine.COLUMNNAME_C_BPartner_ID,
			I_M_ForecastLine.COLUMNNAME_M_Product_ID,
			I_M_ForecastLine.COLUMNNAME_Qty,
			I_M_ForecastLine.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	public void add_M_HU_PI_Item_Product(final I_M_ForecastLine forecastLine)
	{
		final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
		final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(I_M_ForecastLine.Table_Name);
		if (null != handler)
		{
			handler.applyChangesFor(forecastLine);
			updateQtyPacks(forecastLine);
			updateQtyCalculated(forecastLine);
		}
	}

	@CalloutMethod(columnNames = { I_M_ForecastLine.COLUMNNAME_Qty })
	public void updateQtyTU(final I_M_ForecastLine forecastLine)
	{
		updateQtyPacks(forecastLine);
		updateQtyCalculated(forecastLine);
	}

	@CalloutMethod(columnNames = { I_M_ForecastLine.COLUMNNAME_QtyEnteredTU, I_M_ForecastLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void updateQtyCU(final I_M_ForecastLine forecastLine)
	{
		final IHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);
		final Integer qtyPacks = packingAware.getQtyTU().intValue();
		Services.get(IHUPackingAwareBL.class).setQtyCUFromQtyTU(packingAware, qtyPacks);
	}


	private void updateQtyPacks(final I_M_ForecastLine forecastLine)
	{
		final IHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);
		Services.get(IHUPackingAwareBL.class).setQtyTU(packingAware);
	}

	private void updateQtyCalculated(final I_M_ForecastLine forecastLine)
	{
		final IHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);
		final BigDecimal qty = packingAware.getQty();
		packingAware.setQty(qty);
	}

}
