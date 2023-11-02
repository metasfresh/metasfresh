package de.metas.bpartner.composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static de.metas.util.Check.assume;

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
	@Nullable
	private OrgId orgId;

	private BPartner bpartner;

	@NonNull
	private final List<BPartnerLocation> locations;

	@NonNull
	private final List<BPartnerContact> contacts;

	@NonNull
	private final List<BPartnerBankAccount> bankAccounts;

	@NonNull
	private final List<BPartnerCreditLimit> creditLimits;

	@Builder(toBuilder = true)
	@JsonCreator
	private BPartnerComposite(
			@JsonProperty("org") @Nullable final OrgId orgId,
			@JsonProperty("bpartner") @Nullable final BPartner bpartner,
			@JsonProperty("locations") @Singular final List<BPartnerLocation> locations,
			@JsonProperty("contacts") @Singular final List<BPartnerContact> contacts,
			@JsonProperty("bankAccounts") @Singular final List<BPartnerBankAccount> bankAccounts,
			@JsonProperty("creditLimits") @Singular final List<BPartnerCreditLimit> creditLimits)
	{
		this.orgId = orgId;

		this.bpartner = coalesceSuppliers(
				() -> bpartner,
				() -> BPartner.builder().build());

		this.locations = new ArrayList<>(coalesceNotNull(locations, ImmutableList.of()));
		this.contacts = new ArrayList<>(coalesceNotNull(contacts, ImmutableList.of()));
		this.bankAccounts = new ArrayList<>(coalesceNotNull(bankAccounts, ImmutableList.of()));
		this.creditLimits = new ArrayList<>(coalesceNotNull(creditLimits, ImmutableList.of()));
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

	/**
	 * Only active instances are actually validated. Empty list means "valid"
	 */
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

	/**
	 * Changes this instance by removing all contacts whose IDs are not in the given set
	 */
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
		return extractLocation(bpLocation -> bpLocation.containsHandle(handle));
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

	@NonNull
	public Stream<BPartnerContactId> streamContactIds()
	{
		return this.getContacts().stream().map(BPartnerContact::getId);
	}

	@NonNull
	public Stream<BPartnerLocationId> streamBPartnerLocationIds()
	{
		return this.getLocations().stream().map(BPartnerLocation::getId);
	}

	@Nullable
	public String getOrgCode(@NonNull final Function<@NonNull OrgId, @NonNull String> orgId2String)
	{
		if (orgId == null)
		{
			return null;
		}

		return orgId2String.apply(orgId);
	}

	@NonNull
	public Optional<BPartnerBankAccount> getBankAccountByQrIban(@NonNull final String qrIban)
	{
		return bankAccounts.stream()
				.filter(bankAccount -> qrIban.equals(bankAccount.getQrIban()))
				.findFirst();
	}

	@NonNull
	public OrgId getOrgIdNotNull()
	{
		return Check.assumeNotNull(getOrgId(), "At this point, is expected the orgId to be resolved!");
	}

}
