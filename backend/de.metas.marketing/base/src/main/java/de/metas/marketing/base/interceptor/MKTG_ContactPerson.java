package de.metas.marketing.base.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.i18n.Language;
import de.metas.marketing.base.UserService;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.util.Services;
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

@Component
@Interceptor(I_MKTG_ContactPerson.class)
public class MKTG_ContactPerson
{
	private final UserService userService;

	private final ContactPersonRepository contactPersonRepo;

	private MKTG_ContactPerson(@NonNull final UserService userService, @NonNull final ContactPersonRepository contactPersonRepo)
	{
		this.userService = userService;
		this.contactPersonRepo = contactPersonRepo;
	}

	/**
	 * When MKTG_ContactPerson.AD_User_ID changes, then update MKTG_Campaign_ContactPerson.AD_User_ID accordingly
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_MKTG_ContactPerson.COLUMNNAME_AD_User_ID)
	public void updateCampaignContactPersonAdUserId(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// note AD_User_ID=0 needs special treatment
		final Integer newAdUserID = contactPerson.getAD_User_ID() <= 0 ? null : contactPerson.getAD_User_ID();

		final IQueryUpdater<I_MKTG_Campaign_ContactPerson> updater = queryBL
				.createCompositeQueryUpdater(I_MKTG_Campaign_ContactPerson.class)
				.addSetColumnValue(I_MKTG_Campaign_ContactPerson.COLUMNNAME_AD_User_ID, newAdUserID);

		createContactPersonQuery(contactPerson)
				.update(updater);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCampaignContactPersonAdUserId(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		createContactPersonQuery(contactPerson)
				.delete();
	}

	private IQuery<I_MKTG_Campaign_ContactPerson> createContactPersonQuery(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPerson.getMKTG_ContactPerson_ID())
				.create();
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_MKTG_ContactPerson.COLUMNNAME_EMail, I_MKTG_ContactPerson.COLUMNNAME_AD_Language })
	public void updateUserFromContactPerson(final I_MKTG_ContactPerson contactPersonRecord)
	{
		final I_MKTG_ContactPerson oldContactPerson = InterfaceWrapperHelper.createOld(contactPersonRecord, I_MKTG_ContactPerson.class);

		final String oldContactPersonMail = oldContactPerson.getEMail();
		final Language oldContactPersonLanguage = Language.asLanguage(oldContactPerson.getAD_Language());

		final ContactPerson contactPerson = contactPersonRepo.asContactPerson(contactPersonRecord);

		userService.updateUserFromContactPersonIfFeasible(
				contactPerson,
				oldContactPersonMail,
				oldContactPersonLanguage);
	}
}
