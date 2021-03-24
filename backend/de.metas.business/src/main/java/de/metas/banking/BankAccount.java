package de.metas.banking;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

/**
 * Own (organization) bank account.
 */
@Value
@Builder
public class BankAccount
{
	@NonNull
	BankAccountId id;

	@NonNull
	BankId bankId;

	@Nullable
	String accountName;

	@Nullable
	String accountNo;

	@Nullable
	String esrRenderedAccountNo;
	
	@Nullable
	String IBAN;
	
	@Nullable
	String QR_IBAN;

	@Nullable
	String SEPA_CreditorIdentifier;
	
	@NonNull
	CurrencyId currencyId;

	@NonNull
	OrgId orgId;
	
	
	public boolean isAccountNoMatching(@NonNull final String accountNo) 
	{
		final String QR_IBAN = StringUtils.cleanWhitespace(getQR_IBAN());
		final String IBAN = StringUtils.cleanWhitespace(getIBAN());
		final String SEPA_CreditorIdentifier = StringUtils.cleanWhitespace(getSEPA_CreditorIdentifier());
		
		final String postAcctNoCleaned = StringUtils.cleanWhitespace(accountNo);
		
		return postAcctNoCleaned.equals(QR_IBAN) 
				|| postAcctNoCleaned.equals(IBAN)
				|| postAcctNoCleaned.equals(SEPA_CreditorIdentifier);
		
	}
}
