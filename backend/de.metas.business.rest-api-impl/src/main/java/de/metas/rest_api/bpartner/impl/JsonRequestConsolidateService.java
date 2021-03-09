package de.metas.rest_api.bpartner.impl;

import de.metas.bpartner.composite.BPartnerContact;
import de.metas.common.bpartner.request.JsonRequestBPartner;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.request.JsonRequestComposite;
import de.metas.common.bpartner.request.JsonRequestContact;
import de.metas.common.bpartner.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.request.JsonRequestLocation;
import de.metas.common.bpartner.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.request.JsonRequestLocationUpsertItem;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.IdentifierString.Type;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

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

/**
 * If a request-item is coming with an identifier such as {@code ext-1234}, then this service makes sure that the item itself has the respective property such as {@code "externalId" : "1234"} set.
 */
@Service
public class JsonRequestConsolidateService
{
	/**
	 * Puts the request's identifier string also into the request's item.
	 * E.g. if the identifier string is val-123 and the request's item's value is empty, then it puts item's value = "123"
	 */
	public void consolidateWithIdentifier(@NonNull final JsonRequestBPartnerUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getBpartnerIdentifier());
		final JsonRequestComposite bpartnerComposite = requestItem.getBpartnerComposite();

		consolidateBPartnerWithIdentifier(identifierString, bpartnerComposite.getBpartner());

		consolidateWithIdentifier(bpartnerComposite.getContactsNotNull());

		consolidateWithIdentifier(bpartnerComposite.getLocationsNotNull());

		// note that bank-accounts don't need this treatment yet because they don't have the identifier stuff etc
	}

	public void consolidateWithIdentifier(@NonNull final JsonRequestContactUpsert contacts)
	{
		for (final JsonRequestContactUpsertItem contactRequestItem : contacts.getRequestItems())
		{
			consolidateWithIdentifier(contactRequestItem);
		}
	}

	public void consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsert locations)
	{
		for (final JsonRequestLocationUpsertItem locationRequestItem : locations.getRequestItems())
		{
			consolidateWithIdentifier(locationRequestItem);
		}
	}

	/**
	 * @return the identifier string which the given {@code jsonBPartner} will have *after* if was synched and persistes in metasfresh.
	 */
	private void consolidateBPartnerWithIdentifier(
			@NonNull final IdentifierString identifierString,
			@Nullable final JsonRequestBPartner jsonBPartner)
	{
		if (jsonBPartner == null)
		{
			return;
		}

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (!jsonBPartner.isExternalIdSet())
				{
					jsonBPartner.setExternalId(identifierString.asJsonExternalId());
				}
				break;
			case VALUE:
				if (!jsonBPartner.isCodeSet())
				{
					jsonBPartner.setCode(identifierString.asValue());
				}
				break;
			case INTERNALNAME:
				throw new InvalidIdentifierException(identifierString);
			case GLN:
				// GLN-identifierString is valid for bPartner-lookup, but we can't consolidate the given jsonBPartner with it
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestBPartner", jsonBPartner);
		}
	}

	private void consolidateWithIdentifier(@NonNull final JsonRequestLocationUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getLocationIdentifier());
		final JsonRequestLocation jsonLocation = requestItem.getLocation();

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (!jsonLocation.isExternalIdSet())
				{
					jsonLocation.setExternalId(identifierString.asJsonExternalId());

				}
				break;
			case VALUE:
				throw new InvalidIdentifierException(identifierString);
			case INTERNALNAME:
				throw new InvalidIdentifierException(identifierString);
			case GLN:
				if (jsonLocation.isGlnSet())
				{
					jsonLocation.setGln(identifierString.asGLN().getCode());
				}
				break;
			default:
				throw new AdempiereException("Unexpected IdentifierString.Type=" + identifierString.getType())
						.appendParametersToMessage()
						.setParameter("identifierString", identifierString)
						.setParameter("jsonRequestLocationUpsertItem", requestItem);
		}
	}

	private void consolidateWithIdentifier(@NonNull final JsonRequestContactUpsertItem requestItem)
	{
		final IdentifierString identifierString = IdentifierString.of(requestItem.getContactIdentifier());
		final JsonRequestContact jsonContact = requestItem.getContact();

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				// nothing to do; the bpartner-JSON has no metasfresh-ID to consolidate with
				break;
			case EXTERNAL_ID:
				if (!jsonContact.isExternalIdSet())
				{
					jsonContact.setExternalId(identifierString.asJsonExternalId());
				}
				break;
			case VALUE:
				if (!jsonContact.isCodeSet())
				{
					jsonContact.setCode(identifierString.asValue());
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
