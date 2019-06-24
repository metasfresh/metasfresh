package de.metas.vertical.creditscore.creditpass.service;

import java.util.Optional;

import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MBPartner;
import org.springframework.stereotype.Service;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.service
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

import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerLocationInfo;
import de.metas.bpartner.service.BPartnerLocationInfoRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.Location;
import de.metas.location.LocationRepository;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import lombok.NonNull;

@Service
public class CreditPassTransactionDataFactory
{
	private final UserRepository userRepo;

	private final BPartnerLocationInfoRepository bPartnerLocationInfoRepository;

	private final LocationRepository locationRepository;

	public CreditPassTransactionDataFactory(@NonNull final UserRepository userRepo,
			@NonNull final BPartnerLocationInfoRepository bPartnerLocationRepository,
			@NonNull final LocationRepository locatinoRepository)
	{
		this.userRepo = userRepo;
		this.bPartnerLocationInfoRepository = bPartnerLocationRepository;
		this.locationRepository = locatinoRepository;
	}

	public CreditPassTransactionData collectTransactionData(@NonNull final BPartnerId bPartnerId)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final IBankingBPBankAccountDAO bpBankAccountRepo = Services.get(IBankingBPBankAccountDAO.class);

		int userId = MBPartner.getDefaultContactId(bPartnerId.getRepoId());
		User user = null;
		if (userId > 0)
		{
			user = userRepo.getByIdInTrx(UserId.ofRepoId(userId));
		}
		Optional<User> optionalUser = Optional.ofNullable(user);

		//TODO using bill location as default for now - will maybe change
		Location location = null;
		final BPartnerLocationId bPartnerLocationId = bpartnersRepo.getBilltoDefaultLocationIdByBpartnerId(bPartnerId);
		if (bPartnerLocationId != null)
		{
			BPartnerLocationInfo bPartnerLocation = bPartnerLocationInfoRepository.getByBPartnerLocationId(bPartnerLocationId);
			if (bPartnerLocation != null)
			{
				location = locationRepository.getByLocationId(bPartnerLocation.getLocationId());
			}
		}
		Optional<Location> optionalLocation = Optional.ofNullable(location);
		//TODO avoid using db models
		I_C_BP_BankAccount bankAccount = bpBankAccountRepo.retrieveDefaultBankAccount(bpartnersRepo.getByIdInTrx(bPartnerId));
		Optional<I_C_BP_BankAccount> optionalBankAccount = Optional.ofNullable(bankAccount);

		return CreditPassTransactionData.builder()
				.firstName(optionalUser.map(User::getFirstName).orElse(null))
				.lastName(optionalUser.map(User::getLastName).orElse(null))
				.dateOfBirth(optionalUser.map(User::getBirthday).orElse(null))
				.email(optionalUser.map(User::getEmailAddress).orElse(null))
				.phoneNr(optionalUser.map(User::getPhone).orElse(null))
				.streetFull(optionalLocation.map(Location::getStreetAddress).orElse(null))
				.zip(optionalLocation.map(Location::getPostal).orElse(null))
				.city(optionalLocation.map(Location::getCity).orElse(null))
				.country(optionalLocation.map(Location::getCountryCode).orElse(null))
				.bankRoutingCode(optionalBankAccount.map(I_C_BP_BankAccount::getRoutingNo).orElse(null))
				.accountNr(optionalBankAccount.map(I_C_BP_BankAccount::getAccountNo).orElse(null))
				.iban(optionalBankAccount.map(I_C_BP_BankAccount::getIBAN).orElse(null))
				.creditCardNr(optionalBankAccount.map(I_C_BP_BankAccount::getCreditCardNumber).orElse(null))
				.creditCardType(optionalBankAccount.map(I_C_BP_BankAccount::getCreditCardType).orElse(null))
				.companyName(optionalBankAccount.map(I_C_BP_BankAccount::getA_Name).orElse(null))
				.build();
	}

}
