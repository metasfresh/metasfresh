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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Workplace;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class WorkplaceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<Integer, WorkplacesMap> cache = CCache.<Integer, WorkplacesMap>builder()
			.tableName(I_C_Workplace.Table_Name)
			.build();

	@NonNull
	public Workplace getById(@NonNull final WorkplaceId id)
	{
		return getMap().getById(id);
	}

	@NonNull
	public Collection<Workplace> getByIds(final Collection<WorkplaceId> ids)
	{
		return getMap().getByIds(ids);
	}

	public boolean isAnyWorkplaceActive()
	{
		return !getMap().isEmpty();
	}

	public List<Workplace> getAllActive() {return getMap().getAllActive();}

	@NonNull
	private WorkplacesMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private WorkplacesMap retrieveMap()
	{
		final ImmutableList<Workplace> list = queryBL.createQueryBuilder(I_C_Workplace.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(WorkplaceRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new WorkplacesMap(list);
	}

	@NonNull
	private static Workplace fromRecord(final I_C_Workplace record)
	{
		return Workplace.builder()
				.id(WorkplaceId.ofRepoId(record.getC_Workplace_ID()))
				.name(record.getName())
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.build();
	}

	private static class WorkplacesMap
	{
		private final ImmutableMap<WorkplaceId, Workplace> byId;
		@Getter private final ImmutableList<Workplace> allActive;

		WorkplacesMap(final List<Workplace> list)
		{
			this.byId = Maps.uniqueIndex(list, Workplace::getId);
			this.allActive = ImmutableList.copyOf(list);
		}

		@NonNull
		public Workplace getById(final WorkplaceId id)
		{
			final Workplace workplace = byId.get(id);
			if (workplace == null)
			{
				throw new AdempiereException("No workplace found for " + id);
			}
			return workplace;
		}

		public Collection<Workplace> getByIds(final Collection<WorkplaceId> ids)
		{
			if (ids.isEmpty())
			{
				return ImmutableList.of();
			}

			return ids.stream()
					.map(this::getById)
					.collect(ImmutableList.toImmutableList());
		}

		public boolean isEmpty()
		{
			return byId.isEmpty();
		}
	}
}
