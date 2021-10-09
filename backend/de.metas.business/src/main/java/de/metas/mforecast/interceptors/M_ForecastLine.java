package de.metas.mforecast.interceptors;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.WarehouseInvalidForOrgException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.organization.IOrgDAO;
import de.metas.util.Services;

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
		final WarehouseId warehouseId = WarehouseId.ofRepoId(forecastLine.getM_Warehouse_ID());
		final I_M_Warehouse wh = Services.get(IWarehouseDAO.class).getById(warehouseId);
		final int adOrgId = forecastLine.getAD_Org_ID();
		if (wh.getAD_Org_ID() != adOrgId)
		{
			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(adOrgId);
			throw new WarehouseInvalidForOrgException(wh.getName(), orgName);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_M_ForecastLine.COLUMNNAME_M_Forecast_ID})
	public void updateFieldsFromHeader(final I_M_ForecastLine forecastLine)
	{
		final I_M_Forecast forecast = forecastLine.getM_Forecast();
		if (forecast != null)
		{
			forecastLine.setC_BPartner_ID(forecast.getC_BPartner_ID());
			forecastLine.setM_Warehouse_ID(forecast.getM_Warehouse_ID());
			forecastLine.setC_Period(forecast.getC_Period());
			forecastLine.setDatePromised(forecast.getDatePromised());
		}
	}
}
