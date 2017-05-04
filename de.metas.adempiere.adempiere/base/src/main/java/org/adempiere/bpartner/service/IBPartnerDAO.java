package org.adempiere.bpartner.service;

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
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Shipper;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;

public interface IBPartnerDAO extends ISingletonService
{
	/**
	 *
	 * @param value
	 * @return may return <code>null</code> if there is no matching bpartner
	 *
	 */
	<T extends I_C_BPartner> T retrieveBPartner(Properties ctx, String value, Class<T> clazz, String trxName);

	/**
	 * Retrieve {@link I_C_BPartner} assigned to given organization
	 *
	 * @param ctx
	 * @param orgId
	 * @param clazz
	 * @param trxName
	 * @return {@link I_C_BPartner}; never return null
	 * @throws OrgHasNoBPartnerLinkException if no partner was found
	 */
	<T extends I_C_BPartner> T retrieveOrgBPartner(Properties ctx, int orgId, Class<T> clazz, String trxName);

	<T extends I_C_Location> List<T> retrieveLocation(Properties ctx, String address1, String city, String postal, String countryCode, Class<T> clazz, String trxName);

	/**
	 *
	 * @param countryCode
	 * @param trxName
	 * @return
	 */
	I_C_Country retrieveCountry(String countryCode, String trxName);

	/**
	 *
	 * @param name the name of a business partner location.
	 * @param trxName
	 * @return may return <code>null</code> if there is no matching bPartnerLocation
	 */
	I_C_BPartner_Location retrieveBPartnerLocation(String name, String trxName);

	List<I_C_BPartner_Location> retrieveBPartnerLocations(Properties ctx, int bpartnerId, String trxName);

	/**
	 *
	 * @param bPartnerId
	 * @param reload
	 * @param trxName
	 * @return
	 * @deprecated please use {@link #retrieveBPartnerLocations(I_C_BPartner)} or {@link #retrieveBPartnerLocations(Properties, int, String)}.
	 */
	@Deprecated
	List<I_C_BPartner_Location> retrieveBPartnerLocations(int bPartnerId, boolean reload, String trxName);

	List<I_C_BPartner_Location> retrieveBPartnerLocations(I_C_BPartner bpartner);

	/**
	 * Contacts of the partner, ordered by ad_user_ID, ascending
	 *
	 * @param bPartnerId
	 * @param reload
	 * @param trxName
	 * @return
	 */
	List<org.compiere.model.I_AD_User> retrieveContacts(int bPartnerId, boolean reload, String trxName);

	/**
	 * Contacts of the partner, ordered by ad_user_ID, ascending
	 *
	 * @param ctx
	 * @param partnerId
	 * @param trxName
	 * @return
	 */
	List<I_AD_User> retrieveContacts(Properties ctx, int partnerId, String trxName);

	/**
	 * Contacts of the partner, ordered by ad_user_ID, ascending
	 *
	 * @param bpartner
	 * @return
	 */
	List<I_AD_User> retrieveContacts(I_C_BPartner bpartner);

	I_C_BPartner retrieveDefaultVendor(int productId, String trxName) throws ProductHasNoVendorException;

	I_C_Greeting retrieveGreeting(String name, String trxName);

	/**
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param soTrx
	 * @param trxName
	 * @return
	 * @deprecated this method accesses the legacy pricelist-columns of the given <code>C_BPartner</code>.
	 */
	@Deprecated
	int retrievePriceListId(Properties ctx, int bPartnerId, boolean soTrx, String trxName);

	/**
	 * Returns the <code>M_PricingSystem_ID</code> to use for a given bPartner.
	 *
	 *
	 * @param ctx
	 * @param bPartnerId the ID of the BPartner for which we need the pricing system id
	 * @param soTrx
	 *            <ul>
	 *            <li>if <code>true</code>, then the method first checks <code>C_BPartner.M_PricingSystem_ID</code> , then (if the BPartner has a C_BP_Group_ID) in
	 *            <code>C_BP_Group.M_PricingSystem_ID</code> and finally (if the C_BPArtner has a AD_Org_ID>0) in <code>AD_OrgInfo.M_PricingSystem_ID</code></li>
	 *            <li>if <code>false</code></li>, then the method first checks <code>C_BPartner.PO_PricingSystem_ID</code>, then (if the BPartner has a C_BP_Group_ID!) in
	 *            <code>C_BP_Group.PO_PricingSystem_ID</code>. Note that <code>AD_OrgInfo</code> has currently no <code>PO_PricingSystem_ID</code> column.
	 *            </ul>
	 * @param trxName
	 * @return M_PricingSystem_ID or 0
	 */
	int retrievePricingSystemId(Properties ctx, int bPartnerId, boolean soTrx, String trxName);

	/**
	 * Retrieves the discount schema for the given BParnter. If the BPartner has none, it falls back to the partner's C_BP_Group. If the partner has no group or that group hasn't a discount schema
	 * either, it returns <code>null</code>.
	 *
	 * @param partner
	 * @param soTrx if <code>true</code>, the sales discount schema is returned, otherwise the purchase discount schema is returned.
	 */
	I_M_DiscountSchema retrieveDiscountSchemaOrNull(I_C_BPartner partner, boolean soTrx);

	I_M_Shipper retrieveShipper(int bPartnerId, String trxName);

	I_M_Shipper retrieveDefaultShipper();

	/**
	 * @param address
	 * @param po
	 * @param columnName
	 * @return true if an address with the flag columnName on true already exists in the table, false otherwise.
	 */
	boolean existsDefaultAddressInTable(I_C_BPartner_Location address, String trxName, String columnName);

	/**
	 * @param user
	 * @param trxName
	 * @return true if a contact with the flag defaultContact on true already exists in the table, false otherwise.
	 */
	boolean existsDefaultContactInTable(de.metas.adempiere.model.I_AD_User user, String trxName);

	/**
	 * Search after the BPartner when the value is given
	 *
	 * @param ctx
	 * @param value
	 * @return C_BPartner_Location object or null
	 */
	I_C_BPartner retrieveBPartnerByValue(Properties ctx, String value);

	/**
	 * Retrieve partner by exact value or by the ending string.
	 *
	 * Use case: why have BPartner-Values such as "G01234", but on ESR-payment documents, there is only "01234", because there it may only contain digits.
	 *
	 * @param ctx
	 * @param bpValue an exact bpartner value. Try to retrieve by that value first, if <code>null</code> or empty, directly try the fallback
	 * @param bpValueSuffixToFallback the suffix of a bpartner value. Only use if retrieval by <code>bpValue</code> produced no results. If <code>null</code> or empty, return <code>null</code>.
	 *
	 * @return a single bPartner or <code>null</code>
	 *
	 * @throws org.adempiere.exceptions.DBMoreThenOneRecordsFoundException if there is more than one matching partner.
	 */
	I_C_BPartner retrieveBPartnerByValueOrSuffix(Properties ctx, String bpValue, String bpValueSuffixToFallback);

	<T extends I_AD_User> T retrieveDefaultContactOrNull(I_C_BPartner bPartner, Class<T> clazz);

	/**
	 * Checks if there more BP Locations for given BP, excluding the given one.
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @param excludeBPLocationId
	 * @param trxName
	 * @return true if there more BP locations for given BP, excluding the given one
	 */
	boolean hasMoreLocations(Properties ctx, int bpartnerId, int excludeBPLocationId, String trxName);

	/**
	 * Search the {@link I_C_BP_Relation}s for matching partner and location (note that the link without location is acceptable too)
	 *
	 * @param contextProvider
	 * @param partner
	 * @param location
	 * @return {@link I_C_BP_Relation} first encountered which is used for billing
	 */
	I_C_BP_Relation retrieveBillBPartnerRelationFirstEncountered(Object contextProvider, I_C_BPartner partner, I_C_BPartner_Location location);

	/**
	 * Retrieve default/first ship to location.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param trxName
	 * @return ship to location or null
	 */
	I_C_BPartner_Location retrieveShipToLocation(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve all (active) ship to locations.
	 *
	 * NOTE: the default ship to location will be the first.
	 *
	 * @param bpartner
	 * @return all bpartner's ship to locations
	 */
	List<I_C_BPartner_Location> retrieveBPartnerShipToLocations(I_C_BPartner bpartner);

	/**
	 * Retrieve default/first bill to location.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param alsoTryBilltoRelation if <code>true</code> and the given partner has no billTo location, then the method also checks if there is a billTo-<code>C_BP_Relation</code> and if so, returns
	 *            that relation's bPartner location.
	 * @param trxName
	 * @return bill to location or null
	 */
	I_C_BPartner_Location retrieveBillToLocation(Properties ctx,
			int bPartnerId,
			boolean alsoTryBilltoRelation,
			String trxName);

	/**
	 * Get the fit contact for the given partner and isSOTrx. In case of SOTrx, the salesContacts will have priority. Same for POTrx and PurcanseCOntacts In case of 2 entries with equal values in the
	 * fields above, the Default contact will have priority
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @param isSOTrx
	 * @param trxName
	 * @return
	 */
	I_AD_User retrieveContact(Properties ctx, int bpartnerId, boolean isSOTrx, String trxName);

}
