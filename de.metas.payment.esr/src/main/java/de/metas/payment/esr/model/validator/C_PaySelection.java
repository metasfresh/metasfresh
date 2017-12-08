package de.metas.payment.esr.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.StringJoiner;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Interceptor(I_C_PaySelection.class)
public class C_PaySelection
{
	private static final String MSG_PaySelectionLines_No_ESRReference = "C_PaySelection_PaySelectionLines_No_ESRReference";

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void verifyESRReferences(final I_C_PaySelection paySelection)

	{
		// first make sure the lines have C_BP_BankAccount values set
		Services.get(IPaySelectionBL.class).validateBankAccounts(paySelection);

		StringJoiner joiner = new StringJoiner(",");

		for (final I_C_PaySelectionLine paySelectionLine : Services.get(IPaySelectionDAO.class).retrievePaySelectionLines(paySelection, I_C_PaySelectionLine.class))
		{
			if (hasESRBankAccount(paySelectionLine) && (!isValidESRReference(paySelectionLine)))
			{
				joiner.add(String.valueOf(paySelectionLine.getLine()));
			}
		}

		if (joiner.length() != 0)
		{
			throw new AdempiereException(MSG_PaySelectionLines_No_ESRReference, new Object[] { joiner.toString() });
		}

	}

	private boolean isValidESRReference(final I_C_PaySelectionLine paySelectionLine)
	{
		String reference = paySelectionLine.getReference();

		if (reference == null)
		{
			return false;
		}

		reference = reference.replaceAll("\\s+", "");

		return reference.length() == 27;

	}

	private boolean hasESRBankAccount(final I_C_PaySelectionLine paySelectionLine)
	{
		final org.compiere.model.I_C_BP_BankAccount bpBankAccount = paySelectionLine.getC_BP_BankAccount();

		Check.assumeNotNull(bpBankAccount, "The paySelectionLine {} cannot have a null bankAccount", paySelectionLine);

		final de.metas.payment.esr.model.I_C_BP_BankAccount esrBankAccount = create(bpBankAccount, de.metas.payment.esr.model.I_C_BP_BankAccount.class);

		return esrBankAccount.isEsrAccount();

	}
}
