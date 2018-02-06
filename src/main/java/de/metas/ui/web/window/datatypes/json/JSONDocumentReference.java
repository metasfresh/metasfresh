package de.metas.ui.web.window.datatypes.json;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentReference;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentReference
{
	static final JSONDocumentReference of(final DocumentReference documentReference, final JSONOptions jsonOpts)
	{
		try
		{
			return new JSONDocumentReference(documentReference, jsonOpts);
		}
		catch (Exception ex)
		{
			logger.warn("Failed convering {} to {}. Skipped", documentReference, JSONDocumentReference.class, ex);
			return null;
		}
	}

	public static final List<JSONDocumentReference> ofList(final Collection<DocumentReference> documentReferences, final JSONOptions jsonOpts)
	{
		if (documentReferences.isEmpty())
		{
			return ImmutableList.of();
		}

		return documentReferences.stream()
				.map(documentReference -> of(documentReference, jsonOpts))
				.filter(jsonDocumentReference -> jsonDocumentReference != null)
				.collect(ImmutableList.toImmutableList());
	}

	private static final transient Logger logger = LogManager.getLogger(JSONDocumentReference.class);

	@JsonProperty("id")
	private final String id;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("documentType")
	private final WindowId windowId;
	@JsonProperty("documentsCount")
	private final int documentsCount;
	@JsonProperty("filter")
	private final JSONDocumentFilter filter;

	@JsonProperty("loadDuration")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String loadDurationStr;

	private JSONDocumentReference(final DocumentReference documentReference, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAD_Language();

		id = documentReference.getId();
		caption = documentReference.getCaption(adLanguage);
		windowId = documentReference.getWindowId();
		documentsCount = documentReference.getDocumentsCount();

		final DocumentFilter filter = documentReference.getFilter();
		this.filter = JSONDocumentFilter.of(filter, adLanguage);

		final Duration loadDuration = documentReference.getLoadDuration();
		this.loadDurationStr = loadDuration != null ? formatDuration(loadDuration) : null;
	}

	private static final String formatDuration(final Duration loadDuration)
	{
		return TimeUtil.formatElapsed(loadDuration);
	}
}
