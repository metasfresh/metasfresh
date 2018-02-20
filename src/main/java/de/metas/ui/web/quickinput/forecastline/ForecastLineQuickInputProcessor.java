package de.metas.ui.web.quickinput.forecastline;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.gui.search.HUPackingAwareCopy.ASICopyMode;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ForecastLineHUPackingAware;
import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ForecastLineQuickInputProcessor implements IQuickInputProcessor
{
	private final transient IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);

	@Override
	public DocumentId process(final QuickInput quickInput)
	{
		final I_M_Forecast forecast= quickInput.getRootDocumentAs(I_M_Forecast.class);
		final I_M_ForecastLine forecastLine = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class, forecast);
		forecastLine.setM_Forecast(forecast);
		updateForecastLine(forecastLine, quickInput);
		save(forecastLine);
		return DocumentId.of(forecastLine.getM_ForecastLine_ID());
	}

	private final void updateForecastLine(final I_M_ForecastLine forecastLine, final QuickInput fromQuickInput)
	{
		final I_M_Forecast forecast = fromQuickInput.getRootDocumentAs(I_M_Forecast.class);
		final IForecastLineQuickInput fromForecastLineQuickInput = fromQuickInput.getQuickInputDocumentAs(IForecastLineQuickInput.class);
		final IHUPackingAware quickInputPackingAware = createQuickInputPackingAware(forecast, fromForecastLineQuickInput);

		final IHUPackingAware orderLinePackingAware = ForecastLineHUPackingAware.of(forecastLine);

		huPackingAwareBL.prepareCopyFrom(quickInputPackingAware)
				.overridePartner(false)
				.asiCopyMode(ASICopyMode.CopyID) // because we just created the ASI
				.copyTo(orderLinePackingAware);
	}

	private IHUPackingAware createQuickInputPackingAware(
			@NonNull final I_M_Forecast forecast,
			@NonNull final IForecastLineQuickInput quickInput)
	{
		final PlainHUPackingAware huPackingAware = createAndInitHuPackingAware(forecast, quickInput);

		final BigDecimal quickInputQty = quickInput.getQty();
		if (quickInputQty == null || quickInputQty.signum() <= 0)
		{
			throw new AdempiereException("Qty shall be greather than zero");
		}

		huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(huPackingAware, quickInputQty);

		return validateNewHuPackingAware(huPackingAware);
	}

	private PlainHUPackingAware createAndInitHuPackingAware(
			@NonNull final I_M_Forecast forecast,
			@NonNull final IForecastLineQuickInput quickInput)
	{
		final PlainHUPackingAware huPackingAware = new PlainHUPackingAware();
		huPackingAware.setC_BPartner(forecast.getC_BPartner());
		huPackingAware.setDateOrdered(forecast.getDatePromised());
		huPackingAware.setInDispute(false);

		final ProductAndAttributes productAndAttributes = ProductLookupDescriptor.toProductAndAttributes(quickInput.getM_Product_ID());
		final I_M_Product product = load(productAndAttributes.getProductId(), I_M_Product.class);
		huPackingAware.setM_Product_ID(product.getM_Product_ID());
		huPackingAware.setC_UOM(product.getC_UOM());
		huPackingAware.setM_AttributeSetInstance_ID(createASI(productAndAttributes));

		final I_M_HU_PI_Item_Product piItemProduct = quickInput.getM_HU_PI_Item_Product();
		huPackingAware.setM_HU_PI_Item_Product(piItemProduct);

		return huPackingAware;
	}

	private PlainHUPackingAware validateNewHuPackingAware(@NonNull final PlainHUPackingAware huPackingAware)
	{
		if (huPackingAware.getQty() == null || huPackingAware.getQty().signum() <= 0)
		{
			throw new AdempiereException("Qty shall be greather than zero");
		}
		if (huPackingAware.getQtyTU() == null || huPackingAware.getQtyTU().signum() <= 0)
		{
			throw new AdempiereException("QtyTU shall be greather than zero");
		}
		return huPackingAware;
	}

	private static final int createASI(final ProductAndAttributes productAndAttributes)
	{
		final ImmutableAttributeSet attributes = productAndAttributes.getAttributes();
		if (attributes.isEmpty())
		{
			return -1;
		}

		final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_AttributeSetInstance asi = asiBL.createASIWithASFromProductAndInsertAttributeSet(
				productAndAttributes.getProductId(),
				attributes);

		return asi.getM_AttributeSetInstance_ID();
	}
}
