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

import org.compiere.Adempiere;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.service.SecurPharmHUAttributesScanner;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;

// TODO move to webui for HU splitting and view update
public class M_HU_SecurpharmScan extends JavaProcess implements IProcessPrecondition
{
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	protected final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	public static final String PARAM_DataMatrix = "dataMatrix";
	@Param(mandatory = true, parameterName = PARAM_DataMatrix)
	private String dataMatrixString;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no SecurPharm config");
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		final HuId huId = HuId.ofRepoId(context.getSingleSelectedRecordId());
		final SecurPharmHUAttributesScanner scanner = securPharmService.newHUScanner();
		if (!scanner.isEligible(huId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HU not eligible");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DataMatrixCode dataMatrix = getDataMatrix();
		final I_M_HU handlingUnit = getHandlingUnit();

		securPharmService
				.newHUScanner()
				.scanAndUpdateHUAttributes(dataMatrix, handlingUnit);

		return MSG_OK;
	}

	protected I_M_HU getHandlingUnit()
	{
		final HuId huId = HuId.ofRepoId(getRecord_ID());
		return handlingUnitsRepo.getById(huId);
	}

	protected final DataMatrixCode getDataMatrix()
	{
		// FIXME: DEBUG!!!!
		if (true)
		{
			return DataMatrixCode.ofBase64Encoded("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		}
		return DataMatrixCode.ofString(dataMatrixString);
	}
}
