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

package de.metas.ui.web.ddorder.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_OrderLine;

public class WEBUI_DD_OrderLine_MoveSelected_HU extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IViewsRepository viewsRepository = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);

	private static final String PARAM_M_HU_ID = I_M_HU.COLUMNNAME_M_HU_ID;
	@Param(parameterName = PARAM_M_HU_ID, mandatory = true)
	private int mHuID;

	private static final String PARAM_DD_ORDER_LINE_ID = I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID;
	@Param(parameterName = PARAM_DD_ORDER_LINE_ID, mandatory = true)
	private int ddOrderLineId;

	private static final String PARAM_LOCATOR_TO_ID = I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID;
	@Param(parameterName = PARAM_LOCATOR_TO_ID, mandatory = true)
	private int paramLocatorToId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (getSelectedRowIds().isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		else if (huEditorWasNotOpenedFromADDOrderLine())
		{
			return ProcessPreconditionsResolution
					.rejectWithInternalReason("The process should only be available when the HUEditor was opened from a DD_OrderLine window!");
		}
		else if (selectedRowIsNotATopHU())
		{
			return ProcessPreconditionsResolution
					.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{

		final I_DD_OrderLine selectedDDOrderLine = ddOrderDAO.getLineById(DDOrderLineId.ofRepoId(ddOrderLineId));

		final I_M_HU huToMove = huDAO.getById(HuId.ofRepoId(mHuID));

		huDDOrderBL.createMovements()
				.setDDOrderLines(ImmutableList.of(selectedDDOrderLine))
				.setLocatorToIdOverride(paramLocatorToId)
				.setDoDirectMovements(true)
				.setFailIfCannotAllocate(true)
				.allocateHU(huToMove)
				.processWithinOwnTrx();

		return MSG_OK;
	}

	private boolean huEditorWasNotOpenedFromADDOrderLine()
	{
		final ViewId parentViewId = getView().getParentViewId();

		if (parentViewId == null || getView().getParentRowId() == null)
		{
			return true;
		}

		final String parentViewTableName = viewsRepository.getView(parentViewId).getTableNameOrNull();

		return !I_DD_OrderLine.Table_Name.equals(parentViewTableName);
	}

	private boolean selectedRowIsNotATopHU()
	{
		return getView(HUEditorView.class)
				.streamByIds(getSelectedRowIds())
				.noneMatch(HUEditorRow::isTopLevel);
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_DD_OrderLine ddOrderLine = getSelectedDDOrderLine();

		final String parameterName = parameter.getColumnName();
		if (PARAM_M_HU_ID.equals(parameterName))
		{
			return getRecord_ID();
		}
		else if (PARAM_DD_ORDER_LINE_ID.equals(parameterName))
		{
			return ddOrderLine.getDD_OrderLine_ID();
		}
		else if (PARAM_LOCATOR_TO_ID.equals(parameterName))
		{
			return ddOrderLine.getM_LocatorTo_ID();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private I_DD_OrderLine getSelectedDDOrderLine()
	{
		final ViewId parentViewId = getView().getParentViewId();
		final DocumentIdsSelection selectedParentRow = DocumentIdsSelection.of(ImmutableList.of(getView().getParentRowId()));

		final int selectedOrderLineId = viewsRepository.getView(parentViewId)
				.streamByIds(selectedParentRow)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No DD_OrderLine was selected!"))
				.getFieldValueAsInt(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID, -1);

		return ddOrderDAO.getLineById(DDOrderLineId.ofRepoId(selectedOrderLineId));
	}
}
