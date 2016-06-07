package de.metas.ordercandidate.modelvalidator;

import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.ordercandidate.model.I_C_OLCand;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Validator(I_C_OLCand.class)
@Callout(I_C_OLCand.class)
public class C_OLCand_Override
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void onCBPartnerOverride(final I_C_OLCand olCand)
	{
		final I_C_BPartner bpartnerOverride = olCand.getC_BPartner_Override();
		if (bpartnerOverride == null)
		{
			// in case the bpartner Override was deleted, also delete the bpartner Location Override
			olCand.setC_BP_Location_Override(null);
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location shipToLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, bpartnerOverride.getC_BPartner_ID(), trxName);

		if (shipToLocation == null)
		{
			// no location was found
			olCand.setC_BP_Location_Override(null);
		}

		olCand.setC_BP_Location_Override(shipToLocation);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID })
	public void onDropShipPartnerOverride(final I_C_OLCand olCand)
	{
		final I_C_BPartner dropShipPartnerOverride = olCand.getDropShip_BPartner_Override();
		if (dropShipPartnerOverride == null)
		{
			// in case the drop-ship bpartner Override was deleted, also delete the drop-ship Location Override
			olCand.setDropShip_Location_Override(null);
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_C_BPartner_Location dropShipLocation = Services.get(IBPartnerDAO.class).retrieveShipToLocation(ctx, dropShipPartnerOverride.getC_BPartner_ID(), trxName);

		if (dropShipLocation == null)
		{
			// no location was found
			olCand.setDropShip_Location_Override(null);
		}

		olCand.setDropShip_Location_Override(dropShipLocation);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID)
	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID })
	public void onHandOverPartnerOverride(final I_C_OLCand olCand)
	{
		final I_C_BPartner handOverPartnerOverride = olCand.getHandOver_Partner_Override();
		if (handOverPartnerOverride == null)
		{
			// in case the handover bpartner Override was deleted, also delete the handover Location Override
			olCand.setHandOver_Location_Override(null);
		}

		final I_C_BPartner_Location handOverLocation = Services.get(IBPartnerDAO.class).retrieveHandOverLocation(handOverPartnerOverride);

		if (handOverLocation == null)
		{
			// no location was found
			olCand.setHandOver_Location_Override(null);
		}

		olCand.setDropShip_Location_Override(handOverLocation);
	}
}
