/**
 * 
 */
package de.metas.payment.sepa.model.validator;

/*
 * #%L
 * de.metas.payment.sepa
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;

import de.metas.payment.sepa.api.IIBANValidationBL;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author cg
 *
 */

@Validator(I_C_BP_BankAccount.class)
public class C_BP_BankAccount
{
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_BP_BankAccount.COLUMNNAME_IBAN }
			)
			public void validateIBAN(final I_C_BP_BankAccount bp_bankAccount)
	{
		String ibanCode = bp_bankAccount.getIBAN();
		if (!Check.isEmpty(ibanCode, true))
		{
			// remove empty spaces
			ibanCode = ibanCode.replaceAll("\\s", ""); // remove spaces
			
			// validate IBAN
			final boolean isValidateIBAN = Services.get(ISysConfigBL.class).getBooleanValue("C_BP_BankAccount.validateIBAN", false);

			if (isValidateIBAN)
			{
				Services.get(IIBANValidationBL.class).validate(ibanCode);
			}
		}

	}

}
