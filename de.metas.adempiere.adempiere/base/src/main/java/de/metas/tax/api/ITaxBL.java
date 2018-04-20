package de.metas.tax.api;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.TaxNoExemptFoundException;
import org.adempiere.exceptions.TaxNotFoundException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Warehouse;

public interface ITaxBL extends ISingletonService
{
	/**
	 * Try to retrieve tax by {@link #retrieveTaxIdForCategory(Properties, int, int, org.compiere.model.I_C_BPartner_Location, Timestamp, int, boolean, String, boolean)} first.<br>
	 * If that doesn't work, try retrieving the German tax
	 *
	 * @param ctx
	 * @param model
	 * @param taxCategoryId
	 * @param productId
	 * @param chargeId
	 * @param billDate
	 * @param shipDate
	 * @param adOrgId
	 * @param warehouse
	 * @param billC_BPartner_Location_ID
	 * @param shipC_BPartner_Location_ID
	 * @param isSOTrx
	 * @param trxName
	 * @return taxId
	 */
	int getTax(Properties ctx,
			Object model,
			int taxCategoryId,
			int productId,
			int chargeId,
			Timestamp billDate,
			Timestamp shipDate,
			int adOrgId,
			I_M_Warehouse warehouse,
			int billC_BPartner_Location_ID,
			int shipC_BPartner_Location_ID,
			boolean isSOTrx,
			String trxName);

	/**
	 * Retrieve <code>taxId<code> from the given <code>taxCategoryId</code>
	 *
	 * @param ctx
	 * @param countryFromId
	 * @param orgId
	 * @param bpLocTo
	 * @param billDate
	 * @param taxCategoryId
	 * @param isSOTrx
	 * @param trxName
	 * @param throwEx if <code>true</code>, and no <code>C_Tax</code> record can be found, then throw an exception that contains the failed query. <br>
	 * 			Otherwise, just log and return <code>-1</code>.
	 * @return taxId
	 */
	int retrieveTaxIdForCategory(Properties ctx,
			int countryFromId,
			int orgId,
			org.compiere.model.I_C_BPartner_Location bpLocTo,
			Timestamp billDate,
			int taxCategoryId,
			boolean isSOTrx,
			String trxName,
			boolean throwEx);

	/**
	 * Calculate Tax - no rounding
	 *
	 * @param tax
	 * @param amount amount
	 * @param taxIncluded if true tax is calculated from gross otherwise from net
	 * @param scale scale
	 * @return tax amount
	 */
	BigDecimal calculateTax(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * Calculate base amount, excluding tax
	 *
	 * @param tax
	 * @param amount
	 * @param taxIncluded
	 * @param scale
	 * @return
	 */
	BigDecimal calculateBaseAmt(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * Get Tax ID - converts parameters to call Get Tax.
	 *
	 * <pre>
	 * 	M_Product_ID/C_Charge_ID	->	C_TaxCategory_ID
	 * 	billDate, shipDate			->	billDate, shipDate
	 * 	AD_Org_ID					->	billFromC_Location_ID
	 * 	M_Warehouse_ID				->	shipFromC_Location_ID
	 * 	billC_BPartner_Location_ID  ->	billToC_Location_ID
	 * 	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 * </pre>
	 *
	 * @param ctx context
	 * @param M_Product_ID product
	 * @param C_Charge_ID product
	 * @param billDate invoice date
	 * @param shipDate ship date (ignored)
	 * @param AD_Org_ID org
	 * @param M_Warehouse_ID warehouse (ignored)
	 * @param billC_BPartner_Location_ID invoice location
	 * @param shipC_BPartner_Location_ID ship location (ignored)
	 * @param IsSOTrx is a sales trx
	 * @return C_Tax_ID
	 * @throws TaxNotFoundException
	 */
	int get(Properties ctx, int M_Product_ID, int C_Charge_ID,
			Timestamp billDate, Timestamp shipDate,
			int AD_Org_ID, int M_Warehouse_ID,
			int billC_BPartner_Location_ID, int shipC_BPartner_Location_ID,
			boolean IsSOTrx);

	/**
	 * Get Exempt Tax Code
	 *
	 * @param ctx context
	 * @param AD_Org_ID org to find client
	 * @return C_Tax_ID
	 * @throws TaxNoExemptFoundException if no tax exempt found
	 */
	int getExemptTax(Properties ctx, int AD_Org_ID);

	/**
	 * Sets the correct flags if given tax has {@link I_C_Tax#isWholeTax()} set.
	 *
	 * @param tax
	 */
	void setupIfIsWholeTax(final I_C_Tax tax);

	int retrieveDefaultTaxCategoryId();
}
