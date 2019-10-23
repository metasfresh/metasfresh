package de.metas.bpartner.service;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Value
public class BPartnerContactQuery
{
	/** If set, it overrides the other query parameters */
	UserId userId;

	/** If set, it overrides the {@code value} parameter */
	ExternalId externalId;

	String value;

	/** If set, it is ANDed to the rest */
	BPartnerId bPartnerId;

	@Builder
	private BPartnerContactQuery(
			@Nullable final UserId userId,
			@Nullable final ExternalId externalId,
			@Nullable final String value,
			@Nullable final BPartnerId bPartnerId)
	{
		this.userId = userId;
		this.externalId = externalId;
		this.value = value;
		this.bPartnerId = bPartnerId;

		assume(userId != null || externalId != null || !isEmpty(value, true),
				"At least one of the parameters 'userId, externalId and value needs to be non-null/non-empty");
	}
}
