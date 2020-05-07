package org.adempiere.bpartner.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.ModelValidator;
import org.compiere.util.CacheMgt;
import org.springframework.stereotype.Component;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Interceptor(I_C_BPartner_Stats.class)
@Component
public class C_BPartner_Stats
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {I_C_BPartner_Stats.COLUMNNAME_CreditLimitIndicator})
	public void refreshPartner(@NonNull final I_C_BPartner_Stats stats)
	{
		// column CreditLimitIndicator is a virtual column in C_BPartner and we need to force refresh it
		CacheMgt.get().reset(I_C_BPartner.Table_Name, stats.getC_BPartner_ID());
	}
}
