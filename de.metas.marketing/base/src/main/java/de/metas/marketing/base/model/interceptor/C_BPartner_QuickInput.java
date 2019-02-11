package de.metas.marketing.base.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.User;
import org.adempiere.user.UserRepository;
import org.adempiere.user.api.IUserDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.util.Services;

/*
 * #%L
 * marketing-base
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

@Interceptor(I_C_BPartner_QuickInput.class)
@Component()
public class C_BPartner_QuickInput
{
	private final UserRepository userRepository = Adempiere.getBean(UserRepository.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_BPartner_QuickInput.COLUMNNAME_IsNewsletter })
	public void setNewsletter(final I_C_BPartner_QuickInput quickInput)
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);

		if(InterfaceWrapperHelper.isNew(quickInput))
		{
			// nothing to do yet
			return;
		}


		final I_AD_User userRecord = userDAO.getByIdInTrx(quickInput.getAD_User_ID(), I_AD_User.class);

		if(userRecord == null)
		{
			// nothing to do
			return;
		}

		final User user = userRepository.ofRecord(userRecord);
		userRepository.save(user);
	}
}
