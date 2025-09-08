/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.handlingunits.process;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.internaluse.HUInternalUseInventoryCreateRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;

public class WEBUI_M_HU_MoveToGarbage_Partial extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Param(parameterName = "QtyTU", mandatory = true)
	private int p_qtyTU_Int;

	@Param(parameterName = "C_DocType_ID")
	@Nullable
	private DocTypeId p_internalUseInventoryDocTypeId;

	@Param(parameterName = "Description")
	private String p_description;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		final HUEditorRow huRow = getView().getById(rowId);
		if (huRow.isLU())
		{
			if (!huRow.hasIncludedTUs())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("no TUs");
			}
		}
		else if (huRow.isTU())
		{
			// OK
		}
		else
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a LU or TU");
		}

		if (!huRow.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HUStatus is not Active");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	// @RunOutOfTrx // run in transaction!
	protected String doIt()
	{
		final LUTUResult.TUsList tus = extractTUs();

		inventoryService.moveToGarbage(
				HUInternalUseInventoryCreateRequest.builder()
						.hus(tus.toHURecords())
						.movementDate(Env.getZonedDateTime(getCtx()))
						.internalUseInventoryDocTypeId(p_internalUseInventoryDocTypeId)
						.description(p_description)
						.completeInventory(true)
						.moveEmptiesToEmptiesWarehouse(true)
						.sendNotifications(true)
						.build());

		return MSG_OK;
	}

	private LUTUResult.TUsList extractTUs()
	{
		if (p_qtyTU_Int <= 0)
		{
			throw new FillMandatoryException("QtyTU");
		}
		final QtyTU qtyTU = QtyTU.ofInt(p_qtyTU_Int);

		final I_M_HU topLevelHU = handlingUnitsBL.getById(HuId.ofRepoId(getRecord_ID()));

		final LUTUResult.TUsList tus = HUTransformService.newInstance()
				.husToNewTUs(HUTransformService.HUsToNewTUsRequest.forSourceHuAndQty(topLevelHU, qtyTU.toInt()));
		if (tus.getQtyTU().toInt() != qtyTU.toInt())
		{
			throw new AdempiereException(WEBUI_HU_Constants.MSG_NotEnoughTUsFound, qtyTU.toInt(), tus.getQtyTU());
		}

		return tus;
	}

}
