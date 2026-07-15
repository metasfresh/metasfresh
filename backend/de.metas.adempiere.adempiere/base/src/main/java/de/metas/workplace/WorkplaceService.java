/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace;

import com.google.common.collect.ImmutableSet;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkplaceService
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final WorkplaceRepository workplaceRepository;
	@NonNull private final WorkplaceUserAssignRepository workplaceUserAssignRepository;

	public Workplace create(@NonNull final WorkplaceCreateRequest request) {return workplaceRepository.create(request);}

	@NonNull
	public Workplace getById(@NonNull final WorkplaceId id)
	{
		return workplaceRepository.getById(id);
	}

	@NonNull
	public Collection<Workplace> getByIds(final Collection<WorkplaceId> ids)
	{
		return workplaceRepository.getByIds(ids);
	}

	@NonNull
	public Optional<Workplace> getWorkplaceByUserId(@NonNull final UserId userId)
	{
		return workplaceUserAssignRepository.getWorkplaceIdByUserId(userId)
				.map(workplaceRepository::getById);
	}

	public List<Workplace> getAllActive() {return workplaceRepository.getAllActive();}

	public Optional<WarehouseId> getWarehouseIdByUserId(@NonNull final UserId userId)
	{
		return getWorkplaceByUserId(userId).map(Workplace::getWarehouseId);
	}

	public void assignWorkplace(@NonNull UserId userId, @NonNull WorkplaceId workplaceId)
	{
		workplaceUserAssignRepository.create(WorkplaceAssignmentCreateRequest.builder().userId(userId).workplaceId(workplaceId).build());
	}

	public void assignWorkplace(@NonNull final WorkplaceAssignmentCreateRequest request)
	{
		workplaceUserAssignRepository.create(request);
	}

	public boolean isUserAssigned(@NonNull final UserId userId, @NonNull final WorkplaceId expectedWorkplaceId)
	{
		final WorkplaceId workplaceId = workplaceUserAssignRepository.getWorkplaceIdByUserId(userId).orElse(null);
		return WorkplaceId.equals(workplaceId, expectedWorkplaceId);
	}

	public boolean isAnyWorkplaceActive()
	{
		return workplaceRepository.isAnyWorkplaceActive();
	}

	public ImmutableSet<LocatorId> getAllPackingPlacePickFromLocatorIds()
	{
		return workplaceRepository.getAllPackingPlacePickFromLocatorIds();
	}

	public Set<LocatorId> getPickFromLocatorIds(final Workplace workplace)
	{
		if (workplace.getPickFromLocatorId() != null)
		{
			return ImmutableSet.of(workplace.getPickFromLocatorId());
		}
		else
		{
			return warehouseBL.getLocatorIdsByWarehouseId(workplace.getWarehouseId());
		}
	}

	/**
	 * The single target locator to deliver to for this workplace: the configured {@code PickFrom_Locator_ID} if set,
	 * otherwise the workplace warehouse's default locator (always resolvable via
	 * {@link IWarehouseBL#getOrCreateDefaultLocatorId(WarehouseId)}). Use this when exactly one delivery locator is
	 * required (e.g. the DD_Order picking-replenishment target); {@link #getPickFromLocatorIds(Workplace)} returns the
	 * multi-locator set used for availability/source filtering.
	 */
	@NonNull
	public LocatorId getPickFromLocatorIdOrWarehouseDefault(@NonNull final Workplace workplace)
	{
		final LocatorId pickFromLocatorId = workplace.getPickFromLocatorId();
		return pickFromLocatorId != null
				? pickFromLocatorId
				: warehouseBL.getOrCreateDefaultLocatorId(workplace.getWarehouseId());
	}
}
