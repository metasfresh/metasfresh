/**
 * 
 */
package de.metas.purchasing.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.interfaces.I_C_BPartner_Product;

/**
 * @author cg
 * 
 */
public interface IBPartnerProductDAO extends ISingletonService
{

	public static final String MSG_ProductSalesBanError = "ProductSalesBanError";
	
	/**
	 * Retrieves all C_BPartner_Products for selected vendor
	 * 
	 * @param ctx
	 * @param Vendor_ID C_BPartner_ID
	 * @param filter
	 * @return C_BPartner_Products for Vendor_ID
	 */
	List<I_C_BPartner_Product> retrieveBPartnerForProduct(Properties ctx, int Vendor_ID, int productId, int orgId);

	/**
	 * Retrieves single {@link I_C_BPartner_Product} association for the given product and partner. THe association must have the given ad_Org_ID or ad_org_id = 0
	 * 
	 * @param partner
	 * @param product
	 * @param organization
	 * @return the BPartner-Product association or null
	 */
	I_C_BPartner_Product retrieveBPartnerProductAssociation(I_C_BPartner partner, I_M_Product product, final int orgId);

	/**
	 * Retrieves single {@link I_C_BPartner_Product} association. If there isn't an association for the given org, check if there isn't one for the org *
	 * 
	 * @param ctx
	 * @param bpartnerId
	 * @param productId
	 * @param orgId
	 * @return the BPartner-Product association per org or null
	 */
	I_C_BPartner_Product retrieveBPartnerProductAssociation(Properties ctx, int bpartnerId, int productId, int orgId);

	/**
	 * Retrieves the BP Product entry either if it is used for customer and has the BP = customerPartner (and has a bp vendor set)
	 * of is the currentVendor. The BP Product must be of the given org or of the org 0
	 * 
	 * @param customerPartner
	 * @param product
	 * @param org
	 * @return first entry, order by BP vendor and org_ID, nulls last
	 */
	I_C_BPartner_Product retrieveBPProductForCustomer(I_C_BPartner customerPartner, I_M_Product product, int orgId);

	List<I_C_BPartner_Product> retrieveAllVendors(int productId, int orgId);

	List<ProductExclude> retrieveAllProductSalesExcludes();

	Optional<ProductExclude> getExcludedFromSaleToCustomer(int productId, int partnerId);
}
