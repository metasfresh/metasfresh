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

import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.payment.sepa.api.IIBANValidationBL;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.ModelValidator;

/**
 * @author cg
 */

@Validator(I_C_BP_BankAccount.class)
public class C_BP_BankAccount
{

	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

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
				try
				{
					Services.get(IIBANValidationBL.class).validate(ibanCode);
				}
				catch (final Exception e)
				{
					throw AdempiereException.wrapIfNeeded(e).markAsUserValidationError();
				}
			}
		}

	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_BP_BankAccount.COLUMNNAME_A_Country_ID }
	)
	public void setA_Country(final I_C_BP_BankAccount bp_bankAccount)
	{
		final int countryId = bp_bankAccount.getA_Country_ID();
		if (countryId > 0)
		{
			final String countryCode = countryDAO.getCountryCode(CountryId.ofRepoId(countryId));
			bp_bankAccount.setA_Country(countryCode);
		}
		else
		{
			bp_bankAccount.setA_Country(null);
		}
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_BP_BankAccount.COLUMNNAME_A_Country }
	)
	public void validateA_Country(final I_C_BP_BankAccount bp_bankAccount)
	{
		final String A_Country = bp_bankAccount.getA_Country();
		if (Check.isNotBlank(A_Country))
		{
			final CountryId countryId = countryDAO.getCountryIdByCountryCodeOrNull(A_Country);
			if (countryId == null)
			{
				throw new AdempiereException("Country code " + A_Country + " not found");
			}
			else
			{
				bp_bankAccount.setA_Country_ID(countryId.getRepoId());
			}
		}

	}
}
