package de.metas.vertical.creditscore.creditpass.service;

import de.metas.banking.Bank;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.Location;
import de.metas.location.LocationRepository;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import lombok.NonNull;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MBPartner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditPassTransactionDataFactory
{
	private final UserRepository userRepo;
	private final LocationRepository locationRepository;
	private final BankRepository bankRepository;

	public CreditPassTransactionDataFactory(
			@NonNull final UserRepository userRepo,
			@NonNull final LocationRepository locatinoRepository,
			@NonNull final BankRepository bankRepository)
	{
		this.userRepo = userRepo;
		this.locationRepository = locatinoRepository;
		this.bankRepository = bankRepository;
	}

	public CreditPassTransactionData collectTransactionData(@NonNull final BPartnerId bPartnerId)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final IBPBankAccountDAO bankAccountRepo = Services.get(IBPBankAccountDAO.class);

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
			BPartnerLocationAndCaptureId bPartnerLocation = bpartnersRepo.getBPartnerLocationAndCaptureIdInTrx(bPartnerLocationId);
			if (bPartnerLocation != null)
			{
				location = locationRepository.getByLocationId(bPartnerLocation.getLocationCaptureId());
			}
		}
		Optional<Location> optionalLocation = Optional.ofNullable(location);
		//TODO avoid using db models
		Optional<I_C_BP_BankAccount> optionalBankAccount = bankAccountRepo.retrieveDefaultBankAccountInTrx(bPartnerId);

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
				.bankRoutingCode(optionalBankAccount.map(this::getRoutingNo).orElse(null))
				.accountNr(optionalBankAccount.map(I_C_BP_BankAccount::getAccountNo).orElse(null))
				.iban(optionalBankAccount.map(I_C_BP_BankAccount::getIBAN).orElse(null))
				.creditCardNr(optionalBankAccount.map(I_C_BP_BankAccount::getCreditCardNumber).orElse(null))
				.creditCardType(optionalBankAccount.map(I_C_BP_BankAccount::getCreditCardType).orElse(null))
				.companyName(optionalBankAccount.map(I_C_BP_BankAccount::getA_Name).orElse(null))
				.build();
	}

	private String getRoutingNo(final I_C_BP_BankAccount bpartnerBankAccount)
	{
		final BankId bankId = BankId.ofRepoIdOrNull(bpartnerBankAccount.getC_Bank_ID());
		if(bankId != null)
		{
			final Bank bank = bankRepository.getById(bankId);
			return bank.getRoutingNo();
		}
		
		return bpartnerBankAccount.getRoutingNo();
	}

}
