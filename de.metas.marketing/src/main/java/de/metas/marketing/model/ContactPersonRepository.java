package de.metas.marketing.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.springframework.stereotype.Repository;

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

@Repository
public class ContactPersonRepository
{

	public ContactPerson store(@NonNull final ContactPerson contactPerson)
	{
		final I_MKTG_ContactPerson contactPersonRecord;
		if (contactPerson.getRepoId() > 0)
		{
			contactPersonRecord = load(contactPerson.getRepoId(), I_MKTG_ContactPerson.class);
		}
		else
		{
			contactPersonRecord = newInstance(I_MKTG_ContactPerson.class);
		}

		contactPersonRecord.setAD_User_ID(contactPerson.getAdUserId());
		contactPersonRecord.setC_BPartner_ID(contactPerson.getCBpartnerId());
		contactPersonRecord.setName(contactPerson.getName());
		contactPersonRecord.setEMail(contactPerson.getAddress().getValue());
		save(contactPersonRecord);

		return contactPerson.toBuilder()
				.repoId(contactPersonRecord.getMKTG_ContactPerson_ID())
				.build();
	}

}
