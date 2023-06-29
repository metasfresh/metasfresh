/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.incoterms.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Incoterms;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class IncotermsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, IncotermsMap> cache = CCache.<Integer, IncotermsMap>builder()
			.tableName(I_C_Incoterms.Table_Name)
			.build();

	@NonNull
	public Incoterms getById(@NonNull final IncotermsId incotermsId)
	{
		return getMap().getById(incotermsId);
	}

	@NonNull
	public Incoterms getByValue(@NonNull final String value)
	{
		return getMap().getByValue(value);
	}

	private IncotermsMap getMap() {return cache.getOrLoad(0, this::retrieveMap);}

	private IncotermsMap retrieveMap()
	{
		final List<Incoterms> list = queryBL.createQueryBuilder(I_C_Incoterms.class)
				.stream()
				.map(IncotermsRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());

		return new IncotermsMap(list);
	}

	@NonNull
	private static Incoterms ofRecord(@NonNull final I_C_Incoterms record)
	{
		return Incoterms.builder()
				.incotermsId(IncotermsId.ofRepoId(record.getC_Incoterms_ID()))
				.value(record.getValue())
				.name(record.getName())
				.description(record.getDescription())
				.isActive(record.isActive())
				.build();
	}

	//
	//
	//

	private static class IncotermsMap
	{

		private final ImmutableList<Incoterms> list;
		private final ImmutableMap<IncotermsId, Incoterms> byId;

		public IncotermsMap(final List<Incoterms> list)
		{
			this.list = ImmutableList.copyOf(list);
			this.byId = Maps.uniqueIndex(list, Incoterms::getIncotermsId);
		}

		public Incoterms getById(@NonNull final IncotermsId incotermsId)
		{
			final Incoterms incoterms = byId.get(incotermsId);
			if (incoterms == null)
			{
				throw new AdempiereException("No Incoterms found for " + incotermsId);
			}
			return incoterms;
		}

		@NonNull
		public Incoterms getByValue(@NonNull final String value)
		{
			final List<Incoterms> result = list.stream()
					.filter(incoterms -> incoterms.isActive()
							&& Objects.equals(incoterms.getValue(), value))
					.collect(Collectors.toList());

			if (result.isEmpty())
			{
				throw new AdempiereException("No active Incoterms found for `" + value + "`");
			}
			else if (result.size() != 1)
			{
				throw new AdempiereException("More than one Incoterms found for `" + value + "`: " + result);
			}
			else
			{
				return result.get(0);
			}
		}

	}
}
