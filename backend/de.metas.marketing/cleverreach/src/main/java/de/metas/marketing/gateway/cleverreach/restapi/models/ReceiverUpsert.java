/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.gateway.cleverreach.restapi.models;

import de.metas.marketing.base.model.ContactPerson;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ReceiverUpsert
{
	/**
	 * Note that for now we don't include the email's active status, because the remote system is the source of truth for that.
	 */
	public static ReceiverUpsert of(@NonNull final ContactPerson contactPerson)
	{
		final int id = StringUtils.trimBlankToOptional(contactPerson.getRemoteId()).map(Integer::parseInt).orElse(0);

		return builder()
				.id(id)
				.email(contactPerson.getEmailAddessStringOrNull())
				.build();
	}

	int id;
	@NonNull String email;

}
