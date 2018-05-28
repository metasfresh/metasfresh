package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUAssignmentDAO.HuAssignment;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.HUDescriptor.HUDescriptorBuilder;
import de.metas.material.event.commons.ProductDescriptor;
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

public class M_Transaction_HuDescriptor
{
	public static final M_Transaction_HuDescriptor INSTANCE = new M_Transaction_HuDescriptor();

	private M_Transaction_HuDescriptor()
	{
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForInOutLine(
			@NonNull final I_M_InOutLine inOutLine,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(inOutLine, deleted);
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForCostCollector(
			@NonNull final I_PP_Cost_Collector costCollector,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(costCollector, deleted);
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForMovementLine(
			@NonNull final I_M_MovementLine movementLine,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(movementLine, deleted);
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForInventoryLine(
			@NonNull final I_M_InventoryLine inventoryLine,
			final boolean deleted)
	{
		return createHUDescriptorsForModel(inventoryLine, deleted);
	}

	private static ImmutableList<HUDescriptor> createHUDescriptorsForModel(
			@NonNull final Object huReferencedModel,
			final boolean deleted)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final List<HuAssignment> huAssignments = huAssignmentDAO
				.retrieveHUAssignmentPojosForModel(huReferencedModel);

		final ImmutableList.Builder<HUDescriptor> result = ImmutableList.builder();
		for (final HuAssignment huAssignment : huAssignments)
		{
			result.addAll(createHuDescriptors(huAssignment.getLowestLevelHU(), deleted));
		}

		return result.build();
	}

	private static ImmutableList<HUDescriptor> createHuDescriptors(
			@NonNull final I_M_HU hu,
			final boolean deleted)
	{
		final HUDescriptorBuilder builder = HUDescriptor.builder()
				.huId(hu.getM_HU_ID());

		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		final IHUStorage storage = huContext.getHUStorageFactory().getStorage(hu);

		// note that we could have the AttributesKey without making an ASI, but we need the ASI-ID for display reasons in the material dispo window.
		final IPair<AttributesKey, Integer> attributesKeyAndAsiId = createAttributesKeyAndAsiId(hu);

		final List<IHUProductStorage> productStorages = storage.getProductStorages();
		final ImmutableList.Builder<HUDescriptor> descriptors = ImmutableList.builder();
		for (final IHUProductStorage productStorage : productStorages)
		{
			final ProductDescriptor productDescriptor = ProductDescriptor
					.forProductAndAttributes(
							productStorage.getM_Product_ID(),
							attributesKeyAndAsiId.getLeft(),
							attributesKeyAndAsiId.getRight());

			final BigDecimal quantity = productStorage.getQtyInStockingUOM();

			final HUDescriptor descriptor = builder
					.productDescriptor(productDescriptor)
					.quantity(deleted ? BigDecimal.ZERO : quantity)
					.quantityDelta(deleted ? quantity.negate() : quantity)
					.build();
			descriptors.add(descriptor);
		}
		return descriptors.build();
	}

	private static IPair<AttributesKey, Integer> createAttributesKeyAndAsiId(@NonNull final I_M_HU hu)
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		final IAttributeStorage attributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);

		// we don't want all the non-storage-relevant attributes to pollute the ASI we will display in the material disposition window
		final IAttributeSet storageRelevantSubSet = ImmutableAttributeSet.createSubSet(attributeStorage, I_M_Attribute::isStorageRelevant);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIFromAttributeSet(storageRelevantSubSet);

		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asi.getM_AttributeSetInstance_ID())
				.orElse(AttributesKey.NONE);

		return ImmutablePair.of(attributesKey, asi.getM_AttributeSetInstance_ID());
	}

}
