package de.metas.rest_api.bpartner.impl;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.bpartner.composite.BPartnerContact;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestComposite.JsonRequestCompositeBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsert.JsonRequestContactUpsertBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsert.JsonRequestLocationUpsertBuilder;
import de.metas.rest_api.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.rest_api.exception.InvalidIdentifierException;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.util.Check;
import lombok.NonNull;

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

@Service
public class JsonRequestConsolidateService
{
	public JsonRequestBPartnerUpsertItem consolidateWithIdentifier(@NonNull final JsonRequestBPartnerUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getBpartnerIdentifier());
		final JsonRequestComposite bpartnerComposite = requestItem.getBpartnerComposite();

		final JsonRequestCompositeBuilder result = bpartnerComposite.toBuilder();

		final JsonRequestBPartner consolidatedBPartner = consolidateBPartnerWithIdentifier(identifierString, bpartnerComposite.getBpartner());
		result.bpartner(consolidatedBPartner);

		final JsonRequestContactUpsert consolidatedContacts = consolidateWithIdentifier(bpartnerComposite.getContactsNotNull());
		result.contacts(consolidatedContacts);

		final JsonRequestLocationUpsert consolidatedLocations = consolidateWithIdentifier(bpartnerComposite.getLocationsNotNull());
		result.locations(consolidatedLocations);

		// note that bank-accounts don't need this treatment yet because they don't have the identifier stuff etc

		return requestItem.toBuilder().bpartnerComposite(result.build()).build();
	}

	public JsonRequestContactUpsert consolidateWithIdentifier(@NonNull final JsonRequestContactUpsert contacts)
	{
		final JsonRequestContactUpsertBuilder consolidatedContacts = contacts.toBuilder().clearRequestItems(); // preserve sync advise
		for (JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			consolidatedContacts.requestItem(consolidateWithIdentifier(contactRequestItem));
		}
		return consolidatedContacts.build();
	}

	public JsonRequestLocationUpsert consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsert locations)
	{
		final JsonRequestLocationUpsertBuilder consolidatedLocations = locations.toBuilder().clearRequestItems(); // preserve sync advise
		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			consolidatedLocations.requestItem(consolidateWithIdentifier(locationRequestItem));
		}
		return consolidatedLocations.build();
	}

	private JsonRequestBPartner consolidateBPartnerWithIdentifier(
			@NonNull final IdentifierString identifierString,
			@Nullable final JsonRequestBPartner jsonBPartner)
	{
		if (jsonBPartner == null)
		{
			return jsonBPartner; // nothing to consolidate with
		}

		JsonRequestBPartner consolidatedBPartner = jsonBPartner; // might be overridden
		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (jsonBPartner.getExternalId() == null)
				{
					consolidatedBPartner = jsonBPartner.toBuilder().externalId(identifierString.asJsonExternalId()).build();
				}
				break;
			case VALUE:
				if (Check.isEmpty(jsonBPartner.getCode(), true))
				{
					consolidatedBPartner = jsonBPartner.toBuilder().code(identifierString.asValue()).build();
				}
				break;
			case INTERNALNAME:
				throw new InvalidIdentifierException(identifierString);
			case GLN:
				throw new InvalidIdentifierException(identifierString);
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestBPartner", jsonBPartner);
		}
		return consolidatedBPartner;
	}

	private JsonRequestLocationUpsertItem consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getLocationIdentifier());
		final JsonRequestLocation jsonLocation = requestItem.getLocation();

		JsonRequestLocation consolidatedLocation = jsonLocation; // might be overridden

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (jsonLocation.getExternalId() == null)
				{
					consolidatedLocation = jsonLocation.toBuilder().externalId(identifierString.asJsonExternalId()).build();
				}
				break;
			case VALUE:
				throw new InvalidIdentifierException(identifierString);
			case INTERNALNAME:
				throw new InvalidIdentifierException(identifierString);
			case GLN:
				if (Check.isEmpty(jsonLocation.getGln(), true))
				{
					consolidatedLocation = jsonLocation.toBuilder().gln(identifierString.asGLN().getCode()).build();
				}
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestLocationUpsertItem", requestItem);
		}

		if (jsonLocation.equals(consolidatedLocation))
		{
			return requestItem;
		}
		return requestItem.toBuilder()
				.location(consolidatedLocation)
				.build();
	}

	private JsonRequestContactUpsertItem consolidateWithIdentifier(@NonNull final JsonRequestContactUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getContactIdentifier());
		final JsonRequestContact jsonContact = requestItem.getContact();

		JsonRequestContact consolidatedContact = jsonContact; // might be overridden
		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (jsonContact.getExternalId() == null)
				{
					consolidatedContact = jsonContact.toBuilder().externalId(identifierString.asJsonExternalId()).build();
				}
				break;
			case VALUE:
				if (Check.isEmpty(jsonContact.getCode(), true))
				{
					consolidatedContact = jsonContact.toBuilder().code(identifierString.asValue()).build();
				}
				break;
			case INTERNALNAME:
				throw new InvalidIdentifierException(identifierString);
			case GLN:
				throw new InvalidIdentifierException(identifierString);
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestContactUpsertItem", requestItem);
		}

		if (jsonContact.equals(consolidatedContact))
		{
			return requestItem;
		}
		return requestItem.toBuilder()
				.contact(consolidatedContact)
				.build();
	}

	/**
	 * Assumes that the given {@code contact} has a property that matches the given type.
	 * This is the case if {@link #consolidateWithIdentifier(JsonRequestContactUpsert)} was called on the contact's respective json request item.
	 */
	public IdentifierString extractIdentifier(@NonNull final Type type, @NonNull final BPartnerContact contact)
	{
		switch (type)
		{
			case METASFRESH_ID:
				return IdentifierString.ofRepoId(contact.getId().getRepoId());
			case EXTERNAL_ID:
				return IdentifierString.of(IdentifierString.PREFIX_EXTERNAL_ID + contact.getExternalId().getValue());
			case VALUE:
				return IdentifierString.of(IdentifierString.PREFIX_VALUE + contact.getValue());
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + type)
						.appendParametersToMessage()
						.setParameter("bpartnerContact", contact);
		}
	}
}
