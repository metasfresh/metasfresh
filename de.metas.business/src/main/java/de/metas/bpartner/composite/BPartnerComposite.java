package de.metas.bpartner.composite;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Data
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public final class BPartnerComposite
{
	private OrgId orgId;

	private BPartner bpartner;

	private final List<BPartnerLocation> locations;

	private final List<BPartnerContact> contacts;

	@Builder(toBuilder = true)
	@JsonCreator
	private BPartnerComposite(
			@JsonProperty("org") @Nullable final OrgId orgId,
			@JsonProperty("bpartner") @Nullable final BPartner bpartner,
			@JsonProperty("locations") @Singular final List<BPartnerLocation> locations,
			@JsonProperty("contacts") @Singular final List<BPartnerContact> contacts)
	{
		this.orgId = orgId;

		this.bpartner = coalesceSuppliers(
				() -> bpartner,
				() -> BPartner.builder().build());

		this.locations = new ArrayList<>(coalesce(locations, ImmutableList.of()));
		this.contacts = new ArrayList<>(coalesce(contacts, ImmutableList.of()));
	}

	public ImmutableSet<GLN> extractLocationGlns()
	{
		return this.locations
				.stream()
				.map(BPartnerLocation::getGln)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	public BPartnerContact extractContact(@NonNull final BPartnerContactId contactId)
	{
		assume(contactId.getBpartnerId().equals(bpartner.getId()), "The given contactId's bpartnerId needs to be equal to {}; contactId={}", bpartner.getId(), contactId);
		return contacts
				.stream()
				.filter(c -> Objects.equals(c.getId(), contactId))
				.findAny()
				.orElseThrow(() -> new AdempiereException("Missing contact with contactId=" + contactId));
	}

	public BPartnerComposite deepCopy()
	{
		final BPartnerCompositeBuilder result = this
				.toBuilder()
				.bpartner(getBpartner().toBuilder().build());

		result.clearLocations();
		for (final BPartnerLocation location : getLocations())
		{
			result.location(location.deepCopy());
		}

		result.clearContacts();
		for (final BPartnerContact contact : getContacts())
		{
			result.contact(contact.deepCopy());
		}

		return result.build();
	}

	/** empty list means valid */
	public ImmutableList<ITranslatableString> validate()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		if (orgId == null)
		{
			result.add(TranslatableStrings.constant("Missing bpartnerComposite.orgId"));
		}

		if (bpartner == null)
		{
			result.add(TranslatableStrings.constant("Missing bpartnerComposite.bpartner"));
		}
		else
		{
			result.addAll(validateLookupKeys());
		}

		result.addAll(bpartner.validate());

		result.addAll(validateLocations());

		result.addAll(validateContacts());

		return result.build();
	}

	private ImmutableList<ITranslatableString> validateContacts()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		final List<BPartnerContact> defaultContacts = new ArrayList<>();
		final List<BPartnerContact> purchaseDefaultContacts = new ArrayList<>();
		final List<BPartnerContact> salesDefaultContacts = new ArrayList<>();

		for (final BPartnerContact contact : contacts)
		{
			// result.addAll(contact.validate()); // doesn't yet have a validate method

			final BPartnerContactType contactType = contact.getContactType();
			if (contactType != null && contactType.getDefaultContact().orElse(false))
			{
				defaultContacts.add(contact);
			}
			if (contactType != null && contactType.getPurchaseDefault().orElse(false))
			{
				purchaseDefaultContacts.add(contact);
			}
			if (contactType != null && contactType.getSalesDefault().orElse(false))
			{
				salesDefaultContacts.add(contact);
			}
		}
		if (defaultContacts.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one contact may be flagged as default"));
		}
		if (purchaseDefaultContacts.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one contact may be flagged as purchaseDefault"));
		}
		if (salesDefaultContacts.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one contact may be flagged as salesDefault"));
		}

		return result.build();
	}

	private ImmutableList<ITranslatableString> validateLocations()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		final List<BPartnerLocation> defaultShipToLocations = new ArrayList<>();
		final List<BPartnerLocation> defaultBillToLocations = new ArrayList<>();
		for (final BPartnerLocation location : locations)
		{
			result.addAll(location.validate());

			final BPartnerLocationType locationType = location.getLocationType();
			if (locationType != null && locationType.getBillToDefault().orElse(false))
			{
				defaultBillToLocations.add(location);
			}
			if (locationType != null && locationType.getShipToDefault().orElse(false))
			{
				defaultShipToLocations.add(location);
			}
		}
		if (defaultShipToLocations.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one location may be flagged as default shipTo location"));
		}
		if (defaultBillToLocations.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one location may be flagged as default billTo location"));
		}
		return result.build();
	}

	private ImmutableList<ITranslatableString> validateLookupKeys()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		final boolean hasLookupKey = bpartner.getId() != null
				|| !isEmpty(bpartner.getValue(), true)
				|| bpartner.getExternalId() != null
				|| !extractLocationGlns().isEmpty();
		if (!hasLookupKey)
		{
			result.add(TranslatableStrings.constant("At least one of bpartner.id, bpartner.code, bpartner.externalId or one location.gln needs to be non-empty"));
		}

		return result.build();
	}

	public Optional<BPartnerContact> getContact(@NonNull final BPartnerContactId contactId)
	{
		return getContacts()
				.stream()
				.filter(c -> contactId.equals(c.getId()))
				.findAny();
	}

	public Optional<BPartnerLocation> extractLocation(@NonNull final BPartnerLocationId bPartnerLocationId)
	{
		return getLocations()
				.stream()
				.filter(l -> bPartnerLocationId.equals(l.getId()))
				.findAny();
	}

	/** Changes this instance by removing all contacts whose IDs are not in the given set */
	public void retainContacts(@NonNull final Set<BPartnerContactId> contactIdsToRetain)
	{
		contacts.removeIf(contact -> !contactIdsToRetain.contains(contact.getId()));
	}
}
