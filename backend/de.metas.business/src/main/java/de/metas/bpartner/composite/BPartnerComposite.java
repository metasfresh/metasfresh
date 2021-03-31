package de.metas.bpartner.composite;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;
import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import de.metas.util.Check;
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

	private final List<BPartnerBankAccount> bankAccounts;

	@Builder(toBuilder = true)
	@JsonCreator
	private BPartnerComposite(
			@JsonProperty("org") @Nullable final OrgId orgId,
			@JsonProperty("bpartner") @Nullable final BPartner bpartner,
			@JsonProperty("locations") @Singular final List<BPartnerLocation> locations,
			@JsonProperty("contacts") @Singular final List<BPartnerContact> contacts,
			@JsonProperty("bankAccounts") @Singular final List<BPartnerBankAccount> bankAccounts)
	{
		this.orgId = orgId;

		this.bpartner = coalesceSuppliers(
				() -> bpartner,
				() -> BPartner.builder().build());

		this.locations = new ArrayList<>(coalesce(locations, ImmutableList.of()));
		this.contacts = new ArrayList<>(coalesce(contacts, ImmutableList.of()));
		this.bankAccounts = new ArrayList<>(coalesce(bankAccounts, ImmutableList.of()));
	}

	public ImmutableSet<GLN> extractLocationGlns()
	{
		return this.locations
				.stream()
				.map(BPartnerLocation::getGln)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
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

	/** Only active instances are actually validated. Empty list means "valid" */
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
			result.addAll(bpartner.validate());
		}

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
			if (!contact.isActive())
			{
				continue;
			}
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

		final List<BPartnerLocation> activeDefaultShipToLocations = new ArrayList<>();
		final List<BPartnerLocation> activeDefaultBillToLocations = new ArrayList<>();
		for (final BPartnerLocation location : locations)
		{
			if (!location.isActive())
			{
				continue;
			}
			result.addAll(location.validate());

			final BPartnerLocationType locationType = location.getLocationType();
			if (locationType != null && locationType.getBillToDefault().orElse(false))
			{
				activeDefaultBillToLocations.add(location);
			}
			if (locationType != null && locationType.getShipToDefault().orElse(false))
			{
				activeDefaultShipToLocations.add(location);
			}
		}
		if (activeDefaultShipToLocations.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one location may be flagged as default shipTo location"));
		}
		if (activeDefaultBillToLocations.size() > 1)
		{
			result.add(TranslatableStrings.constant("Not more than one location may be flagged as default billTo location"));
		}
		return result.build();
	}

	private ImmutableList<ITranslatableString> validateLookupKeys()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();

		final boolean hasLookupKey = bpartner.isIdentifiedByExternalReference()
				|| bpartner.getId() != null
				|| !isEmpty(bpartner.getValue(), true)
				|| bpartner.getExternalId() != null
				|| !extractLocationGlns().isEmpty();
		if (!hasLookupKey)
		{
			result.add(TranslatableStrings.constant("At least one of bpartner.id, bpartner.code, bpartner.externalId or one location.gln needs to be non-empty"));
		}

		return result.build();
	}

	public Optional<BPartnerContact> extractContactOpt(@NonNull final BPartnerContactId contactId)
	{
		assume(contactId.getBpartnerId().equals(bpartner.getId()), "The given contactId's bpartnerId needs to be equal to {}; contactId={}", bpartner.getId(), contactId);
		return getContacts()
				.stream()
				.filter(c -> contactId.equals(c.getId()))
				.findAny();
	}

	public Optional<BPartnerContact> extractContact(@NonNull final BPartnerContactId contactId)
	{
		assume(contactId.getBpartnerId().equals(bpartner.getId()), "The given contactId's bpartnerId needs to be equal to {}; contactId={}", bpartner.getId(), contactId);
		return createFilteredContactStream(c -> Objects.equals(c.getId(), contactId))
				.findAny();
	}

	public Optional<BPartnerContact> extractContactByHandle(@NonNull final String handle)
	{
		final Predicate<BPartnerContact> predicate = c -> c.getHandles().contains(handle);
		return extractContact(predicate);
	}

	public Optional<BPartnerContact> extractContact(@NonNull final Predicate<BPartnerContact> filter)
	{
		return createFilteredContactStream(filter)
				.findAny();
	}

	private Stream<BPartnerContact> createFilteredContactStream(@NonNull final Predicate<BPartnerContact> filter)
	{
		return getContacts()
				.stream()
				.filter(filter);
	}

	/** Changes this instance by removing all contacts whose IDs are not in the given set */
	public void retainContacts(@NonNull final Set<BPartnerContactId> contactIdsToRetain)
	{
		contacts.removeIf(contact -> !contactIdsToRetain.contains(contact.getId()));
	}

	public Optional<BPartnerLocation> extractLocation(@NonNull final BPartnerLocationId bPartnerLocationId)
	{
		final Predicate<BPartnerLocation> predicate = l -> bPartnerLocationId.equals(l.getId());

		return createFilteredLocationStream(predicate).findAny();
	}

	public Optional<BPartnerLocation> extractBillToLocation()
	{
		final Predicate<BPartnerLocation> predicate = l -> l.getLocationType().getIsBillToOr(false);

		return createFilteredLocationStream(predicate)
				.sorted(Comparator.comparing(l -> !l.getLocationType().getIsBillToDefaultOr(false)))
				.findFirst();
	}

	public Optional<BPartnerLocation> extractShipToLocation()
	{
		final Predicate<BPartnerLocation> predicate = l -> l.getLocationType().getIsShipToOr(false);

		return createFilteredLocationStream(predicate)
				.sorted(Comparator.comparing(l -> !l.getLocationType().getIsShipToDefaultOr(false)))
				.findFirst();
	}

	public Optional<BPartnerLocation> extractLocationByHandle(@NonNull final String handle)
	{
		final Predicate<BPartnerLocation> predicate = l -> l.getHandles().contains(handle);
		return extractLocation(predicate);
	}

	public Optional<BPartnerLocation> extractLocation(@NonNull final Predicate<BPartnerLocation> filter)
	{
		return createFilteredLocationStream(filter).findAny();
	}

	private Stream<BPartnerLocation> createFilteredLocationStream(@NonNull final Predicate<BPartnerLocation> filter)
	{
		return getLocations()
				.stream()
				.filter(filter);
	}

	public List<BPartnerBankAccount> getBankAccounts()
	{
		return bankAccounts;
	}

	public void addBankAccount(@NonNull final BPartnerBankAccount bankAccount)
	{
		bankAccounts.add(bankAccount);
	}

	public Optional<BPartnerBankAccount> getBankAccountByIBAN(@NonNull final String iban)
	{
		Check.assumeNotEmpty(iban, "iban is not empty");

		return bankAccounts.stream()
				.filter(bankAccount -> iban.equals(bankAccount.getIban()))
				.findFirst();
	}
}
