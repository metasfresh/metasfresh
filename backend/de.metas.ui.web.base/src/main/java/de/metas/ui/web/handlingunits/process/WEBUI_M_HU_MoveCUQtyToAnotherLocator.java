package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_M_HU_MoveCUQtyToAnotherLocator extends HUEditorProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@Autowired private DocumentCollection documentsCollection;

	@Param(parameterName = I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, mandatory = true)
	private WarehouseId p_WarehouseId;

	@Param(parameterName = I_M_Locator.COLUMNNAME_M_Locator_ID, mandatory = true)
	private int p_LocatorId;

	static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal p_qty;

	private HUMovementGeneratorResult movementResult = null;

	@Override
	@OverridingMethodsMustInvokeSuper
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not the HU view");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		final HUEditorRow huRow = getView().getById(rowId);

		if (!huRow.isCU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HU is not CU");
		}

		if (!huRow.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HUStatus is not Active");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@ProcessParamLookupValuesProvider(parameterName = I_M_Locator.COLUMNNAME_M_Locator_ID, numericKey = true, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.lookup, dependsOn = { I_M_Warehouse.COLUMNNAME_M_Warehouse_ID })
	public LookupValuesList getAvailableLocators(final LookupDataSourceContext evalCtx)
	{
		final List<org.compiere.model.I_M_Locator> locatorsToLoad = p_WarehouseId != null ?
				warehouseDAO.getLocators(p_WarehouseId, I_M_Locator.class) :
				Collections.emptyList();

		return locatorsToLoad.stream()
				.sorted(Comparator.comparing(org.compiere.model.I_M_Locator::getValue))
				.map(locator -> IntegerLookupValue.of(locator.getM_Locator_ID(), locator.getValue()))
				.filter(evalCtx.getFilterPredicate())
				.collect(LookupValuesList.collect());
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_Qty.equals(parameter.getColumnName()))
		{
			final HUEditorRow huEditorRow = getSingleSelectedRow();
			return huEditorRow != null
					? huEditorRow.getQtyCU()
					: BigDecimal.ZERO;
		}

		// Fallback
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final HUEditorRow huEditorRow = getSingleSelectedRow();
		final BigDecimal huQty = huEditorRow.getQtyCU() != null
				? huEditorRow.getQtyCU()
				: BigDecimal.ZERO;

		if (p_qty.signum() <= 0 || huQty.compareTo(p_qty) < 0)
		{
			throw new AdempiereException("Not Valid Qty");
		}

		// split HU
		if (huQty.compareTo(p_qty) > 0)
		{
			final WebuiHUTransformParameters parameters = WebuiHUTransformParameters.builder()
					.actionType(WebuiHUTransformCommand.ActionType.CU_To_NewCU)
					.qtyCU(p_qty)
					.build();

			final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
					.selectedRow(huEditorRow)
					.contextDocumentLines(getContextDocumentLines())
					.parameters(parameters)
					.build();

			final WebuiHUTransformCommandResult result = command.execute();
			moveToLocator(result);
			updateViewFromResult(result);
		}

		// In case of full Qty, move the top level HU
		if (huQty.compareTo(p_qty) == 0)
		{
			final I_M_HU cuHU = huEditorRow.getM_HU();
			final I_M_HU huMove = getTopLevelHU(cuHU);
			if (huMove != null)
			{
				final LocatorId toLocatorId = LocatorId.ofRepoId(p_WarehouseId, p_LocatorId);
				movementResult = huMovementBL.moveHUsToLocator(ImmutableList.of(huMove), toLocatorId);
			}
		}

		return MSG_OK;
	}

	private I_M_HU getTopLevelHU(@NonNull final I_M_HU cuHU)
	{
		final boolean isTopLevel = handlingUnitsBL.isTopLevel(cuHU);
		return (isTopLevel)
				? cuHU
				: handlingUnitsBL.getTopLevelParent(cuHU);

	}

	private List<TableRecordReference> getContextDocumentLines()
	{
		return getView().getReferencingDocumentPaths()
				.stream()
				.map(referencingDocumentPath -> documentsCollection.getTableRecordReference(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList());
	}

	private void moveToLocator(@NonNull final WebuiHUTransformCommandResult result)
	{
		final ImmutableList<I_M_HU> createdHUs = result.getHuIdsCreated()
				.stream()
				.map(handlingUnitsDAO::getById)
				.collect(ImmutableList.toImmutableList());

		huMovementBL.moveHUsToLocator(createdHUs, LocatorId.ofRepoId(p_WarehouseId, p_LocatorId));
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (movementResult != null)
		{
			getView().invalidateAll();
		}
	}

	private void updateViewFromResult(final WebuiHUTransformCommandResult result)
	{
		final HUEditorView view = getView();

		boolean changes = false;
		if (view.addHUIds(result.getHuIdsToAddToView()))
		{
			changes = true;
		}
		if (view.removeHUIds(result.getHuIdsToRemoveFromView()))
		{
			changes = true;
		}
		//
		if (changes)
		{
			view.invalidateAll();
		}
	}

}
