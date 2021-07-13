/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.utils;

import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;

@Value
public class OrgAndBPartnerCompositeLookupKey
{
	public static OrgAndBPartnerCompositeLookupKey ofIdentifierString(
			@NonNull final IdentifierString identifierString,
			@NonNull final OrgId orgId)
	{
		return of(
				BPartnerCompositeLookupKey.ofIdentifierString(identifierString),
				orgId);
	}

	public static OrgAndBPartnerCompositeLookupKey of(
			@NonNull final BPartnerCompositeLookupKey bpartnerCompositeLookupKey,
			@NonNull final OrgId orgId)
	{
		return new OrgAndBPartnerCompositeLookupKey(
				bpartnerCompositeLookupKey,
				orgId);
	}

	@NonNull
	BPartnerCompositeLookupKey compositeLookupKey;

	@NonNull
	OrgId orgId;
}