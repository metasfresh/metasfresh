package de.metas.procurement.base.model.interceptor;

import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_AD_User;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

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

@Interceptor(I_AD_User.class)
public class AD_User
{
	private final IWebuiPush webuiPush = Services.get(IWebuiPush.class);

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE
	}, ifColumnsChanged = {
			I_AD_User.COLUMNNAME_Name,
			I_AD_User.COLUMNNAME_EMail,
			I_AD_User.COLUMNNAME_IsMFProcurementUser,
			I_AD_User.COLUMNNAME_ProcurementPassword,
			I_AD_User.COLUMNNAME_IsActive }
			//
			, afterCommit = true)
	public void pushToWebUI(final I_AD_User contactRecord)
	{
		if (contactRecord.isIsMFProcurementUser()
				|| InterfaceWrapperHelper.isValueChanged(contactRecord, I_AD_User.COLUMNNAME_IsMFProcurementUser))
		{
			webuiPush.pushBPartnerForContact(contactRecord);
		}
	}

}
