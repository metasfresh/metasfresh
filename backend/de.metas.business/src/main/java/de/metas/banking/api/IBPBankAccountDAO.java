package de.metas.banking.api;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IBPBankAccountDAO extends de.metas.bpartner.service.IBPBankAccountDAO
{
	BankAccount getById(final BankAccountId bankAccountId);

	Optional<BankAccountId> retrieveByBPartnerAndCurrencyAndIBAN(
			@NonNull BPartnerId bpartnerId,
			@NonNull CurrencyId currencyId,
			@NonNull String iban);

	@Nullable
	BankId getBankId(@NonNull BankAccountId bankAccountId);

	Optional<BankAccount> getDefaultBankAccount(BPartnerId bPartnerId);

}
