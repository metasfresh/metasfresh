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
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;

public class M_Securpharm_Productdata_Result_Retry extends JavaProcess implements IProcessPrecondition
{
	protected final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	public static final String PARAM_DataMatrix = "dataMatrix";
	@Param(mandatory = true, parameterName = PARAM_DataMatrix)
	private String dataMatrixString;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No SecurPharm config");
		}

		//
		// Retry makes sense only if the SecurPharm product has errors (i.e. was not acquired yet)
		final SecurPharmProductId productDataResultId = SecurPharmProductId.ofRepoId(context.getSingleSelectedRecordId());
		final SecurPharmProduct product = securPharmService.getProductById(productDataResultId);
		if (!product.isError())
		{
			ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DataMatrixCode dataMatrix = getDataMatrix();
		final HuId huId = getHuId();

		securPharmService
				.newHUScanner()
				// TODO: advice the scanner to update the existing SecurPharm product
				.scanAndUpdateHUAttributes(dataMatrix, huId);

		return MSG_OK;
	}

	private HuId getHuId()
	{
		final SecurPharmProductId productId = SecurPharmProductId.ofRepoId(getRecord_ID());
		final SecurPharmProduct product = securPharmService.getProductById(productId);
		return product.getHuId();
	}

	private final DataMatrixCode getDataMatrix()
	{
		return DataMatrixCode.ofString(dataMatrixString);
	}

}
