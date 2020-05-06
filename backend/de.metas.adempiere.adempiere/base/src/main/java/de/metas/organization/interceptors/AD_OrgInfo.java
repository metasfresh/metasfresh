package de.metas.organization.interceptors;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.organization.OrgInfo;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.organization.impl.OrgDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Interceptor(I_AD_OrgInfo.class)
@Component
public class AD_OrgInfo
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_OrgInfo orgInfoRecord)
	{
		// just to make sure everything is OK
		OrgDAO.toOrgInfo(orgInfoRecord);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_OrgInfo.COLUMNNAME_StoreCreditCardData)
	public void afterStoreCreditCardDataChanged(final I_AD_OrgInfo orgInfoRecord)
	{
		final OrgInfo orgInfo = OrgDAO.toOrgInfo(orgInfoRecord);
		final StoreCreditCardNumberMode ccStoreMode = orgInfo.getStoreCreditCardNumberMode();

		Env.setContext(Env.getCtx(), Env.CTXNAME_StoreCreditCardData, ccStoreMode.getCode());
	}
}
