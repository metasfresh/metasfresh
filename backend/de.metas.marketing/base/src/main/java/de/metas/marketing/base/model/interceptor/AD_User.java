package de.metas.marketing.base.model.interceptor;

import java.util.Optional;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.marketing.base.CampaignService;
import de.metas.marketing.base.ContactPersonService;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.CampaignRepository;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;

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
@Interceptor(I_AD_User.class)
@Component
public class AD_User
{

	private final static String MRG_MKTG_Campaign_NewsletterGroup_Missing_For_Org = "MKTG_Campaign_NewsletterGroup_Missing_For_Org";

	private final CampaignRepository campaignRepository;
	private final CampaignService campaignService;
	private final UserRepository userRepository;
	private final ContactPersonService contactPersonService;

	public AD_User(@NonNull final ContactPersonService contactPersonService,
			@NonNull final UserRepository userRepository,
			@NonNull final CampaignRepository campaignRepository,
			@NonNull final CampaignService campaignService)
	{
		this.contactPersonService = contactPersonService;
		this.userRepository = userRepository;
		this.campaignRepository = campaignRepository;
		this.campaignService = campaignService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE}, ifColumnsChanged = { I_AD_User.COLUMNNAME_IsNewsletter })
	public void onChangeNewsletter(final I_AD_User userRecord)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final boolean isNewsletter = userRecord.isNewsletter();

		final Optional<CampaignId> defaultcampaignId = campaignRepository.getDefaultNewsletterCampaignId(userRecord.getAD_Org_ID());

		if (isNewsletter)
		{
			if (!defaultcampaignId.isPresent())
			{
				final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(
						MRG_MKTG_Campaign_NewsletterGroup_Missing_For_Org,
						userRecord.getAD_Org().getName());

				throw new AdempiereException(translatableMsgText);
			}
			final User user = userRepository.ofRecord(userRecord);
			campaignService.addToCampaignIfHasEmailAddress(user, defaultcampaignId.get());
		}
		else
		{
			if (!defaultcampaignId.isPresent())
			{
				return; // nothing to do
			}
			final User user = userRepository.ofRecord(userRecord);
			campaignService.removeUserFromCampaign(user, defaultcampaignId.get());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = { I_AD_User.COLUMNNAME_EMail, I_AD_User.COLUMNNAME_AD_Language })
	public void onChangeEmail(final I_AD_User userRecord)
	{
		final User user = userRepository.ofRecord(userRecord);

		final I_AD_User oldUser = InterfaceWrapperHelper.createOld(userRecord, I_AD_User.class);

		final String oldEmail = oldUser.getEMail();
		final Language oldLanguage = Language.asLanguage(oldUser.getAD_Language());

		contactPersonService.updateContactPersonsEmailFromUser(user, oldEmail, oldLanguage);
	}
}
