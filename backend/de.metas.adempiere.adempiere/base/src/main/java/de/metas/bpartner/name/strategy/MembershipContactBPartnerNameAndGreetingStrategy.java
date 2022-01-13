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
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.greeting.Greeting;
import de.metas.greeting.GreetingId;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.model.X_C_BP_Group;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MembershipContactBPartnerNameAndGreetingStrategy implements BPartnerNameAndGreetingStrategy
{
	public static final BPartnerNameAndGreetingStrategyId ID = BPartnerNameAndGreetingStrategyId.ofString(X_C_BP_Group.BPNAMEANDGREETINGSTRATEGY_MembershipContact);

	private static final AdMessageKey MSG_And = AdMessageKey.of("And");

	private final IUserBL userBL = Services.get(IUserBL.class);
	private final GreetingRepository greetingRepo;

	public MembershipContactBPartnerNameAndGreetingStrategy(final GreetingRepository greetingRepo)
	{
		this.greetingRepo = greetingRepo;
	}

	@Override
	public BPartnerNameAndGreetingStrategyId getId()
	{
		return ID;
	}

	@Override
	public ExplainedOptional<NameAndGreeting> compute(final ComputeNameAndGreetingRequest request)
	{
		final ImmutableList<ComputeNameAndGreetingRequest.Contact> allContacts = request.getContacts();
		final ImmutableList<ComputeNameAndGreetingRequest.Contact> membershipContacts = allContacts
				.stream()
				.filter(ComputeNameAndGreetingRequest.Contact::isMembershipContact)
				.collect(ImmutableList.toImmutableList());

		if (membershipContacts.isEmpty())
		{
			return !allContacts.isEmpty()
					? computeForSingleContact(allContacts.get(0), false)
					: ExplainedOptional.emptyBecause("no membership contact");
		}
		else if (membershipContacts.size() == 1)
		{
			final ComputeNameAndGreetingRequest.Contact contact = membershipContacts.get(0);
			return computeForSingleContact(contact, true);
		}
		else // at least 2 membership contacts
		{
			final String adLanguage = StringUtils.trimBlankToOptional(request.getAdLanguage()).orElseGet(Language::getBaseAD_Language);
			final ComputeNameAndGreetingRequest.Contact person1 = membershipContacts.get(0);
			final ComputeNameAndGreetingRequest.Contact person2 = membershipContacts.get(1);

			return computeForTwoContacts(person1, person2, adLanguage);
		}
	}

	@NonNull
	private ExplainedOptional<NameAndGreeting> computeForSingleContact(@NonNull final ComputeNameAndGreetingRequest.Contact contact, final boolean isMembershipContact)
	{
		final String nameComposite =
				contact.getFirstName()
				+" "+
				contact.getLastName();

		return ExplainedOptional.of(NameAndGreeting.builder()
				.name(nameComposite)
				.greetingId(isMembershipContact ? contact.getGreetingId() : null)
				.build());
	}

	@NonNull
	private ExplainedOptional<NameAndGreeting> computeForTwoContacts(
			@NonNull final ComputeNameAndGreetingRequest.Contact person1,
			@NonNull final ComputeNameAndGreetingRequest.Contact person2,
			@NonNull final String adLanguage)
	{
		final GreetingId greetingId = greetingRepo.getComposite(person1.getGreetingId(), person2.getGreetingId())
				.map(Greeting::getId)
				.orElse(null);

		if (!Objects.equals(person1.getLastName(), person2.getLastName()))
		{
			final String nameComposite = TranslatableStrings.builder()
					.append(person1.getFirstName())
					.append(" ")
					.append(person1.getLastName())
					.append(" ")
					.appendADMessage(MSG_And)
					.append(" ")
					.append(person2.getFirstName())
					.append(" ")
					.append(person2.getLastName())
					.build()
					.translate(adLanguage);

			return ExplainedOptional.of(NameAndGreeting.builder()
												.name(nameComposite)
												.greetingId(greetingId)
												.build());
		}
		else
		{
			final String nameComposite = TranslatableStrings.builder()
					.append(person1.getFirstName())
					.append(" ")
					.appendADMessage(MSG_And)
					.append(" ")
					.append(person2.getFirstName())
					.append(" ")
					.append(person1.getLastName())
					.build()
					.translate(adLanguage);

			return ExplainedOptional.of(NameAndGreeting.builder()
					.name(nameComposite)
					.greetingId(greetingId)
					.build());
		}
	}

}
