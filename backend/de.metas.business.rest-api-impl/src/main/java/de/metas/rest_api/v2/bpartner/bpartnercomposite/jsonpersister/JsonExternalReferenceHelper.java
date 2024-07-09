/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister;

import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestGreetingUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.bankaccount.BPBankAccountType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.greeting.GreetingExternalReferenceType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static de.metas.externalreference.ExternalIdentifier.Type.EXTERNAL_REFERENCE;

@UtilityClass
public class JsonExternalReferenceHelper
{
	@NonNull
	public static Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final JsonRequestLocationUpsertItem upsertItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(upsertItem.getLocationIdentifier());

		if (externalIdentifier.getType() != EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(JsonExternalReferenceLookupItem.builder()
									.type(BPLocationExternalReferenceType.BPARTNER_LOCATION.getCode())
									.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
									.build())
				.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
				.externalSystemConfigId(upsertItem.getExternalSystemConfigId())
				.version(upsertItem.getExternalVersion())
				.isReadOnlyMetasfresh(upsertItem.getIsReadOnlyInMetasfresh())
				.build();

		return Optional.of(externalReferenceItem);
	}

	@NonNull
	public static Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final JsonRequestContactUpsertItem upsertItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(upsertItem.getContactIdentifier());

		if (externalIdentifier.getType() != EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(JsonExternalReferenceLookupItem.builder()
									.type(ExternalUserReferenceType.USER_ID.getCode())
									.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
									.build())
				.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
				.externalSystemConfigId(upsertItem.getExternalSystemConfigId())
				.isReadOnlyMetasfresh(upsertItem.getIsReadOnlyInMetasfresh())
				.build();

		return Optional.of(externalReferenceItem);
	}

	@NonNull
	public static Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final JsonRequestBPartnerUpsertItem upsertItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(upsertItem.getBpartnerIdentifier());

		if (externalIdentifier.getType() != EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(JsonExternalReferenceLookupItem.builder()
									.type(BPartnerExternalReferenceType.BPARTNER.getCode())
									.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
									.build())
				.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
				.externalSystemConfigId(upsertItem.getExternalSystemConfigId())
				.version(upsertItem.getExternalVersion())
				.isReadOnlyMetasfresh(upsertItem.getIsReadOnlyInMetasfresh())
				.externalReferenceUrl(upsertItem.getExternalReferenceUrl())
				.build();

		return Optional.of(externalReferenceItem);
	}

	@NonNull
	public static Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final JsonRequestBankAccountUpsertItem upsertItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(upsertItem.getIdentifier());

		if (externalIdentifier.getType() != EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(JsonExternalReferenceLookupItem.builder()
									.type(BPBankAccountType.BPBankAccount.getCode())
									.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
									.build())
				.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
				.build();

		return Optional.of(externalReferenceItem);
	}

	@NonNull
	public static Optional<JsonExternalReferenceRequestItem> getExternalReferenceItem(@NonNull final JsonRequestGreetingUpsertItem upsertItem)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(upsertItem.getIdentifier());

		if (externalIdentifier.getType() != EXTERNAL_REFERENCE)
		{
			return Optional.empty();
		}

		final JsonExternalReferenceRequestItem externalReferenceItem = JsonExternalReferenceRequestItem.builder()
				.lookupItem(JsonExternalReferenceLookupItem.builder()
									.type(GreetingExternalReferenceType.GREETING.getCode())
									.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
									.build())
				.externalReference(externalIdentifier.asExternalValueAndSystem().getValue())
				.build();

		return Optional.of(externalReferenceItem);
	}
}
