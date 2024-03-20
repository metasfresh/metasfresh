package de.metas.greeting;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Greeting;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

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

	@NonNull
	public Optional<Greeting> getByName(@NonNull final String name)
	{
		return getGreetingsMap().getByName(name);
	}

	private GreetingsMap getGreetingsMap()
	{
		return cache.getOrLoad(0, this::retrieveGreetingsMap);
	}

	private GreetingsMap retrieveGreetingsMap()
	{
		final ImmutableList<Greeting> list = queryBL
				.createQueryBuilder(I_C_Greeting.class)

				// We need also inactive greetings. If we have to render an address, but the AD_User's greeting was dectivated,
				// then we still need the greeting in order to render that address
				//.addOnlyActiveRecordsFilter()

				.create()
				.stream(I_C_Greeting.class)
				.map(GreetingRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new GreetingsMap(list);
	}

	@NonNull
	private static Greeting fromRecord(@NonNull final I_C_Greeting record)
	{
		final IModelTranslationMap trlsMap = InterfaceWrapperHelper.getModelTranslationMap(record);

		return Greeting.builder()
				.id(GreetingId.ofRepoId(record.getC_Greeting_ID()))
				.name(record.getName())
				.greeting(trlsMap.getColumnTrl(I_C_Greeting.COLUMNNAME_Greeting, record.getGreeting()))
				.standardType(GreetingStandardType.ofNullableCode(record.getGreetingStandardType()))
				.letterSalutation(record.getLetter_Salutation())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.active(record.isActive())
				.build();
	}

	public Greeting upsertGreeting(@NonNull final UpsertGreetingRequest request)
	{
		final I_C_Greeting record = InterfaceWrapperHelper.loadOrNew(request.getGreetingId(), I_C_Greeting.class);

		record.setName(request.getName());
		record.setGreeting(request.getGreeting());
		record.setGreetingStandardType(GreetingStandardType.toCode(request.getStandardType()));
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setLetter_Salutation(request.getLetterSalutation());

		InterfaceWrapperHelper.saveRecord(record);

		return fromRecord(record);
	}

	public Optional<Greeting> getComposite(
			@Nullable final GreetingId greetingId1,
			@Nullable final GreetingId greetingId2)
	{
		return getGreetingsMap().getComposite(greetingId1, greetingId2);
	}
}
