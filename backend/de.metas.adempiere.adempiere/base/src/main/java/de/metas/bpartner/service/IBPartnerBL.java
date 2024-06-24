package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.i18n.Language;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

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

public interface IBPartnerBL extends ISingletonService
{
	I_C_BPartner getById(BPartnerId bpartnerId);

	String getBPartnerValue(final BPartnerId bpartnerId);

	String getBPartnerName(@Nullable final BPartnerId bpartnerId);

	String getBPartnerValueAndName(final BPartnerId bpartnerId);

	Map<BPartnerId, String> getBPartnerNames(@NonNull Set<BPartnerId> bpartnerIds);

	/**
	 * make full address
	 */
	String mkFullAddress(@NonNull I_C_BPartner bpartner, @Nullable I_C_BPartner_Location bpLocation, @Nullable LocationId locationId, @Nullable I_AD_User bpContact);

	/**
	 * Retrieve user/contact assigned to default/first ship to address. If no user/contact found, the first default user contact will be returned.
	 *
	 * @return user/contact or null
	 */
	I_AD_User retrieveShipContact(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve user/contact that is assigned to given location. If no assigned contact found then the default BPartner contact will be returned.<br>
	 * If there is no contact for given BPartner null will be returned.
	 *
	 * @return user/contact or null
	 */
	I_AD_User retrieveUserForLoc(I_C_BPartner_Location loc);

	/**
	 * Compute and set {@link I_C_BPartner_Location#COLUMNNAME_Address} field.
	 */
	void setAddress(I_C_BPartner_Location bpLocation);

	void updateAllAddresses(I_C_BPartner bpartner);

	void updateMemo(@NonNull final BPartnerId bpartnerId, String memo);

	I_AD_User retrieveShipContact(I_C_BPartner bpartner);

	/**
	 * Creates a draft contact linked to given partner.
	 * <p>
	 * It's up to the caller to set the other fields and then save it or not.
	 *
	 * @return draft contact
	 */
	I_AD_User createDraftContact(I_C_BPartner bpartner);

	/**
	 * @return true if InOut consolidation is allowed for given partner
	 */
	boolean isAllowConsolidateInOutEffective(BPartnerId bpartnerId, SOTrx soTrx);

	/**
	 * @return true if InOut consolidation is allowed for given partner
	 */
	boolean isAllowConsolidateInOutEffective(I_C_BPartner partner, SOTrx soTrx);

	@Deprecated
	@Nullable
	default Language getLanguage(final Properties ctx_NOTUSED, final int bpartnerRepoId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bpartnerRepoId);
		return bpartnerId != null
				? getLanguage(bpartnerId).orElse(null)
				: null;
	}

	Optional<Language> getLanguage(@NonNull BPartnerId bpartnerId);

	Optional<BPartnerId> getBPartnerIdForModel(@Nullable Object model);

	/**
	 * Get the language of the given model's C_BPartner, if it has a <code>C_BPartner_ID</code> column and if the BPartner is set.
	 */
	Optional<Language> getLanguageForModel(Object model);

	Optional<Language> getLanguage(@NonNull I_C_BPartner bpartner);

	/**
	 * Retrieves the discount schema for the given BPartner. If the BPartner has none, it falls back to the partner's C_BP_Group.
	 * If the partner has no group or that group hasn't a discount schema either, it returns <code>-1</code>.
	 *
	 * @param soTrx if <code>true</code>, the sales discount schema is returned, otherwise the purchase discount schema is returned.
	 * @return partner's discount schema or -1
	 */
	int getDiscountSchemaId(I_C_BPartner bpartner, SOTrx soTrx);

	/**
	 * @return partner's discount schema or -1
	 */
	int getDiscountSchemaId(BPartnerId bpartnerId, SOTrx soTrx);

	/**
	 * Retrieves (out of transaction) a list of {@link User} that could be bill contacts, best first.
	 * <p>
	 * See {@link RetrieveContactRequest}.
	 */
	@Nullable
	User retrieveContactOrNull(RetrieveContactRequest request);

	String getAddressStringByBPartnerLocationId(BPartnerLocationId bpartnerLocationId);

	@Nullable
	UserId getSalesRepIdOrNull(BPartnerId bpartnerId);

	@Nullable
	BPartnerId getBPartnerSalesRepId(BPartnerId bpartnerId);

	void setBPartnerSalesRepIdOutOfTrx(BPartnerId bPartnerId, BPartnerId salesRepBPartnerId);

	/**
	 * @return previous sales rep or null
	 */
	@Nullable
	UserId setSalesRepId(BPartnerId bpartnerId, final UserId salesRepId);

	BPartnerPrintFormatMap getPrintFormats(@NonNull BPartnerId bpartnerId);

	void updateNameAndGreetingFromContacts(@NonNull BPartnerId bpartnerId);

	void setPreviousIdIfPossible(@NonNull I_C_BPartner_Location location);

	@Value
	@Builder
	class RetrieveContactRequest
	{
		public enum ContactType
		{
			BILL_TO_DEFAULT, SHIP_TO_DEFAULT, SALES_DEFAULT, SUBJECT_MATTER
		}

		public enum IfNotFound
		{
			RETURN_DEFAULT_CONTACT, RETURN_NULL
		}

		@NonNull
		BPartnerId bpartnerId;

		@Nullable
		ContactType contactType;

		/**
		 * If specified, then contacts with this location are preferred, even if a user ad another location is the default bill-to user.
		 */
		@Nullable
		BPartnerLocationId bPartnerLocationId;

		/**
		 * If specified, then only matching contacts are considered
		 */
		@Default
		@NonNull
		Predicate<User> filter = user -> true;

		/**
		 * If specified and there are multiple equally good fits, then the first fit according to this comparator is returned.
		 * If not specified, then the contact whose name comes alphabetically first is returned.
		 */
		@Default
		@NonNull
		Comparator<User> comparator = Comparator.comparing(User::getName);

		boolean onlyActive;

		@Default
		IfNotFound ifNotFound = IfNotFound.RETURN_DEFAULT_CONTACT;
	}

	CountryId getCountryId(@NonNull BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId);

	CountryId getCountryId(@NonNull BPartnerInfo bpartnerInfo);

	LocationId getLocationId(@NonNull BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId);

	int getFreightCostIdByBPartnerId(BPartnerId bpartnerId);

	ShipmentAllocationBestBeforePolicy getBestBeforePolicy(BPartnerId bpartnerId);

	Optional<PaymentTermId> getPaymentTermIdForBPartner(BPartnerId bpartnerId, SOTrx soTrx);

	/**
	 * @return the payment rule for the BP. If none is set, gets the one of the BP group.
	 */
	Optional<PaymentRule> getPaymentRuleForBPartner(BPartnerId bpartnerId);

	boolean isSalesRep(BPartnerId bpartnerId);

	void validateSalesRep(@NonNull BPartnerId bPartnerId, @Nullable BPartnerId salesRepId);

	void updateFromPreviousLocation(final I_C_BPartner_Location bpLocation);

	void updateFromPreviousLocationNoSave(final I_C_BPartner_Location bpLocation);

	/**
	 * extracted logic from legacy code
	 * @param bp
	 * @return
	 */
	I_C_BPartner_Location extractShipToLocation(@NonNull I_C_BPartner bp);

	@NonNull
	Optional<UserId> getDefaultDunningContact(@NonNull final BPartnerId bPartnerId);
}
