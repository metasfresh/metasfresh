
package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
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

public class WEBUI_Add_Batch_SerialNo_To_CUs extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String SERIALNO_SEPARATOR = ",";

	private static final String PARAM_SerialNo = "SerialNo";
	@Param(parameterName = PARAM_SerialNo, mandatory = true)
	private String p_SerialNo;

	Set<Integer> changedHUs = new HashSet<>();

	ArrayList<String> _serialNumbers;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!streamSelectedRows(HUEditorRowFilter.select(Select.ALL)).allMatch(huRow -> isEligibleHuRow(huRow)))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_CU));
		}

		return ProcessPreconditionsResolution.accept();

	}

	@Override
	protected String doIt() throws Exception
	{
		orderSelectedCUs().forEach(this::processCURow);

		return MSG_OK;
	}

	private ArrayList<String> getSerialNumbers()
	{
		if (_serialNumbers == null)
		{
			_serialNumbers = parseSerialNumbers();
		}
		return _serialNumbers;

	}

	private ArrayList<String> parseSerialNumbers()
	{
		final String serialNumbersNorm = p_SerialNo
				.replace("\n", SERIALNO_SEPARATOR)
				.replace(";", SERIALNO_SEPARATOR);

		final List<String> serialNosList = Splitter.on(SERIALNO_SEPARATOR)
				.trimResults()
				.omitEmptyStrings()
				.splitToList(serialNumbersNorm);

		return new ArrayList<>(serialNosList);
	}

	@Override
	protected void postProcess(boolean success)
	{
		if (!success)
		{
			return;
		}
		removeSelectedRowsIfHUDestoyed();
		invalidateView();
	}

	private boolean isEligibleHuRow(final HUEditorRow huRow)
	{
		if (!huRow.isCU())
		{
			return false;
		}

		if (huRow.getC_UOM_ID() != IUOMDAO.C_UOM_ID_Each)
		{
			return false;
		}

		return huRow.isHUStatusPlanning() || huRow.isHUStatusActive();

	}

	private ImmutableList<HUEditorRow> orderSelectedCUs()
	{
		return streamSelectedRows(HUEditorRowFilter.select(Select.ALL))
				.sorted(new Comparator<HUEditorRow>()
				{
					@Override
					public int compare(final HUEditorRow o1, final HUEditorRow o2)
					{
						if (!isAggregateHU(o1))
						{
							return -1;
						}
						if (!isAggregateHU(o2))
						{
							return 1;
						}
						return 0;
					}
				}).collect(ImmutableList.toImmutableList());
	}

	private void processCURow(final HUEditorRow selectedCuRow)
	{
		final ArrayList<String> availableSerialNumbers = getSerialNumbers();
		if (availableSerialNumbers.isEmpty())
		{
			return;
		}

		final HUEditorRow parentRow = getParentHURowOrNull(selectedCuRow);

		final HUEditorRow topLevelRow = parentRow == null ? null : getParentHURowOrNull(parentRow);

		final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy = HUEditorRow.HUEditorRowHierarchy.builder()
				.cuRow(selectedCuRow)
				.parentRow(parentRow)
				.topLevelRow(topLevelRow)
				.build();

		final WebuiHUTransformCommandResult result = WEBUIHUCreationWithSerialNumberService.newInstance().action_CreateCUs_With_SerialNumbers(huEditorRowHierarchy, availableSerialNumbers);

		updateViewFromResult(result);

	}

	private HUEditorRow getParentHURowOrNull(final HUEditorRow cuRow)
	{
		if (cuRow == null)
		{
			return null;
		}
		return getView().getParentRowByChildIdOrNull(cuRow.getId());
	}

	private boolean isAggregateHU(final HUEditorRow huRow)
	{
		final I_M_HU hu = huRow.getM_HU();
		return handlingUnitsBL.isAggregateHU(hu);
	}

	private final void updateViewFromResult(final WebuiHUTransformCommandResult result)
	{
		final HUEditorView view = getView();

		view.addHUIds(result.getHuIdsToAddToView());

		view.removeHUIds(result.getHuIdsToRemoveFromView());

		if (!result.getHuIdsChanged().isEmpty())
		{
			removeHUsIfDestroyed(result.getHuIdsChanged());
		}

	}

	/** @return true if view was changed and needs invalidation */
	private final boolean removeSelectedRowsIfHUDestoyed()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return false;
		}
		else if (selectedRowIds.isAll())
		{
			return false;
		}

		final HUEditorView view = getView();
		final ImmutableSet<Integer> selectedHUIds = view.streamByIds(selectedRowIds)
				.map(row -> row.getM_HU_ID())
				.collect(ImmutableSet.toImmutableSet());

		return removeHUsIfDestroyed(selectedHUIds);
	}

	/**
	 * @return true if at least one HU was removed
	 */
	private boolean removeHUsIfDestroyed(final Collection<Integer> huIds)
	{
		final ImmutableSet<Integer> destroyedHUIds = huIds.stream()
				.distinct()
				.map(huId -> load(huId, I_M_HU.class))
				.filter(Services.get(IHandlingUnitsBL.class)::isDestroyed)
				.map(I_M_HU::getM_HU_ID)
				.collect(ImmutableSet.toImmutableSet());
		if (destroyedHUIds.isEmpty())
		{
			return false;
		}

		final HUEditorView view = getView();
		final boolean changes = view.removeHUIds(destroyedHUIds);
		return changes;
	}

}
