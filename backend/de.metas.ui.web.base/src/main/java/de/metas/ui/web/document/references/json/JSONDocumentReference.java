package de.metas.ui.web.document.references.json;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

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
import de.metas.ui.web.document.references.DocumentReference;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;

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
	@Nullable
	public static JSONDocumentReference of(final DocumentReference documentReference, final JSONOptions jsonOpts)
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

	public static List<JSONDocumentReference> ofList(final Collection<DocumentReference> documentReferences, final JSONOptions jsonOpts)
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

	@JsonProperty("priority")
	private final int priority;

	@JsonProperty("internalName")
	private final String internalName;

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("targetWindowId")
	private final WindowId targetWindowId;
	@JsonProperty("targetCategory")
	private final String targetCategory;

	@JsonProperty("documentsCount")
	private final int documentsCount;

	@JsonProperty("filter")
	private final JSONDocumentFilter filter;

	@JsonProperty("loadDuration")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String loadDuration;

	private JSONDocumentReference(
			@NonNull final DocumentReference documentReference,
			@NonNull final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		id = documentReference.getId().toJson();
		priority = documentReference.getPriority().toInt();

		internalName = documentReference.getInternalName();
		caption = documentReference.getCaption(adLanguage);
		targetWindowId = documentReference.getTargetWindow().getWindowId();
		targetCategory = documentReference.getTargetWindow().getCategory();
		documentsCount = documentReference.getDocumentsCount();

		final DocumentFilter filter = documentReference.getFilter();
		this.filter = JSONDocumentFilter.of(filter, jsonOpts);

		final Duration loadDuration = documentReference.getLoadDuration();
		this.loadDuration = loadDuration != null ? TimeUtil.formatElapsed(loadDuration) : null;
	}
}
