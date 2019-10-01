package de.metas.bpartner.impexp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

final class BPartnersCache
{
	private final IBPartnerDAO bpartnersRepo;

	private final Map<BPartnerId, BPartner> bpartnersCache = new HashMap<>();

	@Builder
	private BPartnersCache(
			@NonNull final IBPartnerDAO bpartnersRepo)
	{
		this.bpartnersRepo = bpartnersRepo;
	}

	public BPartner getBPartnerById(final BPartnerId bpartnerId)
	{
		return bpartnersCache.computeIfAbsent(bpartnerId, this::retrieveBPartnerById);
	}

	private BPartner retrieveBPartnerById(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartnerRecord = bpartnersRepo.getByIdInTrx(bpartnerId);
		return new BPartner(bpartnerRecord);
	}

	public BPartner newBPartner(@NonNull final I_C_BPartner bpartnerRecord)
	{
		return new BPartner(bpartnerRecord);
	}

	public void clear()
	{
		bpartnersCache.clear();
	}

	final class BPartner
	{
		@Getter
		private I_C_BPartner record;

		private ArrayList<I_C_BPartner_Location> bpLocations;
		private ArrayList<I_AD_User> contacts;

		private BPartner(@NonNull final I_C_BPartner record)
		{
			this.record = record;
		}

		public BPartnerId getIdOrNull()
		{
			return BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());
		}

		public int getOrgId()
		{
			return record.getAD_Org_ID();
		}

		public void save()
		{
			final boolean isNew = record.getC_BPartner_ID() <= 0;
			bpartnersRepo.save(record);

			if (isNew)
			{
				bpLocations = new ArrayList<>();
				contacts = new ArrayList<>();
			}

			bpartnersCache.put(getIdOrNull(), this);
		}

		public Optional<I_C_BPartner_Location> getBPLocationById(@NonNull final BPartnerLocationId bpLocationId)
		{
			final int bpLocationRepoId = bpLocationId.getRepoId();
			return getFirstBPLocationMatching(bplRecord -> bplRecord.getC_BPartner_Location_ID() == bpLocationRepoId);
		}

		public Optional<I_C_BPartner_Location> getFirstBPLocationMatching(@NonNull final BPartnerLocationMatchingKey matchingKey)
		{
			return getFirstBPLocationMatching(bpLocation -> isBPartnerLocationMatching(bpLocation, matchingKey));
		}

		private boolean isBPartnerLocationMatching(@NonNull final I_C_BPartner_Location bpLocation, @NonNull final BPartnerLocationMatchingKey matchingKey)
		{
			final BPartnerLocationMatchingKey bpLocationKey = BPartnerLocationMatchingKey.of(bpLocation.getC_Location());
			return bpLocationKey.equals(matchingKey);
		}

		public Optional<I_C_BPartner_Location> getFirstBPLocationMatching(@NonNull final Predicate<I_C_BPartner_Location> filter)
		{
			return getOrLoadBPLocations()
					.stream()
					.filter(filter)
					.findFirst();
		}

		private ArrayList<I_C_BPartner_Location> getOrLoadBPLocations()
		{
			if (bpLocations == null)
			{
				if (record.getC_BPartner_ID() > 0)
				{
					bpLocations = new ArrayList<>(bpartnersRepo.retrieveBPartnerLocations(record));
				}
				else
				{
					bpLocations = new ArrayList<>();
				}
			}

			return bpLocations;
		}

		public void addAndSaveLocation(final I_C_BPartner_Location bpartnerLocation)
		{
			bpartnersRepo.save(bpartnerLocation);
			final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerLocation.getC_BPartner_ID(), bpartnerLocation.getC_BPartner_Location_ID());

			if (!getBPLocationById(bpartnerLocationId).isPresent())
			{
				getOrLoadBPLocations().add(bpartnerLocation);
			}
		}

		public Optional<I_AD_User> getContactById(final BPartnerContactId contactId)
		{
			return getOrLoadContacts()
					.stream()
					.filter(contact -> contact.getAD_User_ID() == contactId.getRepoId())
					.findFirst();
		}

		private ArrayList<I_AD_User> getOrLoadContacts()
		{
			if (contacts == null)
			{
				if (record.getC_BPartner_ID() > 0)
				{
					contacts = new ArrayList<>(bpartnersRepo.retrieveContacts(record));
				}
				else
				{
					contacts = new ArrayList<>();
				}
			}

			return contacts;
		}

		public BPartnerContactId addAndSaveContact(final I_AD_User contact)
		{
			bpartnersRepo.save(contact);
			final BPartnerContactId contactId = BPartnerContactId.ofRepoId(contact.getC_BPartner_ID(), contact.getAD_User_ID());

			if (!getContactById(contactId).isPresent())
			{
				getOrLoadContacts().add(contact);
			}

			return contactId;
		}
	}
}
