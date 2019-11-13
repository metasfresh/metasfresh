package de.metas.bpartner.service;

import java.util.Comparator;

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

import java.util.Properties;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;

import com.google.common.base.Predicates;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.i18n.Language;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

public interface IBPartnerBL extends ISingletonService
{
	String getBPartnerValue(final BPartnerId bpartnerId);

	String getBPartnerName(final BPartnerId bpartnerId);

	String getBPartnerValueAndName(final BPartnerId bpartnerId);

	/**
	 * make full address
	 *
	 */
	String mkFullAddress(I_C_BPartner bPartner, I_C_BPartner_Location location, I_AD_User user, String trxName);

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
	 * @param loc
	 * @return user/contact or null
	 */
	I_AD_User retrieveUserForLoc(I_C_BPartner_Location loc);

	/**
	 * Compute and set {@link I_C_BPartner_Location#COLUMNNAME_Address} field.
	 *
	 * @param bpLocation
	 */
	void setAddress(I_C_BPartner_Location bpLocation);

	I_AD_User retrieveShipContact(I_C_BPartner bpartner);

	/**
	 * Creates a draft contact linked to given partner.
	 *
	 * It's up to the caller to set the other fields and then save it or not.
	 *
	 * @param bpartner
	 * @return draft contact
	 */
	I_AD_User createDraftContact(I_C_BPartner bpartner);

	/**
	 * @param partner the partner to check for. Internally working with {@link de.metas.interfaces.I_C_BPartner}.
	 * @param isSOTrx
	 * @return true if InOut consolidation is allowed for given partner
	 */
	boolean isAllowConsolidateInOutEffective(I_C_BPartner partner, SOTrx soTrx);

	/**
	 * Use {@link IBPartnerAware} to get BPartner from given model.
	 *
	 * @param model
	 * @return bpartner or <code>null</code>
	 */
	I_C_BPartner getBPartnerForModel(Object model);

	/**
	 * Gets BPartner's Language
	 *
	 * @param ctx_NOTUSED
	 * @param bpartnerId
	 * @return {@link Language} or <code>null</code>
	 */
	Language getLanguage(Properties ctx_NOTUSED, int bpartnerId);

	/**
	 * Get the language of the given model's C_BPartner, if it has a <code>C_BPartner_ID</code> column and if the BPartner is set.
	 *
	 * @param model
	 * @return the language, if found, <code>null</code> otherwise.
	 */
	Language getLanguageForModel(Object model);

	/**
	 * Creates BPartner, Location and contact from given template.
	 *
	 * @param template
	 * @return created bpartner
	 * @task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	I_C_BPartner createFromTemplate(I_C_BPartner_QuickInput template);

	/**
	 * Retrieves the discount schema for the given BParnter. If the BPartner has none, it falls back to the partner's C_BP_Group.
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
	 * Retrieves (out of transaction) a list of {@link User} that could be bill contacts, best first. See {@link ContactQuery}.
	 */
	User retrieveContactOrNull(RetrieveContactRequest request);

	String getAddressStringByBPartnerLocationId(BPartnerLocationId bpartnerLocationId);

	UserId getSalesRepIdOrNull(BPartnerId bpartnerId);

	@Value
	@Builder
	public static class RetrieveContactRequest
	{
		public enum ContactType
		{
			BILL_TO_DEFAULT, SHIP_TO_DEFAULT, SALES_DEFAULT, SUBJECT_MATTER;
		}

		@NonNull
		BPartnerId bpartnerId;

		@Nullable
		ContactType contactType;

		/** If specified, then contacts with this location are preferred, even if a user ad another location is the default bill-to user. */
		@Nullable
		BPartnerLocationId bPartnerLocationId;

		/** If specified, then only matching contacts are considered */
		@Default
		@NonNull
		Predicate<User> filter = Predicates.alwaysTrue();

		/**
		 * If specified and there are multiple equally good fits, then the first fit according to this comparator is returned.
		 * If not specified, then the contact whose name comes alphabetically first is returned.
		 */
		@Default
		@NonNull
		Comparator<User> comparator = Comparator.comparing(User::getName);
	}

	int getFreightCostIdByBPartnerId(BPartnerId bpartnerId);

	CountryId getBPartnerLocationCountryId(BPartnerLocationId bpLocationId);

	ShipmentAllocationBestBeforePolicy getBestBeforePolicy(BPartnerId bpartnerId);
}
