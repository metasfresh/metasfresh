package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Predicates;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.user.User;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class BPartnerBL_RetrieveContactOrNullTests
{
	private BPartnerId bpartnerId;
	private BPartnerLocationId bpartnerLocationId;
	private BPartnerBL bPartnerBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_BPartner bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);
		bpartnerId = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

		final I_C_BPartner_Location bPartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner(bPartnerRecord);
		saveRecord(bPartnerLocationRecord);
		bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bPartnerLocationRecord.getC_BPartner_Location_ID());

		bPartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bPartnerBL);
	}

	@Test
	public void retrieveBillContacts_no_users()
	{
		final User result = invokeMethodUnderTest();
		assertThat(result).isNull();
	}

	@Test
	public void retrieveBillContacts_no_users_that_match_filter()
	{
		userRecordCreator()
				.name("A")
				.createAndSave();

		final Predicate<User> filter = u -> !u.getName().equals("A");
		final User result = invokeMethodUnderTestWithPredicate(filter);

		assertThat(result).isNull();
	}

	@Test
	public void retrieveBillContacts_prefer_BillToContact_Default()
	{
		userRecordCreator()
				.name("A")
				.createAndSave();

		final I_AD_User userBillToContactRecord = userRecordCreator()
				.name("B")
				.billToDefaultContact(true)
				.createAndSave()
				.getUserRecord();

		final User result = invokeMethodUnderTest();

		assertThat(result.getId().getRepoId()).isEqualTo(userBillToContactRecord.getAD_User_ID());
	}

	@Test
	public void retrieveBillContacts_prefer_contact_at_bpartnerLocation()
	{
		userRecordCreator()
				.name("A")
				.bpartnerLocationId(bpartnerLocationId)
				.createAndSave();

		userRecordCreator()
				.name("B")
				.billToDefaultContact(true)
				.createAndSave();

		userRecordCreator()
				.name("C")
				.createAndSave();

		final User result = invokeMethodUnderTest();

		assertThat(result.getName()).isEqualTo("A");
	}

	private User invokeMethodUnderTest()
	{
		return invokeMethodUnderTestWithPredicate(Predicates.alwaysTrue());
	}

	private User invokeMethodUnderTestWithPredicate(@NonNull final Predicate<User> predicate)
	{
		final RetrieveContactRequest request = RetrieveContactRequest
				.builder()
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(bpartnerLocationId)
				.contactType(ContactType.BILL_TO_DEFAULT)
				.filter(predicate)
				.build();
		final User result = bPartnerBL.retrieveContactOrNull(request);
		return result;
	}

	/**
	 * If there are multiple users at the given location and one of them has {@code IsBillToContact_Default == true}, then prefer that one.
	 */
	@Test
	public void retrieveBillContacts_prefer_billToContact_at_bpartnerLocation_2()
	{
		userRecordCreator()
				.name("A")
				.bpartnerLocationId(bpartnerLocationId)
				.createAndSave();

		userRecordCreator()
				.name("B")
				.bpartnerLocationId(bpartnerLocationId)
				.defaultContact(true)
				.createAndSave();

		userRecordCreator()
				.name("C")
				.bpartnerLocationId(bpartnerLocationId)
				.billToDefaultContact(true)
				.createAndSave()
				.getUserRecord();

		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("D");
		userRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		saveRecord(userRecord);

		final User result = invokeMethodUnderTest();

		assertThat(result.getName()).isEqualTo("C");
	}

	private de.metas.bpartner.service.impl.BPartnerBL_RetrieveContactOrNullTests.UserRecordCreator.UserRecordCreatorBuilder userRecordCreator()
	{
		final de.metas.bpartner.service.impl.BPartnerBL_RetrieveContactOrNullTests.UserRecordCreator.UserRecordCreatorBuilder builder = UserRecordCreator
				.builder()
				.bpartnerId(bpartnerId);
		return builder;
	}

	@Value
	private static class UserRecordCreator
	{
		I_AD_User userRecord;

		@Builder(buildMethodName = "createAndSave")
		private UserRecordCreator(
				@NonNull BPartnerId bpartnerId,
				@Nullable BPartnerLocationId bpartnerLocationId,
				@NonNull String name,
				boolean defaultContact,
				boolean billToDefaultContact)
		{
			userRecord = newInstance(I_AD_User.class);
			userRecord.setC_BPartner_ID(bpartnerId.getRepoId());
			if (bpartnerLocationId != null)
			{
				userRecord.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
			}
			userRecord.setName(name);
			userRecord.setIsDefaultContact(defaultContact);
			userRecord.setIsBillToContact_Default(billToDefaultContact);
			saveRecord(userRecord);
		}

	}
}
