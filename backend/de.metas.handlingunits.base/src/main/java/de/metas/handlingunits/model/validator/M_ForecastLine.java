package de.metas.handlingunits.model.validator;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ForecastLineHUPackingAware;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.model.I_M_ForecastLine;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import java.math.BigDecimal;

@Interceptor(I_M_ForecastLine.class)
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
