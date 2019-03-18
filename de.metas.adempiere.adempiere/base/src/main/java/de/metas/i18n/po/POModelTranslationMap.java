package de.metas.i18n.po;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.impl.NullModelTranslation;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@link IModelTranslationMap} implementation which lazy loads all translations of a given database record, identified by {@link POTrlInfo} and a given recordId.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public final class POModelTranslationMap implements IModelTranslationMap
{
	@Getter
	private final int recordId;
	private final ImmutableMap<String, IModelTranslation> trlsByLanguage;

	@Builder
	private POModelTranslationMap(
			final int recordId,
			@NonNull final ImmutableMap<String, IModelTranslation> trlsByLanguage)
	{
		this.recordId = recordId;
		this.trlsByLanguage = trlsByLanguage;
	}

	@Override
	public IModelTranslation getTranslation(final String adLanguage)
	{
		final IModelTranslation trl = trlsByLanguage.get(adLanguage);
		return trl != null ? trl : NullModelTranslation.instance;
	}

	@Override
	public Map<String, IModelTranslation> getAllTranslations()
	{
		return trlsByLanguage;
	}
}
