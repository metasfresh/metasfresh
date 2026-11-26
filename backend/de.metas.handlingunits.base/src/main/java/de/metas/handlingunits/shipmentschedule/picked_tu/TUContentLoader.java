package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Reads the CUs of a TU: which of them hold a product and, for a given shipment schedule, which of them the schedule picked;
 * and groups them by the attributes that split a shipment line.
 */
public class TUContentLoader
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	/**
	 * @return the CUs of the TU that hold the product
	 */
	public List<I_M_HU> retrieveCUsHoldingProduct(
			@NonNull final I_M_HU tu,
			@NonNull final ProductId productId,
			@NonNull final IHUStorageFactory storageFactory)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(tu)
				.stream()
				.filter(handlingUnitsBL::isVirtual)
				.filter(cu -> {
					final IHUProductStorage productStorage = storageFactory.getStorage(cu).getProductStorageOrNull(productId);
					return productStorage != null && !productStorage.isEmpty();
				})
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @param pickedHuIds the HUs the schedule's picking recorded; if it recorded some, only those CUs count: a TU that several
	 *                    schedules picked into holds the CUs of all of them. If it recorded the TU itself, every CU of the TU counts.
	 * @return the CUs of the TU that hold the product and that the schedule picked
	 */
	public List<I_M_HU> retrieveCUsPickedBySchedule(
			@NonNull final I_M_HU tu,
			@NonNull final ProductId productId,
			@NonNull final Set<HuId> pickedHuIds,
			@NonNull final IHUStorageFactory storageFactory)
	{
		final boolean isEveryCUCounted = pickedHuIds.isEmpty() || pickedHuIds.contains(HuId.ofRepoId(tu.getM_HU_ID()));
		return retrieveCUsHoldingProduct(tu, productId, storageFactory).stream()
				.filter(cu -> isEveryCUCounted || pickedHuIds.contains(HuId.ofRepoId(cu.getM_HU_ID())))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return the product storages of the CUs that hold the product, per attribute combination, in the order of first appearance
	 */
	public ImmutableListMultimap<AttributesKey, IHUProductStorage> groupStoragesByAttributeCombination(
			@NonNull final Collection<I_M_HU> cus,
			@NonNull final ProductId productId,
			@NonNull final IHUContext huContext,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final IAttributeStorageFactory attrFactory = huContext.getHUAttributeStorageFactory();
		final ImmutableListMultimap.Builder<AttributesKey, IHUProductStorage> result = ImmutableListMultimap.builder();
		for (final I_M_HU cu : cus)
		{
			final IHUProductStorage productStorage = storageFactory.getStorage(cu).getProductStorageOrNull(productId);
			if (productStorage == null || productStorage.isEmpty())
			{
				continue;
			}
			result.put(computeUseInASIAttributesKey(cu, attrFactory, attributeSplitsShipmentLine), productStorage);
		}
		return result.build();
	}

	/**
	 * Predicate deciding whether an attribute splits the shipment line, i.e. whether it is whitelisted in
	 * {@code M_ShipmentSchedule_AttributeConfig} for the schedule's handler: the same rule the M_InOutLine
	 * aggregation applies. Handlers are registered once at server startup ({@code InOutCandidateValidator#onInit}),
	 * so during real picking the handler is present and the whitelist restricts the split. It is null only in narrow
	 * init/test contexts that bypass that registration; there the predicate falls back to "no restriction".
	 */
	public Predicate<AttributeId> attributeSplitsShipmentLine(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleHandler handler = shipmentScheduleHandlerBL.getHandlerForOrNull(shipmentSchedule);
		if (handler == null)
		{
			return attributeId -> true;
		}
		return attributeId -> handler.attributeShallBePartOfShipmentLine(shipmentSchedule, attributeDAO.getAttributeRecordById(attributeId));
	}

	private static AttributesKey computeUseInASIAttributesKey(
			@NonNull final I_M_HU cu,
			@NonNull final IAttributeStorageFactory factory,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		// IAttributeStorage does not implement getAttributeValueIdOrNull, so we cannot use
		// AttributesKeys.createAttributesKeyFromAttributeSet here: list-type attributes
		// would be silently dropped. Instead, the key is built directly from IAttributeValue,
		// using the value string for all attribute types (consistent for grouping purposes).
		final ImmutableSet<AttributesKeyPart> parts = factory.getAttributeStorage(cu)
				.getAttributeValues()
				.stream()
				.filter(attributeValue -> attributeValue.isUseInASI() && !attributeValue.isEmpty())
				.filter(attributeValue -> attributeSplitsShipmentLine.test(attributeValue.getAttributeId()))
				.map(TUContentLoader::toAttributesKeyPart)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		return parts.isEmpty() ? AttributesKey.NONE : AttributesKey.ofParts(parts);
	}

	@Nullable
	private static AttributesKeyPart toAttributesKeyPart(@NonNull final IAttributeValue attributeValue)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(attributeValue.getM_Attribute().getM_Attribute_ID());
		if (AttributeValueType.ofCode(attributeValue.getAttributeValueType()).isNumber())
		{
			// Use BigDecimal-specific part so scale-differences like 3.0 vs 3.00 are normalized
			final BigDecimal value = attributeValue.getValueAsBigDecimal();
			return value != null && value.signum() != 0
					? AttributesKeyPart.ofNumberAttribute(attributeId, value)
					: null;
		}
		// String, List and Date attributes all carry their value via getValueAsString()
		final String valueStr = attributeValue.getValueAsString();
		return Check.isNotBlank(valueStr)
				? AttributesKeyPart.ofStringAttribute(attributeId, valueStr)
				: null;
	}
}

