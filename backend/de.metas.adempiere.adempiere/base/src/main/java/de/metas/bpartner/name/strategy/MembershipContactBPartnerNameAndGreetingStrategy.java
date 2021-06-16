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
import de.metas.i18n.IMsgBL;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Component
public class MembershipContactBPartnerNameAndGreetingStrategy implements BPartnerNameAndGreetingStrategy
{
	public static final BPartnerNameAndGreetingStrategyId ID = BPartnerNameAndGreetingStrategyId.ofString("MEMBERSHIP_CONTACT");

	private static final AdMessageKey MSG_And = AdMessageKey.of("And");

	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
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
		final ImmutableList<ComputeNameAndGreetingRequest.Contact> membershipContacts = request.getContacts()
				.stream()
				.filter(ComputeNameAndGreetingRequest.Contact::isMembershipContact)
				.sorted(Comparator.comparing(ComputeNameAndGreetingRequest.Contact::getLastName))
				.limit(2)
				.collect(ImmutableList.toImmutableList());

		if (membershipContacts.isEmpty())
		{
			return ExplainedOptional.emptyBecause("no membership contact");
		}
		else if (membershipContacts.size() == 1)
		{
			final ComputeNameAndGreetingRequest.Contact contact = membershipContacts.get(0);
			return ExplainedOptional.of(NameAndGreeting.builder()
												.name(userBL.buildContactName(contact.getFirstName(), contact.getLastName()))
												.greetingId(contact.getGreetingId())
												.build());
		}
		else
		{
			final ComputeNameAndGreetingRequest.Contact person1 = membershipContacts.get(0);
			final ComputeNameAndGreetingRequest.Contact person2 = membershipContacts.get(1);

			if (!Objects.equals(person1.getLastName(), person2.getLastName()))
			{
				return ExplainedOptional.of(NameAndGreeting.builder()
													.name(userBL.buildContactName(person1.getFirstName(), person1.getLastName()))
													.greetingId(person1.getGreetingId())
													.build());
			}

			final Optional<Greeting> greetingComposite = greetingRepo.getComposite(person1.getGreetingId(), person2.getGreetingId());

			final Greeting greeting = greetingComposite.orElse(null);

			final GreetingId greetingId = greeting== null? null : greeting.getId();

			final String nameComposite = person1.getFirstName()
					+ " "
					+ msgBL.getMsg(Env.getCtx(), MSG_And)
					+ " "
					+ person2.getFirstName();
			return ExplainedOptional.of(NameAndGreeting.builder()
												.name(userBL.buildContactName(nameComposite, person1.getLastName()))
												.greetingId(greetingId)
												.build());
		}
	}

}
