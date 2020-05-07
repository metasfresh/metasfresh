/**
 * 
 */
package de.metas.contracts.flatrate.impexp;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Services;

import com.google.common.collect.Ordering;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author cg
 *
 */
@UtilityClass
/* package */ class FlatrateTermImportFinder
{
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	
	public I_AD_User findBillUser(final Properties ctx, final int bpartnerId)
	{
		final List<I_AD_User> users = bpartnerDAO.retrieveContacts(ctx, bpartnerId, ITrx.TRXNAME_ThreadInherited);
		if (users.isEmpty())
		{
			return null;
		}
		else if (users.size() == 1)
		{
			return users.get(0);
		}
		else
		{
			final I_AD_User billUser = users.stream()
					.filter(I_AD_User::isBillToContact_Default)
					.sorted(Ordering.natural().onResultOf(user -> user.isBillToContact_Default() ? 0 : 1))
					.findFirst().get();
			if (billUser.isBillToContact_Default())
			{
				return billUser;
			}
			else
			{
				return null;
			}
		}
	}
	

	public I_AD_User findDropShipUser(final Properties ctx, final int bpartnerId)
	{
		final List<I_AD_User> users = bpartnerDAO.retrieveContacts(ctx, bpartnerId, ITrx.TRXNAME_ThreadInherited);
		if (users.isEmpty())
		{
			return null;
		}
		else if (users.size() == 1)
		{
			return users.get(0);
		}
		else
		{
			final I_AD_User dropShipUser = users.stream()
					.filter(I_AD_User::isShipToContact_Default)
					.sorted(Ordering.natural().onResultOf(user -> user.isShipToContact_Default() ? 0 : 1))
					.findFirst().get();
			if (dropShipUser.isShipToContact_Default())
			{
				return dropShipUser;
			}
			else
			{
				return null;
			}
		}
	}
	
	public I_C_BPartner_Location findBPartnerShipToLocation(final Properties ctx, final int bpartnerId)
	{
		final List<I_C_BPartner_Location> bpLocations = bpartnerDAO.retrieveBPartnerLocations(ctx, bpartnerId, ITrx.TRXNAME_None);
		if (bpLocations.isEmpty())
		{
			return null;
		}
		else if (bpLocations.size() == 1)
		{
			return bpLocations.get(0);
		}
		else
		{
			final I_C_BPartner_Location bpLocation = bpLocations.stream()
					.filter(I_C_BPartner_Location::isShipTo)
					.sorted(Ordering.natural().onResultOf(bpl -> bpl.isShipToDefault() ? 0 : 1))
					.findFirst().get();
			if (bpLocation.isShipToDefault())
			{
				return bpLocation;
			}
			else
			{
				return null;
			}
		}
	}
}
