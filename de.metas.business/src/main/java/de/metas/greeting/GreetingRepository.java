package de.metas.greeting;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Greeting;
import org.springframework.stereotype.Repository;

import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.Language;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

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
	private static final Map<CacheKey, Greeting> cache = new HashMap<>();

	@Value
	private static class CacheKey
	{
		GreetingId greetingId;
		Language language;
	}

	public Greeting getByIdAndLang(@NonNull final GreetingId id, @Nullable final Language language)
	{
		final I_C_Greeting greetingRecord = loadOutOfTrx(id, I_C_Greeting.class);

		final I_C_Greeting greetingTrlRecord;
		if (language == null)
		{
			greetingTrlRecord = translate(greetingRecord, I_C_Greeting.class);
		}
		else
		{
			greetingTrlRecord = translate(greetingRecord, I_C_Greeting.class, language.getAD_Language());
		}

		return Greeting.builder()
				.id(id)
				.language(language)
				.greeting(greetingTrlRecord.getGreeting())
				.build();
	}

	private void loadAll()
	{
		final List<I_C_Greeting> greetingRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Greeting.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_Greeting.class);

		final Language baseLanguage = Language.getBaseLanguage();

		for (final I_C_Greeting greetingRecord : greetingRecords)
		{
			final GreetingId greetingId = addGreetingRecordToCache(greetingRecord, baseLanguage);

			final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(greetingRecord);
			final Map<String, IModelTranslation> allTranslations = modelTranslationMap.getAllTranslations();

			final Collection<IModelTranslation> values = allTranslations.values();

			for (final IModelTranslation value : values)
			{
				addGreetingTrlToCache(greetingId, value);
			}
		}
	}

	private GreetingId addGreetingRecordToCache(
			@NonNull final I_C_Greeting greetingRecord,
			@NonNull final Language baseLanguage)
	{
		final GreetingId greetingId = GreetingId.ofRepoId(greetingRecord.getC_Greeting_ID());

		final CacheKey baseLangKey = new CacheKey(greetingId, baseLanguage);

		final Greeting baseLangGreeting = Greeting.builder()
				.id(greetingId)
				.language(baseLanguage)
				.greeting(greetingRecord.getGreeting())
				.build();
		cache.put(baseLangKey, baseLangGreeting);
		return greetingId;
	}

	private void addGreetingTrlToCache(
			@NonNull final GreetingId greetingId,
			@NonNull final IModelTranslation value)
	{
		final Language language = Language.asLanguage(value.getAD_Language());

		final CacheKey key = new CacheKey(greetingId, language);

		final Greeting greeting = Greeting.builder()
				.id(greetingId)
				.language(language)
				.greeting(value.getTranslation(I_C_Greeting.COLUMNNAME_Greeting))
				.build();
		cache.put(key, greeting);
	}
}
