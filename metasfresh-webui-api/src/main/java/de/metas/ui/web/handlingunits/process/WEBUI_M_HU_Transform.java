package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
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
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

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

	//
	// Services
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	// private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

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
	@Param(parameterName = PARAM_Action, mandatory = true)
	private String p_Action;
	//
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private I_M_HU_PI_Item_Product p_M_HU_PI_Item_Product;
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
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

	private static final String PARAM_HUPlanningReceiptOwnerPM = "HUPlanningReceiptOwnerPM";
	@Param(parameterName = PARAM_HUPlanningReceiptOwnerPM)
	private boolean p_HUPlanningReceiptOwnerPM;

	public WEBUI_M_HU_Transform()
	{
		Adempiere.autowire(this);
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
				final I_C_BPartner bpartner = null; // TODO
				final I_M_HU_PI_Item luPIItem = findLU_HU_PI_Item(p_M_HU_PI_Item_Product, p_M_LU_HU_PI_ID, bpartner);
				action_SplitTU_To_NewLU(row, p_QtyTU, p_M_HU_PI_Item_Product, luPIItem, p_HUPlanningReceiptOwnerPM);
				break;
			}
			case TU_To_ExistingLU:
			{
				action_SplitTU_To_ExistingLU(row, p_QtyTU, p_M_LU_HU, p_HUPlanningReceiptOwnerPM);
				break;
			}
			//
			default:
			{
				throw new AdempiereException("@Unknown@ " + action);
			}
		}

		return MSG_OK;
	}

	// private void actionSplitCUToNew(final HUDocumentView cuRow)
	// {
	// Check.assume(cuRow.isCU(), "CU Row: {}", cuRow);
	//
	// final IMutableHUContext huContextInitial = handlingUnitsBL.createMutableHUContextForProcessing(this);
	// final IHUSplitBuilder splitBuilder = new HUSplitBuilder(huContextInitial);
	//
	// //
	// // "Our" HU, the one which the user selected for split
	// final I_M_HU huToSplit = cuRow.getM_HU();
	// splitBuilder.setHUToSplit(huToSplit);
	//
	// //
	// // DocumentLine / Trx Referenced model (if available)
	// final IHUDocumentLine documentLine = null; // TODO huToSplitKey.findDocumentLineOrNull();
	// splitBuilder.setDocumentLine(documentLine);
	// splitBuilder.setCUTrxReferencedModel(documentLine == null ? null : documentLine.getTrxReferencedModel());
	//
	// splitBuilder.setCUProduct(cuRow.getM_Product());
	// splitBuilder.setCUQty(cuRow.getQtyCU());
	// splitBuilder.setCUUOM(cuRow.getC_UOM());
	//
	// splitBuilder.setCUPerTU(p_QtyCU);
	// splitBuilder.setTUPerLU(p_QtyTU);
	// splitBuilder.setMaxLUToAllocate(BigDecimal.valueOf(Integer.MAX_VALUE));
	//
	// final I_M_HU_PI_Item_Product tuPI_ItemProduct = InterfaceWrapperHelper.create(getCtx(), p_M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
	// final I_M_HU_PI_Item tuPI_Item = tuPI_ItemProduct.getM_HU_PI_Item();
	// splitBuilder.setTU_M_HU_PI_Item(tuPI_Item);
	//
	// // LU
	// final I_M_HU_PI tuPI = tuPI_Item.getM_HU_PI_Version().getM_HU_PI();
	// final I_C_BPartner bpartner = huToSplit.getC_BPartner();
	// final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartner)
	// .stream()
	// .filter(piItem -> piItem.getM_HU_PI_Version().getM_HU_PI_ID() == p_M_LU_HU_PI_ID)
	// .findFirst()
	// .orElseThrow(() -> new AdempiereException(tuPI.getName() + " cannot be loaded to " + p_M_LU_HU_PI_ID));
	// splitBuilder.setLU_M_HU_PI_Item(luPI_Item);
	//
	// final List<I_M_HU> husAfterSplit = splitBuilder.split();
	// getView().addHUsAndInvalidate(husAfterSplit);
	// }

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

	private IAllocationRequest createCUAllocationRequest(final HUDocumentView cuRow, final BigDecimal qtyCU)
	{
		//
		// Create allocation request for the quantity user entered
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(getCtx());
		final IAllocationRequest allocationRequest = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(cuRow.getM_Product())
				.setQuantity(new Quantity(cuRow.getQtyCU(), cuRow.getC_UOM()))
				// .setFromReferencedModel(rs) // TODO: do we need it?
				.setForceQtyAllocation(true)
				.create();

		if (allocationRequest.isZeroQty())
		{
			throw new AdempiereException("@QtyCU@ shall be greather than zero");
		}

		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		// TODO: do we need this?
		// huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		return allocationRequest;
	}

	/**
	 * Split selected CU to a new CU.
	 *
	 * @param cuRow
	 * @param qtyCU
	 */
	private void action_SplitCU_To_NewCU(final HUDocumentView cuRow, final BigDecimal qtyCU)
	{
		final IAllocationRequest request = createCUAllocationRequest(cuRow, qtyCU);
		final IAllocationSource source = HUListAllocationSourceDestination.of(cuRow.getM_HU());
		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();

		//
		// Transfer Qty
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(request);

		//
		// Notify
		getView().addHUsAndInvalidate(destination.getCreatedHUs());
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
		final IAllocationRequest request = createCUAllocationRequest(cuRow, qtyCU);
		final IAllocationSource source = HUListAllocationSourceDestination.of(cuRow.getM_HU());
		final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(tuHU);

		//
		// Transfer Qty
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(request);

		//
		// Notify
		getView().addHUAndInvalidate(tuHU);
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
		final IAllocationRequest request = createCUAllocationRequest(cuRow, qtyCU);
		final IAllocationSource source = HUListAllocationSourceDestination.of(cuRow.getM_HU());
		final IHUProducerAllocationDestination destination = HUProducerDestination.ofM_HU_PI_Item_Product(tuPIItemProduct)
				.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

		//
		// Transfer Qty
		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(request);

		//
		// Notify
		getView().addHUsAndInvalidate(destination.getCreatedHUs());

	}

	/**
	 * Split a given number of TUs from current selected TU(aggregated) line to new TUs.
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
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	/**
	 * Split TU to new LU (only one LU!).
	 *
	 * @param tuRow
	 * @param QtyTU
	 * @param tuPIItemProductId
	 * @param luPIItemId
	 * @param isOwnPackingMaterials
	 */
	private void action_SplitTU_To_NewLU( //
			final HUDocumentView tuRow //
			, final BigDecimal QtyTU //
			, final I_M_HU_PI_Item_Product tuPIItemProduct //
			, final I_M_HU_PI_Item luPIItem //
			, final boolean isOwnPackingMaterials //
	)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}

	// Params:
	// * existing LU (M_HU_ID)
	// * QtyTUs
	private void action_SplitTU_To_ExistingLU(
			final HUDocumentView tuRow //
			, final BigDecimal QtyTU //
			, final I_M_HU luHU //
			, final boolean isOwnPackingMaterials //
	)
	{
		// TODO: implement
		throw new UnsupportedOperationException();
	}
}
