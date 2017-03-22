package de.metas.procurement.base.model.interceptor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
@Callout(I_C_Flatrate_DataEntry.class)
@Interceptor(I_C_Flatrate_DataEntry.class)
public class C_Flatrate_DataEntry
{
	public static final C_Flatrate_DataEntry instance = new C_Flatrate_DataEntry();

	private C_Flatrate_DataEntry()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = {
			I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Planned,
			I_C_Flatrate_DataEntry.COLUMNNAME_FlatrateAmtPerUOM,
			I_C_Flatrate_DataEntry.COLUMNNAME_C_Currency_ID })
	public void updateFlatrateAmt(final I_C_Flatrate_DataEntry dataEntry)
	{
		updateFlatrateAmt0(dataEntry);
	}

	@CalloutMethod(columnNames = {
			I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Planned,
			I_C_Flatrate_DataEntry.COLUMNNAME_FlatrateAmtPerUOM,
			I_C_Flatrate_DataEntry.COLUMNNAME_C_Currency_ID })
	public void updateFlatrateAmt(final I_C_Flatrate_DataEntry dataEntry, final ICalloutField unused)
	{
		updateFlatrateAmt0(dataEntry);
	}

	private void updateFlatrateAmt0(final I_C_Flatrate_DataEntry dataEntry)
	{
		// gh #770: null values in one of the two factors shall result in the product being null and not 0.
		if (InterfaceWrapperHelper.isNull(dataEntry, I_C_Flatrate_DataEntry.COLUMNNAME_FlatrateAmtPerUOM)
				|| InterfaceWrapperHelper.isNull(dataEntry, I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Planned))
		{
			dataEntry.setFlatrateAmt(null);
			return;
		}

		final BigDecimal product = dataEntry.getQty_Planned().multiply(dataEntry.getFlatrateAmtPerUOM());
		final BigDecimal flatrateAmt;
		if (dataEntry.getC_Currency_ID() > 0)
		{
			// round to currency precision
			flatrateAmt = product.setScale(dataEntry.getC_Currency().getStdPrecision(), RoundingMode.HALF_UP);
		}
		else
		{
			flatrateAmt = product;
		}
		dataEntry.setFlatrateAmt(flatrateAmt);
	}
}
