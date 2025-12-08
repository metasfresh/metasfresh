package de.metas.banking.accounting;

import de.metas.acct.Account;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.BankAccountId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Own (organization) bank account - accounting settings
 */
@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BankAccountAcct
{
	@NonNull
	BankAccountId bankAccountId;

	@NonNull AcctSchemaId acctSchemaId;

	@NonNull Account B_Asset_Acct;
	@NonNull Account B_UnallocatedCash_Acct;
	@NonNull Account B_InTransit_Acct;
	@NonNull Account B_PaymentSelect_Acct;
	@NonNull Account B_InterestRev_Acct;
	@NonNull Account B_InterestExp_Acct;
	@NonNull Account PayBankFee_Acct;
	@NonNull Account RealizedGain_Acct;
	@NonNull Account RealizedLoss_Acct;

	@NonNull Optional<Account> Payment_WriteOff_Acct;

	@NonNull
	public Optional<Account> getAccount(@NonNull final BankAccountAcctType acctType)
	{
		switch (acctType)
		{
			//
			// Account Type - Payment
			case B_UnallocatedCash_Acct:
				return Optional.of(B_UnallocatedCash_Acct);
			case B_InTransit_Acct:
				return Optional.of(B_InTransit_Acct);
			case B_PaymentSelect_Acct:
				return Optional.of(B_PaymentSelect_Acct);
			case PayBankFee_Acct:
				return Optional.of(PayBankFee_Acct);

			//
			// Account Type - Bank Statement
			case B_Asset_Acct:
				return Optional.of(B_Asset_Acct);
			case B_InterestRev_Acct:
				return Optional.of(B_InterestRev_Acct);
			case B_InterestExp_Acct:
				return Optional.of(B_InterestExp_Acct);
			case RealizedGain_Acct:
				return Optional.of(RealizedGain_Acct);
			case RealizedLoss_Acct:
				return Optional.of(RealizedLoss_Acct);
			case Payment_WriteOff_Acct:
				return Payment_WriteOff_Acct;

			//
			default:
				throw new AdempiereException("Unknown account type: " + acctType);
		}
	}

}
