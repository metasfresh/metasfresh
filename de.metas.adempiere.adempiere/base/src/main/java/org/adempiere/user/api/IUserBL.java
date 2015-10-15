package org.adempiere.user.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.ISingletonService;

import de.metas.adempiere.model.I_AD_User;

public interface IUserBL extends ISingletonService
{
	String PARAM_ACTION = "action";
	String ACTION_ResetPassword = "ResetPassword";
	String PARAM_AccountPasswordResetCode = "AccountPasswordResetCode";

	void createResetPasswordByEMailRequest(I_AD_User user);

	void createResetPasswordByEMailRequest(Properties ctx, String userId);

	I_AD_User resetPassword(Properties ctx, int adUserId, String passwordResetCode, String newPassword);

	/**
	 * generates random password
	 * 
	 * @return
	 */
	String generatePassword();

	/**
	 * Checks if given user has a C_BPartner which is an employee.
	 * 
	 * @param user
	 * @return true if is employee
	 */
	boolean isEmployee(final org.compiere.model.I_AD_User user);
	
	String buildContactName(final String firstName, final String lastName);
}
