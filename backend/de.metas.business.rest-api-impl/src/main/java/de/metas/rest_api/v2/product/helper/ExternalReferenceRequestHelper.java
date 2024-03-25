/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.rest_api.v2.product.helper;

import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalreference.v2.JsonRequestExternalReferenceUpsert;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReferenceValueAndSystem;
import de.metas.externalreference.allergen.AllergenExternalReferenceType;
import de.metas.product.allergen.AllergenId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ExternalReferenceRequestHelper
{
	@NonNull
	public static Optional<JsonRequestExternalReferenceUpsert> getRequestExternalRefUpsert(
			@NonNull final AllergenId allergenId,
			@NonNull final ExternalIdentifier externalIdentifier)
	{
		if (externalIdentifier.getType() != ExternalIdentifier.Type.EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final ExternalReferenceValueAndSystem externalReferenceValueAndSystem = externalIdentifier.asExternalValueAndSystem();

		final JsonExternalReferenceLookupItem externalReferenceLookupItem = JsonExternalReferenceLookupItem.builder()
				.externalReference(externalReferenceValueAndSystem.getValue())
				.type(AllergenExternalReferenceType.ALLERGEN.getCode())
				.build();

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(externalReferenceLookupItem)
				.metasfreshId(JsonMetasfreshId.of(allergenId.getRepoId()))
				.build();

		return Optional.of(JsonRequestExternalReferenceUpsert.builder()
								   .systemName(JsonExternalSystemName.of(externalIdentifier.asExternalValueAndSystem().getExternalSystem()))
								   .externalReferenceItem(externalReferenceItem)
								   .build());
	}
}
