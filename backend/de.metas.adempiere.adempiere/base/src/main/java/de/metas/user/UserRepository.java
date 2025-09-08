package de.metas.user;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.Language;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_User;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class UserRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public User getByIdInTrx(@NonNull final UserId userId)
	{
		final I_AD_User userRecord = load(userId.getRepoId(), I_AD_User.class);

		Check.assumeNotNull(userRecord, "UserRecord not null");

		return ofRecord(userRecord);
	}

	public User ofRecord(@NonNull final I_AD_User userRecord)
	{
		final IUserBL userBL = Services.get(IUserBL.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final Language userLanguage = Language.asLanguage(userRecord.getAD_Language());
		final Language bpartnerLanguage = bPartnerBL.getLanguageForModel(userRecord).orElse(null);
		final Language language = userBL.getUserLanguage(userRecord);

		return User.builder()
				.bpartnerId(BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID()))
				.id(UserId.ofRepoId(userRecord.getAD_User_ID()))
				.externalId(ExternalId.ofOrNull(userRecord.getExternalId()))

				.name(userRecord.getName())
				.firstName(userRecord.getFirstname())
				.lastName(userRecord.getLastname())
				.birthday(TimeUtil.asLocalDate(userRecord.getBirthday()))
				.emailAddress(userRecord.getEMail())

				.userLanguage(userLanguage)
				.bPartnerLanguage(bpartnerLanguage)
				.language(language)

				.build();
	}

	public User save(@NonNull final User user)
	{
		final I_AD_User userRecord;
		if (user.getId() == null)
		{
			userRecord = newInstance(I_AD_User.class);
		}
		else
		{
			userRecord = load(user.getId().getRepoId(), I_AD_User.class);
		}
		userRecord.setC_BPartner_ID(BPartnerId.toRepoId(user.getBpartnerId()));
		userRecord.setName(user.getName());
		userRecord.setFirstname(user.getFirstName());
		userRecord.setLastname(user.getLastName());
		userRecord.setBirthday(TimeUtil.asTimestamp(user.getBirthday()));
		userRecord.setEMail(user.getEmailAddress());
		userRecord.setAD_Language(Language.asLanguageStringOrNull(user.getUserLanguage()));
		saveRecord(userRecord);

		return user
				.toBuilder()
				.id(UserId.ofRepoId(userRecord.getAD_User_ID()))
				.build();
	}

	@NonNull
	public Optional<UserId> getDefaultDunningContact(@NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsDunningContact, true)
				.create()
				.firstIdOnlyOptional(UserId::ofRepoIdOrNull);
	}
}
