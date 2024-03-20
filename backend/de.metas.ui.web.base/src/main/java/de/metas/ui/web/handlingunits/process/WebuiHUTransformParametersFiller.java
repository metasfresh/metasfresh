package de.metas.ui.web.handlingunits.process;

import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
 * Helper class used to fill {@link WebuiHUTransformParameters} (default values, lookups etc).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class WebuiHUTransformParametersFiller
{
	// services
	private final ADReferenceService adReferenceService;
	private final LookupDataSourceFactory lookupDataSourceFactory;
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUStatusBL statusBL = Services.get(IHUStatusBL.class);

	// Context
	private final HUEditorView _view;
	private final HUEditorRow _selectedRow;
	private final ActionType _actionType;
	private final boolean _checkExistingHUsInsideView;
	private final boolean _isMoveToDifferentWarehouseEnabled;

	@Builder
	private WebuiHUTransformParametersFiller(
			@NonNull final ADReferenceService adReferenceService,
			@NonNull LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final HUEditorView view,
			@NonNull final HUEditorRow selectedRow,
			@Nullable final ActionType actionType,
			final boolean checkExistingHUsInsideView,
			final boolean isMoveToDifferentWarehouseEnabled)
	{
		this.adReferenceService = adReferenceService;
		this.lookupDataSourceFactory = lookupDataSourceFactory;
		this._view = view;
		this._selectedRow = selectedRow;
		this._actionType = actionType;
		this._checkExistingHUsInsideView = checkExistingHUsInsideView;
		this._isMoveToDifferentWarehouseEnabled = isMoveToDifferentWarehouseEnabled;
	}

	private HUEditorView getView()
	{
		return _view;
	}

	private HUEditorRow getSelectedRow()
	{
		return _selectedRow;
	}

	private ActionType getActionType()
	{
		return _actionType;
	}

	public boolean isCheckExistingHUsInsideView()
	{
		return _checkExistingHUsInsideView;
	}

	//
	//
	//

	public Object getParameterDefaultValue(final String parameterName)
	{

		if (WEBUI_M_HU_Transform.PARAM_QtyCUsPerTU.equals(parameterName))
		{
			final I_M_HU cu = getSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.

			return HUTransformService.newInstance().getMaximumQtyCU(cu, getSelectedRow().getC_UOM()).toBigDecimal();
		}
		else if (WEBUI_M_HU_Transform.PARAM_QtyTU.equals(parameterName))
		{
			final I_M_HU tu = getSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return HUTransformService.newInstance().getMaximumQtyTU(tu);
		}
		else if (WEBUI_M_HU_Transform.PARAM_HUPlanningReceiptOwnerPM_TU.equals(parameterName))
		{
			return getSelectedRow().isHUPlanningReceiptOwnerPM(); // should work, because otherwise the param is not even shown.
		}
		else if (WEBUI_M_HU_Transform.PARAM_HUPlanningReceiptOwnerPM_LU.equals(parameterName))
		{
			return getSelectedRow().isHUPlanningReceiptOwnerPM(); // should work, because otherwise the param is not even shown.
		}
		else if (WEBUI_M_HU_Transform.PARAM_SHOW_WAREHOUSE_ID.equals(parameterName))
		{
			return getShowWarehouseFlag();
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	/**
	 * @return the actions that are available according to which row is currently selected and to also according to whether there are already existing TUs or LUs in the context.
	 */
	public final LookupValuesList getActions(final AdProcessId processId)
	{
		final Set<String> allowedActions = new HashSet<>();

		final HUEditorRow huRow = getSelectedRow();
		if (huRow.isCU())
		{
			allowedActions.addAll(getActionTypesForCUs());
		}

		else if (huRow.isTU())
		{
			allowedActions.addAll(getActionTypesForTUs());
		}

		else if (huRow.isLU())
		{
			allowedActions.add(ActionType.LU_Set_Ownership.toString());
		}

		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process_Para processParameter = adProcessDAO.retrieveProcessParameter(processId, WEBUI_M_HU_Transform.PARAM_Action);
		final ReferenceId actionsReferenceId = ReferenceId.ofRepoId(processParameter.getAD_Reference_Value_ID());
		final Collection<ADRefListItem> allActiveActionItems = adReferenceService.retrieveListItems(actionsReferenceId);

		final String adLanguage = Env.getAD_Language();

		return allActiveActionItems
				.stream()
				.filter(item -> allowedActions.contains(item.getValueName()))
				.map(item -> StringLookupValue.of(item.getValueName(), item.getName(), item.getDescription()))
				.sorted(Comparator.comparing(lookupValue -> lookupValue.getDisplayName(adLanguage)))
				.collect(LookupValuesList.collect());
	}

	private Set<String> getActionTypesForCUs()
	{
		final Set<String> selectableTypes = new HashSet<>();

		selectableTypes.add(ActionType.CU_To_NewCU.toString());
		selectableTypes.add(ActionType.CU_To_NewTUs.toString());

		final boolean existsTUs;
		if (isCheckExistingHUsInsideView())
		{
			existsTUs = getView().matchesAnyRowRecursive(HUEditorRowFilter.builder()
					.select(Select.TU)
					.excludeHUId(getParentHUIdOfSelectedRow())
					.excludeHUStatus(X_M_HU.HUSTATUS_Destroyed)
					.build());
		}
		else
		{
			existsTUs = true;
		}

		if (existsTUs)
		{
			selectableTypes.add(ActionType.CU_To_ExistingTU.toString());
		}

		return selectableTypes;
	}

	private Set<String> getActionTypesForTUs()
	{
		final Set<String> selectableTypes = new HashSet<>();

		selectableTypes.add(ActionType.TU_To_NewTUs.toString());
		selectableTypes.add(ActionType.TU_To_NewLUs.toString());
		selectableTypes.add(ActionType.TU_Set_Ownership.toString());

		final boolean existsLUs;
		if (isCheckExistingHUsInsideView())
		{
			existsLUs = getView().matchesAnyRowRecursive(HUEditorRowFilter.builder()
					.select(Select.LU)
					.excludeHUId(getParentHUIdOfSelectedRow())
					.excludeHUStatus(X_M_HU.HUSTATUS_Destroyed)
					.build());
		}
		else
		{
			existsLUs = true;
		}
		if (existsLUs)
		{
			selectableTypes.add(ActionType.TU_To_ExistingLU.toString());
		}
		return selectableTypes;
	}

	/**
	 * @return a list of PI item products that match the selected CU's product and partner, sorted by name.
	 */
	public LookupValuesList getM_HU_PI_Item_Products()
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.CU_To_NewTUs)
		{
			final HUEditorRow cuRow = getSelectedRow();
			return retrieveHUPItemProductsForNewTU(cuRow);
		}

		return LookupValuesList.EMPTY;
	}

	public Optional<I_M_HU_PI_Item_Product> getDefaultM_HU_PI_Item_Product()
	{
		final HUEditorRow cuRow = getSelectedRow();
		final ProductId productId = cuRow.getProductId();
		final BPartnerId bpartnerId = IHandlingUnitsBL.extractBPartnerIdOrNull(cuRow.getM_HU());

		final List<I_M_HU_PI_Item_Product> allPIs = WEBUI_ProcessHelper.retrieveHUPIItemProductRecords(
				Env.getCtx(),
				productId,
				bpartnerId,
				false
		);

		return allPIs.stream()
				.filter(hu -> hu.isActive() && hu.isDefaultForProduct())
				.min(Comparator.comparingInt(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID));
	}

	private static LookupValuesList retrieveHUPItemProductsForNewTU(final HUEditorRow cuRow)
	{
		final ProductId productId = cuRow.getProductId();
		final BPartnerId bpartnerId = IHandlingUnitsBL.extractBPartnerIdOrNull(cuRow.getM_HU());

		return WEBUI_ProcessHelper.retrieveHUPIItemProducts(
				Env.getCtx(),
				productId,
				bpartnerId,
				false); // includeVirtualItem = false..moving a cu onto a "virtual" TU makes no sense. Instead, the user can just leave the CU as it is, or take it out of a physical TU
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_ExistingTU}.
	 *
	 * @return existing TUs that are available in the current HU editor context, sorted by ID.
	 */
	public LookupValuesPage getTUsLookupValues(final LookupDataSourceContext context)
	{
		if (isCheckExistingHUsInsideView())
		{
			return getTUsLookupValues_InsideView(context);
		}
		else
		{
			return getTUsLookupValues_All(context);
		}
	}

	private LookupValuesPage getTUsLookupValues_InsideView(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			final LookupValuesList list = getView()
					.streamAllRecursive(HUEditorRowFilter.builder()
							.select(Select.TU) // ..needs to be a TU
							.userInputFilter(context.getFilterOrIfAny(null))
							.excludeHUId(getParentHUIdOfSelectedRow()) // ..may not be the one TU that 'cu' is already attached to
							.excludeHUStatus(X_M_HU.HUSTATUS_Destroyed)
							.build())
					.sorted(Comparator.comparing(row -> row.getHuId().getRepoId()))
					.map(HUEditorRow::toLookupValue)
					.collect(LookupValuesList.collect());

			return LookupValuesPage.allValues(list);
		}

		return LookupValuesPage.EMPTY;
	}

	private LookupValuesPage getTUsLookupValues_All(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			// TODO: cache the descriptor
			// TODO: filter by TUs
			// TODO: search by barcode too
			final LookupDescriptor lookupDescriptor = lookupDataSourceFactory.getLookupDescriptorProviders().sql()
					.setCtxTableName(null) // ctxTableName
					.setCtxColumnName("M_HU_ID")
					.setDisplayType(DisplayType.Search)
					.buildForDefaultScope();

			return findEntries(lookupDescriptor, context);
		}

		return LookupValuesPage.EMPTY;
	}

	/**
	 * @return existing LUs that are available in the current HU editor context, sorted by ID.
	 */
	public LookupValuesPage getLUsLookupValues(final LookupDataSourceContext context)
	{
		if (isCheckExistingHUsInsideView())
		{
			return getLUsLookupValues_InsideView(context);
		}
		else
		{
			return getLUsLookupValues_All(context);
		}
	}

	private LookupValuesPage getLUsLookupValues_InsideView(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			final LookupValuesList list = getView()
					.streamAllRecursive(HUEditorRowFilter.builder()
							.select(Select.LU) // ..needs to be a LU
							.userInputFilter(context.getFilterOrIfAny(null))
							.excludeHUId(getParentHUIdOfSelectedRow()) // ..may not be the one LU that 'tu' is already attached to
							.excludeHUStatus(X_M_HU.HUSTATUS_Destroyed)
							.build())
					.sorted(Comparator.comparing(row -> row.getHuId().getRepoId()))
					.map(HUEditorRow::toLookupValue)
					.collect(LookupValuesList.collect());

			return LookupValuesPage.allValues(list);
		}

		return LookupValuesPage.EMPTY;
	}

	private LookupValuesPage getLUsLookupValues_All(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			// TODO: cache the descriptor
			// TODO: filter by LUs
			// TODO: search by barcode too
			final LookupDescriptor lookupDescriptor = lookupDataSourceFactory.getLookupDescriptorProviders().sql()
					.setCtxTableName(null) // ctxTableName
					.setCtxColumnName("M_HU_ID")
					.setDisplayType(DisplayType.Search)
					.buildForDefaultScope();

			return findEntries(lookupDescriptor, context);
		}
		else
		{
			return LookupValuesPage.EMPTY;
		}
	}

	private LookupValuesPage findEntries(
			final LookupDescriptor lookupDescriptor,
			final LookupDataSourceContext context)
	{
		final LookupDataSource dataSource = lookupDataSourceFactory.getLookupDataSource(lookupDescriptor);
		final IdsToFilter idsToFilter = context.getIdsToFilter();
		if (idsToFilter.isSingleValue())
		{
			final LookupValue result = dataSource.findById(idsToFilter.getSingleValueAsObject());
			return LookupValuesPage.ofNullable(result);
		}
		else if (idsToFilter.isMultipleValues())
		{
			final LookupValuesList result = dataSource.findByIdsOrdered(idsToFilter.getMultipleValues());
			return LookupValuesPage.allValues(result);
		}
		else
		{
			return dataSource
					.findEntities(
							context,
							context.getFilter(),
							context.getOffset(0),
							context.getLimit(10));
		}
	}

	private HuId getParentHUIdOfSelectedRow()
	{
		final HUEditorRow huRow = getSelectedRow();
		final I_M_HU hu = huRow.getM_HU();
		final int parentIdOfSelectedHU = handlingUnitsDAO.retrieveParentId(hu);
		return HuId.ofRepoIdOrNull(parentIdOfSelectedHU);
	}

	public LookupValuesList getM_HU_PI_Item_IDs()
	{
		final ActionType actionType = getActionType();
		if (actionType != ActionType.TU_To_NewLUs)
		{
			return LookupValuesList.EMPTY;
		}

		final List<I_M_HU_PI_Item> luPIItems = getAvailableLUPIItems();

		return luPIItems.stream()
				.filter(luPIItem -> luPIItem.getM_HU_PI_Version().isCurrent() && luPIItem.getM_HU_PI_Version().isActive() && luPIItem.getM_HU_PI_Version().getM_HU_PI().isActive())
				.map(luPIItem -> IntegerLookupValue.of(luPIItem.getM_HU_PI_Item_ID(), WEBUI_ProcessHelper.buildHUPIItemString(luPIItem)))
				.sorted(Comparator.comparing(IntegerLookupValue::getDisplayName))
				.collect(LookupValuesList.collect());
	}

	public I_M_HU_PI_Item getDefaultM_LU_PI_ItemOrNull()
	{
		final List<I_M_HU_PI_Item> luPIItems = getAvailableLUPIItems();
		final Optional<I_M_HU_PI_Item> defaultHUPIItem = luPIItems.stream()
				.filter(luPIItem -> luPIItem.getM_HU_PI_Version().isCurrent() && luPIItem.getM_HU_PI_Version().isActive() && luPIItem.getM_HU_PI_Version().getM_HU_PI().isActive())
				.sorted(Comparator.comparing(I_M_HU_PI_Item::getM_HU_PI_Item_ID)) // TODO what to order by ?
				.findFirst();

		return defaultHUPIItem.orElse(null);
	}

	private List<I_M_HU_PI_Item> getAvailableLUPIItems()
	{
		final HUEditorRow tuRow = getSelectedRow();
		final I_M_HU tuHU = tuRow.getM_HU();
		final I_M_HU_PI_Version effectivePIVersion = handlingUnitsBL.getEffectivePIVersion(tuHU);
		Check.errorIf(effectivePIVersion == null, "tuHU is inconsistent; hu={}", tuHU);

		return handlingUnitsDAO.retrieveParentPIItemsForParentPI(
				effectivePIVersion.getM_HU_PI(),
				null,
				IHandlingUnitsBL.extractBPartnerIdOrNull(tuHU));
	}

	public boolean getShowWarehouseFlag()
	{
		final ActionType currentActionType = getActionType();

		if (currentActionType == null)
		{
			return false;
		}

		final boolean isMoveToWarehouseAllowed = _isMoveToDifferentWarehouseEnabled && statusBL.isStatusActive(getSelectedRow().getM_HU());

		if (!isMoveToWarehouseAllowed)
		{
			return false;
		}

		final boolean showWarehouse;

		switch (currentActionType)
		{
			case CU_To_NewCU:
			case CU_To_NewTUs:
			case TU_To_NewLUs:
			case TU_To_NewTUs:
				showWarehouse = true;
				break;
			default:
				showWarehouse = false;
		}

		return showWarehouse;
	}
}
