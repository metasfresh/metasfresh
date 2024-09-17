/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.bpartner.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.GLN;
import de.metas.bpartner.GeographicalCoordinatesWithBPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.service.impl.GLNQuery;
import de.metas.email.EMailAddress;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

public interface IBPartnerDAO extends ISingletonService
{
	void save(I_C_BPartner bpartner);

	void saveOutOfTrx(I_C_BPartner bpartner);

	void save(I_C_BPartner_Location bpartnerLocation);

	void save(I_AD_User bpartnerContact);

	I_C_BPartner getById(final int bpartnerId);

	<T extends I_C_BPartner> T getById(int bpartnerId, Class<T> modelClass);

	I_C_BPartner getByIdOutOfTrx(BPartnerId bpartnerId);

	I_C_BPartner getById(final BPartnerId bpartnerId);

	<T extends I_C_BPartner> T getById(BPartnerId bpartnerId, Class<T> modelClass);

	List<I_C_BPartner> getByIds(@NonNull Collection<BPartnerId> bpartnerIds);

	/**
	 * @deprecated Please use {@link IBPartnerDAO#retrieveBPartnerIdBy(BPartnerQuery)} instead.
	 */
	@Deprecated
	Optional<BPartnerId> getBPartnerIdByValue(String bpartnerValue);

	/**
	 * Get the ID of the BPArtner with the given {@code salesPartnerCode} and {@code IsSalesRep='Y'}.
	 *
	 * @param onlyOrgIds restrict to any of the given orgIds. If empty, then don't filter for orgIds
	 * @return empty if the given {@code salesPartnerCode} is empty.
	 * @throws org.adempiere.exceptions.AdempiereException if the given parameters match more than one bPartner.
	 */
	Optional<BPartnerId> getBPartnerIdBySalesPartnerCode(String salesPartnerCode, Set<OrgId> onlyOrgIds);

	Optional<BPartnerId> getBPartnerIdByExternalId(@NonNull ExternalId externalId);

	I_C_BPartner getByIdInTrx(BPartnerId bpartnerId);

	/**
	 * Retrieve {@link I_C_BPartner} assigned to given organization
	 *
	 * @return {@link I_C_BPartner}; never return null
	 * @throws OrgHasNoBPartnerLinkException if no partner was found
	 */
	<T extends I_C_BPartner> T retrieveOrgBPartner(Properties ctx, int orgId, Class<T> clazz, @Nullable String trxName);

	Optional<UserId> getDefaultContactId(BPartnerId bpartnerId);

	Stream<UserId> getUserIdsForBpartnerLocation(BPartnerLocationId bpartnerId);

	Optional<BPartnerLocationId> getBPartnerLocationIdByExternalId(BPartnerId bpartnerId, ExternalId externalId);

	Optional<BPartnerLocationId> getBPartnerLocationIdByGln(BPartnerId bpartnerId, GLN gln);

	@NonNull
	BPartnerLocationId getBPartnerLocationIdByRepoId(final int repoId);

	ImmutableSet<BPartnerLocationId> getBPartnerLocationIdsByRepoIds(@NonNull Set<Integer> repoIds);

	/**
	 * @deprecated in all cases i can imagine, if the caller has a {@code bpartnerLocationId}, they need the actual record, even if it is inactive.
	 * Think e.g. of a completed shipment. Therefore, please consider using {@link #getBPartnerLocationByIdEvenInactive(BPartnerLocationId)} instead.
	 */
	@Nullable
	@Deprecated
	I_C_BPartner_Location getBPartnerLocationById(BPartnerLocationId bpartnerLocationId);

	@Nullable
	I_C_BPartner_Location getBPartnerLocationByIdEvenInactive(@NonNull BPartnerLocationId bpartnerLocationId);

	@Nullable
	I_C_BPartner_Location getBPartnerLocationByIdInTrx(BPartnerLocationId bpartnerLocationId);

	BPartnerLocationAndCaptureId getBPartnerLocationAndCaptureIdInTrx(@NonNull BPartnerLocationId bpartnerLocationId);

	boolean existsAndIsActive(BPartnerLocationId bpartnerLocationId);

	List<I_C_BPartner_Location> retrieveBPartnerLocations(BPartnerId bpartnerId);

	List<I_C_BPartner_Location> retrieveBPartnerLocations(@NonNull final BPartnerId bpartnerId, final boolean includeInactive);

	List<I_C_BPartner_Location> retrieveBPartnerLocations(I_C_BPartner bpartner);

	Set<CountryId> retrieveBPartnerLocationCountryIds(BPartnerId bpartnerId);

	CountryId getCountryIdInTrx(BPartnerLocationId bpLocationId);

	LocationId getLocationId(@NonNull BPartnerLocationId bpLocationId);

	/**
	 * @return Contacts of the partner, ordered by ad_user_ID, ascending
	 */
	List<I_AD_User> retrieveContacts(Properties ctx, int partnerId, @Nullable String trxName);

	/**
	 * @return Contacts of the partner, ordered by ad_user_ID, ascending
	 */
	List<I_AD_User> retrieveContacts(I_C_BPartner bpartner);

	List<I_AD_User> retrieveContacts(BPartnerId bpartnerId);

	<T extends I_C_BPartner> T getByIdInTrx(@NonNull BPartnerId bpartnerId, @NonNull Class<T> modelClass);

	Optional<BPartnerContactId> getContactIdByExternalId(BPartnerId bpartnerId, ExternalId externalId);

	ImmutableSet<BPartnerContactId> getContactIdsByRepoIds(@NonNull Set<Integer> repoIds);

	@Nullable
	I_AD_User getContactById(@NonNull BPartnerContactId contactId);

	String getContactLocationEmail(@Nullable BPartnerContactId contactId);
	@Nullable
	I_AD_User getContactByIdInTrx(BPartnerContactId contactId);

	<T extends I_AD_User> T getContactById(BPartnerContactId contactId, Class<T> modelClass);

	@NonNull EMailAddress getContactEMail(BPartnerContactId contactId);

	@Nullable
	PricingSystemId retrievePricingSystemIdOrNullInTrx(BPartnerId bPartnerId, SOTrx soTrx);

	@Nullable
	PricingSystemId retrievePricingSystemIdOrNull(BPartnerId bPartnerId, SOTrx soTrx);

	ShipperId getShipperId(BPartnerId bpartnerId);

	/**
	 * @return true if an address with the flag columnName on true already exists in the table, false otherwise.
	 */
	boolean existsDefaultAddressInTable(I_C_BPartner_Location address, @Nullable String trxName, String columnName);

	/**
	 * @return true if a contact with the flag defaultContact on true already exists in the table, false otherwise.
	 */
	boolean existsDefaultContactInTable(I_AD_User user, @Nullable String trxName);

	/**
	 * Search after the BPartner when the value is given
	 *
	 * @return C_BPartner_Location object or null
	 */
	@Nullable
	I_C_BPartner retrieveBPartnerByValue(Properties ctx, String value);

	/**
	 * Retrieve partner by exact value or by the ending string.
	 * <p>
	 * Use case: why have BPartner-Values such as "G01234", but on ESR-payment documents, there is only "01234", because there it may only contain digits.
	 *
	 * @param bpValue                 an exact bpartner value. Try to retrieve by that value first, if <code>null</code> or empty, directly try the fallback
	 * @param bpValueSuffixToFallback the suffix of a bpartner value. Only use if retrieval by <code>bpValue</code> produced no results. If <code>null</code> or empty, return <code>null</code>.
	 * @return a single bPartner or <code>null</code>
	 * @throws org.adempiere.exceptions.DBMoreThanOneRecordsFoundException if there is more than one matching partner.
	 */
	@Nullable
	I_C_BPartner retrieveBPartnerByValueOrSuffix(Properties ctx, String bpValue, String bpValueSuffixToFallback);

	boolean hasEmailAddress(@NonNull BPartnerContactId contactId);

	@Nullable
	<T extends org.compiere.model.I_AD_User> T retrieveDefaultContactOrNull(I_C_BPartner bPartner, Class<T> clazz);

	/**
	 * Checks if there more BP Locations for given BP, excluding the given one.
	 *
	 * @return true if there more BP locations for given BP, excluding the given one
	 */
	boolean hasMoreLocations(Properties ctx, int bpartnerId, int excludeBPLocationId, @Nullable String trxName);

	/**
	 * Search the {@link I_C_BP_Relation}s for matching partner and location (note that the link without location is acceptable too)
	 *
	 * @return {@link I_C_BP_Relation} first encountered which is used for billing
	 */
	I_C_BP_Relation retrieveBillBPartnerRelationFirstEncountered(Object contextProvider, I_C_BPartner partner, I_C_BPartner_Location location);


	/**
	 * Retrieve default/first ship to location.
	 *
	 * @return ship to location or null
	 * @deprecated please consider using {@link #retrieveBPartnerLocation(BPartnerLocationQuery)} instead
	 */
	@Deprecated
	I_C_BPartner_Location retrieveShipToLocation(Properties ctx, int bPartnerId, @Nullable String trxName);

	/**
	 * Retrieve all (active) ship to locations.
	 * <p>
	 * NOTE: the default ship to location will be the first.
	 *
	 * @return all bpartner's ship to locations
	 */
	List<I_C_BPartner_Location> retrieveBPartnerShipToLocations(I_C_BPartner bpartner);

	List<I_C_BPartner_Location> retrieveBPartnerLocationsByIds(Set<BPartnerLocationId> ids);

	/**
	 * Performs an non-strict search (e.g. if BP has only one address, it returns it even if it's not flagged as the default ShipTo address).
	 *
	 * @return bp location or null
	 * @deprecated please consider using {@link #retrieveBPartnerLocation(BPartnerLocationQuery)} instead
	 */
	@Deprecated
	@Nullable
	I_C_BPartner_Location getDefaultShipToLocation(BPartnerId bpartnerId);

	@Nullable
	CountryId getDefaultShipToLocationCountryIdOrNull(BPartnerId bpartnerId);

	@NonNull
	CountryId getCountryId(@NonNull BPartnerLocationId bpLocationId);
	/**
	 * Retrieve default/first bill to location.
	 *
	 * @param alsoTryBilltoRelation if <code>true</code> and the given partner has no billTo location, then the method also checks if there is a billTo-<code>C_BP_Relation</code> and if so, returns
	 *                              that relation's bPartner location.
	 * @return bill to location or null
	 * @deprecated please consider using {@link #retrieveBPartnerLocation(BPartnerLocationQuery)} instead
	 */
	@Deprecated
	I_C_BPartner_Location retrieveBillToLocation(Properties ctx,
			int bPartnerId,
			boolean alsoTryBilltoRelation,
			@Nullable String trxName);

	/**
	 * Get the fit contact for the given partner and isSOTrx. In case of SOTrx, the salesContacts will have priority. Same for POTrx and PurcanseCOntacts In case of 2 entries with equal values in the
	 * fields above, the Default contact will have priority
	 */
	@Nullable
	I_AD_User retrieveContact(Properties ctx, int bpartnerId, boolean isSOTrx, @Nullable String trxName);

	Map<BPartnerId, Integer> retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType bpartnerType);

	/**
	 * @deprecated please consider using {@link #retrieveBPartnerLocation(BPartnerLocationQuery)} instead
	 */
	@Deprecated
	BPartnerLocationId getBilltoDefaultLocationIdByBpartnerId(BPartnerId bpartnerId);

	/**
	 * @deprecated please consider using {@link #retrieveBPartnerLocation(BPartnerLocationQuery)} instead
	 */
	@Deprecated
	BPartnerLocationId getShiptoDefaultLocationIdByBpartnerId(BPartnerId bpartnerId);

	String getBPartnerNameById(BPartnerId bpartnerId);

	List<String> getBPartnerNamesByIds(Collection<BPartnerId> bpartnerIds);

	Optional<BPartnerId> retrieveBPartnerIdBy(BPartnerQuery query);

	ImmutableSet<BPartnerId> retrieveBPartnerIdsBy(BPartnerQuery query);

	BPartnerLocationId retrieveBPartnerLocationId(BPartnerLocationQuery query);

	@Nullable
	I_C_BPartner_Location retrieveBPartnerLocation(@NonNull BPartnerLocationQuery query);

	BPartnerPrintFormatMap getPrintFormats(@NonNull BPartnerId bpartnerId);

	Optional<BPartnerId> getCounterpartBPartnerId(
			@NonNull OrgMappingId orgMappingId,
			@NonNull OrgId targetOrgId);

	BPartnerId cloneBPartnerRecord(@NonNull CloneBPartnerRequest request);

	List<I_C_BPartner> retrieveVendors(@NonNull QueryLimit limit);

	@Value
	@Builder
	class BPartnerLocationQuery
	{
		public enum Type
		{
			BILL_TO, SHIP_TO, REMIT_TO
		}

		@NonNull
		BPartnerId bpartnerId;

		@NonNull
		Type type;

		/**
		 * If {@code false}, then bpartner locations with the given type are preferred, but also a location with another type can be returned.
		 * {@code true} by default.
		 */
		@Default
		boolean applyTypeStrictly = true;

		/**
		 * If set, then return the bPartner relation which has this id as {@code C_BPartner_Location_ID} and if not found, fallback to initial location.
		 */
		@Nullable
		BPartnerLocationId relationBPartnerLocationId;

		@Nullable
		String postalCode;

		@Nullable
		String city;

		@Nullable
		CountryId countryId;

		public boolean applyLocationChecks()
		{
			return countryId != null;
		}
	}

	BPGroupId getBPGroupIdByBPartnerId(BPartnerId bpartnerId);

	Stream<BPartnerId> streamBPartnerIdsBySalesRepBPartnerId(BPartnerId parentPartnerId);

	List<BPartnerId> getParentsUpToTheTopInTrx(BPartnerId bpartnerId);

	boolean isCampaignPriceAllowed(BPartnerId bpartnerId);

	boolean pricingSystemBelongsToCustomerForPriceMutation(PricingSystemId pricingSystemId);

	Optional<BPartnerContactId> getBPartnerContactIdBy(BPartnerContactQuery query);

	List<GeographicalCoordinatesWithBPartnerLocationId> getGeoCoordinatesByBPartnerIds(Collection<BPartnerId> bpartnerIds);

	List<GeographicalCoordinatesWithBPartnerLocationId> getGeoCoordinatesByBPartnerLocationIds(Collection<Integer> bpartnerLocationRepoIds);

	I_C_BPartner_Location retrieveCurrentBillLocationOrNull(BPartnerId partnerId);

	BPartnerLocationId retrieveLastUpdatedLocation(BPartnerId bpartnerId);
	
	List<I_C_BPartner> retrieveByIds(Set<BPartnerId> bpartnerIds);

	BPartnerLocationId getCurrentLocation(final BPartnerLocationId locationId);

	@NonNull
	Optional<BPartnerLocationId> retrieveSingleBPartnerLocationIdBy(@NonNull GLNQuery query);
}
