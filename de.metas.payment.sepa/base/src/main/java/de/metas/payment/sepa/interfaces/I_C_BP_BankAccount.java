package de.metas.payment.sepa.interfaces;

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


public interface I_C_BP_BankAccount extends de.metas.banking.model.I_C_BP_BankAccount
{
	// @formatter:off
	String COLUMNNAME_SEPA_CREDITORIDENTIFIER = "SEPA_CreditorIdentifier";
	String getSEPA_CreditorIdentifier();
	void setSEPA_CreditorIdentifier(String SEPA_CreditorIdentifier);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_SwiftCode = "SwiftCode";
	void setSwiftCode(String SwiftCode);
	String getSwiftCode();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IBAN = "IBAN";
	@Override
	String getIBAN();
	@Override
	void setIBAN(String IBAN);
	// @formatter:on

	// 04288: column isDefaultESR
	// @formatter:off
	String COLUMNNAME_IsDefaultSEPA = "IsDefaultSEPA";
	boolean isDefaultSEPA();
	void setIsDefaultSEPA(boolean isDefaultSEPA);
	// @formatter:on

}
