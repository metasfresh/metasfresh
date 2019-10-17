package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.stream.Stream;

import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine.HuForInventoryLineBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class HuForInventoryLineFactory
{
	public Stream<HuForInventoryLine> ofHURecord(@NonNull final I_M_HU huRecord)
	{
		final HuForInventoryLineBuilder builder = HuForInventoryLine
				.builder()
				.orgId(OrgId.ofRepoId(huRecord.getAD_Org_ID()))
				.locatorId(IHandlingUnitsBL.extractLocatorId(huRecord));

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		return handlingUnitsBL
				.getStorageFactory()
				.streamHUProductStorages(huRecord)
				.filter(huProductStorage -> !huProductStorage.isEmpty())
				.map(huProductStorage -> createHuForInventoryLine(builder, huProductStorage));
	}

	private HuForInventoryLine createHuForInventoryLine(
			@NonNull final HuForInventoryLineBuilder huForInventoryLineBuilder,
			@NonNull final IHUProductStorage huProductStorage)
	{
		final AttributesKey attributesKey = createAttributesKey(huProductStorage.getM_HU());

		return huForInventoryLineBuilder
				.storageAttributesKey(attributesKey)
				.huId(huProductStorage.getHuId())
				.productId(huProductStorage.getProductId())
				.quantity(huProductStorage.getQty())
				.build();
	}

	private AttributesKey createAttributesKey(@NonNull final I_M_HU huRecord)
	{
		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

		final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(huRecord);

		final ImmutableAttributeSet storageRelevantSubSet = ImmutableAttributeSet.createSubSet(attributeStorage, I_M_Attribute::isStorageRelevant);

		return AttributesKeys
				.createAttributesKeyFromAttributeSet(storageRelevantSubSet)
				.orElse(AttributesKey.NONE);
	}
}
