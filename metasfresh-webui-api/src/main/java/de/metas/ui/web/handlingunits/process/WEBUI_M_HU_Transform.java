package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_C_BPartner;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransferService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;

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

@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_Transform extends HUViewProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	public static enum ActionType
	{
		CU_To_NewCU //
		, CU_To_ExistingTU //
		, CU_To_NewTUs //
		, TU_To_NewTUs //
		, TU_To_NewLU //
		, TU_To_ExistingLU //
	}

	//
	// Parameters
	private static final String PARAM_Action = "Action";

	//
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";

	//
	private static final String PARAM_M_TU_HU_ID = "M_TU_HU_ID";

	//
	private static final String PARAM_M_LU_HU_ID = "M_LU_HU_ID";

	//
	private static final String PARAM_QtyCU = "QtyCU";

	//
	private static final String PARAM_QtyTU = "QtyTU";

	private static final String PARAM_HUPlanningReceiptOwnerPM = "HUPlanningReceiptOwnerPM";

	//
	// Services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@Param(parameterName = PARAM_Action, mandatory = true)
	private String p_Action;
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private I_M_HU_PI_Item_Product p_M_HU_PI_Item_Product;
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	@Param(parameterName = PARAM_M_TU_HU_ID)
	private I_M_HU p_M_TU_HU;
	@Param(parameterName = PARAM_M_LU_HU_ID)
	private I_M_HU p_M_LU_HU;
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM)
	private boolean p_HUPlanningReceiptOwnerPM;

	public WEBUI_M_HU_Transform()
	{
		Adempiere.autowire(this);
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	/**
	 * This process is applicable if there is exactly one HU-row selected
	 */
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();

		}
		if (getSingleSelectedRow().isLU())
		{
			return ProcessPreconditionsResolution.reject("Only applicable for CUs and TUs");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final HUDocumentView row = getSingleSelectedRow();
		final ActionType action = ActionType.valueOf(p_Action);
		switch (action)
		{
			case CU_To_NewCU:
			{
				action_SplitCU_To_NewCU(row, p_QtyCU);
				break;
			}
			case CU_To_ExistingTU:
			{
				action_SplitCU_To_ExistingTU(row, p_QtyCU, p_M_TU_HU);
				break;
			}
			case CU_To_NewTUs:
			{
				action_SplitCU_To_NewTUs(row, p_QtyCU, p_M_HU_PI_Item_Product, p_HUPlanningReceiptOwnerPM);
				break;
			}
			//
			case TU_To_NewTUs:
			{
				action_SplitTU_To_NewTUs(row, p_QtyTU, p_M_HU_PI_Item_Product, p_HUPlanningReceiptOwnerPM);
				break;
			}
			case TU_To_NewLU:
			{
				if (p_M_HU_PI_Item_Product == null)
				{
					throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
				}
				final I_C_BPartner bpartner = getSingleSelectedRow().getM_HU().getC_BPartner();
				final I_M_HU_PI_Item luPIItem = findLU_HU_PI_Item(p_M_HU_PI_Item_Product, p_M_LU_HU_PI_ID, bpartner);
				action_SplitTU_To_NewLU(row, p_QtyTU, p_M_HU_PI_Item_Product, luPIItem, p_HUPlanningReceiptOwnerPM);
				break;
			}
			case TU_To_ExistingLU:
			{
				action_SplitTU_To_ExistingLU(row, p_QtyTU, p_M_LU_HU);
				break;
			}
			//
			default:
			{
				throw new AdempiereException("@Unknown@ @Action@ " + action);
			}
		}

		return MSG_OK;
	}

	/**
	 * Split selected CU to an existing TU.
	 *
	 * @param cuRow
	 * @param qtyCU quantity to split
	 * @param tuHU
	 */
	private void action_SplitCU_To_ExistingTU(final HUDocumentView cuRow, final BigDecimal qtyCU, final I_M_HU tuHU)
	{
		HUTransferService.get(getCtx())
				.splitCU_To_ExistingTU(cuRow.getM_HU(), cuRow.getM_Product(), cuRow.getC_UOM(), qtyCU, tuHU);

		// Notify
		getView().addHUAndInvalidate(tuHU);
	}

	/**
	 * Split selected CU to a new CU.
	 *
	 * @param cuRow
	 * @param qtyCU
	 */
	private void action_SplitCU_To_NewCU(final HUDocumentView cuRow, final BigDecimal qtyCU)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.splitCU_To_NewCU(cuRow.getM_HU(), cuRow.getM_Product(), cuRow.getC_UOM(), qtyCU);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	/**
	 * Split selected CU to new top level TUs
	 *
	 * @param cuRow cu row to split
	 * @param qtyCU quantity CU to split
	 * @param tuPIItemProductId to TU
	 * @param isOwnPackingMaterials
	 */
	private void action_SplitCU_To_NewTUs(final HUDocumentView cuRow, final BigDecimal qtyCU, final I_M_HU_PI_Item_Product tuPIItemProduct, final boolean isOwnPackingMaterials)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.splitCU_To_NewTUs(cuRow.getM_HU(), cuRow.getM_Product(), cuRow.getC_UOM(), qtyCU, tuPIItemProduct, isOwnPackingMaterials);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	// Params:
	// * existing LU (M_HU_ID)
	// * QtyTUs
	private void action_SplitTU_To_ExistingLU(
			final HUDocumentView tuRow //
			, final BigDecimal qtyTU //
			, final I_M_HU luHU)
	{
		HUTransferService.get(getCtx())
				.splitTU_To_ExistingLU(tuRow.getM_HU(), qtyTU, luHU);

		// Notify
		getView().addHUAndInvalidate(luHU);
	}

	/**
	 * Split TU to new LU (only one LU!).
	 *
	 * @param tuRow represents the TU (or TUs in the aggregate-HU-case) that is our split source
	 * @param qtyTU the number of TUs we want to split from the given {@code tuRow}
	 * @param tuPIItemProductId
	 * @param luPIItemId
	 * @param isOwnPackingMaterials
	 */
	private void action_SplitTU_To_NewLU( //
			final HUDocumentView tuRow //
			, final BigDecimal qtyTU //
			, final I_M_HU_PI_Item_Product tuPIItemProduct //
			, final I_M_HU_PI_Item luPIItem //
			, final boolean isOwnPackingMaterials //
	)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.splitTU_To_NewLU(tuRow.getM_HU(), qtyTU, tuPIItemProduct, luPIItem, isOwnPackingMaterials);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	/**
	 * Split a given number of TUs from current selected TU line to new TUs.
	 *
	 * @param tuRow
	 * @param qtyTU
	 * @param tuPIItemProductId
	 * @param isOwnPackingMaterials
	 */
	private void action_SplitTU_To_NewTUs(final HUDocumentView tuRow //
			, final BigDecimal qtyTU //
			, final I_M_HU_PI_Item_Product tuPIItemProduct //
			, final boolean isOwnPackingMaterials //
	)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.splitTU_To_NewTUs(tuRow.getM_HU(), qtyTU, tuPIItemProduct, isOwnPackingMaterials);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	private final I_M_HU_PI_Item findLU_HU_PI_Item(final I_M_HU_PI_Item_Product tuPIItemProduct, final int luPI_ID, final I_C_BPartner bpartner)
	{
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartner)
				.stream()
				.filter(piItem -> piItem.getM_HU_PI_Version().getM_HU_PI_ID() == luPI_ID)
				.findFirst()
				.orElseThrow(() -> new AdempiereException(tuPI.getName() + " cannot be loaded to " + p_M_LU_HU_PI_ID));

		return luPI_Item;
	}

	/**
	 * 
	 * @return the action that are available according to which row is currently selected and to also according to whether there are already existing TUs or LUs in the context.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_Action, dependsOn = {}, numericKey = false)
	private LookupValuesList getActions()
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

		final I_AD_Process_Para processParameter = adProcessDAO.retriveProcessParameter(getCtx(), getProcessInfo().getAD_Process_ID(), PARAM_Action);
		final Collection<I_AD_Ref_List> allActionItems = adReferenceDAO.retrieveListItems(getCtx(), processParameter.getAD_Reference_Value_ID());

		final Set<String> selectableTypes = new HashSet<>();

		if (getSingleSelectedRow().isCU())
		{
			final boolean existsTU = getView()
					.streamAllRecursive()
					.anyMatch(row -> row.isTU());

			selectableTypes.add(ActionType.CU_To_NewCU.toString());
			selectableTypes.add(ActionType.CU_To_NewTUs.toString());
			if (existsTU)
			{
				selectableTypes.add(ActionType.CU_To_ExistingTU.toString());
			}
		}

		if (getSingleSelectedRow().isTU())
		{
			final boolean existsLU = getView()
					.streamAllRecursive()
					.anyMatch(row -> row.isLU());

			selectableTypes.add(ActionType.TU_To_NewTUs.toString());
			selectableTypes.add(ActionType.TU_To_NewLU.toString());
			if (existsLU)
			{
				selectableTypes.add(ActionType.TU_To_ExistingLU.toString());
			}
		}

		return allActionItems.stream()
				.filter(item -> selectableTypes.contains(item.getValueName()))
				.map(item -> InterfaceWrapperHelper.translate(item, I_AD_Ref_List.class)) // replace 'item' with its translated version
				.map(item -> StringLookupValue.of(item.getValueName(), item.getName()))
				.collect(LookupValuesList.collect());
	}

	/**
	 * 
	 * @return existing TUs that are available in the current HU editor context.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_TU_HU_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getTULookupValues()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			return getView()
					.streamAllRecursive()
					.filter(row -> row.isTU())
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}
		else
		{
			return LookupValuesList.EMPTY;
		}
	}

	/**
	 * 
	 * @return existing LUs that are available in the current HU editor context.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_LU_HU_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getLULookupValues()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			return getView()
					.streamAllRecursive()
					.filter(row -> row.isLU())
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}
		else
		{
			return LookupValuesList.EMPTY;
		}
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_Product_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		switch (actionType)
		{
			case CU_To_NewTUs:
				return retrieveHUPItemProductsForNewTU();

			case TU_To_NewTUs:
				return retrieveHUPItemProductsForNewTU();
				
			case TU_To_NewLU:
				return retrieveHUPItemProductsForNewTU(); // here we also create new TUs, in addition to the new LU

			default:
				return LookupValuesList.EMPTY;
		}
	}

	private LookupValuesList retrieveHUPItemProductsForNewTU()
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);

		final HUDocumentView huRow = getSingleSelectedRow();

		final Stream<I_M_HU_PI_Item_Product> stream = hupiItemProductDAO
				.retrieveTUs(getCtx(), huRow.getM_Product(), huRow.getM_HU().getC_BPartner())
				.stream();
		return stream
				.map(huPIItemProduct -> IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName()))
				.collect(LookupValuesList.collect());
	}

	/**
	 * Retrieves all LU packing instructions that can be used with the currently selected {@link #PARAM_M_HU_PI_Item_Product_ID}.
	 * 
	 * @return
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_LU_HU_PI_ID, dependsOn = { PARAM_Action, PARAM_M_HU_PI_Item_Product_ID }, numericKey = true)
	private LookupValuesList getM_LU_HU_PI_ID()
	{
		if (p_M_HU_PI_Item_Product == null)
		{
			return LookupValuesList.EMPTY;
		}

		final I_M_HU_PI tuPI = p_M_HU_PI_Item_Product.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		final List<I_M_HU_PI> luPIs = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Item.class, this)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_Item.COLUMN_Included_HU_PI_ID, tuPI.getM_HU_PI_ID())
				.andCollect(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_HU_PI_Version.COLUMN_M_HU_PI_ID)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_M_HU_PI.COLUMN_M_HU_PI_ID).endOrderBy()
				.create()
				.list();

		return luPIs.stream().map(luPI -> IntegerLookupValue.of(luPI.getM_HU_PI_ID(), luPI.getName()))
				.collect(LookupValuesList.collect());
	}
}
