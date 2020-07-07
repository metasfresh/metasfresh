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
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.i18n.AdMessageKey;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService;
import de.metas.ui.web.pickingV2.productsToPick.rows.WebuiPickHUResult;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class ProductsToPick_PickAndPackSelected extends ProductsToPickViewBasedProcess
{
	private final ProductsToPickRowsService rowsService = SpringContextHolder.instance.getBean(ProductsToPickRowsService.class);

	private final AdMessageKey MSG_SET_DEFAULT_PACKING_INSTRUCTION = AdMessageKey.of("de.metas.ui.web.pickingV2.productsToPick.process.ProductsToPick_PickAndPackSelected.SetDefaultPackingInstruction");
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isPickerProfile())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only picker shall pick");
		}

		if (!rowsService.anyRowsEligibleForPicking(getSelectedRows()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select only rows that can be picked");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		ensureDefaultPackingInstructionExists();
		pick();
		pack();

		invalidateView();

		return MSG_OK;
	}

	private void pick()
	{
		final ImmutableList<WebuiPickHUResult> result = rowsService.pick(getSelectedRows());

		updateViewRowFromPickingCandidate(result);
	}

	private void pack()
	{
		final ImmutableList<WebuiPickHUResult> result = rowsService.setPackingInstruction(getSelectedRows(), getHuPackingInstructionsId());

		updateViewRowFromPickingCandidate(result);
	}

	@NonNull
	private HuPackingInstructionsId getHuPackingInstructionsId()
	{
		final I_M_HU_PI defaultPIForPicking = handlingUnitsDAO.retrievePIDefaultForPicking();
		if (defaultPIForPicking == null)
		{
			throw new AdempiereException(MSG_SET_DEFAULT_PACKING_INSTRUCTION);
		}

		return HuPackingInstructionsId.ofRepoId(defaultPIForPicking.getM_HU_PI_ID());
	}

	private void ensureDefaultPackingInstructionExists()
	{
		getHuPackingInstructionsId();
	}

}
