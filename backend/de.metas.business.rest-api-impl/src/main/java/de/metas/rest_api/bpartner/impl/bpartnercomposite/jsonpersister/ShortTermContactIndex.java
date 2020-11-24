package de.metas.rest_api.bpartner.impl.bpartnercomposite.jsonpersister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContact.BPartnerContactBuilder;
import de.metas.rest_api.exception.InvalidIdentifierException;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.user.UserId;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
public class ShortTermContactIndex
{
	Map<BPartnerContactId, BPartnerContact> id2Contact;
	Map<ExternalId, BPartnerContact> externalId2Contact;
	Map<String, BPartnerContact> value2Contact;

	/**
	 * locations that were not (yet) retrieved via {@link #extract(IdentifierString)}.
	 */
	Map<BPartnerContactId, BPartnerContact> id2UnusedContact;

	BPartnerId bpartnerId;
	BPartnerComposite bpartnerComposite;

	public ShortTermContactIndex(@NonNull final BPartnerComposite bpartnerComposite)
	{
		this.bpartnerComposite = bpartnerComposite;
		this.bpartnerId = bpartnerComposite.getBpartner().getId();  // might be null; we synched to BPartner, but didn't yet save it

		this.id2Contact = new HashMap<>();
		this.id2UnusedContact = new HashMap<>();
		this.externalId2Contact = new HashMap<>();
		this.value2Contact = new HashMap<>();

		for (final BPartnerContact bpartnerContact : bpartnerComposite.getContacts())
		{
			this.id2Contact.put(bpartnerContact.getId(), bpartnerContact);
			this.id2UnusedContact.put(bpartnerContact.getId(), bpartnerContact);
			this.externalId2Contact.put(bpartnerContact.getExternalId(), bpartnerContact);
			this.value2Contact.put(bpartnerContact.getValue(), bpartnerContact);
		}
	}

	public BPartnerContact extract(@NonNull final IdentifierString contactIdentifier)
	{
		final BPartnerContact result = extract0(contactIdentifier);
		if (result != null)
		{
			id2UnusedContact.remove(result.getId());
		}
		return result;
	}

	private BPartnerContact extract0(@NonNull final IdentifierString contactIdentifier)
	{
		switch (contactIdentifier.getType())
		{
			case METASFRESH_ID:
				if (bpartnerId != null)
				{
					final BPartnerContactId bpartnerContactId = BPartnerContactId.of(bpartnerId, contactIdentifier.asMetasfreshId(UserId::ofRepoId));
					return id2Contact.get(bpartnerContactId);
				}
				else
				{
					return null;
				}
			case EXTERNAL_ID:
				final BPartnerContact resultByExternalId = externalId2Contact.get(contactIdentifier.asExternalId());
				return resultByExternalId;
			case VALUE:
				final BPartnerContact resultByValue = value2Contact.get(contactIdentifier.asValue());
				return resultByValue;
			default:
				throw new InvalidIdentifierException(contactIdentifier);
		}
	}

	public BPartnerContact newContact(@NonNull final IdentifierString contactIdentifier)
	{
		final BPartnerContact contact;
		final BPartnerContactBuilder contactBuilder = BPartnerContact.builder();

		switch (contactIdentifier.getType())
		{
			case METASFRESH_ID:
				if (bpartnerId != null)
				{
					final BPartnerContactId bpartnerContactId = BPartnerContactId.of(bpartnerId, contactIdentifier.asMetasfreshId(UserId::ofRepoId));
					contact = contactBuilder.id(bpartnerContactId).build();
					id2Contact.put(bpartnerContactId, contact);
				}
				else
				{
					contact = contactBuilder.build();
				}
				break;
			case EXTERNAL_ID:
				contact = contactBuilder.externalId(contactIdentifier.asExternalId()).build();
				externalId2Contact.put(contactIdentifier.asExternalId(), contact);
				break;
			case VALUE:
				contact = contactBuilder.value(contactIdentifier.asValue()).build();
				value2Contact.put(contactIdentifier.asValue(), contact);
				break;
			default:
				throw new InvalidIdentifierException(contactIdentifier);
		}

		bpartnerComposite
				.getContacts()
				.add(contact);

		return contact;
	}

	public Collection<BPartnerContact> getUnusedContacts()
	{
		return id2UnusedContact.values();
	}

	public void resetDefaultContactFlags()
	{
		for (final BPartnerContact bpartnerContact : getUnusedContacts())
		{
			bpartnerContact.getContactType().setDefaultContact(false);
		}
	}

	public void resetShipToDefaultFlags()
	{
		for (final BPartnerContact bpartnerContact : getUnusedContacts())
		{
			bpartnerContact.getContactType().setShipToDefault(false);
		}
	}

	public void resetPurchaseDefaultFlags()
	{
		for (final BPartnerContact bpartnerContact : getUnusedContacts())
		{
			bpartnerContact.getContactType().setPurchaseDefault(false);
		}
	}

	public void resetSalesDefaultFlags()
	{
		for (final BPartnerContact bpartnerContact : getUnusedContacts())
		{
			bpartnerContact.getContactType().setSalesDefault(false);
		}
	}

	public void resetBillToDefaultFlags()
	{
		for (final BPartnerContact bpartnerContact : getUnusedContacts())
		{
			bpartnerContact.getContactType().setBillToDefault(false);
		}
	}
}
