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

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;

import de.metas.adempiere.model.I_AD_User;
import de.metas.i18n.Language;

public interface IBPartnerBL extends ISingletonService
{
	/**
	 * make full address
	 *
	 * @param bPartner
	 * @param location
	 * @param user
	 * @param trxName
	 * @return
	 */
	String mkFullAddress(I_C_BPartner bPartner, I_C_BPartner_Location location, I_AD_User user, String trxName);

	/**
	 * Retrieve user/contact assigned to default/first ship to address. If no user/contact found, the first default user contact will be returned.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param trxName
	 * @return user/contact or null
	 */
	I_AD_User retrieveShipContact(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve user/contact assigned to default/first bill to address. If no user/contact found, the first default user contact will be returned.
	 *
	 * @param ctx
	 * @param bPartnerId
	 * @param trxName
	 * @return user/contact or null
	 */
	I_AD_User retrieveBillContact(Properties ctx, int bPartnerId, String trxName);

	/**
	 * Retrieve user/contact that is assigned to given location. If no assigned contact found then the default BPartner contact will be returned.<br>
	 * If there is no contact for given BPartner null will be returned.
	 *
	 * @param loc
	 * @return user/contact or null
	 */
	I_AD_User retrieveUserForLoc(I_C_BPartner_Location loc);

	//
	// Commenting out this de.metas.terminable related code, because it assumes that the following columns exist
	//
	// /**
	// * Set the default flag of a terminable location to the location that is marked as nextID.
	// * This way, the next location will inherit the attributions of the old one.
	// *
	// * @param bpLocation
	// */
	// void updateNextLocation(I_C_BPartner_Location bpLocation);

	// /**
	// * @param bpLocation
	// * @return true if the address is terminated in the past or in the current day
	// */
	// boolean isTerminatedInThePast(I_C_BPartner_Location bpLocation);

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
	boolean isAllowConsolidateInOutEffective(I_C_BPartner partner, boolean isSOTrx);

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
	 * @param ctx
	 * @param bpartnerId
	 * @return {@link Language} or <code>null</code>
	 */
	Language getLanguage(Properties ctx, int bpartnerId);

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
	int getDiscountSchemaId(I_C_BPartner bpartner, boolean soTrx);

	int getDiscountSchemaId(int bpartnerId, boolean soTrx);
}
