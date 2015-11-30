package de.metas.materialtracking.qualityBasedInvoicing;

/*
 * #%L
 * de.metas.materialtracking
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


import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Contains vendor invoicing informations.
 *
 * Based on these informations, the actual invoicing will perform.
 *
 * @author tsa
 *
 */
public interface IVendorInvoicingInfo
{
	/**
	 * @return vendor billing bpartner
	 */
	int getBill_BPartner_ID();

	/**
	 * @return vendor billing bpartner location
	 */
	int getBill_Location_ID();

	/**
	 * @return vendor billing bpartner contact
	 */
	int getBill_User_ID();

	/**
	 * @return vendor currency
	 */
	int getC_Currency_ID();

	/**
	 * @return vendor pricing system
	 */
	int getM_PricingSystem_ID();

	I_M_PricingSystem getM_PricingSystem();

	I_M_PriceList_Version getM_PriceList_Version();

	/**
	 * @return vendor contract; never return null
	 */
	I_C_Flatrate_Term getC_Flatrate_Term();

	/**
	 * @return invoicing rule
	 */
	String getInvoiceRule();

	/**
	 * Add another invoice candidate.
	 * <p>
	 * <b>IMPORTANT: </b> No consistency check! only the first added invoiceCandidate will be considered!! TODO: fix that
	 *
	 * @param invoiceCandidate
	 */
	void add(I_C_Invoice_Candidate invoiceCandidate);

}
