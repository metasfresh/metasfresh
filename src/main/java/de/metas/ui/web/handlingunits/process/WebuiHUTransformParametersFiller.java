package de.metas.ui.web.handlingunits.process;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowQuery;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Builder;
import lombok.NonNull;

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
 *
 */
public class WebuiHUTransformParametersFiller
{
	// services
	private final transient IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// Context
	private final HUEditorView _view;
	private final HUEditorRow _selectedRow;
	private final ActionType _actionType;
	private final boolean _checkExistingHUsInsideView;

	@Builder
	private WebuiHUTransformParametersFiller(@NonNull final HUEditorView view,
			@NonNull final HUEditorRow selectedRow,
			@Nullable final ActionType actionType,
			final boolean checkExistingHUsInsideView)
	{
		this._view = view;
		this._selectedRow = selectedRow;
		this._actionType = actionType;
		this._checkExistingHUsInsideView = checkExistingHUsInsideView;
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
		if (WEBUI_M_HU_Transform.PARAM_QtyCU.equals(parameterName))
		{
			final I_M_HU cu = getSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return HUTransformService.get().getMaximumQtyCU(cu);
		}
		else if (WEBUI_M_HU_Transform.PARAM_QtyTU.equals(parameterName))
		{
			final I_M_HU tu = getSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return HUTransformService.get().getMaximumQtyTU(tu);
		}
		else if (WEBUI_M_HU_Transform.PARAM_HUPlanningReceiptOwnerPM_TU.equals(parameterName))
		{
			return getSelectedRow().isHUPlanningReceiptOwnerPM(); // should work, because otherwise the param is not even shown.
		}
		else if (WEBUI_M_HU_Transform.PARAM_HUPlanningReceiptOwnerPM_LU.equals(parameterName))
		{
			return getSelectedRow().isHUPlanningReceiptOwnerPM(); // should work, because otherwise the param is not even shown.
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	/**
	 * @return the actions that are available according to which row is currently selected and to also according to whether there are already existing TUs or LUs in the context.
	 */
	public final LookupValuesList getActions(final int processId)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process_Para processParameter = adProcessDAO.retriveProcessParameter(Env.getCtx(), processId, WEBUI_M_HU_Transform.PARAM_Action);
		final int actionsReferenceId = processParameter.getAD_Reference_Value_ID();
		final Collection<ADRefListItem> allActiveActionItems = adReferenceDAO.retrieveListItems(actionsReferenceId);

		final Set<String> selectableTypes = new HashSet<>();

		final HUEditorRow huRow = getSelectedRow();
		if (huRow.isCU())
		{
			selectableTypes.add(ActionType.CU_To_NewCU.toString());
			selectableTypes.add(ActionType.CU_To_NewTUs.toString());

			final boolean existsTUs;
			if (isCheckExistingHUsInsideView())
			{
				existsTUs = getView().matchesAnyRowRecursive(HUEditorRowQuery.builder()
						.rowType(HUEditorRowType.TU)
						.excludeHUId(getParentHUIdOfSelectedRow())
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
		}

		else if (huRow.isTU())
		{
			selectableTypes.add(ActionType.TU_To_NewTUs.toString());
			selectableTypes.add(ActionType.TU_To_NewLUs.toString());
			selectableTypes.add(ActionType.TU_Set_Ownership.toString());

			final boolean existsLUs;
			if (isCheckExistingHUsInsideView())
			{
				existsLUs = getView().matchesAnyRowRecursive(HUEditorRowQuery.builder()
						.rowType(HUEditorRowType.LU)
						.excludeHUId(getParentHUIdOfSelectedRow())
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
		}

		else if (huRow.isLU())
		{
			selectableTypes.add(ActionType.LU_Set_Ownership.toString());
		}

		final String adLanguage = Env.getAD_Language();

		return allActiveActionItems.stream()
				.filter(item -> selectableTypes.contains(item.getValueName()))
				.map(item -> StringLookupValue.of(item.getValueName(), item.getName()))
				.sorted(Comparator.comparing(lookupValue -> lookupValue.getDisplayName(adLanguage)))
				.collect(LookupValuesList.collect());
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

	private static LookupValuesList retrieveHUPItemProductsForNewTU(final HUEditorRow cuRow)
	{
		// final HUEditorRow cuRow = getSingleSelectedRow();
		final I_M_Product product = cuRow.getM_Product();
		final I_C_BPartner bPartner = cuRow.getM_HU().getC_BPartner();

		return WEBUI_ProcessHelper.retrieveHUPIItemProducts(Env.getCtx(), product, bPartner);
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_ExistingTU}.
	 *
	 * @return existing TUs that are available in the current HU editor context, sorted by ID.
	 */
	public LookupValuesList getTUsLookupValues(final LookupDataSourceContext context)
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

	private LookupValuesList getTUsLookupValues_InsideView(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			return getView()
					.streamAllRecursive(HUEditorRowQuery.builder()
							.stringFilter(context.getFilterOrIfAny(null))
							.rowType(HUEditorRowType.TU) // ..needs to be a TU
							.excludeHUId(getParentHUIdOfSelectedRow()) // ..may not be the one TU that 'cu' is already attached to
							.build())
					.sorted(Comparator.comparing(HUEditorRow::getM_HU_ID))
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}

		return LookupValuesList.EMPTY;
	}

	private LookupValuesList getTUsLookupValues_All(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			// TODO: cache the descriptor
			// TODO: filter by TUs
			// TODO: search by barcode too
			final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.builder()
					.setColumnName("M_HU_ID")
					.setDisplayType(DisplayType.Search)
					.buildForDefaultScope();
			LookupDataSource dataSource = LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
			final LookupValuesList result = dataSource.findEntities(context, context.getFilter(), context.getOffset(0), context.getLimit(10));
			return result;
		}

		return LookupValuesList.EMPTY;
	}

	/**
	 * @return existing LUs that are available in the current HU editor context, sorted by ID.
	 */
	public LookupValuesList getLUsLookupValues(final LookupDataSourceContext context)
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

	private LookupValuesList getLUsLookupValues_InsideView(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			return getView()
					.streamAllRecursive(HUEditorRowQuery.builder()
							.stringFilter(context.getFilterOrIfAny(null))
							.rowType(HUEditorRowType.LU) // ..needs to be a LU
							.excludeHUId(getParentHUIdOfSelectedRow()) // ..may not be the one LU that 'tu' is already attached to
							.build())
					.sorted(Comparator.comparing(HUEditorRow::getM_HU_ID))
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}

		return LookupValuesList.EMPTY;
	}

	private LookupValuesList getLUsLookupValues_All(final LookupDataSourceContext context)
	{
		final ActionType actionType = getActionType();
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			// TODO: cache the descriptor
			// TODO: filter by LUs
			// TODO: search by barcode too
			final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.builder()
					.setColumnName("M_HU_ID")
					.setDisplayType(DisplayType.Search)
					.buildForDefaultScope();
			LookupDataSource dataSource = LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
			final LookupValuesList result = dataSource.findEntities(context, context.getFilter(), context.getOffset(0), context.getLimit(10));
			return result;
		}
		else
		{
			return LookupValuesList.EMPTY;
		}
	}

	private int getParentHUIdOfSelectedRow()
	{
		final HUEditorRow huRow = getSelectedRow();
		final I_M_HU hu = huRow.getM_HU();
		final int parentIdOfSelectedHU = handlingUnitsDAO.retrieveParentId(hu);
		return parentIdOfSelectedHU;
	}

	public LookupValuesList getM_HU_PI_Item_IDs()
	{
		final ActionType actionType = getActionType();
		if (actionType != ActionType.TU_To_NewLUs)
		{
			return LookupValuesList.EMPTY;
		}

		final HUEditorRow tuRow = getSelectedRow();
		final I_M_HU tuHU = tuRow.getM_HU();
		final I_M_HU_PI tuPI = handlingUnitsBL.getEffectivePIVersion(tuHU).getM_HU_PI();

		final List<I_M_HU_PI_Item> luPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, null, tuHU.getC_BPartner());

		return luPIItems.stream()
				.filter(luPIItem -> luPIItem.getM_HU_PI_Version().isCurrent() && luPIItem.getM_HU_PI_Version().isActive() && luPIItem.getM_HU_PI_Version().getM_HU_PI().isActive())
				.map(luPIItem -> IntegerLookupValue.of(luPIItem.getM_HU_PI_Item_ID(), buildHUPIItemString(luPIItem)))
				.sorted(Comparator.comparing(IntegerLookupValue::getDisplayName))
				.collect(LookupValuesList.collect());
	}

	private static String buildHUPIItemString(final I_M_HU_PI_Item huPIItem)
	{
		return StringUtils.formatMessage("{} ({} x {})",
				huPIItem.getM_HU_PI_Version().getName(),
				huPIItem.getQty().setScale(0, RoundingMode.HALF_UP), // it's always integer quantities
				huPIItem.getIncluded_HU_PI().getName());
	}
}
