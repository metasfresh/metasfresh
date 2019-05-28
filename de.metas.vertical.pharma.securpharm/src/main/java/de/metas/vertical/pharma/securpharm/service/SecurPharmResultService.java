/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLineRepository;
import de.metas.inventory.InventoryId;
import de.metas.vertical.pharma.securpharm.model.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.model.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.repository.SecurPharmResultRepository;
import lombok.NonNull;

@Service
public class SecurPharmResultService
{
	private final SecurPharmResultRepository resultRepository;
	private final InventoryLineRepository inventoryRepo;

	public SecurPharmResultService(
			@NonNull final SecurPharmResultRepository resultRepository,
			@NonNull final InventoryLineRepository inventoryRepo)
	{
		this.resultRepository = resultRepository;
		this.inventoryRepo = inventoryRepo;
	}

	public void save(@NonNull final SecurPharmProduct product)
	{
		save(product, ImmutableList.of());
	}

	public void save(@NonNull final SecurPharmProduct product, final Collection<SecurPharmLog> logs)
	{
		resultRepository.save(product, logs);
	}

	public void save(@NonNull final DecommissionResponse response, final Collection<SecurPharmLog> logs)
	{
		resultRepository.save(response, logs);
	}

	public void save(@NonNull final UndoDecommissionResponse response, final Collection<SecurPharmLog> logs)
	{
		resultRepository.save(response, logs);
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		return resultRepository.getProductById(id);
	}

	public Collection<SecurPharmProduct> getProductsByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Set<HuId> huIds = getHUIdsByInventoryId(inventoryId);
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return resultRepository.getProductDataResultByHuIds(huIds);
	}

	private Set<HuId> getHUIdsByInventoryId(final InventoryId inventoryId)
	{
		return inventoryRepo.getByInventoryId(inventoryId)
				.stream()
				.flatMap(inventoryLine -> inventoryLine.getHUIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

}
