/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pickingV2.productsToPick.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService;
import de.metas.ui.web.pickingV2.productsToPick.rows.WebuiPickHUResult;
import org.compiere.SpringContextHolder;

public class ProductsToPick_SetPackingInstructions extends ProductsToPickViewBasedProcess
{
	private final ProductsToPickRowsService rowsService = SpringContextHolder.instance.getBean(ProductsToPickRowsService.class);

	@Param(parameterName = "M_HU_PI_ID", mandatory = true)
	private int p_M_HU_PI_ID;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isPickerProfile())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only picker shall pack");
		}

		if (!rowsService.anyRowsEligibleForPacking(getSelectedRows()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible rows were selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final ImmutableList<WebuiPickHUResult> result = rowsService.setPackingInstruction(getSelectedRows(), getHuPackingInstructionsId());

		updateViewRowFromPickingCandidate(result);

		invalidateView();

		return MSG_OK;
	}

	private HuPackingInstructionsId getHuPackingInstructionsId()
	{
		return HuPackingInstructionsId.ofRepoId(p_M_HU_PI_ID);
	}

}
