package de.metas.marketing.base.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.Language;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.ContactPersonsEventBus;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.ContactPersonId;
import de.metas.marketing.base.model.ContactPersonRepository;
import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

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
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ContactPersonService contactPersonService;
	private final ContactPersonsEventBus contactPersonsEventBus;

	public MKTG_ContactPerson(
			@NonNull final ContactPersonService contactPersonService,
			@NonNull final ContactPersonsEventBus contactPersonsEventBus)
	{
		this.contactPersonService = contactPersonService;
		this.contactPersonsEventBus = contactPersonsEventBus;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeSave(@NonNull final I_MKTG_ContactPerson contactPersonRecord, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isChange() && InterfaceWrapperHelper.isValueChanged(contactPersonRecord, I_MKTG_ContactPerson.COLUMNNAME_AD_User_ID))
		{
			updateCampaignContactPersonAdUserId(contactPersonRecord);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_MKTG_ContactPerson contactPersonRecord, @NonNull final ModelChangeType changeType)
	{
		if (InterfaceWrapperHelper.isValueChanged(contactPersonRecord, I_MKTG_ContactPerson.COLUMNNAME_EMail, I_MKTG_ContactPerson.COLUMNNAME_AD_Language))
		{
			updateUserFromContactPerson(contactPersonRecord);
		}

		if (changeType.isChange())
		{
			trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail)
					.getPropertyAndProcessAfterCommit(
							ChangesCollector.class.getName(),
							() -> new ChangesCollector(contactPersonsEventBus),
							ChangesCollector::commit)
					.collectChangedContact(ContactPersonRepository.toContactPerson(contactPersonRecord));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		queryCampaignContactPersonAssignment(contactPersonRecord).delete();
	}

	private void updateCampaignContactPersonAdUserId(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		final UserId newAdUserId = UserId.ofRepoIdOrNullIfSystem(contactPersonRecord.getAD_User_ID());

		final IQueryUpdater<I_MKTG_Campaign_ContactPerson> updater = queryBL
				.createCompositeQueryUpdater(I_MKTG_Campaign_ContactPerson.class)
				.addSetColumnValue(I_MKTG_Campaign_ContactPerson.COLUMNNAME_AD_User_ID, newAdUserId);

		queryCampaignContactPersonAssignment(contactPersonRecord).update(updater);
	}

	private IQuery<I_MKTG_Campaign_ContactPerson> queryCampaignContactPersonAssignment(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPerson.getMKTG_ContactPerson_ID())
				.create();
	}

	private void updateUserFromContactPerson(final I_MKTG_ContactPerson contactPersonRecord)
	{
		final I_MKTG_ContactPerson oldContactPersonRecord = InterfaceWrapperHelper.createOld(contactPersonRecord, I_MKTG_ContactPerson.class);
		final String oldContactPersonMail = oldContactPersonRecord.getEMail();
		final Language oldContactPersonLanguage = Language.asLanguage(oldContactPersonRecord.getAD_Language());

		final ContactPerson contactPerson = ContactPersonRepository.toContactPerson(contactPersonRecord);

		contactPersonService.updateUserFromContactPersonIfFeasible(
				contactPerson,
				oldContactPersonMail,
				oldContactPersonLanguage);
	}

	//
	//
	//
	private static class ChangesCollector
	{
		private final ContactPersonsEventBus contactPersonsEventBus;

		private HashMap<ContactPersonId, ContactPerson> contacts = new HashMap<>();

		private ChangesCollector(@NonNull final ContactPersonsEventBus contactPersonsEventBus)
		{
			this.contactPersonsEventBus = contactPersonsEventBus;
		}

		public void collectChangedContact(final ContactPerson contact)
		{
			if (contacts == null)
			{
				throw new AdempiereException("collector already committed");
			}

			contacts.put(contact.getContactPersonId(), contact);
		}

		public void commit()
		{
			if (contacts == null)
			{
				return;
			}

			final ImmutableList<ContactPerson> contacts = ImmutableList.copyOf(this.contacts.values());
			this.contacts = null;
			if (contacts.isEmpty())
			{
				return;
			}

			contactPersonsEventBus.notifyChanged(contacts);
		}
	}
}
