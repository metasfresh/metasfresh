package de.metas.procurement.base.impl;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import com.google.common.annotations.VisibleForTesting;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.sync.util.UUIDs;
import de.metas.rfq.model.I_C_RfQResponseLine;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@VisibleForTesting
public final class SyncUUIDs
{
	private SyncUUIDs()
	{
	}
	
	public static final String toUUIDString(final I_C_BPartner bpartner)
	{
		return UUIDs.fromIdAsString(bpartner.getC_BPartner_ID());
	}
	
	public static final int getC_BPartner_ID(final String uuid)
	{
		return UUIDs.toId(uuid);
	}
	
	public static final String toUUIDString(final I_AD_User contact)
	{
		return UUIDs.fromIdAsString(contact.getAD_User_ID());
	}
	
	public static final String toUUIDString(final I_PMM_Product pmmProduct)
	{
		return UUIDs.fromIdAsString(pmmProduct.getPMM_Product_ID());
	}

	public static final int getPMM_Product_ID(final String uuid)
	{
		return UUIDs.toId(uuid);
	}

	public static final String toUUIDString(final I_C_Flatrate_Term contract)
	{
		return UUIDs.fromIdAsString(contract.getC_Flatrate_Term_ID());
	}
	
	public static final int getC_Flatrate_Term_ID(final String uuid)
	{
		return UUIDs.toId(uuid);
	}

	public static final String toUUIDString(final I_C_RfQResponseLine rfqResponseLine)
	{
		return toC_RfQReponseLine_UUID(rfqResponseLine.getC_RfQResponseLine_ID());
	}
	
	public static final String toC_RfQReponseLine_UUID(final int C_RfQResponseLine_ID)
	{
		return UUIDs.fromIdAsString(C_RfQResponseLine_ID);
	}

	
	public static final int getC_RfQResponseLine_ID(final String uuid)
	{
		return UUIDs.toId(uuid);
	}
}
