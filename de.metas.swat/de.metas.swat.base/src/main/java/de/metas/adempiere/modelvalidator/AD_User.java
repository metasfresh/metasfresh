package de.metas.adempiere.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;

/**
 * This model validator
 * <ul>
 * <li>sets AD_User.Name from AD_User.FirstName and AD_User.LastName</li>
 * <li>Checks if the password contains no spaces and has at least a length of <code>org.compiere.util.Login.MinPasswordLength</code> (AD_AsyConfig) characters</li>
 * </ul>
 * 
 */
@Validator(I_AD_User.class)
@Callout(value = I_AD_User.class)
public class AD_User
{

	private static final String MSG_INCORRECT_PASSWORD = "org.compiere.util.Login.IncorrectPassword";
	private static final String SYS_MIN_PASSWORD_LENGTH = "org.compiere.util.Login.MinPasswordLength";
	
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	// 04270
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_AD_User.COLUMNNAME_Password, I_AD_User.COLUMNNAME_IsSystemUser })
	public void checkPassword(final I_AD_User user)
	{
		Check.assumeNotNull(user, "Param 'user' not null");
		
		if (!user.isSystemUser())
		{
			return;
		}
		
		final Properties ctx = InterfaceWrapperHelper.getCtx(user);
		final String errorMessage = Services.get(IMsgBL.class).getMsg(ctx, MSG_INCORRECT_PASSWORD);
		final int minPasswordLength = getMinPasswordLength();
		
		final String password = user.getPassword();

		Check.assumeNotNull(password, errorMessage, minPasswordLength);
		Check.assume(!password.contains(" "), errorMessage, minPasswordLength);
		Check.assume(password.length() >= minPasswordLength, errorMessage, minPasswordLength);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname })
	@CalloutMethod(columnNames = {I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname})
	public void setName(final I_AD_User user)
	{
		final String contactName = Services.get(IUserBL.class).buildContactName(user.getFirstname(), user.getLastname());
		user.setName(contactName);
	}

	private int getMinPasswordLength()
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYS_MIN_PASSWORD_LENGTH, 8);
	}
}
