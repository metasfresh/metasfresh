package de.metas.acct.tax;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_ValidCombination;

import de.metas.acct.api.AcctSchemaId;
import de.metas.currency.CurrencyPrecision;

/**
 * Accountable tax record.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08351_Automatikibuchung_Steuer_in_Hauptbuchjournal_%28106598648165%29
 */
public interface ITaxAccountable
{
	/** @return true if tax account is on Debit */
	boolean isAccountSignDR();

	/** @return true if tax account is on Credit */
	boolean isAccountSignCR();

	AcctSchemaId getAcctSchemaId();

	CurrencyPrecision getPrecision();

	I_C_ValidCombination getTaxTotal_Acct();

	I_C_ValidCombination getTaxBase_Acct();

	void setTaxTotalAmt(final BigDecimal totalAmt);

	BigDecimal getTaxTotalAmt();

	void setTaxBaseAmt(final BigDecimal taxBaseAmt);

	BigDecimal getTaxBaseAmt();

	void setTaxAmt(final BigDecimal taxAmt);

	BigDecimal getTaxAmt();

	void setTax_Acct(final I_C_ValidCombination taxAcct);

	I_C_ValidCombination getTax_Acct();

	void setC_Tax(final I_C_Tax tax);

	int getC_Tax_ID();

	I_C_Tax getC_Tax();
}
