package de.metas.marketing.base.model;

import de.metas.marketing.base.model.ContactPerson.ContactPersonBuilder;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

/**
 * Represents some updated record received from a remote platform.<br>
 * <b>IMPORTANT:</b> the {@link #updateContactPerson(ContactPerson)} method may not overwrite anything that was unspecified in the update.
 * I.e. when adding further fields in here, please make sure to make a distinction between "empty/null" and "not-specified/leave-it-as-is".
 */
@Value
@Builder
public class ContactPersonRemoteUpdate
{
	ContactAddress address;

	/** the remote system's ID which we can use to sync with the campaign on the remote marketing tool */
	String remoteId;

	public ContactPerson updateContactPerson(@NonNull final ContactPerson contactPerson)
	{
		return applyAndBuild(contactPerson.toBuilder());
	}

	public ContactPerson toContactPerson(@NonNull final PlatformId platformId, @NonNull final OrgId orgId)
	{
		return applyAndBuild(ContactPerson.builder()
									 .orgId(orgId)
									 .platformId(platformId));
	}

	private ContactPerson applyAndBuild(@NonNull final ContactPersonBuilder builder)
	{
		return builder.address(address)
				.remoteId(remoteId)
				.build();
	}
}
