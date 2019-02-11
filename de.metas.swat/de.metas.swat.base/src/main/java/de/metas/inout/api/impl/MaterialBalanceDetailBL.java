package de.metas.inout.api.impl;

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


import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;

import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IMaterialBalanceConfigDAO;
import de.metas.inout.api.IMaterialBalanceDetailBL;
import de.metas.inout.api.IMaterialBalanceDetailDAO;
import de.metas.inout.api.MaterialBalanceConfig;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_Material_Balance_Detail;
import de.metas.util.Services;

public class MaterialBalanceDetailBL implements IMaterialBalanceDetailBL
{

	@Override
	public void addInOutToBalance(final I_M_InOut inOut)
	{
		// services
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IMaterialBalanceConfigDAO materialBalanceConfigDAO = Services.get(IMaterialBalanceConfigDAO.class);
		final IMaterialBalanceDetailDAO materialBalanceDetailDAO = Services.get(IMaterialBalanceDetailDAO.class);

		final Iterator<I_M_InOutLine> lines = inoutDAO.retrieveAllLines(inOut).iterator();

		if (!lines.hasNext())
		{
			// do nothing
			return;
		}

		while (lines.hasNext())
		{
			final I_M_InOutLine line = lines.next();

			final MaterialBalanceConfig balanceConfig = materialBalanceConfigDAO.retrieveFitBalanceConfig(line);

			if (balanceConfig == null)
			{
				// in case there was no balance config entry that fits the given line, it means that the line is not important in balancing
				continue;
			}

			// If a suitable balance config was found, add the line to the according Material Balance Detail
			materialBalanceDetailDAO.addLineToBalance(line, balanceConfig);
		}

	}

	@Override
	public void resetMaterialDetailsForConfigAndDate(final MaterialBalanceConfig config, final Timestamp resetDate)
	{
		// services
		final IMaterialBalanceDetailDAO materialBalanceDAO = Services.get(IMaterialBalanceDetailDAO.class);

		final List<I_M_Material_Balance_Detail> detailsToReset = materialBalanceDAO.retrieveDetailsForConfigAndDate(config, resetDate);

		for (final I_M_Material_Balance_Detail materialBalanceDetail : detailsToReset)
		{
			materialBalanceDetail.setIsReset(true);
			materialBalanceDetail.setResetDate(resetDate);
			InterfaceWrapperHelper.save(materialBalanceDetail);
		}

	}

	@Override
	public void resetAllMaterialDetailsForDate(final Timestamp resetDate)
	{
		resetMaterialDetailsForConfigAndDate(null, resetDate);
	}

}
