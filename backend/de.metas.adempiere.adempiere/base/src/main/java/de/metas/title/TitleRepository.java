/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.title;

import de.metas.i18n.Language;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Title;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

@Repository
public class TitleRepository
{
	private static final Map<TitleRepository.CacheKey, Title> cache = new HashMap<>();

	@Value
	private static class CacheKey
	{
		TitleId titleId;
		Language language;
	}

	public Title getByIdAndLang(@NonNull final TitleId id, @Nullable final Language language)
	{
		final I_C_Title titleRecord = loadOutOfTrx(id, I_C_Title.class);

		final I_C_Title titleTrlRecord;
		if (language == null)
		{
			titleTrlRecord = translate(titleRecord, I_C_Title.class);
		}
		else
		{
			titleTrlRecord = translate(titleRecord, I_C_Title.class, language.getAD_Language());
		}

		return Title.builder()
				.id(id)
				.language(language)
				.title(titleTrlRecord.getName())
				.build();
	}
}
