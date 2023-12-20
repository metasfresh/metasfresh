package de.metas.banking;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
@Builder(toBuilder = true)
public class BankAccount
{
	@NonNull
	BankAccountId id;

	@NonNull
	BPartnerId bPartnerId;

	@Nullable // C_BP_BankAccount.C_Bank_ID is not mandatory!
	BankId bankId;

	@Nullable
	String accountName;

	@Nullable
	String name;

	@Nullable
	String accountNo;

	@Nullable
	String esrRenderedAccountNo;

	@Nullable
	String IBAN;

	@Nullable
	String SwiftCode;

	@Nullable
	String QR_IBAN;

	@Nullable
	String SEPA_CreditorIdentifier;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	OrgId orgId;

	@Nullable
	String routingNo;

	public boolean isAccountNoMatching(@NonNull final String accountNo)
	{
		final String QR_IBAN = StringUtils.trimBlankToNull(getQR_IBAN());
		final String IBAN = StringUtils.trimBlankToNull(getIBAN());
		final String SEPA_CreditorIdentifier = StringUtils.trimBlankToNull(getSEPA_CreditorIdentifier());

		final int indexOfSlash = StringUtils.findIndexOf(accountNo, "/");

		final String accountNoWithoutTrailingSlash = indexOfSlash >= 0
				? accountNo.substring(0, indexOfSlash)
				: accountNo;

		final String postAcctNoCleaned = StringUtils.cleanWhitespace(accountNoWithoutTrailingSlash);

		return postAcctNoCleaned.equals(QR_IBAN)
				|| postAcctNoCleaned.equals(IBAN)
				|| postAcctNoCleaned.equals(SEPA_CreditorIdentifier);

	}
}
