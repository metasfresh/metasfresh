/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.money.CurrencyId;
import de.metas.rest_api.utils.MetasfreshId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;

@ToString
public class ShortTermBankAccountIndex
{
	@Getter
	private final BPartnerComposite bpartnerComposite;

	private final HashMap<BPartnerBankAccountId, BPartnerBankAccount> bankAccountsById = new HashMap<>();
	private final HashMap<String, BPartnerBankAccount> bankAccountsByIBAN = new HashMap<>();

	public ShortTermBankAccountIndex(@NonNull final BPartnerComposite bpartnerComposite)
	{
		this.bpartnerComposite = bpartnerComposite;

		for (final BPartnerBankAccount bankAccount : bpartnerComposite.getBankAccounts())
		{
			bankAccountsById.put(bankAccount.getId(), bankAccount);
			bankAccountsByIBAN.put(bankAccount.getIban(), bankAccount);
		}
	}

	public Collection<BPartnerBankAccount> getRemainingBankAccounts()
	{
		return bankAccountsById.values();
	}

	public void remove(@NonNull final BPartnerBankAccountId bpartnerBankAccountId)
	{
		bankAccountsById.remove(bpartnerBankAccountId);
	}

	public BPartnerBankAccount extract(@NonNull final MetasfreshId metasfreshId)
	{
		if (bpartnerComposite.getBpartner().getId() == null)
		{
			throw new AdempiereException("Bank account exists, but enclosing bpartnerComposite does not exist in metasfresh yet!")
					.appendParametersToMessage()
					.setParameter("BPBankAccountId", metasfreshId.getValue())
					.setParameter("bpartnerComposite.bpartner.value", bpartnerComposite.getBpartner().getValue());
		}

		final BPartnerBankAccountId bPartnerBankAccountId = BPartnerBankAccountId.ofRepoId(bpartnerComposite.getBpartner().getId(),
																						   metasfreshId.getValue());

		final BPartnerBankAccount bankAccount = bankAccountsById.get(bPartnerBankAccountId);

		if (bankAccount == null)
		{
			throw new AdempiereException("No BPartnerBankAccount found for current BPartner-ID/BankAccount-ID combo!")
					.appendParametersToMessage()
					.setParameter("BPartnerBankAccountId", bPartnerBankAccountId);
		}

		return bankAccount;
	}

	@Nullable
	public BPartnerBankAccount extractOrNull(@NonNull final String iban)
	{
		if (bpartnerComposite.getBpartner().getId() == null)
		{
			return null;
		}

		return bankAccountsByIBAN.get(iban);
	}

	public BPartnerBankAccount newBankAccount(
			@NonNull final String iban,
			@NonNull final CurrencyId currencyId)
	{
		final BPartnerBankAccount bankAccount = BPartnerBankAccount.builder()
				.iban(iban)
				.currencyId(currencyId)
				.build();

		bpartnerComposite.addBankAccount(bankAccount);

		return bankAccount;
	}

}
