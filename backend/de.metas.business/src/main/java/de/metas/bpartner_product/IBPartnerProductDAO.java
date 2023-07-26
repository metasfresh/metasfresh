/**
 *
 */
package de.metas.bpartner_product;

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
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IBPartnerProductDAO extends ISingletonService
{
	/**
	 * Retrieves all C_BPartner_Products for selected vendor
	 *
	 * @param Vendor_ID C_BPartner_ID
	 * @return C_BPartner_Products for Vendor_ID
	 */
	List<I_C_BPartner_Product> retrieveBPartnerForProduct(Properties ctx, BPartnerId Vendor_ID, ProductId productId, OrgId orgId);

	/**
	 * Retrieves single {@link I_C_BPartner_Product} association for the given product and partner. THe association must have the given ad_Org_ID or ad_org_id = 0
	 *
	 * @return the BPartner-Product association or null
	 */
	I_C_BPartner_Product retrieveBPartnerProductAssociation(I_C_BPartner partner, I_M_Product product, final OrgId orgId);

	/**
	 * Retrieves single {@link I_C_BPartner_Product} association. If there isn't an association for the given org, check if there isn't one for the org *
	 *
	 * @return the BPartner-Product association per org or null
	 */
	I_C_BPartner_Product retrieveBPartnerProductAssociation(Properties ctx, BPartnerId bpartnerId, ProductId productId, OrgId orgId);

	/**
	 * Retrieves the BP Product entry either if it is used for customer and has the BP = customerPartner (and has a bp vendor set)
	 * of is the currentVendor. The BP Product must be of the given org or of the org 0
	 *
	 * @return first entry, order by BP vendor and org_ID, nulls last
	 */
	I_C_BPartner_Product retrieveBPProductForCustomer(I_C_BPartner customerPartner, I_M_Product product, OrgId orgId);

	List<I_C_BPartner_Product> retrieveForProductIds(Set<ProductId> productIds);

	List<ProductExclude> retrieveAllProductSalesExcludes();

	Optional<ProductExclude> getExcludedFromSaleToCustomer(ProductId productId, BPartnerId partnerId);

	Map<BPartnerId, I_C_BPartner_Product> retrieveByVendorIds(Set<BPartnerId> vendorIds, ProductId productId, OrgId orgId);

	I_C_BPartner_Product retrieveByVendorId(BPartnerId vendorId, ProductId productId, OrgId orgId);

	List<I_C_BPartner_Product> retrieveAllBPartnerProductAssociations(Properties ctx, BPartnerId bpartnerId, ProductId productId, OrgId orgId, String trxName);

	Optional<ProductId> getProductIdByCustomerProductNo(BPartnerId customerId, String customerProductNo);

	Optional<ProductId> getProductIdByCustomerProductName(BPartnerId customerId, String customerProductName);
}
