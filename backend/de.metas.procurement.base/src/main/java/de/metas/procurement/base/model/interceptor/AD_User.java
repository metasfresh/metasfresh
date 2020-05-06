package de.metas.procurement.base.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.model.I_AD_User;

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
	public static final transient AD_User instance = new AD_User();

	private AD_User()
	{
	}

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
	public void pushToWebUI(final I_AD_User contact)
	{
		// FIXME: as it is will push ANY bpartner, even if it is relevant or not from PMM's point of view
		// good part: will solve the case when u have a pmm bpartner with one 1 user and u just un-ticket the IsMFProcurementUser flag for it
		// bad part: a lot of not relevant BPartners will be sent to procurement webui
//		Services.get(IWebuiPush.class).pushBPartnerForContact(contact);
	}

}
