package de.metas.marketing.base.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import lombok.NonNull;

/*
 * #%L
 * de.metas.marketing
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

@Callout(I_MKTG_Campaign_ContactPerson.class)
@Interceptor(I_MKTG_Campaign_ContactPerson.class)
public class MKTG_Campaign_ContactPerson
{
	public static final MKTG_Campaign_ContactPerson INSTANCE = new MKTG_Campaign_ContactPerson();

	private MKTG_Campaign_ContactPerson()
	{
	}

	@CalloutMethod(columnNames = I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_ContactPerson_ID)
	@ModelChange(//
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_MKTG_Campaign_ContactPerson.COLUMNNAME_MKTG_ContactPerson_ID)
	public void updateAD_User_ID(@NonNull final I_MKTG_Campaign_ContactPerson campaignContactPerson)
	{
		updateAdUserId(campaignContactPerson);
	}

	private void updateAdUserId(@NonNull final I_MKTG_Campaign_ContactPerson campaignContactPerson)
	{
		if (campaignContactPerson.getMKTG_ContactPerson_ID() <= 0)
		{
			campaignContactPerson.setAD_User(null);
			return;
		}

		final int adUserId = campaignContactPerson.getMKTG_ContactPerson().getAD_User_ID();
		if (adUserId <= 0)
		{
			campaignContactPerson.setAD_User(null);
			return;
		}

		campaignContactPerson.setAD_User_ID(adUserId);
		return;
	}
}
