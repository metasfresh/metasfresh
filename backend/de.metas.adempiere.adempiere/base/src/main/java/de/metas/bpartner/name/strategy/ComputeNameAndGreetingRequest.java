/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.name.strategy;

import com.google.common.collect.ImmutableList;
import de.metas.greeting.GreetingId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Value
@Builder
public class ComputeNameAndGreetingRequest
{
	@Nullable
	String adLanguage;

	@Value
	@Builder
	public static class Contact
	{
		@Nullable
		GreetingId greetingId;

		@Nullable
		String firstName;

		@Nullable
		String lastName;

		int seqNo;

		boolean isDefaultContact;
		boolean isMembershipContact;
	}

	@Singular @NonNull ImmutableList<Contact> contacts;

	public Optional<Contact> getPrimaryContact()
	{
		final List<Contact> contactsOrderedPrimaryFirst = getContactsOrderedPrimaryFirst();
		return !contactsOrderedPrimaryFirst.isEmpty()
				? Optional.of(contactsOrderedPrimaryFirst.get(0))
				: Optional.empty();
	}

	public List<Contact> getContactsOrderedPrimaryFirst()
	{
		return contacts.stream()
				.sorted(Comparator.<Contact, Integer>comparing(contact -> contact.isDefaultContact() ? 0 : 1)
						.thenComparing(Contact::getSeqNo))
				.collect(ImmutableList.toImmutableList());
	}
}
