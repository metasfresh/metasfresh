package de.metas.banking.api;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import org.compiere.model.I_C_BP_BankAccount;

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

	Optional<BankAccount> getDefaultESRBankAccount(@NonNull BPartnerId bpartnerId);

	Optional<BankAccountId> getBankAccountId(@NonNull BankId bankId, @NonNull String accountNo);

	@NonNull Optional<BankAccountId> getBankAccountIdByIBAN(@NonNull String iban);

	<T extends I_C_BP_BankAccount> T  getById(@NonNull BankAccountId bankAccountId, @NonNull Class<T> modelClass);
}
