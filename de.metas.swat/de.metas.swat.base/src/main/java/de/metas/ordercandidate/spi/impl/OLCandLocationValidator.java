package de.metas.ordercandidate.spi.impl;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09623_old_incoice_location_taken_sometimes_in_excel_import_%28104714160405%29
 */
@Component
public class OLCandLocationValidator implements IOLCandValidator
{
	// error messages
	private static final String ERR_Bill_Location_Inactive = "ERR_Bill_Location_Inactive";
	private static final String ERR_C_BPartner_Location_Effective_Inactive = "ERR_C_BPartner_Location_Effective_Inactive";
	private static final String ERR_DropShip_Location_Inactive = "ERR_DropShip_Location_Inactive";
	private static final String ERR_HandOver_Location_Inactive = "ERR_HandOver_Location_Inactive";

	@Override
	public boolean validate(final I_C_OLCand olCand)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final IOLCandEffectiveValuesBL olcandEffectiveBL = Services.get(IOLCandEffectiveValuesBL.class);

		// Error messages about which of the locations are not active
		final StringBuilder msg = new StringBuilder();

		// flag to tell if the OLCand locations are valid. In case one of them is not, the flag will be false.
		boolean isValid = true;

		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);

		// Bill Location
		final I_C_BPartner_Location billLocation = olcandEffectiveBL.getBill_Location_Effective(olCand);

		if (billLocation != null && !billLocation.isActive())
		{
			final String localMsg = msgBL.getMsg(ctx, ERR_Bill_Location_Inactive);
			msg.append(localMsg + "\n");
			isValid = false;
		}

		// C_BPartner_Location_Effective

		final I_C_BPartner_Location bpLocationEffective = olcandEffectiveBL.getC_BP_Location_Effective(olCand);
		if (bpLocationEffective != null && !bpLocationEffective.isActive())
		{
			final String localMsg = msgBL.getMsg(ctx, ERR_C_BPartner_Location_Effective_Inactive);
			msg.append(localMsg + "\n");
			isValid = false;
		}

		// DropShip_Location
		final I_C_BPartner_Location dropShipLocation = olcandEffectiveBL.getDropShip_Location_Effective(olCand);

		if (dropShipLocation != null && !dropShipLocation.isActive())
		{
			final String localMsg = msgBL.getMsg(ctx, ERR_DropShip_Location_Inactive);
			msg.append(localMsg + "\n");
			isValid = false;
		}

		// HandOver_Location
		final I_C_BPartner_Location handOverLocation = olcandEffectiveBL.getHandOver_Location_Effective(olCand);
		if (handOverLocation != null && !handOverLocation.isActive())
		{
			final String localMsg = msgBL.getMsg(ctx, ERR_HandOver_Location_Inactive);
			msg.append(localMsg + "\n");
			isValid = false;
		}

		if (!isValid)
		{
			olCand.setIsError(true);
			olCand.setErrorMsg(Services.get(IMsgBL.class).parseTranslation(ctx, msg.toString()));
			return false;
		}

		return true;

	}
}
