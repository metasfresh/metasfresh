package de.metas.bpartner.product.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Callout(I_C_BPartner_Product.class)
public class C_BPartner_Product
{
	@CalloutMethod(columnNames = I_C_BPartner_Product.COLUMNNAME_M_Product_ID)
	public void setOrgOnProductChange(final I_C_BPartner_Product bpartnerProduct)
	{
		if (bpartnerProduct.getM_Product_ID() <= 0)
		{
			// nothing to change
			return;
		}
		final I_M_Product productRecord = loadOutOfTrx(bpartnerProduct.getM_Product_ID(), I_M_Product.class);
		bpartnerProduct.setAD_Org_ID(productRecord.getAD_Org_ID());

		bpartnerProduct.setPicking_AgeTolerance_BeforeMonths(productRecord.getPicking_AgeTolerance_BeforeMonths());
		bpartnerProduct.setPicking_AgeTolerance_AfterMonths(productRecord.getPicking_AgeTolerance_AfterMonths());
	}
}
