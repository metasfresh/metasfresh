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
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

@Component // not calling it service, because right now it's intended to be used only from the M_Transaction model interceptor
public class HUDescriptorFromInventoryLineService
{
	private final InventoryRepository inventoryRepository;
	private final HUDescriptorService huDescriptorService;
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	public HUDescriptorFromInventoryLineService(
			@NonNull final InventoryRepository inventoryRepository,
			@NonNull final HUDescriptorService huDescriptorService)
	{
		this.inventoryRepository = inventoryRepository;
		this.huDescriptorService = huDescriptorService;
	}

	public ImmutableList<HUDescriptor> createHuDescriptorsForInventoryLine(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			final boolean deleted)
	{
		final InventoryLine inventoryLine = inventoryRepository.toInventoryLine(
				InterfaceWrapperHelper.create(inventoryLineRecord, de.metas.handlingunits.model.I_M_InventoryLine.class));

		final ImmutableList<InventoryLineHU> inventoryLineHUs = inventoryLine.getInventoryLineHUs();
		final ImmutableList<HuId> huIds = CollectionUtils.extractDistinctElements(inventoryLineHUs, InventoryLineHU::getHuId);

		return handlingUnitsDAO
				.getByIds(huIds)
				.stream()
				.flatMap(huRecord -> huDescriptorService.createHuDescriptors(huRecord, deleted).stream())
				.collect(ImmutableList.toImmutableList());
	}
}
