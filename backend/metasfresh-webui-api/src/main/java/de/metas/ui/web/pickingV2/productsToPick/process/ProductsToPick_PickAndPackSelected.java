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
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.window.datatypes.DocumentId;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.SpringContextHolder;

public class ProductsToPick_PickAndPackSelected extends ProductsToPickViewBasedProcess
{
	private final ProductsToPickHelper productsToPickHelper = SpringContextHolder.instance.getBean(ProductsToPickHelper.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isPickerProfile())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only picker shall pick");
		}

		if (!productsToPickHelper.anyRowsEligibleForPicking(getSelectedRows()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select only rows that can be picked");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		pick();
		pack();

		invalidateView();

		return MSG_OK;
	}

	private void pick()
	{
		final ImmutableList<ImmutablePair<DocumentId, PickingCandidate>> result = productsToPickHelper.pick(getSelectedRows());

		result.forEach(pair -> updateViewRowFromPickingCandidate(pair.getLeft(), pair.getRight()));
	}

	private void pack()
	{
		final ImmutableList<ImmutablePair<DocumentId, PickingCandidate>> result = productsToPickHelper.setPackingInstruction(getSelectedRows(), getHuPackingInstructionsId());

		result.forEach(pair -> updateViewRowFromPickingCandidate(pair.getLeft(), pair.getRight()));
	}

	private HuPackingInstructionsId getHuPackingInstructionsId()
	{
		// TODO tbp: set the correct M_HU_PI here.
		return HuPackingInstructionsId.ofRepoId(1);
	}

}
