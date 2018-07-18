package de.metas.marketing.base.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.user.User;
import org.adempiere.user.UserId;
import org.adempiere.user.UserRepository;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.springframework.stereotype.Component;

import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;

/*
 * #%L
 * marketing-base
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

@Component("de.metas.marketing.base.model.interceptor.MKTG_ContactPerson")
@Callout(I_MKTG_ContactPerson.class)
@Interceptor(I_MKTG_ContactPerson.class)
public class MKTG_ContactPerson
{

	@Init
	public void registerCallout()
	{
		// this class serves as both model validator an callout
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_MKTG_ContactPerson.COLUMNNAME_EMail })
	public void onChangeEmail(final I_MKTG_ContactPerson contactPersonRecord, final ICalloutField field)
	{

		final ContactPersonRepository contactPersonRepo = Adempiere.getBean(ContactPersonRepository.class);
		final UserRepository userRepo = Adempiere.getBean(UserRepository.class);

		final ContactPerson contactPerson = contactPersonRepo.asContactPerson(contactPersonRecord);

		final UserId userId = contactPerson.getUserId();

		if(userId.getRepoId() <= 0)
		{
			// no user to update the email
			return;
		}
		final User user = userRepo.getById(userId);

		final String oldContactPersonMail = String.valueOf(field.getOldValue());
		final String newContactPersonMail = contactPerson.getEmailAddessStringOrNull();

		final boolean isFitForEmailUpdate = isFitForEmailUpdate(user, oldContactPersonMail);

		if (isFitForEmailUpdate)
		{
			userRepo.updateEmail(user, newContactPersonMail);
		}
	}

	private boolean isFitForEmailUpdate(final User user, final String oldContactPersonMail)
	{
		final String userEmailAddress = user.getEmailAddress();

		if (Check.isEmpty(userEmailAddress))
		{
			return true;
		}

		if (userEmailAddress.equals(oldContactPersonMail))
		{
			return true;
		}

		return false;
	}

}
