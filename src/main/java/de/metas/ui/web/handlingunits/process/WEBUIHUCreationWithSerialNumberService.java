package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.ISerialNoDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.Profiles;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.window.model.DocumentCollection;

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
@Profile(Profiles.PROFILE_Webui)
public class WEBUIHUCreationWithSerialNumberService
{
	// Services

	private DocumentCollection documentCollections;

	public static WEBUIHUCreationWithSerialNumberService newInstance()
	{
		return new WEBUIHUCreationWithSerialNumberService();
	}

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final ISerialNoDAO serialNoDAO = Services.get(ISerialNoDAO.class);

	private final Set<Integer> huIDsChanged = new HashSet<>();
	private final Set<Integer> huIDsAdded = new HashSet<>();
	private final Set<Integer> huIDsToRemove = new HashSet<>();

	private HUEditorView view;

	public final WebuiHUTransformCommandResult action_CreateCUs_With_SerialNumbers(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final List<String> availableSerialNumbers)
	{
		view = huEditorRowHierarchy.getView();
		documentCollections = huEditorRowHierarchy.getDocumentCollection();
		final HUEditorRow selectedCuRow = huEditorRowHierarchy.getCuRow();

		final int qtyCU = selectedCuRow.getQtyCU().intValueExact();
		if (qtyCU == 1)
		{
			final String serialNo = availableSerialNumbers.remove(0);
			assignSerialNumberToCU(selectedCuRow.getM_HU_ID(), serialNo);
			huIDsChanged.add(selectedCuRow.getM_HU_ID());

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
				.huIdsToRemoveFromView(huIDsToRemove)
				.huIdsToAddToView(huIDsAdded)
				.build();
	}

	private Set<Integer> splitIntoCUs(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final int cuNumber)
	{
		if (cuNumber == 0)
		{
			return ImmutableSet.of();
		}

		final Set<Integer> splitCUs = new HashSet<>();

		while (cuNumber > splitCUs.size())
		{
			final int maxCUsAllowedPerBatch = cuNumber - splitCUs.size();
			splitCUs.addAll(createCUsBatch(huEditorRowHierarchy, maxCUsAllowedPerBatch));
		}

		return splitCUs;
	}

	private Set<Integer> createCUsBatch(final HUEditorRow.HUEditorRowHierarchy huEditorRowHierarchy, final int maxCUsAllowedPerBatch)
	{
		final Set<Integer> splitCUIDs = new HashSet<>();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final HUEditorRow cuRow = huEditorRowHierarchy.getCuRow();

		final HUEditorRow parentRow = huEditorRowHierarchy.getParentRow();

		final int initialQtyCU = cuRow.getQtyCU().intValueExact();

		I_M_HU huToSplit = cuRow.getM_HU();
		int numberOfCUsToCreate;

		if (parentRow == null)
		{
			// The CU will not be split when its qty gets to 1. So make sure the selected CU gets in the list of split CUs
			final int selectedCUID = huToSplit.getM_HU_ID();
			splitCUIDs.add(selectedCUID);

			numberOfCUsToCreate = maxCUsAllowedPerBatch < initialQtyCU ? maxCUsAllowedPerBatch : initialQtyCU;

			for (int i = 0; i < numberOfCUsToCreate; i++)
			{
				final List<I_M_HU> createdCUs = newHUTransformation().cuToNewCU(huToSplit, BigDecimal.ONE);
				splitCUIDs.addAll(createdCUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()));
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
			numberOfCUsToCreate = Util.getMinimumOfThree(tuCapacity, maxCUsAllowedPerBatch, initialQtyCU);

			for (int i = 0; i < numberOfCUsToCreate; i++)
			{
				final List<I_M_HU> createdCUs = newHUTransformation().cuToExistingTU(huToSplit, BigDecimal.ONE, parentHU);

				splitCUIDs.addAll(createdCUs.stream().map(I_M_HU::getM_HU_ID).collect(ImmutableSet.toImmutableSet()));
			}
		}

		return splitCUIDs;
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

	private I_M_HU createNonAggregatedTU(final HUEditorRow tuRow, final HUEditorRow luRow)
	{
		final I_M_HU newTU = newHUTransformation().tuToNewTUs(tuRow.getM_HU(), BigDecimal.ONE).get(0);

		if (luRow != null)
		{
			final I_M_HU oldLU = luRow.getM_HU();

			if (handlingUnitsBL.isDestroyed(oldLU))
			{
				final I_M_HU_PI_Item luPIItem = oldLU.getM_HU_LUTU_Configuration().getM_LU_HU_PI_Item();

				final List<I_M_HU> tuToNewLUs = newHUTransformation().tuToNewLUs(newTU, BigDecimal.ONE, luPIItem, false);

				huIDsToRemove.add(oldLU.getM_HU_ID());
				huIDsAdded.add(tuToNewLUs.get(0).getM_HU_ID());

			}
			else
			{

				moveTUToLU(newTU.getM_HU_ID(), luRow, tuRow);
			}
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

		Check.errorUnless(attributeStorage.hasAttribute(serialNoAttribute), "There is no SerialNo attribute {} defined for the handling unit {}", serialNoAttribute, hu);

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

	private final HUTransformService newHUTransformation()
	{
		return HUTransformService.builder()
				.referencedObjects(getContextDocumentLines())
				.build();
	}

	/**
	 * @return context document/lines (e.g. the receipt schedules)
	 */
	private List<TableRecordReference> getContextDocumentLines()
	{
		if (view == null)
		{
			return ImmutableList.of();
		}

		return view.getReferencingDocumentPaths()
				.stream()
				.map(referencingDocumentPath -> documentCollections.getTableRecordReference(referencingDocumentPath))
				.collect(GuavaCollectors.toImmutableList());
	}
}
