package de.metas.marketing.base.model;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.i18n.Language;
import de.metas.letter.BoilerPlateId;
import de.metas.location.LocationId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder(toBuilder = true)
public class ContactPerson implements DataRecord
{
	public static ContactPerson newForUserPlatformAndLocation(
			@NonNull final User user,
			@NonNull final PlatformId platformId,
			@Nullable final BPartnerLocationId bpLocationId)
	{
		final EmailAddress emailaddress = Check.isEmpty(user.getEmailAddress(), true) ? null : EmailAddress.ofString(user.getEmailAddress());

		return ContactPerson.builder()
				.platformId(platformId)
				.name(user.getName())
				.userId(user.getId())
				.bPartnerId(user.getBpartnerId())
				.bpLocationId(bpLocationId)
				.address(emailaddress)
				.language(user.getLanguage())
				.build();
	}

	public static Optional<ContactPerson> cast(@Nullable final DataRecord dataRecord)
	{
		if (dataRecord instanceof ContactPerson)
		{
			return Optional.ofNullable((ContactPerson)dataRecord);
		}
		return Optional.empty();
	}

	String name;

	@Nullable
	UserId userId;

	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	ContactAddress address;

	/** might be null if the contact person was not stored yet */
	@Nullable
	ContactPersonId contactPersonId;

	/** the remote system's ID which we can use to sync with the campaign on the remote marketing tool */
	String remoteId;

	@NonNull
	PlatformId platformId;

	@Nullable
	BPartnerLocationId bpLocationId;

	/**
	 * If a {@link #bPartnerId} is not-null, then this is always the locationId of the respective {@link #bpLocationId}.
	 * In that case, if the respective {@link #bpLocationId} is {@code null}, then this is also {@code null}.
	 */
	@Nullable
	LocationId locationId;

	@Nullable
	BoilerPlateId boilerPlateId;

	@Nullable
	Language language;

	public String getEmailAddessStringOrNull()
	{
		return EmailAddress.getEmailAddessStringOrNull(getAddress());
	}

	public Boolean getEmailAddessIsActivatedOrNull()
	{
		return EmailAddress.getActiveOnRemotePlatformOrNull(getAddress());
	}
}
