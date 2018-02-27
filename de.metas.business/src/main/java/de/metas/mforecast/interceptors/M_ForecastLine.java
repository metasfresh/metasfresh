package de.metas.mforecast.interceptors;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.WarehouseInvalidForOrgException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.MOrg;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.business
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

@Component
@Interceptor(I_M_ForecastLine.class)
public class M_ForecastLine
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_M_ForecastLine.COLUMNNAME_AD_Org_ID, I_M_ForecastLine.COLUMNNAME_M_Warehouse_ID })
	public void beforeSave(final I_M_ForecastLine forecastLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(forecastLine);
		final MWarehouse wh = MWarehouse.get(ctx, forecastLine.getM_Warehouse_ID());
		final int adOrgId = forecastLine.getAD_Org_ID();
		if (wh.getAD_Org_ID() != adOrgId)
		{
			throw new WarehouseInvalidForOrgException(wh.getName(), MOrg.get(ctx, adOrgId).getName());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_M_ForecastLine.COLUMNNAME_M_Forecast_ID})
	public void updateFieldsFromHeader(final I_M_ForecastLine forecastLine)
	{
		final I_M_Forecast forecast = forecastLine.getM_Forecast();
		if (forecast != null)
		{
			forecastLine.setC_BPartner(forecast.getC_BPartner());
			forecastLine.setM_Warehouse(forecast.getM_Warehouse());
			forecastLine.setC_Period(forecast.getC_Period());
			forecastLine.setDatePromised(forecast.getDatePromised());
		}
	}
}
