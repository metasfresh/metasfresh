package de.metas.bpartner;

import de.metas.lang.RepoIdAware;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class BPartnerContactId implements RepoIdAware
{
	int repoId;

	@NonNull
	BPartnerId bpartnerId;

	public static BPartnerContactId ofRepoId(@NonNull final BPartnerId bpartnerId, final int contactId)
	{
		return new BPartnerContactId(bpartnerId, contactId);
	}

	public static BPartnerContactId ofRepoId(final int bpartnerId, final int contactId)
	{
		return new BPartnerContactId(BPartnerId.ofRepoId(bpartnerId), contactId);
	}

	public static BPartnerContactId ofRepoIdOrNull(@NonNull final BPartnerId bpartnerId, final int contactId)
	{
		return contactId > 0 ? ofRepoId(bpartnerId, contactId) : null;
	}

	private BPartnerContactId(@NonNull final BPartnerId bpartnerId, final int contactId)
	{
		this.repoId = Check.assumeGreaterThanZero(contactId, "contactId");
		this.bpartnerId = bpartnerId;
	}

	public static int toRepoId(final BPartnerContactId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
