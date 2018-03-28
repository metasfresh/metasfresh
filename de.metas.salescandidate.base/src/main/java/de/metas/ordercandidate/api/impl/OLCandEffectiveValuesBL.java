package de.metas.ordercandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;

public class OLCandEffectiveValuesBL implements IOLCandEffectiveValuesBL
{
	@Override
	public int getC_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer bPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_C_BPartner_ID);
		final int bPartnerID = bPartnerOverride == null ? 0 : bPartnerOverride;

		if (bPartnerID <= 0)
		{
			return olCand.getC_BPartner_ID();
		}
		return bPartnerID;
	}

	@Override
	public I_C_BPartner getC_BPartner_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_BPartner_Effective_ID(olCand),
				I_C_BPartner.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getC_BP_Location_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer bpLocationOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID);
		final int bpLocationID = bpLocationOverride == null ? 0 : bpLocationOverride;

		if (bpLocationID <= 0)
		{
			return olCand.getC_BPartner_Location_ID();
		}
		return bpLocationID;
	}

	@Override
	public I_C_BPartner_Location getC_BP_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_BP_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getAD_User_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getAD_User_ID();
	}

	// TODO: figure out which of those getDatePromised methods shall stay!
	@Override
	public Timestamp getDatePromised_Effective(final I_C_OLCand olCand)
	{
		if (olCand.getDatePromised_Override() == null)
		{
			return olCand.getDatePromised() != null
					? olCand.getDatePromised()
					: SystemTime.asDayTimestamp();
		}
		return olCand.getDatePromised_Override();
	}

	@Override
	public Timestamp getDatePromisedEffective(final I_C_OLCand olCand)
	{
		Check.assumeNotNull(olCand, "OLCand not null");
		final Timestamp datePromisedOverride = olCand.getDatePromised_Override();

		if (datePromisedOverride != null)
		{
			return datePromisedOverride;
		}

		return olCand.getDatePromised();
	}

	@Override
	public int getBill_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_BPartner_ID() > 0
				? olCand.getBill_BPartner_ID()
				: getC_BPartner_Effective_ID(olCand);
	}

	@Override
	public <T extends I_C_BPartner> T getBill_BPartner_Effective(final I_C_OLCand olCand, final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getBill_BPartner_Effective_ID(olCand),
				clazz,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getBill_Location_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_Location_ID() > 0
				? olCand.getBill_Location_ID()
				: getC_BP_Location_Effective_ID(olCand);
	}

	@Override
	public I_C_BPartner_Location getBill_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getBill_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getBill_User_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_User_ID() > 0
				? olCand.getBill_User_ID()
				: getAD_User_Effective_ID(olCand);
	}

	@Override
	public int getDropShip_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer dropShipBPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID);
		final int dropShipBPartnerID = dropShipBPartnerOverride == null ? 0 : dropShipBPartnerOverride;

		final int bpartnerID = getC_BPartner_Effective_ID(olCand);

		if (dropShipBPartnerID > 0)
		{
			// the dropship partner was set
			return dropShipBPartnerID;
		}

		// fall-back to bpartner
		return bpartnerID;
	}

	@Override
	public int getDropShip_Location_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer dropShipLocationOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_DropShip_Location_ID);
		final int dropShipLocationID = dropShipLocationOverride == null ? 0 : dropShipLocationOverride;

		final int bpLocationId = getC_BP_Location_Effective_ID(olCand);

		if (dropShipLocationID > 0)
		{
			// the dropship location was set
			return dropShipLocationID;
		}

		// fallback to the bp location
		return bpLocationId;
	}

	@Override
	public I_C_BPartner_Location getDropShip_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getDropShip_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getDropShip_User_Effective_ID(final I_C_OLCand olCand)
	{
		return getAD_User_Effective_ID(olCand);
	}

	@Override
	public int getM_Product_Effective_ID(final I_C_OLCand olCand)
	{
		Check.assumeNotNull(olCand, "OLCand not null");

		return olCand.getM_Product_Override_ID() > 0
				? olCand.getM_Product_Override_ID()
				: olCand.getM_Product_ID();
	}

	@Override
	public I_M_Product getM_Product_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getM_Product_Effective_ID(olCand),
				I_M_Product.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getC_UOM_Effective_ID(final I_C_OLCand olCand)
	{
		Check.assumeNotNull(olCand, "OLCand not null");

		return olCand.isManualPrice()
				? olCand.getC_UOM_ID()
				: olCand.getC_UOM_Internal_ID();
	}

	@Override
	public I_C_UOM getC_UOM_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_UOM_Effective_ID(olCand),
				I_C_UOM.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getHandOver_Partner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer handOverPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_HandOver_Partner_ID);
		final int handOverPartnerID = handOverPartnerOverride == null ? 0 : handOverPartnerOverride;

		final int bpartnerId = getC_BPartner_Effective_ID(olCand);

		if (handOverPartnerID > 0)
		{
			// the handover partner was set

			return handOverPartnerID;
		}

		// fall-back to C_BPartner
		return bpartnerId;

	}

	@Override
	public I_C_BPartner getHandOver_Partner_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getHandOver_Partner_Effective_ID(olCand),
				I_C_BPartner.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public int getHandOver_Location_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer handOverLocationOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_ID);
		final int handOverLocationID = handOverLocationOverride == null ? 0 : handOverLocationOverride;

		final int bpLocationId = getC_BP_Location_Effective_ID(olCand);

		if (handOverLocationID > 0)
		{
			// the handover location was set
			return handOverLocationID;
		}

		// fall-back to C_BPartner_Location
		return bpLocationId;
	}

	@Override
	public I_C_BPartner_Location getHandOver_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getHandOver_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}
}
