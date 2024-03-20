package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
 * HU transformation process (template).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_Transform
		extends HUEditorProcessTemplate
		implements IProcessPrecondition,
		IProcessParametersCallout,
		IProcessDefaultParametersProvider
{
	// Services
	@Autowired private DocumentCollection documentsCollection;
	@Autowired private ADReferenceService adReferenceService;
	@Autowired private LookupDataSourceFactory lookupDataSourceFactory;

	private final HUTransformService huTransformService = HUTransformService.newInstance();
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private static final String SYS_CONFIG_ENABLE_MOVEMENT = "WEBUI_M_HU_Transform.enableMovement";

	//
	// Parameters
	//
	public static final String PARAM_CheckExistingHUsInsideView = WEBUI_M_HU_Transform.class.getName() + ".checkExistingHUsInsideView";

	protected static final String PARAM_Action = "Action";
	@Param(parameterName = PARAM_Action, mandatory = true)
	private String p_Action;

	//
	protected static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private I_M_HU_PI_Item_Product p_M_HU_PI_Item_Product;

	protected static final String PARAM_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_ID)
	private I_M_HU_PI_Item p_M_HU_PI_Item;

	//
	protected static final String PARAM_M_TU_HU_ID = "M_TU_HU_ID";
	@Param(parameterName = PARAM_M_TU_HU_ID)
	private I_M_HU p_M_TU_HU;

	//
	protected static final String PARAM_M_LU_HU_ID = "M_LU_HU_ID";
	@Param(parameterName = PARAM_M_LU_HU_ID)
	private I_M_HU p_M_LU_HU;

	//
	protected static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU)
	private BigDecimal p_QtyCUsPerTU;

	//
	protected static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;

	protected static final String PARAM_HUPlanningReceiptOwnerPM_LU = "HUPlanningReceiptOwnerPM_LU";
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM_LU)
	private boolean p_HUPlanningReceiptOwnerPM_LU;

	protected static final String PARAM_HUPlanningReceiptOwnerPM_TU = "HUPlanningReceiptOwnerPM_TU";
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM_TU)
	private boolean p_HUPlanningReceiptOwnerPM_TU;

	protected static final String PARAM_MOVE_TO_WAREHOUSE_ID = "MoveToWarehouseId";
	@Param(parameterName = PARAM_MOVE_TO_WAREHOUSE_ID)
	private WarehouseId moveToWarehouseId;

	protected static final String PARAM_SHOW_WAREHOUSE_ID = "ShowWarehouseID";
	@Param(parameterName = PARAM_SHOW_WAREHOUSE_ID)
	public boolean showWarehouse;

	private final boolean isMoveToDifferentWarehouseEnabled = sysConfigBL.getBooleanValue(SYS_CONFIG_ENABLE_MOVEMENT, false);

	protected WebuiHUTransformParametersFiller newParametersFiller()
	{
		final HUEditorView view = getView();
		final HUEditorRow selectedRow = getSingleSelectedRow();

		return WebuiHUTransformParametersFiller.builder()
				.adReferenceService(adReferenceService)
				.lookupDataSourceFactory(lookupDataSourceFactory)
				.view(view)
				.selectedRow(selectedRow)
				.actionType(p_Action == null ? null : ActionType.valueOf(p_Action))
				.checkExistingHUsInsideView(view.getParameterAsBoolean(PARAM_CheckExistingHUsInsideView, false))
				.isMoveToDifferentWarehouseEnabled(isMoveToDifferentWarehouseEnabled)
				.build();
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_Action, numericKey = false)
	private LookupValuesList getActions()
	{
		return newParametersFiller().getActions(getProcessInfo().getAdProcessId());
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_ExistingTU}.
	 *
	 * @return existing TUs that are available in the current HU editor context, sorted by ID.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_TU_HU_ID, dependsOn = PARAM_Action, numericKey = true, lookupSource = LookupSource.lookup, lookupTableName = I_M_HU.Table_Name)
	private LookupValuesPage getTULookupValues(final LookupDataSourceContext context)
	{
		return newParametersFiller().getTUsLookupValues(context);
	}

	/**
	 * Needed when the selected action is {@link ActionType#TU_To_ExistingLU}.
	 *
	 * @return existing LUs that are available in the current HU editor context, sorted by ID.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_LU_HU_ID, dependsOn = PARAM_Action, numericKey = true, lookupSource = LookupSource.lookup, lookupTableName = I_M_HU.Table_Name)
	private LookupValuesPage getLULookupValues(final LookupDataSourceContext context)
	{
		return newParametersFiller().getLUsLookupValues(context);
	}

	/**
	 * Needed when the selected action is {@link ActionType#TU_To_NewLUs}.
	 *
	 * @return a list of HU PI items that link the currently selected TU with a TUperLU-qty and a LU packing instruction.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_ID, dependsOn = PARAM_Action, numericKey = true, lookupTableName = I_M_HU_PI_Item.Table_Name)
	private LookupValuesList getM_HU_PI_Item_ID()
	{
		return newParametersFiller().getM_HU_PI_Item_IDs();
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_NewTUs}.
	 *
	 * @return a list of PI item products that match the selected CU's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_Product_ID, dependsOn = PARAM_Action, numericKey = true, lookupTableName = I_M_HU_PI_Item_Product.Table_Name)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		return newParametersFiller().getM_HU_PI_Item_Products();
	}

	/**
	 * @return For the two parameters {@link #PARAM_QtyTU} and {@value #PARAM_QtyCUsPerTU}, this method returns the "maximum" (i.e. what's inside the currently selected source TU resp. CU).<br>
	 * For any other parameter, it returns {@link IProcessDefaultParametersProvider#DEFAULT_VALUE_NOTAVAILABLE}.
	 */
	@Override
	public final Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();
		return newParametersFiller().getParameterDefaultValue(parameterName);
	}

	/**
	 * This process is applicable if there is exactly one HU-row selected
	 */
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final WebuiHUTransformParameters parameters = WebuiHUTransformParameters.builder()
				.actionType(p_Action == null ? null : ActionType.valueOf(p_Action))
				.huPIItemProduct(p_M_HU_PI_Item_Product)
				.huPIItem(p_M_HU_PI_Item)
				.tuHU(p_M_TU_HU)
				.luHU(p_M_LU_HU)
				.qtyCU(p_QtyCUsPerTU)
				.qtyTU(p_QtyTU)
				.huPlanningReceiptOwnerPM_TU(ActionType.valueOf(p_Action).equals(ActionType.TU_Set_Ownership) != p_HUPlanningReceiptOwnerPM_TU)
				.huPlanningReceiptOwnerPM_LU(ActionType.valueOf(p_Action).equals(ActionType.LU_Set_Ownership) != p_HUPlanningReceiptOwnerPM_LU)
				.build();

		final WebuiHUTransformCommand command = WebuiHUTransformCommand.builder()
				.selectedRow(getSingleSelectedRow())
				.contextDocumentLines(getContextDocumentLines())
				.parameters(parameters)
				.build();

		final WebuiHUTransformCommandResult result = command.execute();
		moveToWarehouse(result);

		updateViewFromResult(result);

		return MSG_OK;
	}

	/**
	 * @return context document/lines (e.g. the receipt schedules)
	 */
	private List<TableRecordReference> getContextDocumentLines()
	{
		return getView().getReferencingDocumentPaths()
				.stream()
				.map(referencingDocumentPath -> documentsCollection.getTableRecordReference(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList());
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
		if (!result.getHuIdsChanged().isEmpty())
		{
			removeHUsIfDestroyed(result.getHuIdsChanged());
			changes = true;
		}
		if (removeSelectedRowsIfHUDestoyed())
		{
			changes = true;
		}
		//
		if (changes)
		{
			view.invalidateAll();
		}
	}

	/**
	 * @return true if view was changed and needs invalidation
	 */
	private boolean removeSelectedRowsIfHUDestoyed()
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
		final ImmutableSet<HuId> selectedHUIds = view.streamByIds(selectedRowIds)
				.map(HUEditorRow::getHuId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return removeHUsIfDestroyed(selectedHUIds);
	}

	/**
	 * @return true if at least one HU was removed
	 */
	private boolean removeHUsIfDestroyed(final Collection<HuId> huIds)
	{
		final ImmutableSet<HuId> destroyedHUIds = huIds.stream()
				.distinct()
				.map(huId -> load(huId, I_M_HU.class))
				.filter(Services.get(IHandlingUnitsBL.class)::isDestroyed)
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (destroyedHUIds.isEmpty())
		{
			return false;
		}

		final HUEditorView view = getView();
		return view.removeHUIds(destroyedHUIds);
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		final String actionName = p_Action;

		if (ActionType.TU_To_NewLUs.toString().equals(actionName))
		{
			onParameterChanged_ActionTUToNewLUs(parameterName);
		}

		if (PARAM_Action.equals(parameterName) && ActionType.CU_To_NewTUs.toString().equals(actionName))
		{
			onParameterChanged_ActionCUToNewTUs();
		}

		if (PARAM_Action.equals(parameterName))
		{
			setShowWarehouseFlag();
		}
	}

	private void onParameterChanged_ActionTUToNewLUs(final String parameterName)
	{
		@SuppressWarnings("ConstantConditions") // at this point i don't think the HU can be null.
		final BigDecimal realTUQty = huTransformService.getMaximumQtyTU(getSingleSelectedRow().getM_HU());

		if (PARAM_Action.equals(parameterName))
		{
			final I_M_HU_PI_Item defaultHUPIItem = newParametersFiller().getDefaultM_LU_PI_ItemOrNull();
			p_M_HU_PI_Item = defaultHUPIItem;

			if (defaultHUPIItem != null)
			{
				p_QtyTU = realTUQty.min(defaultHUPIItem.getQty());
			}
		}
		else if (PARAM_M_HU_PI_Item_ID.equals(parameterName) && p_M_HU_PI_Item != null)
		{
			p_QtyTU = realTUQty.min(p_M_HU_PI_Item.getQty());
		}
	}

	private void onParameterChanged_ActionCUToNewTUs()
	{
		final Optional<I_M_HU_PI_Item_Product> packingItemOptional = newParametersFiller().getDefaultM_HU_PI_Item_Product();

		if (packingItemOptional.isPresent())
		{
			final BigDecimal realCUQty = getSingleSelectedRow().getQtyCU();

			p_M_HU_PI_Item_Product = packingItemOptional.get();
			p_QtyCUsPerTU = realCUQty.min(packingItemOptional.get().getQty());
		}
	}

	private void moveToWarehouse(final WebuiHUTransformCommandResult result)
	{
		if (moveToWarehouseId != null && showWarehouse)
		{
			final ImmutableList<I_M_HU> createdHUs = result.getHuIdsCreated()
					.stream()
					.map(handlingUnitsDAO::getById)
					.collect(ImmutableList.toImmutableList());

			huMovementBL.moveHUsToWarehouse(createdHUs, moveToWarehouseId);
		}
	}

	private void setShowWarehouseFlag()
	{
		this.showWarehouse = newParametersFiller().getShowWarehouseFlag();

		if (!this.showWarehouse)
		{
			this.moveToWarehouseId = null;
		}
	}
}
