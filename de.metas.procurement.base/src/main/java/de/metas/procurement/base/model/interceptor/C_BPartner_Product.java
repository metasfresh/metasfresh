package de.metas.procurement.base.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import org.compiere.model.I_C_BPartner_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.util.Services;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_C_BPartner_Product.class)
public class C_BPartner_Product
{
	public static final transient C_BPartner_Product instance = new C_BPartner_Product();

	private C_BPartner_Product()
	{
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_BPartner_Product.COLUMNNAME_ProductName, I_C_BPartner_Product.COLUMNNAME_IsActive })
	public void updatePMM_Product(final I_C_BPartner_Product bpartnerProduct)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerProduct.getC_BPartner_ID());
		Services.get(IPMMProductBL.class).updateByBPartner(bpartnerId);
	}
}
