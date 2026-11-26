package de.metas.material.interceptor;

import de.metas.adempiere.model.I_M_Product;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Shared fixture for {@link M_ForecastEventCreatorTest} and {@link M_ForecastPostMaterialEventEventCreatorTest};
 * both exercise {@link M_ForecastEventCreator} and need an {@code M_ForecastLine} with its own warehouse and
 * product master-data.
 */
final class ForecastLineTestFixture
{
	private ForecastLineTestFixture()
	{
	}

	/**
	 * Creates+saves a new warehouse and product, then builds (but does not save) a forecast line linked to
	 * {@code forecastRecord} using them. Callers may set further fields (e.g. C_BPartner_ID) before saving.
	 */
	static I_M_ForecastLine newForecastLine(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final BigDecimal qty)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);
		final I_M_Product product = newInstance(I_M_Product.class);
		save(product);

		final I_M_ForecastLine forecastLineRecord = newInstance(I_M_ForecastLine.class);
		forecastLineRecord.setM_Forecast(forecastRecord);
		forecastLineRecord.setDatePromised(TimeUtil.asTimestamp(SystemTime.asInstant()));
		forecastLineRecord.setQty(qty);
		forecastLineRecord.setM_Product_ID(product.getM_Product_ID());
		forecastLineRecord.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		return forecastLineRecord;
	}
}
