package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.OnHandQtyChangedEvent;
import de.metas.material.event.stock.OnHandQtyChangedEvent.OnHandQtyChangedEventBuilder;
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

public class M_Transaction_OnHandQtyEventCreator extends M_Transaction_EventCreator
{
	public static final M_Transaction_OnHandQtyEventCreator INSTANCE = new M_Transaction_OnHandQtyEventCreator();

	private M_Transaction_OnHandQtyEventCreator()
	{
	}

	@Override
	public List<MaterialEvent> createEventsForInOutLine(@NonNull final I_M_Transaction transaction, final boolean deleted)
	{
		return createEventsForModel(transaction.getM_InOutLine(), deleted);
	}

	@Override
	public List<MaterialEvent> createEventsForCostCollector(@NonNull final I_M_Transaction transaction, final boolean deleted)
	{
		return createEventsForModel(transaction.getPP_Cost_Collector(), deleted);
	}

	@Override
	public List<MaterialEvent> createEventsForMovementLine(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		return createEventsForModel(transaction.getM_MovementLine(), deleted);
	}

	private static List<MaterialEvent> createEventsForModel(
			@NonNull final Object huReferencedModel,
			final boolean deleted)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<HuAssignment> huAssignments = huAssignmentDAO
				.retrieveHUAssignmentPojosForModel(huReferencedModel);

		final ImmutableList.Builder<MaterialEvent> result = ImmutableList.builder();
		for (final HuAssignment huAssignment : huAssignments)
		{
			result.addAll(createEventsForHu(huAssignment.getLowestLevelHU(), deleted));
		}

		return result.build();
	}

	private static ArrayList<OnHandQtyChangedEvent> createEventsForHu(
			@NonNull final I_M_HU hu,
			final boolean deleted)
	{
		final OnHandQtyChangedEventBuilder builder = OnHandQtyChangedEvent.builder()
				.warehouseId(hu.getM_Locator().getM_Warehouse_ID())
				.huId(hu.getM_HU_ID());

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

		final IAttributeStorageFactory huAttributeStorageFactory = Services.get(IAttributeStorageFactoryService.class)
				.createHUAttributeStorageFactory(storageFactory);
		final IAttributeStorage attributeStorage = huAttributeStorageFactory.getAttributeStorage(hu);

		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromAttributeSet(attributeStorage)
				.orElse(AttributesKey.NONE);

		final IHUStorage storage = storageFactory.getStorage(hu);
		final List<IHUProductStorage> productStorages = storage.getProductStorages();

		final ArrayList<OnHandQtyChangedEvent> events = new ArrayList<>();

		for (final IHUProductStorage productStorage : productStorages)
		{
			final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
					productStorage.getM_Product_ID(),
					attributesKey,
					AttributeConstants.M_AttributeSetInstance_ID_None);

			final BigDecimal quantity = productStorage.getQtyInStockingUOM();

			final OnHandQtyChangedEvent event = builder.eventDescriptor(EventDescriptor.createNew(hu))
					.productDescriptor(productDescriptor)
					.quantity(deleted ? BigDecimal.ZERO : quantity)
					.quantityDelta(deleted ? quantity.negate() : quantity)
					.build();
			events.add(event);
		}
		return events;
	}

}
