package de.metas.handlingunits.weighting;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.UpdateHUQtyRequest;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.inventory.InventoryId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
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

public class WeightHUCommand
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUQtyService huQtyService;

	private final HuId huId;
	private final PlainWeightable targetWeight;

	@Builder
	private WeightHUCommand(
			@NonNull final HUQtyService huQtyService,
			//
			@NonNull final HuId huId,
			@NonNull final IWeightable targetWeight)
	{
		this.huQtyService = huQtyService;

		this.huId = huId;
		this.targetWeight = PlainWeightable.copyOf(targetWeight);
	}

	public Optional<InventoryId> execute()
	{
		final Inventory inventoryHeader = createAndCompleteInventory();
		if (inventoryHeader == null)
		{
			return Optional.empty();
		}

		updateHUWeights();

		return Optional.of(inventoryHeader.getId());
	}

	@Nullable
	private Inventory createAndCompleteInventory()
	{
		final Quantity targetWeightNet = Quantity.of(targetWeight.getWeightNet(), targetWeight.getWeightNetUOM());

		final UpdateHUQtyRequest updateHUQtyRequest = UpdateHUQtyRequest.builder()
				.qty(targetWeightNet)
				.huId(huId)
				.build();

		return huQtyService.updateQty(updateHUQtyRequest);
	}

	private void updateHUWeights()
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IWeightable huAttributes = getHUAttributes(hu);

		huAttributes.setWeightTareAdjust(targetWeight.getWeightTareAdjust());
		huAttributes.setWeightGross(targetWeight.getWeightGross());
		huAttributes.setWeightNet(targetWeight.getWeightNet());
		huAttributes.setWeightNetNoPropagate(targetWeight.getWeightNet());
	}

	private IWeightable getHUAttributes(final I_M_HU hu)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IAttributeStorage huAttributes = huContextFactory
				.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		return Weightables.wrap(huAttributes);
	}

}
