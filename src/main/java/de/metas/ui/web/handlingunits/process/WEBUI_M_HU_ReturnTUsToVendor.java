package de.metas.ui.web.handlingunits.process;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.edi.model.I_M_InOutLine;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Partial vendor return.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/2391
 */
public class WEBUI_M_HU_ReturnTUsToVendor extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

	@Param(parameterName = "QtyTU", mandatory = true)
	private int p_QtyTU;

	private I_M_HU topLevelHU;
	private List<I_M_HU> tusToReturn;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if(!isHUEditorView())
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

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_QtyTU <= 0)
		{
			throw new FillMandatoryException("QtyTU");
		}

		topLevelHU = getRecord(I_M_HU.class);

		//
		// Get the original receipt line
		final List<I_M_InOutLine> receiptLines = huAssignmentDAO.retrieveModelsForHU(topLevelHU, I_M_InOutLine.class)
				.stream()
				.filter(inoutLine -> !inoutLine.getM_InOut().isSOTrx()) // material receipt
				.collect(ImmutableList.toImmutableList());
		final I_M_InOutLine receiptLine = ListUtils.singleElement(receiptLines);

		//
		// Split out the TUs we need to return
		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(topLevelHU, p_QtyTU);
		tusToReturn = HUTransformService.newInstance().husToNewTUs(request);
		if (tusToReturn.size() != p_QtyTU)
		{
			throw new AdempiereException(WEBUI_HU_Constants.MSG_NotEnoughTUsFound, new Object[] { p_QtyTU, tusToReturn.size() });
		}

		//
		// Assign the split TUs to the receipt line
		// FIXME: this is a workaround until https://github.com/metasfresh/metasfresh/issues/2392 is implemented 
		tusToReturn.forEach(tu -> huAssignmentBL.createHUAssignmentBuilder()
				.initializeAssignment(getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setM_LU_HU(null)
				.setM_TU_HU(tu)
				.setTopLevelHU(tu)
				.setModel(receiptLine)
				.build());

		//
		// Actually create the vendor return
		final Timestamp movementDate = Env.getDate(getCtx());
		huInOutBL.createVendorReturnInOutForHUs(tusToReturn, movementDate);

		//
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		// If something failed, don't bother
		if (!success)
		{
			return;
		}

		// Remove the TUs from our view (in case of any top level TUs)
		if (tusToReturn != null && !tusToReturn.isEmpty())
		{
			getView().removeHUIds(tusToReturn.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableList.toImmutableList()));
		}

		// Invalidate the view because the top level LU changed too
		getView().invalidateAll();
	}

}
