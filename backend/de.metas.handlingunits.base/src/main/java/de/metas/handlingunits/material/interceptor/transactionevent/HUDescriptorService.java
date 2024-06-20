/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.material.interceptor.transactionevent;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class HUDescriptorService
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	@NonNull
	public ImmutableList<HUDescriptor> createHuDescriptors(@NonNull final I_M_HU huRecord)
	{
		return createHuDescriptors(huRecord, false);
	}

	public ImmutableList<HUDescriptor> createHuDescriptors(
			@NonNull final I_M_HU huRecord,
			final boolean deleted)
	{
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();
		final IHUStorage storage = huContext.getHUStorageFactory().getStorage(huRecord);

		// Important note: we could have the AttributesKey without making an ASI, but we need the ASI-ID for display reasons in the material dispo window.
		final IPair<AttributesKey, AttributeSetInstanceId> attributesKeyAndAsiId = createAttributesKeyAndAsiId(huContext, huRecord);
		final AttributesKey attributesKey = attributesKeyAndAsiId.getLeft();
		final AttributeSetInstanceId asiId = attributesKeyAndAsiId.getRight();

		final ImmutableList.Builder<HUDescriptor> descriptors = ImmutableList.builder();
		for (final IHUProductStorage productStorage : storage.getProductStorages())
		{
			final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
					productStorage.getProductId().getRepoId(),
					attributesKey,
					asiId.getRepoId());

			final BigDecimal quantity = productStorage.getQtyInStockingUOM()
					.toBigDecimal()
					.max(BigDecimal.ZERO); // guard against the quantity being e.g. -0.001 for whatever reason

			final HUDescriptor descriptor = HUDescriptor.builder()
					.huId(huRecord.getM_HU_ID())
					.productDescriptor(productDescriptor)
					.quantity(deleted ? BigDecimal.ZERO : quantity)
					.build();
			descriptors.add(descriptor);
		}
		return descriptors.build();
	}

	private IPair<AttributesKey, AttributeSetInstanceId> createAttributesKeyAndAsiId(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU hu)
	{
		final IAttributeSet attributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);

		// we don't want all the non-storage-relevant attributes to pollute the ASI we will display in the material disposition window
		final IAttributeSet storageRelevantSubSet = ImmutableAttributeSet.createSubSet(attributeStorage, I_M_Attribute::isStorageRelevant);

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIFromAttributeSet(storageRelevantSubSet);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());

		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(AttributesKey.NONE);

		return ImmutablePair.of(attributesKey, asiId);
	}
}
