/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContact.BPartnerContactBuilder;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.user.UserId;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Value
public class ShortTermContactIndex
{
	Map<BPartnerContactId, BPartnerContact> id2Contact;

	/**
	 * locations that were not (yet) retrieved via {@link #extract(ExternalIdentifier)}.
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

		for (final BPartnerContact bpartnerContact : bpartnerComposite.getContacts())
		{
			this.id2Contact.put(bpartnerContact.getId(), bpartnerContact);
			this.id2UnusedContact.put(bpartnerContact.getId(), bpartnerContact);
		}
	}

	public BPartnerContact extract(@NonNull final MetasfreshId metasfreshId)
	{
		if(bpartnerId == null){
			throw new AdempiereException("The provided contact belongs to another bpartner instance.");
		}

		final BPartnerContactId bpartnerContactId = BPartnerContactId.of(bpartnerId, UserId.ofRepoId(metasfreshId.getValue()));
		final BPartnerContact result = id2Contact.get(bpartnerContactId);

		if (result != null)
		{
			id2UnusedContact.remove(result.getId());
		}

		return result;
	}

	public BPartnerContact extract(@NonNull final ExternalIdentifier contactIdentifier)
	{
		final BPartnerContact result = extract0(contactIdentifier);
		if (result != null)
		{
			id2UnusedContact.remove(result.getId());
		}
		return result;
	}

	private BPartnerContact extract0(@NonNull final ExternalIdentifier contactIdentifier)
	{
		switch (contactIdentifier.getType())
		{
			case METASFRESH_ID:
				if (bpartnerId != null)
				{
					final BPartnerContactId bpartnerContactId = BPartnerContactId.of(bpartnerId, UserId.ofRepoId(contactIdentifier.asMetasfreshId().getValue()));
					return id2Contact.get(bpartnerContactId);
				}
				else
				{
					return null;
				}
			case EXTERNAL_REFERENCE:
				throw new AdempiereException("Type EXTERNAL_REFERENCE is not allowed. The identifier should be resolved to a METASFRESH_ID.");
			default:
				throw new InvalidIdentifierException(contactIdentifier.getRawValue());
		}
	}

	public BPartnerContact newContact(@NonNull final ExternalIdentifier contactIdentifier)
	{
		final BPartnerContact contact;
		final BPartnerContactBuilder contactBuilder = BPartnerContact.builder();

		switch (contactIdentifier.getType())
		{
			case METASFRESH_ID:

				throw new AdempiereException("No inserts are allowed with this type. External identifier: " + contactIdentifier.getRawValue());

			case EXTERNAL_REFERENCE:
				contact = contactBuilder.build();
				break;
			default:
				throw new InvalidIdentifierException(contactIdentifier.getRawValue());
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
