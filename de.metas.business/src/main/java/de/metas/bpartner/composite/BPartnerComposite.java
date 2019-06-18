package de.metas.bpartner.composite;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;
import static de.metas.util.lang.CoalesceUtil.coalesceSuppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerContactId;
import de.metas.i18n.ITranslatableString;
import de.metas.util.rest.ExternalId;
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

	public ImmutableList<String> extractLocationGlns()
	{
		return this.locations
				.stream()
				.map(BPartnerLocation::getGln)
				.filter(gln -> !isEmpty(gln, true))
				.collect(ImmutableList.toImmutableList());
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
			result.add(ITranslatableString.constant("Missing BPartnerComposite.orgId"));
		}

		if (bpartner == null)
		{
			result.add(ITranslatableString.constant("Missing BPartnerComposite.bpartner"));
		}
		else
		{
			final boolean lokupValuesAreOk = !isEmpty(bpartner.getValue(), true)
					|| bpartner.getExternalId() != null
					|| !extractLocationGlns().isEmpty();
			if (!lokupValuesAreOk)
			{
				result.add(ITranslatableString.constant("At least one of BPartner.code, bpartner.externalId or one location.gln needs to be non-empty"));
			}
		}

		result.addAll(bpartner.validate());

		return result.build();
	}

	public Optional<BPartnerContact> getContact(@NonNull final BPartnerContactId contactId)
	{
		return getContacts()
				.stream()
				.filter(c -> contactId.equals(c.getId()))
				.findAny();
	}

	public Optional<BPartnerLocation> extractLocation(@NonNull final ExternalId externalId)
	{
		return getLocations()
				.stream()
				.filter(l -> externalId.equals(l.getExternalId()))
				.findAny();
	}

	public Optional<BPartnerContact> extractContact(@NonNull final ExternalId externalId)
	{
		return getContacts()
				.stream()
				.filter(c -> externalId.equals(c.getExternalId()))
				.findAny();
	}
}
