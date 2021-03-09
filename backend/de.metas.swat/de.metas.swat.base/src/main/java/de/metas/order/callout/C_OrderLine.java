package de.metas.order.callout;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.util.Services;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void onProductChanged(final I_C_OrderLine orderLine)
	{
		resetManualFlags(orderLine);
		Services.get(IOrderLineBL.class).updateProductDescriptionFromProductBOMIfConfigured(orderLine);
	}

	private void resetManualFlags(final I_C_OrderLine orderLineRecord)
	{
		orderLineRecord.setIsManualDiscount(false);
		orderLineRecord.setIsManualPrice(false);
		orderLineRecord.setIsManualPaymentTerm(false);

		Services.get(IOrderLineBL.class).updatePrices(orderLineRecord); // see task 06727
	}

	/**
	 * Sets {@code C_OrderLine.IsManualPrice='Y'}, but only if the user "manually" edited the price.
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_PriceEntered }, skipIfCopying = true, skipIfIndirectlyCalled = true)
	public void setIsManualPriceFlag(final I_C_OrderLine orderLine)
	{
		orderLine.setIsManualPrice(true);
	}
}
