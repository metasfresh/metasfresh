package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.ISerialNoDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRow.HUEditorRowHierarchy;
import de.metas.ui.web.handlingunits.HUEditorRowId;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommandResult.WebuiHUTransformCommandResultBuilder;
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
 * HU Transformation command.
 * 
 * Takes {@link WebuiHUTransformParameters} as input and transform given HU by calling the proper {@link HUTransformService} methods.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WebuiHUTransformCommand
{
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
		 * Invokes {@link HUTransformService#cuToNewCU(I_M_HU, BigDecimal)}.
		 */
		CU_To_NewCU,

		/**
		 * Invokes {@link HUTransformService#cuToNewTUs(I_M_HU, BigDecimal, I_M_HU_PI_Item_Product, boolean)}.
		 */
		CU_To_NewTUs,

		/**
		 * Sets {@link I_M_HU#setHUPlanningReceiptOwnerPM(boolean)} for the currently selected TU. The value is neither propagated to a possible parent nor to any children.
		 * <p>
		 * Parameters:
		 * <ul>
		 * <li>the currently selected TU line</li>
		 * <li>{@link WEBUI_M_HU_Transform_Template#PARAM_HUPlanningReceiptOwnerPM_TU}</li>
		 * </ul>
		 */
		TU_Set_Ownership,

		/**
		 * Invokes {@link HUTransformService#cuToExistingTU(I_M_HU, BigDecimal, I_M_HU)}.
		 */
		CU_To_ExistingTU,

		/**
		 * Invokes {@link HUTransformService#tuToNewTUs(I_M_HU, BigDecimal, boolean)}.
		 */
		TU_To_NewTUs,

		/**
		 * Invokes {@link HUTransformService#tuToNewLUs(I_M_HU, BigDecimal, I_M_HU_PI_Item, boolean)}.
		 */
		TU_To_NewLUs,

		/**
		 * Invokes {@link HUTransformService#tuToExistingLU(I_M_HU, BigDecimal, I_M_HU).
		 */
		TU_To_ExistingLU,

		/**
		 * Sets {@link I_M_HU#setHUPlanningReceiptOwnerPM(boolean)} for the currently selected LU. The value is not propagated to any children.
		 * <p>
		 * Parameters:
		 * <ul>
		 * <li>the currently selected LU line</li>
		 * <li>{@link WEBUI_M_HU_Transform_Template#PARAM_HUPlanningReceiptOwnerPM_LU}</li>
		 * </ul>
		 */
		LU_Set_Ownership,

		/**
		 * TODO Docu
		 */
		CUs_With_SerialNumbers
	}

	//
	// Parameters
	private final HUEditorRow _selectedRow;
	private final List<TableRecordReference> _contextDocumentLines;
	private final WebuiHUTransformParameters _parameters;
	private final HUEditorRow.HUEditorRowHierarchy _huEditorRowHierarchy;

	// Services
	final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	final ISerialNoDAO serialNoDAO = Services.get(ISerialNoDAO.class);

	@Builder
	private WebuiHUTransformCommand(
			@NonNull final HUEditorRow selectedRow,
			@Nullable final List<TableRecordReference> contextDocumentLines,
			@NonNull final WebuiHUTransformParameters parameters,
			final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy)
	{
		this._selectedRow = selectedRow;
		this._contextDocumentLines = contextDocumentLines != null ? ImmutableList.copyOf(contextDocumentLines) : ImmutableList.of();
		this._parameters = parameters;
		this._huEditorRowHierarchy = huEditorRowHierarchy;
	}

	private final WebuiHUTransformParameters getParameters()
	{
		return _parameters;
	}

	private final ActionType getActionType()
	{
		return getParameters().getActionType();
	}

	private HUEditorRow getSelectedRow()
	{
		return _selectedRow;
	}

	public List<TableRecordReference> getContextDocumentLines()
	{
		return _contextDocumentLines;
	}

	private HUEditorRowHierarchy getHuEditorRowHierarchy()
	{
		return _huEditorRowHierarchy;
	}

	private final HUTransformService newHUTransformation()
	{
		return HUTransformService.builder()
				.referencedObjects(getContextDocumentLines())
				.build();
	}

	public final WebuiHUTransformCommandResult execute()
	{
		final HUEditorRow row = getSelectedRow();
		final ActionType action = getActionType();
		final WebuiHUTransformParameters parameters = getParameters();

		final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy = getHuEditorRowHierarchy();

		switch (action)
		{
			case CU_To_NewCU:
			{
				return action_SplitCU_To_NewCU(row, parameters.getQtyCU());
			}
			case CU_To_ExistingTU:
			{
				return action_SplitCU_To_ExistingTU(row, parameters.getTuHU(), parameters.getQtyCU());
			}
			case CU_To_NewTUs:
			{
				if (parameters.getHuPIItemProduct() == null)
				{
					throw new FillMandatoryException(WEBUI_M_HU_Transform.PARAM_M_HU_PI_Item_Product_ID);
				}
				return action_SplitCU_To_NewTUs(row, parameters.getHuPIItemProduct(), parameters.getQtyCU(), parameters.isHuPlanningReceiptOwnerPM_TU());
			}
			case TU_Set_Ownership:
			{
				return action_updatePlanningReceiptOwnerPM(row, parameters.isHuPlanningReceiptOwnerPM_TU());
			}
			case TU_To_NewTUs:
			{
				return action_SplitTU_To_NewTUs(row, parameters.getQtyTU());
			}
			case TU_To_NewLUs:
			{
				if (parameters.getHuPIItem() == null)
				{
					throw new FillMandatoryException(WEBUI_M_HU_Transform.PARAM_M_HU_PI_Item_ID);
				}
				return action_SplitTU_To_NewLU(row, parameters.getHuPIItem(), parameters.getQtyTU(), parameters.isHuPlanningReceiptOwnerPM_LU());
			}
			case LU_Set_Ownership:
			{
				return action_updatePlanningReceiptOwnerPM(row, parameters.isHuPlanningReceiptOwnerPM_LU());
			}
			case TU_To_ExistingLU:
			{
				return action_SplitTU_To_ExistingLU(row, parameters.getLuHU(), parameters.getQtyTU());
			}
			case CUs_With_SerialNumbers:
			{
				return action_CreateCUs_With_SerialNumbers(huEditorRowHierarchy, parameters);
			}
			//
			default:
			{
				throw new AdempiereException("@Unknown@ @Action@ " + action);
			}
		}
	}

	private WebuiHUTransformCommandResult action_CreateCUs_With_SerialNumbers(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final WebuiHUTransformParameters parameters)
	{
		final List<String> availableSerialNumbers = parameters.getSerialNumbers();

		final HUEditorRow selectedCuRow = huEditorRowHierarchy.getCuRow();

		final Set<Integer> huIDsChanged = new HashSet<>();
		final Set<Integer> huIDsAdded = new HashSet<>();

		final int qtyCU = selectedCuRow.getQtyCU().intValueExact();
		if (qtyCU == 1)
		{
			final String serialNo = availableSerialNumbers.remove(0);
			assignSerialNumberToCU(selectedCuRow.getM_HU_ID(), serialNo);
			huIDsAdded.add(selectedCuRow.getM_HU_ID());

		}
		else
		{
			final int serialNoCount = availableSerialNumbers.size();
			final int cusToCreateCount = qtyCU < serialNoCount ? qtyCU : serialNoCount;

			final Set<Integer> splittedCUIDs = splitIntoCUs(huEditorRowHierarchy, cusToCreateCount);
			assignSerialNumbersToCUs(splittedCUIDs, availableSerialNumbers);
			huIDsAdded.addAll(splittedCUIDs);
		}

		return WebuiHUTransformCommandResult.builder()
				.huIdsChanged(huIDsChanged)
				.huIdsToAddToView(huIDsAdded)
				.fullViewInvalidation(true) // because it might be that the TU is inside an LU of which we don't know the ID
				.build();
	}

	private Set<Integer> splitIntoCUs(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final int cuNumber)
	{
		if (cuNumber == 0)
		{
			return ImmutableSet.of();
		}

		final Set<Integer> createdCUs = new HashSet<>();

		while (cuNumber > createdCUs.size())
		{
			final int maxCUsAllowedPerBatch = cuNumber - createdCUs.size();
			createdCUs.addAll(createCUsBatch(huEditorRowHierarchy, maxCUsAllowedPerBatch));
		}

		return createdCUs;
	}

	private Set<Integer> createCUsBatch(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final int maxCUsAllowedPerBatch)
	{
		final Set<Integer> addedHUIDs = new HashSet<>();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final HUEditorRow cuRow = huEditorRowHierarchy.getCuRow();

		final HUEditorRow parentRow = huEditorRowHierarchy.getParentRow();

		final int initialQtyCU = cuRow.getQtyCU().intValueExact();

		I_M_HU huToSplit = cuRow.getM_HU();
		int numberOfCUsToCreate;

		if (parentRow == null)
		{
			numberOfCUsToCreate = maxCUsAllowedPerBatch < initialQtyCU ? maxCUsAllowedPerBatch : initialQtyCU;

			for (int i = 0; i < numberOfCUsToCreate; i++)
			{
				final List<I_M_HU> createdCUs = newHUTransformation().cuToNewCU(huToSplit, BigDecimal.ONE);
				addedHUIDs.addAll(createdCUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()));
			}
		}
		else
		{
			Check.assume(parentRow.isTU(), " Parent row must be TU: " + parentRow);
			I_M_HU parentHU = parentRow.getM_HU();

			if (isAggregateHU(parentRow))
			{
				final HUEditorRow luRow = huEditorRowHierarchy.getTopLevelRow();

				parentHU = createNonAggregatedTU(parentRow, luRow);

				huToSplit = handlingUnitsDAO.retrieveIncludedHUs(parentHU).get(0); 
			}

			final int tuCapacity = calculateTUCapacity(parentHU);
			numberOfCUsToCreate = getMinimumOfThree(tuCapacity, maxCUsAllowedPerBatch, initialQtyCU);
			for (int i = 0; i < numberOfCUsToCreate; i++)
			{
				final List<I_M_HU> createdCUs = newHUTransformation().cuToExistingTU(huToSplit, BigDecimal.ONE, parentHU);

				addedHUIDs.addAll(createdCUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()));
			}
		}

		return addedHUIDs;
	}

	private int calculateTUCapacity(final I_M_HU parentHU)
	{
		final I_M_HU_PI_Item_Product huPIP = parentHU.getM_HU_PI_Item_Product();

		if (huPIP == null)
		{
			// TODO
			throw new UnsupportedOperationException();
		}

		return huPIP.getQty().intValueExact();
	}

	private int getMinimumOfThree(final int no1, final int no2, final int no3)
	{
		return no1 < no2 ? (no1 < no3 ? no1 : no3) : (no2 < no3 ? no2 : no3);
	}

	private I_M_HU createNonAggregatedTU(final HUEditorRow tuRow, final HUEditorRow luRow)
	{
		final I_M_HU newTU = newHUTransformation().tuToNewTUs(tuRow.getM_HU(), BigDecimal.ONE).get(0);

		if (luRow != null)
		{
			moveTUToLU(newTU.getM_HU_ID(), luRow, tuRow);
		}

		return newTU;
	}

	private void moveTUToLU(final int tuId, final HUEditorRow luRow, final HUEditorRow tuRow)
	{

		final I_M_HU newTU = create(Env.getCtx(), tuId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		newHUTransformation().tuToExistingLU(newTU, BigDecimal.ONE, luRow.getM_HU());
	}

	private void assignSerialNumbersToCUs(final Set<Integer> cuIDs, final List<String> availableSerialNumbers)
	{

		final List<Integer> listOfCUIDs = cuIDs.stream().collect(ImmutableList.toImmutableList());

		final int numberOfCUs = listOfCUIDs.size();

		for (int i = 0; i < numberOfCUs; i++)
		{
			if (availableSerialNumbers.isEmpty())
			{
				return;
			}

			assignSerialNumberToCU(listOfCUIDs.get(i), availableSerialNumbers.remove(0));
		}
	}

	private void assignSerialNumberToCU(final int huId, final String serialNo)
	{
		final I_M_HU hu = create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_ThreadInherited);

		final IContextAware ctxAware = getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);

		final IAttributeStorage attributeStorage = getAttributeStorage(huContext, hu);

		final I_M_Attribute serialNoAttribute = serialNoDAO.getSerialNoAttribute(ctxAware.getCtx());

		Check.assume(attributeStorage.hasAttribute(serialNoAttribute), "There is no SerialNo attribute defined for the handling unit" + hu);

		attributeStorage.setValue(serialNoAttribute, serialNo.trim());
		attributeStorage.saveChangesIfNeeded();

	}

	private boolean isAggregateHU(final HUEditorRow huRow)
	{
		final I_M_HU hu = huRow.getM_HU();
		return handlingUnitsBL.isAggregateHU(hu);
	}

	private final IAttributeStorage getAttributeStorage(final IHUContext huContext, final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return attributeStorage;
	}

	/**
	 *
	 * @param row
	 * @param huPlanningReceiptOwnerPM
	 * @return
	 * @task https://github.com/metasfresh/metasfresh/issues/1130
	 */
	private WebuiHUTransformCommandResult action_updatePlanningReceiptOwnerPM(final HUEditorRow row, final boolean huPlanningReceiptOwnerPM)
	{
		final I_M_HU hu = row.getM_HU();
		hu.setHUPlanningReceiptOwnerPM(huPlanningReceiptOwnerPM);
		InterfaceWrapperHelper.save(hu);

		return WebuiHUTransformCommandResult.builder()
				.huIdChanged(hu.getM_HU_ID())
				.build();
	}

	/**
	 * Split selected CU to an existing TU.
	 *
	 * @param cuRow
	 * @param tuHU
	 * @param qtyCU quantity to split
	 * @return
	 */
	private WebuiHUTransformCommandResult action_SplitCU_To_ExistingTU(final HUEditorRow cuRow, final I_M_HU tuHU, final BigDecimal qtyCU)
	{
		final List<I_M_HU> createdCUs = newHUTransformation().cuToExistingTU(cuRow.getM_HU(), qtyCU, tuHU);

		return WebuiHUTransformCommandResult.builder()
				.huIdChanged(cuRow.getHURowId().getTopLevelHUId())
				.huIdChanged(Services.get(IHandlingUnitsBL.class).getTopLevelParent(tuHU).getM_HU_ID())
				.huIdsCreated(createdCUs.stream().map(hu -> hu.getM_HU_ID()).collect(ImmutableList.toImmutableList()))
				.build();
	}

	/**
	 * Split selected CU to a new CU.
	 *
	 * @param cuRow
	 * @param qtyCU
	 * @return
	 */
	private WebuiHUTransformCommandResult action_SplitCU_To_NewCU(final HUEditorRow cuRow, final BigDecimal qtyCU)
	{
		// TODO: if qtyCU is the "maximum", then don't do anything, but show a user message
		final List<I_M_HU> createdHUs = newHUTransformation().cuToNewCU(cuRow.getM_HU(), qtyCU);

		final ImmutableSet<Integer> createdHUIds = createdHUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet());

		return WebuiHUTransformCommandResult.builder()
				.huIdChanged(cuRow.getHURowId().getTopLevelHUId())
				.huIdsToAddToView(createdHUIds)
				.huIdsCreated(createdHUIds)
				.build();
	}

	/**
	 * Split selected CU to new top level TUs
	 *
	 * @param cuRow cu row to split
	 * @param qtyCU quantity CU to split
	 * @param isOwnPackingMaterials
	 * @param tuPIItemProductId to TU
	 * @return
	 */
	private WebuiHUTransformCommandResult action_SplitCU_To_NewTUs(
			final HUEditorRow cuRow, final I_M_HU_PI_Item_Product tuPIItemProduct, final BigDecimal qtyCU, final boolean isOwnPackingMaterials)
	{
		final List<I_M_HU> createdHUs = newHUTransformation().cuToNewTUs(cuRow.getM_HU(), qtyCU, tuPIItemProduct, isOwnPackingMaterials);
		final ImmutableSet<Integer> createdHUIds = createdHUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet());
		return WebuiHUTransformCommandResult.builder()
				.huIdChanged(cuRow.getHURowId().getTopLevelHUId())
				.huIdsToAddToView(createdHUIds)
				.huIdsCreated(createdHUIds)
				.build();
	}

	private WebuiHUTransformCommandResult action_SplitTU_To_ExistingLU(final HUEditorRow tuRow, final I_M_HU luHU, final BigDecimal qtyTU)
	{
		newHUTransformation().tuToExistingLU(tuRow.getM_HU(), qtyTU, luHU);

		final HUEditorRowId tuRowId = tuRow.getHURowId();
		return WebuiHUTransformCommandResult.builder()
				.huIdChanged(tuRowId.getTopLevelHUId())
				.huIdChanged(luHU.getM_HU_ID())
				.fullViewInvalidation(true) // because it might be that the TU is inside an LU of which we don't know the ID
				.build();
	}

	/**
	 * Split TU to new LU (only one LU!).
	 *
	 * @param tuRow represents the TU (or TUs in the aggregate-HU-case) that is our split source
	 * @param qtyTU the number of TUs we want to split from the given {@code tuRow}
	 * @param isOwnPackingMaterials
	 * @param tuPIItemProductId
	 * @param luPI
	 * @return
	 */
	private WebuiHUTransformCommandResult action_SplitTU_To_NewLU(
			final HUEditorRow tuRow, final I_M_HU_PI_Item huPIItem, final BigDecimal qtyTU, final boolean isOwnPackingMaterials)
	{
		final List<I_M_HU> createdHUs = newHUTransformation().tuToNewLUs(tuRow.getM_HU(), qtyTU, huPIItem, isOwnPackingMaterials);

		return WebuiHUTransformCommandResult.builder()
				.huIdsToAddToView(createdHUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()))
				.huIdChanged(tuRow.getHURowId().getTopLevelHUId())
				.fullViewInvalidation(true) // because it might be that the TU is inside an LU of which we don't know the ID
				.build();
	}

	/**
	 * Split a given number of TUs from current selected TU line to new TUs.
	 *
	 * @param tuRow
	 * @param qtyTU
	 * @param tuPI
	 * @return
	 */
	private WebuiHUTransformCommandResult action_SplitTU_To_NewTUs(final HUEditorRow tuRow, final BigDecimal qtyTU)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		// TODO: if qtyTU is the "maximum", then don't do anything, but show a user message
		final I_M_HU fromTU = tuRow.getM_HU();
		final I_M_HU fromTopLevelHU = handlingUnitsBL.getTopLevelParent(fromTU);

		final List<I_M_HU> createdHUs = newHUTransformation().tuToNewTUs(fromTU, qtyTU);

		final WebuiHUTransformCommandResultBuilder resultBuilder = WebuiHUTransformCommandResult.builder()
				.huIdsToAddToView(createdHUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()));

		if (handlingUnitsBL.isDestroyedRefreshFirst(fromTopLevelHU))
		{
			resultBuilder.huIdToRemoveFromView(fromTopLevelHU.getM_HU_ID());
		}
		else
		{
			resultBuilder.huIdChanged(fromTopLevelHU.getM_HU_ID());
		}

		return resultBuilder.build();
	}

}
