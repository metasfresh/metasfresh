/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.commission.commissioninstance.interceptor;

import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	/**
	 *  Reset {@link I_C_OrderLine.COLUMNNAME_Base_Commission_Points_Per_Price_UOM} and {@link I_C_OrderLine.COLUMNNAME_Traded_Commission_Percent}
	 *  in case the price computed by the price engine was overridden by the user in UI, so the commission points will be calculated based on the
	 *  {@link I_C_OrderLine.COLUMNNAME_PriceActual}.
	 *
	 * @param icRecord Invoice Candidate record
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
				 ifColumnsChanged = {I_C_OrderLine.COLUMNNAME_PriceEntered, I_C_OrderLine.COLUMNNAME_PriceStd})
	public void resetComputedCommissionPoints(@NonNull final I_C_OrderLine icRecord)
	{
		if ( !icRecord.getPriceStd().equals( icRecord.getPriceEntered() ) )
		{
			icRecord.setBase_Commission_Points_Per_Price_UOM(null);
			icRecord.setTraded_Commission_Percent(null);
		}
	}
}
