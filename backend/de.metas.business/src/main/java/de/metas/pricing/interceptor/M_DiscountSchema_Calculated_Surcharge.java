package de.metas.pricing.interceptor;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.TrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Interceptor(I_M_DiscountSchema_Calculated_Surcharge.class)
@Component
public class M_DiscountSchema_Calculated_Surcharge
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_DiscountSchema_Calculated_Surcharge.COLUMNNAME_Surcharge_Calc_SQL })
	public void validateSQL(@NonNull final I_M_DiscountSchema_Calculated_Surcharge record)
	{
		// needs to be its own transaction, because validate_surcharge_calculation_SQL sets transaction to read only, and it can't be changed again before save then
		trxManager.runInNewTrx(() -> {
			DB.executeFunctionCallEx(Trx.TRXNAME_ThreadInherited, "SELECT validate_surcharge_calculation_SQL(?,?,?)", new Object[]{record.getSurcharge_Calc_SQL(), 0, 0});
		});
	}
}
