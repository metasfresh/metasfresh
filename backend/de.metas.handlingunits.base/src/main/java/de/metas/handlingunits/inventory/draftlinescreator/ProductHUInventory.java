/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.inventory.draftlinescreator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Value
public class ProductHUInventory
{
	@NonNull
	ProductId productId;

	@NonNull
	ImmutableList<HuForInventoryLine> huForInventoryLineList;

	public static ProductHUInventory of(@NonNull final ProductId productId, @NonNull final List<HuForInventoryLine> huForInventoryLineList)
	{
		final boolean notAllLinesMatchTheProduct = huForInventoryLineList.stream()
				.anyMatch(huForInventoryLine -> !huForInventoryLine.getProductId().equals(productId));

		if (notAllLinesMatchTheProduct)
		{
			throw new AdempiereException("Not all huForInventoryLineList match the given product!")
					.appendParametersToMessage()
					.setParameter("huForInventoryLineList", huForInventoryLineList)
					.setParameter("productId", productId);
		}

		return new ProductHUInventory(productId, ImmutableList.copyOf(huForInventoryLineList));
	}

	@NonNull
	public Quantity getTotalQtyBooked(
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final I_C_UOM uom)
	{
		return huForInventoryLineList.stream()
				.map(HuForInventoryLine::getQuantityBooked)
				.map(qty -> uomConversionBL.convertQuantityTo(qty, productId, UomId.ofRepoId(uom.getC_UOM_ID())))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantity.zero(uom));
	}

	@NonNull
	public Map<LocatorId, ProductHUInventory> mapByLocatorId()
	{
		return mapByKey(HuForInventoryLine::getLocatorId);
	}

	@NonNull
	public Map<WarehouseId, ProductHUInventory> mapByWarehouseId()
	{
		return mapByKey(huForInventoryLine -> huForInventoryLine.getLocatorId().getWarehouseId());
	}

	@NonNull
	public List<InventoryLineHU> toInventoryLineHUs(
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final UomId targetUomId)
	{
		final UnaryOperator<Quantity> uomConverter = qty -> uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
		return huForInventoryLineList.stream()
				.map(DraftInventoryLinesCreator::toInventoryLineHU)
				.map(inventoryLineHU -> inventoryLineHU.convertQuantities(uomConverter))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Set<HuId> getHuIds()
	{
		return huForInventoryLineList.stream()
				.map(HuForInventoryLine::getHuId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private <K> Map<K, ProductHUInventory> mapByKey(final Function<HuForInventoryLine, K> keyProvider)
	{
		final Map<K, List<HuForInventoryLine>> key2Hus = new HashMap<>();

		huForInventoryLineList.forEach(hu -> {
			final ArrayList<HuForInventoryLine> husFromTargetWarehouse = new ArrayList<>();
			husFromTargetWarehouse.add(hu);

			key2Hus.merge(keyProvider.apply(hu), husFromTargetWarehouse, (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		});

		return key2Hus.keySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(Function.identity(), warehouseId -> ProductHUInventory.of(this.productId, key2Hus.get(warehouseId))));
	}
}
