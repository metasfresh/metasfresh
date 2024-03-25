/*
 * #%L
 * de-metas-camel-ebay-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.ebay;

import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Werner Gaulke
 *
 */
@Value
public class MetasfreshIdProvider
{
	@NonNull
	@Getter(AccessLevel.NONE)
	Map<JsonExternalReferenceLookupItem, JsonExternalReferenceRequestItem> lookupItemToResponseItem;

	public static MetasfreshIdProvider of(final Map<JsonExternalReferenceLookupItem, JsonExternalReferenceRequestItem> lookupItemToResponseItem)
	{
		return new MetasfreshIdProvider(lookupItemToResponseItem);
	}

	@NonNull
	public Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final String externalId, @NonNull final String type)
	{
		final JsonExternalReferenceLookupItem lookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalId)
				.type(type)
				.build();

		return Optional.ofNullable(lookupItemToResponseItem.get(lookupItem));
	}
}
