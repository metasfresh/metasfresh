package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.material.event.commons.HUOnHandQtyChangeDescriptor;
import de.metas.material.event.commons.HUOnHandQtyChangeDescriptor.HUOnHandQtyChangeDescriptorBuilder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class M_Transaction_HuOnHandQtyChangeDescriptor
{
	public static final M_Transaction_HuOnHandQtyChangeDescriptor INSTANCE = new M_Transaction_HuOnHandQtyChangeDescriptor();

	private M_Transaction_HuOnHandQtyChangeDescriptor()
	{
	}

	public List<HUOnHandQtyChangeDescriptor> createHuDescriptorsForInOutLine(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(transaction.getM_InOutLine(), deleted);
	}

	public List<HUOnHandQtyChangeDescriptor> createHuDescriptorsForCostCollector(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(transaction.getPP_Cost_Collector(), deleted);
	}

	public List<HUOnHandQtyChangeDescriptor> createHuDescriptorsForMovementLine(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(transaction.getM_MovementLine(), deleted);
	}

	public List<HUOnHandQtyChangeDescriptor> createHuDescriptorsForInventoryLine(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(transaction.getM_InventoryLine(), deleted);
	}

	private static List<HUOnHandQtyChangeDescriptor> createHUDescriptorsForModel(
			@NonNull final Object huReferencedModel,
			final boolean deleted)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<HuAssignment> huAssignments = huAssignmentDAO
				.retrieveHUAssignmentPojosForModel(huReferencedModel);

		final ImmutableList.Builder<HUOnHandQtyChangeDescriptor> result = ImmutableList.builder();
		for (final HuAssignment huAssignment : huAssignments)
		{
			result.addAll(createHuDescriptors(huAssignment.getLowestLevelHU(), deleted));
		}

		return result.build();
	}

	private static ArrayList<HUOnHandQtyChangeDescriptor> createHuDescriptors(
			@NonNull final I_M_HU hu,
			final boolean deleted)
	{
		final HUOnHandQtyChangeDescriptorBuilder builder = HUOnHandQtyChangeDescriptor.builder()
				.huId(hu.getM_HU_ID());

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

		final IHUStorage storage = storageFactory.getStorage(hu);
		final List<IHUProductStorage> productStorages = storage.getProductStorages();

		final ArrayList<HUOnHandQtyChangeDescriptor> events = new ArrayList<>();

		for (final IHUProductStorage productStorage : productStorages)
		{
			final BigDecimal quantity = productStorage.getQtyInStockingUOM();

			final HUOnHandQtyChangeDescriptor event = builder
					.quantity(deleted ? BigDecimal.ZERO : quantity)
					.quantityDelta(deleted ? quantity.negate() : quantity)
					.build();
			events.add(event);
		}
		return events;
	}

}
