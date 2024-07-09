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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.location.DocumentLocation;
import de.metas.location.CountryId;
import de.metas.pricing.PricingSystemId;
import org.compiere.model.I_M_PriceList_Version;

/**
 * Contains vendor invoicing informations.
 * <p>
 * Based on these informations, the actual invoicing will perform.
 *
 * @author tsa
 */
public interface IVendorInvoicingInfo
{
	/**
	 * @return vendor billing bpartner
	 */
	BPartnerId getBill_BPartner_ID();

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

	CountryId getCountryId();
	
	I_M_PriceList_Version getM_PriceList_Version();

	/**
	 * @return vendor pricing system
	 */
	PricingSystemId getPricingSystemId();

	/**
	 * @return vendor contract; never return null
	 */
	I_C_Flatrate_Term getC_Flatrate_Term();

	/**
	 * @return invoicing rule
	 */
	String getInvoiceRule();

	default DocumentLocation getBillLocation()
	{
		final BPartnerId billBPartnerId = getBill_BPartner_ID();
		return DocumentLocation.builder()
				.bpartnerId(billBPartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(billBPartnerId, getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(billBPartnerId, getBill_User_ID()))
				.build();
	}
}
