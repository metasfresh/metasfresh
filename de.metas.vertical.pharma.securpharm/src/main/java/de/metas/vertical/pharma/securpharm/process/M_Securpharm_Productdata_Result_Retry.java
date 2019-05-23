/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResultId;

public class M_Securpharm_Productdata_Result_Retry extends M_HU_SecurpharmScan
{

	private final IHandlingUnitsDAO handlingUnitsBL = Services.get(IHandlingUnitsDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
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

		final SecurPharmProductDataResultId productDataResultId = SecurPharmProductDataResultId.ofRepoId(context.getSingleSelectedRecordId());
		final SecurPharmProductDataResult productData = securPharmService.getProductDataResultById(productDataResultId);
		if (!productData.isError() && productData.getHuId() == null)
		{
			ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected I_M_HU getHandlingUnit()
	{
		final SecurPharmProductDataResultId productDataResultId = SecurPharmProductDataResultId.ofRepoId(getRecord_ID());
		final SecurPharmProductDataResult productData = securPharmService.getProductDataResultById(productDataResultId);

		return handlingUnitsBL.getById(productData.getHuId());
	}
}
