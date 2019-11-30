package de.metas.handlingunits.generichumodel;

import static de.metas.util.Check.assume;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.generichumodel.HU.HUBuilder;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackagingCode;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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

@Repository
public class HURepository
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public HU getbyId(@NonNull final HuId id)
	{
		final I_M_HU huRecord = handlingUnitsDAO.getById(id);
		return ofRecord(huRecord);
	}

	private HU ofRecord(@NonNull final I_M_HU huRecord)
	{
		final HUIteratorListener listener = new HUIteratorListener(huRecord);

		new HUIterator()
				.setEnableStorageIteration(true)
				.setListener(listener)
				.iterate(huRecord);

		return listener.getResult();
	}

	private static class HUIteratorListener extends HUIteratorListenerAdapter
	{
		private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		private final IAttributeStorageFactory attributeStorageFactory = Services.get(IAttributeStorageFactoryService.class).createHUAttributeStorageFactory();
		private final HUStack huStack = new HUStack();

		public HUIteratorListener(I_M_HU rootHuRecord)
		{
			huStack.push(extractIdAndBuilder(rootHuRecord));
		}

		private ImmutablePair<HuId, HUBuilder> extractIdAndBuilder(@NonNull final I_M_HU rootHuRecord)
		{
			final HuId huId = extractHuId(rootHuRecord);
			final HUBuilder rootHu = createHUBuilder(rootHuRecord);
			final ImmutablePair<HuId, HUBuilder> pair = ImmutablePair.of(huId, rootHu);
			return pair;
		}

		private HuId extractHuId(@NonNull final I_M_HU rootHuRecord)
		{
			return HuId.ofRepoId(rootHuRecord.getM_HU_ID());
		}

		private HUBuilder createHUBuilder(@NonNull final I_M_HU huRecord)
		{

			final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(huRecord);
			return HU.builder()
					.type(HUType.ofCode(handlingUnitsBL.getHU_UnitType(huRecord)))
					.packagingCode(extractPackagingCodeId(huRecord))
					.atributes(attributeStorage);
		}

		private ImmutableMap<ProductId, Quantity> extractProductsAndQuantities(final I_M_HU huRecord)
		{
			final ImmutableMap<ProductId, Quantity> productsAndQuantities = handlingUnitsBL
					.getStorageFactory()
					.getStorage(huRecord).getProductStorages()
					.stream()
					.collect(ImmutableMap.toImmutableMap(
							IHUProductStorage::getProductId,
							IHUProductStorage::getQtyInStockingUOM));
			return productsAndQuantities;
		}

		@Override
		public Result beforeHU(final IMutable<I_M_HU> huMutable)
		{
			final I_M_HU huRecord = huMutable.getValue();
			huStack.push(extractIdAndBuilder(huRecord));
			return getDefaultResult();
		}

		@Override
		public Result afterHU(final I_M_HU huRecord)
		{
			final IPair<HuId, HUBuilder> currentIdAndBuilder = huStack.pop();
			assume(extractHuId(huRecord).equals(currentIdAndBuilder.getLeft()), "Current HU needs to be the one we just popped from the stack");
			final HUBuilder childBuilder = currentIdAndBuilder.getRight();

			final HUBuilder parentBuilder = huStack.peek().getRight();

			if (handlingUnitsBL.isAggregateHU(huRecord))
			{
				final ImmutableMap<ProductId, Quantity> productsAndQuantities = extractProductsAndQuantities(huRecord);

				final int logicalNumberOfTUs = huRecord.getM_HU_Item_Parent().getQty().intValue();
				final ImmutableMap<ProductId, Quantity> productsAndQuantitiesPerHU = devideQuantities(productsAndQuantities, logicalNumberOfTUs);
				childBuilder.productQuantities(productsAndQuantitiesPerHU);
				for (int i = 0; i < logicalNumberOfTUs; i++)
				{
					final HU currentChild = childBuilder.build();
					parentBuilder.childHU(currentChild);
				}
			}
			else
			{
				final ImmutableMap<ProductId, Quantity> productsAndQuantities = extractProductsAndQuantities(huRecord);
				childBuilder.productQuantities(productsAndQuantities);

				parentBuilder.childHU(childBuilder.build());
			}
			return Result.CONTINUE;
		}

		private ImmutableMap<ProductId, Quantity> devideQuantities(
				@NonNull final ImmutableMap<ProductId, Quantity> productsAndQuantities,
				final int divisor)
		{
			final ImmutableSet<Entry<ProductId, Quantity>> entrySet = productsAndQuantities.entrySet();
			final BigDecimal divisorBD = new BigDecimal(divisor);
			return entrySet
					.stream()
					.collect(ImmutableMap.toImmutableMap(
							e -> e.getKey(),
							e -> e.getValue().divide(divisorBD)));

		}

		private Optional<PackagingCode> extractPackagingCodeId(@NonNull final I_M_HU hu)
		{

			final I_M_HU_PI_Version piVersionrecord = loadOutOfTrx(hu.getM_HU_PI_Version_ID(), I_M_HU_PI_Version.class);
			final int packagingCodeRecordId = piVersionrecord.getM_HU_PackagingCode_ID();
			if (packagingCodeRecordId <= 0)
			{
				return Optional.empty();
			}
			final I_M_HU_PackagingCode packagingCodeRecord = loadOutOfTrx(packagingCodeRecordId, I_M_HU_PackagingCode.class);

			return Optional.of(PackagingCode.builder()
					.id(PackagingCodeId.ofRepoId(packagingCodeRecordId))
					.onlyForType(Optional.ofNullable(HUType.ofCodeOrNull(packagingCodeRecord.getHU_UnitType())))
					.value(packagingCodeRecord.getPackagingCode())
					.build());

		}

		@Override
		public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
		{
			return getDefaultResult();
		}

		@Override
		public Result afterHUItem(final I_M_HU_Item item)
		{
			return getDefaultResult();
		}

		@Override
		public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
		{
			return getDefaultResult();
		}

		@Override
		public Result afterHUItemStorage(final IHUItemStorage itemStorage)
		{
			return getDefaultResult();
		}

		public HU getResult()
		{
			final HU root = huStack.pop().getRight().build();
			assume(huStack.isEmpty(), "In the end, huStack needs to be empty");
			return root;
		}
	}

	private static class HUStack
	{
		private final ArrayList<HuId> huIds = new ArrayList<HuId>();
		private final HashMap<HuId, HUBuilder> hus = new HashMap<HuId, HU.HUBuilder>();

		void push(IPair<HuId, HUBuilder> idWithHuBuilder)
		{
			this.huIds.add(idWithHuBuilder.getLeft());
			this.hus.put(idWithHuBuilder.getLeft(), idWithHuBuilder.getRight());
		}

		public boolean isEmpty()
		{
			return huIds.isEmpty();
		}

		public IPair<HuId, HUBuilder> peek()
		{
			final HuId huId = this.huIds.get(huIds.size() - 1);
			final HUBuilder huBuilder = this.hus.get(huId);

			return ImmutablePair.of(huId, huBuilder);
		}

		final IPair<HuId, HUBuilder> pop()
		{
			final HuId huId = this.huIds.remove(huIds.size() - 1);
			final HUBuilder huBuilder = this.hus.remove(huId);

			return ImmutablePair.of(huId, huBuilder);
		}
	}

}
