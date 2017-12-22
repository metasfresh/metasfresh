package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;

import de.metas.document.engine.IDocument;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.mforecast.IForecastDAO;
import de.metas.product.IProductBL;

public class ForecastMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	private static final String MSG_ForcastNoBalanceDemand = "Forecast can never balance demand";

	public ForecastMRPSupplyProducer()
	{
		super();

		addSourceColumnNames(I_M_Forecast.Table_Name, new String[] {
				I_M_Forecast.COLUMNNAME_Processing
				});
		addSourceColumnNames(I_M_ForecastLine.Table_Name, new String[] {
				"AD_Org_ID",
				I_M_ForecastLine.COLUMNNAME_DatePromised,
				I_M_ForecastLine.COLUMNNAME_M_Warehouse_ID,
				I_M_ForecastLine.COLUMNNAME_M_Product_ID,
				I_M_ForecastLine.COLUMNNAME_M_AttributeSetInstance_ID,
				I_M_ForecastLine.COLUMNNAME_Qty
		});
	}


	@Override
	public Class<?> getDocumentClass()
	{
		return I_M_Forecast.class;
	}


	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, IMutable<String> notAppliesReason)
	{
		// forecast can never balance demand
		notAppliesReason.setValue(MSG_ForcastNoBalanceDemand);
		return false;
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		// nothing
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		// shall never been called
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		// nothing
	}

	@Override
	protected void onRecordChange(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();

		if (model instanceof I_M_Forecast && event.isChange())
		{
			final I_M_Forecast fl = (I_M_Forecast)model;
			M_Forecast(fl);
			final List <I_M_ForecastLine> flines = Services.get(IForecastDAO.class).retrieveLines(fl);
			for (I_M_ForecastLine f : flines)
			{
				M_ForecastLine(f);
			}
		}
		//
		else if (model instanceof I_M_ForecastLine && event.isChange())
		{
			final I_M_ForecastLine fl = (I_M_ForecastLine)model;
			M_ForecastLine(fl);
		}
	}

	/**
	 * Create MRP record based in Forecast
	 *
	 * @param MForecast Forecast
	 */
	private void M_Forecast(final I_M_Forecast forecast)
	{
		final List<I_PP_MRP> list = Services.get(IMRPDAO.class).retrieveMRPRecords(forecast);
		for (final I_PP_MRP mrp : list)
		{
			setM_Forecast(mrp, forecast);
		}
	}

	/**
	 * Create MRP record based in Forecast Line
	 *
	 * @param I_M_ForecastLine Forecast Line
	 */
	private void M_ForecastLine(final I_M_ForecastLine forecastLine)
	{
		final I_M_Product product = forecastLine.getM_Product();
		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(product);

		final I_M_Forecast forecast = forecastLine.getM_Forecast();
		I_PP_MRP mrp = Services.get(IMRPDAO.class).retrieveMRPRecord(forecastLine);
		if (mrp == null)
		{
			mrp = mrpBL.createMRP(forecastLine);
			mrp.setM_ForecastLine(forecastLine);
		}

		final BigDecimal qtyTarget = forecastLine.getQty();
		final BigDecimal qty = qtyTarget;

		setM_Forecast(mrp, forecast);
		mrp.setName("MRP");
		mrp.setAD_Org_ID(forecastLine.getAD_Org_ID());
		mrp.setDatePromised(forecastLine.getDatePromised());
		mrp.setDateStartSchedule(forecastLine.getDatePromised());
		mrp.setDateFinishSchedule(forecastLine.getDatePromised());
		mrp.setDateOrdered(forecastLine.getDatePromised());
		mrp.setM_Warehouse_ID(forecastLine.getM_Warehouse_ID());
		mrp.setM_Product(product);
		mrp.setM_AttributeSetInstance_ID(forecastLine.getM_AttributeSetInstance_ID());
		mrpBL.setQty(mrp, qtyTarget, qty, uom);
		mrp.setDocStatus(IDocument.STATUS_InProgress);
		if(forecastLine.getM_Forecast().isProcessing())
		{
			mrp.setC_BPartner_ID(forecastLine.getC_BPartner_ID());
		}
		InterfaceWrapperHelper.save(mrp);
	}

	private final void setM_Forecast(final I_PP_MRP mrp, final I_M_Forecast f)
	{
		mrpBL.updateMRPFromContext(mrp);

		mrp.setOrderType(X_PP_MRP.ORDERTYPE_Forecast);
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Demand);
		mrp.setM_Forecast_ID(f.getM_Forecast_ID());
		mrp.setDescription(f.getDescription());
	}


}
