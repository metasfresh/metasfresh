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

import de.metas.document.location.IDocumentLocationBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.SpringContextHolder;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void onProductChanged(final I_C_OrderLine orderLine)
	{
		resetManualFlags(orderLine);
		orderLineBL.updateProductDescriptionFromProductBOMIfConfigured(orderLine);
	}

	private void resetManualFlags(final I_C_OrderLine orderLineRecord)
	{
		orderLineRecord.setIsManualDiscount(false);
		orderLineRecord.setIsManualPrice(false);
		orderLineRecord.setIsManualPaymentTerm(false);

		orderLineBL.updatePrices(orderLineRecord); // see task 06727
	}

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_Discount }, skipIfCopying = true, skipIfIndirectlyCalled = true)
	public void SetIsManualDiscount(final I_C_OrderLine orderLine)
	{
		orderLine.setIsManualDiscount(true);
	}

	/**
	 * Sets {@code C_OrderLine.IsManualPrice='Y'}, but only if the user "manually" edited the price.
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_PriceEntered }, skipIfCopying = true, skipIfIndirectlyCalled = true)
	public void setIsManualPriceFlag(final I_C_OrderLine orderLine)
	{
		orderLine.setIsManualPrice(true);
	}

	@CalloutMethod(columnNames = {
			I_C_OrderLine.COLUMNNAME_C_BPartner_ID,
			I_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID,
			I_C_OrderLine.COLUMNNAME_AD_User_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_C_OrderLine orderLine)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine));
	}

	@CalloutMethod(columnNames = {
			I_C_OrderLine.COLUMNNAME_C_BPartner_ID,
			I_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID},
			skipIfCopying = true)
	public void updateBPartnerAddressForceUpdateCapturedLocation(final I_C_OrderLine orderLine)
	{
		documentLocationBL.updateCapturedLocation(OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine));
	}

	@CalloutMethod(columnNames = {I_C_OrderLine.COLUMNNAME_C_BPartner_ID},
			skipIfCopying = true)
	public void updateBPartnerLocation(final I_C_OrderLine orderLine)
	{
		orderLineBL.setBPLocation(orderLine);
	}
}
