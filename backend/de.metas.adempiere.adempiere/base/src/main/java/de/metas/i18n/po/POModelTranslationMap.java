package de.metas.i18n.po;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.Language;
import de.metas.i18n.impl.NullModelTranslation;
import de.metas.i18n.impl.NullModelTranslationMap;
import de.metas.logging.LogManager;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link IModelTranslationMap} implementation which lazy loads all translations of a given database record, identified by {@link POTrlInfo} and a given recordId.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class POModelTranslationMap implements IModelTranslationMap
{
	public static IModelTranslationMap of(final POTrlInfo trlInfo, final int recordId)
	{
		if(!trlInfo.isTranslated())
		{
			return NullModelTranslationMap.instance;
		}
		return new POModelTranslationMap(trlInfo, recordId);
	}

	private static final Logger logger = LogManager.getLogger(POModelTranslationMap.class);

	private final POTrlInfo trlInfo;

	private int _recordId;
	private final ConcurrentHashMap<String, IModelTranslation> _trlsByLanguage = new ConcurrentHashMap<>();
	private boolean _trlsByLanguage_FullyLoaded = false;

	private POModelTranslationMap(@NonNull final POTrlInfo trlInfo, final int recordId)
	{
		super();
		this.trlInfo = trlInfo;
		_recordId = recordId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("recordId", _recordId)
				.add("loadedLanguages", _trlsByLanguage.keySet())
				.add("trlInfo", trlInfo)
				.toString();
	}

	@Override
	public IModelTranslation getTranslation(final String adLanguage)
	{
		return getTranslation(getRecord_ID(), adLanguage);
	}

	public IModelTranslation getTranslation(final int recordId, final String adLanguage)
	{
		setRecord_ID(recordId);
		
		// If we were asked about base language, there is no point to go forward
		// because only the translations will be loaded,
		// so the effect will be the same: NullModelTranslation will be returned
		if(Language.isBaseLanguage(adLanguage))
		{
			return NullModelTranslation.instance;
		}

		try
		{
			return _trlsByLanguage.computeIfAbsent(adLanguage, k -> POTrlRepository.instance.retriveByLanguage(trlInfo, getRecord_ID(), adLanguage));
		}
		catch (final Exception e)
		{
			logger.warn("Failed loading translation for recordId={}, adLanguage={} ({})", recordId, adLanguage, this, e);
			return NullModelTranslation.instance;
		}
	}

	@Override
	public Map<String, IModelTranslation> getAllTranslations()
	{
		return getAllTranslations(getRecord_ID());
	}

	private Map<String, IModelTranslation> getAllTranslations(final int recordId)
	{
		setRecord_ID(recordId);

		final Map<String, IModelTranslation> translations;
		if (!_trlsByLanguage_FullyLoaded)
		{
			translations = POTrlRepository.instance.retriveAllById(trlInfo, recordId);
			_trlsByLanguage.clear();
			_trlsByLanguage.putAll(translations);
		}
		else
		{
			translations = ImmutableMap.copyOf(_trlsByLanguage);
		}

		return translations;
	}

	private void setRecord_ID(final int recordId)
	{
		if (_recordId == recordId)
		{
			return;
		}

		_recordId = recordId;
		_trlsByLanguage.clear();
		_trlsByLanguage_FullyLoaded = false;
	}

	private int getRecord_ID()
	{
		return _recordId;
	}
}
