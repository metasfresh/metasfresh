package de.metas.payment.sepa.api;

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

	List<I_SEPA_Export_Line> retrieveLines(I_SEPA_Export doc);

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
