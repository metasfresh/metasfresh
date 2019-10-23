package de.metas.contracts.commission.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

@Interceptor(I_C_BPartner.class)
@Component
public class C_BPartner
{
	/**
	 * Makes sure that we don't run into trouble because a user pasted a code with unwanted whitespaces, e.g. from excel.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, //
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_SalesPartnerCode)
	public void trimSalesPartnerCode(@NonNull final I_C_BPartner bpartner)
	{
		if (bpartner.getSalesPartnerCode() == null)
		{
			return;
		}
		bpartner.setSalesPartnerCode(bpartner.getSalesPartnerCode().trim());
	}
}
