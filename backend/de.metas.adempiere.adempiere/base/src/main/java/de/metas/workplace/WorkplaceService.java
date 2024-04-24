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

import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkplaceService
{
	@NonNull
	private final WorkplaceRepository workplaceRepository;
	@NonNull
	private final WorkplaceUserAssignRepository workplaceUserAssignRepository;

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
}
