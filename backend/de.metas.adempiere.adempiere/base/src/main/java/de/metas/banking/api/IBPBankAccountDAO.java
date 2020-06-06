package de.metas.banking.api;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.compiere.model.I_C_BP_BankAccount;

import com.google.common.collect.ImmutableListMultimap;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

/**
 * TODO This interface must be moved in de.metas.banking but there still are some dependencies which need be fixed
 */
public interface IBPBankAccountDAO extends ISingletonService
{
	I_C_BP_BankAccount getById(final BankAccountId bpBankAccountId);

	<T extends I_C_BP_BankAccount> T getById(final BankAccountId bpBankAccountId, Class<T> modelType);

	/**
	 * Retrieve all the bank accounts of the currency <code>currencyID</code> for the partner <code> partnerID</code>
	 * In case the currencyID is not set (<=0) just retrieve all accounts of the bpartner
	 * The bank accounts will be ordered by their IsDefault values, with true first.
	 */
	List<I_C_BP_BankAccount> retrieveBankAccountsForPartnerAndCurrency(Properties ctx, int partnerID, int currencyID);

	Optional<BankAccountId> retrieveByBPartnerAndCurrencyAndIBAN(@NonNull BPartnerId bPartnerId, @NonNull CurrencyId currencyId, @NonNull String iban);

	/**
	 * Deactivate all {@link I_C_BP_BankAccount} records for the given bPartnerId, besides
	 * <li>
	 *     <ul>the ones whose id is in the given {@code exceptIds}</ul>
	 *     <ul>the ones that have no IBAN; why: this is used for persisting {@code BPartnerComposite}s which never have no-iban-backaccounts; so we need to prevent them from being deactivated.</ul>
	 * </li>
	 */
	void deactivateIBANAccountsByBPartnerExcept(BPartnerId bpartnerId, Collection<BPartnerBankAccountId> exceptIds);

	ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> getAllByBPartnerIds(Collection<BPartnerId> bpartnerIds);
}
