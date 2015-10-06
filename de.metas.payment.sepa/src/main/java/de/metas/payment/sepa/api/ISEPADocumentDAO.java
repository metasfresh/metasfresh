package de.metas.payment.sepa.api;

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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;

import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;

public interface ISEPADocumentDAO extends ISingletonService
{

	/**
	 * Returns a BP bank account the same way we do in a letter: ORDER BY IsDefault DESC, LIMIT 1
	 * 
	 * @param i_C_BPartner
	 * @return bPartner bank account for use in SEPA XML
	 */
	I_C_BP_BankAccount retrieveSEPABankAccount(I_C_BPartner i_C_BPartner);

	/**
	 * Retrieve all lines for give <code>doc</code>
	 * 
	 * @param doc
	 * @return lines
	 */
	Iterator<I_SEPA_Export_Line> retrieveLines(I_SEPA_Export doc);

	/**
	 * Vs82 05761
	 * Retrieve all SEPA export lines with IsDebitTrxFailed = 'Y' and action Partner-Zahlungsweise zu "auf Rechnung" ändern (change payment rule to from direct debit to invoice) 
	 * 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	List<I_SEPA_Export_Line> retrieveLinesChangeRule(Properties ctx, String trxName);
}
