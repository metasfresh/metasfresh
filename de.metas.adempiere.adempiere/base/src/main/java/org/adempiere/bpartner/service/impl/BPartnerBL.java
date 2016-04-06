package org.adempiere.bpartner.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerAware;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MBPartner;
import org.compiere.util.Language;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.service.ILocationBL;
import de.metas.adempiere.service.impl.AddressBuilder;
import de.metas.interfaces.I_C_BPartner;

public class BPartnerBL implements IBPartnerBL
{
	/* package */static final String SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override = "C_BPartner.SOTrx_AllowConsolidateInOut_Override";

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public String mkFullAddress(
			final I_C_BPartner bPartner,
			final I_C_BPartner_Location location,
			final I_AD_User user,
			final String trxName)
	{
		return new AddressBuilder().buildBPartnerFullAddressString(bPartner, location, user, trxName);
	}

	@Override
	public I_AD_User retrieveShipContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final I_C_BPartner_Location loc = bPartnerDAO.retrieveShipToLocation(ctx, bPartnerId, trxName);
		final int bPartnerLocationId = loc == null ? -1 : loc.getC_BPartner_Location_ID();
		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Override
	public I_AD_User retrieveShipContact(final org.compiere.model.I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int bPartnerId = bpartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final org.compiere.model.I_AD_User userPO = retrieveShipContact(ctx, bPartnerId, trxName);
		return InterfaceWrapperHelper.create(userPO, I_AD_User.class);
	}

	@Override
	public I_AD_User retrieveBillContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final org.compiere.model.I_C_BPartner_Location loc = bPartnerDAO.retrieveBillToLocation(ctx, bPartnerId, false, trxName);
		final int bPartnerLocationId = loc == null ? -1 : loc.getC_BPartner_Location_ID();
		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Override
	public I_AD_User retrieveUserForLoc(final org.compiere.model.I_C_BPartner_Location loc)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(loc);
		final int bPartnerId = loc.getC_BPartner_ID();
		final int bPartnerLocationId = loc.getC_BPartner_Location_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(loc);

		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	private I_AD_User retrieveUserForLoc(final Properties ctx, final int bPartnerId, final int bPartnerLocationId, final String trxName)
	{
		final List<I_AD_User> users = bPartnerDAO.retrieveContacts(ctx, bPartnerId, trxName);

		if (bPartnerLocationId > 0)
		{
			for (final I_AD_User user : users)
			{
				if (user.getC_BPartner_Location_ID() == bPartnerLocationId)
				{
					return user;
				}
			}
		}

		return getDefaultBPContact(users);
	}

	/**
	 * Selects the default contact from a list of BPartner users. Returns first user with IsDefaultContact=Y found or first contact.
	 *
	 * @param users
	 * @return default user/contact.
	 */
	private I_AD_User getDefaultBPContact(final List<I_AD_User> users)
	{
		if (users == null || users.isEmpty())
		{
			return null;
		}

		for (final I_AD_User user : users)
		{
			if (user.isDefaultContact())
			{
				return user;
			}
		}
		return users.get(0);
	}

	//
	// Commenting out this de.metas.terminable related code, because it assumes that the following columns exist
	//
	/*
	 * @Override public void updateNextLocation(I_C_BPartner_Location bpLocation) { final int nextId = bpLocation.getNext_ID(); if (nextId <= 0) { return; }
	 * 
	 * final Properties ctx = InterfaceWrapperHelper.getCtx(bpLocation); final String trxName = InterfaceWrapperHelper.getTrxName(bpLocation);
	 * 
	 * final I_C_BPartner_Location nextLocation = InterfaceWrapperHelper.create(ctx, nextId, I_C_BPartner_Location.class, trxName);
	 * 
	 * // inherit the flags from the previous
	 * 
	 * // Don't update the defaults if the current location is still valid. if (isTerminatedInThePast(bpLocation)) { nextLocation.setIsBillToDefault(bpLocation.isBillToDefault());
	 * nextLocation.setIsShipToDefault(bpLocation.isShipToDefault()); }
	 * 
	 * nextLocation.setIsBillTo(bpLocation.isBillTo()); nextLocation.setIsShipTo(bpLocation.isShipTo());
	 * 
	 * InterfaceWrapperHelper.save(nextLocation); }
	 */

	@Override
	public void setAddress(final I_C_BPartner_Location bpLocation)
	{
		final String address = Services.get(ILocationBL.class).mkAddress(bpLocation.getC_Location(),
				"",  // bPartnerBlock
				"" // userBlock
		);

		bpLocation.setAddress(address);

	}

	@Override
	public boolean isAllowConsolidateInOutEffective(final org.compiere.model.I_C_BPartner partner, final boolean isSOTrx)
	{
		Check.assumeNotNull(partner, "partner not null");

		final I_C_BPartner partnerToUse = InterfaceWrapperHelper.create(partner, de.metas.interfaces.I_C_BPartner.class);
		final boolean partnerAllowConsolidateInOut = partnerToUse.isAllowConsolidateInOut();
		if (partnerAllowConsolidateInOut)
		{
			return true;
		}

		//
		// 07973: Attempt to override SO shipment consolidation if configured
		if (isSOTrx)
		{
			final boolean allowConsolidateInOutOverrideDefault = false; // default=false (preserve existing logic)
			final boolean allowConsolidateInOutOverride = Services.get(ISysConfigBL.class).getBooleanValue(
					SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override,
					allowConsolidateInOutOverrideDefault
					);
			return allowConsolidateInOutOverride;
		}
		return false;
	}

	@Override
	public Language getLanguage(final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId > 0)
		{
			final MBPartner bp = MBPartner.get(ctx, bpartnerId);
			if (null != bp)
			{
				final String lang = bp.getAD_Language();
				if (!Check.isEmpty(lang, true))
				{
					return Language.getLanguage(lang);
				}
			}
			return null;
		}
		return null;
	}
	
	@Override
	public I_C_BPartner getBPartnerForModel(final Object model)
	{
		// 09527 get the most suitable language:
		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IBPartnerAware.class);
		if (bpartnerAware == null)
		{
			return null;
		}
		if (bpartnerAware.getC_BPartner_ID() <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(bpartnerAware.getC_BPartner(), I_C_BPartner.class);
	}


	@Override
	public Language getLanguageForModel(final Object model)
	{
		// 09527 get the most suitable language:
		final I_C_BPartner bpartner = getBPartnerForModel(model);
		if (bpartner == null)
		{
			return null;
		}
		final String lang = bpartner.getAD_Language();
		if (Check.isEmpty(lang, true))
		{
			return null;
		}

		return Language.getLanguage(lang);
	}
}
