/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.impl.HandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result;

public class M_Securpharm_Productdata_Result_Retry extends M_HU_SecurpharmScan
{

	private final HandlingUnitsDAO handlingUnitsBL = Services.get(HandlingUnitsDAO.class);

	@Override
	protected I_M_HU getHandlingUnit()
	{
		I_M_Securpharm_Productdata_Result resultEntity = getRecord(I_M_Securpharm_Productdata_Result.class);
		return handlingUnitsBL.getById(HuId.ofRepoId(resultEntity.getM_HU_ID()));
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.reject();
		}
		I_M_Securpharm_Productdata_Result productData = context.getSelectedModel(I_M_Securpharm_Productdata_Result.class);
		if (!productData.isError() && productData.getM_HU_ID() <= 0)
		{
			ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

}
