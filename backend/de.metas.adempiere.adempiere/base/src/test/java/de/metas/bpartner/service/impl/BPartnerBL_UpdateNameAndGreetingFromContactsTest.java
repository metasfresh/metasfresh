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

package de.metas.bpartner.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategy;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategyId;
import de.metas.bpartner.name.strategy.FirstContactBPartnerNameAndGreetingStrategy;
import de.metas.bpartner.name.strategy.MembershipContactBPartnerNameAndGreetingStrategy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.greeting.GreetingRepository;
import de.metas.greeting.GreetingStandardType;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Greeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class BPartnerBL_UpdateNameAndGreetingFromContactsTest
{
	// taken from de.metas.greeting.GreetingsMapTest
	private I_C_Greeting greeting_MR;
	private I_C_Greeting greeting_MRS;
	private I_C_Greeting greeting_MR_AND_MRS;

	private BPartnerBL bPartnerBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		SpringContextHolder.registerJUnitBean(GreetingRepository.class, new GreetingRepository());
		final List<BPartnerNameAndGreetingStrategy> strategies = new ArrayList<>();
		strategies.add(new FirstContactBPartnerNameAndGreetingStrategy());
		strategies.add(new MembershipContactBPartnerNameAndGreetingStrategy(new GreetingRepository()));
		SpringContextHolder.registerJUnitBean(BPartnerNameAndGreetingStrategies.class, new BPartnerNameAndGreetingStrategies(Optional.of(strategies)));

		bPartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bPartnerBL);

		greeting_MR = createGreeting(GreetingStandardType.MR);
		greeting_MRS = createGreeting(GreetingStandardType.MRS);
		greeting_MR_AND_MRS = createGreeting(GreetingStandardType.MR_AND_MRS);
	}

	@Test
	public void useFirstContact()
	{
		final I_C_BP_Group group = createGroup(FirstContactBPartnerNameAndGreetingStrategy.ID);
		final I_C_BPartner partner = createPartner(group);

		final String firstName = "FirstName";
		final String lastname = "LastName";
		final boolean isMembership = false;
		createUser(
				partner,
				firstName,
				lastname,
				greeting_MRS,
				isMembership,
				-1);

		bPartnerBL.updateNameAndGreetingFromContacts(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		refresh(partner);
		assertThat(partner.getName()).isEqualTo(lastname + ", " + firstName);
		assertThat(partner.getC_Greeting_ID()).isEqualTo(-1);
	}

	@Test
	public void useFirstMembershipContact()
	{
		final I_C_BP_Group group = createGroup(MembershipContactBPartnerNameAndGreetingStrategy.ID);
		final I_C_BPartner partner = createPartner(group);

		final String firstName = "FirstName";
		final String lastname = "LastName";

		final boolean isMembership = true;
		createUser(
				partner,
				firstName,
				lastname,
				greeting_MRS,
				isMembership,
				-1);

		bPartnerBL.updateNameAndGreetingFromContacts(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		refresh(partner);
		assertThat(partner.getName()).isEqualTo(firstName + " " + lastname);
		assertThat(partner.getC_Greeting_ID()).isEqualTo(greeting_MRS.getC_Greeting_ID());
	}

	@Test
	public void use2MembershipContacts_SameLastname()
	{
		final I_C_BP_Group group = createGroup(MembershipContactBPartnerNameAndGreetingStrategy.ID);
		final I_C_BPartner partner = createPartner(group);

		final String firstName = "FirstName";
		final String lastname = "LastName";

		final boolean isMembership = true;
		createUser(
				partner,
				firstName,
				lastname,
				greeting_MR,
				isMembership,
				-1);

		final String firstName2 = "FirstName2";

		createGreeting(GreetingStandardType.MRS);
		final boolean isMembership2 = true;
		createUser(
				partner,
				firstName2,
				lastname,
				greeting_MRS,
				isMembership2,
				-1);

		bPartnerBL.updateNameAndGreetingFromContacts(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		refresh(partner);
		assertThat(partner.getName()).isEqualTo(firstName + " And " + firstName2 + " " + lastname);
		assertThat(partner.getC_Greeting_ID()).isEqualTo(greeting_MR_AND_MRS.getC_Greeting_ID());
	}

	@Test
	public void use2MembershipContacts_SameLastnameWithSeq()
	{
		final I_C_BP_Group group = createGroup(MembershipContactBPartnerNameAndGreetingStrategy.ID);
		final I_C_BPartner partner = createPartner(group);


		final String firstName2 = "FirstName2";
		final String lastname = "LastName";

		createGreeting(GreetingStandardType.MRS);
		final boolean isMembership2 = true;
		createUser(
				partner,
				firstName2,
				lastname,
				greeting_MRS,
				isMembership2,
				2);


		final String firstName = "FirstName";


		final boolean isMembership = true;
		createUser(
				partner,
				firstName,
				lastname,
				greeting_MR,
				isMembership,
				1);



		bPartnerBL.updateNameAndGreetingFromContacts(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		refresh(partner);
		assertThat(partner.getName()).isEqualTo(firstName + " And " + firstName2 + " " + lastname);
		assertThat(partner.getC_Greeting_ID()).isEqualTo(greeting_MR_AND_MRS.getC_Greeting_ID());
	}

	@Test
	public void use2MembershipContacts_DifferentLastname()
	{
		final I_C_BP_Group group = createGroup(MembershipContactBPartnerNameAndGreetingStrategy.ID);
		final I_C_BPartner partner = createPartner(group);

		final String firstName = "FirstName";
		final String lastname = "LastName";

		final boolean isMembership = true;
		createUser(
				partner,
				firstName,
				lastname,
				greeting_MR,
				isMembership,
				-1);

		final String firstName2 = "FirstName2";
		final String lastname2 = "LastName2";

		createGreeting(GreetingStandardType.MRS);
		final boolean isMembership2 = true;
		createUser(
				partner,
				firstName2,
				lastname2,
				greeting_MRS,
				isMembership2,
				-1);

		bPartnerBL.updateNameAndGreetingFromContacts(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		refresh(partner);
		assertThat(partner.getName()).isEqualTo(firstName + " "
														+ lastname + " And " + firstName2 + " " + lastname2);
		assertThat(partner.getC_Greeting_ID()).isEqualTo(greeting_MR_AND_MRS.getC_Greeting_ID());
	}

	private I_C_BP_Group createGroup(
			@Nullable final BPartnerNameAndGreetingStrategyId strategyId)
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("Group");
		group.setBPNameAndGreetingStrategy(strategyId != null ? strategyId.getAsString() : null);

		save(group);

		return group;
	}

	private I_C_BPartner createPartner(final I_C_BP_Group group)
	{

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setC_BP_Group_ID(group.getC_BP_Group_ID());

		save(partner);
		return partner;
	}

	@SuppressWarnings("SameParameterValue")
	private void createUser(final I_C_BPartner partner,
			final String firstName,
			final String lastname,
			final I_C_Greeting greeting,
			final boolean isMembership,
			final int seqNo)
	{
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setC_BPartner_ID(partner.getC_BPartner_ID());
		user.setFirstname(firstName);
		user.setLastname(lastname);
		user.setIsMembershipContact(isMembership);
		user.setC_Greeting_ID(greeting.getC_Greeting_ID());
		user.setSeqNo(seqNo);

		save(user);

	}

	private I_C_Greeting createGreeting(@NonNull final GreetingStandardType standardType)
	{
		final I_C_Greeting greeting = newInstance(I_C_Greeting.class);

		greeting.setName(standardType.getCode());
		greeting.setGreeting(standardType.getCode());
		greeting.setGreetingStandardType(standardType.getCode());

		save(greeting);

		return greeting;
	}

}

