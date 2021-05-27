package de.metas.greeting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Greeting;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * #%L
 * de.metas.business
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
public class GreetingRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, GreetingsMap> cache = CCache.<Integer, GreetingsMap>builder()
			.tableName(I_C_Greeting.Table_Name)
			.build();

	public Greeting getById(@NonNull final GreetingId id)
	{
		return getGreetingsMap().getById(id);
	}

	private GreetingsMap getGreetingsMap()
	{
		return cache.getOrLoad(0, this::retrieveGreetingsMap);
	}

	private GreetingsMap retrieveGreetingsMap()
	{
		final ImmutableList<Greeting> list = queryBL
				.createQueryBuilder(I_C_Greeting.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_C_Greeting.class)
				.map(GreetingRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new GreetingsMap(list);
	}

	private static Greeting fromRecord(@NonNull final I_C_Greeting record)
	{
		final IModelTranslationMap trlsMap = InterfaceWrapperHelper.getModelTranslationMap(record);

		return Greeting.builder()
				.id(GreetingId.ofRepoId(record.getC_Greeting_ID()))
				.name(record.getName())
				.greeting(trlsMap.getColumnTrl(I_C_Greeting.COLUMNNAME_Greeting, record.getGreeting()))
				.build();
	}

	@EqualsAndHashCode
	@ToString
	private static class GreetingsMap
	{
		private final ImmutableMap<GreetingId, Greeting> greetingsById;

		public GreetingsMap(@NonNull final List<Greeting> greetings)
		{
			greetingsById = Maps.uniqueIndex(greetings, Greeting::getId);
		}

		public Greeting getById(@NonNull final GreetingId id)
		{
			final Greeting greeting = greetingsById.get(id);
			if (greeting == null)
			{
				throw new AdempiereException("No greeting found for " + id);
			}
			return greeting;
		}
	}
}
