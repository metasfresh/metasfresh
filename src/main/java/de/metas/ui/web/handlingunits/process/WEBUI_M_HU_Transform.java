package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransferService;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
import de.metas.ui.web.window.model.DocumentCollection;

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
public class WEBUI_M_HU_Transform
		extends HUViewProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final  String SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS = "de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform.AllowNewTUsWithInfiniteCapacity";

	/**
	 *
	 * Enumerates the actions supported by this process. There is an analog {@code AD_Ref_List} in the application dictionary. <b>Please keep it in sync</b>.
	 * <p>
	 * <h1>Important notes</h1>
	 * <b>About the quantity parameter:</b>
	 * <ul>
	 * <li>There is always a source HU (CU or TU) parameter and always a quantity parameter (CU storage-qty or int number of TUs)</li>
	 * <li>The parameter's default value is the maximum that's in the source</li>
	 * <li>the process only ever performs a split (see {@link IHUSplitBuilder}) if the parameter value is decreased. If the user instead goes with the suggested maximum value, then the source HU is "moved" as it is.</li>
	 * <li>For the {@link #CU_To_NewCU} and {@link #TU_To_NewTUs} actions, if the user goes with the suggested maximum value, then nothing is done. But there is a (return) message which gives a brief explanation to the user.
	 * </ul>
	 *
	 */
	public static enum ActionType
	{
		/**
		 * Invokes {@link HUTransferService#cuToNewCU(I_M_HU, BigDecimal)}.
		 */
		CU_To_NewCU,

		/**
		 * Invokes {@link HUTransferService#cuToNewTUs(I_M_HU, BigDecimal, I_M_HU_PI_Item_Product, boolean)}.
		 */
		CU_To_NewTUs,

		/**
		 * Sets {@link I_M_HU#setHUPlanningReceiptOwnerPM(boolean)} for the currently selected TU. The value is neither propagated to a possible parent nor to any children.
		 * <p>
		 * Parameters:
		 * <ul>
		 * <li>the currently selected TU line</li>
		 * <li>{@link WEBUI_M_HU_Transform#PARAM_HUPlanningReceiptOwnerPM_TU}</li>
		 * </ul>
		 */
		TU_Set_Ownership,

		/**
		 * Invokes {@link HUTransferService#cuToExistingTU(I_M_HU, BigDecimal, I_M_HU)}.
		 */
		CU_To_ExistingTU,

		/**
		 * Invokes {@link HUTransferService#tuToNewTUs(I_M_HU, BigDecimal, boolean)}.
		 */
		TU_To_NewTUs,

		/**
		 * Invokes {@link HUTransferService#tuToNewLUs(I_M_HU, BigDecimal, I_M_HU_PI_Item, boolean)}.
		 */
		TU_To_NewLUs,

		/**
		 * Invokes {@link HUTransferService#tuToExistingLU(I_M_HU, BigDecimal, I_M_HU).
		 */
		TU_To_ExistingLU,

		/**
		 * Sets {@link I_M_HU#setHUPlanningReceiptOwnerPM(boolean)} for the currently selected LU. The value is not propagated to any children.
		 * <p>
		 * Parameters:
		 * <ul>
		 * <li>the currently selected LU line</li>
		 * <li>{@link WEBUI_M_HU_Transform#PARAM_HUPlanningReceiptOwnerPM_LU}</li>
		 * </ul>
		 */
		LU_Set_Ownership
	}

	//
	// Parameters
	//
	private static final String PARAM_Action = "Action";
	@Param(parameterName = PARAM_Action, mandatory = true)
	private String p_Action;

	//
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private I_M_HU_PI_Item_Product p_M_HU_PI_Item_Product;

	private static final String PARAM_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_ID)
	private I_M_HU_PI_Item p_M_HU_PI_Item;

	//
	private static final String PARAM_M_TU_HU_ID = "M_TU_HU_ID";
	@Param(parameterName = PARAM_M_TU_HU_ID)
	private I_M_HU p_M_TU_HU;

	//
	private static final String PARAM_M_LU_HU_ID = "M_LU_HU_ID";
	@Param(parameterName = PARAM_M_LU_HU_ID)
	private I_M_HU p_M_LU_HU;

	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;

	//
	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;

	private static final String PARAM_HUPlanningReceiptOwnerPM_LU = "HUPlanningReceiptOwnerPM_LU";
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM_LU)
	private boolean p_HUPlanningReceiptOwnerPM_LU;

	private static final String PARAM_HUPlanningReceiptOwnerPM_TU = "HUPlanningReceiptOwnerPM_TU";
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM_TU)
	private boolean p_HUPlanningReceiptOwnerPM_TU;

	@Autowired
	private DocumentCollection documentsCollection;

	public WEBUI_M_HU_Transform()
	{
		Adempiere.autowire(this);
	}

	/**
	 * @return For the two parameters {@link #PARAM_QtyTU} and {@value #PARAM_QtyCU}, this method returns the "maximum" (i.e. what's inside the currently selected source TU resp. CU).<br>
	 *         For any other parameter, it returns {@link IProcessDefaultParametersProvider#DEFAULT_VALUE_NOTAVAILABLE}.
	 */
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCU.equals(parameter.getColumnName()))
		{
			final I_M_HU cu = getSingleSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return HUTransferService.get(getCtx()).getMaximumQtyCU(cu);
		}

		if (PARAM_QtyTU.equals(parameter.getColumnName()))
		{
			final I_M_HU tu = getSingleSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return HUTransferService.get(getCtx()).getMaximumQtyTU(tu);
		}

		if (PARAM_HUPlanningReceiptOwnerPM_TU.equals(parameter.getColumnName()))
		{
			final I_M_HU tu = getSingleSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return tu.isHUPlanningReceiptOwnerPM();
		}

		if (PARAM_HUPlanningReceiptOwnerPM_LU.equals(parameter.getColumnName()))
		{
			final I_M_HU lu = getSingleSelectedRow().getM_HU(); // should work, because otherwise the param is not even shown.
			return lu.isHUPlanningReceiptOwnerPM();
		}

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
				action_SplitCU_To_ExistingTU(row, p_M_TU_HU, p_QtyCU);
				break;
			}
			case CU_To_NewTUs:
			{
				if (p_M_HU_PI_Item_Product == null)
				{
					throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
				}
				action_SplitCU_To_NewTUs(row, p_M_HU_PI_Item_Product, p_QtyCU, p_HUPlanningReceiptOwnerPM_TU);
				break;
			}
			case TU_Set_Ownership:
			{
				updatePlanningReceiptOwnerPM(row, p_HUPlanningReceiptOwnerPM_TU);
				break;
			}
			case TU_To_NewTUs:
			{
				action_SplitTU_To_NewTUs(row, p_QtyTU);
				break;
			}
			case TU_To_NewLUs:
			{
				if (p_M_HU_PI_Item == null)
				{
					throw new FillMandatoryException(PARAM_M_HU_PI_Item_ID);
				}
				action_SplitTU_To_NewLU(row, p_M_HU_PI_Item, p_QtyTU, p_HUPlanningReceiptOwnerPM_LU);
				break;
			}
			case LU_Set_Ownership:
			{
				updatePlanningReceiptOwnerPM(row, p_HUPlanningReceiptOwnerPM_LU);
				break;
			}
			case TU_To_ExistingLU:
			{
				action_SplitTU_To_ExistingLU(row, p_M_LU_HU, p_QtyTU);
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
	 *
	 * @param row
	 * @param huPlanningReceiptOwnerPM
	 * @task https://github.com/metasfresh/metasfresh/issues/1130
	 */
	private void updatePlanningReceiptOwnerPM(final HUDocumentView row, final boolean huPlanningReceiptOwnerPM)
	{
		final I_M_HU hu = row.getM_HU();
		hu.setHUPlanningReceiptOwnerPM(huPlanningReceiptOwnerPM);
		InterfaceWrapperHelper.save(hu);
	}

	private List<TableRecordReference> getM_ReceiptSchedules()
	{
		return getView()
				.getReferencingDocumentPaths().stream()
				.map(referencingDocumentPath -> documentsCollection.getTableRecordReference(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Split selected CU to an existing TU.
	 *
	 * @param cuRow
	 * @param tuHU
	 * @param qtyCU quantity to split
	 */
	private void action_SplitCU_To_ExistingTU(final HUDocumentView cuRow, final I_M_HU tuHU, final BigDecimal qtyCU)
	{
		HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.cuToExistingTU(cuRow.getM_HU(), qtyCU, tuHU);

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

		// TODO: if qtyCU is the "maximum", then don't do anything, but show a user message
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.cuToNewCU(cuRow.getM_HU(), qtyCU);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	/**
	 * Split selected CU to new top level TUs
	 *
	 * @param cuRow cu row to split
	 * @param qtyCU quantity CU to split
	 * @param isOwnPackingMaterials
	 * @param tuPIItemProductId to TU
	 */
	private void action_SplitCU_To_NewTUs(
			final HUDocumentView cuRow,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final BigDecimal qtyCU,
			final boolean isOwnPackingMaterials)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.cuToNewTUs(cuRow.getM_HU(), qtyCU, tuPIItemProduct, isOwnPackingMaterials);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	// Params:
	// * existing LU (M_HU_ID)
	// * QtyTUs
	private void action_SplitTU_To_ExistingLU(
			final HUDocumentView tuRow,
			final I_M_HU luHU,
			final BigDecimal qtyTU)
	{
		HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.tuToExistingLU(tuRow.getM_HU(), qtyTU, luHU);

		// Notify
		getView().addHUAndInvalidate(luHU);
	}

	/**
	 * Split TU to new LU (only one LU!).
	 *
	 * @param tuRow represents the TU (or TUs in the aggregate-HU-case) that is our split source
	 * @param qtyTU the number of TUs we want to split from the given {@code tuRow}
	 * @param isOwnPackingMaterials
	 * @param tuPIItemProductId
	 * @param luPI
	 */
	private void action_SplitTU_To_NewLU(
			final HUDocumentView tuRow,
			final I_M_HU_PI_Item huPIItem,
			final BigDecimal qtyTU,
			final boolean isOwnPackingMaterials)
	{
		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.tuToNewLUs(tuRow.getM_HU(), qtyTU, huPIItem, isOwnPackingMaterials);

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	/**
	 * Split a given number of TUs from current selected TU line to new TUs.
	 *
	 * @param tuRow
	 * @param qtyTU
	 * @param tuPI
	 */
	private void action_SplitTU_To_NewTUs(
			final HUDocumentView tuRow,
			final BigDecimal qtyTU)
	{
		// TODO: if qtyTU is the "maximum", then don't do anything, but show a user message
		final I_M_HU sourceTuHU = tuRow.getM_HU();

		final List<I_M_HU> createdHUs = HUTransferService.get(getCtx())
				.withReferencedObjects(getM_ReceiptSchedules())
				.tuToNewTUs(sourceTuHU, qtyTU, sourceTuHU.isHUPlanningReceiptOwnerPM());

		// Notify
		getView().addHUsAndInvalidate(createdHUs);
	}

	/**
	 *
	 * @return the actions that are available according to which row is currently selected and to also according to whether there are already existing TUs or LUs in the context.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_Action, dependsOn = {}, numericKey = false)
	private LookupValuesList getActions()
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

		final I_AD_Process_Para processParameter = adProcessDAO.retriveProcessParameter(getCtx(), getProcessInfo().getAD_Process_ID(), PARAM_Action);
		final Collection<I_AD_Ref_List> allActiveActionItems = adReferenceDAO.retrieveListItems(getCtx(), processParameter.getAD_Reference_Value_ID());

		final Set<String> selectableTypes = new HashSet<>();

		if (getSingleSelectedRow().isCU())
		{
			selectableTypes.add(ActionType.CU_To_NewCU.toString());
			selectableTypes.add(ActionType.CU_To_NewTUs.toString());

			final boolean existsTU = getView()
					.streamAllRecursive()
					.anyMatch(((Predicate<HUDocumentView>)row -> row.isTU())
							.and(notAlreadyOurParent()));

			if (existsTU)
			{
				selectableTypes.add(ActionType.CU_To_ExistingTU.toString());
			}
		}

		else if (getSingleSelectedRow().isTU())
		{
			selectableTypes.add(ActionType.TU_To_NewTUs.toString());
			selectableTypes.add(ActionType.TU_To_NewLUs.toString());
			selectableTypes.add(ActionType.TU_Set_Ownership.toString());

			final boolean existsLU = getView()
					.streamAllRecursive()
					.anyMatch(((Predicate<HUDocumentView>)row -> row.isLU())
							.and(notAlreadyOurParent()));

			if (existsLU)
			{
				selectableTypes.add(ActionType.TU_To_ExistingLU.toString());
			}
		}

		else if (getSingleSelectedRow().isLU())
		{
			selectableTypes.add(ActionType.LU_Set_Ownership.toString());
		}

		return allActiveActionItems.stream()
				.filter(item -> selectableTypes.contains(item.getValueName()))
				.map(item -> InterfaceWrapperHelper.translate(item, I_AD_Ref_List.class)) // replace 'item' with its translated version
				.sorted(Comparator.comparing(I_AD_Ref_List::getName))
				.map(item -> StringLookupValue.of(item.getValueName(), item.getName()))
				.collect(LookupValuesList.collect());
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_ExistingTU}.
	 *
	 * @return existing TUs that are available in the current HU editor context, sorted by ID.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_TU_HU_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getTULookupValues()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType == ActionType.CU_To_ExistingTU)
		{
			return getView()
					.streamAllRecursive()
					.filter(row -> row.isTU()) // ..needs to be a TU
					.filter(notAlreadyOurParent()) // ..may not be the one TU that 'cu' is already attached to
					.sorted(Comparator.comparing(HUDocumentView::getM_HU_ID))
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}

		return LookupValuesList.EMPTY;
	}

	/**
	 * Needed when the selected action is {@link ActionType#TU_To_ExistingLU}.
	 *
	 * @return existing LUs that are available in the current HU editor context, sorted by ID.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_LU_HU_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getLULookupValues()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType == ActionType.TU_To_ExistingLU)
		{
			return getView()
					.streamAllRecursive()
					.filter(row -> row.isLU()) // ..needs to be a LU
					.filter(notAlreadyOurParent()) // ..may not be the one LU that 'tu' is already attached to
					.sorted(Comparator.comparing(HUDocumentView::getM_HU_ID))
					.sorted(Comparator.comparing(HUDocumentView::getM_HU_ID))
					.map(row -> row.toLookupValue())
					.collect(LookupValuesList.collect());
		}

		return LookupValuesList.EMPTY;
	}

	/**
	 * Creates and returns a predicate to verify if a HUDocumentView is not the parent of the currently selected HU.
	 */
	private Predicate<HUDocumentView> notAlreadyOurParent()
	{
		final I_M_HU selectedHU = getSingleSelectedRow().getM_HU();
		final I_M_HU parentOfSelectedHU = Services.get(IHandlingUnitsDAO.class).retrieveParent(selectedHU);

		return (row -> parentOfSelectedHU == null || row.getM_HU_ID() != parentOfSelectedHU.getM_HU_ID());
	}

	/**
	 * Needed when the selected action is {@link ActionType#CU_To_NewTUs}.
	 *
	 * @return a list of PI item products that match the selected CU's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_Product_ID, dependsOn = PARAM_Action, numericKey = true)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType == ActionType.CU_To_NewTUs)
		{
			return retrieveHUPItemProductsForNewTU();
		}

		return LookupValuesList.EMPTY;
	}

	private LookupValuesList retrieveHUPItemProductsForNewTU()
	{
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final boolean allowInfiniteCapacity = sysConfigBL.getBooleanValue(SYSCONFIG_ALLOW_INFINIT_CAPACITY_TUS, true,
				Env.getAD_Client_ID(getCtx()), Env.getAD_Org_ID(getCtx()));

		final HUDocumentView cuRow = getSingleSelectedRow();

		final Stream<I_M_HU_PI_Item_Product> stream = hupiItemProductDAO
				.retrieveTUs(getCtx(), cuRow.getM_Product(), cuRow.getM_HU().getC_BPartner(), allowInfiniteCapacity)
				.stream();

		return stream
				.sorted(Comparator.comparing(I_M_HU_PI_Item_Product::getName))
				.map(huPIItemProduct -> IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName()))
				.collect(LookupValuesList.collect());
	}

	/**
	 * Needed when the selected action is {@link ActionType#TU_To_NewLUs}.
	 *
	 * @return a list of HU PI items that link the currently selected TU with a TUperLU-qty and a LU packing instruction.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_ID, dependsOn = { PARAM_Action }, numericKey = true)
	private LookupValuesList getM_HU_PI_Item_ID()
	{
		final ActionType actionType = p_Action == null ? null : ActionType.valueOf(p_Action);
		if (actionType != ActionType.TU_To_NewLUs)
		{
			return LookupValuesList.EMPTY;
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final HUDocumentView tuRow = getSingleSelectedRow();
		final I_M_HU tuHU = tuRow.getM_HU();
		final I_M_HU_PI tuPI = handlingUnitsBL.getEffectivePIVersion(tuHU).getM_HU_PI();

		final List<I_M_HU_PI_Item> luPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, null, tuHU.getC_BPartner());

		return luPIItems.stream()
				.filter(luPIItem -> luPIItem.getM_HU_PI_Version().isCurrent() && luPIItem.getM_HU_PI_Version().isActive() && luPIItem.getM_HU_PI_Version().getM_HU_PI().isActive())
				.map(luPIItem -> IntegerLookupValue.of(luPIItem.getM_HU_PI_Item_ID(), buildHUPIItemString(luPIItem)))
				.sorted(Comparator.comparing(IntegerLookupValue::getDisplayName))
				.collect(LookupValuesList.collect());
	}

	private String buildHUPIItemString(final I_M_HU_PI_Item huPIItem)
	{
		return StringUtils.formatMessage("{} ({} x {})",
				huPIItem.getM_HU_PI_Version().getName(),
				huPIItem.getQty().setScale(0, RoundingMode.HALF_UP), // it's always integer quantities
				huPIItem.getIncluded_HU_PI().getName());
	}
}
